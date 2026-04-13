package kotlin.reflect.jvm.internal.impl.util;

import java.util.Arrays;
import java.util.Collection;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.util.c;
import kotlin.text.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: modifierChecks.kt */
public final class d {
    @Nullable
    private final f a;
    @Nullable
    private final j b;
    @Nullable
    private final Collection<f> c;
    @NotNull
    private final l<u, String> d;
    @NotNull
    private final b[] e;

    private d(f name, j regex, Collection<f> nameList, l<? super u, String> additionalCheck, b... checks) {
        this.a = name;
        this.b = regex;
        this.c = nameList;
        this.d = additionalCheck;
        this.e = checks;
    }

    public final boolean b(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        if (this.a != null && (!k.a(functionDescriptor.getName(), this.a))) {
            return false;
        }
        if (this.b != null) {
            String b2 = functionDescriptor.getName().b();
            k.b(b2, "functionDescriptor.name.asString()");
            if (!this.b.matches(b2)) {
                return false;
            }
        }
        Collection<f> collection = this.c;
        return collection == null || collection.contains(functionDescriptor.getName());
    }

    @NotNull
    public final c a(@NotNull u functionDescriptor) {
        k.f(functionDescriptor, "functionDescriptor");
        for (b check : this.e) {
            String checkResult = check.a(functionDescriptor);
            if (checkResult != null) {
                return new c.b(checkResult);
            }
        }
        String additionalCheckResult = this.d.invoke(functionDescriptor);
        if (additionalCheckResult != null) {
            return new c.b(additionalCheckResult);
        }
        return c.C0431c.b;
    }

    /* compiled from: modifierChecks.kt */
    public static final class a extends kotlin.jvm.internal.l implements l {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull u $receiver) {
            k.f($receiver, "$receiver");
            return null;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public d(@NotNull f name, @NotNull b[] checks, @NotNull l<? super u, String> additionalChecks) {
        this(name, (j) null, (Collection<f>) null, additionalChecks, (b[]) Arrays.copyOf(checks, checks.length));
        k.f(name, "name");
        k.f(checks, "checks");
        k.f(additionalChecks, "additionalChecks");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(f fVar, b[] bVarArr, l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(fVar, bVarArr, (l<? super u, String>) (i & 4) != 0 ? a.INSTANCE : lVar);
    }

    /* compiled from: modifierChecks.kt */
    public static final class b extends kotlin.jvm.internal.l implements l {
        public static final b INSTANCE = new b();

        b() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull u $receiver) {
            k.f($receiver, "$receiver");
            return null;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public d(@NotNull j regex, @NotNull b[] checks, @NotNull l<? super u, String> additionalChecks) {
        this((f) null, regex, (Collection<f>) null, additionalChecks, (b[]) Arrays.copyOf(checks, checks.length));
        k.f(regex, "regex");
        k.f(checks, "checks");
        k.f(additionalChecks, "additionalChecks");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(j jVar, b[] bVarArr, l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(jVar, bVarArr, (l<? super u, String>) (i & 4) != 0 ? b.INSTANCE : lVar);
    }

    /* compiled from: modifierChecks.kt */
    public static final class c extends kotlin.jvm.internal.l implements l {
        public static final c INSTANCE = new c();

        c() {
            super(1);
        }

        @Nullable
        public final Void invoke(@NotNull u $receiver) {
            k.f($receiver, "$receiver");
            return null;
        }
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public d(@NotNull Collection<f> nameList, @NotNull b[] checks, @NotNull l<? super u, String> additionalChecks) {
        this((f) null, (j) null, nameList, additionalChecks, (b[]) Arrays.copyOf(checks, checks.length));
        k.f(nameList, "nameList");
        k.f(checks, "checks");
        k.f(additionalChecks, "additionalChecks");
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d(Collection collection, b[] bVarArr, l lVar, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((Collection<f>) collection, bVarArr, (l<? super u, String>) (i & 4) != 0 ? c.INSTANCE : lVar);
    }
}
