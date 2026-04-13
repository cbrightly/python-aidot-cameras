package io.ktor.server.engine;

import java.io.Closeable;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: OverridingClassLoader.kt */
public final class u extends ClassLoader implements Closeable {
    private final a c;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public u(@NotNull List<URL> classpath, @Nullable ClassLoader parentClassLoader) {
        super(parentClassLoader);
        k.f(classpath, "classpath");
        Object[] array = classpath.toArray(new URL[0]);
        if (array != null) {
            ClassLoader parent = getParent();
            k.b(parent, "parent");
            this.c = new a((URL[]) array, parent);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    /* access modifiers changed from: protected */
    @NotNull
    public synchronized Class<?> loadClass(@NotNull String name, boolean resolve) {
        k.f(name, "name");
        try {
        } catch (ClassNotFoundException e) {
            Class<?> loadClass = super.loadClass(name, resolve);
            k.b(loadClass, "super.loadClass(name, resolve)");
            return loadClass;
        }
        return this.c.findClass(name);
    }

    public void close() {
        this.c.close();
    }

    /* compiled from: OverridingClassLoader.kt */
    public static final class a extends URLClassLoader {
        private final ClassLoader c;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public a(@NotNull URL[] urls, @NotNull ClassLoader realParent) {
            super(urls, (ClassLoader) null);
            k.f(urls, "urls");
            k.f(realParent, "realParent");
            this.c = realParent;
        }

        @NotNull
        public Class<?> findClass(@NotNull String name) {
            k.f(name, "name");
            Class loaded = super.findLoadedClass(name);
            if (loaded != null) {
                return loaded;
            }
            try {
                Class<?> findClass = super.findClass(name);
                k.b(findClass, "super.findClass(name)");
                return findClass;
            } catch (ClassNotFoundException e) {
                Class<?> loadClass = this.c.loadClass(name);
                k.b(loadClass, "realParent.loadClass(name)");
                return loadClass;
            }
        }
    }
}
