package kotlin.reflect.jvm.internal.impl.builtins.jvm;

import java.io.InputStream;
import kotlin.collections.q;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.a0;
import kotlin.reflect.jvm.internal.impl.descriptors.deserialization.c;
import kotlin.reflect.jvm.internal.impl.descriptors.y;
import kotlin.reflect.jvm.internal.impl.incremental.components.c;
import kotlin.reflect.jvm.internal.impl.load.kotlin.n;
import kotlin.reflect.jvm.internal.impl.name.b;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.a;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.e;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.l;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.m;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.o;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.p;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.r;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.s;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.v;
import kotlin.reflect.jvm.internal.impl.storage.j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JvmBuiltInsPackageFragmentProvider.kt */
public final class g extends a {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public g(@NotNull j storageManager, @NotNull n finder, @NotNull y moduleDescriptor, @NotNull a0 notFoundClasses, @NotNull kotlin.reflect.jvm.internal.impl.descriptors.deserialization.a additionalClassPartsProvider, @NotNull c platformDependentDeclarationFilter, @NotNull m deserializationConfiguration, @NotNull kotlin.reflect.jvm.internal.impl.types.checker.n kotlinTypeChecker) {
        super(storageManager, finder, moduleDescriptor);
        j jVar = storageManager;
        y yVar = moduleDescriptor;
        a0 a0Var = notFoundClasses;
        k.f(jVar, "storageManager");
        k.f(finder, "finder");
        k.f(yVar, "moduleDescriptor");
        k.f(a0Var, "notFoundClasses");
        k.f(additionalClassPartsProvider, "additionalClassPartsProvider");
        k.f(platformDependentDeclarationFilter, "platformDependentDeclarationFilter");
        k.f(deserializationConfiguration, "deserializationConfiguration");
        k.f(kotlinTypeChecker, "kotlinTypeChecker");
        l lVar = r0;
        o oVar = r1;
        o oVar2 = new o(this);
        e eVar = r1;
        kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.a aVar = kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.a.n;
        e eVar2 = new e(yVar, a0Var, aVar);
        v.a aVar2 = v.a.a;
        r rVar = r.a;
        k.b(rVar, "ErrorReporter.DO_NOTHING");
        y yVar2 = yVar;
        j jVar2 = storageManager;
        y yVar3 = moduleDescriptor;
        l lVar2 = new l(jVar2, yVar3, deserializationConfiguration, oVar, eVar, this, aVar2, rVar, c.a.a, s.a.a, q.j(new kotlin.reflect.jvm.internal.impl.builtins.functions.a(jVar, yVar2), new d(storageManager, moduleDescriptor, (kotlin.jvm.functions.l) null, 4, (DefaultConstructorMarker) null)), notFoundClasses, kotlin.reflect.jvm.internal.impl.serialization.deserialization.k.a.a(), additionalClassPartsProvider, platformDependentDeclarationFilter, aVar.e(), kotlinTypeChecker);
        g(lVar);
    }

    /* access modifiers changed from: protected */
    @Nullable
    public p b(@NotNull b fqName) {
        k.f(fqName, "fqName");
        InputStream inputStream = d().b(fqName);
        if (inputStream == null) {
            return null;
        }
        return kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins.c.p3.a(fqName, f(), e(), inputStream, false);
    }
}
