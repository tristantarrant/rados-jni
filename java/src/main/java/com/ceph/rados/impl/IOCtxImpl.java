package com.ceph.rados.impl;

import com.ceph.rados.IOCtx;
import com.ceph.rados.PoolStat;
import com.ceph.rados.Rados;
import com.ceph.rados.RadosAsync;
import com.ceph.rados.RadosSync;

class IOCtxImpl implements IOCtx {
    final RadosImpl rados;
    final long address;
    String poolName;
    long poolId = -1;

    IOCtxImpl(RadosImpl rados, String poolName) {
        this.rados = rados;
        this.poolName = poolName;
        this.address = Native.INSTANCE.ioctx_create(this.rados.address, poolName);
    }

    public IOCtxImpl(RadosImpl rados, long poolId) {
        this.rados = rados;
        this.poolId = poolId;
        this.address = Native.INSTANCE.ioctx_create(this.rados.address, poolId);
    }

    @Override
    public String getPoolName() {
        return poolName != null ? poolName : Native.INSTANCE.pool_reverse_lookup(this.rados.address, poolId);
    }

    @Override
    public long getPoolId() {
        return poolId >= 0 ? poolId : Native.INSTANCE.pool_lookup(this.rados.address, poolName);
    }

    @Override
    public Rados getCluster() {
        return rados;
    }

    @Override
    public RadosSync sync() {
        return new RadosSyncImpl(this);
    }

    @Override
    public RadosAsync async() {
        return new RadosAsyncImpl(this);
    }

    @Override
    public void close() throws Exception {
        Native.INSTANCE.ioctx_destroy(address);
    }

    @Override
    public PoolStat getStat() {
        return Native.INSTANCE.pool_stat(address);
    }
}
