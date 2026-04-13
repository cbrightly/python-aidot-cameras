package com.google.android.gms.vision;

import android.util.SparseArray;
import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.vision.Detector;
import java.util.ArrayList;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class MultiDetector extends Detector<Object> {
    /* access modifiers changed from: private */
    public List<Detector<? extends Object>> zza;

    public void release() {
        for (Detector<? extends Object> release : this.zza) {
            release.release();
        }
        this.zza.clear();
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
    public static class Builder {
        private MultiDetector zza = new MultiDetector();

        @RecentlyNonNull
        public Builder add(@RecentlyNonNull Detector<? extends Object> detector) {
            this.zza.zza.add(detector);
            return this;
        }

        @RecentlyNonNull
        public MultiDetector build() {
            if (this.zza.zza.size() != 0) {
                return this.zza;
            }
            throw new RuntimeException("No underlying detectors added to MultiDetector.");
        }
    }

    @RecentlyNonNull
    public SparseArray<Object> detect(@RecentlyNonNull Frame frame) {
        SparseArray<Object> sparseArray = new SparseArray<>();
        for (Detector<? extends Object> detect : this.zza) {
            SparseArray detect2 = detect.detect(frame);
            int i = 0;
            while (true) {
                if (i < detect2.size()) {
                    int keyAt = detect2.keyAt(i);
                    if (sparseArray.get(keyAt) == null) {
                        sparseArray.append(keyAt, detect2.valueAt(i));
                        i++;
                    } else {
                        StringBuilder sb = new StringBuilder(104);
                        sb.append("Detection ID overlap for id = ");
                        sb.append(keyAt);
                        sb.append("  This means that one of the detectors is not using global IDs.");
                        throw new IllegalStateException(sb.toString());
                    }
                }
            }
        }
        return sparseArray;
    }

    public void receiveFrame(@RecentlyNonNull Frame frame) {
        for (Detector<? extends Object> receiveFrame : this.zza) {
            receiveFrame.receiveFrame(frame);
        }
    }

    public void setProcessor(@RecentlyNonNull Detector.Processor<Object> processor) {
        throw new UnsupportedOperationException("MultiDetector.setProcessor is not supported.  You should set a processor instance on each underlying detector instead.");
    }

    public boolean isOperational() {
        for (Detector<? extends Object> isOperational : this.zza) {
            if (!isOperational.isOperational()) {
                return false;
            }
        }
        return true;
    }

    private MultiDetector() {
        this.zza = new ArrayList();
    }
}
