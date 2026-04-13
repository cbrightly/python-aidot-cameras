package pub.devrel.easypermissions;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import pub.devrel.easypermissions.EasyPermissions;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class RationaleDialogFragmentCompat extends AppCompatDialogFragment {
    private EasyPermissions.PermissionCallbacks c;
    private EasyPermissions.a d;
    private b f;

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

    public static RationaleDialogFragmentCompat l1(@NonNull String rationaleMsg, @NonNull String positiveButton, @NonNull String negativeButton, @StyleRes int theme, int requestCode, b dialogStrategy, @NonNull String[] permissions) {
        RationaleDialogFragmentCompat dialogFragment = new RationaleDialogFragmentCompat();
        dialogFragment.setArguments(new e(positiveButton, negativeButton, rationaleMsg, theme, requestCode, dialogStrategy, permissions).b());
        dialogFragment.f = dialogStrategy;
        return dialogFragment;
    }

    public void m1(FragmentManager manager, String tag) {
        if (!manager.isStateSaved()) {
            show(manager, tag);
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() != null) {
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

    public void onDetach() {
        super.onDetach();
        this.c = null;
        this.d = null;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setCancelable(false);
        e config = new e(getArguments());
        d clickListener = new d(this, config, this.c, this.d);
        b bVar = this.f;
        if (bVar == null) {
            return config.a(getActivity(), clickListener);
        }
        bVar.setOnClickListener(clickListener);
        return this.f.a(getActivity(), config.a, config.b, config.e);
    }
}
