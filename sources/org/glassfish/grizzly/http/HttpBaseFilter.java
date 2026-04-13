package org.glassfish.grizzly.http;

import org.glassfish.grizzly.filterchain.BaseFilter;

public class HttpBaseFilter extends BaseFilter {
    /* access modifiers changed from: protected */
    public void bind(HttpRequestPacket request, HttpResponsePacket response) {
        request.setResponse(response);
        response.setRequest(request);
    }
}
