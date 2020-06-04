#include <rados/librados.h>
#include "common.h"
#include "com_ceph_rados_impl_Native.h"

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1create__JLjava_lang_String_2(JNIEnv *env, jobject instance, jlong address, jstring poolname)
{
    int res;
    rados_t cluster;
    const char *poolname_ptr;

    cluster = (rados_t)address;
    poolname_ptr = (*env)->GetStringUTFChars(env, poolname, NULL);
    res = rados_pool_create(cluster, poolname_ptr);
    (*env)->ReleaseStringUTFChars(env, poolname, poolname_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot create pool", res);
    }
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1create__JLjava_lang_String_2I(JNIEnv *env, jobject instance, jlong address, jstring poolname, jint crush_rule)
{
    int res;
    rados_t cluster;
    const char *poolname_ptr;

    cluster = (rados_t)address;
    poolname_ptr = (*env)->GetStringUTFChars(env, poolname, NULL);
    res = rados_pool_create_with_crush_rule(cluster, poolname_ptr, crush_rule);
    (*env)->ReleaseStringUTFChars(env, poolname, poolname_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot create pool", res);
    }
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1delete(JNIEnv *env, jobject instance, jlong address, jstring poolname)
{
    int res;
    rados_t cluster;
    const char *poolname_ptr;

    cluster = (rados_t)address;
    poolname_ptr = (*env)->GetStringUTFChars(env, poolname, NULL);
    res = rados_pool_delete(cluster, poolname_ptr);
    (*env)->ReleaseStringUTFChars(env, poolname, poolname_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot delete pool", res);
    }
}

JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_pool_1list(JNIEnv *env, jobject instance, jlong address, jobject buf, jlong len)
{
    int res;
    rados_t cluster;
    char *buffer;

    cluster = (rados_t)address;
    buffer = buf != 0 ? (*env)->GetDirectBufferAddress(env, buf) : 0;
    res = rados_pool_list(cluster, buffer, len);
    if (res < 0) {
        throwRadosException(env, "Cannot fetch pool names", res);
    }
    return res;
}

JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_pool_1lookup(JNIEnv *env, jobject instance, jlong address, jstring poolname)
{
    long res;
    rados_t cluster;
    const char *poolname_ptr;

    cluster = (rados_t)address;
    poolname_ptr = (*env)->GetStringUTFChars(env, poolname, NULL);
    res = rados_pool_lookup(cluster, poolname_ptr);
    (*env)->ReleaseStringUTFChars(env, poolname, poolname_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot lookup pool id", res);
    }
    return res;
}

JNIEXPORT jstring JNICALL Java_com_ceph_rados_impl_Native_pool_1reverse_1lookup(JNIEnv *env, jobject instance, jlong address, jlong pool_id) {
    long res;
    rados_t cluster;
    char buf[512];

    cluster = (rados_t)address;
    res = rados_pool_reverse_lookup(cluster, pool_id, buf, 512);
    if (res < 0) {
        throwRadosException(env, "Cannot reverse lookup pool name", res);
    }
    return (*env)->NewStringUTF(env, buf);
}
