package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.ColorPickConfig;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.core.TranslucentActivity;
import com.didichuxing.doraemonkit.util.ColorUtil;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class ColorPickerInfoDokitView extends AbsDokitView {
    private ImageView mClose;
    private ImageView mColor;
    private TextView mColorHex;

    public void onCreate(Context context) {
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_color_picker_info, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.width = getScreenShortSideLength();
        params.height = -2;
        params.x = 0;
        params.y = UIUtils.getHeightPixels() - UIUtils.dp2px(95.0f);
    }

    public void onViewCreated(FrameLayout view) {
        initView();
    }

    private void initView() {
        this.mColor = (ImageView) findViewById(R.id.color);
        this.mColorHex = (TextView) findViewById(R.id.color_hex);
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.mClose = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View view2 = view;
                ColorPickManager.getInstance().setColorPickerDokitView((ColorPickerDokitView) null);
                ColorPickConfig.setColorPickOpen(false);
                DokitViewManager.getInstance().detach(ColorPickerDokitView.class.getSimpleName());
                DokitViewManager.getInstance().detach(ColorPickerInfoDokitView.class.getSimpleName());
                if (a.b() != null && (a.b() instanceof TranslucentActivity)) {
                    a.b().finish();
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
    }

    public void showInfo(@ColorInt int colorInt, int x, int y) {
        this.mColor.setImageDrawable(new ColorDrawable(colorInt));
        this.mColorHex.setText(String.format(ColorPickConstants.TEXT_FOCUS_INFO, new Object[]{ColorUtil.parseColorInt(colorInt), Integer.valueOf(x + 16), Integer.valueOf(y + 16)}));
    }

    public void onEnterBackground() {
    }

    public void onEnterForeground() {
    }
}
