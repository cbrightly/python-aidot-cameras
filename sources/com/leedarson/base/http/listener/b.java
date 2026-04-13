package com.leedarson.base.http.listener;

/* compiled from: DownloadListener */
public interface b {
    void onFailure(String str);

    void onFinish(String str);

    void onProgress(int i);

    void onStart();
}
