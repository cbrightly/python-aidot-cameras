package org.glassfish.tyrus.core;

import jakarta.websocket.MessageHandler;
import java.io.IOException;
import java.io.Reader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

public class ReaderBuffer {
    private static final Logger LOGGER = Logger.getLogger(ReaderBuffer.class.getName());
    private volatile StringBuffer buffer;
    private volatile int bufferSize;
    private final AtomicBoolean buffering = new AtomicBoolean(true);
    private final Condition condition;
    private volatile int currentlyBuffered;
    private final ExecutorService executorService;
    private final ReentrantLock lock;
    /* access modifiers changed from: private */
    public volatile MessageHandler.Whole<Reader> messageHandler;
    /* access modifiers changed from: private */
    public volatile BufferedStringReader reader;
    private volatile boolean receivedLast;
    private volatile boolean sessionClosed;

    public ReaderBuffer(ExecutorService executorService2) {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.lock = reentrantLock;
        this.condition = reentrantLock.newCondition();
        this.receivedLast = false;
        this.reader = null;
        this.sessionClosed = false;
        this.buffer = new StringBuffer();
        this.executorService = executorService2;
        this.currentlyBuffered = 0;
    }

    public char[] getNextChars(int number) {
        boolean interrupted;
        this.lock.lock();
        try {
            if (this.buffer.length() == 0) {
                if (this.receivedLast) {
                    this.reader = null;
                    this.buffering.set(true);
                    this.currentlyBuffered = 0;
                    return null;
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
            int size = number > this.buffer.length() ? this.buffer.length() : number;
            char[] result = new char[size];
            this.buffer.getChars(0, size, result, 0);
            this.buffer.delete(0, size);
            this.lock.unlock();
            return result;
        } finally {
            this.lock.unlock();
        }
    }

    public void finishReading() {
        this.buffer = new StringBuffer();
        this.reader = null;
    }

    /* JADX INFO: finally extract failed */
    public void appendMessagePart(String message, boolean last) {
        this.lock.lock();
        try {
            this.currentlyBuffered += message.length();
            if (this.currentlyBuffered <= this.bufferSize) {
                this.buffer.append(message);
            } else if (this.buffering.get()) {
                this.buffering.set(false);
                MessageTooBigException messageTooBigException = new MessageTooBigException(LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW());
                LOGGER.log(Level.FINE, LocalizationMessages.PARTIAL_MESSAGE_BUFFER_OVERFLOW(), messageTooBigException);
                this.receivedLast = true;
                throw messageTooBigException;
            }
            this.receivedLast = last;
            this.condition.signalAll();
            this.lock.unlock();
            if (this.reader == null) {
                this.reader = new BufferedStringReader(this);
                this.executorService.execute(new Runnable() {
                    public void run() {
                        ReaderBuffer.this.messageHandler.onMessage(ReaderBuffer.this.reader);
                    }
                });
            }
        } catch (Throwable messageTooBigException2) {
            this.lock.unlock();
            throw messageTooBigException2;
        }
    }

    public void setMessageHandler(MessageHandler.Whole<Reader> messageHandler2) {
        this.messageHandler = messageHandler2;
    }

    public void resetBuffer(int bufferSize2) {
        this.bufferSize = bufferSize2;
        this.buffering.set(true);
        this.currentlyBuffered = 0;
        this.buffer.delete(0, this.buffer.length());
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
