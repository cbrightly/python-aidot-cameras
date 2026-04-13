package kotlin.reflect.jvm.internal.impl.resolve.constants;

import java.util.Arrays;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.types.i0;
import org.jetbrains.annotations.NotNull;

/* compiled from: constantValues.kt */
public final class e extends p<Character> {
    public e(char value) {
        super(Character.valueOf(value));
    }

    @NotNull
    /* renamed from: d */
    public i0 a(@NotNull y module) {
        k.f(module, "module");
        i0 u = module.j().u();
        k.b(u, "module.builtIns.charType");
        return u;
    }

    @NotNull
    public String toString() {
        String format = String.format("\\u%04X ('%s')", Arrays.copyOf(new Object[]{Integer.valueOf(((Character) b()).charValue()), c(((Character) b()).charValue())}, 2));
        k.b(format, "java.lang.String.format(this, *args)");
        return format;
    }

    private final String c(char c) {
        switch (c) {
            case 8:
                return "\\b";
            case 9:
                return "\\t";
            case 10:
                return "\\n";
            case 12:
                return "\\f";
            case 13:
                return "\\r";
            default:
                return e(c) ? String.valueOf(c) : "?";
        }
    }

    private final boolean e(char c) {
        byte t = (byte) Character.getType(c);
        return (t == 0 || t == 13 || t == 14 || t == 15 || t == 16 || t == 18 || t == 19) ? false : true;
    }
}
