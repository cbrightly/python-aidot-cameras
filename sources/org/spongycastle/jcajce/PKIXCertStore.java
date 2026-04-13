package org.spongycastle.jcajce;

import java.security.cert.Certificate;
import org.spongycastle.util.Store;

public interface PKIXCertStore<T extends Certificate> extends Store<T> {
}
