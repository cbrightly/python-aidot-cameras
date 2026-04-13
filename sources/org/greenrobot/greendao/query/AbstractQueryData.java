package org.greenrobot.greendao.query;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.AbstractQuery;

public abstract class AbstractQueryData<T, Q extends AbstractQuery<T>> {
    final AbstractDao<T, ?> dao;
    final String[] initialValues;
    final Map<Long, WeakReference<Q>> queriesForThreads = new HashMap();
    final String sql;

    /* access modifiers changed from: protected */
    public abstract Q createQuery();

    AbstractQueryData(AbstractDao<T, ?> dao2, String sql2, String[] initialValues2) {
        this.dao = dao2;
        this.sql = sql2;
        this.initialValues = initialValues2;
    }

    /* access modifiers changed from: package-private */
    public Q forCurrentThread(Q query) {
        if (Thread.currentThread() != query.ownerThread) {
            return forCurrentThread();
        }
        String[] strArr = this.initialValues;
        System.arraycopy(strArr, 0, query.parameters, 0, strArr.length);
        return query;
    }

    /* access modifiers changed from: package-private */
    public Q forCurrentThread() {
        Q query;
        long threadId = Thread.currentThread().getId();
        synchronized (this.queriesForThreads) {
            WeakReference<Q> queryRef = this.queriesForThreads.get(Long.valueOf(threadId));
            query = queryRef != null ? (AbstractQuery) queryRef.get() : null;
            if (query == null) {
                gc();
                query = createQuery();
                this.queriesForThreads.put(Long.valueOf(threadId), new WeakReference(query));
            } else {
                String[] strArr = this.initialValues;
                System.arraycopy(strArr, 0, query.parameters, 0, strArr.length);
            }
        }
        return query;
    }

    /* access modifiers changed from: package-private */
    public void gc() {
        synchronized (this.queriesForThreads) {
            Iterator<Map.Entry<Long, WeakReference<Q>>> iterator = this.queriesForThreads.entrySet().iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getValue().get() == null) {
                    iterator.remove();
                }
            }
        }
    }
}
