package com.leedarson.log.reporter;

import android.content.Context;
import com.leedarson.log.elk.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: MultiReporter */
public class e implements d {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Context a;
    private int b;
    private ArrayList<d> c;

    public e(int typeFlag, Context context) {
        this.b = typeFlag;
        this.a = context;
        ArrayList<d> arrayList = new ArrayList<>();
        this.c = arrayList;
        if ((typeFlag & 1) != 0) {
            arrayList.add(new b(b.b().a(), b.b().c()));
        }
        if ((typeFlag & 8) != 0) {
            this.c.add(new c(b.b().a()));
        }
        if ((typeFlag & 2) != 0) {
            this.c.add(new f());
        }
        if ((typeFlag & 4) != 0) {
            this.c.add(new a());
        }
    }

    public void b() {
        ArrayList<d> arrayList;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 1338, new Class[0], Void.TYPE).isSupported && (arrayList = this.c) != null) {
            Iterator<d> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().b();
            }
        }
    }

    public void a(String content) {
        ArrayList<d> arrayList;
        if (!PatchProxy.proxy(new Object[]{content}, this, changeQuickRedirect, false, 1339, new Class[]{String.class}, Void.TYPE).isSupported && (arrayList = this.c) != null) {
            Iterator<d> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().a(content);
            }
        }
    }
}
