#include <rados/librados.h>
#include "common.h"
#include "com_ceph_rados_impl_Native.h"

/**
 * JNI implementations
 */

JNIEXPORT jobject JNICALL Java_com_ceph_rados_impl_Native_version(JNIEnv* env, jobject instance)
{
    int major, minor, extra;

    jclass versionClass = (*env)->FindClass(env, "com/ceph/rados/Version");
    jobject version = (*env)->AllocObject(env, versionClass);
    jfieldID majorField = (*env)->GetFieldID(env, versionClass , "major", "I");
    jfieldID minorField = (*env)->GetFieldID(env, versionClass , "minor", "I");
    jfieldID extraField = (*env)->GetFieldID(env, versionClass , "extra", "I");

    rados_version(&major, &minor, &extra);

    (*env)->SetIntField(env, version, majorField, major);
    (*env)->SetIntField(env, version, minorField, minor);
    (*env)->SetIntField(env, version, extraField, extra);

    return version;
}

JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_create(JNIEnv *env, jobject instance)
{
    int res;
    rados_t cluster;

    res = rados_create(&cluster, NULL);
    if (res < 0) {
        throwRadosException(env, "Cannot create rados instance", res);
    }
    return (jlong) cluster;
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_shutdown(JNIEnv *env, jobject instance, jlong address)
{
    rados_shutdown((rados_t)address);
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_conf_1read_1file(JNIEnv *env, jobject instance, jlong address, jstring path)
{
    int res;
    rados_t cluster;
    const char* path_ptr;

    cluster = (rados_t)address;

    if (path != 0) {
        path_ptr = (*env)->GetStringUTFChars(env, path, NULL);
        res = rados_conf_read_file(cluster, path_ptr);
        (*env)->ReleaseStringUTFChars(env, path, path_ptr);
    } else {
        res = rados_conf_read_file(cluster, 0);
    }


    if (res < 0) {
        throwRadosException(env, "Cannot read rados configuration file", res);
    }
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_conf_1set(JNIEnv *env, jobject instance, jlong address, jstring option, jstring value)
{
    int res;
    rados_t cluster;
    const char* option_ptr;
    const char* value_ptr;

    cluster = (rados_t)address;

    option_ptr = (*env)->GetStringUTFChars(env, option, NULL);
    value_ptr = (*env)->GetStringUTFChars(env, value, NULL);
    res = rados_conf_set(cluster, option_ptr, value_ptr);
    (*env)->ReleaseStringUTFChars(env, option, option_ptr);
    (*env)->ReleaseStringUTFChars(env, value, value_ptr);

    if (res < 0) {
        throwRadosException(env, "Cannot create IO context", res);
    }
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_connect(JNIEnv *env, jobject instance, jlong address)
{
    int res;
    rados_t cluster;

    cluster = (rados_t)address;
    res = rados_connect(cluster);
    if (res < 0) {
        throwRadosException(env, "Cannot connect to cluster", res);
    }
}

JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_ioctx_1create__JLjava_lang_String_2(JNIEnv *env, jobject instance, jlong address, jstring poolname)
{
    int res;
    rados_t cluster;
    rados_ioctx_t ioctx;
    const char *poolname_ptr;

    cluster = (rados_t)address;
    poolname_ptr = (*env)->GetStringUTFChars(env, poolname, NULL);
    res = rados_ioctx_create(cluster, poolname_ptr, &ioctx);
    (*env)->ReleaseStringUTFChars(env, poolname, poolname_ptr);
    if (res < 0) {
        throwRadosException(env, "Cannot create IO context", res);
    }
    return (jlong) ioctx;
}

JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_ioctx_1create__JJ(JNIEnv *env, jobject instance, jlong address, jlong pool_id)
{
    int res;
    rados_t cluster;
    rados_ioctx_t ioctx;

    cluster = (rados_t)address;
    res = rados_ioctx_create2(cluster, pool_id, &ioctx);
    if (res < 0) {
        throwRadosException(env, "Cannot create IO context", res);
    }
    return (jlong) ioctx;
}

JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_ioctx_1destroy(JNIEnv *env, jobject instance, jlong address)
{
    rados_ioctx_t ioctx;

    ioctx = (rados_ioctx_t) address;
    rados_ioctx_destroy(ioctx);
}
