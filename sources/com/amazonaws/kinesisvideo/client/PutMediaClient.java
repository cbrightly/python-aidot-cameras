package com.amazonaws.kinesisvideo.client;

import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.encoding.ChunkEncoder;
import com.amazonaws.kinesisvideo.http.HttpMethodName;
import com.amazonaws.kinesisvideo.http.ParallelSimpleHttpClient;
import com.amazonaws.kinesisvideo.signing.KinesisVideoSigner;
import com.amazonaws.kinesisvideo.stream.throttling.BandwidthMeasuringOutputStream;
import com.amazonaws.kinesisvideo.stream.throttling.BandwidthThrottledOutputStream;
import com.amazonaws.kinesisvideo.stream.throttling.BandwidthThrottlerImpl;
import com.amazonaws.kinesisvideo.stream.throttling.OpsPerSecondMeasurer;
import com.amazonaws.kinesisvideo.util.VersionUtil;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class PutMediaClient {
    private static final long BITS_IN_A_KILOBIT = 1024;
    private static final int BUFFER_SIZE = 1048576;
    private static final double BYTES_IN_MB = 1048576.0d;
    private static final String CHUNKED = "chunked";
    private static final String CONNECTION = "connection";
    private static final String FRAGMENT_TIME_CODE_TYPE_HEADER = "x-amzn-fragment-timecode-type";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final int LOGGING_INTERVAL = 250;
    private static final double MILLI_TO_SEC = 1000.0d;
    private static final String PRODUCER_START_TIMESTAMP_HEADER = "x-amzn-producer-start-timestamp";
    private static final String STREAM_NAME_HEADER = "x-amzn-stream-name";
    private static final String TRANSFER_ENCODING = "Transfer-Encoding";
    private static final String USER_AGENT = "user-agent";
    private ParallelSimpleHttpClient httpClient;
    /* access modifiers changed from: private */
    public final Log log;
    /* access modifiers changed from: private */
    public final Builder mBuilder;

    private PutMediaClient(Builder builder) {
        this.mBuilder = builder;
        this.log = builder.mLog;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void putMediaInBackground() {
        putMediaWithSender(sendChunkEncodedMvkStream(0));
    }

    public void putMediaInBackgroundWithSleep(int sleepTime) {
        putMediaWithSender(sendChunkEncodedMvkStream(sleepTime));
    }

    private void putMediaWithSender(Consumer<OutputStream> sender) {
        ParallelSimpleHttpClient.Builder clientBuilder = ParallelSimpleHttpClient.builder().uri(this.mBuilder.mUri).method(HttpMethodName.POST).log(this.log).header(STREAM_NAME_HEADER, this.mBuilder.mStreamName).header("Transfer-Encoding", "chunked").header(CONNECTION, "keep-alive").header(USER_AGENT, VersionUtil.getUserAgent());
        clientBuilder.setReceiverCallback(this.mBuilder.mAcksReceiver);
        clientBuilder.header(PRODUCER_START_TIMESTAMP_HEADER, String.format(Locale.US, "%.3f", new Object[]{Double.valueOf(((double) this.mBuilder.mTimestamp) / MILLI_TO_SEC)}));
        clientBuilder.header(FRAGMENT_TIME_CODE_TYPE_HEADER, this.mBuilder.mFragmentTimecodeType);
        clientBuilder.completionCallback(this.mBuilder.mCompletion);
        clientBuilder.setSenderCallback(sender);
        clientBuilder.setTimeout(this.mBuilder.mReceiveTimeout);
        ParallelSimpleHttpClient build = clientBuilder.build();
        this.httpClient = build;
        sign(build);
        if (this.mBuilder.unsignedHeaders != null) {
            for (String headerName : this.mBuilder.unsignedHeaders.keySet()) {
                clientBuilder.header(headerName, (String) this.mBuilder.unsignedHeaders.get(headerName));
            }
        }
        this.httpClient.connectAndProcessInBackground();
    }

    public void close() {
        this.httpClient.close();
    }

    private void sign(ParallelSimpleHttpClient client) {
        if (this.mBuilder.mSigner != null) {
            this.mBuilder.mSigner.sign(client);
        }
    }

    private Consumer<OutputStream> sendChunkEncodedMvkStream(final int fragmentThrottle) {
        return new Consumer<OutputStream>() {
            public void accept(OutputStream rawOutputStream) {
                try {
                    OutputStream throttledOutputStream = PutMediaClient.this.throttleAndMeasureOutput(rawOutputStream);
                    FileOutputStream outputFileStream = PutMediaClient.this.createOutputFileStream();
                    byte[] buffer = new byte[1048576];
                    long counter = 0;
                    boolean continueLoop = true;
                    while (continueLoop) {
                        int mkvBytesRead = PutMediaClient.this.mBuilder.mMkvStream.read(buffer);
                        counter++;
                        if (counter % 250 == 0) {
                            Log access$1400 = PutMediaClient.this.log;
                            access$1400.debug("Sending data, counter : " + counter);
                        }
                        if (mkvBytesRead == -1) {
                            PutMediaClient.this.log.info("End-of-stream is reported. Terminating...");
                            continueLoop = false;
                        } else {
                            throttledOutputStream.write(ChunkEncoder.encode(buffer, mkvBytesRead));
                            PutMediaClient.this.tryWriteToFile(outputFileStream, buffer, mkvBytesRead);
                            int i = fragmentThrottle;
                            if (i > 0) {
                                Thread.sleep((long) i);
                            }
                        }
                    }
                    throttledOutputStream.write(ChunkEncoder.encode(buffer, 0));
                    rawOutputStream.flush();
                    Log access$14002 = PutMediaClient.this.log;
                    access$14002.debug("Data sent. counter : " + counter);
                    PutMediaClient.this.tryCloseOutputFileStream(outputFileStream);
                } catch (Exception e) {
                    PutMediaClient.this.log.debug("Exception while sending data.", e);
                    throw new RuntimeException("Exception while sending encoded chunk in MKV stream ! ", e);
                } catch (Throwable th) {
                    PutMediaClient.this.tryCloseOutputFileStream((FileOutputStream) null);
                    throw th;
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public OutputStream throttleAndMeasureOutput(OutputStream rawOutputStream) {
        OutputStream throttledOutputStream = throttleStream(rawOutputStream);
        return this.mBuilder.mLogUsedBandwidth ? logBytesPerSecond(throttledOutputStream) : throttledOutputStream;
    }

    private OutputStream throttleStream(OutputStream rawOutputStream) {
        if (this.mBuilder.upstreamKbps != null) {
            return new BandwidthThrottledOutputStream(rawOutputStream, new BandwidthThrottlerImpl(this.mBuilder.upstreamKbps.longValue() * 1024));
        }
        return rawOutputStream;
    }

    private OutputStream logBytesPerSecond(OutputStream outputStream) {
        return new BandwidthMeasuringOutputStream(outputStream, new OpsPerSecondMeasurer(logBytesPerSecond()));
    }

    private Consumer<Long> logBytesPerSecond() {
        return new Consumer<Long>() {
            public void accept(Long bytesWrittenPerSecond) {
                double megabitPerSecond = PutMediaClient.this.mbitPerSecond(bytesWrittenPerSecond.longValue());
                System.out.println(String.format("%n   ===> actual megabit/sec: %.2f mbps", new Object[]{Double.valueOf(megabitPerSecond)}));
            }
        };
    }

    /* access modifiers changed from: private */
    public FileOutputStream createOutputFileStream() {
        try {
            if (this.mBuilder.mFileOutputPath == null) {
                return null;
            }
            return new FileOutputStream(this.mBuilder.mFileOutputPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to open the file " + this.mBuilder.mFileOutputPath, e);
        }
    }

    /* access modifiers changed from: private */
    public void tryWriteToFile(FileOutputStream fileOutputStream, byte[] buffer, int bytesToWrite) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.write(buffer, 0, bytesToWrite);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public void tryCloseOutputFileStream(FileOutputStream outputFileStream) {
        if (outputFileStream != null) {
            try {
                outputFileStream.close();
            } catch (IOException e) {
                this.log.error(e.getMessage());
            }
        }
    }

    /* access modifiers changed from: private */
    public double mbitPerSecond(long bps) {
        return ((double) (8 * bps)) / BYTES_IN_MB;
    }

    public static class Builder {
        /* access modifiers changed from: private */
        public Consumer<InputStream> mAcksReceiver;
        /* access modifiers changed from: private */
        public Consumer<Exception> mCompletion;
        /* access modifiers changed from: private */
        public String mFileOutputPath;
        /* access modifiers changed from: private */
        public String mFragmentTimecodeType;
        /* access modifiers changed from: private */
        public Log mLog = new Log(Log.SYSTEM_OUT);
        /* access modifiers changed from: private */
        public boolean mLogUsedBandwidth;
        /* access modifiers changed from: private */
        public InputStream mMkvStream;
        /* access modifiers changed from: private */
        public Integer mReceiveTimeout;
        /* access modifiers changed from: private */
        public KinesisVideoSigner mSigner;
        /* access modifiers changed from: private */
        public String mStreamName;
        /* access modifiers changed from: private */
        public long mTimestamp;
        /* access modifiers changed from: private */
        public URI mUri;
        /* access modifiers changed from: private */
        public Map<String, String> unsignedHeaders;
        /* access modifiers changed from: private */
        public Long upstreamKbps;

        public Builder putMediaDestinationUri(URI uri) {
            this.mUri = uri;
            return this;
        }

        public Builder streamName(String streamName) {
            this.mStreamName = streamName;
            return this;
        }

        public Builder mkvStream(InputStream mkvStream) {
            this.mMkvStream = mkvStream;
            return this;
        }

        public Builder receiveAcks(Consumer<InputStream> acksReceiver) {
            this.mAcksReceiver = acksReceiver;
            return this;
        }

        public Builder receiveCompletion(Consumer<Exception> completion) {
            this.mCompletion = completion;
            return this;
        }

        public Builder timestamp(long timestamp) {
            this.mTimestamp = timestamp;
            return this;
        }

        public Builder signWith(KinesisVideoSigner signer) {
            this.mSigner = signer;
            return this;
        }

        public Builder fragmentTimecodeType(String fragmentTimecodeType) {
            this.mFragmentTimecodeType = fragmentTimecodeType;
            return this;
        }

        public Builder receiveTimeout(Integer timeout) {
            this.mReceiveTimeout = timeout;
            return this;
        }

        public Builder logUsedBandwidth(boolean logBandwidth) {
            this.mLogUsedBandwidth = logBandwidth;
            return this;
        }

        public Builder fileOutputPath(String fileOutputPath) {
            this.mFileOutputPath = fileOutputPath;
            return this;
        }

        public Builder upstreamKbps(long kbps) {
            this.upstreamKbps = Long.valueOf(kbps);
            return this;
        }

        public Builder log(Log log) {
            this.mLog = (Log) Preconditions.checkNotNull(log);
            return this;
        }

        public Builder unsignedHeader(String name, String value) {
            if (this.unsignedHeaders == null) {
                this.unsignedHeaders = new HashMap();
            }
            this.unsignedHeaders.put(name, value);
            return this;
        }

        public PutMediaClient build() {
            Preconditions.checkNotNull(this.mUri);
            Preconditions.checkNotNull(this.mStreamName);
            Preconditions.checkNotNull(this.mMkvStream);
            Preconditions.checkNotNull(this.mAcksReceiver);
            return new PutMediaClient(this);
        }
    }
}
