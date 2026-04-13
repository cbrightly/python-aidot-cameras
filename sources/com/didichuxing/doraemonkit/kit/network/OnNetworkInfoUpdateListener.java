package com.didichuxing.doraemonkit.kit.network;

import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;

public interface OnNetworkInfoUpdateListener {
    void onNetworkInfoUpdate(NetworkRecord networkRecord, boolean z);
}
