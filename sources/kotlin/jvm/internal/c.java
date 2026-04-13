package kotlin.jvm.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.reflect.b;
import kotlin.reflect.e;
import kotlin.reflect.j;
import kotlin.reflect.n;
import kotlin.reflect.o;
import kotlin.reflect.s;

/* compiled from: CallableReference */
public abstract class c implements b, Serializable {
    public static final Object NO_RECEIVER = a.c;
    private final boolean isTopLevel;
    private final String name;
    private final Class owner;
    protected final Object receiver;
    private transient b reflected;
    private final String signature;

    /* access modifiers changed from: protected */
    public abstract b computeReflected();

    /* compiled from: CallableReference */
    public static class a implements Serializable {
        /* access modifiers changed from: private */
        public static final a c = new a();

        private a() {
        }

        private Object readResolve() {
            return c;
        }
    }

    public c() {
        this(NO_RECEIVER);
    }

    protected c(Object receiver2) {
        this(receiver2, (Class) null, (String) null, (String) null, false);
    }

    protected c(Object receiver2, Class owner2, String name2, String signature2, boolean isTopLevel2) {
        this.receiver = receiver2;
        this.owner = owner2;
        this.name = name2;
        this.signature = signature2;
        this.isTopLevel = isTopLevel2;
    }

    public Object getBoundReceiver() {
        return this.receiver;
    }

    public b compute() {
        b result = this.reflected;
        if (result != null) {
            return result;
        }
        b result2 = computeReflected();
        this.reflected = result2;
        return result2;
    }

    /* access modifiers changed from: protected */
    public b getReflected() {
        b result = compute();
        if (result != this) {
            return result;
        }
        throw new kotlin.jvm.b();
    }

    public e getOwner() {
        Class cls = this.owner;
        if (cls == null) {
            return null;
        }
        return this.isTopLevel ? a0.c(cls) : a0.b(cls);
    }

    public String getName() {
        return this.name;
    }

    public String getSignature() {
        return this.signature;
    }

    public List<j> getParameters() {
        return getReflected().getParameters();
    }

    public n getReturnType() {
        return getReflected().getReturnType();
    }

    public List<Annotation> getAnnotations() {
        return getReflected().getAnnotations();
    }

    public List<o> getTypeParameters() {
        return getReflected().getTypeParameters();
    }

    public Object call(Object... args) {
        return getReflected().call(args);
    }

    public Object callBy(Map args) {
        return getReflected().callBy(args);
    }

    public s getVisibility() {
        return getReflected().getVisibility();
    }

    public boolean isFinal() {
        return getReflected().isFinal();
    }

    public boolean isOpen() {
        return getReflected().isOpen();
    }

    public boolean isAbstract() {
        return getReflected().isAbstract();
    }

    public boolean isSuspend() {
        return getReflected().isSuspend();
    }
}
