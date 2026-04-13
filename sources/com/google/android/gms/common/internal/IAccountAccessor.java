package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.NonNull;
import com.google.android.gms.internal.common.zzb;
import com.google.android.gms.internal.common.zzc;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface IAccountAccessor extends IInterface {

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static abstract class Stub extends zzb implements IAccountAccessor {
        public Stub() {
            super("com.google.android.gms.common.internal.IAccountAccessor");
        }

        @NonNull
        public static IAccountAccessor asInterface(@NonNull IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface queryLocalInterface = obj.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
            return queryLocalInterface instanceof IAccountAccessor ? (IAccountAccessor) queryLocalInterface : new zzw(obj);
        }

        /* access modifiers changed from: protected */
        public final boolean zza(int i, @NonNull Parcel parcel, @NonNull Parcel parcel2, int i2) {
            if (i != 2) {
                return false;
            }
            Account zzb = zzb();
            parcel2.writeNoException();
            zzc.zzd(parcel2, zzb);
            return true;
        }
    }

    @NonNull
    Account zzb();
}
