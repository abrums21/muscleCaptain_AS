/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_chenglong_muscle_util_MyPatchUtil */

#ifndef _Included_com_chenglong_muscle_util_MyPatchUtil
#define _Included_com_chenglong_muscle_util_MyPatchUtil
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_chenglong_muscle_util_MyPatchUtil
 * Method:    bsdiff
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_chenglong_muscle_util_PatchUtil_bsdiff
        (JNIEnv *, jobject, jstring, jstring, jstring);

/*
 * Class:     com_chenglong_muscle_util_MyPatchUtil
 * Method:    bspatch
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_chenglong_muscle_util_PatchUtil_bspatch
  (JNIEnv *, jobject, jstring, jstring, jstring);
//
//JNIEXPORT jstring JNICALL Java_com_chenglong_muscle_util_PatchUtil_bsdiff
//        (JNIEnv *, jobject, jstring, jstring, jstring) {
//    return (*env)->NewStringUTF(env, "Hello from JNI !");
//}
//
//JNIEXPORT jstring JNICALL Java_com_chenglong_muscle_util_PatchUtil_bspatch
//  (JNIEnv *, jobject, jstring, jstring, jstring);
//{
//return (*env)->NewStringUTF(env, "Hello from JNI !");
//}

#ifdef __cplusplus
}
#endif
#endif
