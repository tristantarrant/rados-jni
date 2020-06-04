package com.ceph.rados;

public enum ChecksumType {
    XXHASH32(0),
    XXHASH64(1),
    CRC32C(2);

    private final int value;

    ChecksumType(int value) {
        this.value = value;
    }
}
