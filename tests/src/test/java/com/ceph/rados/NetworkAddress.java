package com.ceph.rados;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.function.Predicate;

/**
 * @author Tristan Tarrant &lt;tristan@infinispan.org&gt;
 **/
public class NetworkAddress {
    public static InetAddress fromString(String value) throws IOException {
        switch (value) {
            case "GLOBAL":
                return globalAddress();
            case "LOOPBACK":
                return loopback();
            case "NON_LOOPBACK":
                return nonLoopback();
            case "SITE_LOCAL":
                return siteLocal();
            case "LINK_LOCAL":
                return linkLocalAddress();
            default:
                if (value.startsWith("match-interface:")) {
                    return matchInterface(value.substring(value.indexOf(':') + 1));
                } else if (value.startsWith("match-address:")) {
                    return matchAddress(value.substring(value.indexOf(':') + 1));
                } else if (value.startsWith("match-host:")) {
                    return matchHost(value.substring(value.indexOf(':') + 1));
                } else {
                    return inetAddress(value);
                }
        }
    }

    public static InetAddress globalAddress() throws IOException {
        return findAddress(a -> !a.isLoopbackAddress() && !a.isSiteLocalAddress() && !a.isLinkLocalAddress());
    }

    public static InetAddress loopback() throws IOException {
        return findAddress(InetAddress::isLoopbackAddress);
    }

    public static InetAddress nonLoopback() throws IOException {
        return findAddress(a -> !a.isLoopbackAddress());
    }

    public static InetAddress siteLocal() throws IOException {
        return findAddress(InetAddress::isSiteLocalAddress);
    }

    public static InetAddress matchInterface(String regex) throws IOException {
        return findInterface(i -> i.getName().matches(regex)).getInetAddresses().nextElement();
    }

    public static InetAddress matchAddress(String regex) throws IOException {
        return findAddress(a -> a.getHostAddress().matches(regex));
    }

    public static InetAddress matchHost(String regex) throws IOException {
        return findAddress(a -> a.getHostName().matches(regex));
    }

    public static InetAddress inetAddress(String value) throws UnknownHostException {
        return InetAddress.getByName(value);
    }

    public static InetAddress anyAddress() throws UnknownHostException {
        return InetAddress.getByAddress(new byte[]{0, 0, 0, 0});
    }

    public static InetAddress linkLocalAddress() throws IOException {
        return findAddress(InetAddress::isLinkLocalAddress);
    }

    static InetAddress findAddress(Predicate<InetAddress> matcher) throws IOException {
        for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isUp()) {

                for (Enumeration addresses = networkInterface.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    if (matcher.test(address)) {
                        return address;
                    }
                }
            }
        }
        throw new IOException("No matching addresses found");
    }

    private static NetworkInterface findInterface(Predicate<NetworkInterface> matcher) throws IOException {
        for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
            NetworkInterface networkInterface = interfaces.nextElement();
            if (networkInterface.isUp()) {
                if (matcher.test(networkInterface)) {
                    return networkInterface;
                }
            }
        }
        throw new IOException("No matching addresses found");
    }
}
