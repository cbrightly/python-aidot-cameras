package com.airbnb.lottie;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.os.TraceCompat;
import com.airbnb.lottie.network.b;
import com.airbnb.lottie.network.e;
import com.airbnb.lottie.network.f;
import com.airbnb.lottie.network.g;
import com.airbnb.lottie.network.h;
import java.io.File;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: L */
public class b0 {
    public static boolean a = false;
    private static boolean b = false;
    private static boolean c = true;
    private static boolean d = true;
    private static String[] e;
    private static long[] f;
    private static int g = 0;
    private static int h = 0;
    private static f i;
    private static e j;
    private static volatile h k;
    private static volatile g l;

    public static void a(String section) {
        if (b) {
            int i2 = g;
            if (i2 == 20) {
                h++;
                return;
            }
            e[i2] = section;
            f[i2] = System.nanoTime();
            TraceCompat.beginSection(section);
            g++;
        }
    }

    public static float b(String section) {
        int i2 = h;
        if (i2 > 0) {
            h = i2 - 1;
            return 0.0f;
        } else if (!b) {
            return 0.0f;
        } else {
            int i3 = g - 1;
            g = i3;
            if (i3 == -1) {
                throw new IllegalStateException("Can't end trace section. There are none.");
            } else if (section.equals(e[i3])) {
                TraceCompat.endSection();
                return ((float) (System.nanoTime() - f[g])) / 1000000.0f;
            } else {
                throw new IllegalStateException("Unbalanced trace call " + section + ". Expected " + e[g] + ".");
            }
        }
    }

    @NonNull
    public static h e(@NonNull Context context) {
        h local = k;
        if (local == null) {
            synchronized (h.class) {
                local = k;
                if (local == null) {
                    g d2 = d(context);
                    f fVar = i;
                    if (fVar == null) {
                        fVar = new b();
                    }
                    h hVar = new h(d2, fVar);
                    local = hVar;
                    k = hVar;
                }
            }
        }
        return local;
    }

    @Nullable
    public static g d(@NonNull Context context) {
        if (!c) {
            return null;
        }
        Context appContext = context.getApplicationContext();
        g local = l;
        if (local == null) {
            synchronized (g.class) {
                local = l;
                if (local == null) {
                    e eVar = j;
                    if (eVar == null) {
                        eVar = new a(appContext);
                    }
                    g gVar = new g(eVar);
                    local = gVar;
                    l = gVar;
                }
            }
        }
        return local;
    }

    /* compiled from: L */
    public class a implements e {
        final /* synthetic */ Context a;

        a(Context context) {
            this.a = context;
        }

        @NonNull
        public File a() {
            return new File(this.a.getCacheDir(), "lottie_network_cache");
        }
    }

    public static boolean c() {
        return d;
    }
}
