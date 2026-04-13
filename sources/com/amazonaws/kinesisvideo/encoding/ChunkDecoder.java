package com.amazonaws.kinesisvideo.encoding;

import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.model.Response;
import com.amazonaws.kinesisvideo.model.ResponseStatus;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class ChunkDecoder {
    private static final int BUFFER_SIZE = 4096;
    private static final int HEX_RADIX = 16;
    private static final String LINE_DELIMITER = "\r\n";
    private static final Log LOG = new Log(Log.SYSTEM_OUT);
    private static final int MAX_BUFFER_BYTES = 16384;
    private static final String PAYLOAD_DELIMITER = "\r\n\r\n";

    private ChunkDecoder() {
    }

    public static Map<String, String> decodeHeaders(InputStream inputStream) {
        return parseHeaders(inputStream);
    }

    private static Map<String, String> parseHeaders(InputStream inputStream) {
        Map<String, String> headers = new HashMap<>();
        try {
            for (String line : readInputStream(inputStream, PAYLOAD_DELIMITER.getBytes(StandardCharsets.UTF_8)).split("\r\n")) {
                String[] headerParts = line.split(":", 2);
                if (headerParts.length == 2) {
                    headers.put(headerParts[0].trim(), headerParts[1].trim());
                }
            }
            return headers;
        } catch (Throwable e) {
            throw new RuntimeException("Exception while decoding headers ! ", e);
        }
    }

    public static ResponseStatus readStatusLine(InputStream inputStream) {
        return parseStatusLine(inputStream);
    }

    private static ResponseStatus parseStatusLine(InputStream inputStream) {
        try {
            String[] statusLineArray = readInputStream(inputStream, "\r\n".getBytes(StandardCharsets.UTF_8)).split("\\s");
            return ResponseStatus.builder().protocol(statusLineArray[0]).statusCode(Integer.parseInt(statusLineArray[1])).reason(joinSubArray(statusLineArray, 2)).build();
        } catch (Throwable e) {
            throw new RuntimeException("Exception while reading status line ! ", e);
        }
    }

    private static String joinSubArray(String[] array, int startIndex) {
        StringBuilder builder = new StringBuilder();
        for (int i = startIndex; i < array.length; i++) {
            builder.append(array[i]);
            if (i < array.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }

    private static String readInputStream(InputStream inputStream, byte[] delimiter) {
        int offset;
        byte[] buffer = new byte[4096];
        int offset2 = 0;
        while (true) {
            offset = offset2 + 1;
            if (inputStream.read(buffer, offset2, 1) > -1 && arrayIndexOf(buffer, 0, offset, delimiter) == -1) {
                offset2 = offset;
            }
        }
        return new String(buffer, 0, offset, StandardCharsets.UTF_8);
    }

    public static int arrayIndexOf(byte[] haystack, int tail, int head, byte[] needle) {
        int i;
        int index = tail;
        while (index != head) {
            boolean match = false;
            int j = 0;
            while (true) {
                i = 0;
                if (j >= needle.length) {
                    break;
                }
                match = haystack[(index + j) % haystack.length] == needle[j];
                if (!match) {
                    break;
                }
                j++;
            }
            if (match) {
                return index;
            }
            if (index != haystack.length - 1) {
                i = index + 1;
            }
            index = i;
        }
        return -1;
    }

    public static int parseChunkSize(byte[] buffer, int tail, int head) {
        byte[] tmp;
        if (tail < head) {
            tmp = new byte[(head - tail)];
            System.arraycopy(buffer, tail, tmp, 0, head - tail);
        } else {
            byte[] tmp2 = new byte[((buffer.length + head) - tail)];
            System.arraycopy(buffer, tail, tmp2, 0, buffer.length - tail);
            System.arraycopy(buffer, 0, tmp2, buffer.length - tail, head);
            tmp = tmp2;
        }
        return Integer.parseInt(new String(tmp, 0, tmp.length, StandardCharsets.UTF_8).trim(), 16);
    }

    public static Response parseStatusLineAndHeaders(InputStream inputStream) {
        return Response.builder().responseStatus(parseStatusLine(inputStream)).responseHeaders(parseHeaders(inputStream)).responsePayload(inputStream).build();
    }

    public static Response parseEntireTextResponse(InputStream inputStream) {
        return Response.builder().responseStatus(parseStatusLine(inputStream)).responseHeaders(parseHeaders(inputStream)).responseBody(parseTextBody(new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII)))).responsePayload(inputStream).build();
    }

    private static String parseTextBody(BufferedReader reader) {
        StringBuilder builder = new StringBuilder();
        String line = null;
        do {
            try {
                line = reader.readLine();
                builder.append(line);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        } while (line != null);
        return builder.toString();
    }

    public static Integer decodeAckInResponseBody(InputStream inputStream, Consumer<String> ackTimestampConsumer) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.US_ASCII));
        try {
            skipResponseHeaders(reader);
            String line = skipEmptyLines(reader);
            Log log = LOG;
            log.debug("Chunk size: " + line);
            while (true) {
                int chunkSize = Integer.parseInt(line.trim(), 16);
                if (chunkSize != 0) {
                    char[] buff = new char[16384];
                    int offset = 0;
                    do {
                        int numBytesRead = reader.read(buff, offset, (chunkSize + 2) - offset);
                        if (numBytesRead >= 0) {
                            offset += numBytesRead;
                        } else {
                            throw new RuntimeException("Unexpected end of stream while reading chunked data");
                        }
                    } while (offset < chunkSize + 2);
                    String chunk = new String(buff, 0, chunkSize);
                    Log log2 = LOG;
                    log2.debug("Chunk: " + chunk);
                    ackTimestampConsumer.accept(chunk);
                    line = reader.readLine();
                    log2.debug("Chunk size: " + line);
                    if (line == null) {
                        break;
                    }
                } else {
                    break;
                }
            }
            return 0;
        } catch (Throwable e) {
            throw new RuntimeException("Exception while decoding Ack in response ! ", e);
        }
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private static java.lang.String skipEmptyLines(java.io.BufferedReader r2) {
        /*
        L_0x0000:
            java.lang.String r0 = r2.readLine()
            if (r0 == 0) goto L_0x000c
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0000
        L_0x000c:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.kinesisvideo.encoding.ChunkDecoder.skipEmptyLines(java.io.BufferedReader):java.lang.String");
    }

    private static String skipResponseHeaders(BufferedReader reader) {
        String line = skipEmptyLines(reader);
        Log log = LOG;
        log.debug("Skip header: " + line);
        if (isStatusLine(line)) {
            do {
                line = reader.readLine();
                Log log2 = LOG;
                log2.debug("Skip header: " + line);
            } while (isNotBlank(line));
        }
        return line;
    }

    private static boolean isNotBlank(CharSequence cs) {
        int strLen = cs == null ? 0 : cs.length();
        if (cs == null || strLen == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean isStatusLine(String line) {
        return true;
    }
}
