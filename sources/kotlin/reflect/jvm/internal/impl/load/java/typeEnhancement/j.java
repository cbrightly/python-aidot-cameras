package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.List;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: predefinedEnhancementInfo.kt */
public final class j {
    @Nullable
    private final r a;
    @NotNull
    private final List<r> b;

    public j() {
        this((r) null, (List) null, 3, (DefaultConstructorMarker) null);
    }

    public j(@Nullable r returnTypeInfo, @NotNull List<r> parametersInfo) {
        k.f(parametersInfo, "parametersInfo");
        this.a = returnTypeInfo;
        this.b = parametersInfo;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ j(r rVar, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : rVar, (i & 2) != 0 ? q.g() : list);
    }

    @Nullable
    public final r b() {
        return this.a;
    }

    @NotNull
    public final List<r> a() {
        return this.b;
    }
}
