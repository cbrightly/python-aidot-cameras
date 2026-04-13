package io.reactivex.internal.queue;

import io.reactivex.internal.fuseable.f;
import io.reactivex.internal.queue.MpscLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: MpscLinkedQueue */
public final class a<T> implements f<T> {
    private final AtomicReference<C0306a<T>> c = new AtomicReference<>();
    private final AtomicReference<C0306a<T>> d = new AtomicReference<>();

    public a() {
        MpscLinkedQueue.LinkedQueueNode<T> node = new C0306a<>();
        d(node);
        e(node);
    }

    public boolean offer(T e) {
        if (e != null) {
            MpscLinkedQueue.LinkedQueueNode<T> nextNode = new C0306a<>(e);
            e(nextNode).soNext(nextNode);
            return true;
        }
        throw new NullPointerException("Null is not a valid element");
    }

    public T poll() {
        C0306a lvNext;
        C0306a aVar;
        T currConsumerNode = a();
        MpscLinkedQueue.LinkedQueueNode<T> nextNode = currConsumerNode.lvNext();
        if (nextNode != null) {
            T nextValue = nextNode.getAndNullValue();
            d(nextNode);
            return nextValue;
        } else if (currConsumerNode == c()) {
            return null;
        } else {
            do {
                lvNext = currConsumerNode.lvNext();
                aVar = lvNext;
            } while (lvNext == null);
            T nextValue2 = aVar.getAndNullValue();
            d(aVar);
            return nextValue2;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000a, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clear() {
        /*
            r1 = this;
        L_0x0000:
            java.lang.Object r0 = r1.poll()
            if (r0 == 0) goto L_0x000d
            boolean r0 = r1.isEmpty()
            if (r0 != 0) goto L_0x000d
            goto L_0x0000
        L_0x000d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: io.reactivex.internal.queue.a.clear():void");
    }

    /* access modifiers changed from: package-private */
    public C0306a<T> c() {
        return this.c.get();
    }

    /* access modifiers changed from: package-private */
    public C0306a<T> e(C0306a<T> node) {
        return this.c.getAndSet(node);
    }

    /* access modifiers changed from: package-private */
    public C0306a<T> b() {
        return this.d.get();
    }

    /* access modifiers changed from: package-private */
    public C0306a<T> a() {
        return this.d.get();
    }

    /* access modifiers changed from: package-private */
    public void d(C0306a<T> node) {
        this.d.lazySet(node);
    }

    public boolean isEmpty() {
        return b() == c();
    }

    /* renamed from: io.reactivex.internal.queue.a$a  reason: collision with other inner class name */
    /* compiled from: MpscLinkedQueue */
    public static final class C0306a<E> extends AtomicReference<C0306a<E>> {
        private static final long serialVersionUID = 2404266111789071508L;
        private E value;

        C0306a() {
        }

        C0306a(E val) {
            spValue(val);
        }

        public E getAndNullValue() {
            E temp = lpValue();
            spValue((Object) null);
            return temp;
        }

        public E lpValue() {
            return this.value;
        }

        public void spValue(E newValue) {
            this.value = newValue;
        }

        public void soNext(C0306a<E> n) {
            lazySet(n);
        }

        public C0306a<E> lvNext() {
            return (C0306a) get();
        }
    }
}
