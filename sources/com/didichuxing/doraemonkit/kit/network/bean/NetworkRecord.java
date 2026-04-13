package com.didichuxing.doraemonkit.kit.network.bean;

import java.io.Serializable;

public class NetworkRecord implements Serializable {
    private static final String METHOD_GET = "get";
    private static final String METHOD_POST = "post";
    public long endTime;
    public Request mRequest;
    public int mRequestId;
    public Response mResponse;
    public String mResponseBody;
    public long requestLength;
    public long responseLength;
    public long startTime;

    public boolean filter(String text) {
        Request request = this.mRequest;
        if (request == null || !request.filter(text)) {
            return false;
        }
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r0.method;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isGetRecord() {
        /*
            r2 = this;
            com.didichuxing.doraemonkit.kit.network.bean.Request r0 = r2.mRequest
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r0.method
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r1 = "get"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord.isGetRecord():boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r0.method;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isPostRecord() {
        /*
            r2 = this;
            com.didichuxing.doraemonkit.kit.network.bean.Request r0 = r2.mRequest
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r0.method
            if (r0 == 0) goto L_0x0016
            java.lang.String r0 = r0.toLowerCase()
            java.lang.String r1 = "post"
            boolean r0 = android.text.TextUtils.equals(r1, r0)
            if (r0 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0017
        L_0x0016:
            r0 = 0
        L_0x0017:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord.isPostRecord():boolean");
    }
}
