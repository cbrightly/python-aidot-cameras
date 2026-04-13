package com.squareup.okhttp;

import java.util.ArrayDeque;
import java.util.Deque;

/* compiled from: Dispatcher */
public final class m {
    private int a = 64;
    private int b = 5;
    private final Deque<?> c = new ArrayDeque();
    private final Deque<?> d = new ArrayDeque();
    private final Deque<e> e = new ArrayDeque();

    /* access modifiers changed from: package-private */
    public synchronized void a(e call) {
        this.e.add(call);
    }

    /* access modifiers changed from: package-private */
    public synchronized void b(e call) {
        if (!this.e.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }
}
