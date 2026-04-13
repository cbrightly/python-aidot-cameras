package org.spongycastle.asn1;

import com.github.druk.dnssd.DNSSD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.slf4j.e;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class ASN1GeneralizedTime extends ASN1Primitive {
    private byte[] c;

    public static ASN1GeneralizedTime r(Object obj) {
        if (obj == null || (obj instanceof ASN1GeneralizedTime)) {
            return (ASN1GeneralizedTime) obj;
        }
        if (obj instanceof byte[]) {
            try {
                return (ASN1GeneralizedTime) ASN1Primitive.h((byte[]) obj);
            } catch (Exception e) {
                throw new IllegalArgumentException("encoding error in getInstance: " + e.toString());
            }
        } else {
            throw new IllegalArgumentException("illegal object in getInstance: " + obj.getClass().getName());
        }
    }

    public ASN1GeneralizedTime(String time) {
        this.c = Strings.f(time);
        try {
            q();
        } catch (ParseException e) {
            throw new IllegalArgumentException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1GeneralizedTime(Date time) {
        SimpleDateFormat dateF = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
        dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
        this.c = Strings.f(dateF.format(time));
    }

    ASN1GeneralizedTime(byte[] bytes) {
        this.c = bytes;
    }

    public String s() {
        String stime = Strings.b(this.c);
        if (stime.charAt(stime.length() - 1) == 'Z') {
            return stime.substring(0, stime.length() - 1) + "GMT+00:00";
        }
        int signPos = stime.length() - 5;
        char sign = stime.charAt(signPos);
        if (sign == '-' || sign == '+') {
            return stime.substring(0, signPos) + "GMT" + stime.substring(signPos, signPos + 3) + ":" + stime.substring(signPos + 3);
        }
        int signPos2 = stime.length() - 3;
        char sign2 = stime.charAt(signPos2);
        if (sign2 == '-' || sign2 == '+') {
            return stime.substring(0, signPos2) + "GMT" + stime.substring(signPos2) + ":00";
        }
        return stime + o();
    }

    private String o() {
        String sign = e.ANY_NON_NULL_MARKER;
        TimeZone timeZone = TimeZone.getDefault();
        int offset = timeZone.getRawOffset();
        if (offset < 0) {
            sign = "-";
            offset = -offset;
        }
        int hours = offset / 3600000;
        int minutes = (offset - (((hours * 60) * 60) * 1000)) / DNSSD.DNSSD_DEFAULT_TIMEOUT;
        try {
            if (timeZone.useDaylightTime() && timeZone.inDaylightTime(q())) {
                hours += sign.equals(e.ANY_NON_NULL_MARKER) ? 1 : -1;
            }
        } catch (ParseException e) {
        }
        return "GMT" + sign + p(hours) + ":" + p(minutes);
    }

    private String p(int time) {
        if (time >= 10) {
            return Integer.toString(time);
        }
        return "0" + time;
    }

    public Date q() {
        SimpleDateFormat dateF;
        SimpleDateFormat dateF2;
        SimpleDateFormat dateF3;
        String stime = Strings.b(this.c);
        String d = stime;
        if (stime.endsWith("Z")) {
            if (t()) {
                dateF = new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'");
            } else {
                dateF = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
            }
            dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else if (stime.indexOf(45) > 0 || stime.indexOf(43) > 0) {
            d = s();
            if (t()) {
                dateF2 = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");
            } else {
                dateF2 = new SimpleDateFormat("yyyyMMddHHmmssz");
            }
            dateF.setTimeZone(new SimpleTimeZone(0, "Z"));
        } else {
            if (t()) {
                dateF3 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            } else {
                dateF3 = new SimpleDateFormat("yyyyMMddHHmmss");
            }
            dateF.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
        }
        if (t()) {
            String frac = d.substring(14);
            int index = 1;
            while (index < frac.length() && '0' <= (ch = frac.charAt(index)) && ch <= '9') {
                index++;
            }
            if (index - 1 > 3) {
                d = d.substring(0, 14) + (frac.substring(0, 4) + frac.substring(index));
            } else if (index - 1 == 1) {
                d = d.substring(0, 14) + (frac.substring(0, index) + "00" + frac.substring(index));
            } else if (index - 1 == 2) {
                d = d.substring(0, 14) + (frac.substring(0, index) + "0" + frac.substring(index));
            }
        }
        return dateF.parse(d);
    }

    private boolean t() {
        int i = 0;
        while (true) {
            byte[] bArr = this.c;
            if (i == bArr.length) {
                return false;
            }
            if (bArr[i] == 46 && i == 14) {
                return true;
            }
            i++;
        }
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
        out.g(24, this.c);
    }

    /* access modifiers changed from: package-private */
    public boolean e(ASN1Primitive o) {
        if (!(o instanceof ASN1GeneralizedTime)) {
            return false;
        }
        return Arrays.b(this.c, ((ASN1GeneralizedTime) o).c);
    }

    public int hashCode() {
        return Arrays.J(this.c);
    }
}
