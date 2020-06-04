package com.ceph.rados;

import java.util.EnumSet;

public enum AllocHintFlag {
    SEQUENTIAL_WRITE(1),
    RANDOM_WRITE(2),
    SEQUENTIAL_READ(4),
    RANDOM_READ(8),
    APPEND_ONLY(16),
    IMMUTABLE(32),
    SHORTLIVED(64),
    LONGLIVED(128),
    COMPRESSIBLE(256),
    INCOMPRESSIBLE(512);

    private final int value;

    AllocHintFlag(int value) {
        this.value = value;
    }

    public static int combine(AllocHintFlag... flags) {
        int f = 0;
        if (flags != null) {
            for (AllocHintFlag flag : flags) {
                f |= flag.value;
            }
        }
        return f;
    }

    public static int combine(EnumSet<AllocHintFlag> flags) {
        int f = 0;
        if (flags != null) {
            for (AllocHintFlag flag : flags) {
                f |= flag.value;
            }
        }
        return f;
    }
}
