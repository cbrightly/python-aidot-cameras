package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.compression.zip.GZipDecoder;
import org.glassfish.grizzly.compression.zip.GZipEncoder;
import org.glassfish.grizzly.memory.Buffers;

public class GZipContentEncoding implements ContentEncoding {
    private static final String[] ALIASES = {NAME, "deflate"};
    public static final int DEFAULT_IN_BUFFER_SIZE = 512;
    public static final int DEFAULT_OUT_BUFFER_SIZE = 512;
    public static final String NAME = "gzip";
    private final GZipDecoder decoder;
    private final GZipEncoder encoder;
    private final EncodingFilter encoderFilter;

    public GZipContentEncoding() {
        this(512, 512);
    }

    public GZipContentEncoding(int inBufferSize, int outBufferSize) {
        this(inBufferSize, outBufferSize, (EncodingFilter) null);
    }

    public GZipContentEncoding(int inBufferSize, int outBufferSize, EncodingFilter encoderFilter2) {
        this.decoder = new GZipDecoder(inBufferSize);
        this.encoder = new GZipEncoder(outBufferSize);
        if (encoderFilter2 != null) {
            this.encoderFilter = encoderFilter2;
        } else {
            this.encoderFilter = new EncodingFilter() {
                public boolean applyEncoding(HttpHeader httpPacket) {
                    return false;
                }

                public boolean applyDecoding(HttpHeader httpPacket) {
                    return true;
                }
            };
        }
    }

    public String getName() {
        return NAME;
    }

    public String[] getAliases() {
        return (String[]) ALIASES.clone();
    }

    public static String[] getGzipAliases() {
        return (String[]) ALIASES.clone();
    }

    public final boolean wantDecode(HttpHeader header) {
        return this.encoderFilter.applyDecoding(header);
    }

    public final boolean wantEncode(HttpHeader header) {
        return this.encoderFilter.applyEncoding(header);
    }

    public ParsingResult decode(Connection connection, HttpContent httpContent) {
        HttpHeader httpHeader = httpContent.getHttpHeader();
        Buffer input = httpContent.getContent();
        TransformationResult<Buffer, Buffer> result = this.decoder.transform(httpHeader, input);
        Buffer remainder = result.getExternalRemainder();
        if (remainder == null || !remainder.hasRemaining()) {
            input.tryDispose();
            remainder = null;
        } else {
            input.shrink();
        }
        try {
            switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                    httpContent.setContent(result.getMessage());
                    ParsingResult create = ParsingResult.create(httpContent, remainder);
                    result.recycle();
                    return create;
                case 2:
                    return ParsingResult.create((HttpContent) null, remainder);
                case 3:
                    throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }

    /* renamed from: org.glassfish.grizzly.http.GZipContentEncoding$2  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$TransformationResult$Status;

        static {
            int[] iArr = new int[TransformationResult.Status.values().length];
            $SwitchMap$org$glassfish$grizzly$TransformationResult$Status = iArr;
            try {
                iArr[TransformationResult.Status.COMPLETE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$TransformationResult$Status[TransformationResult.Status.INCOMPLETE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$glassfish$grizzly$TransformationResult$Status[TransformationResult.Status.ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public HttpContent encode(Connection connection, HttpContent httpContent) {
        HttpHeader httpHeader = httpContent.getHttpHeader();
        Buffer input = httpContent.getContent();
        boolean isLast = httpContent.isLast();
        if (!isLast && !input.hasRemaining()) {
            return httpContent;
        }
        TransformationResult<Buffer, Buffer> result = this.encoder.transform(httpHeader, input);
        input.tryDispose();
        try {
            switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                case 2:
                    Buffer encodedBuffer = result.getMessage();
                    if (isLast) {
                        encodedBuffer = Buffers.appendBuffers(connection.getMemoryManager(), encodedBuffer, this.encoder.finish(httpHeader));
                    }
                    if (encodedBuffer != null) {
                        httpContent.setContent(encodedBuffer);
                        return httpContent;
                    }
                    result.recycle();
                    return null;
                case 3:
                    throw new IllegalStateException("GZip decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }

    public boolean equals(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            return getName().equals(((GZipContentEncoding) obj).getName());
        }
        return false;
    }

    public int hashCode() {
        return (3 * 53) + getName().hashCode();
    }
}
