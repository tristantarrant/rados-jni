package com.ceph.rados.impl;

import java.security.AccessController;
import java.security.PrivilegedAction;

final class SecurityActions {
    private SecurityActions() {
    }

    private static <T> T doPrivileged(PrivilegedAction<T> action) {
        if (System.getSecurityManager() != null) {
            return AccessController.doPrivileged(action);
        } else {
            return action.run();
        }
    }

    static void systemLoad(String filename) {
        doPrivileged(() -> {
            System.load(filename);
            return null;
        });
    }

    static void systemLoadLibrary(String libname) {
        doPrivileged(() -> {
            System.loadLibrary(libname);
            return null;
        });
    }

    public static String getProperty(String key) {
        return doPrivileged(() -> System.getProperty(key));
    }
}
