package com.ceph.rados;

import static com.ceph.rados.Util.asDirectByteBuffer;
import static com.ceph.rados.Util.asString;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public interface RadosSync {
    /**
     * Write a ByteBuffer into the object identified by oid at the specified offset
     *
     * @param oid    the object identifier
     * @param buf    the byte buffer to write
     * @param offset the offset into the object
     */
    void write(String oid, ByteBuffer buf, int offset);

    /**
     * Write a string into the object identified by oid
     *
     * @param oid   the object identifier
     * @param value the string value
     */
    default void write(String oid, String value) {
        write(oid, asDirectByteBuffer(value), 0);
    }

    /**
     * Write a ByteBuffer into the object identified by oid
     *
     * @param oid
     * @param buf
     */
    void write(String oid, ByteBuffer buf);

    /**
     * Append a ByteBuffer to the object identified by oid
     *
     * @param oid
     * @param buf
     */
    void append(String oid, ByteBuffer buf);

    /**
     * Reads the content of the object identified by oid into a supplied ByteBuffer. If the object doesn't exist
     * this method throws a {@link RadosException}
     *
     * @param oid the object identifier
     * @param buf a byte buffer with sufficient capacity to hold the content
     * @return the number of bytes read
     *
     */
    int read(String oid, ByteBuffer buf);

    /**
     * Removes the object identified by oid
     *
     * @param oid the object identifier
     */
    void remove(String oid);

    /**
     * Resizes the object identified by oid to a specified size
     *
     * @param oid
     * @param size
     */
    void trunc(String oid, long size);

    /**
     * Return the version of the last object read or written to. This exposes the internal version number of the last
     * object read or written via this io context
     *
     * @return a long value
     */
    long lastVersion();


    /**
     * Get the value of an extended attribute on an object.
     *
     * @param oid  the object identifier
     * @param name the name of the attribute
     * @param buf  the buffer where the attribute's value will be read
     */
    void getXattr(String oid, String name, ByteBuffer buf);

    /**
     * Get the value of an extended attribute on an object.
     *
     * @param oid  the object identifier
     * @param name the name of the attribute
     * @return a string representing the value
     */
    default String getXattr(String oid, String name) {
        ByteBuffer buf = ByteBuffer.allocateDirect(4096);
        getXattr(oid, name, buf);
        return asString(buf);
    }

    /**
     * Set the value of an extended attribute on an object.
     *
     * @param oid  the object identifier
     * @param name the name of the attribute
     * @param buf  the buffer containing the attribute's value
     */
    void setXattr(String oid, String name, ByteBuffer buf);

    /**
     * Set the value of an extended attribute on an object.
     *
     * @param oid   the object identifier
     * @param name  the name of the attribute
     * @param value a string representing the value
     */
    default void setXattr(String oid, String name, String value) {
        setXattr(oid, name, asDirectByteBuffer(value));
    }

    /**
     * Delete an extended attribute from an object.
     *
     * @param oid  the object identifier
     * @param name the name of the attribute
     */
    void rmXattr(String oid, String name);

    /**
     * Start iterating over xattrs on an object.
     *
     * @param oid the object identifier
     * @return an {@link Iterator} which must be consumed until the end.
     */
    Iterator<Xattr> getXattrs(String oid);

    /**
     * Create a new write operation. This will store all actions to be performed atomically. You must invoke {@link
     * WriteOp#release()} or {@link WriteOp#close()} when you are finished with it.
     *
     * @return
     */
    SyncWriteOp writeOp();

    /**
     * Create a new read operation. This will perform all actions atomically. You must invoke {@link ReadOp#release()}
     * or {@link ReadOp#close()} when you are finished with it.
     *
     * @return
     */
    SyncReadOp readOp();

    /**
     * Take an exclusive lock on an object.
     *
     * @param oid
     */
    Lock lockExclusive(String oid);

    /**
     * Take a shared lock on an object.
     *
     * @param oid
     */
    Lock lockShared(String oid);

    /**
     * @param oid
     * @param name
     * @return
     */
    List<Locker> listLockers(String oid, String name);


}
