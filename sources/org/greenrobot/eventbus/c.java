package org.greenrobot.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;

/* compiled from: EventBus */
public class c {
    public static String a = "EventBus";
    static volatile c b;
    private static final d c = new d();
    private static final Map<Class<?>, List<Class<?>>> d = new HashMap();
    private final Map<Class<?>, CopyOnWriteArrayList<p>> e;
    private final Map<Object, List<Class<?>>> f;
    private final Map<Class<?>, Object> g;
    private final ThreadLocal<C0169c> h;
    private final g i;
    private final k j;
    private final b k;
    private final a l;
    private final o m;
    private final ExecutorService n;
    private final boolean o;
    private final boolean p;
    private final boolean q;
    private final boolean r;
    private final boolean s;
    private final boolean t;
    private final int u;
    private final f v;

    /* compiled from: EventBus */
    public class a extends ThreadLocal<C0169c> {
        a() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public C0169c initialValue() {
            return new C0169c();
        }
    }

    public static c c() {
        if (b == null) {
            synchronized (c.class) {
                if (b == null) {
                    b = new c();
                }
            }
        }
        return b;
    }

    public c() {
        this(c);
    }

    c(d builder) {
        this.h = new a();
        this.v = builder.b();
        this.e = new HashMap();
        this.f = new HashMap();
        this.g = new ConcurrentHashMap();
        g c2 = builder.c();
        this.i = c2;
        this.j = c2 != null ? c2.a(this) : null;
        this.k = new b(this);
        this.l = new a(this);
        List<org.greenrobot.eventbus.meta.b> list = builder.k;
        this.u = list != null ? list.size() : 0;
        this.m = new o(builder.k, builder.i, builder.h);
        this.p = builder.b;
        this.q = builder.c;
        this.r = builder.d;
        this.s = builder.e;
        this.o = builder.f;
        this.t = builder.g;
        this.n = builder.j;
    }

    public void p(Object subscriber) {
        List<n> a2 = this.m.a(subscriber.getClass());
        synchronized (this) {
            for (n subscriberMethod : a2) {
                q(subscriber, subscriberMethod);
            }
        }
    }

    private void q(Object subscriber, n subscriberMethod) {
        Class<?> eventType = subscriberMethod.c;
        p newSubscription = new p(subscriber, subscriberMethod);
        CopyOnWriteArrayList<Subscription> subscriptions = this.e.get(eventType);
        if (subscriptions == null) {
            subscriptions = new CopyOnWriteArrayList<>();
            this.e.put(eventType, subscriptions);
        } else if (subscriptions.contains(newSubscription)) {
            throw new EventBusException("Subscriber " + subscriber.getClass() + " already registered to event " + eventType);
        }
        int size = subscriptions.size();
        int i2 = 0;
        while (true) {
            if (i2 > size) {
                break;
            } else if (i2 == size || subscriberMethod.d > ((p) subscriptions.get(i2)).b.d) {
                subscriptions.add(i2, newSubscription);
            } else {
                i2++;
            }
        }
        subscriptions.add(i2, newSubscription);
        List<Class<?>> subscribedEvents = this.f.get(subscriber);
        if (subscribedEvents == null) {
            subscribedEvents = new ArrayList<>();
            this.f.put(subscriber, subscribedEvents);
        }
        subscribedEvents.add(eventType);
        if (!subscriberMethod.e) {
            return;
        }
        if (this.t) {
            for (Map.Entry<Class<?>, Object> entry : this.g.entrySet()) {
                if (eventType.isAssignableFrom(entry.getKey())) {
                    b(newSubscription, entry.getValue());
                }
            }
            return;
        }
        b(newSubscription, this.g.get(eventType));
    }

    private void b(p newSubscription, Object stickyEvent) {
        if (stickyEvent != null) {
            o(newSubscription, stickyEvent, i());
        }
    }

    private boolean i() {
        g gVar = this.i;
        if (gVar != null) {
            return gVar.b();
        }
        return true;
    }

    public synchronized boolean j(Object subscriber) {
        return this.f.containsKey(subscriber);
    }

    private void s(Object subscriber, Class<?> eventType) {
        List<Subscription> subscriptions = this.e.get(eventType);
        if (subscriptions != null) {
            int size = subscriptions.size();
            int i2 = 0;
            while (i2 < size) {
                p subscription = (p) subscriptions.get(i2);
                if (subscription.a == subscriber) {
                    subscription.c = false;
                    subscriptions.remove(i2);
                    i2--;
                    size--;
                }
                i2++;
            }
        }
    }

    public synchronized void r(Object subscriber) {
        List<Class<?>> subscribedTypes = this.f.get(subscriber);
        if (subscribedTypes != null) {
            for (Class<?> eventType : subscribedTypes) {
                s(subscriber, eventType);
            }
            this.f.remove(subscriber);
        } else {
            f fVar = this.v;
            Level level = Level.WARNING;
            fVar.b(level, "Subscriber to unregister was not registered before: " + subscriber.getClass());
        }
    }

    public void l(Object event) {
        C0169c postingState = this.h.get();
        List<Object> eventQueue = postingState.a;
        eventQueue.add(event);
        if (!postingState.b) {
            postingState.c = i();
            postingState.b = true;
            if (!postingState.f) {
                while (!eventQueue.isEmpty()) {
                    try {
                        m(eventQueue.remove(0), postingState);
                    } finally {
                        postingState.b = false;
                        postingState.c = false;
                    }
                }
                return;
            }
            throw new EventBusException("Internal error. Abort state was not reset");
        }
    }

    private void m(Object event, C0169c postingState) {
        Class<?> eventClass = event.getClass();
        boolean subscriptionFound = false;
        if (this.t) {
            List<Class<?>> eventTypes = k(eventClass);
            int countTypes = eventTypes.size();
            for (int h2 = 0; h2 < countTypes; h2++) {
                subscriptionFound |= n(event, postingState, eventTypes.get(h2));
            }
        } else {
            subscriptionFound = n(event, postingState, eventClass);
        }
        if (!subscriptionFound) {
            if (this.q) {
                f fVar = this.v;
                Level level = Level.FINE;
                fVar.b(level, "No subscribers registered for event " + eventClass);
            }
            if (this.s && eventClass != h.class && eventClass != m.class) {
                l(new h(this, event));
            }
        }
    }

    private boolean n(Object event, C0169c postingState, Class<?> eventClass) {
        CopyOnWriteArrayList<Subscription> subscriptions;
        synchronized (this) {
            subscriptions = this.e.get(eventClass);
        }
        if (subscriptions == null || subscriptions.isEmpty()) {
            return false;
        }
        Iterator<Subscription> it = subscriptions.iterator();
        while (it.hasNext()) {
            p subscription = (p) it.next();
            postingState.e = event;
            postingState.d = subscription;
            try {
                o(subscription, event, postingState.c);
                if (postingState.f) {
                    return true;
                }
            } finally {
                postingState.e = null;
                postingState.d = null;
                postingState.f = false;
            }
        }
        return true;
    }

    /* compiled from: EventBus */
    public static /* synthetic */ class b {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[ThreadMode.values().length];
            a = iArr;
            try {
                iArr[ThreadMode.POSTING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[ThreadMode.MAIN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[ThreadMode.MAIN_ORDERED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[ThreadMode.BACKGROUND.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[ThreadMode.ASYNC.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void o(p subscription, Object event, boolean isMainThread) {
        switch (b.a[subscription.b.b.ordinal()]) {
            case 1:
                h(subscription, event);
                return;
            case 2:
                if (isMainThread) {
                    h(subscription, event);
                    return;
                } else {
                    this.j.a(subscription, event);
                    return;
                }
            case 3:
                k kVar = this.j;
                if (kVar != null) {
                    kVar.a(subscription, event);
                    return;
                } else {
                    h(subscription, event);
                    return;
                }
            case 4:
                if (isMainThread) {
                    this.k.a(subscription, event);
                    return;
                } else {
                    h(subscription, event);
                    return;
                }
            case 5:
                this.l.a(subscription, event);
                return;
            default:
                throw new IllegalStateException("Unknown thread mode: " + subscription.b.b);
        }
    }

    private static List<Class<?>> k(Class<?> eventClass) {
        List<Class<?>> eventTypes;
        Map<Class<?>, List<Class<?>>> map = d;
        synchronized (map) {
            eventTypes = map.get(eventClass);
            if (eventTypes == null) {
                eventTypes = new ArrayList<>();
                for (Class<? super Object> clazz = eventClass; clazz != null; clazz = clazz.getSuperclass()) {
                    eventTypes.add(clazz);
                    a(eventTypes, clazz.getInterfaces());
                }
                d.put(eventClass, eventTypes);
            }
        }
        return eventTypes;
    }

    static void a(List<Class<?>> eventTypes, Class<?>[] interfaces) {
        for (Class<?> interfaceClass : interfaces) {
            if (!eventTypes.contains(interfaceClass)) {
                eventTypes.add(interfaceClass);
                a(eventTypes, interfaceClass.getInterfaces());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void g(i pendingPost) {
        Object event = pendingPost.b;
        p subscription = pendingPost.c;
        i.b(pendingPost);
        if (subscription.c) {
            h(subscription, event);
        }
    }

    /* access modifiers changed from: package-private */
    public void h(p subscription, Object event) {
        try {
            subscription.b.a.invoke(subscription.a, new Object[]{event});
        } catch (InvocationTargetException e2) {
            f(subscription, event, e2.getCause());
        } catch (IllegalAccessException e3) {
            throw new IllegalStateException("Unexpected exception", e3);
        }
    }

    private void f(p subscription, Object event, Throwable cause) {
        if (event instanceof m) {
            if (this.p) {
                f fVar = this.v;
                Level level = Level.SEVERE;
                fVar.a(level, "SubscriberExceptionEvent subscriber " + subscription.a.getClass() + " threw an exception", cause);
                m exEvent = (m) event;
                f fVar2 = this.v;
                fVar2.a(level, "Initial event " + exEvent.c + " caused exception in " + exEvent.d, exEvent.b);
            }
        } else if (!this.o) {
            if (this.p) {
                f fVar3 = this.v;
                Level level2 = Level.SEVERE;
                fVar3.a(level2, "Could not dispatch event: " + event.getClass() + " to subscribing class " + subscription.a.getClass(), cause);
            }
            if (this.r) {
                l(new m(this, cause, event, subscription.a));
            }
        } else {
            throw new EventBusException("Invoking subscriber failed", cause);
        }
    }

    /* renamed from: org.greenrobot.eventbus.c$c  reason: collision with other inner class name */
    /* compiled from: EventBus */
    public static final class C0169c {
        final List<Object> a = new ArrayList();
        boolean b;
        boolean c;
        p d;
        Object e;
        boolean f;

        C0169c() {
        }
    }

    /* access modifiers changed from: package-private */
    public ExecutorService d() {
        return this.n;
    }

    public f e() {
        return this.v;
    }

    public String toString() {
        return "EventBus[indexCount=" + this.u + ", eventInheritance=" + this.t + "]";
    }
}
