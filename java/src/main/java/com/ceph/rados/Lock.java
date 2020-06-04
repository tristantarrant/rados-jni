package com.ceph.rados;

public interface Lock extends AutoCloseable {
    String getOid();

    String getName();

    String getCookie();

    void release();
}
