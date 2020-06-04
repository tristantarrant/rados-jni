package com.ceph.rados;

import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public interface WriteOp<T extends WriteOp> extends AutoCloseable {

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

    T setXattr(String name, ByteBuffer buf);

    T rmXattr(String name);

    T create(boolean exclusive);

    T write(ByteBuffer buf);

    T append(ByteBuffer buf);

    T remove();

    T truncate(long offset);

    T zero(long offset, long len);

    T putAll(Map<String, ByteBuffer> map);

    T removeAll(Set<String> keys);

    T setAllocHint(long expectedObjectSize, long expectedWriteSize);

    T operate(String key, Instant mtime, OperationFlag... flags);

    default T operate(String key) {
        return operate(key, Instant.now());
    }
}
