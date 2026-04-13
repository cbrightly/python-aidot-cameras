package org.apache.http.impl.auth;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.SecureRandom;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.j;
import org.apache.http.auth.l;
import org.apache.http.b;
import org.apache.http.o;
import org.apache.http.protocol.a;
import org.apache.http.protocol.f;

/* compiled from: DigestScheme */
public class d extends o {
    private static final char[] d = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final long serialVersionUID = 3883908186234566916L;
    private String a1;
    private String a2;
    private String cnonce;
    private boolean complete;
    private String lastNonce;
    private long nounceCount;

    public d(Charset credentialsCharset) {
        super(credentialsCharset);
        this.complete = false;
    }

    @Deprecated
    public d(j challengeState) {
        super(challengeState);
    }

    public d() {
        this(b.b);
    }

    public void processChallenge(org.apache.http.d header) {
        super.processChallenge(header);
        this.complete = true;
        if (getParameters().isEmpty()) {
            throw new MalformedChallengeException("Authentication challenge is empty");
        }
    }

    public boolean isComplete() {
        if ("true".equalsIgnoreCase(getParameter("stale"))) {
            return false;
        }
        return this.complete;
    }

    public String getSchemeName() {
        return "digest";
    }

    public boolean isConnectionBased() {
        return false;
    }

    public void overrideParamter(String name, String value) {
        getParameters().put(name, value);
    }

    @Deprecated
    public org.apache.http.d authenticate(l credentials, o request) {
        return authenticate(credentials, request, new a());
    }

    public org.apache.http.d authenticate(l credentials, o request, f context) {
        org.apache.http.util.a.i(credentials, "Credentials");
        org.apache.http.util.a.i(request, "HTTP request");
        if (getParameter("realm") == null) {
            throw new AuthenticationException("missing realm in challenge");
        } else if (getParameter("nonce") != null) {
            getParameters().put("methodname", request.r().getMethod());
            getParameters().put("uri", request.r().getUri());
            if (getParameter("charset") == null) {
                getParameters().put("charset", getCredentialsCharset(request));
            }
            return a(credentials, request);
        } else {
            throw new AuthenticationException("missing nonce in challenge");
        }
    }

    private static MessageDigest b(String digAlg) {
        try {
            return MessageDigest.getInstance(digAlg);
        } catch (Exception e) {
            throw new UnsupportedDigestAlgorithmException("Unsupported algorithm in HTTP Digest authentication: " + digAlg);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:100:0x03a3  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x02a3  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x02f9  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x02ff  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x0348  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0371  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0383  */
    /* JADX WARNING: Removed duplicated region for block: B:96:0x0396  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.apache.http.d a(org.apache.http.auth.l r36, org.apache.http.o r37) {
        /*
            r35 = this;
            r1 = r35
            r2 = r37
            java.lang.String r0 = "uri"
            java.lang.String r3 = r1.getParameter(r0)
            java.lang.String r4 = "realm"
            java.lang.String r5 = r1.getParameter(r4)
            java.lang.String r6 = "nonce"
            java.lang.String r7 = r1.getParameter(r6)
            java.lang.String r8 = "opaque"
            java.lang.String r9 = r1.getParameter(r8)
            java.lang.String r10 = "methodname"
            java.lang.String r10 = r1.getParameter(r10)
            java.lang.String r11 = "algorithm"
            java.lang.String r12 = r1.getParameter(r11)
            if (r12 != 0) goto L_0x002c
            java.lang.String r12 = "MD5"
        L_0x002c:
            java.util.HashSet r13 = new java.util.HashSet
            r14 = 8
            r13.<init>(r14)
            r14 = -1
            java.lang.String r15 = "qop"
            r16 = r14
            java.lang.String r14 = r1.getParameter(r15)
            r17 = r8
            java.lang.String r8 = "auth-int"
            r18 = r9
            java.lang.String r9 = "auth"
            if (r14 == 0) goto L_0x0086
            r19 = r11
            java.util.StringTokenizer r11 = new java.util.StringTokenizer
            r20 = r15
            java.lang.String r15 = ","
            r11.<init>(r14, r15)
        L_0x0051:
            boolean r15 = r11.hasMoreTokens()
            if (r15 == 0) goto L_0x006d
            java.lang.String r15 = r11.nextToken()
            java.lang.String r15 = r15.trim()
            r21 = r11
            java.util.Locale r11 = java.util.Locale.ROOT
            java.lang.String r11 = r15.toLowerCase(r11)
            r13.add(r11)
            r11 = r21
            goto L_0x0051
        L_0x006d:
            r21 = r11
            boolean r11 = r2 instanceof org.apache.http.k
            if (r11 == 0) goto L_0x007b
            boolean r11 = r13.contains(r8)
            if (r11 == 0) goto L_0x007b
            r11 = 1
            goto L_0x0085
        L_0x007b:
            boolean r11 = r13.contains(r9)
            if (r11 == 0) goto L_0x0083
            r11 = 2
            goto L_0x0085
        L_0x0083:
            r11 = r16
        L_0x0085:
            goto L_0x008b
        L_0x0086:
            r19 = r11
            r20 = r15
            r11 = 0
        L_0x008b:
            r15 = -1
            if (r11 == r15) goto L_0x0414
            java.lang.String r15 = "charset"
            java.lang.String r15 = r1.getParameter(r15)
            if (r15 != 0) goto L_0x0098
            java.lang.String r15 = "ISO-8859-1"
        L_0x0098:
            r16 = r12
            r21 = r8
            java.lang.String r8 = "MD5-sess"
            r22 = r14
            r14 = r16
            boolean r16 = r14.equalsIgnoreCase(r8)
            if (r16 == 0) goto L_0x00ac
            java.lang.String r16 = "MD5"
            r14 = r16
        L_0x00ac:
            r16 = r6
            java.security.MessageDigest r23 = b(r14)     // Catch:{ UnsupportedDigestAlgorithmException -> 0x03ec }
            r24 = r23
            java.security.Principal r23 = r36.getUserPrincipal()
            java.lang.String r6 = r23.getName()
            r23 = r14
            java.lang.String r14 = r36.getPassword()
            r26 = r0
            java.lang.String r0 = r1.lastNonce
            boolean r0 = r7.equals(r0)
            r27 = r3
            r2 = 1
            if (r0 == 0) goto L_0x00da
            r0 = r9
            r28 = r10
            long r9 = r1.nounceCount
            long r9 = r9 + r2
            r1.nounceCount = r9
            goto L_0x00e4
        L_0x00da:
            r0 = r9
            r28 = r10
            r1.nounceCount = r2
            r2 = 0
            r1.cnonce = r2
            r1.lastNonce = r7
        L_0x00e4:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r3 = 256(0x100, float:3.59E-43)
            r2.<init>(r3)
            java.util.Formatter r3 = new java.util.Formatter
            java.util.Locale r9 = java.util.Locale.US
            r3.<init>(r2, r9)
            r9 = 1
            java.lang.Object[] r10 = new java.lang.Object[r9]
            r29 = r10
            long r9 = r1.nounceCount
            java.lang.Long r9 = java.lang.Long.valueOf(r9)
            r10 = 0
            r29[r10] = r9
            java.lang.String r9 = "%08x"
            r10 = r29
            r3.format(r9, r10)
            r3.close()
            java.lang.String r9 = r2.toString()
            java.lang.String r10 = r1.cnonce
            if (r10 != 0) goto L_0x0118
            java.lang.String r10 = createCnonce()
            r1.cnonce = r10
        L_0x0118:
            r10 = 0
            r1.a1 = r10
            r1.a2 = r10
            boolean r8 = r12.equalsIgnoreCase(r8)
            r10 = 58
            if (r8 == 0) goto L_0x016a
            r8 = 0
            r2.setLength(r8)
            r2.append(r6)
            r2.append(r10)
            r2.append(r5)
            r2.append(r10)
            r2.append(r14)
            java.lang.String r8 = r2.toString()
            byte[] r8 = org.apache.http.util.f.d(r8, r15)
            r10 = r24
            byte[] r8 = r10.digest(r8)
            java.lang.String r8 = encode(r8)
            r24 = r3
            r3 = 0
            r2.setLength(r3)
            r2.append(r8)
            r3 = 58
            r2.append(r3)
            r2.append(r7)
            r2.append(r3)
            java.lang.String r3 = r1.cnonce
            r2.append(r3)
            java.lang.String r3 = r2.toString()
            r1.a1 = r3
            goto L_0x0189
        L_0x016a:
            r10 = r24
            r24 = r3
            r3 = 0
            r2.setLength(r3)
            r2.append(r6)
            r3 = 58
            r2.append(r3)
            r2.append(r5)
            r2.append(r3)
            r2.append(r14)
            java.lang.String r3 = r2.toString()
            r1.a1 = r3
        L_0x0189:
            java.lang.String r3 = r1.a1
            byte[] r3 = org.apache.http.util.f.d(r3, r15)
            byte[] r3 = r10.digest(r3)
            java.lang.String r3 = encode(r3)
            r8 = 2
            if (r11 != r8) goto L_0x01be
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r29 = r14
            r14 = r28
            r8.append(r14)
            r28 = r12
            r12 = 58
            r8.append(r12)
            r12 = r27
            r8.append(r12)
            java.lang.String r8 = r8.toString()
            r1.a2 = r8
            r31 = r0
            r30 = r11
            goto L_0x0275
        L_0x01be:
            r29 = r14
            r14 = r28
            r28 = r12
            r12 = r27
            r8 = 1
            if (r11 != r8) goto L_0x025b
            r8 = 0
            r27 = r8
            r30 = r11
            r8 = r37
            boolean r11 = r8 instanceof org.apache.http.k
            if (r11 == 0) goto L_0x01dc
            r11 = r8
            org.apache.http.k r11 = (org.apache.http.k) r11
            org.apache.http.j r11 = r11.a()
            goto L_0x01de
        L_0x01dc:
            r11 = r27
        L_0x01de:
            if (r11 == 0) goto L_0x0211
            boolean r27 = r11.isRepeatable()
            if (r27 != 0) goto L_0x0211
            boolean r27 = r13.contains(r0)
            if (r27 == 0) goto L_0x0209
            r27 = 2
            r31 = r0
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            r8 = 58
            r0.append(r8)
            r0.append(r12)
            java.lang.String r0 = r0.toString()
            r1.a2 = r0
            r11 = r27
            goto L_0x024f
        L_0x0209:
            org.apache.http.auth.AuthenticationException r0 = new org.apache.http.auth.AuthenticationException
            java.lang.String r4 = "Qop auth-int cannot be used with a non-repeatable entity"
            r0.<init>(r4)
            throw r0
        L_0x0211:
            r31 = r0
            org.apache.http.impl.auth.h r0 = new org.apache.http.impl.auth.h
            r0.<init>(r10)
            r8 = r0
            if (r11 == 0) goto L_0x0223
            r11.writeTo(r8)     // Catch:{ IOException -> 0x021f }
            goto L_0x0223
        L_0x021f:
            r0 = move-exception
            r27 = r11
            goto L_0x0253
        L_0x0223:
            r8.close()     // Catch:{ IOException -> 0x0250 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            r27 = r11
            r11 = 58
            r0.append(r11)
            r0.append(r12)
            r0.append(r11)
            byte[] r11 = r8.a()
            java.lang.String r11 = encode(r11)
            r0.append(r11)
            java.lang.String r0 = r0.toString()
            r1.a2 = r0
            r11 = r30
        L_0x024f:
            goto L_0x0277
        L_0x0250:
            r0 = move-exception
            r27 = r11
        L_0x0253:
            org.apache.http.auth.AuthenticationException r4 = new org.apache.http.auth.AuthenticationException
            java.lang.String r11 = "I/O error reading entity content"
            r4.<init>(r11, r0)
            throw r4
        L_0x025b:
            r31 = r0
            r30 = r11
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r14)
            r8 = 58
            r0.append(r8)
            r0.append(r12)
            java.lang.String r0 = r0.toString()
            r1.a2 = r0
        L_0x0275:
            r11 = r30
        L_0x0277:
            java.lang.String r0 = r1.a2
            byte[] r0 = org.apache.http.util.f.d(r0, r15)
            byte[] r0 = r10.digest(r0)
            java.lang.String r0 = encode(r0)
            if (r11 != 0) goto L_0x02a3
            r8 = 0
            r2.setLength(r8)
            r2.append(r3)
            r8 = 58
            r2.append(r8)
            r2.append(r7)
            r2.append(r8)
            r2.append(r0)
            java.lang.String r8 = r2.toString()
            r25 = r3
            goto L_0x02dc
        L_0x02a3:
            r8 = 58
            r8 = 0
            r2.setLength(r8)
            r2.append(r3)
            r8 = 58
            r2.append(r8)
            r2.append(r7)
            r2.append(r8)
            r2.append(r9)
            r2.append(r8)
            r25 = r3
            java.lang.String r3 = r1.cnonce
            r2.append(r3)
            r2.append(r8)
            r3 = 1
            if (r11 != r3) goto L_0x02cd
            r3 = r21
            goto L_0x02cf
        L_0x02cd:
            r3 = r31
        L_0x02cf:
            r2.append(r3)
            r2.append(r8)
            r2.append(r0)
            java.lang.String r8 = r2.toString()
        L_0x02dc:
            byte[] r3 = org.apache.http.util.f.a(r8)
            byte[] r3 = r10.digest(r3)
            java.lang.String r3 = encode(r3)
            r27 = r0
            org.apache.http.util.d r0 = new org.apache.http.util.d
            r32 = r2
            r2 = 128(0x80, float:1.794E-43)
            r0.<init>(r2)
            boolean r2 = r35.isProxy()
            if (r2 == 0) goto L_0x02ff
            java.lang.String r2 = "Proxy-Authorization"
            r0.append((java.lang.String) r2)
            goto L_0x0304
        L_0x02ff:
            java.lang.String r2 = "Authorization"
            r0.append((java.lang.String) r2)
        L_0x0304:
            java.lang.String r2 = ": Digest "
            r0.append((java.lang.String) r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r30 = r8
            r8 = 20
            r2.<init>(r8)
            org.apache.http.message.m r8 = new org.apache.http.message.m
            r33 = r10
            java.lang.String r10 = "username"
            r8.<init>(r10, r6)
            r2.add(r8)
            org.apache.http.message.m r8 = new org.apache.http.message.m
            r8.<init>(r4, r5)
            r2.add(r8)
            org.apache.http.message.m r4 = new org.apache.http.message.m
            r8 = r16
            r4.<init>(r8, r7)
            r2.add(r4)
            org.apache.http.message.m r4 = new org.apache.http.message.m
            r8 = r26
            r4.<init>(r8, r12)
            r2.add(r4)
            org.apache.http.message.m r4 = new org.apache.http.message.m
            java.lang.String r8 = "response"
            r4.<init>(r8, r3)
            r2.add(r4)
            java.lang.String r4 = "nc"
            if (r11 == 0) goto L_0x0371
            org.apache.http.message.m r8 = new org.apache.http.message.m
            r10 = 1
            if (r11 != r10) goto L_0x0350
            r10 = r21
            goto L_0x0352
        L_0x0350:
            r10 = r31
        L_0x0352:
            r16 = r3
            r3 = r20
            r8.<init>(r3, r10)
            r2.add(r8)
            org.apache.http.message.m r8 = new org.apache.http.message.m
            r8.<init>(r4, r9)
            r2.add(r8)
            org.apache.http.message.m r8 = new org.apache.http.message.m
            java.lang.String r10 = r1.cnonce
            java.lang.String r1 = "cnonce"
            r8.<init>(r1, r10)
            r2.add(r8)
            goto L_0x0375
        L_0x0371:
            r16 = r3
            r3 = r20
        L_0x0375:
            org.apache.http.message.m r1 = new org.apache.http.message.m
            r10 = r19
            r8 = r28
            r1.<init>(r10, r8)
            r2.add(r1)
            if (r18 == 0) goto L_0x0396
            org.apache.http.message.m r1 = new org.apache.http.message.m
            r19 = r5
            r5 = r18
            r34 = r17
            r17 = r6
            r6 = r34
            r1.<init>(r6, r5)
            r2.add(r1)
            goto L_0x039c
        L_0x0396:
            r19 = r5
            r17 = r6
            r5 = r18
        L_0x039c:
            r1 = 0
        L_0x039d:
            int r6 = r2.size()
            if (r1 >= r6) goto L_0x03e6
            java.lang.Object r6 = r2.get(r1)
            org.apache.http.message.m r6 = (org.apache.http.message.m) r6
            if (r1 <= 0) goto L_0x03b3
            r18 = r2
            java.lang.String r2 = ", "
            r0.append((java.lang.String) r2)
            goto L_0x03b5
        L_0x03b3:
            r18 = r2
        L_0x03b5:
            java.lang.String r2 = r6.getName()
            boolean r20 = r4.equals(r2)
            if (r20 != 0) goto L_0x03cf
            boolean r20 = r3.equals(r2)
            if (r20 != 0) goto L_0x03cf
            boolean r20 = r10.equals(r2)
            if (r20 == 0) goto L_0x03cc
            goto L_0x03cf
        L_0x03cc:
            r20 = 0
            goto L_0x03d1
        L_0x03cf:
            r20 = 1
        L_0x03d1:
            r21 = r2
            org.apache.http.message.f r2 = org.apache.http.message.f.b
            r26 = r3
            if (r20 != 0) goto L_0x03db
            r3 = 1
            goto L_0x03dc
        L_0x03db:
            r3 = 0
        L_0x03dc:
            r2.f(r0, r6, r3)
            int r1 = r1 + 1
            r2 = r18
            r3 = r26
            goto L_0x039d
        L_0x03e6:
            org.apache.http.message.q r1 = new org.apache.http.message.q
            r1.<init>(r0)
            return r1
        L_0x03ec:
            r0 = move-exception
            r19 = r5
            r30 = r11
            r8 = r12
            r23 = r14
            r5 = r18
            r2 = 0
            r12 = r3
            r14 = r10
            r1 = r0
            r0 = r2
            org.apache.http.auth.AuthenticationException r2 = new org.apache.http.auth.AuthenticationException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Unsuppported digest algorithm: "
            r3.append(r4)
            r4 = r23
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x0414:
            r22 = r14
            org.apache.http.auth.AuthenticationException r0 = new org.apache.http.auth.AuthenticationException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "None of the qop methods is supported: "
            r1.append(r2)
            r2 = r22
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.http.impl.auth.d.a(org.apache.http.auth.l, org.apache.http.o):org.apache.http.d");
    }

    /* access modifiers changed from: package-private */
    public String getCnonce() {
        return this.cnonce;
    }

    /* access modifiers changed from: package-private */
    public String getA1() {
        return this.a1;
    }

    /* access modifiers changed from: package-private */
    public String getA2() {
        return this.a2;
    }

    static String encode(byte[] binaryData) {
        int n = binaryData.length;
        char[] buffer = new char[(n * 2)];
        for (int i = 0; i < n; i++) {
            char[] cArr = d;
            buffer[i * 2] = cArr[(binaryData[i] & 240) >> 4];
            buffer[(i * 2) + 1] = cArr[binaryData[i] & 15];
        }
        return new String(buffer);
    }

    public static String createCnonce() {
        byte[] tmp = new byte[8];
        new SecureRandom().nextBytes(tmp);
        return encode(tmp);
    }

    public String toString() {
        return "DIGEST [complete=" + this.complete + ", nonce=" + this.lastNonce + ", nc=" + this.nounceCount + "]";
    }
}
