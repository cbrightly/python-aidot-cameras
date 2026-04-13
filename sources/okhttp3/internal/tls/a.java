package okhttp3.internal.tls;

import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BasicCertificateChainCleaner.kt */
public final class a extends c {
    public static final C0471a b = new C0471a((DefaultConstructorMarker) null);
    private final e c;

    public a(@NotNull e trustRootIndex) {
        k.f(trustRootIndex, "trustRootIndex");
        this.c = trustRootIndex;
    }

    @NotNull
    public List<Certificate> a(@NotNull List<? extends Certificate> chain, @NotNull String hostname) {
        k.f(chain, "chain");
        k.f(hostname, "hostname");
        Deque queue = new ArrayDeque(chain);
        List result = new ArrayList();
        Object removeFirst = queue.removeFirst();
        k.b(removeFirst, "queue.removeFirst()");
        result.add(removeFirst);
        boolean foundTrustedCertificate = false;
        int c2 = 0;
        while (c2 < 9) {
            Object obj = result.get(result.size() - 1);
            if (obj != null) {
                X509Certificate toVerify = (X509Certificate) obj;
                X509Certificate trustedCert = this.c.a(toVerify);
                if (trustedCert != null) {
                    if (result.size() > 1 || (true ^ k.a(toVerify, trustedCert))) {
                        result.add(trustedCert);
                    }
                    if (b(trustedCert, trustedCert)) {
                        return result;
                    }
                    foundTrustedCertificate = true;
                } else {
                    Iterator i = queue.iterator();
                    k.b(i, "queue.iterator()");
                    while (i.hasNext()) {
                        Object next = i.next();
                        if (next != null) {
                            X509Certificate signingCert = (X509Certificate) next;
                            if (b(toVerify, signingCert)) {
                                i.remove();
                                result.add(signingCert);
                            }
                        } else {
                            throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
                        }
                    }
                    if (foundTrustedCertificate) {
                        return result;
                    }
                    throw new SSLPeerUnverifiedException("Failed to find a trusted cert that signed " + toVerify);
                }
                c2++;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
            }
        }
        throw new SSLPeerUnverifiedException("Certificate chain too long: " + result);
    }

    private final boolean b(X509Certificate toVerify, X509Certificate signingCert) {
        if (!k.a(toVerify.getIssuerDN(), signingCert.getSubjectDN())) {
            return false;
        }
        try {
            toVerify.verify(signingCert.getPublicKey());
            return true;
        } catch (GeneralSecurityException e) {
            return false;
        }
    }

    public int hashCode() {
        return this.c.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof a) || !k.a(((a) other).c, this.c)) {
            return false;
        }
        return true;
    }

    /* renamed from: okhttp3.internal.tls.a$a  reason: collision with other inner class name */
    /* compiled from: BasicCertificateChainCleaner.kt */
    public static final class C0471a {
        private C0471a() {
        }

        public /* synthetic */ C0471a(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
