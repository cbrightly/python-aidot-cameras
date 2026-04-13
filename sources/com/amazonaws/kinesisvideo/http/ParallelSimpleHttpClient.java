package com.amazonaws.kinesisvideo.http;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.socket.SocketFactory;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ParallelSimpleHttpClient implements HttpClient {
    private static final String CLRF = "\r\n";
    private static final String HEADER_FORMAT = "%s: %s";
    private static final String HOST_HEADER = "Host";
    private static final String HTTP_1_1 = "HTTP/1.1";
    /* access modifiers changed from: private */
    public static final Consumer<Exception> NO_OP_COMPLETION = new Consumer<Exception>() {
        public void accept(Exception object) {
        }
    };
    /* access modifiers changed from: private */
    public static final Consumer<OutputStream> NO_OP_SENDER = new Consumer<OutputStream>() {
        public void accept(OutputStream outputStream) {
        }
    };
    private static final String SPACE = " ";
    /* access modifiers changed from: private */
    public final Log log;
    /* access modifiers changed from: private */
    public final Builder mBuilder;
    /* access modifiers changed from: private */
    public InputStream mInputStream;
    /* access modifiers changed from: private */
    public OutputStream mOutputStream;
    private Socket mSocket;
    /* access modifiers changed from: private */
    public ExecutorService payloadSender;
    /* access modifiers changed from: private */
    public ExecutorService responseReceiver;

    public static final class Builder {
        /* access modifiers changed from: private */
        public Consumer<Exception> mCompletion;
        /* access modifiers changed from: private */
        public final Map<String, String> mHeaders;
        /* access modifiers changed from: private */
        public Log mLog;
        /* access modifiers changed from: private */
        public HttpMethodName mMethod;
        /* access modifiers changed from: private */
        public Consumer<InputStream> mReceiver;
        /* access modifiers changed from: private */
        public Consumer<OutputStream> mSender;
        /* access modifiers changed from: private */
        public Integer mTimeout;
        /* access modifiers changed from: private */
        public URI mUri;

        private Builder() {
            this.mLog = new Log(Log.SYSTEM_OUT);
            this.mHeaders = new HashMap();
            this.mSender = ParallelSimpleHttpClient.NO_OP_SENDER;
            this.mCompletion = ParallelSimpleHttpClient.NO_OP_COMPLETION;
        }

        public Builder uri(URI uri) {
            this.mUri = uri;
            this.mHeaders.put("Host", uri.getHost());
            return this;
        }

        public Builder method(HttpMethodName method) {
            this.mMethod = method;
            return this;
        }

        public Builder header(String key, String value) {
            this.mHeaders.put(key, value);
            return this;
        }

        public Builder completionCallback(Consumer<Exception> completion) {
            if (completion != null) {
                this.mCompletion = completion;
            }
            return this;
        }

        public Builder setSenderCallback(Consumer<OutputStream> sender) {
            this.mSender = sender;
            return this;
        }

        public Builder setReceiverCallback(Consumer<InputStream> receiver) {
            this.mReceiver = receiver;
            return this;
        }

        public Builder setTimeout(Integer timeout) {
            this.mTimeout = timeout;
            return this;
        }

        public Builder log(@NonNull Log log) {
            this.mLog = log;
            return this;
        }

        public ParallelSimpleHttpClient build() {
            Preconditions.checkNotNull(this.mUri);
            return new ParallelSimpleHttpClient(this);
        }
    }

    private ParallelSimpleHttpClient(Builder builder) {
        this.mBuilder = builder;
        this.log = builder.mLog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void connectAndProcessInBackground() {
        try {
            Preconditions.checkNotNull(this.mBuilder.mReceiver, "No callback set for the receiver!");
            initSocket();
            startCommunication();
        } catch (Throwable e) {
            throw new RuntimeException("Exception while connecting to the server ! ", e);
        }
    }

    private void initSocket() {
        this.mSocket = new SocketFactory().createSocket(this.mBuilder.mUri);
        if (this.mBuilder.mTimeout != null) {
            this.mSocket.setSoTimeout(this.mBuilder.mTimeout.intValue());
        }
        this.mInputStream = this.mSocket.getInputStream();
        this.mOutputStream = this.mSocket.getOutputStream();
    }

    public InputStream connectAndGetResponse() {
        try {
            initSocket();
            sendInitRequest();
            return this.mInputStream;
        } catch (Exception e) {
            throw new RuntimeException("Exception while executing and returning response ! ", e);
        }
    }

    private void startCommunication() {
        sendInitRequest();
        sendPayloadInBackground();
        receiveResponseInBackground();
    }

    private void sendInitRequest() {
        Writer outputWriter = new BufferedWriter(new OutputStreamWriter(this.mOutputStream, Charset.defaultCharset()));
        String initRequest = getHttpRequestString() + getHeadersString() + "\r\n";
        this.log.debug("Request: " + initRequest);
        outputWriter.write(initRequest);
        outputWriter.flush();
    }

    private String getHttpRequestString() {
        return this.mBuilder.mMethod + SPACE + this.mBuilder.mUri.getPath() + SPACE + "HTTP/1.1" + "\r\n";
    }

    public HttpMethodName getMethod() {
        return this.mBuilder.mMethod;
    }

    public URI getUri() {
        return this.mBuilder.mUri;
    }

    public Map<String, String> getHeaders() {
        return this.mBuilder.mHeaders;
    }

    public InputStream getContent() {
        return null;
    }

    private String getHeadersString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> header : this.mBuilder.mHeaders.entrySet()) {
            builder.append(String.format(HEADER_FORMAT, new Object[]{header.getKey(), header.getValue()}));
            builder.append("\r\n");
        }
        String allHeaders = builder.toString();
        if (allHeaders.isEmpty()) {
            return "\r\n";
        }
        return allHeaders;
    }

    private void sendPayloadInBackground() {
        if (this.mBuilder.mSender != null) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
            this.payloadSender = newFixedThreadPool;
            newFixedThreadPool.execute(new Runnable() {
                /* JADX WARNING: Code restructure failed: missing block: B:3:0x002a, code lost:
                    if (0 != 0) goto L_0x0040;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                        r5 = this;
                        r0 = 0
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r1 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.common.logging.Log r1 = r1.log     // Catch:{ Exception -> 0x002f }
                        java.lang.String r2 = "Start sending data."
                        r1.debug(r2)     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r1 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient$Builder r1 = r1.mBuilder     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.common.function.Consumer r1 = r1.mSender     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r2 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this     // Catch:{ Exception -> 0x002f }
                        java.io.OutputStream r2 = r2.mOutputStream     // Catch:{ Exception -> 0x002f }
                        r1.accept(r2)     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r1 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this     // Catch:{ Exception -> 0x002f }
                        com.amazonaws.kinesisvideo.common.logging.Log r1 = r1.log     // Catch:{ Exception -> 0x002f }
                        java.lang.String r2 = "End sending data. Sent all data, close."
                        r1.debug(r2)     // Catch:{ Exception -> 0x002f }
                        if (r0 == 0) goto L_0x004d
                        goto L_0x0040
                    L_0x002d:
                        r1 = move-exception
                        goto L_0x0058
                    L_0x002f:
                        r1 = move-exception
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r2 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this     // Catch:{ all -> 0x002d }
                        com.amazonaws.kinesisvideo.common.logging.Log r2 = r2.log     // Catch:{ all -> 0x002d }
                        java.lang.String r3 = "Exception thrown on sending thread"
                        r4 = 0
                        java.lang.Object[] r4 = new java.lang.Object[r4]     // Catch:{ all -> 0x002d }
                        r2.exception(r1, r3, r4)     // Catch:{ all -> 0x002d }
                        r0 = r1
                    L_0x0040:
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r1 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient$Builder r1 = r1.mBuilder
                        com.amazonaws.kinesisvideo.common.function.Consumer r1 = r1.mCompletion
                        r1.accept(r0)
                    L_0x004d:
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r1 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this
                        java.util.concurrent.ExecutorService r1 = r1.payloadSender
                        r1.shutdownNow()
                        return
                    L_0x0058:
                        if (r0 == 0) goto L_0x0067
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r2 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient$Builder r2 = r2.mBuilder
                        com.amazonaws.kinesisvideo.common.function.Consumer r2 = r2.mCompletion
                        r2.accept(r0)
                    L_0x0067:
                        com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient r2 = com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.this
                        java.util.concurrent.ExecutorService r2 = r2.payloadSender
                        r2.shutdownNow()
                        throw r1
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient.AnonymousClass3.run():void");
                }
            });
        }
    }

    private void receiveResponseInBackground() {
        if (this.mBuilder.mReceiver != null) {
            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
            this.responseReceiver = newFixedThreadPool;
            newFixedThreadPool.execute(new Runnable() {
                public void run() {
                    Exception storedException = null;
                    try {
                        ParallelSimpleHttpClient.this.log.debug("Starting receiving data");
                        ParallelSimpleHttpClient.this.mBuilder.mReceiver.accept(ParallelSimpleHttpClient.this.mInputStream);
                        ParallelSimpleHttpClient.this.log.debug("Received all data, close");
                    } catch (Exception e) {
                        ParallelSimpleHttpClient.this.log.exception(e, "Exception thrown on receiving thread", new Object[0]);
                        storedException = e;
                    } catch (Throwable th) {
                        ParallelSimpleHttpClient.this.mBuilder.mCompletion.accept(null);
                        ParallelSimpleHttpClient.this.responseReceiver.shutdownNow();
                        ParallelSimpleHttpClient.this.closeSocket();
                        throw th;
                    }
                    ParallelSimpleHttpClient.this.mBuilder.mCompletion.accept(storedException);
                    ParallelSimpleHttpClient.this.responseReceiver.shutdownNow();
                    ParallelSimpleHttpClient.this.closeSocket();
                }
            });
        }
    }

    public void closeSocket() {
        try {
            this.mSocket.close();
            this.mInputStream.close();
            this.mOutputStream.close();
        } catch (Throwable e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while shutting down!", e);
        }
    }

    public void close() {
        this.payloadSender.shutdownNow();
        this.responseReceiver.shutdownNow();
        closeSocket();
        this.mBuilder.mCompletion.accept(null);
    }
}
