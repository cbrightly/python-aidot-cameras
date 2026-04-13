package com.didichuxing.doraemonkit.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public abstract class DialogProvider<T> {
    private boolean cancellable = true;
    private T mData;
    private DialogListener mDialogListener;
    private DialogFragment mHost;
    private View mView;

    /* access modifiers changed from: protected */
    public abstract void findViews(View view);

    public abstract int getLayoutId();

    public DialogProvider(T data, DialogListener listener) {
        this.mDialogListener = listener;
        this.mData = data;
    }

    public void setHost(DialogFragment host) {
        this.mHost = host;
    }

    public DialogFragment getHost() {
        return this.mHost;
    }

    public Context getContext() {
        DialogFragment dialogFragment = this.mHost;
        if (dialogFragment == null) {
            return null;
        }
        return dialogFragment.getContext();
    }

    public final View createView(LayoutInflater inflater, ViewGroup parent) {
        View inflate = inflater.inflate(getLayoutId(), parent, false);
        this.mView = inflate;
        return inflate;
    }

    public final void onViewCreated(View view) {
        findViews(view);
        registerForListeners();
        bindData(this.mData);
    }

    /* access modifiers changed from: protected */
    public void bindData(T t) {
    }

    private void registerForListeners() {
        View positiveView = getPositiveView();
        if (positiveView != null) {
            positiveView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    DialogProvider.this.onPositive();
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
        View negativeView = getNegativeView();
        if (negativeView != null) {
            negativeView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    DialogProvider.this.onNegative();
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
        View cancelView = getCancelView();
        if (cancelView != null) {
            cancelView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    DialogProvider.this.cancel();
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void onPositive() {
        boolean dismiss = true;
        DialogListener dialogListener = this.mDialogListener;
        if (dialogListener != null) {
            dismiss = dialogListener.onPositive();
        }
        if (dismiss) {
            dismiss();
        }
    }

    /* access modifiers changed from: private */
    public void onNegative() {
        boolean dismiss = true;
        DialogListener dialogListener = this.mDialogListener;
        if (dialogListener != null) {
            dismiss = dialogListener.onNegative();
        }
        if (dismiss) {
            dismiss();
        }
    }

    public void show(FragmentManager childFragmentManager) {
        this.mHost.show(childFragmentManager, (String) null);
    }

    public void dismiss() {
        this.mHost.dismiss();
    }

    /* access modifiers changed from: protected */
    public void cancel() {
        dismiss();
        DialogListener dialogListener = this.mDialogListener;
        if (dialogListener != null) {
            dialogListener.onCancel();
        }
    }

    /* access modifiers changed from: package-private */
    public void onCancel() {
        DialogListener dialogListener = this.mDialogListener;
        if (dialogListener != null) {
            dialogListener.onCancel();
        }
    }

    public boolean isCancellable() {
        return this.cancellable;
    }

    public void setCancellable(boolean cancellable2) {
        this.cancellable = cancellable2;
    }

    /* access modifiers changed from: protected */
    public View getPositiveView() {
        return null;
    }

    /* access modifiers changed from: protected */
    public View getNegativeView() {
        return null;
    }

    /* access modifiers changed from: protected */
    public View getCancelView() {
        return null;
    }

    public void setDialogListener(DialogListener dialogListener) {
        this.mDialogListener = dialogListener;
    }
}
