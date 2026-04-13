package com.didichuxing.doraemonkit.kit.network.okhttp.interceptor;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.kit.network.bean.WhiteHostBean;
import com.didichuxing.doraemonkit.kit.network.core.DefaultResponseHandler;
import com.didichuxing.doraemonkit.kit.network.core.NetworkInterpreter;
import com.didichuxing.doraemonkit.kit.network.core.RequestBodyHelper;
import com.didichuxing.doraemonkit.kit.network.okhttp.ForwardingResponseBody;
import com.didichuxing.doraemonkit.kit.network.okhttp.InterceptorUtil;
import com.didichuxing.doraemonkit.kit.network.okhttp.OkHttpInspectorRequest;
import com.didichuxing.doraemonkit.kit.network.okhttp.OkHttpInspectorResponse;
import com.didichuxing.doraemonkit.kit.network.utils.OkHttpResponseKt;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.io.InputStream;
import java.util.List;
import meshsdk.BaseResp;
import okhttp3.a0;
import okhttp3.b0;
import okhttp3.d0;
import okhttp3.e0;
import okhttp3.w;
import okhttp3.x;

public class DoraemonInterceptor implements w {
    public static final String TAG = "DoraemonInterceptor";
    private final NetworkInterpreter mNetworkInterpreter = NetworkInterpreter.get();

    @NonNull
    public d0 intercept(@NonNull w.a chain) {
        w.a aVar = chain;
        if (!NetworkManager.isActive()) {
            b0 request = chain.g();
            try {
                return aVar.a(request);
            } catch (Exception e) {
                Exception e2 = e;
                x h = x.h("application/json;charset=utf-8");
                return new d0.a().g(BaseResp.ERR_MSG_SEND_FAIL).m(String.format("%s==>Exception:%s", new Object[]{chain.g().l().j(), e2.getMessage()})).r(request).b(e0.create(h, "" + e2.getMessage())).p(a0.HTTP_1_1).c();
            }
        } else {
            b0 request2 = chain.g();
            int requestId = this.mNetworkInterpreter.nextRequestId();
            try {
                d0 response = aVar.a(request2);
                if (InterceptorUtil.isImg(response.n("Content-Type")) || !matchWhiteHost(request2)) {
                    return response;
                }
                NetworkRecord record = this.mNetworkInterpreter.createRecord(requestId, new OkHttpInspectorRequest(requestId, request2, new RequestBodyHelper()));
                this.mNetworkInterpreter.fetchResponseInfo(record, new OkHttpInspectorResponse(requestId, request2, response));
                e0 body = response.a();
                InputStream responseStream = null;
                x contentType = null;
                if (body != null) {
                    contentType = body.contentType();
                    responseStream = body.byteStream();
                }
                InputStream responseStream2 = this.mNetworkInterpreter.interpretResponseStream(contentType != null ? contentType.toString() : null, responseStream, new DefaultResponseHandler(this.mNetworkInterpreter, requestId, record));
                record.mResponseBody = OkHttpResponseKt.bodyContent(response);
                LogHelper.d("http-monitor", "response body >>>\n" + record.mResponseBody);
                if (responseStream2 != null) {
                    return response.v().b(new ForwardingResponseBody(body, responseStream2)).c();
                }
                return response;
            } catch (Exception e3) {
                Exception e4 = e3;
                LogHelper.e(TAG, "e===>" + e4.getMessage());
                this.mNetworkInterpreter.httpExchangeFailed(requestId, e4.toString());
                x h2 = x.h("application/json;charset=utf-8");
                return new d0.a().g(BaseResp.ERR_MSG_SEND_FAIL).m(String.format("%s==>Exception:%s", new Object[]{chain.g().l().j(), e4.getMessage()})).r(request2).b(e0.create(h2, "" + e4.getMessage())).p(a0.HTTP_1_1).c();
            }
        }
    }

    private boolean matchWhiteHost(b0 request) {
        List<WhiteHostBean> whiteHostBeans = DokitConstant.WHITE_HOSTS;
        if (whiteHostBeans.isEmpty()) {
            return true;
        }
        for (WhiteHostBean whiteHostBean : whiteHostBeans) {
            if (!TextUtils.isEmpty(whiteHostBean.getHost())) {
                if (whiteHostBean.getHost().equalsIgnoreCase(request.l().j())) {
                    return true;
                }
            }
        }
        return false;
    }
}
