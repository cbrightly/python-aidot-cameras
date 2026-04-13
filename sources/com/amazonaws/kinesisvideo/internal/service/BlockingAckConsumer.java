package com.amazonaws.kinesisvideo.internal.service;

import androidx.annotation.NonNull;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.encoding.ChunkDecoder;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.internal.service.exception.AccessDeniedException;
import com.amazonaws.kinesisvideo.internal.service.exception.AmazonServiceException;
import com.amazonaws.kinesisvideo.internal.service.exception.ResourceNotFoundException;
import com.amazonaws.kinesisvideo.model.ResponseStatus;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BlockingAckConsumer implements Consumer<InputStream> {
    private static final int HTTP_ACCESS_DENIED = 403;
    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_OK = 200;
    private static final long RESPONSE_TIMEOUT_IN_MILLISECONDS = 10000;
    private final Consumer<InputStream> inputStreamConsumer;
    private KinesisVideoProducerStream kinesisVideoProducerStream;
    private Log log;
    private final CountDownLatch responseLatch = new CountDownLatch(1);
    private Exception storedException;

    /* JADX WARNING: type inference failed for: r3v0, types: [com.amazonaws.kinesisvideo.common.function.Consumer<java.io.InputStream>, java.lang.Object] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BlockingAckConsumer(@androidx.annotation.NonNull com.amazonaws.kinesisvideo.common.function.Consumer<java.io.InputStream> r3, com.amazonaws.kinesisvideo.common.logging.Log r4, @androidx.annotation.NonNull com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream r5) {
        /*
            r2 = this;
            r2.<init>()
            java.lang.Object r0 = com.amazonaws.kinesisvideo.common.preconditions.Preconditions.checkNotNull(r3)
            com.amazonaws.kinesisvideo.common.function.Consumer r0 = (com.amazonaws.kinesisvideo.common.function.Consumer) r0
            r2.inputStreamConsumer = r0
            java.util.concurrent.CountDownLatch r0 = new java.util.concurrent.CountDownLatch
            r1 = 1
            r0.<init>(r1)
            r2.responseLatch = r0
            r2.log = r4
            r2.kinesisVideoProducerStream = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.kinesisvideo.internal.service.BlockingAckConsumer.<init>(com.amazonaws.kinesisvideo.common.function.Consumer, com.amazonaws.kinesisvideo.common.logging.Log, com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream):void");
    }

    public void accept(@NonNull InputStream inputStream) {
        Preconditions.checkNotNull(inputStream);
        try {
            ResponseStatus responseStatus = ChunkDecoder.readStatusLine(inputStream);
            int responseCode = responseStatus.getStatusCode();
            switch (responseCode) {
                case 200:
                    this.log.debug(String.format("PutMedia call for stream %s return OK with request id %s", new Object[]{this.kinesisVideoProducerStream.getStreamName(), ChunkDecoder.decodeHeaders(inputStream)}));
                    break;
                case 400:
                    throw new AmazonServiceException("PutMedia call returned bad request: " + responseStatus.getReason());
                case 403:
                    throw new AccessDeniedException("Access is denied: " + responseStatus.getReason());
                case HTTP_NOT_FOUND /*404*/:
                    throw new ResourceNotFoundException("Resource not found: " + responseStatus.getReason());
                default:
                    throw new AmazonServiceException("PutMedia call returned status code " + responseCode + " with reason: " + responseStatus.getReason());
            }
        } catch (Exception e) {
            this.storedException = e;
        } catch (Throwable th) {
            this.responseLatch.countDown();
            throw th;
        }
        this.responseLatch.countDown();
        if (this.storedException == null) {
            this.inputStreamConsumer.accept(inputStream);
        }
    }

    public void awaitResponse() {
        try {
            if (this.responseLatch.await(10000, TimeUnit.MILLISECONDS)) {
                Exception exc = this.storedException;
                if (exc == null) {
                    return;
                }
                if (exc instanceof KinesisVideoException) {
                    throw ((KinesisVideoException) exc);
                }
                throw new KinesisVideoException((Throwable) this.storedException);
            }
            throw new KinesisVideoException("Getting PutMedia Response timed out");
        } catch (InterruptedException e) {
            throw new KinesisVideoException((Throwable) e);
        } catch (Throwable th) {
            Exception exc2 = this.storedException;
            if (exc2 == null) {
                throw th;
            } else if (exc2 instanceof KinesisVideoException) {
                throw ((KinesisVideoException) exc2);
            } else {
                throw new KinesisVideoException((Throwable) this.storedException);
            }
        }
    }
}
