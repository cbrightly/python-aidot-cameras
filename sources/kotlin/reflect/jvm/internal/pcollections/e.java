package kotlin.reflect.jvm.internal.pcollections;

import java.io.Serializable;

/* compiled from: MapEntry */
public final class e<K, V> implements Serializable {
    public final K key;
    public final V value;

    public e(K key2, V value2) {
        this.key = key2;
        this.value = value2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002b A[ORIG_RETURN, RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof kotlin.reflect.jvm.internal.pcollections.e
            r1 = 0
            if (r0 != 0) goto L_0x0006
            return r1
        L_0x0006:
            r0 = r5
            kotlin.reflect.jvm.internal.pcollections.e r0 = (kotlin.reflect.jvm.internal.pcollections.e) r0
            K r2 = r4.key
            if (r2 != 0) goto L_0x0012
            K r2 = r0.key
            if (r2 != 0) goto L_0x002c
            goto L_0x001a
        L_0x0012:
            K r3 = r0.key
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x002c
        L_0x001a:
            V r2 = r4.value
            if (r2 != 0) goto L_0x0023
            V r2 = r0.value
            if (r2 != 0) goto L_0x002c
            goto L_0x002b
        L_0x0023:
            V r3 = r0.value
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x002c
        L_0x002b:
            r1 = 1
        L_0x002c:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.pcollections.e.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        K k = this.key;
        int i = 0;
        int hashCode = k == null ? 0 : k.hashCode();
        V v = this.value;
        if (v != null) {
            i = v.hashCode();
        }
        return hashCode ^ i;
    }

    public String toString() {
        return this.key + "=" + this.value;
    }
}
