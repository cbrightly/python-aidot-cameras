package org.spongycastle.crypto.tls;

public interface TlsPeer {
    TlsCompression h();

    void m(boolean z);

    TlsCipher n();

    void p(short s, short s2, String str, Throwable th);

    void s(short s, short s2);

    void v();
}
