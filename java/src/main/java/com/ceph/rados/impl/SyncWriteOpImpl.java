package com.ceph.rados.impl;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.Map;
import java.util.Set;

import com.ceph.rados.CmpXattrOp;
import com.ceph.rados.OperationFlag;
import com.ceph.rados.SyncWriteOp;

public class SyncWriteOpImpl implements SyncWriteOp {
    final IOCtxImpl ioCtx;
    final long opAddress;

    SyncWriteOpImpl(IOCtxImpl ioCtx) {
        this.ioCtx = ioCtx;
        this.opAddress = Native.INSTANCE.create_write_op();
    }

    @Override
    public void release() {
        Native.INSTANCE.release_write_op(opAddress);
    }

    @Override
    public SyncWriteOp flags(OperationFlag... flags) {
        return this;
    }

    @Override
    public SyncWriteOp assertExists() {
        return this;
    }

    @Override
    public SyncWriteOp assertVersion(long version) {
        return this;
    }

    @Override
    public SyncWriteOp cmpExt(ByteBuffer buf) {
        assert buf.isDirect();
        return this;
    }

    @Override
    public SyncWriteOp cmpXattr(String name, CmpXattrOp op, ByteBuffer buf) {
        assert buf.isDirect();
        return this;
    }

    @Override
    public SyncWriteOp setXattr(String name, ByteBuffer buf) {
        assert buf.isDirect();
        return this;
    }

    @Override
    public SyncWriteOp rmXattr(String name) {
        return this;
    }

    @Override
    public SyncWriteOp create(boolean exclusive) {
        return this;
    }

    @Override
    public SyncWriteOp write(ByteBuffer buf) {
        assert buf.isDirect();
        return this;
    }

    @Override
    public SyncWriteOp append(ByteBuffer buf) {
        assert buf.isDirect();
        return this;
    }

    @Override
    public SyncWriteOp remove() {
        return this;
    }

    @Override
    public SyncWriteOp truncate(long offset) {
        return this;
    }

    @Override
    public SyncWriteOp zero(long offset, long len) {
        return this;
    }

    @Override
    public SyncWriteOp putAll(Map<String, ByteBuffer> map) {
        return this;
    }

    @Override
    public SyncWriteOp removeAll(Set<String> keys) {
        return this;
    }

    @Override
    public SyncWriteOp setAllocHint(long expectedObjectSize, long expectedWriteSize) {
        return this;
    }

    @Override
    public SyncWriteOp operate(String key, Instant mtime, OperationFlag... flags) {
        Native.INSTANCE.write_op_operate(opAddress, key, mtime.getEpochSecond(), OperationFlag.combine(flags));
        return this;
    }
}
