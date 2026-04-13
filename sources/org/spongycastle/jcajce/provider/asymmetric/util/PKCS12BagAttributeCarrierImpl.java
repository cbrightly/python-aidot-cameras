package org.spongycastle.jcajce.provider.asymmetric.util;

import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OutputStream;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class PKCS12BagAttributeCarrierImpl implements PKCS12BagAttributeCarrier {
    private Hashtable c;
    private Vector d;

    PKCS12BagAttributeCarrierImpl(Hashtable attributes, Vector ordering) {
        this.c = attributes;
        this.d = ordering;
    }

    public PKCS12BagAttributeCarrierImpl() {
        this(new Hashtable(), new Vector());
    }

    public void setBagAttribute(ASN1ObjectIdentifier oid, ASN1Encodable attribute) {
        if (this.c.containsKey(oid)) {
            this.c.put(oid, attribute);
            return;
        }
        this.c.put(oid, attribute);
        this.d.addElement(oid);
    }

    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier oid) {
        return (ASN1Encodable) this.c.get(oid);
    }

    public Enumeration getBagAttributeKeys() {
        return this.d.elements();
    }

    public void b(ObjectOutputStream out) {
        if (this.d.size() == 0) {
            out.writeObject(new Hashtable());
            out.writeObject(new Vector());
            return;
        }
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        ASN1OutputStream aOut = new ASN1OutputStream(bOut);
        Enumeration e = getBagAttributeKeys();
        while (e.hasMoreElements()) {
            ASN1ObjectIdentifier oid = (ASN1ObjectIdentifier) e.nextElement();
            aOut.j(oid);
            aOut.j((ASN1Encodable) this.c.get(oid));
        }
        out.writeObject(bOut.toByteArray());
    }

    public void a(ObjectInputStream in) {
        Object obj = in.readObject();
        if (obj instanceof Hashtable) {
            this.c = (Hashtable) obj;
            this.d = (Vector) in.readObject();
            return;
        }
        ASN1InputStream aIn = new ASN1InputStream((byte[]) obj);
        while (true) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aIn.r();
            ASN1ObjectIdentifier oid = aSN1ObjectIdentifier;
            if (aSN1ObjectIdentifier != null) {
                setBagAttribute(oid, aIn.r());
            } else {
                return;
            }
        }
    }
}
