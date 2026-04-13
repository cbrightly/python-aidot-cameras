package com.leedarson.newui.cloud_play_back;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.ui.BaseFragmentActivity;
import com.leedarson.newui.cloud_play_back.repos.beans.SignalEventParamsBean;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.leedarson.view.titlelayout.LDSTitleLayoutView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class CloudPlayBackEventSignalActivity extends BaseFragmentActivity {
    public static String a2 = "CloudPlayBackEventSignalActivity.KEY_PARAMS_DATA";
    public static ChangeQuickRedirect changeQuickRedirect;
    private LDSTitleLayoutView p2;
    private FrameLayout p3;
    CloudPlayBackEventSignalFragment p4;
    SignalEventParamsBean z4;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 3570, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            super.onCreate(savedInstanceState);
        }
    }

    public int S() {
        return R$layout.cloud_playback_event_signal_activity;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3571, new Class[0], Void.TYPE).isSupported) {
            b0();
            this.p2 = (LDSTitleLayoutView) findViewById(R$id.layout_title_box);
        }
    }

    private void b0() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3572, new Class[0], Void.TYPE).isSupported) {
            LDSTitleLayoutView lDSTitleLayoutView = (LDSTitleLayoutView) findViewById(R$id.layout_title_box);
            this.p2 = lDSTitleLayoutView;
            Y(lDSTitleLayoutView.getTitleTxt(), PubUtils.getString(this, R$string.live_cloud_play_back_new_title), "IPC", "Playback List");
            this.p2.set_EventActionHandler(new a());
            int i = R$id.frameContent_box;
            this.p3 = (FrameLayout) findViewById(i);
            CloudPlayBackEventSignalFragment cloudPlayBackEventSignalFragment = new CloudPlayBackEventSignalFragment();
            this.p4 = cloudPlayBackEventSignalFragment;
            M(cloudPlayBackEventSignalFragment.G4.I(new a(this), b.c));
            this.p4.r3(this.z4);
            getSupportFragmentManager().beginTransaction().add(i, (Fragment) this.p4).commit();
        }
    }

    public class a implements LDSTitleLayoutView.d {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3579, new Class[0], Void.TYPE).isSupported) {
                CloudPlayBackEventSignalActivity.this.finish();
            }
        }

        public void a() {
        }

        public void c() {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: c0 */
    public /* synthetic */ void e0(Boolean aBoolean) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{aBoolean}, this, changeQuickRedirect, false, 3578, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            LDSTitleLayoutView lDSTitleLayoutView = this.p2;
            if (!aBoolean.booleanValue()) {
                i = 8;
            }
            lDSTitleLayoutView.setVisibility(i);
        }
    }

    static /* synthetic */ void f0(Throwable throwable) {
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3573, new Class[0], Void.TYPE).isSupported) {
            if (getIntent().hasExtra(a2)) {
                this.z4 = (SignalEventParamsBean) new Gson().fromJson(getIntent().getStringExtra(a2), SignalEventParamsBean.class);
                return;
            }
            finish();
        }
    }

    public void onNewIntent(Intent intent) {
        if (!PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 3574, new Class[]{Intent.class}, Void.TYPE).isSupported) {
            super.onNewIntent(intent);
            if (intent.hasExtra(a2)) {
                SignalEventParamsBean signalEventParamsBean = (SignalEventParamsBean) new Gson().fromJson(getIntent().getStringExtra(a2), SignalEventParamsBean.class);
                this.z4 = signalEventParamsBean;
                this.p4.q3(signalEventParamsBean);
                return;
            }
            finish();
        }
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3575, new Class[0], Void.TYPE).isSupported) {
            super.onBackPressed();
            if (!this.p4.f0()) {
                this.p4.T1();
            }
        }
    }

    @SuppressLint({"RestrictedApi"})
    public boolean dispatchKeyEvent(KeyEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 3576, new Class[]{KeyEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getKeyCode() != 4 || this.p4.f0()) {
            return super.dispatchKeyEvent(event);
        }
        this.p4.T1();
        return true;
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 3577, new Class[0], Void.TYPE).isSupported) {
            super.finish();
        }
    }
}
