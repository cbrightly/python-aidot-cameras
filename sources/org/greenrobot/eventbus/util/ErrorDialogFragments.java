package org.greenrobot.eventbus.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;

public class ErrorDialogFragments {
    public static int a = 0;
    public static Class<?> b;

    public static Dialog a(Context context, Bundle arguments, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(arguments.getString("de.greenrobot.eventbus.errordialog.title"));
        builder.setMessage(arguments.getString("de.greenrobot.eventbus.errordialog.message"));
        int i = a;
        if (i != 0) {
            builder.setIcon(i);
        }
        builder.setPositiveButton(17039370, onClickListener);
        return builder.create();
    }

    public static void b(DialogInterface dialog, int which, Activity activity, Bundle arguments) {
        Class<?> cls = b;
        if (cls != null) {
            try {
                cls.newInstance();
                throw null;
            } catch (Exception e) {
                throw new RuntimeException("Event cannot be constructed", e);
            }
        } else if (arguments.getBoolean("de.greenrobot.eventbus.errordialog.finish_after_dialog", false) && activity != null) {
            activity.finish();
        }
    }

    public static class Support extends DialogFragment implements DialogInterface.OnClickListener {
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

        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return ErrorDialogFragments.a(getActivity(), getArguments(), this);
        }

        @SensorsDataInstrumented
        public void onClick(DialogInterface dialog, int which) {
            ErrorDialogFragments.b(dialog, which, getActivity(), getArguments());
            SensorsDataAutoTrackHelper.trackDialog(dialog, which);
        }
    }
}
