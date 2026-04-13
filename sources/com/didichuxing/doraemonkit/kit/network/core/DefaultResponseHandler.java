package com.didichuxing.doraemonkit.kit.network.core;

import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DefaultResponseHandler implements ResponseHandler {
    private final NetworkInterpreter mNetworkInterpreter;
    private NetworkRecord mRecord;
    private final int mRequestId;

    public DefaultResponseHandler(NetworkInterpreter networkInterpreter, int requestId, NetworkRecord record) {
        this.mNetworkInterpreter = networkInterpreter;
        this.mRequestId = requestId;
        this.mRecord = record;
    }

    public void onEOF(ByteArrayOutputStream outputStream) {
        this.mNetworkInterpreter.responseReadFinished(this.mRequestId, this.mRecord, outputStream);
    }

    public void onError(IOException e) {
        this.mNetworkInterpreter.responseReadFailed(this.mRequestId, e.toString());
    }
}
