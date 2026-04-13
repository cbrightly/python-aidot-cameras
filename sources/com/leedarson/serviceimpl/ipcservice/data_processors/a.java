package com.leedarson.serviceimpl.ipcservice.data_processors;

import android.app.Activity;
import android.content.Intent;
import com.leedarson.R$anim;
import com.leedarson.bean.H5ActionName;
import com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalActivity;
import com.luck.picture.lib.config.PictureConfig;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CloudPlaySignalEventProcessor */
public class a extends g {
    public static ChangeQuickRedirect changeQuickRedirect;

    public a() {
        this.b = H5ActionName.ACTION_PUSH;
        this.a = "Navigator";
    }

    public boolean a(Activity activity, String str, String str2, String action, String str3) {
        Class<String> cls = String.class;
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{activity, str, str2, action, str3}, this, changeQuickRedirect2, false, 8086, new Class[]{Activity.class, cls, cls, cls, cls}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        String str4 = str;
        Activity activity2 = activity;
        String service = str2;
        String data = str3;
        if (this.b.equals(action) && this.a.equals(service)) {
            try {
                JSONObject dataObj = new JSONObject(data);
                if (dataObj.has(PictureConfig.EXTRA_PAGE) && dataObj.getString(PictureConfig.EXTRA_PAGE).equals(H5ActionName.IPC_SIGNAL_EVENT)) {
                    com.leedarson.base.logger.a.c("signal_event", "signal_event_push===>data=" + data);
                    if (dataObj.has("params")) {
                        JSONObject params = dataObj.getJSONObject("params");
                        Intent intent = new Intent(activity2, CloudPlayBackEventSignalActivity.class);
                        intent.putExtra(CloudPlayBackEventSignalActivity.a2, params.toString());
                        activity2.startActivity(intent);
                        activity2.overridePendingTransition(R$anim.ipc_slide_in_right, R$anim.ipc_slide_out_left);
                    }
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
