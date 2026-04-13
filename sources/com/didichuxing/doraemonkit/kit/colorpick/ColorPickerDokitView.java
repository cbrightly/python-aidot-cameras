package com.didichuxing.doraemonkit.kit.colorpick;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.annotation.RequiresApi;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.util.ImageUtil;
import com.didichuxing.doraemonkit.util.UIUtils;

@RequiresApi(api = 21)
public class ColorPickerDokitView extends AbsDokitView {
    private int height;
    /* access modifiers changed from: private */
    public ImageCapture mImageCapture;
    private ColorPickerInfoDokitView mInfoDokitView;
    /* access modifiers changed from: private */
    public ColorPickerView mPickerView;
    private int statusBarHeight;
    private int width;

    public void onCreate(Context context) {
        ColorPickManager.getInstance().setColorPickerDokitView(this);
        this.mInfoDokitView = (ColorPickerInfoDokitView) DokitViewManager.getInstance().getDokitView(a.b(), ColorPickerInfoDokitView.class.getSimpleName());
        ImageCapture imageCapture = new ImageCapture();
        this.mImageCapture = imageCapture;
        try {
            imageCapture.init(context, getBundle(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void onScreenServiceReady() {
        this.mImageCapture.initImageRead(ColorPickManager.getInstance().getMediaProjection());
    }

    public void onViewCreated(FrameLayout view) {
        initView();
    }

    private void initView() {
        ColorPickerView colorPickerView = (ColorPickerView) findViewById(R.id.picker_view);
        this.mPickerView = colorPickerView;
        ViewGroup.LayoutParams params = colorPickerView.getLayoutParams();
        params.width = 512;
        params.height = 512;
        this.mPickerView.setLayoutParams(params);
        this.width = UIUtils.getWidthPixels();
        this.height = UIUtils.getHeightPixels();
        this.statusBarHeight = UIUtils.getStatusBarHeight();
        captureInfo(500);
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_color_picker, (ViewGroup) null);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = 520;
        int i = DokitViewLayoutParams.WRAP_CONTENT;
        params.height = i;
        params.width = i;
    }

    public void onDestroy() {
        super.onDestroy();
        this.mImageCapture.destroy();
    }

    /* access modifiers changed from: private */
    public void showInfo() {
        int y;
        int x;
        if (isNormalMode()) {
            x = getNormalLayoutParams().leftMargin;
            y = getNormalLayoutParams().topMargin;
        } else {
            x = getSystemLayoutParams().x;
            y = getSystemLayoutParams().y;
        }
        int startX = (x + 256) - (32 / 2);
        int startY = ((y + 256) - (32 / 2)) + UIUtils.getStatusBarHeight();
        Bitmap bitmap = this.mImageCapture.getPartBitmap(startX, startY, 32, 32);
        if (bitmap != null) {
            int colorInt = ImageUtil.getPixel(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
            this.mPickerView.setBitmap(bitmap, colorInt, startX, startY);
            this.mInfoDokitView.showInfo(colorInt, startX, startY);
        }
    }

    private void captureInfo(int delay) {
        this.mPickerView.setVisibility(4);
        getRootView().postDelayed(new Runnable() {
            public void run() {
                ColorPickerDokitView.this.mImageCapture.capture();
                ColorPickerDokitView.this.mPickerView.setVisibility(0);
                ColorPickerDokitView.this.showInfo();
            }
        }, (long) delay);
    }

    public void onDown(int x, int y) {
        super.onDown(x, y);
        captureInfo(100);
    }

    public void onMove(int x, int y, int dx, int dy) {
        super.onMove(x, y, dx, dy);
        if (isNormalMode()) {
            checkBound(getNormalLayoutParams());
        } else {
            checkBound(getSystemLayoutParams());
        }
        showInfo();
    }

    private void checkBound(FrameLayout.LayoutParams layoutParams) {
        if (layoutParams.leftMargin < (-this.mPickerView.getWidth()) / 2) {
            layoutParams.leftMargin = (-this.mPickerView.getWidth()) / 2;
        }
        if (layoutParams.leftMargin > (this.width - (this.mPickerView.getWidth() / 2)) - 16) {
            layoutParams.leftMargin = (this.width - (this.mPickerView.getWidth() / 2)) - 16;
        }
        if (layoutParams.topMargin < ((-this.mPickerView.getHeight()) / 2) - this.statusBarHeight) {
            layoutParams.topMargin = ((-this.mPickerView.getHeight()) / 2) - this.statusBarHeight;
        }
        if (layoutParams.topMargin > (this.height - (this.mPickerView.getHeight() / 2)) - 16) {
            layoutParams.topMargin = (this.height - (this.mPickerView.getHeight() / 2)) - 16;
        }
        layoutParams.width = 512;
        layoutParams.height = 512;
        invalidate();
    }

    private void checkBound(WindowManager.LayoutParams layoutParams) {
        if (layoutParams.x < (-this.mPickerView.getWidth()) / 2) {
            layoutParams.x = (-this.mPickerView.getWidth()) / 2;
        }
        if (layoutParams.x > (this.width - (this.mPickerView.getWidth() / 2)) - 16) {
            layoutParams.x = (this.width - (this.mPickerView.getWidth() / 2)) - 16;
        }
        if (layoutParams.y < ((-this.mPickerView.getHeight()) / 2) - this.statusBarHeight) {
            layoutParams.y = ((-this.mPickerView.getHeight()) / 2) - this.statusBarHeight;
        }
        if (layoutParams.y > (this.height - (this.mPickerView.getHeight() / 2)) - 16) {
            layoutParams.y = (this.height - (this.mPickerView.getHeight() / 2)) - 16;
        }
    }

    public void onEnterBackground() {
    }

    public void onEnterForeground() {
    }

    public boolean restrictBorderline() {
        return false;
    }
}
