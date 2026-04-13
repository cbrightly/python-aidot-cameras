package org.spongycastle.jce.provider;

import com.meituan.robust.Constants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralSubtree;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;
import org.spongycastle.util.Strings;

public class PKIXNameConstraintValidator {
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

    private static boolean X(ASN1Sequence dns, ASN1Sequence subtree) {
        if (subtree.size() < 1 || subtree.size() > dns.size()) {
            return false;
        }
        for (int j2 = subtree.size() - 1; j2 >= 0; j2--) {
            if (!subtree.r(j2).equals(dns.r(j2))) {
                return false;
            }
        }
        return true;
    }

    public void k(ASN1Sequence dns) {
        j(this.f, dns);
    }

    public void d(ASN1Sequence dns) {
        c(this.a, dns);
    }

    private void j(Set permitted, ASN1Sequence dns) {
        if (permitted != null) {
            if (!permitted.isEmpty() || dns.size() != 0) {
                Iterator it = permitted.iterator();
                while (it.hasNext()) {
                    if (X(dns, (ASN1Sequence) it.next())) {
                        return;
                    }
                }
                throw new PKIXNameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
            }
        }
    }

    private void c(Set excluded, ASN1Sequence dns) {
        if (!excluded.isEmpty()) {
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                if (X(dns, (ASN1Sequence) it.next())) {
                    throw new PKIXNameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
                }
            }
        }
    }

    private Set x(Set permitted, Set dns) {
        Set intersect = new HashSet();
        Iterator it = dns.iterator();
        while (it.hasNext()) {
            ASN1Sequence dn = ASN1Sequence.o(((GeneralSubtree) it.next()).e().h().toASN1Primitive());
            if (permitted != null) {
                Iterator _iter = permitted.iterator();
                while (_iter.hasNext()) {
                    ASN1Sequence subtree = (ASN1Sequence) _iter.next();
                    if (X(dn, subtree)) {
                        intersect.add(dn);
                    } else if (X(subtree, dn)) {
                        intersect.add(subtree);
                    }
                }
            } else if (dn != null) {
                intersect.add(dn);
            }
        }
        return intersect;
    }

    private Set P(Set excluded, ASN1Sequence dn) {
        if (!excluded.isEmpty()) {
            Set intersect = new HashSet();
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                ASN1Sequence subtree = (ASN1Sequence) it.next();
                if (X(dn, subtree)) {
                    intersect.add(subtree);
                } else if (X(subtree, dn)) {
                    intersect.add(dn);
                } else {
                    intersect.add(subtree);
                    intersect.add(dn);
                }
            }
            return intersect;
        } else if (dn == null) {
            return excluded;
        } else {
            excluded.add(dn);
            return excluded;
        }
    }

    private Set z(Set permitted, Set emails) {
        Set intersect = new HashSet();
        Iterator it = emails.iterator();
        while (it.hasNext()) {
            String email = v(((GeneralSubtree) it.next()).e());
            if (permitted != null) {
                Iterator it2 = permitted.iterator();
                while (it2.hasNext()) {
                    A(email, (String) it2.next(), intersect);
                }
            } else if (email != null) {
                intersect.add(email);
            }
        }
        return intersect;
    }

    private Set R(Set excluded, String email) {
        if (!excluded.isEmpty()) {
            Set union = new HashSet();
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                S((String) it.next(), email, union);
            }
            return union;
        } else if (email == null) {
            return excluded;
        } else {
            excluded.add(email);
            return excluded;
        }
    }

    private Set B(Set permitted, Set ips) {
        Set intersect = new HashSet();
        Iterator it = ips.iterator();
        while (it.hasNext()) {
            byte[] ip = ASN1OctetString.o(((GeneralSubtree) it.next()).e().h()).q();
            if (permitted != null) {
                Iterator it2 = permitted.iterator();
                while (it2.hasNext()) {
                    intersect.addAll(C((byte[]) it2.next(), ip));
                }
            } else if (ip != null) {
                intersect.add(ip);
            }
        }
        return intersect;
    }

    private Set T(Set excluded, byte[] ip) {
        if (!excluded.isEmpty()) {
            Set union = new HashSet();
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                union.addAll(U((byte[]) it.next(), ip));
            }
            return union;
        } else if (ip == null) {
            return excluded;
        } else {
            excluded.add(ip);
            return excluded;
        }
    }

    private Set U(byte[] ipWithSubmask1, byte[] ipWithSubmask2) {
        Set set = new HashSet();
        if (Arrays.b(ipWithSubmask1, ipWithSubmask2)) {
            set.add(ipWithSubmask1);
        } else {
            set.add(ipWithSubmask1);
            set.add(ipWithSubmask2);
        }
        return set;
    }

    private Set C(byte[] ipWithSubmask1, byte[] ipWithSubmask2) {
        if (ipWithSubmask1.length != ipWithSubmask2.length) {
            return Collections.EMPTY_SET;
        }
        byte[][] temp = u(ipWithSubmask1, ipWithSubmask2);
        byte[] ip1 = temp[0];
        byte[] subnetmask1 = temp[1];
        byte[] ip2 = temp[2];
        byte[] subnetmask2 = temp[3];
        byte[][] minMax = L(ip1, subnetmask1, ip2, subnetmask2);
        if (q(J(minMax[0], minMax[2]), K(minMax[1], minMax[3])) == 1) {
            return Collections.EMPTY_SET;
        }
        return Collections.singleton(G(M(minMax[0], minMax[2]), M(subnetmask1, subnetmask2)));
    }

    private byte[] G(byte[] ip, byte[] subnetMask) {
        int ipLength = ip.length;
        byte[] temp = new byte[(ipLength * 2)];
        System.arraycopy(ip, 0, temp, 0, ipLength);
        System.arraycopy(subnetMask, 0, temp, ipLength, ipLength);
        return temp;
    }

    private byte[][] u(byte[] ipWithSubmask1, byte[] ipWithSubmask2) {
        int ipLength = ipWithSubmask1.length / 2;
        byte[] ip1 = new byte[ipLength];
        byte[] subnetmask1 = new byte[ipLength];
        System.arraycopy(ipWithSubmask1, 0, ip1, 0, ipLength);
        System.arraycopy(ipWithSubmask1, ipLength, subnetmask1, 0, ipLength);
        byte[] ip2 = new byte[ipLength];
        byte[] subnetmask2 = new byte[ipLength];
        System.arraycopy(ipWithSubmask2, 0, ip2, 0, ipLength);
        System.arraycopy(ipWithSubmask2, ipLength, subnetmask2, 0, ipLength);
        return new byte[][]{ip1, subnetmask1, ip2, subnetmask2};
    }

    private byte[][] L(byte[] ip1, byte[] subnetmask1, byte[] ip2, byte[] subnetmask2) {
        int ipLength = ip1.length;
        byte[] min1 = new byte[ipLength];
        byte[] max1 = new byte[ipLength];
        byte[] min2 = new byte[ipLength];
        byte[] max2 = new byte[ipLength];
        for (int i2 = 0; i2 < ipLength; i2++) {
            min1[i2] = (byte) (ip1[i2] & subnetmask1[i2]);
            max1[i2] = (byte) ((ip1[i2] & subnetmask1[i2]) | (~subnetmask1[i2]));
            min2[i2] = (byte) (ip2[i2] & subnetmask2[i2]);
            max2[i2] = (byte) ((ip2[i2] & subnetmask2[i2]) | (~subnetmask2[i2]));
        }
        return new byte[][]{min1, max1, min2, max2};
    }

    private void m(Set permitted, String email) {
        if (permitted != null) {
            Iterator it = permitted.iterator();
            while (it.hasNext()) {
                if (r(email, (String) it.next())) {
                    return;
                }
            }
            if (email.length() != 0 || permitted.size() != 0) {
                throw new PKIXNameConstraintValidatorException("Subject email address is not from a permitted subtree.");
            }
        }
    }

    private void f(Set excluded, String email) {
        if (!excluded.isEmpty()) {
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                if (r(email, (String) it.next())) {
                    throw new PKIXNameConstraintValidatorException("Email address is from an excluded subtree.");
                }
            }
        }
    }

    private void n(Set permitted, byte[] ip) {
        if (permitted != null) {
            Iterator it = permitted.iterator();
            while (it.hasNext()) {
                if (H(ip, (byte[]) it.next())) {
                    return;
                }
            }
            if (ip.length != 0 || permitted.size() != 0) {
                throw new PKIXNameConstraintValidatorException("IP is not from a permitted subtree.");
            }
        }
    }

    private void g(Set excluded, byte[] ip) {
        if (!excluded.isEmpty()) {
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                if (H(ip, (byte[]) it.next())) {
                    throw new PKIXNameConstraintValidatorException("IP is from an excluded subtree.");
                }
            }
        }
    }

    private boolean H(byte[] ip, byte[] constraint) {
        int ipLength = ip.length;
        if (ipLength != constraint.length / 2) {
            return false;
        }
        byte[] subnetMask = new byte[ipLength];
        System.arraycopy(constraint, ipLength, subnetMask, 0, ipLength);
        byte[] permittedSubnetAddress = new byte[ipLength];
        byte[] ipSubnetAddress = new byte[ipLength];
        for (int i2 = 0; i2 < ipLength; i2++) {
            permittedSubnetAddress[i2] = (byte) (constraint[i2] & subnetMask[i2]);
            ipSubnetAddress[i2] = (byte) (ip[i2] & subnetMask[i2]);
        }
        return Arrays.b(permittedSubnetAddress, ipSubnetAddress);
    }

    private boolean r(String email, String constraint) {
        String sub = email.substring(email.indexOf(64) + 1);
        if (constraint.indexOf(64) != -1) {
            if (!email.equalsIgnoreCase(constraint) && !sub.equalsIgnoreCase(constraint.substring(1))) {
                return false;
            }
            return true;
        } else if (constraint.charAt(0) != '.') {
            if (sub.equalsIgnoreCase(constraint)) {
                return true;
            }
        } else if (Y(sub, constraint)) {
            return true;
        }
        return false;
    }

    private boolean Y(String testDomain, String domain) {
        String tempDomain = domain;
        if (tempDomain.startsWith(".")) {
            tempDomain = tempDomain.substring(1);
        }
        String[] domainParts = Strings.e(tempDomain, '.');
        String[] testDomainParts = Strings.e(testDomain, '.');
        if (testDomainParts.length <= domainParts.length) {
            return false;
        }
        int d2 = testDomainParts.length - domainParts.length;
        for (int i2 = -1; i2 < domainParts.length; i2++) {
            if (i2 == -1) {
                if (testDomainParts[i2 + d2].equals("")) {
                    return false;
                }
            } else if (!domainParts[i2].equalsIgnoreCase(testDomainParts[i2 + d2])) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:5:0x000d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void l(java.util.Set r4, java.lang.String r5) {
        /*
            r3 = this;
            if (r4 != 0) goto L_0x0003
            return
        L_0x0003:
            java.util.Iterator r0 = r4.iterator()
        L_0x0007:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0022
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r3.Y(r5, r1)
            if (r2 != 0) goto L_0x0021
            boolean r2 = r5.equalsIgnoreCase(r1)
            if (r2 == 0) goto L_0x0020
            goto L_0x0021
        L_0x0020:
            goto L_0x0007
        L_0x0021:
            return
        L_0x0022:
            int r1 = r5.length()
            if (r1 != 0) goto L_0x002f
            int r1 = r4.size()
            if (r1 != 0) goto L_0x002f
            return
        L_0x002f:
            org.spongycastle.jce.provider.PKIXNameConstraintValidatorException r1 = new org.spongycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r2 = "DNS is not from a permitted subtree."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.PKIXNameConstraintValidator.l(java.util.Set, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:6:0x0011  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void e(java.util.Set r5, java.lang.String r6) {
        /*
            r4 = this;
            boolean r0 = r5.isEmpty()
            if (r0 == 0) goto L_0x0007
            return
        L_0x0007:
            java.util.Iterator r0 = r5.iterator()
        L_0x000b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x002c
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            boolean r2 = r4.Y(r6, r1)
            if (r2 != 0) goto L_0x0024
            boolean r2 = r6.equalsIgnoreCase(r1)
            if (r2 != 0) goto L_0x0024
            goto L_0x000b
        L_0x0024:
            org.spongycastle.jce.provider.PKIXNameConstraintValidatorException r2 = new org.spongycastle.jce.provider.PKIXNameConstraintValidatorException
            java.lang.String r3 = "DNS is from an excluded subtree."
            r2.<init>(r3)
            throw r2
        L_0x002c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jce.provider.PKIXNameConstraintValidator.e(java.util.Set, java.lang.String):void");
    }

    private void S(String email1, String email2, Set union) {
        if (email1.indexOf(64) != -1) {
            String _sub = email1.substring(email1.indexOf(64) + 1);
            if (email2.indexOf(64) != -1) {
                if (email1.equalsIgnoreCase(email2)) {
                    union.add(email1);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (email2.startsWith(".")) {
                if (Y(_sub, email2)) {
                    union.add(email2);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (_sub.equalsIgnoreCase(email2)) {
                union.add(email2);
            } else {
                union.add(email1);
                union.add(email2);
            }
        } else if (email1.startsWith(".")) {
            if (email2.indexOf(64) != -1) {
                if (Y(email2.substring(email1.indexOf(64) + 1), email1)) {
                    union.add(email1);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (email2.startsWith(".")) {
                if (Y(email1, email2) || email1.equalsIgnoreCase(email2)) {
                    union.add(email2);
                } else if (Y(email2, email1)) {
                    union.add(email1);
                } else {
                    union.add(email1);
                    union.add(email2);
                }
            } else if (Y(email2, email1)) {
                union.add(email1);
            } else {
                union.add(email1);
                union.add(email2);
            }
        } else if (email2.indexOf(64) != -1) {
            if (email2.substring(email1.indexOf(64) + 1).equalsIgnoreCase(email1)) {
                union.add(email1);
                return;
            }
            union.add(email1);
            union.add(email2);
        } else if (email2.startsWith(".")) {
            if (Y(email1, email2)) {
                union.add(email2);
                return;
            }
            union.add(email1);
            union.add(email2);
        } else if (email1.equalsIgnoreCase(email2)) {
            union.add(email1);
        } else {
            union.add(email1);
            union.add(email2);
        }
    }

    private void W(String email1, String email2, Set union) {
        if (email1.indexOf(64) != -1) {
            String _sub = email1.substring(email1.indexOf(64) + 1);
            if (email2.indexOf(64) != -1) {
                if (email1.equalsIgnoreCase(email2)) {
                    union.add(email1);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (email2.startsWith(".")) {
                if (Y(_sub, email2)) {
                    union.add(email2);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (_sub.equalsIgnoreCase(email2)) {
                union.add(email2);
            } else {
                union.add(email1);
                union.add(email2);
            }
        } else if (email1.startsWith(".")) {
            if (email2.indexOf(64) != -1) {
                if (Y(email2.substring(email1.indexOf(64) + 1), email1)) {
                    union.add(email1);
                    return;
                }
                union.add(email1);
                union.add(email2);
            } else if (email2.startsWith(".")) {
                if (Y(email1, email2) || email1.equalsIgnoreCase(email2)) {
                    union.add(email2);
                } else if (Y(email2, email1)) {
                    union.add(email1);
                } else {
                    union.add(email1);
                    union.add(email2);
                }
            } else if (Y(email2, email1)) {
                union.add(email1);
            } else {
                union.add(email1);
                union.add(email2);
            }
        } else if (email2.indexOf(64) != -1) {
            if (email2.substring(email1.indexOf(64) + 1).equalsIgnoreCase(email1)) {
                union.add(email1);
                return;
            }
            union.add(email1);
            union.add(email2);
        } else if (email2.startsWith(".")) {
            if (Y(email1, email2)) {
                union.add(email2);
                return;
            }
            union.add(email1);
            union.add(email2);
        } else if (email1.equalsIgnoreCase(email2)) {
            union.add(email1);
        } else {
            union.add(email1);
            union.add(email2);
        }
    }

    private Set y(Set permitted, Set dnss) {
        Set intersect = new HashSet();
        Iterator it = dnss.iterator();
        while (it.hasNext()) {
            String dns = v(((GeneralSubtree) it.next()).e());
            if (permitted != null) {
                Iterator _iter = permitted.iterator();
                while (_iter.hasNext()) {
                    String _permitted = (String) _iter.next();
                    if (Y(_permitted, dns)) {
                        intersect.add(_permitted);
                    } else if (Y(dns, _permitted)) {
                        intersect.add(dns);
                    }
                }
            } else if (dns != null) {
                intersect.add(dns);
            }
        }
        return intersect;
    }

    /* access modifiers changed from: protected */
    public Set Q(Set excluded, String dns) {
        if (!excluded.isEmpty()) {
            Set union = new HashSet();
            Iterator _iter = excluded.iterator();
            while (_iter.hasNext()) {
                String _permitted = (String) _iter.next();
                if (Y(_permitted, dns)) {
                    union.add(dns);
                } else if (Y(dns, _permitted)) {
                    union.add(_permitted);
                } else {
                    union.add(_permitted);
                    union.add(dns);
                }
            }
            return union;
        } else if (dns == null) {
            return excluded;
        } else {
            excluded.add(dns);
            return excluded;
        }
    }

    private void A(String email1, String email2, Set intersect) {
        if (email1.indexOf(64) != -1) {
            String _sub = email1.substring(email1.indexOf(64) + 1);
            if (email2.indexOf(64) != -1) {
                if (email1.equalsIgnoreCase(email2)) {
                    intersect.add(email1);
                }
            } else if (email2.startsWith(".")) {
                if (Y(_sub, email2)) {
                    intersect.add(email1);
                }
            } else if (_sub.equalsIgnoreCase(email2)) {
                intersect.add(email1);
            }
        } else if (email1.startsWith(".")) {
            if (email2.indexOf(64) != -1) {
                if (Y(email2.substring(email1.indexOf(64) + 1), email1)) {
                    intersect.add(email2);
                }
            } else if (email2.startsWith(".")) {
                if (Y(email1, email2) || email1.equalsIgnoreCase(email2)) {
                    intersect.add(email1);
                } else if (Y(email2, email1)) {
                    intersect.add(email2);
                }
            } else if (Y(email2, email1)) {
                intersect.add(email2);
            }
        } else if (email2.indexOf(64) != -1) {
            if (email2.substring(email2.indexOf(64) + 1).equalsIgnoreCase(email1)) {
                intersect.add(email2);
            }
        } else if (email2.startsWith(".")) {
            if (Y(email1, email2)) {
                intersect.add(email1);
            }
        } else if (email1.equalsIgnoreCase(email2)) {
            intersect.add(email1);
        }
    }

    private void h(Set excluded, String uri) {
        if (!excluded.isEmpty()) {
            Iterator it = excluded.iterator();
            while (it.hasNext()) {
                if (I(uri, (String) it.next())) {
                    throw new PKIXNameConstraintValidatorException("URI is from an excluded subtree.");
                }
            }
        }
    }

    private Set E(Set permitted, Set uris) {
        Set intersect = new HashSet();
        Iterator it = uris.iterator();
        while (it.hasNext()) {
            String uri = v(((GeneralSubtree) it.next()).e());
            if (permitted != null) {
                Iterator _iter = permitted.iterator();
                while (_iter.hasNext()) {
                    F((String) _iter.next(), uri, intersect);
                }
            } else if (uri != null) {
                intersect.add(uri);
            }
        }
        return intersect;
    }

    private Set V(Set excluded, String uri) {
        if (!excluded.isEmpty()) {
            Set union = new HashSet();
            Iterator _iter = excluded.iterator();
            while (_iter.hasNext()) {
                W((String) _iter.next(), uri, union);
            }
            return union;
        } else if (uri == null) {
            return excluded;
        } else {
            excluded.add(uri);
            return excluded;
        }
    }

    private void F(String email1, String email2, Set intersect) {
        if (email1.indexOf(64) != -1) {
            String _sub = email1.substring(email1.indexOf(64) + 1);
            if (email2.indexOf(64) != -1) {
                if (email1.equalsIgnoreCase(email2)) {
                    intersect.add(email1);
                }
            } else if (email2.startsWith(".")) {
                if (Y(_sub, email2)) {
                    intersect.add(email1);
                }
            } else if (_sub.equalsIgnoreCase(email2)) {
                intersect.add(email1);
            }
        } else if (email1.startsWith(".")) {
            if (email2.indexOf(64) != -1) {
                if (Y(email2.substring(email1.indexOf(64) + 1), email1)) {
                    intersect.add(email2);
                }
            } else if (email2.startsWith(".")) {
                if (Y(email1, email2) || email1.equalsIgnoreCase(email2)) {
                    intersect.add(email1);
                } else if (Y(email2, email1)) {
                    intersect.add(email2);
                }
            } else if (Y(email2, email1)) {
                intersect.add(email2);
            }
        } else if (email2.indexOf(64) != -1) {
            if (email2.substring(email2.indexOf(64) + 1).equalsIgnoreCase(email1)) {
                intersect.add(email2);
            }
        } else if (email2.startsWith(".")) {
            if (Y(email1, email2)) {
                intersect.add(email1);
            }
        } else if (email1.equalsIgnoreCase(email2)) {
            intersect.add(email1);
        }
    }

    private void o(Set permitted, String uri) {
        if (permitted != null) {
            Iterator it = permitted.iterator();
            while (it.hasNext()) {
                if (I(uri, (String) it.next())) {
                    return;
                }
            }
            if (uri.length() != 0 || permitted.size() != 0) {
                throw new PKIXNameConstraintValidatorException("URI is not from a permitted subtree.");
            }
        }
    }

    private boolean I(String uri, String constraint) {
        String host = t(uri);
        if (!constraint.startsWith(".")) {
            if (host.equalsIgnoreCase(constraint)) {
                return true;
            }
            return false;
        } else if (Y(host, constraint)) {
            return true;
        } else {
            return false;
        }
    }

    private static String t(String url) {
        String sub = url.substring(url.indexOf(58) + 1);
        if (sub.indexOf("//") != -1) {
            sub = sub.substring(sub.indexOf("//") + 2);
        }
        if (sub.lastIndexOf(58) != -1) {
            sub = sub.substring(0, sub.lastIndexOf(58));
        }
        String sub2 = sub.substring(sub.indexOf(58) + 1);
        String sub3 = sub2.substring(sub2.indexOf(64) + 1);
        if (sub3.indexOf(47) != -1) {
            return sub3.substring(0, sub3.indexOf(47));
        }
        return sub3;
    }

    public void i(GeneralName name) {
        switch (name.i()) {
            case 1:
                m(this.h, v(name));
                return;
            case 2:
                l(this.g, DERIA5String.o(name.h()).a());
                return;
            case 4:
                k(ASN1Sequence.o(name.h().toASN1Primitive()));
                return;
            case 6:
                o(this.i, DERIA5String.o(name.h()).a());
                return;
            case 7:
                n(this.j, ASN1OctetString.o(name.h()).q());
                return;
            default:
                return;
        }
    }

    public void b(GeneralName name) {
        switch (name.i()) {
            case 1:
                f(this.c, v(name));
                return;
            case 2:
                e(this.b, DERIA5String.o(name.h()).a());
                return;
            case 4:
                d(ASN1Sequence.o(name.h().toASN1Primitive()));
                return;
            case 6:
                h(this.d, DERIA5String.o(name.h()).a());
                return;
            case 7:
                g(this.e, ASN1OctetString.o(name.h()).q());
                return;
            default:
                return;
        }
    }

    public void D(GeneralSubtree[] permitted) {
        Map subtreesMap = new HashMap();
        for (int i2 = 0; i2 != permitted.length; i2++) {
            GeneralSubtree subtree = permitted[i2];
            Integer tagNo = Integers.b(subtree.e().i());
            if (subtreesMap.get(tagNo) == null) {
                subtreesMap.put(tagNo, new HashSet());
            }
            ((Set) subtreesMap.get(tagNo)).add(subtree);
        }
        for (Map.Entry entry : subtreesMap.entrySet()) {
            switch (((Integer) entry.getKey()).intValue()) {
                case 1:
                    this.h = z(this.h, (Set) entry.getValue());
                    break;
                case 2:
                    this.g = y(this.g, (Set) entry.getValue());
                    break;
                case 4:
                    this.f = x(this.f, (Set) entry.getValue());
                    break;
                case 6:
                    this.i = E(this.i, (Set) entry.getValue());
                    break;
                case 7:
                    this.j = B(this.j, (Set) entry.getValue());
                    break;
            }
        }
    }

    private String v(GeneralName name) {
        return DERIA5String.o(name.h()).a();
    }

    public void a(GeneralSubtree subtree) {
        GeneralName base = subtree.e();
        switch (base.i()) {
            case 1:
                this.c = R(this.c, v(base));
                return;
            case 2:
                this.b = Q(this.b, v(base));
                return;
            case 4:
                this.a = P(this.a, (ASN1Sequence) base.h().toASN1Primitive());
                return;
            case 6:
                this.d = V(this.d, v(base));
                return;
            case 7:
                this.e = T(this.e, ASN1OctetString.o(base.h()).q());
                return;
            default:
                return;
        }
    }

    private static byte[] J(byte[] ip1, byte[] ip2) {
        for (int i2 = 0; i2 < ip1.length; i2++) {
            if ((ip1[i2] & 65535) > (65535 & ip2[i2])) {
                return ip1;
            }
        }
        return ip2;
    }

    private static byte[] K(byte[] ip1, byte[] ip2) {
        for (int i2 = 0; i2 < ip1.length; i2++) {
            if ((ip1[i2] & 65535) < (65535 & ip2[i2])) {
                return ip1;
            }
        }
        return ip2;
    }

    private static int q(byte[] ip1, byte[] ip2) {
        if (Arrays.b(ip1, ip2)) {
            return 0;
        }
        if (Arrays.b(J(ip1, ip2), ip1)) {
            return 1;
        }
        return -1;
    }

    private static byte[] M(byte[] ip1, byte[] ip2) {
        byte[] temp = new byte[ip1.length];
        for (int i2 = 0; i2 < ip1.length; i2++) {
            temp[i2] = (byte) (ip1[i2] | ip2[i2]);
        }
        return temp;
    }

    public int hashCode() {
        return w(this.a) + w(this.b) + w(this.c) + w(this.e) + w(this.d) + w(this.f) + w(this.g) + w(this.h) + w(this.j) + w(this.i);
    }

    private int w(Collection coll) {
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

    public boolean equals(Object o) {
        if (!(o instanceof PKIXNameConstraintValidator)) {
            return false;
        }
        PKIXNameConstraintValidator constraintValidator = (PKIXNameConstraintValidator) o;
        if (!p(constraintValidator.a, this.a) || !p(constraintValidator.b, this.b) || !p(constraintValidator.c, this.c) || !p(constraintValidator.e, this.e) || !p(constraintValidator.d, this.d) || !p(constraintValidator.f, this.f) || !p(constraintValidator.g, this.g) || !p(constraintValidator.h, this.h) || !p(constraintValidator.j, this.j) || !p(constraintValidator.i, this.i)) {
            return false;
        }
        return true;
    }

    private boolean p(Collection coll1, Collection coll2) {
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
                    if (s(a2, it2.next())) {
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

    private boolean s(Object o1, Object o2) {
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

    private String N(byte[] ip) {
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

    private String O(Set ips) {
        String temp = "" + Constants.ARRAY_TYPE;
        Iterator it = ips.iterator();
        while (it.hasNext()) {
            temp = temp + N((byte[]) it.next()) + ",";
        }
        if (temp.length() > 1) {
            temp = temp.substring(0, temp.length() - 1);
        }
        return temp + "]";
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
            temp = (temp + "IP:\n") + O(this.j) + "\n";
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
        return (temp2 + "IP:\n") + O(this.e) + "\n";
    }
}
