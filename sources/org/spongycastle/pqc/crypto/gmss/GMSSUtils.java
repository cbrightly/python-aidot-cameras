package org.spongycastle.pqc.crypto.gmss;

import java.util.Enumeration;
import java.util.Vector;
import org.spongycastle.util.Arrays;

public class GMSSUtils {
    GMSSUtils() {
    }

    static byte[][] c(byte[][] data) {
        if (data == null) {
            return null;
        }
        byte[][] copy = new byte[data.length][];
        for (int i = 0; i != data.length; i++) {
            copy[i] = Arrays.h(data[i]);
        }
        return copy;
    }

    static Treehash[] b(Treehash[] data) {
        if (data == null) {
            return null;
        }
        Treehash[] copy = new Treehash[data.length];
        System.arraycopy(data, 0, copy, 0, data.length);
        return copy;
    }

    static Vector[] a(Vector[] data) {
        if (data == null) {
            return null;
        }
        Vector[] copy = new Vector[data.length];
        for (int i = 0; i != data.length; i++) {
            copy[i] = new Vector();
            Enumeration en = data[i].elements();
            while (en.hasMoreElements()) {
                copy[i].addElement(en.nextElement());
            }
        }
        return copy;
    }
}
