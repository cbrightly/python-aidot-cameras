package com.leedarson.newui.door_phone.repos;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import io.reactivex.processors.b;

/* compiled from: DoorPhoneRepos */
public class f extends g {
    public static final Integer b = 1;
    public static final Integer c = 2;
    public static ChangeQuickRedirect changeQuickRedirect;
    public static final Integer d = 3;
    private Integer e = b;
    public b<Boolean> f = b.Y();
    public b<Boolean> g = b.Y();
    private boolean h = true;

    public void f(Integer pageState) {
        this.e = pageState;
    }

    public Integer c() {
        return this.e;
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4158, new Class[0], Void.TYPE).isSupported) {
            Integer num = this.e;
            if (num == b) {
                this.f.onNext(true);
                return;
            }
            Integer num2 = c;
            if (num == num2) {
                this.g.onNext(true);
                this.e = d;
            } else if (num == d) {
                this.g.onNext(false);
                this.e = num2;
            }
        }
    }

    public void e() {
        Integer num = this.e;
        if (num != b && num == c) {
        }
    }
}
