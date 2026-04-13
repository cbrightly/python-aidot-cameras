package kotlin.reflect.jvm.internal.impl.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.ServiceLoader;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.y;
import kotlin.jvm.functions.l;
import kotlin.jvm.functions.p;
import kotlin.n;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.a1;
import kotlin.reflect.jvm.internal.impl.descriptors.b;
import kotlin.reflect.jvm.internal.impl.descriptors.h0;
import kotlin.reflect.jvm.internal.impl.descriptors.i0;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.l0;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.q;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.descriptors.u;
import kotlin.reflect.jvm.internal.impl.descriptors.v;
import kotlin.reflect.jvm.internal.impl.descriptors.w;
import kotlin.reflect.jvm.internal.impl.descriptors.w0;
import kotlin.reflect.jvm.internal.impl.descriptors.z0;
import kotlin.reflect.jvm.internal.impl.resolve.d;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.b0;
import kotlin.reflect.jvm.internal.impl.types.checker.g;
import kotlin.reflect.jvm.internal.impl.types.checker.i;
import kotlin.reflect.jvm.internal.impl.types.d0;
import kotlin.reflect.jvm.internal.impl.types.u0;
import kotlin.x;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OverridingUtil */
public class i {
    private static final List<d> a;
    public static final i b;
    private static final g.a c;
    private final kotlin.reflect.jvm.internal.impl.types.checker.i d;
    /* access modifiers changed from: private */
    public final g.a e;

    private static /* synthetic */ void a(int i) {
        String str;
        int i2;
        Throwable th;
        switch (i) {
            case 7:
            case 8:
            case 12:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 40:
            case 41:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 85:
            case 86:
            case 87:
            case 90:
            case 93:
            case 98:
                str = "@NotNull method %s.%s must not return null";
                break;
            default:
                str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                break;
        }
        switch (i) {
            case 7:
            case 8:
            case 12:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 40:
            case 41:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 85:
            case 86:
            case 87:
            case 90:
            case 93:
            case 98:
                i2 = 2;
                break;
            default:
                i2 = 3;
                break;
        }
        Object[] objArr = new Object[i2];
        switch (i) {
            case 1:
            case 3:
                objArr[0] = "kotlinTypeRefiner";
                break;
            case 2:
                objArr[0] = "axioms";
                break;
            case 4:
            case 5:
                objArr[0] = "candidateSet";
                break;
            case 6:
                objArr[0] = "transformFirst";
                break;
            case 7:
            case 8:
            case 12:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 40:
            case 41:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 85:
            case 86:
            case 87:
            case 90:
            case 93:
            case 98:
                objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil";
                break;
            case 9:
                objArr[0] = "f";
                break;
            case 10:
                objArr[0] = "g";
                break;
            case 11:
            case 13:
                objArr[0] = "descriptor";
                break;
            case 14:
                objArr[0] = "result";
                break;
            case 15:
            case 18:
            case 26:
            case 36:
                objArr[0] = "superDescriptor";
                break;
            case 16:
            case 19:
            case 27:
            case 37:
                objArr[0] = "subDescriptor";
                break;
            case 38:
                objArr[0] = "firstParameters";
                break;
            case 39:
                objArr[0] = "secondParameters";
                break;
            case 42:
                objArr[0] = "typeInSuper";
                break;
            case 43:
                objArr[0] = "typeInSub";
                break;
            case 44:
            case 47:
                objArr[0] = "typeChecker";
                break;
            case 45:
                objArr[0] = "superTypeParameter";
                break;
            case 46:
                objArr[0] = "subTypeParameter";
                break;
            case 48:
                objArr[0] = "name";
                break;
            case 49:
                objArr[0] = "membersFromSupertypes";
                break;
            case 50:
                objArr[0] = "membersFromCurrent";
                break;
            case 51:
            case 57:
            case 60:
            case 81:
            case 84:
            case 91:
                objArr[0] = "current";
                break;
            case 52:
            case 58:
            case 62:
            case 82:
            case 101:
                objArr[0] = "strategy";
                break;
            case 53:
                objArr[0] = "overriding";
                break;
            case 54:
                objArr[0] = "fromSuper";
                break;
            case 55:
                objArr[0] = "fromCurrent";
                break;
            case 56:
                objArr[0] = "descriptorsFromSuper";
                break;
            case 59:
            case 61:
                objArr[0] = "notOverridden";
                break;
            case 63:
            case 65:
            case 69:
                objArr[0] = "a";
                break;
            case 64:
            case 66:
            case 71:
                objArr[0] = "b";
                break;
            case 67:
                objArr[0] = "candidate";
                break;
            case 68:
            case 83:
            case 88:
            case 104:
                objArr[0] = "descriptors";
                break;
            case 70:
                objArr[0] = "aReturnType";
                break;
            case 72:
                objArr[0] = "bReturnType";
                break;
            case 73:
            case 80:
                objArr[0] = "overridables";
                break;
            case 74:
            case 96:
                objArr[0] = "descriptorByHandle";
                break;
            case 89:
                objArr[0] = "classModality";
                break;
            case 92:
                objArr[0] = "toFilter";
                break;
            case 94:
            case 99:
                objArr[0] = "overrider";
                break;
            case 95:
            case 100:
                objArr[0] = "extractFrom";
                break;
            case 97:
                objArr[0] = "onConflict";
                break;
            case 102:
            case 103:
                objArr[0] = "memberDescriptor";
                break;
            default:
                objArr[0] = "equalityAxioms";
                break;
        }
        switch (i) {
            case 7:
            case 8:
                objArr[1] = "filterOverrides";
                break;
            case 12:
                objArr[1] = "getOverriddenDeclarations";
                break;
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                objArr[1] = "isOverridableBy";
                break;
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                objArr[1] = "isOverridableByWithoutExternalConditions";
                break;
            case 40:
            case 41:
                objArr[1] = "createTypeChecker";
                break;
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
                objArr[1] = "selectMostSpecificMember";
                break;
            case 85:
            case 86:
            case 87:
                objArr[1] = "determineModalityForFakeOverride";
                break;
            case 90:
                objArr[1] = "getMinimalModality";
                break;
            case 93:
                objArr[1] = "filterVisibleFakeOverrides";
                break;
            case 98:
                objArr[1] = "extractMembersOverridableInBothWays";
                break;
            default:
                objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil";
                break;
        }
        switch (i) {
            case 1:
                objArr[2] = "createWithTypeRefiner";
                break;
            case 2:
            case 3:
                objArr[2] = "<init>";
                break;
            case 4:
                objArr[2] = "filterOutOverridden";
                break;
            case 5:
            case 6:
                objArr[2] = "filterOverrides";
                break;
            case 7:
            case 8:
            case 12:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 40:
            case 41:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 85:
            case 86:
            case 87:
            case 90:
            case 93:
            case 98:
                break;
            case 9:
            case 10:
                objArr[2] = "overrides";
                break;
            case 11:
                objArr[2] = "getOverriddenDeclarations";
                break;
            case 13:
            case 14:
                objArr[2] = "collectOverriddenDeclarations";
                break;
            case 15:
            case 16:
            case 18:
            case 19:
                objArr[2] = "isOverridableBy";
                break;
            case 26:
            case 27:
                objArr[2] = "isOverridableByWithoutExternalConditions";
                break;
            case 36:
            case 37:
                objArr[2] = "getBasicOverridabilityProblem";
                break;
            case 38:
            case 39:
                objArr[2] = "createTypeChecker";
                break;
            case 42:
            case 43:
            case 44:
                objArr[2] = "areTypesEquivalent";
                break;
            case 45:
            case 46:
            case 47:
                objArr[2] = "areTypeParametersEquivalent";
                break;
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                objArr[2] = "generateOverridesInFunctionGroup";
                break;
            case 53:
            case 54:
                objArr[2] = "isVisibleForOverride";
                break;
            case 55:
            case 56:
            case 57:
            case 58:
                objArr[2] = "extractAndBindOverridesForMember";
                break;
            case 59:
                objArr[2] = "allHasSameContainingDeclaration";
                break;
            case 60:
            case 61:
            case 62:
                objArr[2] = "createAndBindFakeOverrides";
                break;
            case 63:
            case 64:
                objArr[2] = "isMoreSpecific";
                break;
            case 65:
            case 66:
                objArr[2] = "isVisibilityMoreSpecific";
                break;
            case 67:
            case 68:
                objArr[2] = "isMoreSpecificThenAllOf";
                break;
            case 69:
            case 70:
            case 71:
            case 72:
                objArr[2] = "isReturnTypeMoreSpecific";
                break;
            case 73:
            case 74:
                objArr[2] = "selectMostSpecificMember";
                break;
            case 80:
            case 81:
            case 82:
                objArr[2] = "createAndBindFakeOverride";
                break;
            case 83:
            case 84:
                objArr[2] = "determineModalityForFakeOverride";
                break;
            case 88:
            case 89:
                objArr[2] = "getMinimalModality";
                break;
            case 91:
            case 92:
                objArr[2] = "filterVisibleFakeOverrides";
                break;
            case 94:
            case 95:
            case 96:
            case 97:
            case 99:
            case 100:
            case 101:
                objArr[2] = "extractMembersOverridableInBothWays";
                break;
            case 102:
                objArr[2] = "resolveUnknownVisibilityForMember";
                break;
            case 103:
                objArr[2] = "computeVisibilityToInherit";
                break;
            case 104:
                objArr[2] = "findMaxVisibility";
                break;
            default:
                objArr[2] = "createWithEqualityAxioms";
                break;
        }
        String format = String.format(str, objArr);
        switch (i) {
            case 7:
            case 8:
            case 12:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 40:
            case 41:
            case 75:
            case 76:
            case 77:
            case 78:
            case 79:
            case 85:
            case 86:
            case 87:
            case 90:
            case 93:
            case 98:
                th = new IllegalStateException(format);
                break;
            default:
                th = new IllegalArgumentException(format);
                break;
        }
        throw th;
    }

    static {
        Class<d> cls = d.class;
        a = y.C0(ServiceLoader.load(cls, cls.getClassLoader()));
        a aVar = new a();
        c = aVar;
        b = new i(aVar, i.a.a);
    }

    /* compiled from: OverridingUtil */
    public static final class a implements g.a {
        private static /* synthetic */ void b(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "b";
                    break;
                default:
                    objArr[0] = "a";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil$1";
            objArr[2] = "equals";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        a() {
        }

        public boolean a(@NotNull u0 a, @NotNull u0 b) {
            if (a == null) {
                b(0);
            }
            if (b == null) {
                b(1);
            }
            return a.equals(b);
        }
    }

    @NotNull
    public static i m(@NotNull g.a equalityAxioms) {
        if (equalityAxioms == null) {
            a(0);
        }
        return new i(equalityAxioms, i.a.a);
    }

    @NotNull
    public static i n(@NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        if (kotlinTypeRefiner == null) {
            a(1);
        }
        return new i(c, kotlinTypeRefiner);
    }

    private i(@NotNull g.a axioms, @NotNull kotlin.reflect.jvm.internal.impl.types.checker.i kotlinTypeRefiner) {
        if (axioms == null) {
            a(2);
        }
        if (kotlinTypeRefiner == null) {
            a(3);
        }
        this.e = axioms;
        this.d = kotlinTypeRefiner;
    }

    @NotNull
    public static <D extends kotlin.reflect.jvm.internal.impl.descriptors.a> Set<D> s(@NotNull Set<D> candidateSet) {
        if (candidateSet == null) {
            a(4);
        }
        return t(candidateSet, !candidateSet.isEmpty() && kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.r(kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.a.m((m) candidateSet.iterator().next())), new b());
    }

    /* compiled from: OverridingUtil */
    public static final class b implements p<D, D, n<kotlin.reflect.jvm.internal.impl.descriptors.a, kotlin.reflect.jvm.internal.impl.descriptors.a>> {
        b() {
        }

        /* renamed from: a */
        public n<kotlin.reflect.jvm.internal.impl.descriptors.a, kotlin.reflect.jvm.internal.impl.descriptors.a> invoke(D a, D b) {
            return new n<>(a, b);
        }
    }

    @NotNull
    public static <D> Set<D> t(@NotNull Set<D> candidateSet, boolean allowDescriptorCopies, @NotNull p<? super D, ? super D, n<kotlin.reflect.jvm.internal.impl.descriptors.a, kotlin.reflect.jvm.internal.impl.descriptors.a>> transformFirst) {
        if (candidateSet == null) {
            a(5);
        }
        if (transformFirst == null) {
            a(6);
        }
        if (candidateSet.size() <= 1) {
            return candidateSet;
        }
        Set<D> result = new LinkedHashSet<>();
        for (D meD : candidateSet) {
            Iterator<D> iterator = result.iterator();
            while (true) {
                if (!iterator.hasNext()) {
                    result.add(meD);
                    break;
                }
                Pair<CallableDescriptor, CallableDescriptor> meAndOther = (n) transformFirst.invoke(meD, iterator.next());
                kotlin.reflect.jvm.internal.impl.descriptors.a me2 = (kotlin.reflect.jvm.internal.impl.descriptors.a) meAndOther.component1();
                kotlin.reflect.jvm.internal.impl.descriptors.a other = (kotlin.reflect.jvm.internal.impl.descriptors.a) meAndOther.component2();
                if (K(me2, other, allowDescriptorCopies)) {
                    iterator.remove();
                } else if (K(other, me2, allowDescriptorCopies)) {
                    break;
                }
            }
        }
        if (!result.isEmpty()) {
            return result;
        }
        throw new AssertionError("All candidates filtered out from " + candidateSet);
    }

    public static <D extends kotlin.reflect.jvm.internal.impl.descriptors.a> boolean K(@NotNull D f2, @NotNull D g2, boolean allowDeclarationCopies) {
        if (f2 == null) {
            a(9);
        }
        if (g2 == null) {
            a(10);
        }
        if (!f2.equals(g2) && a.a.e(f2.a(), g2.a(), allowDeclarationCopies)) {
            return true;
        }
        kotlin.reflect.jvm.internal.impl.descriptors.a originalG = g2.a();
        for (D overriddenFunction : c.d(f2)) {
            if (a.a.e(originalG, overriddenFunction, allowDeclarationCopies)) {
                return true;
            }
        }
        return false;
    }

    @NotNull
    public static Set<kotlin.reflect.jvm.internal.impl.descriptors.b> A(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
        if (descriptor == null) {
            a(11);
        }
        Set<CallableMemberDescriptor> result = new LinkedHashSet<>();
        g(descriptor, result);
        return result;
    }

    private static void g(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b descriptor, @NotNull Set<kotlin.reflect.jvm.internal.impl.descriptors.b> result) {
        if (descriptor == null) {
            a(13);
        }
        if (result == null) {
            a(14);
        }
        if (descriptor.h().isReal()) {
            result.add(descriptor);
        } else if (!descriptor.d().isEmpty()) {
            for (kotlin.reflect.jvm.internal.impl.descriptors.b overridden : descriptor.d()) {
                g(overridden, result);
            }
        } else {
            throw new IllegalStateException("No overridden descriptors found for (fake override) " + descriptor);
        }
    }

    @NotNull
    public j E(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.e subClassDescriptor) {
        if (superDescriptor == null) {
            a(15);
        }
        if (subDescriptor == null) {
            a(16);
        }
        j F = F(superDescriptor, subDescriptor, subClassDescriptor, false);
        if (F == null) {
            a(17);
        }
        return F;
    }

    @NotNull
    public j F(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, @Nullable kotlin.reflect.jvm.internal.impl.descriptors.e subClassDescriptor, boolean checkReturnType) {
        if (superDescriptor == null) {
            a(18);
        }
        if (subDescriptor == null) {
            a(19);
        }
        j basicResult = G(superDescriptor, subDescriptor, checkReturnType);
        boolean wasSuccess = basicResult.c() == j.a.OVERRIDABLE;
        for (d externalCondition : a) {
            if (externalCondition.a() != d.a.CONFLICTS_ONLY && (!wasSuccess || externalCondition.a() != d.a.SUCCESS_ONLY)) {
                switch (C0411i.a[externalCondition.b(superDescriptor, subDescriptor, subClassDescriptor).ordinal()]) {
                    case 1:
                        wasSuccess = true;
                        break;
                    case 2:
                        j b2 = j.b("External condition failed");
                        if (b2 == null) {
                            a(20);
                        }
                        return b2;
                    case 3:
                        j d2 = j.d("External condition");
                        if (d2 == null) {
                            a(21);
                        }
                        return d2;
                }
            }
        }
        if (!wasSuccess) {
            return basicResult;
        }
        for (d externalCondition2 : a) {
            if (externalCondition2.a() == d.a.CONFLICTS_ONLY) {
                switch (C0411i.a[externalCondition2.b(superDescriptor, subDescriptor, subClassDescriptor).ordinal()]) {
                    case 1:
                        throw new IllegalStateException("Contract violation in " + externalCondition2.getClass().getName() + " condition. It's not supposed to end with success");
                    case 2:
                        j b3 = j.b("External condition failed");
                        if (b3 == null) {
                            a(23);
                        }
                        return b3;
                    case 3:
                        j d3 = j.d("External condition");
                        if (d3 == null) {
                            a(24);
                        }
                        return d3;
                }
            }
        }
        j e2 = j.e();
        if (e2 == null) {
            a(25);
        }
        return e2;
    }

    @NotNull
    public j G(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor, boolean checkReturnType) {
        if (superDescriptor == null) {
            a(26);
        }
        if (subDescriptor == null) {
            a(27);
        }
        j basicOverridability = x(superDescriptor, subDescriptor);
        if (basicOverridability != null) {
            return basicOverridability;
        }
        List<b0> h2 = h(superDescriptor);
        List<b0> h3 = h(subDescriptor);
        List<t0> typeParameters = superDescriptor.getTypeParameters();
        List<t0> typeParameters2 = subDescriptor.getTypeParameters();
        if (typeParameters.size() != typeParameters2.size()) {
            for (int i = 0; i < h2.size(); i++) {
                if (!kotlin.reflect.jvm.internal.impl.types.checker.g.a.b(h2.get(i), h3.get(i))) {
                    j d2 = j.d("Type parameter number mismatch");
                    if (d2 == null) {
                        a(29);
                    }
                    return d2;
                }
            }
            j b2 = j.b("Type parameter number mismatch");
            if (b2 == null) {
                a(30);
            }
            return b2;
        }
        kotlin.reflect.jvm.internal.impl.types.checker.g typeChecker = l(typeParameters, typeParameters2);
        for (int i2 = 0; i2 < typeParameters.size(); i2++) {
            if (!d(typeParameters.get(i2), typeParameters2.get(i2), typeChecker)) {
                j d3 = j.d("Type parameter bounds mismatch");
                if (d3 == null) {
                    a(31);
                }
                return d3;
            }
        }
        for (int i3 = 0; i3 < h2.size(); i3++) {
            if (!e(h2.get(i3), h3.get(i3), typeChecker)) {
                j d4 = j.d("Value parameter type mismatch");
                if (d4 == null) {
                    a(32);
                }
                return d4;
            }
        }
        if ((superDescriptor instanceof u) == 0 || !(subDescriptor instanceof u) || ((u) superDescriptor).isSuspend() == ((u) subDescriptor).isSuspend()) {
            if (checkReturnType) {
                b0 superReturnType = superDescriptor.getReturnType();
                b0 subReturnType = subDescriptor.getReturnType();
                if (!(superReturnType == null || subReturnType == null)) {
                    if (!(d0.a(subReturnType) && d0.a(superReturnType)) && !typeChecker.d(this.d.g(subReturnType), this.d.g(superReturnType))) {
                        j b3 = j.b("Return type mismatch");
                        if (b3 == null) {
                            a(34);
                        }
                        return b3;
                    }
                }
            }
            j e2 = j.e();
            if (e2 == null) {
                a(35);
            }
            return e2;
        }
        j b4 = j.b("Incompatible suspendability");
        if (b4 == null) {
            a(33);
        }
        return b4;
    }

    @Nullable
    public static j x(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor) {
        if (superDescriptor == null) {
            a(36);
        }
        if (subDescriptor == null) {
            a(37);
        }
        if (((superDescriptor instanceof u) && !(subDescriptor instanceof u)) || ((superDescriptor instanceof i0) && !(subDescriptor instanceof i0))) {
            return j.d("Member kind mismatch");
        }
        if (!(superDescriptor instanceof u) && !(superDescriptor instanceof i0)) {
            throw new IllegalArgumentException("This type of CallableDescriptor cannot be checked for overridability: " + superDescriptor);
        } else if (!superDescriptor.getName().equals(subDescriptor.getName())) {
            return j.d("Name mismatch");
        } else {
            j receiverAndParameterResult = f(superDescriptor, subDescriptor);
            if (receiverAndParameterResult != null) {
                return receiverAndParameterResult;
            }
            return null;
        }
    }

    @NotNull
    private kotlin.reflect.jvm.internal.impl.types.checker.g l(@NotNull List<t0> firstParameters, @NotNull List<t0> secondParameters) {
        if (firstParameters == null) {
            a(38);
        }
        if (secondParameters == null) {
            a(39);
        }
        if (firstParameters.size() != secondParameters.size()) {
            throw new AssertionError("Should be the same number of type parameters: " + firstParameters + " vs " + secondParameters);
        } else if (firstParameters.isEmpty()) {
            kotlin.reflect.jvm.internal.impl.types.checker.g f2 = kotlin.reflect.jvm.internal.impl.types.checker.h.f(this.e);
            if (f2 == null) {
                a(40);
            }
            return f2;
        } else {
            Map<TypeConstructor, TypeConstructor> matchingTypeConstructors = new HashMap<>();
            for (int i = 0; i < firstParameters.size(); i++) {
                matchingTypeConstructors.put(firstParameters.get(i).i(), secondParameters.get(i).i());
            }
            kotlin.reflect.jvm.internal.impl.types.checker.g f3 = kotlin.reflect.jvm.internal.impl.types.checker.h.f(new c(matchingTypeConstructors));
            if (f3 == null) {
                a(41);
            }
            return f3;
        }
    }

    /* compiled from: OverridingUtil */
    public class c implements g.a {
        final /* synthetic */ Map a;

        private static /* synthetic */ void b(int i) {
            Object[] objArr = new Object[3];
            switch (i) {
                case 1:
                    objArr[0] = "b";
                    break;
                default:
                    objArr[0] = "a";
                    break;
            }
            objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil$3";
            objArr[2] = "equals";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        c(Map map) {
            this.a = map;
        }

        public boolean a(@NotNull u0 a2, @NotNull u0 b2) {
            if (a2 == null) {
                b(0);
            }
            if (b2 == null) {
                b(1);
            }
            if (i.this.e.a(a2, b2)) {
                return true;
            }
            u0 img1 = (u0) this.a.get(a2);
            u0 img2 = (u0) this.a.get(b2);
            if ((img1 == null || !img1.equals(b2)) && (img2 == null || !img2.equals(a2))) {
                return false;
            }
            return true;
        }
    }

    @Nullable
    private static j f(kotlin.reflect.jvm.internal.impl.descriptors.a superDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.a subDescriptor) {
        boolean z = true;
        boolean z2 = superDescriptor.L() == null;
        if (subDescriptor.L() != null) {
            z = false;
        }
        if (z2 != z) {
            return j.d("Receiver presence mismatch");
        }
        if (superDescriptor.g().size() != subDescriptor.g().size()) {
            return j.d("Value parameter number mismatch");
        }
        return null;
    }

    private boolean e(@NotNull b0 typeInSuper, @NotNull b0 typeInSub, @NotNull kotlin.reflect.jvm.internal.impl.types.checker.g typeChecker) {
        if (typeInSuper == null) {
            a(42);
        }
        if (typeInSub == null) {
            a(43);
        }
        if (typeChecker == null) {
            a(44);
        }
        if (d0.a(typeInSuper) && d0.a(typeInSub)) {
            return true;
        }
        return typeChecker.b(this.d.g(typeInSuper), this.d.g(typeInSub));
    }

    private boolean d(@NotNull t0 superTypeParameter, @NotNull t0 subTypeParameter, @NotNull kotlin.reflect.jvm.internal.impl.types.checker.g typeChecker) {
        if (superTypeParameter == null) {
            a(45);
        }
        if (subTypeParameter == null) {
            a(46);
        }
        if (typeChecker == null) {
            a(47);
        }
        List<b0> upperBounds = superTypeParameter.getUpperBounds();
        List<KotlinType> subBounds = new ArrayList<>(subTypeParameter.getUpperBounds());
        if (upperBounds.size() != subBounds.size()) {
            return false;
        }
        for (b0 superBound : upperBounds) {
            ListIterator<KotlinType> it = subBounds.listIterator();
            while (it.hasNext()) {
                if (e(superBound, (b0) it.next(), typeChecker)) {
                    it.remove();
                }
            }
            return false;
        }
        return true;
    }

    private static List<b0> h(kotlin.reflect.jvm.internal.impl.descriptors.a callableDescriptor) {
        l0 receiverParameter = callableDescriptor.L();
        List<KotlinType> parameters = new ArrayList<>();
        if (receiverParameter != null) {
            parameters.add(receiverParameter.getType());
        }
        for (w0 valueParameterDescriptor : callableDescriptor.g()) {
            parameters.add(valueParameterDescriptor.getType());
        }
        return parameters;
    }

    public void w(@NotNull kotlin.reflect.jvm.internal.impl.name.f name, @NotNull Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> membersFromSupertypes, @NotNull Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> membersFromCurrent, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current, @NotNull h strategy) {
        if (name == null) {
            a(48);
        }
        if (membersFromSupertypes == null) {
            a(49);
        }
        if (membersFromCurrent == null) {
            a(50);
        }
        if (current == null) {
            a(51);
        }
        if (strategy == null) {
            a(52);
        }
        Collection<CallableMemberDescriptor> notOverridden = new LinkedHashSet<>(membersFromSupertypes);
        for (kotlin.reflect.jvm.internal.impl.descriptors.b fromCurrent : membersFromCurrent) {
            notOverridden.removeAll(p(fromCurrent, membersFromSupertypes, current, strategy));
        }
        k(current, notOverridden, strategy);
    }

    public static boolean J(@NotNull v overriding, @NotNull v fromSuper) {
        if (overriding == null) {
            a(53);
        }
        if (fromSuper == null) {
            a(54);
        }
        return !z0.h(fromSuper.getVisibility()) && z0.i(fromSuper, overriding);
    }

    private Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> p(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b fromCurrent, @NotNull Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> descriptorsFromSuper, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current, @NotNull h strategy) {
        if (fromCurrent == null) {
            a(55);
        }
        if (descriptorsFromSuper == null) {
            a(56);
        }
        if (current == null) {
            a(57);
        }
        if (strategy == null) {
            a(58);
        }
        Collection<CallableMemberDescriptor> bound = new ArrayList<>(descriptorsFromSuper.size());
        Collection<CallableMemberDescriptor> overridden = kotlin.reflect.jvm.internal.impl.utils.j.a();
        for (kotlin.reflect.jvm.internal.impl.descriptors.b fromSupertype : descriptorsFromSuper) {
            j.a result = E(fromSupertype, fromCurrent, current).c();
            boolean isVisibleForOverride = J(fromCurrent, fromSupertype);
            switch (C0411i.b[result.ordinal()]) {
                case 1:
                    if (isVisibleForOverride) {
                        overridden.add(fromSupertype);
                    }
                    bound.add(fromSupertype);
                    break;
                case 2:
                    if (isVisibleForOverride) {
                        strategy.c(fromSupertype, fromCurrent);
                    }
                    bound.add(fromSupertype);
                    break;
            }
        }
        strategy.d(fromCurrent, overridden);
        return bound;
    }

    private static boolean c(@NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> notOverridden) {
        if (notOverridden == null) {
            a(59);
        }
        if (notOverridden.size() < 2) {
            return true;
        }
        return y.K(notOverridden, new d(notOverridden.iterator().next().b()));
    }

    /* compiled from: OverridingUtil */
    public static final class d implements l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        final /* synthetic */ m c;

        d(m mVar) {
            this.c = mVar;
        }

        /* renamed from: a */
        public Boolean invoke(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
            return Boolean.valueOf(descriptor.b() == this.c);
        }
    }

    private static void k(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current, @NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> notOverridden, @NotNull h strategy) {
        if (current == null) {
            a(60);
        }
        if (notOverridden == null) {
            a(61);
        }
        if (strategy == null) {
            a(62);
        }
        if (c(notOverridden)) {
            for (kotlin.reflect.jvm.internal.impl.descriptors.b descriptor : notOverridden) {
                j(Collections.singleton(descriptor), current, strategy);
            }
            return;
        }
        Queue<CallableMemberDescriptor> fromSuperQueue = new LinkedList<>(notOverridden);
        while (!fromSuperQueue.isEmpty()) {
            j(r(k.a(fromSuperQueue), fromSuperQueue, strategy), current, strategy);
        }
    }

    public static boolean C(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a a2, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a b2) {
        if (a2 == null) {
            a(63);
        }
        if (b2 == null) {
            a(64);
        }
        b0 aReturnType = a2.getReturnType();
        b0 bReturnType = b2.getReturnType();
        if (aReturnType == null) {
            throw new AssertionError("Return type of " + a2 + " is null");
        } else if (bReturnType == null) {
            throw new AssertionError("Return type of " + b2 + " is null");
        } else if (!I(a2, b2)) {
            return false;
        } else {
            if (a2 instanceof u) {
                if (b2 instanceof u) {
                    return H(a2, aReturnType, b2, bReturnType);
                }
                throw new AssertionError("b is " + b2.getClass());
            } else if (!(a2 instanceof i0)) {
                throw new IllegalArgumentException("Unexpected callable: " + a2.getClass());
            } else if (b2 instanceof i0) {
                i0 pa = (i0) a2;
                i0 pb = (i0) b2;
                if (!B(pa.getSetter(), pb.getSetter())) {
                    return false;
                }
                if (pa.K() && pb.K()) {
                    return b.l(a2.getTypeParameters(), b2.getTypeParameters()).b(aReturnType, bReturnType);
                }
                if ((pa.K() || !pb.K()) && H(a2, aReturnType, b2, bReturnType)) {
                    return true;
                }
                return false;
            } else {
                throw new AssertionError("b is " + b2.getClass());
            }
        }
    }

    private static boolean I(@NotNull q a2, @NotNull q b2) {
        if (a2 == null) {
            a(65);
        }
        if (b2 == null) {
            a(66);
        }
        Integer result = z0.d(a2.getVisibility(), b2.getVisibility());
        return result == null || result.intValue() >= 0;
    }

    private static boolean B(@Nullable h0 a2, @Nullable h0 b2) {
        if (a2 == null || b2 == null) {
            return true;
        }
        return I(a2, b2);
    }

    private static boolean D(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a candidate, @NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.a> descriptors) {
        if (candidate == null) {
            a(67);
        }
        if (descriptors == null) {
            a(68);
        }
        for (kotlin.reflect.jvm.internal.impl.descriptors.a descriptor : descriptors) {
            if (!C(candidate, descriptor)) {
                return false;
            }
        }
        return true;
    }

    private static boolean H(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.a a2, @NotNull b0 aReturnType, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.a b2, @NotNull b0 bReturnType) {
        if (a2 == null) {
            a(69);
        }
        if (aReturnType == null) {
            a(70);
        }
        if (b2 == null) {
            a(71);
        }
        if (bReturnType == null) {
            a(72);
        }
        return b.l(a2.getTypeParameters(), b2.getTypeParameters()).d(aReturnType, bReturnType);
    }

    @NotNull
    public static <H> H M(@NotNull Collection<H> overridables, @NotNull l<H, kotlin.reflect.jvm.internal.impl.descriptors.a> descriptorByHandle) {
        if (overridables == null) {
            a(73);
        }
        if (descriptorByHandle == null) {
            a(74);
        }
        if (overridables.isEmpty()) {
            throw new AssertionError("Should have at least one overridable descriptor");
        } else if (overridables.size() == 1) {
            H R = y.R(overridables);
            if (R == null) {
                a(75);
            }
            return R;
        } else {
            Collection<H> candidates = new ArrayList<>(2);
            List<CallableDescriptor> callableMemberDescriptors = y.g0(overridables, descriptorByHandle);
            H transitivelyMostSpecific = y.R(overridables);
            kotlin.reflect.jvm.internal.impl.descriptors.a transitivelyMostSpecificDescriptor = descriptorByHandle.invoke(transitivelyMostSpecific);
            for (H overridable : overridables) {
                kotlin.reflect.jvm.internal.impl.descriptors.a descriptor = descriptorByHandle.invoke(overridable);
                if (D(descriptor, callableMemberDescriptors)) {
                    candidates.add(overridable);
                }
                if (C(descriptor, transitivelyMostSpecificDescriptor) && !C(transitivelyMostSpecificDescriptor, descriptor)) {
                    transitivelyMostSpecific = overridable;
                }
            }
            if (candidates.isEmpty()) {
                if (transitivelyMostSpecific == null) {
                    a(76);
                }
                return transitivelyMostSpecific;
            } else if (candidates.size() == 1) {
                H R2 = y.R(candidates);
                if (R2 == null) {
                    a(77);
                }
                return R2;
            } else {
                H firstNonFlexible = null;
                Iterator i$ = candidates.iterator();
                while (true) {
                    if (!i$.hasNext()) {
                        break;
                    }
                    H candidate = i$.next();
                    if (!kotlin.reflect.jvm.internal.impl.types.y.b(descriptorByHandle.invoke(candidate).getReturnType())) {
                        firstNonFlexible = candidate;
                        break;
                    }
                }
                if (firstNonFlexible != null) {
                    return firstNonFlexible;
                }
                H R3 = y.R(candidates);
                if (R3 == null) {
                    a(79);
                }
                return R3;
            }
        }
    }

    private static void j(@NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> overridables, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current, @NotNull h strategy) {
        if (overridables == null) {
            a(80);
        }
        if (current == null) {
            a(81);
        }
        if (strategy == null) {
            a(82);
        }
        Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> u = u(current, overridables);
        boolean allInvisible = u.isEmpty();
        Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> collection = allInvisible ? overridables : u;
        w modality = o(collection, current);
        a1 visibility = allInvisible ? z0.h : z0.g;
        b.a aVar = b.a.FAKE_OVERRIDE;
        kotlin.reflect.jvm.internal.impl.descriptors.b fakeOverride = ((kotlin.reflect.jvm.internal.impl.descriptors.b) M(collection, new e())).B0(current, modality, visibility, aVar, false);
        strategy.d(fakeOverride, collection);
        if (!fakeOverride.d().isEmpty()) {
            strategy.a(fakeOverride);
            return;
        }
        throw new AssertionError("Overridden descriptors should be set for " + aVar);
    }

    /* compiled from: OverridingUtil */
    public static final class e implements l<kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.reflect.jvm.internal.impl.descriptors.a> {
        e() {
        }

        /* renamed from: a */
        public kotlin.reflect.jvm.internal.impl.descriptors.b invoke(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
            return descriptor;
        }
    }

    @NotNull
    private static w o(@NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> descriptors, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current) {
        if (descriptors == null) {
            a(83);
        }
        if (current == null) {
            a(84);
        }
        boolean hasOpen = false;
        boolean hasAbstract = false;
        for (kotlin.reflect.jvm.internal.impl.descriptors.b descriptor : descriptors) {
            switch (C0411i.c[descriptor.p().ordinal()]) {
                case 1:
                    w wVar = w.FINAL;
                    if (wVar == null) {
                        a(85);
                    }
                    return wVar;
                case 2:
                    throw new IllegalStateException("Member cannot have SEALED modality: " + descriptor);
                case 3:
                    hasOpen = true;
                    break;
                case 4:
                    hasAbstract = true;
                    break;
            }
        }
        boolean transformAbstractToClassModality = (!current.d0() || current.p() == w.ABSTRACT || current.p() == w.SEALED) ? false : true;
        if (hasOpen && !hasAbstract) {
            w wVar2 = w.OPEN;
            if (wVar2 == null) {
                a(86);
            }
            return wVar2;
        } else if (hasOpen || !hasAbstract) {
            Set<CallableMemberDescriptor> allOverriddenDeclarations = new HashSet<>();
            for (kotlin.reflect.jvm.internal.impl.descriptors.b descriptor2 : descriptors) {
                allOverriddenDeclarations.addAll(A(descriptor2));
            }
            return z(s(allOverriddenDeclarations), transformAbstractToClassModality, current.p());
        } else {
            w p = transformAbstractToClassModality ? current.p() : w.ABSTRACT;
            if (p == null) {
                a(87);
            }
            return p;
        }
    }

    /* renamed from: kotlin.reflect.jvm.internal.impl.resolve.i$i  reason: collision with other inner class name */
    /* compiled from: OverridingUtil */
    public static /* synthetic */ class C0411i {
        static final /* synthetic */ int[] a;
        static final /* synthetic */ int[] b;
        static final /* synthetic */ int[] c;

        static {
            int[] iArr = new int[w.values().length];
            c = iArr;
            try {
                iArr[w.FINAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                c[w.SEALED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                c[w.OPEN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                c[w.ABSTRACT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            int[] iArr2 = new int[j.a.values().length];
            b = iArr2;
            try {
                iArr2[j.a.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                b[j.a.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                b[j.a.INCOMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            int[] iArr3 = new int[d.b.values().length];
            a = iArr3;
            try {
                iArr3[d.b.OVERRIDABLE.ordinal()] = 1;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[d.b.CONFLICT.ordinal()] = 2;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[d.b.INCOMPATIBLE.ordinal()] = 3;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[d.b.UNKNOWN.ordinal()] = 4;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    @NotNull
    private static w z(@NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> descriptors, boolean transformAbstractToClassModality, @NotNull w classModality) {
        if (descriptors == null) {
            a(88);
        }
        if (classModality == null) {
            a(89);
        }
        w result = w.ABSTRACT;
        for (kotlin.reflect.jvm.internal.impl.descriptors.b descriptor : descriptors) {
            w effectiveModality = (!transformAbstractToClassModality || descriptor.p() != w.ABSTRACT) ? descriptor.p() : classModality;
            if (effectiveModality.compareTo(result) < 0) {
                result = effectiveModality;
            }
        }
        if (result == null) {
            a(90);
        }
        return result;
    }

    /* compiled from: OverridingUtil */
    public static final class f implements l<kotlin.reflect.jvm.internal.impl.descriptors.b, Boolean> {
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.e c;

        f(kotlin.reflect.jvm.internal.impl.descriptors.e eVar) {
            this.c = eVar;
        }

        /* renamed from: a */
        public Boolean invoke(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
            return Boolean.valueOf(!z0.h(descriptor.getVisibility()) && z0.i(descriptor, this.c));
        }
    }

    @NotNull
    private static Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> u(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.e current, @NotNull Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> toFilter) {
        if (current == null) {
            a(91);
        }
        if (toFilter == null) {
            a(92);
        }
        List<T> Q = y.Q(toFilter, new f(current));
        if (Q == null) {
            a(93);
        }
        return Q;
    }

    @NotNull
    public static <H> Collection<H> q(@NotNull H overrider, @NotNull Collection<H> extractFrom, @NotNull l<H, kotlin.reflect.jvm.internal.impl.descriptors.a> descriptorByHandle, @NotNull l<H, x> onConflict) {
        if (overrider == null) {
            a(94);
        }
        if (extractFrom == null) {
            a(95);
        }
        if (descriptorByHandle == null) {
            a(96);
        }
        if (onConflict == null) {
            a(97);
        }
        Collection<H> overridable = new ArrayList<>();
        overridable.add(overrider);
        kotlin.reflect.jvm.internal.impl.descriptors.a overriderDescriptor = descriptorByHandle.invoke(overrider);
        Iterator<H> iterator = extractFrom.iterator();
        while (iterator.hasNext()) {
            H candidate = iterator.next();
            kotlin.reflect.jvm.internal.impl.descriptors.a candidateDescriptor = descriptorByHandle.invoke(candidate);
            if (overrider == candidate) {
                iterator.remove();
            } else {
                j.a finalResult = y(overriderDescriptor, candidateDescriptor);
                if (finalResult == j.a.OVERRIDABLE) {
                    overridable.add(candidate);
                    iterator.remove();
                } else if (finalResult == j.a.CONFLICT) {
                    onConflict.invoke(candidate);
                    iterator.remove();
                }
            }
        }
        return overridable;
    }

    @Nullable
    public static j.a y(kotlin.reflect.jvm.internal.impl.descriptors.a overriderDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.a candidateDescriptor) {
        i iVar = b;
        j.a result1 = iVar.E(candidateDescriptor, overriderDescriptor, (kotlin.reflect.jvm.internal.impl.descriptors.e) null).c();
        j.a result2 = iVar.E(overriderDescriptor, candidateDescriptor, (kotlin.reflect.jvm.internal.impl.descriptors.e) null).c();
        j.a aVar = j.a.OVERRIDABLE;
        if (result1 == aVar && result2 == aVar) {
            return aVar;
        }
        j.a aVar2 = j.a.CONFLICT;
        return (result1 == aVar2 || result2 == aVar2) ? aVar2 : j.a.INCOMPATIBLE;
    }

    @NotNull
    private static Collection<kotlin.reflect.jvm.internal.impl.descriptors.b> r(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b overrider, @NotNull Queue<kotlin.reflect.jvm.internal.impl.descriptors.b> extractFrom, @NotNull h strategy) {
        if (overrider == null) {
            a(99);
        }
        if (extractFrom == null) {
            a(100);
        }
        if (strategy == null) {
            a(101);
        }
        return q(overrider, extractFrom, new g(), new h(strategy, overrider));
    }

    /* compiled from: OverridingUtil */
    public static final class g implements l<kotlin.reflect.jvm.internal.impl.descriptors.b, kotlin.reflect.jvm.internal.impl.descriptors.a> {
        g() {
        }

        /* renamed from: a */
        public kotlin.reflect.jvm.internal.impl.descriptors.a invoke(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
            return descriptor;
        }
    }

    /* compiled from: OverridingUtil */
    public static final class h implements l<kotlin.reflect.jvm.internal.impl.descriptors.b, x> {
        final /* synthetic */ h c;
        final /* synthetic */ kotlin.reflect.jvm.internal.impl.descriptors.b d;

        h(h hVar, kotlin.reflect.jvm.internal.impl.descriptors.b bVar) {
            this.c = hVar;
            this.d = bVar;
        }

        /* renamed from: a */
        public x invoke(kotlin.reflect.jvm.internal.impl.descriptors.b descriptor) {
            this.c.b(this.d, descriptor);
            return x.a;
        }
    }

    public static void L(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b memberDescriptor, @Nullable l<kotlin.reflect.jvm.internal.impl.descriptors.b, x> cannotInferVisibility) {
        a1 visibilityToInherit;
        if (memberDescriptor == null) {
            a(102);
        }
        for (kotlin.reflect.jvm.internal.impl.descriptors.b descriptor : memberDescriptor.d()) {
            if (descriptor.getVisibility() == z0.g) {
                L(descriptor, cannotInferVisibility);
            }
        }
        if (memberDescriptor.getVisibility() == z0.g) {
            a1 maxVisibility = i(memberDescriptor);
            if (maxVisibility == null) {
                if (cannotInferVisibility != null) {
                    cannotInferVisibility.invoke(memberDescriptor);
                }
                visibilityToInherit = z0.e;
            } else {
                visibilityToInherit = maxVisibility;
            }
            if (memberDescriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.impl.b0) {
                ((kotlin.reflect.jvm.internal.impl.descriptors.impl.b0) memberDescriptor).T0(visibilityToInherit);
                for (h0 accessor : ((i0) memberDescriptor).s()) {
                    L(accessor, maxVisibility == null ? null : cannotInferVisibility);
                }
            } else if (memberDescriptor instanceof kotlin.reflect.jvm.internal.impl.descriptors.impl.p) {
                ((kotlin.reflect.jvm.internal.impl.descriptors.impl.p) memberDescriptor).b1(visibilityToInherit);
            } else if (memberDescriptor instanceof a0) {
                a0 propertyAccessorDescriptor = (a0) memberDescriptor;
                propertyAccessorDescriptor.H0(visibilityToInherit);
                if (visibilityToInherit != propertyAccessorDescriptor.Q().getVisibility()) {
                    propertyAccessorDescriptor.C0(false);
                }
            } else {
                throw new AssertionError();
            }
        }
    }

    @Nullable
    private static a1 i(@NotNull kotlin.reflect.jvm.internal.impl.descriptors.b memberDescriptor) {
        if (memberDescriptor == null) {
            a(103);
        }
        Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> d2 = memberDescriptor.d();
        a1 maxVisibility = v(d2);
        if (maxVisibility == null) {
            return null;
        }
        if (memberDescriptor.h() != b.a.FAKE_OVERRIDE) {
            return maxVisibility.e();
        }
        for (kotlin.reflect.jvm.internal.impl.descriptors.b overridden : d2) {
            if (overridden.p() != w.ABSTRACT && !overridden.getVisibility().equals(maxVisibility)) {
                return null;
            }
        }
        return maxVisibility;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0062  */
    @org.jetbrains.annotations.Nullable
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static kotlin.reflect.jvm.internal.impl.descriptors.a1 v(@org.jetbrains.annotations.NotNull java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.descriptors.b> r7) {
        /*
            if (r7 != 0) goto L_0x0007
            r0 = 104(0x68, float:1.46E-43)
            a(r0)
        L_0x0007:
            boolean r0 = r7.isEmpty()
            if (r0 == 0) goto L_0x0010
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r0 = kotlin.reflect.jvm.internal.impl.descriptors.z0.l
            return r0
        L_0x0010:
            r0 = 0
            java.util.Iterator r1 = r7.iterator()
        L_0x0015:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x0054
            java.lang.Object r2 = r1.next()
            kotlin.reflect.jvm.internal.impl.descriptors.b r2 = (kotlin.reflect.jvm.internal.impl.descriptors.b) r2
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r3 = r2.getVisibility()
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r4 = kotlin.reflect.jvm.internal.impl.descriptors.z0.g
            if (r3 == r4) goto L_0x003d
            if (r0 != 0) goto L_0x002d
            r0 = r3
            goto L_0x0015
        L_0x002d:
            java.lang.Integer r4 = kotlin.reflect.jvm.internal.impl.descriptors.z0.d(r3, r0)
            if (r4 != 0) goto L_0x0035
            r0 = 0
            goto L_0x003c
        L_0x0035:
            int r5 = r4.intValue()
            if (r5 <= 0) goto L_0x003c
            r0 = r3
        L_0x003c:
            goto L_0x0015
        L_0x003d:
            java.lang.AssertionError r4 = new java.lang.AssertionError
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Visibility should have been computed for "
            r5.append(r6)
            r5.append(r2)
            java.lang.String r5 = r5.toString()
            r4.<init>(r5)
            throw r4
        L_0x0054:
            r1 = 0
            if (r0 != 0) goto L_0x0058
            return r1
        L_0x0058:
            java.util.Iterator r2 = r7.iterator()
        L_0x005c:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x007b
            java.lang.Object r3 = r2.next()
            kotlin.reflect.jvm.internal.impl.descriptors.b r3 = (kotlin.reflect.jvm.internal.impl.descriptors.b) r3
            kotlin.reflect.jvm.internal.impl.descriptors.a1 r4 = r3.getVisibility()
            java.lang.Integer r4 = kotlin.reflect.jvm.internal.impl.descriptors.z0.d(r0, r4)
            if (r4 == 0) goto L_0x007a
            int r5 = r4.intValue()
            if (r5 >= 0) goto L_0x0079
            goto L_0x007a
        L_0x0079:
            goto L_0x005c
        L_0x007a:
            return r1
        L_0x007b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.resolve.i.v(java.util.Collection):kotlin.reflect.jvm.internal.impl.descriptors.a1");
    }

    /* compiled from: OverridingUtil */
    public static class j {
        private static final j a = new j(a.OVERRIDABLE, "SUCCESS");
        private final a b;
        private final String c;

        /* compiled from: OverridingUtil */
        public enum a {
            OVERRIDABLE,
            INCOMPATIBLE,
            CONFLICT
        }

        private static /* synthetic */ void a(int i) {
            String str;
            int i2;
            Throwable th;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    str = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
                    break;
                default:
                    str = "@NotNull method %s.%s must not return null";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    i2 = 3;
                    break;
                default:
                    i2 = 2;
                    break;
            }
            Object[] objArr = new Object[i2];
            switch (i) {
                case 1:
                case 2:
                case 4:
                    objArr[0] = "debugMessage";
                    break;
                case 3:
                    objArr[0] = FirebaseAnalytics.Param.SUCCESS;
                    break;
                default:
                    objArr[0] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil$OverrideCompatibilityInfo";
                    break;
            }
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    objArr[1] = "kotlin/reflect/jvm/internal/impl/resolve/OverridingUtil$OverrideCompatibilityInfo";
                    break;
                case 5:
                    objArr[1] = "getResult";
                    break;
                case 6:
                    objArr[1] = "getDebugMessage";
                    break;
                default:
                    objArr[1] = FirebaseAnalytics.Param.SUCCESS;
                    break;
            }
            switch (i) {
                case 1:
                    objArr[2] = "incompatible";
                    break;
                case 2:
                    objArr[2] = "conflict";
                    break;
                case 3:
                case 4:
                    objArr[2] = "<init>";
                    break;
            }
            String format = String.format(str, objArr);
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                    th = new IllegalArgumentException(format);
                    break;
                default:
                    th = new IllegalStateException(format);
                    break;
            }
            throw th;
        }

        @NotNull
        public static j e() {
            j jVar = a;
            if (jVar == null) {
                a(0);
            }
            return jVar;
        }

        @NotNull
        public static j d(@NotNull String debugMessage) {
            if (debugMessage == null) {
                a(1);
            }
            return new j(a.INCOMPATIBLE, debugMessage);
        }

        @NotNull
        public static j b(@NotNull String debugMessage) {
            if (debugMessage == null) {
                a(2);
            }
            return new j(a.CONFLICT, debugMessage);
        }

        public j(@NotNull a success, @NotNull String debugMessage) {
            if (success == null) {
                a(3);
            }
            if (debugMessage == null) {
                a(4);
            }
            this.b = success;
            this.c = debugMessage;
        }

        @NotNull
        public a c() {
            a aVar = this.b;
            if (aVar == null) {
                a(5);
            }
            return aVar;
        }
    }
}
