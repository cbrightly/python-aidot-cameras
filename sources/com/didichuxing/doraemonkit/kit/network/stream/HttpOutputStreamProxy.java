package com.didichuxing.doraemonkit.kit.network.stream;

import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import com.didichuxing.doraemonkit.kit.network.core.RequestBodyHelper;
import java.io.OutputStream;

public class HttpOutputStreamProxy extends OutputStreamProxy {
    private final NetworkInterpreter mInterpreter;
    private final int mRequestId;

    public HttpOutputStreamProxy(OutputStream out, int requestId, NetworkInterpreter interpreter) {
        super(out);
        this.mRequestId = requestId;
        this.mInterpreter = interpreter;
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void onStreamComplete() {
        NetworkRecord record = NetworkManager.get().getRecord(this.mRequestId);
        if (record != null && record.mRequest != null) {
            RequestBodyHelper requestBodyHelper = new RequestBodyHelper();
            try {
                this.mOutputStream.writeTo(requestBodyHelper.createBodySink(record.mRequest.encode));
                this.out.close();
                this.mInterpreter.fetRequestBody(record, requestBodyHelper.getDisplayBody());
            } catch (Throwable th) {
                this.out.close();
                throw th;
            }
        }
    }
}
