package com.ceph.rados;

import static com.ceph.rados.Util.asDirectByteBuffer;
import static com.ceph.rados.Util.asString;

import java.nio.ByteBuffer;
import java.util.concurrent.CompletionStage;

public interface RadosAsync {

    CompletionStage<Void> write(String oid, ByteBuffer buf);

    CompletionStage<Void> append(String oid, ByteBuffer buf);

    CompletionStage<Void> remove(String oid);

    CompletionStage<Void> read(String oid, ByteBuffer buf);

    CompletionStage<Void> flush();

    CompletionStage<Void> setXattr(String oid, String name, ByteBuffer buf);

    default CompletionStage<Void> setXattr(String oid, String name, String value) {
        return setXattr(oid, name, asDirectByteBuffer(value));
    }

    /**
     * Asynchronously get the value of an extended attribute on an object.
     *
     * @param oid
     * @param buf
     * @return
     */
    CompletionStage<Void> getXattr(String oid, String name, ByteBuffer buf);


    default CompletionStage<String> getXattr(String oid, String name) {
        ByteBuffer buf = ByteBuffer.allocateDirect(4096);
        return getXattr(oid, name, buf).thenApply(v -> asString(buf));
    }

    /**
     * Asynchronously delete an extended attribute from an object.
     *
     * @param oid
     * @param name
     * @return
     */
    CompletionStage<Void> rmXattr(String oid, String name);

    ASyncWriteOp writeOp();
}
