package kotlin.reflect.jvm.internal.impl.util;

import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import org.jetbrains.annotations.NotNull;

/* compiled from: ModuleVisibilityHelper.kt */
public interface g {
    boolean a(@NotNull m mVar, @NotNull m mVar2);

    /* compiled from: ModuleVisibilityHelper.kt */
    public static final class a implements g {
        public static final a a = new a();

        private a() {
        }

        public boolean a(@NotNull m what, @NotNull m from) {
            k.f(what, "what");
            k.f(from, "from");
            return true;
        }
    }
}
