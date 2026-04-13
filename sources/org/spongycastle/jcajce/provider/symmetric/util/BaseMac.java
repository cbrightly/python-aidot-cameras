package org.spongycastle.jcajce.provider.symmetric.util;

import com.alibaba.fastjson.asm.Opcodes;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Hashtable;
import java.util.Map;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.PBEKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.AEADParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.params.RC2Parameters;
import org.spongycastle.crypto.params.SkeinParameters;
import org.spongycastle.jcajce.PKCS12Key;
import org.spongycastle.jcajce.provider.symmetric.util.PBE;
import org.spongycastle.jcajce.spec.AEADParameterSpec;
import org.spongycastle.jcajce.spec.SkeinParameterSpec;

public class BaseMac extends MacSpi implements PBE {
    private static final Class c = ClassUtil.a(BaseMac.class, "javax.crypto.spec.GCMParameterSpec");
    private Mac d;
    private int f = 2;
    private int q = 1;
    private int x = Opcodes.IF_ICMPNE;

    protected BaseMac(Mac macEngine) {
        this.d = macEngine;
    }

    protected BaseMac(Mac macEngine, int scheme, int pbeHash, int keySize) {
        this.d = macEngine;
        this.f = scheme;
        this.q = pbeHash;
        this.x = keySize;
    }

    /* access modifiers changed from: protected */
    public void engineInit(Key key, AlgorithmParameterSpec params) {
        CipherParameters param;
        KeyParameter keyParam;
        if (key != null) {
            if (key instanceof PKCS12Key) {
                try {
                    SecretKey k = (SecretKey) key;
                    try {
                        PBEParameterSpec pbeSpec = (PBEParameterSpec) params;
                        if ((k instanceof PBEKey) && pbeSpec == null) {
                            pbeSpec = new PBEParameterSpec(((PBEKey) k).getSalt(), ((PBEKey) k).getIterationCount());
                        }
                        int digest = 1;
                        int keySize = Opcodes.IF_ICMPNE;
                        if (this.d.b().startsWith("GOST")) {
                            digest = 6;
                            keySize = 256;
                        } else {
                            Mac mac = this.d;
                            if ((mac instanceof HMac) && !mac.b().startsWith("SHA-1")) {
                                if (this.d.b().startsWith("SHA-224")) {
                                    digest = 7;
                                    keySize = 224;
                                } else if (this.d.b().startsWith("SHA-256")) {
                                    digest = 4;
                                    keySize = 256;
                                } else if (this.d.b().startsWith("SHA-384")) {
                                    digest = 8;
                                    keySize = 384;
                                } else if (this.d.b().startsWith("SHA-512")) {
                                    digest = 9;
                                    keySize = 512;
                                } else if (this.d.b().startsWith("RIPEMD160")) {
                                    digest = 2;
                                    keySize = Opcodes.IF_ICMPNE;
                                } else {
                                    throw new InvalidAlgorithmParameterException("no PKCS12 mapping for HMAC: " + this.d.b());
                                }
                            }
                        }
                        param = PBE.Util.c(k, 2, digest, keySize, pbeSpec);
                    } catch (Exception e) {
                        throw new InvalidAlgorithmParameterException("PKCS12 requires a PBEParameterSpec");
                    }
                } catch (Exception e2) {
                    throw new InvalidKeyException("PKCS12 requires a SecretKey/PBEKey");
                }
            } else if (key instanceof BCPBEKey) {
                BCPBEKey k2 = (BCPBEKey) key;
                if (k2.getParam() != null) {
                    param = k2.getParam();
                } else if (params instanceof PBEParameterSpec) {
                    param = PBE.Util.e(k2, params);
                } else {
                    throw new InvalidAlgorithmParameterException("PBE requires PBE parameters to be set.");
                }
            } else if (!(params instanceof PBEParameterSpec)) {
                param = new KeyParameter(key.getEncoded());
            } else {
                throw new InvalidAlgorithmParameterException("inappropriate parameter type: " + params.getClass().getName());
            }
            if (param instanceof ParametersWithIV) {
                keyParam = (KeyParameter) param.b();
            } else {
                keyParam = param;
            }
            if (params instanceof AEADParameterSpec) {
                AEADParameterSpec aeadSpec = (AEADParameterSpec) params;
                param = new AEADParameters(keyParam, aeadSpec.b(), aeadSpec.c(), aeadSpec.a());
            } else if (params instanceof IvParameterSpec) {
                param = new ParametersWithIV(keyParam, ((IvParameterSpec) params).getIV());
            } else if (params instanceof RC2ParameterSpec) {
                param = new ParametersWithIV(new RC2Parameters(keyParam.a(), ((RC2ParameterSpec) params).getEffectiveKeyBits()), ((RC2ParameterSpec) params).getIV());
            } else if (params instanceof SkeinParameterSpec) {
                param = new SkeinParameters.Builder(a(((SkeinParameterSpec) params).a())).c(keyParam.a()).a();
            } else if (params == null) {
                param = new KeyParameter(key.getEncoded());
            } else {
                Class cls = c;
                if (cls != null && cls.isAssignableFrom(params.getClass())) {
                    try {
                        param = new AEADParameters(keyParam, ((Integer) cls.getDeclaredMethod("getTLen", new Class[0]).invoke(params, new Object[0])).intValue(), (byte[]) cls.getDeclaredMethod("getIV", new Class[0]).invoke(params, new Object[0]));
                    } catch (Exception e3) {
                        throw new InvalidAlgorithmParameterException("Cannot process GCMParameterSpec.");
                    }
                } else if (!(params instanceof PBEParameterSpec)) {
                    throw new InvalidAlgorithmParameterException("unknown parameter type: " + params.getClass().getName());
                }
            }
            try {
                this.d.a(param);
            } catch (Exception e4) {
                throw new InvalidAlgorithmParameterException("cannot initialize MAC: " + e4.getMessage());
            }
        } else {
            throw new InvalidKeyException("key is null");
        }
    }

    /* access modifiers changed from: protected */
    public int engineGetMacLength() {
        return this.d.e();
    }

    /* access modifiers changed from: protected */
    public void engineReset() {
        this.d.reset();
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte input) {
        this.d.d(input);
    }

    /* access modifiers changed from: protected */
    public void engineUpdate(byte[] input, int offset, int len) {
        this.d.update(input, offset, len);
    }

    /* access modifiers changed from: protected */
    public byte[] engineDoFinal() {
        byte[] out = new byte[engineGetMacLength()];
        this.d.c(out, 0);
        return out;
    }

    private static Hashtable a(Map paramsMap) {
        Hashtable newTable = new Hashtable();
        for (Object key : paramsMap.keySet()) {
            newTable.put(key, paramsMap.get(key));
        }
        return newTable;
    }
}
