package com.sensorsdata.analytics.android.sdk.internal.api;

import org.json.JSONObject;

public interface IUserIdentityAPI {
    public static final String BIND_ID = "$BindID";
    public static final String UNBIND_ID = "$UnbindID";

    void bind(String str, String str2);

    @Deprecated
    String getAnonymousId();

    String getDistinctId();

    String getLoginId();

    @Deprecated
    void identify(String str);

    void login(String str);

    void login(String str, JSONObject jSONObject);

    void logout();

    @Deprecated
    void resetAnonymousId();

    void unbind(String str, String str2);
}
