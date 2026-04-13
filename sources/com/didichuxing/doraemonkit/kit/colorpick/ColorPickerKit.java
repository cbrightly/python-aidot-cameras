package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Context;
import android.content.Intent;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.ColorPickConfig;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.kit.AbstractKit;
import com.didichuxing.doraemonkit.kit.core.TranslucentActivity;
import net.sqlcipher.database.SQLiteDatabase;

public class ColorPickerKit extends AbstractKit {
    private static final String TAG = "ColorPicker";

    public int getName() {
        return R.string.dk_kit_color_picker;
    }

    public int getIcon() {
        return R.mipmap.dk_color_picker;
    }

    public void onClick(Context context) {
        Intent intent = new Intent(context, TranslucentActivity.class);
        intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
        intent.putExtra(BundleKey.FRAGMENT_INDEX, 4);
        context.startActivity(intent);
    }

    public void onAppInit(Context context) {
        ColorPickConfig.setColorPickOpen(false);
    }

    public boolean isInnerKit() {
        return true;
    }

    public String innerKitId() {
        return "dokit_sdk_ui_ck_color_pick";
    }
}
