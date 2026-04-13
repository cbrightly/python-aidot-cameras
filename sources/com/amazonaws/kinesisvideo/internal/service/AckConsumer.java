package com.amazonaws.kinesisvideo.internal.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.producer.KinesisVideoProducerStream;
import com.amazonaws.kinesisvideo.producer.ProducerException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AckConsumer implements Consumer<InputStream> {
    private static final String END_OF_STREAM_MSG = "0";
    private static final int FOUR_KB = 4096;
    private static final long STOPPED_TIMEOUT_IN_MILLISECONDS = 15000;
    private InputStream ackStream = null;
    private volatile boolean closed = false;
    private final Log log;
    private final CountDownLatch stoppedLatch;
    private final KinesisVideoProducerStream stream;
    private final long uploadHandle;

    public AckConsumer(long uploadHandle2, @NonNull KinesisVideoProducerStream stream2, @NonNull Log log2) {
        this.stream = (KinesisVideoProducerStream) Preconditions.checkNotNull(stream2);
        this.uploadHandle = uploadHandle2;
        this.log = (Log) Preconditions.checkNotNull(log2);
        this.stoppedLatch = new CountDownLatch(1);
    }

    public void accept(@NonNull InputStream inputStream) {
        this.ackStream = (InputStream) Preconditions.checkNotNull(inputStream);
        processAckInputStream();
    }

    @Nullable
    public InputStream getAckStream() {
        return this.ackStream;
    }

    private void processAckInputStream() {
        Preconditions.checkNotNull(this.stream);
        byte[] buffer = new byte[4096];
        this.log.info("Starting ACK processing");
        while (!this.closed) {
            try {
                int bytesRead = this.ackStream.read(buffer);
                String bytesString = null;
                if (bytesRead > 0) {
                    bytesString = new String(buffer, 0, bytesRead, StandardCharsets.UTF_8);
                }
                if (bytesRead != -1) {
                    if (!END_OF_STREAM_MSG.equals(bytesString)) {
                        if (bytesRead != 0) {
                            Log log2 = this.log;
                            log2.debug("Received ACK bits: " + bytesString);
                            try {
                                this.stream.parseFragmentAck(this.uploadHandle, bytesString);
                            } catch (ProducerException e) {
                                this.log.exception(e, "Processing ACK threw an exception. Logging and continuing. ", new Object[0]);
                            }
                        }
                    }
                }
                this.log.debug("Received end-of-stream for ACKs.");
                this.closed = true;
            } catch (IOException e2) {
                this.log.exception(e2);
            } catch (Throwable th) {
                this.stoppedLatch.countDown();
                throw th;
            }
        }
        this.log.debug("Finished reading ACKs stream");
        this.stoppedLatch.countDown();
    }

    public void close() {
        this.closed = true;
        try {
            InputStream inputStream = this.ackStream;
            if (inputStream != null) {
                inputStream.close();
            }
            try {
                if (!this.stoppedLatch.await(15000, TimeUnit.MILLISECONDS)) {
                    throw new ProducerException("ACK stream stopping time out", 0);
                }
            } catch (InterruptedException e) {
                throw new ProducerException(e);
            }
        } catch (IOException e2) {
            throw new ProducerException(e2);
        }
    }
}
