package com.sensorsdata.analytics.android.sdk.visual.model;

import android.view.View;
import java.io.Serializable;
import java.lang.ref.WeakReference;

public class ViewNode implements Serializable {
    private static final long serialVersionUID = -1242947408632673572L;
    private boolean isListView;
    private WeakReference<View> view;
    private String viewContent;
    private String viewOriginalPath;
    private String viewPath;
    private String viewPosition;
    private String viewType;

    public ViewNode(String viewContent2, String viewType2) {
        this((View) null, (String) null, (String) null, (String) null, viewContent2, viewType2, false);
    }

    public ViewNode(View view2, String viewPosition2, String viewOriginalPath2, String viewPath2, String elementContent) {
        this(view2, viewPosition2, viewOriginalPath2, viewPath2, elementContent, (String) null, false);
    }

    public ViewNode(View view2, String viewPosition2, String viewOriginalPath2, String viewPath2, String viewContent2, String viewType2, boolean isListView2) {
        this.view = new WeakReference<>(view2);
        this.viewPosition = viewPosition2;
        this.viewOriginalPath = viewOriginalPath2;
        this.viewPath = viewPath2;
        this.viewContent = viewContent2;
        this.viewType = viewType2;
        this.isListView = isListView2;
    }

    public String getViewPosition() {
        return this.viewPosition;
    }

    public void setViewPosition(String viewPosition2) {
        this.viewPosition = viewPosition2;
    }

    public String getViewOriginalPath() {
        return this.viewOriginalPath;
    }

    public void setViewOriginalPath(String viewOriginalPath2) {
        this.viewOriginalPath = viewOriginalPath2;
    }

    public String getViewPath() {
        return this.viewPath;
    }

    public void setViewPath(String viewPath2) {
        this.viewPath = viewPath2;
    }

    public String getViewContent() {
        return this.viewContent;
    }

    public void setViewContent(String viewContent2) {
        this.viewContent = viewContent2;
    }

    public String getViewType() {
        return this.viewType;
    }

    public void setViewType(String viewType2) {
        this.viewType = viewType2;
    }

    public boolean isListView() {
        return this.isListView;
    }

    public void setListView(boolean listView) {
        this.isListView = listView;
    }

    public WeakReference<View> getView() {
        return this.view;
    }
}
