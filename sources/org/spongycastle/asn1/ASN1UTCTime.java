package org.spongycastle.asn1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class ASN1UTCTime extends ASN1Primitive {
    private byte[] c;

    ASN1UTCTime(byte[] time) {
        this.c = time;
    }

    public Date o() {
        SimpleDateFormat dateF = new SimpleDateFormat("yyyyMMddHHmmssz");
        dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
        return dateF.parse(p());
    }

    public String q() {
        String stime = Strings.b(this.c);
        if (stime.indexOf(45) >= 0 || stime.indexOf(43) >= 0) {
            int index = stime.indexOf(45);
            if (index < 0) {
                index = stime.indexOf(43);
            }
            String d = stime;
            if (index == stime.length() - 3) {
                d = d + "00";
            }
            if (index == 10) {
                return d.substring(0, 10) + "00GMT" + d.substring(10, 13) + ":" + d.substring(13, 15);
            }
            return d.substring(0, 12) + "GMT" + d.substring(12, 15) + ":" + d.substring(15, 17);
        } else if (stime.length() == 11) {
            return stime.substring(0, 10) + "00GMT+00:00";
        } else {
            return stime.substring(0, 12) + "GMT+00:00";
        }
    }

    public String p() {
        String d = q();
        if (d.charAt(0) < '5') {
            return "20" + d;
        }
        return "19" + d;
    }

    /* access modifiers changed from: package-private */
    public boolean i() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public int g() {
        int length = this.c.length;
        return StreamUtil.a(length) + 1 + length;
    }

    /* access modifiers changed from: package-private */
    public void f(ASN1OutputStream out) {
        out.c(23);
        int length = this.c.length;
        out.i(length);
        for (int i = 0; i != length; i++) {
            out.c(this.c[i]);
        }
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1UTCTime)) {
            return false;
        }
        return Arrays.b(this.c, ((ASN1UTCTime) o).c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }

    public String toString() {
        return Strings.b(this.c);
    }
}
