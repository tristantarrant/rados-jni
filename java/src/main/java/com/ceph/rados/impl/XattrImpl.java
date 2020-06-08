package com.ceph.rados.impl;

import java.nio.ByteBuffer;

import com.ceph.rados.Xattr;

/**
 * @author Tristan Tarrant &lt;tristan@infinispan.org&gt;
 **/
public class XattrImpl implements Xattr {
    private final String name;
    private final ByteBuffer value;

    public XattrImpl(String name, ByteBuffer value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ByteBuffer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "XattrImpl{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
