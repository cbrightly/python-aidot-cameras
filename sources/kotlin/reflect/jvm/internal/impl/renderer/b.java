package kotlin.reflect.jvm.internal.impl.renderer;

import java.util.ArrayList;
import kotlin.collections.w;
import kotlin.jvm.internal.k;
import kotlin.reflect.jvm.internal.impl.descriptors.b0;
import kotlin.reflect.jvm.internal.impl.descriptors.e;
import kotlin.reflect.jvm.internal.impl.descriptors.h;
import kotlin.reflect.jvm.internal.impl.descriptors.m;
import kotlin.reflect.jvm.internal.impl.descriptors.t0;
import kotlin.reflect.jvm.internal.impl.name.f;
import org.jetbrains.annotations.NotNull;

/* compiled from: ClassifierNamePolicy.kt */
public interface b {
    @NotNull
    String a(@NotNull h hVar, @NotNull c cVar);

    /* renamed from: kotlin.reflect.jvm.internal.impl.renderer.b$b  reason: collision with other inner class name */
    /* compiled from: ClassifierNamePolicy.kt */
    public static final class C0402b implements b {
        public static final C0402b a = new C0402b();

        private C0402b() {
        }

        @NotNull
        public String a(@NotNull h classifier, @NotNull c renderer) {
            k.f(classifier, "classifier");
            k.f(renderer, "renderer");
            if (classifier instanceof t0) {
                f name = ((t0) classifier).getName();
                k.b(name, "classifier.name");
                return renderer.w(name, false);
            }
            ArrayList qualifiedNameElements = new ArrayList();
            m current = classifier;
            do {
                qualifiedNameElements.add(current.getName());
                current = current.b();
            } while (current instanceof e);
            return q.c(w.H(qualifiedNameElements));
        }
    }

    /* compiled from: ClassifierNamePolicy.kt */
    public static final class a implements b {
        public static final a a = new a();

        private a() {
        }

        @NotNull
        public String a(@NotNull h classifier, @NotNull c renderer) {
            k.f(classifier, "classifier");
            k.f(renderer, "renderer");
            if (classifier instanceof t0) {
                f name = ((t0) classifier).getName();
                k.b(name, "classifier.name");
                return renderer.w(name, false);
            }
            kotlin.reflect.jvm.internal.impl.name.c m = kotlin.reflect.jvm.internal.impl.resolve.c.m(classifier);
            k.b(m, "DescriptorUtils.getFqName(classifier)");
            return renderer.v(m);
        }
    }

    /* compiled from: ClassifierNamePolicy.kt */
    public static final class c implements b {
        public static final c a = new c();

        private c() {
        }

        @NotNull
        public String a(@NotNull h classifier, @NotNull c renderer) {
            k.f(classifier, "classifier");
            k.f(renderer, "renderer");
            return b(classifier);
        }

        private final String b(h descriptor) {
            f name = descriptor.getName();
            k.b(name, "descriptor.name");
            String nameString = q.b(name);
            if (descriptor instanceof t0) {
                return nameString;
            }
            m b = descriptor.b();
            k.b(b, "descriptor.containingDeclaration");
            String qualifier = c(b);
            if (qualifier == null || !(!k.a(qualifier, ""))) {
                return nameString;
            }
            return qualifier + "." + nameString;
        }

        private final String c(m descriptor) {
            if (descriptor instanceof e) {
                return b((h) descriptor);
            }
            if (!(descriptor instanceof b0)) {
                return null;
            }
            kotlin.reflect.jvm.internal.impl.name.c j = ((b0) descriptor).e().j();
            k.b(j, "descriptor.fqName.toUnsafe()");
            return q.a(j);
        }
    }
}
