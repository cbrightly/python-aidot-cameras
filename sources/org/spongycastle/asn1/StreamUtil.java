package org.spongycastle.asn1;

import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class StreamUtil {
    private static final long a = Runtime.getRuntime().maxMemory();

    StreamUtil() {
    }

    static int c(InputStream in) {
        if (in instanceof LimitedInputStream) {
            return ((LimitedInputStream) in).a();
        }
        if (in instanceof ASN1InputStream) {
            return ((ASN1InputStream) in).m();
        }
        if (in instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream) in).available();
        }
        if (in instanceof FileInputStream) {
            try {
                FileChannel channel = ((FileInputStream) in).getChannel();
                long size = channel != null ? channel.size() : 2147483647L;
                if (size < 2147483647L) {
                    return (int) size;
                }
            } catch (IOException e) {
            }
        }
        long j = a;
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    static int a(int length) {
        int count = 1;
        if (length > 127) {
            int size = 1;
            int val = length;
            while (true) {
                int i = val >>> 8;
                val = i;
                if (i == 0) {
                    break;
                }
                size++;
            }
            for (int i2 = (size - 1) * 8; i2 >= 0; i2 -= 8) {
                count++;
            }
        }
        return count;
    }

    static int b(int tagNo) {
        if (tagNo < 31) {
            return 1;
        }
        if (tagNo < 128) {
            return 1 + 1;
        }
        byte[] stack = new byte[5];
        int pos = stack.length - 1;
        stack[pos] = (byte) (tagNo & NeedPermissionEvent.PER_IPC_SPEAK_PERM);
        do {
            tagNo >>= 7;
            pos--;
            stack[pos] = (byte) ((tagNo & NeedPermissionEvent.PER_IPC_SPEAK_PERM) | 128);
        } while (tagNo > 127);
        return 1 + (stack.length - pos);
    }
}
