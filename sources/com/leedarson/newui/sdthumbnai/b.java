package com.leedarson.newui.sdthumbnai;

import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.collections.q;
import kotlin.collections.y;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ThumbnaiTransformTaskManager.kt */
public final class b {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 3;
    @NotNull
    private final ArrayList<TaskItemBean> b = new ArrayList<>();
    @Nullable
    private a c;
    @NotNull
    private final String d = "ThumbnaiTask";

    /* compiled from: ThumbnaiTransformTaskManager.kt */
    public interface a {
        void a(@NotNull List<Long> list);
    }

    private final void c(TaskItemBean taskItem) {
        T t;
        TaskItemBean it;
        if (!PatchProxy.proxy(new Object[]{taskItem}, this, changeQuickRedirect, false, 4664, new Class[]{TaskItemBean.class}, Void.TYPE).isSupported) {
            Iterator<T> it2 = this.b.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    t = null;
                    break;
                }
                t = it2.next();
                if (((TaskItemBean) t).getTimeSpan() == taskItem.getTimeSpan()) {
                    it = 1;
                    continue;
                } else {
                    it = null;
                    continue;
                }
                if (it != null) {
                    break;
                }
            }
            TaskItemBean oldTaskItem = (TaskItemBean) t;
            if (oldTaskItem != null) {
                oldTaskItem.setQueryTime(System.currentTimeMillis());
            } else {
                this.b.add(taskItem);
            }
        }
    }

    public final void d(@NotNull List<Long> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 4665, new Class[]{List.class}, Void.TYPE).isSupported) {
            List timesList = list;
            k.e(timesList, "timesList");
            int index = 0;
            for (T next : timesList) {
                int index$iv = index + 1;
                if (index < 0) {
                    q.q();
                }
                long l = ((Number) next).longValue();
                a(k.l("收到下载任务请求 -- 正在进行任务限流派发ing ", Long.valueOf(l)));
                List timesList2 = timesList;
                TaskItemBean taskItemBean = r9;
                TaskItemBean taskItemBean2 = new TaskItemBean(l, false, System.currentTimeMillis(), a.IDLE);
                c(taskItemBean);
                timesList = timesList2;
                index = index$iv;
            }
            g();
        }
    }

    public final void f(long timeSpan) {
        TaskItemBean it;
        TaskItemBean it2;
        if (!PatchProxy.proxy(new Object[]{new Long(timeSpan)}, this, changeQuickRedirect, false, 4666, new Class[]{Long.TYPE}, Void.TYPE).isSupported) {
            a(k.l("收到下载成功回执  timeSpan=", Long.valueOf(timeSpan)));
            List $this$lastOrNull$iv = this.b;
            ListIterator iterator$iv = $this$lastOrNull$iv.listIterator($this$lastOrNull$iv.size());
            while (true) {
                if (!iterator$iv.hasPrevious()) {
                    it = null;
                    break;
                }
                it = iterator$iv.previous();
                if (it.getTimeSpan() == timeSpan) {
                    it2 = 1;
                    continue;
                } else {
                    it2 = null;
                    continue;
                }
                if (it2 != null) {
                    break;
                }
            }
            TaskItemBean taskItemBean = it;
            if (taskItemBean != null) {
                TaskItemBean it3 = taskItemBean;
                it3.setCached(true);
                it3.setTaskStatue(a.IDLE);
            }
            g();
        }
    }

    public final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4667, new Class[0], Void.TYPE).isSupported) {
            a("通道发生断开 - 下载中状态全部标记为IDLE");
            Iterable $this$filterTo$iv$iv = this.b;
            ArrayList $this$forEachIndexed$iv = new ArrayList();
            for (T next : $this$filterTo$iv$iv) {
                if (((TaskItemBean) next).getTaskStatue() == a.RUNNING) {
                    $this$forEachIndexed$iv.add(next);
                }
            }
            int index = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                int index$iv = index + 1;
                if (index < 0) {
                    q.q();
                }
                ((TaskItemBean) item$iv).setTaskStatue(a.IDLE);
                index = index$iv;
            }
        }
    }

    private final void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4668, new Class[0], Void.TYPE).isSupported) {
            Iterable $this$filterTo$iv$iv = this.b;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = $this$filterTo$iv$iv.iterator();
            while (true) {
                boolean z = true;
                if (!it.hasNext()) {
                    break;
                }
                T next = it.next();
                if (((TaskItemBean) next).getTaskStatue() != a.RUNNING) {
                    z = false;
                }
                if (z) {
                    arrayList.add(next);
                }
            }
            int freeTaskCount = this.a - arrayList.size();
            if (freeTaskCount > 0) {
                Iterable $this$filterTo$iv$iv2 = this.b;
                ArrayList $this$sortedByDescending$iv = new ArrayList();
                for (T next2 : $this$filterTo$iv$iv2) {
                    TaskItemBean it2 = (TaskItemBean) next2;
                    if (((it2.isCached() || it2.getTaskStatue() != a.IDLE) ? null : 1) != null) {
                        $this$sortedByDescending$iv.add(next2);
                    }
                }
                List pickTargetTasks = y.w0(y.u0($this$sortedByDescending$iv, new C0117b()), freeTaskCount);
                if (!pickTargetTasks.isEmpty()) {
                    ArrayList timeList = new ArrayList();
                    int index = 0;
                    for (Object item$iv : pickTargetTasks) {
                        int index$iv = index + 1;
                        if (index < 0) {
                            q.q();
                        }
                        TaskItemBean taskItemBean = (TaskItemBean) item$iv;
                        taskItemBean.setTaskStatue(a.RUNNING);
                        timeList.add(Long.valueOf(taskItemBean.getTimeSpan()));
                        a(k.l("开始准备向通道发送下载请求 timeSpan=", Long.valueOf(taskItemBean.getTimeSpan())));
                        index = index$iv;
                    }
                    a aVar = this.c;
                    if (aVar != null) {
                        if (aVar != null) {
                            aVar.a(timeList);
                        }
                        a("通知业务层可以执行下载  -- ");
                        return;
                    }
                    b("业务层感知下载发现 -handler ==null ");
                    return;
                }
                a("当前没有待下载的任务");
                return;
            }
            a(k.l("当前下载并发数已拉满，没有空余的并发下载数  当前-->", Integer.valueOf(freeTaskCount)));
        }
    }

    public final void i(@NotNull a handler) {
        if (!PatchProxy.proxy(new Object[]{handler}, this, changeQuickRedirect, false, 4669, new Class[]{a.class}, Void.TYPE).isSupported) {
            k.e(handler, "handler");
            a("设置拥赛控制层 - 下载接口");
            this.c = handler;
        }
    }

    private final void a(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4670, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.d).m(k.l(message, "  ThumbnaiTask"), new Object[0]);
        }
    }

    private final void b(String message) {
        if (!PatchProxy.proxy(new Object[]{message}, this, changeQuickRedirect, false, 4671, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g(this.d).c(k.l(message, " ThumbnaiTask"), new Object[0]);
        }
    }

    public final void h() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4672, new Class[0], Void.TYPE).isSupported) {
            this.c = null;
            try {
                this.b.clear();
            } catch (Exception e) {
                b(k.l("释放下载任务异常  e=", e));
            }
        }
    }

    /* renamed from: com.leedarson.newui.sdthumbnai.b$b  reason: collision with other inner class name */
    /* compiled from: Comparisons.kt */
    public static final class C0117b<T> implements Comparator {
        public static ChangeQuickRedirect changeQuickRedirect;

        public final int compare(T a, T b) {
            Class<Object> cls = Object.class;
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{a, b}, this, changeQuickRedirect2, false, 4673, new Class[]{cls, cls}, Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            return kotlin.comparisons.a.c(Long.valueOf(((TaskItemBean) b).getQueryTime()), Long.valueOf(((TaskItemBean) a).getQueryTime()));
        }
    }
}
