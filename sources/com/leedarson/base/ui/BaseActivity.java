package com.leedarson.base.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.c;
import com.leedarson.base.utils.n;
import com.leedarson.base.utils.s;
import com.leedarson.base.utils.w;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.common.dialogs.a;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.BleMeshService;
import com.leedarson.serviceinterface.INoNetSnapTipView;
import com.leedarson.serviceinterface.IpcService;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.leedarson.serviceinterface.prefs.SharePreferenceUtils;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import java.util.HashMap;
import java.util.List;
import me.jessyan.autosize.internal.CustomAdapt;
import org.jetbrains.annotations.NotNull;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.a;

public abstract class BaseActivity extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks, CustomAdapt, n {
    public static ChangeQuickRedirect changeQuickRedirect;
    private HashMap<Integer, Integer> d = new HashMap<>();
    private s f;
    /* access modifiers changed from: private */
    public com.leedarson.base.views.common.dialogs.a q;
    public com.leedarson.base.listener.a x;
    public boolean y = true;
    public int z = 375;

    public abstract int O();

    public abstract void R();

    public abstract void init();

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 338, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            a.b g = timber.log.a.g("pageRouter");
            g.m("【页面跟踪】进入页面--->" + getClass().getSimpleName(), new Object[0]);
            getWindow().clearFlags(1024);
            this.f = new s();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onCreate(savedInstanceState);
            }
            setContentView(O());
            R();
            init();
            c.h().a(this);
            T();
        }
    }

    public void onStart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 339, new Class[0], Void.TYPE).isSupported) {
            super.onStart();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onStart();
            }
        }
    }

    public void onRestart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 340, new Class[0], Void.TYPE).isSupported) {
            super.onRestart();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.a();
            }
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 341, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onResume();
            }
            PubUtils.setLanguage(this);
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 342, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onPause();
            }
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 343, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onStop();
            }
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 344, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            a.b g = timber.log.a.g("pageRouter");
            g.m("【页面跟踪】离开页面--->" + getClass().getSimpleName(), new Object[0]);
            c.h().d(this);
            com.leedarson.base.listener.a aVar = this.x;
            if (aVar != null) {
                aVar.onDestroy();
            }
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 345, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            super.onConfigurationChanged(newConfig);
            PubUtils.setLanguage(this);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), permissions, grantResults}, this, changeQuickRedirect, false, 346, new Class[]{Integer.TYPE, String[].class, int[].class}, Void.TYPE).isSupported) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.d(requestCode, permissions, grantResults, this);
        }
    }

    public void a1(int requestCode, List<String> perms) {
        Object[] objArr = {new Integer(requestCode), perms};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 347, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            this.f.b(requestCode, perms);
        }
    }

    public void Q(int requestCode, List<String> perms) {
        BleMeshService bleMeshService;
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), perms}, this, changeQuickRedirect, false, 348, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            this.f.a(requestCode, perms);
            if (!S(requestCode) && !EasyPermissions.a(this, (String[]) perms.toArray(new String[perms.size()])) && EasyPermissions.j(this, perms)) {
                com.leedarson.base.views.common.dialogs.a aVar = this.q;
                if (aVar == null || !aVar.isShowing()) {
                    String contentTxt = PubUtils.getString(this, R$string.permission_ask_again);
                    if (this.d.containsKey(Integer.valueOf(requestCode))) {
                        contentTxt = PubUtils.getString(this, this.d.get(Integer.valueOf(requestCode)).intValue());
                    }
                    com.leedarson.base.views.common.dialogs.a aVar2 = new com.leedarson.base.views.common.dialogs.a(this);
                    this.q = aVar2;
                    aVar2.i(PubUtils.getString(this, R$string.permission_title_setting));
                    this.q.d(PubUtils.getString(this, R$string.cancel));
                    this.q.f(PubUtils.getString(this, R$string.ok));
                    this.q.h(contentTxt);
                    this.q.c(new a());
                    this.q.show();
                    if (requestCode == 139 && (bleMeshService = (BleMeshService) com.alibaba.android.arouter.launcher.a.c().g(BleMeshService.class)) != null) {
                        bleMeshService.onPermissionRequestGranted("", false);
                    }
                }
            }
        }
    }

    public class a implements a.c {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void a() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 356, new Class[0], Void.TYPE).isSupported) {
                try {
                    w.K(BaseActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                com.leedarson.base.views.common.dialogs.a unused = BaseActivity.this.q = null;
            }
        }

        public void onCancel() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 357, new Class[0], Void.TYPE).isSupported) {
                com.leedarson.base.views.common.dialogs.a unused = BaseActivity.this.q = null;
            }
        }
    }

    private boolean S(int requestCode) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(requestCode)}, this, changeQuickRedirect, false, 349, new Class[]{Integer.TYPE}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (requestCode == 140) {
            SharePreferenceUtils.setPrefBoolean(this, "nearby_permission_denied", true);
            return true;
        }
        for (int r : new int[]{137}) {
            if (r == requestCode) {
                return true;
            }
        }
        return false;
    }

    public void W(com.leedarson.base.listener.a listener) {
        this.x = listener;
    }

    public boolean isBaseOnWidth() {
        return this.y;
    }

    public float getSizeInDp() {
        return (float) this.z;
    }

    public void T() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 350, new Class[0], Void.TYPE).isSupported) {
            U(3, R$string.rationale_location);
            U(NeedPermissionEvent.PER_GET_LOCATION_BLE, R$string.rationale_location_ble);
            U(NeedPermissionEvent.PER_GET_LOCATION_DISCOVER_DEV, R$string.rationale_location_discover_dev);
            int i = R$string.rationale_ipc_storage;
            U(NeedPermissionEvent.PER_IPC_ALBUM_PERM, i);
            U(128, i);
            U(126, i);
            U(123, R$string.rationale_take_photo);
            U(NeedPermissionEvent.PER_IPC_SPEAK_PERM, R$string.get_success);
        }
    }

    public void U(int requestCode, int msgId) {
        Object[] objArr = {new Integer(requestCode), new Integer(msgId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 351, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.d.put(Integer.valueOf(requestCode), Integer.valueOf(msgId));
        }
    }

    public void V(LDSTextView tvTitle, String title, String... pageTitle) {
        Class[] clsArr = {LDSTextView.class, String.class, String[].class};
        if (!PatchProxy.proxy(new Object[]{tvTitle, title, pageTitle}, this, changeQuickRedirect, false, 352, clsArr, Void.TYPE).isSupported) {
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 353, new Class[0], Void.TYPE).isSupported) {
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
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 354, new Class[0], Void.TYPE).isSupported) {
            IpcService ipcService = (IpcService) com.alibaba.android.arouter.launcher.a.c().g(IpcService.class);
            if (ipcService != null) {
                ipcService.dismissNoNetTipView();
            }
        }
    }

    public boolean N() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 355, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return SharePreferenceUtils.getPrefBoolean(BaseApplication.b(), "lsd_enable_tip_in_webpage_flag", false);
    }
}
