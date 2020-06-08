package com.ceph.rados;

import static com.ceph.rados.Util.asDirectByteBuffer;

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

    default T cmpExt(String s) {
        return cmpExt(asDirectByteBuffer(s));
    }

    T cmpXattr(String name, CmpXattrOp op, ByteBuffer buf);

    default T cmpXattr(String name, CmpXattrOp op, String value) {
        return cmpXattr(name, op, asDirectByteBuffer(value));
    }

    T setXattr(String name, ByteBuffer buf);

    default T setXattr(String name, String s) {
        return setXattr(name, asDirectByteBuffer(s));
    }

    T rmXattr(String name);

    T create(boolean exclusive);

    T write(ByteBuffer buf);

    default T write(String s) {
        return write(asDirectByteBuffer(s));
    }

    T append(ByteBuffer buf);

    default T append(String s) {
        return append(asDirectByteBuffer(s));
    }

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
