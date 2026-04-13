package org.spongycastle.jcajce.provider.symmetric.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.StreamCipher;
import org.spongycastle.crypto.params.ParametersWithIV;

public class BaseStreamCipher extends BaseWrapCipher implements PBE {
    private String A4;
    private Class[] a1;
    private int a2;
    private StreamCipher p1;
    private int p2;
    private ParametersWithIV p3;
    private int p4;
    private PBEParameterSpec z4;

    protected BaseStreamCipher(StreamCipher engine, int ivLength) {
        this(engine, ivLength, -1, -1);
    }

    protected BaseStreamCipher(StreamCipher engine, int ivLength, int keySizeInBits, int digest) {
        this.a1 = new Class[]{RC2ParameterSpec.class, RC5ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
        this.p4 = 0;
        this.z4 = null;
        this.A4 = null;
        this.p1 = engine;
        this.p4 = ivLength;
        this.a2 = keySizeInBits;
        this.p2 = digest;
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        ParametersWithIV parametersWithIV = this.p3;
        if (parametersWithIV != null) {
            return parametersWithIV.a();
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineGetKeySize(Key key) {
        return key.getEncoded().length * 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetOutputSize(int inputLen) {
        return inputLen;
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        AlgorithmParameters algorithmParameters = this.q;
        if (algorithmParameters != null || this.z4 == null) {
            return algorithmParameters;
        }
        try {
            AlgorithmParameters engineParams = a(this.A4);
            engineParams.init(this.z4);
            return engineParams;
        } catch (Exception e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String mode) {
        if (!mode.equalsIgnoreCase("ECB")) {
            throw new NoSuchAlgorithmException("can't support mode " + mode);
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String padding) {
        if (!padding.equalsIgnoreCase("NoPadding")) {
            throw new NoSuchPaddingException("Padding " + padding + " unknown.");
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: org.spongycastle.crypto.CipherParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v16, resolved type: org.spongycastle.crypto.CipherParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v20, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v21, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v22, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v23, resolved type: org.spongycastle.crypto.params.KeyParameter} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v24, resolved type: org.spongycastle.crypto.CipherParameters} */
    /* JADX WARNING: type inference failed for: r0v5, types: [org.spongycastle.crypto.CipherParameters] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r12, java.security.Key r13, java.security.spec.AlgorithmParameterSpec r14, java.security.SecureRandom r15) {
        /*
            r11 = this;
            r0 = 0
            r11.z4 = r0
            r11.A4 = r0
            r11.q = r0
            boolean r0 = r13 instanceof javax.crypto.SecretKey
            if (r0 == 0) goto L_0x0152
            boolean r0 = r13 instanceof org.spongycastle.jcajce.PKCS12Key
            if (r0 == 0) goto L_0x004d
            r0 = r13
            org.spongycastle.jcajce.PKCS12Key r0 = (org.spongycastle.jcajce.PKCS12Key) r0
            r1 = r14
            javax.crypto.spec.PBEParameterSpec r1 = (javax.crypto.spec.PBEParameterSpec) r1
            r11.z4 = r1
            boolean r2 = r0 instanceof org.spongycastle.jcajce.PKCS12KeyWithParameters
            if (r2 == 0) goto L_0x0032
            if (r1 != 0) goto L_0x0032
            javax.crypto.spec.PBEParameterSpec r1 = new javax.crypto.spec.PBEParameterSpec
            r2 = r0
            org.spongycastle.jcajce.PKCS12KeyWithParameters r2 = (org.spongycastle.jcajce.PKCS12KeyWithParameters) r2
            byte[] r2 = r2.getSalt()
            r3 = r0
            org.spongycastle.jcajce.PKCS12KeyWithParameters r3 = (org.spongycastle.jcajce.PKCS12KeyWithParameters) r3
            int r3 = r3.getIterationCount()
            r1.<init>(r2, r3)
            r11.z4 = r1
        L_0x0032:
            byte[] r4 = r0.getEncoded()
            r5 = 2
            int r6 = r11.p2
            int r7 = r11.a2
            int r1 = r11.p4
            int r8 = r1 * 8
            javax.crypto.spec.PBEParameterSpec r9 = r11.z4
            org.spongycastle.crypto.StreamCipher r1 = r11.p1
            java.lang.String r10 = r1.b()
            org.spongycastle.crypto.CipherParameters r0 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.h(r4, r5, r6, r7, r8, r9, r10)
            goto L_0x00e0
        L_0x004d:
            boolean r0 = r13 instanceof org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r0 == 0) goto L_0x00ad
            r0 = r13
            org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey r0 = (org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey) r0
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = r0.getOID()
            if (r1 == 0) goto L_0x0065
            org.spongycastle.asn1.ASN1ObjectIdentifier r1 = r0.getOID()
            java.lang.String r1 = r1.s()
            r11.A4 = r1
            goto L_0x006b
        L_0x0065:
            java.lang.String r1 = r0.getAlgorithm()
            r11.A4 = r1
        L_0x006b:
            org.spongycastle.crypto.CipherParameters r1 = r0.getParam()
            if (r1 == 0) goto L_0x0085
            org.spongycastle.crypto.CipherParameters r1 = r0.getParam()
            javax.crypto.spec.PBEParameterSpec r2 = new javax.crypto.spec.PBEParameterSpec
            byte[] r3 = r0.getSalt()
            int r4 = r0.getIterationCount()
            r2.<init>(r3, r4)
            r11.z4 = r2
            goto L_0x0098
        L_0x0085:
            boolean r1 = r14 instanceof javax.crypto.spec.PBEParameterSpec
            if (r1 == 0) goto L_0x00a5
            org.spongycastle.crypto.StreamCipher r1 = r11.p1
            java.lang.String r1 = r1.b()
            org.spongycastle.crypto.CipherParameters r1 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.g(r0, r14, r1)
            r2 = r14
            javax.crypto.spec.PBEParameterSpec r2 = (javax.crypto.spec.PBEParameterSpec) r2
            r11.z4 = r2
        L_0x0098:
            int r2 = r0.getIvSize()
            if (r2 == 0) goto L_0x00a3
            r2 = r1
            org.spongycastle.crypto.params.ParametersWithIV r2 = (org.spongycastle.crypto.params.ParametersWithIV) r2
            r11.p3 = r2
        L_0x00a3:
            r0 = r1
            goto L_0x00e0
        L_0x00a5:
            java.security.InvalidAlgorithmParameterException r1 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r2 = "PBE requires PBE parameters to be set."
            r1.<init>(r2)
            throw r1
        L_0x00ad:
            if (r14 != 0) goto L_0x00c5
            int r0 = r11.p2
            if (r0 > 0) goto L_0x00bd
            org.spongycastle.crypto.params.KeyParameter r0 = new org.spongycastle.crypto.params.KeyParameter
            byte[] r1 = r13.getEncoded()
            r0.<init>(r1)
            goto L_0x00e0
        L_0x00bd:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.String r1 = "Algorithm requires a PBE key"
            r0.<init>(r1)
            throw r0
        L_0x00c5:
            boolean r0 = r14 instanceof javax.crypto.spec.IvParameterSpec
            if (r0 == 0) goto L_0x014a
            org.spongycastle.crypto.params.ParametersWithIV r0 = new org.spongycastle.crypto.params.ParametersWithIV
            org.spongycastle.crypto.params.KeyParameter r1 = new org.spongycastle.crypto.params.KeyParameter
            byte[] r2 = r13.getEncoded()
            r1.<init>(r2)
            r2 = r14
            javax.crypto.spec.IvParameterSpec r2 = (javax.crypto.spec.IvParameterSpec) r2
            byte[] r2 = r2.getIV()
            r0.<init>(r1, r2)
            r11.p3 = r0
        L_0x00e0:
            int r1 = r11.p4
            r2 = 1
            if (r1 == 0) goto L_0x010f
            boolean r1 = r0 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r1 != 0) goto L_0x010f
            r1 = r15
            if (r1 != 0) goto L_0x00f2
            java.security.SecureRandom r3 = new java.security.SecureRandom
            r3.<init>()
            r1 = r3
        L_0x00f2:
            if (r12 == r2) goto L_0x0100
            r3 = 3
            if (r12 != r3) goto L_0x00f8
            goto L_0x0100
        L_0x00f8:
            java.security.InvalidAlgorithmParameterException r2 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r3 = "no IV set when one expected"
            r2.<init>(r3)
            throw r2
        L_0x0100:
            int r3 = r11.p4
            byte[] r3 = new byte[r3]
            r1.nextBytes(r3)
            org.spongycastle.crypto.params.ParametersWithIV r4 = new org.spongycastle.crypto.params.ParametersWithIV
            r4.<init>(r0, r3)
            r0 = r4
            r11.p3 = r0
        L_0x010f:
            switch(r12) {
                case 1: goto L_0x011c;
                case 2: goto L_0x0115;
                case 3: goto L_0x011c;
                case 4: goto L_0x0115;
                default: goto L_0x0112;
            }
        L_0x0112:
            java.security.InvalidParameterException r1 = new java.security.InvalidParameterException     // Catch:{ Exception -> 0x0124 }
            goto L_0x0126
        L_0x0115:
            org.spongycastle.crypto.StreamCipher r1 = r11.p1     // Catch:{ Exception -> 0x0124 }
            r2 = 0
            r1.a(r2, r0)     // Catch:{ Exception -> 0x0124 }
            goto L_0x0122
        L_0x011c:
            org.spongycastle.crypto.StreamCipher r1 = r11.p1     // Catch:{ Exception -> 0x0124 }
            r1.a(r2, r0)     // Catch:{ Exception -> 0x0124 }
        L_0x0122:
            return
        L_0x0124:
            r1 = move-exception
            goto L_0x0140
        L_0x0126:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0124 }
            r2.<init>()     // Catch:{ Exception -> 0x0124 }
            java.lang.String r3 = "unknown opmode "
            r2.append(r3)     // Catch:{ Exception -> 0x0124 }
            r2.append(r12)     // Catch:{ Exception -> 0x0124 }
            java.lang.String r3 = " passed"
            r2.append(r3)     // Catch:{ Exception -> 0x0124 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x0124 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x0124 }
            throw r1     // Catch:{ Exception -> 0x0124 }
        L_0x0140:
            java.security.InvalidKeyException r2 = new java.security.InvalidKeyException
            java.lang.String r3 = r1.getMessage()
            r2.<init>(r3)
            throw r2
        L_0x014a:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r1 = "unknown parameter type."
            r0.<init>(r1)
            throw r0
        L_0x0152:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Key for algorithm "
            r1.append(r2)
            java.lang.String r2 = r13.getAlgorithm()
            r1.append(r2)
            java.lang.String r2 = " not suitable for symmetric enryption."
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.symmetric.util.BaseStreamCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null) {
            int i = 0;
            while (true) {
                Class[] clsArr = this.a1;
                if (i == clsArr.length) {
                    break;
                }
                try {
                    paramSpec = params.getParameterSpec(clsArr[i]);
                    break;
                } catch (Exception e) {
                    i++;
                }
            }
            if (paramSpec == null) {
                throw new InvalidAlgorithmParameterException("can't handle parameter " + params.toString());
            }
        }
        engineInit(opmode, key, paramSpec, random);
        this.q = params;
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, SecureRandom random) {
        try {
            engineInit(opmode, key, (AlgorithmParameterSpec) null, random);
        } catch (InvalidAlgorithmParameterException e) {
            throw new InvalidKeyException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        byte[] out = new byte[inputLen];
        this.p1.d(input, inputOffset, inputLen, out, 0);
        return out;
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        if (outputOffset + inputLen <= output.length) {
            try {
                this.p1.d(input, inputOffset, inputLen, output, outputOffset);
                return inputLen;
            } catch (DataLengthException e) {
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new ShortBufferException("output buffer too short for input.");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        if (inputLen != 0) {
            byte[] out = engineUpdate(input, inputOffset, inputLen);
            this.p1.reset();
            return out;
        }
        this.p1.reset();
        return new byte[0];
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        if (outputOffset + inputLen <= output.length) {
            if (inputLen != 0) {
                this.p1.d(input, inputOffset, inputLen, output, outputOffset);
            }
            this.p1.reset();
            return inputLen;
        }
        throw new ShortBufferException("output buffer too short for input.");
    }
}
