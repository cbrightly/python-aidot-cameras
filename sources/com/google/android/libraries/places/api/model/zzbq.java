package com.google.android.libraries.places.api.model;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.android.libraries.places.api.model.Period;
import com.google.android.libraries.places.internal.zzjs;
import com.google.android.libraries.places.internal.zzjt;
import com.google.android.libraries.places.internal.zzkc;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzbq {
    public static final /* synthetic */ int zza = 0;
    private static final zzjt zzb;
    private static final LocalTime zzc = LocalTime.newInstance(23, 59);

    static {
        zzjs zzjs = new zzjs();
        zzjs.zza(1, DayOfWeek.SUNDAY);
        zzjs.zza(2, DayOfWeek.MONDAY);
        zzjs.zza(3, DayOfWeek.TUESDAY);
        zzjs.zza(4, DayOfWeek.WEDNESDAY);
        zzjs.zza(5, DayOfWeek.THURSDAY);
        zzjs.zza(6, DayOfWeek.FRIDAY);
        zzjs.zza(7, DayOfWeek.SATURDAY);
        zzb = zzjs.zzb();
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x00ce A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00f0  */
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Boolean zza(com.google.android.libraries.places.api.model.Place r15, long r16) {
        /*
            com.google.android.libraries.places.api.model.Place$BusinessStatus r0 = r15.getBusinessStatus()
            java.lang.Integer r1 = r15.getUtcOffsetMinutes()
            r2 = 0
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r2)
            if (r0 == 0) goto L_0x0016
            com.google.android.libraries.places.api.model.Place$BusinessStatus r4 = com.google.android.libraries.places.api.model.Place.BusinessStatus.OPERATIONAL
            if (r0 != r4) goto L_0x0014
            goto L_0x0016
        L_0x0014:
            return r3
        L_0x0016:
            r4 = 0
            if (r1 != 0) goto L_0x001a
            return r4
        L_0x001a:
            com.google.android.libraries.places.api.model.OpeningHours r0 = r15.getCurrentOpeningHours()
            int r5 = r1.intValue()
            if (r0 != 0) goto L_0x0026
            goto L_0x00c8
        L_0x0026:
            java.util.TimeZone r5 = zze(r5)
            if (r5 == 0) goto L_0x00c8
            java.util.ArrayList r6 = new java.util.ArrayList
            java.util.List r7 = r0.getPeriods()
            r6.<init>(r7)
            boolean r7 = r6.isEmpty()
            if (r7 != 0) goto L_0x00c8
            com.google.android.libraries.places.api.model.zzbp r7 = com.google.android.libraries.places.api.model.zzbp.zza     // Catch:{ NullPointerException -> 0x00c7 }
            java.util.Collections.sort(r6, r7)     // Catch:{ NullPointerException -> 0x00c7 }
            java.lang.Object r2 = r6.get(r2)     // Catch:{ NullPointerException -> 0x00c7 }
            com.google.android.libraries.places.api.model.Period r2 = (com.google.android.libraries.places.api.model.Period) r2     // Catch:{ NullPointerException -> 0x00c7 }
            com.google.android.libraries.places.api.model.TimeOfWeek r2 = r2.getOpen()     // Catch:{ NullPointerException -> 0x00c7 }
            if (r2 == 0) goto L_0x00c5
            com.google.android.libraries.places.api.model.LocalDate r2 = r2.getDate()     // Catch:{ NullPointerException -> 0x00c7 }
            boolean r7 = r6.isEmpty()     // Catch:{ NullPointerException -> 0x00c7 }
            if (r7 != 0) goto L_0x00bf
            int r7 = r6.size()     // Catch:{ NullPointerException -> 0x00c7 }
            int r7 = r7 + -1
            java.lang.Object r6 = r6.get(r7)     // Catch:{ NullPointerException -> 0x00c7 }
            com.google.android.libraries.places.api.model.Period r6 = (com.google.android.libraries.places.api.model.Period) r6     // Catch:{ NullPointerException -> 0x00c7 }
            com.google.android.libraries.places.api.model.TimeOfWeek r6 = r6.getClose()     // Catch:{ NullPointerException -> 0x00c7 }
            if (r6 == 0) goto L_0x00bd
            com.google.android.libraries.places.api.model.LocalDate r6 = r6.getDate()     // Catch:{ NullPointerException -> 0x00c7 }
            if (r2 == 0) goto L_0x00c8
            if (r6 == 0) goto L_0x00c8
            java.util.Calendar r5 = java.util.Calendar.getInstance(r5)     // Catch:{ NullPointerException -> 0x00c7 }
            int r8 = r2.getYear()     // Catch:{ NullPointerException -> 0x00c7 }
            int r7 = r2.getMonth()     // Catch:{ NullPointerException -> 0x00c7 }
            int r9 = r7 + -1
            int r10 = r2.getDay()     // Catch:{ NullPointerException -> 0x00c7 }
            r11 = 0
            r12 = 0
            r7 = r5
            r7.set(r8, r9, r10, r11, r12)     // Catch:{ NullPointerException -> 0x00c7 }
            long r13 = r5.getTimeInMillis()     // Catch:{ NullPointerException -> 0x00c7 }
            int r8 = r6.getYear()     // Catch:{ NullPointerException -> 0x00c7 }
            int r2 = r6.getMonth()     // Catch:{ NullPointerException -> 0x00c7 }
            int r9 = r2 + -1
            int r10 = r6.getDay()     // Catch:{ NullPointerException -> 0x00c7 }
            r11 = 23
            r12 = 59
            r7 = r5
            r7.set(r8, r9, r10, r11, r12)     // Catch:{ NullPointerException -> 0x00c7 }
            long r5 = r5.getTimeInMillis()     // Catch:{ NullPointerException -> 0x00c7 }
            java.lang.Long r2 = java.lang.Long.valueOf(r13)     // Catch:{ NullPointerException -> 0x00c7 }
            java.lang.Long r5 = java.lang.Long.valueOf(r5)     // Catch:{ NullPointerException -> 0x00c7 }
            com.google.android.libraries.places.internal.zzkc r2 = com.google.android.libraries.places.internal.zzkc.zzb(r2, r5)     // Catch:{ NullPointerException -> 0x00c7 }
            java.lang.Long r5 = java.lang.Long.valueOf(r16)     // Catch:{ NullPointerException -> 0x00c7 }
            boolean r2 = r2.zzd(r5)     // Catch:{ NullPointerException -> 0x00c7 }
            if (r2 == 0) goto L_0x00c8
            goto L_0x00cc
        L_0x00bd:
            throw r4     // Catch:{ NullPointerException -> 0x00c7 }
        L_0x00bf:
            java.util.NoSuchElementException r0 = new java.util.NoSuchElementException     // Catch:{ NullPointerException -> 0x00c7 }
            r0.<init>()     // Catch:{ NullPointerException -> 0x00c7 }
            throw r0     // Catch:{ NullPointerException -> 0x00c7 }
        L_0x00c5:
            throw r4     // Catch:{ NullPointerException -> 0x00c7 }
        L_0x00c7:
            r0 = move-exception
        L_0x00c8:
            com.google.android.libraries.places.api.model.OpeningHours r0 = r15.getOpeningHours()
        L_0x00cc:
            if (r0 != 0) goto L_0x00cf
            return r4
        L_0x00cf:
            java.util.List r0 = r0.getPeriods()
            boolean r2 = r0.isEmpty()
            if (r2 == 0) goto L_0x00da
            return r3
        L_0x00da:
            boolean r2 = zzf(r0)
            r5 = 1
            if (r2 == 0) goto L_0x00e6
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r5)
            return r0
        L_0x00e6:
            java.util.Iterator r2 = r0.iterator()
        L_0x00ea:
            boolean r6 = r2.hasNext()
            if (r6 == 0) goto L_0x0103
            java.lang.Object r6 = r2.next()
            com.google.android.libraries.places.api.model.Period r6 = (com.google.android.libraries.places.api.model.Period) r6
            com.google.android.libraries.places.api.model.TimeOfWeek r7 = r6.getOpen()
            if (r7 == 0) goto L_0x0102
            com.google.android.libraries.places.api.model.TimeOfWeek r6 = r6.getClose()
            if (r6 != 0) goto L_0x00ea
        L_0x0102:
            return r4
        L_0x0103:
            int r1 = r1.intValue()
            java.util.TimeZone r1 = zze(r1)
            if (r1 != 0) goto L_0x010e
            return r4
        L_0x010e:
            java.util.Calendar r1 = java.util.Calendar.getInstance(r1)
            r6 = r16
            r1.setTimeInMillis(r6)
            com.google.android.libraries.places.internal.zzjt r2 = zzb
            r4 = 7
            int r4 = r1.get(r4)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            java.lang.Object r2 = r2.get(r4)
            com.google.android.libraries.places.api.model.DayOfWeek r2 = (com.google.android.libraries.places.api.model.DayOfWeek) r2
            r4 = 11
            int r4 = r1.get(r4)
            r6 = 12
            int r1 = r1.get(r6)
            com.google.android.libraries.places.api.model.LocalTime r1 = com.google.android.libraries.places.api.model.LocalTime.newInstance(r4, r1)
            java.util.Map r0 = zzd(r0)
            java.lang.Object r0 = r0.get(r2)
            java.util.List r0 = (java.util.List) r0
            if (r0 != 0) goto L_0x0145
            return r3
        L_0x0145:
            java.util.Iterator r0 = r0.iterator()
        L_0x0149:
            boolean r2 = r0.hasNext()
            if (r2 == 0) goto L_0x0160
            java.lang.Object r2 = r0.next()
            com.google.android.libraries.places.internal.zzkc r2 = (com.google.android.libraries.places.internal.zzkc) r2
            boolean r2 = r2.zzd(r1)
            if (r2 == 0) goto L_0x0149
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r5)
            return r0
        L_0x0160:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.model.zzbq.zza(com.google.android.libraries.places.api.model.Place, long):java.lang.Boolean");
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0042  */
    @java.lang.Deprecated
    @androidx.annotation.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.Boolean zzb(com.google.android.libraries.places.api.model.Place r7, long r8) {
        /*
            com.google.android.libraries.places.api.model.Place$BusinessStatus r0 = r7.getBusinessStatus()
            com.google.android.libraries.places.api.model.OpeningHours r1 = r7.getOpeningHours()
            java.lang.Integer r7 = r7.getUtcOffsetMinutes()
            r2 = 0
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
            if (r0 == 0) goto L_0x001a
            com.google.android.libraries.places.api.model.Place$BusinessStatus r3 = com.google.android.libraries.places.api.model.Place.BusinessStatus.OPERATIONAL
            if (r0 != r3) goto L_0x0018
            goto L_0x001a
        L_0x0018:
            return r2
        L_0x001a:
            r0 = 0
            if (r1 == 0) goto L_0x00b2
            if (r7 != 0) goto L_0x0021
            goto L_0x00b2
        L_0x0021:
            java.util.List r1 = r1.getPeriods()
            boolean r3 = r1.isEmpty()
            if (r3 == 0) goto L_0x002c
            return r2
        L_0x002c:
            boolean r3 = zzf(r1)
            r4 = 1
            if (r3 == 0) goto L_0x0038
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r4)
            return r7
        L_0x0038:
            java.util.Iterator r3 = r1.iterator()
        L_0x003c:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x0055
            java.lang.Object r5 = r3.next()
            com.google.android.libraries.places.api.model.Period r5 = (com.google.android.libraries.places.api.model.Period) r5
            com.google.android.libraries.places.api.model.TimeOfWeek r6 = r5.getOpen()
            if (r6 == 0) goto L_0x0054
            com.google.android.libraries.places.api.model.TimeOfWeek r5 = r5.getClose()
            if (r5 != 0) goto L_0x003c
        L_0x0054:
            return r0
        L_0x0055:
            int r7 = r7.intValue()
            java.util.TimeZone r7 = zze(r7)
            if (r7 != 0) goto L_0x0060
            return r0
        L_0x0060:
            java.util.Calendar r7 = java.util.Calendar.getInstance(r7)
            r7.setTimeInMillis(r8)
            com.google.android.libraries.places.internal.zzjt r8 = zzb
            r9 = 7
            int r9 = r7.get(r9)
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
            java.lang.Object r8 = r8.get(r9)
            com.google.android.libraries.places.api.model.DayOfWeek r8 = (com.google.android.libraries.places.api.model.DayOfWeek) r8
            r9 = 11
            int r9 = r7.get(r9)
            r0 = 12
            int r7 = r7.get(r0)
            com.google.android.libraries.places.api.model.LocalTime r7 = com.google.android.libraries.places.api.model.LocalTime.newInstance(r9, r7)
            java.util.Map r9 = zzd(r1)
            java.lang.Object r8 = r9.get(r8)
            java.util.List r8 = (java.util.List) r8
            if (r8 != 0) goto L_0x0095
            return r2
        L_0x0095:
            java.util.Iterator r8 = r8.iterator()
        L_0x0099:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x00b0
            java.lang.Object r9 = r8.next()
            com.google.android.libraries.places.internal.zzkc r9 = (com.google.android.libraries.places.internal.zzkc) r9
            boolean r9 = r9.zzd(r7)
            if (r9 == 0) goto L_0x0099
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r4)
            return r7
        L_0x00b0:
            return r2
        L_0x00b2:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.libraries.places.api.model.zzbq.zzb(com.google.android.libraries.places.api.model.Place, long):java.lang.Boolean");
    }

    private static Object zzc(Map map, Object obj, Object obj2) {
        return map.containsKey(obj) ? map.get(obj) : obj2;
    }

    private static Map zzd(List list) {
        EnumMap enumMap = new EnumMap(DayOfWeek.class);
        if (list.isEmpty()) {
            return enumMap;
        }
        Period period = (Period) list.get(0);
        int i = 0;
        while (period != null) {
            TimeOfWeek open = period.getOpen();
            TimeOfWeek close = period.getClose();
            if (open == null || close == null) {
                i++;
                period = i >= list.size() ? null : (Period) list.get(i);
            } else {
                DayOfWeek day = open.getDay();
                LocalTime time = open.getTime();
                if (open.getDay() != close.getDay()) {
                    LocalTime localTime = zzc;
                    List list2 = (List) zzc(enumMap, day, new ArrayList());
                    list2.add(zzkc.zzb(time, localTime));
                    enumMap.put(day, list2);
                    TimeOfWeek newInstance = TimeOfWeek.newInstance(DayOfWeek.values()[(day.ordinal() + 1) % 7], LocalTime.newInstance(0, 0));
                    TimeOfWeek close2 = period.getClose();
                    Period.Builder builder = Period.builder();
                    builder.setOpen(newInstance);
                    builder.setClose(close2);
                    period = builder.build();
                } else {
                    LocalTime time2 = close.getTime();
                    List list3 = (List) zzc(enumMap, day, new ArrayList());
                    list3.add(zzkc.zzc(time, time2));
                    enumMap.put(day, list3);
                    i++;
                    period = i >= list.size() ? null : (Period) list.get(i);
                }
            }
        }
        return enumMap;
    }

    @Nullable
    private static TimeZone zze(int i) {
        String[] availableIDs = TimeZone.getAvailableIDs((int) TimeUnit.MINUTES.toMillis((long) i));
        if (availableIDs != null && availableIDs.length > 0) {
            return TimeZone.getTimeZone(availableIDs[0]);
        }
        Log.w("Places", String.format("Cannot find timezone that associates with utcOffsetMinutes %d from Place object.", new Object[]{Integer.valueOf(i)}));
        return null;
    }

    private static boolean zzf(List list) {
        if (list.size() != 1) {
            return false;
        }
        Period period = (Period) list.get(0);
        TimeOfWeek open = period.getOpen();
        return period.getClose() == null && open != null && open.getDay() == DayOfWeek.SUNDAY && open.getTime().getHours() == 0 && open.getTime().getMinutes() == 0;
    }
}
