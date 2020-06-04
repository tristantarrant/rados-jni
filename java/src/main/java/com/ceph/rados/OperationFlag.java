package com.ceph.rados;

import java.util.EnumSet;

public enum OperationFlag {
    NOFLAG(0),
    BALANCE_READS(1),
    LOCALIZE_READS(2),
    ORDER_READS_WRITES(4),
    IGNORE_CACHE(8),
    SKIPRWLOCKS(16),
    IGNORE_OVERLAY(32),
    FULL_TRY(64),
    FULL_FORCE(128),
    IGNORE_REDIRECT(256),
    ORDERSNAP(512),
    RETURNVEC(1024);

    private final int value;

    OperationFlag(int value) {
        this.value = value;
    }

    public static int combine(OperationFlag... flags) {
        int f = 0;
        if (flags != null) {
            for (OperationFlag flag : flags) {
                f |= flag.value;
            }
        }
        return f;
    }

    public static int combine(EnumSet<OperationFlag> flags) {
        int f = 0;
        if (flags != null) {
            for (OperationFlag flag : flags) {
                f |= flag.value;
            }
        }
        return f;
    }
}
