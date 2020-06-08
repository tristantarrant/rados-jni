/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_ceph_rados_impl_Native */

#ifndef _Included_com_ceph_rados_impl_Native
#define _Included_com_ceph_rados_impl_Native
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    version
 * Signature: ()Lcom/ceph/rados/Version;
 */
JNIEXPORT jobject JNICALL Java_com_ceph_rados_impl_Native_version
  (JNIEnv *, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    create
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_create
  (JNIEnv *, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    shutdown
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_shutdown
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    conf_read_file
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_conf_1read_1file
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    conf_set
 * Signature: (JLjava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_conf_1set
  (JNIEnv *, jobject, jlong, jstring, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    connect
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_connect
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    ioctx_create
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_ioctx_1create__JLjava_lang_String_2
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    ioctx_create
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_ioctx_1create__JJ
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    ioctx_destroy
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_ioctx_1destroy
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_list
 * Signature: (JLjava/nio/ByteBuffer;J)I
 */
JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_pool_1list
  (JNIEnv *, jobject, jlong, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_lookup
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_pool_1lookup
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_reverse_lookup
 * Signature: (JJ)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ceph_rados_impl_Native_pool_1reverse_1lookup
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_stat
 * Signature: (J)Lcom/ceph/rados/PoolStat;
 */
JNIEXPORT jobject JNICALL Java_com_ceph_rados_impl_Native_pool_1stat
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_create
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1create__JLjava_lang_String_2
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_create
 * Signature: (JLjava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1create__JLjava_lang_String_2I
  (JNIEnv *, jobject, jlong, jstring, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    pool_delete
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_pool_1delete
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    fsid
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ceph_rados_impl_Native_fsid
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    ioctx_get_id
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_ioctx_1get_1id
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    get_last_version
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_get_1last_1version
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    write
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;III)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_write
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    read
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;III)I
 */
JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_read
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    append
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;II)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_append
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    remove
 * Signature: (JLjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_remove
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    trunc
 * Signature: (JLjava/lang/String;J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_trunc
  (JNIEnv *, jobject, jlong, jstring, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    cmpext
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;III)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_cmpext
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    getxattr
 * Signature: (JLjava/lang/String;Ljava/lang/String;Ljava/nio/ByteBuffer;II)I
 */
JNIEXPORT jint JNICALL Java_com_ceph_rados_impl_Native_getxattr
  (JNIEnv *, jobject, jlong, jstring, jstring, jobject, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    setxattr
 * Signature: (JLjava/lang/String;Ljava/lang/String;Ljava/nio/ByteBuffer;II)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_setxattr
  (JNIEnv *, jobject, jlong, jstring, jstring, jobject, jint, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    rmxattr
 * Signature: (JLjava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_rmxattr
  (JNIEnv *, jobject, jlong, jstring, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    getxattrs
 * Signature: (JLjava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_getxattrs
  (JNIEnv *, jobject, jlong, jstring);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    getxattrs_next
 * Signature: (J)Lcom/ceph/rados/Xattr;
 */
JNIEXPORT jobject JNICALL Java_com_ceph_rados_impl_Native_getxattrs_1next
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    getxattrs_end
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_getxattrs_1end
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    aio_write
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;IIILjava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_aio_1write
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jint, jobject, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    aio_append
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;IILjava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_aio_1append
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jobject, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    aio_read
 * Signature: (JLjava/lang/String;Ljava/nio/ByteBuffer;IIILjava/util/concurrent/CompletableFuture;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_aio_1read
  (JNIEnv *, jobject, jlong, jstring, jobject, jint, jint, jint, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    aio_remove
 * Signature: (JLjava/lang/String;Ljava/util/concurrent/CompletableFuture;Ljava/util/concurrent/CompletableFuture;)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_aio_1remove
  (JNIEnv *, jobject, jlong, jstring, jobject, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    aio_flush
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_aio_1flush
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    create_write_op
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_create_1write_1op
  (JNIEnv *, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    release_write_op
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_release_1write_1op
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    write_op_operate
 * Signature: (JLjava/lang/String;JI)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_write_1op_1operate
  (JNIEnv *, jobject, jlong, jstring, jlong, jint);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    create_read_op
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_com_ceph_rados_impl_Native_create_1read_1op
  (JNIEnv *, jobject);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    release_read_op
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_release_1read_1op
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_ceph_rados_impl_Native
 * Method:    read_op_operate
 * Signature: (JLjava/lang/String;I)V
 */
JNIEXPORT void JNICALL Java_com_ceph_rados_impl_Native_read_1op_1operate
  (JNIEnv *, jobject, jlong, jstring, jint);

#ifdef __cplusplus
}
#endif
#endif
