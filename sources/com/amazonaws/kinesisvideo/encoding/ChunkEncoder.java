package com.amazonaws.kinesisvideo.encoding;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class ChunkEncoder {
    public static byte[] encode(byte[] bytes, int count) {
        try {
            ByteArrayOutputStream writer = new ByteArrayOutputStream();
            writeChunkSize(writer, count);
            writeCRLF(writer);
            writePayload(writer, bytes, count);
            writeCRLF(writer);
            return writer.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException("Exception while encoding chunks ! ", e);
        }
    }

    private static void writeChunkSize(OutputStream socketWrite, int count) {
        socketWrite.write(Integer.toHexString(count).getBytes(StandardCharsets.US_ASCII));
    }

    private static void writeCRLF(OutputStream socketWrite) {
        socketWrite.write(13);
        socketWrite.write(10);
    }

    private static void writePayload(OutputStream socketWrite, byte[] payloadBytes, int count) {
        socketWrite.write(payloadBytes, 0, count);
    }

    private ChunkEncoder() {
    }
}
