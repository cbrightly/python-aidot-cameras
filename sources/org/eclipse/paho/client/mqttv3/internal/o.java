package org.eclipse.paho.client.mqttv3.internal;

import java.io.InputStream;
import java.io.OutputStream;

/* compiled from: NetworkModule */
public interface o {
    String a();

    InputStream getInputStream();

    OutputStream getOutputStream();

    void start();

    void stop();
}
