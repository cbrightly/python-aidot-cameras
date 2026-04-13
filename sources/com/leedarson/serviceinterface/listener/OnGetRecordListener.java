package com.leedarson.serviceinterface.listener;

import java.util.List;

public interface OnGetRecordListener {
    void onException(Throwable th);

    void onFailure(int i, String str);

    void onGetCacheTimestamp(long j);

    void onSuccess(int i, List<byte[]> list);
}
