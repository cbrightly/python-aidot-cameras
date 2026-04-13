package com.didichuxing.doraemonkit.aop.method_stack;

import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: MethodInvokNode.kt */
public final class MethodInvokNode {
    @NotNull
    private List<MethodInvokNode> children = new ArrayList();
    @Nullable
    private String className;
    private int costTimeMillis;
    @Nullable
    private String currentThreadName;
    private long endTimeMillis;
    private int level;
    @Nullable
    private String methodName;
    @Nullable
    private MethodInvokNode parent;
    private long startTimeMillis;

    @Nullable
    public final MethodInvokNode getParent() {
        return this.parent;
    }

    public final void setParent(@Nullable MethodInvokNode methodInvokNode) {
        this.parent = methodInvokNode;
    }

    public final long getStartTimeMillis() {
        return this.startTimeMillis;
    }

    public final void setStartTimeMillis(long j) {
        this.startTimeMillis = j;
    }

    @Nullable
    public final String getCurrentThreadName() {
        return this.currentThreadName;
    }

    public final void setCurrentThreadName(@Nullable String str) {
        this.currentThreadName = str;
    }

    @Nullable
    public final String getClassName() {
        return this.className;
    }

    public final void setClassName(@Nullable String str) {
        this.className = str;
    }

    @Nullable
    public final String getMethodName() {
        return this.methodName;
    }

    public final void setMethodName(@Nullable String str) {
        this.methodName = str;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int i) {
        this.level = i;
    }

    @NotNull
    public final List<MethodInvokNode> getChildren() {
        return this.children;
    }

    public final void setChildren(@NotNull List<MethodInvokNode> list) {
        k.f(list, "<set-?>");
        this.children = list;
    }

    public final long getEndTimeMillis() {
        return this.endTimeMillis;
    }

    public final void setEndTimeMillis(long endTimeMillis2) {
        this.endTimeMillis = endTimeMillis2;
        this.costTimeMillis = (int) (endTimeMillis2 - this.startTimeMillis);
    }

    public final int getCostTimeMillis() {
        return (int) (this.endTimeMillis - this.startTimeMillis);
    }

    public final void addChild(@NotNull MethodInvokNode methodInvokNode) {
        k.f(methodInvokNode, "methodInvokNode");
        this.children.add(methodInvokNode);
    }

    public final void setCostTimeMillis(int costTimeMillis2) {
        this.costTimeMillis = costTimeMillis2;
    }
}
