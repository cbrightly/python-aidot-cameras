package com.yanzhenjie.andserver.framework.handler;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.error.ContentNotAcceptableException;
import com.yanzhenjie.andserver.error.ContentNotSupportedException;
import com.yanzhenjie.andserver.error.HeaderValidateException;
import com.yanzhenjie.andserver.error.MethodNotSupportException;
import com.yanzhenjie.andserver.error.ParamValidateException;
import com.yanzhenjie.andserver.framework.mapping.Mapping;
import com.yanzhenjie.andserver.framework.mapping.d;
import com.yanzhenjie.andserver.framework.mapping.e;
import com.yanzhenjie.andserver.framework.mapping.f;
import com.yanzhenjie.andserver.http.HttpMethod;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.util.MediaType;
import com.yanzhenjie.andserver.util.h;
import com.yanzhenjie.andserver.util.k;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: MappingAdapter */
public abstract class b implements a, k {
    /* access modifiers changed from: protected */
    @NonNull
    public abstract Map<com.yanzhenjie.andserver.framework.mapping.b, f> g();

    public boolean a(@NonNull c request) {
        List<f.b> d = f.d(request.getPath());
        List<com.yanzhenjie.andserver.framework.mapping.b> f = f(d);
        if (f.isEmpty()) {
            f = e(d);
        }
        if (f.isEmpty()) {
            return false;
        }
        com.yanzhenjie.andserver.http.b method = request.getMethod();
        if (method.equals(com.yanzhenjie.andserver.http.b.OPTIONS)) {
            return true;
        }
        com.yanzhenjie.andserver.framework.mapping.b mapping = b(f, method);
        if (mapping != null) {
            e param = mapping.d();
            if (param != null) {
                l(param, request);
            }
            e header = mapping.b();
            if (header != null) {
                k(header, request);
            }
            d consume = mapping.a();
            if (consume != null) {
                j(consume, request);
            }
            d produce = mapping.f();
            if (produce != null) {
                m(produce, request);
            }
            return true;
        }
        MethodNotSupportException exception = new MethodNotSupportException(method);
        exception.setMethods(c(f));
        throw exception;
    }

    @Nullable
    public f d(@NonNull c request) {
        List<f.b> d = f.d(request.getPath());
        List<com.yanzhenjie.andserver.framework.mapping.b> f = f(d);
        if (f.isEmpty()) {
            f = e(d);
        }
        com.yanzhenjie.andserver.http.b method = request.getMethod();
        com.yanzhenjie.andserver.framework.mapping.b mapping = b(f, method);
        if (method.equals(com.yanzhenjie.andserver.http.b.OPTIONS) && mapping == null) {
            return new e(request, f, g());
        }
        if (mapping == null) {
            return null;
        }
        d mime = mapping.f();
        if (mime != null) {
            h mediaType = null;
            Iterator<d.a> it = mime.a().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                h produce = (d.a) it.next();
                if (!produce.toString().startsWith("!")) {
                    mediaType = produce;
                    break;
                }
            }
            request.setAttribute("http.response.Produce", mediaType);
        }
        return g().get(mapping);
    }

    private List<com.yanzhenjie.andserver.framework.mapping.b> f(List<f.b> httpSegments) {
        List<Mapping> mappings = new ArrayList<>();
        for (com.yanzhenjie.andserver.framework.mapping.b mapping : g().keySet()) {
            for (f.a rule : mapping.e().b()) {
                if (i(rule.a(), httpSegments)) {
                    mappings.add(mapping);
                }
            }
        }
        return mappings;
    }

    private boolean i(List<f.b> segments, List<f.b> httpSegments) {
        if (httpSegments.size() == segments.size() && f.c(segments).equals(f.c(httpSegments))) {
            return true;
        }
        return false;
    }

    private List<com.yanzhenjie.andserver.framework.mapping.b> e(List<f.b> httpSegments) {
        List<Mapping> mappings = new ArrayList<>();
        for (com.yanzhenjie.andserver.framework.mapping.b mapping : g().keySet()) {
            for (f.a rule : mapping.e().b()) {
                if (h(rule.a(), httpSegments)) {
                    mappings.add(mapping);
                }
            }
        }
        return mappings;
    }

    private boolean h(List<f.b> segments, List<f.b> httpSegments) {
        if (httpSegments.size() != segments.size()) {
            return false;
        }
        for (int i = 0; i < segments.size(); i++) {
            f.b segment = segments.get(i);
            if (!segment.equals(httpSegments.get(i)) && !segment.b()) {
                return false;
            }
        }
        return true;
    }

    private void l(e param, c request) {
        for (e.a rule : param.a()) {
            String key = rule.a();
            List<String> keys = request.w();
            String value = rule.b();
            List<String> values = request.y(key);
            if (rule.c()) {
                if (keys.contains(key)) {
                    throw new ParamValidateException(String.format("The parameter [%s] is not allowed.", new Object[]{key}));
                }
            } else if (rule.d()) {
                if (values.contains(value)) {
                    throw new ParamValidateException(String.format("The value of parameter %s cannot be %s.", new Object[]{key, value}));
                }
            } else if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                if (!TextUtils.isEmpty(key) && TextUtils.isEmpty(value) && !keys.contains(key)) {
                    throw new ParamValidateException(String.format("The parameter %s is missing.", new Object[]{key}));
                }
            } else if (!keys.contains(key) || !values.contains(value)) {
                throw new ParamValidateException(String.format("The value of parameter %s is missing or wrong.", new Object[]{key}));
            }
        }
    }

    private void k(e header, c request) {
        for (e.a rule : header.a()) {
            String key = rule.a();
            List<String> keys = request.x();
            String value = rule.b();
            List<String> values = request.c(key);
            if (rule.c()) {
                if (keys.contains(key)) {
                    throw new HeaderValidateException(String.format("The header [%s] is not allowed.", new Object[]{key}));
                }
            } else if (rule.d()) {
                if (values.contains(value)) {
                    throw new HeaderValidateException(String.format("The value of header %s cannot be %s.", new Object[]{key, value}));
                }
            } else if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(value) && (!keys.contains(key) || !values.contains(value))) {
                throw new HeaderValidateException(String.format("The value of header %s is missing or wrong.", new Object[]{key}));
            } else if (!TextUtils.isEmpty(key) && TextUtils.isEmpty(value) && !keys.contains(key)) {
                throw new HeaderValidateException(String.format("The header %s is missing.", new Object[]{key}));
            }
        }
    }

    private void j(d mime, c request) {
        List<d.a> a = mime.a();
        h contentType = request.getContentType();
        List<MediaType> includeType = new ArrayList<>();
        for (d.a rule : a) {
            String type = rule.getType();
            boolean nonContent = type.startsWith("!");
            if (nonContent) {
                type = type.substring(1);
            }
            h consume = new h(type, rule.getSubtype());
            if (!nonContent) {
                includeType.add(consume);
            } else if (consume.equalsExcludeParameter(contentType)) {
                throw new ContentNotSupportedException(contentType);
            }
        }
        boolean included = false;
        Iterator<MediaType> it = includeType.iterator();
        while (true) {
            if (it.hasNext()) {
                if (((h) it.next()).includes(contentType)) {
                    included = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!included) {
            throw new ContentNotSupportedException(contentType);
        }
    }

    private void m(d mime, c request) {
        List<d.a> a = mime.a();
        List<h> v = request.v();
        for (d.a rule : a) {
            String type = rule.getType();
            boolean nonContent = type.startsWith("!");
            if (nonContent) {
                type = type.substring(1);
            }
            h produce = new h(type, rule.getSubtype());
            boolean exclude = false;
            for (h accept : v) {
                if (accept.includes(produce)) {
                    exclude = true;
                }
            }
            if (nonContent && exclude) {
                throw new ContentNotAcceptableException();
            } else if (!nonContent && !exclude) {
                throw new ContentNotAcceptableException();
            }
        }
    }

    public static List<com.yanzhenjie.andserver.http.b> c(List<com.yanzhenjie.andserver.framework.mapping.b> mappings) {
        List<HttpMethod> methods = new ArrayList<>();
        for (com.yanzhenjie.andserver.framework.mapping.b child : mappings) {
            methods.addAll(child.c().b());
        }
        return methods;
    }

    public static com.yanzhenjie.andserver.framework.mapping.b b(List<com.yanzhenjie.andserver.framework.mapping.b> mappings, com.yanzhenjie.andserver.http.b method) {
        for (com.yanzhenjie.andserver.framework.mapping.b child : mappings) {
            if (child.c().b().contains(method)) {
                return child;
            }
        }
        return null;
    }
}
