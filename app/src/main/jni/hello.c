#include <jni.h>


/** jni规定 本地方法名 Java_调用本地方法类所在的包名_类名_方法名
 *	JNIEnv * env	java环境，提供函数供调用
 *	jobject obj 	调用本地方法的对象
 *
 *	typedef const struct JNINativeInterface* JNIEnv;
 *	JNIEnv <=> struct JNINativeInterface*
 *	env : JNIEnv * <=> struct JNINativeInterface**
 *	(*env)->NewStringUTF();
 */
jstring Java_edu_jiangxin_easymarry_activity_JNIActivity_helloFromC(JNIEnv *env, jobject obj){

	//jstring     (*NewStringUTF)(JNIEnv*, const char*); 把C字符串转化为java中字符串


	// 把C字符串转化为java中字符串
	return (*env)->NewStringUTF(env,"hello world");
}

jint Java_edu_jiangxin_easymarry_activity_JNIActivity_resultFromC(JNIEnv *env,
															 jobject obj, jint a, jint b) {

	return a + b;
}
