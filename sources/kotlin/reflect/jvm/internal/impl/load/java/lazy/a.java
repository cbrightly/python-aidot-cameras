package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import kotlin.i;
import kotlin.jvm.internal.k;
import kotlin.jvm.internal.l;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.c;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.g;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.load.java.a;
import kotlin.reflect.jvm.internal.impl.load.java.structure.x;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.h;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: context.kt */
public final class a {
    @NotNull
    public static final h b(@NotNull h $this$child, @NotNull m typeParameterResolver) {
        k.f($this$child, "$this$child");
        k.f(typeParameterResolver, "typeParameterResolver");
        return new h($this$child.a(), typeParameterResolver, $this$child.c());
    }

    @Nullable
    public static final d g(@NotNull h $this$computeNewDefaultTypeQualifiers, @NotNull g additionalAnnotations) {
        EnumMap enumMap;
        EnumMap p1;
        k.f($this$computeNewDefaultTypeQualifiers, "$this$computeNewDefaultTypeQualifiers");
        k.f(additionalAnnotations, "additionalAnnotations");
        if ($this$computeNewDefaultTypeQualifiers.a().a().c()) {
            return $this$computeNewDefaultTypeQualifiers.b();
        }
        List arrayList = new ArrayList();
        Iterator it = additionalAnnotations.iterator();
        while (it.hasNext()) {
            Object it$iv$iv = i($this$computeNewDefaultTypeQualifiers, (c) it.next());
            if (it$iv$iv != null) {
                arrayList.add(it$iv$iv);
            }
        }
        List<k> nullabilityQualifiersWithApplicability = arrayList;
        if (nullabilityQualifiersWithApplicability.isEmpty()) {
            return $this$computeNewDefaultTypeQualifiers.b();
        }
        d b2 = $this$computeNewDefaultTypeQualifiers.b();
        if (b2 == null || (p1 = b2.b()) == null) {
            enumMap = new EnumMap(a.C0356a.class);
        } else {
            enumMap = new EnumMap(p1);
        }
        EnumMap nullabilityQualifiersByType = enumMap;
        boolean wasUpdate = false;
        for (k kVar : nullabilityQualifiersWithApplicability) {
            h nullability = kVar.a();
            for (a.C0356a applicabilityType : kVar.b()) {
                nullabilityQualifiersByType.put(applicabilityType, nullability);
                wasUpdate = true;
            }
        }
        return !wasUpdate ? $this$computeNewDefaultTypeQualifiers.b() : new d(nullabilityQualifiersByType);
    }

    private static final k i(@NotNull h $this$extractDefaultNullabilityQualifier, c annotationDescriptor) {
        h c;
        h nullabilityQualifier;
        kotlin.reflect.jvm.internal.impl.load.java.a typeQualifierResolver = $this$extractDefaultNullabilityQualifier.a().a();
        k it = typeQualifierResolver.h(annotationDescriptor);
        if (it != null) {
            return it;
        }
        a.b j = typeQualifierResolver.j(annotationDescriptor);
        if (j == null) {
            return null;
        }
        c typeQualifier = j.a();
        List applicability = j.b();
        kotlin.reflect.jvm.internal.impl.utils.h jsr305State = typeQualifierResolver.g(annotationDescriptor);
        if (jsr305State == null) {
            jsr305State = typeQualifierResolver.f(typeQualifier);
        }
        if (jsr305State.isIgnore() || (c = $this$extractDefaultNullabilityQualifier.a().p().c(typeQualifier)) == null || (nullabilityQualifier = h.b(c, (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.g) null, jsr305State.isWarning(), 1, (Object) null)) == null) {
            return null;
        }
        return new k(nullabilityQualifier, applicability);
    }

    @NotNull
    public static final h j(@NotNull h $this$replaceComponents, @NotNull b components) {
        k.f($this$replaceComponents, "$this$replaceComponents");
        k.f(components, "components");
        return new h(components, $this$replaceComponents.f(), $this$replaceComponents.c());
    }

    private static final h a(@NotNull h $this$child, m containingDeclaration, x typeParameterOwner, int typeParametersIndexOffset, kotlin.g<d> delegateForTypeQualifiers) {
        m mVar;
        b a = $this$child.a();
        if (typeParameterOwner != null) {
            mVar = new i($this$child, containingDeclaration, typeParameterOwner, typeParametersIndexOffset);
        } else {
            mVar = $this$child.f();
        }
        return new h(a, mVar, delegateForTypeQualifiers);
    }

    public static /* synthetic */ h f(h hVar, m mVar, x xVar, int i, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return e(hVar, mVar, xVar, i);
    }

    @NotNull
    public static final h e(@NotNull h $this$childForMethod, @NotNull m containingDeclaration, @NotNull x typeParameterOwner, int typeParametersIndexOffset) {
        k.f($this$childForMethod, "$this$childForMethod");
        k.f(containingDeclaration, "containingDeclaration");
        k.f(typeParameterOwner, "typeParameterOwner");
        return a($this$childForMethod, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, $this$childForMethod.c());
    }

    public static /* synthetic */ h d(h hVar, kotlin.reflect.jvm.internal.impl.descriptors.g gVar, x xVar, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            xVar = null;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return c(hVar, gVar, xVar, i);
    }

    @NotNull
    public static final h c(@NotNull h $this$childForClassOrPackage, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.g containingDeclaration, @Nullable x typeParameterOwner, int typeParametersIndexOffset) {
        k.f($this$childForClassOrPackage, "$this$childForClassOrPackage");
        k.f(containingDeclaration, "containingDeclaration");
        return a($this$childForClassOrPackage, containingDeclaration, typeParameterOwner, typeParametersIndexOffset, i.a(kotlin.k.NONE, new C0360a($this$childForClassOrPackage, containingDeclaration)));
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.load.java.lazy.a$a  reason: collision with other inner class name */
    /* compiled from: context.kt */
    public static final class C0360a extends l implements kotlin.jvm.functions.a<d> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.g $containingDeclaration;
        final /* synthetic */ h $this_childForClassOrPackage;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        C0360a(h hVar, kotlin.reflect.jvm.internal.impl.descriptors.g gVar) {
            super(0);
            this.$this_childForClassOrPackage = hVar;
            this.$containingDeclaration = gVar;
        }

        @Nullable
        public final d invoke() {
            return a.g(this.$this_childForClassOrPackage, this.$containingDeclaration.getAnnotations());
        }
    }

    @NotNull
    public static final h h(@NotNull h $this$copyWithNewDefaultTypeQualifiers, @NotNull g additionalAnnotations) {
        k.f($this$copyWithNewDefaultTypeQualifiers, "$this$copyWithNewDefaultTypeQualifiers");
        k.f(additionalAnnotations, "additionalAnnotations");
        if (additionalAnnotations.isEmpty()) {
            return $this$copyWithNewDefaultTypeQualifiers;
        }
        return new h($this$copyWithNewDefaultTypeQualifiers.a(), $this$copyWithNewDefaultTypeQualifiers.f(), i.a(kotlin.k.NONE, new b($this$copyWithNewDefaultTypeQualifiers, additionalAnnotations)));
    }

    /* compiled from: context.kt */
    public static final class b extends l implements kotlin.jvm.functions.a<d> {
        final /* synthetic */ g $additionalAnnotations;
        final /* synthetic */ h $this_copyWithNewDefaultTypeQualifiers;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        b(h hVar, g gVar) {
            super(0);
            this.$this_copyWithNewDefaultTypeQualifiers = hVar;
            this.$additionalAnnotations = gVar;
        }

        @Nullable
        public final d invoke() {
            return a.g(this.$this_copyWithNewDefaultTypeQualifiers, this.$additionalAnnotations);
        }
    }
}
