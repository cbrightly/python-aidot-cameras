package org.greenrobot.eventbus;

import java.lang.reflect.Method;

/* compiled from: SubscriberMethod */
public class n {
    final Method a;
    final ThreadMode b;
    final Class<?> c;
    final int d;
    final boolean e;
    String f;

    public n(Method method, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.a = method;
        this.b = threadMode;
        this.c = eventType;
        this.d = priority;
        this.e = sticky;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof n)) {
            return false;
        }
        a();
        n otherSubscriberMethod = (n) other;
        otherSubscriberMethod.a();
        return this.f.equals(otherSubscriberMethod.f);
    }

    private synchronized void a() {
        if (this.f == null) {
            StringBuilder builder = new StringBuilder(64);
            builder.append(this.a.getDeclaringClass().getName());
            builder.append('#');
            builder.append(this.a.getName());
            builder.append('(');
            builder.append(this.c.getName());
            this.f = builder.toString();
        }
    }

    public int hashCode() {
        return this.a.hashCode();
    }
}
