package com.didichuxing.doraemonkit.kit.webdoor;

import android.content.Context;
import android.content.Intent;
import com.didichuxing.doraemonkit.constant.BundleKey;
import com.didichuxing.doraemonkit.constant.CachesKey;
import com.didichuxing.doraemonkit.kit.core.UniversalActivity;
import com.didichuxing.doraemonkit.util.CacheUtils;
import java.io.Serializable;
import java.util.ArrayList;
import net.sqlcipher.database.SQLiteDatabase;

public class WebDoorManager {
    private static final String TAG = "WebDoorManager";
    private ArrayList<String> mHistory;
    private WebDoorCallback mWebDoorCallback = new DefaultWebDoorCallback();

    public interface WebDoorCallback {
        void overrideUrlLoading(Context context, String str);
    }

    public WebDoorCallback getWebDoorCallback() {
        return this.mWebDoorCallback;
    }

    public void setWebDoorCallback(WebDoorCallback webDoorCallback) {
        this.mWebDoorCallback = webDoorCallback;
    }

    public void removeWebDoorCallback() {
        this.mWebDoorCallback = null;
    }

    public void saveHistory(String text) {
        if (this.mHistory == null) {
            this.mHistory = (ArrayList) CacheUtils.readObject(CachesKey.WEB_DOOR_HISTORY);
        }
        if (this.mHistory == null) {
            this.mHistory = new ArrayList<>();
        }
        if (!this.mHistory.contains(text)) {
            if (this.mHistory.size() == 5) {
                this.mHistory.remove(0);
            }
            this.mHistory.add(text);
            CacheUtils.saveObject(CachesKey.WEB_DOOR_HISTORY, (Serializable) this.mHistory);
        }
    }

    public ArrayList<String> getHistory() {
        if (this.mHistory == null) {
            this.mHistory = (ArrayList) CacheUtils.readObject(CachesKey.WEB_DOOR_HISTORY);
        }
        if (this.mHistory == null) {
            this.mHistory = new ArrayList<>();
        }
        return this.mHistory;
    }

    public void clearHistory() {
        this.mHistory.clear();
        CacheUtils.saveObject(CachesKey.WEB_DOOR_HISTORY, (Serializable) this.mHistory);
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static WebDoorManager INSTANCE = new WebDoorManager();

        private Holder() {
        }
    }

    public static WebDoorManager getInstance() {
        return Holder.INSTANCE;
    }

    public class DefaultWebDoorCallback implements WebDoorCallback {
        private DefaultWebDoorCallback() {
        }

        public void overrideUrlLoading(Context context, String url) {
            Intent intent = new Intent(context, UniversalActivity.class);
            intent.setFlags(SQLiteDatabase.CREATE_IF_NECESSARY);
            intent.putExtra(BundleKey.FRAGMENT_INDEX, 18);
            intent.putExtra(BundleKey.KEY_URL, url);
            context.startActivity(intent);
        }
    }
}
