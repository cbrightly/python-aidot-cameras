package com.leedarson.newui.repoter;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.log.elk.a;
import com.leedarson.newui.repoter.beans.ELKStepRecordBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* compiled from: ELKEventIPCBuilder */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    public a a;
    public ArrayList<ELKStepRecordBean> b = new ArrayList<>();
    Gson c = new Gson();

    public void a(ELKStepRecordBean stepRecordBean) {
        if (!PatchProxy.proxy(new Object[]{stepRecordBean}, this, changeQuickRedirect, false, 4485, new Class[]{ELKStepRecordBean.class}, Void.TYPE).isSupported) {
            if (this.b.size() == 0) {
                this.a.u("create", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
                this.a.u("time", Long.valueOf(System.currentTimeMillis()));
            }
            this.b.add(stepRecordBean);
        }
    }

    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4486, new Class[0], Void.TYPE).isSupported) {
            this.b.clear();
        }
    }

    public void b(String description) {
        if (!PatchProxy.proxy(new Object[]{description}, this, changeQuickRedirect, false, 4487, new Class[]{String.class}, Void.TYPE).isSupported) {
            int code = 200;
            long durationTotal = 0;
            for (int i = 0; i < this.b.size(); i++) {
                if (i == this.b.size() - 1) {
                    ArrayList<ELKStepRecordBean> arrayList = this.b;
                    code = arrayList.get(arrayList.size() - 1).code;
                }
                durationTotal += this.b.get(i).duration;
            }
            this.a.u("code", Integer.valueOf(code)).u(TypedValues.TransitionType.S_DURATION, Long.valueOf(durationTotal)).u("description", description).p(this.c.toJson((Object) this.b)).a().b();
            this.b.clear();
        }
    }
}
