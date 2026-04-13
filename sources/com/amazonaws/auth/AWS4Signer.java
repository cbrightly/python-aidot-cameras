package com.amazonaws.auth;

import com.amazonaws.AmazonClientException;
import com.amazonaws.Request;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import com.amazonaws.util.AwsHostNameUtils;
import com.amazonaws.util.BinaryUtils;
import com.amazonaws.util.DateUtils;
import com.amazonaws.util.HttpUtils;
import com.amazonaws.util.StringUtils;
import com.didichuxing.doraemonkit.okgo.cookie.SerializableCookie;
import com.didichuxing.doraemonkit.okgo.model.Progress;
import com.meituan.robust.Constants;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AWS4Signer extends AbstractAWSSigner implements ServiceAwareSigner, RegionAwareSigner, Presigner {
    protected static final String ALGORITHM = "AWS4-HMAC-SHA256";
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final long MAX_EXPIRATION_TIME_IN_SECONDS = 604800;
    private static final long MILLISEC = 1000;
    protected static final String TERMINATOR = "aws4_request";
    private static final String TIME_PATTERN = "yyyyMMdd'T'HHmmss'Z'";
    protected static final Log log = LogFactory.getLog(AWS4Signer.class);
    protected boolean doubleUrlEncode;
    protected Date overriddenDate;
    protected String regionName;
    protected String serviceName;

    public AWS4Signer() {
        this(true);
    }

    public AWS4Signer(boolean doubleUrlEncoding) {
        this.doubleUrlEncode = doubleUrlEncoding;
    }

    public void sign(Request<?> request, AWSCredentials credentials) {
        Request<?> request2 = request;
        AWSCredentials aWSCredentials = credentials;
        if (!(aWSCredentials instanceof AnonymousAWSCredentials)) {
            AWSCredentials sanitizedCredentials = sanitizeCredentials(aWSCredentials);
            if (sanitizedCredentials instanceof AWSSessionCredentials) {
                addSessionCredentials(request2, (AWSSessionCredentials) sanitizedCredentials);
            }
            addHostHeader(request);
            long dateMilli = getDateFromRequest(request);
            String dateStamp = getDateStamp(dateMilli);
            String scope = getScope(request2, dateStamp);
            String contentSha256 = calculateContentHash(request);
            String timeStamp = getTimeStamp(dateMilli);
            request2.addHeader("X-Amz-Date", timeStamp);
            if (request.getHeaders().get("x-amz-content-sha256") != null && "required".equals(request.getHeaders().get("x-amz-content-sha256"))) {
                request2.addHeader("x-amz-content-sha256", contentSha256);
            }
            String str = timeStamp;
            HeaderSigningResult headerSigningResult = computeSignature(request, dateStamp, timeStamp, ALGORITHM, contentSha256, sanitizedCredentials);
            StringBuilder sb = new StringBuilder();
            sb.append("Credential=");
            sb.append(sanitizedCredentials.getAWSAccessKeyId() + "/" + scope);
            request2.addHeader("Authorization", "AWS4-HMAC-SHA256 " + sb.toString() + ", " + ("SignedHeaders=" + getSignedHeadersString(request)) + ", " + ("Signature=" + BinaryUtils.toHex(headerSigningResult.getSignature())));
            processRequestPayload(request2, headerSigningResult);
        }
    }

    public void setServiceName(String serviceName2) {
        this.serviceName = serviceName2;
    }

    public void setRegionName(String regionName2) {
        this.regionName = regionName2;
    }

    /* access modifiers changed from: protected */
    public void addSessionCredentials(Request<?> request, AWSSessionCredentials credentials) {
        request.addHeader("x-amz-security-token", credentials.getSessionToken());
    }

    /* access modifiers changed from: protected */
    public String extractRegionName(URI endpoint) {
        String str = this.regionName;
        if (str != null) {
            return str;
        }
        return AwsHostNameUtils.parseRegionName(endpoint.getHost(), this.serviceName);
    }

    /* access modifiers changed from: protected */
    public String extractServiceName(URI endpoint) {
        String str = this.serviceName;
        if (str != null) {
            return str;
        }
        return AwsHostNameUtils.parseServiceName(endpoint);
    }

    /* access modifiers changed from: package-private */
    public void overrideDate(Date overriddenDate2) {
        this.overriddenDate = overriddenDate2;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalizedHeaderString(Request<?> request) {
        List<String> sortedHeaders = new ArrayList<>();
        sortedHeaders.addAll(request.getHeaders().keySet());
        Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);
        StringBuilder buffer = new StringBuilder();
        for (String header : sortedHeaders) {
            if (needsSign(header)) {
                String key = StringUtils.lowerCase(header).replaceAll("\\s+", " ");
                String value = request.getHeaders().get(header);
                buffer.append(key);
                buffer.append(":");
                if (value != null) {
                    buffer.append(value.replaceAll("\\s+", " "));
                }
                buffer.append("\n");
            }
        }
        return buffer.toString();
    }

    /* access modifiers changed from: protected */
    public String getSignedHeadersString(Request<?> request) {
        List<String> sortedHeaders = new ArrayList<>();
        sortedHeaders.addAll(request.getHeaders().keySet());
        Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);
        StringBuilder buffer = new StringBuilder();
        for (String header : sortedHeaders) {
            if (needsSign(header)) {
                if (buffer.length() > 0) {
                    buffer.append(Constants.PACKNAME_END);
                }
                buffer.append(StringUtils.lowerCase(header));
            }
        }
        return buffer.toString();
    }

    /* access modifiers changed from: protected */
    public String getCanonicalRequest(Request<?> request, String contentSha256) {
        String path = HttpUtils.appendUri(request.getEndpoint().getPath(), request.getResourcePath());
        String canonicalRequest = request.getHttpMethod().toString() + "\n" + getCanonicalizedResourcePath(path, this.doubleUrlEncode) + "\n" + getCanonicalizedQueryString(request) + "\n" + getCanonicalizedHeaderString(request) + "\n" + getSignedHeadersString(request) + "\n" + contentSha256;
        log.debug("AWS4 Canonical Request: '\"" + canonicalRequest + "\"");
        return canonicalRequest;
    }

    /* access modifiers changed from: protected */
    public String getStringToSign(String algorithm, String dateTime, String scope, String canonicalRequest) {
        String stringToSign = algorithm + "\n" + dateTime + "\n" + scope + "\n" + BinaryUtils.toHex(hash(canonicalRequest));
        log.debug("AWS4 String to Sign: '\"" + stringToSign + "\"");
        return stringToSign;
    }

    /* access modifiers changed from: protected */
    public final HeaderSigningResult computeSignature(Request<?> request, String dateStamp, String timeStamp, String algorithm, String contentSha256, AWSCredentials sanitizedCredentials) {
        String str = dateStamp;
        String str2 = timeStamp;
        String regionName2 = extractRegionName(request.getEndpoint());
        String serviceName2 = extractServiceName(request.getEndpoint());
        String scope = str + "/" + regionName2 + "/" + serviceName2 + "/" + TERMINATOR;
        String stringToSign = getStringToSign(algorithm, str2, scope, getCanonicalRequest(request, contentSha256));
        Charset charset = StringUtils.UTF8;
        byte[] kSecret = ("AWS4" + sanitizedCredentials.getAWSSecretKey()).getBytes(charset);
        SigningAlgorithm signingAlgorithm = SigningAlgorithm.HmacSHA256;
        byte[] kSigning = sign(TERMINATOR, sign(serviceName2, sign(regionName2, sign(str, kSecret, signingAlgorithm), signingAlgorithm), signingAlgorithm), signingAlgorithm);
        return new HeaderSigningResult(str2, scope, kSigning, sign(stringToSign.getBytes(charset), kSigning, signingAlgorithm));
    }

    /* access modifiers changed from: protected */
    public final String getTimeStamp(long dateMilli) {
        return DateUtils.format("yyyyMMdd'T'HHmmss'Z'", new Date(dateMilli));
    }

    /* access modifiers changed from: protected */
    public final String getDateStamp(long dateMilli) {
        return DateUtils.format(DATE_PATTERN, new Date(dateMilli));
    }

    /* access modifiers changed from: protected */
    public final long getDateFromRequest(Request<?> request) {
        Date date = getSignatureDate(getTimeOffset(request));
        if (this.overriddenDate != null) {
            date = this.overriddenDate;
        }
        return date.getTime();
    }

    /* access modifiers changed from: protected */
    public void addHostHeader(Request<?> request) {
        String hostHeader = request.getEndpoint().getHost();
        if (HttpUtils.isUsingNonDefaultPort(request.getEndpoint())) {
            hostHeader = hostHeader + ":" + request.getEndpoint().getPort();
        }
        request.addHeader("Host", hostHeader);
    }

    /* access modifiers changed from: protected */
    public String getScope(Request<?> request, String dateStamp) {
        String regionName2 = extractRegionName(request.getEndpoint());
        String serviceName2 = extractServiceName(request.getEndpoint());
        return dateStamp + "/" + regionName2 + "/" + serviceName2 + "/" + TERMINATOR;
    }

    /* access modifiers changed from: protected */
    public String calculateContentHash(Request<?> request) {
        InputStream payloadStream = getBinaryRequestPayloadStream(request);
        payloadStream.mark(-1);
        String contentSha256 = BinaryUtils.toHex(hash(payloadStream));
        try {
            payloadStream.reset();
            return contentSha256;
        } catch (IOException e) {
            throw new AmazonClientException("Unable to reset stream after calculating AWS4 signature", e);
        }
    }

    /* access modifiers changed from: protected */
    public void processRequestPayload(Request<?> request, HeaderSigningResult headerSigningResult) {
    }

    public static class HeaderSigningResult {
        private final String dateTime;
        private final byte[] kSigning;
        private final String scope;
        private final byte[] signature;

        public HeaderSigningResult(String dateTime2, String scope2, byte[] kSigning2, byte[] signature2) {
            this.dateTime = dateTime2;
            this.scope = scope2;
            this.kSigning = kSigning2;
            this.signature = signature2;
        }

        public String getDateTime() {
            return this.dateTime;
        }

        public String getScope() {
            return this.scope;
        }

        public byte[] getKSigning() {
            byte[] bArr = this.kSigning;
            byte[] kSigningCopy = new byte[bArr.length];
            System.arraycopy(bArr, 0, kSigningCopy, 0, bArr.length);
            return kSigningCopy;
        }

        public byte[] getSignature() {
            byte[] bArr = this.signature;
            byte[] signatureCopy = new byte[bArr.length];
            System.arraycopy(bArr, 0, signatureCopy, 0, bArr.length);
            return signatureCopy;
        }
    }

    /*  JADX ERROR: NullPointerException in pass: CodeShrinkVisitor
        java.lang.NullPointerException
        */
    public void presignRequest(com.amazonaws.Request<?> r21, com.amazonaws.auth.AWSCredentials r22, java.util.Date r23) {
        /*
            r20 = this;
            r7 = r20
            r8 = r21
            r9 = r22
            boolean r0 = r9 instanceof com.amazonaws.auth.AnonymousAWSCredentials
            if (r0 == 0) goto L_0x000b
            return
        L_0x000b:
            r0 = 604800(0x93a80, double:2.98811E-318)
            if (r23 == 0) goto L_0x001f
            long r2 = r23.getTime()
            long r4 = java.lang.System.currentTimeMillis()
            long r2 = r2 - r4
            r4 = 1000(0x3e8, double:4.94E-321)
            long r0 = r2 / r4
            r10 = r0
            goto L_0x0020
        L_0x001f:
            r10 = r0
        L_0x0020:
            r0 = 604800(0x93a80, double:2.98811E-318)
            int r0 = (r10 > r0 ? 1 : (r10 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x00b5
            r20.addHostHeader(r21)
            com.amazonaws.auth.AWSCredentials r12 = r7.sanitizeCredentials(r9)
            boolean r0 = r12 instanceof com.amazonaws.auth.AWSSessionCredentials
            if (r0 == 0) goto L_0x003e
            r0 = r12
            com.amazonaws.auth.AWSSessionCredentials r0 = (com.amazonaws.auth.AWSSessionCredentials) r0
            java.lang.String r0 = r0.getSessionToken()
            java.lang.String r1 = "X-Amz-Security-Token"
            r8.addParameter(r1, r0)
        L_0x003e:
            long r13 = r20.getDateFromRequest(r21)
            java.lang.String r15 = r7.getDateStamp(r13)
            java.lang.String r6 = r7.getScope(r8, r15)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r12.getAWSAccessKeyId()
            r0.append(r1)
            java.lang.String r1 = "/"
            r0.append(r1)
            r0.append(r6)
            java.lang.String r5 = r0.toString()
            java.lang.String r4 = r7.getTimeStamp(r13)
            java.lang.String r0 = "X-Amz-Algorithm"
            java.lang.String r1 = "AWS4-HMAC-SHA256"
            r8.addParameter(r0, r1)
            java.lang.String r0 = "X-Amz-Date"
            r8.addParameter(r0, r4)
            java.lang.String r0 = r20.getSignedHeadersString(r21)
            java.lang.String r1 = "X-Amz-SignedHeaders"
            r8.addParameter(r1, r0)
            java.lang.String r0 = java.lang.Long.toString(r10)
            java.lang.String r1 = "X-Amz-Expires"
            r8.addParameter(r1, r0)
            java.lang.String r0 = "X-Amz-Credential"
            r8.addParameter(r0, r5)
            java.lang.String r16 = r20.calculateContentHashPresign(r21)
            java.lang.String r17 = "AWS4-HMAC-SHA256"
            r0 = r20
            r1 = r21
            r2 = r15
            r3 = r4
            r18 = r4
            r4 = r17
            r17 = r5
            r5 = r16
            r19 = r6
            r6 = r12
            com.amazonaws.auth.AWS4Signer$HeaderSigningResult r0 = r0.computeSignature(r1, r2, r3, r4, r5, r6)
            byte[] r1 = r0.getSignature()
            java.lang.String r1 = com.amazonaws.util.BinaryUtils.toHex(r1)
            java.lang.String r2 = "X-Amz-Signature"
            r8.addParameter(r2, r1)
            return
        L_0x00b5:
            com.amazonaws.AmazonClientException r0 = new com.amazonaws.AmazonClientException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Requests that are pre-signed by SigV4 algorithm are valid for at most 7 days. The expiration date set on the current request ["
            r1.append(r2)
            long r2 = r23.getTime()
            java.lang.String r2 = r7.getTimeStamp(r2)
            r1.append(r2)
            java.lang.String r2 = "] has exceeded this limit."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>((java.lang.String) r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.auth.AWS4Signer.presignRequest(com.amazonaws.Request, com.amazonaws.auth.AWSCredentials, java.util.Date):void");
    }

    /* access modifiers changed from: protected */
    public String calculateContentHashPresign(Request<?> request) {
        return calculateContentHash(request);
    }

    /* access modifiers changed from: package-private */
    public boolean needsSign(String header) {
        return Progress.DATE.equalsIgnoreCase(header) || "Content-MD5".equalsIgnoreCase(header) || SerializableCookie.HOST.equalsIgnoreCase(header) || header.startsWith("x-amz") || header.startsWith("X-Amz");
    }
}
