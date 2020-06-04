#include <rados/librados.h>
#include "common.h"
#include "com_ceph_rados_impl_Native.h"

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_write(JNIEnv *env, jobject instance, jlong address, jstring oid, jobject buf, jint buf_offset, jint offset, jint len)
{
    int res;
    rados_ioctx_t ioctx;
    const char *oid_ptr;
    const char *buffer;

    ioctx = (rados_ioctx_t)address;
    oid_ptr = (*env)->GetStringUTFChars(env, oid, NULL);
    buffer = (*env)->GetDirectBufferAddress(env, buf);
    res = rados_write(ioctx, oid_ptr, buffer + buf_offset, len, offset);
    (*env)->ReleaseStringUTFChars(env, oid, oid_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot write", res);
    }
}

JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_read(JNIEnv *env, jobject instance, jlong address, jstring oid, jobject buf, jint buf_offset, jint offset, jint len)
{
    int res;
    rados_ioctx_t ioctx;
    const char *oid_ptr;
    char *buffer;

    ioctx = (rados_ioctx_t)address;
    oid_ptr = (*env)->GetStringUTFChars(env, oid, NULL);
    buffer = (*env)->GetDirectBufferAddress(env, buf);
    res = rados_read(ioctx, oid_ptr, buffer + buf_offset, len, offset);
    (*env)->ReleaseStringUTFChars(env, oid, oid_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot read", res);
    }
    return res;
}

JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_getxattr(JNIEnv *env, jobject instance, jlong address, jstring oid, jstring name, jobject buf, jint buf_offset, jint len)
{
    int res;
    rados_ioctx_t ioctx;
    const char *oid_ptr;
    const char *name_ptr;
    char *buffer;

    ioctx = (rados_ioctx_t)address;
    oid_ptr = (*env)->GetStringUTFChars(env, oid, NULL);
    name_ptr = (*env)->GetStringUTFChars(env, name, NULL);
    buffer = (*env)->GetDirectBufferAddress(env, buf);
    res = rados_getxattr(ioctx, oid_ptr, name_ptr, buffer + buf_offset, len);
    (*env)->ReleaseStringUTFChars(env, oid, oid_ptr);
    (*env)->ReleaseStringUTFChars(env, name, name_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot read", res);
    }
    return res;
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_setxattr(JNIEnv *env, jobject instance, jlong address, jstring oid, jstring name, jobject buf, jint buf_offset, jint len)
{
    int res;
    rados_ioctx_t ioctx;
    const char *oid_ptr;
    const char *name_ptr;
    char *buffer;

    ioctx = (rados_ioctx_t)address;
    oid_ptr = (*env)->GetStringUTFChars(env, oid, NULL);
    name_ptr = (*env)->GetStringUTFChars(env, name, NULL);
    buffer = (*env)->GetDirectBufferAddress(env, buf);
    res = rados_setxattr(ioctx, oid_ptr, name_ptr, buffer + buf_offset, len);
    (*env)->ReleaseStringUTFChars(env, oid, oid_ptr);
    (*env)->ReleaseStringUTFChars(env, name, name_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot read", res);
    }
}

JNIEXPORT jobject JNICALL Java_com_ceph_rados_impl_Native_getxattrs_1next(JNIEnv *env, jobject instance, jlong address)
{
    int res;
    rados_xattrs_iter_t iter;
    const char *name;
    const char *val;
    size_t len;

    iter = (rados_xattrs_iter_t)address;
    res = rados_getxattrs_next(iter, &name, &val, &len);
    if (res < 0) {
        throwRadosException(env, "Cannot get next attribute", res);
    }
    if (name == NULL && val == NULL) {
        return NULL;
    }
    /* TODO: build an Xattr instance and populate it */
    return NULL;
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_getxattrs_1end(JNIEnv *env, jobject instance, jlong address)
{
    rados_getxattrs_end((rados_xattrs_iter_t)address);
}