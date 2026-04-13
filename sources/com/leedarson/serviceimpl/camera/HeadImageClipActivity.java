package com.leedarson.serviceimpl.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.camera.view.ClipViewLayout;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import timber.log.a;

public class HeadImageClipActivity extends BaseActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private double A4;
    private Button a1;
    protected HashMap<String, String> a2 = new HashMap<>();
    private ClipViewLayout p0;
    private Button p1;
    private a p2;
    boolean p3 = false;
    private Uri p4;
    private Uri z4;

    static /* synthetic */ void X(HeadImageClipActivity x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 7392, new Class[]{HeadImageClipActivity.class}, Void.TYPE).isSupported) {
            x0.Z();
        }
    }

    public void initView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7383, new Class[0], Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setDarkMode(this);
            this.p2 = new a(this);
            Z();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7384, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            a aVar = this.p2;
            if (aVar != null) {
                aVar.removeCallbacksAndMessages((Object) null);
            }
        }
    }

    private void Z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7385, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (ClipViewLayout) findViewById(R$id.clipViewLayout);
            this.a1 = (Button) findViewById(R$id.cancel_action);
            this.p1 = (Button) findViewById(R$id.use_action);
            if (getIntent() != null) {
                this.p4 = getIntent().getData();
                this.z4 = (Uri) getIntent().getParcelableExtra("output");
                Intent intent = getIntent();
                float cutRatio = intent.getFloatExtra("cutRatio", 1.0f);
                int cutShape = intent.getIntExtra("cutShape", 1);
                this.A4 = intent.getDoubleExtra("scale", 1.0d);
                this.p0.setClipType(cutShape);
                this.p0.setCutRatio(cutRatio);
            }
            this.a1.setOnClickListener(this);
            this.p1.setOnClickListener(this);
            this.p0.setVisibility(0);
            this.p0.setImageSrc(this.p4);
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7386, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
        }
    }

    public int O() {
        return R$layout.activity_clip_image;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7387, new Class[0], Void.TYPE).isSupported) {
            initView();
        }
    }

    public void R() {
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 7388, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.cancel_action) {
            setResult(0);
            finish();
        } else if (viewId == R$id.use_action) {
            Y();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7389, new Class[0], Void.TYPE).isSupported) {
            super.finish();
        }
    }

    public static class a extends Handler {
        public static ChangeQuickRedirect changeQuickRedirect;
        WeakReference<HeadImageClipActivity> a;

        a(HeadImageClipActivity activity) {
            this.a = new WeakReference<>(activity);
        }

        public void handleMessage(Message msg) {
            HeadImageClipActivity mActivity;
            if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 7393, new Class[]{Message.class}, Void.TYPE).isSupported && (mActivity = (HeadImageClipActivity) this.a.get()) != null) {
                switch (msg.what) {
                    case 241:
                        HeadImageClipActivity.X(mActivity);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7390, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            finish();
        }
    }

    private void Y() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7391, new Class[0], Void.TYPE).isSupported) {
            Bitmap zoomedCropBitmap = this.p0.d(this.A4).copy(Bitmap.Config.RGB_565, true);
            if (zoomedCropBitmap != null) {
                a.b g = timber.log.a.g("HeadImageClipActivity");
                g.h("savePath " + this.z4, new Object[0]);
                if (this.z4 != null) {
                    File saveFile = new File(this.z4.getPath());
                    if (!saveFile.exists()) {
                        try {
                            saveFile.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    long l = System.currentTimeMillis();
                    File cacheDir = getCacheDir();
                    Uri mSaveUri = Uri.fromFile(new File(cacheDir, "cropped" + l + ".jpg"));
                    if (this.z4 != null) {
                        OutputStream outputStream = null;
                        try {
                            OutputStream outputStream2 = getContentResolver().openOutputStream(mSaveUri);
                            if (outputStream2 != null) {
                                zoomedCropBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream2);
                            }
                            if (outputStream2 != null) {
                                try {
                                    outputStream2.close();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }
                        } catch (IOException ex) {
                            a.b g2 = timber.log.a.g("HeadImageClipActivity");
                            g2.c("Cannot open file: " + mSaveUri + ex, new Object[0]);
                            if (outputStream != null) {
                                outputStream.close();
                            }
                        } catch (Throwable th) {
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (IOException e3) {
                                    e3.printStackTrace();
                                }
                            }
                            throw th;
                        }
                        Intent intent = new Intent();
                        intent.setData(mSaveUri);
                        setResult(-1, intent);
                        finish();
                    }
                }
            }
        }
    }
}
