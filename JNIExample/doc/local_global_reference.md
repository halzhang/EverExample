
#说说 JNI 的 Local Reference 和 Global Reference

##开发环境

Android Studio 2.2.3，Nexus 6 ，Android 7.0，配合许巍的《逍遥行》

## Local References

LocalRef 有一个引用表，单次调用 jni 方法时，引用数超过引用表的长度，就会 crash。

###示例代码:

 	jint i = 0;
    jstring jstring1;
    for (; i < 512; i++) {
        jstring1 = (*env)->NewStringUTF(env, "testLocalRef");
        const char *cstr = (*env)->GetStringUTFChars(env, jstring1, NULL);
        LOGI("test local ref: %s %d", cstr, i);
        (*env)->ReleaseStringUTFChars(env, jstring1, cstr);
        (*env)->DeleteLocalRef(env, jstring1);//删除引用
    }

####错误日志

	art/runtime/indirect_reference_table.cc:116] JNI ERROR (app bug): local reference table overflow (max=512)
	art/runtime/indirect_reference_table.cc:116] local reference table dump:

###测试两个系统版本，结论如下：

<table>
	<tr>
		<td>平台</td>
		<td>引用表长度</td>
	</tr>
		<tr>
		<td>Android 7.0</td>
		<td>512</td>
	</tr>
	<tr>
		<td>Android 4.3</td>
		<td>512</td>
	</tr>
</table>

Android 4.0+已经是主流版本，低于4.0版本不做测试验证；

实际上引用表长度是512，但是开发者在实现的时候最大长度不是512，而是**506**，估计是 jvm 也占用了部分资源。总之不管长度如何，内存用完了记得还回去！

**出来混，迟早要还的**

##Global Reference

Global Reference 也有一个引用表，当引用个数超过表长度时，也有Crash。如下信息：

	art/runtime/indirect_reference_table.cc:116] JNI ERROR (app bug): global reference table overflow (max=51200)

由此可以见，引用个数最大值为51200。

###示例代码：
	
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
    jstring key;
    jstring tmp_key = (*env)->NewStringUTF(env, "key_int");
    key = (*env)->NewGlobalRef(env, tmp_key);//创建一个全局引用
    (*env)->CallVoidMethod(env, bundleObject, putIntID, key, value);

此段代码作用是创建一个 Bundle 对象，调用 putInt 方法，加入一个 key_int=100 的键值对。在调用 putInt 函数的时候，创建了一个全局引用。这段代码单次调用可能没返现问题，但是一旦循环调用，就会出现上面说的错误。

如何修复？代码片段如下：

	jstring tmp_key = (*env)->NewStringUTF(env, "key_int");
    key = (*env)->NewGlobalRef(env, tmp_key);//创建一个全局引用
    (*env)->CallVoidMethod(env, bundleObject, putIntID, key, value);
    (*env)->DeleteGlobalRef(env, key);//删除全局引用
    (*env)->DeleteLocalRef(env, tmp_key);//删除局部引用

进一步优化代码：

	jint value = 100;
    jstring key = (*env)->NewStringUTF(env, "key_int");
    (*env)->CallVoidMethod(env, bundleObject, putIntID, key, value);
    (*env)->DeleteLocalRef(env, key);//删除局部引用

##扩展阅读
一篇关于局部引用在 ICS 的变化说明：[JNI Local Reference Changes in ICS](https://android-developers.googleblog.com/2011/11/jni-local-reference-changes-in-ics.html)

JNI 官方文档：[jni spec](http://docs.oracle.com/javase/1.5.0/docs/guide/jni/spec/jniTOC.html)
