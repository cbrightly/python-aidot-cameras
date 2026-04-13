package com.yanzhenjie.andserver.framework.view;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.yanzhenjie.andserver.error.NotFoundException;
import com.yanzhenjie.andserver.error.ServerInternalException;
import com.yanzhenjie.andserver.framework.body.b;
import com.yanzhenjie.andserver.framework.e;
import com.yanzhenjie.andserver.http.c;
import com.yanzhenjie.andserver.http.g;
import com.yanzhenjie.andserver.http.i;
import com.yanzhenjie.andserver.util.h;
import com.yanzhenjie.andserver.util.k;

/* compiled from: ViewResolver */
public class d implements k {
    private e h;

    public void b(@Nullable c view, @NonNull c request, @NonNull com.yanzhenjie.andserver.http.d response) {
        if (view != null) {
            Object output = view.b();
            if (view.a()) {
                d(output, request, response);
            } else {
                c(output, request, response);
            }
        }
    }

    private void d(Object output, @NonNull c request, @NonNull com.yanzhenjie.andserver.http.d response) {
        if (output instanceof i) {
            response.b((i) output);
            return;
        }
        e eVar = this.h;
        if (eVar != null) {
            response.b(eVar.a(output, a(request)));
        } else if (output == null) {
            response.b(new b(""));
        } else if (output instanceof String) {
            response.b(new b(output.toString(), a(request)));
        } else {
            response.b(new b(output.toString()));
        }
    }

    @Nullable
    private h a(@NonNull c request) {
        Object mtAttribute = request.getAttribute("http.response.Produce");
        if (mtAttribute instanceof h) {
            return (h) mtAttribute;
        }
        return null;
    }

    private void c(Object output, @NonNull c request, @NonNull com.yanzhenjie.andserver.http.d response) {
        if (output instanceof CharSequence) {
            String action = output.toString();
            if (!TextUtils.isEmpty(action)) {
                if (action.matches("redirect:(.)*")) {
                    response.e(302);
                    if (action.length() >= 9) {
                        response.setHeader("Location", action.substring(9));
                    }
                } else if (action.matches("forward:(.)*")) {
                    String path = action.substring(8);
                    g dispatcher = request.u(path);
                    if (dispatcher != null) {
                        dispatcher.a(request, response);
                        return;
                    }
                    throw new NotFoundException(path);
                } else if (action.matches(k.c)) {
                    String path2 = action + ".html";
                    g dispatcher2 = request.u(path2);
                    if (dispatcher2 != null) {
                        dispatcher2.a(request, response);
                        return;
                    }
                    throw new NotFoundException(path2);
                } else {
                    throw new NotFoundException(action);
                }
            }
        } else {
            throw new ServerInternalException(String.format("The return value of [%s] is not supported", new Object[]{output}));
        }
    }
}
