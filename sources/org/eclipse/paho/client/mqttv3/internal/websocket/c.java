package org.eclipse.paho.client.mqttv3.internal.websocket;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.SecureRandom;

/* compiled from: WebSocketFrame */
public class c {
    private byte a;
    private boolean b;
    private byte[] c;
    private boolean d = false;

    public byte[] f() {
        return this.c;
    }

    public boolean g() {
        return this.d;
    }

    public c(byte opcode, boolean fin, byte[] payload) {
        this.a = opcode;
        this.b = fin;
        if (payload != null) {
            this.c = (byte[]) payload.clone();
        }
    }

    private void h(byte incomingByte) {
        this.b = (incomingByte & OTACommand.RESP_ID_VERSION) != 0;
        this.a = (byte) (incomingByte & 15);
    }

    public c(InputStream input) {
        h((byte) input.read());
        byte b2 = this.a;
        boolean z = true;
        if (b2 == 2) {
            byte maskLengthByte = (byte) input.read();
            boolean masked = (maskLengthByte & OTACommand.RESP_ID_VERSION) == 0 ? false : z;
            int payloadLength = (byte) (maskLengthByte & Byte.MAX_VALUE);
            int byteCount = 0;
            if (payloadLength == 127) {
                byteCount = 8;
            } else if (payloadLength == 126) {
                byteCount = 2;
            }
            payloadLength = byteCount > 0 ? 0 : payloadLength;
            while (true) {
                byteCount--;
                if (byteCount < 0) {
                    break;
                }
                payloadLength |= (((byte) input.read()) & 255) << (byteCount * 8);
            }
            byte[] maskingKey = null;
            if (masked) {
                maskingKey = new byte[4];
                input.read(maskingKey, 0, 4);
            }
            this.c = new byte[payloadLength];
            int offsetIndex = 0;
            int tempLength = payloadLength;
            while (offsetIndex != payloadLength) {
                int bytesRead = input.read(this.c, offsetIndex, tempLength);
                offsetIndex += bytesRead;
                tempLength -= bytesRead;
            }
            if (masked) {
                int i = 0;
                while (true) {
                    byte[] bArr = this.c;
                    if (i < bArr.length) {
                        bArr[i] = (byte) (bArr[i] ^ maskingKey[i % 4]);
                        i++;
                    } else {
                        return;
                    }
                }
            }
        } else if (b2 == 8) {
            this.d = true;
        } else {
            throw new IOException("Invalid Frame: Opcode: " + this.a);
        }
    }

    public byte[] d() {
        byte[] bArr = this.c;
        int length = bArr.length + 6;
        if (bArr.length > 65535) {
            length += 8;
        } else if (bArr.length >= 126) {
            length += 2;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        a(buffer, this.a, this.b);
        byte[] mask = e();
        c(buffer, this.c.length, mask);
        int i = 0;
        while (true) {
            byte[] bArr2 = this.c;
            if (i >= bArr2.length) {
                buffer.flip();
                return buffer.array();
            }
            byte b2 = (byte) (bArr2[i] ^ mask[i % 4]);
            bArr2[i] = b2;
            buffer.put(b2);
            i++;
        }
    }

    public static void c(ByteBuffer buffer, int length, byte[] mask) {
        if (mask != null) {
            b(buffer, length, true);
            buffer.put(mask);
            return;
        }
        b(buffer, length, false);
    }

    private static void b(ByteBuffer buffer, int length, boolean masked) {
        if (length >= 0) {
            byte b2 = masked ? OTACommand.RESP_ID_VERSION : 0;
            if (length > 65535) {
                buffer.put((byte) (b2 | Byte.MAX_VALUE));
                buffer.put((byte) 0);
                buffer.put((byte) 0);
                buffer.put((byte) 0);
                buffer.put((byte) 0);
                buffer.put((byte) ((length >> 24) & 255));
                buffer.put((byte) ((length >> 16) & 255));
                buffer.put((byte) ((length >> 8) & 255));
                buffer.put((byte) (length & 255));
            } else if (length >= 126) {
                buffer.put((byte) (b2 | 126));
                buffer.put((byte) (length >> 8));
                buffer.put((byte) (length & 255));
            } else {
                buffer.put((byte) (b2 | length));
            }
        } else {
            throw new IllegalArgumentException("Length cannot be negative");
        }
    }

    public static void a(ByteBuffer buffer, byte opcode, boolean fin) {
        byte b2 = 0;
        if (fin) {
            b2 = (byte) (0 | 128);
        }
        buffer.put((byte) ((opcode & 15) | b2));
    }

    public static byte[] e() {
        SecureRandom secureRandomGenerator = new SecureRandom();
        return new byte[]{(byte) secureRandomGenerator.nextInt(255), (byte) secureRandomGenerator.nextInt(255), (byte) secureRandomGenerator.nextInt(255), (byte) secureRandomGenerator.nextInt(255)};
    }
}
