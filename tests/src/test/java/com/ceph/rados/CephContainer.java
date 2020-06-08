package com.ceph.rados;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.HostPortWaitStrategy;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.nio.file.Path;
import java.time.Duration;

public class CephContainer extends GenericContainer<CephContainer> {
    public static final String IMAGE = "ceph/daemon";
    public static final String DEFAULT_TAG = "latest-octopus";
    private static byte[] netMaskByPrefix = {(byte) 128, (byte) 192, (byte) 224, (byte) 240, (byte) 248, (byte) 252, (byte) 254};
    private String bucketName = "CEPH";
    private String rgwName = "localhost";
    private String demoUid = "ceph";
    private String demoAccessKey = "access";
    private String demoSecretKey = "secret";
    private String monIP;
    private NetworkAutoDetectMode networkAutoDetectMode = NetworkAutoDetectMode.IPV4_ONLY;
    private Path cephEtcPath;
    private String cephPublicNetwork;
    private String rgwFrontendType = "beast";
    private String volumeName;

    public CephContainer() {
        this(IMAGE + ":" + DEFAULT_TAG);
    }

    public CephContainer(String dockerImageName) {
        super(dockerImageName);
        try {
            InetAddress address = NetworkAddress.siteLocal();
            monIP = address.getHostAddress();
            NetworkInterface iface = NetworkInterface.getByInetAddress(address);
            InterfaceAddress interfaceAddress = iface.getInterfaceAddresses().stream().filter(addr -> addr.getAddress().equals(address)).findFirst().get();
            cephPublicNetwork = interfaceAddressToSubnet(interfaceAddress);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String interfaceAddressToSubnet(InterfaceAddress interfaceAddress) {
        byte[] inetAddress = interfaceAddress.getAddress().getAddress();
        short prefixLength = interfaceAddress.getNetworkPrefixLength();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inetAddress.length; i++) {
            if (i > 0) {
                sb.append('.');
            }
            int a = inetAddress[i] & 0xff;
            if (prefixLength >= 8) {
                sb.append(a);
                prefixLength -= 8;
            } else if (prefixLength > 0) {
                sb.append(a & netMaskByPrefix[prefixLength - 1]);
                prefixLength = 0;
            } else {
                sb.append("0");
            }
        }
        return sb.append('/').append(interfaceAddress.getNetworkPrefixLength()).toString();
    }

    @Override
    protected void configure() {
        withEnv("RGW_NAME", rgwName)
                .withEnv("RGW_FRONTEND_TYPE", rgwFrontendType)
                .withEnv("NETWORK_AUTO_DETECT", networkAutoDetectMode.value)
                .withEnv("CEPH_DAEMON", "demo")
                .withEnv("CEPH_DEMO_UID", demoUid)
                .withEnv("CEPH_DEMO_ACCESS_KEY", demoAccessKey)
                .withEnv("CEPH_DEMO_SECRET_KEY", demoSecretKey)
                .withEnv("MON_IP", monIP)
                .withEnv("CEPH_PUBLIC_NETWORK", cephPublicNetwork)
                .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Setting up a demo user.*").withStartupTimeout(Duration.ofSeconds(30)))
                .withLogConsumer(new Slf4jLogConsumer(logger()));
    }

    public CephContainer withBucketName(String bucketName) {
        this.bucketName = bucketName.toUpperCase();
        return self();
    }

    public CephContainer withRgwName(String rgwName) {
        this.rgwName = rgwName;
        return self();
    }

    public CephContainer withDemoUid(String demoUid) {
        this.demoUid = demoUid;
        return self();
    }

    public CephContainer withNetworkAutoDetectMode(NetworkAutoDetectMode networkAutoDetectMode) {
        this.networkAutoDetectMode = networkAutoDetectMode;
        return self();
    }

    public Path getCephEtcPath() {
        return cephEtcPath;
    }

    public enum NetworkAutoDetectMode {
        IPV6_OR_IPV4("1"),
        IPV4_ONLY("4"),
        IPV6_ONLY("6");

        private final String value;

        NetworkAutoDetectMode(String value) {
            this.value = value;
        }
    }

    public enum RgwFrontendType {
        CIVETWEB,
        BEAST
    }
}
