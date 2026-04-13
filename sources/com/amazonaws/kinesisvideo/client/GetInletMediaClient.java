package com.amazonaws.kinesisvideo.client;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.http.HttpMethodName;
import com.amazonaws.kinesisvideo.http.KinesisVideoApacheHttpClient;
import com.amazonaws.kinesisvideo.signing.KinesisVideoSigner;
import java.net.URI;
import org.apache.http.client.methods.c;
import org.apache.http.entity.f;

public final class GetInletMediaClient {
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private static final String X_AMZN_REQUEST_ID = "x-amzn-RequestId";
    private Integer mConnectionTimeoutInMillis;
    private String mGetInletMediaInputInJson;
    private Integer mReadTimeoutInMillis;
    private KinesisVideoSigner mSigner;
    private URI mUri;

    GetInletMediaClient(GetInletMediaClientBuilder builder) {
        this.mUri = builder.mUri;
        this.mSigner = builder.mSigner;
        this.mGetInletMediaInputInJson = builder.mGetInletMediaInputInJson;
        this.mConnectionTimeoutInMillis = builder.mConnectionTimeoutInMillis;
        this.mReadTimeoutInMillis = builder.mReadTimeoutInMillis;
    }

    public GetInletMediaClientBuilder builder() {
        return new GetInletMediaClientBuilder();
    }

    public static class GetInletMediaClientBuilder {
        /* access modifiers changed from: private */
        public Integer mConnectionTimeoutInMillis;
        /* access modifiers changed from: private */
        public String mGetInletMediaInputInJson;
        /* access modifiers changed from: private */
        public Integer mReadTimeoutInMillis;
        /* access modifiers changed from: private */
        public KinesisVideoSigner mSigner;
        /* access modifiers changed from: private */
        public URI mUri;

        GetInletMediaClientBuilder() {
        }

        public GetInletMediaClientBuilder uri(URI uri) {
            this.mUri = uri;
            return this;
        }

        public GetInletMediaClientBuilder signer(KinesisVideoSigner signer) {
            this.mSigner = signer;
            return this;
        }

        public GetInletMediaClientBuilder getInletMediaInputInJson(String getInletMediaInputInJson) {
            this.mGetInletMediaInputInJson = getInletMediaInputInJson;
            return this;
        }

        public GetInletMediaClientBuilder connectionTimeoutInMillis(Integer connectionTimeoutInMillis) {
            this.mConnectionTimeoutInMillis = connectionTimeoutInMillis;
            return this;
        }

        public GetInletMediaClientBuilder readTimeoutInMillis(Integer readTimeoutInMillis) {
            this.mReadTimeoutInMillis = readTimeoutInMillis;
            return this;
        }

        public GetInletMediaClient build() {
            return new GetInletMediaClient(this);
        }
    }

    public c execute(@NonNull String requestId) {
        return getHttpClient(requestId).executeRequest();
    }

    private KinesisVideoApacheHttpClient getHttpClient(@NonNull String requestId) {
        KinesisVideoApacheHttpClient.Builder withUri = KinesisVideoApacheHttpClient.builder().withUri(this.mUri);
        f fVar = f.APPLICATION_JSON;
        KinesisVideoApacheHttpClient.Builder clientBuilder = withUri.withContentType(fVar).withMethod(HttpMethodName.POST).withContentInJson(this.mGetInletMediaInputInJson).withHeader("Content-Type", fVar.getMimeType()).withHeader(X_AMZN_REQUEST_ID, requestId);
        Integer num = this.mConnectionTimeoutInMillis;
        if (num != null) {
            clientBuilder = clientBuilder.withConnectionTimeoutInMillis(num.intValue());
        }
        Integer num2 = this.mReadTimeoutInMillis;
        if (num2 != null) {
            clientBuilder = clientBuilder.withSocketTimeoutInMillis(num2.intValue());
        }
        KinesisVideoApacheHttpClient client = clientBuilder.build();
        this.mSigner.sign(client);
        return client;
    }

    public URI getUri() {
        return this.mUri;
    }

    public void setUri(URI uri) {
        this.mUri = uri;
    }

    public KinesisVideoSigner getSigner() {
        return this.mSigner;
    }

    public void setSigner(KinesisVideoSigner signer) {
        this.mSigner = signer;
    }

    public String getGetInletMediaInputInJson() {
        return this.mGetInletMediaInputInJson;
    }

    public void setmGetInletMediaInputInJson(String getInletMediaInputInJson) {
        this.mGetInletMediaInputInJson = getInletMediaInputInJson;
    }

    public Integer getConnectionTimeoutInMillis() {
        return this.mConnectionTimeoutInMillis;
    }

    public void setConnectionTimeoutInMillis(Integer connectionTimeoutInMillis) {
        this.mConnectionTimeoutInMillis = connectionTimeoutInMillis;
    }

    public Integer getReadTimeoutInMillis() {
        return this.mReadTimeoutInMillis;
    }

    public void setReadTimeoutInMillis(Integer readTimeoutInMillis) {
        this.mReadTimeoutInMillis = readTimeoutInMillis;
    }
}
