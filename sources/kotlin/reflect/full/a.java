package kotlin.reflect.full;

import java.util.Iterator;
import kotlin.jvm.internal.k;
import kotlin.reflect.b;
import kotlin.reflect.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: KCallables.kt */
public final class a {
    @Nullable
    public static final j a(@NotNull b<?> $this$extensionReceiverParameter) {
        k.f($this$extensionReceiverParameter, "$this$extensionReceiverParameter");
        Object single$iv = null;
        boolean found$iv = false;
        Iterator<T> it = $this$extensionReceiverParameter.getParameters().iterator();
        while (true) {
            if (it.hasNext()) {
                Object element$iv = it.next();
                if (((j) element$iv).h() == j.a.EXTENSION_RECEIVER) {
                    if (found$iv) {
                        single$iv = null;
                        break;
                    }
                    single$iv = element$iv;
                    found$iv = true;
                }
            } else if (!found$iv) {
                single$iv = null;
            }
        }
        return (j) single$iv;
    }
}
