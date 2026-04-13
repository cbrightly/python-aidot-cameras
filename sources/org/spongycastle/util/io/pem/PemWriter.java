package org.spongycastle.util.io.pem;

import java.io.BufferedWriter;
import java.io.Writer;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Base64;

public class PemWriter extends BufferedWriter {
    private final int c;
    private char[] d = new char[64];

    public PemWriter(Writer out) {
        super(out);
        String nl = Strings.d();
        if (nl != null) {
            this.c = nl.length();
        } else {
            this.c = 2;
        }
    }

    public void c(PemObjectGenerator objGen) {
        PemObject obj = objGen.a();
        i(obj.d());
        if (!obj.c().isEmpty()) {
            for (PemHeader hdr : obj.c()) {
                write(hdr.b());
                write(": ");
                write(hdr.c());
                newLine();
            }
            newLine();
        }
        a(obj.b());
        g(obj.d());
    }

    private void a(byte[] bytes) {
        char[] cArr;
        byte[] bytes2 = Base64.b(bytes);
        int i = 0;
        while (i < bytes2.length) {
            int index = 0;
            while (true) {
                cArr = this.d;
                if (index == cArr.length || i + index >= bytes2.length) {
                    write(cArr, 0, index);
                    newLine();
                    i += this.d.length;
                } else {
                    cArr[index] = (char) bytes2[i + index];
                    index++;
                }
            }
            write(cArr, 0, index);
            newLine();
            i += this.d.length;
        }
    }

    private void i(String type) {
        write("-----BEGIN " + type + "-----");
        newLine();
    }

    private void g(String type) {
        write("-----END " + type + "-----");
        newLine();
    }
}
