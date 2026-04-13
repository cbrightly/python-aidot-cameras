package org.glassfish.grizzly.http;

import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.TransformationResult;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.compression.lzma.LZMAEncoder;

public class LZMAContentEncoding implements ContentEncoding {
    private static final String[] ALIASES = {NAME};
    public static final String NAME = "lzma";
    private final LZMADecoder decoder;
    private final LZMAEncoder encoder;
    private final EncodingFilter encodingFilter;

    public LZMAContentEncoding() {
        this((EncodingFilter) null);
    }

    public LZMAContentEncoding(EncodingFilter encodingFilter2) {
        this.decoder = new LZMADecoder();
        this.encoder = new LZMAEncoder();
        if (encodingFilter2 != null) {
            this.encodingFilter = encodingFilter2;
        } else {
            this.encodingFilter = new EncodingFilter() {
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

    public static String[] getLzmaAliases() {
        return (String[]) ALIASES.clone();
    }

    public boolean wantDecode(HttpHeader header) {
        return this.encodingFilter.applyDecoding(header);
    }

    public boolean wantEncode(HttpHeader header) {
        return this.encodingFilter.applyEncoding(header);
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
                    this.decoder.finish(httpHeader);
                    ParsingResult create = ParsingResult.create(httpContent, remainder);
                    result.recycle();
                    return create;
                case 2:
                    return ParsingResult.create((HttpContent) null, remainder);
                case 3:
                    throw new IllegalStateException("LZMA decode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
        } finally {
            result.recycle();
        }
        result.recycle();
    }

    /* renamed from: org.glassfish.grizzly.http.LZMAContentEncoding$2  reason: invalid class name */
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
        if (httpContent.isLast() && !input.hasRemaining()) {
            return httpContent;
        }
        TransformationResult<Buffer, Buffer> result = this.encoder.transform(httpContent.getHttpHeader(), input);
        input.tryDispose();
        try {
            switch (AnonymousClass2.$SwitchMap$org$glassfish$grizzly$TransformationResult$Status[result.getStatus().ordinal()]) {
                case 1:
                    this.encoder.finish(httpHeader);
                    break;
                case 2:
                    break;
                case 3:
                    throw new IllegalStateException("LZMA encode error. Code: " + result.getErrorCode() + " Description: " + result.getErrorDescription());
                default:
                    throw new IllegalStateException("Unexpected status: " + result.getStatus());
            }
            Buffer encodedBuffer = result.getMessage();
            if (encodedBuffer != null) {
                httpContent.setContent(encodedBuffer);
                return httpContent;
            }
            result.recycle();
            return null;
        } finally {
            result.recycle();
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LZMAContentEncoding that = (LZMAContentEncoding) o;
        LZMADecoder lZMADecoder = this.decoder;
        if (lZMADecoder == null ? that.decoder != null : !lZMADecoder.equals(that.decoder)) {
            return false;
        }
        LZMAEncoder lZMAEncoder = this.encoder;
        if (lZMAEncoder == null ? that.encoder != null : !lZMAEncoder.equals(that.encoder)) {
            return false;
        }
        EncodingFilter encodingFilter2 = this.encodingFilter;
        if (encodingFilter2 == null ? that.encodingFilter == null : encodingFilter2.equals(that.encodingFilter)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        LZMADecoder lZMADecoder = this.decoder;
        int i = 0;
        int hashCode = (lZMADecoder != null ? lZMADecoder.hashCode() : 0) * 31;
        LZMAEncoder lZMAEncoder = this.encoder;
        int result = (hashCode + (lZMAEncoder != null ? lZMAEncoder.hashCode() : 0)) * 31;
        EncodingFilter encodingFilter2 = this.encodingFilter;
        if (encodingFilter2 != null) {
            i = encodingFilter2.hashCode();
        }
        return result + i;
    }
}
