package org.glassfish.grizzly.http.server.accesslog;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.glassfish.grizzly.Grizzly;
import org.glassfish.grizzly.http.server.HttpServer;

public class QueueingAppender implements AccessLogAppender {
    /* access modifiers changed from: private */
    public static final Logger LOGGER = Grizzly.logger(HttpServer.class);
    /* access modifiers changed from: private */
    public final AccessLogAppender appender;
    /* access modifiers changed from: private */
    public final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final Thread thread;

    public QueueingAppender(AccessLogAppender appender2) {
        if (appender2 != null) {
            this.appender = appender2;
            Thread thread2 = new Thread(new Dequeuer());
            this.thread = thread2;
            thread2.setName(toString());
            thread2.setDaemon(true);
            thread2.start();
            return;
        }
        throw new NullPointerException("Null appender");
    }

    public void append(String accessLogEntry) {
        if (this.thread.isAlive()) {
            try {
                this.queue.put(accessLogEntry);
            } catch (InterruptedException exception) {
                LOGGER.log(Level.FINE, "Interrupted adding log entry to the queue", exception);
            }
        }
    }

    public void close() {
        this.thread.interrupt();
        try {
            this.thread.join();
        } catch (InterruptedException exception) {
            LOGGER.log(Level.FINE, "Interrupted stopping de-queuer", exception);
        } catch (Throwable th) {
            this.appender.close();
            throw th;
        }
        this.appender.close();
    }

    public final class Dequeuer implements Runnable {
        private Dequeuer() {
        }

        public void run() {
            while (true) {
                try {
                    String accessLogEntry = (String) QueueingAppender.this.queue.take();
                    if (accessLogEntry != null) {
                        QueueingAppender.this.appender.append(accessLogEntry);
                    }
                } catch (InterruptedException exception) {
                    QueueingAppender.LOGGER.log(Level.FINE, "Interrupted waiting for log entry to be queued, exiting!", exception);
                    return;
                } catch (Throwable throwable) {
                    QueueingAppender.LOGGER.log(Level.WARNING, "Exception caught appending ququed log entry", throwable);
                }
            }
        }
    }
}
