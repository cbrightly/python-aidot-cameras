package org.glassfish.grizzly.http.server;

import java.util.Arrays;
import java.util.Set;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.EncodingFilter;
import org.glassfish.grizzly.http.HttpHeader;
import org.glassfish.grizzly.http.HttpRequestPacket;
import org.glassfish.grizzly.http.HttpResponsePacket;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;

public class CompressionEncodingFilter implements EncodingFilter {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final String[] aliases;
    private final CompressionConfig compressionConfig;

    public CompressionEncodingFilter(CompressionConfig compressionConfig2, String[] aliases2) {
        this.compressionConfig = new CompressionConfig(compressionConfig2);
        this.aliases = (String[]) Arrays.copyOf(aliases2, aliases2.length);
    }

    public CompressionEncodingFilter(CompressionConfig.CompressionModeI compressionMode, int compressionMinSize, String[] compressibleMimeTypes, String[] noCompressionUserAgents, String[] aliases2) {
        this(compressionMode, compressionMinSize, compressibleMimeTypes, noCompressionUserAgents, aliases2, false);
    }

    public CompressionEncodingFilter(CompressionConfig.CompressionModeI compressionMode, int compressionMinSize, String[] compressibleMimeTypes, String[] noCompressionUserAgents, String[] aliases2, boolean enableDecompression) {
        CompressionConfig.CompressionMode mode;
        if (compressionMode instanceof CompressionConfig.CompressionMode) {
            mode = (CompressionConfig.CompressionMode) compressionMode;
        } else if (compressionMode instanceof CompressionLevel) {
            mode = ((CompressionLevel) compressionMode).normalize();
        } else {
            throw new AssertionError();
        }
        CompressionConfig compressionConfig2 = new CompressionConfig(mode, compressionMinSize, (Set<String>) null, (Set<String>) null, enableDecompression);
        this.compressionConfig = compressionConfig2;
        compressionConfig2.setCompressibleMimeTypes(compressibleMimeTypes);
        compressionConfig2.setNoCompressionUserAgents(noCompressionUserAgents);
        this.aliases = (String[]) Arrays.copyOf(aliases2, aliases2.length);
    }

    public boolean applyEncoding(HttpHeader httpPacket) {
        if (httpPacket.isRequest()) {
            if (httpPacket instanceof HttpRequestPacket) {
                return false;
            }
            throw new AssertionError();
        } else if (httpPacket instanceof HttpResponsePacket) {
            return canCompressHttpResponse((HttpResponsePacket) httpPacket, this.compressionConfig, this.aliases);
        } else {
            throw new AssertionError();
        }
    }

    public boolean applyDecoding(HttpHeader httpPacket) {
        if (!httpPacket.isRequest()) {
            return false;
        }
        if (httpPacket instanceof HttpRequestPacket) {
            return canDecompressHttpRequest((HttpRequestPacket) httpPacket, this.compressionConfig, this.aliases);
        }
        throw new AssertionError();
    }

    protected static boolean canCompressHttpResponse(HttpResponsePacket response, CompressionConfig compressionConfig2, String[] aliases2) {
        if (!response.getContentEncodings().isEmpty()) {
            return false;
        }
        DataChunk contentEncodingMB = response.getHeaders().getValue(Header.ContentEncoding);
        if ((contentEncodingMB != null && !contentEncodingMB.isNull()) || !CompressionConfig.isClientSupportCompression(compressionConfig2, response.getRequest(), aliases2)) {
            return false;
        }
        if (compressionConfig2.getCompressionMode() == CompressionConfig.CompressionMode.FORCE) {
            response.setChunked(true);
            response.setContentLength(-1);
            return true;
        }
        long contentLength = response.getContentLength();
        if ((contentLength != -1 && contentLength < ((long) compressionConfig2.getCompressionMinSize())) || !compressionConfig2.checkMimeType(response.getContentType())) {
            return false;
        }
        response.setChunked(true);
        response.setContentLength(-1);
        return true;
    }

    protected static boolean canDecompressHttpRequest(HttpRequestPacket request, CompressionConfig config, String[] aliases2) {
        String contentEncoding;
        if (!config.isDecompressionEnabled() || (contentEncoding = request.getHeader(Header.ContentEncoding)) == null) {
            return false;
        }
        String contentEncoding2 = contentEncoding.trim();
        for (String alias : aliases2) {
            if (alias.equals(contentEncoding2)) {
                return true;
            }
        }
        return false;
    }
}
