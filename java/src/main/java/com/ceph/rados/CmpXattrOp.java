package com.ceph.rados;

public enum CmpXattrOp {
    EQ(1),
    NE(2),
    GT(3),
    GTE(4),
    LT(5),
    LTE(6);

    private final int value;


    CmpXattrOp(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
