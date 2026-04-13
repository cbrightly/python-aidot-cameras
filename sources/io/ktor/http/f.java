package io.ktor.http;

import io.ktor.util.date.c;
import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.k;
import kotlin.text.x;
import org.jetbrains.annotations.NotNull;

/* compiled from: DateUtils.kt */
public final class f {
    private static final List<String> a = q.j("***, dd MMM YYYY hh:mm:ss zzz", "****, dd-MMM-YYYY hh:mm:ss zzz", "*** MMM d hh:mm:ss YYYY", "***, dd-MMM-YYYY hh:mm:ss zzz", "***, dd-MMM-YYYY hh-mm-ss zzz", "***, dd MMM YYYY hh:mm:ss zzz", "*** dd-MMM-YYYY hh:mm:ss zzz", "*** dd MMM YYYY hh:mm:ss zzz", "*** dd-MMM-YYYY hh-mm-ss zzz", "***,dd-MMM-YYYY hh:mm:ss zzz", "*** MMM d YYYY hh:mm:ss zzz");

    @NotNull
    public static final String b(@NotNull c $this$toHttpDate) {
        k.f($this$toHttpDate, "$this$toHttpDate");
        StringBuilder sb = new StringBuilder();
        StringBuilder $this$buildString = sb;
        $this$buildString.append($this$toHttpDate.c().getValue() + ", ");
        $this$buildString.append(a($this$toHttpDate.b(), 2) + ' ');
        $this$buildString.append($this$toHttpDate.f().getValue() + ' ');
        $this$buildString.append(a($this$toHttpDate.j(), 4));
        $this$buildString.append(' ' + a($this$toHttpDate.d(), 2) + ':' + a($this$toHttpDate.e(), 2) + ':' + a($this$toHttpDate.h(), 2) + ' ');
        $this$buildString.append("GMT");
        String sb2 = sb.toString();
        k.b(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }

    private static final String a(int $this$padZero, int length) {
        return x.q0(String.valueOf($this$padZero), length, '0');
    }
}
