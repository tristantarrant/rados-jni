package com.ceph.rados;

public class ClusterStat {
    long kb;

    long kbUsed;

    long kbAvail;

    long numObjects;

    public long getKb() {
        return kb;
    }

    public long getKbUsed() {
        return kbUsed;
    }

    public long getKbAvail() {
        return kbAvail;
    }

    public long getNumObjects() {
        return numObjects;
    }
}
