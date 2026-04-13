package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.ColorPickConfig;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.core.DokitIntent;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;

public class ColorPickerSettingFragment extends BaseFragment {
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (requestCaptureScreen()) {
            ColorPickConfig.setColorPickOpen(true);
        }
    }

    private boolean requestCaptureScreen() {
        MediaProjectionManager mediaProjectionManager;
        if (Build.VERSION.SDK_INT < 21 || (mediaProjectionManager = (MediaProjectionManager) getContext().getSystemService("media_projection")) == null) {
            return false;
        }
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), 10001);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10001 && resultCode == -1) {
            if (!DokitConstant.IS_NORMAL_FLOAT_MODE) {
                finish();
            }
            showColorPicker(data);
            return;
        }
        showToast((CharSequence) "start color pick fail");
        finish();
    }

    private void showColorPicker(Intent data) {
        DokitViewManager.getInstance().detachToolPanel();
        DokitIntent pageIntent = new DokitIntent(ColorPickerInfoDokitView.class);
        pageIntent.mode = 1;
        DokitViewManager.getInstance().attach(pageIntent);
        DokitIntent pageIntent2 = new DokitIntent(ColorPickerDokitView.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", data);
        pageIntent2.bundle = bundle;
        pageIntent2.mode = 1;
        DokitViewManager.getInstance().attach(pageIntent2);
    }

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_color_picker_setting;
    }
}
