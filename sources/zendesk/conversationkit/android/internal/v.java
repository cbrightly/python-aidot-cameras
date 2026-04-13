package zendesk.conversationkit.android.internal;

import java.util.ArrayList;
import java.util.List;
import kotlin.collections.y;
import kotlin.jvm.functions.a;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import zendesk.conversationkit.android.d;

/* compiled from: EffectMapper.kt */
public final class v {
    @NotNull
    private final List<d> a = new ArrayList();

    public v(@NotNull l<? super v, x> block) {
        k.e(block, "block");
        block.invoke(this);
    }

    public final void b(@NotNull a<? extends d> block) {
        k.e(block, "block");
        this.a.add(block.invoke());
    }

    public final <T> void a(@Nullable T data, @NotNull l<? super T, ? extends d> block) {
        k.e(block, "block");
        List<d> list = this.a;
        if (data != null) {
            list.add(block.invoke(data));
        }
    }

    @NotNull
    public final List<d> c() {
        return y.C0(this.a);
    }
}
