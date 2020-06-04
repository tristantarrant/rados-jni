package com.ceph.rados.impl;

import com.ceph.rados.ASyncWriteOp;
import com.ceph.rados.RadosAsync;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class RadosAsyncImpl implements RadosAsync {
    private final IOCtxImpl ioCtx;

    public RadosAsyncImpl(IOCtxImpl ioCtx) {
        this.ioCtx = ioCtx;
    }

    @Override
    public CompletionStage<Void> write(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        CompletableFuture<Void> complete = new CompletableFuture<>();
        Native.INSTANCE.aio_write(ioCtx.address, oid, buf, buf.position(), 0, buf.limit() - buf.position(), complete, null);
        return complete;
    }

    @Override
    public CompletionStage<Void> append(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        CompletableFuture<Void> complete = new CompletableFuture<>();
        Native.INSTANCE.aio_append(ioCtx.address, oid, buf, buf.position(), buf.limit() - buf.position(), complete, null);
        return complete;
    }

    @Override
    public CompletionStage<Void> remove(String oid) {
        CompletableFuture<Void> complete = new CompletableFuture<>();
        Native.INSTANCE.aio_remove(ioCtx.address, oid, complete, null);
        return complete;
    }

    @Override
    public CompletionStage<Void> read(String oid, ByteBuffer buf) {
        assert buf.isDirect();
        return null;
    }

    @Override
    public CompletionStage<Void> flush() {
        return null;
    }

    @Override
    public ASyncWriteOp writeOp() {
        return null;
    }
}
