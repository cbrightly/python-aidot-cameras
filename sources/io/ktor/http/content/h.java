package io.ktor.http.content;

import io.ktor.http.content.k;
import io.ktor.util.r;
import java.io.InputStream;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: MultipartJvm.kt */
public final class h {

    /* compiled from: MultipartJvm.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<InputStream> {
        final /* synthetic */ k.a $this_streamProvider;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(k.a aVar) {
            super(0);
            this.$this_streamProvider = aVar;
        }

        @NotNull
        public final InputStream invoke() {
            return r.a(this.$this_streamProvider.f().invoke());
        }
    }

    @NotNull
    public static final kotlin.jvm.functions.a<InputStream> a(@NotNull k.a $this$streamProvider) {
        kotlin.jvm.internal.k.f($this$streamProvider, "$this$streamProvider");
        return new a($this$streamProvider);
    }
}
