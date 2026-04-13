package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public final class zzhi implements Runnable {
    private final /* synthetic */ Uri zzco;
    private final /* synthetic */ boolean zzcp;
    private final /* synthetic */ String zzcs;
    private final /* synthetic */ BaseImplementation.ResultHolder zzfh;
    private final /* synthetic */ zzhg zzfi;

    zzhi(zzhg zzhg, Uri uri, BaseImplementation.ResultHolder resultHolder, boolean z, String str) {
        this.zzfi = zzhg;
        this.zzco = uri;
        this.zzfh = resultHolder;
        this.zzcp = z;
        this.zzcs = str;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing receiveFileFromChannelTask");
        }
        if (!"file".equals(this.zzco.getScheme())) {
            Log.w("WearableClient", "Channel.receiveFile used with non-file URI");
            this.zzfh.setFailedResult(new Status(10, "Channel.receiveFile used with non-file URI"));
            return;
        }
        File file = new File(this.zzco.getPath());
        try {
            ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 671088640 | (this.zzcp ? 33554432 : 0));
            try {
                ((zzep) this.zzfi.getService()).zza((zzek) new zzhf(this.zzfh), this.zzcs, open);
                try {
                    open.close();
                } catch (IOException e) {
                    Log.w("WearableClient", "Failed to close targetFd", e);
                }
            } catch (RemoteException e2) {
                Log.w("WearableClient", "Channel.receiveFile failed.", e2);
                this.zzfh.setFailedResult(new Status(8));
                try {
                    open.close();
                } catch (IOException e3) {
                    Log.w("WearableClient", "Failed to close targetFd", e3);
                }
            } catch (Throwable th) {
                try {
                    open.close();
                } catch (IOException e4) {
                    Log.w("WearableClient", "Failed to close targetFd", e4);
                }
                throw th;
            }
        } catch (FileNotFoundException e5) {
            String valueOf = String.valueOf(file);
            StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 49);
            sb.append("File couldn't be opened for Channel.receiveFile: ");
            sb.append(valueOf);
            Log.w("WearableClient", sb.toString());
            this.zzfh.setFailedResult(new Status(13));
        }
    }
}
