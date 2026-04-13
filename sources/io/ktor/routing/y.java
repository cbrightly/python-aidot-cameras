package io.ktor.routing;

import com.google.android.libraries.places.api.model.PlaceTypes;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: RoutingResolveTrace.kt */
public class y {
    private List<y> a;
    @NotNull
    private final i b;
    private final int c;
    @Nullable
    private w d;

    public y(@NotNull i route, int segmentIndex, @Nullable w result) {
        k.f(route, PlaceTypes.ROUTE);
        this.b = route;
        this.c = segmentIndex;
        this.d = result;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ y(i iVar, int i, w wVar, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(iVar, i, (i2 & 4) != 0 ? null : wVar);
    }

    @NotNull
    public final i b() {
        return this.b;
    }

    public final int c() {
        return this.c;
    }

    public final void d(@Nullable w wVar) {
        this.d = wVar;
    }

    public final void a(@NotNull y item) {
        k.f(item, "item");
        List items = this.a;
        if (items == null) {
            items = new ArrayList();
            this.a = items;
        }
        items.add(item);
    }

    @NotNull
    public String toString() {
        return this.b + ", segment:" + this.c + " -> " + this.d;
    }
}
