//
// Created by Administrator on 2016/5/20.
//
#include <jni.h>
#include <android/log.h>

#define NULL '\0'

#define  LOG_TAG    "jniexample"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

#include "com_halzhang_android_example_jniexample_JNIEngine.h"

//static jobject bundle_object;

jobject getInstance(JNIEnv *env, jclass obj_class);


/*
 * Class:     com_halzhang_android_example_jniexample_JNIEngine
 * Method:    init
 * Signature: (Ljava/lang/Object;)Z
 */
JNIEXPORT jboolean JNICALL
Java_com_halzhang_android_example_jniexample_JNIEngine_init(JNIEnv *env, jobject jniEngine,
                                                            jobject context) {

    jclass cls = (*env)->FindClass(env, "android/content/Context");
    jmethodID mid = (*env)->GetMethodID(env, cls, "getSystemService",
                                        "(Ljava/lang/String;)Ljava/lang/Object;");
    jfieldID fid = (*env)->GetStaticFieldID(env, cls, "TELEPHONY_SERVICE", "Ljava/lang/String;");
    jstring str = (*env)->GetStaticObjectField(env, cls, fid);
    jobject telephony = (*env)->CallObjectMethod(env, context, mid, str);
    cls = (*env)->FindClass(env, "android/telephony/TelephonyManager");
    mid = (*env)->GetMethodID(env, cls, "getDeviceId", "()Ljava/lang/String;");
    str = (*env)->CallObjectMethod(env, telephony, mid);
    if (str != NULL) {
        jsize len = (*env)->GetStringUTFLength(env, str);
        char *deviceId = calloc(len + 1, 1);
        (*env)->GetStringUTFRegion(env, str, 0, len, deviceId);
        LOGI("deviceId = %s", deviceId);
        (*env)->DeleteLocalRef(env, str);
        return JNI_TRUE;
    }

    return JNI_FALSE;
}

char *StringToChar(JNIEnv *env, jstring string) {
    if (string != NULL) {
        jsize len = (*env)->GetStringUTFLength(env, string);
        char *charValue = calloc(len + 1, 1);
        (*env)->GetStringUTFRegion(env, string, 0, len, charValue);
        return charValue;
    }
    return 0;
}

JNIEXPORT jboolean JNICALL
Java_com_halzhang_android_example_jniexample_JNIEngine_testLocalRef(JNIEnv *env, jobject instance) {

    jint i = 0;
    jstring jstring1;
    for (; i < 512; i++) {
        jstring1 = (*env)->NewStringUTF(env, "testLocalRef");
        const char *cstr = (*env)->GetStringUTFChars(env, jstring1, NULL);
        LOGI("test local ref: %s %d", cstr, i);
        (*env)->ReleaseStringUTFChars(env, jstring1, cstr);
        (*env)->DeleteLocalRef(env, jstring1);
    }
    return JNI_TRUE;

}

JNIEXPORT jobject JNICALL
Java_com_halzhang_android_example_jniexample_JNIEngine_getData(JNIEnv *env, jobject instance) {

    // TODO
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
//    bundle_object = (*env)->NewGlobalRef(env, bundleObject);//全局引用
    jint value = 100;
    jstring key = (*env)->NewStringUTF(env, "key_int");
    (*env)->CallVoidMethod(env, bundleObject, putIntID, key, value);
    (*env)->DeleteLocalRef(env, key);//删除局部引用
    return bundleObject;
}

jobject getInstance(JNIEnv *env, jclass obj_class) {
    jmethodID construction_id = (*env)->GetMethodID(env, obj_class, "<init>", "()V");
    jobject obj = (*env)->NewObject(env, obj_class, construction_id);
    return obj;
}

JNIEXPORT jstring JNICALL
Java_com_halzhang_android_example_jniexample_JNIEngine_createString(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env,
                                "Java_com_halzhang_android_example_jniexample_JNIEngine_createString");
}