package smarthome.ui.navigationbar;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import com.leedarson.base.utils.p;
import com.leedarson.serviceinterface.LightsRhythmService;
import com.leedarson.serviceinterface.event.JsBridgeCallbackEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import smarthome.manager.b;

/* compiled from: TabBarEventDispatcher */
public class i {
    public static ChangeQuickRedirect changeQuickRedirect;
    private Activity a;
    private LDSNavigationBar b;

    public i(Activity activity, LDSNavigationBar ldsNavigationBar) {
        this.a = activity;
        this.b = ldsNavigationBar;
    }

    public void d(String callbackkey, String str, Object data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Object.class};
        if (!PatchProxy.proxy(new Object[]{callbackkey, str, data}, this, changeQuickRedirect, false, 14288, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(data.toString());
                String activeKey = jsonObject.optString("activeKey");
                JSONArray tabs = jsonObject.optJSONArray("tabs");
                JSONArray badge = jsonObject.optJSONArray("badge");
                JSONArray tabPreload = jsonObject.optJSONArray("tabPreload");
                this.b.M(activeKey, tabs, badge);
                b.g().o(tabPreload);
                c.c().l(new JsBridgeCallbackEvent(callbackkey, p.c().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void e(String callbackkey, String str, Object data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Object.class};
        if (!PatchProxy.proxy(new Object[]{callbackkey, str, data}, this, changeQuickRedirect, false, 14289, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(data.toString());
                if (jsonObject.has("visible")) {
                    if (jsonObject.getBoolean("visible")) {
                        this.b.N();
                    } else {
                        this.b.o();
                    }
                }
                c.c().l(new JsBridgeCallbackEvent(callbackkey, p.c().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void a(String callbackkey, String action, Object data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Object.class};
        if (!PatchProxy.proxy(new Object[]{callbackkey, action, data}, this, changeQuickRedirect, false, 14290, clsArr, Void.TYPE).isSupported) {
            if (LightsRhythmService.ACTION_SET_CONFIG.equals(action)) {
                d(callbackkey, action, data);
            } else if ("visible".equals(action)) {
                e(callbackkey, action, data);
            } else if ("reload".equals(action)) {
                c(callbackkey, action, data);
            } else if ("pageLoaded".equals(action)) {
                b(callbackkey, data);
            }
        }
    }

    private void c(String callbackkey, String str, Object data) {
        Class<String> cls = String.class;
        Class[] clsArr = {cls, cls, Object.class};
        if (!PatchProxy.proxy(new Object[]{callbackkey, str, data}, this, changeQuickRedirect, false, 14291, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject jsonObject = new JSONObject(data.toString());
                if (jsonObject.has("reload")) {
                    b.g().f(jsonObject.optString("reload")).getWebView().reload();
                }
                c.c().l(new JsBridgeCallbackEvent(callbackkey, p.c().toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void b(String str, Object data) {
        Class[] clsArr = {String.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{str, data}, this, changeQuickRedirect, false, 14292, clsArr, Void.TYPE).isSupported) {
            try {
                String _tabKey = new JSONObject(data.toString()).optString("tabKey");
                if (!TextUtils.isEmpty(_tabKey)) {
                    this.b.H(_tabKey);
                }
            } catch (Exception e) {
                Log.e("TabBarEventDidpatch", " onPageLoaded.exception =" + e.toString());
            }
        }
    }
}
