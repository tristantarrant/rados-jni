package com.ceph.rados;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class RadosTest {

    public static final String POOL_NAME = "jni";
    public static final URI CEPH_URI = URI.create("/home/tst/Work/Ceph/cluster/etc/ceph/ceph.conf");

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mon host", "192.168.1.156");
        return properties;
    }

    @Test
    public void createDestroy() throws Exception {
        try (Rados rados = Rados.fromURI(CEPH_URI).connect()) {
            try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
                RadosSync sync = ioCtx.sync();
                long start = System.currentTimeMillis();
                ByteBuffer out = directString("value");
                ByteBuffer in = ByteBuffer.allocateDirect(10);
                for (int i = 0; i < 10000; i++) {
                    sync.write("key", out);
                    in.position(0);
                    sync.read("key", in);
                }
                System.out.printf("Time = %d\n", System.currentTimeMillis() - start);
            }
        }
    }

    @Test
    public void poolNames() throws Exception {
        try (Rados rados = Rados.fromURI(CEPH_URI).connect()) {
            List<String> pools = rados.poolNames();
            String poolName = rados.reversePoolLookup(6);
            rados.poolCreate("jni");
            List<String> ppolsAfterCreate = rados.poolNames();
            assertEquals(ppolsAfterCreate.size(), pools.size() + 1);
            rados.poolDelete("jni");
            List<String> poolsAfterDelete = rados.poolNames();
            assertEquals(poolsAfterDelete.size(), pools.size());
        }
    }

    @Test
    public void writeOp() throws Exception {
        try (Rados rados = Rados.fromProperties(getProperties()).connect()) {
            try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
                RadosSync sync = ioCtx.sync();
                sync.writeOp()
                        .flags(OperationFlag.IGNORE_CACHE, OperationFlag.IGNORE_REDIRECT)
                        .write(directString("value"))
                        .setXattr("a1", directString("v1"))
                        .setXattr("a2", directString("v2"))
                        .operate("key1", Instant.now())
                        .release();
                sync.readOp();
                RadosAsync async = ioCtx.async();
                async.writeOp()
                        .flags(OperationFlag.IGNORE_CACHE, OperationFlag.IGNORE_REDIRECT)
                        .write(directString("value"))
                        .setXattr("a1", directString("v1"))
                        .setXattr("a2", directString("v2"))
                        .operate("key2", Instant.now());
            }
        }
    }

    @Test
    public void testVersion() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1_000_000; i++) {
            Rados.version();
        }
        System.out.println("Total = " + (System.currentTimeMillis() - start));
    }

    static String byteBufferToString(ByteBuffer buffer) {
        CharsetDecoder cd = StandardCharsets.UTF_8.newDecoder();
        try {
            return cd.decode(buffer).toString();
        } catch (CharacterCodingException e) {
            throw new RuntimeException(e);
        }
    }

    ByteBuffer directString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        return byteBuffer.put(bytes);
    }

    @Test
    public void testBuffer() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(100);
        printByteBuffer(byteBuffer);
        byteBuffer.putChar('a');
        byteBuffer.putChar('b');
        byteBuffer.putChar('c');
        printByteBuffer(byteBuffer);
        byteBuffer.flip();
        printByteBuffer(byteBuffer);

    }

    private static void printByteBuffer(ByteBuffer buffer) {
        System.out.printf("Buffer='%s' (%s)\n", byteBufferToString(buffer), buffer.toString());
    }
}
