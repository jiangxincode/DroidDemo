#include <jni.h>
#include "../nativeCode/modelAssimp/modelAssimp.h"


#ifdef __cplusplus
extern "C" {
#endif

extern ModelAssimp *gAssimpObject;

JNIEXPORT void JNICALL
Java_edu_jiangxin_droiddemo_opengl_assimp_MyGLRenderer_DrawFrameNative(JNIEnv *env,
                                                                      jobject instance) {

    if (gAssimpObject == NULL) {
        return;
    }
    gAssimpObject->Render();

}

JNIEXPORT void JNICALL
Java_edu_jiangxin_droiddemo_opengl_assimp_MyGLRenderer_SurfaceCreatedNative(JNIEnv *env,
                                                                           jobject instance) {

    if (gAssimpObject == NULL) {
        return;
    }
    gAssimpObject->PerformGLInits();

}

JNIEXPORT void JNICALL
Java_edu_jiangxin_droiddemo_opengl_assimp_MyGLRenderer_SurfaceChangedNative(JNIEnv *env,
                                                                           jobject instance,
                                                                           jint width,
                                                                           jint height) {

    if (gAssimpObject == NULL) {
        return;
    }
    gAssimpObject->SetViewport(width, height);

}

#ifdef __cplusplus
}
#endif

