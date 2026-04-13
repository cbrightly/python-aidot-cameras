package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttPublish */
public class o extends h {
    private l g;
    private String h;
    private byte[] i = null;

    public o(String name, l message) {
        super((byte) 3);
        this.h = name;
        this.g = message;
    }

    public o(byte info, byte[] data) {
        super((byte) 3);
        p pVar = new p();
        this.g = pVar;
        pVar.k(3 & (info >> 1));
        if ((info & 1) == 1) {
            this.g.l(true);
        }
        if ((info & 8) == 8) {
            ((p) this.g).g(true);
        }
        a counter = new a(new ByteArrayInputStream(data));
        DataInputStream dis = new DataInputStream(counter);
        this.h = u.j(dis);
        if (this.g.d() > 0) {
            this.d = dis.readUnsignedShort();
        }
        byte[] payload = new byte[(data.length - counter.a())];
        dis.readFully(payload);
        dis.close();
        this.g.j(payload);
    }

    public String toString() {
        String string;
        StringBuffer hex = new StringBuffer();
        byte[] payload = this.g.c();
        int limit = Math.min(payload.length, 20);
        for (int i2 = 0; i2 < limit; i2++) {
            String ch = Integer.toHexString(payload[i2]);
            if (ch.length() == 1) {
                ch = "0" + ch;
            }
            hex.append(ch);
        }
        try {
            string = new String(payload, 0, limit, "UTF-8");
        } catch (Exception e) {
            string = "?";
        }
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(" qos:");
        sb.append(this.g.d());
        if (this.g.d() > 0) {
            sb.append(" msgId:");
            sb.append(this.d);
        }
        sb.append(" retained:");
        sb.append(this.g.f());
        sb.append(" dup:");
        sb.append(this.e);
        sb.append(" topic:\"");
        sb.append(this.h);
        sb.append("\"");
        sb.append(" payload:[hex:");
        sb.append(hex);
        sb.append(" utf8:\"");
        sb.append(string);
        sb.append("\"");
        sb.append(" length:");
        sb.append(payload.length);
        sb.append("]");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public byte q() {
        byte info = (byte) (this.g.d() << 1);
        if (this.g.f()) {
            info = (byte) (info | 1);
        }
        if (this.g.e() || this.e) {
            return (byte) (info | 8);
        }
        return info;
    }

    public String E() {
        return this.h;
    }

    public l D() {
        return this.g;
    }

    protected static byte[] C(l message) {
        return message.c();
    }

    public byte[] r() {
        if (this.i == null) {
            this.i = C(this.g);
        }
        return this.i;
    }

    public int c() {
        try {
            return r().length;
        } catch (MqttException e) {
            return 0;
        }
    }

    public void y(int msgId) {
        super.y(msgId);
        l lVar = this.g;
        if (lVar instanceof p) {
            ((p) lVar).n(msgId);
        }
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            u.m(dos, this.h);
            if (this.g.d() > 0) {
                dos.writeShort(this.d);
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }

    public boolean v() {
        return true;
    }
}
