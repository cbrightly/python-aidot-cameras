package com.google.android.gms.vision;

import android.util.SparseArray;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.vision.Frame;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public abstract class Detector<T> {
    private final Object zza = new Object();
    @GuardedBy("processorLock")
    @Nullable
    private Processor<T> zzb;

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public interface Processor<T> {
        void receiveDetections(@RecentlyNonNull Detections<T> detections);

        void release();
    }

    @RecentlyNonNull
    public abstract SparseArray<T> detect(@RecentlyNonNull Frame frame);

    public void release() {
        synchronized (this.zza) {
            Processor<T> processor = this.zzb;
            if (processor != null) {
                processor.release();
                this.zzb = null;
            }
        }
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class Detections<T> {
        private final SparseArray<T> zza;
        private final Frame.Metadata zzb;
        private final boolean zzc;

        public Detections(@RecentlyNonNull SparseArray<T> sparseArray, @RecentlyNonNull Frame.Metadata metadata, boolean z) {
            this.zza = sparseArray;
            this.zzb = metadata;
            this.zzc = z;
        }

        @RecentlyNonNull
        public SparseArray<T> getDetectedItems() {
            return this.zza;
        }

        @RecentlyNonNull
        public Frame.Metadata getFrameMetadata() {
            return this.zzb;
        }

        public boolean detectorIsOperational() {
            return this.zzc;
        }
    }

    public boolean setFocus(int i) {
        return true;
    }

    public void receiveFrame(@RecentlyNonNull Frame frame) {
        Frame.Metadata metadata = new Frame.Metadata(frame.getMetadata());
        metadata.zza();
        Detections detections = new Detections(detect(frame), metadata, isOperational());
        synchronized (this.zza) {
            Processor<T> processor = this.zzb;
            if (processor != null) {
                processor.receiveDetections(detections);
            } else {
                throw new IllegalStateException("Detector processor must first be set with setProcessor in order to receive detection results.");
            }
        }
    }

    public void setProcessor(@RecentlyNonNull Processor<T> processor) {
        synchronized (this.zza) {
            Processor<T> processor2 = this.zzb;
            if (processor2 != null) {
                processor2.release();
            }
            this.zzb = processor;
        }
    }

    public boolean isOperational() {
        return true;
    }
}
