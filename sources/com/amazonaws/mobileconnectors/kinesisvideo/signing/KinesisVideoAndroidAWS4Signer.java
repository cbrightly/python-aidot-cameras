package com.amazonaws.mobileconnectors.kinesisvideo.signing;

import android.util.Log;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.http.HttpClient;
import com.amazonaws.kinesisvideo.signing.KinesisVideoSigner;
import java.net.URI;
import java.util.Map;

public class KinesisVideoAndroidAWS4Signer extends AWS4Signer implements KinesisVideoSigner {
    private static final String AUTH_HEADER = "Authorization";
    private static final String CONTENT_HASH_HEADER = "x-amz-content-sha256";
    private static final String CONTENT_STREAMING_PAYLOAD = "STREAMING-AWS4-HMAC-SHA256-PAYLOAD";
    private static final String DATE_HEADER = "X-Amz-Date";
    private static final String SECURITY_TOKEN_HEADER = "X-Amz-Security-Token";
    private static final String TAG = KinesisVideoAndroidAWS4Signer.class.getSimpleName();
    private final AWSCredentials mAWSCredentials;
    private final KinesisVideoClientConfiguration mConfiguration;

    public static class SimpleSignableRequest extends DefaultRequest {
        public SimpleSignableRequest(HttpClient httpClient) {
            super("kinesisvideo");
            try {
                setHttpMethod(getHttpMethod(httpClient.getMethod().name()));
                setEndpoint(new URI(httpClient.getUri().getScheme() + "://" + httpClient.getUri().getHost()));
                setResourcePath(httpClient.getUri().getPath());
                setHeaders(httpClient.getHeaders());
            } catch (Throwable e) {
                throw new RuntimeException("Exception while creating signable request ! ", e);
            }
        }

        private static HttpMethodName getHttpMethod(String name) {
            for (HttpMethodName httpMethod : HttpMethodName.values()) {
                if (httpMethod.name().equals(name)) {
                    return httpMethod;
                }
            }
            throw new RuntimeException("Unsupported http method: " + name);
        }
    }

    public KinesisVideoAndroidAWS4Signer(AWSCredentials credentials, KinesisVideoClientConfiguration config) {
        this.mAWSCredentials = credentials;
        this.mConfiguration = config;
    }

    /* access modifiers changed from: protected */
    public String getCanonicalRequest(Request<?> request, String contentSha256) {
        return super.getCanonicalRequest(request, contentSha256);
    }

    /* access modifiers changed from: protected */
    public String calculateContentHash(Request<?> request) {
        request.addHeader(CONTENT_HASH_HEADER, "required");
        if (shouldAddContentStreamingPayloadInHeader(request.getHttpMethod().name())) {
            return CONTENT_STREAMING_PAYLOAD;
        }
        return super.calculateContentHash(request);
    }

    public void sign(HttpClient httpClient) {
        setServiceName(this.mConfiguration.getServiceName());
        setRegionName(this.mConfiguration.getRegion());
        SimpleSignableRequest signableRequest = toSignableRequest(httpClient);
        sign(signableRequest, this.mAWSCredentials);
        httpClient.getHeaders().put("Authorization", getHeaderIgnoreCase(signableRequest.getHeaders(), "Authorization"));
        httpClient.getHeaders().put("X-Amz-Date", getHeaderIgnoreCase(signableRequest.getHeaders(), "X-Amz-Date"));
        addSecurityToken(httpClient, signableRequest, this.mAWSCredentials);
        addContentHeader(httpClient);
    }

    public SimpleSignableRequest toSignableRequest(HttpClient httpClient) {
        return new SimpleSignableRequest(httpClient);
    }

    private void addSecurityToken(HttpClient httpClient, SimpleSignableRequest signableRequest, AWSCredentials credentials) {
        Object anotherToken;
        Object securityToken = getHeaderIgnoreCase(signableRequest.getHeaders(), SECURITY_TOKEN_HEADER);
        if (securityToken != null) {
            httpClient.getHeaders().put(SECURITY_TOKEN_HEADER, securityToken);
            Log.i(TAG, "using security token from signed request");
        } else if ((credentials instanceof AWSSessionCredentials) && (anotherToken = ((AWSSessionCredentials) credentials).getSessionToken()) != null) {
            Log.i(TAG, "using security token from aws session credentials");
            httpClient.getHeaders().put(SECURITY_TOKEN_HEADER, anotherToken.toString());
        }
    }

    private String getHeaderIgnoreCase(Map<String, String> headers, String headerKey) {
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (headerKey.equalsIgnoreCase(header.getKey())) {
                return header.getValue();
            }
        }
        return null;
    }

    private void addContentHeader(HttpClient httpClient) {
        if (shouldAddContentStreamingPayloadInHeader(httpClient.getMethod().name())) {
            httpClient.getHeaders().put(CONTENT_HASH_HEADER, CONTENT_STREAMING_PAYLOAD);
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldAddContentStreamingPayloadInHeader(String httpMethodName) {
        return HttpMethodName.POST.name().equals(httpMethodName);
    }
}
