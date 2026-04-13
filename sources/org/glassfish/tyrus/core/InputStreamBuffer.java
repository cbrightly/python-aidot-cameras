package org.glassfish.tyrus.core;

import jakarta.websocket.MessageHandler;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class InputStreamBuffer {
    private static final Logger LOGGER = Logger.getLogger(InputStreamBuffer.class.getName());
    private volatile int bufferSize;
    private final List<ByteBuffer> bufferedFragments = new ArrayList();
    private final Condition condition;
    private volatile int currentlyBuffered;
    private final ExecutorService executorService;
    /* access modifiers changed from: private */
    public volatile BufferedInputStream inputStream = null;
    private final ReentrantLock lock;
    /* access modifiers changed from: private */
    public volatile MessageHandler.Whole<InputStream> messageHandler;
    private volatile boolean receivedLast = false;
    private volatile boolean sessionClosed = false;

    public InputStreamBuffer(ExecutorService executorService2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
        this.executorService = executorService2;
        this.currentlyBuffered = 0;
    }

    public int getNextByte() {
        boolean interrupted;
        this.lock.lock();
        if (this.bufferedFragments.isEmpty()) {
            if (this.receivedLast) {
                this.inputStream = null;
                this.currentlyBuffered = 0;
                this.lock.unlock();
                return -1;
            }
            checkClosedSession();
            do {
                interrupted = false;
                try {
                    this.condition.await();
                    checkClosedSession();
                    continue;
                } catch (InterruptedException e) {
                    interrupted = true;
                    continue;
                }
            } while (interrupted);
        }
        try {
            if (this.bufferedFragments.size() != 1 || this.bufferedFragments.get(0).hasRemaining() || !this.receivedLast) {
                ByteBuffer firstBuffer = this.bufferedFragments.get(0);
                byte result = firstBuffer.get();
                if (!firstBuffer.hasRemaining()) {
                    this.bufferedFragments.remove(0);
                }
                byte b = result & 255;
                this.lock.unlock();
                return b;
            }
            this.inputStream = null;
            this.currentlyBuffered = 0;
            return -1;
        } finally {
            this.lock.unlock();
        }
    }

    public void finishReading() {
        this.bufferedFragments.clear();
        this.inputStream = null;
    }

    /* JADX INFO: finally extract failed */
    public void appendMessagePart(ByteBuffer message, boolean last) {
        this.lock.lock();
        try {
            this.currentlyBuffered += message.remaining();
            if (this.currentlyBuffered <= this.bufferSize) {
                this.bufferedFragments.add(message);
                this.receivedLast = last;
                this.condition.signalAll();
                this.lock.unlock();
                if (this.inputStream == null) {
                    this.inputStream = new BufferedInputStream(this);
                    this.executorService.execute(new Runnable() {
                        public void run() {
                            InputStreamBuffer.this.messageHandler.onMessage(InputStreamBuffer.this.inputStream);
                        }
                    });
                    return;
                }
                return;
            }
            MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
            LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
            this.receivedLast = true;
            throw messageTooBigException;
        } catch (Throwable messageTooBigException2) {
            this.lock.unlock();
            throw messageTooBigException2;
        }
    }

    public void setMessageHandler(MessageHandler.Whole<InputStream> messageHandler2) {
        this.messageHandler = messageHandler2;
    }

    public void resetBuffer(int bufferSize2) {
        this.bufferSize = bufferSize2;
        this.currentlyBuffered = 0;
        this.bufferedFragments.clear();
    }

    /* access modifiers changed from: package-private */
    public void onSessionClosed() {
        this.sessionClosed = true;
        this.lock.lock();
        try {
            this.condition.signalAll();
        } finally {
            this.lock.unlock();
        }
    }

    private void checkClosedSession() {
        if (this.sessionClosed) {
            throw new IOException("Websocket session has been closed.");
        }
    }
}
