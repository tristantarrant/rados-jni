package com.ceph.rados;

import static com.ceph.rados.Util.asDirectByteBuffer;
import static com.ceph.rados.Util.asString;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Tristan Tarrant &lt;tristan@infinispan.org&gt;
 **/
public class RadosTest {
    public static final String OID = "k1";
    private static CephContainer container;
    private static Rados rados;

    @BeforeAll
    public static void setup() throws IOException {
        container = new CephContainer();
        container.start();
        Path path = Files.createTempDirectory(RadosTest.class.getSimpleName());
        Path cephConf = path.resolve("ceph.conf");
        container.copyFileFromContainer("/etc/ceph/ceph.conf", cephConf.toString());
        Path cephKeyring = path.resolve("ceph.client.admin.keyring");
        container.copyFileFromContainer("/etc/ceph/ceph.client.admin.keyring", cephKeyring.toString());
        rados = Rados.fromURI(cephConf.toUri()).configurationProperty("keyring", cephKeyring.toString()).connect();
    }

    @AfterAll
    public static void teardown() throws Exception {
        try {
            rados.close();
        } finally {
            container.stop();
        }
    }

    @Test
    public void testPoolOperations() throws Exception {
        final String POOL_NAME = "test-pool";
        // This will validate that the FSID is an actual UUID
        UUID.fromString(rados.fsid());
        rados.poolCreate(POOL_NAME);
        List<String> poolNames = rados.poolNames();
        assertThat(poolNames).contains(POOL_NAME);
        long poolId = rados.poolLookup(POOL_NAME);
        assertThat(rados.reversePoolLookup(poolId)).isEqualTo(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            assertThat(ioCtx.getPoolName()).isEqualTo(POOL_NAME);
            assertThat(ioCtx.getPoolId()).isEqualTo(poolId);
            assertThat(ioCtx.getCluster()).isEqualTo(rados);
        }
    }

    @Test
    public void testSyncOperations() throws Exception {
        final String POOL_NAME = "sync-pool";
        rados.poolCreate(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            RadosSync sync = ioCtx.sync();
            ByteBuffer toWrite = asDirectByteBuffer("value");
            sync.write(OID, toWrite);
            ByteBuffer toRead = ByteBuffer.allocateDirect(1024);
            sync.read(OID, toRead);
            assertThat(toRead.limit()).isEqualTo(5);
            assertThat(UTF_8.decode(toRead).toString()).isEqualTo("value");
            sync.append(OID, asDirectByteBuffer("appended"));
            toRead.clear();
            sync.read(OID, toRead);
            assertThat(toRead.limit()).isEqualTo(13);
            assertThat(UTF_8.decode(toRead).toString()).isEqualTo("valueappended");
            sync.trunc(OID, 6);
            toRead.clear();
            sync.read(OID, toRead);
            assertThat(toRead.limit()).isEqualTo(6);
            assertThat(UTF_8.decode(toRead).toString()).isEqualTo("valuea");
            sync.remove(OID);
            toRead.clear();
            assertThatThrownBy(() -> sync.read(OID, toRead)).isInstanceOf(RadosException.class).hasMessageContaining("No such file or directory");
        }
    }

    @Test
    public void testSyncXattrOperations() throws Exception {
        final String POOL_NAME = "sync-xattr-pool";
        rados.poolCreate(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            RadosSync sync = ioCtx.sync();
            sync.write(OID, "value");
            sync.setXattr(OID, "attr", "attr_value");
            assertThat(sync.getXattr(OID, "attr")).isEqualTo("attr_value");
            Iterator<Xattr> xattrs = sync.getXattrs(OID);
            assertThat(xattrs.hasNext()).isTrue();
            assertThat(xattrs.next()).matches(xattr -> "attr".equals(xattr.getName()) && "attr_value".equals(asString(xattr.getValue())));
            assertThat(xattrs.hasNext()).isFalse();
        }
    }

    @Test
    public void testAsyncXattrOperations() throws Exception {
        final String POOL_NAME = "async-xattr-pool";
        rados.poolCreate(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            RadosAsync async = ioCtx.async();
            ByteBuffer toWrite = asDirectByteBuffer("value");
            async.write(OID, toWrite);
            async.setXattr(OID, "attr", "attr_value");
            assertThat(async.getXattr(OID, "attr")).isEqualTo("attr_value");

        }
    }

    @Test
    public void testSyncWriteOp() throws Exception {
        final String POOL_NAME = "sync-write-op-pool";
        rados.poolCreate(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            RadosSync sync = ioCtx.sync();
            sync.writeOp()
                    .flags(OperationFlag.IGNORE_CACHE, OperationFlag.IGNORE_REDIRECT)
                    .write(asDirectByteBuffer("value"))
                    .setXattr("a1", asDirectByteBuffer("v1"))
                    .setXattr("a2", asDirectByteBuffer("v2"))
                    .operate("key1", Instant.now())
                    .release();
            sync.readOp();
        }
    }

    @Test
    public void testAsyncWriteOp() throws Exception {
        final String POOL_NAME = "async-write-op-pool";
        rados.poolCreate(POOL_NAME);
        try (IOCtx ioCtx = rados.createIOContext(POOL_NAME)) {
            RadosAsync async = ioCtx.async();
            async.writeOp()
                    .flags(OperationFlag.IGNORE_CACHE, OperationFlag.IGNORE_REDIRECT)
                    .write("value")
                    .setXattr("a1", "v1")
                    .setXattr("a2", "v2")
                    .operate("key2", Instant.now());
        }
    }
}
