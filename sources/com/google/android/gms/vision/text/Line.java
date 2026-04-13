package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.internal.vision.zzah;
import com.google.android.gms.internal.vision.zzao;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public class Line implements Text {
    private zzah zza;
    private List<Element> zzb;

    Line(zzah zzah) {
        this.zza = zzah;
    }

    @RecentlyNonNull
    public String getLanguage() {
        return this.zza.zzd;
    }

    @RecentlyNonNull
    public String getValue() {
        return this.zza.zzc;
    }

    @RecentlyNonNull
    public Rect getBoundingBox() {
        return zzc.zza((Text) this);
    }

    @RecentlyNonNull
    public Point[] getCornerPoints() {
        return zzc.zza(this.zza.zzb);
    }

    @RecentlyNonNull
    public List<? extends Text> getComponents() {
        if (this.zza.zza.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzb == null) {
            this.zzb = new ArrayList(this.zza.zza.length);
            for (zzao element : this.zza.zza) {
                this.zzb.add(new Element(element));
            }
        }
        return this.zzb;
    }

    public float getAngle() {
        return this.zza.zzb.zze;
    }

    public boolean isVertical() {
        return this.zza.zze;
    }
}
