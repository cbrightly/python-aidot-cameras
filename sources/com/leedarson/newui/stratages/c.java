package com.leedarson.newui.stratages;

import android.view.View;
import com.leedarson.base.http.observer.l;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.disposables.b;
import io.reactivex.e;
import java.util.concurrent.TimeUnit;

/* compiled from: AutoSwitchResolutionStratage */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    private b a;
    private int b = 5;
    View.OnClickListener c;
    View.OnClickListener d;

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4843, new Class[0], Void.TYPE).isSupported) {
            a("等待画面数据流来临....");
            f();
            this.a = e.w(1).g((long) this.b, TimeUnit.SECONDS).c(l.c()).I(new a(this), b.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public /* synthetic */ void c(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 4846, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            a("画面在 " + this.b + " S 还未出画面 - 尝试提示用户是否需要切换清晰至Auto模式");
            View.OnClickListener onClickListener = this.c;
            if (onClickListener != null) {
                onClickListener.onClick((View) null);
            }
        }
    }

    static /* synthetic */ void d(Throwable throwable) {
    }

    public void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4844, new Class[0], Void.TYPE).isSupported) {
            a("画面已收到 - 关闭等待倒计时 ");
            f();
        }
    }

    public void f() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4845, new Class[0], Void.TYPE).isSupported) {
            a("释放倒计时监听器 - 关闭清晰度切换弹窗 ");
            b bVar = this.a;
            if (bVar != null && !bVar.isDisposed()) {
                this.a.dispose();
            }
            View.OnClickListener onClickListener = this.d;
            if (onClickListener != null) {
                onClickListener.onClick((View) null);
            }
        }
    }

    public void setOnShowDialogHandler(View.OnClickListener showHandler) {
        this.c = showHandler;
    }

    public void setOnHideDialogHandler(View.OnClickListener hideDialogHandler) {
        this.d = hideDialogHandler;
    }

    private void a(String message) {
    }
}
