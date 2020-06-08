package com.ceph.rados;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author Tristan Tarrant &lt;tristan@infinispan.org&gt;
 **/
public class Util {
    public static ByteBuffer asDirectByteBuffer(String s) {
        StandardCharsets.UTF_8.encode(s);
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buf = ByteBuffer.allocateDirect(bytes.length);
        return buf.put(bytes);
    }

    public static String asString(ByteBuffer buf) {

        return StandardCharsets.UTF_8.decode(buf).toString();
    }
}
