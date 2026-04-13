package com.leedarson.newui.pages.repos;

import com.leedarson.R$string;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.SDRecord;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.smartcamera.kvswebrtc.f0;
import com.leedarson.smartcamera.listener.d;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.e;
import io.reactivex.f;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import kotlin.collections.y;
import kotlin.jvm.internal.d0;
import kotlin.jvm.internal.k;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: DeleteEventRepos.kt */
public final class h {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private final io.reactivex.processors.b<Boolean> a;
    @NotNull
    private final io.reactivex.processors.b<Boolean> b;
    @NotNull
    private final io.reactivex.processors.b<String> c;
    @NotNull
    private final io.reactivex.processors.b<ArrayList<SDRecord>> d;
    @NotNull
    private final io.reactivex.processors.b<Long> e;
    private boolean f;
    private final int g = 50;
    @Nullable
    private com.leedarson.smartcamera.sdk.a h;
    @Nullable
    private f0 i;
    /* access modifiers changed from: private */
    @Nullable
    public SDCardEditRequestParamBean j;
    private int k;
    private int l;
    @NotNull
    private HashMap<Integer, ArrayList<SDRecord>> m = new HashMap<>();
    @NotNull
    private io.reactivex.processors.b<String> n;
    private int o;
    @NotNull
    private final d p;

    public h() {
        io.reactivex.processors.b<Boolean> Y = io.reactivex.processors.b.Y();
        k.d(Y, "create<Boolean>()");
        this.a = Y;
        io.reactivex.processors.b<Boolean> Y2 = io.reactivex.processors.b.Y();
        k.d(Y2, "create<Boolean>()");
        this.b = Y2;
        io.reactivex.processors.b<String> Y3 = io.reactivex.processors.b.Y();
        k.d(Y3, "create<String>()");
        this.c = Y3;
        io.reactivex.processors.b<ArrayList<SDRecord>> Y4 = io.reactivex.processors.b.Y();
        k.d(Y4, "create<ArrayList<SDRecord>>()");
        this.d = Y4;
        io.reactivex.processors.b<Long> Y5 = io.reactivex.processors.b.Y();
        k.d(Y5, "create<Long>()");
        this.e = Y5;
        io.reactivex.processors.b<String> Y6 = io.reactivex.processors.b.Y();
        k.d(Y6, "create<String>()");
        this.n = Y6;
        this.p = new b(this);
    }

    public static final /* synthetic */ void i(h $this, String msg) {
        Class[] clsArr = {h.class, String.class};
        if (!PatchProxy.proxy(new Object[]{$this, msg}, (Object) null, changeQuickRedirect, true, 4333, clsArr, Void.TYPE).isSupported) {
            $this.b(msg);
        }
    }

    @NotNull
    public final io.reactivex.processors.b<Boolean> p() {
        return this.a;
    }

    @NotNull
    public final io.reactivex.processors.b<Boolean> n() {
        return this.b;
    }

    @NotNull
    public final io.reactivex.processors.b<String> o() {
        return this.c;
    }

    @NotNull
    public final io.reactivex.processors.b<ArrayList<SDRecord>> q() {
        return this.d;
    }

    @NotNull
    public final io.reactivex.processors.b<Long> r() {
        return this.e;
    }

    public final int m() {
        return this.g;
    }

    @NotNull
    public final io.reactivex.processors.b<String> u() {
        return this.n;
    }

    public final void y(@NotNull SDCardEditRequestParamBean pgData) {
        f0 f0Var;
        if (!PatchProxy.proxy(new Object[]{pgData}, this, changeQuickRedirect, false, 4321, new Class[]{SDCardEditRequestParamBean.class}, Void.TYPE).isSupported) {
            k.e(pgData, "pgData");
            this.j = pgData;
            if (pgData.isWebRTC()) {
                if (pgData.isSupportPreCon()) {
                    f0Var = com.leedarson.manager.a.i().j(pgData.getDeviceId());
                } else {
                    f0Var = com.leedarson.manager.a.i().j(k.l(pgData.getDeviceId(), "-SD"));
                }
                this.i = f0Var;
                return;
            }
            this.h = com.leedarson.manager.a.i().m(pgData.getDeviceId());
        }
    }

    public final void k(@NotNull ArrayList<SDRecord> arrayList) {
        if (!PatchProxy.proxy(new Object[]{arrayList}, this, changeQuickRedirect, false, 4322, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            Iterable $this$filter$iv = arrayList;
            k.e($this$filter$iv, "itemDataList");
            ArrayList<SDRecord> neeToDeleteItems = new ArrayList<>();
            ArrayList $this$actualDeleteData_u24lambda_u2d1 = neeToDeleteItems;
            Collection destination$iv$iv = new ArrayList();
            for (T next : $this$filter$iv) {
                SDRecord item = (SDRecord) next;
                if (item.isCheck() && item.itemType == 0) {
                    destination$iv$iv.add(next);
                }
            }
            $this$actualDeleteData_u24lambda_u2d1.addAll(destination$iv$iv);
            ArrayList _delRecords = new ArrayList();
            for (SDRecord it : neeToDeleteItems) {
                _delRecords.add(Long.valueOf(it.getTimestamp()));
            }
            this.o = _delRecords.size();
            this.m.clear();
            this.b.onNext(true);
            this.k = (int) ((float) Math.ceil((double) ((((float) neeToDeleteItems.size()) * 1.0f) / ((float) this.g))));
            this.l = 0;
            int size = neeToDeleteItems.size();
            if (size > 0) {
                int i2 = 0;
                do {
                    int i3 = i2;
                    i2++;
                    if (this.m.get(Integer.valueOf(i3 / this.g)) == null) {
                        HashMap<Integer, ArrayList<SDRecord>> hashMap = this.m;
                        Integer valueOf = Integer.valueOf(i3 / this.g);
                        ArrayList $this$actualDeleteData_u24lambda_u2d3 = new ArrayList();
                        $this$actualDeleteData_u24lambda_u2d3.add(neeToDeleteItems.get(i3));
                        hashMap.put(valueOf, $this$actualDeleteData_u24lambda_u2d3);
                        continue;
                    } else {
                        ArrayList arrayList2 = this.m.get(Integer.valueOf(i3 / this.g));
                        if (arrayList2 == null) {
                            continue;
                        } else {
                            arrayList2.add(neeToDeleteItems.get(i3));
                            continue;
                        }
                    }
                } while (i2 < size);
            }
            this.f = true;
            e();
        }
    }

    public final void z() {
        this.f = false;
    }

    private final void e() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4323, new Class[0], Void.TYPE).isSupported) {
            if (!this.f) {
                this.b.onNext(false);
                return;
            }
            ArrayList _currentPagesData = this.m.get(Integer.valueOf(this.l));
            if (_currentPagesData != null) {
                c(_currentPagesData).P(60000, TimeUnit.MILLISECONDS).I(new b(this), new a(this));
            }
        }
    }

    /* access modifiers changed from: private */
    public static final void f(h this$0, ArrayList it) {
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4330, new Class[]{h.class, ArrayList.class}, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.q().onNext(it);
            this$0.l++;
            io.reactivex.processors.b<String> u = this$0.u();
            d0 d0Var = d0.a;
            Locale locale = Locale.US;
            String string = PubUtils.getString(BaseApplication.b(), R$string.lds_deleting);
            k.d(string, "getString(BaseApplication.getApp(), R.string.lds_deleting)");
            String format = String.format(locale, string, Arrays.copyOf(new Object[]{this$0.s(this$0.l * this$0.m(), this$0.o)}, 1));
            k.d(format, "format(locale, format, *args)");
            u.onNext(format);
            if (this$0.l < this$0.k) {
                this$0.e();
                return;
            }
            this$0.n().onNext(false);
            this$0.p().onNext(false);
            this$0.o().onNext(PubUtils.getString(BaseApplication.b(), R$string.delete_success));
        }
    }

    /* access modifiers changed from: private */
    public static final void g(h this$0, Throwable it) {
        Class[] clsArr = {h.class, Throwable.class};
        if (!PatchProxy.proxy(new Object[]{this$0, it}, (Object) null, changeQuickRedirect, true, 4331, clsArr, Void.TYPE).isSupported) {
            k.e(this$0, "this$0");
            this$0.a(k.l("分页删除 删除发生异常 ", it));
            this$0.u().onNext(PubUtils.getString(BaseApplication.b(), R$string.timeout));
            this$0.o().onNext(PubUtils.getString(BaseApplication.b(), R$string.delete_failed));
            this$0.n().onNext(false);
            this$0.p().onNext(false);
        }
    }

    private final e<ArrayList<SDRecord>> c(ArrayList<SDRecord> itemDataList) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{itemDataList}, this, changeQuickRedirect, false, 4324, new Class[]{ArrayList.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        e<ArrayList<SDRecord>> d2 = e.d(new c(itemDataList, this), io.reactivex.a.DROP);
        k.d(d2, "create({ emitter ->\n            val neeToDeleteItems = itemDataList.filter { item ->\n                item.isCheck && item.itemType == SDRecord.itemType_Event\n            }\n            val _delRecords = ArrayList<Long>()\n            neeToDeleteItems.forEach {\n                _delRecords.add(it.timestamp)\n                //I(\"分页删除  -- 正在删除 -----> \" + DateUtil.stampToDate(it.timestamp, \"yyyy-MM-dd HH:mm:ss\") + \",currentPageIndex=\" + currentGroupIndex + \", timestamp=\" + it.timestamp)\n            }\n            val deleteRecordRespListerner = object : DeleteRecordRespListener {\n                override fun onSuccess() {\n                    emitter.onNext(itemDataList)\n                    emitter.onComplete()\n                }\n\n                override fun onError(code: Int) {\n                    emitter.onError(Throwable(\"分页删除失败删除失败....\"))\n                }\n            }\n            _pageRequestParams?.apply {\n                if (isWebRTC) {\n                    sdWebRTCChannel?.deleteRecord(0, eventType, _delRecords, deleteRecordRespListerner)\n                } else {\n                    tutkChannel?.deleteRecord(0, eventType, _delRecords, deleteRecordRespListerner)\n                }\n            }\n        }, BackpressureStrategy.DROP)");
        return d2;
    }

    /* access modifiers changed from: private */
    public static final void d(ArrayList $itemDataList, h this$0, f emitter) {
        if (!PatchProxy.proxy(new Object[]{$itemDataList, this$0, emitter}, (Object) null, changeQuickRedirect, true, 4332, new Class[]{ArrayList.class, h.class, f.class}, Void.TYPE).isSupported) {
            k.e($itemDataList, "$itemDataList");
            k.e(this$0, "this$0");
            k.e(emitter, "emitter");
            ArrayList<SDRecord> arrayList = new ArrayList<>();
            for (Object element$iv$iv : $itemDataList) {
                SDRecord item = (SDRecord) element$iv$iv;
                if (((!item.isCheck() || item.itemType != 0) ? null : 1) != null) {
                    arrayList.add(element$iv$iv);
                }
            }
            Iterable $this$filter$iv = arrayList;
            ArrayList _delRecords = new ArrayList();
            for (SDRecord it : arrayList) {
                _delRecords.add(Long.valueOf(it.getTimestamp()));
            }
            a deleteRecordRespListerner = new a(emitter, $itemDataList);
            SDCardEditRequestParamBean $this$_actualDeleteDatas_u24lambda_u2d10_u24lambda_u2d9 = this$0.j;
            if ($this$_actualDeleteDatas_u24lambda_u2d10_u24lambda_u2d9 != null) {
                if ($this$_actualDeleteDatas_u24lambda_u2d10_u24lambda_u2d9.isWebRTC()) {
                    f0 f0Var = this$0.i;
                    if (f0Var != null) {
                        f0Var.N0(0, $this$_actualDeleteDatas_u24lambda_u2d10_u24lambda_u2d9.getEventType(), _delRecords, deleteRecordRespListerner);
                        return;
                    }
                    return;
                }
                com.leedarson.smartcamera.sdk.a aVar = this$0.h;
                if (aVar != null) {
                    aVar.B0(0, $this$_actualDeleteDatas_u24lambda_u2d10_u24lambda_u2d9.getEventType(), _delRecords, deleteRecordRespListerner);
                }
            }
        }
    }

    /* compiled from: DeleteEventRepos.kt */
    public static final class a implements com.leedarson.smartcamera.listener.a {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ f<ArrayList<SDRecord>> a;
        final /* synthetic */ ArrayList<SDRecord> b;

        a(f<ArrayList<SDRecord>> $emitter, ArrayList<SDRecord> $itemDataList) {
            this.a = $emitter;
            this.b = $itemDataList;
        }

        public void onSuccess() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4335, new Class[0], Void.TYPE).isSupported) {
                this.a.onNext(this.b);
                this.a.onComplete();
            }
        }

        public void a(int i) {
            Object[] objArr = {new Integer(i)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4336, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                this.a.onError(new Throwable("分页删除失败删除失败...."));
            }
        }
    }

    private final String s(int number1, int number2) {
        Object[] objArr = {new Integer(number1), new Integer(number2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4325, new Class[]{cls, cls}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        DecimalFormat df = new DecimalFormat("#");
        if (number2 == 0) {
            return "0%";
        }
        double n2 = ((double) number1) / ((double) number2);
        if (n2 > 1.0d) {
            n2 = 1.0d;
        }
        return k.l(df.format((double) (((float) n2) * ((float) 100))), "%");
    }

    public final void t(@NotNull List<? extends SDRecord> dataItems) {
        if (!PatchProxy.proxy(new Object[]{dataItems}, this, changeQuickRedirect, false, 4326, new Class[]{List.class}, Void.TYPE).isSupported) {
            k.e(dataItems, "dataItems");
            List filterEventDatas = new ArrayList();
            for (T next : dataItems) {
                if ((((SDRecord) next).itemType == 0 ? 1 : null) != null) {
                    filterEventDatas.add(next);
                }
            }
            h(filterEventDatas);
        }
    }

    /* compiled from: DeleteEventRepos.kt */
    public static final class b implements d {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ h a;

        b(h $receiver) {
            this.a = $receiver;
        }

        public void c(int status) {
            Object[] objArr = {new Integer(status)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4337, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                h.i(this.a, k.l("###获取图片成功通道状态变化 .... status=", Integer.valueOf(status)));
            }
        }

        public void a(long time, @NotNull String path) {
            Object[] objArr = {new Long(time), path};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4338, new Class[]{Long.TYPE, String.class}, Void.TYPE).isSupported) {
                k.e(path, "path");
                h hVar = this.a;
                h.i(hVar, "###获取图片成功111 .... time=" + time + "  , time=" + com.leedarson.utils.e.j(time, "HH:mm:ss"));
                this.a.r().onNext(Long.valueOf(time));
            }
        }

        public void b(long time, @NotNull byte[] imgBytes) {
            Object[] objArr = {new Long(time), imgBytes};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4339, new Class[]{Long.TYPE, byte[].class}, Void.TYPE).isSupported) {
                k.e(imgBytes, "imgBytes");
                h hVar = this.a;
                h.i(hVar, "###获取图片成功222 .... time=" + time + "  , time=" + com.leedarson.utils.e.j(time, "HH:mm:ss"));
                SDCardEditRequestParamBean $this$onSuccess_u24lambda_u2d0 = this.a.j;
                if ($this$onSuccess_u24lambda_u2d0 != null) {
                    h hVar2 = this.a;
                    String eventStr = $this$onSuccess_u24lambda_u2d0.getEventStr();
                    String deviceId = $this$onSuccess_u24lambda_u2d0.getDeviceId();
                    boolean b = com.leedarson.smartcamera.utils.d.b(eventStr, deviceId, time + "");
                    String eventStr2 = $this$onSuccess_u24lambda_u2d0.getEventStr();
                    String deviceId2 = $this$onSuccess_u24lambda_u2d0.getDeviceId();
                    boolean h = com.leedarson.smartcamera.utils.d.h(eventStr2, deviceId2, time + "", imgBytes);
                    hVar2.r().onNext(Long.valueOf(time));
                }
            }
        }
    }

    @NotNull
    public final d l() {
        return this.p;
    }

    private final void h(List<? extends SDRecord> $this$filter$iv) {
        if (!PatchProxy.proxy(new Object[]{$this$filter$iv}, this, changeQuickRedirect, false, 4327, new Class[]{List.class}, Void.TYPE).isSupported) {
            List arrayList = new ArrayList();
            for (T next : $this$filter$iv) {
                if ((((SDRecord) next).itemType == 0 ? 1 : null) != null) {
                    arrayList.add(next);
                }
            }
            List filterEventDatas = arrayList;
            b(k.l("###获取图片-->预获取图片个数  filterEventDatas.size =", Integer.valueOf(filterEventDatas.size())));
            SDCardEditRequestParamBean $this$_getThumbnais_u24lambda_u2d15 = this.j;
            if ($this$_getThumbnais_u24lambda_u2d15 == null) {
                ArrayList arrayList2 = filterEventDatas;
                return;
            }
            String targetId = $this$_getThumbnais_u24lambda_u2d15.isWebRTC() ? $this$_getThumbnais_u24lambda_u2d15.getDeviceId() : $this$_getThumbnais_u24lambda_u2d15.getP2pId();
            ArrayList arrayList3 = new ArrayList();
            Iterable<SDRecord> $this$forEach$iv = filterEventDatas;
            int $i$f$forEach = false;
            for (SDRecord itemSdRecord : $this$forEach$iv) {
                List filterEventDatas2 = filterEventDatas;
                Iterable $this$forEach$iv2 = $this$forEach$iv;
                int $i$f$forEach2 = $i$f$forEach;
                long j2 = (long) 1000;
                if (!com.leedarson.smartcamera.utils.d.e($this$_getThumbnais_u24lambda_u2d15.getEventStr(), targetId, String.valueOf(itemSdRecord.getTimestamp() / j2))) {
                    b("###获取图片 - (筛选未缓存图片) .... time=" + itemSdRecord.getTimestamp() + "  , time=" + com.leedarson.utils.e.j(itemSdRecord.getTimestamp(), "HH:mm:ss"));
                    arrayList3.add(Long.valueOf(itemSdRecord.getTimestamp() / j2));
                }
                filterEventDatas = filterEventDatas2;
                $this$forEach$iv = $this$forEach$iv2;
                $i$f$forEach = $i$f$forEach2;
            }
            Iterable iterable = $this$forEach$iv;
            int i2 = $i$f$forEach;
            b("###获取图片 - (筛选未缓存图片) 共计收集到未缓存图片 " + arrayList3.size() + 24352);
            if ($this$_getThumbnais_u24lambda_u2d15.isWebRTC()) {
                f0 f0Var = this.i;
                if (f0Var != null) {
                    f0Var.n1(y.C0(arrayList3), l());
                    return;
                }
                return;
            }
            com.leedarson.smartcamera.sdk.a $this$_getThumbnais_u24lambda_u2d15_u24lambda_u2d14 = this.h;
            if ($this$_getThumbnais_u24lambda_u2d15_u24lambda_u2d14 != null && $this$_getThumbnais_u24lambda_u2d15_u24lambda_u2d14.a1()) {
                $this$_getThumbnais_u24lambda_u2d15_u24lambda_u2d14.T0($this$_getThumbnais_u24lambda_u2d15.getEventStr(), y.C0(arrayList3), l());
            }
        }
    }

    private final void b(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4328, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("DeleteEventRepos").h("msg  =%s", msg);
        }
    }

    private final void a(String msg) {
        if (!PatchProxy.proxy(new Object[]{msg}, this, changeQuickRedirect, false, 4329, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("DeleteEventRepos").h("msg  =%s", msg);
        }
    }
}
