package com.google.android.material.bottomsheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class BottomSheetDialogFragment extends AppCompatDialogFragment {
    private boolean waitingForDismissAllowingStateLoss;

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), getTheme());
    }

    public void dismiss() {
        if (!tryDismissWithAnimation(false)) {
            super.dismiss();
        }
    }

    public void dismissAllowingStateLoss() {
        if (!tryDismissWithAnimation(true)) {
            super.dismissAllowingStateLoss();
        }
    }

    private boolean tryDismissWithAnimation(boolean allowingStateLoss) {
        Dialog baseDialog = getDialog();
        if (!(baseDialog instanceof BottomSheetDialog)) {
            return false;
        }
        BottomSheetDialog dialog = (BottomSheetDialog) baseDialog;
        BottomSheetBehavior<FrameLayout> behavior = dialog.getBehavior();
        if (!behavior.isHideable() || !dialog.getDismissWithAnimation()) {
            return false;
        }
        dismissWithAnimation(behavior, allowingStateLoss);
        return true;
    }

    private void dismissWithAnimation(@NonNull BottomSheetBehavior<?> behavior, boolean allowingStateLoss) {
        this.waitingForDismissAllowingStateLoss = allowingStateLoss;
        if (behavior.getState() == 5) {
            dismissAfterAnimation();
            return;
        }
        if (getDialog() instanceof BottomSheetDialog) {
            ((BottomSheetDialog) getDialog()).removeDefaultCallback();
        }
        behavior.addBottomSheetCallback(new BottomSheetDismissCallback());
        behavior.setState(5);
    }

    /* access modifiers changed from: private */
    public void dismissAfterAnimation() {
        if (this.waitingForDismissAllowingStateLoss) {
            super.dismissAllowingStateLoss();
        } else {
            super.dismiss();
        }
    }

    public class BottomSheetDismissCallback extends BottomSheetBehavior.BottomSheetCallback {
        private BottomSheetDismissCallback() {
        }

        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == 5) {
                BottomSheetDialogFragment.this.dismissAfterAnimation();
            }
        }

        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }
}
