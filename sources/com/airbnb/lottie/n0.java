package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import com.airbnb.lottie.utils.f;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: PerformanceTracker */
public class n0 {
    private boolean a = false;
    private final Set<b> b = new ArraySet();
    private final Map<String, f> c = new HashMap();
    private final Comparator<Pair<String, Float>> d = new a();

    /* compiled from: PerformanceTracker */
    public interface b {
        void a(float f);
    }

    /* compiled from: PerformanceTracker */
    public class a implements Comparator<Pair<String, Float>> {
        a() {
        }

        /* renamed from: a */
        public int compare(Pair<String, Float> o1, Pair<String, Float> o2) {
            float r1 = ((Float) o1.second).floatValue();
            float r2 = ((Float) o2.second).floatValue();
            if (r2 > r1) {
                return 1;
            }
            if (r1 > r2) {
                return -1;
            }
            return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public void b(boolean enabled) {
        this.a = enabled;
    }

    public void a(String layerName, float millis) {
        if (this.a) {
            f meanCalculator = this.c.get(layerName);
            if (meanCalculator == null) {
                meanCalculator = new f();
                this.c.put(layerName, meanCalculator);
            }
            meanCalculator.a(millis);
            if (layerName.equals("__container")) {
                for (b listener : this.b) {
                    listener.a(millis);
                }
            }
        }
    }
}
