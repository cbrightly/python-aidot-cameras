package com.didichuxing.doraemonkit.okgo.model;

import org.glassfish.grizzly.http.server.Constants;

public enum HttpMethod {
    GET(Constants.GET),
    POST(Constants.POST),
    PUT("PUT"),
    DELETE("DELETE"),
    HEAD(Constants.HEAD),
    PATCH("PATCH"),
    OPTIONS("OPTIONS"),
    TRACE("TRACE");
    
    private final String value;

    private HttpMethod(String value2) {
        this.value = value2;
    }

    public String toString() {
        return this.value;
    }

    /* renamed from: com.didichuxing.doraemonkit.okgo.model.HttpMethod$1  reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod = null;

        static {
            int[] iArr = new int[HttpMethod.values().length];
            $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod = iArr;
            try {
                iArr[HttpMethod.POST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod[HttpMethod.PUT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod[HttpMethod.PATCH.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod[HttpMethod.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod[HttpMethod.OPTIONS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public boolean hasBody() {
        switch (AnonymousClass1.$SwitchMap$com$didichuxing$doraemonkit$okgo$model$HttpMethod[ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                return true;
            default:
                return false;
        }
    }
}
