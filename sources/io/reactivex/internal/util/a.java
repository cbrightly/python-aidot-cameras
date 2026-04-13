package io.reactivex.internal.util;

import io.reactivex.functions.h;
import io.reactivex.q;
import org.reactivestreams.b;

/* compiled from: AppendOnlyLinkedArrayList */
public class a<T> {
    final int a;
    final Object[] b;
    Object[] c;
    int d;

    /* renamed from: io.reactivex.internal.util.a$a  reason: collision with other inner class name */
    /* compiled from: AppendOnlyLinkedArrayList */
    public interface C0309a<T> extends h<T> {
        boolean test(T t);
    }

    public a(int capacity) {
        this.a = capacity;
        Object[] objArr = new Object[(capacity + 1)];
        this.b = objArr;
        this.c = objArr;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void c(T r5) {
        /*
            r4 = this;
            int r0 = r4.a
            int r1 = r4.d
            if (r1 != r0) goto L_0x0011
            int r2 = r0 + 1
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.Object[] r3 = r4.c
            r3[r0] = r2
            r4.c = r2
            r1 = 0
        L_0x0011:
            java.lang.Object[] r2 = r4.c
            r2[r1] = r5
            int r2 = r1 + 1
            r4.d = r2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.util.a.c(java.lang.Object):void");
    }

    public void e(T value) {
        this.b[0] = value;
    }

    public void d(C0309a<? super T> consumer) {
        int c2 = this.a;
        for (Object[] a2 = this.b; a2 != null; a2 = (Object[]) a2[c2]) {
            int i = 0;
            while (i < c2) {
                Object o = a2[i];
                if (o == null) {
                    continue;
                    break;
                } else if (!consumer.test(o)) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public <U> boolean b(b<? super U> subscriber) {
        int c2 = this.a;
        for (Object[] a2 = this.b; a2 != null; a2 = (Object[]) a2[c2]) {
            int i = 0;
            while (i < c2) {
                Object o = a2[i];
                if (o == null) {
                    continue;
                    break;
                } else if (h.acceptFull(o, subscriber)) {
                    return true;
                } else {
                    i++;
                }
            }
        }
        return false;
    }

    public <U> boolean a(q<? super U> observer) {
        int c2 = this.a;
        for (Object[] a2 = this.b; a2 != null; a2 = (Object[]) a2[c2]) {
            int i = 0;
            while (i < c2) {
                Object o = a2[i];
                if (o == null) {
                    continue;
                    break;
                } else if (h.acceptFull(o, observer)) {
                    return true;
                } else {
                    i++;
                }
            }
        }
        return false;
    }
}
