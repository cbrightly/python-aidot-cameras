package org.eclipse.paho.client.mqttv3.internal.wire;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.internal.i;
import org.eclipse.paho.client.mqttv3.m;
import org.eclipse.paho.client.mqttv3.o;

/* compiled from: MqttWireMessage */
public abstract class u {
    protected static final Charset a = StandardCharsets.UTF_8;
    private static final String[] b = {"reserved", "CONNECT", "CONNACK", "PUBLISH", "PUBACK", "PUBREC", "PUBREL", "PUBCOMP", "SUBSCRIBE", "SUBACK", "UNSUBSCRIBE", "UNSUBACK", "PINGREQ", "PINGRESP", "DISCONNECT"};
    private byte c;
    protected int d;
    protected boolean e = false;
    private o f;

    /* access modifiers changed from: protected */
    public abstract byte q();

    /* access modifiers changed from: protected */
    public abstract byte[] u();

    public u(byte type) {
        this.c = type;
        this.d = 0;
    }

    public byte[] r() {
        return new byte[0];
    }

    public byte t() {
        return this.c;
    }

    public int p() {
        return this.d;
    }

    public void y(int msgId) {
        this.d = msgId;
    }

    public String o() {
        return Integer.toString(p());
    }

    public byte[] n() {
        try {
            int first = ((t() & 15) << 4) ^ (q() & 15);
            byte[] varHeader = u();
            int remLen = varHeader.length + r().length;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeByte(first);
            dos.write(k((long) remLen));
            dos.write(varHeader);
            dos.flush();
            return baos.toByteArray();
        } catch (IOException ioe) {
            throw new MqttException((Throwable) ioe);
        }
    }

    public boolean v() {
        return true;
    }

    public static u h(m data) {
        byte[] payload = data.e();
        if (payload == null) {
            payload = new byte[0];
        }
        return g(new v(data.d(), data.a(), data.f(), payload, data.b(), data.c()));
    }

    public static u i(byte[] bytes) {
        return g(new ByteArrayInputStream(bytes));
    }

    private static u g(InputStream inputStream) {
        try {
            a counter = new a(inputStream);
            DataInputStream in = new DataInputStream(counter);
            int first = in.readUnsignedByte();
            byte type = (byte) (first >> 4);
            int i = first & 15;
            int first2 = i;
            byte info = (byte) i;
            long remainder = (((long) counter.a()) + ((long) w(in).a())) - ((long) counter.a());
            byte[] data = new byte[0];
            if (remainder > 0) {
                data = new byte[((int) remainder)];
                in.readFully(data, 0, data.length);
            }
            if (type == 1) {
                return new d(info, data);
            }
            if (type == 3) {
                return new o(info, data);
            }
            if (type == 4) {
                return new k(info, data);
            }
            if (type == 7) {
                return new l(info, data);
            }
            if (type == 2) {
                return new c(info, data);
            }
            if (type == 12) {
                return new i(info, data);
            }
            if (type == 13) {
                return new j(info, data);
            }
            if (type == 8) {
                return new r(info, data);
            }
            if (type == 9) {
                return new q(info, data);
            }
            if (type == 10) {
                return new t(info, data);
            }
            if (type == 11) {
                return new s(info, data);
            }
            if (type == 6) {
                return new n(info, data);
            }
            if (type == 5) {
                return new m(info, data);
            }
            if (type == 14) {
                return new e(info, data);
            }
            throw i.a(6);
        } catch (IOException io2) {
            throw new MqttException((Throwable) io2);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:3:0x0018  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] k(long r8) {
        /*
            int r0 = (int) r8
            B(r0)
            r0 = 0
            r1 = r8
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream
            r3.<init>()
        L_0x000b:
            r4 = 128(0x80, double:6.32E-322)
            long r6 = r1 % r4
            int r6 = (int) r6
            byte r6 = (byte) r6
            long r1 = r1 / r4
            r4 = 0
            int r7 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r7 <= 0) goto L_0x001b
            r7 = r6 | 128(0x80, float:1.794E-43)
            byte r6 = (byte) r7
        L_0x001b:
            r3.write(r6)
            int r0 = r0 + 1
            int r4 = (r1 > r4 ? 1 : (r1 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0028
            r4 = 4
            if (r0 < r4) goto L_0x000b
        L_0x0028:
            byte[] r4 = r3.toByteArray()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.wire.u.k(long):byte[]");
    }

    public static w w(DataInputStream in) {
        byte digit;
        int msgLength = 0;
        int multiplier = 1;
        int count = 0;
        do {
            digit = in.readByte();
            count++;
            msgLength += (digit & Byte.MAX_VALUE) * multiplier;
            multiplier *= 128;
        } while ((digit & OTACommand.RESP_ID_VERSION) != 0);
        if (msgLength >= 0 && msgLength <= 268435455) {
            return new w(msgLength, count);
        }
        throw new IOException("This property must be a number between 0 and 268435455. Read value was: " + msgLength);
    }

    /* access modifiers changed from: protected */
    public byte[] l() {
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

    public void x(boolean duplicate) {
        this.e = duplicate;
    }

    public static void m(DataOutputStream dos, String stringToEncode) {
        A(stringToEncode);
        try {
            byte[] encodedString = stringToEncode.getBytes(a);
            dos.write((byte) ((encodedString.length >>> 8) & 255));
            dos.write((byte) ((encodedString.length >>> 0) & 255));
            dos.write(encodedString);
        } catch (UnsupportedEncodingException ex) {
            throw new MqttException((Throwable) ex);
        } catch (IOException ex2) {
            throw new MqttException((Throwable) ex2);
        }
    }

    public static String j(DataInputStream input) {
        try {
            byte[] encodedString = new byte[input.readUnsignedShort()];
            input.readFully(encodedString);
            String output = new String(encodedString, a);
            A(output);
            return output;
        } catch (IOException ex) {
            throw new MqttException((Throwable) ex);
        }
    }

    private static void A(String input) {
        int i = 0;
        while (i < input.length()) {
            boolean isBad = false;
            char c2 = input.charAt(i);
            if (Character.isHighSurrogate(c2)) {
                i++;
                if (i == input.length()) {
                    isBad = true;
                } else {
                    char c22 = input.charAt(i);
                    if (Character.isLowSurrogate(c22)) {
                        isBad = true;
                    } else {
                        int ch = ((c2 & 1023) << 10) | (c22 & 1023);
                        if ((ch & 65535) == 65535 || (65535 & ch) == 65534) {
                            isBad = true;
                        }
                    }
                }
            } else if (Character.isISOControl(c2) || Character.isLowSurrogate(c2)) {
                isBad = true;
            } else if (c2 >= 64976 && (c2 == 65534 || c2 >= 64976 || c2 <= 64991)) {
                isBad = true;
            }
            if (!isBad) {
                i++;
            } else {
                throw new IllegalArgumentException(String.format("Invalid UTF-8 char: [%x]", new Object[]{Integer.valueOf(c2)}));
            }
        }
    }

    public static void B(int value) {
        if (value < 0 || value > 268435455) {
            throw new IllegalArgumentException("This property must be a number between 0 and 268435455");
        }
    }

    public o s() {
        return this.f;
    }

    public void z(o token) {
        this.f = token;
    }

    public String toString() {
        return b[this.c];
    }
}
