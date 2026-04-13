package com.didichuxing.doraemonkit.kit.network.room_db;

import android.text.TextUtils;
import com.blankj.utilcode.util.b0;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.network.room_db.AbsMockApiBean;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DokitDbManager<T extends AbsMockApiBean> {
    public static final String CONTENT_TYPE = "application/json";
    public static int FROM_SDK_DIDI = 100;
    public static int FROM_SDK_OTHER = 101;
    public static final String IS_NOT_NORMAL_BODY_PARAMS = "is not normal body parmas";
    public static final String IS_NOT_NORMAL_QUERY_PARAMS = "is not normal query parmas";
    public static final String MEDIA_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String MEDIA_TYPE_JSON = "application/json";
    public static final int MOCK_API_INTERCEPT = 1;
    public static final int MOCK_API_TEMPLATE = 2;
    private static final String TAG = "DokitDbManager";
    private Map<String, List<T>> mGlobalInterceptApiMaps = new HashMap();
    private MockTemplateApiBean mGlobalTemplateApiBean;
    private Map<String, List<T>> mGlobalTemplateApiMaps = new HashMap();

    public Map<String, List<T>> getGlobalInterceptApiMaps() {
        return this.mGlobalInterceptApiMaps;
    }

    public Map<String, List<T>> getGlobalTemplateApiMaps() {
        return this.mGlobalTemplateApiMaps;
    }

    public MockTemplateApiBean getGlobalTemplateApiBean() {
        return this.mGlobalTemplateApiBean;
    }

    public void setGlobalTemplateApiBean(MockTemplateApiBean mGlobalTemplateApiBean2) {
        this.mGlobalTemplateApiBean = mGlobalTemplateApiBean2;
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static DokitDbManager INSTANCE = new DokitDbManager();

        private Holder() {
        }
    }

    public static DokitDbManager getInstance() {
        return Holder.INSTANCE;
    }

    public void getAllInterceptApis() {
        b0.g(new b0.e<List<T>>() {
            public List<T> doInBackground() {
                DokitDatabase db = DokitViewManager.getInstance().getDb();
                if (db != null && db.mockApiDao() != null) {
                    return db.mockApiDao().getAllInterceptApi();
                }
                throw new NullPointerException("mDb == null || mDb.mockApiDao()");
            }

            public void onSuccess(List<T> result) {
                DokitDbManager.this.list2mapByIntercept(result);
            }

            public void onFail(Throwable t) {
                super.onFail(t);
                LogHelper.e(DokitDbManager.TAG, "error====>" + t.getMessage());
            }
        });
    }

    public void getAllTemplateApis() {
        b0.g(new b0.e<List<T>>() {
            public List<T> doInBackground() {
                DokitDatabase db = DokitViewManager.getInstance().getDb();
                if (db != null && db.mockApiDao() != null) {
                    return db.mockApiDao().getAllTemplateApi();
                }
                throw new NullPointerException("mDb == null || mDb.mockApiDao()");
            }

            public void onSuccess(List<T> result) {
                DokitDbManager.this.list2mapByTemplate(result);
            }

            public void onFail(Throwable t) {
                super.onFail(t);
                LogHelper.e(DokitDbManager.TAG, "error====>" + t.getMessage());
            }
        });
    }

    public T getTemplateApiByIdInDb(String id) {
        return DokitViewManager.getInstance().getDb().mockApiDao().findTemplateApiById(id);
    }

    public T getInterceptApiByIdInDb(String id) {
        return DokitViewManager.getInstance().getDb().mockApiDao().findInterceptApiById(id);
    }

    public T getInterceptApiByIdInMap(String path, String id, int fromSDK) {
        Map<String, List<T>> map = this.mGlobalInterceptApiMaps;
        if (map == null) {
            return null;
        }
        List<T> mGlobalInterceptApis = map.get(path);
        if (mGlobalInterceptApis == null) {
            mGlobalInterceptApis = this.mGlobalInterceptApiMaps.get(DokitConstant.dealDidiPlatformPath(path, fromSDK));
        }
        if (mGlobalInterceptApis == null) {
            return null;
        }
        for (T mockApi : mGlobalInterceptApis) {
            if (mockApi.getId().equals(id)) {
                return mockApi;
            }
        }
        return null;
    }

    public T getTemplateApiByIdInMap(String path, String id, int fromSDK) {
        Map<String, List<T>> map = this.mGlobalTemplateApiMaps;
        if (map == null) {
            return null;
        }
        List<T> mGlobalTemplateApis = map.get(path);
        if (mGlobalTemplateApis == null) {
            mGlobalTemplateApis = this.mGlobalTemplateApiMaps.get(DokitConstant.dealDidiPlatformPath(path, fromSDK));
        }
        if (mGlobalTemplateApis == null) {
            return null;
        }
        for (T mockApi : mGlobalTemplateApis) {
            if (mockApi.getId().equals(id)) {
                return mockApi;
            }
        }
        return null;
    }

    public void insertAllInterceptApi(final List<MockInterceptApiBean> mockApis) {
        b0.g(new b0.e<Object>() {
            public Object doInBackground() {
                DokitViewManager.getInstance().getDb().mockApiDao().insertAllInterceptApi(mockApis);
                DokitDbManager.this.getAllInterceptApis();
                return null;
            }

            public void onSuccess(Object result) {
            }
        });
    }

    public void insertAllTemplateApi(final List<MockTemplateApiBean> mockApis) {
        b0.g(new b0.e<Void>() {
            public Void doInBackground() {
                DokitViewManager.getInstance().getDb().mockApiDao().insertAllTemplateApi(mockApis);
                return null;
            }

            public void onSuccess(Void result) {
                DokitDbManager.this.getAllTemplateApis();
            }
        });
    }

    public void updateInterceptApi(final MockInterceptApiBean mockApi) {
        b0.g(new b0.e<Void>() {
            public Void doInBackground() {
                DokitViewManager.getInstance().getDb().mockApiDao().updateInterceptApi(mockApi);
                return null;
            }

            public void onSuccess(Void result) {
                DokitDbManager.this.getAllInterceptApis();
            }
        });
    }

    public void updateTemplateApi(final MockTemplateApiBean mockApi) {
        b0.g(new b0.e<Object>() {
            public Object doInBackground() {
                DokitViewManager.getInstance().getDb().mockApiDao().updateTemplateApi(mockApi);
                DokitDbManager.this.getAllTemplateApis();
                return null;
            }

            public void onSuccess(Object result) {
            }
        });
    }

    public String getMockInterceptSelectedSceneIdByPathAndId(String path, String id) {
        if (this.mGlobalInterceptApiMaps.get(path) == null) {
            return "";
        }
        for (T mockApi : this.mGlobalInterceptApiMaps.get(path)) {
            if (mockApi.getId().equals(id)) {
                return mockApi.getSelectedSceneId();
            }
        }
        return "";
    }

    public String isMockMatched(String path, String jsonQuery, String jsonRequestBody, int operateType, int fromSDK) {
        T mockApi;
        if (!TextUtils.isEmpty(jsonQuery) && jsonQuery.equals(IS_NOT_NORMAL_QUERY_PARAMS)) {
            return "";
        }
        if ((TextUtils.isEmpty(jsonRequestBody) || !jsonRequestBody.equals(IS_NOT_NORMAL_BODY_PARAMS)) && (mockApi = mockMatched(path, jsonQuery, jsonRequestBody, operateType, fromSDK)) != null) {
            return mockApi.getId();
        }
        return "";
    }

    private T mockMatched(String path, String jsonQuery, String jsonRequestBody, int operateType, int fromSDK) {
        List<T> mockApis = null;
        if (operateType == 1) {
            mockApis = this.mGlobalInterceptApiMaps.get(path);
            if (mockApis == null) {
                mockApis = this.mGlobalInterceptApiMaps.get(DokitConstant.dealDidiPlatformPath(path, fromSDK));
            }
        } else if (operateType == 2 && (mockApis = this.mGlobalTemplateApiMaps.get(path)) == null) {
            mockApis = this.mGlobalTemplateApiMaps.get(DokitConstant.dealDidiPlatformPath(path, fromSDK));
        }
        if (mockApis == null) {
            return null;
        }
        for (T mockApi : mockApis) {
            if (mockApi.isOpen() && queriesMatched(jsonQuery, mockApi) && bodyMatched(jsonRequestBody, mockApi)) {
                return mockApi;
            }
        }
        return null;
    }

    private boolean queriesMatched(String jsonQuery, T mockApi) {
        String mockQuery = mockApi.getQuery();
        boolean mockQueryIsEmpty = TextUtils.isEmpty(mockQuery) || "{}".equals(mockQuery);
        if (mockQueryIsEmpty && TextUtils.isEmpty(jsonQuery)) {
            return true;
        }
        if (!TextUtils.isEmpty(jsonQuery) && mockQueryIsEmpty) {
            return true;
        }
        if ((!TextUtils.isEmpty(jsonQuery) || mockQueryIsEmpty) && !TextUtils.isEmpty(jsonQuery) && !mockQueryIsEmpty) {
            try {
                JSONObject jsonQueryLocal = new JSONObject(jsonQuery);
                JSONObject jsonQueryMock = new JSONObject(mockQuery);
                List<String> keys = new ArrayList<>();
                Iterator<String> iterator = jsonQueryMock.keys();
                while (iterator.hasNext()) {
                    keys.add(iterator.next());
                }
                int count = 0;
                for (int index = 0; index < keys.size(); index++) {
                    String key = keys.get(index);
                    if (jsonQueryLocal.has(key) && jsonQueryMock.getString(key).equals(jsonQueryLocal.get(key))) {
                        count++;
                    }
                }
                if (count == keys.size()) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    private boolean bodyMatched(String jsonRequestBody, T mockApi) {
        String mockBody = mockApi.getBody();
        boolean mockQueryIsEmpty = TextUtils.isEmpty(mockBody) || "{}".equals(mockBody);
        if (mockQueryIsEmpty && TextUtils.isEmpty(jsonRequestBody)) {
            return true;
        }
        if (!TextUtils.isEmpty(jsonRequestBody) && mockQueryIsEmpty) {
            return true;
        }
        if ((!TextUtils.isEmpty(jsonRequestBody) || mockQueryIsEmpty) && !TextUtils.isEmpty(jsonRequestBody) && !mockQueryIsEmpty) {
            try {
                JSONObject jsonBodyLocal = new JSONObject(jsonRequestBody);
                JSONObject jsonBodyMock = new JSONObject(mockBody);
                List<String> keys = new ArrayList<>();
                Iterator<String> iterator = jsonBodyMock.keys();
                while (iterator.hasNext()) {
                    keys.add(iterator.next());
                }
                int count = 0;
                for (int index = 0; index < keys.size(); index++) {
                    String key = keys.get(index);
                    if (jsonBodyLocal.has(key) && jsonBodyMock.getString(key).equals(jsonBodyLocal.get(key))) {
                        count++;
                    }
                }
                if (count == keys.size()) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    public void list2mapByIntercept(List<T> interceptApiBeans) {
        this.mGlobalInterceptApiMaps.clear();
        for (T mockApi : interceptApiBeans) {
            if (this.mGlobalInterceptApiMaps.get(mockApi.getPath()) == null) {
                List<T> mockInterceptApiBeans = new ArrayList<>();
                mockInterceptApiBeans.add(mockApi);
                this.mGlobalInterceptApiMaps.put(mockApi.getPath(), mockInterceptApiBeans);
            } else {
                this.mGlobalInterceptApiMaps.get(mockApi.getPath()).add(mockApi);
            }
        }
    }

    /* access modifiers changed from: private */
    public void list2mapByTemplate(List<T> templateApiBeans) {
        this.mGlobalTemplateApiMaps.clear();
        for (T mockApi : templateApiBeans) {
            if (this.mGlobalTemplateApiMaps.get(mockApi.getPath()) == null) {
                List<T> mockTemplateApiBeans = new ArrayList<>();
                mockTemplateApiBeans.add(mockApi);
                this.mGlobalTemplateApiMaps.put(mockApi.getPath(), mockTemplateApiBeans);
            } else {
                this.mGlobalTemplateApiMaps.get(mockApi.getPath()).add(mockApi);
            }
        }
    }
}
