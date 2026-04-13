package com.google.android.gms.common.api.internal;

import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.internal.base.zau;
import com.google.errorprone.annotations.ResultIgnorabilityUnspecified;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@KeepName
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class BasePendingResult<R extends Result> extends PendingResult<R> {
    static final ThreadLocal zaa = new zaq();
    public static final /* synthetic */ int zad = 0;
    @KeepName
    private zas mResultGuardian;
    @NonNull
    protected final CallbackHandler zab;
    @NonNull
    protected final WeakReference zac;
    private final Object zae;
    private final CountDownLatch zaf;
    private final ArrayList zag;
    @Nullable
    private ResultCallback zah;
    private final AtomicReference zai;
    /* access modifiers changed from: private */
    @Nullable
    public Result zaj;
    private Status zak;
    private volatile boolean zal;
    private boolean zam;
    private boolean zan;
    @Nullable
    private ICancelToken zao;
    private volatile zada zap;
    private boolean zaq;

    @Deprecated
    BasePendingResult() {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList();
        this.zai = new AtomicReference();
        this.zaq = false;
        this.zab = new CallbackHandler(Looper.getMainLooper());
        this.zac = new WeakReference((Object) null);
    }

    private final Result zaa() {
        Result result;
        synchronized (this.zae) {
            Preconditions.checkState(!this.zal, "Result has already been consumed.");
            Preconditions.checkState(isReady(), "Result is not ready.");
            result = this.zaj;
            this.zaj = null;
            this.zah = null;
            this.zal = true;
        }
        zadb zadb = (zadb) this.zai.getAndSet((Object) null);
        if (zadb != null) {
            zadb.zaa.zab.remove(this);
        }
        return (Result) Preconditions.checkNotNull(result);
    }

    private final void zab(Result result) {
        this.zaj = result;
        this.zak = result.getStatus();
        this.zao = null;
        this.zaf.countDown();
        if (this.zam) {
            this.zah = null;
        } else {
            ResultCallback resultCallback = this.zah;
            if (resultCallback != null) {
                this.zab.removeMessages(2);
                this.zab.zaa(resultCallback, zaa());
            } else if (this.zaj instanceof Releasable) {
                this.mResultGuardian = new zas(this, (zar) null);
            }
        }
        ArrayList arrayList = this.zag;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((PendingResult.StatusListener) arrayList.get(i)).onComplete(this.zak);
        }
        this.zag.clear();
    }

    public static void zal(@Nullable Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (RuntimeException e) {
                Log.w("BasePendingResult", "Unable to release ".concat(String.valueOf(String.valueOf(result))), e);
            }
        }
    }

    public final void addStatusListener(@NonNull PendingResult.StatusListener statusListener) {
        boolean z;
        if (statusListener != null) {
            z = true;
        } else {
            z = false;
        }
        Preconditions.checkArgument(z, "Callback cannot be null.");
        synchronized (this.zae) {
            if (isReady()) {
                statusListener.onComplete(this.zak);
            } else {
                this.zag.add(statusListener);
            }
        }
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    public final R await() {
        Preconditions.checkNotMainThread("await must not be called on the UI thread");
        boolean z = true;
        Preconditions.checkState(!this.zal, "Result has already been consumed");
        if (this.zap != null) {
            z = false;
        }
        Preconditions.checkState(z, "Cannot await if then() has been called.");
        try {
            this.zaf.await();
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return zaa();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x002a, code lost:
        return;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void cancel() {
        /*
            r2 = this;
            java.lang.Object r0 = r2.zae
            monitor-enter(r0)
            boolean r1 = r2.zam     // Catch:{ all -> 0x002b }
            if (r1 != 0) goto L_0x0029
            boolean r1 = r2.zal     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x000d
            goto L_0x0029
        L_0x000d:
            com.google.android.gms.common.internal.ICancelToken r1 = r2.zao     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0016
            r1.cancel()     // Catch:{ RemoteException -> 0x0015 }
            goto L_0x0016
        L_0x0015:
            r1 = move-exception
        L_0x0016:
            com.google.android.gms.common.api.Result r1 = r2.zaj     // Catch:{ all -> 0x002b }
            zal(r1)     // Catch:{ all -> 0x002b }
            r1 = 1
            r2.zam = r1     // Catch:{ all -> 0x002b }
            com.google.android.gms.common.api.Status r1 = com.google.android.gms.common.api.Status.RESULT_CANCELED     // Catch:{ all -> 0x002b }
            com.google.android.gms.common.api.Result r1 = r2.createFailedResult(r1)     // Catch:{ all -> 0x002b }
            r2.zab(r1)     // Catch:{ all -> 0x002b }
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x0029:
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            return
        L_0x002b:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002b }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.cancel():void");
    }

    /* access modifiers changed from: protected */
    @NonNull
    @KeepForSdk
    public abstract R createFailedResult(@NonNull Status status);

    @KeepForSdk
    @Deprecated
    public final void forceFailureUnlessReady(@NonNull Status status) {
        synchronized (this.zae) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zan = true;
            }
        }
    }

    public final boolean isCanceled() {
        boolean z;
        synchronized (this.zae) {
            z = this.zam;
        }
        return z;
    }

    @KeepForSdk
    public final boolean isReady() {
        return this.zaf.getCount() == 0;
    }

    /* access modifiers changed from: protected */
    @KeepForSdk
    public final void setCancelToken(@NonNull ICancelToken cancelToken) {
        synchronized (this.zae) {
            this.zao = cancelToken;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x003a, code lost:
        return;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(@androidx.annotation.Nullable com.google.android.gms.common.api.ResultCallback<? super R> r5) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.zae
            monitor-enter(r0)
            if (r5 != 0) goto L_0x000b
            r5 = 0
            r4.zah = r5     // Catch:{ all -> 0x003b }
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            return
        L_0x000b:
            boolean r1 = r4.zal     // Catch:{ all -> 0x003b }
            r2 = 1
            r1 = r1 ^ r2
            java.lang.String r3 = "Result has already been consumed."
            com.google.android.gms.common.internal.Preconditions.checkState(r1, r3)     // Catch:{ all -> 0x003b }
            com.google.android.gms.common.api.internal.zada r1 = r4.zap     // Catch:{ all -> 0x003b }
            if (r1 != 0) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r2 = 0
        L_0x001a:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.Preconditions.checkState(r2, r1)     // Catch:{ all -> 0x003b }
            boolean r1 = r4.isCanceled()     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0027
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            return
        L_0x0027:
            boolean r1 = r4.isReady()     // Catch:{ all -> 0x003b }
            if (r1 == 0) goto L_0x0037
            com.google.android.gms.common.api.internal.BasePendingResult$CallbackHandler r1 = r4.zab     // Catch:{ all -> 0x003b }
            com.google.android.gms.common.api.Result r2 = r4.zaa()     // Catch:{ all -> 0x003b }
            r1.zaa(r5, r2)     // Catch:{ all -> 0x003b }
            goto L_0x0039
        L_0x0037:
            r4.zah = r5     // Catch:{ all -> 0x003b }
        L_0x0039:
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            return
        L_0x003b:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x003b }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback):void");
    }

    @NonNull
    public final <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        boolean z;
        TransformedResult<S> then;
        Preconditions.checkState(!this.zal, "Result has already been consumed.");
        synchronized (this.zae) {
            boolean z2 = false;
            if (this.zap == null) {
                z = true;
            } else {
                z = false;
            }
            Preconditions.checkState(z, "Cannot call then() twice.");
            if (this.zah == null) {
                z2 = true;
            }
            Preconditions.checkState(z2, "Cannot call then() if callbacks are set.");
            Preconditions.checkState(!this.zam, "Cannot call then() if result was canceled.");
            this.zaq = true;
            this.zap = new zada(this.zac);
            then = this.zap.then(resultTransform);
            if (isReady()) {
                this.zab.zaa(this.zap, zaa());
            } else {
                this.zah = this.zap;
            }
        }
        return then;
    }

    public final void zak() {
        boolean z = true;
        if (!this.zaq && !((Boolean) zaa.get()).booleanValue()) {
            z = false;
        }
        this.zaq = z;
    }

    public final boolean zam() {
        boolean isCanceled;
        synchronized (this.zae) {
            if (((GoogleApiClient) this.zac.get()) == null || !this.zaq) {
                cancel();
            }
            isCanceled = isCanceled();
        }
        return isCanceled;
    }

    public final void zan(@Nullable zadb zadb) {
        this.zai.set(zadb);
    }

    @VisibleForTesting
    /* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
    public static class CallbackHandler<R extends Result> extends zau {
        public CallbackHandler() {
            super(Looper.getMainLooper());
        }

        public final void handleMessage(@NonNull Message message) {
            int i = message.what;
            switch (i) {
                case 1:
                    Pair pair = (Pair) message.obj;
                    ResultCallback resultCallback = (ResultCallback) pair.first;
                    Result result = (Result) pair.second;
                    try {
                        resultCallback.onResult(result);
                        return;
                    } catch (RuntimeException e) {
                        BasePendingResult.zal(result);
                        throw e;
                    }
                case 2:
                    ((BasePendingResult) message.obj).forceFailureUnlessReady(Status.RESULT_TIMEOUT);
                    return;
                default:
                    Log.wtf("BasePendingResult", "Don't know how to handle message: " + i, new Exception());
                    return;
            }
        }

        public final void zaa(@NonNull ResultCallback resultCallback, @NonNull Result result) {
            int i = BasePendingResult.zad;
            sendMessage(obtainMessage(1, new Pair((ResultCallback) Preconditions.checkNotNull(resultCallback), result)));
        }

        public CallbackHandler(@NonNull Looper looper) {
            super(looper);
        }
    }

    @KeepForSdk
    public final void setResult(@NonNull R result) {
        synchronized (this.zae) {
            if (this.zan || this.zam) {
                zal(result);
                return;
            }
            isReady();
            Preconditions.checkState(!isReady(), "Results have already been set");
            Preconditions.checkState(!this.zal, "Result has already been consumed");
            zab(result);
        }
    }

    @KeepForSdk
    @Deprecated
    protected BasePendingResult(@NonNull Looper looper) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList();
        this.zai = new AtomicReference();
        this.zaq = false;
        this.zab = new CallbackHandler(looper);
        this.zac = new WeakReference((Object) null);
    }

    @ResultIgnorabilityUnspecified
    @NonNull
    public final R await(long j, @NonNull TimeUnit timeUnit) {
        if (j > 0) {
            Preconditions.checkNotMainThread("await must not be called on the UI thread when time is greater than zero.");
        }
        boolean z = true;
        Preconditions.checkState(!this.zal, "Result has already been consumed.");
        if (this.zap != null) {
            z = false;
        }
        Preconditions.checkState(z, "Cannot await if then() has been called.");
        try {
            if (!this.zaf.await(j, timeUnit)) {
                forceFailureUnlessReady(Status.RESULT_TIMEOUT);
            }
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.RESULT_INTERRUPTED);
        }
        Preconditions.checkState(isReady(), "Result is not ready.");
        return zaa();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0048, code lost:
        return;
     */
    @com.google.android.gms.common.annotation.KeepForSdk
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setResultCallback(@androidx.annotation.NonNull com.google.android.gms.common.api.ResultCallback<? super R> r5, long r6, @androidx.annotation.NonNull java.util.concurrent.TimeUnit r8) {
        /*
            r4 = this;
            java.lang.Object r0 = r4.zae
            monitor-enter(r0)
            if (r5 != 0) goto L_0x000b
            r5 = 0
            r4.zah = r5     // Catch:{ all -> 0x0049 }
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            return
        L_0x000b:
            boolean r1 = r4.zal     // Catch:{ all -> 0x0049 }
            r2 = 1
            r1 = r1 ^ r2
            java.lang.String r3 = "Result has already been consumed."
            com.google.android.gms.common.internal.Preconditions.checkState(r1, r3)     // Catch:{ all -> 0x0049 }
            com.google.android.gms.common.api.internal.zada r1 = r4.zap     // Catch:{ all -> 0x0049 }
            if (r1 != 0) goto L_0x0019
            goto L_0x001a
        L_0x0019:
            r2 = 0
        L_0x001a:
            java.lang.String r1 = "Cannot set callbacks if then() has been called."
            com.google.android.gms.common.internal.Preconditions.checkState(r2, r1)     // Catch:{ all -> 0x0049 }
            boolean r1 = r4.isCanceled()     // Catch:{ all -> 0x0049 }
            if (r1 == 0) goto L_0x0027
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            return
        L_0x0027:
            boolean r1 = r4.isReady()     // Catch:{ all -> 0x0049 }
            if (r1 == 0) goto L_0x0037
            com.google.android.gms.common.api.internal.BasePendingResult$CallbackHandler r6 = r4.zab     // Catch:{ all -> 0x0049 }
            com.google.android.gms.common.api.Result r7 = r4.zaa()     // Catch:{ all -> 0x0049 }
            r6.zaa(r5, r7)     // Catch:{ all -> 0x0049 }
            goto L_0x0047
        L_0x0037:
            r4.zah = r5     // Catch:{ all -> 0x0049 }
            com.google.android.gms.common.api.internal.BasePendingResult$CallbackHandler r5 = r4.zab     // Catch:{ all -> 0x0049 }
            long r6 = r8.toMillis(r6)     // Catch:{ all -> 0x0049 }
            r8 = 2
            android.os.Message r8 = r5.obtainMessage(r8, r4)     // Catch:{ all -> 0x0049 }
            r5.sendMessageDelayed(r8, r6)     // Catch:{ all -> 0x0049 }
        L_0x0047:
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            return
        L_0x0049:
            r5 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0049 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.api.internal.BasePendingResult.setResultCallback(com.google.android.gms.common.api.ResultCallback, long, java.util.concurrent.TimeUnit):void");
    }

    @KeepForSdk
    protected BasePendingResult(@Nullable GoogleApiClient apiClient) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList();
        this.zai = new AtomicReference();
        this.zaq = false;
        this.zab = new CallbackHandler(apiClient != null ? apiClient.getLooper() : Looper.getMainLooper());
        this.zac = new WeakReference(apiClient);
    }

    @KeepForSdk
    @VisibleForTesting
    protected BasePendingResult(@NonNull CallbackHandler<R> callbackHandler) {
        this.zae = new Object();
        this.zaf = new CountDownLatch(1);
        this.zag = new ArrayList();
        this.zai = new AtomicReference();
        this.zaq = false;
        this.zab = (CallbackHandler) Preconditions.checkNotNull(callbackHandler, "CallbackHandler must not be null");
        this.zac = new WeakReference((Object) null);
    }
}
