package retrofit2;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.e;
import retrofit2.h;

/* compiled from: Platform */
public class p {
    private static final p a = e();
    private final boolean b;
    @Nullable
    private final Constructor<MethodHandles.Lookup> c;

    static p f() {
        return a;
    }

    private static p e() {
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            return new a();
        }
        return new p(true);
    }

    p(boolean hasJava8Types) {
        this.b = hasJava8Types;
        Constructor<MethodHandles.Lookup> lookupConstructor = null;
        if (hasJava8Types) {
            Class<MethodHandles.Lookup> cls = MethodHandles.Lookup.class;
            try {
                lookupConstructor = cls.getDeclaredConstructor(new Class[]{Class.class, Integer.TYPE});
                lookupConstructor.setAccessible(true);
            } catch (NoClassDefFoundError | NoSuchMethodException e) {
            }
        }
        this.c = lookupConstructor;
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public Executor b() {
        return null;
    }

    /* access modifiers changed from: package-private */
    public List<? extends e.a> a(@Nullable Executor callbackExecutor) {
        i executorFactory = new i(callbackExecutor);
        if (!this.b) {
            return Collections.singletonList(executorFactory);
        }
        return Arrays.asList(new e.a[]{g.a, executorFactory});
    }

    /* access modifiers changed from: package-private */
    public List<? extends h.a> c() {
        return this.b ? Collections.singletonList(n.a) : Collections.emptyList();
    }

    /* access modifiers changed from: package-private */
    public int d() {
        return this.b ? 1 : 0;
    }

    /* access modifiers changed from: package-private */
    @IgnoreJRERequirement
    public boolean h(Method method) {
        return this.b && method.isDefault();
    }

    /* access modifiers changed from: package-private */
    @Nullable
    @IgnoreJRERequirement
    public Object g(Method method, Class<?> declaringClass, Object object, Object... args) {
        MethodHandles.Lookup lookup;
        Constructor<MethodHandles.Lookup> constructor = this.c;
        if (constructor != null) {
            lookup = constructor.newInstance(new Object[]{declaringClass, -1});
        } else {
            lookup = MethodHandles.lookup();
        }
        return lookup.unreflectSpecial(method, declaringClass).bindTo(object).invokeWithArguments(args);
    }

    /* compiled from: Platform */
    public static final class a extends p {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a() {
            super(Build.VERSION.SDK_INT >= 24);
        }

        public Executor b() {
            return new C0492a();
        }

        /* access modifiers changed from: package-private */
        @Nullable
        public Object g(Method method, Class<?> declaringClass, Object object, Object... args) {
            if (Build.VERSION.SDK_INT >= 26) {
                return p.super.g(method, declaringClass, object, args);
            }
            throw new UnsupportedOperationException("Calling default methods on API 24 and 25 is not supported");
        }

        /* renamed from: retrofit2.p$a$a  reason: collision with other inner class name */
        /* compiled from: Platform */
        public static final class C0492a implements Executor {
            private final Handler c = new Handler(Looper.getMainLooper());

            C0492a() {
            }

            public void execute(Runnable r) {
                this.c.post(r);
            }
        }
    }
}
