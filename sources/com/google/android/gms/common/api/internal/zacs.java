package com.google.android.gms.common.api.internal;

import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.IAccountAccessor;
import java.util.Set;

@WorkerThread
/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public interface zacs {
    void zae(ConnectionResult connectionResult);

    void zaf(@Nullable IAccountAccessor iAccountAccessor, @Nullable Set set);
}
