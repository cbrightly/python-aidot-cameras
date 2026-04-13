package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import com.didichuxing.doraemonkit.kit.main.MainIconDokitView;
import com.didichuxing.doraemonkit.widget.dialog.CommonDialogProvider;
import com.didichuxing.doraemonkit.widget.dialog.DialogInfo;
import com.didichuxing.doraemonkit.widget.dialog.DialogProvider;
import com.didichuxing.doraemonkit.widget.dialog.UniversalDialogFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;

public abstract class BaseFragment extends Fragment {
    public String TAG = getClass().getSimpleName();
    private int mContainer;
    private View mRootView;

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

    /* access modifiers changed from: protected */
    @LayoutRes
    public abstract int onRequestLayout();

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

    public final <T extends View> T findViewById(@IdRes int id) {
        return this.mRootView.findViewById(id);
    }

    @Nullable
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
        int id = onRequestLayout();
        if (id > 0) {
            this.mRootView = inflater.inflate(id, container, false);
        }
        if (this.mRootView == null) {
            this.mRootView = onCreateView(savedInstanceState);
        }
        if (interceptTouchEvents() && (view = this.mRootView) != null) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        }
        return this.mRootView;
    }

    @SensorsDataInstrumented
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        View view2 = view;
        super.onViewCreated(view2, savedInstanceState);
        tryGetContainerId();
        try {
            if (view2.getContext() instanceof Activity) {
                ((Activity) view2.getContext()).getWindow().getDecorView().requestLayout();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DokitViewManager.getInstance().detach((Class<? extends AbsDokitView>) MainIconDokitView.class);
        FragmentTrackHelper.onFragmentViewCreated(this, view, savedInstanceState);
    }

    private void tryGetContainerId() {
        View parent;
        View view = this.mRootView;
        if (view != null && (parent = (View) view.getParent()) != null) {
            this.mContainer = parent.getId();
        }
    }

    /* access modifiers changed from: protected */
    public View onCreateView(Bundle savedInstanceState) {
        return this.mRootView;
    }

    /* access modifiers changed from: protected */
    public boolean interceptTouchEvents() {
        return false;
    }

    public int getContainer() {
        if (this.mContainer == 0) {
            tryGetContainerId();
        }
        return this.mContainer;
    }

    /* access modifiers changed from: protected */
    public boolean onBackPressed() {
        return false;
    }

    public void showToast(CharSequence msg) {
        Toast.makeText(getContext(), msg, 0).show();
    }

    public void showToast(@StringRes int res) {
        Toast.makeText(getContext(), res, 0).show();
    }

    public void showContent(Class<? extends BaseFragment> fragmentClass, Bundle bundle) {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.showContent(fragmentClass, bundle);
        }
    }

    public void finish() {
        BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null) {
            activity.doBack(this);
        }
    }

    public void showContent(Class<? extends BaseFragment> fragmentClass) {
        showContent(fragmentClass, (Bundle) null);
    }

    public void onDestroyView() {
        super.onDestroyView();
    }

    public DialogProvider showDialog(DialogInfo dialogInfo) {
        CommonDialogProvider provider = new CommonDialogProvider(dialogInfo, dialogInfo.listener);
        showDialog((DialogProvider) provider);
        return provider;
    }

    public void showDialog(DialogProvider provider) {
        UniversalDialogFragment dialog = new UniversalDialogFragment();
        provider.setHost(dialog);
        dialog.setProvider(provider);
        provider.show(getChildFragmentManager());
    }

    public void dismissDialog(DialogProvider provider) {
        provider.dismiss();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mRootView != null) {
            this.mRootView = null;
        }
    }
}
