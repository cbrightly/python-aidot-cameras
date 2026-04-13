package com.google.android.gms.common.api.internal;

import android.os.DeadObjectException;
import android.os.RemoteException;
import androidx.annotation.NonNull;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.TaskCompletionSource;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class zad extends zac {
    protected final TaskCompletionSource zaa;

    public zad(int i, TaskCompletionSource taskCompletionSource) {
        super(i);
        this.zaa = taskCompletionSource;
    }

    /* access modifiers changed from: protected */
    public abstract void zac(zabq zabq);

    public final void zad(@NonNull Status status) {
        this.zaa.trySetException(new ApiException(status));
    }

    public final void zae(@NonNull Exception exc) {
        this.zaa.trySetException(exc);
    }

    public final void zaf(zabq zabq) {
        try {
            zac(zabq);
        } catch (DeadObjectException e) {
            zad(zai.zah(e));
            throw e;
        } catch (RemoteException e2) {
            zad(zai.zah(e2));
        } catch (RuntimeException e3) {
            this.zaa.trySetException(e3);
        }
    }

    public void zag(@NonNull zaad zaad, boolean z) {
    }
}
