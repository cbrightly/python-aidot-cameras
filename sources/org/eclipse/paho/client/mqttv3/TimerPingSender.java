package org.eclipse.paho.client.mqttv3;

import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.paho.client.mqttv3.logging.a;
import org.eclipse.paho.client.mqttv3.logging.b;

public class TimerPingSender implements n {
    /* access modifiers changed from: private */
    public static final String a = TimerPingSender.class.getName();
    /* access modifiers changed from: private */
    public a b = b.a("org.eclipse.paho.client.mqttv3.internal.nls.logcat", a);
    /* access modifiers changed from: private */
    public org.eclipse.paho.client.mqttv3.internal.a c;
    private Timer d;
    private String e;

    public void a(org.eclipse.paho.client.mqttv3.internal.a comms) {
        if (comms != null) {
            this.c = comms;
            String f0 = comms.t().f0();
            this.e = f0;
            this.b.setResourceName(f0);
            return;
        }
        throw new IllegalArgumentException("ClientComms cannot be null.");
    }

    public void start() {
        this.b.fine(a, "start", "659", new Object[]{this.e});
        Timer timer = new Timer("MQTT Ping: " + this.e);
        this.d = timer;
        timer.schedule(new PingTask(this, (PingTask) null), this.c.u());
    }

    public void stop() {
        this.b.fine(a, "stop", "661", (Object[]) null);
        Timer timer = this.d;
        if (timer != null) {
            timer.cancel();
        }
    }

    public void b(long delayInMilliseconds) {
        this.d.schedule(new PingTask(this, (PingTask) null), delayInMilliseconds);
    }

    public class PingTask extends TimerTask {
        private static final String methodName = "PingTask.run";

        private PingTask() {
        }

        /* synthetic */ PingTask(TimerPingSender timerPingSender, PingTask pingTask) {
            this();
        }

        public void run() {
            TimerPingSender.this.b.fine(TimerPingSender.a, methodName, "660", new Object[]{Long.valueOf(System.nanoTime())});
            TimerPingSender.this.c.m();
        }
    }
}
