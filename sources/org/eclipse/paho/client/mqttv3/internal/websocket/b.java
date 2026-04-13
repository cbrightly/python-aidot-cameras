package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/* compiled from: ExtendedByteArrayOutputStream */
public class b extends ByteArrayOutputStream {
    final e c;
    final h d;

    b(e module) {
        this.c = module;
        this.d = null;
    }

    b(h module) {
        this.c = null;
        this.d = module;
    }

    public void flush() {
        ByteBuffer byteBuffer;
        synchronized (this) {
            byteBuffer = ByteBuffer.wrap(toByteArray());
            reset();
        }
        a().write(new c((byte) 2, true, byteBuffer.array()).d());
        a().flush();
    }

    /* access modifiers changed from: package-private */
    public OutputStream a() {
        e eVar = this.c;
        if (eVar != null) {
            return eVar.d();
        }
        h hVar = this.d;
        if (hVar != null) {
            return hVar.h();
        }
        return null;
    }
}
