package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.util.HashMap;
import java.util.Map;

public final class zzer<T> {
    private final Map<T, zzhk<T>> zzeb = new HashMap();

    zzer() {
    }

    /* JADX WARNING: type inference failed for: r1v2, types: [android.os.IInterface] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(android.os.IBinder r11) {
        /*
            r10 = this;
            java.util.Map<T, com.google.android.gms.wearable.internal.zzhk<T>> r0 = r10.zzeb
            monitor-enter(r0)
            if (r11 != 0) goto L_0x0008
            r11 = 0
            goto L_0x001c
        L_0x0008:
            java.lang.String r1 = "com.google.android.gms.wearable.internal.IWearableService"
            android.os.IInterface r1 = r11.queryLocalInterface(r1)     // Catch:{ all -> 0x00cf }
            boolean r2 = r1 instanceof com.google.android.gms.wearable.internal.zzep     // Catch:{ all -> 0x00cf }
            if (r2 == 0) goto L_0x0016
            r11 = r1
            com.google.android.gms.wearable.internal.zzep r11 = (com.google.android.gms.wearable.internal.zzep) r11     // Catch:{ all -> 0x00cf }
            goto L_0x001c
        L_0x0016:
            com.google.android.gms.wearable.internal.zzeq r1 = new com.google.android.gms.wearable.internal.zzeq     // Catch:{ all -> 0x00cf }
            r1.<init>(r11)     // Catch:{ all -> 0x00cf }
            r11 = r1
        L_0x001c:
            com.google.android.gms.wearable.internal.zzgz r1 = new com.google.android.gms.wearable.internal.zzgz     // Catch:{ all -> 0x00cf }
            r1.<init>()     // Catch:{ all -> 0x00cf }
            java.util.Map<T, com.google.android.gms.wearable.internal.zzhk<T>> r2 = r10.zzeb     // Catch:{ all -> 0x00cf }
            java.util.Set r2 = r2.entrySet()     // Catch:{ all -> 0x00cf }
            java.util.Iterator r2 = r2.iterator()     // Catch:{ all -> 0x00cf }
        L_0x002c:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x00cf }
            if (r3 == 0) goto L_0x00cd
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x00cf }
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3     // Catch:{ all -> 0x00cf }
            java.lang.Object r4 = r3.getValue()     // Catch:{ all -> 0x00cf }
            com.google.android.gms.wearable.internal.zzhk r4 = (com.google.android.gms.wearable.internal.zzhk) r4     // Catch:{ all -> 0x00cf }
            com.google.android.gms.wearable.internal.zzd r5 = new com.google.android.gms.wearable.internal.zzd     // Catch:{ RemoteException -> 0x008d }
            r5.<init>(r4)     // Catch:{ RemoteException -> 0x008d }
            r11.zza((com.google.android.gms.wearable.internal.zzek) r1, (com.google.android.gms.wearable.internal.zzd) r5)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r5 = "WearableClient"
            r6 = 3
            boolean r5 = android.util.Log.isLoggable(r5, r6)     // Catch:{ RemoteException -> 0x008d }
            if (r5 == 0) goto L_0x008c
            java.lang.String r5 = "WearableClient"
            java.lang.Object r6 = r3.getKey()     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r7 = java.lang.String.valueOf(r4)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch:{ RemoteException -> 0x008d }
            int r8 = r8.length()     // Catch:{ RemoteException -> 0x008d }
            int r8 = r8 + 27
            java.lang.String r9 = java.lang.String.valueOf(r7)     // Catch:{ RemoteException -> 0x008d }
            int r9 = r9.length()     // Catch:{ RemoteException -> 0x008d }
            int r8 = r8 + r9
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ RemoteException -> 0x008d }
            r9.<init>(r8)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r8 = "onPostInitHandler: added: "
            r9.append(r8)     // Catch:{ RemoteException -> 0x008d }
            r9.append(r6)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r6 = "/"
            r9.append(r6)     // Catch:{ RemoteException -> 0x008d }
            r9.append(r7)     // Catch:{ RemoteException -> 0x008d }
            java.lang.String r6 = r9.toString()     // Catch:{ RemoteException -> 0x008d }
            android.util.Log.d(r5, r6)     // Catch:{ RemoteException -> 0x008d }
        L_0x008c:
            goto L_0x002c
        L_0x008d:
            r5 = move-exception
            java.lang.String r5 = "WearableClient"
            java.lang.Object r3 = r3.getKey()     // Catch:{ all -> 0x00cf }
            java.lang.String r3 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x00cf }
            java.lang.String r4 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x00cf }
            java.lang.String r6 = java.lang.String.valueOf(r3)     // Catch:{ all -> 0x00cf }
            int r6 = r6.length()     // Catch:{ all -> 0x00cf }
            int r6 = r6 + 32
            java.lang.String r7 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x00cf }
            int r7 = r7.length()     // Catch:{ all -> 0x00cf }
            int r6 = r6 + r7
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x00cf }
            r7.<init>(r6)     // Catch:{ all -> 0x00cf }
            java.lang.String r6 = "onPostInitHandler: Didn't add: "
            r7.append(r6)     // Catch:{ all -> 0x00cf }
            r7.append(r3)     // Catch:{ all -> 0x00cf }
            java.lang.String r3 = "/"
            r7.append(r3)     // Catch:{ all -> 0x00cf }
            r7.append(r4)     // Catch:{ all -> 0x00cf }
            java.lang.String r3 = r7.toString()     // Catch:{ all -> 0x00cf }
            android.util.Log.w(r5, r3)     // Catch:{ all -> 0x00cf }
            goto L_0x002c
        L_0x00cd:
            monitor-exit(r0)     // Catch:{ all -> 0x00cf }
            return
        L_0x00cf:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00cf }
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.internal.zzer.zza(android.os.IBinder):void");
    }

    public final void zza(zzhg zzhg, BaseImplementation.ResultHolder<Status> resultHolder, T t, zzhk<T> zzhk) {
        synchronized (this.zzeb) {
            if (this.zzeb.get(t) != null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String valueOf = String.valueOf(t);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 20);
                    sb.append("duplicate listener: ");
                    sb.append(valueOf);
                    Log.v("WearableClient", sb.toString());
                }
                resultHolder.setResult(new Status(WearableStatusCodes.DUPLICATE_LISTENER));
                return;
            }
            if (Log.isLoggable("WearableClient", 2)) {
                String valueOf2 = String.valueOf(t);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 14);
                sb2.append("new listener: ");
                sb2.append(valueOf2);
                Log.v("WearableClient", sb2.toString());
            }
            this.zzeb.put(t, zzhk);
            try {
                ((zzep) zzhg.getService()).zza((zzek) new zzes(this.zzeb, t, resultHolder), new zzd(zzhk));
            } catch (RemoteException e) {
                if (Log.isLoggable("WearableClient", 3)) {
                    String valueOf3 = String.valueOf(t);
                    StringBuilder sb3 = new StringBuilder(String.valueOf(valueOf3).length() + 39);
                    sb3.append("addListener failed, removing listener: ");
                    sb3.append(valueOf3);
                    Log.d("WearableClient", sb3.toString());
                }
                this.zzeb.remove(t);
                throw e;
            }
        }
    }

    public final void zza(zzhg zzhg, BaseImplementation.ResultHolder<Status> resultHolder, T t) {
        synchronized (this.zzeb) {
            zzhk remove = this.zzeb.remove(t);
            if (remove == null) {
                if (Log.isLoggable("WearableClient", 2)) {
                    String valueOf = String.valueOf(t);
                    StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 25);
                    sb.append("remove Listener unknown: ");
                    sb.append(valueOf);
                    Log.v("WearableClient", sb.toString());
                }
                resultHolder.setResult(new Status(WearableStatusCodes.UNKNOWN_LISTENER));
                return;
            }
            remove.clear();
            if (Log.isLoggable("WearableClient", 2)) {
                String valueOf2 = String.valueOf(t);
                StringBuilder sb2 = new StringBuilder(String.valueOf(valueOf2).length() + 24);
                sb2.append("service.removeListener: ");
                sb2.append(valueOf2);
                Log.v("WearableClient", sb2.toString());
            }
            ((zzep) zzhg.getService()).zza((zzek) new zzet(this.zzeb, t, resultHolder), new zzfw(remove));
        }
    }
}
