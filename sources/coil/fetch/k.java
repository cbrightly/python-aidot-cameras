package coil.fetch;

import okhttp3.e;
import okhttp3.v;
import org.jetbrains.annotations.NotNull;

/* compiled from: HttpFetcher.kt */
public final class k extends i<v> {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public k(@NotNull e.a callFactory) {
        super(callFactory);
        kotlin.jvm.internal.k.e(callFactory, "callFactory");
    }

    @NotNull
    /* renamed from: g */
    public String c(@NotNull v data) {
        kotlin.jvm.internal.k.e(data, "data");
        String vVar = data.toString();
        kotlin.jvm.internal.k.d(vVar, "data.toString()");
        return vVar;
    }

    @NotNull
    /* renamed from: h */
    public v f(@NotNull v $this$toHttpUrl) {
        kotlin.jvm.internal.k.e($this$toHttpUrl, "<this>");
        return $this$toHttpUrl;
    }
}
