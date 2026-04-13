package com.google.android.gms.internal.measurement;

import android.os.IBinder;
import android.os.IInterface;

/* compiled from: com.google.android.gms:play-services-measurement-base@@21.2.2 */
public abstract class zzcb extends zzbn implements zzcc {
    public zzcb() {
        super("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
    }

    public static zzcc asInterface(IBinder obj) {
        if (obj == null) {
            return null;
        }
        IInterface queryLocalInterface = obj.queryLocalInterface("com.google.android.gms.measurement.api.internal.IAppMeasurementDynamiteService");
        return queryLocalInterface instanceof zzcc ? (zzcc) queryLocalInterface : new zzca(obj);
    }

    /* JADX WARNING: type inference failed for: r3v0 */
    /* JADX WARNING: type inference failed for: r3v9, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v14, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v22, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v28, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v33, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v38, types: [com.google.android.gms.internal.measurement.zzck] */
    /* JADX WARNING: type inference failed for: r3v43, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v48, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v53, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v58, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v64, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v69, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v76, types: [com.google.android.gms.internal.measurement.zzci] */
    /* JADX WARNING: type inference failed for: r3v81, types: [com.google.android.gms.internal.measurement.zzci] */
    /* JADX WARNING: type inference failed for: r3v86, types: [com.google.android.gms.internal.measurement.zzci] */
    /* JADX WARNING: type inference failed for: r3v91, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v96, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v101, types: [com.google.android.gms.internal.measurement.zzcf] */
    /* JADX WARNING: type inference failed for: r3v106 */
    /* JADX WARNING: type inference failed for: r3v107 */
    /* JADX WARNING: type inference failed for: r3v108 */
    /* JADX WARNING: type inference failed for: r3v109 */
    /* JADX WARNING: type inference failed for: r3v110 */
    /* JADX WARNING: type inference failed for: r3v111 */
    /* JADX WARNING: type inference failed for: r3v112 */
    /* JADX WARNING: type inference failed for: r3v113 */
    /* JADX WARNING: type inference failed for: r3v114 */
    /* JADX WARNING: type inference failed for: r3v115 */
    /* JADX WARNING: type inference failed for: r3v116 */
    /* JADX WARNING: type inference failed for: r3v117 */
    /* JADX WARNING: type inference failed for: r3v118 */
    /* JADX WARNING: type inference failed for: r3v119 */
    /* JADX WARNING: type inference failed for: r3v120 */
    /* JADX WARNING: type inference failed for: r3v121 */
    /* JADX WARNING: type inference failed for: r3v122 */
    /* JADX WARNING: type inference failed for: r3v123 */
    /* JADX WARNING: type inference failed for: r3v124 */
    /* JADX WARNING: type inference failed for: r3v125 */
    /* JADX WARNING: type inference failed for: r3v126 */
    /* JADX WARNING: type inference failed for: r3v127 */
    /* JADX WARNING: type inference failed for: r3v128 */
    /* JADX WARNING: type inference failed for: r3v129 */
    /* JADX WARNING: type inference failed for: r3v130 */
    /* JADX WARNING: type inference failed for: r3v131 */
    /* JADX WARNING: type inference failed for: r3v132 */
    /* JADX WARNING: type inference failed for: r3v133 */
    /* JADX WARNING: type inference failed for: r3v134 */
    /* JADX WARNING: type inference failed for: r3v135 */
    /* JADX WARNING: type inference failed for: r3v136 */
    /* JADX WARNING: type inference failed for: r3v137 */
    /* JADX WARNING: type inference failed for: r3v138 */
    /* JADX WARNING: type inference failed for: r3v139 */
    /* JADX WARNING: type inference failed for: r3v140 */
    /* JADX WARNING: type inference failed for: r3v141 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean zza(int r11, android.os.Parcel r12, android.os.Parcel r13, int r14) {
        /*
            r10 = this;
            java.lang.String r1 = "com.google.android.gms.measurement.api.internal.IEventHandlerProxy"
            java.lang.String r2 = "com.google.android.gms.measurement.api.internal.IBundleReceiver"
            r3 = 0
            switch(r11) {
                case 1: goto L_0x04c3;
                case 2: goto L_0x049f;
                case 3: goto L_0x0465;
                case 4: goto L_0x0444;
                case 5: goto L_0x0417;
                case 6: goto L_0x03f2;
                case 7: goto L_0x03e2;
                case 8: goto L_0x03ce;
                case 9: goto L_0x03b6;
                case 10: goto L_0x038d;
                case 11: goto L_0x037d;
                case 12: goto L_0x0371;
                case 13: goto L_0x0365;
                case 14: goto L_0x0359;
                case 15: goto L_0x033c;
                case 16: goto L_0x031b;
                case 17: goto L_0x02fa;
                case 18: goto L_0x02d7;
                case 19: goto L_0x02b6;
                case 20: goto L_0x0295;
                case 21: goto L_0x0274;
                case 22: goto L_0x0253;
                case 23: goto L_0x0243;
                case 24: goto L_0x0233;
                case 25: goto L_0x021f;
                case 26: goto L_0x020b;
                case 27: goto L_0x01ef;
                case 28: goto L_0x01db;
                case 29: goto L_0x01c7;
                case 30: goto L_0x01b3;
                case 31: goto L_0x0186;
                case 32: goto L_0x0159;
                case 33: goto L_0x0130;
                case 34: goto L_0x010f;
                case 35: goto L_0x00ee;
                case 36: goto L_0x00cd;
                case 37: goto L_0x00c1;
                case 38: goto L_0x009c;
                case 39: goto L_0x0090;
                case 40: goto L_0x006f;
                case 41: goto L_0x0008;
                case 42: goto L_0x005f;
                case 43: goto L_0x0053;
                case 44: goto L_0x003f;
                case 45: goto L_0x002b;
                case 46: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            r0 = 0
            return r0
        L_0x000a:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x0011
            goto L_0x0023
        L_0x0011:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x001e
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x0023
        L_0x001e:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x0023:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getSessionId(r3)
            goto L_0x04dd
        L_0x002b:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setConsentThirdParty(r1, r2)
            goto L_0x04dd
        L_0x003f:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setConsent(r1, r2)
            goto L_0x04dd
        L_0x0053:
            long r1 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.clearMeasurementEnabled(r1)
            goto L_0x04dd
        L_0x005f:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setDefaultEventParameters(r1)
            goto L_0x04dd
        L_0x006f:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x0076
            goto L_0x0088
        L_0x0076:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0083
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x0088
        L_0x0083:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x0088:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.isDataCollectionEnabled(r3)
            goto L_0x04dd
        L_0x0090:
            boolean r1 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setDataCollectionEnabled(r1)
            goto L_0x04dd
        L_0x009c:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x00a3
            goto L_0x00b5
        L_0x00a3:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x00b0
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x00b5
        L_0x00b0:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x00b5:
            int r1 = r12.readInt()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getTestFlag(r3, r1)
            goto L_0x04dd
        L_0x00c1:
            java.util.HashMap r1 = com.google.android.gms.internal.measurement.zzbo.zzb(r12)
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.initForTests(r1)
            goto L_0x04dd
        L_0x00cd:
            android.os.IBinder r2 = r12.readStrongBinder()
            if (r2 != 0) goto L_0x00d4
            goto L_0x00e6
        L_0x00d4:
            android.os.IInterface r1 = r2.queryLocalInterface(r1)
            boolean r3 = r1 instanceof com.google.android.gms.internal.measurement.zzci
            if (r3 == 0) goto L_0x00e1
            r3 = r1
            com.google.android.gms.internal.measurement.zzci r3 = (com.google.android.gms.internal.measurement.zzci) r3
            goto L_0x00e6
        L_0x00e1:
            com.google.android.gms.internal.measurement.zzcg r3 = new com.google.android.gms.internal.measurement.zzcg
            r3.<init>(r2)
        L_0x00e6:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.unregisterOnMeasurementEventListener(r3)
            goto L_0x04dd
        L_0x00ee:
            android.os.IBinder r2 = r12.readStrongBinder()
            if (r2 != 0) goto L_0x00f5
            goto L_0x0107
        L_0x00f5:
            android.os.IInterface r1 = r2.queryLocalInterface(r1)
            boolean r3 = r1 instanceof com.google.android.gms.internal.measurement.zzci
            if (r3 == 0) goto L_0x0102
            r3 = r1
            com.google.android.gms.internal.measurement.zzci r3 = (com.google.android.gms.internal.measurement.zzci) r3
            goto L_0x0107
        L_0x0102:
            com.google.android.gms.internal.measurement.zzcg r3 = new com.google.android.gms.internal.measurement.zzcg
            r3.<init>(r2)
        L_0x0107:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.registerOnMeasurementEventListener(r3)
            goto L_0x04dd
        L_0x010f:
            android.os.IBinder r2 = r12.readStrongBinder()
            if (r2 != 0) goto L_0x0116
            goto L_0x0128
        L_0x0116:
            android.os.IInterface r1 = r2.queryLocalInterface(r1)
            boolean r3 = r1 instanceof com.google.android.gms.internal.measurement.zzci
            if (r3 == 0) goto L_0x0123
            r3 = r1
            com.google.android.gms.internal.measurement.zzci r3 = (com.google.android.gms.internal.measurement.zzci) r3
            goto L_0x0128
        L_0x0123:
            com.google.android.gms.internal.measurement.zzcg r3 = new com.google.android.gms.internal.measurement.zzcg
            r3.<init>(r2)
        L_0x0128:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setEventInterceptor(r3)
            goto L_0x04dd
        L_0x0130:
            int r1 = r12.readInt()
            java.lang.String r2 = r12.readString()
            android.os.IBinder r3 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            android.os.IBinder r4 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r4 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r4)
            android.os.IBinder r5 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r5 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r5)
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r0 = r10
            r0.logHealthData(r1, r2, r3, r4, r5)
            goto L_0x04dd
        L_0x0159:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            android.os.IBinder r4 = r12.readStrongBinder()
            if (r4 != 0) goto L_0x0168
            goto L_0x017a
        L_0x0168:
            android.os.IInterface r2 = r4.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0175
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x017a
        L_0x0175:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r4)
        L_0x017a:
            long r4 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.performAction(r1, r3, r4)
            goto L_0x04dd
        L_0x0186:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.IBinder r4 = r12.readStrongBinder()
            if (r4 != 0) goto L_0x0195
            goto L_0x01a7
        L_0x0195:
            android.os.IInterface r2 = r4.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x01a2
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x01a7
        L_0x01a2:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r4)
        L_0x01a7:
            long r4 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivitySaveInstanceState(r1, r3, r4)
            goto L_0x04dd
        L_0x01b3:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityResumed(r1, r2)
            goto L_0x04dd
        L_0x01c7:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityPaused(r1, r2)
            goto L_0x04dd
        L_0x01db:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityDestroyed(r1, r2)
            goto L_0x04dd
        L_0x01ef:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.Parcelable$Creator r2 = android.os.Bundle.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r2)
            android.os.Bundle r2 = (android.os.Bundle) r2
            long r3 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityCreated(r1, r2, r3)
            goto L_0x04dd
        L_0x020b:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityStopped(r1, r2)
            goto L_0x04dd
        L_0x021f:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.onActivityStarted(r1, r2)
            goto L_0x04dd
        L_0x0233:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.endAdUnitExposure(r1, r2)
            goto L_0x04dd
        L_0x0243:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.beginAdUnitExposure(r1, r2)
            goto L_0x04dd
        L_0x0253:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x025a
            goto L_0x026c
        L_0x025a:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0267
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x026c
        L_0x0267:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x026c:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.generateEventId(r3)
            goto L_0x04dd
        L_0x0274:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x027b
            goto L_0x028d
        L_0x027b:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0288
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x028d
        L_0x0288:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x028d:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getGmpAppId(r3)
            goto L_0x04dd
        L_0x0295:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x029c
            goto L_0x02ae
        L_0x029c:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x02a9
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x02ae
        L_0x02a9:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x02ae:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getAppInstanceId(r3)
            goto L_0x04dd
        L_0x02b6:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x02bd
            goto L_0x02cf
        L_0x02bd:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x02ca
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x02cf
        L_0x02ca:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x02cf:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getCachedAppInstanceId(r3)
            goto L_0x04dd
        L_0x02d7:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x02de
            goto L_0x02f2
        L_0x02de:
            java.lang.String r2 = "com.google.android.gms.measurement.api.internal.IStringProvider"
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzck
            if (r3 == 0) goto L_0x02ed
            r3 = r2
            com.google.android.gms.internal.measurement.zzck r3 = (com.google.android.gms.internal.measurement.zzck) r3
            goto L_0x02f2
        L_0x02ed:
            com.google.android.gms.internal.measurement.zzcj r3 = new com.google.android.gms.internal.measurement.zzcj
            r3.<init>(r1)
        L_0x02f2:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setInstanceIdProvider(r3)
            goto L_0x04dd
        L_0x02fa:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x0301
            goto L_0x0313
        L_0x0301:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x030e
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x0313
        L_0x030e:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x0313:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getCurrentScreenClass(r3)
            goto L_0x04dd
        L_0x031b:
            android.os.IBinder r1 = r12.readStrongBinder()
            if (r1 != 0) goto L_0x0322
            goto L_0x0334
        L_0x0322:
            android.os.IInterface r2 = r1.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x032f
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x0334
        L_0x032f:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r1)
        L_0x0334:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getCurrentScreenName(r3)
            goto L_0x04dd
        L_0x033c:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            java.lang.String r2 = r12.readString()
            java.lang.String r3 = r12.readString()
            long r4 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r0 = r10
            r0.setCurrentScreen(r1, r2, r3, r4)
            goto L_0x04dd
        L_0x0359:
            long r1 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setSessionTimeoutDuration(r1)
            goto L_0x04dd
        L_0x0365:
            long r1 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setMinimumSessionDuration(r1)
            goto L_0x04dd
        L_0x0371:
            long r1 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.resetAnalyticsData(r1)
            goto L_0x04dd
        L_0x037d:
            boolean r1 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setMeasurementEnabled(r1, r2)
            goto L_0x04dd
        L_0x038d:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            android.os.IBinder r5 = r12.readStrongBinder()
            if (r5 != 0) goto L_0x039c
            goto L_0x03ae
        L_0x039c:
            android.os.IInterface r2 = r5.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x03a9
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x03ae
        L_0x03a9:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r5)
        L_0x03ae:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getConditionalUserProperties(r1, r4, r3)
            goto L_0x04dd
        L_0x03b6:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r3)
            android.os.Bundle r3 = (android.os.Bundle) r3
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.clearConditionalUserProperty(r1, r2, r3)
            goto L_0x04dd
        L_0x03ce:
            android.os.Parcelable$Creator r1 = android.os.Bundle.CREATOR
            android.os.Parcelable r1 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r1)
            android.os.Bundle r1 = (android.os.Bundle) r1
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setConditionalUserProperty(r1, r2)
            goto L_0x04dd
        L_0x03e2:
            java.lang.String r1 = r12.readString()
            long r2 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.setUserId(r1, r2)
            goto L_0x04dd
        L_0x03f2:
            java.lang.String r1 = r12.readString()
            android.os.IBinder r4 = r12.readStrongBinder()
            if (r4 != 0) goto L_0x03fd
            goto L_0x040f
        L_0x03fd:
            android.os.IInterface r2 = r4.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x040a
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x040f
        L_0x040a:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r4)
        L_0x040f:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getMaxUserProperties(r1, r3)
            goto L_0x04dd
        L_0x0417:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            boolean r5 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            android.os.IBinder r6 = r12.readStrongBinder()
            if (r6 != 0) goto L_0x042a
            goto L_0x043c
        L_0x042a:
            android.os.IInterface r2 = r6.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0437
            r3 = r2
            com.google.android.gms.internal.measurement.zzcf r3 = (com.google.android.gms.internal.measurement.zzcf) r3
            goto L_0x043c
        L_0x0437:
            com.google.android.gms.internal.measurement.zzcd r3 = new com.google.android.gms.internal.measurement.zzcd
            r3.<init>(r6)
        L_0x043c:
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.getUserProperties(r1, r4, r5, r3)
            goto L_0x04dd
        L_0x0444:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.IBinder r3 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r3 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r3)
            boolean r4 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            long r5 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r0 = r10
            r0.setUserProperty(r1, r2, r3, r4, r5)
            goto L_0x04dd
        L_0x0465:
            java.lang.String r1 = r12.readString()
            java.lang.String r4 = r12.readString()
            android.os.Parcelable$Creator r5 = android.os.Bundle.CREATOR
            android.os.Parcelable r5 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r5)
            android.os.Bundle r5 = (android.os.Bundle) r5
            android.os.IBinder r6 = r12.readStrongBinder()
            if (r6 != 0) goto L_0x047d
            r6 = r3
            goto L_0x048f
        L_0x047d:
            android.os.IInterface r2 = r6.queryLocalInterface(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzcf
            if (r3 == 0) goto L_0x0489
            com.google.android.gms.internal.measurement.zzcf r2 = (com.google.android.gms.internal.measurement.zzcf) r2
            goto L_0x048e
        L_0x0489:
            com.google.android.gms.internal.measurement.zzcd r2 = new com.google.android.gms.internal.measurement.zzcd
            r2.<init>(r6)
        L_0x048e:
            r6 = r2
        L_0x048f:
            long r8 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r0 = r10
            r2 = r4
            r3 = r5
            r4 = r6
            r5 = r8
            r0.logEventAndBundle(r1, r2, r3, r4, r5)
            goto L_0x04dd
        L_0x049f:
            java.lang.String r1 = r12.readString()
            java.lang.String r2 = r12.readString()
            android.os.Parcelable$Creator r3 = android.os.Bundle.CREATOR
            android.os.Parcelable r3 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r3)
            android.os.Bundle r3 = (android.os.Bundle) r3
            boolean r4 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            boolean r5 = com.google.android.gms.internal.measurement.zzbo.zzf(r12)
            long r6 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r0 = r10
            r0.logEvent(r1, r2, r3, r4, r5, r6)
            goto L_0x04dd
        L_0x04c3:
            android.os.IBinder r1 = r12.readStrongBinder()
            com.google.android.gms.dynamic.IObjectWrapper r1 = com.google.android.gms.dynamic.IObjectWrapper.Stub.asInterface(r1)
            android.os.Parcelable$Creator<com.google.android.gms.internal.measurement.zzcl> r2 = com.google.android.gms.internal.measurement.zzcl.CREATOR
            android.os.Parcelable r2 = com.google.android.gms.internal.measurement.zzbo.zza(r12, r2)
            com.google.android.gms.internal.measurement.zzcl r2 = (com.google.android.gms.internal.measurement.zzcl) r2
            long r3 = r12.readLong()
            com.google.android.gms.internal.measurement.zzbo.zzc(r12)
            r10.initialize(r1, r2, r3)
        L_0x04dd:
            r13.writeNoException()
            r0 = 1
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzcb.zza(int, android.os.Parcel, android.os.Parcel, int):boolean");
    }
}
