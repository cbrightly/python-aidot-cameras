package io.ktor.features;

import io.ktor.application.b;
import io.ktor.http.s;
import io.ktor.http.v;
import io.ktor.request.d;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;

/* compiled from: CallLogging.kt */
public final class e {
    @NotNull
    public static final String c(@NotNull d $this$toLogString) {
        k.f($this$toLogString, "$this$toLogString");
        return io.ktor.request.e.d($this$toLogString).i() + " - " + io.ktor.request.e.g($this$toLogString);
    }

    /* access modifiers changed from: private */
    public static final String b(b call) {
        Object status = call.b().b();
        if (status == null) {
            status = "Unhandled";
        }
        if (k.a(status, v.c0.j())) {
            return status + ": " + c(call.getRequest()) + " -> " + call.b().getHeaders().d(s.V0.w());
        }
        return status + ": " + c(call.getRequest());
    }
}
