package com.google.android.libraries.places.internal;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzabs extends zzafz implements zzahi {
    /* access modifiers changed from: private */
    public static final zzabs zzb;
    private zzagh zzd = zzafz.zzB();

    static {
        zzabs zzabs = new zzabs();
        zzb = zzabs;
        zzafz.zzI(zzabs.class, zzabs);
    }

    private zzabs() {
    }

    public static zzabr zza() {
        return (zzabr) zzb.zzw();
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.util.Collection, java.lang.Iterable] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void zzd(com.google.android.libraries.places.internal.zzabs r3, java.lang.Iterable r4) {
        /*
            com.google.android.libraries.places.internal.zzagh r0 = r3.zzd
            boolean r1 = r0.zzc()
            if (r1 != 0) goto L_0x000e
            com.google.android.libraries.places.internal.zzagh r0 = com.google.android.libraries.places.internal.zzafz.zzC(r0)
            r3.zzd = r0
        L_0x000e:
            com.google.android.libraries.places.internal.zzagh r3 = r3.zzd
            byte[] r0 = com.google.android.libraries.places.internal.zzagi.zzd
            boolean r0 = r3 instanceof java.util.ArrayList
            if (r0 == 0) goto L_0x0025
            r0 = r3
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r1 = r3.size()
            int r2 = r4.size()
            int r1 = r1 + r2
            r0.ensureCapacity(r1)
        L_0x0025:
            int r0 = r3.size()
            java.util.Iterator r4 = r4.iterator()
        L_0x002d:
            boolean r1 = r4.hasNext()
            if (r1 == 0) goto L_0x006c
            java.lang.Object r1 = r4.next()
            if (r1 != 0) goto L_0x0068
            int r4 = r3.size()
            int r4 = r4 - r0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Element at index "
            r1.append(r2)
            r1.append(r4)
            java.lang.String r4 = " is null."
            r1.append(r4)
            java.lang.String r4 = r1.toString()
            int r1 = r3.size()
            int r1 = r1 + -1
        L_0x005a:
            if (r1 < r0) goto L_0x0062
            r3.remove(r1)
            int r1 = r1 + -1
            goto L_0x005a
        L_0x0062:
            java.lang.NullPointerException r3 = new java.lang.NullPointerException
            r3.<init>(r4)
            throw r3
        L_0x0068:
            r3.add(r1)
            goto L_0x002d
        L_0x006c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.internal.zzabs.zzd(com.google.android.libraries.places.internal.zzabs, java.lang.Iterable):void");
    }

    /* access modifiers changed from: protected */
    public final Object zzb(int i, Object obj, Object obj2) {
        switch (i - 1) {
            case 0:
                return (byte) 1;
            case 2:
                return zzafz.zzF(zzb, "\u0001\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0001\u0000\u0001\u001a", new Object[]{"zzd"});
            case 3:
                return new zzabs();
            case 4:
                return new zzabr((zzya) null);
            case 5:
                return zzb;
            default:
                return null;
        }
    }
}
