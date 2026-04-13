package org.glassfish.grizzly.http;

import java.io.UnsupportedEncodingException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.http.util.BufferChunk;
import org.glassfish.grizzly.http.util.ByteChunk;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.utils.Charsets;

public enum Protocol {
    HTTP_0_9(0, 9),
    HTTP_1_0(1, 0),
    HTTP_1_1(1, 1),
    HTTP_2_0(2, 0);
    
    private final int majorVersion;
    private final int minorVersion;
    private final byte[] protocolBytes;
    private final String protocolString;

    public static Protocol valueOf(byte[] protocolBytes2, int offset, int len) {
        if (len == 0) {
            return HTTP_0_9;
        }
        Protocol protocol = HTTP_1_1;
        if (equals(protocol, protocolBytes2, offset, len)) {
            return protocol;
        }
        Protocol protocol2 = HTTP_1_0;
        if (equals(protocol2, protocolBytes2, offset, len)) {
            return protocol2;
        }
        Protocol protocol3 = HTTP_2_0;
        if (equals(protocol3, protocolBytes2, offset, len)) {
            return protocol3;
        }
        Protocol protocol4 = HTTP_0_9;
        if (equals(protocol4, protocolBytes2, offset, len)) {
            return protocol4;
        }
        throw new IllegalStateException("Unknown protocol " + new String(protocolBytes2, offset, len, Charsets.ASCII_CHARSET));
    }

    public static Protocol valueOf(Buffer protocolBuffer, int offset, int len) {
        if (len == 0) {
            return HTTP_0_9;
        }
        Protocol protocol = HTTP_1_1;
        if (equals(protocol, protocolBuffer, offset, len)) {
            return protocol;
        }
        Protocol protocol2 = HTTP_1_0;
        if (equals(protocol2, protocolBuffer, offset, len)) {
            return protocol2;
        }
        Protocol protocol3 = HTTP_2_0;
        if (equals(protocol3, protocolBuffer, offset, len)) {
            return protocol3;
        }
        Protocol protocol4 = HTTP_0_9;
        if (equals(protocol4, protocolBuffer, offset, len)) {
            return protocol4;
        }
        throw new IllegalStateException("Unknown protocol " + protocolBuffer.toStringContent(Charsets.ASCII_CHARSET, offset, len));
    }

    public static Protocol valueOf(DataChunk protocolC) {
        if (protocolC.getLength() == 0) {
            return HTTP_0_9;
        }
        Protocol protocol = HTTP_1_1;
        if (protocolC.equals(protocol.getProtocolBytes())) {
            return protocol;
        }
        Protocol protocol2 = HTTP_1_0;
        if (protocolC.equals(protocol2.getProtocolBytes())) {
            return protocol2;
        }
        Protocol protocol3 = HTTP_2_0;
        if (protocolC.equals(protocol3.getProtocolBytes())) {
            return protocol3;
        }
        Protocol protocol4 = HTTP_0_9;
        if (protocolC.equals(protocol4.getProtocolBytes())) {
            return protocol4;
        }
        throw new IllegalStateException("Unknown protocol " + protocolC.toString());
    }

    private static boolean equals(Protocol protocol, byte[] protocolBytes2, int offset, int len) {
        byte[] knownProtocolBytes = protocol.getProtocolBytes();
        return ByteChunk.equals(knownProtocolBytes, 0, knownProtocolBytes.length, protocolBytes2, offset, len);
    }

    private static boolean equals(Protocol protocol, Buffer protocolBuffer, int offset, int len) {
        byte[] knownProtocolBytes = protocol.getProtocolBytes();
        return BufferChunk.equals(knownProtocolBytes, 0, knownProtocolBytes.length, protocolBuffer, offset, len);
    }

    private Protocol(int majorVersion2, int minorVersion2) {
        byte[] protocolBytes0;
        this.majorVersion = majorVersion2;
        this.minorVersion = minorVersion2;
        String str = "HTTP/" + majorVersion2 + '.' + minorVersion2;
        this.protocolString = str;
        try {
            protocolBytes0 = str.getBytes("US-ASCII");
        } catch (UnsupportedEncodingException e) {
            protocolBytes0 = this.protocolString.getBytes(Charsets.ASCII_CHARSET);
        }
        this.protocolBytes = protocolBytes0;
    }

    public int getMajorVersion() {
        return this.majorVersion;
    }

    public int getMinorVersion() {
        return this.minorVersion;
    }

    public String getProtocolString() {
        return this.protocolString;
    }

    public byte[] getProtocolBytes() {
        return this.protocolBytes;
    }

    public boolean equals(String s) {
        if (s == null) {
            return false;
        }
        byte[] bArr = this.protocolBytes;
        return ByteChunk.equals(bArr, 0, bArr.length, s);
    }

    public boolean equals(DataChunk protocolC) {
        return protocolC != null && !protocolC.isNull() && protocolC.equals(this.protocolBytes);
    }
}
