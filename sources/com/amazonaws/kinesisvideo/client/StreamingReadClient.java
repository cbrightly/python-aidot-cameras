package com.amazonaws.kinesisvideo.client;

import com.amazonaws.kinesisvideo.http.HttpMethodName;
import com.amazonaws.kinesisvideo.http.KinesisVideoApacheHttpClient;
import com.amazonaws.kinesisvideo.signing.KinesisVideoSigner;
import java.net.URI;
import org.apache.http.client.methods.c;
import org.apache.http.entity.f;

public class StreamingReadClient {
    private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
    private Integer mConnectionTimeoutInMillis;
    private String mInputInJson;
    private Integer mReadTimeoutInMillis;
    private KinesisVideoSigner mSigner;
    private URI mUri;

    StreamingReadClient(StreamingReadClientBuilder builder) {
        this.mUri = builder.mUri;
        this.mSigner = builder.mSigner;
        this.mInputInJson = builder.mInputInJson;
        this.mConnectionTimeoutInMillis = builder.mConnectionTimeoutInMillis;
        this.mReadTimeoutInMillis = builder.mReadTimeoutInMillis;
    }

    public static StreamingReadClientBuilder builder() {
        return new StreamingReadClientBuilder();
    }

    public static class StreamingReadClientBuilder {
        /* access modifiers changed from: private */
        public Integer mConnectionTimeoutInMillis;
        /* access modifiers changed from: private */
        public String mInputInJson;
        /* access modifiers changed from: private */
        public Integer mReadTimeoutInMillis;
        /* access modifiers changed from: private */
        public KinesisVideoSigner mSigner;
        /* access modifiers changed from: private */
        public URI mUri;

        StreamingReadClientBuilder() {
        }

        public StreamingReadClientBuilder uri(URI uri) {
            this.mUri = uri;
            return this;
        }

        public StreamingReadClientBuilder signer(KinesisVideoSigner signer) {
            this.mSigner = signer;
            return this;
        }

        public StreamingReadClientBuilder inputInJson(String inputInJson) {
            this.mInputInJson = inputInJson;
            return this;
        }

        public StreamingReadClientBuilder connectionTimeoutInMillis(Integer connectionTimeoutInMillis) {
            this.mConnectionTimeoutInMillis = connectionTimeoutInMillis;
            return this;
        }

        public StreamingReadClientBuilder readTimeoutInMillis(Integer readTimeoutInMillis) {
            this.mReadTimeoutInMillis = readTimeoutInMillis;
            return this;
        }

        public StreamingReadClient build() {
            return new StreamingReadClient(this);
        }
    }

    public c execute() {
        return getHttpClient().executeRequest();
    }

    private KinesisVideoApacheHttpClient getHttpClient() {
        KinesisVideoApacheHttpClient.Builder withUri = KinesisVideoApacheHttpClient.builder().withUri(this.mUri);
        f fVar = f.APPLICATION_JSON;
        KinesisVideoApacheHttpClient.Builder clientBuilder = withUri.withContentType(fVar).withMethod(HttpMethodName.POST).withContentInJson(this.mInputInJson).withHeader("Content-Type", fVar.getMimeType());
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

    public String getInputInJson() {
        return this.mInputInJson;
    }

    public void setInputInJson(String inputInJson) {
        this.mInputInJson = inputInJson;
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
