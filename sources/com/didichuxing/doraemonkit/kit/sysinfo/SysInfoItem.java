package com.didichuxing.doraemonkit.kit.sysinfo;

public class SysInfoItem {
    public boolean isPermission;
    public final String name;
    public final String value;

    public SysInfoItem(String name2, String value2) {
        this.name = name2;
        this.value = value2;
    }

    public SysInfoItem(String name2, String value2, boolean isPermission2) {
        this.name = name2;
        this.value = value2;
        this.isPermission = isPermission2;
    }
}
