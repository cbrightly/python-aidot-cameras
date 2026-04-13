package com.didichuxing.doraemonkit.kit.network.common;

import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import com.didichuxing.doraemonkit.util.LogHelper;

public class NetworkPrinterHelper {
    private static final String TAG = "NetworkLogHelper";
    private final NetworkInterpreter mInterpreter;

    private NetworkPrinterHelper() {
        this.mInterpreter = NetworkInterpreter.get();
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static NetworkPrinterHelper INSTANCE = new NetworkPrinterHelper();

        private Holder() {
        }
    }

    private static NetworkPrinterHelper get() {
        return Holder.INSTANCE;
    }

    public static int obtainRequestId() {
        return get().mInterpreter.nextRequestId();
    }

    public static void updateRequest(@NonNull CommonInspectorRequest request) {
        get().mInterpreter.createRecord(request.id(), request);
    }

    public static void updateResponse(@NonNull CommonInspectorResponse response) {
        NetworkRecord networkRecord = NetworkManager.get().getRecord(response.requestId());
        if (networkRecord != null) {
            get().mInterpreter.fetchResponseInfo(networkRecord, response);
            return;
        }
        LogHelper.e(TAG, "updateResponse fail ,record is null for requestId: " + response.requestId());
    }

    public static void updateResponseBody(int requestId, String body) {
        NetworkRecord networkRecord = NetworkManager.get().getRecord(requestId);
        if (networkRecord != null) {
            get().mInterpreter.fetchResponseBody(networkRecord, body);
            return;
        }
        LogHelper.e(TAG, "updateResponseBody fail ,record is null for requestId: " + requestId);
    }
}
