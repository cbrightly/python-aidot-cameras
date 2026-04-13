package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.internal.measurement.zzbn;
import com.google.android.gms.internal.measurement.zzbo;
import java.util.List;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@21.2.2 */
public abstract class zzej extends zzbn implements zzek {
    public zzej() {
        super("com.google.android.gms.measurement.internal.IMeasurementService");
    }

    /* access modifiers changed from: protected */
    public final boolean zza(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzbo.zzc(parcel);
                zzk((zzaw) zzbo.zza(parcel, zzaw.CREATOR), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 2:
                zzbo.zzc(parcel);
                zzt((zzlj) zzbo.zza(parcel, zzlj.CREATOR), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 4:
                zzbo.zzc(parcel);
                zzj((zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 5:
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                zzbo.zzc(parcel);
                zzl((zzaw) zzbo.zza(parcel, zzaw.CREATOR), readString, readString2);
                parcel2.writeNoException();
                return true;
            case 6:
                zzbo.zzc(parcel);
                zzs((zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 7:
                boolean zzf = zzbo.zzf(parcel);
                zzbo.zzc(parcel);
                List zze = zze((zzq) zzbo.zza(parcel, zzq.CREATOR), zzf);
                parcel2.writeNoException();
                parcel2.writeTypedList(zze);
                return true;
            case 9:
                String readString3 = parcel.readString();
                zzbo.zzc(parcel);
                byte[] zzu = zzu((zzaw) zzbo.zza(parcel, zzaw.CREATOR), readString3);
                parcel2.writeNoException();
                parcel2.writeByteArray(zzu);
                return true;
            case 10:
                long readLong = parcel.readLong();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                zzbo.zzc(parcel);
                zzq(readLong, readString4, readString5, readString6);
                parcel2.writeNoException();
                return true;
            case 11:
                zzbo.zzc(parcel);
                String zzd = zzd((zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                parcel2.writeString(zzd);
                return true;
            case 12:
                zzbo.zzc(parcel);
                zzn((zzac) zzbo.zza(parcel, zzac.CREATOR), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 13:
                zzbo.zzc(parcel);
                zzo((zzac) zzbo.zza(parcel, zzac.CREATOR));
                parcel2.writeNoException();
                return true;
            case 14:
                zzbo.zzc(parcel);
                List zzh = zzh(parcel.readString(), parcel.readString(), zzbo.zzf(parcel), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zzh);
                return true;
            case 15:
                String readString7 = parcel.readString();
                String readString8 = parcel.readString();
                String readString9 = parcel.readString();
                boolean zzf2 = zzbo.zzf(parcel);
                zzbo.zzc(parcel);
                List zzi = zzi(readString7, readString8, readString9, zzf2);
                parcel2.writeNoException();
                parcel2.writeTypedList(zzi);
                return true;
            case 16:
                zzbo.zzc(parcel);
                List zzf3 = zzf(parcel.readString(), parcel.readString(), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                parcel2.writeTypedList(zzf3);
                return true;
            case 17:
                String readString10 = parcel.readString();
                String readString11 = parcel.readString();
                String readString12 = parcel.readString();
                zzbo.zzc(parcel);
                List zzg = zzg(readString10, readString11, readString12);
                parcel2.writeNoException();
                parcel2.writeTypedList(zzg);
                return true;
            case 18:
                zzbo.zzc(parcel);
                zzm((zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 19:
                zzbo.zzc(parcel);
                zzr((Bundle) zzbo.zza(parcel, Bundle.CREATOR), (zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            case 20:
                zzbo.zzc(parcel);
                zzp((zzq) zzbo.zza(parcel, zzq.CREATOR));
                parcel2.writeNoException();
                return true;
            default:
                return false;
        }
    }
}
