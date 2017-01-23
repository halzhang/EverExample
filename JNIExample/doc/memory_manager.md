# JNI 内存管理
在 jni 中创建对象传递到 java 层，这部分对象的内存是如何管理的呢？
## 测试环境
Android Studio 2.2.3

Android 7.0

### 测试代码功能：
在 jni 生成一个 Bundle 对象，调用 putInt 方法，放入 key_int=100。在 java 层循环调用2000次。
### 调用代码如下：
	 int i = 0;
     while (i < 2000) {
	 	i++;
	 	Bundle data = mJNIEngine.getData();
	 	if (data != null) {
	 		int keyInt = data.getInt("key_int");
			Log.i(TAG, "onGetBundleClick: " + keyInt + " call time: " + i);
	 	}
	 }
### 操作过程：
App 启动--GC--执行调用代码-（dump java heap）-GC-（dump java heap）
操作过程中插入了两次 dump 操作，观察内存变化。
## Case 0；
### 代码如下：
	jclass bundle_class = (*env)->FindClass(env, "android/os/Bundle");
    if (bundle_class == NULL) {
        LOGW("can not find Bundle class.");
        return NULL;
    }
    jmethodID putIntID = (*env)->GetMethodID(env, bundle_class, "putInt", "(Ljava/lang/String;I)V");
    if (putIntID == NULL) {
        LOGW("can not find putInt method.");
        return NULL;
    }
    jobject bundleObject = getInstance(env, bundle_class);
    if (bundleObject == NULL) {
        LOGW("can not init Bundle object");
        return NULL;
    }
    jint value = 100;
    jstring key = (*env)->NewStringUTF(env, "key_int");
    (*env)->CallVoidMethod(env, bundleObject, putIntID, key, value);
    (*env)->DeleteLocalRef(env, key);//删除局部引用
    return bundleObject;


### 结果：
* 在第一次 dump 看到生成2000个 Bundle 对象和“key\_int”字符串
* 在第二次 dump 看到 Bundle 和 “key\_int” 已经被回收释放

## Case 1；
在这个案例，我们讲 Bundle 声明称一个静态变量，才采用全局引用赋值；

	static jobject bundle_object;
--

	jobject bundleObject = getInstance(env, bundle_class);
    if (bundleObject == NULL) {
        LOGW("can not init Bundle object");
        return NULL;
    }
    bundle_object = (*env)->NewGlobalRef(env, bundleObject);//全局引用
    jint value = 100;
    jstring key = (*env)->NewStringUTF(env, "key_int");
    (*env)->CallVoidMethod(env, bundle_object, putIntID, key, value);
    (*env)->DeleteLocalRef(env, key);//删除局部引用
    return bundle_object;

### 结果：
* 在第一次 dump 看到生成2000个 Bundle 对象和“key\_int”字符串
* 在第二次 dump 看到 Bundle 和 “key\_int” 无法被回收

## 结语
通过以上两个 case 对比，大致知道从 jni 传递到 java的对象是怎么管理的了。

以上，欢迎交流指正。
