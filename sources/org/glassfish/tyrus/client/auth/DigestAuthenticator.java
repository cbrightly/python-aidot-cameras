package org.glassfish.tyrus.client.auth;

import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.grizzly.http.server.Constants;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public final class DigestAuthenticator extends Authenticator {
    private static final int CLIENT_NONCE_BYTE_COUNT = 4;
    private static final char[] HEX_ARRAY = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Pattern KEY_VALUE_PAIR_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*(\"([^\"]+)\"|(\\w+))\\s*,?\\s*");
    private static final Logger logger = Logger.getLogger(DigestAuthenticator.class.getName());
    private SecureRandom randomGenerator;

    DigestAuthenticator() {
        try {
            this.randomGenerator = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            logger.config(LocalizationMessages.AUTHENTICATION_DIGEST_NO_SUCH_ALG());
        }
    }

    public String generateAuthorizationHeader(URI uri, String wwwAuthenticateHeader, Credentials credentials) {
        if (credentials != null) {
            try {
                DigestScheme digestScheme = parseAuthHeaders(wwwAuthenticateHeader);
                if (digestScheme != null) {
                    return createNextAuthToken(digestScheme, uri.toString(), credentials);
                }
                throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREATE_AUTH_HEADER_FAILED());
            } catch (IOException e) {
                throw new AuthenticationException(e.getMessage());
            }
        } else {
            throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING());
        }
    }

    private DigestScheme parseAuthHeaders(String authHeader) {
        if (authHeader == null) {
            return null;
        }
        String[] parts = authHeader.trim().split("\\s+", 2);
        if (parts.length != 2 || !parts[0].toLowerCase().equals("digest")) {
            return null;
        }
        String realm = null;
        String nonce = null;
        String opaque = null;
        QOP qop = QOP.UNSPECIFIED;
        Algorithm algorithm = Algorithm.UNSPECIFIED;
        boolean stale = false;
        Matcher match = KEY_VALUE_PAIR_PATTERN.matcher(parts[1]);
        while (match.find()) {
            if (match.groupCount() == 4) {
                String key = match.group(1);
                String valNoQuotes = match.group(3);
                String val = valNoQuotes == null ? match.group(4) : valNoQuotes;
                if (key.equals("qop")) {
                    qop = QOP.parse(val);
                } else if (key.equals("realm")) {
                    realm = val;
                } else if (key.equals("nonce")) {
                    nonce = val;
                } else if (key.equals("opaque")) {
                    opaque = val;
                } else if (key.equals("stale")) {
                    stale = Boolean.parseBoolean(val);
                } else if (key.equals("algorithm")) {
                    algorithm = Algorithm.parse(val);
                }
            }
        }
        return new DigestScheme(realm, nonce, opaque, qop, algorithm, stale);
    }

    private String createNextAuthToken(DigestScheme ds, String uri, Credentials credentials) {
        String ha1;
        String response;
        StringBuilder sb = new StringBuilder(100);
        sb.append("Digest ");
        append(sb, "username", credentials.getUsername());
        append(sb, "realm", ds.getRealm());
        append(sb, "nonce", ds.getNonce());
        append(sb, "opaque", ds.getOpaque());
        append(sb, "algorithm", ds.getAlgorithm().toString(), false);
        append(sb, "qop", ds.getQop().toString(), false);
        append(sb, "uri", uri);
        if (ds.getAlgorithm().equals(Algorithm.MD5_SESS)) {
            ha1 = md5(md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), AuthConfig.CHARACTER_SET)));
        } else {
            ha1 = md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), AuthConfig.CHARACTER_SET));
        }
        String ha2 = md5(Constants.GET, uri);
        if (ds.getQop().equals(QOP.UNSPECIFIED)) {
            response = md5(ha1, ds.getNonce(), ha2);
        } else {
            String cnonce = randomBytes(4);
            append(sb, "cnonce", cnonce);
            String nc = String.format("%08x", new Object[]{Integer.valueOf(ds.incrementCounter())});
            append(sb, "nc", nc, false);
            response = md5(ha1, ds.getNonce(), nc, cnonce, ds.getQop().toString(), ha2);
        }
        append(sb, "response", response);
        return sb.toString();
    }

    private static void append(StringBuilder sb, String key, String value, boolean useQuote) {
        if (value != null) {
            if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
                sb.append(", ");
            }
            sb.append(key);
            sb.append('=');
            if (useQuote) {
                sb.append(StringUtil.DOUBLE_QUOTE);
            }
            sb.append(value);
            if (useQuote) {
                sb.append(StringUtil.DOUBLE_QUOTE);
            }
        }
    }

    private static void append(StringBuilder sb, String key, String value) {
        append(sb, key, value, true);
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[(bytes.length * 2)];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 255;
            char[] cArr = HEX_ARRAY;
            hexChars[j * 2] = cArr[v >>> 4];
            hexChars[(j * 2) + 1] = cArr[v & 15];
        }
        return new String(hexChars);
    }

    private static String md5(String... tokens) {
        StringBuilder sb = new StringBuilder(100);
        for (String token : tokens) {
            if (sb.length() > 0) {
                sb.append(':');
            }
            sb.append(token);
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sb.toString().getBytes(AuthConfig.CHARACTER_SET), 0, sb.length());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException ex) {
            throw new AuthenticationException(ex.getMessage());
        }
    }

    private String randomBytes(int nbBytes) {
        byte[] bytes = new byte[nbBytes];
        this.randomGenerator.nextBytes(bytes);
        return bytesToHex(bytes);
    }

    public enum QOP {
        UNSPECIFIED((String) null),
        AUTH("auth");
        
        private final String qop;

        private QOP(String qop2) {
            this.qop = qop2;
        }

        public String toString() {
            return this.qop;
        }

        public static QOP parse(String val) {
            if (val == null || val.isEmpty()) {
                return UNSPECIFIED;
            }
            if (val.contains("auth")) {
                return AUTH;
            }
            throw new UnsupportedOperationException(LocalizationMessages.AUTHENTICATION_DIGEST_QOP_UNSUPPORTED(val));
        }
    }

    public enum Algorithm {
        UNSPECIFIED((String) null),
        MD5("MD5"),
        MD5_SESS("MD5-sess");
        
        private final String md;

        private Algorithm(String md2) {
            this.md = md2;
        }

        public String toString() {
            return this.md;
        }

        public static Algorithm parse(String val) {
            if (val == null || val.isEmpty()) {
                return UNSPECIFIED;
            }
            String val2 = val.trim();
            Algorithm algorithm = MD5_SESS;
            if (val2.contains(algorithm.md) || val2.contains(algorithm.md.toLowerCase())) {
                return algorithm;
            }
            return MD5;
        }
    }

    public final class DigestScheme {
        private final Algorithm algorithm;
        private volatile int nc = 0;
        private final String nonce;
        private final String opaque;
        private final QOP qop;
        private final String realm;
        private final boolean stale;

        DigestScheme(String realm2, String nonce2, String opaque2, QOP qop2, Algorithm algorithm2, boolean stale2) {
            this.realm = realm2;
            this.nonce = nonce2;
            this.opaque = opaque2;
            this.qop = qop2;
            this.algorithm = algorithm2;
            this.stale = stale2;
        }

        public int incrementCounter() {
            int i = this.nc + 1;
            this.nc = i;
            return i;
        }

        public String getNonce() {
            return this.nonce;
        }

        public String getRealm() {
            return this.realm;
        }

        public String getOpaque() {
            return this.opaque;
        }

        public Algorithm getAlgorithm() {
            return this.algorithm;
        }

        public QOP getQop() {
            return this.qop;
        }

        public boolean isStale() {
            return this.stale;
        }

        public int getNc() {
            return this.nc;
        }
    }
}
