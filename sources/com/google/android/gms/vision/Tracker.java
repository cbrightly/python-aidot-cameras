package com.google.android.gms.vision;

import androidx.annotation.RecentlyNonNull;
import com.google.android.gms.vision.Detector;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.1.3 */
public class Tracker<T> {
    public void onNewItem(int i, @RecentlyNonNull T t) {
    }

    public void onUpdate(@RecentlyNonNull Detector.Detections<T> detections, @RecentlyNonNull T t) {
    }

    public void onMissing(@RecentlyNonNull Detector.Detections<T> detections) {
    }

    public void onDone() {
    }
}
