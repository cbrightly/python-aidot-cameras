package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;

/* compiled from: MqttUnsubscribe */
public class t extends u {
    private String[] g;
    private int h;

    public t(String[] names) {
        super((byte) 10);
        if (names != null) {
            this.g = (String[]) names.clone();
        }
    }

    public t(byte info, byte[] data) {
        super((byte) 10);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        this.h = 0;
        this.g = new String[10];
        boolean end = false;
        while (!end) {
            try {
                this.g[this.h] = u.j(dis);
            } catch (Exception e) {
                end = true;
            }
        }
        dis.close();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(" names:[");
        for (int i = 0; i < this.h; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("\"" + this.g[i] + "\"");
        }
        sb.append("]");
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public byte q() {
        return (byte) ((this.e ? 8 : 0) | 2);
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeShort(this.d);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }

    public byte[] r() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            for (String name : this.g) {
                u.m(dos, name);
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }
}
