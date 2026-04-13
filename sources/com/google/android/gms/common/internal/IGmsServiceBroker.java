package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.errorprone.annotations.CanIgnoreReturnValue;

/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public interface IGmsServiceBroker extends IInterface {

    /* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
    public static abstract class Stub extends Binder implements IGmsServiceBroker {
        public Stub() {
            attachInterface(this, "com.google.android.gms.common.internal.IGmsServiceBroker");
        }

        @NonNull
        @KeepForSdk
        @CanIgnoreReturnValue
        public IBinder asBinder() {
            return this;
        }

        public final boolean onTransact(int i, @NonNull Parcel parcel, @Nullable Parcel parcel2, int i2) {
            IGmsCallbacks iGmsCallbacks;
            if (i > 16777215) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface("com.google.android.gms.common.internal.IGmsServiceBroker");
            IBinder readStrongBinder = parcel.readStrongBinder();
            GetServiceRequest getServiceRequest = null;
            if (readStrongBinder == null) {
                iGmsCallbacks = null;
            } else {
                IInterface queryLocalInterface = readStrongBinder.queryLocalInterface("com.google.android.gms.common.internal.IGmsCallbacks");
                iGmsCallbacks = queryLocalInterface instanceof IGmsCallbacks ? (IGmsCallbacks) queryLocalInterface : new zzab(readStrongBinder);
            }
            if (i == 46) {
                if (parcel.readInt() != 0) {
                    getServiceRequest = GetServiceRequest.CREATOR.createFromParcel(parcel);
                }
                getService(iGmsCallbacks, getServiceRequest);
                Preconditions.checkNotNull(parcel2);
                parcel2.writeNoException();
                return true;
            } else if (i == 47) {
                if (parcel.readInt() != 0) {
                    zzak createFromParcel = zzak.CREATOR.createFromParcel(parcel);
                }
                throw new UnsupportedOperationException();
            } else {
                parcel.readInt();
                if (i != 4) {
                    parcel.readString();
                    switch (i) {
                        case 1:
                            parcel.readString();
                            parcel.createStringArray();
                            parcel.readString();
                            if (parcel.readInt() != 0) {
                                Bundle bundle = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                                break;
                            }
                            break;
                        case 2:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 23:
                        case 25:
                        case 27:
                        case 37:
                        case 38:
                        case 41:
                        case 43:
                            if (parcel.readInt() != 0) {
                                Bundle bundle2 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                                break;
                            }
                            break;
                        case 9:
                            parcel.readString();
                            parcel.createStringArray();
                            parcel.readString();
                            parcel.readStrongBinder();
                            parcel.readString();
                            if (parcel.readInt() != 0) {
                                Bundle bundle3 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                                break;
                            }
                            break;
                        case 10:
                            parcel.readString();
                            parcel.createStringArray();
                            break;
                        case 19:
                            parcel.readStrongBinder();
                            if (parcel.readInt() != 0) {
                                Bundle bundle4 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                                break;
                            }
                            break;
                        case 20:
                        case 30:
                            parcel.createStringArray();
                            parcel.readString();
                            if (parcel.readInt() != 0) {
                                Bundle bundle5 = (Bundle) Bundle.CREATOR.createFromParcel(parcel);
                                break;
                            }
                            break;
                        case 34:
                            parcel.readString();
                            break;
                    }
                }
                throw new UnsupportedOperationException();
            }
        }
    }

    @KeepForSdk
    void getService(@NonNull IGmsCallbacks iGmsCallbacks, @Nullable GetServiceRequest getServiceRequest);
}
