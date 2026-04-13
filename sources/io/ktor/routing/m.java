package io.ktor.routing;

import androidx.core.app.NotificationCompat;
import com.google.android.libraries.places.api.model.PlaceTypes;
import io.ktor.application.b;
import io.ktor.http.y;
import io.ktor.http.z;
import io.ktor.response.d;
import kotlin.g;
import kotlin.i;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import org.jetbrains.annotations.NotNull;

/* compiled from: RoutingApplicationCall.kt */
public final class m implements b {
    @NotNull
    private final n a;
    @NotNull
    private final o b;
    @NotNull
    private final g c;
    /* access modifiers changed from: private */
    public final b d;
    @NotNull
    private final i e;

    @NotNull
    public y getParameters() {
        return (y) this.c.getValue();
    }

    public m(@NotNull b call, @NotNull i route, @NotNull io.ktor.request.b receivePipeline, @NotNull d responsePipeline, @NotNull y parameters) {
        k.f(call, NotificationCompat.CATEGORY_CALL);
        k.f(route, PlaceTypes.ROUTE);
        k.f(receivePipeline, "receivePipeline");
        k.f(responsePipeline, "responsePipeline");
        k.f(parameters, "parameters");
        this.d = call;
        this.e = route;
        this.a = new n(this, receivePipeline, call.getRequest());
        this.b = new o(this, responsePipeline, call.b());
        this.c = i.a(kotlin.k.NONE, new a(this, parameters));
    }

    @NotNull
    public io.ktor.application.a a() {
        return this.d.a();
    }

    @NotNull
    public io.ktor.util.b getAttributes() {
        return this.d.getAttributes();
    }

    @NotNull
    /* renamed from: d */
    public n getRequest() {
        return this.a;
    }

    @NotNull
    /* renamed from: e */
    public o b() {
        return this.b;
    }

    /* compiled from: RoutingApplicationCall.kt */
    public static final class a extends l implements kotlin.jvm.functions.a<y> {
        final /* synthetic */ y $parameters;
        final /* synthetic */ m this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(m mVar, y yVar) {
            super(0);
            this.this$0 = mVar;
            this.$parameters = yVar;
        }

        @NotNull
        public final y invoke() {
            y.a aVar = y.b;
            z zVar = new z(0, 1, (DefaultConstructorMarker) null);
            z $this$build = zVar;
            $this$build.b(this.this$0.d.getParameters());
            $this$build.d(this.$parameters);
            return zVar.m();
        }
    }

    @NotNull
    public String toString() {
        return "RoutingApplicationCall(route=" + this.e + ')';
    }
}
