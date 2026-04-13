package com.android.volley.toolbox;

import androidx.annotation.Nullable;
import com.android.volley.ParseError;
import com.android.volley.h;
import com.android.volley.k;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: JsonObjectRequest */
public class m extends n<JSONObject> {
    public m(String url, k.b<JSONObject> listener, @Nullable k.a errorListener) {
        super(0, url, (String) null, listener, errorListener);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    @Deprecated
    public m(String url, @Nullable JSONObject jsonRequest, k.b<JSONObject> listener, @Nullable k.a errorListener) {
        super(jsonRequest == null ? 0 : 1, url, jsonRequest != null ? jsonRequest.toString() : null, listener, errorListener);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public m(int method, String url, @Nullable JSONObject jsonRequest, k.b<JSONObject> listener, @Nullable k.a errorListener) {
        super(method, url, jsonRequest != null ? jsonRequest.toString() : null, listener, errorListener);
    }

    /* access modifiers changed from: protected */
    public k<JSONObject> parseNetworkResponse(h response) {
        try {
            return k.c(new JSONObject(new String(response.b, g.f(response.c, "utf-8"))), g.e(response));
        } catch (UnsupportedEncodingException e) {
            return k.a(new ParseError((Throwable) e));
        } catch (JSONException je) {
            return k.a(new ParseError((Throwable) je));
        }
    }
}
