package com.leedarson.serviceimpl.blec075.onekeyreset;

import android.content.Context;
import com.leedarson.log.tracker.BaseStepBean;
import com.leedarson.log.tracker.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.HashMap;
import org.json.JSONException;

/* compiled from: OneKeyReporter */
public class g extends a<BaseStepBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String i;
    private String j;

    public g(String tag, Context context, String mac) {
        super(context);
        this.i = mac;
        this.j = tag;
    }

    public HashMap<String, Object> f() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 6528, new Class[0], HashMap.class);
        if (proxy.isSupported) {
            return (HashMap) proxy.result;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("mac", this.i);
        return map;
    }

    public void o(HashMap<String, Object> firstFields, BaseStepBean bean) {
        Class[] clsArr = {HashMap.class, BaseStepBean.class};
        if (!PatchProxy.proxy(new Object[]{firstFields, bean}, this, changeQuickRedirect, false, 6529, clsArr, Void.TYPE).isSupported) {
            super.o(firstFields, bean);
            firstFields.put("code", Integer.valueOf(bean.getCode()));
        }
    }

    public void e(BaseStepBean bean) {
        if (!PatchProxy.proxy(new Object[]{bean}, this, changeQuickRedirect, false, 6530, new Class[]{BaseStepBean.class}, Void.TYPE).isSupported) {
            try {
                timber.log.a.g(this.j).a(bean.toJson().toString(), new Object[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.e(bean);
        }
    }
}
