package com.yanzhenjie.andserver;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.error.NotFoundException;
import com.yanzhenjie.andserver.framework.b;
import com.yanzhenjie.andserver.framework.e;
import com.yanzhenjie.andserver.framework.view.d;
import com.yanzhenjie.andserver.http.h;
import com.yanzhenjie.andserver.http.k;
import com.yanzhenjie.andserver.http.l;
import com.yanzhenjie.andserver.http.multipart.g;
import com.yanzhenjie.andserver.http.session.f;
import com.yanzhenjie.andserver.register.b;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.apache.httpcore.m;
import org.apache.httpcore.p;
import org.apache.httpcore.protocol.j;

/* compiled from: DispatcherHandler */
public class c implements j, b {
    private final Context a;
    private com.yanzhenjie.andserver.http.session.c b;
    private e c;
    private d d;
    private com.yanzhenjie.andserver.framework.b e;
    private com.yanzhenjie.andserver.framework.config.b f;
    private List<com.yanzhenjie.andserver.framework.handler.a> g = new LinkedList();
    private List<com.yanzhenjie.andserver.framework.c> h = new LinkedList();

    public c(Context context) {
        this.a = context;
        this.b = new f(context);
        this.d = new d();
        this.e = new b.C0230b(com.yanzhenjie.andserver.framework.b.a);
        this.h.add(new com.yanzhenjie.andserver.framework.f());
    }

    public void d(@NonNull com.yanzhenjie.andserver.framework.handler.a adapter) {
        com.yanzhenjie.andserver.util.a.c(adapter, "The adapter cannot be null.");
        if (!this.g.contains(adapter)) {
            this.g.add(adapter);
        }
    }

    public void c(@NonNull com.yanzhenjie.andserver.framework.c interceptor) {
        com.yanzhenjie.andserver.util.a.c(interceptor, "The interceptor cannot be null.");
        if (!this.h.contains(interceptor)) {
            this.h.add(interceptor);
        }
    }

    public void e(@NonNull com.yanzhenjie.andserver.framework.b resolver) {
        com.yanzhenjie.andserver.util.a.c(resolver, "The exceptionResolver cannot be null.");
        this.e = new b.C0230b(resolver);
    }

    public void a(com.yanzhenjie.andserver.framework.config.b multipart) {
        this.f = multipart;
    }

    public void b(m req, p res, org.apache.httpcore.protocol.d con) {
        j(new k(req, new com.yanzhenjie.andserver.http.j(con), this, this.b), new l(res));
    }

    /* access modifiers changed from: private */
    public void j(com.yanzhenjie.andserver.http.c request, com.yanzhenjie.andserver.http.d response) {
        com.yanzhenjie.andserver.http.multipart.d multipartResolver = new g();
        try {
            if (multipartResolver.f(request)) {
                g(multipartResolver);
                request = multipartResolver.b(request);
            }
            com.yanzhenjie.andserver.framework.handler.a ha = h(request);
            if (ha != null) {
                com.yanzhenjie.andserver.framework.handler.f handler = ha.d(request);
                if (handler == null) {
                    throw new NotFoundException(request.getPath());
                } else if (!k(request, response, handler)) {
                    request.setAttribute("android.context", this.a);
                    request.setAttribute("http.message.converter", this.c);
                    this.d.b(handler.c(request, response), request, response);
                    l(request, response);
                    if (!(request instanceof com.yanzhenjie.andserver.http.multipart.c)) {
                        return;
                    }
                    multipartResolver.c((com.yanzhenjie.andserver.http.multipart.c) request);
                } else if (request instanceof com.yanzhenjie.andserver.http.multipart.c) {
                    multipartResolver.c((com.yanzhenjie.andserver.http.multipart.c) request);
                }
            } else {
                throw new NotFoundException(request.getPath());
            }
        } catch (Throwable err) {
            if (request instanceof com.yanzhenjie.andserver.http.multipart.c) {
                multipartResolver.c((com.yanzhenjie.andserver.http.multipart.c) request);
            }
            throw err;
        }
    }

    private void g(com.yanzhenjie.andserver.http.multipart.d multipartResolver) {
        com.yanzhenjie.andserver.framework.config.b bVar = this.f;
        if (bVar != null) {
            long allFileMaxSize = bVar.a();
            if (allFileMaxSize == -1 || allFileMaxSize > 0) {
                multipartResolver.a(allFileMaxSize);
            }
            long fileMaxSize = this.f.b();
            if (fileMaxSize == -1 || fileMaxSize > 0) {
                multipartResolver.d(fileMaxSize);
            }
            int maxInMemorySize = this.f.c();
            if (maxInMemorySize > 0) {
                multipartResolver.g(maxInMemorySize);
            }
            File uploadTempDir = this.f.d();
            if (uploadTempDir != null) {
                multipartResolver.e(uploadTempDir);
            }
        }
    }

    private com.yanzhenjie.andserver.framework.handler.a h(com.yanzhenjie.andserver.http.c request) {
        for (com.yanzhenjie.andserver.framework.handler.a ha : this.g) {
            if (ha.a(request)) {
                return ha;
            }
        }
        return null;
    }

    private boolean k(com.yanzhenjie.andserver.http.c request, com.yanzhenjie.andserver.http.d response, com.yanzhenjie.andserver.framework.handler.f handler) {
        for (com.yanzhenjie.andserver.framework.c interceptor : this.h) {
            if (interceptor.a(request, response, handler)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    public com.yanzhenjie.andserver.http.g i(com.yanzhenjie.andserver.http.c request, String path) {
        com.yanzhenjie.andserver.http.c copyRequest = request;
        while (copyRequest instanceof h) {
            copyRequest = ((h) request).d();
        }
        ((k) copyRequest).k(path);
        if (h(copyRequest) != null) {
            return new a();
        }
        throw new NotFoundException(request.getPath());
    }

    /* compiled from: DispatcherHandler */
    public class a implements com.yanzhenjie.andserver.http.g {
        a() {
        }

        public void a(@NonNull com.yanzhenjie.andserver.http.c request, @NonNull com.yanzhenjie.andserver.http.d response) {
            c.this.j(request, response);
        }
    }

    private void l(com.yanzhenjie.andserver.http.c request, com.yanzhenjie.andserver.http.d response) {
        Object objSession = request.getAttribute("http.request.Session");
        if (objSession instanceof com.yanzhenjie.andserver.http.session.b) {
            com.yanzhenjie.andserver.http.session.b session = (com.yanzhenjie.andserver.http.session.b) objSession;
            try {
                this.b.a(session);
            } catch (IOException e2) {
                Log.e("AndServer", "Session persistence failed.", e2);
            }
            com.yanzhenjie.andserver.http.cookie.a cookie = new com.yanzhenjie.andserver.http.cookie.a("ASESSIONID", session.getId());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.d(cookie);
        }
    }
}
