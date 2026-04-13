package com.leedarson.serviceinterface.listener;

public interface OnRequestListener {
    void onRequestFail(int i, String str);

    void onRequestResult(Object obj);
}
