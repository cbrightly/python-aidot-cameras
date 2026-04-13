package io.ktor.http.cio;

import com.amazonaws.kinesisvideo.auth.DefaultAuthCallbacks;
import com.google.firebase.analytics.FirebaseAnalytics;
import io.ktor.http.u;
import io.ktor.utils.io.i;
import io.ktor.utils.io.j;
import io.ktor.utils.io.l;
import kotlin.coroutines.d;
import kotlin.coroutines.intrinsics.c;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: HttpBody.kt */
public final class e {
    public static final boolean b(@NotNull u method, @Nullable CharSequence upgrade, @Nullable d connectionOptions) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        return k.a(method, u.i.c()) && upgrade != null && connectionOptions != null && connectionOptions.f();
    }

    public static final boolean a(@NotNull u method, long contentLength, @Nullable CharSequence transferEncoding, @Nullable d connectionOptions, @Nullable CharSequence contentType) {
        k.f(method, FirebaseAnalytics.Param.METHOD);
        if (transferEncoding != null) {
            return true;
        }
        if (contentLength != -1) {
            if (contentLength > 0) {
                return true;
            }
            return false;
        } else if (contentType != null) {
            return true;
        } else {
            u.a aVar = u.i;
            if (k.a(method, aVar.c()) || k.a(method, aVar.d()) || k.a(method, aVar.e()) || connectionOptions == null || !connectionOptions.d()) {
                return false;
            }
            return true;
        }
    }

    @Nullable
    public static final Object c(long contentLength, @Nullable CharSequence transferEncoding, @Nullable d connectionOptions, @NotNull i input, @NotNull l out, @NotNull d<? super x> $completion) {
        if (transferEncoding != null) {
            if (io.ktor.http.cio.internals.d.b(transferEncoding, 0, 0, "chunked", 3, (Object) null)) {
                Object b = c.b(input, out, contentLength, $completion);
                return b == c.d() ? b : x.a;
            } else if (!io.ktor.http.cio.internals.d.b(transferEncoding, 0, 0, "identity", 3, (Object) null)) {
                out.d(new IllegalStateException("Unsupported transfer-encoding " + transferEncoding));
            }
        }
        if (contentLength != -1) {
            Object a = j.a(input, out, contentLength, $completion);
            if (a == c.d()) {
                return a;
            }
            return x.a;
        } else if (connectionOptions == null || !connectionOptions.d()) {
            out.d(new IllegalStateException("Failed to parse request body: request body length should be specified,\nchunked transfer encoding should be used or\nkeep-alive should be disabled (connection: close)"));
            return x.a;
        } else {
            Object a2 = j.a(input, out, DefaultAuthCallbacks.CREDENTIALS_NEVER_EXPIRE, $completion);
            if (a2 == c.d()) {
                return a2;
            }
            return x.a;
        }
    }
}
