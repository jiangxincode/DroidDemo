#include <jni.h>
#include "../nativeCode/modelAssimp/modelAssimp.h"
#include "../nativeCode/common/myJNIHelper.h"

#ifdef __cplusplus
extern "C" {
#endif

// global pointer is used in JNI calls to reference to same object of type Cube
ModelAssimp *gAssimpObject =NULL;

// global pointer to instance of MyJNIHelper that is used to read from assets
MyJNIHelper * gHelperObject=NULL;

/**
 * Create the persistent native object and also initialize the single helper object
 */
JNIEXPORT void JNICALL
Java_edu_jiangxin_opengldemo_AssimpActivity_CreateObjectNative(JNIEnv *env,
                                                                         jobject instance,
                                                                         jobject assetManager,
                                                                         jstring pathToInternalDir) {

    gHelperObject = new MyJNIHelper(env, instance, assetManager, pathToInternalDir);
    gAssimpObject = new ModelAssimp();
}

JNIEXPORT void JNICALL
Java_edu_jiangxin_opengldemo_AssimpActivity_DeleteObjectNative(JNIEnv *env,
                                                                         jobject instance) {
    if (gAssimpObject != NULL) {
        delete gAssimpObject;
    }
    gAssimpObject = NULL;

    if (gHelperObject != NULL) {
        delete gHelperObject;
    }
    gHelperObject = NULL;
}

#ifdef __cplusplus
}
#endif
