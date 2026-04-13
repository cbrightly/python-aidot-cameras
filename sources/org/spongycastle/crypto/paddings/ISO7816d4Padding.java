package org.spongycastle.crypto.paddings;

import com.leedarson.serviceimpl.business.bean.OTACommand;
import java.security.SecureRandom;
import org.spongycastle.crypto.InvalidCipherTextException;

public class ISO7816d4Padding implements BlockCipherPadding {
    public void b(SecureRandom random) {
    }

    public int c(byte[] in, int inOff) {
        int added = in.length - inOff;
        in[inOff] = OTACommand.RESP_ID_VERSION;
        while (true) {
            inOff++;
            if (inOff >= in.length) {
                return added;
            }
            in[inOff] = 0;
        }
    }

    public int a(byte[] in) {
        int count = in.length - 1;
        while (count > 0 && in[count] == 0) {
            count--;
        }
        if (in[count] == Byte.MIN_VALUE) {
            return in.length - count;
        }
        throw new InvalidCipherTextException("pad block corrupted");
    }
}
