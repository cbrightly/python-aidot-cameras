package com.google.android.gms.internal.measurement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement@@21.2.2 */
public final class zzbb {
    /* JADX WARNING: type inference failed for: r1v86, types: [com.google.android.gms.internal.measurement.zzap] */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x021c, code lost:
        if (r0 <= r24.zzc()) goto L_0x0222;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x021e, code lost:
        r0 = r24.zzc();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0222, code lost:
        r2 = r24.zzc();
        r3 = new com.google.android.gms.internal.measurement.zzae();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0230, code lost:
        if (r26.size() <= 1) goto L_0x0299;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:105:0x0232, code lost:
        r4 = java.lang.Math.max(0, (int) com.google.android.gms.internal.measurement.zzh.zza(r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(1)).zzh().doubleValue()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x024e, code lost:
        if (r4 <= 0) goto L_0x026a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x0250, code lost:
        r5 = r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x0257, code lost:
        if (r5 >= java.lang.Math.min(r2, r0 + r4)) goto L_0x026a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x0259, code lost:
        r3.zzq(r3.zzc(), r9.zze(r0));
        r9.zzp(r0);
        r5 = r5 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x026f, code lost:
        if (r26.size() <= 2) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:0x0271, code lost:
        r2 = 2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:115:0x0276, code lost:
        if (r2 >= r26.size()) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:116:0x0278, code lost:
        r4 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(r2));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x0284, code lost:
        if ((r4 instanceof com.google.android.gms.internal.measurement.zzag) != false) goto L_0x0290;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:118:0x0286, code lost:
        r9.zzo((r0 + r2) - 2, r4);
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:120:0x0297, code lost:
        throw new java.lang.IllegalArgumentException("Failed to parse elements to add");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0299, code lost:
        if (r0 >= r2) goto L_0x02ae;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x029b, code lost:
        r3.zzq(r3.zzc(), r9.zze(r0));
        r9.zzq(r0, (com.google.android.gms.internal.measurement.zzap) null);
        r0 = r0 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x02b0, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzj("sort", 1, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:125:0x02bf, code lost:
        if (r24.zzc() >= 2) goto L_0x02c2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x02c2, code lost:
        r0 = r24.zzm();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x02ca, code lost:
        if (r26.isEmpty() != false) goto L_0x02e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x02cc, code lost:
        r1 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x02d9, code lost:
        if ((r1 instanceof com.google.android.gms.internal.measurement.zzai) == false) goto L_0x02df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x02db, code lost:
        r3 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x02e6, code lost:
        throw new java.lang.IllegalArgumentException("Comparator should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x02e7, code lost:
        r3 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:134:0x02e8, code lost:
        java.util.Collections.sort(r0, new com.google.android.gms.internal.measurement.zzba(r3, r6));
        r24.zzn();
        r0 = r0.iterator();
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:136:0x02fc, code lost:
        if (r0.hasNext() == false) goto L_0x030b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x02fe, code lost:
        r9.zzq(r2, (com.google.android.gms.internal.measurement.zzap) r0.next());
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:138:0x030b, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x030c, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzh("some", 1, r1);
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x0323, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzai) == false) goto L_0x037c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0329, code lost:
        if (r24.zzc() != 0) goto L_0x032e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x032e, code lost:
        r0 = (com.google.android.gms.internal.measurement.zzai) r0;
        r1 = r24.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x0338, code lost:
        if (r1.hasNext() == false) goto L_0x0379;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:147:0x033a, code lost:
        r2 = ((java.lang.Integer) r1.next()).intValue();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x0348, code lost:
        if (r9.zzs(r2) == false) goto L_0x0334;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x0374, code lost:
        if (r0.zza(r6, java.util.Arrays.asList(new com.google.android.gms.internal.measurement.zzap[]{r9.zze(r2), new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r2)), r9})).zzg().booleanValue() == false) goto L_0x0334;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x0381, code lost:
        throw new java.lang.IllegalArgumentException("Callback should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x0382, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzj("slice", 2, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:156:0x0390, code lost:
        if (r26.isEmpty() == false) goto L_0x0398;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:158:0x0398, code lost:
        r7 = (double) r24.zzc();
        r10 = com.google.android.gms.internal.measurement.zzh.zza(r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x03b6, code lost:
        if (r10 >= 0.0d) goto L_0x03be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x03b8, code lost:
        r10 = java.lang.Math.max(r10 + r7, 0.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:161:0x03be, code lost:
        r10 = java.lang.Math.min(r10, r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x03c7, code lost:
        if (r26.size() != 2) goto L_0x03f0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:164:0x03c9, code lost:
        r0 = com.google.android.gms.internal.measurement.zzh.zza(r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(1)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x03e2, code lost:
        if (r0 >= 0.0d) goto L_0x03ea;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x03e4, code lost:
        r7 = java.lang.Math.max(r7 + r0, 0.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:167:0x03ea, code lost:
        r7 = java.lang.Math.min(r7, r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x03f0, code lost:
        r0 = new com.google.android.gms.internal.measurement.zzae();
        r1 = (int) r10;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x03f9, code lost:
        if (((double) r1) >= r7) goto L_0x040a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x03fb, code lost:
        r0.zzq(r0.zzc(), r9.zze(r1));
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x040b, code lost:
        r9 = r24;
        com.google.android.gms.internal.measurement.zzh.zzh("shift", 0, r26);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0417, code lost:
        if (r24.zzc() != 0) goto L_0x041c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:175:0x041c, code lost:
        r1 = r9.zze(0);
        r9.zzp(0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x0425, code lost:
        r9 = r24;
        com.google.android.gms.internal.measurement.zzh.zzh("reverse", 0, r26);
        r0 = r24.zzc();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0431, code lost:
        if (r0 == 0) goto L_0x045c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0433, code lost:
        r2 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:180:0x0436, code lost:
        if (r2 >= (r0 / 2)) goto L_0x045c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x043c, code lost:
        if (r9.zzs(r2) == false) goto L_0x0459;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:183:0x043e, code lost:
        r1 = r9.zze(r2);
        r9.zzq(r2, (com.google.android.gms.internal.measurement.zzap) null);
        r3 = (r0 - 1) - r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:184:0x044d, code lost:
        if (r9.zzs(r3) == false) goto L_0x0456;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:185:0x044f, code lost:
        r9.zzq(r2, r9.zze(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:186:0x0456, code lost:
        r9.zzq(r3, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:187:0x0459, code lost:
        r2 = r2 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:188:0x045c, code lost:
        return r9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:190:0x0468, code lost:
        return zzc(r24, r25, r26, false);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:192:0x0474, code lost:
        return zzc(r24, r25, r26, true);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:193:0x0475, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:194:0x047f, code lost:
        if (r26.isEmpty() != false) goto L_0x049d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x0481, code lost:
        r0 = r26.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:197:0x0489, code lost:
        if (r0.hasNext() == false) goto L_0x049d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:198:0x048b, code lost:
        r9.zzq(r24.zzc(), r6.zzb((com.google.android.gms.internal.measurement.zzap) r0.next()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:200:0x04ab, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r24.zzc()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:201:0x04ac, code lost:
        r9 = r24;
        com.google.android.gms.internal.measurement.zzh.zzh(com.leedarson.bean.H5ActionName.ACTION_POP, 0, r26);
        r0 = r24.zzc();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:202:0x04b8, code lost:
        if (r0 != 0) goto L_0x04bd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:204:0x04bd, code lost:
        r0 = r0 - 1;
        r1 = r9.zze(r0);
        r9.zzp(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:205:0x04c8, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzh("map", 1, r1);
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:206:0x04df, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzao) == false) goto L_0x04f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:208:0x04e5, code lost:
        if (r24.zzc() != 0) goto L_0x04ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:212:0x04fa, code lost:
        throw new java.lang.IllegalArgumentException("Callback should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:213:0x04fb, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzj("lastIndexOf", 2, r1);
        r0 = com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:214:0x050b, code lost:
        if (r26.isEmpty() != false) goto L_0x0518;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:215:0x050d, code lost:
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:216:0x0518, code lost:
        r2 = r24.zzc() - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:217:0x0523, code lost:
        if (r26.size() <= 1) goto L_0x055d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:218:0x0525, code lost:
        r1 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:219:0x053b, code lost:
        if (java.lang.Double.isNaN(r1.zzh().doubleValue()) == false) goto L_0x0545;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:220:0x053d, code lost:
        r1 = (double) (r24.zzc() - 1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:221:0x0545, code lost:
        r1 = com.google.android.gms.internal.measurement.zzh.zza(r1.zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:223:0x0553, code lost:
        if (r1 >= 0.0d) goto L_0x055e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:224:0x0555, code lost:
        r1 = r1 + ((double) r24.zzc());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:225:0x055d, code lost:
        r1 = (double) r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:227:0x0560, code lost:
        if (r1 >= 0.0d) goto L_0x056c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:229:0x056c, code lost:
        r1 = (int) java.lang.Math.min((double) r24.zzc(), r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:230:0x0576, code lost:
        if (r1 < 0) goto L_0x0597;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:232:0x057c, code lost:
        if (r9.zzs(r1) == false) goto L_0x0594;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:234:0x0586, code lost:
        if (com.google.android.gms.internal.measurement.zzh.zzl(r9.zze(r1), r0) == false) goto L_0x0594;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:236:0x0594, code lost:
        r1 = r1 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:238:0x05a1, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzj("join", 1, r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:239:0x05af, code lost:
        if (r24.zzc() != 0) goto L_0x05b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:242:0x05b8, code lost:
        if (r26.isEmpty() != false) goto L_0x05d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:243:0x05ba, code lost:
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:244:0x05c7, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzan) != false) goto L_0x05d3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:246:0x05cb, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzau) == false) goto L_0x05ce;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:247:0x05ce, code lost:
        r0 = r0.zzi();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:248:0x05d3, code lost:
        r0 = "";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:249:0x05d6, code lost:
        r0 = ",";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:251:0x05e3, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzj("indexOf", 2, r1);
        r0 = com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:252:0x05f3, code lost:
        if (r26.isEmpty() != false) goto L_0x0600;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:253:0x05f5, code lost:
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:255:0x0605, code lost:
        if (r26.size() <= 1) goto L_0x063e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:256:0x0607, code lost:
        r1 = com.google.android.gms.internal.measurement.zzh.zza(r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(1)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:257:0x0624, code lost:
        if (r1 < ((double) r24.zzc())) goto L_0x0630;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:260:0x0632, code lost:
        if (r1 >= 0.0d) goto L_0x063b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:261:0x0634, code lost:
        r3 = ((double) r24.zzc()) + r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:262:0x063b, code lost:
        r3 = r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:263:0x063e, code lost:
        r1 = r24.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:265:0x0646, code lost:
        if (r1.hasNext() == false) goto L_0x066b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:266:0x0648, code lost:
        r2 = ((java.lang.Integer) r1.next()).intValue();
        r5 = (double) r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:267:0x0655, code lost:
        if (r5 < r3) goto L_0x0642;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:269:0x065f, code lost:
        if (com.google.android.gms.internal.measurement.zzh.zzl(r9.zze(r2), r0) == false) goto L_0x0642;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:272:0x0675, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
        com.google.android.gms.internal.measurement.zzh.zzh(r21, 1, r1);
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:273:0x068e, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzao) == false) goto L_0x06a2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x0694, code lost:
        if (r24.zzb() != 0) goto L_0x0699;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:277:0x0699, code lost:
        zzb(r9, r6, (com.google.android.gms.internal.measurement.zzao) r0, (java.lang.Boolean) null, (java.lang.Boolean) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:279:0x06a7, code lost:
        throw new java.lang.IllegalArgumentException("Callback should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:280:0x06a8, code lost:
        r9 = r24;
        r6 = r25;
        r0 = r26;
        com.google.android.gms.internal.measurement.zzh.zzh(r22, 1, r0);
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:281:0x06c1, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzao) == false) goto L_0x0704;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:283:0x06c7, code lost:
        if (r24.zzb() != 0) goto L_0x06cf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:285:0x06cf, code lost:
        r2 = r24.zzd();
        r0 = zzb(r9, r6, (com.google.android.gms.internal.measurement.zzao) r0, (java.lang.Boolean) null, true);
        r1 = new com.google.android.gms.internal.measurement.zzae();
        r0 = r0.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:287:0x06e7, code lost:
        if (r0.hasNext() == false) goto L_0x0702;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:288:0x06e9, code lost:
        r1.zzq(r1.zzc(), ((com.google.android.gms.internal.measurement.zzae) r2).zze(((java.lang.Integer) r0.next()).intValue()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:291:0x0709, code lost:
        throw new java.lang.IllegalArgumentException("Callback should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:292:0x070a, code lost:
        r3 = r24;
        r6 = r25;
        r0 = r26;
        com.google.android.gms.internal.measurement.zzh.zzh("every", 1, r0);
        r0 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r0.get(0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:293:0x0721, code lost:
        if ((r0 instanceof com.google.android.gms.internal.measurement.zzao) == false) goto L_0x0747;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:295:0x0727, code lost:
        if (r24.zzc() != 0) goto L_0x072c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:298:0x073f, code lost:
        if (zzb(r3, r6, (com.google.android.gms.internal.measurement.zzao) r0, false, true).zzc() == r24.zzc()) goto L_0x0744;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:302:0x074c, code lost:
        throw new java.lang.IllegalArgumentException("Callback should be a method");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:303:0x074d, code lost:
        r3 = r24;
        r6 = r25;
        r0 = r26;
        r1 = r24.zzd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:304:0x075b, code lost:
        if (r26.isEmpty() != false) goto L_0x07b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:305:0x075d, code lost:
        r0 = r26.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:307:0x0765, code lost:
        if (r0.hasNext() == false) goto L_0x07b1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:308:0x0767, code lost:
        r2 = r6.zzb((com.google.android.gms.internal.measurement.zzap) r0.next());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:309:0x0773, code lost:
        if ((r2 instanceof com.google.android.gms.internal.measurement.zzag) != false) goto L_0x07a9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:310:0x0775, code lost:
        r3 = (com.google.android.gms.internal.measurement.zzae) r1;
        r4 = r3.zzc();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:311:0x077e, code lost:
        if ((r2 instanceof com.google.android.gms.internal.measurement.zzae) == false) goto L_0x07a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:312:0x0780, code lost:
        r2 = (com.google.android.gms.internal.measurement.zzae) r2;
        r5 = r2.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:314:0x078a, code lost:
        if (r5.hasNext() == false) goto L_0x0761;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:315:0x078c, code lost:
        r7 = (java.lang.Integer) r5.next();
        r3.zzq(r7.intValue() + r4, r2.zze(r7.intValue()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:316:0x07a4, code lost:
        r3.zzq(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:318:0x07b0, code lost:
        throw new java.lang.IllegalStateException("Failed evaluation of arguments");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:319:0x07b1, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:354:?, code lost:
        return r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:355:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:356:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:357:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:358:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:359:?, code lost:
        return r24.zzd();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:360:?, code lost:
        return r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:361:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:362:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:363:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:364:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:365:?, code lost:
        return zzb(r9, r6, (com.google.android.gms.internal.measurement.zzao) r0, (java.lang.Boolean) null, (java.lang.Boolean) null);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:366:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:367:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(-1.0d));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:368:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(-1.0d));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:369:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r1));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:370:?, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r9.zzj(r0));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:371:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzm;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:372:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(-1.0d));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:373:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(-1.0d));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:374:?, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf(r5));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:375:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:376:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzf;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:377:?, code lost:
        return r1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:378:?, code lost:
        return new com.google.android.gms.internal.measurement.zzae();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:379:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:380:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzk;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:381:?, code lost:
        return com.google.android.gms.internal.measurement.zzap.zzl;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00ea, code lost:
        r2 = r17;
        r4 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00ef, code lost:
        r2 = r17;
        r4 = r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x010e, code lost:
        r2 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x011e, code lost:
        r0 = 65535;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x0120, code lost:
        r17 = r2;
        r21 = "forEach";
        r22 = r4;
        r3 = 0.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x012c, code lost:
        switch(r0) {
            case 0: goto L_0x074d;
            case 1: goto L_0x070a;
            case 2: goto L_0x06a8;
            case 3: goto L_0x0675;
            case 4: goto L_0x05e3;
            case 5: goto L_0x05a1;
            case 6: goto L_0x04fb;
            case 7: goto L_0x04c8;
            case 8: goto L_0x04ac;
            case 9: goto L_0x0475;
            case 10: goto L_0x0469;
            case 11: goto L_0x045d;
            case 12: goto L_0x0425;
            case 13: goto L_0x040b;
            case 14: goto L_0x0382;
            case 15: goto L_0x030c;
            case 16: goto L_0x02b0;
            case 17: goto L_0x01df;
            case 18: goto L_0x01c9;
            case 19: goto L_0x0137;
            default: goto L_0x012f;
        };
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x0136, code lost:
        throw new java.lang.IllegalArgumentException("Command not supported");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x013b, code lost:
        if (r26.isEmpty() != false) goto L_0x01b8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x013d, code lost:
        r0 = new com.google.android.gms.internal.measurement.zzae();
        r1 = r26.iterator();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x014a, code lost:
        if (r1.hasNext() == false) goto L_0x016c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x014c, code lost:
        r2 = r25.zzb((com.google.android.gms.internal.measurement.zzap) r1.next());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x015a, code lost:
        if ((r2 instanceof com.google.android.gms.internal.measurement.zzag) != false) goto L_0x0164;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:78:0x015c, code lost:
        r0.zzq(r0.zzc(), r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x016b, code lost:
        throw new java.lang.IllegalStateException("Argument evaluation failed");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x016c, code lost:
        r1 = r0.zzc();
        r2 = r24.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x0178, code lost:
        if (r2.hasNext() == false) goto L_0x0193;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x017a, code lost:
        r3 = (java.lang.Integer) r2.next();
        r0.zzq(r3.intValue() + r1, r24.zze(r3.intValue()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x0193, code lost:
        r9 = r24;
        r24.zzn();
        r1 = r0.zzk();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01a0, code lost:
        if (r1.hasNext() == false) goto L_0x01ba;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01a2, code lost:
        r2 = (java.lang.Integer) r1.next();
        r9.zzq(r2.intValue(), r0.zze(r2.intValue()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01b8, code lost:
        r9 = r24;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01c8, code lost:
        return new com.google.android.gms.internal.measurement.zzah(java.lang.Double.valueOf((double) r24.zzc()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01c9, code lost:
        com.google.android.gms.internal.measurement.zzh.zzh(r17, 0, r26);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01de, code lost:
        return new com.google.android.gms.internal.measurement.zzat(r24.zzj(","));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01df, code lost:
        r9 = r24;
        r6 = r25;
        r1 = r26;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01e9, code lost:
        if (r26.isEmpty() == false) goto L_0x01f2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01f2, code lost:
        r0 = (int) com.google.android.gms.internal.measurement.zzh.zza(r6.zzb((com.google.android.gms.internal.measurement.zzap) r1.get(0)).zzh().doubleValue());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x020b, code lost:
        if (r0 >= 0) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x020d, code lost:
        r0 = java.lang.Math.max(0, r0 + r24.zzc());
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.measurement.zzap zza(java.lang.String r23, com.google.android.gms.internal.measurement.zzae r24, com.google.android.gms.internal.measurement.zzg r25, java.util.List r26) {
        /*
            r0 = r23
            r1 = r24
            r2 = r25
            r3 = r26
            int r4 = r23.hashCode()
            java.lang.String r5 = "indexOf"
            java.lang.String r6 = "reverse"
            java.lang.String r7 = "slice"
            java.lang.String r8 = "shift"
            java.lang.String r9 = "every"
            java.lang.String r10 = "sort"
            java.lang.String r11 = "some"
            java.lang.String r12 = "join"
            java.lang.String r13 = "pop"
            java.lang.String r14 = "map"
            java.lang.String r15 = "lastIndexOf"
            java.lang.String r3 = "forEach"
            java.lang.String r1 = "filter"
            java.lang.String r2 = "toString"
            r16 = -1
            r17 = r2
            r2 = 1
            r18 = r1
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r2)
            switch(r4) {
                case -1776922004: goto L_0x0111;
                case -1354795244: goto L_0x0100;
                case -1274492040: goto L_0x00f4;
                case -934873754: goto L_0x00e0;
                case -895859076: goto L_0x00d4;
                case -678635926: goto L_0x00cc;
                case -467511597: goto L_0x00c4;
                case -277637751: goto L_0x00b8;
                case 107868: goto L_0x00b0;
                case 111185: goto L_0x00a7;
                case 3267882: goto L_0x009f;
                case 3452698: goto L_0x0094;
                case 3536116: goto L_0x008b;
                case 3536286: goto L_0x0082;
                case 96891675: goto L_0x0075;
                case 109407362: goto L_0x006b;
                case 109526418: goto L_0x0061;
                case 965561430: goto L_0x0055;
                case 1099846370: goto L_0x004b;
                case 1943291465: goto L_0x0041;
                default: goto L_0x003b;
            }
        L_0x003b:
            r2 = r17
            r4 = r18
            goto L_0x011e
        L_0x0041:
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x00ef
            r0 = 4
            goto L_0x00ea
        L_0x004b:
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x00ef
            r0 = 12
            goto L_0x00ea
        L_0x0055:
            java.lang.String r4 = "reduceRight"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00ef
            r0 = 11
            goto L_0x00ea
        L_0x0061:
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x00ef
            r0 = 14
            goto L_0x00ea
        L_0x006b:
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x00ef
            r0 = 13
            goto L_0x00ea
        L_0x0075:
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x00ef
            r2 = r17
            r4 = r18
            r0 = 1
            goto L_0x0120
        L_0x0082:
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x00ef
            r0 = 16
            goto L_0x00ea
        L_0x008b:
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x00ef
            r0 = 15
            goto L_0x00ea
        L_0x0094:
            java.lang.String r4 = "push"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00ef
            r0 = 9
            goto L_0x00ea
        L_0x009f:
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00ef
            r0 = 5
            goto L_0x00ea
        L_0x00a7:
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x00ef
            r0 = 8
            goto L_0x00ea
        L_0x00b0:
            boolean r0 = r0.equals(r14)
            if (r0 == 0) goto L_0x00ef
            r0 = 7
            goto L_0x00ea
        L_0x00b8:
            java.lang.String r4 = "unshift"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00ef
            r0 = 19
            goto L_0x00ea
        L_0x00c4:
            boolean r0 = r0.equals(r15)
            if (r0 == 0) goto L_0x00ef
            r0 = 6
            goto L_0x00ea
        L_0x00cc:
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x00ef
            r0 = 3
            goto L_0x00ea
        L_0x00d4:
            java.lang.String r4 = "splice"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00ef
            r0 = 17
            goto L_0x00ea
        L_0x00e0:
            java.lang.String r4 = "reduce"
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x00ef
            r0 = 10
        L_0x00ea:
            r2 = r17
            r4 = r18
            goto L_0x0120
        L_0x00ef:
            r2 = r17
            r4 = r18
            goto L_0x011e
        L_0x00f4:
            r4 = r18
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x010e
            r2 = r17
            r0 = 2
            goto L_0x0120
        L_0x0100:
            r4 = r18
            java.lang.String r2 = "concat"
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x010e
            r2 = r17
            r0 = 0
            goto L_0x0120
        L_0x010e:
            r2 = r17
            goto L_0x011e
        L_0x0111:
            r4 = r18
            r2 = r17
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x011e
            r0 = 18
            goto L_0x0120
        L_0x011e:
            r0 = r16
        L_0x0120:
            r19 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            r17 = r2
            java.lang.String r2 = "Callback should be a method"
            r21 = r3
            r22 = r4
            r3 = 0
            switch(r0) {
                case 0: goto L_0x074d;
                case 1: goto L_0x070a;
                case 2: goto L_0x06a8;
                case 3: goto L_0x0675;
                case 4: goto L_0x05e3;
                case 5: goto L_0x05a1;
                case 6: goto L_0x04fb;
                case 7: goto L_0x04c8;
                case 8: goto L_0x04ac;
                case 9: goto L_0x0475;
                case 10: goto L_0x0469;
                case 11: goto L_0x045d;
                case 12: goto L_0x0425;
                case 13: goto L_0x040b;
                case 14: goto L_0x0382;
                case 15: goto L_0x030c;
                case 16: goto L_0x02b0;
                case 17: goto L_0x01df;
                case 18: goto L_0x01c9;
                case 19: goto L_0x0137;
                default: goto L_0x012f;
            }
        L_0x012f:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Command not supported"
            r0.<init>(r1)
            throw r0
        L_0x0137:
            boolean r0 = r26.isEmpty()
            if (r0 != 0) goto L_0x01b8
            com.google.android.gms.internal.measurement.zzae r0 = new com.google.android.gms.internal.measurement.zzae
            r0.<init>()
            java.util.Iterator r1 = r26.iterator()
        L_0x0146:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x016c
            java.lang.Object r2 = r1.next()
            com.google.android.gms.internal.measurement.zzap r2 = (com.google.android.gms.internal.measurement.zzap) r2
            r6 = r25
            com.google.android.gms.internal.measurement.zzap r2 = r6.zzb(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzag
            if (r3 != 0) goto L_0x0164
            int r3 = r0.zzc()
            r0.zzq(r3, r2)
            goto L_0x0146
        L_0x0164:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Argument evaluation failed"
            r0.<init>(r1)
            throw r0
        L_0x016c:
            int r1 = r0.zzc()
            java.util.Iterator r2 = r24.zzk()
        L_0x0174:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0193
            java.lang.Object r3 = r2.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r4 = r3.intValue()
            int r4 = r4 + r1
            int r3 = r3.intValue()
            r9 = r24
            com.google.android.gms.internal.measurement.zzap r3 = r9.zze(r3)
            r0.zzq(r4, r3)
            goto L_0x0174
        L_0x0193:
            r9 = r24
            r24.zzn()
            java.util.Iterator r1 = r0.zzk()
        L_0x019c:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x01ba
            java.lang.Object r2 = r1.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r3 = r2.intValue()
            int r2 = r2.intValue()
            com.google.android.gms.internal.measurement.zzap r2 = r0.zze(r2)
            r9.zzq(r3, r2)
            goto L_0x019c
        L_0x01b8:
            r9 = r24
        L_0x01ba:
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            int r1 = r24.zzc()
            double r1 = (double) r1
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
            r0.<init>(r1)
            return r0
        L_0x01c9:
            r9 = r24
            r0 = r17
            r1 = r26
            r2 = 0
            com.google.android.gms.internal.measurement.zzh.zzh(r0, r2, r1)
            com.google.android.gms.internal.measurement.zzat r0 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r1 = ","
            java.lang.String r1 = r9.zzj(r1)
            r0.<init>(r1)
            return r0
        L_0x01df:
            r9 = r24
            r6 = r25
            r1 = r26
            boolean r0 = r26.isEmpty()
            if (r0 == 0) goto L_0x01f2
            com.google.android.gms.internal.measurement.zzae r0 = new com.google.android.gms.internal.measurement.zzae
            r0.<init>()
            goto L_0x02af
        L_0x01f2:
            r0 = 0
            java.lang.Object r2 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r2 = (com.google.android.gms.internal.measurement.zzap) r2
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r2)
            java.lang.Double r0 = r0.zzh()
            double r2 = r0.doubleValue()
            double r2 = com.google.android.gms.internal.measurement.zzh.zza(r2)
            int r0 = (int) r2
            if (r0 >= 0) goto L_0x0218
            int r2 = r24.zzc()
            int r0 = r0 + r2
            r2 = 0
            int r0 = java.lang.Math.max(r2, r0)
            goto L_0x0222
        L_0x0218:
            int r2 = r24.zzc()
            if (r0 <= r2) goto L_0x0222
            int r0 = r24.zzc()
        L_0x0222:
            int r2 = r24.zzc()
            com.google.android.gms.internal.measurement.zzae r3 = new com.google.android.gms.internal.measurement.zzae
            r3.<init>()
            int r4 = r26.size()
            r5 = 1
            if (r4 <= r5) goto L_0x0299
            java.lang.Object r4 = r1.get(r5)
            com.google.android.gms.internal.measurement.zzap r4 = (com.google.android.gms.internal.measurement.zzap) r4
            com.google.android.gms.internal.measurement.zzap r4 = r6.zzb(r4)
            java.lang.Double r4 = r4.zzh()
            double r4 = r4.doubleValue()
            double r4 = com.google.android.gms.internal.measurement.zzh.zza(r4)
            int r4 = (int) r4
            r5 = 0
            int r4 = java.lang.Math.max(r5, r4)
            if (r4 <= 0) goto L_0x026a
            r5 = r0
        L_0x0251:
            int r7 = r0 + r4
            int r7 = java.lang.Math.min(r2, r7)
            if (r5 >= r7) goto L_0x026a
            com.google.android.gms.internal.measurement.zzap r7 = r9.zze(r0)
            int r8 = r3.zzc()
            r3.zzq(r8, r7)
            r9.zzp(r0)
            int r5 = r5 + 1
            goto L_0x0251
        L_0x026a:
            int r2 = r26.size()
            r4 = 2
            if (r2 <= r4) goto L_0x0298
            r2 = 2
        L_0x0272:
            int r4 = r26.size()
            if (r2 >= r4) goto L_0x0298
            java.lang.Object r4 = r1.get(r2)
            com.google.android.gms.internal.measurement.zzap r4 = (com.google.android.gms.internal.measurement.zzap) r4
            com.google.android.gms.internal.measurement.zzap r4 = r6.zzb(r4)
            boolean r5 = r4 instanceof com.google.android.gms.internal.measurement.zzag
            if (r5 != 0) goto L_0x0290
            int r5 = r0 + r2
            int r5 = r5 + -2
            r9.zzo(r5, r4)
            int r2 = r2 + 1
            goto L_0x0272
        L_0x0290:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Failed to parse elements to add"
            r0.<init>(r1)
            throw r0
        L_0x0298:
            goto L_0x02ae
        L_0x0299:
            if (r0 >= r2) goto L_0x02ad
            com.google.android.gms.internal.measurement.zzap r1 = r9.zze(r0)
            int r4 = r3.zzc()
            r3.zzq(r4, r1)
            r1 = 0
            r9.zzq(r0, r1)
            int r0 = r0 + 1
            goto L_0x0299
        L_0x02ad:
        L_0x02ae:
            r0 = r3
        L_0x02af:
            return r0
        L_0x02b0:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 1
            com.google.android.gms.internal.measurement.zzh.zzj(r10, r0, r1)
            int r0 = r24.zzc()
            r2 = 2
            if (r0 >= r2) goto L_0x02c2
            goto L_0x030b
        L_0x02c2:
            java.util.List r0 = r24.zzm()
            boolean r2 = r26.isEmpty()
            if (r2 != 0) goto L_0x02e7
            r2 = 0
            java.lang.Object r1 = r1.get(r2)
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            com.google.android.gms.internal.measurement.zzap r1 = r6.zzb(r1)
            boolean r2 = r1 instanceof com.google.android.gms.internal.measurement.zzai
            if (r2 == 0) goto L_0x02df
            r3 = r1
            com.google.android.gms.internal.measurement.zzai r3 = (com.google.android.gms.internal.measurement.zzai) r3
            goto L_0x02e8
        L_0x02df:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Comparator should be a method"
            r0.<init>(r1)
            throw r0
        L_0x02e7:
            r3 = 0
        L_0x02e8:
            com.google.android.gms.internal.measurement.zzba r1 = new com.google.android.gms.internal.measurement.zzba
            r1.<init>(r3, r6)
            java.util.Collections.sort(r0, r1)
            r24.zzn()
            java.util.Iterator r0 = r0.iterator()
            r2 = 0
        L_0x02f8:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x030b
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            int r3 = r2 + 1
            r9.zzq(r2, r1)
            r2 = r3
            goto L_0x02f8
        L_0x030b:
            return r9
        L_0x030c:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r11, r0, r1)
            r0 = 0
            java.lang.Object r1 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r1)
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzai
            if (r1 == 0) goto L_0x037c
            int r1 = r24.zzc()
            if (r1 != 0) goto L_0x032e
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzl
            goto L_0x037b
        L_0x032e:
            com.google.android.gms.internal.measurement.zzai r0 = (com.google.android.gms.internal.measurement.zzai) r0
            java.util.Iterator r1 = r24.zzk()
        L_0x0334:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0379
            java.lang.Object r2 = r1.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            boolean r3 = r9.zzs(r2)
            if (r3 == 0) goto L_0x0334
            r3 = 3
            com.google.android.gms.internal.measurement.zzap[] r3 = new com.google.android.gms.internal.measurement.zzap[r3]
            com.google.android.gms.internal.measurement.zzap r4 = r9.zze(r2)
            r5 = 0
            r3[r5] = r4
            double r4 = (double) r2
            com.google.android.gms.internal.measurement.zzah r2 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r4 = java.lang.Double.valueOf(r4)
            r2.<init>(r4)
            r4 = 1
            r3[r4] = r2
            r2 = 2
            r3[r2] = r9
            java.util.List r2 = java.util.Arrays.asList(r3)
            com.google.android.gms.internal.measurement.zzap r2 = r0.zza(r6, r2)
            java.lang.Boolean r2 = r2.zzg()
            boolean r2 = r2.booleanValue()
            if (r2 == 0) goto L_0x0334
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzk
            goto L_0x037b
        L_0x0379:
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzl
        L_0x037b:
            return r0
        L_0x037c:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r2)
            throw r0
        L_0x0382:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r7, r0, r1)
            boolean r0 = r26.isEmpty()
            if (r0 == 0) goto L_0x0398
            com.google.android.gms.internal.measurement.zzap r0 = r24.zzd()
            goto L_0x040a
        L_0x0398:
            int r0 = r24.zzc()
            double r7 = (double) r0
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r10 = r0.doubleValue()
            double r10 = com.google.android.gms.internal.measurement.zzh.zza(r10)
            int r0 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x03be
            double r10 = r10 + r7
            double r10 = java.lang.Math.max(r10, r3)
            goto L_0x03c2
        L_0x03be:
            double r10 = java.lang.Math.min(r10, r7)
        L_0x03c2:
            int r0 = r26.size()
            r2 = 2
            if (r0 != r2) goto L_0x03ef
            r0 = 1
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            java.lang.Double r0 = r0.zzh()
            double r0 = r0.doubleValue()
            double r0 = com.google.android.gms.internal.measurement.zzh.zza(r0)
            int r2 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r2 >= 0) goto L_0x03ea
            double r7 = r7 + r0
            double r7 = java.lang.Math.max(r7, r3)
            goto L_0x03f0
        L_0x03ea:
            double r7 = java.lang.Math.min(r7, r0)
            goto L_0x03f0
        L_0x03ef:
        L_0x03f0:
            com.google.android.gms.internal.measurement.zzae r0 = new com.google.android.gms.internal.measurement.zzae
            r0.<init>()
            int r1 = (int) r10
        L_0x03f6:
            double r2 = (double) r1
            int r2 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r2 >= 0) goto L_0x0409
            com.google.android.gms.internal.measurement.zzap r2 = r9.zze(r1)
            int r3 = r0.zzc()
            r0.zzq(r3, r2)
            int r1 = r1 + 1
            goto L_0x03f6
        L_0x0409:
        L_0x040a:
            return r0
        L_0x040b:
            r9 = r24
            r1 = r26
            r0 = 0
            com.google.android.gms.internal.measurement.zzh.zzh(r8, r0, r1)
            int r1 = r24.zzc()
            if (r1 != 0) goto L_0x041c
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
            goto L_0x0424
        L_0x041c:
            com.google.android.gms.internal.measurement.zzap r1 = r9.zze(r0)
            r9.zzp(r0)
            r0 = r1
        L_0x0424:
            return r0
        L_0x0425:
            r9 = r24
            r1 = r26
            r0 = 0
            com.google.android.gms.internal.measurement.zzh.zzh(r6, r0, r1)
            int r0 = r24.zzc()
            if (r0 == 0) goto L_0x045c
            r2 = 0
        L_0x0434:
            int r1 = r0 / 2
            if (r2 >= r1) goto L_0x045c
            boolean r1 = r9.zzs(r2)
            if (r1 == 0) goto L_0x0459
            com.google.android.gms.internal.measurement.zzap r1 = r9.zze(r2)
            r3 = 0
            r9.zzq(r2, r3)
            int r3 = r0 + -1
            int r3 = r3 - r2
            boolean r4 = r9.zzs(r3)
            if (r4 == 0) goto L_0x0456
            com.google.android.gms.internal.measurement.zzap r4 = r9.zze(r3)
            r9.zzq(r2, r4)
        L_0x0456:
            r9.zzq(r3, r1)
        L_0x0459:
            int r2 = r2 + 1
            goto L_0x0434
        L_0x045c:
            return r9
        L_0x045d:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 0
            com.google.android.gms.internal.measurement.zzap r0 = zzc(r9, r6, r1, r0)
            return r0
        L_0x0469:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 1
            com.google.android.gms.internal.measurement.zzap r0 = zzc(r9, r6, r1, r0)
            return r0
        L_0x0475:
            r9 = r24
            r6 = r25
            r1 = r26
            boolean r0 = r26.isEmpty()
            if (r0 != 0) goto L_0x049d
            java.util.Iterator r0 = r26.iterator()
        L_0x0485:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x049d
            java.lang.Object r1 = r0.next()
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            com.google.android.gms.internal.measurement.zzap r1 = r6.zzb(r1)
            int r2 = r24.zzc()
            r9.zzq(r2, r1)
            goto L_0x0485
        L_0x049d:
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            int r1 = r24.zzc()
            double r1 = (double) r1
            java.lang.Double r1 = java.lang.Double.valueOf(r1)
            r0.<init>(r1)
            return r0
        L_0x04ac:
            r9 = r24
            r1 = r26
            r0 = 0
            com.google.android.gms.internal.measurement.zzh.zzh(r13, r0, r1)
            int r0 = r24.zzc()
            if (r0 != 0) goto L_0x04bd
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
            goto L_0x04c7
        L_0x04bd:
            int r0 = r0 + -1
            com.google.android.gms.internal.measurement.zzap r1 = r9.zze(r0)
            r9.zzp(r0)
            r0 = r1
        L_0x04c7:
            return r0
        L_0x04c8:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r14, r0, r1)
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzao
            if (r1 == 0) goto L_0x04f5
            int r1 = r24.zzc()
            if (r1 != 0) goto L_0x04ed
            com.google.android.gms.internal.measurement.zzae r0 = new com.google.android.gms.internal.measurement.zzae
            r0.<init>()
            goto L_0x04f4
        L_0x04ed:
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            r1 = 0
            com.google.android.gms.internal.measurement.zzae r0 = zzb(r9, r6, r0, r1, r1)
        L_0x04f4:
            return r0
        L_0x04f5:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r2)
            throw r0
        L_0x04fb:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r15, r0, r1)
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
            boolean r2 = r26.isEmpty()
            if (r2 != 0) goto L_0x0518
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
        L_0x0518:
            int r2 = r24.zzc()
            int r2 = r2 + -1
            int r5 = r26.size()
            r7 = 1
            if (r5 <= r7) goto L_0x055d
            java.lang.Object r1 = r1.get(r7)
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            com.google.android.gms.internal.measurement.zzap r1 = r6.zzb(r1)
            java.lang.Double r2 = r1.zzh()
            double r5 = r2.doubleValue()
            boolean r2 = java.lang.Double.isNaN(r5)
            if (r2 == 0) goto L_0x0545
            int r1 = r24.zzc()
            int r1 = r1 + -1
            double r1 = (double) r1
            goto L_0x0551
        L_0x0545:
            java.lang.Double r1 = r1.zzh()
            double r1 = r1.doubleValue()
            double r1 = com.google.android.gms.internal.measurement.zzh.zza(r1)
        L_0x0551:
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x055c
            int r5 = r24.zzc()
            double r5 = (double) r5
            double r1 = r1 + r5
            goto L_0x055e
        L_0x055c:
            goto L_0x055e
        L_0x055d:
            double r1 = (double) r2
        L_0x055e:
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x056c
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r1 = java.lang.Double.valueOf(r19)
            r0.<init>(r1)
            goto L_0x05a0
        L_0x056c:
            int r3 = r24.zzc()
            double r3 = (double) r3
            double r1 = java.lang.Math.min(r3, r1)
            int r1 = (int) r1
        L_0x0576:
            if (r1 < 0) goto L_0x0597
            boolean r2 = r9.zzs(r1)
            if (r2 == 0) goto L_0x0594
            com.google.android.gms.internal.measurement.zzap r2 = r9.zze(r1)
            boolean r2 = com.google.android.gms.internal.measurement.zzh.zzl(r2, r0)
            if (r2 == 0) goto L_0x0594
            double r0 = (double) r1
            com.google.android.gms.internal.measurement.zzah r2 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r0 = java.lang.Double.valueOf(r0)
            r2.<init>(r0)
            r0 = r2
            goto L_0x05a0
        L_0x0594:
            int r1 = r1 + -1
            goto L_0x0576
        L_0x0597:
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r1 = java.lang.Double.valueOf(r19)
            r0.<init>(r1)
        L_0x05a0:
            return r0
        L_0x05a1:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 1
            com.google.android.gms.internal.measurement.zzh.zzj(r12, r0, r1)
            int r0 = r24.zzc()
            if (r0 != 0) goto L_0x05b4
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzm
            goto L_0x05e2
        L_0x05b4:
            boolean r0 = r26.isEmpty()
            if (r0 != 0) goto L_0x05d6
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzan
            if (r1 != 0) goto L_0x05d3
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzau
            if (r1 == 0) goto L_0x05ce
            goto L_0x05d3
        L_0x05ce:
            java.lang.String r0 = r0.zzi()
            goto L_0x05d8
        L_0x05d3:
            java.lang.String r0 = ""
            goto L_0x05d8
        L_0x05d6:
            java.lang.String r0 = ","
        L_0x05d8:
            com.google.android.gms.internal.measurement.zzat r1 = new com.google.android.gms.internal.measurement.zzat
            java.lang.String r0 = r9.zzj(r0)
            r1.<init>(r0)
            r0 = r1
        L_0x05e2:
            return r0
        L_0x05e3:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = 2
            com.google.android.gms.internal.measurement.zzh.zzj(r5, r0, r1)
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
            boolean r2 = r26.isEmpty()
            if (r2 != 0) goto L_0x0600
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
        L_0x0600:
            int r2 = r26.size()
            r5 = 1
            if (r2 <= r5) goto L_0x063d
            java.lang.Object r1 = r1.get(r5)
            com.google.android.gms.internal.measurement.zzap r1 = (com.google.android.gms.internal.measurement.zzap) r1
            com.google.android.gms.internal.measurement.zzap r1 = r6.zzb(r1)
            java.lang.Double r1 = r1.zzh()
            double r1 = r1.doubleValue()
            double r1 = com.google.android.gms.internal.measurement.zzh.zza(r1)
            int r5 = r24.zzc()
            double r5 = (double) r5
            int r5 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x0630
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r1 = java.lang.Double.valueOf(r19)
            r0.<init>(r1)
            goto L_0x0674
        L_0x0630:
            int r3 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r3 >= 0) goto L_0x063b
            int r3 = r24.zzc()
            double r3 = (double) r3
            double r3 = r3 + r1
            goto L_0x063e
        L_0x063b:
            r3 = r1
            goto L_0x063e
        L_0x063d:
        L_0x063e:
            java.util.Iterator r1 = r24.zzk()
        L_0x0642:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x066b
            java.lang.Object r2 = r1.next()
            java.lang.Integer r2 = (java.lang.Integer) r2
            int r2 = r2.intValue()
            double r5 = (double) r2
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 < 0) goto L_0x0642
            com.google.android.gms.internal.measurement.zzap r2 = r9.zze(r2)
            boolean r2 = com.google.android.gms.internal.measurement.zzh.zzl(r2, r0)
            if (r2 == 0) goto L_0x0642
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r1 = java.lang.Double.valueOf(r5)
            r0.<init>(r1)
            goto L_0x0674
        L_0x066b:
            com.google.android.gms.internal.measurement.zzah r0 = new com.google.android.gms.internal.measurement.zzah
            java.lang.Double r1 = java.lang.Double.valueOf(r19)
            r0.<init>(r1)
        L_0x0674:
            return r0
        L_0x0675:
            r9 = r24
            r6 = r25
            r1 = r26
            r0 = r21
            r3 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r0, r3, r1)
            r0 = 0
            java.lang.Object r0 = r1.get(r0)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            boolean r1 = r0 instanceof com.google.android.gms.internal.measurement.zzao
            if (r1 == 0) goto L_0x06a2
            int r1 = r24.zzb()
            if (r1 != 0) goto L_0x0699
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
            goto L_0x06a1
        L_0x0699:
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            r1 = 0
            zzb(r9, r6, r0, r1, r1)
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzf
        L_0x06a1:
            return r0
        L_0x06a2:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r2)
            throw r0
        L_0x06a8:
            r9 = r24
            r6 = r25
            r0 = r26
            r3 = r22
            r4 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r3, r4, r0)
            r3 = 0
            java.lang.Object r0 = r0.get(r3)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            boolean r3 = r0 instanceof com.google.android.gms.internal.measurement.zzao
            if (r3 == 0) goto L_0x0704
            int r2 = r24.zzb()
            if (r2 != 0) goto L_0x06cf
            com.google.android.gms.internal.measurement.zzae r0 = new com.google.android.gms.internal.measurement.zzae
            r0.<init>()
            goto L_0x0703
        L_0x06cf:
            com.google.android.gms.internal.measurement.zzap r2 = r24.zzd()
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            r3 = 0
            com.google.android.gms.internal.measurement.zzae r0 = zzb(r9, r6, r0, r3, r1)
            com.google.android.gms.internal.measurement.zzae r1 = new com.google.android.gms.internal.measurement.zzae
            r1.<init>()
            java.util.Iterator r0 = r0.zzk()
        L_0x06e3:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0702
            java.lang.Object r3 = r0.next()
            java.lang.Integer r3 = (java.lang.Integer) r3
            int r3 = r3.intValue()
            r4 = r2
            com.google.android.gms.internal.measurement.zzae r4 = (com.google.android.gms.internal.measurement.zzae) r4
            com.google.android.gms.internal.measurement.zzap r3 = r4.zze(r3)
            int r4 = r1.zzc()
            r1.zzq(r4, r3)
            goto L_0x06e3
        L_0x0702:
            r0 = r1
        L_0x0703:
            return r0
        L_0x0704:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r2)
            throw r0
        L_0x070a:
            r3 = r24
            r6 = r25
            r0 = r26
            r4 = 1
            com.google.android.gms.internal.measurement.zzh.zzh(r9, r4, r0)
            r4 = 0
            java.lang.Object r0 = r0.get(r4)
            com.google.android.gms.internal.measurement.zzap r0 = (com.google.android.gms.internal.measurement.zzap) r0
            com.google.android.gms.internal.measurement.zzap r0 = r6.zzb(r0)
            boolean r4 = r0 instanceof com.google.android.gms.internal.measurement.zzao
            if (r4 == 0) goto L_0x0747
            int r2 = r24.zzc()
            if (r2 != 0) goto L_0x072c
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzk
            goto L_0x0746
        L_0x072c:
            com.google.android.gms.internal.measurement.zzao r0 = (com.google.android.gms.internal.measurement.zzao) r0
            r2 = 0
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            com.google.android.gms.internal.measurement.zzae r0 = zzb(r3, r6, r0, r2, r1)
            int r0 = r0.zzc()
            int r1 = r24.zzc()
            if (r0 == r1) goto L_0x0744
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzl
            goto L_0x0746
        L_0x0744:
            com.google.android.gms.internal.measurement.zzap r0 = com.google.android.gms.internal.measurement.zzap.zzk
        L_0x0746:
            return r0
        L_0x0747:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            r0.<init>(r2)
            throw r0
        L_0x074d:
            r3 = r24
            r6 = r25
            r0 = r26
            com.google.android.gms.internal.measurement.zzap r1 = r24.zzd()
            boolean r2 = r26.isEmpty()
            if (r2 != 0) goto L_0x07b1
            java.util.Iterator r0 = r26.iterator()
        L_0x0761:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x07b1
            java.lang.Object r2 = r0.next()
            com.google.android.gms.internal.measurement.zzap r2 = (com.google.android.gms.internal.measurement.zzap) r2
            com.google.android.gms.internal.measurement.zzap r2 = r6.zzb(r2)
            boolean r3 = r2 instanceof com.google.android.gms.internal.measurement.zzag
            if (r3 != 0) goto L_0x07a9
            r3 = r1
            com.google.android.gms.internal.measurement.zzae r3 = (com.google.android.gms.internal.measurement.zzae) r3
            int r4 = r3.zzc()
            boolean r5 = r2 instanceof com.google.android.gms.internal.measurement.zzae
            if (r5 == 0) goto L_0x07a4
            com.google.android.gms.internal.measurement.zzae r2 = (com.google.android.gms.internal.measurement.zzae) r2
            java.util.Iterator r5 = r2.zzk()
        L_0x0786:
            boolean r7 = r5.hasNext()
            if (r7 == 0) goto L_0x0761
            java.lang.Object r7 = r5.next()
            java.lang.Integer r7 = (java.lang.Integer) r7
            int r8 = r7.intValue()
            int r8 = r8 + r4
            int r7 = r7.intValue()
            com.google.android.gms.internal.measurement.zzap r7 = r2.zze(r7)
            r3.zzq(r8, r7)
            goto L_0x0786
        L_0x07a4:
            r3.zzq(r4, r2)
            goto L_0x0761
        L_0x07a9:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Failed evaluation of arguments"
            r0.<init>(r1)
            throw r0
        L_0x07b1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzbb.zza(java.lang.String, com.google.android.gms.internal.measurement.zzae, com.google.android.gms.internal.measurement.zzg, java.util.List):com.google.android.gms.internal.measurement.zzap");
    }

    private static zzae zzb(zzae zzae, zzg zzg, zzai zzai, Boolean bool, Boolean bool2) {
        zzae zzae2 = new zzae();
        Iterator zzk = zzae.zzk();
        while (zzk.hasNext()) {
            int intValue = ((Integer) zzk.next()).intValue();
            if (zzae.zzs(intValue)) {
                zzap zza = zzai.zza(zzg, Arrays.asList(new zzap[]{zzae.zze(intValue), new zzah(Double.valueOf((double) intValue)), zzae}));
                if (zza.zzg().equals(bool)) {
                    return zzae2;
                }
                if (bool2 == null || zza.zzg().equals(bool2)) {
                    zzae2.zzq(intValue, zza);
                }
            }
        }
        return zzae2;
    }

    private static zzap zzc(zzae zzae, zzg zzg, List list, boolean z) {
        zzap zzap;
        int i;
        int i2;
        zzh.zzi("reduce", 1, list);
        zzh.zzj("reduce", 2, list);
        zzap zzb = zzg.zzb((zzap) list.get(0));
        if (zzb instanceof zzai) {
            if (list.size() == 2) {
                zzap = zzg.zzb((zzap) list.get(1));
                if (zzap instanceof zzag) {
                    throw new IllegalArgumentException("Failed to parse initial value");
                }
            } else if (zzae.zzc() != 0) {
                zzap = null;
            } else {
                throw new IllegalStateException("Empty array with no initial value error");
            }
            zzai zzai = (zzai) zzb;
            int zzc = zzae.zzc();
            if (z) {
                i = 0;
            } else {
                i = zzc - 1;
            }
            int i3 = -1;
            if (z) {
                i2 = zzc - 1;
            } else {
                i2 = 0;
            }
            if (true == z) {
                i3 = 1;
            }
            if (zzap == null) {
                zzap = zzae.zze(i);
                i += i3;
            }
            while ((i2 - i) * i3 >= 0) {
                if (zzae.zzs(i)) {
                    zzap = zzai.zza(zzg, Arrays.asList(new zzap[]{zzap, zzae.zze(i), new zzah(Double.valueOf((double) i)), zzae}));
                    if (zzap instanceof zzag) {
                        throw new IllegalStateException("Reduce operation failed");
                    }
                }
                i += i3;
            }
            return zzap;
        }
        throw new IllegalArgumentException("Callback should be a method");
    }
}
