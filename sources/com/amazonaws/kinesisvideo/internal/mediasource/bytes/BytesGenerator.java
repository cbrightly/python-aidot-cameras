package com.amazonaws.kinesisvideo.internal.mediasource.bytes;

import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.internal.mediasource.OnStreamDataAvailable;
import com.amazonaws.kinesisvideo.stream.throttling.DiscreteTimePeriodsThrottler;
import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BytesGenerator {
    private static final int DISCRETENESS_10HZ = 10;
    private static final int MAX_FRAME_SIZE_BYTES_1024 = 1024;
    private final ExecutorService executor = Executors.newFixedThreadPool(1);
    private int frameCounter = 0;
    private final byte[][] framesData = {new byte[1024], new byte[1024], new byte[1024], new byte[1024], new byte[1024], new byte[1024]};
    private volatile boolean isRunning;
    /* access modifiers changed from: private */
    public final Log log = LogFactory.getLog(BytesGenerator.class);
    private OnStreamDataAvailable streamDataAvailable;
    private final DiscreteTimePeriodsThrottler throttler;

    public BytesGenerator(int fps) {
        this.throttler = new DiscreteTimePeriodsThrottler(fps, 10);
    }

    public void onStreamDataAvailable(OnStreamDataAvailable streamDataAvailable2) {
        this.streamDataAvailable = streamDataAvailable2;
    }

    public synchronized void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            startGeneratorInBackground();
        } else {
            throw new IllegalStateException("should stop previous generator before starting the new one");
        }
    }

    public synchronized void stop() {
        this.isRunning = false;
    }

    private void startGeneratorInBackground() {
        this.executor.execute(new Runnable() {
            public void run() {
                try {
                    BytesGenerator.this.generateBytesAndNotifyListener();
                } catch (KinesisVideoException e) {
                    BytesGenerator.this.log.error("Failed to keep generating frames with Exception", e);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void generateBytesAndNotifyListener() {
        while (this.isRunning) {
            fillArrayWithDigitsOfFramesCounter();
            OnStreamDataAvailable onStreamDataAvailable = this.streamDataAvailable;
            if (onStreamDataAvailable != null) {
                byte[][] bArr = this.framesData;
                onStreamDataAvailable.onFrameDataAvailable(ByteBuffer.wrap(bArr[this.frameCounter % bArr.length]));
            }
            this.frameCounter++;
            this.throttler.throttle();
        }
    }

    private void fillArrayWithDigitsOfFramesCounter() {
        byte[] counterBytes = (String.valueOf(this.frameCounter) + "|").getBytes(StandardCharsets.US_ASCII);
        byte[][] bArr = this.framesData;
        byte[] frameData = bArr[this.frameCounter % bArr.length];
        for (int i = 0; i < frameData.length; i++) {
            frameData[i] = counterBytes[i % counterBytes.length];
        }
    }
}
