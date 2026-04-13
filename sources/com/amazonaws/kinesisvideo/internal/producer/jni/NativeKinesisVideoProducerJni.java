package com.amazonaws.kinesisvideo.internal.producer.jni;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.logging.LogLevel;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoMetrics;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducer;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoStreamMetrics;
import com.amazonaws.kinesisvideo.internal.producer.ReadResult;
import com.amazonaws.kinesisvideo.internal.producer.ServiceCallbacks;
import com.amazonaws.kinesisvideo.producer.AuthCallbacks;
import com.amazonaws.kinesisvideo.producer.AuthInfo;
import com.amazonaws.kinesisvideo.producer.DeviceInfo;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFragmentAck;
import com.amazonaws.kinesisvideo.producer.KinesisVideoFrame;
import com.amazonaws.kinesisvideo.producer.ProducerException;
import com.amazonaws.kinesisvideo.producer.StorageCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamCallbacks;
import com.amazonaws.kinesisvideo.producer.StreamDescription;
import com.amazonaws.kinesisvideo.producer.StreamInfo;
import com.amazonaws.kinesisvideo.producer.Tag;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class NativeKinesisVideoProducerJni implements KinesisVideoProducer {
    private static final String EXPECTED_LIBRARY_VERSION = "1.8";
    public static final long INVALID_CLIENT_HANDLE_VALUE = 0;
    public static final long INVALID_STREAM_HANDLE_VALUE = 0;
    private static final String PRODUCER_NATIVE_LIBRARY_NAME = "KinesisVideoProducerJNI";
    private final AuthCallbacks mAuthCallbacks;
    private final Object mCallbackSyncObject;
    private long mClientHandle;
    private volatile boolean mIsReady;
    private final Map<Long, KinesisVideoProducerStream> mKinesisVideoHandleMap;
    private final KinesisVideoMetrics mKinesisVideoMetrics;
    private boolean mLibraryInitialized;
    private final NativeLibraryLoader mLibraryLoader;
    private final Log mLog;
    private final CountDownLatch mReadyLatch;
    private final ServiceCallbacks mServiceCallbacks;
    private final StorageCallbacks mStorageCallbacks;
    private final Object mSyncObject;

    private native void createDeviceResultEvent(long j, long j2, int i, @Nullable String str);

    private native long createKinesisVideoClient(@NonNull DeviceInfo deviceInfo);

    private native long createKinesisVideoStream(long j, @NonNull StreamInfo streamInfo);

    private native void createStreamResultEvent(long j, long j2, int i, @Nullable String str);

    private native void describeStreamResultEvent(long j, long j2, int i, @Nullable StreamDescription streamDescription);

    private native void deviceCertToTokenResultEvent(long j, long j2, int i, @Nullable byte[] bArr, int i2, long j3);

    private native void freeKinesisVideoClient(long j);

    private native void freeKinesisVideoStream(long j, long j2);

    private native void getKinesisVideoMetrics(long j, @NonNull KinesisVideoMetrics kinesisVideoMetrics);

    private native void getKinesisVideoStreamData(long j, long j2, @NonNull byte[] bArr, int i, int i2, @NonNull ReadResult readResult);

    private native void getKinesisVideoStreamMetrics(long j, long j2, @NonNull KinesisVideoStreamMetrics kinesisVideoStreamMetrics);

    private native String getNativeCodeCompileTime();

    private native String getNativeLibraryVersion();

    private native void getStreamingEndpointResultEvent(long j, long j2, int i, @Nullable String str);

    private native void getStreamingTokenResultEvent(long j, long j2, int i, @Nullable byte[] bArr, int i2, long j3);

    private native void kinesisVideoStreamFormatChanged(long j, long j2, @Nullable byte[] bArr);

    private native void kinesisVideoStreamFragmentAck(long j, long j2, long j3, @NonNull KinesisVideoFragmentAck kinesisVideoFragmentAck);

    private native void kinesisVideoStreamParseFragmentAck(long j, long j2, long j3, @NonNull String str);

    private native void kinesisVideoStreamTerminated(long j, long j2, long j3, int i);

    private native void putKinesisVideoFragmentMetadata(long j, long j2, @NonNull String str, @NonNull String str2, boolean z);

    private native void putKinesisVideoFrame(long j, long j2, @NonNull KinesisVideoFrame kinesisVideoFrame);

    private native void putStreamResultEvent(long j, long j2, int i, long j3);

    private native void stopKinesisVideoStream(long j, long j2);

    private native void stopKinesisVideoStreams(long j);

    private native void tagResourceResultEvent(long j, long j2, int i);

    public NativeKinesisVideoProducerJni(@NonNull AuthCallbacks authCallbacks, @NonNull StorageCallbacks storageCallbacks, @NonNull ServiceCallbacks serviceCallbacks) {
        this(authCallbacks, storageCallbacks, serviceCallbacks, new Log(Log.SYSTEM_OUT, LogLevel.VERBOSE, "Producer JNI"));
    }

    public NativeKinesisVideoProducerJni(@NonNull AuthCallbacks authCallbacks, @NonNull StorageCallbacks storageCallbacks, @NonNull ServiceCallbacks serviceCallbacks, @NonNull Log log) {
        this(authCallbacks, storageCallbacks, serviceCallbacks, log, new CountDownLatch(1));
    }

    public NativeKinesisVideoProducerJni(@NonNull AuthCallbacks authCallbacks, @NonNull StorageCallbacks storageCallbacks, @NonNull ServiceCallbacks serviceCallbacks, @NonNull Log log, @NonNull CountDownLatch readyLatch) {
        this.mClientHandle = 0;
        this.mLibraryInitialized = false;
        this.mIsReady = false;
        this.mSyncObject = new Object();
        this.mCallbackSyncObject = new Object();
        this.mKinesisVideoHandleMap = new HashMap();
        Log log2 = (Log) Preconditions.checkNotNull(log);
        this.mLog = log2;
        this.mAuthCallbacks = (AuthCallbacks) Preconditions.checkNotNull(authCallbacks);
        this.mStorageCallbacks = (StorageCallbacks) Preconditions.checkNotNull(storageCallbacks);
        ServiceCallbacks serviceCallbacks2 = (ServiceCallbacks) Preconditions.checkNotNull(serviceCallbacks);
        this.mServiceCallbacks = serviceCallbacks2;
        this.mReadyLatch = (CountDownLatch) Preconditions.checkNotNull(readyLatch);
        this.mLibraryLoader = new NativeLibraryLoader(log2);
        serviceCallbacks2.initialize(this);
        this.mKinesisVideoMetrics = new KinesisVideoMetrics();
    }

    /* access modifiers changed from: protected */
    public void finalize() {
        if (isInitialized()) {
            free();
        }
    }

    public void create(@NonNull DeviceInfo deviceInfo) {
        create(deviceInfo, "");
    }

    public void createSync(@NonNull DeviceInfo deviceInfo) {
        createSync(deviceInfo, "");
    }

    public void create(@NonNull DeviceInfo deviceInfo, @NonNull String nativeLibraryPath) {
        Preconditions.checkNotNull(deviceInfo);
        Preconditions.checkState(!isInitialized());
        synchronized (this.mSyncObject) {
            if (!this.mLibraryInitialized) {
                initializeLibrary(nativeLibraryPath);
                this.mLibraryInitialized = true;
            }
            this.mClientHandle = createKinesisVideoClient(deviceInfo);
        }
    }

    public void createSync(@NonNull DeviceInfo deviceInfo, @NonNull String nativeLibraryPath) {
        create(deviceInfo, nativeLibraryPath);
        try {
            if (!this.mReadyLatch.await(KinesisVideoProducer.READY_TIMEOUT_IN_MILLISECONDS, TimeUnit.MILLISECONDS)) {
                throw new ProducerException("Kinesis Video producer creation time out", 0);
            }
        } catch (InterruptedException ex) {
            throw new ProducerException(ex);
        }
    }

    public boolean isInitialized() {
        boolean z;
        synchronized (this.mSyncObject) {
            z = this.mClientHandle != 0;
        }
        return z;
    }

    public boolean isReady() {
        boolean z;
        synchronized (this.mSyncObject) {
            z = this.mIsReady;
        }
        return z;
    }

    @NonNull
    public KinesisVideoMetrics getMetrics() {
        KinesisVideoMetrics kinesisVideoMetrics;
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            getKinesisVideoMetrics(this.mClientHandle, this.mKinesisVideoMetrics);
            kinesisVideoMetrics = this.mKinesisVideoMetrics;
        }
        return kinesisVideoMetrics;
    }

    public void free() {
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            freeKinesisVideoClient(this.mClientHandle);
            this.mClientHandle = 0;
        }
    }

    public KinesisVideoProducerStream createStream(@NonNull StreamInfo streamInfo, @Nullable StreamCallbacks streamCallbacks) {
        NativeKinesisVideoProducerStream nativeKinesisVideoProducerStream;
        Preconditions.checkNotNull(streamInfo);
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            long streamHandle = createKinesisVideoStream(this.mClientHandle, streamInfo);
            nativeKinesisVideoProducerStream = new NativeKinesisVideoProducerStream(this, streamInfo, streamHandle, this.mLog, streamCallbacks);
            this.mKinesisVideoHandleMap.put(Long.valueOf(streamHandle), nativeKinesisVideoProducerStream);
        }
        return nativeKinesisVideoProducerStream;
    }

    public KinesisVideoProducerStream createStreamSync(@NonNull StreamInfo streamInfo, @Nullable StreamCallbacks streamCallbacks) {
        NativeKinesisVideoProducerStream stream = (NativeKinesisVideoProducerStream) createStream(streamInfo, streamCallbacks);
        stream.awaitReady();
        return stream;
    }

    public void stopStreams() {
        if (isInitialized()) {
            synchronized (this.mSyncObject) {
                stopKinesisVideoStreams(this.mClientHandle);
            }
        }
    }

    public void stopStream(long streamHandle) {
        if (isInitialized()) {
            synchronized (this.mSyncObject) {
                stopKinesisVideoStream(this.mClientHandle, streamHandle);
            }
        }
    }

    public void freeStreams() {
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            for (KinesisVideoProducerStream stream : this.mKinesisVideoHandleMap.values()) {
                this.mKinesisVideoHandleMap.remove(Long.valueOf(stream.getStreamHandle()));
                freeStream(stream);
            }
        }
    }

    public void freeStream(@NonNull KinesisVideoProducerStream stream) {
        Preconditions.checkNotNull(stream);
        if (isInitialized()) {
            synchronized (this.mSyncObject) {
                freeKinesisVideoStream(this.mClientHandle, stream.getStreamHandle());
            }
        }
    }

    public void streamTerminated(long streamHandle, long uploadHandle, int statusCode) {
        if (isInitialized()) {
            synchronized (this.mSyncObject) {
                kinesisVideoStreamTerminated(this.mClientHandle, streamHandle, uploadHandle, statusCode);
            }
        }
    }

    public void getStreamMetrics(long streamHandle, @NonNull KinesisVideoStreamMetrics streamMetrics) {
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            getKinesisVideoStreamMetrics(this.mClientHandle, streamHandle, streamMetrics);
        }
    }

    public void putFrame(long streamHandle, @NonNull KinesisVideoFrame kinesisVideoFrameFrame) {
        Preconditions.checkState(isInitialized());
        Preconditions.checkNotNull(kinesisVideoFrameFrame);
        synchronized (this.mSyncObject) {
            putKinesisVideoFrame(this.mClientHandle, streamHandle, kinesisVideoFrameFrame);
        }
    }

    public void putFragmentMetadata(long streamHandle, @NonNull String metadataName, @NonNull String metadataValue, boolean persistent) {
        Preconditions.checkState(isInitialized());
        Preconditions.checkNotNull(metadataName);
        Preconditions.checkNotNull(metadataValue);
        synchronized (this.mSyncObject) {
            putKinesisVideoFragmentMetadata(this.mClientHandle, streamHandle, metadataName, metadataValue, persistent);
        }
    }

    public void fragmentAck(long streamHandle, long uploadHandle, @NonNull KinesisVideoFragmentAck kinesisVideoFragmentAck) {
        Preconditions.checkState(isInitialized());
        Preconditions.checkNotNull(kinesisVideoFragmentAck);
        synchronized (this.mSyncObject) {
            kinesisVideoStreamFragmentAck(this.mClientHandle, streamHandle, uploadHandle, kinesisVideoFragmentAck);
        }
    }

    public void parseFragmentAck(long streamHandle, long uploadHandle, @NonNull String kinesisVideoFragmentAck) {
        Preconditions.checkState(isInitialized());
        Preconditions.checkNotNull(kinesisVideoFragmentAck);
        synchronized (this.mSyncObject) {
            kinesisVideoStreamParseFragmentAck(this.mClientHandle, streamHandle, uploadHandle, kinesisVideoFragmentAck);
        }
    }

    public void getStreamData(long streamHandle, @NonNull byte[] fillBuffer, int offset, int length, @NonNull ReadResult readResult) {
        Preconditions.checkState(isInitialized());
        Preconditions.checkNotNull(fillBuffer);
        Preconditions.checkNotNull(readResult);
        synchronized (this.mSyncObject) {
            getKinesisVideoStreamData(this.mClientHandle, streamHandle, fillBuffer, offset, length, readResult);
        }
    }

    public void streamFormatChanged(long streamHandle, @Nullable byte[] codecPrivateData) {
        Preconditions.checkState(isInitialized());
        synchronized (this.mSyncObject) {
            kinesisVideoStreamFormatChanged(this.mClientHandle, streamHandle, codecPrivateData);
        }
    }

    @Nullable
    private AuthInfo getDeviceCertificate() {
        AuthInfo deviceCertificate;
        synchronized (this.mCallbackSyncObject) {
            deviceCertificate = this.mAuthCallbacks.getDeviceCertificate();
        }
        return deviceCertificate;
    }

    @Nullable
    private AuthInfo getSecurityToken() {
        AuthInfo securityToken;
        synchronized (this.mCallbackSyncObject) {
            securityToken = this.mAuthCallbacks.getSecurityToken();
        }
        return securityToken;
    }

    @Nullable
    private String getDeviceFingerprint() {
        String deviceFingerprint;
        synchronized (this.mCallbackSyncObject) {
            deviceFingerprint = this.mAuthCallbacks.getDeviceFingerprint();
        }
        return deviceFingerprint;
    }

    private void streamUnderflowReport(long streamHandle) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamUnderflowReport();
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void storageOverflowPressure(long remainingSize) {
        synchronized (this.mCallbackSyncObject) {
            this.mStorageCallbacks.storageOverflowPressure(remainingSize);
        }
    }

    private void streamLatencyPressure(long streamHandle, long duration) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamLatencyPressure(duration);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void streamConnectionStale(long streamHandle, long lastAckDuration) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamConnectionStale(lastAckDuration);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void fragmentAckReceived(long streamHandle, @NonNull KinesisVideoFragmentAck fragmentAck) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).fragmentAckReceived(fragmentAck);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void droppedFrameReport(long streamHandle, long frameTimecode) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).droppedFrameReport(frameTimecode);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void droppedFragmentReport(long streamHandle, long fragmentTimecode) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).droppedFragmentReport(fragmentTimecode);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void streamErrorReport(long streamHandle, long fragmentTimecode, long statusCode) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamErrorReport(fragmentTimecode, statusCode);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void streamDataAvailable(long streamHandle, String streamName, long uploadHandle, long duration, long availableSize) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamDataAvailable(uploadHandle, duration, availableSize);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void streamReady(long streamHandle) {
        synchronized (this.mCallbackSyncObject) {
            synchronized (this.mSyncObject) {
                if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                    this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamReady();
                } else {
                    throw new ProducerException("Invalid stream handle.", 13);
                }
            }
        }
    }

    private void streamClosed(long streamHandle, long uploadHandle) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mKinesisVideoHandleMap.containsKey(Long.valueOf(streamHandle))) {
                this.mKinesisVideoHandleMap.get(Long.valueOf(streamHandle)).streamClosed(uploadHandle);
            } else {
                throw new ProducerException("Invalid stream handle.", 13);
            }
        }
    }

    private void clientReady(long clientHandle) {
        synchronized (this.mCallbackSyncObject) {
            if (this.mClientHandle == clientHandle) {
                this.mIsReady = true;
                this.mReadyLatch.countDown();
            } else {
                throw new ProducerException("Invalid client handle.", 13);
            }
        }
    }

    private int createStream(@NonNull String deviceName, @NonNull String streamName, @NonNull String contentType, @Nullable String kmsKeyId, long retentionPeriod, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.createStream(deviceName, streamName, contentType, kmsKeyId, retentionPeriod, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void createStreamResult(long customData, @Nullable String streamArn, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            createStreamResultEvent(this.mClientHandle, customData, httpStatusCode, streamArn);
        }
    }

    private int describeStream(@NonNull String streamName, long callAfter, long timeout, byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.describeStream(streamName, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void describeStreamResult(long customData, @Nullable StreamDescription streamDescription, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            describeStreamResultEvent(this.mClientHandle, customData, httpStatusCode, streamDescription);
        }
    }

    private int getStreamingEndpoint(@NonNull String streamName, @NonNull String apiName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.getStreamingEndpoint(streamName, apiName, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void getStreamingEndpointResult(long customData, @Nullable String endpoint, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            getStreamingEndpointResultEvent(this.mClientHandle, customData, httpStatusCode, endpoint);
        }
    }

    private int getStreamingToken(@NonNull String streamName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.getStreamingToken(streamName, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void getStreamingTokenResult(long customData, @Nullable byte[] token, long expiration, int httpStatusCode) {
        byte[] bArr = token;
        synchronized (this.mSyncObject) {
            getStreamingTokenResultEvent(this.mClientHandle, customData, httpStatusCode, token, bArr == null ? 0 : bArr.length, expiration);
        }
    }

    private int putStream(@NonNull String streamName, @NonNull String containerType, long streamStartTime, boolean absoluteFragmentTimes, boolean ackRequired, @NonNull String streamingEndpoint, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.putStream(streamName, containerType, streamStartTime, absoluteFragmentTimes, ackRequired, streamingEndpoint, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void putStreamResult(long customData, long clientStreamHandle, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            putStreamResultEvent(this.mClientHandle, customData, httpStatusCode, clientStreamHandle);
        }
    }

    private int tagResource(@NonNull String resourceArn, @NonNull Tag[] tags, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.tagResource(resourceArn, tags, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void tagResourceResult(long customData, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            tagResourceResultEvent(this.mClientHandle, customData, httpStatusCode);
        }
    }

    private int createDevice(@NonNull String deviceName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.createDevice(deviceName, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void createDeviceResult(long customData, @Nullable String deviceArm, int httpStatusCode) {
        synchronized (this.mSyncObject) {
            createDeviceResultEvent(this.mClientHandle, customData, httpStatusCode, deviceArm);
        }
    }

    private int deviceCertToToken(@NonNull String deviceName, long callAfter, long timeout, @Nullable byte[] authData, int authType, long customData) {
        synchronized (this.mCallbackSyncObject) {
            try {
                this.mServiceCallbacks.deviceCertToToken(deviceName, callAfter, timeout, authData, authType, customData);
            } catch (ProducerException e) {
                return e.getStatusCode();
            } catch (Throwable th) {
                throw th;
            }
        }
        return 0;
    }

    public void deviceCertToTokenResult(long customData, @Nullable byte[] token, long expiration, int httpStatusCode) {
        byte[] bArr = token;
        synchronized (this.mSyncObject) {
            deviceCertToTokenResultEvent(this.mClientHandle, customData, httpStatusCode, token, bArr == null ? 0 : bArr.length, expiration);
        }
    }

    private void initializeLibrary(@NonNull String nativeLibraryPath) {
        if (this.mLibraryLoader.loadNativeLibrary(nativeLibraryPath, PRODUCER_NATIVE_LIBRARY_NAME)) {
            String libraryVersion = getNativeLibraryVersion();
            String compileTime = getNativeCodeCompileTime();
            this.mLog.verbose("%s library: version %s, compile time %s", PRODUCER_NATIVE_LIBRARY_NAME, libraryVersion, compileTime);
            Preconditions.checkState(libraryVersion.equals(EXPECTED_LIBRARY_VERSION), String.format("FATAL DEPLOYMENT ERROR: This app is built to run with version %s of the lib%s.so library, but version %s was found on this device", new Object[]{EXPECTED_LIBRARY_VERSION, PRODUCER_NATIVE_LIBRARY_NAME, libraryVersion}));
            return;
        }
        throw new ProducerException("Failed loading native library", 13);
    }
}
