#include <rados/librados.h>
#include <jni.h>

#ifndef _Included_com_ceph_rados_impl_common
#define _Included_com_ceph_rados_impl_common
#ifdef __cplusplus
extern "C" {
#endif


void throwRadosException(JNIEnv *env, const char *message, int err);

void ack_callback(rados_completion_t comp, void *arg);

void commit_callback(rados_completion_t comp, void *arg);

#ifdef __cplusplus
}
#endif
#endif
