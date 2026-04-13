package org.glassfish.grizzly.http;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.glassfish.grizzly.http.util.DataChunk;
import org.glassfish.grizzly.http.util.Header;
import org.glassfish.grizzly.utils.ArraySet;

public final class CompressionConfig {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final ArraySet<String> compressibleMimeTypes;
    private int compressionMinSize;
    private CompressionMode compressionMode;
    private boolean decompressionEnabled;
    private final ArraySet<String> noCompressionUserAgents;

    public interface CompressionModeI {
    }

    public enum CompressionMode implements CompressionModeI {
        OFF,
        ON,
        FORCE;

        public static CompressionMode fromString(String mode) {
            if ("on".equalsIgnoreCase(mode)) {
                return ON;
            }
            if ("force".equalsIgnoreCase(mode)) {
                return FORCE;
            }
            if ("off".equalsIgnoreCase(mode)) {
                return OFF;
            }
            throw new IllegalArgumentException("Compression mode is not recognized. Supported modes: " + Arrays.toString(values()));
        }
    }

    public CompressionConfig() {
        Class<String> cls = String.class;
        this.compressibleMimeTypes = new ArraySet<>(cls);
        this.noCompressionUserAgents = new ArraySet<>(cls);
        this.compressionMode = CompressionMode.OFF;
    }

    public CompressionConfig(CompressionConfig compression) {
        Class<String> cls = String.class;
        this.compressibleMimeTypes = new ArraySet<>(cls);
        this.noCompressionUserAgents = new ArraySet<>(cls);
        set(compression);
    }

    public CompressionConfig(CompressionMode compressionMode2, int compressionMinSize2, Set<String> compressibleMimeTypes2, Set<String> noCompressionUserAgents2) {
        this(compressionMode2, compressionMinSize2, compressibleMimeTypes2, noCompressionUserAgents2, false);
    }

    public CompressionConfig(CompressionMode compressionMode2, int compressionMinSize2, Set<String> compressibleMimeTypes2, Set<String> noCompressionUserAgents2, boolean decompressionEnabled2) {
        Class<String> cls = String.class;
        this.compressibleMimeTypes = new ArraySet<>(cls);
        this.noCompressionUserAgents = new ArraySet<>(cls);
        setCompressionMode(compressionMode2);
        setCompressionMinSize(compressionMinSize2);
        setCompressibleMimeTypes(compressibleMimeTypes2);
        setNoCompressionUserAgents(noCompressionUserAgents2);
        setDecompressionEnabled(decompressionEnabled2);
    }

    public void set(CompressionConfig compression) {
        this.compressionMode = compression.compressionMode;
        this.compressionMinSize = compression.compressionMinSize;
        setCompressibleMimeTypes((Set<String>) compression.compressibleMimeTypes);
        setNoCompressionUserAgents((Set<String>) compression.noCompressionUserAgents);
        this.decompressionEnabled = compression.isDecompressionEnabled();
    }

    public CompressionMode getCompressionMode() {
        return this.compressionMode;
    }

    public void setCompressionMode(CompressionMode mode) {
        this.compressionMode = mode != null ? mode : CompressionMode.OFF;
    }

    public int getCompressionMinSize() {
        return this.compressionMinSize;
    }

    public void setCompressionMinSize(int compressionMinSize2) {
        this.compressionMinSize = compressionMinSize2;
    }

    @Deprecated
    public Set<String> getCompressableMimeTypes() {
        return getCompressibleMimeTypes();
    }

    @Deprecated
    public void setCompressableMimeTypes(Set<String> compressibleMimeTypes2) {
        setCompressibleMimeTypes(compressibleMimeTypes2);
    }

    @Deprecated
    public void setCompressableMimeTypes(String... compressibleMimeTypes2) {
        setCompressibleMimeTypes(compressibleMimeTypes2);
    }

    public Set<String> getCompressibleMimeTypes() {
        return Collections.unmodifiableSet(this.compressibleMimeTypes);
    }

    public void setCompressibleMimeTypes(Set<String> compressibleMimeTypes2) {
        this.compressibleMimeTypes.clear();
        if (compressibleMimeTypes2 != null && !compressibleMimeTypes2.isEmpty()) {
            this.compressibleMimeTypes.addAll(compressibleMimeTypes2);
        }
    }

    public void setCompressibleMimeTypes(String... compressibleMimeTypes2) {
        this.compressibleMimeTypes.clear();
        if (compressibleMimeTypes2.length > 0) {
            this.compressibleMimeTypes.addAll((T[]) compressibleMimeTypes2);
        }
    }

    public Set<String> getNoCompressionUserAgents() {
        return Collections.unmodifiableSet(this.noCompressionUserAgents);
    }

    public void setNoCompressionUserAgents(Set<String> noCompressionUserAgents2) {
        this.noCompressionUserAgents.clear();
        if (noCompressionUserAgents2 != null && !noCompressionUserAgents2.isEmpty()) {
            this.noCompressionUserAgents.addAll(noCompressionUserAgents2);
        }
    }

    public void setNoCompressionUserAgents(String... noCompressionUserAgents2) {
        this.noCompressionUserAgents.clear();
        if (noCompressionUserAgents2.length > 0) {
            this.noCompressionUserAgents.addAll((T[]) noCompressionUserAgents2);
        }
    }

    public boolean isDecompressionEnabled() {
        return this.decompressionEnabled;
    }

    public void setDecompressionEnabled(boolean decompressionEnabled2) {
        this.decompressionEnabled = decompressionEnabled2;
    }

    public static boolean isClientSupportCompression(CompressionConfig compressionConfig, HttpRequestPacket request, String[] aliases) {
        CompressionMode mode = compressionConfig.getCompressionMode();
        switch (AnonymousClass1.$SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode[mode.ordinal()]) {
            case 1:
                return false;
            default:
                if (Protocol.HTTP_1_1 != request.getProtocol() || !isClientSupportContentEncoding(request, aliases)) {
                    return false;
                }
                if (mode == CompressionMode.FORCE || compressionConfig.checkUserAgent(request)) {
                    return true;
                }
                return false;
        }
    }

    /* renamed from: org.glassfish.grizzly.http.CompressionConfig$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode;

        static {
            int[] iArr = new int[CompressionMode.values().length];
            $SwitchMap$org$glassfish$grizzly$http$CompressionConfig$CompressionMode = iArr;
            try {
                iArr[CompressionMode.OFF.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public boolean checkUserAgent(HttpRequestPacket request) {
        DataChunk userAgentValueDC;
        if (this.noCompressionUserAgents.isEmpty() || (userAgentValueDC = request.getHeaders().getValue(Header.UserAgent)) == null || indexOf((String[]) this.noCompressionUserAgents.getArray(), userAgentValueDC) == -1) {
            return true;
        }
        return false;
    }

    public boolean checkMimeType(String contentType) {
        return this.compressibleMimeTypes.isEmpty() || indexOfStartsWith((String[]) this.compressibleMimeTypes.getArray(), contentType) != -1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:6:0x0015, code lost:
        r2 = r10[r4];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean isClientSupportContentEncoding(org.glassfish.grizzly.http.HttpRequestPacket r9, java.lang.String[] r10) {
        /*
            org.glassfish.grizzly.http.util.MimeHeaders r0 = r9.getHeaders()
            org.glassfish.grizzly.http.util.Header r1 = org.glassfish.grizzly.http.util.Header.AcceptEncoding
            org.glassfish.grizzly.http.util.DataChunk r0 = r0.getValue((org.glassfish.grizzly.http.util.Header) r1)
            r1 = 0
            if (r0 != 0) goto L_0x000e
            return r1
        L_0x000e:
            r2 = 0
            r3 = -1
            r4 = 0
            int r5 = r10.length
        L_0x0012:
            r6 = -1
            if (r4 >= r5) goto L_0x0021
            r2 = r10[r4]
            int r3 = r0.indexOf((java.lang.String) r2, (int) r1)
            if (r3 == r6) goto L_0x001e
            goto L_0x0021
        L_0x001e:
            int r4 = r4 + 1
            goto L_0x0012
        L_0x0021:
            if (r3 != r6) goto L_0x0024
            return r1
        L_0x0024:
            if (r2 == 0) goto L_0x0055
            r4 = 59
            int r5 = r2.length()
            int r5 = r5 + r3
            int r4 = r0.indexOf((char) r4, (int) r5)
            if (r4 == r6) goto L_0x0053
            r5 = 61
            int r4 = r0.indexOf((char) r5, (int) r4)
            r5 = 44
            int r5 = r0.indexOf((char) r5, (int) r4)
            if (r5 == r6) goto L_0x0043
            r6 = r5
            goto L_0x0047
        L_0x0043:
            int r6 = r0.getLength()
        L_0x0047:
            int r7 = r4 + 1
            float r7 = org.glassfish.grizzly.http.util.HttpUtils.convertQValueToFloat((org.glassfish.grizzly.http.util.DataChunk) r0, (int) r7, (int) r6)
            r8 = 0
            int r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1))
            if (r7 != 0) goto L_0x0053
            return r1
        L_0x0053:
            r1 = 1
            return r1
        L_0x0055:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.grizzly.http.CompressionConfig.isClientSupportContentEncoding(org.glassfish.grizzly.http.HttpRequestPacket, java.lang.String[]):boolean");
    }

    private static int indexOf(String[] aliases, DataChunk dc) {
        if (dc == null || dc.isNull()) {
            return -1;
        }
        for (int i = 0; i < aliases.length; i++) {
            if (dc.indexOf(aliases[i], 0) != -1) {
                return i;
            }
        }
        return -1;
    }

    private static int indexOfStartsWith(String[] aliases, String s) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        for (int i = 0; i < aliases.length; i++) {
            if (s.startsWith(aliases[i])) {
                return i;
            }
        }
        return -1;
    }
}
