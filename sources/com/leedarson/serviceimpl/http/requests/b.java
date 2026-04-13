package com.leedarson.serviceimpl.http.requests;

import android.text.TextUtils;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.utils.m;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.trello.rxlifecycle3.android.a;
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

/* compiled from: FileUpload2Request */
public class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String a;
    public com.trello.rxlifecycle3.b<a> b;
    public List<String> c = new ArrayList();
    public String d = null;
    public String e = null;
    public String f = null;
    public String g = null;
    public String h = null;

    public b(com.trello.rxlifecycle3.b<a> lifecycle) {
        this.b = lifecycle;
    }

    public io.reactivex.disposables.b a(e<String> next, e<Throwable> error) {
        Class<e> cls = e.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{next, error}, this, changeQuickRedirect2, false, 8010, new Class[]{cls, cls}, io.reactivex.disposables.b.class);
        if (proxy.isSupported) {
            return (io.reactivex.disposables.b) proxy.result;
        }
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
            parts.add(y.c.b("files", com.leedarson.serviceimpl.http.b.a(file.getName()), c0.create(x.h(h.MULTIPART_FORM_DATA_VALUE), file)));
        }
        return com.leedarson.base.http.observer.h.a().e(((com.leedarson.base.http.api.a) com.leedarson.base.http.b.b().a(com.leedarson.base.http.api.a.class)).b(this.d, headerMap, paramMap, parts), this.b, l.a).Y(next, error);
    }
}
