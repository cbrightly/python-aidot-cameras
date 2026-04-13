package org.greenrobot.greendao.async;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

public class AsyncOperationExecutor implements Runnable, Handler.Callback {
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private int countOperationsCompleted;
    private int countOperationsEnqueued;
    private volatile boolean executorRunning;
    private Handler handlerMainThread;
    private int lastSequenceNumber;
    private volatile AsyncOperationListener listener;
    private volatile AsyncOperationListener listenerMainThread;
    private volatile int maxOperationCountToMerge = 50;
    private final BlockingQueue<AsyncOperation> queue = new LinkedBlockingQueue();
    private volatile int waitForMergeMillis = 50;

    AsyncOperationExecutor() {
    }

    public void enqueue(AsyncOperation operation) {
        synchronized (this) {
            int i = this.lastSequenceNumber + 1;
            this.lastSequenceNumber = i;
            operation.sequenceNumber = i;
            this.queue.add(operation);
            this.countOperationsEnqueued++;
            if (!this.executorRunning) {
                this.executorRunning = true;
                executorService.execute(this);
            }
        }
    }

    public int getMaxOperationCountToMerge() {
        return this.maxOperationCountToMerge;
    }

    public void setMaxOperationCountToMerge(int maxOperationCountToMerge2) {
        this.maxOperationCountToMerge = maxOperationCountToMerge2;
    }

    public int getWaitForMergeMillis() {
        return this.waitForMergeMillis;
    }

    public void setWaitForMergeMillis(int waitForMergeMillis2) {
        this.waitForMergeMillis = waitForMergeMillis2;
    }

    public AsyncOperationListener getListener() {
        return this.listener;
    }

    public void setListener(AsyncOperationListener listener2) {
        this.listener = listener2;
    }

    public AsyncOperationListener getListenerMainThread() {
        return this.listenerMainThread;
    }

    public void setListenerMainThread(AsyncOperationListener listenerMainThread2) {
        this.listenerMainThread = listenerMainThread2;
    }

    public synchronized boolean isCompleted() {
        return this.countOperationsEnqueued == this.countOperationsCompleted;
    }

    public synchronized void waitForCompletion() {
        while (!isCompleted()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e);
            }
        }
    }

    public synchronized boolean waitForCompletion(int maxMillis) {
        if (!isCompleted()) {
            try {
                wait((long) maxMillis);
            } catch (InterruptedException e) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e);
            }
        }
        return isCompleted();
    }

    public void run() {
        AsyncOperation operation2;
        while (true) {
            try {
                AsyncOperation operation = this.queue.poll(1, TimeUnit.SECONDS);
                if (operation == null) {
                    synchronized (this) {
                        operation = (AsyncOperation) this.queue.poll();
                        if (operation == null) {
                            this.executorRunning = false;
                            this.executorRunning = false;
                            return;
                        }
                    }
                }
                if (!operation.isMergeTx() || (operation2 = this.queue.poll((long) this.waitForMergeMillis, TimeUnit.MILLISECONDS)) == null) {
                    executeOperationAndPostCompleted(operation);
                } else if (operation.isMergeableWith(operation2)) {
                    mergeTxAndExecute(operation, operation2);
                } else {
                    executeOperationAndPostCompleted(operation);
                    executeOperationAndPostCompleted(operation2);
                }
            } catch (InterruptedException e) {
                try {
                    DaoLog.w(Thread.currentThread().getName() + " was interruppted", e);
                    return;
                } finally {
                    this.executorRunning = false;
                }
            }
        }
    }

    private void mergeTxAndExecute(AsyncOperation operation1, AsyncOperation operation2) {
        ArrayList<AsyncOperation> mergedOps = new ArrayList<>();
        mergedOps.add(operation1);
        mergedOps.add(operation2);
        Database db = operation1.getDatabase();
        db.beginTransaction();
        boolean success = false;
        int i = 0;
        while (true) {
            try {
                if (i < mergedOps.size()) {
                    AsyncOperation operation = mergedOps.get(i);
                    executeOperation(operation);
                    if (operation.isFailed()) {
                        break;
                    }
                    if (i == mergedOps.size() - 1) {
                        AsyncOperation peekedOp = (AsyncOperation) this.queue.peek();
                        if (i >= this.maxOperationCountToMerge || !operation.isMergeableWith(peekedOp)) {
                            db.setTransactionSuccessful();
                        } else {
                            AsyncOperation removedOp = (AsyncOperation) this.queue.remove();
                            if (removedOp == peekedOp) {
                                mergedOps.add(removedOp);
                            } else {
                                throw new DaoException("Internal error: peeked op did not match removed op");
                            }
                        }
                    }
                    i++;
                }
            } finally {
                try {
                    db.endTransaction();
                } catch (RuntimeException e) {
                    DaoLog.i("Async transaction could not be ended, success so far was: " + success, e);
                }
            }
        }
        db.setTransactionSuccessful();
        success = true;
        if (success) {
            int mergedCount = mergedOps.size();
            Iterator<AsyncOperation> it = mergedOps.iterator();
            while (it.hasNext()) {
                AsyncOperation asyncOperation = it.next();
                asyncOperation.mergedOperationsCount = mergedCount;
                handleOperationCompleted(asyncOperation);
            }
            return;
        }
        DaoLog.i("Reverted merged transaction because one of the operations failed. Executing operations one by one instead...");
        Iterator<AsyncOperation> it2 = mergedOps.iterator();
        while (it2.hasNext()) {
            AsyncOperation asyncOperation2 = it2.next();
            asyncOperation2.reset();
            executeOperationAndPostCompleted(asyncOperation2);
        }
    }

    private void handleOperationCompleted(AsyncOperation operation) {
        operation.setCompleted();
        AsyncOperationListener listenerToCall = this.listener;
        if (listenerToCall != null) {
            listenerToCall.onAsyncOperationCompleted(operation);
        }
        if (this.listenerMainThread != null) {
            if (this.handlerMainThread == null) {
                this.handlerMainThread = new Handler(Looper.getMainLooper(), this);
            }
            this.handlerMainThread.sendMessage(this.handlerMainThread.obtainMessage(1, operation));
        }
        synchronized (this) {
            int i = this.countOperationsCompleted + 1;
            this.countOperationsCompleted = i;
            if (i == this.countOperationsEnqueued) {
                notifyAll();
            }
        }
    }

    private void executeOperationAndPostCompleted(AsyncOperation operation) {
        executeOperation(operation);
        handleOperationCompleted(operation);
    }

    private void executeOperation(AsyncOperation operation) {
        operation.timeStarted = System.currentTimeMillis();
        try {
            switch (AnonymousClass1.$SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[operation.type.ordinal()]) {
                case 1:
                    operation.dao.delete(operation.parameter);
                    break;
                case 2:
                    operation.dao.deleteInTx((Iterable) operation.parameter);
                    break;
                case 3:
                    operation.dao.deleteInTx((T[]) (Object[]) operation.parameter);
                    break;
                case 4:
                    operation.dao.insert(operation.parameter);
                    break;
                case 5:
                    operation.dao.insertInTx((Iterable) operation.parameter);
                    break;
                case 6:
                    operation.dao.insertInTx((T[]) (Object[]) operation.parameter);
                    break;
                case 7:
                    operation.dao.insertOrReplace(operation.parameter);
                    break;
                case 8:
                    operation.dao.insertOrReplaceInTx((Iterable) operation.parameter);
                    break;
                case 9:
                    operation.dao.insertOrReplaceInTx((T[]) (Object[]) operation.parameter);
                    break;
                case 10:
                    operation.dao.update(operation.parameter);
                    break;
                case 11:
                    operation.dao.updateInTx((Iterable) operation.parameter);
                    break;
                case 12:
                    operation.dao.updateInTx((T[]) (Object[]) operation.parameter);
                    break;
                case 13:
                    executeTransactionRunnable(operation);
                    break;
                case 14:
                    executeTransactionCallable(operation);
                    break;
                case 15:
                    operation.result = ((Query) operation.parameter).forCurrentThread().list();
                    break;
                case 16:
                    operation.result = ((Query) operation.parameter).forCurrentThread().unique();
                    break;
                case 17:
                    operation.dao.deleteByKey(operation.parameter);
                    break;
                case 18:
                    operation.dao.deleteAll();
                    break;
                case 19:
                    operation.result = operation.dao.load(operation.parameter);
                    break;
                case 20:
                    operation.result = operation.dao.loadAll();
                    break;
                case 21:
                    operation.result = Long.valueOf(operation.dao.count());
                    break;
                case 22:
                    operation.dao.refresh(operation.parameter);
                    break;
                default:
                    throw new DaoException("Unsupported operation: " + operation.type);
            }
        } catch (Throwable th) {
            operation.throwable = th;
        }
        operation.timeCompleted = System.currentTimeMillis();
    }

    /* renamed from: org.greenrobot.greendao.async.AsyncOperationExecutor$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType;

        static {
            int[] iArr = new int[AsyncOperation.OperationType.values().length];
            $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType = iArr;
            try {
                iArr[AsyncOperation.OperationType.Delete.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteInTxIterable.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteInTxArray.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Insert.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertInTxIterable.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertInTxArray.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplace.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplaceInTxIterable.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.InsertOrReplaceInTxArray.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Update.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.UpdateInTxIterable.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.UpdateInTxArray.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.TransactionRunnable.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.TransactionCallable.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.QueryList.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.QueryUnique.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteByKey.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.DeleteAll.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Load.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.LoadAll.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Count.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$org$greenrobot$greendao$async$AsyncOperation$OperationType[AsyncOperation.OperationType.Refresh.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
        }
    }

    private void executeTransactionRunnable(AsyncOperation operation) {
        Database db = operation.getDatabase();
        db.beginTransaction();
        try {
            ((Runnable) operation.parameter).run();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void executeTransactionCallable(AsyncOperation operation) {
        Database db = operation.getDatabase();
        db.beginTransaction();
        try {
            operation.result = ((Callable) operation.parameter).call();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public boolean handleMessage(Message msg) {
        AsyncOperationListener listenerToCall = this.listenerMainThread;
        if (listenerToCall == null) {
            return false;
        }
        listenerToCall.onAsyncOperationCompleted((AsyncOperation) msg.obj);
        return false;
    }
}
