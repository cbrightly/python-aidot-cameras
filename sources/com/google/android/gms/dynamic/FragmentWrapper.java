package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IFragmentWrapper;

@SuppressLint({"NewApi"})
@KeepForSdk
/* compiled from: com.google.android.gms:play-services-basement@@18.2.0 */
public final class FragmentWrapper extends IFragmentWrapper.Stub {
    private final Fragment zza;

    private FragmentWrapper(Fragment fragment) {
        this.zza = fragment;
    }

    @KeepForSdk
    @Nullable
    public static FragmentWrapper wrap(@Nullable Fragment fragment) {
        if (fragment != null) {
            return new FragmentWrapper(fragment);
        }
        return null;
    }

    public final boolean zzA() {
        return this.zza.isVisible();
    }

    public final int zzb() {
        return this.zza.getId();
    }

    public final int zzc() {
        return this.zza.getTargetRequestCode();
    }

    @Nullable
    public final Bundle zzd() {
        return this.zza.getArguments();
    }

    @Nullable
    public final IFragmentWrapper zze() {
        return wrap(this.zza.getParentFragment());
    }

    @Nullable
    public final IFragmentWrapper zzf() {
        return wrap(this.zza.getTargetFragment());
    }

    @NonNull
    public final IObjectWrapper zzg() {
        return ObjectWrapper.wrap(this.zza.getActivity());
    }

    @NonNull
    public final IObjectWrapper zzh() {
        return ObjectWrapper.wrap(this.zza.getResources());
    }

    @NonNull
    public final IObjectWrapper zzi() {
        return ObjectWrapper.wrap(this.zza.getView());
    }

    @Nullable
    public final String zzj() {
        return this.zza.getTag();
    }

    public final void zzk(@NonNull IObjectWrapper iObjectWrapper) {
        View view = (View) ObjectWrapper.unwrap(iObjectWrapper);
        Fragment fragment = this.zza;
        Preconditions.checkNotNull(view);
        fragment.registerForContextMenu(view);
    }

    public final void zzl(boolean z) {
        this.zza.setHasOptionsMenu(z);
    }

    public final void zzm(boolean z) {
        this.zza.setMenuVisibility(z);
    }

    public final void zzn(boolean z) {
        this.zza.setRetainInstance(z);
    }

    public final void zzo(boolean z) {
        this.zza.setUserVisibleHint(z);
    }

    public final void zzp(@NonNull Intent intent) {
        this.zza.startActivity(intent);
    }

    public final void zzq(@NonNull Intent intent, int i) {
        this.zza.startActivityForResult(intent, i);
    }

    public final void zzr(@NonNull IObjectWrapper iObjectWrapper) {
        View view = (View) ObjectWrapper.unwrap(iObjectWrapper);
        Fragment fragment = this.zza;
        Preconditions.checkNotNull(view);
        fragment.unregisterForContextMenu(view);
    }

    public final boolean zzs() {
        return this.zza.getRetainInstance();
    }

    public final boolean zzt() {
        return this.zza.getUserVisibleHint();
    }

    public final boolean zzu() {
        return this.zza.isAdded();
    }

    public final boolean zzv() {
        return this.zza.isDetached();
    }

    public final boolean zzw() {
        return this.zza.isHidden();
    }

    public final boolean zzx() {
        return this.zza.isInLayout();
    }

    public final boolean zzy() {
        return this.zza.isRemoving();
    }

    public final boolean zzz() {
        return this.zza.isResumed();
    }
}
