package io.ktor.utils.io.core;

import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.utils.io.errors.a;
import kotlin.jvm.internal.k;
import kotlin.ranges.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: InputPeek.kt */
public final class z {
    public static /* synthetic */ int b(w wVar, c cVar, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 1;
        }
        if ((i4 & 8) != 0) {
            i3 = Integer.MAX_VALUE;
        }
        return a(wVar, cVar, i, i2, i3);
    }

    public static final int a(@NotNull w $this$peekTo, @NotNull c destination, int offset, int min, int max) {
        k.f($this$peekTo, "$this$peekTo");
        k.f(destination, FirebaseAnalytics.Param.DESTINATION);
        a.a(destination, offset, min, max);
        c this_$iv = destination;
        int copied = (int) $this$peekTo.n0(destination.n(), (long) destination.s(), (long) offset, (long) min, (long) n.e(max, this_$iv.m() - this_$iv.s()));
        destination.a(copied);
        return copied;
    }
}
