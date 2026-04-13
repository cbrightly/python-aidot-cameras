package me.jessyan.autosize.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.jessyan.autosize.utils.Preconditions;

public class ExternalAdaptManager {
    private boolean isRun;
    private List<String> mCancelAdaptList;
    private Map<String, ExternalAdaptInfo> mExternalAdaptInfos;

    public synchronized ExternalAdaptManager addCancelAdaptOfActivity(Class<?> targetClass) {
        Preconditions.checkNotNull(targetClass, "targetClass == null");
        if (!this.isRun) {
            this.isRun = true;
        }
        if (this.mCancelAdaptList == null) {
            this.mCancelAdaptList = new ArrayList();
        }
        this.mCancelAdaptList.add(targetClass.getCanonicalName());
        return this;
    }

    public synchronized ExternalAdaptManager addExternalAdaptInfoOfActivity(Class<?> targetClass, ExternalAdaptInfo info) {
        Preconditions.checkNotNull(targetClass, "targetClass == null");
        if (!this.isRun) {
            this.isRun = true;
        }
        if (this.mExternalAdaptInfos == null) {
            this.mExternalAdaptInfos = new HashMap(16);
        }
        this.mExternalAdaptInfos.put(targetClass.getCanonicalName(), info);
        return this;
    }

    public synchronized boolean isCancelAdapt(Class<?> targetClass) {
        Preconditions.checkNotNull(targetClass, "targetClass == null");
        List<String> list = this.mCancelAdaptList;
        if (list == null) {
            return false;
        }
        return list.contains(targetClass.getCanonicalName());
    }

    public synchronized ExternalAdaptInfo getExternalAdaptInfoOfActivity(Class<?> targetClass) {
        Preconditions.checkNotNull(targetClass, "targetClass == null");
        Map<String, ExternalAdaptInfo> map = this.mExternalAdaptInfos;
        if (map == null) {
            return null;
        }
        return map.get(targetClass.getCanonicalName());
    }

    public boolean isRun() {
        return this.isRun;
    }

    public ExternalAdaptManager setRun(boolean run) {
        this.isRun = run;
        return this;
    }
}
