package org.greenrobot.greendao.rx;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import rx.Observable;
import rx.Scheduler;

@Experimental
public class RxTransaction extends RxBase {
    /* access modifiers changed from: private */
    public final AbstractDaoSession daoSession;

    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    public RxTransaction(AbstractDaoSession daoSession2) {
        this.daoSession = daoSession2;
    }

    public RxTransaction(AbstractDaoSession daoSession2, Scheduler scheduler) {
        super(scheduler);
        this.daoSession = daoSession2;
    }

    @Experimental
    public Observable<Void> run(final Runnable runnable) {
        return wrap(new Callable<Void>() {
            public Void call() {
                RxTransaction.this.daoSession.runInTx(runnable);
                return null;
            }
        });
    }

    @Experimental
    public <T> Observable<T> call(final Callable<T> callable) {
        return wrap(new Callable<T>() {
            public T call() {
                return RxTransaction.this.daoSession.callInTx(callable);
            }
        });
    }

    @Experimental
    public AbstractDaoSession getDaoSession() {
        return this.daoSession;
    }
}
