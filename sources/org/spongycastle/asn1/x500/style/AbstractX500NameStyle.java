package org.spongycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1ParsingException;
import org.spongycastle.asn1.DERUTF8String;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x500.X500NameStyle;

public abstract class AbstractX500NameStyle implements X500NameStyle {
    public static Hashtable h(Hashtable paramsMap) {
        Hashtable newTable = new Hashtable();
        Enumeration keys = paramsMap.keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            newTable.put(key, paramsMap.get(key));
        }
        return newTable;
    }

    private int g(ASN1Encodable enc) {
        return IETFUtils.e(IETFUtils.q(enc)).hashCode();
    }

    public int f(X500Name name) {
        int hashCodeValue = 0;
        RDN[] rdns = name.h();
        for (int i = 0; i != rdns.length; i++) {
            if (rdns[i].h()) {
                AttributeTypeAndValue[] atv = rdns[i].g();
                for (int j = 0; j != atv.length; j++) {
                    hashCodeValue = (hashCodeValue ^ atv[j].f().hashCode()) ^ g(atv[j].g());
                }
            } else {
                hashCodeValue = (hashCodeValue ^ rdns[i].e().f().hashCode()) ^ g(rdns[i].e().g());
            }
        }
        return hashCodeValue;
    }

    public ASN1Encodable e(ASN1ObjectIdentifier oid, String value) {
        if (value.length() == 0 || value.charAt(0) != '#') {
            if (value.length() != 0 && value.charAt(0) == '\\') {
                value = value.substring(1);
            }
            return i(oid, value);
        }
        try {
            return IETFUtils.p(value, 1);
        } catch (IOException e) {
            throw new ASN1ParsingException("can't recode value for oid " + oid.s());
        }
    }

    /* access modifiers changed from: protected */
    public ASN1Encodable i(ASN1ObjectIdentifier oid, String value) {
        return new DERUTF8String(value);
    }

    public boolean d(X500Name name1, X500Name name2) {
        RDN[] rdns1 = name1.h();
        RDN[] rdns2 = name2.h();
        if (rdns1.length != rdns2.length) {
            return false;
        }
        boolean reverse = false;
        if (!(rdns1[0].e() == null || rdns2[0].e() == null)) {
            reverse = !rdns1[0].e().f().equals(rdns2[0].e().f());
        }
        for (int i = 0; i != rdns1.length; i++) {
            if (!j(reverse, rdns1[i], rdns2)) {
                return false;
            }
        }
        return true;
    }

    private boolean j(boolean reverse, RDN rdn, RDN[] possRDNs) {
        if (reverse) {
            int i = possRDNs.length - 1;
            while (i >= 0) {
                if (possRDNs[i] == null || !k(rdn, possRDNs[i])) {
                    i--;
                } else {
                    possRDNs[i] = null;
                    return true;
                }
            }
            return false;
        }
        int i2 = 0;
        while (i2 != possRDNs.length) {
            if (possRDNs[i2] == null || !k(rdn, possRDNs[i2])) {
                i2++;
            } else {
                possRDNs[i2] = null;
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean k(RDN rdn1, RDN rdn2) {
        return IETFUtils.j(rdn1, rdn2);
    }
}
