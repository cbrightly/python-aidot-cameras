package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.IAccountAccessor;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public class AccountAccessor extends IAccountAccessor.Stub {
    @KeepForSdk
    @Nullable
    public static Account getAccountBinderSafe(@NonNull IAccountAccessor accountAccessor) {
        Account account = null;
        if (accountAccessor != null) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                account = accountAccessor.zzb();
            } catch (RemoteException e) {
                Log.w("AccountAccessor", "Remote account accessor probably died");
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
        return account;
    }

    public final boolean equals(@Nullable Object obj) {
        throw null;
    }

    @NonNull
    public final Account zzb() {
        throw null;
    }
}
