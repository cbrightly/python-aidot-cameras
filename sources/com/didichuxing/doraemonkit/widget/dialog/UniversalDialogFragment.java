package com.didichuxing.doraemonkit.widget.dialog;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;

public class UniversalDialogFragment extends DialogFragment {
    private DialogProvider mProvider;

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
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z);
    }

    public void setProvider(DialogProvider provider) {
        this.mProvider = provider;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, 0);
    }

    public int getTheme() {
        return R.style.DK_Dialog;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(1);
        DialogProvider dialogProvider = this.mProvider;
        if (dialogProvider != null) {
            setCancelable(dialogProvider.isCancellable());
        }
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = getGravity();
        lp.width = getWidth();
        lp.height = getHeight();
        window.setAttributes(lp);
        return this.mProvider.createView(inflater, container);
    }

    @SensorsDataInstrumented
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        Bundle bundle2 = bundle;
        this.mProvider.onViewCreated(view);
        FragmentTrackHelper.onFragmentViewCreated(this, view, bundle);
    }

    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(getWidth(), getHeight());
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(17170445)));
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        this.mProvider.onCancel();
    }

    /* access modifiers changed from: protected */
    public int getGravity() {
        return 17;
    }

    /* access modifiers changed from: protected */
    public int getWidth() {
        return -2;
    }

    /* access modifiers changed from: protected */
    public int getHeight() {
        return -2;
    }
}
