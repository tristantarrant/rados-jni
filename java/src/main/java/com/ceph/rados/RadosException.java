package com.ceph.rados;

public class RadosException extends RuntimeException {
    public RadosException(String message) {
        super(message);
    }
}
