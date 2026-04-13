package org.greenrobot.eventbus;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.greenrobot.eventbus.meta.b;

/* compiled from: SubscriberMethodFinder */
public class o {
    private static final Map<Class<?>, List<n>> a = new ConcurrentHashMap();
    private static final a[] b = new a[4];
    private List<b> c;
    private final boolean d;
    private final boolean e;

    o(List<b> subscriberInfoIndexes, boolean strictMethodVerification, boolean ignoreGeneratedIndex) {
        this.c = subscriberInfoIndexes;
        this.d = strictMethodVerification;
        this.e = ignoreGeneratedIndex;
    }

    /* access modifiers changed from: package-private */
    public List<n> a(Class<?> subscriberClass) {
        List<n> list;
        Map<Class<?>, List<n>> map = a;
        List<SubscriberMethod> subscriberMethods = map.get(subscriberClass);
        if (subscriberMethods != null) {
            return subscriberMethods;
        }
        if (this.e) {
            list = c(subscriberClass);
        } else {
            list = b(subscriberClass);
        }
        if (!list.isEmpty()) {
            map.put(subscriberClass, list);
            return list;
        }
        throw new EventBusException("Subscriber " + subscriberClass + " and its super classes have no public methods with the @Subscribe annotation");
    }

    private List<n> b(Class<?> subscriberClass) {
        a findState = g();
        findState.c(subscriberClass);
        while (findState.f != null) {
            org.greenrobot.eventbus.meta.a f = f(findState);
            findState.h = f;
            if (f != null) {
                for (n subscriberMethod : f.a()) {
                    if (findState.a(subscriberMethod.a, subscriberMethod.c)) {
                        findState.a.add(subscriberMethod);
                    }
                }
            } else {
                d(findState);
            }
            findState.d();
        }
        return e(findState);
    }

    private List<n> e(a findState) {
        List<SubscriberMethod> subscriberMethods = new ArrayList<>(findState.a);
        findState.e();
        synchronized (b) {
            int i = 0;
            while (true) {
                if (i >= 4) {
                    break;
                }
                a[] aVarArr = b;
                if (aVarArr[i] == null) {
                    aVarArr[i] = findState;
                    break;
                }
                i++;
            }
        }
        return subscriberMethods;
    }

    private a g() {
        synchronized (b) {
            for (int i = 0; i < 4; i++) {
                a[] aVarArr = b;
                a state = aVarArr[i];
                if (state != null) {
                    aVarArr[i] = null;
                    return state;
                }
            }
            return new a();
        }
    }

    private org.greenrobot.eventbus.meta.a f(a findState) {
        org.greenrobot.eventbus.meta.a aVar = findState.h;
        if (!(aVar == null || aVar.c() == null)) {
            org.greenrobot.eventbus.meta.a superclassInfo = findState.h.c();
            if (findState.f == superclassInfo.b()) {
                return superclassInfo;
            }
        }
        List<b> list = this.c;
        if (list == null) {
            return null;
        }
        for (b index : list) {
            org.greenrobot.eventbus.meta.a info = index.a(findState.f);
            if (info != null) {
                return info;
            }
        }
        return null;
    }

    private List<n> c(Class<?> subscriberClass) {
        a findState = g();
        findState.c(subscriberClass);
        while (findState.f != null) {
            d(findState);
            findState.d();
        }
        return e(findState);
    }

    private void d(a findState) {
        Method[] methods;
        a aVar = findState;
        int i = 1;
        try {
            methods = aVar.f.getDeclaredMethods();
        } catch (Throwable th) {
            Method[] methods2 = aVar.f.getMethods();
            aVar.g = true;
            methods = methods2;
        }
        int length = methods.length;
        char c2 = 0;
        int i2 = 0;
        while (i2 < length) {
            Method method = methods[i2];
            int modifiers = method.getModifiers();
            if ((modifiers & 1) != 0 && (modifiers & 5192) == 0) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == i) {
                    l subscribeAnnotation = (l) method.getAnnotation(l.class);
                    if (subscribeAnnotation != null) {
                        Class<?> eventType = parameterTypes[c2];
                        if (aVar.a(method, eventType)) {
                            ThreadMode threadMode = subscribeAnnotation.threadMode();
                            n nVar = r7;
                            List<n> list = aVar.a;
                            Class<?> cls = eventType;
                            n nVar2 = new n(method, eventType, threadMode, subscribeAnnotation.priority(), subscribeAnnotation.sticky());
                            list.add(nVar);
                        }
                    }
                } else if (this.d && method.isAnnotationPresent(l.class)) {
                    throw new EventBusException("@Subscribe method " + (method.getDeclaringClass().getName() + "." + method.getName()) + "must have exactly 1 parameter but has " + parameterTypes.length);
                }
            } else if (this.d && method.isAnnotationPresent(l.class)) {
                throw new EventBusException((method.getDeclaringClass().getName() + "." + method.getName()) + " is a illegal @Subscribe method: must be public, non-static, and non-abstract");
            }
            i2++;
            i = 1;
            c2 = 0;
        }
    }

    /* compiled from: SubscriberMethodFinder */
    public static class a {
        final List<n> a = new ArrayList();
        final Map<Class, Object> b = new HashMap();
        final Map<String, Class> c = new HashMap();
        final StringBuilder d = new StringBuilder(128);
        Class<?> e;
        Class<?> f;
        boolean g;
        org.greenrobot.eventbus.meta.a h;

        a() {
        }

        /* access modifiers changed from: package-private */
        public void c(Class<?> subscriberClass) {
            this.f = subscriberClass;
            this.e = subscriberClass;
            this.g = false;
            this.h = null;
        }

        /* access modifiers changed from: package-private */
        public void e() {
            this.a.clear();
            this.b.clear();
            this.c.clear();
            this.d.setLength(0);
            this.e = null;
            this.f = null;
            this.g = false;
            this.h = null;
        }

        /* access modifiers changed from: package-private */
        public boolean a(Method method, Class<?> eventType) {
            Object existing = this.b.put(eventType, method);
            if (existing == null) {
                return true;
            }
            if (existing instanceof Method) {
                if (b((Method) existing, eventType)) {
                    this.b.put(eventType, this);
                } else {
                    throw new IllegalStateException();
                }
            }
            return b(method, eventType);
        }

        private boolean b(Method method, Class<?> eventType) {
            this.d.setLength(0);
            this.d.append(method.getName());
            StringBuilder sb = this.d;
            sb.append('>');
            sb.append(eventType.getName());
            String methodKey = this.d.toString();
            Class<?> methodClass = method.getDeclaringClass();
            Class<?> methodClassOld = this.c.put(methodKey, methodClass);
            if (methodClassOld == null || methodClassOld.isAssignableFrom(methodClass)) {
                return true;
            }
            this.c.put(methodKey, methodClassOld);
            return false;
        }

        /* access modifiers changed from: package-private */
        public void d() {
            if (this.g) {
                this.f = null;
                return;
            }
            Class<? super Object> superclass = this.f.getSuperclass();
            this.f = superclass;
            String clazzName = superclass.getName();
            if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                this.f = null;
            }
        }
    }
}
