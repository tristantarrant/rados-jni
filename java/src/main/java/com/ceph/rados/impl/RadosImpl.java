package com.ceph.rados.impl;

import com.ceph.rados.IOCtx;
import com.ceph.rados.Rados;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RadosImpl implements Rados {
    final long address;
    boolean connected;

    public RadosImpl(URI uri) {
        address = Native.INSTANCE.create();
        configurationFile(uri);
    }

    public RadosImpl(Properties properties) {
        address = Native.INSTANCE.create();
        properties.forEach((k, v) -> configurationProperty((String) k, v.toString()));
    }

    @Override
    public Rados configurationFile(URI uri) {
        Native.INSTANCE.conf_read_file(address, uri == null ? null : uri.getPath());
        return this;
    }

    @Override
    public Rados configurationProperty(String name, String value) {
        Native.INSTANCE.conf_set(address, name, value);
        return this;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public synchronized Rados connect() {
        if (!connected) {
            Native.INSTANCE.connect(address);
            connected = true;
        }
        return this;
    }

    @Override
    public IOCtx createIOContext(String poolName) {
        return new IOCtxImpl(this, poolName);
    }

    @Override
    public IOCtx createIOContext(long poolId) {
        return new IOCtxImpl(this, poolId);
    }

    @Override
    public synchronized void close() throws Exception {
        if (connected) {
            Native.INSTANCE.shutdown(address);
            connected = false;
        }
    }

    @Override
    public List<String> poolNames() {
        // Call it first with a small buffer
        int size = Native.INSTANCE.pool_list(address, null, 0);
        ByteBuffer buf = ByteBuffer.allocateDirect(size);
        size = Native.INSTANCE.pool_list(address, buf, size);
        List<String> names = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            byte b = buf.get();
            if (b == 0 && sb.length() > 0) {
                names.add(sb.toString());
                sb = new StringBuilder();
            } else {
                sb.append((char) (b & 0xFF));
            }
        }
        return names;
    }

    @Override
    public long poolLookup(String poolName) {
        return Native.INSTANCE.pool_lookup(address, poolName);
    }

    @Override
    public String reversePoolLookup(long poolId) {
        return Native.INSTANCE.pool_reverse_lookup(address, poolId);
    }

    @Override
    public void poolCreate(String poolName) {
        Native.INSTANCE.pool_create(address, poolName);
    }

    @Override
    public void poolCreate(String poolName, int crushRule) {
        Native.INSTANCE.pool_create(address, poolName, crushRule);
    }

    @Override
    public void poolDelete(String poolName) {
        Native.INSTANCE.pool_delete(address, poolName);
    }

    @Override
    public String fsid() {
        return Native.INSTANCE.fsid(address);
    }
}
