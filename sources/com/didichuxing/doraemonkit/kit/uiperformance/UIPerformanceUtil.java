package com.didichuxing.doraemonkit.kit.uiperformance;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import com.didichuxing.doraemonkit.model.ViewInfo;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.util.UIUtils;
import java.util.ArrayList;
import java.util.List;

public class UIPerformanceUtil {
    private static final String TAG = "UIPerformanceUtil";

    public static List<ViewInfo> getViewInfos(Activity activity) {
        if (activity == null) {
            LogHelper.d(TAG, "resume activity is null");
            return new ArrayList();
        } else if (activity.getWindow() != null) {
            return getViewInfos(UIUtils.getDokitAppContentView(activity));
        } else {
            LogHelper.d(TAG, "resume activity window is null");
            return new ArrayList();
        }
    }

    private static List<ViewInfo> getViewInfos(View view) {
        List<ViewInfo> infos = new ArrayList<>();
        traverseViews(view, infos, 0);
        return infos;
    }

    private static void traverseViews(View view, List<ViewInfo> infos, int layerNum) {
        if (view != null) {
            int layerNum2 = layerNum + 1;
            if (view instanceof ViewGroup) {
                int childCount = ((ViewGroup) view).getChildCount();
                if (childCount != 0) {
                    for (int index = childCount - 1; index >= 0; index--) {
                        traverseViews(((ViewGroup) view).getChildAt(index), infos, layerNum2);
                    }
                    return;
                }
                return;
            }
            long startTime = System.nanoTime();
            ViewInfo viewInfo = new ViewInfo(view);
            viewInfo.drawTime = ((float) ((System.nanoTime() - startTime) / 10000)) / 100.0f;
            viewInfo.layerNum = layerNum2;
            infos.add(viewInfo);
        }
    }
}
