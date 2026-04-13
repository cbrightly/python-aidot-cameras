package com.didichuxing.doraemonkit.kit.methodtrace;

public class AppHealthMethodCostBean {
    private String costTime = "0";
    private String functionName;

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName2) {
        this.functionName = functionName2;
    }

    public String getCostTime() {
        return this.costTime;
    }

    public void setCostTime(String costTime2) {
        this.costTime = costTime2;
    }

    public String toString() {
        return "AppHealthMethodCostBean{functionName='" + this.functionName + '\'' + ", costTime='" + this.costTime + '\'' + '}';
    }
}
