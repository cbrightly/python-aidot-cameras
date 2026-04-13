package com.lzf.easyfloat.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.lzf.easyfloat.interfaces.OnPermissionResult;
import com.lzf.easyfloat.utils.Logger;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PermissionFragment.kt */
public final class PermissionFragment extends Fragment {
    @NotNull
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    @Nullable
    public static OnPermissionResult onPermissionResult;

    public void _$_clearFindViewByIdCache() {
    }

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        FragmentTrackHelper.trackOnHiddenChanged(this, z);
    }

    @SensorsDataInstrumented
    public void onPause() {
        super.onPause();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @SensorsDataInstrumented
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentTrackHelper.onFragmentViewCreated(this, view, bundle);
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z);
    }

    /* compiled from: PermissionFragment.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final void requestPermission(@NotNull Activity activity, @NotNull OnPermissionResult onPermissionResult) {
            k.e(activity, "activity");
            k.e(onPermissionResult, "onPermissionResult");
            PermissionFragment.onPermissionResult = onPermissionResult;
            activity.getFragmentManager().beginTransaction().add(new PermissionFragment(), activity.getLocalClassName()).commitAllowingStateLoss();
        }
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PermissionUtils.INSTANCE.requestPermission$easyfloat_release(this);
        Logger.INSTANCE.i("PermissionFragment：requestPermission");
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 199) {
            new Handler(Looper.getMainLooper()).postDelayed(new a(this), 500);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: onActivityResult$lambda-0  reason: not valid java name */
    public static final void m13onActivityResult$lambda0(PermissionFragment this$0) {
        k.e(this$0, "this$0");
        Activity activity = this$0.getActivity();
        if (activity != null) {
            boolean check = PermissionUtils.checkPermission(activity);
            Logger.INSTANCE.i(k.l("PermissionFragment onActivityResult: ", Boolean.valueOf(check)));
            OnPermissionResult onPermissionResult2 = onPermissionResult;
            if (onPermissionResult2 != null) {
                onPermissionResult2.permissionResult(check);
            }
            onPermissionResult = null;
            this$0.getFragmentManager().beginTransaction().remove(this$0).commitAllowingStateLoss();
        }
    }
}
