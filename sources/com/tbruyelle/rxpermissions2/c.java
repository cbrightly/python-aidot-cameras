package com.tbruyelle.rxpermissions2;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.b;
import java.util.HashMap;
import java.util.Map;

/* compiled from: RxPermissionsFragment */
public class c extends Fragment {
    private Map<String, b<a>> c = new HashMap();
    private boolean d;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /* access modifiers changed from: package-private */
    @TargetApi(23)
    public void g(@NonNull String[] permissions) {
        requestPermissions(permissions, 42);
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 42) {
            boolean[] shouldShowRequestPermissionRationale = new boolean[permissions.length];
            for (int i = 0; i < permissions.length; i++) {
                shouldShowRequestPermissionRationale[i] = shouldShowRequestPermissionRationale(permissions[i]);
            }
            f(permissions, grantResults, shouldShowRequestPermissionRationale);
        }
    }

    /* access modifiers changed from: package-private */
    public void f(String[] permissions, int[] grantResults, boolean[] shouldShowRequestPermissionRationale) {
        int size = permissions.length;
        for (int i = 0; i < size; i++) {
            e("onRequestPermissionsResult  " + permissions[i]);
            PublishSubject<Permission> subject = (b) this.c.get(permissions[i]);
            if (subject == null) {
                Log.e("RxPermissions", "RxPermissions.onRequestPermissionsResult invoked but didn't find the corresponding permission request.");
                return;
            }
            this.c.remove(permissions[i]);
            subject.onNext(new a(permissions[i], grantResults[i] == 0, shouldShowRequestPermissionRationale[i]));
            subject.onComplete();
        }
    }

    /* access modifiers changed from: package-private */
    @TargetApi(23)
    public boolean c(String permission) {
        return getActivity().checkSelfPermission(permission) == 0;
    }

    /* access modifiers changed from: package-private */
    @TargetApi(23)
    public boolean d(String permission) {
        return getActivity().getPackageManager().isPermissionRevokedByPolicy(permission, getActivity().getPackageName());
    }

    public b<a> b(@NonNull String permission) {
        return this.c.get(permission);
    }

    public boolean a(@NonNull String permission) {
        return this.c.containsKey(permission);
    }

    public b<a> h(@NonNull String permission, @NonNull b<a> subject) {
        return this.c.put(permission, subject);
    }

    /* access modifiers changed from: package-private */
    public void e(String message) {
        if (this.d) {
            Log.d("RxPermissions", message);
        }
    }
}
