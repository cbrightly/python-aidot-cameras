package com.android.volley;

public class ClientError extends ServerError {
    public ClientError(h networkResponse) {
        super(networkResponse);
    }

    public ClientError() {
    }
}
