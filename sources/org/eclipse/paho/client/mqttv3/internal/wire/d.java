package org.eclipse.paho.client.mqttv3.internal.wire;

import com.leedarson.bean.Constants;
import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.l;

/* compiled from: MqttConnect */
public class d extends u {
    private String g;
    private boolean h;
    private l i;
    private String j;
    private char[] k;
    private int l;
    private String m;
    private int n;

    public d(byte info, byte[] data) {
        super((byte) 1);
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
        String j2 = u.j(dis);
        byte readByte = dis.readByte();
        byte readByte2 = dis.readByte();
        this.l = dis.readUnsignedShort();
        this.g = u.j(dis);
        dis.close();
    }

    public d(String clientId, int mqttVersion, boolean cleanSession, int keepAliveInterval, String userName, char[] password, l willMessage, String willDestination) {
        super((byte) 1);
        this.g = clientId;
        this.h = cleanSession;
        this.l = keepAliveInterval;
        this.j = userName;
        if (password != null) {
            this.k = (char[]) password.clone();
        }
        this.i = willMessage;
        this.m = willDestination;
        this.n = mqttVersion;
    }

    public String toString() {
        return String.valueOf(super.toString()) + " clientId " + this.g + " keepAliveInterval " + this.l;
    }

    /* access modifiers changed from: protected */
    public byte q() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] u() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            int i2 = this.n;
            if (i2 == 3) {
                u.m(dos, "MQIsdp");
            } else if (i2 == 4) {
                u.m(dos, Constants.SERVICE_MQTT);
            }
            dos.write(this.n);
            byte connectFlags = 0;
            if (this.h) {
                connectFlags = (byte) (0 | 2);
            }
            l lVar = this.i;
            if (lVar != null) {
                connectFlags = (byte) ((lVar.d() << 3) | ((byte) (connectFlags | 4)));
                if (this.i.f()) {
                    connectFlags = (byte) (connectFlags | 32);
                }
            }
            if (this.j != null) {
                connectFlags = (byte) (connectFlags | OTACommand.RESP_ID_VERSION);
                if (this.k != null) {
                    connectFlags = (byte) (connectFlags | 64);
                }
            }
            dos.write(connectFlags);
            dos.writeShort(this.l);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ioe) {
            throw new MqttException((Throwable) ioe);
        }
    }

    public byte[] r() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            u.m(dos, this.g);
            if (this.i != null) {
                u.m(dos, this.m);
                dos.writeShort(this.i.c().length);
                dos.write(this.i.c());
            }
            String str = this.j;
            if (str != null) {
                u.m(dos, str);
                if (this.k != null) {
                    u.m(dos, new String(this.k));
                }
            }
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }

    public boolean v() {
        return false;
    }

    public String o() {
        return "Con";
    }
}
