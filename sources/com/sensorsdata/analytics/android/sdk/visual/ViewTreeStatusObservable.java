package com.sensorsdata.analytics.android.sdk.visual;

import android.app.Activity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.AppStateManager;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;
import com.sensorsdata.analytics.android.sdk.util.WindowHelper;
import com.sensorsdata.analytics.android.sdk.visual.model.SnapInfo;
import com.sensorsdata.analytics.android.sdk.visual.model.ViewNode;
import com.sensorsdata.analytics.android.sdk.visual.util.Dispatcher;
import com.sensorsdata.analytics.android.sdk.visual.util.VisualUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class ViewTreeStatusObservable implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener, ViewTreeObserver.OnGlobalFocusChangeListener {
    private static final String TAG = "SA.ViewTreeStatusObservable";
    public static volatile ViewTreeStatusObservable viewTreeStatusObservable;
    private final Runnable mTraverseRunnable = new TraverseRunnable();
    private HashMap<String, ViewNode> mViewNodesHashMap = new HashMap<>();
    private SparseArray<ViewNode> mViewNodesWithHashCode = new SparseArray<>();
    private HashMap<String, ViewNode> mWebViewHashMap = new HashMap<>();

    public static ViewTreeStatusObservable getInstance() {
        if (viewTreeStatusObservable == null) {
            synchronized (ViewTreeStatusObservable.class) {
                if (viewTreeStatusObservable == null) {
                    viewTreeStatusObservable = new ViewTreeStatusObservable();
                }
            }
        }
        return viewTreeStatusObservable;
    }

    public class TraverseRunnable implements Runnable {
        TraverseRunnable() {
        }

        public void run() {
            long startTime = System.currentTimeMillis();
            SALog.i(ViewTreeStatusObservable.TAG, "start traverse...");
            ViewTreeStatusObservable.this.traverseNode();
            SALog.i(ViewTreeStatusObservable.TAG, "stop traverse...:" + (System.currentTimeMillis() - startTime));
        }
    }

    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
        SALog.i(TAG, "onGlobalFocusChanged");
        traverse();
    }

    public void onGlobalLayout() {
        SALog.i(TAG, "onGlobalLayout");
        traverse();
    }

    public void onScrollChanged() {
        SALog.i(TAG, "onScrollChanged");
        traverse();
    }

    public void traverse() {
        try {
            Dispatcher.getInstance().postDelayed(this.mTraverseRunnable, 100);
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public ViewNode getViewNode(View view) {
        ViewNode viewNode = null;
        try {
            viewNode = this.mViewNodesWithHashCode.get(view.hashCode());
            if (viewNode == null && (viewNode = getViewPathAndPosition(view)) != null) {
                this.mViewNodesWithHashCode.put(view.hashCode(), viewNode);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
        return viewNode;
    }

    public ViewNode getViewNode(WeakReference<View> reference, String elementPath, String elementPosition, String screenName) {
        Activity activity;
        try {
            ViewNode viewNode = this.mViewNodesHashMap.get(generateKey(elementPath, elementPosition, screenName));
            if (viewNode != null) {
                return viewNode;
            }
            View rootView = null;
            if (!(reference == null || reference.get() == null)) {
                rootView = ((View) reference.get()).getRootView();
            }
            if (rootView == null && (activity = AppStateManager.getInstance().getForegroundActivity()) != null && activity.getWindow() != null && activity.getWindow().isActive()) {
                rootView = activity.getWindow().getDecorView();
            }
            if (rootView != null) {
                traverseNode(rootView);
            }
            return this.mViewNodesHashMap.get(generateKey(elementPath, elementPosition, screenName));
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public ViewNode getViewNode(String elementPath) {
        try {
            ViewNode viewNode = this.mWebViewHashMap.get(elementPath);
            if (viewNode != null && viewNode.getView() != null && viewNode.getView().get() != null) {
                return viewNode;
            }
            View rootView = null;
            Activity activity = AppStateManager.getInstance().getForegroundActivity();
            if (!(activity == null || activity.getWindow() == null || !activity.getWindow().isActive())) {
                rootView = activity.getWindow().getDecorView();
            }
            if (rootView != null) {
                traverseNode(rootView);
            }
            return this.mWebViewHashMap.get(elementPath);
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    public void clearWebViewCache() {
        try {
            HashMap<String, ViewNode> hashMap = this.mWebViewHashMap;
            if (hashMap != null) {
                hashMap.clear();
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    /* access modifiers changed from: private */
    public void traverseNode() {
        this.mViewNodesHashMap.clear();
        this.mViewNodesWithHashCode.clear();
        this.mWebViewHashMap.clear();
        traverseNode((View) null);
    }

    private void traverseNode(View rootView) {
        try {
            SparseArray<ViewNode> tempSparseArray = new SparseArray<>();
            HashMap<String, ViewNode> tempHashMap = new HashMap<>();
            HashMap<String, ViewNode> tempWebViewHashMap = new HashMap<>();
            if (rootView != null) {
                traverseNode(rootView, tempSparseArray, tempHashMap, tempWebViewHashMap);
            } else {
                for (View view : WindowHelper.getSortedWindowViews()) {
                    traverseNode(view, tempSparseArray, tempHashMap, tempWebViewHashMap);
                }
            }
            this.mViewNodesHashMap = tempHashMap;
            this.mViewNodesWithHashCode = tempSparseArray;
            this.mWebViewHashMap = tempWebViewHashMap;
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public List<View> getCurrentWebView() {
        try {
            if (this.mWebViewHashMap.size() == 0) {
                traverseNode();
            }
            if (this.mWebViewHashMap.size() <= 0) {
                return null;
            }
            List<View> list = new ArrayList<>();
            for (ViewNode viewNode : this.mWebViewHashMap.values()) {
                WeakReference<View> reference = viewNode.getView();
                if (!(reference == null || reference.get() == null)) {
                    list.add(reference.get());
                }
            }
            return list;
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return null;
        }
    }

    private String generateKey(String elementPath, String elementPosition, String screenName) {
        StringBuilder key = new StringBuilder();
        key.append(elementPath);
        if (!TextUtils.isEmpty(elementPosition)) {
            key.append(elementPosition);
        }
        if (!TextUtils.isEmpty(screenName)) {
            key.append(screenName);
        }
        return key.toString();
    }

    private void traverseNode(View view, SparseArray<ViewNode> sparseArray, HashMap<String, ViewNode> hashMap, HashMap<String, ViewNode> webViewHashMap) {
        JSONObject jsonObject;
        if (view != null) {
            try {
                ViewNode viewNode = getCacheViewPathAndPosition(view, true);
                if (viewNode != null) {
                    sparseArray.put(view.hashCode(), viewNode);
                    if (!TextUtils.isEmpty(viewNode.getViewPath()) && (jsonObject = VisualUtil.getScreenNameAndTitle(view, (SnapInfo) null)) != null) {
                        String screenName = jsonObject.optString(AopConstants.SCREEN_NAME);
                        if (!TextUtils.isEmpty(screenName)) {
                            if (!TextUtils.isEmpty(viewNode.getViewContent())) {
                                hashMap.put(generateKey(viewNode.getViewPath(), viewNode.getViewPosition(), screenName), viewNode);
                            }
                            if (ViewUtil.instanceOfWebView(view)) {
                                webViewHashMap.put(viewNode.getViewPath() + screenName, viewNode);
                            }
                        }
                    }
                }
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    int childCount = group.getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = group.getChildAt(i);
                        if (child != null) {
                            traverseNode(child, sparseArray, hashMap, webViewHashMap);
                        }
                    }
                }
            } catch (Exception e) {
                SALog.printStackTrace(e);
            }
        }
    }

    public ViewNode getViewPathAndPosition(View clickView) {
        return getCacheViewPathAndPosition(clickView, false);
    }

    private ViewNode getCacheViewPathAndPosition(View clickView, boolean fromVisual) {
        View parent_view;
        ViewNode currentNode;
        ViewNode parentNode;
        int replacePosition;
        View view = clickView;
        boolean z = fromVisual;
        ViewNode currentNode2 = this.mViewNodesWithHashCode.get(clickView.hashCode());
        if (currentNode2 != null) {
            return currentNode2;
        }
        ViewParent viewParent = clickView.getParent();
        if (viewParent instanceof ViewGroup) {
            parent_view = (View) viewParent;
        } else {
            parent_view = null;
        }
        if (parent_view == null) {
            currentNode = ViewUtil.getViewPathAndPosition(clickView, fromVisual);
        } else {
            StringBuilder opx = new StringBuilder();
            StringBuilder px = new StringBuilder();
            ViewNode parentNode2 = this.mViewNodesWithHashCode.get(parent_view.hashCode());
            if (parentNode2 == null) {
                ViewNode parentNode3 = ViewUtil.getViewPathAndPosition(parent_view, z);
                this.mViewNodesWithHashCode.put(parent_view.hashCode(), parentNode3);
                parentNode = parentNode3;
            } else {
                parentNode = parentNode2;
            }
            opx.append(parentNode.getViewOriginalPath());
            px.append(parentNode.getViewPath());
            ViewNode currentNode3 = ViewUtil.getViewNode(view, ((ViewGroup) parent_view).indexOfChild(view), z);
            String listPosition = parentNode.getViewPosition();
            if (!TextUtils.isEmpty(currentNode3.getViewPath()) && currentNode3.getViewPath().contains("-") && !TextUtils.isEmpty(listPosition) && (replacePosition = px.lastIndexOf("-")) != -1) {
                px.replace(replacePosition, replacePosition + 1, String.valueOf(listPosition));
            }
            opx.append(currentNode3.getViewOriginalPath());
            px.append(currentNode3.getViewPath());
            currentNode = new ViewNode(clickView, currentNode3.getViewPosition(), opx.toString(), px.toString(), currentNode3.getViewContent());
        }
        this.mViewNodesWithHashCode.put(clickView.hashCode(), currentNode);
        return currentNode;
    }
}
