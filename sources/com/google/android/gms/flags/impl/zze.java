package com.google.android.gms.flags.impl;

import android.content.SharedPreferences;
import java.util.concurrent.Callable;

public final class zze implements Callable<Integer> {
    private final /* synthetic */ SharedPreferences zzo;
    private final /* synthetic */ String zzp;
    private final /* synthetic */ Integer zzr;

    zze(SharedPreferences sharedPreferences, String str, Integer num) {
        this.zzo = sharedPreferences;
        this.zzp = str;
        this.zzr = num;
    }

    public final /* synthetic */ Object call() {
        return Integer.valueOf(this.zzo.getInt(this.zzp, this.zzr.intValue()));
    }
}
