package com.google.android.gms.cloudmessaging;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-cloud-messaging@@17.0.0 */
public interface IMessengerCompat extends IInterface {
    public static final String DESCRIPTOR = "com.google.android.gms.iid.IMessengerCompat";
    public static final int TRANSACTION_SEND = 1;

    /* compiled from: com.google.android.gms:play-services-cloud-messaging@@17.0.0 */
    public static class Impl extends Binder implements IMessengerCompat {
        @NonNull
        public IBinder asBinder() {
            throw null;
        }

        public boolean onTransact(int i, @NonNull Parcel parcel, @Nullable Parcel parcel2, int i2) {
            throw null;
        }

        public void send(@NonNull Message message) {
            throw null;
        }
    }

    /* compiled from: com.google.android.gms:play-services-cloud-messaging@@17.0.0 */
    public static class Proxy implements IMessengerCompat {
        private final IBinder zza;

        Proxy(@NonNull IBinder iBinder) {
            this.zza = iBinder;
        }

        @NonNull
        public IBinder asBinder() {
            return this.zza;
        }

        public void send(@NonNull Message msg) {
            Parcel obtain = Parcel.obtain();
            obtain.writeInterfaceToken(IMessengerCompat.DESCRIPTOR);
            obtain.writeInt(1);
            msg.writeToParcel(obtain, 0);
            try {
                this.zza.transact(1, obtain, (Parcel) null, 1);
            } finally {
                obtain.recycle();
            }
        }
    }

    void send(@NonNull Message message);
}
