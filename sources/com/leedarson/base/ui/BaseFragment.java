package com.leedarson.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.g;
import com.leedarson.module_base.R$id;
import com.leedarson.module_base.R$layout;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.components.support.RxFragment;
import java.util.List;
import me.jessyan.autosize.internal.CustomAdapt;
import org.jetbrains.annotations.NotNull;
import pub.devrel.easypermissions.EasyPermissions;

public abstract class BaseFragment extends RxFragment implements EasyPermissions.PermissionCallbacks, CustomAdapt {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public g d;
    public io.reactivex.disposables.a f = new io.reactivex.disposables.a();
    Handler p0 = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public Toast q;
    protected Activity x;
    public boolean y = true;
    public int z = 375;

    public abstract int r1();

    public abstract void t1(View view);

    public void o1(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 358, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            this.f.b(disposable);
        }
    }

    public void p1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 359, new Class[0], Void.TYPE).isSupported) {
            this.f.dispose();
        }
    }

    public void q1(io.reactivex.disposables.b disposable) {
        if (!PatchProxy.proxy(new Object[]{disposable}, this, changeQuickRedirect, false, 360, new Class[]{io.reactivex.disposables.b.class}, Void.TYPE).isSupported) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    public void onAttach(Activity activity) {
        if (!PatchProxy.proxy(new Object[]{activity}, this, changeQuickRedirect, false, 361, new Class[]{Activity.class}, Void.TYPE).isSupported) {
            super.onAttach(activity);
            this.x = activity;
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 362, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            this.x.getWindow().clearFlags(1024);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect, false, 363, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        return proxy.isSupported ? (View) proxy.result : inflater.inflate(r1(), container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Class[] clsArr = {View.class, Bundle.class};
        if (!PatchProxy.proxy(new Object[]{view, savedInstanceState}, this, changeQuickRedirect, false, 364, clsArr, Void.TYPE).isSupported) {
            super.onViewCreated(view, savedInstanceState);
            t1(view);
            this.d = new g(getContext());
        }
    }

    public void onStart() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 365, new Class[0], Void.TYPE).isSupported) {
            super.onStart();
        }
    }

    public void onResume() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 366, new Class[0], Void.TYPE).isSupported) {
            super.onResume();
            if (getActivity() != null) {
                PubUtils.setLanguage(getActivity());
            }
        }
    }

    public void onPause() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 367, new Class[0], Void.TYPE).isSupported) {
            super.onPause();
        }
    }

    public void onStop() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 368, new Class[0], Void.TYPE).isSupported) {
            super.onStop();
        }
    }

    public void onDestroyView() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 369, new Class[0], Void.TYPE).isSupported) {
            super.onDestroyView();
        }
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 370, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            p1();
            g gVar = this.d;
            if (gVar != null) {
                gVar.dismiss();
            }
        }
    }

    public void onDetach() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 371, new Class[0], Void.TYPE).isSupported) {
            super.onDetach();
        }
    }

    public void onConfigurationChanged(@NotNull @NonNull Configuration newConfig) {
        if (!PatchProxy.proxy(new Object[]{newConfig}, this, changeQuickRedirect, false, 372, new Class[]{Configuration.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                PubUtils.setLanguage(getActivity());
            }
            super.onConfigurationChanged(newConfig);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!PatchProxy.proxy(new Object[]{new Integer(requestCode), permissions, grantResults}, this, changeQuickRedirect, false, 373, new Class[]{Integer.TYPE, String[].class, int[].class}, Void.TYPE).isSupported) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            EasyPermissions.d(requestCode, permissions, grantResults, this);
        }
    }

    public void a1(int requestCode, List<String> list) {
    }

    public void Q(int i, List<String> perms) {
        Object[] objArr = {new Integer(i), perms};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 374, new Class[]{Integer.TYPE, List.class}, Void.TYPE).isSupported) {
            String[] strArr = (String[]) perms.toArray(new String[perms.size()]);
            getActivity();
        }
    }

    public boolean isBaseOnWidth() {
        return this.y;
    }

    public float getSizeInDp() {
        return (float) this.z;
    }

    public void D1(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 375, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(getClass().getSimpleName()).a(log, new Object[0]);
        }
    }

    public void E1(String log) {
        if (!PatchProxy.proxy(new Object[]{log}, this, changeQuickRedirect, false, 376, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(getClass().getSimpleName()).h(log, new Object[0]);
        }
    }

    public void H1(Runnable runnable) {
        if (!PatchProxy.proxy(new Object[]{runnable}, this, changeQuickRedirect, false, 377, new Class[]{Runnable.class}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                this.p0.post(runnable);
            }
        }
    }

    public void onAttach(@NonNull Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 378, new Class[]{Context.class}, Void.TYPE).isSupported) {
            super.onAttach(context);
            this.x = (Activity) context;
        }
    }

    public Activity s1() {
        return this.x;
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 388, new Class[0], Void.TYPE).isSupported) {
                BaseFragment.this.d.setCancelable(false);
                BaseFragment.this.d.setCanceledOnTouchOutside(false);
                BaseFragment.this.d.g();
            }
        }
    }

    public void B1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 379, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.d != null) {
                H1(new a());
            }
        }
    }

    public void C1(boolean flagCancel) {
        Object[] objArr = {new Byte(flagCancel ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 380, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null && this.d != null) {
                H1(new a(this, flagCancel));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: y1 */
    public /* synthetic */ void z1(boolean flagCancel) {
        if (!PatchProxy.proxy(new Object[]{new Byte(flagCancel ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 387, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.d.setCancelable(flagCancel);
            this.d.setCanceledOnTouchOutside(false);
            this.d.g();
        }
    }

    public void A1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 381, new Class[0], Void.TYPE).isSupported) {
            if (getActivity() != null && this.d != null) {
                H1(new b(this));
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: w1 */
    public /* synthetic */ void x1() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 386, new Class[0], Void.TYPE).isSupported) {
            this.d.e();
        }
    }

    public class b implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        b(int i) {
            this.c = i;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 389, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (BaseFragment.this.s1() != null) {
                        View toastRoot = LayoutInflater.from(BaseFragment.this.s1()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                        if (BaseFragment.this.q != null) {
                            BaseFragment.this.q.cancel();
                        }
                        Toast unused = BaseFragment.this.q = new Toast(BaseFragment.this.s1());
                        BaseFragment.this.q.setGravity(17, 0, 0);
                        BaseFragment.this.q.setView(toastRoot);
                        ((LDSTextView) toastRoot.findViewById(R$id.toast_notice)).setText(PubUtils.getString(BaseApplication.b(), this.c));
                        BaseFragment.this.q.setDuration(1);
                        BaseFragment.this.q.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void F1(int resId) {
        Object[] objArr = {new Integer(resId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 382, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (getActivity() != null) {
                H1(new b(resId));
            }
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        c(String str) {
            this.c = str;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 390, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (BaseFragment.this.s1() != null) {
                        View toastRoot = LayoutInflater.from(BaseFragment.this.s1()).inflate(R$layout.layout_toast_image, (ViewGroup) null);
                        if (BaseFragment.this.q != null) {
                            BaseFragment.this.q.cancel();
                        }
                        Toast unused = BaseFragment.this.q = new Toast(BaseFragment.this.s1());
                        BaseFragment.this.q.setGravity(17, 0, 0);
                        BaseFragment.this.q.setView(toastRoot);
                        ((LDSTextView) toastRoot.findViewById(R$id.toast_notice)).setText(this.c);
                        BaseFragment.this.q.setDuration(1);
                        BaseFragment.this.q.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void G1(String content) {
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 383, new Class[]{String.class}, Void.TYPE).isSupported) {
            if (getActivity() != null && !TextUtils.isEmpty(content)) {
                H1(new c(content));
            }
        }
    }

    public boolean v1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 384, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean u1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 385, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (getActivity() == null || getActivity().isDestroyed() || getActivity().isFinishing() || !isAdded()) {
            return false;
        }
        return true;
    }
}
