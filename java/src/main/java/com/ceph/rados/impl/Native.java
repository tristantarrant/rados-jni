package com.ceph.rados.impl;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;

import com.ceph.rados.PoolStat;
import com.ceph.rados.Version;
import com.ceph.rados.Xattr;

public class Native {
    public static final Native INSTANCE;

    static {
        Loader.load("rados-jni", Native.class.getClassLoader());
        INSTANCE = new Native();
    }

    private Native() {
    }

    // Global calls
    public native Version version();

    public native long create();

    public native void shutdown(long cluster_address);

    public native void conf_read_file(long cluster_address, String path);

    public native void conf_set(long cluster_address, String option, String value);

    public native void connect(long cluster_address);

    public native long ioctx_create(long cluster_address, String pool_name);

    public native long ioctx_create(long cluster_address, long poolId);

    public native void ioctx_destroy(long ioctx_address);

    public native int pool_list(long cluster_address, ByteBuffer buf, long length);

    public native long pool_lookup(long cluster_address, String pool_name);

    public native String pool_reverse_lookup(long cluster_address, long poolId);

    public native PoolStat pool_stat(long ioctx_address);

    public native void pool_create(long cluster_address, String pool_name);

    public native void pool_create(long cluster_address, String pool_name, int crush_rule);

    public native void pool_delete(long cluster_address, String pool_name);

    public native String fsid(long cluster_address);

    public native long ioctx_get_id(long ioctx_address);

    // Synchronous IO

    public native long get_last_version(long ioctx_address);

    public native void write(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int offset, int length);

    public native int read(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int offset, int length);

    public native void append(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int length);

    public native void remove(long ioctx_address, String oid);

    public native void trunc(long ioctx_address, String oid, long size);

    //TODO int rados_checksum(rados_ioctx_t io, const char * oid, rados_checksum_type_t type, const char * init_value, size_t init_value_len, size_t len, uint64_t off, size_t chunk_size, char * pchecksum, size_t checksum_len)

    public native void cmpext(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int offset, int length);

    // Xattrs

    public native int getxattr(long ioctx_address, String oid, String name, ByteBuffer buf, int offset, int length);

    public native void setxattr(long ioctx_address, String oid, String name, ByteBuffer buf, int offset, int length);

    public native void rmxattr(long ioctx_address, String oid, String name);

    public native long getxattrs(long ioctx_address, String oid);

    public native Xattr getxattrs_next(long xattrs_iterator_address);

    public native void getxattrs_end(long xattrs_iterator_address);

    // Asynchronous IO

    public native void aio_write(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int offset, int length, CompletableFuture<Void> complete, CompletableFuture<Void> ack);

    public native void aio_append(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int length, CompletableFuture<Void> complete, CompletableFuture<Void> ack);

    public native void aio_read(long ioctx_address, String oid, ByteBuffer buf, int buf_offset, int offset, int length, CompletableFuture<ByteBuffer> complete);

    public native void aio_remove(long address, String oid, CompletableFuture<Void> complete, CompletableFuture<Void> ack);

    public native void aio_flush(long ioctx_address);

    // Write ops

    public native long create_write_op();

    public native void release_write_op(long op_address);

    public native void write_op_operate(long op_address, String key, long mtime, int flags);

    // Read ops

    public native long create_read_op();

    public native void release_read_op(long op_address);

    public native void read_op_operate(long op_address, String key, int flags);
}
