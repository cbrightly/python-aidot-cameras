package com.leedarson.newui.sdthumbnai;

import com.google.chip.chiptool.setuppayloadscanner.a;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThumbnaiTransformTaskManager.kt */
public final class TaskItemBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean isCached;
    private long queryTime;
    @NotNull
    private a taskStatue;
    private final long timeSpan;

    public static /* synthetic */ TaskItemBean copy$default(TaskItemBean taskItemBean, long j, boolean z, long j2, a aVar, int i, Object obj) {
        TaskItemBean taskItemBean2 = taskItemBean;
        int i2 = i;
        long j3 = j;
        boolean z2 = z;
        long j4 = j2;
        Object[] objArr = {taskItemBean2, new Long(j3), new Byte(z2 ? (byte) 1 : 0), new Long(j4), aVar, new Integer(i2), obj};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 4659, new Class[]{TaskItemBean.class, cls, Boolean.TYPE, cls, a.class, Integer.TYPE, Object.class}, TaskItemBean.class);
        if (proxy.isSupported) {
            return (TaskItemBean) proxy.result;
        }
        if ((i2 & 1) != 0) {
            j3 = taskItemBean2.timeSpan;
        }
        if ((i2 & 2) != 0) {
            z2 = taskItemBean2.isCached;
        }
        if ((i2 & 4) != 0) {
            j4 = taskItemBean2.queryTime;
        }
        return taskItemBean.copy(j3, z2, j4, (i2 & 8) != 0 ? taskItemBean2.taskStatue : aVar);
    }

    public final long component1() {
        return this.timeSpan;
    }

    public final boolean component2() {
        return this.isCached;
    }

    public final long component3() {
        return this.queryTime;
    }

    @NotNull
    public final a component4() {
        return this.taskStatue;
    }

    @NotNull
    public final TaskItemBean copy(long j, boolean z, long j2, @NotNull a aVar) {
        Object[] objArr = {new Long(j), new Byte(z ? (byte) 1 : 0), new Long(j2), aVar};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Long.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4658, new Class[]{cls, Boolean.TYPE, cls, a.class}, TaskItemBean.class);
        if (proxy.isSupported) {
            return (TaskItemBean) proxy.result;
        }
        long j3 = j;
        boolean z2 = z;
        k.e(aVar, "taskStatue");
        return new TaskItemBean(j3, z2, j2, aVar);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TaskItemBean)) {
            return false;
        }
        TaskItemBean taskItemBean = (TaskItemBean) obj;
        return this.timeSpan == taskItemBean.timeSpan && this.isCached == taskItemBean.isCached && this.queryTime == taskItemBean.queryTime && this.taskStatue == taskItemBean.taskStatue;
    }

    public int hashCode() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4661, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        int a = a.a(this.timeSpan) * 31;
        boolean z = this.isCached;
        if (z) {
            z = true;
        }
        return ((((a + (z ? 1 : 0)) * 31) + a.a(this.queryTime)) * 31) + this.taskStatue.hashCode();
    }

    @NotNull
    public String toString() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4660, new Class[0], String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        return "TaskItemBean(timeSpan=" + this.timeSpan + ", isCached=" + this.isCached + ", queryTime=" + this.queryTime + ", taskStatue=" + this.taskStatue + ')';
    }

    public TaskItemBean(long timeSpan2, boolean isCached2, long queryTime2, @NotNull a taskStatue2) {
        k.e(taskStatue2, "taskStatue");
        this.timeSpan = timeSpan2;
        this.isCached = isCached2;
        this.queryTime = queryTime2;
        this.taskStatue = taskStatue2;
    }

    public final long getTimeSpan() {
        return this.timeSpan;
    }

    public final boolean isCached() {
        return this.isCached;
    }

    public final void setCached(boolean z) {
        this.isCached = z;
    }

    public final long getQueryTime() {
        return this.queryTime;
    }

    public final void setQueryTime(long j) {
        this.queryTime = j;
    }

    @NotNull
    public final a getTaskStatue() {
        return this.taskStatue;
    }

    public final void setTaskStatue(@NotNull a aVar) {
        if (!PatchProxy.proxy(new Object[]{aVar}, this, changeQuickRedirect, false, 4657, new Class[]{a.class}, Void.TYPE).isSupported) {
            k.e(aVar, "<set-?>");
            this.taskStatue = aVar;
        }
    }
}
