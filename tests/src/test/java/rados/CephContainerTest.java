package com.ceph.rados;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

/**
 * @author Tristan Tarrant &lt;tristan@infinispan.org&gt;
 **/
public class CephContainerTest {

    @Test
    public void testCephContainer() {
        try (CephContainer container = new CephContainer()) {
            container.start();
            container.copyFileFromContainer("/etc/ceph/ceph.conf", "/tmp/ceph.conf");
            container.copyFileFromContainer("/etc/ceph/ceph.client.admin.keyring", "/tmp/ceph.client.admin.keyring");
            try (Rados rados = Rados.fromURI(Paths.get("/tmp/ceph.conf").toUri()).configurationProperty("keyring", "/tmp/ceph.client.admin.keyring").connect()) {
                System.out.println(rados.fsid());
                for(String pool : rados.poolNames()) {
                    System.out.println(pool);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
