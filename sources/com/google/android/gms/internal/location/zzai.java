package com.google.android.gms.internal.location;

import android.location.Location;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: com.google.android.gms:play-services-location@@21.0.1 */
public final /* synthetic */ class zzai implements OnCompleteListener {
    public final /* synthetic */ AtomicReference zza;
    public final /* synthetic */ CountDownLatch zzb;

    public /* synthetic */ zzai(AtomicReference atomicReference, CountDownLatch countDownLatch) {
        this.zza = atomicReference;
        this.zzb = countDownLatch;
    }

    public final void onComplete(Task task) {
        AtomicReference atomicReference = this.zza;
        CountDownLatch countDownLatch = this.zzb;
        if (task.isSuccessful()) {
            atomicReference.set((Location) task.getResult());
        }
        countDownLatch.countDown();
    }
}
