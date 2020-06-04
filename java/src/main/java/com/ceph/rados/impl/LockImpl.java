package com.ceph.rados.impl;

import com.ceph.rados.Lock;

public class LockImpl implements Lock {
    private String oid;
    private String name;
    private String cookie;

    @Override
    public String getOid() {
        return oid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCookie() {
        return cookie;
    }

    @Override
    public void release() {
        //TODO
    }

    @Override
    public void close() throws Exception {
        release();
    }
}
