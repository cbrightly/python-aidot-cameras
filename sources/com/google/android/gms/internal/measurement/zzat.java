package com.google.android.gms.internal.measurement;

import java.util.Iterator;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzat implements Iterable, zzap {
    /* access modifiers changed from: private */
    public final String zza;

    public zzat(String str) {
        if (str != null) {
            this.zza = str;
            return;
        }
        throw new IllegalArgumentException("StringValue cannot be null.");
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzat)) {
            return false;
        }
        return this.zza.equals(((zzat) obj).zza);
    }

    public final int hashCode() {
        return this.zza.hashCode();
    }

    public final Iterator iterator() {
        return new zzas(this);
    }

    public final String toString() {
        String str = this.zza;
        return "\"" + str + "\"";
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01cf, code lost:
        r3 = r17;
        r6 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x01d4, code lost:
        r3 = r17;
        r6 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01fa, code lost:
        r1 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01fb, code lost:
        r17 = "undefined";
        r20 = r3;
        r19 = r4;
        r3 = 0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0204, code lost:
        switch(r1) {
            case 0: goto L_0x06c9;
            case 1: goto L_0x068e;
            case 2: goto L_0x0644;
            case 3: goto L_0x05eb;
            case 4: goto L_0x0587;
            case 5: goto L_0x0536;
            case 6: goto L_0x04a2;
            case 7: goto L_0x0454;
            case 8: goto L_0x03c1;
            case 9: goto L_0x030a;
            case 10: goto L_0x028b;
            case 11: goto L_0x0276;
            case 12: goto L_0x0260;
            case 13: goto L_0x0249;
            case 14: goto L_0x023e;
            case 15: goto L_0x0227;
            case 16: goto L_0x0211;
            default: goto L_0x0207;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:114:0x0207, code lost:
        r6 = r21;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0210, code lost:
        throw new java.lang.IllegalArgumentException("Command not supported");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0211, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh(r5, 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0227, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh(r5, 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x023e, code lost:
        r2 = r6;
        r6 = r21;
        com.google.android.gms.internal.measurement.zzh.zzh(r2, 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0249, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh("toLowerCase", 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0260, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh("toLocaleLowerCase", 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0276, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh(r14, 0, r24);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x028b, code lost:
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("substring", 2, r0);
        r1 = r21.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0299, code lost:
        if (r24.isEmpty() != false) goto L_0x02b6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x029b, code lost:
        r2 = r23;
        r3 = (int) com.google.android.gms.internal.measurement.zzh.zza(r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02b6, code lost:
        r2 = r23;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02be, code lost:
        if (r24.size() <= 1) goto L_0x02d8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02c0, code lost:
        r0 = (int) com.google.android.gms.internal.measurement.zzh.zza(r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02d8, code lost:
        r0 = r1.length();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x02dc, code lost:
        r3 = java.lang.Math.min(java.lang.Math.max(r3, 0), r1.length());
        r0 = java.lang.Math.min(java.lang.Math.max(r0, 0), r1.length());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:131:0x030a, code lost:
        r6 = r21;
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("split", 2, r0);
        r1 = r6.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x031a, code lost:
        if (r1.length() != 0) goto L_0x032d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x032d, code lost:
        r3 = new java.util.ArrayList();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x0336, code lost:
        if (r24.isEmpty() == false) goto L_0x033d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x0338, code lost:
        r3.add(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x033d, code lost:
        r4 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x0352, code lost:
        if (r24.size() <= 1) goto L_0x036b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0354, code lost:
        r7 = com.google.android.gms.internal.measurement.zzh.zzd(r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x036b, code lost:
        r7 = 2147483647L;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0372, code lost:
        if (r7 != 0) goto L_0x037b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x037b, code lost:
        r0 = r1.split(java.util.regex.Pattern.quote(r4), ((int) r7) + 1);
        r1 = r0.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:145:0x038b, code lost:
        if (r4.isEmpty() == false) goto L_0x03a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x038d, code lost:
        if (r1 <= 0) goto L_0x03a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x038f, code lost:
        r14 = r0[0].isEmpty();
        r2 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x039e, code lost:
        if (r0[r2].isEmpty() != false) goto L_0x03a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x03a0, code lost:
        r2 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x03a2, code lost:
        r2 = r1;
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x03a7, code lost:
        if (((long) r1) <= r7) goto L_0x03ab;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x03a9, code lost:
        r2 = r2 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x03ab, code lost:
        if (r14 >= r2) goto L_0x03ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x03ad, code lost:
        r3.add(new com.google.android.gms.internal.measurement.zzat(r0[r14]));
        r14 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x03c1, code lost:
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("slice", 2, r0);
        r1 = r21.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x03d1, code lost:
        if (r24.isEmpty() != false) goto L_0x03e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03d3, code lost:
        r7 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzh().doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x03e7, code lost:
        r7 = 0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x03e8, code lost:
        r7 = com.google.android.gms.internal.measurement.zzh.zza(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x03ee, code lost:
        if (r7 >= 0.0d) goto L_0x03fb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x03f0, code lost:
        r7 = java.lang.Math.max(((double) r1.length()) + r7, 0.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x03fb, code lost:
        r7 = java.lang.Math.min(r7, (double) r1.length());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x0409, code lost:
        if (r24.size() <= 1) goto L_0x041e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x040b, code lost:
        r9 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1)).zzh().doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x041e, code lost:
        r9 = (double) r1.length();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x0423, code lost:
        r9 = com.google.android.gms.internal.measurement.zzh.zza(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x0429, code lost:
        if (r9 >= 0.0d) goto L_0x0436;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x042b, code lost:
        r2 = java.lang.Math.max(((double) r1.length()) + r9, 0.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0436, code lost:
        r2 = java.lang.Math.min(r9, (double) r1.length());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x043f, code lost:
        r0 = (int) r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x0454, code lost:
        r6 = r21;
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj(com.google.firebase.analytics.FirebaseAnalytics.Event.SEARCH, 1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x0462, code lost:
        if (r24.isEmpty() != false) goto L_0x0475;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0464, code lost:
        r17 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0475, code lost:
        r0 = java.util.regex.Pattern.compile(r17).matcher(r6.zza);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0483, code lost:
        if (r0.find() == false) goto L_0x0495;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x04a2, code lost:
        r6 = r21;
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("replace", 2, r0);
        r1 = com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x04b2, code lost:
        if (r24.isEmpty() != false) goto L_0x04d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x04b4, code lost:
        r17 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x04c8, code lost:
        if (r24.size() <= 1) goto L_0x04d7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x04ca, code lost:
        r1 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1));
        r0 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x04d7, code lost:
        r0 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x04d9, code lost:
        r3 = r6.zza;
        r4 = r3.indexOf(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x04df, code lost:
        if (r4 < 0) goto L_0x06c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x04e3, code lost:
        if ((r1 instanceof com.google.android.gms.internal.measurement.zzai) == false) goto L_0x050a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x04e5, code lost:
        r1 = ((com.google.android.gms.internal.measurement.zzai) r1).zza(r2, java.util.Arrays.asList(new com.google.android.gms.internal.measurement.zzap[]{new com.google.android.gms.internal.measurement.zzat(r0), new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r4)), r6}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x0536, code lost:
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("match", 1, r0);
        r1 = r21.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x0546, code lost:
        if (r24.size() > 0) goto L_0x054b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0548, code lost:
        r0 = "";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x054b, code lost:
        r0 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x055b, code lost:
        r0 = java.util.regex.Pattern.compile(r0).matcher(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x0567, code lost:
        if (r0.find() == false) goto L_0x0583;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x0587, code lost:
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("lastIndexOf", 2, r0);
        r1 = r21.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x0597, code lost:
        if (r24.size() > 0) goto L_0x059c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:203:0x0599, code lost:
        r3 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x059c, code lost:
        r3 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x05b3, code lost:
        if (r24.size() >= 2) goto L_0x05b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:207:0x05b5, code lost:
        r4 = Double.NaN;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x05b8, code lost:
        r4 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1)).zzh().doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:210:0x05d0, code lost:
        if (java.lang.Double.isNaN(r4) == false) goto L_0x05d5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:211:0x05d2, code lost:
        r4 = Double.POSITIVE_INFINITY;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x05d5, code lost:
        r4 = com.google.android.gms.internal.measurement.zzh.zza(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x05eb, code lost:
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj("indexOf", 2, r0);
        r1 = r21.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x05fb, code lost:
        if (r24.size() > 0) goto L_0x0600;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x05fd, code lost:
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x0600, code lost:
        r5 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x0617, code lost:
        if (r24.size() >= 2) goto L_0x061a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x061a, code lost:
        r3 = r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(1)).zzh().doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:222:0x0644, code lost:
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzh(r20, 1, r0);
        r1 = r21.zza;
        r0 = r23.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x0667, code lost:
        if ("length".equals(r0.zzi()) == false) goto L_0x066d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x066d, code lost:
        r2 = r0.zzh().doubleValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:226:0x067b, code lost:
        if (r2 != java.lang.Math.floor(r2)) goto L_0x068a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x067d, code lost:
        r0 = (int) r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:228:0x067e, code lost:
        if (r0 < 0) goto L_0x068a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0684, code lost:
        if (r0 >= r1.length()) goto L_0x068a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:233:0x068e, code lost:
        r6 = r21;
        r2 = r23;
        r0 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0698, code lost:
        if (r24.isEmpty() != false) goto L_0x06c7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:235:0x069a, code lost:
        r1 = new java.lang.StringBuilder(r6.zza);
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:237:0x06a6, code lost:
        if (r14 >= r24.size()) goto L_0x06bc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x06a8, code lost:
        r1.append(r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(r14)).zzi());
        r14 = r14 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:241:0x06c9, code lost:
        r6 = r21;
        r2 = r23;
        r0 = r24;
        com.google.android.gms.internal.measurement.zzh.zzj(r19, 1, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x06d9, code lost:
        if (r24.isEmpty() != false) goto L_0x06f4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x06db, code lost:
        r14 = (int) com.google.android.gms.internal.measurement.zzh.zza(r2.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x06f4, code lost:
        r14 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:245:0x06f6, code lost:
        r0 = r6.zza;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x06f8, code lost:
        if (r14 < 0) goto L_0x070f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x06fe, code lost:
        if (r14 < r0.length()) goto L_0x0701;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:254:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzm;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r21.zza.trim());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r21.zza.toUpperCase(java.util.Locale.ENGLISH));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r21.zza.toLowerCase(java.util.Locale.ENGLISH));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:258:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r21.zza.toLowerCase());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:259:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r21.zza.toUpperCase());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r1.substring(java.lang.Math.min(r3, r0), java.lang.Math.max(r3, r0)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae(java.util.Arrays.asList(new com.google.android.gms.internal.measurement.zzap[]{r6}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae(r3);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:264:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r1.substring(r0, java.lang.Math.max(0, ((int) r2) - r0) + r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r0.start()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(-1.0d));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r3.substring(0, r4) + r1.zzi() + r3.substring(r4 + r0.length()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:268:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae(java.util.Arrays.asList(new com.google.android.gms.internal.measurement.zzap[]{new com.google.android.gms.internal.measurement.zzat(r0.group())}));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzg;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:270:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r1.lastIndexOf(r3, (int) r4)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:271:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r1.indexOf(r5, (int) com.google.android.gms.internal.measurement.zzh.zza(r3))));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:274:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:276:?, code lost:
        return r6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(java.lang.String.valueOf(r0.charAt(r14)));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01a3, code lost:
        r4 = r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01b4, code lost:
        r4 = r16;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.gms.internal.measurement.zzap zzbU(java.lang.String r22, com.google.android.gms.internal.measurement.zzg r23, java.util.List r24) {
        /*
            r21 = this;
            r0 = r21
            r1 = r22
            r2 = r23
            r3 = r24
            java.lang.String r4 = "charAt"
            boolean r5 = r4.equals(r1)
            java.lang.String r6 = "concat"
            java.lang.String r7 = "indexOf"
            java.lang.String r8 = "replace"
            java.lang.String r9 = "substring"
            java.lang.String r10 = "split"
            java.lang.String r11 = "slice"
            java.lang.String r12 = "match"
            java.lang.String r13 = "lastIndexOf"
            java.lang.String r14 = "toLocaleUpperCase"
            java.lang.String r15 = "search"
            java.lang.String r2 = "toLowerCase"
            java.lang.String r0 = "toLocaleLowerCase"
            java.lang.String r3 = "toString"
            r16 = r4
            java.lang.String r4 = "hasOwnProperty"
            r17 = r14
            java.lang.String r14 = "toUpperCase"
            r18 = r14
            if (r5 != 0) goto L_0x0121
            boolean r5 = r6.equals(r1)
            if (r5 != 0) goto L_0x011a
            boolean r5 = r4.equals(r1)
            if (r5 != 0) goto L_0x0113
            boolean r5 = r7.equals(r1)
            if (r5 != 0) goto L_0x010c
            boolean r5 = r13.equals(r1)
            if (r5 != 0) goto L_0x0105
            boolean r5 = r12.equals(r1)
            if (r5 != 0) goto L_0x00fe
            boolean r5 = r8.equals(r1)
            if (r5 != 0) goto L_0x00f7
            boolean r5 = r15.equals(r1)
            if (r5 != 0) goto L_0x00f0
            boolean r5 = r11.equals(r1)
            if (r5 != 0) goto L_0x00e9
            boolean r5 = r10.equals(r1)
            if (r5 != 0) goto L_0x00e2
            boolean r5 = r9.equals(r1)
            if (r5 != 0) goto L_0x00db
            boolean r5 = r2.equals(r1)
            if (r5 != 0) goto L_0x00d3
            boolean r5 = r0.equals(r1)
            if (r5 != 0) goto L_0x00cb
            boolean r5 = r3.equals(r1)
            if (r5 != 0) goto L_0x00c3
            r5 = r18
            boolean r18 = r5.equals(r1)
            if (r18 != 0) goto L_0x00bd
            r14 = r17
            boolean r17 = r14.equals(r1)
            if (r17 != 0) goto L_0x00b9
            r17 = r4
            java.lang.String r4 = "trim"
            boolean r4 = r4.equals(r1)
            if (r4 == 0) goto L_0x00a7
            goto L_0x0127
        L_0x00a7:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r2 = 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            r3 = 0
            r2[r3] = r1
            java.lang.String r1 = "%s is not a String function"
            java.lang.String r1 = java.lang.String.format(r1, r2)
            r0.<init>(r1)
            throw r0
        L_0x00b9:
            r17 = r4
            goto L_0x0127
        L_0x00bd:
            r14 = r17
            r17 = r4
            goto L_0x0127
        L_0x00c3:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00cb:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00d3:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00db:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00e2:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00e9:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00f0:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00f7:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x00fe:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x0105:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x010c:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x0113:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x011a:
            r14 = r17
            r5 = r18
            r17 = r4
            goto L_0x0127
        L_0x0121:
            r14 = r17
            r5 = r18
            r17 = r4
        L_0x0127:
            int r4 = r22.hashCode()
            r19 = r3
            switch(r4) {
                case -1789698943: goto L_0x01eb;
                case -1776922004: goto L_0x01d9;
                case -1464939364: goto L_0x01c5;
                case -1361633751: goto L_0x01b7;
                case -1354795244: goto L_0x01a6;
                case -1137582698: goto L_0x019b;
                case -906336856: goto L_0x0193;
                case -726908483: goto L_0x018a;
                case -467511597: goto L_0x0182;
                case -399551817: goto L_0x0179;
                case 3568674: goto L_0x016d;
                case 103668165: goto L_0x0165;
                case 109526418: goto L_0x015c;
                case 109648666: goto L_0x0153;
                case 530542161: goto L_0x014a;
                case 1094496948: goto L_0x0142;
                case 1943291465: goto L_0x0138;
                default: goto L_0x0130;
            }
        L_0x0130:
            r4 = r16
            r3 = r17
            r6 = r19
            goto L_0x01fa
        L_0x0138:
            boolean r1 = r1.equals(r7)
            if (r1 == 0) goto L_0x01b4
            r1 = 3
            goto L_0x01a3
        L_0x0142:
            boolean r1 = r1.equals(r8)
            if (r1 == 0) goto L_0x01b4
            r1 = 6
            goto L_0x01a3
        L_0x014a:
            boolean r1 = r1.equals(r9)
            if (r1 == 0) goto L_0x01b4
            r1 = 10
            goto L_0x01a3
        L_0x0153:
            boolean r1 = r1.equals(r10)
            if (r1 == 0) goto L_0x01b4
            r1 = 9
            goto L_0x01a3
        L_0x015c:
            boolean r1 = r1.equals(r11)
            if (r1 == 0) goto L_0x01b4
            r1 = 8
            goto L_0x01a3
        L_0x0165:
            boolean r1 = r1.equals(r12)
            if (r1 == 0) goto L_0x01b4
            r1 = 5
            goto L_0x01a3
        L_0x016d:
            java.lang.String r4 = "trim"
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x01b4
            r1 = 16
            goto L_0x01a3
        L_0x0179:
            boolean r1 = r1.equals(r5)
            if (r1 == 0) goto L_0x01b4
            r1 = 15
            goto L_0x01a3
        L_0x0182:
            boolean r1 = r1.equals(r13)
            if (r1 == 0) goto L_0x01b4
            r1 = 4
            goto L_0x01a3
        L_0x018a:
            boolean r1 = r1.equals(r14)
            if (r1 == 0) goto L_0x01b4
            r1 = 11
            goto L_0x01a3
        L_0x0193:
            boolean r1 = r1.equals(r15)
            if (r1 == 0) goto L_0x01b4
            r1 = 7
            goto L_0x01a3
        L_0x019b:
            boolean r1 = r1.equals(r2)
            if (r1 == 0) goto L_0x01b4
            r1 = 13
        L_0x01a3:
            r4 = r16
            goto L_0x01cf
        L_0x01a6:
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x01b4
            r4 = r16
            r3 = r17
            r6 = r19
            r1 = 1
            goto L_0x01fb
        L_0x01b4:
            r4 = r16
            goto L_0x01d4
        L_0x01b7:
            r4 = r16
            boolean r1 = r1.equals(r4)
            if (r1 == 0) goto L_0x01d4
            r3 = r17
            r6 = r19
            r1 = 0
            goto L_0x01fb
        L_0x01c5:
            r4 = r16
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x01d4
            r1 = 12
        L_0x01cf:
            r3 = r17
            r6 = r19
            goto L_0x01fb
        L_0x01d4:
            r3 = r17
            r6 = r19
            goto L_0x01fa
        L_0x01d9:
            r4 = r16
            r6 = r19
            boolean r1 = r1.equals(r6)
            if (r1 == 0) goto L_0x01e8
            r1 = 14
            r3 = r17
            goto L_0x01fb
        L_0x01e8:
            r3 = r17
            goto L_0x01fa
        L_0x01eb:
            r4 = r16
            r6 = r19
            r3 = r17
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L_0x01fa
            r1 = 2
            goto L_0x01fb
        L_0x01fa:
            r1 = -1
        L_0x01fb:
            java.lang.String r17 = "undefined"
            r20 = r3
            r19 = r4
            r3 = 0
            switch(r1) {
                case 0: goto L_0x06c9;
                case 1: goto L_0x068e;
                case 2: goto L_0x0644;
                case 3: goto L_0x05eb;
                case 4: goto L_0x0587;
                case 5: goto L_0x0536;
                case 6: goto L_0x04a2;
                case 7: goto L_0x0454;
                case 8: goto L_0x03c1;
                case 9: goto L_0x030a;
                case 10: goto L_0x028b;
                case 11: goto L_0x0276;
                case 12: goto L_0x0260;
                case 13: goto L_0x0249;
                case 14: goto L_0x023e;
                case 15: goto L_0x0227;
                case 16: goto L_0x0211;
                default: goto L_0x0207;
            }
        L_0x0207:
            r6 = r21
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Command not supported"
            r0.<init>(r1)
            throw r0
        L_0x0211:
            r0 = r24
            r1 = 0
            com.google.android.gms.internal.measurement.zzh.zzh(r5, r1, r0)
            r6 = r21
            java.lang.String r0 = r6.zza
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r0.trim()
            r1.<init>(r0)
            goto L_0x0711
        L_0x0227:
            r1 = 0
            r6 = r21
            r0 = r24
            com.google.android.gms.internal.measurement.zzh.zzh(r5, r1, r0)
            java.lang.String r0 = r6.zza
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r0 = r0.toUpperCase(r2)
            r1.<init>(r0)
            goto L_0x0711
        L_0x023e:
            r1 = 0
            r0 = r24
            r2 = r6
            r6 = r21
            com.google.android.gms.internal.measurement.zzh.zzh(r2, r1, r0)
            goto L_0x06c7
        L_0x0249:
            r1 = 0
            r6 = r21
            r0 = r24
            com.google.android.gms.internal.measurement.zzh.zzh(r2, r1, r0)
            java.lang.String r0 = r6.zza
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.util.Locale r2 = java.util.Locale.ENGLISH
            java.lang.String r0 = r0.toLowerCase(r2)
            r1.<init>(r0)
            goto L_0x0711
        L_0x0260:
            r1 = 0
            r6 = r21
            r2 = r0
            r0 = r24
            com.google.android.gms.internal.measurement.zzh.zzh(r2, r1, r0)
            java.lang.String r0 = r6.zza
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r0.toLowerCase()
            r1.<init>(r0)
            goto L_0x0711
        L_0x0276:
            r1 = 0
            r6 = r21
            r0 = r24
            com.google.android.gms.internal.measurement.zzh.zzh(r14, r1, r0)
            java.lang.String r0 = r6.zza
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r0.toUpperCase()
            r1.<init>(r0)
            goto L_0x0711
        L_0x028b:
            r6 = r21
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r9, r1, r0)
            java.lang.String r1 = r6.zza
            boolean r2 = r24.isEmpty()
            if (r2 != 0) goto L_0x02b6
            r2 = 0
            java.lang.Object r3 = r0.get(r2)
            com.google.android.gms.internal.measurement.zzap r3 = (com.google.android.gms.internal.measurement.zzap) r3
            r2 = r23
            com.google.android.gms.internal.measurement.zzap r3 = r2.zzb(r3)
            java.lang.Double r3 = r3.zzh()
            double r3 = r3.doubleValue()
            double r3 = com.google.android.gms.internal.measurement.zzh.zza(r3)
            int r3 = (int) r3
            goto L_0x02b9
        L_0x02b6:
            r2 = r23
            r3 = 0
        L_0x02b9:
            int r4 = r24.size()
            r5 = 1
            if (r4 <= r5) goto L_0x02d8
            java.lang.Object r0 = r0.get(r5)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r4 = r0.doubleValue()
            double r4 = com.google.android.gms.internal.measurement.zzh.zza(r4)
            int r0 = (int) r4
            goto L_0x02dc
        L_0x02d8:
            int r0 = r1.length()
        L_0x02dc:
            r2 = 0
            int r3 = java.lang.Math.max(r3, r2)
            int r4 = r1.length()
            int r3 = java.lang.Math.min(r3, r4)
            int r0 = java.lang.Math.max(r0, r2)
            int r2 = r1.length()
            int r0 = java.lang.Math.min(r0, r2)
            com.google.android.gms.internal.measurement.zzat r2 = new com.google.android.gms.internal.measurement.zzat
            int r4 = java.lang.Math.min(r3, r0)
            int r0 = java.lang.Math.max(r3, r0)
            java.lang.String r0 = r1.substring(r4, r0)
            r2.<init>(r0)
            r1 = r2
            goto L_0x0711
        L_0x030a:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r10, r1, r0)
            java.lang.String r1 = r6.zza
            int r3 = r1.length()
            if (r3 != 0) goto L_0x032d
            com.google.android.gms.internal.measurement.zzae r1 = new com.google.android.gms.internal.measurement.zzae
            r0 = 1
            com.google.android.gms.internal.measurement.zzap[] r0 = new com.google.android.gms.internal.measurement.zzap[r0]
            r2 = 0
            r0[r2] = r6
            java.util.List r0 = java.util.Arrays.asList(r0)
            r1.<init>(r0)
            goto L_0x0711
        L_0x032d:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            boolean r4 = r24.isEmpty()
            if (r4 == 0) goto L_0x033d
            r3.add(r6)
            goto L_0x03ba
        L_0x033d:
            r4 = 0
            java.lang.Object r5 = r0.get(r4)
            com.google.android.gms.internal.measurement.zzap r5 = (com.google.android.gms.internal.measurement.zzap) r5
            com.google.android.gms.internal.measurement.zzap r4 = r2.zzb(r5)
            java.lang.String r4 = r4.zzi()
            int r5 = r24.size()
            r7 = 1
            if (r5 <= r7) goto L_0x036b
            java.lang.Object r0 = r0.get(r7)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r7 = r0.doubleValue()
            long r7 = com.google.android.gms.internal.measurement.zzh.zzd(r7)
            goto L_0x036e
        L_0x036b:
            r7 = 2147483647(0x7fffffff, double:1.060997895E-314)
        L_0x036e:
            r9 = 0
            int r0 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r0 != 0) goto L_0x037b
            com.google.android.gms.internal.measurement.zzae r1 = new com.google.android.gms.internal.measurement.zzae
            r1.<init>()
            goto L_0x0711
        L_0x037b:
            java.lang.String r0 = java.util.regex.Pattern.quote(r4)
            int r2 = (int) r7
            r5 = 1
            int r2 = r2 + r5
            java.lang.String[] r0 = r1.split(r0, r2)
            int r1 = r0.length
            boolean r2 = r4.isEmpty()
            if (r2 == 0) goto L_0x03a2
            if (r1 <= 0) goto L_0x03a2
            r2 = 0
            r2 = r0[r2]
            boolean r14 = r2.isEmpty()
            int r2 = r1 + -1
            r4 = r0[r2]
            boolean r4 = r4.isEmpty()
            if (r4 != 0) goto L_0x03a4
            r2 = r1
            goto L_0x03a4
        L_0x03a2:
            r2 = r1
            r14 = 0
        L_0x03a4:
            long r4 = (long) r1
            int r1 = (r4 > r7 ? 1 : (r4 == r7 ? 0 : -1))
            if (r1 <= 0) goto L_0x03ab
            int r2 = r2 + -1
        L_0x03ab:
            if (r14 >= r2) goto L_0x03ba
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            r4 = r0[r14]
            r1.<init>(r4)
            r3.add(r1)
            int r14 = r14 + 1
            goto L_0x03ab
        L_0x03ba:
            com.google.android.gms.internal.measurement.zzae r1 = new com.google.android.gms.internal.measurement.zzae
            r1.<init>(r3)
            goto L_0x0711
        L_0x03c1:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r11, r1, r0)
            java.lang.String r1 = r6.zza
            boolean r5 = r24.isEmpty()
            if (r5 != 0) goto L_0x03e7
            r5 = 0
            java.lang.Object r7 = r0.get(r5)
            com.google.android.gms.internal.measurement.zzap r7 = (com.google.android.gms.internal.measurement.zzap) r7
            com.google.android.gms.internal.measurement.zzap r5 = r2.zzb(r7)
            java.lang.Double r5 = r5.zzh()
            double r7 = r5.doubleValue()
            goto L_0x03e8
        L_0x03e7:
            r7 = r3
        L_0x03e8:
            double r7 = com.google.android.gms.internal.measurement.zzh.zza(r7)
            int r5 = (r7 > r3 ? 1 : (r7 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x03fb
            int r5 = r1.length()
            double r9 = (double) r5
            double r9 = r9 + r7
            double r7 = java.lang.Math.max(r9, r3)
            goto L_0x0404
        L_0x03fb:
            int r5 = r1.length()
            double r9 = (double) r5
            double r7 = java.lang.Math.min(r7, r9)
        L_0x0404:
            int r5 = r24.size()
            r9 = 1
            if (r5 <= r9) goto L_0x041e
            java.lang.Object r0 = r0.get(r9)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r9 = r0.doubleValue()
            goto L_0x0423
        L_0x041e:
            int r0 = r1.length()
            double r9 = (double) r0
        L_0x0423:
            double r9 = com.google.android.gms.internal.measurement.zzh.zza(r9)
            int r0 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x0436
            int r0 = r1.length()
            double r11 = (double) r0
            double r11 = r11 + r9
            double r2 = java.lang.Math.max(r11, r3)
            goto L_0x043f
        L_0x0436:
            int r0 = r1.length()
            double r2 = (double) r0
            double r2 = java.lang.Math.min(r9, r2)
        L_0x043f:
            int r0 = (int) r7
            int r2 = (int) r2
            int r2 = r2 - r0
            r3 = 0
            int r2 = java.lang.Math.max(r3, r2)
            int r2 = r2 + r0
            com.google.android.gms.internal.measurement.zzat r3 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r1.substring(r0, r2)
            r3.<init>(r0)
            r1 = r3
            goto L_0x0711
        L_0x0454:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 1
            com.google.android.gms.internal.measurement.zzh.zzj(r15, r1, r0)
            boolean r1 = r24.isEmpty()
            if (r1 != 0) goto L_0x0474
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.String r17 = r0.zzi()
            goto L_0x0475
        L_0x0474:
        L_0x0475:
            java.lang.String r0 = r6.zza
            java.util.regex.Pattern r1 = java.util.regex.Pattern.compile(r17)
            java.util.regex.Matcher r0 = r1.matcher(r0)
            boolean r1 = r0.find()
            if (r1 == 0) goto L_0x0495
            com.google.android.gms.internal.measurement.zzah r1 = new com.google.android.gms.internal.measurement.zzah
            int r0 = r0.start()
            double r2 = (double) r0
            java.lang.Double r0 = java.lang.Double.valueOf(r2)
            r1.<init>(r0)
            goto L_0x0711
        L_0x0495:
            com.google.android.gms.internal.measurement.zzah r1 = new com.google.android.gms.internal.measurement.zzah
            r2 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            java.lang.Double r0 = java.lang.Double.valueOf(r2)
            r1.<init>(r0)
            goto L_0x0711
        L_0x04a2:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r8, r1, r0)
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzf
            boolean r3 = r24.isEmpty()
            if (r3 != 0) goto L_0x04d7
            r3 = 0
            java.lang.Object r4 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r4 = (com.google.android.gms.internal.measurement.zzap) r4
            com.google.android.gms.internal.measurement.zzap r3 = r2.zzb(r4)
            java.lang.String r17 = r3.zzi()
            int r3 = r24.size()
            r4 = 1
            if (r3 <= r4) goto L_0x04d7
            java.lang.Object r0 = r0.get(r4)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r1 = r2.zzb(r0)
            r0 = r17
            goto L_0x04d9
        L_0x04d7:
            r0 = r17
        L_0x04d9:
            java.lang.String r3 = r6.zza
            int r4 = r3.indexOf(r0)
            if (r4 < 0) goto L_0x06c7
            boolean r5 = r1 instanceof com.google.android.gms.internal.measurement.zzai
            if (r5 == 0) goto L_0x050a
            com.google.android.gms.internal.measurement.zzai r1 = (com.google.android.gms.internal.measurement.zzai) r1
            r5 = 3
            com.google.android.gms.internal.measurement.zzap[] r5 = new com.google.android.gms.internal.measurement.zzap[r5]
            com.google.android.gms.internal.measurement.zzat r7 = new com.google.android.gms.internal.measurement.zzat
            r7.<init>(r0)
            r8 = 0
            r5[r8] = r7
            double r7 = (double) r4
            com.google.android.gms.internal.measurement.zzah r9 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r7 = java.lang.Double.valueOf(r7)
            r9.<init>(r7)
            r7 = 1
            r5[r7] = r9
            r7 = 2
            r5[r7] = r6
            java.util.List r5 = java.util.Arrays.asList(r5)
            com.google.android.gms.internal.measurement.zzap r1 = r1.zza(r2, r5)
        L_0x050a:
            com.google.android.gms.internal.measurement.zzat r2 = new com.google.android.gms.internal.measurement.zzat
            r5 = 0
            java.lang.String r5 = r3.substring(r5, r4)
            java.lang.String r1 = r1.zzi()
            int r0 = r0.length()
            int r4 = r4 + r0
            java.lang.String r0 = r3.substring(r4)
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r5)
            r3.append(r1)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            r2.<init>(r0)
            r1 = r2
            goto L_0x0711
        L_0x0536:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 1
            com.google.android.gms.internal.measurement.zzh.zzj(r12, r1, r0)
            java.lang.String r1 = r6.zza
            int r3 = r24.size()
            if (r3 > 0) goto L_0x054b
            java.lang.String r0 = ""
            goto L_0x055b
        L_0x054b:
            r3 = 0
            java.lang.Object r0 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.String r0 = r0.zzi()
        L_0x055b:
            java.util.regex.Pattern r0 = java.util.regex.Pattern.compile(r0)
            java.util.regex.Matcher r0 = r0.matcher(r1)
            boolean r1 = r0.find()
            if (r1 == 0) goto L_0x0583
            com.google.android.gms.internal.measurement.zzae r1 = new com.google.android.gms.internal.measurement.zzae
            r2 = 1
            com.google.android.gms.internal.measurement.zzap[] r2 = new com.google.android.gms.internal.measurement.zzap[r2]
            com.google.android.gms.internal.measurement.zzat r3 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r0.group()
            r3.<init>(r0)
            r0 = 0
            r2[r0] = r3
            java.util.List r0 = java.util.Arrays.asList(r2)
            r1.<init>(r0)
            goto L_0x0711
        L_0x0583:
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzg
            goto L_0x0711
        L_0x0587:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r13, r1, r0)
            java.lang.String r1 = r6.zza
            int r3 = r24.size()
            if (r3 > 0) goto L_0x059c
            r3 = r17
            goto L_0x05ae
        L_0x059c:
            r3 = 0
            java.lang.Object r3 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r3 = (com.google.android.gms.internal.measurement.zzap) r3
            com.google.android.gms.internal.measurement.zzap r3 = r2.zzb(r3)
            java.lang.String r17 = r3.zzi()
            r3 = r17
        L_0x05ae:
            int r4 = r24.size()
            r5 = 2
            if (r4 >= r5) goto L_0x05b8
            r4 = 9221120237041090560(0x7ff8000000000000, double:NaN)
            goto L_0x05cc
        L_0x05b8:
            r4 = 1
            java.lang.Object r0 = r0.get(r4)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r4 = r0.doubleValue()
        L_0x05cc:
            boolean r0 = java.lang.Double.isNaN(r4)
            if (r0 == 0) goto L_0x05d5
            r4 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            goto L_0x05d9
        L_0x05d5:
            double r4 = com.google.android.gms.internal.measurement.zzh.zza(r4)
        L_0x05d9:
            int r0 = (int) r4
            com.google.android.gms.internal.measurement.zzah r2 = new com.google.android.gms.internal.measurement.zzah
            int r0 = r1.lastIndexOf(r3, r0)
            double r0 = (double) r0
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            r2.<init>(r0)
            r1 = r2
            goto L_0x0711
        L_0x05eb:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r7, r1, r0)
            java.lang.String r1 = r6.zza
            int r5 = r24.size()
            if (r5 > 0) goto L_0x0600
            r5 = r17
            goto L_0x0612
        L_0x0600:
            r5 = 0
            java.lang.Object r5 = r0.get(r5)
            com.google.android.gms.internal.measurement.zzap r5 = (com.google.android.gms.internal.measurement.zzap) r5
            com.google.android.gms.internal.measurement.zzap r5 = r2.zzb(r5)
            java.lang.String r17 = r5.zzi()
            r5 = r17
        L_0x0612:
            int r7 = r24.size()
            r8 = 2
            if (r7 >= r8) goto L_0x061a
            goto L_0x062e
        L_0x061a:
            r3 = 1
            java.lang.Object r0 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r3 = r0.doubleValue()
        L_0x062e:
            double r2 = com.google.android.gms.internal.measurement.zzh.zza(r3)
            int r0 = (int) r2
            com.google.android.gms.internal.measurement.zzah r2 = new com.google.android.gms.internal.measurement.zzah
            int r0 = r1.indexOf(r5, r0)
            double r0 = (double) r0
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            r2.<init>(r0)
            r1 = r2
            goto L_0x0711
        L_0x0644:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = r20
            r3 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r1, r3, r0)
            java.lang.String r1 = r6.zza
            r3 = 0
            java.lang.Object r0 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.String r2 = r0.zzi()
            java.lang.String r3 = "length"
            boolean r2 = r3.equals(r2)
            if (r2 == 0) goto L_0x066d
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzk
            goto L_0x0711
        L_0x066d:
            java.lang.Double r0 = r0.zzh()
            double r2 = r0.doubleValue()
            double r4 = java.lang.Math.floor(r2)
            int r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r0 != 0) goto L_0x068a
            int r0 = (int) r2
            if (r0 < 0) goto L_0x068a
            int r1 = r1.length()
            if (r0 >= r1) goto L_0x068a
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzk
            goto L_0x0711
        L_0x068a:
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzl
            goto L_0x0711
        L_0x068e:
            r6 = r21
            r2 = r23
            r0 = r24
            boolean r1 = r24.isEmpty()
            if (r1 != 0) goto L_0x06c7
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = r6.zza
            r1.<init>(r3)
            r14 = 0
        L_0x06a2:
            int r3 = r24.size()
            if (r14 >= r3) goto L_0x06bc
            java.lang.Object r3 = r0.get(r14)
            com.google.android.gms.internal.measurement.zzap r3 = (com.google.android.gms.internal.measurement.zzap) r3
            com.google.android.gms.internal.measurement.zzap r3 = r2.zzb(r3)
            java.lang.String r3 = r3.zzi()
            r1.append(r3)
            int r14 = r14 + 1
            goto L_0x06a2
        L_0x06bc:
            com.google.android.gms.internal.measurement.zzat r0 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            r1 = r0
            goto L_0x0711
        L_0x06c7:
            r1 = r6
            goto L_0x0711
        L_0x06c9:
            r6 = r21
            r2 = r23
            r0 = r24
            r1 = r19
            r3 = 1
            com.google.android.gms.internal.measurement.zzh.zzj(r1, r3, r0)
            boolean r1 = r24.isEmpty()
            if (r1 != 0) goto L_0x06f4
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r2.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r0 = r0.doubleValue()
            double r0 = com.google.android.gms.internal.measurement.zzh.zza(r0)
            int r14 = (int) r0
            goto L_0x06f6
        L_0x06f4:
            r1 = 0
            r14 = r1
        L_0x06f6:
            java.lang.String r0 = r6.zza
            if (r14 < 0) goto L_0x070f
            int r1 = r0.length()
            if (r14 < r1) goto L_0x0701
            goto L_0x070f
        L_0x0701:
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            char r0 = r0.charAt(r14)
            java.lang.String r0 = java.lang.String.valueOf(r0)
            r1.<init>(r0)
            goto L_0x0711
        L_0x070f:
            com.google.android.gms.internal.measurement.zzap r1 = com.google.android.gms.internal.measurement.zzap.zzm
        L_0x0711:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzat.zzbU(java.lang.String, com.google.android.gms.internal.measurement.zzg, java.util.List):com.google.android.gms.internal.measurement.zzap");
    }

    public final zzap zzd() {
        return new zzat(this.zza);
    }

    public final Boolean zzg() {
        return Boolean.valueOf(!this.zza.isEmpty());
    }

    public final Double zzh() {
        if (this.zza.isEmpty()) {
            return Double.valueOf(0.0d);
        }
        try {
            return Double.valueOf(this.zza);
        } catch (NumberFormatException e) {
            return Double.valueOf(Double.NaN);
        }
    }

    public final String zzi() {
        return this.zza;
    }

    public final Iterator zzl() {
        return new zzar(this);
    }
}
