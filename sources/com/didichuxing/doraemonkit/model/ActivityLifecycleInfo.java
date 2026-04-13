package com.didichuxing.doraemonkit.model;

public class ActivityLifecycleInfo {
    public static final int ACTIVITY_LIFECYCLE_CREATE2RESUME = 1;
    private int activityLifeCycleCount = 0;
    private String activityName;
    private boolean invokeStopMethod = false;

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName2) {
        this.activityName = activityName2;
    }

    public int getActivityLifeCycleCount() {
        return this.activityLifeCycleCount;
    }

    public void setActivityLifeCycleCount(int activityLifeCycleCount2) {
        this.activityLifeCycleCount = activityLifeCycleCount2;
    }

    public boolean isInvokeStopMethod() {
        return this.invokeStopMethod;
    }

    public void setInvokeStopMethod(boolean invokeStopMethod2) {
        this.invokeStopMethod = invokeStopMethod2;
    }
}
