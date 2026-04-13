package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import com.leedarson.base.utils.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.trello.rxlifecycle3.android.a;
import com.trello.rxlifecycle3.b;
import com.yanzhenjie.andserver.util.h;
import io.reactivex.functions.e;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.c0;
import okhttp3.x;
import okhttp3.y;

/* compiled from: FileUploadRequest */
public class c {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public b<a> b;
    public List<String> c = new ArrayList();
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;

    public c(b<a> lifecycle) {
        this.b = lifecycle;
    }

    public void a(e<String> next, e<Throwable> error) {
        Class<e> cls = e.class;
        Class[] clsArr = {cls, cls};
        if (!PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect, false, 8011, clsArr, Void.TYPE).isSupported) {
            List<MultipartBody.Part> parts = new ArrayList<>();
            Map<String, Object> headerMap = new HashMap<>();
            Map<String, Object> paramMap = new HashMap<>();
            if (!TextUtils.isEmpty(this.f)) {
                headerMap = m.c(this.f);
            }
            if (!TextUtils.isEmpty(this.g)) {
                paramMap = m.c(this.g);
            }
            for (int i = 0; i < this.c.size(); i++) {
                File file = new File(this.c.get(i));
                parts.add(y.c.b("file", com.leedarson.serviceimpl.http.b.a(file.getName()), c0.create(x.h(h.MULTIPART_FORM_DATA_VALUE), file)));
            }
            com.leedarson.base.http.observer.h.a().c(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).b(this.d, headerMap, paramMap, parts), this.b).Y(next, error);
        }
    }
}
