package com.android.volley;

public class NetworkError extends VolleyError {
    public NetworkError() {
    }

    public NetworkError(Throwable cause) {
        super(cause);
    }

    public NetworkError(h networkResponse) {
        super(networkResponse);
    }
}
