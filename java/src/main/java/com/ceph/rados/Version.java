package com.ceph.rados;

public class Version {
    int major;
    int minor;
    int extra;

    @Override
    public String toString() {
        return "Version{" +
                "major=" + major +
                ", minor=" + minor +
                ", extra=" + extra +
                '}';
    }
}
