package io.ktor.utils.io.core;

import java.io.EOFException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: Buffer.kt */
public final class g {
    @NotNull
    public static final Void b(int count, int readRemaining) {
        throw new EOFException("Unable to discard " + count + " bytes: only " + readRemaining + " available for reading");
    }

    @NotNull
    public static final Void a(int count, int writeRemaining) {
        throw new EOFException("Unable to discard " + count + " bytes: only " + writeRemaining + " available for writing");
    }

    @NotNull
    public static final Void h(@NotNull c $this$startGapReservationFailedDueToLimit, int startGap) {
        k.f($this$startGapReservationFailedDueToLimit, "$this$startGapReservationFailedDueToLimit");
        if (startGap > $this$startGapReservationFailedDueToLimit.l()) {
            throw new IllegalArgumentException("Start gap " + startGap + " is bigger than the capacity " + $this$startGapReservationFailedDueToLimit.l());
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to reserve ");
        sb.append(startGap);
        sb.append(" start gap: there are already ");
        c this_$iv = $this$startGapReservationFailedDueToLimit;
        sb.append(this_$iv.l() - this_$iv.m());
        sb.append(" bytes reserved in the end");
        throw new IllegalStateException(sb.toString());
    }

    @NotNull
    public static final Void g(@NotNull c $this$startGapReservationFailed, int startGap) {
        k.f($this$startGapReservationFailed, "$this$startGapReservationFailed");
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to reserve ");
        sb.append(startGap);
        sb.append(" start gap: ");
        sb.append("there are already ");
        c this_$iv = $this$startGapReservationFailed;
        sb.append(this_$iv.s() - this_$iv.o());
        sb.append(" content bytes starting at offset ");
        sb.append($this$startGapReservationFailed.o());
        throw new IllegalStateException(sb.toString());
    }

    public static final void c(@NotNull c $this$endGapReservationFailedDueToCapacity, int endGap) {
        k.f($this$endGapReservationFailedDueToCapacity, "$this$endGapReservationFailedDueToCapacity");
        throw new IllegalArgumentException("End gap " + endGap + " is too big: capacity is " + $this$endGapReservationFailedDueToCapacity.l());
    }

    public static final void e(@NotNull c $this$endGapReservationFailedDueToStartGap, int endGap) {
        k.f($this$endGapReservationFailedDueToStartGap, "$this$endGapReservationFailedDueToStartGap");
        throw new IllegalArgumentException("End gap " + endGap + " is too big: there are already " + $this$endGapReservationFailedDueToStartGap.r() + " bytes reserved in the beginning");
    }

    public static final void d(@NotNull c $this$endGapReservationFailedDueToContent, int endGap) {
        k.f($this$endGapReservationFailedDueToContent, "$this$endGapReservationFailedDueToContent");
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to reserve end gap ");
        sb.append(endGap);
        sb.append(':');
        sb.append(" there are already ");
        c this_$iv = $this$endGapReservationFailedDueToContent;
        sb.append(this_$iv.s() - this_$iv.o());
        sb.append(" content bytes at offset ");
        sb.append($this$endGapReservationFailedDueToContent.o());
        throw new IllegalArgumentException(sb.toString());
    }

    public static final void f(@NotNull c $this$restoreStartGap, int size) {
        k.f($this$restoreStartGap, "$this$restoreStartGap");
        $this$restoreStartGap.v($this$restoreStartGap.o() - size);
    }
}
