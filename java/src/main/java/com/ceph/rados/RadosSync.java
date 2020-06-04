package com.ceph.rados;

import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

public interface RadosSync {
    /**
     * Write a ByteBuffer into the object identified by oid at the specified offset
     * @param oid the object identifier
     * @param buf the byte buffer to write
     * @param offset the offset into the object
     */
    void write(String oid, ByteBuffer buf, long offset);

    /**
     * White a ByteBuffer into the object identified by oid
     * @param oid
     * @param buf
     */
    void write(String oid, ByteBuffer buf);

    /**
     * White a ByteBuffer into the object identified by oid
     * @param oid
     * @param buf
     */
    void append(String oid, ByteBuffer buf);

    int read(String oid, ByteBuffer buf);

    void remove(String oid);

    void trunc(String oid, long size);

    /**
     * Return the version of the last object read or written to.
     * This exposes the internal version number of the last object read or written via this io context
     * @return
     */
    long lastVersion();


    /**
     * Get the value of an extended attribute on an object.
     * @param oid the object identifier
     * @param name the name of the attribute
     * @param buf the buffer where the attribute's value will be read
     */
    void getXattr(String oid, String name, ByteBuffer buf);

    /**
     * Set the value of an extended attribute on an object.
     * @param oid the object identifier
     * @param name the name of the attribute
     * @param buf the buffer containing the attribute's value
     */
    void setXattr(String oid, String name, ByteBuffer buf);

    /**
     * Delete an extended attribute from an object.
     * @param oid the object identifier
     * @param name the name of the attribute
     */
    void rmXattr(String oid, String name);

    /**
     * Start iterating over xattrs on an object.
     * @param oid the object identifier
     * @return an {@link Iterator} which must be consumed until the end.
     */
    Iterator<Xattr> getXattrs(String oid);

    /**
     * Create a new write operation. This will store all actions to be performed atomically.
     * You must invoke {@link WriteOp#release()} or {@link WriteOp#close()} when you are finished with it.
     * @return
     */
    SyncWriteOp writeOp();

    /**
     * Create a new read operation. This will perform all actions atomically.
     * You must invoke {@link ReadOp#release()} or {@link ReadOp#close()} when you are finished with it.
     * @return
     */
    SyncReadOp readOp();

    /**
     * Take an exclusive lock on an object.
     * @param oid
     */
    Lock lockExclusive(String oid);

    /**
     * Take a shared lock on an object.
     * @param oid
     */
    Lock lockShared(String oid);

    /**
     *
     * @param oid
     * @param name
     * @return
     */
    List<Locker> listLockers(String oid, String name);


}
