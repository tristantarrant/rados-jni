package com.ceph.rados.impl;

import static com.ceph.rados.OperationFlag.combine;

import java.nio.ByteBuffer;

import com.ceph.rados.CmpXattrOp;
import com.ceph.rados.OperationFlag;
import com.ceph.rados.SyncReadOp;

public class SyncReadOpImpl implements SyncReadOp {

    final IOCtxImpl ioCtx;
    final long opAddress;

    SyncReadOpImpl(IOCtxImpl ioCtx) {
        this.ioCtx = ioCtx;
        this.opAddress = Native.INSTANCE.create_read_op();
    }

    @Override
    public void release() {
        Native.INSTANCE.release_read_op(opAddress);
    }

    @Override
    public SyncReadOp flags(OperationFlag... flags) {
        return this;
    }

    @Override
    public SyncReadOp assertExists() {
        return this;
    }

    @Override
    public SyncReadOp assertVersion(long version) {
        return this;
    }

    @Override
    public SyncReadOp cmpExt(ByteBuffer buf) {
        return this;
    }

    @Override
    public SyncReadOp cmpXattr(String name, CmpXattrOp op, ByteBuffer buf) {
        return this;
    }

    @Override
    public SyncReadOp read(ByteBuffer buf) {
        return this;
    }

    @Override
    public SyncReadOp operate(String key, OperationFlag... flags) {
        Native.INSTANCE.read_op_operate(opAddress, key, combine(flags));
        return this;
    }
}
