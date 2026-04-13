package com.didichuxing.doraemonkit.kit.network.core;

import android.text.TextUtils;
import androidx.annotation.Nullable;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.bean.Request;
import com.didichuxing.doraemonkit.kit.network.bean.Response;
import com.didichuxing.doraemonkit.kit.network.stream.InputStreamProxy;
import com.didichuxing.doraemonkit.kit.network.utils.Utf8Charset;
import com.didichuxing.doraemonkit.okgo.model.HttpHeaders;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.yanzhenjie.andserver.util.h;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class NetworkInterpreter {
    public static final String TAG = "NetworkInterpreter";
    private final AtomicInteger mNextRequestId = new AtomicInteger(0);
    private ResourceTypeHelper mResourceTypeHelper;

    public interface InspectorHeaders {
        @Nullable
        String firstHeaderValue(String str);

        int headerCount();

        String headerName(int i);

        String headerValue(int i);
    }

    public interface InspectorRequest extends InspectorRequestCommon {
        @Nullable
        byte[] body();

        String method();

        String url();
    }

    public interface InspectorRequestCommon extends InspectorHeaders {
        int id();
    }

    public interface InspectorResponse extends InspectorResponseCommon {
        String url();
    }

    public interface InspectorResponseCommon extends InspectorHeaders {
        int requestId();

        int statusCode();
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static NetworkInterpreter INSTANCE = new NetworkInterpreter();

        private Holder() {
        }
    }

    public static NetworkInterpreter get() {
        return Holder.INSTANCE;
    }

    public void httpExchangeFailed(int requestId, String s) {
    }

    public void responseReadFinished(int requestId, NetworkRecord record, ByteArrayOutputStream outputStream) {
        if (outputStream != null) {
            record.responseLength = (long) outputStream.size();
            record.mResponseBody = outputStream.toString();
        }
    }

    public void responseReadFailed(int requestId, String s) {
        LogHelper.i(TAG, "[responseReadFailed] requestId: " + requestId + " error: " + s);
    }

    public int nextRequestId() {
        return this.mNextRequestId.getAndIncrement();
    }

    public InputStream interpretResponseStream(String contentType, @Nullable InputStream availableInputStream, ResponseHandler responseHandler) {
        if (availableInputStream == null) {
            responseHandler.onEOF((ByteArrayOutputStream) null);
            return null;
        }
        ResourceType resourceType = contentType != null ? getResourceTypeHelper().determineResourceType(contentType) : null;
        if (resourceType == ResourceType.DOCUMENT || resourceType == ResourceType.XHR) {
            return new InputStreamProxy(availableInputStream, responseHandler);
        }
        responseHandler.onEOF((ByteArrayOutputStream) null);
        return availableInputStream;
    }

    public NetworkRecord createRecord(int requestId, InspectorRequest request) {
        NetworkRecord record = new NetworkRecord();
        record.mRequestId = requestId;
        fetchRequestInfo(record, request);
        NetworkManager.get().addRecord(requestId, record);
        return record;
    }

    private void fetchRequestInfo(NetworkRecord record, InspectorRequest request) {
        Request requestJSON = new Request();
        requestJSON.url = request.url();
        requestJSON.method = request.method();
        requestJSON.headers = formatHeadersAsString(request);
        requestJSON.encode = request.firstHeaderValue(HttpHeaders.HEAD_KEY_CONTENT_ENCODING);
        requestJSON.postData = readBodyAsString(request);
        record.mRequest = requestJSON;
        record.startTime = System.currentTimeMillis();
        record.requestLength = readBodyLength(request);
    }

    public void fetRequestBody(NetworkRecord record, byte[] request) {
        Request request2 = record.mRequest;
        if (request2 != null) {
            request2.postData = readBodyAsString(request);
            record.requestLength = readBodyLength(request);
            NetworkManager.get().updateRecord(record, false);
        }
    }

    public void fetchResponseBody(NetworkRecord record, String body) {
        if (TextUtils.isEmpty(body)) {
            record.responseLength = 0;
            record.mResponseBody = null;
            return;
        }
        record.responseLength = (long) body.getBytes().length;
        record.mResponseBody = body;
    }

    public void fetchResponseInfo(NetworkRecord record, InspectorResponse response) {
        Response responseJSON = new Response();
        responseJSON.url = response.url();
        responseJSON.status = response.statusCode();
        responseJSON.headers = formatHeadersAsString(response);
        String contentType = getContentType(response);
        responseJSON.mimeType = contentType != null ? getResourceTypeHelper().stripContentExtras(contentType) : h.APPLICATION_OCTET_STREAM_VALUE;
        record.mResponse = responseJSON;
        record.endTime = System.currentTimeMillis();
        NetworkManager.get().updateRecord(record, false);
    }

    private ResourceTypeHelper getResourceTypeHelper() {
        if (this.mResourceTypeHelper == null) {
            this.mResourceTypeHelper = new ResourceTypeHelper();
        }
        return this.mResourceTypeHelper;
    }

    private String formatHeadersAsString(InspectorHeaders headers) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < headers.headerCount(); i++) {
            String name = headers.headerName(i);
            String value = headers.headerValue(i);
            builder.append(name + ":" + value);
            if (i != headers.headerCount() - 1) {
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    private String readBodyAsString(InspectorRequest request) {
        try {
            byte[] body = request.body();
            if (body != null) {
                return new String(body, Utf8Charset.INSTANCE);
            }
            return null;
        } catch (IOException | OutOfMemoryError e) {
            return null;
        }
    }

    private String readBodyAsString(byte[] body) {
        if (body == null) {
            return null;
        }
        try {
            return new String(body, Utf8Charset.INSTANCE);
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    private long readBodyLength(InspectorRequest request) {
        try {
            byte[] body = request.body();
            if (body != null) {
                return (long) body.length;
            }
            return 0;
        } catch (IOException | OutOfMemoryError e) {
            return 0;
        }
    }

    private long readBodyLength(byte[] body) {
        if (body == null) {
            return 0;
        }
        try {
            return (long) body.length;
        } catch (OutOfMemoryError e) {
            return 0;
        }
    }

    private String getContentType(InspectorHeaders headers) {
        return headers.firstHeaderValue("Content-Type");
    }
}
