package com.didichuxing.doraemonkit.kit.core;

import android.app.Activity;
import java.util.Map;

public interface DokitViewManagerInterface {
    void attach(DokitIntent dokitIntent);

    void detach(Activity activity, AbsDokitView absDokitView);

    void detach(Activity activity, Class<? extends AbsDokitView> cls);

    void detach(Activity activity, String str);

    void detach(AbsDokitView absDokitView);

    void detach(Class<? extends AbsDokitView> cls);

    void detach(String str);

    void detachAll();

    AbsDokitView getDokitView(Activity activity, String str);

    Map<String, AbsDokitView> getDokitViews(Activity activity);

    void notifyBackground();

    void notifyForeground();

    void onActivityCreate(Activity activity);

    void onActivityDestroy(Activity activity);

    void onActivityPause(Activity activity);

    void onActivityResume(Activity activity);

    void onMainActivityCreate(Activity activity);

    void resumeAndAttachDokitViews(Activity activity);
}
