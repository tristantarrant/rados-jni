package com.ceph.rados;

import java.nio.ByteBuffer;

public interface Xattr {
    String getName();

    ByteBuffer getValue();
}
