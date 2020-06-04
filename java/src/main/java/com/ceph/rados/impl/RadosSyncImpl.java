package com.ceph.rados.impl;

import com.ceph.rados.*;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public class RadosSyncImpl implements RadosSync {
    private final IOCtxImpl ioCtx;

    public RadosSyncImpl(IOCtxImpl ioCtx) {
        this.ioCtx = ioCtx;
    }

    @Override
    public void write(String oid, ByteBuffer buf, long offset) {
        assert buf.isDirect();
    }

    @Override
    public void write(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        Native.INSTANCE.write(ioCtx.address, oid, buf, buf.position(), 0, buf.limit() - buf.position());
    }

    @Override
    public void append(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        Native.INSTANCE.append(ioCtx.address, oid, buf, buf.position(), 0, buf.limit() - buf.position());
    }

    @Override
    public int read(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        int len = Native.INSTANCE.read(ioCtx.address, oid, buf, buf.position(), 0, buf.limit() - buf.position());
        buf.limit(len);
        return len;
    }

    @Override
    public void remove(String oid) {
        Native.INSTANCE.remove(ioCtx.address, oid);
    }

    @Override
    public void trunc(String oid, long size) {
        Native.INSTANCE.trunc(ioCtx.address, oid, size);
    }

    @Override
    public long lastVersion() {
        return Native.INSTANCE.get_last_version(ioCtx.address);
    }

    @Override
    public void getXattr(String oid, String name, ByteBuffer buf) {
        assert buf.isDirect();
        int len = Native.INSTANCE.getxattr(ioCtx.address, oid, name, buf, buf.position(), buf.limit() - buf.position());
        buf.limit(len);
    }

    @Override
    public void setXattr(String oid, String name, ByteBuffer buf) {
        assert buf.isDirect();
        Native.INSTANCE.setxattr(ioCtx.address, oid, name, buf, buf.position(), buf.limit() - buf.position());
    }

    @Override
    public void rmXattr(String oid, String name) {
        Native.INSTANCE.rmxattr(ioCtx.address, oid, name);
    }

    @Override
    public Iterator<Xattr> getXattrs(String oid) {
        return new XattrIterator(Native.INSTANCE.getxattrs(ioCtx.address, oid));
    }

    @Override
    public SyncWriteOp writeOp() {
        return new SyncWriteOpImpl(ioCtx);
    }

    @Override
    public SyncReadOp readOp() {
        return null;
    }

    @Override
    public Lock lockExclusive(String oid) {
        return null;
    }

    @Override
    public Lock lockShared(String oid) {
        return null;
    }

    @Override
    public List<Locker> listLockers(String oid, String name) {
        return null;
    }
}
