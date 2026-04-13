package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.api.internal.LifecycleFragment;

/* compiled from: com.google.android.gms:play-services-base@@18.2.0 */
public abstract class zag implements DialogInterface.OnClickListener {
    public static zag zab(Activity activity, @Nullable Intent intent, int i) {
        return new zad(intent, activity, i);
    }

    public static zag zac(@NonNull Fragment fragment, @Nullable Intent intent, int i) {
        return new zae(intent, fragment, i);
    }

    public static zag zad(@NonNull LifecycleFragment lifecycleFragment, @Nullable Intent intent, int i) {
        return new zaf(intent, lifecycleFragment, 2);
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        try {
            zaa();
        } catch (ActivityNotFoundException e) {
            String str = "Failed to start resolution intent.";
            if (true == Build.FINGERPRINT.contains("generic")) {
                str = "Failed to start resolution intent. This may occur when resolving Google Play services connection issues on emulators with Google APIs but not Google Play Store.";
            }
            Log.e("DialogRedirect", str, e);
        } catch (Throwable th) {
            dialogInterface.dismiss();
            throw th;
        }
        dialogInterface.dismiss();
    }

    /* access modifiers changed from: protected */
    public abstract void zaa();
}
