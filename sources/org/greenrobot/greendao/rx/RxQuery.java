package org.greenrobot.greendao.rx;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.query.LazyList;
import org.greenrobot.greendao.query.Query;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.exceptions.Exceptions;

@Experimental
public class RxQuery<T> extends RxBase {
    /* access modifiers changed from: private */
    public final Query<T> query;

    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    public RxQuery(Query<T> query2) {
        this.query = query2;
    }

    public RxQuery(Query<T> query2, Scheduler scheduler) {
        super(scheduler);
        this.query = query2;
    }

    @Experimental
    public Observable<List<T>> list() {
        return wrap(new Callable<List<T>>() {
            public List<T> call() {
                return RxQuery.this.query.forCurrentThread().list();
            }
        });
    }

    @Experimental
    public Observable<T> unique() {
        return wrap(new Callable<T>() {
            public T call() {
                return RxQuery.this.query.forCurrentThread().unique();
            }
        });
    }

    public Observable<T> oneByOne() {
        return wrap(Observable.create(new Observable.OnSubscribe<T>() {
            public void call(Subscriber<? super T> subscriber) {
                LazyList<T> lazyList;
                try {
                    lazyList = RxQuery.this.query.forCurrentThread().listLazyUncached();
                    Iterator<T> it = lazyList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            T entity = it.next();
                            if (subscriber.isUnsubscribed()) {
                                break;
                            }
                            subscriber.onNext(entity);
                        }
                    }
                    lazyList.close();
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onCompleted();
                    }
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    subscriber.onError(e);
                }
            }
        }));
    }
}
