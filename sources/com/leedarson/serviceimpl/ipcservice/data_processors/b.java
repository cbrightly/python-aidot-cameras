package com.leedarson.serviceimpl.ipcservice.data_processors;

import android.app.Activity;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;

/* compiled from: DataProcessDispatcher */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    public ArrayList<g> a;

    public b() {
        ArrayList<g> arrayList = new ArrayList<>();
        this.a = arrayList;
        arrayList.add(new e());
        this.a.add(new d());
        this.a.add(new c());
        this.a.add(new a());
        this.a.add(new f());
    }

    public void a(Activity activity, String str, String str2, String str3, String str4) {
        Class<String> cls = String.class;
        if (!PatchProxy.proxy(new Object[]{activity, str, str2, str3, str4}, this, changeQuickRedirect, false, 8087, new Class[]{Activity.class, cls, cls, cls, cls}, Void.TYPE).isSupported) {
            String callbackKey = str;
            String action = str3;
            Activity activity2 = activity;
            String service = str2;
            String data = str4;
            int i = 0;
            while (i < this.a.size() && !this.a.get(i).a(activity2, callbackKey, service, action, data)) {
                i++;
            }
        }
    }
}
