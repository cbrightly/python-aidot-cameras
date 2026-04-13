package io.netty.handler.ssl;

import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CipherSuiteConverter {
    private static final Pattern JAVA_AES_CBC_PATTERN = Pattern.compile("^(AES)_([0-9]+)_CBC$");
    private static final Pattern JAVA_AES_PATTERN = Pattern.compile("^(AES)_([0-9]+)_(.*)$");
    private static final Pattern JAVA_CIPHERSUITE_PATTERN = Pattern.compile("^(?:TLS|SSL)_((?:(?!_WITH_).)+)_WITH_(.*)_(.*)$");
    private static final Pattern OPENSSL_AES_CBC_PATTERN = Pattern.compile("^(AES)([0-9]+)$");
    private static final Pattern OPENSSL_AES_PATTERN = Pattern.compile("^(AES)([0-9]+)-(.*)$");
    private static final Pattern OPENSSL_CIPHERSUITE_PATTERN = Pattern.compile("^(?:((?:(?:EXP-)?(?:(?:DHE|EDH|ECDH|ECDHE|SRP|RSA)-(?:DSS|RSA|ECDSA|PSK)|(?:ADH|AECDH|KRB5|PSK|SRP)))|EXP)-)?(.*)-(.*)$");
    private static final ConcurrentMap<String, String> j2o = PlatformDependent.newConcurrentHashMap();
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) CipherSuiteConverter.class);
    private static final ConcurrentMap<String, Map<String, String>> o2j = PlatformDependent.newConcurrentHashMap();

    static void clearCache() {
        j2o.clear();
        o2j.clear();
    }

    static boolean isJ2OCached(String key, String value) {
        return value.equals(j2o.get(key));
    }

    static boolean isO2JCached(String key, String protocol, String value) {
        Map<String, String> p2j = (Map) o2j.get(key);
        if (p2j == null) {
            return false;
        }
        return value.equals(p2j.get(protocol));
    }

    static String toOpenSsl(Iterable<String> javaCipherSuites) {
        String c;
        StringBuilder buf = new StringBuilder();
        Iterator<String> it = javaCipherSuites.iterator();
        while (it.hasNext() && (c = it.next()) != null) {
            String converted = toOpenSsl(c);
            if (converted != null) {
                c = converted;
            }
            buf.append(c);
            buf.append(':');
        }
        if (buf.length() <= 0) {
            return "";
        }
        buf.setLength(buf.length() - 1);
        return buf.toString();
    }

    static String toOpenSsl(String javaCipherSuite) {
        String converted = (String) j2o.get(javaCipherSuite);
        if (converted != null) {
            return converted;
        }
        return cacheFromJava(javaCipherSuite);
    }

    private static String cacheFromJava(String javaCipherSuite) {
        String openSslCipherSuite = toOpenSslUncached(javaCipherSuite);
        if (openSslCipherSuite == null) {
            return null;
        }
        j2o.putIfAbsent(javaCipherSuite, openSslCipherSuite);
        String javaCipherSuiteSuffix = javaCipherSuite.substring(4);
        Map<String, String> p2j = new HashMap<>(4);
        p2j.put("", javaCipherSuiteSuffix);
        p2j.put("SSL", "SSL_" + javaCipherSuiteSuffix);
        p2j.put("TLS", "TLS_" + javaCipherSuiteSuffix);
        o2j.put(openSslCipherSuite, p2j);
        logger.debug("Cipher suite mapping: {} => {}", javaCipherSuite, openSslCipherSuite);
        return openSslCipherSuite;
    }

    static String toOpenSslUncached(String javaCipherSuite) {
        Matcher m = JAVA_CIPHERSUITE_PATTERN.matcher(javaCipherSuite);
        if (!m.matches()) {
            return null;
        }
        String handshakeAlgo = toOpenSslHandshakeAlgo(m.group(1));
        String bulkCipher = toOpenSslBulkCipher(m.group(2));
        String hmacAlgo = toOpenSslHmacAlgo(m.group(3));
        if (handshakeAlgo.isEmpty()) {
            return bulkCipher + '-' + hmacAlgo;
        } else if (bulkCipher.contains("CHACHA20")) {
            return handshakeAlgo + '-' + bulkCipher;
        } else {
            return handshakeAlgo + '-' + bulkCipher + '-' + hmacAlgo;
        }
    }

    private static String toOpenSslHandshakeAlgo(String handshakeAlgo) {
        boolean export = handshakeAlgo.endsWith("_EXPORT");
        if (export) {
            handshakeAlgo = handshakeAlgo.substring(0, handshakeAlgo.length() - 7);
        }
        if ("RSA".equals(handshakeAlgo)) {
            handshakeAlgo = "";
        } else if (handshakeAlgo.endsWith("_anon")) {
            handshakeAlgo = 'A' + handshakeAlgo.substring(0, handshakeAlgo.length() - 5);
        }
        if (export) {
            if (handshakeAlgo.isEmpty()) {
                handshakeAlgo = "EXP";
            } else {
                handshakeAlgo = "EXP-" + handshakeAlgo;
            }
        }
        return handshakeAlgo.replace('_', '-');
    }

    private static String toOpenSslBulkCipher(String bulkCipher) {
        if (bulkCipher.startsWith("AES_")) {
            Matcher m = JAVA_AES_CBC_PATTERN.matcher(bulkCipher);
            if (m.matches()) {
                return m.replaceFirst("$1$2");
            }
            Matcher m2 = JAVA_AES_PATTERN.matcher(bulkCipher);
            if (m2.matches()) {
                return m2.replaceFirst("$1$2-$3");
            }
        }
        if ("3DES_EDE_CBC".equals(bulkCipher)) {
            return "DES-CBC3";
        }
        if ("RC4_128".equals(bulkCipher) || "RC4_40".equals(bulkCipher)) {
            return "RC4";
        }
        if ("DES40_CBC".equals(bulkCipher) || "DES_CBC_40".equals(bulkCipher)) {
            return "DES-CBC";
        }
        if ("RC2_CBC_40".equals(bulkCipher)) {
            return "RC2-CBC";
        }
        return bulkCipher.replace('_', '-');
    }

    private static String toOpenSslHmacAlgo(String hmacAlgo) {
        return hmacAlgo;
    }

    static String toJava(String openSslCipherSuite, String protocol) {
        Map<String, String> p2j = (Map) o2j.get(openSslCipherSuite);
        if (p2j == null && (p2j = cacheFromOpenSsl(openSslCipherSuite)) == null) {
            return null;
        }
        String javaCipherSuite = p2j.get(protocol);
        if (javaCipherSuite != null) {
            return javaCipherSuite;
        }
        return protocol + '_' + p2j.get("");
    }

    private static Map<String, String> cacheFromOpenSsl(String openSslCipherSuite) {
        String javaCipherSuiteSuffix = toJavaUncached(openSslCipherSuite);
        if (javaCipherSuiteSuffix == null) {
            return null;
        }
        String javaCipherSuiteSsl = "SSL_" + javaCipherSuiteSuffix;
        String javaCipherSuiteTls = "TLS_" + javaCipherSuiteSuffix;
        Map<String, String> p2j = new HashMap<>(4);
        p2j.put("", javaCipherSuiteSuffix);
        p2j.put("SSL", javaCipherSuiteSsl);
        p2j.put("TLS", javaCipherSuiteTls);
        o2j.putIfAbsent(openSslCipherSuite, p2j);
        ConcurrentMap<String, String> concurrentMap = j2o;
        concurrentMap.putIfAbsent(javaCipherSuiteTls, openSslCipherSuite);
        concurrentMap.putIfAbsent(javaCipherSuiteSsl, openSslCipherSuite);
        InternalLogger internalLogger = logger;
        internalLogger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteTls, openSslCipherSuite);
        internalLogger.debug("Cipher suite mapping: {} => {}", javaCipherSuiteSsl, openSslCipherSuite);
        return p2j;
    }

    static String toJavaUncached(String openSslCipherSuite) {
        boolean export;
        Matcher m = OPENSSL_CIPHERSUITE_PATTERN.matcher(openSslCipherSuite);
        if (!m.matches()) {
            return null;
        }
        String handshakeAlgo = m.group(1);
        if (handshakeAlgo == null) {
            handshakeAlgo = "";
            export = false;
        } else if (handshakeAlgo.startsWith("EXP-")) {
            handshakeAlgo = handshakeAlgo.substring(4);
            export = true;
        } else if ("EXP".equals(handshakeAlgo)) {
            handshakeAlgo = "";
            export = true;
        } else {
            export = false;
        }
        String handshakeAlgo2 = toJavaHandshakeAlgo(handshakeAlgo, export);
        String bulkCipher = toJavaBulkCipher(m.group(2), export);
        String javaCipherSuite = handshakeAlgo2 + "_WITH_" + bulkCipher + '_' + toJavaHmacAlgo(m.group(3));
        if (!bulkCipher.contains("CHACHA20")) {
            return javaCipherSuite;
        }
        return javaCipherSuite + "_SHA256";
    }

    private static String toJavaHandshakeAlgo(String handshakeAlgo, boolean export) {
        if (handshakeAlgo.isEmpty()) {
            handshakeAlgo = "RSA";
        } else if ("ADH".equals(handshakeAlgo)) {
            handshakeAlgo = "DH_anon";
        } else if ("AECDH".equals(handshakeAlgo)) {
            handshakeAlgo = "ECDH_anon";
        }
        String handshakeAlgo2 = handshakeAlgo.replace('-', '_');
        if (!export) {
            return handshakeAlgo2;
        }
        return handshakeAlgo2 + "_EXPORT";
    }

    private static String toJavaBulkCipher(String bulkCipher, boolean export) {
        if (bulkCipher.startsWith("AES")) {
            Matcher m = OPENSSL_AES_CBC_PATTERN.matcher(bulkCipher);
            if (m.matches()) {
                return m.replaceFirst("$1_$2_CBC");
            }
            Matcher m2 = OPENSSL_AES_PATTERN.matcher(bulkCipher);
            if (m2.matches()) {
                return m2.replaceFirst("$1_$2_$3");
            }
        }
        if ("DES-CBC3".equals(bulkCipher)) {
            return "3DES_EDE_CBC";
        }
        if ("RC4".equals(bulkCipher)) {
            if (export) {
                return "RC4_40";
            }
            return "RC4_128";
        } else if ("DES-CBC".equals(bulkCipher)) {
            if (export) {
                return "DES_CBC_40";
            }
            return "DES_CBC";
        } else if (!"RC2-CBC".equals(bulkCipher)) {
            return bulkCipher.replace('-', '_');
        } else {
            if (export) {
                return "RC2_CBC_40";
            }
            return "RC2_CBC";
        }
    }

    private static String toJavaHmacAlgo(String hmacAlgo) {
        return hmacAlgo;
    }

    private CipherSuiteConverter() {
    }
}
