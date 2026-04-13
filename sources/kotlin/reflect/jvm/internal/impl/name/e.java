package kotlin.reflect.jvm.internal.impl.name;

import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import kotlin.text.w;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: FqNamesUtil.kt */
public final class e {
    public static final boolean b(@NotNull b $this$isSubpackageOf, @NotNull b packageName) {
        k.f($this$isSubpackageOf, "$this$isSubpackageOf");
        k.f(packageName, "packageName");
        if (k.a($this$isSubpackageOf, packageName) || packageName.d()) {
            return true;
        }
        String b = $this$isSubpackageOf.b();
        k.b(b, "this.asString()");
        String b2 = packageName.b();
        k.b(b2, "packageName.asString()");
        return a(b, b2);
    }

    private static final boolean a(String subpackageNameStr, String packageNameStr) {
        return w.N(subpackageNameStr, packageNameStr, false, 2, (Object) null) && subpackageNameStr.charAt(packageNameStr.length()) == '.';
    }

    @NotNull
    public static final b d(@NotNull b $this$tail, @NotNull b prefix) {
        k.f($this$tail, "$this$tail");
        k.f(prefix, "prefix");
        if (!b($this$tail, prefix) || prefix.d()) {
            return $this$tail;
        }
        if (k.a($this$tail, prefix)) {
            b bVar = b.a;
            k.b(bVar, "FqName.ROOT");
            return bVar;
        }
        String b = $this$tail.b();
        k.b(b, "asString()");
        int length = prefix.b().length() + 1;
        if (b != null) {
            String substring = b.substring(length);
            k.b(substring, "(this as java.lang.String).substring(startIndex)");
            return new b(substring);
        }
        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
    }

    public static final boolean c(@Nullable String qualifiedName) {
        if (qualifiedName == null) {
            return false;
        }
        i state = i.BEGINNING;
        int length = qualifiedName.length();
        for (int i = 0; i < length; i++) {
            char c = qualifiedName.charAt(i);
            switch (d.a[state.ordinal()]) {
                case 1:
                case 2:
                    if (Character.isJavaIdentifierPart(c)) {
                        state = i.MIDDLE;
                        break;
                    } else {
                        return false;
                    }
                case 3:
                    if (c != '.') {
                        if (Character.isJavaIdentifierPart(c)) {
                            break;
                        } else {
                            return false;
                        }
                    } else {
                        state = i.AFTER_DOT;
                        break;
                    }
            }
        }
        if (state != i.AFTER_DOT) {
            return true;
        }
        return false;
    }
}
