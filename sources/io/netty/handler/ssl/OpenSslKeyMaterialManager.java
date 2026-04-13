package io.netty.handler.ssl;

import io.netty.buffer.ByteBufAllocator;
import io.netty.internal.tcnative.CertificateRequestedCallback;
import io.netty.internal.tcnative.SSL;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.SSLException;
import javax.net.ssl.X509KeyManager;
import javax.security.auth.x500.X500Principal;

public class OpenSslKeyMaterialManager {
    private static final Map<String, String> KEY_TYPES;
    static final String KEY_TYPE_DH_RSA = "DH_RSA";
    static final String KEY_TYPE_EC = "EC";
    static final String KEY_TYPE_EC_EC = "EC_EC";
    static final String KEY_TYPE_EC_RSA = "EC_RSA";
    static final String KEY_TYPE_RSA = "RSA";
    private final X509KeyManager keyManager;
    private final String password;

    static {
        HashMap hashMap = new HashMap();
        KEY_TYPES = hashMap;
        hashMap.put(KEY_TYPE_RSA, KEY_TYPE_RSA);
        hashMap.put("DHE_RSA", KEY_TYPE_RSA);
        hashMap.put("ECDHE_RSA", KEY_TYPE_RSA);
        hashMap.put("ECDHE_ECDSA", KEY_TYPE_EC);
        hashMap.put("ECDH_RSA", KEY_TYPE_EC_RSA);
        hashMap.put("ECDH_ECDSA", KEY_TYPE_EC_EC);
        hashMap.put(KEY_TYPE_DH_RSA, KEY_TYPE_DH_RSA);
    }

    OpenSslKeyMaterialManager(X509KeyManager keyManager2, String password2) {
        this.keyManager = keyManager2;
        this.password = password2;
    }

    /* access modifiers changed from: package-private */
    public void setKeyMaterial(ReferenceCountedOpenSslEngine engine) {
        String alias;
        long ssl = engine.sslPointer();
        String[] authMethods = SSL.authenticationMethods(ssl);
        Set<String> aliases = new HashSet<>(authMethods.length);
        for (String authMethod : authMethods) {
            String type = KEY_TYPES.get(authMethod);
            if (!(type == null || (alias = chooseServerAlias(engine, type)) == null || !aliases.add(alias))) {
                setKeyMaterial(ssl, alias);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public CertificateRequestedCallback.KeyMaterial keyMaterial(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        String alias = chooseClientAlias(engine, keyTypes, issuer);
        long keyBio = 0;
        long pkey = 0;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(alias);
            if (certificates != null) {
                if (certificates.length != 0) {
                    PrivateKey key = this.keyManager.getPrivateKey(alias);
                    long keyCertChainBio = ReferenceCountedOpenSslContext.toBIO(certificates);
                    long certChain = SSL.parseX509Chain(keyCertChainBio);
                    if (key != null) {
                        keyBio = ReferenceCountedOpenSslContext.toBIO(key);
                        pkey = SSL.parsePrivateKey(keyBio, this.password);
                    }
                    CertificateRequestedCallback.KeyMaterial material = new CertificateRequestedCallback.KeyMaterial(certChain, pkey);
                    ReferenceCountedOpenSslContext.freeBio(keyBio);
                    ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
                    SSL.freePrivateKey(0);
                    SSL.freeX509Chain(0);
                    return material;
                }
            }
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            SSL.freePrivateKey(0);
            SSL.freeX509Chain(0);
            return null;
        } catch (SSLException e) {
            throw e;
        } catch (Exception e2) {
            throw new SSLException(e2);
        } catch (Throwable th) {
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            SSL.freePrivateKey(0);
            SSL.freeX509Chain(0);
            throw th;
        }
    }

    private void setKeyMaterial(long ssl, String alias) {
        String str = alias;
        long keyBio = 0;
        long keyCertChainBio = 0;
        long keyCertChainBio2 = 0;
        try {
            X509Certificate[] certificates = this.keyManager.getCertificateChain(str);
            if (certificates == null) {
                long j = ssl;
            } else if (certificates.length == 0) {
                long j2 = ssl;
            } else {
                PrivateKey key = this.keyManager.getPrivateKey(str);
                ByteBufAllocator byteBufAllocator = ByteBufAllocator.DEFAULT;
                PemEncoded encoded = PemX509Certificate.toPEM(byteBufAllocator, true, certificates);
                try {
                    long keyCertChainBio3 = ReferenceCountedOpenSslContext.toBIO(byteBufAllocator, encoded.retain());
                    try {
                        keyCertChainBio2 = ReferenceCountedOpenSslContext.toBIO(byteBufAllocator, encoded.retain());
                        if (key != null) {
                            keyBio = ReferenceCountedOpenSslContext.toBIO(key);
                        }
                        SSL.setCertificateBio(ssl, keyCertChainBio3, keyBio, this.password);
                        try {
                            SSL.setCertificateChainBio(ssl, keyCertChainBio2, true);
                        } catch (Throwable th) {
                            th = th;
                            keyCertChainBio = keyCertChainBio3;
                            try {
                                encoded.release();
                                throw th;
                            } catch (SSLException e) {
                                e = e;
                                throw e;
                            } catch (Exception e2) {
                                e = e2;
                                throw new SSLException(e);
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        long j3 = ssl;
                        keyCertChainBio = keyCertChainBio3;
                        encoded.release();
                        throw th;
                    }
                    try {
                        encoded.release();
                        ReferenceCountedOpenSslContext.freeBio(keyBio);
                        ReferenceCountedOpenSslContext.freeBio(keyCertChainBio3);
                        ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
                        return;
                    } catch (SSLException e3) {
                        e = e3;
                        long j4 = keyCertChainBio3;
                        throw e;
                    } catch (Exception e4) {
                        e = e4;
                        keyCertChainBio = keyCertChainBio3;
                        throw new SSLException(e);
                    } catch (Throwable th3) {
                        e = th3;
                        keyCertChainBio = keyCertChainBio3;
                        ReferenceCountedOpenSslContext.freeBio(keyBio);
                        ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
                        ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
                        throw e;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    long j5 = ssl;
                    encoded.release();
                    throw th;
                }
            }
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
            ReferenceCountedOpenSslContext.freeBio(0);
        } catch (SSLException e5) {
            e = e5;
            long j6 = ssl;
            throw e;
        } catch (Exception e6) {
            e = e6;
            long j7 = ssl;
            throw new SSLException(e);
        } catch (Throwable th5) {
            e = th5;
            ReferenceCountedOpenSslContext.freeBio(keyBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio);
            ReferenceCountedOpenSslContext.freeBio(keyCertChainBio2);
            throw e;
        }
    }

    /* access modifiers changed from: protected */
    public String chooseClientAlias(ReferenceCountedOpenSslEngine engine, String[] keyTypes, X500Principal[] issuer) {
        return this.keyManager.chooseClientAlias(keyTypes, issuer, (Socket) null);
    }

    /* access modifiers changed from: protected */
    public String chooseServerAlias(ReferenceCountedOpenSslEngine engine, String type) {
        return this.keyManager.chooseServerAlias(type, (Principal[]) null, (Socket) null);
    }
}
