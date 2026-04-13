package com.google.android.libraries.places.internal;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzfb {
    private final Gson zza = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    public final Object zza(String str, Class cls) {
        try {
            return this.zza.fromJson(str, cls);
        } catch (JsonSyntaxException e) {
            String name = cls.getName();
            throw new zzdx("Could not convert JSON string to " + name + " due to syntax errors.");
        }
    }
}
