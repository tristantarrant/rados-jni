#include "common.h"

static jint JNI_VERSION = JNI_VERSION_1_8;

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    if ((*vm)->GetEnv(vm, (void**)&env, JNI_VERSION) != JNI_OK) {
        return JNI_ERR;
    }
    return JNI_VERSION;
}

void JNI_OnUnload(JavaVM *vm, void *reserved) {
    /* Nothing to do here */
}

void throwRadosException(JNIEnv *env, const char *message, int err)
{
    char msg[512];
    sprintf(msg, "%s: %d %s", message, -err, strerror(-err));
    fprintf(stderr, "%s\n", msg);
    (*env)->ThrowNew(env, (*env)->FindClass( env, "com/ceph/rados/RadosException" ), msg);
}

void ack_callback(rados_completion_t comp, void *arg) {
}

void commit_callback(rados_completion_t comp, void *arg) {
}
