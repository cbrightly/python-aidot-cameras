package org.glassfish.tyrus.core.uri.internal;

import com.meituan.robust.Constants;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.slf4j.e;

public class UriComponent {
    private static final boolean[][] ENCODING_TABLES = initEncodingTables();
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int[] HEX_TABLE = initHexTable();
    private static final String[] SCHEME = {"0-9", "A-Z", "a-z", e.ANY_NON_NULL_MARKER, "-", "."};
    private static final String[] SUB_DELIMS = {"!", "$", "&", "'", "(", ")", e.ANY_MARKER, e.ANY_NON_NULL_MARKER, ",", Constants.PACKNAME_END, "="};
    private static final String[] UNRESERVED = {"0-9", "A-Z", "a-z", "-", ".", "_", "~"};
    private static final Charset UTF_8_CHARSET = Charset.forName("UTF-8");

    public enum Type {
        UNRESERVED,
        SCHEME,
        AUTHORITY,
        USER_INFO,
        HOST,
        PORT,
        PATH,
        PATH_SEGMENT,
        MATRIX_PARAM,
        QUERY,
        QUERY_PARAM,
        QUERY_PARAM_SPACE_ENCODED,
        FRAGMENT
    }

    private UriComponent() {
    }

    public static void validate(String s, Type t) {
        validate(s, t, false);
    }

    public static void validate(String s, Type t, boolean template) {
        int i = _valid(s, t, template);
        if (i > -1) {
            throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_INVALID_CHARACTER(s, t, Character.valueOf(s.charAt(i)), Integer.valueOf(i)));
        }
    }

    public static boolean valid(String s, Type t) {
        return valid(s, t, false);
    }

    public static boolean valid(String s, Type t, boolean template) {
        return _valid(s, t, template) == -1;
    }

    private static int _valid(String s, Type t, boolean template) {
        boolean[] table = ENCODING_TABLES[t.ordinal()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (((c < 128 && c != '%' && !table[c]) || c >= 128) && (!template || (c != '{' && c != '}'))) {
                return i;
            }
        }
        return -1;
    }

    public static String contextualEncode(String s, Type t) {
        return _encode(s, t, false, true);
    }

    public static String contextualEncode(String s, Type t, boolean template) {
        return _encode(s, t, template, true);
    }

    public static String encode(String s, Type t) {
        return _encode(s, t, false, false);
    }

    public static String encode(String s, Type t, boolean template) {
        return _encode(s, t, template, false);
    }

    public static String encodeTemplateNames(String s) {
        if (s.indexOf(123) != -1) {
            s = s.replace("{", "%7B");
        }
        if (s.indexOf(125) != -1) {
            return s.replace("}", "%7D");
        }
        return s;
    }

    private static String _encode(String s, Type t, boolean template, boolean contextualEncode) {
        boolean[] table = ENCODING_TABLES[t.ordinal()];
        boolean insideTemplateParam = false;
        StringBuilder sb = null;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c >= 128 || !table[c]) {
                if (template) {
                    boolean leavingTemplateParam = false;
                    if (c == '{') {
                        insideTemplateParam = true;
                    } else if (c == '}') {
                        insideTemplateParam = false;
                        leavingTemplateParam = true;
                    }
                    if (insideTemplateParam || leavingTemplateParam) {
                        if (sb != null) {
                            sb.append(c);
                        }
                    }
                }
                if (!contextualEncode || c != '%' || i + 2 >= s.length() || !isHexCharacter(s.charAt(i + 1)) || !isHexCharacter(s.charAt(i + 2))) {
                    if (sb == null) {
                        sb = new StringBuilder();
                        sb.append(s.substring(0, i));
                    }
                    if (c >= 128) {
                        appendUTF8EncodedCharacter(sb, c);
                    } else if (c == ' ' && t == Type.QUERY_PARAM) {
                        sb.append('+');
                    } else {
                        appendPercentEncodedOctet(sb, c);
                    }
                } else {
                    if (sb != null) {
                        sb.append('%');
                        sb.append(s.charAt(i + 1));
                        sb.append(s.charAt(i + 2));
                    }
                    i += 2;
                }
            } else if (sb != null) {
                sb.append(c);
            }
            i++;
        }
        return sb == null ? s : sb.toString();
    }

    private static void appendPercentEncodedOctet(StringBuilder sb, int b) {
        sb.append('%');
        char[] cArr = HEX_DIGITS;
        sb.append(cArr[b >> 4]);
        sb.append(cArr[b & 15]);
    }

    private static void appendUTF8EncodedCharacter(StringBuilder sb, char c) {
        Charset charset = UTF_8_CHARSET;
        ByteBuffer bb = charset.encode("" + c);
        while (bb.hasRemaining()) {
            appendPercentEncodedOctet(sb, bb.get() & 255);
        }
    }

    private static boolean[][] initEncodingTables() {
        boolean[][] tables = new boolean[Type.values().length][];
        List<String> l = new ArrayList<>();
        l.addAll(Arrays.asList(SCHEME));
        tables[Type.SCHEME.ordinal()] = initEncodingTable(l);
        l.clear();
        l.addAll(Arrays.asList(UNRESERVED));
        tables[Type.UNRESERVED.ordinal()] = initEncodingTable(l);
        l.addAll(Arrays.asList(SUB_DELIMS));
        tables[Type.HOST.ordinal()] = initEncodingTable(l);
        tables[Type.PORT.ordinal()] = initEncodingTable(Arrays.asList(new String[]{"0-9"}));
        l.add(":");
        tables[Type.USER_INFO.ordinal()] = initEncodingTable(l);
        l.add("@");
        tables[Type.AUTHORITY.ordinal()] = initEncodingTable(l);
        Type type = Type.PATH_SEGMENT;
        tables[type.ordinal()] = initEncodingTable(l);
        tables[type.ordinal()][59] = false;
        Type type2 = Type.MATRIX_PARAM;
        tables[type2.ordinal()] = (boolean[]) tables[type.ordinal()].clone();
        tables[type2.ordinal()][61] = false;
        l.add("/");
        tables[Type.PATH.ordinal()] = initEncodingTable(l);
        l.add("?");
        Type type3 = Type.QUERY;
        tables[type3.ordinal()] = initEncodingTable(l);
        Type type4 = Type.QUERY_PARAM;
        tables[type4.ordinal()] = initEncodingTable(l);
        tables[type4.ordinal()][61] = false;
        tables[type4.ordinal()][43] = false;
        tables[type4.ordinal()][38] = false;
        tables[Type.QUERY_PARAM_SPACE_ENCODED.ordinal()] = tables[type4.ordinal()];
        tables[Type.FRAGMENT.ordinal()] = tables[type3.ordinal()];
        return tables;
    }

    private static boolean[] initEncodingTable(List<String> allowed) {
        boolean[] table = new boolean[128];
        for (String range : allowed) {
            if (range.length() == 1) {
                table[range.charAt(0)] = true;
            } else if (range.length() == 3 && range.charAt(1) == '-') {
                for (int i = range.charAt(0); i <= range.charAt(2); i++) {
                    table[i] = true;
                }
            }
        }
        return table;
    }

    public static String decode(String s, Type t) {
        if (s != null) {
            int n = s.length();
            if (n == 0) {
                return s;
            }
            if (s.indexOf(37) < 0) {
                if (t != Type.QUERY_PARAM || s.indexOf(43) < 0) {
                    return s;
                }
            } else if (n < 2) {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(1));
            } else if (s.charAt(n - 2) == '%') {
                throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_MALFORMED(Integer.valueOf(n - 2)));
            }
            if (t == null) {
                return decode(s, n);
            }
            switch (AnonymousClass1.$SwitchMap$org$glassfish$tyrus$core$uri$internal$UriComponent$Type[t.ordinal()]) {
                case 1:
                    return decodeHost(s, n);
                case 2:
                    return decodeQueryParam(s, n);
                default:
                    return decode(s, n);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /* renamed from: org.glassfish.tyrus.core.uri.internal.UriComponent$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$glassfish$tyrus$core$uri$internal$UriComponent$Type;

        static {
            int[] iArr = new int[Type.values().length];
            $SwitchMap$org$glassfish$tyrus$core$uri$internal$UriComponent$Type = iArr;
            try {
                iArr[Type.HOST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$glassfish$tyrus$core$uri$internal$UriComponent$Type[Type.QUERY_PARAM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public static MultivaluedMap<String, String> decodeQuery(URI u, boolean decode) {
        return decodeQuery(u.getRawQuery(), decode);
    }

    public static MultivaluedMap<String, String> decodeQuery(String q, boolean decode) {
        return decodeQuery(q, true, decode);
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.glassfish.tyrus.core.uri.internal.MultivaluedMap<java.lang.String, java.lang.String> decodeQuery(java.lang.String r4, boolean r5, boolean r6) {
        /*
            org.glassfish.tyrus.core.uri.internal.MultivaluedStringMap r0 = new org.glassfish.tyrus.core.uri.internal.MultivaluedStringMap
            r0.<init>()
            if (r4 == 0) goto L_0x0034
            int r1 = r4.length()
            if (r1 != 0) goto L_0x000e
            goto L_0x0034
        L_0x000e:
            r1 = 0
        L_0x000f:
            r2 = 38
            int r2 = r4.indexOf(r2, r1)
            r3 = -1
            if (r2 != r3) goto L_0x0020
            java.lang.String r3 = r4.substring(r1)
            decodeQueryParam(r0, r3, r5, r6)
            goto L_0x0029
        L_0x0020:
            if (r2 <= r1) goto L_0x0029
            java.lang.String r3 = r4.substring(r1, r2)
            decodeQueryParam(r0, r3, r5, r6)
        L_0x0029:
            int r1 = r2 + 1
            if (r1 <= 0) goto L_0x0033
            int r2 = r4.length()
            if (r1 < r2) goto L_0x000f
        L_0x0033:
            return r0
        L_0x0034:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.uri.internal.UriComponent.decodeQuery(java.lang.String, boolean, boolean):org.glassfish.tyrus.core.uri.internal.MultivaluedMap");
    }

    private static void decodeQueryParam(MultivaluedMap<String, String> params, String param, boolean decodeNames, boolean decodeValues) {
        String str;
        String str2;
        try {
            int equals = param.indexOf(61);
            if (equals > 0) {
                if (decodeNames) {
                    str = URLDecoder.decode(param.substring(0, equals), "UTF-8");
                } else {
                    str = param.substring(0, equals);
                }
                if (decodeValues) {
                    str2 = URLDecoder.decode(param.substring(equals + 1), "UTF-8");
                } else {
                    str2 = param.substring(equals + 1);
                }
                params.add(str, str2);
            } else if (equals != 0) {
                if (param.length() > 0) {
                    params.add(URLDecoder.decode(param, "UTF-8"), "");
                }
            }
        } catch (UnsupportedEncodingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public static final class PathSegmentImpl implements PathSegment {
        /* access modifiers changed from: private */
        public static final PathSegment EMPTY_PATH_SEGMENT = new PathSegmentImpl("", false);
        private final MultivaluedMap<String, String> matrixParameters;
        private final String path;

        PathSegmentImpl(String path2, boolean decode) {
            this(path2, decode, new MultivaluedStringMap());
        }

        PathSegmentImpl(String path2, boolean decode, MultivaluedMap<String, String> matrixParameters2) {
            this.path = decode ? UriComponent.decode(path2, Type.PATH_SEGMENT) : path2;
            this.matrixParameters = matrixParameters2;
        }

        public String getPath() {
            return this.path;
        }

        public MultivaluedMap<String, String> getMatrixParameters() {
            return this.matrixParameters;
        }

        public String toString() {
            return this.path;
        }
    }

    public static List<PathSegment> decodePath(URI u, boolean decode) {
        String rawPath = u.getRawPath();
        if (rawPath != null && rawPath.length() > 0 && rawPath.charAt(0) == '/') {
            rawPath = rawPath.substring(1);
        }
        return decodePath(rawPath, decode);
    }

    public static List<PathSegment> decodePath(String path, boolean decode) {
        int s;
        List<PathSegment> segments = new LinkedList<>();
        if (path == null) {
            return segments;
        }
        int e = -1;
        do {
            s = e + 1;
            e = path.indexOf(47, s);
            if (e > s) {
                decodePathSegment(segments, path.substring(s, e), decode);
            } else if (e == s) {
                segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
            }
        } while (e != -1);
        if (s < path.length()) {
            decodePathSegment(segments, path.substring(s), decode);
        } else {
            segments.add(PathSegmentImpl.EMPTY_PATH_SEGMENT);
        }
        return segments;
    }

    public static void decodePathSegment(List<PathSegment> segments, String segment, boolean decode) {
        String str;
        int colon = segment.indexOf(59);
        if (colon != -1) {
            if (colon == 0) {
                str = "";
            } else {
                str = segment.substring(0, colon);
            }
            segments.add(new PathSegmentImpl(str, decode, decodeMatrix(segment, decode)));
            return;
        }
        segments.add(new PathSegmentImpl(segment, decode));
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x001d  */
    /* JADX WARNING: Removed duplicated region for block: B:7:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static org.glassfish.tyrus.core.uri.internal.MultivaluedMap<java.lang.String, java.lang.String> decodeMatrix(java.lang.String r5, boolean r6) {
        /*
            org.glassfish.tyrus.core.uri.internal.MultivaluedStringMap r0 = new org.glassfish.tyrus.core.uri.internal.MultivaluedStringMap
            r0.<init>()
            r1 = 59
            int r2 = r5.indexOf(r1)
            int r2 = r2 + 1
            if (r2 == 0) goto L_0x0039
            int r3 = r5.length()
            if (r2 != r3) goto L_0x0016
            goto L_0x0039
        L_0x0016:
            int r3 = r5.indexOf(r1, r2)
            r4 = -1
            if (r3 != r4) goto L_0x0025
            java.lang.String r4 = r5.substring(r2)
            decodeMatrixParam(r0, r4, r6)
            goto L_0x002e
        L_0x0025:
            if (r3 <= r2) goto L_0x002e
            java.lang.String r4 = r5.substring(r2, r3)
            decodeMatrixParam(r0, r4, r6)
        L_0x002e:
            int r2 = r3 + 1
            if (r2 <= 0) goto L_0x0038
            int r3 = r5.length()
            if (r2 < r3) goto L_0x0016
        L_0x0038:
            return r0
        L_0x0039:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.glassfish.tyrus.core.uri.internal.UriComponent.decodeMatrix(java.lang.String, boolean):org.glassfish.tyrus.core.uri.internal.MultivaluedMap");
    }

    private static void decodeMatrixParam(MultivaluedMap<String, String> params, String param, boolean decode) {
        String str;
        int equals = param.indexOf(61);
        if (equals > 0) {
            String substring = param.substring(0, equals);
            Type type = Type.MATRIX_PARAM;
            String decode2 = decode(substring, type);
            if (decode) {
                str = decode(param.substring(equals + 1), type);
            } else {
                str = param.substring(equals + 1);
            }
            params.add(decode2, str);
        } else if (equals != 0 && param.length() > 0) {
            params.add(decode(param, Type.MATRIX_PARAM), "");
        }
    }

    private static String decode(String s, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        int i = 0;
        while (i < n) {
            int i2 = i + 1;
            char c = s.charAt(i);
            if (c != '%') {
                sb.append(c);
                i = i2;
            } else {
                bb = decodePercentEncodedOctets(s, i2, bb);
                i = decodeOctets(i2, bb, sb);
            }
        }
        return sb.toString();
    }

    private static String decodeQueryParam(String s, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        int i = 0;
        while (i < n) {
            int i2 = i + 1;
            char c = s.charAt(i);
            if (c != '%') {
                if (c != '+') {
                    sb.append(c);
                } else {
                    sb.append(' ');
                }
                i = i2;
            } else {
                bb = decodePercentEncodedOctets(s, i2, bb);
                i = decodeOctets(i2, bb, sb);
            }
        }
        return sb.toString();
    }

    private static String decodeHost(String s, int n) {
        StringBuilder sb = new StringBuilder(n);
        ByteBuffer bb = null;
        boolean betweenBrackets = false;
        int i = 0;
        while (i < n) {
            int i2 = i + 1;
            char c = s.charAt(i);
            if (c == '[') {
                betweenBrackets = true;
            } else if (betweenBrackets && c == ']') {
                betweenBrackets = false;
            }
            if (c != '%' || betweenBrackets) {
                sb.append(c);
                i = i2;
            } else {
                bb = decodePercentEncodedOctets(s, i2, bb);
                i = decodeOctets(i2, bb, sb);
            }
        }
        return sb.toString();
    }

    private static ByteBuffer decodePercentEncodedOctets(String s, int i, ByteBuffer bb) {
        if (bb == null) {
            bb = ByteBuffer.allocate(1);
        } else {
            bb.clear();
        }
        while (true) {
            int i2 = i + 1;
            int i3 = i2 + 1;
            bb.put((byte) ((decodeHex(s, i) << 4) | decodeHex(s, i2)));
            if (i3 == s.length()) {
                break;
            }
            i = i3 + 1;
            if (s.charAt(i3) != '%') {
                int i4 = i;
                break;
            } else if (bb.position() == bb.capacity()) {
                bb.flip();
                ByteBuffer bb_new = ByteBuffer.allocate(s.length() / 3);
                bb_new.put(bb);
                bb = bb_new;
            }
        }
        bb.flip();
        return bb;
    }

    private static int decodeOctets(int i, ByteBuffer bb, StringBuilder sb) {
        if (bb.limit() != 1 || (bb.get(0) & 255) >= 128) {
            sb.append(UTF_8_CHARSET.decode(bb).toString());
            return ((bb.limit() * 3) + i) - 1;
        }
        sb.append((char) bb.get(0));
        return i + 2;
    }

    private static int decodeHex(String s, int i) {
        int v = decodeHex(s.charAt(i));
        if (v != -1) {
            return v;
        }
        throw new IllegalArgumentException(LocalizationMessages.URI_COMPONENT_ENCODED_OCTET_INVALID_DIGIT(Integer.valueOf(i), Character.valueOf(s.charAt(i))));
    }

    private static int[] initHexTable() {
        int[] table = new int[128];
        Arrays.fill(table, -1);
        for (char c = '0'; c <= '9'; c = (char) (c + 1)) {
            table[c] = c - '0';
        }
        for (char c2 = 'A'; c2 <= 'F'; c2 = (char) (c2 + 1)) {
            table[c2] = (c2 - 'A') + 10;
        }
        for (char c3 = 'a'; c3 <= 'f'; c3 = (char) (c3 + 1)) {
            table[c3] = (c3 - 'a') + 10;
        }
        return table;
    }

    private static int decodeHex(char c) {
        if (c < 128) {
            return HEX_TABLE[c];
        }
        return -1;
    }

    public static boolean isHexCharacter(char c) {
        return c < 128 && HEX_TABLE[c] != -1;
    }

    public static String fullRelativeUri(URI uri) {
        String str;
        if (uri == null) {
            return null;
        }
        String query = uri.getRawQuery();
        StringBuilder sb = new StringBuilder();
        sb.append(uri.getRawPath());
        if (query == null || query.length() <= 0) {
            str = "";
        } else {
            str = "?" + query;
        }
        sb.append(str);
        return sb.toString();
    }
}
