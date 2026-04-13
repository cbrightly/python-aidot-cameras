package io.ktor.util.date;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DateJvm.kt */
public final class a {
    private static final TimeZone a = TimeZone.getTimeZone("GMT");

    @NotNull
    public static final c b(@Nullable Long timestamp) {
        Calendar instance = Calendar.getInstance(a, Locale.ROOT);
        if (instance == null) {
            k.n();
        }
        return c(instance, timestamp);
    }

    @NotNull
    public static final c a(int seconds, int minutes, int hours, int dayOfMonth, @NotNull d month, int year) {
        k.f(month, "month");
        Calendar instance = Calendar.getInstance(a, Locale.ROOT);
        if (instance == null) {
            k.n();
        }
        Calendar $this$apply = instance;
        $this$apply.set(1, year);
        $this$apply.set(2, month.ordinal());
        $this$apply.set(5, dayOfMonth);
        $this$apply.set(11, hours);
        $this$apply.set(12, minutes);
        $this$apply.set(13, seconds);
        $this$apply.set(14, 0);
        return c(instance, (Long) null);
    }

    @NotNull
    public static final c c(@NotNull Calendar $this$toDate, @Nullable Long timestamp) {
        Calendar calendar = $this$toDate;
        k.f(calendar, "$this$toDate");
        if (timestamp != null) {
            calendar.setTimeInMillis(timestamp.longValue());
        }
        int seconds = calendar.get(13);
        int minutes = calendar.get(12);
        int hours = calendar.get(11);
        e dayOfWeek = e.Companion.a(((calendar.get(7) + 7) - 2) % 7);
        int dayOfMonth = calendar.get(5);
        int dayOfYear = calendar.get(6);
        d month = d.Companion.a(calendar.get(2));
        return new c(seconds, minutes, hours, dayOfWeek, dayOfMonth, dayOfYear, month, calendar.get(1), $this$toDate.getTimeInMillis());
    }
}
