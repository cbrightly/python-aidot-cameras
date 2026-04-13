package meshsdk;

import io.reactivex.functions.f;
import io.reactivex.l;
import io.reactivex.o;
import java.util.concurrent.TimeUnit;

public class UpdateRetryStrategy implements f<l<? extends Throwable>, l<?>> {
    /* access modifiers changed from: private */
    public int maxRetries = 3;
    /* access modifiers changed from: private */
    public int retryCount = 0;
    /* access modifiers changed from: private */
    public int retryDelayMillis = 200;

    static /* synthetic */ int access$004(UpdateRetryStrategy x0) {
        int i = x0.retryCount + 1;
        x0.retryCount = i;
        return i;
    }

    public void setMaxRetries(int maxRetries2) {
        this.maxRetries = maxRetries2;
    }

    public void setRetryDelayMillis(int retryDelayMillis2) {
        this.retryDelayMillis = retryDelayMillis2;
    }

    public l<?> apply(l<? extends Throwable> observable) {
        return observable.u(new f<Throwable, o<?>>() {
            public o<?> apply(Throwable throwable) {
                if (UpdateRetryStrategy.access$004(UpdateRetryStrategy.this) > UpdateRetryStrategy.this.maxRetries) {
                    return l.r(throwable);
                }
                MeshLog.e("downloadFile error:" + throwable.toString() + ",retry count:" + UpdateRetryStrategy.this.retryCount);
                return l.g0((long) UpdateRetryStrategy.this.retryDelayMillis, TimeUnit.MILLISECONDS);
            }
        });
    }
}
