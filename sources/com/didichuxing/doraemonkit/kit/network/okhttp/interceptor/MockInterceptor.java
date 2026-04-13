package com.didichuxing.doraemonkit.kit.network.okhttp.interceptor;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.a;
import com.blankj.utilcode.util.e0;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.health.AppHealthInfoUtil;
import com.didichuxing.doraemonkit.kit.health.model.AppHealthInfo;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.kit.network.okhttp.InterceptorUtil;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.network.room_db.MockInterceptApiBean;
import com.didichuxing.doraemonkit.kit.network.room_db.MockTemplateApiBean;
import com.didichuxing.doraemonkit.util.DokitUtil;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import okhttp3.b0;
import okhttp3.c0;
import okhttp3.d0;
import okhttp3.v;
import okhttp3.w;
import org.glassfish.grizzly.http.server.Constants;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class MockInterceptor implements w {
    public static final String TAG = "MockInterceptor";

    @NotNull
    public d0 intercept(w.a chain) {
        b0 oldRequest = chain.g();
        d0 oldResponse = chain.a(oldRequest);
        if (InterceptorUtil.isImg(oldResponse.n("Content-Type"))) {
            return oldResponse;
        }
        v url = oldRequest.l();
        if (url.j().equalsIgnoreCase(NetworkManager.MOCK_HOST)) {
            return oldResponse;
        }
        String path = URLDecoder.decode(url.e(), "utf-8");
        String queries = url.q();
        String jsonQuery = transformQuery(queries);
        String str = path;
        String str2 = jsonQuery;
        String transformRequestBody = transformRequestBody(oldRequest.a());
        String interceptMatchedId = DokitDbManager.getInstance().isMockMatched(str, str2, transformRequestBody, 1, DokitDbManager.FROM_SDK_OTHER);
        String templateMatchedId = DokitDbManager.getInstance().isMockMatched(str, str2, transformRequestBody, 2, DokitDbManager.FROM_SDK_OTHER);
        try {
            if (DokitConstant.APP_HEALTH_RUNNING) {
                try {
                    addNetWokInfoInAppHealth(oldRequest, oldResponse);
                } catch (Exception e) {
                    e = e;
                    String str3 = queries;
                    String str4 = path;
                    b0 b0Var = oldRequest;
                    String str5 = templateMatchedId;
                }
            }
            if (!TextUtils.isEmpty(interceptMatchedId)) {
                String str6 = queries;
                b0 b0Var2 = oldRequest;
                b0 b0Var3 = oldRequest;
                String str7 = templateMatchedId;
                String str8 = path;
                try {
                    return matchedInterceptRule(url, path, interceptMatchedId, templateMatchedId, b0Var2, oldResponse, chain);
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    return oldResponse;
                }
            } else {
                b0 b0Var4 = oldRequest;
                matchedTemplateRule(oldResponse, path, templateMatchedId);
                return oldResponse;
            }
        } catch (Exception e3) {
            e = e3;
            String str9 = queries;
            String str10 = path;
            b0 b0Var5 = oldRequest;
            String str11 = templateMatchedId;
            e.printStackTrace();
            return oldResponse;
        }
    }

    private String transformQuery(String query) {
        if (TextUtils.isEmpty(query)) {
            return "";
        }
        try {
            String json = DokitUtil.param2Json(query);
            new JSONObject(json);
            return json;
        } catch (Exception e) {
            return DokitDbManager.IS_NOT_NORMAL_QUERY_PARAMS;
        }
    }

    private String transformRequestBody(c0 requestBody) {
        if (requestBody == null || requestBody.contentType() == null) {
            return "";
        }
        try {
            if (TextUtils.isEmpty(DokitUtil.requestBodyToString(requestBody))) {
                return "";
            }
            if (requestBody.contentType().toString().toLowerCase().contains("application/x-www-form-urlencoded")) {
                String json = DokitUtil.param2Json(DokitUtil.requestBodyToString(requestBody));
                new JSONObject(json);
                return json;
            } else if (!requestBody.contentType().toString().toLowerCase().contains("application/json")) {
                return DokitDbManager.IS_NOT_NORMAL_BODY_PARAMS;
            } else {
                String json2 = DokitUtil.requestBodyToString(requestBody);
                new JSONObject(json2);
                return json2;
            }
        } catch (Exception e) {
            LogHelper.e(TAG, "===body json====>" + "");
            return "";
        }
    }

    private void addNetWokInfoInAppHealth(@NonNull b0 request, @NonNull d0 response) {
        long upSize = -1;
        long downSize = -1;
        try {
            if (request.a() != null) {
                upSize = request.a().contentLength();
            }
            if (response.a() != null) {
                downSize = response.a().contentLength();
            }
            long j = 0;
            if (upSize >= 0 || downSize >= 0) {
                long upSize2 = upSize > 0 ? upSize : 0;
                if (downSize > 0) {
                    j = downSize;
                }
                long downSize2 = j;
                String activityName = a.b().getClass().getCanonicalName();
                AppHealthInfo.DataBean.NetworkBean networkBean = AppHealthInfoUtil.getInstance().getNetWorkInfo(activityName);
                AppHealthInfo.DataBean.NetworkBean.NetworkValuesBean networkValuesBean = new AppHealthInfo.DataBean.NetworkBean.NetworkValuesBean();
                networkValuesBean.setCode("" + response.j());
                networkValuesBean.setUp("" + upSize2);
                networkValuesBean.setDown("" + downSize2);
                networkValuesBean.setMethod(request.h());
                networkValuesBean.setTime("" + com.blankj.utilcode.util.d0.b());
                networkValuesBean.setUrl(request.l().toString());
                if (networkBean == null) {
                    AppHealthInfo.DataBean.NetworkBean networkBean2 = new AppHealthInfo.DataBean.NetworkBean();
                    networkBean2.setPage(activityName);
                    List<AppHealthInfo.DataBean.NetworkBean.NetworkValuesBean> networkValuesBeans = new ArrayList<>();
                    networkValuesBeans.add(networkValuesBean);
                    networkBean2.setValues(networkValuesBeans);
                    AppHealthInfoUtil.getInstance().addNetWorkInfo(networkBean2);
                    return;
                }
                List<AppHealthInfo.DataBean.NetworkBean.NetworkValuesBean> networkValuesBeans2 = networkBean.getValues();
                if (networkValuesBeans2 == null) {
                    List<AppHealthInfo.DataBean.NetworkBean.NetworkValuesBean> networkValuesBeans3 = new ArrayList<>();
                    networkValuesBeans3.add(networkValuesBean);
                    networkBean.setValues(networkValuesBeans3);
                    return;
                }
                networkValuesBeans2.add(networkValuesBean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private d0 matchedInterceptRule(v url, String path, String interceptMatchedId, String templateMatchedId, b0 oldRequest, d0 oldResponse, w.a chain) {
        String newUrl;
        String str = path;
        String str2 = templateMatchedId;
        d0 d0Var = oldResponse;
        String scheme = url.t();
        MockInterceptApiBean interceptApiBean = (MockInterceptApiBean) DokitDbManager.getInstance().getInterceptApiByIdInMap(str, interceptMatchedId, DokitDbManager.FROM_SDK_OTHER);
        if (interceptApiBean == null) {
            matchedTemplateRule(d0Var, str, str2);
            return d0Var;
        }
        String selectedSceneId = interceptApiBean.getSelectedSceneId();
        if (!interceptApiBean.isOpen()) {
            matchedTemplateRule(d0Var, str, str2);
            return d0Var;
        } else if (TextUtils.isEmpty(selectedSceneId)) {
            matchedTemplateRule(d0Var, str, str2);
            return d0Var;
        } else {
            StringBuilder sb = new StringBuilder();
            if (NetworkManager.MOCK_SCHEME_HTTP.contains(scheme.toLowerCase())) {
                sb.append(NetworkManager.MOCK_SCHEME_HTTP);
                sb.append(NetworkManager.MOCK_HOST);
                sb.append("/api/app/scene/");
                sb.append(selectedSceneId);
                newUrl = sb.toString();
            } else {
                sb.append(NetworkManager.MOCK_SCHEME_HTTPS);
                sb.append(NetworkManager.MOCK_HOST);
                sb.append("/api/app/scene/");
                sb.append(selectedSceneId);
                newUrl = sb.toString();
            }
            b0 newRequest = new b0.a().i(Constants.GET, (c0) null).p(newUrl).b();
            oldResponse.close();
            d0 newResponse = chain.a(newRequest);
            if (newResponse.j() == 200) {
                e0.n("接口别名:==" + interceptApiBean.getMockApiName() + "==已被拦截");
                if (newResponseHasData(newResponse)) {
                    matchedTemplateRule(newResponse, str, str2);
                    return newResponse;
                }
                matchedTemplateRule(d0Var, str, str2);
                return d0Var;
            }
            matchedTemplateRule(d0Var, str, str2);
            return d0Var;
        }
    }

    private void matchedTemplateRule(d0 oldResponse, String path, String templateMatchedId) {
        MockTemplateApiBean templateApiBean;
        if (!TextUtils.isEmpty(templateMatchedId) && (templateApiBean = (MockTemplateApiBean) DokitDbManager.getInstance().getTemplateApiByIdInMap(path, templateMatchedId, DokitDbManager.FROM_SDK_OTHER)) != null && templateApiBean.isOpen()) {
            saveResponse2DB(oldResponse, templateApiBean);
        }
    }

    private void saveResponse2DB(d0 response, MockTemplateApiBean mockApi) {
        if (response.j() == 200 && response.a() != null) {
            String host = response.J().l().j();
            String strResponseBody = response.x(1048576).string();
            if (!TextUtils.isEmpty(strResponseBody)) {
                if (host.equals(NetworkManager.MOCK_HOST)) {
                    mockApi.setResponseFrom(0);
                } else {
                    mockApi.setResponseFrom(1);
                }
                mockApi.setStrResponse(strResponseBody);
                DokitDbManager.getInstance().updateTemplateApi(mockApi);
                e0.n("模板别名:==" + mockApi.getMockApiName() + "==已被保存");
            }
        }
    }

    private boolean newResponseHasData(d0 response) {
        if (response.a() == null) {
            return false;
        }
        return true;
    }
}
