//
// Created by Administrator on 2016/5/20.
//
#include <jni.h>
#include <android/log.h>

#define NULL '\0'

#define  LOG_TAG    "jniexample"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define  LOGV(...)  __android_log_print(ANDROID_LOG_VERBOSE,LOG_TAG,__VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#include "com_halzhang_android_example_jniexample_JNIEngine.h"

/*
 * Class:     com_halzhang_android_example_jniexample_JNIEngine
 * Method:    init
 * Signature: (Ljava/lang/Object;)Z
 */
JNIEXPORT jboolean JNICALL Java_com_halzhang_android_example_jniexample_JNIEngine_init
  (JNIEnv * env, jobject jniEngine, jobject context)
{

        jclass cls = (*env)->FindClass(env, "android/content/Context");
        jmethodID mid = (*env)->GetMethodID(env, cls, "getSystemService", "(Ljava/lang/String;)Ljava/lang/Object;");
        jfieldID fid = (*env)->GetStaticFieldID(env, cls, "TELEPHONY_SERVICE", "Ljava/lang/String;");
        jstring str = (*env)->GetStaticObjectField(env, cls, fid);
        jobject telephony = (*env)->CallObjectMethod(env, context, mid, str);
        cls = (*env)->FindClass(env, "android/telephony/TelephonyManager");
        mid =(*env)->GetMethodID(env, cls, "getDeviceId", "()Ljava/lang/String;");
        str = (*env)->CallObjectMethod(env, telephony, mid);
        if(str != NULL){
            jsize len = (*env)->GetStringUTFLength(env, str);
            char* deviceId = calloc(len + 1, 1);
            (*env)->GetStringUTFRegion(env, str, 0, len, deviceId);
            LOGI("deviceId = %s",deviceId);
            (*env)->DeleteLocalRef(env, str);
            return JNI_TRUE;
        }

        return JNI_FALSE;
}
