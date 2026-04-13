package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.internal.vision.zzao;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public class Element implements Text {
    private zzao zza;

    Element(zzao zzao) {
        this.zza = zzao;
    }

    @RecentlyNonNull
    public String getLanguage() {
        return this.zza.zzc;
    }

    @RecentlyNonNull
    public String getValue() {
        return this.zza.zzb;
    }

    @RecentlyNonNull
    public Rect getBoundingBox() {
        return zzc.zza((Text) this);
    }

    @RecentlyNonNull
    public Point[] getCornerPoints() {
        return zzc.zza(this.zza.zza);
    }

    @RecentlyNonNull
    public List<? extends Text> getComponents() {
        return new ArrayList();
    }
}
