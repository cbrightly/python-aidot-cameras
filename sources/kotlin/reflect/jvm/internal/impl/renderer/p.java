package kotlin.reflect.jvm.internal.impl.renderer;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;

/* compiled from: DescriptorRenderer.kt */
public enum p {
    ;

    @NotNull
    public abstract String escape(@NotNull String str);

    /* compiled from: DescriptorRenderer.kt */
    public static final class b extends p {
        b(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
        }

        @NotNull
        public String escape(@NotNull String string) {
            k.f(string, TypedValues.Custom.S_STRING);
            return string;
        }
    }

    /* compiled from: DescriptorRenderer.kt */
    public static final class a extends p {
        a(String $enum_name_or_ordinal$0, int $enum_name_or_ordinal$1) {
            super($enum_name_or_ordinal$0, $enum_name_or_ordinal$1, (DefaultConstructorMarker) null);
        }

        @NotNull
        public String escape(@NotNull String string) {
            k.f(string, TypedValues.Custom.S_STRING);
            return w.H(w.H(string, "<", "&lt;", false, 4, (Object) null), ">", "&gt;", false, 4, (Object) null);
        }
    }
}
