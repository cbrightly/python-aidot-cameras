package com.google.android.gms.vision;

import android.util.SparseArray;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.vision.Detector;
import java.util.HashSet;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class MultiProcessor<T> implements Detector.Processor<T> {
    /* access modifiers changed from: private */
    public Factory<T> zza;
    private SparseArray<zza> zzb;
    /* access modifiers changed from: private */
    public int zzc;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public interface Factory<T> {
        @RecentlyNonNull
        Tracker<T> create(@RecentlyNonNull T t);
    }

    public void release() {
        for (int i = 0; i < this.zzb.size(); i++) {
            this.zzb.valueAt(i).zza.onDone();
        }
        this.zzb.clear();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public final class zza {
        /* access modifiers changed from: private */
        public Tracker<T> zza;
        /* access modifiers changed from: private */
        public int zzb;

        private zza(MultiProcessor multiProcessor) {
            this.zzb = 0;
        }

        static /* synthetic */ int zzb(zza zza2) {
            int i = zza2.zzb;
            zza2.zzb = i + 1;
            return i;
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class Builder<T> {
        private MultiProcessor<T> zza;

        public Builder(@RecentlyNonNull Factory<T> factory) {
            MultiProcessor<T> multiProcessor = new MultiProcessor<>();
            this.zza = multiProcessor;
            if (factory != null) {
                Factory unused = multiProcessor.zza = factory;
                return;
            }
            throw new IllegalArgumentException("No factory supplied.");
        }

        @RecentlyNonNull
        public Builder<T> setMaxGapFrames(int i) {
            if (i >= 0) {
                int unused = this.zza.zzc = i;
                return this;
            }
            StringBuilder sb = new StringBuilder(28);
            sb.append("Invalid max gap: ");
            sb.append(i);
            throw new IllegalArgumentException(sb.toString());
        }

        @RecentlyNonNull
        public MultiProcessor<T> build() {
            return this.zza;
        }
    }

    public void receiveDetections(@RecentlyNonNull Detector.Detections<T> detections) {
        SparseArray<T> detectedItems = detections.getDetectedItems();
        for (int i = 0; i < detectedItems.size(); i++) {
            int keyAt = detectedItems.keyAt(i);
            T valueAt = detectedItems.valueAt(i);
            if (this.zzb.get(keyAt) == null) {
                zza zza2 = new zza();
                Tracker unused = zza2.zza = this.zza.create(valueAt);
                zza2.zza.onNewItem(keyAt, valueAt);
                this.zzb.append(keyAt, zza2);
            }
        }
        SparseArray<T> detectedItems2 = detections.getDetectedItems();
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i2 = 0; i2 < this.zzb.size(); i2++) {
            int keyAt2 = this.zzb.keyAt(i2);
            if (detectedItems2.get(keyAt2) == null) {
                zza valueAt2 = this.zzb.valueAt(i2);
                zza.zzb(valueAt2);
                if (valueAt2.zzb >= this.zzc) {
                    valueAt2.zza.onDone();
                    hashSet.add(Integer.valueOf(keyAt2));
                } else {
                    valueAt2.zza.onMissing(detections);
                }
            }
        }
        for (Integer intValue : hashSet) {
            this.zzb.delete(intValue.intValue());
        }
        SparseArray<T> detectedItems3 = detections.getDetectedItems();
        for (int i3 = 0; i3 < detectedItems3.size(); i3++) {
            int keyAt3 = detectedItems3.keyAt(i3);
            T valueAt3 = detectedItems3.valueAt(i3);
            zza zza3 = this.zzb.get(keyAt3);
            int unused2 = zza3.zzb = 0;
            zza3.zza.onUpdate(detections, valueAt3);
        }
    }

    private MultiProcessor() {
        this.zzb = new SparseArray<>();
        this.zzc = 3;
    }
}
