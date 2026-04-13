package org.eclipse.paho.client.mqttv3;

/* compiled from: DisconnectedBufferOptions */
public class a {
    private int a = 5000;
    private boolean b = false;
    private boolean c = false;
    private boolean d = false;

    public void c(int bufferSize) {
        if (bufferSize >= 1) {
            this.a = bufferSize;
            return;
        }
        throw new IllegalArgumentException();
    }

    public boolean a() {
        return this.b;
    }

    public void b(boolean bufferEnabled) {
        this.b = bufferEnabled;
    }

    public void e(boolean persistBuffer) {
        this.c = persistBuffer;
    }

    public void d(boolean deleteOldestMessages) {
        this.d = deleteOldestMessages;
    }
}
