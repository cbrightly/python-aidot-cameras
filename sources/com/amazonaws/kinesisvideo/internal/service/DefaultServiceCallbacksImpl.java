package com.amazonaws.kinesisvideo.internal.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentials;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentialsProvider;
import com.amazonaws.kinesisvideo.auth.StaticCredentialsProvider;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.internal.producer.ServiceCallbacks;
import com.amazonaws.kinesisvideo.internal.producer.client.KinesisVideoServiceClient;
import com.amazonaws.kinesisvideo.producer.ProducerException;
import com.amazonaws.kinesisvideo.producer.StreamDescription;
import com.amazonaws.kinesisvideo.producer.Tag;
import com.amazonaws.kinesisvideo.producer.Time;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultServiceCallbacksImpl implements ServiceCallbacks {
    private static final String ACCESS_DENIED = "AccessDeniedException";
    private static final int HTTP_ACCESS_DENIED = 403;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_OK = 200;
    private static final int HTTP_RESOURCE_IN_USE = 10003;
    private static final String RESOURCE_IN_USE = "ResourceInUseException";
    private static final String RESOURCE_NOT_FOUND = "ResourceNotFoundException";
    /* access modifiers changed from: private */
    public final KinesisVideoClientConfiguration configuration;
    private final ScheduledExecutorService executor;
    /* access modifiers changed from: private */
    public KinesisVideoProducer kinesisVideoProducer = null;
    /* access modifiers changed from: private */
    public final KinesisVideoServiceClient kinesisVideoServiceClient;
    /* access modifiers changed from: private */
    public final Log log;
    /* access modifiers changed from: private */
    public final List<StreamingInfo> mStreams = new ArrayList();
    private long uploadHandle;

    public class CompletionCallback implements Consumer<Exception> {
        private final KinesisVideoProducerStream stream;
        private final long uploadHandle;

        public CompletionCallback(@NonNull KinesisVideoProducerStream stream2, long uploadHandle2) {
            this.stream = (KinesisVideoProducerStream) Preconditions.checkNotNull(stream2);
            this.uploadHandle = uploadHandle2;
        }

        public void accept(@Nullable Exception object) {
            int statusCode;
            if (this.stream.getStreamHandle() != 0 && (statusCode = DefaultServiceCallbacksImpl.getStatusCodeFromException(object)) != 200) {
                try {
                    this.stream.streamTerminated(this.uploadHandle, statusCode);
                } catch (ProducerException e) {
                    DefaultServiceCallbacksImpl.this.log.exception(e, "Reporting stream termination threw an exception", new Object[0]);
                }
            }
        }
    }

    public class StreamingInfo {
        private final KinesisVideoProducerStream stream;

        public StreamingInfo(@NonNull KinesisVideoProducerStream stream2) {
            this.stream = (KinesisVideoProducerStream) Preconditions.checkNotNull(stream2);
        }

        public void stop() {
            try {
                DefaultServiceCallbacksImpl.this.log.debug("Stopping the kinesis video producer stream");
                this.stream.stopStreamSync();
            } catch (ProducerException e) {
                DefaultServiceCallbacksImpl.this.log.exception(e, "Stopping stream threw an exception.", new Object[0]);
            }
        }

        public KinesisVideoProducerStream getStream() {
            return this.stream;
        }
    }

    public DefaultServiceCallbacksImpl(@NonNull Log log2, @NonNull ScheduledExecutorService executor2, @NonNull KinesisVideoClientConfiguration configuration2, @NonNull KinesisVideoServiceClient kinesisVideoServiceClient2) {
        this.executor = (ScheduledExecutorService) Preconditions.checkNotNull(executor2);
        KinesisVideoServiceClient kinesisVideoServiceClient3 = (KinesisVideoServiceClient) Preconditions.checkNotNull(kinesisVideoServiceClient2);
        this.kinesisVideoServiceClient = kinesisVideoServiceClient3;
        this.log = (Log) Preconditions.checkNotNull(log2);
        this.configuration = (KinesisVideoClientConfiguration) Preconditions.checkNotNull(configuration2);
        this.uploadHandle = 0;
        try {
            kinesisVideoServiceClient3.initialize(configuration2);
        } catch (KinesisVideoException e) {
            log2.exception(e);
        }
    }

    public void initialize(@NonNull KinesisVideoProducer kinesisVideoProducer2) {
        Preconditions.checkState(!isInitialized(), "Service callback object has already been initialized");
        this.kinesisVideoProducer = (KinesisVideoProducer) Preconditions.checkNotNull(kinesisVideoProducer2);
    }

    public boolean isInitialized() {
        return this.kinesisVideoProducer != null;
    }

    public void createStream(@NonNull String deviceName, @NonNull String streamName, @NonNull String contentType, @Nullable String kmsKeyId, long retentionPeriod, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        final byte[] bArr = authData;
        final long j = retentionPeriod;
        final long j2 = timeout;
        final String str = streamName;
        final String str2 = deviceName;
        final String str3 = contentType;
        final String str4 = kmsKeyId;
        long delay = calculateRelativeServiceCallAfter(callAfter);
        final long delay2 = customData;
        this.executor.schedule(new Runnable() {
            public void run() {
                int statusCode;
                String streamArn = null;
                try {
                    streamArn = DefaultServiceCallbacksImpl.this.kinesisVideoServiceClient.createStream(str, str2, str3, str4, j / 36000000000L, j2 / 10000, DefaultServiceCallbacksImpl.getCredentialsProvider(bArr, DefaultServiceCallbacksImpl.this.log));
                    statusCode = 200;
                } catch (KinesisVideoException e) {
                    statusCode = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                    DefaultServiceCallbacksImpl.this.log.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.createStreamResult(delay2, streamArn, statusCode);
                } catch (ProducerException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void describeStream(@NonNull String streamName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        long delay = calculateRelativeServiceCallAfter(callAfter);
        final byte[] bArr = authData;
        final long j = timeout;
        final String str = streamName;
        final long j2 = customData;
        this.executor.schedule(new Runnable() {
            public void run() {
                int statusCode;
                StreamDescription streamDescription = null;
                KinesisVideoCredentialsProvider credentialsProvider = DefaultServiceCallbacksImpl.getCredentialsProvider(bArr, DefaultServiceCallbacksImpl.this.log);
                try {
                    streamDescription = DefaultServiceCallbacksImpl.this.kinesisVideoServiceClient.describeStream(str, j / 10000, credentialsProvider);
                    statusCode = 200;
                } catch (KinesisVideoException e) {
                    int statusCode2 = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                    Log access$100 = DefaultServiceCallbacksImpl.this.log;
                    access$100.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                    statusCode = statusCode2;
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.describeStreamResult(j2, streamDescription, statusCode);
                } catch (ProducerException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void getStreamingEndpoint(@NonNull String streamName, @NonNull String apiName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        long delay = calculateRelativeServiceCallAfter(callAfter);
        final byte[] bArr = authData;
        final long j = timeout;
        final String str = streamName;
        final String str2 = apiName;
        final long j2 = customData;
        this.executor.schedule(new Runnable() {
            public void run() {
                KinesisVideoCredentialsProvider credentialsProvider = DefaultServiceCallbacksImpl.getCredentialsProvider(bArr, DefaultServiceCallbacksImpl.this.log);
                int statusCode = 200;
                String endpoint = "";
                try {
                    endpoint = DefaultServiceCallbacksImpl.this.kinesisVideoServiceClient.getDataEndpoint(str, str2, j / 10000, credentialsProvider);
                } catch (KinesisVideoException e) {
                    Log access$100 = DefaultServiceCallbacksImpl.this.log;
                    access$100.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                    statusCode = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                }
                if (statusCode != 200 && DefaultServiceCallbacksImpl.isBlank(endpoint)) {
                    statusCode = DefaultServiceCallbacksImpl.HTTP_NOT_FOUND;
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.getStreamingEndpointResult(j2, endpoint, statusCode);
                } catch (ProducerException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void getStreamingToken(@NonNull String streamName, long callAfter, long timeout, @Nullable byte[] authData, int authType, final long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        long delay = calculateRelativeServiceCallAfter(callAfter);
        this.executor.schedule(new Runnable() {
            public void run() {
                KinesisVideoCredentialsProvider credentialsProvider = DefaultServiceCallbacksImpl.this.configuration.getCredentialsProvider();
                byte[] serializedCredentials = null;
                long expiration = 0;
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    KinesisVideoCredentials credentials = credentialsProvider.getUpdatedCredentials();
                    expiration = credentials.getExpiration().getTime() * 10000;
                    ObjectOutput outputStream = new ObjectOutputStream(byteArrayOutputStream);
                    outputStream.writeObject(credentials);
                    outputStream.flush();
                    serializedCredentials = byteArrayOutputStream.toByteArray();
                    outputStream.close();
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e) {
                    }
                } catch (IOException e2) {
                    DefaultServiceCallbacksImpl.this.log.exception(e2);
                    byteArrayOutputStream.close();
                } catch (KinesisVideoException e3) {
                    DefaultServiceCallbacksImpl.this.log.exception(e3);
                    byteArrayOutputStream.close();
                } catch (Throwable th) {
                    try {
                        byteArrayOutputStream.close();
                    } catch (IOException e4) {
                    }
                    throw th;
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.getStreamingTokenResult(customData, serializedCredentials, expiration, 200);
                } catch (ProducerException e5) {
                    throw new RuntimeException(e5);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void putStream(@NonNull String streamName, @NonNull String containerType, long streamStartTime, boolean absoluteFragmentTimes, boolean ackRequired, @NonNull String dataEndpoint, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        final long j = customData;
        final long j2 = timeout;
        final long j3 = streamStartTime;
        final byte[] bArr = authData;
        final String str = streamName;
        long delay = calculateRelativeServiceCallAfter(callAfter);
        final String str2 = containerType;
        final boolean z = absoluteFragmentTimes;
        final boolean z2 = ackRequired;
        final String str3 = dataEndpoint;
        this.executor.schedule(new Runnable() {
            public void run() {
                KinesisVideoProducerStream kinesisVideoProducerStream;
                int statusCode;
                long streamStartTimeInMillis;
                Iterator it = DefaultServiceCallbacksImpl.this.mStreams.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        kinesisVideoProducerStream = null;
                        break;
                    }
                    StreamingInfo streamingInfo = (StreamingInfo) it.next();
                    if (streamingInfo.getStream().getStreamHandle() == j) {
                        kinesisVideoProducerStream = streamingInfo.getStream();
                        break;
                    }
                }
                if (kinesisVideoProducerStream != null) {
                    long timeoutInMillis = j2 / 10000;
                    long streamStartTimeInMillis2 = j3 / 10000;
                    KinesisVideoCredentialsProvider credentialsProvider = DefaultServiceCallbacksImpl.getCredentialsProvider(bArr, DefaultServiceCallbacksImpl.this.log);
                    long clientUploadHandle = DefaultServiceCallbacksImpl.this.getUploadHandle();
                    try {
                        InputStream dataStream = kinesisVideoProducerStream.getDataStream(clientUploadHandle);
                        BlockingAckConsumer blockingAckConsumer = new BlockingAckConsumer(new AckConsumer(clientUploadHandle, kinesisVideoProducerStream, DefaultServiceCallbacksImpl.this.log), DefaultServiceCallbacksImpl.this.log, kinesisVideoProducerStream);
                        long j = streamStartTimeInMillis2;
                        long j2 = streamStartTimeInMillis2;
                        streamStartTimeInMillis = clientUploadHandle;
                        try {
                            DefaultServiceCallbacksImpl.this.kinesisVideoServiceClient.putMedia(str, str2, j, z, z2, str3, timeoutInMillis, credentialsProvider, dataStream, blockingAckConsumer, new CompletionCallback(kinesisVideoProducerStream, clientUploadHandle));
                            blockingAckConsumer.awaitResponse();
                            statusCode = 200;
                        } catch (KinesisVideoException e) {
                            e = e;
                            int statusCode2 = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                            DefaultServiceCallbacksImpl.this.log.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                            statusCode = statusCode2;
                            DefaultServiceCallbacksImpl.this.log.info("putStreamResult uploadHandle " + streamStartTimeInMillis + " status " + statusCode);
                            DefaultServiceCallbacksImpl.this.kinesisVideoProducer.putStreamResult(j, streamStartTimeInMillis, statusCode);
                        }
                    } catch (KinesisVideoException e2) {
                        e = e2;
                        long j3 = streamStartTimeInMillis2;
                        streamStartTimeInMillis = clientUploadHandle;
                        int statusCode22 = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                        DefaultServiceCallbacksImpl.this.log.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                        statusCode = statusCode22;
                        DefaultServiceCallbacksImpl.this.log.info("putStreamResult uploadHandle " + streamStartTimeInMillis + " status " + statusCode);
                        DefaultServiceCallbacksImpl.this.kinesisVideoProducer.putStreamResult(j, streamStartTimeInMillis, statusCode);
                    }
                    try {
                        DefaultServiceCallbacksImpl.this.log.info("putStreamResult uploadHandle " + streamStartTimeInMillis + " status " + statusCode);
                        DefaultServiceCallbacksImpl.this.kinesisVideoProducer.putStreamResult(j, streamStartTimeInMillis, statusCode);
                    } catch (ProducerException e3) {
                        throw new RuntimeException(e3);
                    }
                } else {
                    throw new IllegalStateException("Couldn't find the correct stream");
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void tagResource(@NonNull String resourceArn, @Nullable Tag[] tags, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        long delay = calculateRelativeServiceCallAfter(callAfter);
        final byte[] bArr = authData;
        final long j = timeout;
        final Tag[] tagArr = tags;
        final String str = resourceArn;
        final long j2 = customData;
        this.executor.schedule(new Runnable() {
            public void run() {
                Map<String, String> tagsMap;
                KinesisVideoCredentialsProvider credentialsProvider = DefaultServiceCallbacksImpl.getCredentialsProvider(bArr, DefaultServiceCallbacksImpl.this.log);
                long timeoutInMillis = j / 10000;
                int statusCode = 200;
                if (tagArr != null) {
                    Map<String, String> tagsMap2 = new HashMap<>(tagArr.length);
                    for (Tag tag : tagArr) {
                        tagsMap2.put(tag.getName(), tag.getValue());
                    }
                    tagsMap = tagsMap2;
                } else {
                    tagsMap = null;
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoServiceClient.tagStream(str, tagsMap, timeoutInMillis, credentialsProvider);
                } catch (KinesisVideoException e) {
                    DefaultServiceCallbacksImpl.this.log.error("Kinesis Video service client returned an error " + e.getMessage() + ". Reporting to Kinesis Video PIC.");
                    statusCode = DefaultServiceCallbacksImpl.getStatusCodeFromException(e);
                }
                if (statusCode != 200) {
                    statusCode = 400;
                }
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.tagResourceResult(j2, statusCode);
                } catch (ProducerException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void createDevice(@NonNull final String deviceName, long callAfter, long timeout, @Nullable byte[] authData, int authType, final long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        long delay = calculateRelativeServiceCallAfter(callAfter);
        this.executor.schedule(new Runnable() {
            public void run() {
                try {
                    DefaultServiceCallbacksImpl.this.kinesisVideoProducer.createDeviceResult(customData, deviceName + "_ARN", 200);
                } catch (ProducerException e) {
                    throw new RuntimeException(e);
                }
            }
        }, delay, TimeUnit.NANOSECONDS);
    }

    public void deviceCertToToken(@NonNull String deviceName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        Preconditions.checkState(isInitialized(), "Service callbacks object should be initialized first");
        this.kinesisVideoProducer.deviceCertToTokenResult(customData, (byte[]) null, 0, 400);
    }

    public synchronized void free() {
        for (StreamingInfo streamingInfo : this.mStreams) {
            streamingInfo.stop();
        }
        this.mStreams.clear();
        this.executor.shutdownNow();
    }

    public synchronized void addStream(@NonNull KinesisVideoProducerStream kinesisVideoProducerStream) {
        this.mStreams.add(new StreamingInfo(kinesisVideoProducerStream));
    }

    public synchronized void removeStream(@NonNull KinesisVideoProducerStream kinesisVideoProducerStream) {
        StreamingInfo streamingInfoToBeRemoved = null;
        Iterator<StreamingInfo> it = this.mStreams.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            StreamingInfo streamingInfo = it.next();
            if (streamingInfo.getStream() == kinesisVideoProducerStream) {
                streamingInfoToBeRemoved = streamingInfo;
                break;
            }
        }
        if (streamingInfoToBeRemoved != null) {
            this.mStreams.remove(streamingInfoToBeRemoved);
        }
    }

    private long calculateRelativeServiceCallAfter(long absoluteCallAfter) {
        return Math.max(0, (100 * absoluteCallAfter) - (System.currentTimeMillis() * Time.NANOS_IN_A_MILLISECOND));
    }

    /* access modifiers changed from: private */
    public long getUploadHandle() {
        long j = this.uploadHandle;
        this.uploadHandle = 1 + j;
        return j;
    }

    /* access modifiers changed from: private */
    public static boolean isBlank(CharSequence cs) {
        if (cs != null) {
            int length = cs.length();
            int strLen = length;
            if (length != 0) {
                for (int i = 0; i < strLen; i++) {
                    if (!Character.isWhitespace(cs.charAt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    @Nullable
    public static KinesisVideoCredentialsProvider getCredentialsProvider(@Nullable byte[] authData, @NonNull Log log2) {
        if (authData == null) {
            log2.warn("NULL credentials have been returned by the credentials provider.");
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(authData);
        try {
            ObjectInput objectInput = new ObjectInputStream(byteArrayInputStream);
            KinesisVideoCredentials credentials = (KinesisVideoCredentials) objectInput.readObject();
            objectInput.close();
            try {
                byteArrayInputStream.close();
            } catch (IOException e) {
                log2.exception(e);
            }
            return new StaticCredentialsProvider(credentials);
        } catch (IOException e2) {
            log2.exception(e2);
            try {
                byteArrayInputStream.close();
            } catch (IOException e3) {
                log2.exception(e3);
            }
            return null;
        } catch (ClassNotFoundException e4) {
            log2.exception(e4);
            try {
                byteArrayInputStream.close();
            } catch (IOException e5) {
                log2.exception(e5);
            }
            return null;
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (IOException e6) {
                log2.exception(e6);
            }
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public static int getStatusCodeFromException(@Nullable Throwable e) {
        if (e == null) {
            return 200;
        }
        if (e.getClass().getName().endsWith(RESOURCE_NOT_FOUND)) {
            return HTTP_NOT_FOUND;
        }
        if (e.getClass().getName().endsWith(RESOURCE_IN_USE)) {
            return 10003;
        }
        if (e.getClass().getName().endsWith(ACCESS_DENIED)) {
            return 403;
        }
        Throwable cause = e.getCause();
        if (cause != null) {
            return getStatusCodeFromException(cause);
        }
        return 400;
    }
}
