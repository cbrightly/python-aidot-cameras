package com.meituan.robust;

public class PatchedClassInfo {
    public String patchClassName;
    public String patchedClassName;

    public PatchedClassInfo(String patchedClassName2, String patchClassName2) {
        this.patchedClassName = patchedClassName2;
        this.patchClassName = patchClassName2;
    }
}
