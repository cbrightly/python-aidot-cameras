package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.o0;
import kotlin.collections.v;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.name.a;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.name.f;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeserializedPackageMemberScope.kt */
public class h extends g {
    private final b m;
    private final b0 n;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public h(@org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.descriptors.b0 r17, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.l r18, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.c r19, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.metadata.deserialization.a r20, @org.jetbrains.annotations.Nullable kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e r21, @org.jetbrains.annotations.NotNull kotlin.reflect.jvm.internal.impl.serialization.deserialization.l r22, @org.jetbrains.annotations.NotNull kotlin.jvm.functions.a<? extends java.util.Collection<kotlin.reflect.jvm.internal.impl.name.f>> r23) {
        /*
            r16 = this;
            r6 = r16
            r14 = r17
            java.lang.String r0 = "packageDescriptor"
            kotlin.jvm.internal.k.f(r14, r0)
            java.lang.String r0 = "proto"
            r15 = r18
            kotlin.jvm.internal.k.f(r15, r0)
            java.lang.String r0 = "nameResolver"
            r5 = r19
            kotlin.jvm.internal.k.f(r5, r0)
            java.lang.String r0 = "metadataVersion"
            r4 = r20
            kotlin.jvm.internal.k.f(r4, r0)
            java.lang.String r0 = "components"
            r3 = r22
            kotlin.jvm.internal.k.f(r3, r0)
            java.lang.String r0 = "classNames"
            r2 = r23
            kotlin.jvm.internal.k.f(r2, r0)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.h r10 = new kotlin.reflect.jvm.internal.impl.metadata.deserialization.h
            kotlin.reflect.jvm.internal.impl.metadata.t r0 = r18.getTypeTable()
            java.lang.String r1 = "proto.typeTable"
            kotlin.jvm.internal.k.b(r0, r1)
            r10.<init>(r0)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.k$a r0 = kotlin.reflect.jvm.internal.impl.metadata.deserialization.k.b
            kotlin.reflect.jvm.internal.impl.metadata.w r1 = r18.getVersionRequirementTable()
            java.lang.String r7 = "proto.versionRequirementTable"
            kotlin.jvm.internal.k.b(r1, r7)
            kotlin.reflect.jvm.internal.impl.metadata.deserialization.k r11 = r0.a(r1)
            r7 = r22
            r8 = r17
            r9 = r19
            r12 = r20
            r13 = r21
            kotlin.reflect.jvm.internal.impl.serialization.deserialization.n r1 = r7.a(r8, r9, r10, r11, r12, r13)
            java.util.List r7 = r18.getFunctionList()
            java.lang.String r0 = "proto.functionList"
            kotlin.jvm.internal.k.b(r7, r0)
            java.util.List r8 = r18.getPropertyList()
            java.lang.String r0 = "proto.propertyList"
            kotlin.jvm.internal.k.b(r8, r0)
            java.util.List r9 = r18.getTypeAliasList()
            java.lang.String r0 = "proto.typeAliasList"
            kotlin.jvm.internal.k.b(r9, r0)
            r0 = r16
            r2 = r7
            r3 = r8
            r4 = r9
            r5 = r23
            r0.<init>(r1, r2, r3, r4, r5)
            r6.n = r14
            kotlin.reflect.jvm.internal.impl.name.b r0 = r17.e()
            r6.m = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.h.<init>(kotlin.reflect.jvm.internal.impl.descriptors.b0, kotlin.reflect.jvm.internal.impl.metadata.l, kotlin.reflect.jvm.internal.impl.metadata.deserialization.c, kotlin.reflect.jvm.internal.impl.metadata.deserialization.a, kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.e, kotlin.reflect.jvm.internal.impl.serialization.deserialization.l, kotlin.jvm.functions.a):void");
    }

    @NotNull
    /* renamed from: F */
    public List<m> d(@NotNull d kindFilter, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(kindFilter, "kindFilter");
        k.f(nameFilter, "nameFilter");
        Collection<m> o = o(kindFilter, nameFilter, kotlin.reflect.jvm.internal.impl.incremental.components.d.WHEN_GET_ALL_DESCRIPTORS);
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> $this$flatMapTo$iv$iv = w().c().k();
        Collection destination$iv$iv = new ArrayList();
        for (kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b it : $this$flatMapTo$iv$iv) {
            v.x(destination$iv$iv, it.a(this.m));
        }
        return y.n0(o, destination$iv$iv);
    }

    /* access modifiers changed from: protected */
    public boolean D(@NotNull f name) {
        Iterable $this$any$iv;
        k.f(name, "name");
        if (super.D(name)) {
            return true;
        }
        Iterable<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> k = w().c().k();
        if (!(k instanceof Collection) || !((Collection) k).isEmpty()) {
            Iterator<kotlin.reflect.jvm.internal.impl.descriptors.deserialization.b> it = k.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().b(this.m, name)) {
                        $this$any$iv = 1;
                        break;
                    }
                } else {
                    $this$any$iv = null;
                    break;
                }
            }
        } else {
            $this$any$iv = null;
        }
        return $this$any$iv != null;
    }

    /* access modifiers changed from: protected */
    @NotNull
    public a t(@NotNull f name) {
        k.f(name, "name");
        return new a(this.m, name);
    }

    @Nullable
    public kotlin.reflect.jvm.internal.impl.descriptors.h c(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        G(name, location);
        return super.c(name, location);
    }

    public void G(@NotNull f name, @NotNull kotlin.reflect.jvm.internal.impl.incremental.components.b location) {
        k.f(name, "name");
        k.f(location, FirebaseAnalytics.Param.LOCATION);
        kotlin.reflect.jvm.internal.impl.incremental.a.b(w().c().o(), location, this.n, name);
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> z() {
        return o0.d();
    }

    /* access modifiers changed from: protected */
    @NotNull
    public Set<f> A() {
        return o0.d();
    }

    /* access modifiers changed from: protected */
    public void m(@NotNull Collection<m> result, @NotNull l<? super f, Boolean> nameFilter) {
        k.f(result, "result");
        k.f(nameFilter, "nameFilter");
    }
}
