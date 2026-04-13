package com.leedarson.serviceimpl.business.netease.LDNetDiagnoService;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class LDNetAsyncTaskEx<Params, Progress, Result> {
    private static final int MESSAGE_POST_CANCEL = 3;
    private static final int MESSAGE_POST_PROGRESS = 2;
    private static final int MESSAGE_POST_RESULT = 1;
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public static final LDNetInternalHandler sHandler = new LDNetInternalHandler(Looper.getMainLooper());
    private final FutureTask<Result> mFuture;
    private volatile Status mStatus = Status.PENDING;
    private final LDNetWorkerRunnable<Params, Result> mWorker;

    public enum Status {
        PENDING,
        RUNNING,
        FINISHED;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public abstract Result doInBackground(Params... paramsArr);

    public abstract ThreadPoolExecutor getThreadPoolExecutor();

    public LDNetAsyncTaskEx() {
        AnonymousClass1 r0 = new LDNetWorkerRunnable<Params, Result>() {
            public static ChangeQuickRedirect changeQuickRedirect;

            public Result call() {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7171, new Class[0], Object.class);
                if (proxy.isSupported) {
                    return proxy.result;
                }
                return LDNetAsyncTaskEx.this.doInBackground(this.mParams);
            }
        };
        this.mWorker = r0;
        this.mFuture = new FutureTask<Result>(r0) {
            public static ChangeQuickRedirect changeQuickRedirect;

            public void done() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7172, new Class[0], Void.TYPE).isSupported) {
                    Result result = null;
                    try {
                        result = get();
                    } catch (InterruptedException e) {
                        Log.w(getClass().getSimpleName(), e);
                    } catch (ExecutionException e2) {
                        Log.w(getClass().getSimpleName(), e2);
                    } catch (CancellationException e3) {
                        LDNetAsyncTaskEx.sHandler.obtainMessage(3, new LDNetAsyncTaskResult(LDNetAsyncTaskEx.this, (Data[]) null)).sendToTarget();
                        return;
                    } catch (Throwable th) {
                    }
                    LDNetAsyncTaskEx.sHandler.obtainMessage(1, new LDNetAsyncTaskResult(LDNetAsyncTaskEx.this, result)).sendToTarget();
                }
            }
        };
    }

    public static class LDNetInternalHandler extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;

        public LDNetInternalHandler(@NonNull Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7173, new Class[]{Message.class}, Void.TYPE).isSupported) {
                LDNetAsyncTaskResult result = (LDNetAsyncTaskResult) msg.obj;
                switch (msg.what) {
                    case 1:
                        result.mTask.finish(result.mData[0]);
                        return;
                    case 2:
                        result.mTask.onProgressUpdate(result.mData);
                        return;
                    case 3:
                        result.mTask.onCancelled();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public final Status getStatus() {
        return this.mStatus;
    }

    public void onPreExecute() {
    }

    public void onPostExecute(Result result) {
    }

    public void onProgressUpdate(Progress... progressArr) {
    }

    public void onCancelled() {
    }

    public final boolean isCancelled() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7166, new Class[0], Boolean.TYPE);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.mFuture.isCancelled();
    }

    public final boolean cancel(boolean mayInterruptIfRunning) {
        Object[] objArr = {new Byte(mayInterruptIfRunning ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7167, new Class[]{cls}, cls);
        return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : this.mFuture.cancel(mayInterruptIfRunning);
    }

    /* renamed from: com.leedarson.serviceimpl.business.netease.LDNetDiagnoService.LDNetAsyncTaskEx$3  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$leedarson$serviceimpl$business$netease$LDNetDiagnoService$LDNetAsyncTaskEx$Status;

        static {
            int[] iArr = new int[Status.values().length];
            $SwitchMap$com$leedarson$serviceimpl$business$netease$LDNetDiagnoService$LDNetAsyncTaskEx$Status = iArr;
            try {
                iArr[Status.RUNNING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$leedarson$serviceimpl$business$netease$LDNetDiagnoService$LDNetAsyncTaskEx$Status[Status.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public final LDNetAsyncTaskEx<Params, Progress, Result> execute(Params... params) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{params}, this, changeQuickRedirect, false, 7168, new Class[]{Object[].class}, LDNetAsyncTaskEx.class);
        if (proxy.isSupported) {
            return (LDNetAsyncTaskEx) proxy.result;
        }
        if (this.mStatus != Status.PENDING) {
            switch (AnonymousClass3.$SwitchMap$com$leedarson$serviceimpl$business$netease$LDNetDiagnoService$LDNetAsyncTaskEx$Status[this.mStatus.ordinal()]) {
                case 1:
                    throw new IllegalStateException("Cannot execute task: the task is already running.");
                case 2:
                    throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
        }
        this.mStatus = Status.RUNNING;
        onPreExecute();
        this.mWorker.mParams = params;
        ThreadPoolExecutor sExecutor = getThreadPoolExecutor();
        if (sExecutor == null) {
            return null;
        }
        sExecutor.execute(this.mFuture);
        return this;
    }

    public final void publishProgress(Progress... values) {
        if (!PatchProxy.proxy(new Object[]{values}, this, changeQuickRedirect, false, 7169, new Class[]{Object[].class}, Void.TYPE).isSupported) {
            sHandler.obtainMessage(2, new LDNetAsyncTaskResult(this, values)).sendToTarget();
        }
    }

    public void finish(Result result) {
        if (!PatchProxy.proxy(new Object[]{result}, this, changeQuickRedirect, false, 7170, new Class[]{Object.class}, Void.TYPE).isSupported) {
            if (isCancelled()) {
                result = null;
            }
            onPostExecute(result);
            this.mStatus = Status.FINISHED;
        }
    }

    public static abstract class LDNetWorkerRunnable<Params, Result> implements Callable<Result> {
        Params[] mParams;

        private LDNetWorkerRunnable() {
        }
    }

    public static class LDNetAsyncTaskResult<Data> {
        final Data[] mData;
        final LDNetAsyncTaskEx mTask;

        LDNetAsyncTaskResult(LDNetAsyncTaskEx task, Data... data) {
            this.mTask = task;
            this.mData = data;
        }
    }
}
