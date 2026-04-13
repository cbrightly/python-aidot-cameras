package okhttp3.internal.tls;

import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: BasicTrustRootIndex.kt */
public final class b implements e {
    private final Map<X500Principal, Set<X509Certificate>> a;

    public b(@NotNull X509Certificate... caCerts) {
        k.f(caCerts, "caCerts");
        Map linkedHashMap = new LinkedHashMap();
        for (X509Certificate caCert : caCerts) {
            Object key$iv = caCert.getSubjectX500Principal();
            k.b(key$iv, "caCert.subjectX500Principal");
            Map $this$getOrPut$iv = linkedHashMap;
            Object value$iv = $this$getOrPut$iv.get(key$iv);
            if (value$iv == null) {
                Object answer$iv = new LinkedHashSet();
                $this$getOrPut$iv.put(key$iv, answer$iv);
                value$iv = answer$iv;
            }
            ((Set) value$iv).add(caCert);
        }
        this.a = linkedHashMap;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.security.cert.X509Certificate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.security.cert.X509Certificate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v0, resolved type: java.security.cert.X509Certificate} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.security.cert.X509Certificate} */
    /* JADX WARNING: Multi-variable type inference failed */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.security.cert.X509Certificate a(@org.jetbrains.annotations.NotNull java.security.cert.X509Certificate r12) {
        /*
            r11 = this;
            java.lang.String r0 = "cert"
            kotlin.jvm.internal.k.f(r12, r0)
            javax.security.auth.x500.X500Principal r0 = r12.getIssuerX500Principal()
            java.util.Map<javax.security.auth.x500.X500Principal, java.util.Set<java.security.cert.X509Certificate>> r1 = r11.a
            java.lang.Object r1 = r1.get(r0)
            java.util.Set r1 = (java.util.Set) r1
            r2 = 0
            if (r1 == 0) goto L_0x003d
            r3 = r1
            r4 = 0
            java.util.Iterator r5 = r3.iterator()
        L_0x001a:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0039
            java.lang.Object r6 = r5.next()
            r7 = r6
            java.security.cert.X509Certificate r7 = (java.security.cert.X509Certificate) r7
            r8 = 0
            java.security.PublicKey r9 = r7.getPublicKey()     // Catch:{ Exception -> 0x0032 }
            r12.verify(r9)     // Catch:{ Exception -> 0x0032 }
            r9 = 1
            goto L_0x0035
        L_0x0032:
            r9 = move-exception
            r10 = 0
            r9 = r10
        L_0x0035:
            if (r9 == 0) goto L_0x001a
            r2 = r6
            goto L_0x003a
        L_0x0039:
        L_0x003a:
            java.security.cert.X509Certificate r2 = (java.security.cert.X509Certificate) r2
            return r2
        L_0x003d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.tls.b.a(java.security.cert.X509Certificate):java.security.cert.X509Certificate");
    }

    public boolean equals(@Nullable Object other) {
        return other == this || ((other instanceof b) && k.a(((b) other).a, this.a));
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
