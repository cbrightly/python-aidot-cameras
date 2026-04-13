package com.leedarson.serviceinterface;

public interface HttpResponseListener {
    void onError(Exception exc);

    void onResponse(String str);
}
