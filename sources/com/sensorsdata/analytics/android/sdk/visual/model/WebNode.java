package com.sensorsdata.analytics.android.sdk.visual.model;

import java.io.Serializable;
import java.util.List;

public class WebNode implements Serializable {
    private static final long serialVersionUID = -5865016149609340219L;
    private String $element_content;
    private String $element_path;
    private String $element_position;
    private String $element_selector;
    private String $title;
    private String $url;
    private boolean enable_click;
    private float height;
    private String id;
    private boolean isRootView;
    private boolean is_list_view;
    private float left;
    private int level;
    private String lib_version;
    private String list_selector;
    private float originLeft;
    private float originTop;
    private float scale;
    private float scrollX;
    private float scrollY;
    private List<String> subelements;
    private String tagName;
    private float top;
    private boolean visibility;
    private float width;
    private int zIndex;

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName2) {
        this.tagName = tagName2;
    }

    public String get$element_selector() {
        return this.$element_selector;
    }

    public void set$element_selector(String $element_selector2) {
        this.$element_selector = $element_selector2;
    }

    public String get$element_content() {
        return this.$element_content;
    }

    public void set$element_content(String $element_content2) {
        this.$element_content = $element_content2;
    }

    public String get$element_path() {
        return this.$element_path;
    }

    public void set$element_path(String $element_path2) {
        this.$element_path = $element_path2;
    }

    public String get$element_position() {
        return this.$element_position;
    }

    public void set$element_position(String $element_position2) {
        this.$element_position = $element_position2;
    }

    public String getList_selector() {
        return this.list_selector;
    }

    public void setList_selector(String list_selector2) {
        this.list_selector = list_selector2;
    }

    public String getLib_version() {
        return this.lib_version;
    }

    public void setLib_version(String lib_version2) {
        this.lib_version = lib_version2;
    }

    public boolean isEnable_click() {
        return this.enable_click;
    }

    public void setEnable_click(boolean enable_click2) {
        this.enable_click = enable_click2;
    }

    public boolean isIs_list_view() {
        return this.is_list_view;
    }

    public void setIs_list_view(boolean is_list_view2) {
        this.is_list_view = is_list_view2;
    }

    public String get$title() {
        return this.$title;
    }

    public void set$title(String $title2) {
        this.$title = $title2;
    }

    public float getTop() {
        return this.top;
    }

    public void setTop(float top2) {
        this.top = top2;
    }

    public float getLeft() {
        return this.left;
    }

    public void setLeft(float left2) {
        this.left = left2;
    }

    public float getWidth() {
        return this.width;
    }

    public void setWidth(float width2) {
        this.width = width2;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height2) {
        this.height = height2;
    }

    public boolean isVisibility() {
        return this.visibility;
    }

    public void setVisibility(boolean visibility2) {
        this.visibility = visibility2;
    }

    public float getScrollX() {
        return this.scrollX;
    }

    public void setScrollX(float scrollX2) {
        this.scrollX = scrollX2;
    }

    public float getScrollY() {
        return this.scrollY;
    }

    public void setScrollY(float scrollY2) {
        this.scrollY = scrollY2;
    }

    public float getScale() {
        return this.scale;
    }

    public void setScale(float scale2) {
        this.scale = scale2;
    }

    public String get$url() {
        return this.$url;
    }

    public void set$url(String $url2) {
        this.$url = $url2;
    }

    public int getzIndex() {
        return this.zIndex;
    }

    public void setzIndex(int zIndex2) {
        this.zIndex = zIndex2;
    }

    public List<String> getSubelements() {
        return this.subelements;
    }

    public void setSubelements(List<String> subelements2) {
        this.subelements = subelements2;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level2) {
        this.level = level2;
    }

    public boolean isRootView() {
        return this.isRootView;
    }

    public void setRootView(boolean rootView) {
        this.isRootView = rootView;
    }

    public float getOriginTop() {
        return this.originTop;
    }

    public void setOriginTop(float originTop2) {
        this.originTop = originTop2;
    }

    public float getOriginLeft() {
        return this.originLeft;
    }

    public void setOriginLeft(float originLeft2) {
        this.originLeft = originLeft2;
    }
}
