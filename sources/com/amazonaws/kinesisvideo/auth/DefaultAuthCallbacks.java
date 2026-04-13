package com.amazonaws.kinesisvideo.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.producer.AuthCallbacks;
import com.amazonaws.kinesisvideo.producer.AuthInfo;
import com.amazonaws.kinesisvideo.producer.AuthInfoType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DefaultAuthCallbacks implements AuthCallbacks {
    public static final long CREDENTIALS_NEVER_EXPIRE = Long.MAX_VALUE;
    private static final int CREDENTIALS_UPDATE_TIMEOUT_MILLIS = 10000;
    /* access modifiers changed from: private */
    public final KinesisVideoCredentialsProvider credentialsProvider;
    private final ScheduledExecutorService executor;
    /* access modifiers changed from: private */
    public long expiration;
    /* access modifiers changed from: private */
    public final Log log;
    /* access modifiers changed from: private */
    public byte[] serializedCredentials;

    public DefaultAuthCallbacks(@NonNull KinesisVideoCredentialsProvider credentialsProvider2, @NonNull ScheduledExecutorService executor2, @NonNull Log log2) {
        this.credentialsProvider = (KinesisVideoCredentialsProvider) Preconditions.checkNotNull(credentialsProvider2);
        this.executor = (ScheduledExecutorService) Preconditions.checkNotNull(executor2);
        this.log = (Log) Preconditions.checkNotNull(log2);
    }

    @Nullable
    public AuthInfo getDeviceCertificate() {
        throw new RuntimeException("Certificate integration is not implemented");
    }

    @Nullable
    public AuthInfo getSecurityToken() {
        try {
            this.executor.schedule(new Runnable() {
                public void run() {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    try {
                        KinesisVideoCredentials credentials = DefaultAuthCallbacks.this.credentialsProvider.getUpdatedCredentials();
                        long unused = DefaultAuthCallbacks.this.expiration = credentials.getExpiration().getTime() * 10000;
                        ObjectOutput outputStream = new ObjectOutputStream(byteArrayOutputStream);
                        outputStream.writeObject(credentials);
                        outputStream.flush();
                        byte[] unused2 = DefaultAuthCallbacks.this.serializedCredentials = byteArrayOutputStream.toByteArray();
                        outputStream.close();
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e) {
                            DefaultAuthCallbacks.this.log.exception(e, "Closing the byte array stream threw an exception", new Object[0]);
                        }
                    } catch (IOException e2) {
                        byte[] unused3 = DefaultAuthCallbacks.this.serializedCredentials = null;
                        long unused4 = DefaultAuthCallbacks.this.expiration = 0;
                        DefaultAuthCallbacks.this.log.exception(e2, "Exception was thrown trying to get updated credentials", new Object[0]);
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e3) {
                            DefaultAuthCallbacks.this.log.exception(e3, "Closing the byte array stream threw an exception", new Object[0]);
                        }
                    } catch (KinesisVideoException e4) {
                        byte[] unused5 = DefaultAuthCallbacks.this.serializedCredentials = null;
                        long unused6 = DefaultAuthCallbacks.this.expiration = 0;
                        DefaultAuthCallbacks.this.log.exception(e4, "Exception was thrown trying to get updated credentials", new Object[0]);
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e5) {
                            DefaultAuthCallbacks.this.log.exception(e5, "Closing the byte array stream threw an exception", new Object[0]);
                        }
                    } catch (Throwable e6) {
                        try {
                            byteArrayOutputStream.close();
                        } catch (IOException e7) {
                            DefaultAuthCallbacks.this.log.exception(e7, "Closing the byte array stream threw an exception", new Object[0]);
                        }
                        throw e6;
                    }
                }
            }, 0, TimeUnit.NANOSECONDS).get(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            this.log.exception(e, "Awaiting for the credentials update threw an exception", new Object[0]);
        } catch (ExecutionException e2) {
            this.log.exception(e2, "Awaiting for the credentials update threw an exception", new Object[0]);
        } catch (TimeoutException e3) {
            this.log.exception(e3, "Awaiting for the credentials update threw an exception", new Object[0]);
        }
        return new AuthInfo(AuthInfoType.SECURITY_TOKEN, this.serializedCredentials, this.expiration);
    }

    @Nullable
    public String getDeviceFingerprint() {
        throw new RuntimeException("Provisioning is not implemented");
    }
}
