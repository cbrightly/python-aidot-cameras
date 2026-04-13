package pub.devrel.easypermissions;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import pub.devrel.easypermissions.EasyPermissions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
/* compiled from: RationaleDialogFragment */
public class f extends DialogFragment {
    private EasyPermissions.PermissionCallbacks c;
    private EasyPermissions.a d;
    private b f;
    private boolean q = false;

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

    public static f a(@NonNull String positiveButton, @NonNull String negativeButton, @NonNull String rationaleMsg, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String[] permissions) {
        f dialogFragment = new f();
        dialogFragment.setArguments(new e(positiveButton, negativeButton, rationaleMsg, theme, requestCode, dialogStrategy, permissions).b());
        return dialogFragment;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (Build.VERSION.SDK_INT >= 17 && getParentFragment() != null) {
            if (getParentFragment() instanceof EasyPermissions.PermissionCallbacks) {
                this.c = (EasyPermissions.PermissionCallbacks) getParentFragment();
            }
            if (getParentFragment() instanceof EasyPermissions.a) {
                this.d = (EasyPermissions.a) getParentFragment();
            }
        }
        if (context instanceof EasyPermissions.PermissionCallbacks) {
            this.c = (EasyPermissions.PermissionCallbacks) context;
        }
        if (context instanceof EasyPermissions.a) {
            this.d = (EasyPermissions.a) context;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        this.q = true;
        super.onSaveInstanceState(outState);
    }

    public void b(FragmentManager manager, String tag) {
        if ((Build.VERSION.SDK_INT < 26 || !manager.isStateSaved()) && !this.q) {
            show(manager, tag);
        }
    }

    public void onDetach() {
        super.onDetach();
        this.c = null;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        e config = new e(getArguments());
        d clickListener = new d(this, config, this.c, this.d);
        b bVar = config.g;
        this.f = bVar;
        if (bVar == null) {
            return config.a(getActivity(), clickListener);
        }
        bVar.setOnClickListener(clickListener);
        return this.f.a(getActivity(), config.a, config.b, config.e);
    }
}
