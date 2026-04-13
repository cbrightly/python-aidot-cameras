package kotlin.reflect.jvm.internal.impl.load.kotlin;

import com.meituan.robust.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import org.jetbrains.annotations.NotNull;

/* compiled from: methodSignatureBuilding.kt */
public final class v {
    public static final v a = new v();

    private v() {
    }

    @NotNull
    public final String h(@NotNull String name) {
        k.f(name, "name");
        return "java/lang/" + name;
    }

    @NotNull
    public final String i(@NotNull String name) {
        k.f(name, "name");
        return "java/util/" + name;
    }

    @NotNull
    public final String g(@NotNull String name) {
        k.f(name, "name");
        return "java/util/function/" + name;
    }

    @NotNull
    public final String[] b(@NotNull String... signatures) {
        k.f(signatures, "signatures");
        String[] strArr = signatures;
        ArrayList arrayList = new ArrayList(strArr.length);
        String[] strArr2 = strArr;
        int length = strArr2.length;
        for (int i = 0; i < length; i++) {
            arrayList.add("<init>(" + strArr2[i] + ")V");
        }
        ArrayList arrayList2 = arrayList;
        Object[] array = arrayList.toArray(new String[0]);
        if (array != null) {
            return (String[]) array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    @NotNull
    public final LinkedHashSet<String> e(@NotNull String name, @NotNull String... signatures) {
        k.f(name, "name");
        k.f(signatures, "signatures");
        return d(h(name), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    @NotNull
    public final LinkedHashSet<String> f(@NotNull String name, @NotNull String... signatures) {
        k.f(name, "name");
        k.f(signatures, "signatures");
        return d(i(name), (String[]) Arrays.copyOf(signatures, signatures.length));
    }

    @NotNull
    public final LinkedHashSet<String> d(@NotNull String internalName, @NotNull String... signatures) {
        k.f(internalName, "internalName");
        k.f(signatures, "signatures");
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        String[] strArr = signatures;
        int length = strArr.length;
        for (int i = 0; i < length; i++) {
            linkedHashSet.add(internalName + '.' + strArr[i]);
        }
        return linkedHashSet;
    }

    @NotNull
    public final String l(@NotNull e classDescriptor, @NotNull String jvmDescriptor) {
        k.f(classDescriptor, "classDescriptor");
        k.f(jvmDescriptor, "jvmDescriptor");
        return k(t.f(classDescriptor), jvmDescriptor);
    }

    @NotNull
    public final String k(@NotNull String internalName, @NotNull String jvmDescriptor) {
        k.f(internalName, "internalName");
        k.f(jvmDescriptor, "jvmDescriptor");
        return internalName + '.' + jvmDescriptor;
    }

    /* compiled from: methodSignatureBuilding.kt */
    public static final class a extends l implements kotlin.jvm.functions.l<String, String> {
        public static final a INSTANCE = new a();

        a() {
            super(1);
        }

        @NotNull
        public final String invoke(@NotNull String it) {
            k.f(it, "it");
            return v.a.c(it);
        }
    }

    @NotNull
    public final String j(@NotNull String name, @NotNull List<String> parameters, @NotNull String ret) {
        k.f(name, "name");
        k.f(parameters, "parameters");
        k.f(ret, "ret");
        return name + '(' + y.b0(parameters, "", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, a.INSTANCE, 30, (Object) null) + ')' + c(ret);
    }

    /* access modifiers changed from: private */
    public final String c(String internalName) {
        if (internalName.length() <= 1) {
            return internalName;
        }
        return Constants.OBJECT_TYPE + internalName + ';';
    }
}
