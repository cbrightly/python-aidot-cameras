package org.spongycastle.asn1.x509;

import com.meituan.robust.Constants;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.spongycastle.util.Arrays;

public class PKIXNameConstraintValidator implements NameConstraintValidator {
    private Set a = new HashSet();
    private Set b = new HashSet();
    private Set c = new HashSet();
    private Set d = new HashSet();
    private Set e = new HashSet();
    private Set f;
    private Set g;
    private Set h;
    private Set i;
    private Set j;

    public int hashCode() {
        return c(this.a) + c(this.b) + c(this.c) + c(this.e) + c(this.d) + c(this.f) + c(this.g) + c(this.h) + c(this.j) + c(this.i);
    }

    public boolean equals(Object o) {
        if (!(o instanceof PKIXNameConstraintValidator)) {
            return false;
        }
        PKIXNameConstraintValidator constraintValidator = (PKIXNameConstraintValidator) o;
        if (!a(constraintValidator.a, this.a) || !a(constraintValidator.b, this.b) || !a(constraintValidator.c, this.c) || !a(constraintValidator.e, this.e) || !a(constraintValidator.d, this.d) || !a(constraintValidator.f, this.f) || !a(constraintValidator.g, this.g) || !a(constraintValidator.h, this.h) || !a(constraintValidator.j, this.j) || !a(constraintValidator.i, this.i)) {
            return false;
        }
        return true;
    }

    public String toString() {
        String temp = "" + "permitted:\n";
        if (this.f != null) {
            temp = (temp + "DN:\n") + this.f.toString() + "\n";
        }
        if (this.g != null) {
            temp = (temp + "DNS:\n") + this.g.toString() + "\n";
        }
        if (this.h != null) {
            temp = (temp + "Email:\n") + this.h.toString() + "\n";
        }
        if (this.i != null) {
            temp = (temp + "URI:\n") + this.i.toString() + "\n";
        }
        if (this.j != null) {
            temp = (temp + "IP:\n") + e(this.j) + "\n";
        }
        String temp2 = temp + "excluded:\n";
        if (!this.a.isEmpty()) {
            temp2 = (temp2 + "DN:\n") + this.a.toString() + "\n";
        }
        if (!this.b.isEmpty()) {
            temp2 = (temp2 + "DNS:\n") + this.b.toString() + "\n";
        }
        if (!this.c.isEmpty()) {
            temp2 = (temp2 + "Email:\n") + this.c.toString() + "\n";
        }
        if (!this.d.isEmpty()) {
            temp2 = (temp2 + "URI:\n") + this.d.toString() + "\n";
        }
        if (this.e.isEmpty()) {
            return temp2;
        }
        return (temp2 + "IP:\n") + e(this.e) + "\n";
    }

    private int c(Collection coll) {
        if (coll == null) {
            return 0;
        }
        int hash = 0;
        for (Object o : coll) {
            if (o instanceof byte[]) {
                hash += Arrays.J((byte[]) o);
            } else {
                hash += o.hashCode();
            }
        }
        return hash;
    }

    private boolean a(Collection coll1, Collection coll2) {
        if (coll1 == coll2) {
            return true;
        }
        if (coll1 == null || coll2 == null || coll1.size() != coll2.size()) {
            return false;
        }
        for (Object a2 : coll1) {
            Iterator it2 = coll2.iterator();
            boolean found = false;
            while (true) {
                if (it2.hasNext()) {
                    if (b(a2, it2.next())) {
                        found = true;
                        continue;
                        break;
                    }
                } else {
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    private boolean b(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (!(o1 instanceof byte[]) || !(o2 instanceof byte[])) {
            return o1.equals(o2);
        }
        return Arrays.b((byte[]) o1, (byte[]) o2);
    }

    private String d(byte[] ip) {
        String temp = "";
        for (int i2 = 0; i2 < ip.length / 2; i2++) {
            temp = temp + Integer.toString(ip[i2] & 255) + ".";
        }
        String temp2 = temp.substring(0, temp.length() - 1) + "/";
        for (int i3 = ip.length / 2; i3 < ip.length; i3++) {
            temp2 = temp2 + Integer.toString(ip[i3] & 255) + ".";
        }
        return temp2.substring(0, temp2.length() - 1);
    }

    private String e(Set ips) {
        String temp = "" + Constants.ARRAY_TYPE;
        Iterator it = ips.iterator();
        while (it.hasNext()) {
            temp = temp + d((byte[]) it.next()) + ",";
        }
        if (temp.length() > 1) {
            temp = temp.substring(0, temp.length() - 1);
        }
        return temp + "]";
    }
}
