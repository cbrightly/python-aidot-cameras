package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;
import androidx.room.Room;
import com.didichuxing.doraemonkit.DoraemonKit;
import com.didichuxing.doraemonkit.constant.DokitConstant;
import com.didichuxing.doraemonkit.kit.main.MainIconDokitView;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDatabase;
import com.didichuxing.doraemonkit.kit.network.room_db.DokitDbManager;
import com.didichuxing.doraemonkit.kit.toolpanel.ToolPanelDokitView;
import com.didichuxing.doraemonkit.util.LogHelper;
import java.util.HashMap;
import java.util.Map;

public class DokitViewManager implements DokitViewManagerInterface {
    private static final String TAG = "DokitViewManagerProxy";
    private static Map<String, Point> mDokitViewPos;
    private Context mContext;
    private DokitDatabase mDB;
    private DokitViewManagerInterface mDokitViewManager;
    private Map<String, LastDokitViewPosInfo> mLastDokitViewPosInfoMaps;

    public interface DokitViewAttachedListener {
        void onDokitViewAdd(AbsDokitView absDokitView);
    }

    public static class Holder {
        /* access modifiers changed from: private */
        public static DokitViewManager INSTANCE = new DokitViewManager();

        private Holder() {
        }
    }

    public static DokitViewManager getInstance() {
        return Holder.INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        if (DokitConstant.IS_NORMAL_FLOAT_MODE) {
            this.mDokitViewManager = new NormalDokitViewManager(context);
        } else {
            this.mDokitViewManager = new SystemDokitViewManager(context);
        }
        mDokitViewPos = new HashMap();
        this.mLastDokitViewPosInfoMaps = new HashMap();
        getDb();
        DokitDbManager.getInstance().getAllInterceptApis();
        DokitDbManager.getInstance().getAllTemplateApis();
    }

    public DokitDatabase getDb() {
        DokitDatabase dokitDatabase = this.mDB;
        if (dokitDatabase != null) {
            return dokitDatabase;
        }
        DokitDatabase build = Room.databaseBuilder(DoraemonKit.APPLICATION, DokitDatabase.class, "dokit-database").allowMainThreadQueries().build();
        this.mDB = build;
        return build;
    }

    public void notifyBackground() {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.notifyBackground();
        }
    }

    public void notifyForeground() {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.notifyForeground();
        }
    }

    /* access modifiers changed from: package-private */
    public void saveDokitViewPos(String tag, int marginLeft, int marginTop) {
        Map<String, Point> map = mDokitViewPos;
        if (map != null) {
            if (map.get(tag) == null) {
                mDokitViewPos.put(tag, new Point(marginLeft, marginTop));
                return;
            }
            Point point = mDokitViewPos.get(tag);
            if (point != null) {
                point.set(marginLeft, marginTop);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Point getDokitViewPos(String tag) {
        Map<String, Point> map = mDokitViewPos;
        if (map == null) {
            return null;
        }
        return map.get(tag);
    }

    public void resumeAndAttachDokitViews(Activity activity) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.resumeAndAttachDokitViews(activity);
        }
    }

    public void onMainActivityCreate(Activity activity) {
    }

    public void onActivityCreate(Activity activity) {
    }

    public void onActivityResume(Activity activity) {
    }

    public void onActivityPause(Activity activity) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.onActivityPause(activity);
        }
    }

    public void attach(DokitIntent dokitIntent) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.attach(dokitIntent);
        }
    }

    public void detachToolPanel() {
        detach(ToolPanelDokitView.class.getSimpleName());
    }

    public void attachToolPanel() {
        DokitIntent toolPanelIntent = new DokitIntent(ToolPanelDokitView.class);
        toolPanelIntent.mode = 1;
        attach(toolPanelIntent);
    }

    public void attachMainIcon() {
        DokitIntent mainIconIntent = new DokitIntent(MainIconDokitView.class);
        mainIconIntent.mode = 1;
        attach(mainIconIntent);
    }

    public void detachMainIcon() {
        detach(MainIconDokitView.class.getSimpleName());
    }

    public void detach(String tag) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(tag);
        }
    }

    public void detach(Activity activity, String tag) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(activity, tag);
        }
    }

    public void detach(AbsDokitView dokitView) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(dokitView);
        }
    }

    public void detach(Activity activity, AbsDokitView dokitView) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(activity, dokitView);
        }
    }

    public void detach(Class<? extends AbsDokitView> dokitViewClass) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(dokitViewClass);
        }
    }

    public void detach(Activity activity, Class<? extends AbsDokitView> dokitViewClass) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detach(activity, dokitViewClass);
        }
    }

    public void detachAll() {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.detachAll();
        }
    }

    public void onActivityDestroy(Activity activity) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else {
            dokitViewManagerInterface.onActivityDestroy(activity);
        }
    }

    public AbsDokitView getDokitView(Activity activity, String tag) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface != null) {
            return dokitViewManagerInterface.getDokitView(activity, tag);
        }
        LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        return null;
    }

    public Map<String, AbsDokitView> getDokitViews(Activity activity) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface != null) {
            return dokitViewManagerInterface.getDokitViews(activity);
        }
        LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        return new HashMap();
    }

    /* access modifiers changed from: package-private */
    public void addDokitViewAttachedListener(DokitViewAttachedListener listener) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else if (!DokitConstant.IS_NORMAL_FLOAT_MODE && (dokitViewManagerInterface instanceof SystemDokitViewManager)) {
            ((SystemDokitViewManager) dokitViewManagerInterface).addListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public void removeDokitViewAttachedListener(DokitViewAttachedListener listener) {
        DokitViewManagerInterface dokitViewManagerInterface = this.mDokitViewManager;
        if (dokitViewManagerInterface == null) {
            LogHelper.e("Doraemon", "mDokitViewManager == null请检查是否已在Application的onCreate中完成初始化");
        } else if (!DokitConstant.IS_NORMAL_FLOAT_MODE && (dokitViewManagerInterface instanceof SystemDokitViewManager)) {
            ((SystemDokitViewManager) dokitViewManagerInterface).removeListener(listener);
        }
    }

    /* access modifiers changed from: package-private */
    public WindowManager getWindowManager() {
        return (WindowManager) this.mContext.getSystemService("window");
    }

    /* access modifiers changed from: package-private */
    public void saveLastDokitViewPosInfo(String key, LastDokitViewPosInfo lastDokitViewPosInfo) {
        Map<String, LastDokitViewPosInfo> map = this.mLastDokitViewPosInfoMaps;
        if (map != null) {
            map.put(key, lastDokitViewPosInfo);
        }
    }

    /* access modifiers changed from: package-private */
    public LastDokitViewPosInfo getLastDokitViewPosInfo(String key) {
        Map<String, LastDokitViewPosInfo> map = this.mLastDokitViewPosInfoMaps;
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /* access modifiers changed from: package-private */
    public void removeLastDokitViewPosInfo(String key) {
        Map<String, LastDokitViewPosInfo> map = this.mLastDokitViewPosInfoMaps;
        if (map != null) {
            map.remove(key);
        }
    }
}
