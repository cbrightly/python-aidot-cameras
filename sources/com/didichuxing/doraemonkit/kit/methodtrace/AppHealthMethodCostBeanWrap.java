package com.didichuxing.doraemonkit.kit.methodtrace;

import java.util.List;

public class AppHealthMethodCostBeanWrap {
    private List<AppHealthMethodCostBean> data;
    private String title;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title2) {
        this.title = title2;
    }

    public List<AppHealthMethodCostBean> getData() {
        return this.data;
    }

    public void setData(List<AppHealthMethodCostBean> data2) {
        this.data = data2;
    }

    public String toString() {
        return "AppHealthMethodCostBeanWrap{trace='" + this.title + '\'' + ", data=" + this.data + '}';
    }
}
