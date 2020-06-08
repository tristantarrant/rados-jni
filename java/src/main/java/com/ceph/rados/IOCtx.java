package com.ceph.rados;

public interface IOCtx extends AutoCloseable {
    Rados getCluster();

    String getPoolName();

    long getPoolId();

    RadosSync sync();

    RadosAsync async();

    /**
     * Get pool usage statistics
     *
     * @return
     */
    PoolStat getStat();
}
