package com.google.android.libraries.places.internal;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.h;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.net.PlacesStatusCodes;

/* compiled from: com.google.android.libraries.places:places@@3.1.0 */
public final class zzdh {
    static ApiException zza(VolleyError volleyError) {
        int i = 8;
        if (volleyError instanceof NetworkError) {
            i = 7;
        } else if (volleyError instanceof TimeoutError) {
            i = 15;
        } else if (!(volleyError instanceof ServerError) && !(volleyError instanceof ParseError)) {
            i = volleyError instanceof AuthFailureError ? PlacesStatusCodes.REQUEST_DENIED : 13;
        }
        h hVar = volleyError.networkResponse;
        return new ApiException(new Status(i, String.format("Unexpected server error (HTTP Code: %s. Message: %s.)", new Object[]{hVar == null ? "N/A" : String.valueOf(hVar.a), volleyError})));
    }
}
