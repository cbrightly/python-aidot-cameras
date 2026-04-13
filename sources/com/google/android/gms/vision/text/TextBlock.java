package com.google.android.gms.vision.text;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.SparseArray;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.internal.vision.zzab;
import com.google.android.gms.internal.vision.zzah;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: com.google.android.gms:play-services-vision@@20.1.3 */
public class TextBlock implements Text {
    private zzah[] zza;
    private Point[] zzb;
    private List<Line> zzc;
    private String zzd;
    private Rect zze;

    TextBlock(SparseArray<zzah> sparseArray) {
        this.zza = new zzah[sparseArray.size()];
        int i = 0;
        while (true) {
            zzah[] zzahArr = this.zza;
            if (i < zzahArr.length) {
                zzahArr[i] = sparseArray.valueAt(i);
                i++;
            } else {
                return;
            }
        }
    }

    @RecentlyNonNull
    public String getLanguage() {
        int i;
        String str = this.zzd;
        if (str != null) {
            return str;
        }
        HashMap hashMap = new HashMap();
        for (zzah zzah : this.zza) {
            if (hashMap.containsKey(zzah.zzd)) {
                i = ((Integer) hashMap.get(zzah.zzd)).intValue();
            } else {
                i = 0;
            }
            hashMap.put(zzah.zzd, Integer.valueOf(i + 1));
        }
        String str2 = (String) ((Map.Entry) Collections.max(hashMap.entrySet(), new zza(this))).getKey();
        this.zzd = str2;
        if (str2 == null || str2.isEmpty()) {
            this.zzd = "und";
        }
        return this.zzd;
    }

    @RecentlyNonNull
    public String getValue() {
        zzah[] zzahArr = this.zza;
        if (zzahArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(zzahArr[0].zzc);
        for (int i = 1; i < this.zza.length; i++) {
            sb.append("\n");
            sb.append(this.zza[i].zzc);
        }
        return sb.toString();
    }

    @RecentlyNonNull
    public Point[] getCornerPoints() {
        zzah[] zzahArr;
        TextBlock textBlock = this;
        if (textBlock.zzb == null) {
            char c = 0;
            if (textBlock.zza.length == 0) {
                textBlock.zzb = new Point[0];
            } else {
                int i = Integer.MIN_VALUE;
                int i2 = 0;
                int i3 = Integer.MAX_VALUE;
                int i4 = Integer.MAX_VALUE;
                int i5 = Integer.MIN_VALUE;
                while (true) {
                    zzahArr = textBlock.zza;
                    if (i2 >= zzahArr.length) {
                        break;
                    }
                    zzab zzab = zzahArr[i2].zzb;
                    zzab zzab2 = zzahArr[c].zzb;
                    double sin = Math.sin(Math.toRadians((double) zzab2.zze));
                    double cos = Math.cos(Math.toRadians((double) zzab2.zze));
                    Point[] pointArr = new Point[4];
                    pointArr[c] = new Point(zzab.zza, zzab.zzb);
                    pointArr[c].offset(-zzab2.zza, -zzab2.zzb);
                    int i6 = i5;
                    int i7 = (int) ((((double) pointArr[c].x) * cos) + (((double) pointArr[c].y) * sin));
                    int i8 = (int) ((((double) (-pointArr[0].x)) * sin) + (((double) pointArr[0].y) * cos));
                    pointArr[0].x = i7;
                    pointArr[0].y = i8;
                    pointArr[1] = new Point(zzab.zzc + i7, i8);
                    pointArr[2] = new Point(zzab.zzc + i7, zzab.zzd + i8);
                    pointArr[3] = new Point(i7, i8 + zzab.zzd);
                    i5 = i6;
                    for (int i9 = 0; i9 < 4; i9++) {
                        Point point = pointArr[i9];
                        i3 = Math.min(i3, point.x);
                        i = Math.max(i, point.x);
                        i4 = Math.min(i4, point.y);
                        i5 = Math.max(i5, point.y);
                    }
                    i2++;
                    c = 0;
                    textBlock = this;
                }
                int i10 = i5;
                zzab zzab3 = zzahArr[0].zzb;
                int i11 = zzab3.zza;
                int i12 = zzab3.zzb;
                double sin2 = Math.sin(Math.toRadians((double) zzab3.zze));
                double cos2 = Math.cos(Math.toRadians((double) zzab3.zze));
                int i13 = i10;
                Point[] pointArr2 = {new Point(i3, i4), new Point(i, i4), new Point(i, i13), new Point(i3, i13)};
                for (int i14 = 0; i14 < 4; i14++) {
                    pointArr2[i14].x = (int) ((((double) pointArr2[i14].x) * cos2) - (((double) pointArr2[i14].y) * sin2));
                    pointArr2[i14].y = (int) ((((double) pointArr2[i14].x) * sin2) + (((double) pointArr2[i14].y) * cos2));
                    pointArr2[i14].offset(i11, i12);
                }
                textBlock = this;
                textBlock.zzb = pointArr2;
            }
        }
        return textBlock.zzb;
    }

    @RecentlyNonNull
    public List<? extends Text> getComponents() {
        if (this.zza.length == 0) {
            return new ArrayList(0);
        }
        if (this.zzc == null) {
            this.zzc = new ArrayList(this.zza.length);
            for (zzah line : this.zza) {
                this.zzc.add(new Line(line));
            }
        }
        return this.zzc;
    }

    @RecentlyNonNull
    public Rect getBoundingBox() {
        if (this.zze == null) {
            this.zze = zzc.zza((Text) this);
        }
        return this.zze;
    }
}
