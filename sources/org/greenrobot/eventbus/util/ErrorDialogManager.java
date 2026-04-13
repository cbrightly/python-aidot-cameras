package org.greenrobot.eventbus.util;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import org.greenrobot.eventbus.c;

public class ErrorDialogManager {
    public static a<?> a;

    public static class SupportManagerFragment extends Fragment {
        protected boolean c;
        protected Bundle d;
        private c f;
        private boolean q;
        private Object x;

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

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            throw null;
        }

        @SensorsDataInstrumented
        public void onResume() {
            super.onResume();
            if (this.q) {
                this.q = false;
                FragmentTrackHelper.trackFragmentResume(this);
                return;
            }
            throw null;
        }

        @SensorsDataInstrumented
        public void onPause() {
            this.f.r(this);
            super.onPause();
            FragmentTrackHelper.trackFragmentPause(this);
        }

        public void onEventMainThread(b event) {
            if (ErrorDialogManager.c(this.x, event)) {
                ErrorDialogManager.b(event);
                FragmentManager fm = getFragmentManager();
                fm.executePendingTransactions();
                DialogFragment existingFragment = (DialogFragment) fm.findFragmentByTag("de.greenrobot.eventbus.error_dialog");
                if (existingFragment != null) {
                    existingFragment.dismiss();
                }
                ErrorDialogManager.a.a(event, this.c, this.d);
                throw null;
            }
        }
    }

    protected static void b(b event) {
        throw null;
    }

    /* access modifiers changed from: private */
    public static boolean c(Object executionScope, b event) {
        Object eventExecutionScope;
        if (event == null || (eventExecutionScope = event.a()) == null || eventExecutionScope.equals(executionScope)) {
            return true;
        }
        return false;
    }
}
