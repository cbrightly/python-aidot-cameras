package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;

public interface HttpProbe {
    void onContentChunkParseEvent(Connection connection, HttpContent httpContent);

    void onContentChunkSerializeEvent(Connection connection, HttpContent httpContent);

    void onContentEncodingParseEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, ContentEncoding contentEncoding);

    void onContentEncodingParseResultEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, ContentEncoding contentEncoding);

    void onContentEncodingSerializeEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, ContentEncoding contentEncoding);

    void onContentEncodingSerializeResultEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, ContentEncoding contentEncoding);

    void onDataReceivedEvent(Connection connection, Buffer buffer);

    void onDataSentEvent(Connection connection, Buffer buffer);

    void onErrorEvent(Connection connection, HttpPacket httpPacket, Throwable th);

    void onHeaderParseEvent(Connection connection, HttpHeader httpHeader, int i);

    void onHeaderSerializeEvent(Connection connection, HttpHeader httpHeader, Buffer buffer);

    void onTransferEncodingParseEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, TransferEncoding transferEncoding);

    void onTransferEncodingSerializeEvent(Connection connection, HttpHeader httpHeader, Buffer buffer, TransferEncoding transferEncoding);

    public static class Adapter implements HttpProbe {
        public void onDataReceivedEvent(Connection connection, Buffer buffer) {
        }

        public void onDataSentEvent(Connection connection, Buffer buffer) {
        }

        public void onHeaderParseEvent(Connection connection, HttpHeader header, int size) {
        }

        public void onHeaderSerializeEvent(Connection connection, HttpHeader header, Buffer buffer) {
        }

        public void onContentChunkParseEvent(Connection connection, HttpContent content) {
        }

        public void onContentChunkSerializeEvent(Connection connection, HttpContent content) {
        }

        public void onContentEncodingParseEvent(Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
        }

        public void onContentEncodingParseResultEvent(Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
        }

        public void onContentEncodingSerializeEvent(Connection connection, HttpHeader header, Buffer buffer, ContentEncoding contentEncoding) {
        }

        public void onContentEncodingSerializeResultEvent(Connection connection, HttpHeader header, Buffer result, ContentEncoding contentEncoding) {
        }

        public void onTransferEncodingParseEvent(Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
        }

        public void onTransferEncodingSerializeEvent(Connection connection, HttpHeader header, Buffer buffer, TransferEncoding transferEncoding) {
        }

        public void onErrorEvent(Connection connection, HttpPacket httpPacket, Throwable error) {
        }
    }
}
