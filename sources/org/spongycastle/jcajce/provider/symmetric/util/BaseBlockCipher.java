package org.spongycastle.jcajce.provider.symmetric.util;

import com.google.android.gms.stats.CodePackage;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.RC5ParameterSpec;
import org.spongycastle.asn1.cms.GCMParameters;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.OutputLengthException;
import org.spongycastle.crypto.engines.DSTU7624Engine;
import org.spongycastle.crypto.modes.AEADBlockCipher;
import org.spongycastle.crypto.modes.CBCBlockCipher;
import org.spongycastle.crypto.modes.CCMBlockCipher;
import org.spongycastle.crypto.modes.CFBBlockCipher;
import org.spongycastle.crypto.modes.CTSBlockCipher;
import org.spongycastle.crypto.modes.EAXBlockCipher;
import org.spongycastle.crypto.modes.GCFBBlockCipher;
import org.spongycastle.crypto.modes.GCMBlockCipher;
import org.spongycastle.crypto.modes.GOFBBlockCipher;
import org.spongycastle.crypto.modes.KCCMBlockCipher;
import org.spongycastle.crypto.modes.KCTRBlockCipher;
import org.spongycastle.crypto.modes.KGCMBlockCipher;
import org.spongycastle.crypto.modes.OCBBlockCipher;
import org.spongycastle.crypto.modes.OFBBlockCipher;
import org.spongycastle.crypto.modes.OpenPGPCFBBlockCipher;
import org.spongycastle.crypto.modes.PGPCFBBlockCipher;
import org.spongycastle.crypto.modes.SICBlockCipher;
import org.spongycastle.crypto.paddings.BlockCipherPadding;
import org.spongycastle.crypto.paddings.ISO10126d2Padding;
import org.spongycastle.crypto.paddings.ISO7816d4Padding;
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.spongycastle.crypto.paddings.TBCPadding;
import org.spongycastle.crypto.paddings.X923Padding;
import org.spongycastle.crypto.paddings.ZeroBytePadding;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.ParametersWithSBox;
import org.spongycastle.jcajce.spec.GOST28147ParameterSpec;
import org.spongycastle.util.Strings;

public class BaseBlockCipher extends BaseWrapCipher implements PBE {
    private static final Class a1 = ClassUtil.a(BaseBlockCipher.class, "javax.crypto.spec.GCMParameterSpec");
    private int A4;
    private int B4 = -1;
    private int C4;
    private int D4 = 0;
    private boolean E4;
    private boolean F4 = true;
    private PBEParameterSpec G4 = null;
    private String H4 = null;
    private String I4 = null;
    private BlockCipher a2;
    private Class[] p1 = {RC2ParameterSpec.class, RC5ParameterSpec.class, a1, GOST28147ParameterSpec.class, IvParameterSpec.class, PBEParameterSpec.class};
    private BlockCipherProvider p2;
    private GenericBlockCipher p3;
    private ParametersWithIV p4;
    private AEADParameters z4;

    public interface GenericBlockCipher {
        void a(boolean z, CipherParameters cipherParameters);

        String b();

        int c(byte[] bArr, int i);

        int d(byte[] bArr, int i, int i2, byte[] bArr2, int i3);

        int e(int i);

        int f(int i);

        BlockCipher g();

        boolean h();

        void i(byte[] bArr, int i, int i2);
    }

    protected BaseBlockCipher(BlockCipher engine) {
        this.a2 = engine;
        this.p3 = new BufferedGenericBlockCipher(engine);
    }

    protected BaseBlockCipher(BlockCipher engine, int scheme, int digest, int keySizeInBits, int ivLength) {
        this.a2 = engine;
        this.B4 = scheme;
        this.C4 = digest;
        this.A4 = keySizeInBits;
        this.D4 = ivLength;
        this.p3 = new BufferedGenericBlockCipher(engine);
    }

    protected BaseBlockCipher(BlockCipherProvider provider) {
        this.a2 = provider.get();
        this.p2 = provider;
        this.p3 = new BufferedGenericBlockCipher(provider.get());
    }

    protected BaseBlockCipher(AEADBlockCipher engine) {
        BlockCipher g = engine.g();
        this.a2 = g;
        this.D4 = g.c();
        this.p3 = new AEADGenericBlockCipher(engine);
    }

    protected BaseBlockCipher(AEADBlockCipher engine, boolean fixedIv, int ivLength) {
        this.a2 = engine.g();
        this.F4 = fixedIv;
        this.D4 = ivLength;
        this.p3 = new AEADGenericBlockCipher(engine);
    }

    protected BaseBlockCipher(BlockCipher engine, int ivLength) {
        this.a2 = engine;
        this.p3 = new BufferedGenericBlockCipher(engine);
        this.D4 = ivLength / 8;
    }

    protected BaseBlockCipher(BufferedBlockCipher engine, int ivLength) {
        this.a2 = engine.d();
        this.p3 = new BufferedGenericBlockCipher(engine);
        this.D4 = ivLength / 8;
    }

    /* access modifiers changed from: protected */
    public int engineGetBlockSize() {
        return this.a2.c();
    }

    /* access modifiers changed from: protected */
    public byte[] engineGetIV() {
        AEADParameters aEADParameters = this.z4;
        if (aEADParameters != null) {
            return aEADParameters.d();
        }
        ParametersWithIV parametersWithIV = this.p4;
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
        return this.p3.f(inputLen);
    }

    /* access modifiers changed from: protected */
    public AlgorithmParameters engineGetParameters() {
        if (this.q == null) {
            if (this.G4 != null) {
                try {
                    AlgorithmParameters a = a(this.H4);
                    this.q = a;
                    a.init(this.G4);
                } catch (Exception e) {
                    return null;
                }
            } else if (this.z4 != null) {
                try {
                    AlgorithmParameters a3 = a(CodePackage.GCM);
                    this.q = a3;
                    a3.init(new GCMParameters(this.z4.d(), this.z4.c() / 8).getEncoded());
                } catch (Exception e2) {
                    throw new RuntimeException(e2.toString());
                }
            } else if (this.p4 != null) {
                String name = this.p3.g().b();
                if (name.indexOf(47) >= 0) {
                    name = name.substring(0, name.indexOf(47));
                }
                try {
                    AlgorithmParameters a4 = a(name);
                    this.q = a4;
                    a4.init(new IvParameterSpec(this.p4.a()));
                } catch (Exception e3) {
                    throw new RuntimeException(e3.toString());
                }
            }
        }
        return this.q;
    }

    /* access modifiers changed from: protected */
    public void engineSetMode(String mode) {
        String l = Strings.l(mode);
        this.I4 = l;
        if (l.equals("ECB")) {
            this.D4 = 0;
            this.p3 = new BufferedGenericBlockCipher(this.a2);
        } else if (this.I4.equals("CBC")) {
            this.D4 = this.a2.c();
            this.p3 = new BufferedGenericBlockCipher((BlockCipher) new CBCBlockCipher(this.a2));
        } else if (this.I4.startsWith("OFB")) {
            this.D4 = this.a2.c();
            if (this.I4.length() != 3) {
                this.p3 = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(this.a2, Integer.parseInt(this.I4.substring(3))));
                return;
            }
            BlockCipher blockCipher = this.a2;
            this.p3 = new BufferedGenericBlockCipher((BlockCipher) new OFBBlockCipher(blockCipher, blockCipher.c() * 8));
        } else if (this.I4.startsWith("CFB")) {
            this.D4 = this.a2.c();
            if (this.I4.length() != 3) {
                this.p3 = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(this.a2, Integer.parseInt(this.I4.substring(3))));
                return;
            }
            BlockCipher blockCipher2 = this.a2;
            this.p3 = new BufferedGenericBlockCipher((BlockCipher) new CFBBlockCipher(blockCipher2, blockCipher2.c() * 8));
        } else if (this.I4.startsWith("PGP")) {
            boolean inlineIV = this.I4.equalsIgnoreCase("PGPCFBwithIV");
            this.D4 = this.a2.c();
            this.p3 = new BufferedGenericBlockCipher((BlockCipher) new PGPCFBBlockCipher(this.a2, inlineIV));
        } else if (this.I4.equalsIgnoreCase("OpenPGPCFB")) {
            this.D4 = 0;
            this.p3 = new BufferedGenericBlockCipher((BlockCipher) new OpenPGPCFBBlockCipher(this.a2));
        } else if (this.I4.startsWith("SIC")) {
            int c = this.a2.c();
            this.D4 = c;
            if (c >= 16) {
                this.F4 = false;
                this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(this.a2)));
                return;
            }
            throw new IllegalArgumentException("Warning: SIC-Mode can become a twotime-pad if the blocksize of the cipher is too small. Use a cipher with a block size of at least 128 bits (e.g. AES)");
        } else if (this.I4.startsWith("CTR")) {
            this.D4 = this.a2.c();
            this.F4 = false;
            BlockCipher blockCipher3 = this.a2;
            if (blockCipher3 instanceof DSTU7624Engine) {
                this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(new KCTRBlockCipher(blockCipher3)));
            } else {
                this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(new SICBlockCipher(blockCipher3)));
            }
        } else if (this.I4.startsWith("GOFB")) {
            this.D4 = this.a2.c();
            this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GOFBBlockCipher(this.a2)));
        } else if (this.I4.startsWith("GCFB")) {
            this.D4 = this.a2.c();
            this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(new GCFBBlockCipher(this.a2)));
        } else if (this.I4.startsWith("CTS")) {
            this.D4 = this.a2.c();
            this.p3 = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(new CBCBlockCipher(this.a2)));
        } else if (this.I4.startsWith("CCM")) {
            this.D4 = 13;
            if (this.a2 instanceof DSTU7624Engine) {
                this.p3 = new AEADGenericBlockCipher(new KCCMBlockCipher(this.a2));
            } else {
                this.p3 = new AEADGenericBlockCipher(new CCMBlockCipher(this.a2));
            }
        } else if (this.I4.startsWith("OCB")) {
            if (this.p2 != null) {
                this.D4 = 15;
                this.p3 = new AEADGenericBlockCipher(new OCBBlockCipher(this.a2, this.p2.get()));
                return;
            }
            throw new NoSuchAlgorithmException("can't support mode " + mode);
        } else if (this.I4.startsWith("EAX")) {
            this.D4 = this.a2.c();
            this.p3 = new AEADGenericBlockCipher(new EAXBlockCipher(this.a2));
        } else if (this.I4.startsWith(CodePackage.GCM)) {
            this.D4 = this.a2.c();
            if (this.a2 instanceof DSTU7624Engine) {
                this.p3 = new AEADGenericBlockCipher(new KGCMBlockCipher(this.a2));
            } else {
                this.p3 = new AEADGenericBlockCipher(new GCMBlockCipher(this.a2));
            }
        } else {
            throw new NoSuchAlgorithmException("can't support mode " + mode);
        }
    }

    /* access modifiers changed from: protected */
    public void engineSetPadding(String padding) {
        String paddingName = Strings.l(padding);
        if (paddingName.equals("NOPADDING")) {
            if (this.p3.h()) {
                this.p3 = new BufferedGenericBlockCipher(new BufferedBlockCipher(this.p3.g()));
            }
        } else if (paddingName.equals("WITHCTS")) {
            this.p3 = new BufferedGenericBlockCipher((BufferedBlockCipher) new CTSBlockCipher(this.p3.g()));
        } else {
            this.E4 = true;
            if (c(this.I4)) {
                throw new NoSuchPaddingException("Only NoPadding can be used with AEAD modes.");
            } else if (paddingName.equals("PKCS5PADDING") || paddingName.equals("PKCS7PADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g());
            } else if (paddingName.equals("ZEROBYTEPADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g(), new ZeroBytePadding());
            } else if (paddingName.equals("ISO10126PADDING") || paddingName.equals("ISO10126-2PADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g(), new ISO10126d2Padding());
            } else if (paddingName.equals("X9.23PADDING") || paddingName.equals("X923PADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g(), new X923Padding());
            } else if (paddingName.equals("ISO7816-4PADDING") || paddingName.equals("ISO9797-1PADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g(), new ISO7816d4Padding());
            } else if (paddingName.equals("TBCPADDING")) {
                this.p3 = new BufferedGenericBlockCipher(this.p3.g(), new TBCPadding());
            } else {
                throw new NoSuchPaddingException("Padding " + padding + " unknown.");
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v2, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: org.spongycastle.crypto.params.AEADParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v7, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v11, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v14, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v18, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v21, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v24, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v25, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v26, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v44, resolved type: org.spongycastle.crypto.params.AEADParameters} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v27, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v40, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v52, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v56, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v93, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v94, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v95, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v96, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v97, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v98, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v99, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v100, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v101, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v104, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v105, resolved type: org.spongycastle.crypto.params.ParametersWithIV} */
    /* JADX WARNING: type inference failed for: r0v11, types: [org.spongycastle.crypto.params.ParametersWithRandom] */
    /* JADX WARNING: type inference failed for: r10v14, types: [org.spongycastle.crypto.params.RC5Parameters] */
    /* JADX WARNING: type inference failed for: r6v34, types: [org.spongycastle.crypto.params.RC2Parameters, org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r6v38, types: [org.spongycastle.crypto.params.ParametersWithSBox, org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r6v66, types: [org.spongycastle.crypto.params.KeyParameter] */
    /* JADX WARNING: type inference failed for: r7v61, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r7v73, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r7v75, types: [org.spongycastle.crypto.CipherParameters] */
    /* JADX WARNING: type inference failed for: r7v83, types: [org.spongycastle.crypto.CipherParameters] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Failed to insert additional move for type inference */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 4 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void engineInit(int r19, java.security.Key r20, java.security.spec.AlgorithmParameterSpec r21, java.security.SecureRandom r22) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            r5 = r22
            r0 = 0
            r1.G4 = r0
            r1.H4 = r0
            r1.q = r0
            r1.z4 = r0
            boolean r6 = r3 instanceof javax.crypto.SecretKey
            if (r6 != 0) goto L_0x0039
            java.security.InvalidKeyException r6 = new java.security.InvalidKeyException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            r7.<init>()
            java.lang.String r8 = "Key for algorithm "
            r7.append(r8)
            if (r3 == 0) goto L_0x0029
            java.lang.String r0 = r20.getAlgorithm()
        L_0x0029:
            r7.append(r0)
            java.lang.String r0 = " not suitable for symmetric enryption."
            r7.append(r0)
            java.lang.String r0 = r7.toString()
            r6.<init>(r0)
            throw r6
        L_0x0039:
            java.lang.String r0 = "RC5-64"
            if (r4 != 0) goto L_0x0052
            org.spongycastle.crypto.BlockCipher r6 = r1.a2
            java.lang.String r6 = r6.b()
            boolean r6 = r6.startsWith(r0)
            if (r6 != 0) goto L_0x004a
            goto L_0x0052
        L_0x004a:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r6 = "RC5 requires an RC5ParametersSpec to be passed in."
            r0.<init>(r6)
            throw r0
        L_0x0052:
            int r6 = r1.B4
            r7 = 2
            java.lang.String r8 = "Algorithm requires a PBE key"
            r9 = 1
            if (r6 == r7) goto L_0x0175
            boolean r7 = r3 instanceof org.spongycastle.jcajce.PKCS12Key
            if (r7 == 0) goto L_0x0060
            goto L_0x0175
        L_0x0060:
            boolean r7 = r3 instanceof org.spongycastle.jcajce.PBKDF1Key
            if (r7 == 0) goto L_0x00b3
            r6 = r3
            org.spongycastle.jcajce.PBKDF1Key r6 = (org.spongycastle.jcajce.PBKDF1Key) r6
            boolean r7 = r4 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x0070
            r7 = r4
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.G4 = r7
        L_0x0070:
            boolean r7 = r6 instanceof org.spongycastle.jcajce.PBKDF1KeyWithParameters
            if (r7 == 0) goto L_0x008d
            javax.crypto.spec.PBEParameterSpec r7 = r1.G4
            if (r7 != 0) goto L_0x008d
            javax.crypto.spec.PBEParameterSpec r7 = new javax.crypto.spec.PBEParameterSpec
            r8 = r6
            org.spongycastle.jcajce.PBKDF1KeyWithParameters r8 = (org.spongycastle.jcajce.PBKDF1KeyWithParameters) r8
            byte[] r8 = r8.getSalt()
            r10 = r6
            org.spongycastle.jcajce.PBKDF1KeyWithParameters r10 = (org.spongycastle.jcajce.PBKDF1KeyWithParameters) r10
            int r10 = r10.getIterationCount()
            r7.<init>(r8, r10)
            r1.G4 = r7
        L_0x008d:
            byte[] r11 = r6.getEncoded()
            r12 = 0
            int r13 = r1.C4
            int r14 = r1.A4
            int r7 = r1.D4
            int r15 = r7 * 8
            javax.crypto.spec.PBEParameterSpec r7 = r1.G4
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r8 = r1.p3
            java.lang.String r17 = r8.b()
            r16 = r7
            org.spongycastle.crypto.CipherParameters r7 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.h(r11, r12, r13, r14, r15, r16, r17)
            boolean r8 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x00b1
            r8 = r7
            org.spongycastle.crypto.params.ParametersWithIV r8 = (org.spongycastle.crypto.params.ParametersWithIV) r8
            r1.p4 = r8
        L_0x00b1:
            goto L_0x0213
        L_0x00b3:
            boolean r7 = r3 instanceof org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r7 == 0) goto L_0x010a
            r6 = r3
            org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey r6 = (org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey) r6
            org.spongycastle.asn1.ASN1ObjectIdentifier r7 = r6.getOID()
            if (r7 == 0) goto L_0x00cb
            org.spongycastle.asn1.ASN1ObjectIdentifier r7 = r6.getOID()
            java.lang.String r7 = r7.s()
            r1.H4 = r7
            goto L_0x00d1
        L_0x00cb:
            java.lang.String r7 = r6.getAlgorithm()
            r1.H4 = r7
        L_0x00d1:
            org.spongycastle.crypto.CipherParameters r7 = r6.getParam()
            if (r7 == 0) goto L_0x00e0
            org.spongycastle.crypto.CipherParameters r7 = r6.getParam()
            org.spongycastle.crypto.CipherParameters r7 = r1.b(r4, r7)
            goto L_0x00f7
        L_0x00e0:
            boolean r7 = r4 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x0102
            r7 = r4
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.G4 = r7
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.p3
            org.spongycastle.crypto.BlockCipher r7 = r7.g()
            java.lang.String r7 = r7.b()
            org.spongycastle.crypto.CipherParameters r7 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.g(r6, r4, r7)
        L_0x00f7:
            boolean r8 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x0100
            r8 = r7
            org.spongycastle.crypto.params.ParametersWithIV r8 = (org.spongycastle.crypto.params.ParametersWithIV) r8
            r1.p4 = r8
        L_0x0100:
            goto L_0x0213
        L_0x0102:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r7 = "PBE requires PBE parameters to be set."
            r0.<init>(r7)
            throw r0
        L_0x010a:
            boolean r7 = r3 instanceof javax.crypto.interfaces.PBEKey
            if (r7 == 0) goto L_0x0152
            r6 = r3
            javax.crypto.interfaces.PBEKey r6 = (javax.crypto.interfaces.PBEKey) r6
            r7 = r4
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.G4 = r7
            boolean r8 = r6 instanceof org.spongycastle.jcajce.PKCS12KeyWithParameters
            if (r8 == 0) goto L_0x012b
            if (r7 != 0) goto L_0x012b
            javax.crypto.spec.PBEParameterSpec r7 = new javax.crypto.spec.PBEParameterSpec
            byte[] r8 = r6.getSalt()
            int r10 = r6.getIterationCount()
            r7.<init>(r8, r10)
            r1.G4 = r7
        L_0x012b:
            byte[] r11 = r6.getEncoded()
            int r12 = r1.B4
            int r13 = r1.C4
            int r14 = r1.A4
            int r7 = r1.D4
            int r15 = r7 * 8
            javax.crypto.spec.PBEParameterSpec r7 = r1.G4
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r8 = r1.p3
            java.lang.String r17 = r8.b()
            r16 = r7
            org.spongycastle.crypto.CipherParameters r7 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.h(r11, r12, r13, r14, r15, r16, r17)
            boolean r8 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x0150
            r8 = r7
            org.spongycastle.crypto.params.ParametersWithIV r8 = (org.spongycastle.crypto.params.ParametersWithIV) r8
            r1.p4 = r8
        L_0x0150:
            goto L_0x0213
        L_0x0152:
            boolean r7 = r3 instanceof org.spongycastle.jcajce.spec.RepeatedSecretKeySpec
            if (r7 != 0) goto L_0x0172
            if (r6 == 0) goto L_0x016c
            r7 = 4
            if (r6 == r7) goto L_0x016c
            if (r6 == r9) goto L_0x016c
            r7 = 5
            if (r6 == r7) goto L_0x016c
            org.spongycastle.crypto.params.KeyParameter r6 = new org.spongycastle.crypto.params.KeyParameter
            byte[] r7 = r20.getEncoded()
            r6.<init>(r7)
            r7 = r6
            goto L_0x0213
        L_0x016c:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            r0.<init>(r8)
            throw r0
        L_0x0172:
            r7 = 0
            goto L_0x0213
        L_0x0175:
            r6 = r3
            javax.crypto.SecretKey r6 = (javax.crypto.SecretKey) r6     // Catch:{ Exception -> 0x0559 }
            boolean r7 = r4 instanceof javax.crypto.spec.PBEParameterSpec
            if (r7 == 0) goto L_0x0182
            r7 = r4
            javax.crypto.spec.PBEParameterSpec r7 = (javax.crypto.spec.PBEParameterSpec) r7
            r1.G4 = r7
        L_0x0182:
            boolean r7 = r6 instanceof javax.crypto.interfaces.PBEKey
            if (r7 == 0) goto L_0x01ab
            javax.crypto.spec.PBEParameterSpec r7 = r1.G4
            if (r7 != 0) goto L_0x01ab
            r7 = r6
            javax.crypto.interfaces.PBEKey r7 = (javax.crypto.interfaces.PBEKey) r7
            byte[] r10 = r7.getSalt()
            if (r10 == 0) goto L_0x01a3
            javax.crypto.spec.PBEParameterSpec r10 = new javax.crypto.spec.PBEParameterSpec
            byte[] r11 = r7.getSalt()
            int r12 = r7.getIterationCount()
            r10.<init>(r11, r12)
            r1.G4 = r10
            goto L_0x01ab
        L_0x01a3:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "PBEKey requires parameters to specify salt"
            r0.<init>(r8)
            throw r0
        L_0x01ab:
            javax.crypto.spec.PBEParameterSpec r7 = r1.G4
            if (r7 != 0) goto L_0x01ba
            boolean r7 = r6 instanceof javax.crypto.interfaces.PBEKey
            if (r7 == 0) goto L_0x01b4
            goto L_0x01ba
        L_0x01b4:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            r0.<init>(r8)
            throw r0
        L_0x01ba:
            boolean r7 = r3 instanceof org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey
            if (r7 == 0) goto L_0x01f0
            r7 = r3
            org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey r7 = (org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey) r7
            org.spongycastle.crypto.CipherParameters r7 = r7.getParam()
            boolean r8 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x01cb
            r8 = r7
            goto L_0x01e6
        L_0x01cb:
            if (r7 != 0) goto L_0x01e8
            byte[] r10 = r6.getEncoded()
            r11 = 2
            int r12 = r1.C4
            int r13 = r1.A4
            int r8 = r1.D4
            int r14 = r8 * 8
            javax.crypto.spec.PBEParameterSpec r15 = r1.G4
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r8 = r1.p3
            java.lang.String r16 = r8.b()
            org.spongycastle.crypto.CipherParameters r8 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.h(r10, r11, r12, r13, r14, r15, r16)
        L_0x01e6:
            r7 = r8
            goto L_0x0209
        L_0x01e8:
            java.security.InvalidKeyException r0 = new java.security.InvalidKeyException
            java.lang.String r8 = "Algorithm requires a PBE key suitable for PKCS12"
            r0.<init>(r8)
            throw r0
        L_0x01f0:
            byte[] r10 = r6.getEncoded()
            r11 = 2
            int r12 = r1.C4
            int r13 = r1.A4
            int r7 = r1.D4
            int r14 = r7 * 8
            javax.crypto.spec.PBEParameterSpec r15 = r1.G4
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r7 = r1.p3
            java.lang.String r16 = r7.b()
            org.spongycastle.crypto.CipherParameters r7 = org.spongycastle.jcajce.provider.symmetric.util.PBE.Util.h(r10, r11, r12, r13, r14, r15, r16)
        L_0x0209:
            boolean r8 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r8 == 0) goto L_0x0212
            r8 = r7
            org.spongycastle.crypto.params.ParametersWithIV r8 = (org.spongycastle.crypto.params.ParametersWithIV) r8
            r1.p4 = r8
        L_0x0212:
        L_0x0213:
            boolean r6 = r4 instanceof org.spongycastle.jcajce.spec.AEADParameterSpec
            r8 = 0
            if (r6 == 0) goto L_0x0259
            java.lang.String r0 = r1.I4
            boolean r0 = r1.c(r0)
            if (r0 != 0) goto L_0x022f
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.p3
            boolean r0 = r0 instanceof org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r0 == 0) goto L_0x0227
            goto L_0x022f
        L_0x0227:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r6 = "AEADParameterSpec can only be used with AEAD modes."
            r0.<init>(r6)
            throw r0
        L_0x022f:
            r0 = r4
            org.spongycastle.jcajce.spec.AEADParameterSpec r0 = (org.spongycastle.jcajce.spec.AEADParameterSpec) r0
            boolean r6 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r6 == 0) goto L_0x0240
            r6 = r7
            org.spongycastle.crypto.params.ParametersWithIV r6 = (org.spongycastle.crypto.params.ParametersWithIV) r6
            org.spongycastle.crypto.CipherParameters r6 = r6.b()
            org.spongycastle.crypto.params.KeyParameter r6 = (org.spongycastle.crypto.params.KeyParameter) r6
            goto L_0x0243
        L_0x0240:
            r6 = r7
            org.spongycastle.crypto.params.KeyParameter r6 = (org.spongycastle.crypto.params.KeyParameter) r6
        L_0x0243:
            org.spongycastle.crypto.params.AEADParameters r10 = new org.spongycastle.crypto.params.AEADParameters
            int r11 = r0.b()
            byte[] r12 = r0.c()
            byte[] r13 = r0.a()
            r10.<init>(r6, r11, r12, r13)
            r1.z4 = r10
            r7 = r10
            goto L_0x04a1
        L_0x0259:
            boolean r6 = r4 instanceof javax.crypto.spec.IvParameterSpec
            if (r6 == 0) goto L_0x02d0
            int r0 = r1.D4
            if (r0 == 0) goto L_0x02ba
            r0 = r4
            javax.crypto.spec.IvParameterSpec r0 = (javax.crypto.spec.IvParameterSpec) r0
            byte[] r6 = r0.getIV()
            int r6 = r6.length
            int r10 = r1.D4
            if (r6 == r10) goto L_0x0296
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r6 = r1.p3
            boolean r6 = r6 instanceof org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r6 != 0) goto L_0x0296
            boolean r6 = r1.F4
            if (r6 != 0) goto L_0x0278
            goto L_0x0296
        L_0x0278:
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "IV must be "
            r8.append(r9)
            int r9 = r1.D4
            r8.append(r9)
            java.lang.String r9 = " bytes long."
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r6.<init>(r8)
            throw r6
        L_0x0296:
            boolean r6 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r6 == 0) goto L_0x02ac
            org.spongycastle.crypto.params.ParametersWithIV r6 = new org.spongycastle.crypto.params.ParametersWithIV
            r10 = r7
            org.spongycastle.crypto.params.ParametersWithIV r10 = (org.spongycastle.crypto.params.ParametersWithIV) r10
            org.spongycastle.crypto.CipherParameters r10 = r10.b()
            byte[] r11 = r0.getIV()
            r6.<init>(r10, r11)
            r7 = r6
            goto L_0x02b6
        L_0x02ac:
            org.spongycastle.crypto.params.ParametersWithIV r6 = new org.spongycastle.crypto.params.ParametersWithIV
            byte[] r10 = r0.getIV()
            r6.<init>(r7, r10)
            r7 = r6
        L_0x02b6:
            r1.p4 = r7
            goto L_0x04a1
        L_0x02ba:
            java.lang.String r0 = r1.I4
            if (r0 == 0) goto L_0x04a1
            java.lang.String r6 = "ECB"
            boolean r0 = r0.equals(r6)
            if (r0 != 0) goto L_0x02c8
            goto L_0x04a1
        L_0x02c8:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r6 = "ECB mode does not use an IV"
            r0.<init>(r6)
            throw r0
        L_0x02d0:
            boolean r6 = r4 instanceof org.spongycastle.jcajce.spec.GOST28147ParameterSpec
            if (r6 == 0) goto L_0x031d
            r0 = r4
            org.spongycastle.jcajce.spec.GOST28147ParameterSpec r0 = (org.spongycastle.jcajce.spec.GOST28147ParameterSpec) r0
            org.spongycastle.crypto.params.ParametersWithSBox r6 = new org.spongycastle.crypto.params.ParametersWithSBox
            org.spongycastle.crypto.params.KeyParameter r10 = new org.spongycastle.crypto.params.KeyParameter
            byte[] r11 = r20.getEncoded()
            r10.<init>(r11)
            r11 = r4
            org.spongycastle.jcajce.spec.GOST28147ParameterSpec r11 = (org.spongycastle.jcajce.spec.GOST28147ParameterSpec) r11
            byte[] r11 = r11.d()
            r6.<init>(r10, r11)
            byte[] r7 = r0.a()
            if (r7 == 0) goto L_0x031a
            int r7 = r1.D4
            if (r7 == 0) goto L_0x031a
            boolean r7 = r6 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x030c
            org.spongycastle.crypto.params.ParametersWithIV r7 = new org.spongycastle.crypto.params.ParametersWithIV
            r10 = r6
            org.spongycastle.crypto.params.ParametersWithIV r10 = (org.spongycastle.crypto.params.ParametersWithIV) r10
            org.spongycastle.crypto.CipherParameters r10 = r10.b()
            byte[] r11 = r0.a()
            r7.<init>(r10, r11)
            r6 = r7
            goto L_0x0316
        L_0x030c:
            org.spongycastle.crypto.params.ParametersWithIV r7 = new org.spongycastle.crypto.params.ParametersWithIV
            byte[] r10 = r0.a()
            r7.<init>(r6, r10)
            r6 = r7
        L_0x0316:
            r1.p4 = r6
            r7 = r6
            goto L_0x031b
        L_0x031a:
            r7 = r6
        L_0x031b:
            goto L_0x04a1
        L_0x031d:
            boolean r6 = r4 instanceof javax.crypto.spec.RC2ParameterSpec
            if (r6 == 0) goto L_0x0365
            r0 = r4
            javax.crypto.spec.RC2ParameterSpec r0 = (javax.crypto.spec.RC2ParameterSpec) r0
            org.spongycastle.crypto.params.RC2Parameters r6 = new org.spongycastle.crypto.params.RC2Parameters
            byte[] r10 = r20.getEncoded()
            r11 = r4
            javax.crypto.spec.RC2ParameterSpec r11 = (javax.crypto.spec.RC2ParameterSpec) r11
            int r11 = r11.getEffectiveKeyBits()
            r6.<init>(r10, r11)
            byte[] r7 = r0.getIV()
            if (r7 == 0) goto L_0x0362
            int r7 = r1.D4
            if (r7 == 0) goto L_0x0362
            boolean r7 = r6 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r7 == 0) goto L_0x0354
            org.spongycastle.crypto.params.ParametersWithIV r7 = new org.spongycastle.crypto.params.ParametersWithIV
            r10 = r6
            org.spongycastle.crypto.params.ParametersWithIV r10 = (org.spongycastle.crypto.params.ParametersWithIV) r10
            org.spongycastle.crypto.CipherParameters r10 = r10.b()
            byte[] r11 = r0.getIV()
            r7.<init>(r10, r11)
            r6 = r7
            goto L_0x035e
        L_0x0354:
            org.spongycastle.crypto.params.ParametersWithIV r7 = new org.spongycastle.crypto.params.ParametersWithIV
            byte[] r10 = r0.getIV()
            r7.<init>(r6, r10)
            r6 = r7
        L_0x035e:
            r1.p4 = r6
            r7 = r6
            goto L_0x0363
        L_0x0362:
            r7 = r6
        L_0x0363:
            goto L_0x04a1
        L_0x0365:
            boolean r6 = r4 instanceof javax.crypto.spec.RC5ParameterSpec
            if (r6 == 0) goto L_0x042a
            r6 = r4
            javax.crypto.spec.RC5ParameterSpec r6 = (javax.crypto.spec.RC5ParameterSpec) r6
            org.spongycastle.crypto.params.RC5Parameters r10 = new org.spongycastle.crypto.params.RC5Parameters
            byte[] r11 = r20.getEncoded()
            r12 = r4
            javax.crypto.spec.RC5ParameterSpec r12 = (javax.crypto.spec.RC5ParameterSpec) r12
            int r12 = r12.getRounds()
            r10.<init>(r11, r12)
            r7 = r10
            org.spongycastle.crypto.BlockCipher r10 = r1.a2
            java.lang.String r10 = r10.b()
            java.lang.String r11 = "RC5"
            boolean r10 = r10.startsWith(r11)
            if (r10 == 0) goto L_0x0422
            org.spongycastle.crypto.BlockCipher r10 = r1.a2
            java.lang.String r10 = r10.b()
            java.lang.String r11 = "RC5-32"
            boolean r10 = r10.equals(r11)
            java.lang.String r11 = "."
            if (r10 == 0) goto L_0x03c2
            int r0 = r6.getWordSize()
            r10 = 32
            if (r0 != r10) goto L_0x03a4
            goto L_0x03f5
        L_0x03a4:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "RC5 already set up for a word size of 32 not "
            r8.append(r9)
            int r9 = r6.getWordSize()
            r8.append(r9)
            r8.append(r11)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x03c2:
            org.spongycastle.crypto.BlockCipher r10 = r1.a2
            java.lang.String r10 = r10.b()
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x03f5
            int r0 = r6.getWordSize()
            r10 = 64
            if (r0 != r10) goto L_0x03d7
            goto L_0x03f5
        L_0x03d7:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "RC5 already set up for a word size of 64 not "
            r8.append(r9)
            int r9 = r6.getWordSize()
            r8.append(r9)
            r8.append(r11)
            java.lang.String r8 = r8.toString()
            r0.<init>(r8)
            throw r0
        L_0x03f5:
            byte[] r0 = r6.getIV()
            if (r0 == 0) goto L_0x0420
            int r0 = r1.D4
            if (r0 == 0) goto L_0x0420
            boolean r0 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r0 == 0) goto L_0x0414
            org.spongycastle.crypto.params.ParametersWithIV r0 = new org.spongycastle.crypto.params.ParametersWithIV
            r10 = r7
            org.spongycastle.crypto.params.ParametersWithIV r10 = (org.spongycastle.crypto.params.ParametersWithIV) r10
            org.spongycastle.crypto.CipherParameters r10 = r10.b()
            byte[] r11 = r6.getIV()
            r0.<init>(r10, r11)
            goto L_0x041d
        L_0x0414:
            org.spongycastle.crypto.params.ParametersWithIV r0 = new org.spongycastle.crypto.params.ParametersWithIV
            byte[] r10 = r6.getIV()
            r0.<init>(r7, r10)
        L_0x041d:
            r1.p4 = r0
            r7 = r0
        L_0x0420:
            goto L_0x04a1
        L_0x0422:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "RC5 parameters passed to a cipher that is not RC5."
            r0.<init>(r8)
            throw r0
        L_0x042a:
            java.lang.Class r0 = a1
            if (r0 == 0) goto L_0x0492
            boolean r6 = r0.isInstance(r4)
            if (r6 == 0) goto L_0x0492
            java.lang.String r6 = r1.I4
            boolean r6 = r1.c(r6)
            if (r6 != 0) goto L_0x044b
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r6 = r1.p3
            boolean r6 = r6 instanceof org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher
            if (r6 == 0) goto L_0x0443
            goto L_0x044b
        L_0x0443:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r6 = "GCMParameterSpec can only be used with AEAD modes."
            r0.<init>(r6)
            throw r0
        L_0x044b:
            java.lang.String r6 = "getTLen"
            java.lang.Class[] r10 = new java.lang.Class[r8]     // Catch:{ Exception -> 0x0489 }
            java.lang.reflect.Method r6 = r0.getDeclaredMethod(r6, r10)     // Catch:{ Exception -> 0x0489 }
            java.lang.String r10 = "getIV"
            java.lang.Class[] r11 = new java.lang.Class[r8]     // Catch:{ Exception -> 0x0489 }
            java.lang.reflect.Method r0 = r0.getDeclaredMethod(r10, r11)     // Catch:{ Exception -> 0x0489 }
            boolean r10 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV     // Catch:{ Exception -> 0x0489 }
            if (r10 == 0) goto L_0x0469
            r10 = r7
            org.spongycastle.crypto.params.ParametersWithIV r10 = (org.spongycastle.crypto.params.ParametersWithIV) r10     // Catch:{ Exception -> 0x0489 }
            org.spongycastle.crypto.CipherParameters r10 = r10.b()     // Catch:{ Exception -> 0x0489 }
            org.spongycastle.crypto.params.KeyParameter r10 = (org.spongycastle.crypto.params.KeyParameter) r10     // Catch:{ Exception -> 0x0489 }
            goto L_0x046c
        L_0x0469:
            r10 = r7
            org.spongycastle.crypto.params.KeyParameter r10 = (org.spongycastle.crypto.params.KeyParameter) r10     // Catch:{ Exception -> 0x0489 }
        L_0x046c:
            org.spongycastle.crypto.params.AEADParameters r11 = new org.spongycastle.crypto.params.AEADParameters     // Catch:{ Exception -> 0x0489 }
            java.lang.Object[] r12 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0489 }
            java.lang.Object r12 = r6.invoke(r4, r12)     // Catch:{ Exception -> 0x0489 }
            java.lang.Integer r12 = (java.lang.Integer) r12     // Catch:{ Exception -> 0x0489 }
            int r12 = r12.intValue()     // Catch:{ Exception -> 0x0489 }
            java.lang.Object[] r13 = new java.lang.Object[r8]     // Catch:{ Exception -> 0x0489 }
            java.lang.Object r13 = r0.invoke(r4, r13)     // Catch:{ Exception -> 0x0489 }
            byte[] r13 = (byte[]) r13     // Catch:{ Exception -> 0x0489 }
            r11.<init>(r10, r12, r13)     // Catch:{ Exception -> 0x0489 }
            r1.z4 = r11     // Catch:{ Exception -> 0x0489 }
            r7 = r11
            goto L_0x04a1
        L_0x0489:
            r0 = move-exception
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "Cannot process GCMParameterSpec."
            r6.<init>(r8)
            throw r6
        L_0x0492:
            if (r4 == 0) goto L_0x04a1
            boolean r0 = r4 instanceof javax.crypto.spec.PBEParameterSpec
            if (r0 == 0) goto L_0x0499
            goto L_0x04a1
        L_0x0499:
            java.security.InvalidAlgorithmParameterException r0 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r6 = "unknown parameter type."
            r0.<init>(r6)
            throw r0
        L_0x04a1:
            int r0 = r1.D4
            if (r0 == 0) goto L_0x04e7
            boolean r0 = r7 instanceof org.spongycastle.crypto.params.ParametersWithIV
            if (r0 != 0) goto L_0x04e7
            boolean r0 = r7 instanceof org.spongycastle.crypto.params.AEADParameters
            if (r0 != 0) goto L_0x04e7
            r0 = r22
            if (r0 != 0) goto L_0x04b7
            java.security.SecureRandom r6 = new java.security.SecureRandom
            r6.<init>()
            r0 = r6
        L_0x04b7:
            if (r2 == r9) goto L_0x04d8
            r6 = 3
            if (r2 != r6) goto L_0x04bd
            goto L_0x04d8
        L_0x04bd:
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r6 = r1.p3
            org.spongycastle.crypto.BlockCipher r6 = r6.g()
            java.lang.String r6 = r6.b()
            java.lang.String r10 = "PGPCFB"
            int r6 = r6.indexOf(r10)
            if (r6 < 0) goto L_0x04d0
            goto L_0x04e7
        L_0x04d0:
            java.security.InvalidAlgorithmParameterException r6 = new java.security.InvalidAlgorithmParameterException
            java.lang.String r8 = "no IV set when one expected"
            r6.<init>(r8)
            throw r6
        L_0x04d8:
            int r6 = r1.D4
            byte[] r6 = new byte[r6]
            r0.nextBytes(r6)
            org.spongycastle.crypto.params.ParametersWithIV r10 = new org.spongycastle.crypto.params.ParametersWithIV
            r10.<init>(r7, r6)
            r7 = r10
            r1.p4 = r7
        L_0x04e7:
            if (r5 == 0) goto L_0x04f3
            boolean r0 = r1.E4
            if (r0 == 0) goto L_0x04f3
            org.spongycastle.crypto.params.ParametersWithRandom r0 = new org.spongycastle.crypto.params.ParametersWithRandom
            r0.<init>(r7, r5)
            r7 = r0
        L_0x04f3:
            switch(r2) {
                case 1: goto L_0x04ff;
                case 2: goto L_0x04f9;
                case 3: goto L_0x04ff;
                case 4: goto L_0x04f9;
                default: goto L_0x04f6;
            }
        L_0x04f6:
            java.security.InvalidParameterException r0 = new java.security.InvalidParameterException     // Catch:{ Exception -> 0x0533 }
            goto L_0x0535
        L_0x04f9:
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.p3     // Catch:{ Exception -> 0x0533 }
            r0.a(r8, r7)     // Catch:{ Exception -> 0x0533 }
            goto L_0x0505
        L_0x04ff:
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.p3     // Catch:{ Exception -> 0x0533 }
            r0.a(r9, r7)     // Catch:{ Exception -> 0x0533 }
        L_0x0505:
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$GenericBlockCipher r0 = r1.p3     // Catch:{ Exception -> 0x0533 }
            boolean r6 = r0 instanceof org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher     // Catch:{ Exception -> 0x0533 }
            if (r6 == 0) goto L_0x0531
            org.spongycastle.crypto.params.AEADParameters r6 = r1.z4     // Catch:{ Exception -> 0x0533 }
            if (r6 != 0) goto L_0x0531
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$AEADGenericBlockCipher r0 = (org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.AEADGenericBlockCipher) r0     // Catch:{ Exception -> 0x0533 }
            org.spongycastle.crypto.modes.AEADBlockCipher r0 = r0.b     // Catch:{ Exception -> 0x0533 }
            org.spongycastle.crypto.params.AEADParameters r6 = new org.spongycastle.crypto.params.AEADParameters     // Catch:{ Exception -> 0x0533 }
            org.spongycastle.crypto.params.ParametersWithIV r8 = r1.p4     // Catch:{ Exception -> 0x0533 }
            org.spongycastle.crypto.CipherParameters r8 = r8.b()     // Catch:{ Exception -> 0x0533 }
            org.spongycastle.crypto.params.KeyParameter r8 = (org.spongycastle.crypto.params.KeyParameter) r8     // Catch:{ Exception -> 0x0533 }
            byte[] r9 = r0.h()     // Catch:{ Exception -> 0x0533 }
            int r9 = r9.length     // Catch:{ Exception -> 0x0533 }
            int r9 = r9 * 8
            org.spongycastle.crypto.params.ParametersWithIV r10 = r1.p4     // Catch:{ Exception -> 0x0533 }
            byte[] r10 = r10.a()     // Catch:{ Exception -> 0x0533 }
            r6.<init>(r8, r9, r10)     // Catch:{ Exception -> 0x0533 }
            r1.z4 = r6     // Catch:{ Exception -> 0x0533 }
        L_0x0531:
            return
        L_0x0533:
            r0 = move-exception
            goto L_0x054f
        L_0x0535:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0533 }
            r6.<init>()     // Catch:{ Exception -> 0x0533 }
            java.lang.String r8 = "unknown opmode "
            r6.append(r8)     // Catch:{ Exception -> 0x0533 }
            r6.append(r2)     // Catch:{ Exception -> 0x0533 }
            java.lang.String r8 = " passed"
            r6.append(r8)     // Catch:{ Exception -> 0x0533 }
            java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0533 }
            r0.<init>(r6)     // Catch:{ Exception -> 0x0533 }
            throw r0     // Catch:{ Exception -> 0x0533 }
        L_0x054f:
            org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$InvalidKeyOrParametersException r6 = new org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher$InvalidKeyOrParametersException
            java.lang.String r8 = r0.getMessage()
            r6.<init>(r8, r0)
            throw r6
        L_0x0559:
            r0 = move-exception
            r6 = r0
            r0 = r6
            java.security.InvalidKeyException r6 = new java.security.InvalidKeyException
            java.lang.String r7 = "PKCS12 requires a SecretKey/PBEKey"
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.jcajce.provider.symmetric.util.BaseBlockCipher.engineInit(int, java.security.Key, java.security.spec.AlgorithmParameterSpec, java.security.SecureRandom):void");
    }

    private CipherParameters b(AlgorithmParameterSpec params, CipherParameters param) {
        if (param instanceof ParametersWithIV) {
            CipherParameters key = ((ParametersWithIV) param).b();
            if (params instanceof IvParameterSpec) {
                this.p4 = new ParametersWithIV(key, ((IvParameterSpec) params).getIV());
                return this.p4;
            } else if (!(params instanceof GOST28147ParameterSpec)) {
                return param;
            } else {
                GOST28147ParameterSpec gost28147Param = (GOST28147ParameterSpec) params;
                CipherParameters param2 = new ParametersWithSBox(param, gost28147Param.d());
                if (gost28147Param.a() == null || this.D4 == 0) {
                    return param2;
                }
                this.p4 = new ParametersWithIV(key, gost28147Param.a());
                return this.p4;
            }
        } else if (params instanceof IvParameterSpec) {
            this.p4 = new ParametersWithIV(param, ((IvParameterSpec) params).getIV());
            return this.p4;
        } else if (!(params instanceof GOST28147ParameterSpec)) {
            return param;
        } else {
            GOST28147ParameterSpec gost28147Param2 = (GOST28147ParameterSpec) params;
            CipherParameters param3 = new ParametersWithSBox(param, gost28147Param2.d());
            if (gost28147Param2.a() == null || this.D4 == 0) {
                return param3;
            }
            return new ParametersWithIV(param3, gost28147Param2.a());
        }
    }

    /* access modifiers changed from: protected */
    public void engineInit(int opmode, Key key, AlgorithmParameters params, SecureRandom random) {
        AlgorithmParameterSpec paramSpec = null;
        if (params != null) {
            int i = 0;
            while (true) {
                Class[] clsArr = this.p1;
                if (i == clsArr.length) {
                    break;
                }
                if (clsArr[i] != null) {
                    try {
                        paramSpec = params.getParameterSpec(clsArr[i]);
                        break;
                    } catch (Exception e) {
                    }
                }
                i++;
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
    public void engineUpdateAAD(byte[] input, int offset, int length) {
        this.p3.i(input, offset, length);
    }

    /* access modifiers changed from: protected */
    public void engineUpdateAAD(ByteBuffer bytebuffer) {
        engineUpdateAAD(bytebuffer.array(), bytebuffer.arrayOffset() + bytebuffer.position(), bytebuffer.limit() - bytebuffer.position());
    }

    /* access modifiers changed from: protected */
    public byte[] engineUpdate(byte[] input, int inputOffset, int inputLen) {
        int length = this.p3.e(inputLen);
        if (length > 0) {
            byte[] out = new byte[length];
            int len = this.p3.d(input, inputOffset, inputLen, out, 0);
            if (len == 0) {
                return null;
            }
            if (len == out.length) {
                return out;
            }
            byte[] tmp = new byte[len];
            System.arraycopy(out, 0, tmp, 0, len);
            return tmp;
        }
        this.p3.d(input, inputOffset, inputLen, (byte[]) null, 0);
        return null;
    }

    /* access modifiers changed from: protected */
    public int engineUpdate(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        if (this.p3.e(inputLen) + outputOffset <= output.length) {
            try {
                return this.p3.d(input, inputOffset, inputLen, output, outputOffset);
            } catch (DataLengthException e) {
                throw new IllegalStateException(e.toString());
            }
        } else {
            throw new ShortBufferException("output buffer too short for input.");
        }
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal(byte[] input, int inputOffset, int inputLen) {
        int len = 0;
        byte[] tmp = new byte[engineGetOutputSize(inputLen)];
        if (inputLen != 0) {
            len = this.p3.d(input, inputOffset, inputLen, tmp, 0);
        }
        try {
            int len2 = len + this.p3.c(tmp, len);
            if (len2 == tmp.length) {
                return tmp;
            }
            byte[] out = new byte[len2];
            System.arraycopy(tmp, 0, out, 0, len2);
            return out;
        } catch (DataLengthException e) {
            throw new IllegalBlockSizeException(e.getMessage());
        }
    }

    /* access modifiers changed from: protected */
    public int engineDoFinal(byte[] input, int inputOffset, int inputLen, byte[] output, int outputOffset) {
        int len = 0;
        if (engineGetOutputSize(inputLen) + outputOffset <= output.length) {
            if (inputLen != 0) {
                try {
                    len = this.p3.d(input, inputOffset, inputLen, output, outputOffset);
                } catch (OutputLengthException e) {
                    throw new IllegalBlockSizeException(e.getMessage());
                } catch (DataLengthException e2) {
                    throw new IllegalBlockSizeException(e2.getMessage());
                }
            }
            return this.p3.c(output, outputOffset + len) + len;
        }
        throw new ShortBufferException("output buffer too short for input.");
    }

    private boolean c(String modeName) {
        return "CCM".equals(modeName) || "EAX".equals(modeName) || CodePackage.GCM.equals(modeName) || "OCB".equals(modeName);
    }

    public static class BufferedGenericBlockCipher implements GenericBlockCipher {
        private BufferedBlockCipher a;

        BufferedGenericBlockCipher(BufferedBlockCipher cipher) {
            this.a = cipher;
        }

        BufferedGenericBlockCipher(BlockCipher cipher) {
            this.a = new PaddedBufferedBlockCipher(cipher);
        }

        BufferedGenericBlockCipher(BlockCipher cipher, BlockCipherPadding padding) {
            this.a = new PaddedBufferedBlockCipher(cipher, padding);
        }

        public void a(boolean forEncryption, CipherParameters params) {
            this.a.f(forEncryption, params);
        }

        public boolean h() {
            return !(this.a instanceof CTSBlockCipher);
        }

        public String b() {
            return this.a.d().b();
        }

        public BlockCipher g() {
            return this.a.d();
        }

        public int f(int len) {
            return this.a.c(len);
        }

        public int e(int len) {
            return this.a.e(len);
        }

        public void i(byte[] input, int offset, int length) {
            throw new UnsupportedOperationException("AAD is not supported in the current mode.");
        }

        public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
            return this.a.g(in, inOff, len, out, outOff);
        }

        public int c(byte[] out, int outOff) {
            try {
                return this.a.a(out, outOff);
            } catch (InvalidCipherTextException e) {
                throw new BadPaddingException(e.getMessage());
            }
        }
    }

    public static class AEADGenericBlockCipher implements GenericBlockCipher {
        private static final Constructor a;
        /* access modifiers changed from: private */
        public AEADBlockCipher b;

        static {
            Class aeadBadTagClass = ClassUtil.a(BaseBlockCipher.class, "javax.crypto.AEADBadTagException");
            if (aeadBadTagClass != null) {
                a = k(aeadBadTagClass);
            } else {
                a = null;
            }
        }

        private static Constructor k(Class clazz) {
            try {
                return clazz.getConstructor(new Class[]{String.class});
            } catch (Exception e) {
                return null;
            }
        }

        AEADGenericBlockCipher(AEADBlockCipher cipher) {
            this.b = cipher;
        }

        public void a(boolean forEncryption, CipherParameters params) {
            this.b.a(forEncryption, params);
        }

        public String b() {
            return this.b.g().b();
        }

        public boolean h() {
            return false;
        }

        public BlockCipher g() {
            return this.b.g();
        }

        public int f(int len) {
            return this.b.f(len);
        }

        public int e(int len) {
            return this.b.e(len);
        }

        public void i(byte[] input, int offset, int length) {
            this.b.i(input, offset, length);
        }

        public int d(byte[] in, int inOff, int len, byte[] out, int outOff) {
            return this.b.d(in, inOff, len, out, outOff);
        }

        public int c(byte[] out, int outOff) {
            try {
                return this.b.c(out, outOff);
            } catch (InvalidCipherTextException e) {
                Constructor constructor = a;
                if (constructor != null) {
                    BadPaddingException aeadBadTag = null;
                    try {
                        aeadBadTag = (BadPaddingException) constructor.newInstance(new Object[]{e.getMessage()});
                    } catch (Exception e2) {
                    }
                    if (aeadBadTag != null) {
                        throw aeadBadTag;
                    }
                }
                throw new BadPaddingException(e.getMessage());
            }
        }
    }

    public static class InvalidKeyOrParametersException extends InvalidKeyException {
        private final Throwable cause;

        InvalidKeyOrParametersException(String msg, Throwable cause2) {
            super(msg);
            this.cause = cause2;
        }

        public Throwable getCause() {
            return this.cause;
        }
    }
}
