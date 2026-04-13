package org.greenrobot.eventbus;

import java.util.ArrayList;
import java.util.List;

/* compiled from: PendingPost */
public final class i {
    private static final List<i> a = new ArrayList();
    Object b;
    p c;
    i d;

    private i(Object event, p subscription) {
        this.b = event;
        this.c = subscription;
    }

    static i a(p subscription, Object event) {
        List<i> list = a;
        synchronized (list) {
            int size = list.size();
            if (size <= 0) {
                return new i(event, subscription);
            }
            i pendingPost = list.remove(size - 1);
            pendingPost.b = event;
            pendingPost.c = subscription;
            pendingPost.d = null;
            return pendingPost;
        }
    }

    static void b(i pendingPost) {
        pendingPost.b = null;
        pendingPost.c = null;
        pendingPost.d = null;
        List<i> list = a;
        synchronized (list) {
            if (list.size() < 10000) {
                list.add(pendingPost);
            }
        }
    }
}
