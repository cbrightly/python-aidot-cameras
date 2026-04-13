package com.android.volley;

public class ServerError extends VolleyError {
    public ServerError(h networkResponse) {
        super(networkResponse);
    }

    public ServerError() {
    }
}
