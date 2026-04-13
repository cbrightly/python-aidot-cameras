package kotlin.reflect.jvm.internal.impl.types.checker;

import kotlin.jvm.internal.a0;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassicTypeCheckerContext.kt */
public final class b {
    /* access modifiers changed from: private */
    public static final String b(@NotNull Object $this$errorMessage) {
        return "ClassicTypeCheckerContext couldn't handle " + a0.b($this$errorMessage.getClass()) + ' ' + $this$errorMessage;
    }
}
