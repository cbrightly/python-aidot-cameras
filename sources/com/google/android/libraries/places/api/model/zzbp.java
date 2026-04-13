package com.google.android.libraries.places.api.model;

import java.util.Comparator;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final /* synthetic */ class zzbp implements Comparator {
    public static final /* synthetic */ zzbp zza = new zzbp();

    private /* synthetic */ zzbp() {
    }

    public final int compare(Object obj, Object obj2) {
        Period period = (Period) obj2;
        int i = zzbq.zza;
        TimeOfWeek open = ((Period) obj).getOpen();
        if (open != null) {
            TimeOfWeek open2 = period.getOpen();
            if (open2 != null) {
                LocalDate date = open.getDate();
                if (date != null) {
                    LocalDate date2 = open2.getDate();
                    if (date2 != null) {
                        return date.compareTo(date2);
                    }
                    throw null;
                }
                throw null;
            }
            throw null;
        }
        throw null;
    }
}
