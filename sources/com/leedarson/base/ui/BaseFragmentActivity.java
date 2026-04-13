package com.leedarson.base.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.jsbridge2.WVJBWebView;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.n;
import com.leedarson.base.utils.o;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.INoNetSnapTipView;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.BackAndFrontChangeEvent;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.trello.rxlifecycle3.components.support.RxFragmentActivity;
import java.util.HashMap;
import java.util.List;
import me.jessyan.autosize.internal.CustomAdapt;
import meshsdk.BaseResp;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.l;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public abstract class BaseFragmentActivity extends RxFragmentActivity implements EasyPermissions.PermissionCallbacks, CustomAdapt, o, n {
    public static ChangeQuickRedirect changeQuickRedirect;
    public int a1 = 375;
    private Toast d;
    public io.reactivex.disposables.a f = new io.reactivex.disposables.a();
    public boolean p0 = true;
    private HashMap<Integer, Integer> p1 = new HashMap<>();
    public boolean q = false;
    public int x = Color.parseColor("#1F2429");
    public int y = Color.parseColor("#8C9399");
    public com.leedarson.base.listener.a z;

    public abstract int S();

    public abstract void T();

    public abstract void init();

    public void attachBaseContext(Context newBase) {
        if (!PatchProxy.proxy(new Object[]{newBase}, this, changeQuickRedirect, false, 391, new Class[]{Context.class}, Void.TYPE).isSupported) {
            super.attachBaseContext(newBase);
            U(newBase);
        }
    }

    private void U(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 392, new Class[]{Context.class}, Void.TYPE).isSupported) {
            if (context == null) {
                Configuration _configuration = context.getResources().getConfiguration();
                _configuration.fontScale = 0.5f;
                applyOverrideConfiguration(_configuration);
            }
        }
    }

    public void showToast(int resId) {
        if (!PatchProxy.proxy(new Object[]{new Integer(resId)}, this, changeQuickRedirect, false, 393, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            View toastRoot = LayoutInflater.from(this).inflate(R$layout.layout_toast_image, (ViewGroup) null);
            Toast toast = this.d;
            if (toast != null) {
                toast.cancel();
            }
            Toast toast2 = new Toast(this);
            this.d = toast2;
            toast2.setGravity(17, 0, 0);
            this.d.setView(toastRoot);
            ((TextView) toastRoot.findViewById(R$id.toast_notice)).setText(PubUtils.getString(this, resId));
            this.d.setDuration(1);
            this.d.show();
        }
    }

    public void a0(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 394, new Class[]{String.class}, Void.TYPE).isSupported) {
            View toastRoot = LayoutInflater.from(this).inflate(R$layout.layout_toast_image, (ViewGroup) null);
            Toast toast = this.d;
            if (toast != null) {
                toast.cancel();
            }
            Toast toast2 = new Toast(this);
            this.d = toast2;
            toast2.setGravity(17, 0, 0);
            this.d.setView(toastRoot);
            ((TextView) toastRoot.findViewById(R$id.toast_notice)).setText(content);
            this.d.setDuration(1);
            this.d.show();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 395, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            getWindow().clearFlags(1024);
            O();
            R();
            c.h().a(this);
            T();
            init();
            org.greenrobot.eventbus.c.c().j(this);
            org.greenrobot.eventbus.c.c().p(this);
            W();
            a.b g = timber.log.a.g("PAGENAME");
            g.h("PAGE_NAME====>" + getClass().getSimpleName(), new Object[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public void R() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 396, new Class[0], Void.TYPE).isSupported) {
            setContentView(R$layout.lds_bz_common_act_layout);
            ((RelativeLayout) findViewById(R$id.rl_base_content)).addView(LayoutInflater.from(this).inflate(S(), (ViewGroup) null), new RelativeLayout.LayoutParams(-1, -1));
        }
    }

    public void onStart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 397, new Class[0], Void.TYPE).isSupported) {
            super.onStart();
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.onStart();
            }
        }
    }

    public void onRestart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 398, new Class[0], Void.TYPE).isSupported) {
            super.onRestart();
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.a();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.CODE_NO_HOUSE_ID, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.onResume();
            }
            Log.i("PAGENAME", "onResume PAGE_NAME====>" + getClass().getSimpleName());
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.ERR_MSG_SEND_FAIL, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.onPause();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 401, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.onStop();
            }
        }
    }

    public void finish() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 402, new Class[0], Void.TYPE).isSupported) {
            super.finish();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 403, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            c.h().d(this);
            com.leedarson.base.listener.a aVar = this.z;
            if (aVar != null) {
                aVar.onDestroy();
            }
            org.greenrobot.eventbus.c.c().r(this);
            this.f.dispose();
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), permissions, grantResults}, this, changeQuickRedirect, false, 404, new Class[]{Integer.TYPE, String[].class, int[].class}, Void.TYPE).isSupported) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.d(requestCode, permissions, grantResults, this);
        }
    }

    public void a1(int requestCode, List<String> list) {
    }

    public void Q(int requestCode, List<String> perms) {
        Object[] objArr = {new Integer(requestCode), perms};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, BaseResp.ERR_CONNECT_FAIL, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            String[] permsStr = (String[]) perms.toArray(new String[perms.size()]);
            Log.e("baseActivity", "onPermissionsDenied: " + EasyPermissions.j(this, perms) + "==" + EasyPermissions.a(this, permsStr) + ",requestCode=" + requestCode);
            if (!EasyPermissions.a(this, permsStr) && EasyPermissions.j(this, perms)) {
                com.leedarson.base.views.common.dialogs.a actionDialog = new com.leedarson.base.views.common.dialogs.a(this);
                String contentTxt = PubUtils.getString(this, R$string.permission_ask_again);
                if (this.p1.containsKey(Integer.valueOf(requestCode))) {
                    contentTxt = PubUtils.getString(this, this.p1.get(Integer.valueOf(requestCode)).intValue());
                }
                actionDialog.i(PubUtils.getString(this, R$string.permission_title_setting));
                actionDialog.d(PubUtils.getString(this, R$string.cancel));
                actionDialog.f(PubUtils.getString(this, R$string.ok));
                actionDialog.h(contentTxt);
                actionDialog.c(new a());
                actionDialog.show();
            }
        }
    }

    public class a implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.ERR_NODE_NOT_EXIST, new Class[0], Void.TYPE).isSupported) {
                try {
                    w.K(BaseFragmentActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        public void onCancel() {
        }
    }

    public void Z(com.leedarson.base.listener.a listener) {
        this.z = listener;
    }

    public boolean isBaseOnWidth() {
        return this.p0;
    }

    public float getSizeInDp() {
        return (float) this.a1;
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        boolean z2 = true;
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, BaseResp.ERR_WAIT_RESPONSE, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            StringBuilder sb = new StringBuilder();
            sb.append("  newConfig.orientation == ORIENTATION_PORTRAIT=");
            if (newConfig.orientation != 1) {
                z2 = false;
            }
            sb.append(z2);
            com.leedarson.base.logger.a.c("BaseFragmentActivity", sb.toString());
            super.onConfigurationChanged(newConfig);
        }
    }

    public void o0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, 407, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            if (webView != null) {
                try {
                    if (webView.getParent() != null) {
                        ((ViewGroup) webView.getParent()).removeView(webView);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void p0(WVJBWebView webView) {
        if (!PatchProxy.proxy(new Object[]{webView}, this, changeQuickRedirect, false, BaseResp.ERR_INVAILD_MODEL_ID, new Class[]{WVJBWebView.class}, Void.TYPE).isSupported) {
            Log.d("TAG", "base attachWebView: ");
            if (webView != null) {
                try {
                    o0(webView);
                    RelativeLayout webLayoutBox = (RelativeLayout) findViewById(R$id.rl_web_container);
                    webLayoutBox.removeAllViews();
                    webLayoutBox.addView(webView, new RelativeLayout.LayoutParams(-1, -1));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("BaseFragmentActivity", "AttachWebView    ===>" + e.toString());
                }
            }
        }
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onBackgroundFrontChange(BackAndFrontChangeEvent event) {
    }

    public void M(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, BaseResp.ERR_DISCONNECT_FAIL, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            this.f.b(disposable);
        }
    }

    public void V(Runnable runnable) {
        if (!PatchProxy.proxy(new Object[]{runnable}, this, changeQuickRedirect, false, 411, new Class[]{Runnable.class}, Void.TYPE).isSupported) {
            new Handler(Looper.getMainLooper()).post(runnable);
        }
    }

    public void W() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.ERR_DOWNLOAD_MESH_FAIL, new Class[0], Void.TYPE).isSupported) {
            X(3, R$string.rationale_location);
            X(NeedPermissionEvent.PER_GET_LOCATION_BLE, R$string.rationale_location_ble);
            X(NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, R$string.rationale_location_discover_dev);
            int i = R$string.rationale_ipc_storage;
            X(NeedPermissionEvent.PER_IPC_ALBUM_PERM, i);
            X(128, i);
            X(126, i);
            X(123, R$string.rationale_take_photo);
            X(NeedPermissionEvent.PER_IPC_SPEAK_PERM, R$string.get_success);
        }
    }

    public void X(int requestCode, int msgId) {
        Object[] objArr = {new Integer(requestCode), new Integer(msgId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, BaseResp.ERR_DOWNLOAD_IMPORT_FAIL, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.p1.put(Integer.valueOf(requestCode), Integer.valueOf(msgId));
        }
    }

    public boolean d0() {
        return true;
    }

    private void O() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.ERR_OP_FAIL, new Class[0], Void.TYPE).isSupported) {
            try {
                String spColor = SharePreferenceUtils.getPrefString(BaseApplication.b(), "themeColor", "#1F2429");
                com.leedarson.base.logger.a.c("configThemeColor", "themeColor=" + spColor);
                this.x = Color.parseColor(spColor);
            } catch (Exception ex) {
                this.x = Color.parseColor("#1F2429");
                ex.printStackTrace();
            }
        }
    }

    public void Y(TextView tvTitle, String title, String... pageTitle) {
        Class[] clsArr = {TextView.class, String.class, String[].class};
        if (!PatchProxy.proxy(new Object[]{tvTitle, title, pageTitle}, this, changeQuickRedirect, false, BaseResp.ERR_PARAM_ERROR, clsArr, Void.TYPE).isSupported) {
            if (title == null) {
                title = "";
            }
            if (tvTitle != null) {
                tvTitle.setText(title);
            }
            StringBuffer sb = new StringBuffer();
            if (pageTitle != null && pageTitle.length > 0) {
                for (int i = 0; i < pageTitle.length; i++) {
                    sb.append(pageTitle[i]);
                    if (i < pageTitle.length - 1) {
                        sb.append(" ");
                    }
                }
            }
            setTitle(sb.toString());
        }
    }

    public void B() {
        IpcService ipcService;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 416, new Class[0], Void.TYPE).isSupported) {
            if (N() && (ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class)) != null) {
                ipcService.showNoNetTipView(this, new b());
            }
        }
    }

    public class b implements INoNetSnapTipView {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onNeedMoreHelpClick() {
        }

        public void onDissmissClick() {
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, BaseResp.ERR_GROUP_NOT_EMPTY, new Class[0], Void.TYPE).isSupported) {
            IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipcService != null) {
                ipcService.dismissNoNetTipView();
            }
        }
    }

    public boolean N() {
        return false;
    }
}
