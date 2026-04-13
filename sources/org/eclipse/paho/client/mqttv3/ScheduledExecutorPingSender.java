package org.eclipse.paho.client.mqttv3;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.eclipse.paho.client.mqttv3.logging.a;

public class ScheduledExecutorPingSender implements n {
    /* access modifiers changed from: private */
    public static final String a = ScheduledExecutorPingSender.class.getName();
    /* access modifiers changed from: private */
    public final a b;
    /* access modifiers changed from: private */
    public org.eclipse.paho.client.mqttv3.internal.a c;
    private ScheduledExecutorService d;
    private ScheduledFuture e;
    /* access modifiers changed from: private */
    public String f;

    public void a(org.eclipse.paho.client.mqttv3.internal.a comms) {
        if (comms != null) {
            this.c = comms;
            this.f = comms.t().f0();
            return;
        }
        throw new IllegalArgumentException("ClientComms cannot be null.");
    }

    public void start() {
        this.b.fine(a, "start", "659", new Object[]{this.f});
        b(this.c.u());
    }

    public void stop() {
        this.b.fine(a, "stop", "661", (Object[]) null);
        ScheduledFuture scheduledFuture = this.e;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
        }
    }

    public void b(long delayInMilliseconds) {
        this.e = this.d.schedule(new PingRunnable(this, (PingRunnable) null), delayInMilliseconds, TimeUnit.MILLISECONDS);
    }

    public class PingRunnable implements Runnable {
        private static final String methodName = "PingTask.run";

        private PingRunnable() {
        }

        /* synthetic */ PingRunnable(ScheduledExecutorPingSender scheduledExecutorPingSender, PingRunnable pingRunnable) {
            this();
        }

        public void run() {
            String originalThreadName = Thread.currentThread().getName();
            Thread currentThread = Thread.currentThread();
            currentThread.setName("MQTT Ping: " + ScheduledExecutorPingSender.this.f);
            ScheduledExecutorPingSender.this.b.fine(ScheduledExecutorPingSender.a, methodName, "660", new Object[]{Long.valueOf(System.nanoTime())});
            ScheduledExecutorPingSender.this.c.m();
            Thread.currentThread().setName(originalThreadName);
        }
    }
}
