package org.spongycastle.asn1.x509;

import java.text.ParseException;
import java.util.Date;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1UTCTime;

public class Time extends ASN1Object implements ASN1Choice {
    ASN1Primitive c;

    public Time(ASN1Primitive time) {
        if ((time instanceof ASN1UTCTime) || (time instanceof ASN1GeneralizedTime)) {
            this.c = time;
            return;
        }
        throw new IllegalArgumentException("unknown object passed to Time");
    }

    public static Time f(Object obj) {
        if (obj == null || (obj instanceof Time)) {
            return (Time) obj;
        }
        if (obj instanceof ASN1UTCTime) {
            return new Time((ASN1UTCTime) obj);
        }
        if (obj instanceof ASN1GeneralizedTime) {
            return new Time((ASN1GeneralizedTime) obj);
        }
        throw new IllegalArgumentException("unknown object in factory: " + obj.getClass().getName());
    }

    public String g() {
        ASN1Primitive aSN1Primitive = this.c;
        if (aSN1Primitive instanceof ASN1UTCTime) {
            return ((ASN1UTCTime) aSN1Primitive).p();
        }
        return ((ASN1GeneralizedTime) aSN1Primitive).s();
    }

    public Date e() {
        try {
            ASN1Primitive aSN1Primitive = this.c;
            if (aSN1Primitive instanceof ASN1UTCTime) {
                return ((ASN1UTCTime) aSN1Primitive).o();
            }
            return ((ASN1GeneralizedTime) aSN1Primitive).q();
        } catch (ParseException e) {
            throw new IllegalStateException("invalid date string: " + e.getMessage());
        }
    }

    public ASN1Primitive toASN1Primitive() {
        return this.c;
    }

    public String toString() {
        return g();
    }
}
