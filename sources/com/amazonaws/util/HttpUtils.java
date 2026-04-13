package com.amazonaws.util;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.l;
import org.slf4j.e;

public class HttpUtils {
    private static final Pattern DECODED_CHARACTERS_PATTERN;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final Pattern ENCODED_CHARACTERS_PATTERN = Pattern.compile(Pattern.quote(e.ANY_NON_NULL_MARKER) + "|" + Pattern.quote(e.ANY_MARKER) + "|" + Pattern.quote("%7E") + "|" + Pattern.quote("%2F"));
    private static final int HTTP_STATUS_OK = 200;
    private static final int PORT_HTTP = 80;
    private static final int PORT_HTTPS = 443;

    static {
        StringBuilder decodePattern = new StringBuilder();
        decodePattern.append(Pattern.quote("%2A"));
        decodePattern.append("|");
        decodePattern.append(Pattern.quote("%2B"));
        decodePattern.append("|");
        DECODED_CHARACTERS_PATTERN = Pattern.compile(decodePattern.toString());
    }

    public static String urlEncode(String value, boolean path) {
        if (value == null) {
            return "";
        }
        try {
            String encoded = URLEncoder.encode(value, "UTF-8");
            Matcher matcher = ENCODED_CHARACTERS_PATTERN.matcher(encoded);
            StringBuffer buffer = new StringBuffer(encoded.length());
            while (matcher.find()) {
                String replacement = matcher.group(0);
                if (e.ANY_NON_NULL_MARKER.equals(replacement)) {
                    replacement = "%20";
                } else if (e.ANY_MARKER.equals(replacement)) {
                    replacement = "%2A";
                } else if ("%7E".equals(replacement)) {
                    replacement = "~";
                } else if (path && "%2F".equals(replacement)) {
                    replacement = "/";
                }
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);
            return buffer.toString();
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String urlDecode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean isUsingNonDefaultPort(URI uri) {
        String scheme = StringUtils.lowerCase(uri.getScheme());
        int port = uri.getPort();
        if (port <= 0) {
            return false;
        }
        if (l.DEFAULT_SCHEME_NAME.equals(scheme) && port == 80) {
            return false;
        }
        if (!"https".equals(scheme) || port != PORT_HTTPS) {
            return true;
        }
        return false;
    }

    public static boolean usePayloadForQueryParameters(Request<?> request) {
        boolean requestIsPOST = HttpMethodName.POST.equals(request.getHttpMethod());
        boolean requestHasNoPayload = request.getContent() == null;
        if (!requestIsPOST || !requestHasNoPayload) {
            return false;
        }
        return true;
    }

    public static String encodeParameters(Request<?> request) {
        if (request.getParameters().isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        try {
            for (Map.Entry<String, String> entry : request.getParameters().entrySet()) {
                String encodedName = URLEncoder.encode(entry.getKey(), "UTF-8");
                String value = entry.getValue();
                String encodedValue = value == null ? "" : URLEncoder.encode(value, "UTF-8");
                if (!first) {
                    sb.append("&");
                } else {
                    first = false;
                }
                sb.append(encodedName);
                sb.append("=");
                sb.append(encodedValue);
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static String appendUri(String baseUri, String path) {
        return appendUri(baseUri, path, false);
    }

    public static String appendUri(String baseUri, String path, boolean escapeDoubleSlash) {
        String resultUri = baseUri;
        if (path != null && path.length() > 0) {
            if (path.startsWith("/")) {
                if (resultUri.endsWith("/")) {
                    resultUri = resultUri.substring(0, resultUri.length() - 1);
                }
            } else if (!resultUri.endsWith("/")) {
                resultUri = resultUri + "/";
            }
            String encodedPath = urlEncode(path, true);
            if (escapeDoubleSlash) {
                encodedPath = encodedPath.replace("//", "/%2F");
            }
            return resultUri + encodedPath;
        } else if (resultUri.endsWith("/")) {
            return resultUri;
        } else {
            return resultUri + "/";
        }
    }

    public static InputStream fetchFile(URI uri, ClientConfiguration config) {
        HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
        connection.setConnectTimeout(getConnectionTimeout(config));
        connection.setReadTimeout(getSocketTimeout(config));
        connection.addRequestProperty("User-Agent", getUserAgent(config));
        if (connection.getResponseCode() == 200) {
            return connection.getInputStream();
        }
        InputStream is = connection.getErrorStream();
        if (is != null) {
            is.close();
        }
        connection.disconnect();
        throw new IOException("Error fetching file from " + uri + ": " + connection.getResponseMessage());
    }

    static String getUserAgent(ClientConfiguration config) {
        String userAgent = null;
        if (config != null) {
            userAgent = config.getUserAgent();
        }
        if (userAgent == null) {
            return ClientConfiguration.DEFAULT_USER_AGENT;
        }
        String str = ClientConfiguration.DEFAULT_USER_AGENT;
        if (str.equals(userAgent)) {
            return userAgent;
        }
        return userAgent + ", " + str;
    }

    static int getConnectionTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getConnectionTimeout();
        }
        return 15000;
    }

    static int getSocketTimeout(ClientConfiguration config) {
        if (config != null) {
            return config.getSocketTimeout();
        }
        return 15000;
    }
}
