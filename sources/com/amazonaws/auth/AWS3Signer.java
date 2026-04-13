package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.meituan.robust.Constants;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.apache.http.l;

public class AWS3Signer extends AbstractAWSSigner {
    private static final String AUTHORIZATION_HEADER = "X-Amzn-Authorization";
    private static final String HTTPS_SCHEME = "AWS3-HTTPS";
    private static final String HTTP_SCHEME = "AWS3";
    private static final String NONCE_HEADER = "x-amz-nonce";
    private static final Log log = LogFactory.getLog(AWS3Signer.class);
    private String overriddenDate;

    public void sign(Request<?> request, AWSCredentials credentials) {
        byte[] bytesToSign;
        String stringToSign;
        Request<?> request2 = request;
        AWSCredentials aWSCredentials = credentials;
        if (!(aWSCredentials instanceof AnonymousAWSCredentials)) {
            AWSCredentials sanitizedCredentials = sanitizeCredentials(aWSCredentials);
            SigningAlgorithm algorithm = SigningAlgorithm.HmacSHA256;
            String nonce = UUID.randomUUID().toString();
            String date = DateUtils.formatRFC822Date(getSignatureDate(getTimeOffset(request)));
            if (this.overriddenDate != null) {
                date = this.overriddenDate;
            }
            request2.addHeader("Date", date);
            request2.addHeader("X-Amz-Date", date);
            String hostHeader = request.getEndpoint().getHost();
            if (HttpUtils.isUsingNonDefaultPort(request.getEndpoint())) {
                hostHeader = hostHeader + ":" + request.getEndpoint().getPort();
            }
            request2.addHeader("Host", hostHeader);
            if (sanitizedCredentials instanceof AWSSessionCredentials) {
                addSessionCredentials(request2, (AWSSessionCredentials) sanitizedCredentials);
            }
            if (0 != 0) {
                request2.addHeader("x-amz-nonce", nonce);
                stringToSign = date + nonce;
                bytesToSign = stringToSign.getBytes(StringUtils.UTF8);
            } else {
                String path = HttpUtils.appendUri(request.getEndpoint().getPath(), request.getResourcePath());
                String stringToSign2 = request.getHttpMethod().toString() + "\n" + getCanonicalizedResourcePath(path) + "\n" + getCanonicalizedQueryString(request.getParameters()) + "\n" + getCanonicalizedHeadersForStringToSign(request) + "\n" + getRequestPayloadWithoutQueryParams(request);
                stringToSign = stringToSign2;
                bytesToSign = hash(stringToSign2);
            }
            log.debug("Calculated StringToSign: " + stringToSign);
            String signature = signAndBase64Encode(bytesToSign, sanitizedCredentials.getAWSSecretKey(), algorithm);
            StringBuilder builder = new StringBuilder();
            builder.append(0 != 0 ? HTTPS_SCHEME : HTTP_SCHEME);
            builder.append(" ");
            builder.append("AWSAccessKeyId=" + sanitizedCredentials.getAWSAccessKeyId() + ",");
            builder.append("Algorithm=" + algorithm.toString() + ",");
            if (0 == 0) {
                builder.append(getSignedHeadersComponent(request) + ",");
            }
            builder.append("Signature=" + signature);
            request2.addHeader(AUTHORIZATION_HEADER, builder.toString());
        }
    }

    private String getSignedHeadersComponent(Request<?> request) {
        StringBuilder builder = new StringBuilder();
        builder.append("SignedHeaders=");
        boolean first = true;
        for (String header : getHeadersForStringToSign(request)) {
            if (!first) {
                builder.append(Constants.PACKNAME_END);
            }
            builder.append(header);
            first = false;
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    public List<String> getHeadersForStringToSign(Request<?> request) {
        List<String> headersToSign = new ArrayList<>();
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            String key = entry.getKey();
            String lowerCaseKey = StringUtils.lowerCase(key);
            if (lowerCaseKey.startsWith("x-amz") || SerializableCookie.HOST.equals(lowerCaseKey)) {
                headersToSign.add(key);
            }
        }
        Collections.sort(headersToSign);
        return headersToSign;
    }

    /* access modifiers changed from: package-private */
    public void overrideDate(String date) {
        this.overriddenDate = date;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedHeadersForStringToSign(Request<?> request) {
        List<String> headersToSign = getHeadersForStringToSign(request);
        for (int i = 0; i < headersToSign.size(); i++) {
            headersToSign.set(i, StringUtils.lowerCase(headersToSign.get(i)));
        }
        SortedMap<String, String> sortedHeaderMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
            if (headersToSign.contains(StringUtils.lowerCase(entry.getKey()))) {
                sortedHeaderMap.put(StringUtils.lowerCase(entry.getKey()), entry.getValue());
            }
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry2 : sortedHeaderMap.entrySet()) {
            builder.append(StringUtils.lowerCase(entry2.getKey()));
            builder.append(":");
            builder.append(entry2.getValue());
            builder.append("\n");
        }
        return builder.toString();
    }

    /* access modifiers changed from: package-private */
    public boolean shouldUseHttpsScheme(Request<?> request) {
        try {
            String protocol = StringUtils.lowerCase(request.getEndpoint().toURL().getProtocol());
            if (l.DEFAULT_SCHEME_NAME.equals(protocol)) {
                return false;
            }
            if ("https".equals(protocol)) {
                return true;
            }
            throw new AmazonClientException("Unknown request endpoint protocol encountered while signing request: " + protocol);
        } catch (MalformedURLException e) {
            throw new AmazonClientException("Unable to parse request endpoint during signing", e);
        }
    }

    /* access modifiers changed from: protected */
    public void addSessionCredentials(Request<?> request, AWSSessionCredentials credentials) {
        request.addHeader("x-amz-security-token", credentials.getSessionToken());
    }
}
