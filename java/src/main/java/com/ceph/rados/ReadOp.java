package com.ceph.rados;

import java.nio.ByteBuffer;
import java.util.EnumSet;

public interface ReadOp<T extends ReadOp> extends AutoCloseable {

    default void close() {
        release();
    }

    void release();

    default T flags(EnumSet<OperationFlag> flags) {
        return flags(flags.toArray(new OperationFlag[flags.size()]));
    }

    T flags(OperationFlag... flags);

    T assertExists();

    T assertVersion(long version);

    T cmpExt(ByteBuffer buf);

    T cmpXattr(String name, CmpXattrOp op, ByteBuffer buf);

    T read(ByteBuffer buf);

    T operate(String key, OperationFlag... flags);
}
