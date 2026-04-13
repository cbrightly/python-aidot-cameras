package zendesk.android.internal;

import com.didichuxing.doraemonkit.kit.loginfo.helper.LogcatHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlinx.coroutines.d1;
import kotlinx.coroutines.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ZendeskDispatchers.kt */
public final class h {
    @NotNull
    private final i0 a;
    @NotNull
    private final i0 b;
    @NotNull
    private final i0 c;

    public h() {
        this((i0) null, (i0) null, (i0) null, 7, (DefaultConstructorMarker) null);
    }

    public h(@NotNull i0 main, @NotNull i0 io2, @NotNull i0 i0Var) {
        k.e(main, LogcatHelper.BUFFER_MAIN);
        k.e(io2, "io");
        k.e(i0Var, "default");
        this.a = main;
        this.b = io2;
        this.c = i0Var;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ h(i0 i0Var, i0 i0Var2, i0 i0Var3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? d1.c() : i0Var, (i & 2) != 0 ? d1.b() : i0Var2, (i & 4) != 0 ? d1.a() : i0Var3);
    }
}
