package com.didichuxing.doraemonkit.datapick;

import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.blankj.utilcode.util.i;
import com.blankj.utilcode.util.j;
import com.blankj.utilcode.util.k;
import com.blankj.utilcode.util.r;
import com.didichuxing.doraemonkit.datapick.DataPickBean;
import com.didichuxing.doraemonkit.kit.network.NetworkManager;
import com.didichuxing.doraemonkit.okgo.DokitOkGo;
import com.didichuxing.doraemonkit.okgo.callback.StringCallback;
import com.didichuxing.doraemonkit.okgo.model.Response;
import com.didichuxing.doraemonkit.okgo.request.PostRequest;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataPickManager {
    private static final String TAG = "DataPickManager";
    /* access modifiers changed from: private */
    public static int jsonFromFile = 100;
    /* access modifiers changed from: private */
    public static int jsonFromMemory = 101;
    private DataPickBean dataPickBean = new DataPickBean();
    /* access modifiers changed from: private */
    public List<DataPickBean.EventBean> events = new ArrayList();
    /* access modifiers changed from: private */
    public String filePath = (r.d() + File.separator + "dokit.json");

    public static class Holder {
        /* access modifiers changed from: private */
        public static DataPickManager INSTANCE = new DataPickManager();

        private Holder() {
        }
    }

    public static DataPickManager getInstance() {
        return Holder.INSTANCE;
    }

    public void addData(@NonNull String eventName) {
        DataPickBean.EventBean eventBean = new DataPickBean.EventBean(eventName);
        List<DataPickBean.EventBean> list = this.events;
        if (list != null) {
            list.add(eventBean);
            if (this.events.size() >= 10) {
                postData();
            } else if (this.events.size() >= 2) {
                List<DataPickBean.EventBean> list2 = this.events;
                long lastTime = Long.parseLong(list2.get(list2.size() - 1).getTime());
                List<DataPickBean.EventBean> list3 = this.events;
                if (lastTime - Long.parseLong(list3.get(list3.size() - 2).getTime()) >= 60000) {
                    postData();
                }
            }
        } else {
            ArrayList arrayList = new ArrayList();
            this.events = arrayList;
            arrayList.add(eventBean);
        }
    }

    public void postData() {
        String strJson = i.d(this.filePath);
        if (!TextUtils.isEmpty(strJson)) {
            try {
                realPost(jsonFromFile, strJson);
            } catch (Exception e) {
            }
        } else {
            List<DataPickBean.EventBean> list = this.events;
            if (list != null && list.size() != 0) {
                this.dataPickBean.setEvents(this.events);
                try {
                    realPost(jsonFromMemory, k.j(this.dataPickBean));
                } catch (Exception e2) {
                }
            }
        }
    }

    private void realPost(final int from, String content) {
        ((PostRequest) DokitOkGo.post(NetworkManager.APP_DATA_PICK_URL).upJson(content)).execute(new StringCallback() {
            public void onSuccess(Response<String> response) {
                if (from == DataPickManager.jsonFromFile) {
                    j.e(DataPickManager.this.filePath);
                }
                if (from == DataPickManager.jsonFromMemory) {
                    DataPickManager.this.events.clear();
                }
            }

            public void onError(Response<String> response) {
                super.onError(response);
                LogHelper.e(DataPickManager.TAG, "error===>" + response.getException().getMessage());
            }
        });
    }

    public void saveData2Local() {
        List<DataPickBean.EventBean> list = this.events;
        if (list != null && list.size() != 0) {
            this.dataPickBean.setEvents(this.events);
            i.f(this.filePath, k.j(this.dataPickBean));
        }
    }
}
