package org.eclipse.paho.client.mqttv3.internal.wire;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttSubscribe */
public class r extends u {
    private String[] g;
    private int[] h;
    private int i;

    public r(byte info, byte[] data) {
        super((byte) 8);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        this.d = dis.readUnsignedShort();
        this.i = 0;
        this.g = new String[10];
        this.h = new int[10];
        boolean end = false;
        while (!end) {
            try {
                this.g[this.i] = u.j(dis);
                int[] iArr = this.h;
                int i2 = this.i;
                this.i = i2 + 1;
                iArr[i2] = dis.readByte();
            } catch (Exception e) {
                end = true;
            }
        }
        dis.close();
    }

    public r(String[] names, int[] qos) {
        super((byte) 8);
        if (names == null || qos == null) {
            throw new IllegalArgumentException();
        }
        this.g = (String[]) names.clone();
        int[] iArr = (int[]) qos.clone();
        this.h = iArr;
        if (this.g.length == iArr.length) {
            this.i = names.length;
            for (int qo : qos) {
                l.m(qo);
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(super.toString());
        sb.append(" names:[");
        for (int i2 = 0; i2 < this.i; i2++) {
            if (i2 > 0) {
                sb.append(", ");
            }
            sb.append("\"");
            sb.append(this.g[i2]);
            sb.append("\"");
        }
        sb.append("] qos:[");
        for (int i3 = 0; i3 < this.i; i3++) {
            if (i3 > 0) {
                sb.append(", ");
            }
            sb.append(this.h[i3]);
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
            int i2 = 0;
            while (true) {
                String[] strArr = this.g;
                if (i2 >= strArr.length) {
                    dos.flush();
                    return baos.toByteArray();
                }
                u.m(dos, strArr[i2]);
                dos.writeByte(this.h[i2]);
                i2++;
            }
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }
}
