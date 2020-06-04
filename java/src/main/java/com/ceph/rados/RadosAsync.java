package com.ceph.rados;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

public interface RadosAsync {

    CompletionStage<Void> write(String oid, ByteBuffer buf);

    CompletionStage<Void> append(String oid, ByteBuffer buf);

    CompletionStage<Void> remove(String oid);

    CompletionStage<Void> read(String oid, ByteBuffer buf);

    CompletionStage<Void> flush();

    ASyncWriteOp writeOp();
}
