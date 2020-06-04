#include "common.h"

static jint JNI_VERSION = JNI_VERSION_1_8;
static jclass radosExceptionClass;

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION) != JNI_OK) {
        return JNI_ERR;
    }
    radosExceptionClass = (*env)->FindClass( env, "com/ceph/rados/RadosException" );
    return JNI_VERSION;
}

void JNI_OnUnload(JavaVM *vm, void *reserved) {
    /* Nothing to do here */
}

void throwRadosException(JNIEnv *env, const char *message, int err)
{
    fprintf(stderr, "%s: %d %s\n", message, -err, strerror(-err));
    (*env)->ThrowNew(env, radosExceptionClass, message);
}

void ack_callback(rados_completion_t comp, void *arg) {
}

void commit_callback(rados_completion_t comp, void *arg) {
}
