package com.leedarson.serviceimpl.reporters;

import android.text.TextUtils;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.google.gson.Gson;
import com.leedarson.log.elk.a;
import com.leedarson.serviceimpl.reporters.beans.MatterConfigStepBeans;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: MatterBaserReporter */
public abstract class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    public ArrayList<MatterConfigStepBeans> a = new ArrayList<>();
    public String b = "";
    public long c = System.currentTimeMillis();

    public abstract a a();

    public h(String traceId, String deviceInfo) {
        if (!TextUtils.isEmpty(traceId)) {
            this.b = traceId;
        } else {
            this.b = "" + System.currentTimeMillis();
        }
        this.c = System.currentTimeMillis();
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8552, new Class[0], Void.TYPE).isSupported) {
            a _build = a();
            _build.x(this.b);
            MatterConfigStepBeans _lastStepBean = null;
            if (this.a.size() != 0) {
                ArrayList<MatterConfigStepBeans> arrayList = this.a;
                _lastStepBean = arrayList.get(arrayList.size() - 1);
            }
            if (_lastStepBean != null) {
                _build.u(TypedValues.TransitionType.S_DURATION, Long.valueOf(_lastStepBean._endTraceTimeSpan - this.c));
                _build.u("code", Integer.valueOf(_lastStepBean.getCode()));
                _build.u("description", _lastStepBean.getDesc());
            }
            try {
                _build.r(new JSONArray(new Gson().toJson((Object) this.a)));
            } catch (JSONException exception) {
                exception.printStackTrace();
            }
            _build.a().b();
            this.a.clear();
        }
    }
}
