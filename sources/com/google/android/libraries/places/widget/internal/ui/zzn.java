package com.google.android.libraries.places.widget.internal.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.libraries.places.internal.zzgt;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzn extends AnimatorListenerAdapter {
    final /* synthetic */ View zza;
    final /* synthetic */ RecyclerView.ViewHolder zzb;
    final /* synthetic */ ViewPropertyAnimator zzc;
    final /* synthetic */ zzo zzd;

    zzn(zzo zzo, View view, RecyclerView.ViewHolder viewHolder, ViewPropertyAnimator viewPropertyAnimator) {
        this.zzd = zzo;
        this.zza = view;
        this.zzb = viewHolder;
        this.zzc = viewPropertyAnimator;
    }

    public final void onAnimationCancel(Animator animator) {
        try {
            zzo.zzf(this.zza);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final void onAnimationStart(Animator animator) {
        try {
            this.zza.setAlpha(0.0f);
            this.zzd.dispatchAddStarting(this.zzb);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }

    public final void onAnimationEnd(Animator animator) {
        try {
            this.zzc.setListener((Animator.AnimatorListener) null);
            this.zzd.dispatchAddFinished(this.zzb);
            this.zzd.zzc.remove(this.zzb);
            this.zzd.zze();
            this.zzc.setStartDelay(0);
        } catch (Error | RuntimeException e) {
            zzgt.zzb(e);
            throw e;
        }
    }
}
