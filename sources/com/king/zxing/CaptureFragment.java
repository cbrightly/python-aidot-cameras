package com.king.zxing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.leedarson.serviceimpl.camera.R$id;
import com.leedarson.serviceimpl.camera.R$layout;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;

public class CaptureFragment extends Fragment implements m {
    private View c;
    private SurfaceView d;
    private ViewfinderView f;
    private View q;
    private i x;

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        FragmentTrackHelper.trackOnHiddenChanged(this, z);
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (r1(m1())) {
            this.c = inflater.inflate(m1(), container, false);
        }
        q1();
        return this.c;
    }

    public void q1() {
        this.d = (SurfaceView) this.c.findViewById(n1());
        int viewfinderViewId = o1();
        if (viewfinderViewId != 0) {
            this.f = (ViewfinderView) this.c.findViewById(viewfinderViewId);
        }
        int ivTorchId = l1();
        if (ivTorchId != 0) {
            View findViewById = this.c.findViewById(ivTorchId);
            this.q = findViewById;
            findViewById.setVisibility(4);
        }
        p1();
    }

    public void p1() {
        i iVar = new i(this, this.d, this.f, this.q);
        this.x = iVar;
        iVar.F(this);
    }

    public boolean r1(@LayoutRes int layoutId) {
        return true;
    }

    public int m1() {
        return R$layout.zxl_capture;
    }

    public int o1() {
        return R$id.viewfinderView;
    }

    public int n1() {
        return R$id.surfaceView;
    }

    public int l1() {
        return R$id.ivTorch;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.x.v();
    }

    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        this.x.A();
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @SensorsDataInstrumented
    public void onPause() {
        super.onPause();
        this.x.x();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    public void onDestroy() {
        super.onDestroy();
        this.x.w();
    }

    public boolean r0(String result) {
        return false;
    }

    public void P(int status) {
    }
}
