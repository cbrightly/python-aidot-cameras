package com.leedarson.newui.pages.repos;

import android.content.Intent;
import com.leedarson.bean.SDRecord;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import io.reactivex.a;
import io.reactivex.e;
import io.reactivex.f;
import io.reactivex.processors.b;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import kotlin.jvm.internal.k;
import kotlin.n;
import org.jetbrains.annotations.NotNull;

/* compiled from: ExtroIntentRepo.kt */
public final class i {
    public static ChangeQuickRedirect changeQuickRedirect;
    @NotNull
    private b<Integer> a;
    private final int b = 180;

    public i() {
        b<Integer> Y = b.Y();
        k.d(Y, "create<Int>()");
        this.a = Y;
    }

    @NotNull
    public final b<Integer> d() {
        return this.a;
    }

    @NotNull
    public final e<SDCardEditRequestParamBean> a(@NotNull Intent intent) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{intent}, this, changeQuickRedirect, false, 4342, new Class[]{Intent.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        k.e(intent, "intent");
        e<SDCardEditRequestParamBean> d = e.d(new d(intent), a.DROP);
        k.d(d, "create({ emitter ->\n            //emitter.onNext(SDCardEditRequestParamBean(null,null,null,null,0,false,false,Array<Long>(),0,0))\n            var deviceId: String? = null;\n            intent.hasExtra(\"deviceId\").apply {\n                if (this) {\n                    deviceId = intent.getStringExtra(\"deviceId\")\n                }\n            }\n\n            var selectedDate: String? = null\n            var startTime = 0L;\n            var endTime = 0L;\n\n            intent.hasExtra(\"selectedDate\").apply {\n                if (this) {\n                    selectedDate = intent.getStringExtra(\"selectedDate\")\n                    startTime = DateUtil.dateToTimeStamp(\"$selectedDate 00:00:00\", \"yyyy-MM-dd HH:mm:ss\")\n                    endTime = DateUtil.dateToTimeStamp(\"$selectedDate 23:59:59\", \"yyyy-MM-dd HH:mm:ss\")\n                }\n            }\n\n            var p2pId: String? = null\n            intent.hasExtra(\"p2pId\").apply {\n                if (this) {\n                    p2pId = intent.getStringExtra(\"p2pId\")\n                }\n            }\n\n            var eventStr: String? = null;\n            intent.hasExtra(\"eventStr\").apply {\n                if (this) {\n                    eventStr = intent.getStringExtra(\"eventStr\")\n                }\n            }\n\n            var eventType = 0;\n            intent.hasExtra(\"eventType\").apply {\n                if (this) {\n                    eventType = intent.getIntExtra(\"eventType\", 0)\n                }\n            }\n\n            var isWebRTC = false\n            intent.hasExtra(\"isWebRTC\").apply {\n                if (this) {\n                    isWebRTC = intent.getBooleanExtra(\"isWebRTC\", false)\n                }\n            }\n\n            var isSupportPreCon = false\n            intent.hasExtra(\"isSupportPreCon\").apply {\n                if (this) {\n                    isSupportPreCon = intent.getBooleanExtra(\"isSupportPreCon\", false)\n                }\n            }\n\n            var sdVideoList: List<Long>? = null;\n            intent.hasExtra(\"lists\").apply {\n                if (this) {\n                    sdVideoList = intent.getSerializableExtra(\"lists\") as List<Long>?\n                }\n            }\n\n            if (sdVideoList == null || sdVideoList?.size == 0) {\n                emitter.onError(Throwable(\"SD卡列表不存在...\"))\n            } else {\n                emitter.onNext(SDCardEditRequestParamBean(deviceId, selectedDate, p2pId,\n                        eventStr, eventType, isWebRTC, isSupportPreCon,\n                        sdVideoList, startTime, endTime))\n                emitter.onComplete()\n            }\n        }, BackpressureStrategy.DROP)");
        return d;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void b(android.content.Intent r29, io.reactivex.f r30) {
        /*
            r0 = 2
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r29
            r9 = 1
            r1[r9] = r30
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<android.content.Intent> r0 = android.content.Intent.class
            r6[r8] = r0
            java.lang.Class<io.reactivex.f> r0 = io.reactivex.f.class
            r6[r9] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r5 = 4348(0x10fc, float:6.093E-42)
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0024
            return
        L_0x0024:
            r0 = r29
            r1 = r30
            java.lang.String r2 = "$intent"
            kotlin.jvm.internal.k.e(r0, r2)
            java.lang.String r2 = "emitter"
            kotlin.jvm.internal.k.e(r1, r2)
            r2 = 0
            java.lang.String r3 = "deviceId"
            boolean r4 = r0.hasExtra(r3)
            r5 = 0
            if (r4 == 0) goto L_0x0040
            java.lang.String r2 = r0.getStringExtra(r3)
        L_0x0040:
            r3 = 0
            r4 = 0
            r6 = 0
            java.lang.String r10 = "selectedDate"
            boolean r11 = r0.hasExtra(r10)
            r12 = 0
            if (r11 == 0) goto L_0x006a
            java.lang.String r3 = r0.getStringExtra(r10)
            java.lang.String r10 = " 00:00:00"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r3, r10)
            java.lang.String r13 = "yyyy-MM-dd HH:mm:ss"
            long r4 = com.leedarson.utils.e.b(r10, r13)
            java.lang.String r10 = " 23:59:59"
            java.lang.String r10 = kotlin.jvm.internal.k.l(r3, r10)
            long r6 = com.leedarson.utils.e.b(r10, r13)
        L_0x006a:
            r10 = 0
            java.lang.String r11 = "p2pId"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x007d
            java.lang.String r10 = r0.getStringExtra(r11)
            r23 = r10
            goto L_0x007f
        L_0x007d:
            r23 = r10
        L_0x007f:
            r10 = 0
            java.lang.String r11 = "eventStr"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x0092
            java.lang.String r10 = r0.getStringExtra(r11)
            r24 = r10
            goto L_0x0094
        L_0x0092:
            r24 = r10
        L_0x0094:
            r10 = 0
            java.lang.String r11 = "eventType"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x00a7
            int r10 = r0.getIntExtra(r11, r8)
            r25 = r10
            goto L_0x00a9
        L_0x00a7:
            r25 = r10
        L_0x00a9:
            r10 = 0
            java.lang.String r11 = "isWebRTC"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x00bc
            boolean r10 = r0.getBooleanExtra(r11, r8)
            r26 = r10
            goto L_0x00be
        L_0x00bc:
            r26 = r10
        L_0x00be:
            r10 = 0
            java.lang.String r11 = "isSupportPreCon"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x00d1
            boolean r10 = r0.getBooleanExtra(r11, r8)
            r27 = r10
            goto L_0x00d3
        L_0x00d1:
            r27 = r10
        L_0x00d3:
            r10 = 0
            java.lang.String r11 = "lists"
            boolean r12 = r0.hasExtra(r11)
            r13 = 0
            if (r12 == 0) goto L_0x00e9
            java.io.Serializable r11 = r0.getSerializableExtra(r11)
            r10 = r11
            java.util.List r10 = (java.util.List) r10
            r28 = r10
            goto L_0x00eb
        L_0x00e9:
            r28 = r10
        L_0x00eb:
            if (r28 == 0) goto L_0x011a
            int r10 = r28.size()
            if (r10 != 0) goto L_0x00f6
            r8 = r9
        L_0x00f6:
            if (r8 == 0) goto L_0x00f9
            goto L_0x011a
        L_0x00f9:
            com.leedarson.newui.pages.repos.SDCardEditRequestParamBean r8 = new com.leedarson.newui.pages.repos.SDCardEditRequestParamBean
            r10 = r8
            r11 = r2
            r12 = r3
            r13 = r23
            r14 = r24
            r15 = r25
            r16 = r26
            r17 = r27
            r18 = r28
            r19 = r4
            r21 = r6
            r10.<init>(r11, r12, r13, r14, r15, r16, r17, r18, r19, r21)
            r1.onNext(r8)
            r1.onComplete()
            goto L_0x0124
        L_0x011a:
            java.lang.Throwable r8 = new java.lang.Throwable
            java.lang.String r9 = "SD卡列表不存在..."
            r8.<init>(r9)
            r1.onError(r8)
        L_0x0124:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.pages.repos.i.b(android.content.Intent, io.reactivex.f):void");
    }

    @NotNull
    public final String c(long time) {
        Object[] objArr = {new Long(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4343, new Class[]{Long.TYPE}, String.class);
        if (proxy.isSupported) {
            return (String) proxy.result;
        }
        String format = new SimpleDateFormat("MMM d, yyyy", Locale.ENGLISH).format(new Date(time));
        k.d(format, "sf.format(d)");
        return format;
    }

    @NotNull
    public final e<ArrayList<SDRecord>> e(@NotNull ArrayList<SDRecord> datas) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{datas}, this, changeQuickRedirect, false, 4344, new Class[]{ArrayList.class}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        k.e(datas, "datas");
        e<ArrayList<SDRecord>> d = e.d(new g(datas), a.DROP);
        k.d(d, "create({\n            var _grouDatas = datas.groupBy { item ->\n                DateUtil.stampToDate(item.timestamp, \"HH\")\n            }\n            var _newDatas = ArrayList<SDRecord>()\n            var _groupIndex = 0\n            _grouDatas.forEach { item ->\n                var _titleRecord = SDRecord().apply {\n                    this.groupTitle = item.key + \":00\"\n                    this.itemType = SDRecord.itemType_Title\n                    this.groupIndex = _groupIndex\n                }\n                item.value.forEach { sdItem ->\n                    sdItem.groupIndex = _groupIndex\n                }\n                _groupIndex++\n                _newDatas.add(_titleRecord)\n                _newDatas.addAll(item.value)\n            }\n            it.onNext(_newDatas)\n        }, BackpressureStrategy.DROP)");
        return d;
    }

    /* access modifiers changed from: private */
    public static final void f(ArrayList arrayList, f fVar) {
        Object answer$iv$iv$iv;
        if (!PatchProxy.proxy(new Object[]{arrayList, fVar}, (Object) null, changeQuickRedirect, true, 4349, new Class[]{ArrayList.class, f.class}, Void.TYPE).isSupported) {
            Iterable $datas = arrayList;
            f it = fVar;
            k.e($datas, "$datas");
            k.e(it, "it");
            Map linkedHashMap = new LinkedHashMap();
            for (Object element$iv$iv : $datas) {
                Object key$iv$iv = com.leedarson.utils.e.j(((SDRecord) element$iv$iv).getTimestamp(), "HH");
                Map $this$getOrPut$iv$iv$iv = linkedHashMap;
                Object value$iv$iv$iv = $this$getOrPut$iv$iv$iv.get(key$iv$iv);
                if (value$iv$iv$iv == null) {
                    answer$iv$iv$iv = new ArrayList();
                    $this$getOrPut$iv$iv$iv.put(key$iv$iv, answer$iv$iv$iv);
                } else {
                    answer$iv$iv$iv = value$iv$iv$iv;
                }
                ((List) answer$iv$iv$iv).add(element$iv$iv);
            }
            Map _grouDatas = linkedHashMap;
            ArrayList arrayList2 = new ArrayList();
            int _groupIndex = 0;
            for (Map.Entry item : _grouDatas.entrySet()) {
                SDRecord _titleRecord = new SDRecord();
                SDRecord $this$groupAndFilterRecords_u24lambda_u2d13_u24lambda_u2d12_u24lambda_u2d10 = _titleRecord;
                $this$groupAndFilterRecords_u24lambda_u2d13_u24lambda_u2d12_u24lambda_u2d10.groupTitle = k.l((String) item.getKey(), ":00");
                $this$groupAndFilterRecords_u24lambda_u2d13_u24lambda_u2d12_u24lambda_u2d10.itemType = 1;
                $this$groupAndFilterRecords_u24lambda_u2d13_u24lambda_u2d12_u24lambda_u2d10.groupIndex = _groupIndex;
                for (SDRecord sdItem : (Iterable) item.getValue()) {
                    sdItem.groupIndex = _groupIndex;
                }
                _groupIndex++;
                arrayList2.add(_titleRecord);
                arrayList2.addAll((Collection) item.getValue());
            }
            it.onNext(arrayList2);
        }
    }

    @NotNull
    public final e<n<Integer, ArrayList<SDRecord>>> n(@NotNull SDRecord itemData, @NotNull ArrayList<SDRecord> dataList, int position) {
        Object[] objArr = {itemData, dataList, new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4345, new Class[]{SDRecord.class, ArrayList.class, Integer.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        k.e(itemData, "itemData");
        k.e(dataList, "dataList");
        e<n<Integer, ArrayList<SDRecord>>> d = e.d(new f(dataList, itemData, this, position), a.DROP);
        k.d(d, "create({\n            //判断其有没有达到最大事件选择池\n            val currentSelectEventSize = dataList.filter { item ->\n                item.isCheck && item.itemType == SDRecord.itemType_Event\n            }.size\n\n            if (!itemData.isCheck) {\n                //即将选中\n                if (currentSelectEventSize > maxSelectLimitCount - 1) {\n                    //已达到了最大选项\n                    it.onError(Throwable(\"不能选择更多,已达到了单次最大选择数...\"))\n                    return@create\n                } else {\n                    itemData.isCheck = !itemData.isCheck\n\n                    val currentGroupEventSelectCount = dataList.filter { item ->\n                        item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event && item.isCheck\n                    }.size\n\n                    val currentGroupEventTotalChildCount = dataList.filter { item ->\n                        item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event\n                    }.size\n\n                    dataList.firstOrNull { item ->\n                        item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Title\n                    }?.let { groupItem ->\n                        //判断当前这个组的数据是否全部选中\n                        groupItem.isCheck = currentGroupEventTotalChildCount == currentGroupEventSelectCount\n                    }\n                }\n\n            } else {\n                //取消选中\n                itemData.isCheck = !itemData.isCheck\n                val currentGroupEventSelectCount = dataList.filter { item ->\n                    item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event && item.isCheck\n                }.size\n\n                val currentGroupEventTotalChildCount = dataList.filter { item ->\n                    item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event\n                }.size\n                dataList.firstOrNull { item ->\n                    item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Title\n                }?.let { groupItem ->\n                    //全部选中\n                    groupItem.isCheck = currentGroupEventSelectCount == currentGroupEventTotalChildCount\n                }\n            }\n            it.onNext(Pair(position, dataList))\n            it.onComplete()\n        }, BackpressureStrategy.DROP)");
        return d;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v4, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v9, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v12, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v13, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v12, resolved type: com.leedarson.bean.SDRecord} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v16, resolved type: com.leedarson.bean.SDRecord} */
    /* access modifiers changed from: private */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x020f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final void o(java.util.ArrayList r18, com.leedarson.bean.SDRecord r19, com.leedarson.newui.pages.repos.i r20, int r21, io.reactivex.f r22) {
        /*
            r0 = 5
            java.lang.Object[] r1 = new java.lang.Object[r0]
            r8 = 0
            r1[r8] = r18
            r9 = 1
            r1[r9] = r19
            r2 = 2
            r1[r2] = r20
            java.lang.Integer r3 = new java.lang.Integer
            r10 = r21
            r3.<init>(r10)
            r4 = 3
            r1[r4] = r3
            r3 = 4
            r1[r3] = r22
            com.meituan.robust.ChangeQuickRedirect r5 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class<java.util.ArrayList> r0 = java.util.ArrayList.class
            r6[r8] = r0
            java.lang.Class<com.leedarson.bean.SDRecord> r0 = com.leedarson.bean.SDRecord.class
            r6[r9] = r0
            java.lang.Class<com.leedarson.newui.pages.repos.i> r0 = com.leedarson.newui.pages.repos.i.class
            r6[r2] = r0
            java.lang.Class r0 = java.lang.Integer.TYPE
            r6[r4] = r0
            java.lang.Class<io.reactivex.f> r0 = io.reactivex.f.class
            r6[r3] = r0
            java.lang.Class r7 = java.lang.Void.TYPE
            r2 = 0
            r4 = 1
            r0 = 4350(0x10fe, float:6.096E-42)
            r3 = r5
            r5 = r0
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0042
            return
        L_0x0042:
            r0 = r18
            r1 = r20
            r2 = r22
            r3 = r19
            r4 = r21
            java.lang.String r5 = "$dataList"
            kotlin.jvm.internal.k.e(r0, r5)
            java.lang.String r5 = "$itemData"
            kotlin.jvm.internal.k.e(r3, r5)
            java.lang.String r5 = "this$0"
            kotlin.jvm.internal.k.e(r1, r5)
            java.lang.String r5 = "it"
            kotlin.jvm.internal.k.e(r2, r5)
            r5 = r0
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r10 = r5
            r11 = 0
            java.util.Iterator r12 = r10.iterator()
        L_0x006d:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x008f
            java.lang.Object r13 = r12.next()
            r14 = r13
            com.leedarson.bean.SDRecord r14 = (com.leedarson.bean.SDRecord) r14
            r15 = 0
            boolean r16 = r14.isCheck()
            if (r16 == 0) goto L_0x0087
            int r8 = r14.itemType
            if (r8 != 0) goto L_0x0087
            r8 = r9
            goto L_0x0088
        L_0x0087:
            r8 = 0
        L_0x0088:
            if (r8 == 0) goto L_0x008d
            r7.add(r13)
        L_0x008d:
            r8 = 0
            goto L_0x006d
        L_0x008f:
            int r5 = r7.size()
            boolean r6 = r3.isCheck()
            if (r6 != 0) goto L_0x016d
            int r6 = r1.b
            int r6 = r6 - r9
            if (r5 <= r6) goto L_0x00ac
            java.lang.Throwable r6 = new java.lang.Throwable
            java.lang.String r7 = "不能选择更多,已达到了单次最大选择数..."
            r6.<init>(r7)
            r2.onError(r6)
            return
        L_0x00ac:
            boolean r6 = r3.isCheck()
            r6 = r6 ^ r9
            r3.setCheck(r6)
            r6 = r0
            r8 = 0
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            r11 = r6
            r12 = 0
            java.util.Iterator r13 = r11.iterator()
        L_0x00c1:
            boolean r14 = r13.hasNext()
            if (r14 == 0) goto L_0x00ea
            java.lang.Object r14 = r13.next()
            r15 = r14
            com.leedarson.bean.SDRecord r15 = (com.leedarson.bean.SDRecord) r15
            r17 = 0
            int r7 = r15.groupIndex
            int r9 = r3.groupIndex
            if (r7 != r9) goto L_0x00e2
            int r7 = r15.itemType
            if (r7 != 0) goto L_0x00e2
            boolean r7 = r15.isCheck()
            if (r7 == 0) goto L_0x00e2
            r7 = 1
            goto L_0x00e3
        L_0x00e2:
            r7 = 0
        L_0x00e3:
            if (r7 == 0) goto L_0x00e8
            r10.add(r14)
        L_0x00e8:
            r9 = 1
            goto L_0x00c1
        L_0x00ea:
            int r6 = r10.size()
            r7 = r0
            r8 = 0
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r10 = r7
            r11 = 0
            java.util.Iterator r12 = r10.iterator()
        L_0x00fe:
            boolean r13 = r12.hasNext()
            if (r13 == 0) goto L_0x0127
            java.lang.Object r13 = r12.next()
            r14 = r13
            com.leedarson.bean.SDRecord r14 = (com.leedarson.bean.SDRecord) r14
            r15 = 0
            r20 = r1
            int r1 = r14.groupIndex
            r19 = r5
            int r5 = r3.groupIndex
            if (r1 != r5) goto L_0x011c
            int r1 = r14.itemType
            if (r1 != 0) goto L_0x011c
            r1 = 1
            goto L_0x011d
        L_0x011c:
            r1 = 0
        L_0x011d:
            if (r1 == 0) goto L_0x0122
            r9.add(r13)
        L_0x0122:
            r5 = r19
            r1 = r20
            goto L_0x00fe
        L_0x0127:
            r20 = r1
            r19 = r5
            int r1 = r9.size()
            r5 = r0
            r7 = 0
            java.util.Iterator r8 = r5.iterator()
        L_0x0137:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0157
            java.lang.Object r9 = r8.next()
            r10 = r9
            com.leedarson.bean.SDRecord r10 = (com.leedarson.bean.SDRecord) r10
            r11 = 0
            int r12 = r10.groupIndex
            int r13 = r3.groupIndex
            if (r12 != r13) goto L_0x0152
            int r12 = r10.itemType
            r13 = 1
            if (r12 != r13) goto L_0x0152
            r10 = 1
            goto L_0x0153
        L_0x0152:
            r10 = 0
        L_0x0153:
            if (r10 == 0) goto L_0x0137
            r7 = r9
            goto L_0x0158
        L_0x0157:
            r7 = 0
        L_0x0158:
            com.leedarson.bean.SDRecord r7 = (com.leedarson.bean.SDRecord) r7
            if (r7 != 0) goto L_0x015f
            goto L_0x0225
        L_0x015f:
            r5 = r7
            r7 = 0
            if (r1 != r6) goto L_0x0165
            r8 = 1
            goto L_0x0166
        L_0x0165:
            r8 = 0
        L_0x0166:
            r5.setCheck(r8)
            goto L_0x0225
        L_0x016d:
            r20 = r1
            r19 = r5
            boolean r1 = r3.isCheck()
            r5 = 1
            r1 = r1 ^ r5
            r3.setCheck(r1)
            r1 = r0
            r5 = 0
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r7 = r1
            r8 = 0
            java.util.Iterator r9 = r7.iterator()
        L_0x0187:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x01ae
            java.lang.Object r10 = r9.next()
            r11 = r10
            com.leedarson.bean.SDRecord r11 = (com.leedarson.bean.SDRecord) r11
            r12 = 0
            int r13 = r11.groupIndex
            int r14 = r3.groupIndex
            if (r13 != r14) goto L_0x01a7
            int r13 = r11.itemType
            if (r13 != 0) goto L_0x01a7
            boolean r13 = r11.isCheck()
            if (r13 == 0) goto L_0x01a7
            r13 = 1
            goto L_0x01a8
        L_0x01a7:
            r13 = 0
        L_0x01a8:
            if (r13 == 0) goto L_0x0187
            r6.add(r10)
            goto L_0x0187
        L_0x01ae:
            int r1 = r6.size()
            r5 = r0
            r6 = 0
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r8 = r5
            r9 = 0
            java.util.Iterator r10 = r8.iterator()
        L_0x01c2:
            boolean r11 = r10.hasNext()
            if (r11 == 0) goto L_0x01e3
            java.lang.Object r11 = r10.next()
            r12 = r11
            com.leedarson.bean.SDRecord r12 = (com.leedarson.bean.SDRecord) r12
            r13 = 0
            int r14 = r12.groupIndex
            int r15 = r3.groupIndex
            if (r14 != r15) goto L_0x01dc
            int r14 = r12.itemType
            if (r14 != 0) goto L_0x01dc
            r13 = 1
            goto L_0x01dd
        L_0x01dc:
            r13 = 0
        L_0x01dd:
            if (r13 == 0) goto L_0x01c2
            r7.add(r11)
            goto L_0x01c2
        L_0x01e3:
            int r5 = r7.size()
            r6 = r0
            r7 = 0
            java.util.Iterator r8 = r6.iterator()
        L_0x01f0:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0211
            java.lang.Object r9 = r8.next()
            r10 = r9
            com.leedarson.bean.SDRecord r10 = (com.leedarson.bean.SDRecord) r10
            r11 = 0
            int r12 = r10.groupIndex
            int r13 = r3.groupIndex
            if (r12 != r13) goto L_0x020b
            int r12 = r10.itemType
            r13 = 1
            if (r12 != r13) goto L_0x020c
            r10 = r13
            goto L_0x020d
        L_0x020b:
            r13 = 1
        L_0x020c:
            r10 = 0
        L_0x020d:
            if (r10 == 0) goto L_0x01f0
            r7 = r9
            goto L_0x0213
        L_0x0211:
            r13 = 1
            r7 = 0
        L_0x0213:
            com.leedarson.bean.SDRecord r7 = (com.leedarson.bean.SDRecord) r7
            if (r7 != 0) goto L_0x0219
        L_0x0218:
            goto L_0x0225
        L_0x0219:
            r6 = r7
            r7 = 0
            if (r1 != r5) goto L_0x021f
            r8 = r13
            goto L_0x0220
        L_0x021f:
            r8 = 0
        L_0x0220:
            r6.setCheck(r8)
            goto L_0x0218
        L_0x0225:
            kotlin.n r1 = new kotlin.n
            java.lang.Integer r5 = java.lang.Integer.valueOf(r4)
            r1.<init>(r5, r0)
            r2.onNext(r1)
            r2.onComplete()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.pages.repos.i.o(java.util.ArrayList, com.leedarson.bean.SDRecord, com.leedarson.newui.pages.repos.i, int, io.reactivex.f):void");
    }

    @NotNull
    public final e<n<Integer, ArrayList<SDRecord>>> l(@NotNull SDRecord itemData, @NotNull ArrayList<SDRecord> dataList, int position) {
        Object[] objArr = {itemData, dataList, new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 4346, new Class[]{SDRecord.class, ArrayList.class, Integer.TYPE}, e.class);
        if (proxy.isSupported) {
            return (e) proxy.result;
        }
        k.e(itemData, "itemData");
        k.e(dataList, "dataList");
        e<n<Integer, ArrayList<SDRecord>>> d = e.d(new e(dataList, itemData, this, position), a.DROP);
        k.d(d, "create({\n            val currentSelectEventSize = dataList.filter { item ->\n                item.isCheck && item.itemType == SDRecord.itemType_Event\n            }.size\n            if (!itemData.isCheck) {\n                //即将要选中整个组(防止溢出多选)\n                val currentGrouUnSelectCount = dataList.filter { item ->\n                    item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event && !item.isCheck\n                }.size\n\n                if ((currentSelectEventSize + currentGrouUnSelectCount) > maxSelectLimitCount) {\n                    //已超出选择限制，进行限制（单次操作无效）\n                    it.onError(Throwable(\"不能选择更多,已达到了单次最大选择数...\"))\n                    return@create\n                } else {\n                    //执行全部选中\n                    itemData.isCheck = !itemData.isCheck\n                    //将本组所有子成员都选中\n                    dataList.filter { item ->\n                        item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event\n                    }.forEach { sdEvent ->\n                        sdEvent.isCheck = true\n                    }\n                }\n            } else {\n                //即将要撤销选中整个组\n                itemData.isCheck = false\n                dataList.filter { item ->\n                    item.groupIndex == itemData.groupIndex && item.itemType == SDRecord.itemType_Event\n                }.forEach { sdEvent ->\n                    sdEvent.isCheck = false\n                }\n            }\n            it.onNext(Pair(position, dataList))\n            it.onComplete()\n\n        }, BackpressureStrategy.DROP)");
        return d;
    }

    /* access modifiers changed from: private */
    public static final void m(ArrayList arrayList, SDRecord sDRecord, i iVar, int i, f fVar) {
        if (!PatchProxy.proxy(new Object[]{arrayList, sDRecord, iVar, new Integer(i), fVar}, (Object) null, changeQuickRedirect, true, 4351, new Class[]{ArrayList.class, SDRecord.class, i.class, Integer.TYPE, f.class}, Void.TYPE).isSupported) {
            Iterable $dataList = arrayList;
            i this$0 = iVar;
            f it = fVar;
            SDRecord $itemData = sDRecord;
            int $position = i;
            k.e($dataList, "$dataList");
            k.e($itemData, "$itemData");
            k.e(this$0, "this$0");
            k.e(it, "it");
            ArrayList arrayList2 = new ArrayList();
            for (Object element$iv$iv : $dataList) {
                SDRecord item = (SDRecord) element$iv$iv;
                if (item.isCheck() && item.itemType == 0) {
                    arrayList2.add(element$iv$iv);
                }
            }
            int currentSelectEventSize = arrayList2.size();
            if (!$itemData.isCheck()) {
                Iterable $this$filter$iv = $dataList;
                ArrayList arrayList3 = new ArrayList();
                for (Object element$iv$iv2 : $this$filter$iv) {
                    SDRecord item2 = (SDRecord) element$iv$iv2;
                    Iterable $this$filter$iv2 = $this$filter$iv;
                    if (item2.groupIndex == $itemData.groupIndex && item2.itemType == 0 && !item2.isCheck()) {
                        arrayList3.add(element$iv$iv2);
                    }
                    $this$filter$iv = $this$filter$iv2;
                }
                if (currentSelectEventSize + arrayList3.size() > this$0.b) {
                    it.onError(new Throwable("不能选择更多,已达到了单次最大选择数..."));
                    return;
                }
                $itemData.setCheck(!$itemData.isCheck());
                ArrayList<SDRecord> $this$forEach$iv = new ArrayList<>();
                for (Object element$iv$iv3 : $dataList) {
                    SDRecord item3 = (SDRecord) element$iv$iv3;
                    i this$02 = this$0;
                    int currentSelectEventSize2 = currentSelectEventSize;
                    if (item3.groupIndex == $itemData.groupIndex && item3.itemType == 0) {
                        $this$forEach$iv.add(element$iv$iv3);
                    }
                    this$0 = this$02;
                    currentSelectEventSize = currentSelectEventSize2;
                }
                int i2 = currentSelectEventSize;
                for (SDRecord sdEvent : $this$forEach$iv) {
                    sdEvent.setCheck(true);
                }
            } else {
                int i3 = currentSelectEventSize;
                $itemData.setCheck(false);
                ArrayList<SDRecord> $this$forEach$iv2 = new ArrayList<>();
                for (Object element$iv$iv4 : $dataList) {
                    SDRecord item4 = (SDRecord) element$iv$iv4;
                    if (((item4.groupIndex == $itemData.groupIndex && item4.itemType == 0) ? 1 : null) != null) {
                        $this$forEach$iv2.add(element$iv$iv4);
                    }
                }
                for (SDRecord sdEvent2 : $this$forEach$iv2) {
                    sdEvent2.setCheck(false);
                }
            }
            it.onNext(new n(Integer.valueOf($position), $dataList));
            it.onComplete();
        }
    }

    public final void k(@NotNull ArrayList<SDRecord> dataList) {
        if (!PatchProxy.proxy(new Object[]{dataList}, this, changeQuickRedirect, false, 4347, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            k.e(dataList, "dataList");
            b<Integer> bVar = this.a;
            ArrayList arrayList = new ArrayList();
            for (T next : dataList) {
                SDRecord it = (SDRecord) next;
                if (((it.itemType != 0 || !it.isCheck()) ? null : 1) != null) {
                    arrayList.add(next);
                }
            }
            bVar.onNext(Integer.valueOf(arrayList.size()));
        }
    }
}
