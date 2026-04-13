package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Cacheable;

public abstract class HttpPacket implements Cacheable {
    public abstract HttpHeader getHttpHeader();

    public abstract boolean isHeader();

    public static boolean isHttp(Object packet) {
        return HttpPacket.class.isAssignableFrom(packet.getClass());
    }
}
