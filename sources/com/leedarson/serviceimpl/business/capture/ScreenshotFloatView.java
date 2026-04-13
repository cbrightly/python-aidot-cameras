package com.leedarson.serviceimpl.business.capture;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.leedarson.serviceimpl.business.R;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.io.File;

public class ScreenshotFloatView extends DragFloatView<String> {
    public static ChangeQuickRedirect changeQuickRedirect;
    View contentView;
    private ImageView imageView;

    public /* bridge */ /* synthetic */ void applyData(Object obj) {
        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 7146, new Class[]{Object.class}, Void.TYPE).isSupported) {
            applyData((String) obj);
        }
    }

    public ScreenshotFloatView(Activity activity) {
        super(activity);
        setDraggable(false);
        setKeepSide(true);
    }

    public View onCreateView() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7141, new Class[0], View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.layout_float_view, (ViewGroup) null);
        this.contentView = inflate;
        this.imageView = (ImageView) inflate.findViewById(R.id.image);
        View llCapture = this.contentView.findViewById(R.id.ll_capture);
        ViewGroup.LayoutParams layoutParams = llCapture.getLayoutParams();
        layoutParams.height = this.imageView.getHeight();
        llCapture.setLayoutParams(layoutParams);
        return this.contentView;
    }

    public void applyData(String data) {
        if (!PatchProxy.proxy(new Object[]{data}, this, changeQuickRedirect, false, 7142, new Class[]{String.class}, Void.TYPE).isSupported) {
            new BitmapFactory.Options().inSampleSize = 8;
            Bitmap bitmap = BitmapFactory.decodeFile(data);
            if (bitmap != null) {
                this.imageView.setImageBitmap(bitmap);
            } else {
                this.imageView.setVisibility(8);
            }
            this.imagePath = data;
            String name = new File(this.imagePath).getName();
            int randomPort = SharePreferenceUtils.getPrefInt(this.mActivity.getApplicationContext(), "serverport", 9999);
            this.imageUrl = "https://localhost:" + randomPort + "/" + name;
        }
    }

    public void applyData(Bitmap source) {
        if (!PatchProxy.proxy(new Object[]{source}, this, changeQuickRedirect, false, 7143, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
            if (source != null) {
                int[] size = generateWindowSize();
                this.imageView.setImageBitmap(Bitmap.createScaledBitmap(source, size[0], size[1], false));
            }
        }
    }

    public int[] generateWindowSize() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7144, new Class[0], int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        return new int[]{dp2px(50.0f), dp2px(100.0f)};
    }

    public WindowManager.LayoutParams generateWindowLayoutParam() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7145, new Class[0], WindowManager.LayoutParams.class);
        if (proxy.isSupported) {
            return (WindowManager.LayoutParams) proxy.result;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = getWidth();
        lp.height = getHeight();
        lp.type = 2;
        lp.format = 1;
        lp.flags = 262184;
        lp.gravity = 51;
        lp.x = (getScreenWidth() - getWidth()) - dp2px(10.0f);
        lp.y = (getScreenHeight() - getHeight()) - dp2px(40.0f);
        return lp;
    }
}
