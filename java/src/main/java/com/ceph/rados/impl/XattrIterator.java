package com.ceph.rados.impl;

import com.ceph.rados.Xattr;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class XattrIterator implements Iterator<Xattr>, AutoCloseable {
    final long address;
    Xattr next;
    boolean eof;

    XattrIterator(long address) {
        this.address = address;
    }

    @Override
    public boolean hasNext() {
        peek();
        return !eof;
    }

    @Override
    public Xattr next() {
        peek();
        if (eof) {
            throw new NoSuchElementException();
        } else {
            Xattr ret = next;
            next = null;
            return ret;
        }
    }

    private void peek() {
        if (next == null) { // Only read the next item if we don't have one already
            next = Native.INSTANCE.getxattrs_next(address);
            if (next == null) {
                close();
            }
        }
    }

    @Override
    public void close() {
        Native.INSTANCE.getxattrs_end(address);
        eof = true;
    }
}
