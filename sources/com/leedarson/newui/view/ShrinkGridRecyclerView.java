package com.leedarson.newui.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.airbnb.lottie.LottieAnimationView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.http.observer.l;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.OpItem;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.TimeUnit;
import org.json.JSONObject;

public class ShrinkGridRecyclerView extends FrameLayout implements Observer {
    public static ChangeQuickRedirect changeQuickRedirect;
    private String A4;
    private boolean B4;
    private boolean C4;
    private boolean D4;
    Handler E4;
    /* access modifiers changed from: private */
    public boolean F4;
    private f G4;
    private e H4;
    /* access modifiers changed from: private */
    public h I4;
    /* access modifiers changed from: private */
    public int a1;
    private boolean a2;
    private final String c;
    private final int d;
    /* access modifiers changed from: private */
    public SafeMeasureRecyclerView f;
    /* access modifiers changed from: private */
    public int p0;
    private boolean p1;
    private boolean p2;
    private boolean p3;
    private boolean p4;
    /* access modifiers changed from: private */
    public GridAdapter q;
    /* access modifiers changed from: private */
    public ArrayList<OpItem> x;
    io.reactivex.processors.b<Boolean> y;
    io.reactivex.disposables.b z;
    private boolean z4;

    public interface e {
        void a(int i);
    }

    public interface f {
        boolean a(View view, MotionEvent motionEvent, int i);
    }

    static /* synthetic */ int[] h(ShrinkGridRecyclerView x0, RecyclerView x1, int x2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{x0, x1, new Integer(x2)}, (Object) null, changeQuickRedirect, true, 5243, new Class[]{ShrinkGridRecyclerView.class, RecyclerView.class, Integer.TYPE}, int[].class);
        return proxy.isSupported ? (int[]) proxy.result : x0.l(x1, x2);
    }

    static /* synthetic */ void i(ShrinkGridRecyclerView x0, String x1) {
        Class[] clsArr = {ShrinkGridRecyclerView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5244, clsArr, Void.TYPE).isSupported) {
            x0.t(x1);
        }
    }

    public ShrinkGridRecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ShrinkGridRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = "ShrinkGridRecyclerView";
        this.d = 5;
        this.x = new ArrayList<>();
        this.y = io.reactivex.processors.b.Y();
        this.p1 = false;
        this.a2 = false;
        this.p2 = false;
        this.p3 = false;
        this.p4 = false;
        this.z4 = false;
        this.A4 = "";
        this.B4 = false;
        this.C4 = true;
        this.D4 = false;
        this.E4 = new Handler(Looper.getMainLooper());
        this.F4 = false;
        this.G4 = new a();
        this.H4 = new b();
        m(context);
    }

    private void m(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5233, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_shrink_recycler_view, this, true);
            SafeMeasureRecyclerView safeMeasureRecyclerView = (SafeMeasureRecyclerView) findViewById(R$id.recycler);
            this.f = safeMeasureRecyclerView;
            ((SimpleItemAnimator) safeMeasureRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            this.q = new GridAdapter(this.x, context);
            this.f.setLayoutManager(new GridLayoutManager(context, 5) {
                public static ChangeQuickRedirect changeQuickRedirect;

                public boolean canScrollVertically() {
                    return false;
                }
            });
            this.f.setAdapter(this.q);
            this.q.setOnItemClickListener(this.H4);
            this.q.setOnItemTouchListener(this.G4);
            u();
            this.z = this.y.e(100, TimeUnit.MILLISECONDS).c(l.c()).I(new n(this), o.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: q */
    public /* synthetic */ void r(Object obj) {
        if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 5242, new Class[]{Object.class}, Void.TYPE).isSupported) {
            a();
        }
    }

    static /* synthetic */ void s(Throwable throwable) {
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void u() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5234(0x1472, float:7.334E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.z
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.z
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.ShrinkGridRecyclerView.u():void");
    }

    public class a implements f {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(View view, MotionEvent event, int position) {
            Object[] objArr = {view, event, new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 5245, new Class[]{View.class, MotionEvent.class, Integer.TYPE}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            OpItem opItem = OpItem.speakHalf;
            if (!opItem.isStateEnabled()) {
                return false;
            }
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case 0:
                    if (((OpItem) ShrinkGridRecyclerView.this.x.get(position)) == opItem) {
                        boolean unused = ShrinkGridRecyclerView.this.F4 = true;
                        if (ShrinkGridRecyclerView.this.I4 != null) {
                            ShrinkGridRecyclerView.this.I4.m(true);
                            break;
                        }
                    } else {
                        return false;
                    }
                    break;
                case 1:
                case 3:
                    if (ShrinkGridRecyclerView.this.F4 && ShrinkGridRecyclerView.this.I4 != null) {
                        opItem.setWorking(false);
                        ShrinkGridRecyclerView.this.I4.j(true);
                        boolean unused2 = ShrinkGridRecyclerView.this.F4 = false;
                        break;
                    }
                case 2:
                    if (ShrinkGridRecyclerView.this.F4) {
                        return true;
                    }
                    break;
            }
            return false;
        }
    }

    public class b implements e {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(int position) {
            Object[] objArr = {new Integer(position)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5246, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                OpItem item = (OpItem) ShrinkGridRecyclerView.this.q.a.get(position);
                if (ShrinkGridRecyclerView.this.I4 != null && item.isStateEnabled()) {
                    switch (d.a[item.ordinal()]) {
                        case 1:
                            ShrinkGridRecyclerView.this.I4.c();
                            return;
                        case 2:
                            ShrinkGridRecyclerView.this.I4.v();
                            return;
                        case 4:
                            ShrinkGridRecyclerView.this.I4.w();
                            return;
                        case 5:
                            ShrinkGridRecyclerView.this.I4.o();
                            return;
                        case 6:
                            ShrinkGridRecyclerView.this.I4.s();
                            return;
                        case 7:
                            ShrinkGridRecyclerView.this.I4.t();
                            return;
                        case 8:
                            ShrinkGridRecyclerView.this.I4.h();
                            return;
                        case 9:
                            ShrinkGridRecyclerView.this.I4.u();
                            return;
                        case 10:
                            ShrinkGridRecyclerView.this.I4.k();
                            return;
                        case 11:
                            ShrinkGridRecyclerView.this.I4.q();
                            return;
                        case 12:
                            ShrinkGridRecyclerView.this.I4.p();
                            return;
                        case 13:
                            ShrinkGridRecyclerView.this.I4.i();
                            return;
                        case 14:
                            ShrinkGridRecyclerView.this.I4.g();
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    public static /* synthetic */ class d {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[OpItem.values().length];
            a = iArr;
            try {
                iArr[OpItem.record.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[OpItem.snap.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[OpItem.speakHalf.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[OpItem.speakFull.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[OpItem.alarm.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[OpItem.light.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[OpItem.sdcard.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[OpItem.multiView.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[OpItem.album.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[OpItem.tracking.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[OpItem.onoff.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[OpItem.path.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[OpItem.ai.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[OpItem.focus.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
        }
    }

    public void setDataList(List<OpItem> datas) {
        if (!PatchProxy.proxy(new Object[]{datas}, this, changeQuickRedirect, false, 5235, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.x.clear();
            this.x.addAll(datas);
            this.q.notifyDataSetChanged();
        }
    }

    public class c implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5247, new Class[0], Void.TYPE).isSupported) {
                if (ShrinkGridRecyclerView.this.x.size() > 5) {
                    ShrinkGridRecyclerView shrinkGridRecyclerView = ShrinkGridRecyclerView.this;
                    int[] viewHeight = ShrinkGridRecyclerView.h(shrinkGridRecyclerView, shrinkGridRecyclerView.f, 5);
                    ShrinkGridRecyclerView shrinkGridRecyclerView2 = ShrinkGridRecyclerView.this;
                    ShrinkGridRecyclerView.i(shrinkGridRecyclerView2, "totalHeight:" + viewHeight[0] + ",itemHeight:" + viewHeight[1] + "==" + ShrinkGridRecyclerView.this.f);
                    int unused = ShrinkGridRecyclerView.this.p0 = viewHeight[0];
                    int unused2 = ShrinkGridRecyclerView.this.a1 = viewHeight[1];
                }
            }
        }
    }

    public void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5236, new Class[0], Void.TYPE).isSupported) {
            try {
                getHandler().post(new c());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static class GridAdapter extends RecyclerView.Adapter<ViewHolder> {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public ArrayList<OpItem> a;
        private Context b;
        /* access modifiers changed from: private */
        public e c;
        /* access modifiers changed from: private */
        public f d;
        /* access modifiers changed from: private */
        public long e = 0;

        public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i)}, this, changeQuickRedirect, false, 5251, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                f((ViewHolder) viewHolder, i);
            }
        }

        @NonNull
        public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 5252, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
            return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : g(viewGroup, i);
        }

        public GridAdapter(ArrayList<OpItem> list, Context context) {
            this.a = list;
            this.b = context;
        }

        public void setOnItemClickListener(e listenser) {
            this.c = listenser;
        }

        public void setOnItemTouchListener(f listenser) {
            this.d = listenser;
        }

        @NonNull
        public ViewHolder g(@NonNull ViewGroup viewGroup, int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i)}, this, changeQuickRedirect, false, 5248, new Class[]{ViewGroup.class, Integer.TYPE}, ViewHolder.class);
            if (proxy.isSupported) {
                return (ViewHolder) proxy.result;
            }
            return new ViewHolder(LayoutInflater.from(this.b).inflate(R$layout.item_grid, (ViewGroup) null));
        }

        public void f(@NonNull ViewHolder viewHolder, int position) {
            if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(position)}, this, changeQuickRedirect, false, 5249, new Class[]{ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
                OpItem opItem = this.a.get(position);
                int p = position;
                viewHolder.itemView.setOnTouchListener(new a(p));
                viewHolder.itemView.setOnClickListener(new b(p));
                viewHolder.a.setText(opItem.getDescName());
                if (opItem.isStateEnabled()) {
                    viewHolder.itemView.setEnabled(true);
                    if (opItem != OpItem.alarm || !opItem.isWorking()) {
                        int padding = com.leedarson.base.utils.d.b(this.b, 8.5f);
                        viewHolder.b.setPadding(padding, padding, padding, padding);
                        viewHolder.b.setImageResource(opItem.isWorking() ? opItem.getWorkingResId() : opItem.getResId());
                    } else {
                        viewHolder.b.setImageResource(0);
                        viewHolder.b.setAnimation("ipcalarming.json");
                        viewHolder.b.setRepeatCount(-1);
                        viewHolder.b.u();
                        viewHolder.b.setPadding(0, 0, 0, 0);
                    }
                    if (opItem.isWorking() != 0) {
                        viewHolder.a.setTextColor(this.b.getResources().getColor(opItem.getWorkingColor()));
                        if (opItem == OpItem.record) {
                            SpannableString spanString = new SpannableString(opItem.getDescName());
                            spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5515B")), 0, 3, 33);
                            viewHolder.a.setText(spanString);
                            return;
                        }
                        return;
                    }
                    viewHolder.a.setTextColor(this.b.getResources().getColor(opItem.getNormalColor()));
                    return;
                }
                if (opItem == OpItem.alarm) {
                    int padding2 = com.leedarson.base.utils.d.b(this.b, 8.5f);
                    viewHolder.b.setPadding(padding2, padding2, padding2, padding2);
                }
                viewHolder.itemView.setEnabled(false);
                viewHolder.a.setTextColor(this.b.getResources().getColor(opItem.getDisabledColorId()));
                viewHolder.b.setImageResource(opItem.getDisabledResId());
            }
        }

        public class a implements View.OnTouchListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int c;

            a(int i) {
                this.c = i;
            }

            public boolean onTouch(View v, MotionEvent event) {
                PatchProxyResult proxy = PatchProxy.proxy(new Object[]{v, event}, this, changeQuickRedirect, false, 5253, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
                if (proxy.isSupported) {
                    return ((Boolean) proxy.result).booleanValue();
                }
                if (GridAdapter.this.d != null) {
                    return GridAdapter.this.d.a(v, event, this.c);
                }
                return false;
            }
        }

        public class b implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ int c;

            b(int i) {
                this.c = i;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5254, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View view2 = view;
                if (System.currentTimeMillis() - GridAdapter.this.e < 600) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                long unused = GridAdapter.this.e = System.currentTimeMillis();
                if (GridAdapter.this.c != null) {
                    GridAdapter.this.c.a(this.c);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }

        public int getItemCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5250, new Class[0], Integer.TYPE);
            if (proxy.isSupported) {
                return ((Integer) proxy.result).intValue();
            }
            ArrayList<OpItem> arrayList = this.a;
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LDSTextView a;
            public LottieAnimationView b;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.a = (LDSTextView) itemView.findViewById(R$id.tv_name);
                this.b = (LottieAnimationView) itemView.findViewById(R$id.iv_op_icon);
            }
        }
    }

    private int[] l(RecyclerView listView, int col) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{listView, new Integer(col)}, this, changeQuickRedirect, false, 5237, new Class[]{RecyclerView.class, Integer.TYPE}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int[] viewHeight = new int[2];
        if (listView == null) {
            return viewHeight;
        }
        int totalHeight = 0;
        int i = 0;
        while (i < listView.getAdapter().getItemCount()) {
            View listItem = listView.getLayoutManager().findViewByPosition(i);
            if (listItem != null) {
                listItem.measure(0, 0);
                int measuredHeight = listItem.getMeasuredHeight();
                if (i == 0) {
                    viewHeight[1] = measuredHeight - 5;
                }
                totalHeight += measuredHeight;
            }
            i += col;
        }
        viewHeight[0] = totalHeight;
        return viewHeight;
    }

    private void t(String s) {
        if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 5238, new Class[]{String.class}, Void.TYPE).isSupported) {
            timber.log.a.g("ShrinkGridRecyclerView").a(s, new Object[0]);
        }
    }

    public void setOnEventCallback(h eventCallback) {
        this.I4 = eventCallback;
    }

    public void update(Observable observable, Object arg) {
        if (!PatchProxy.proxy(new Object[]{observable, arg}, this, changeQuickRedirect, false, 5239, new Class[]{Observable.class, Object.class}, Void.TYPE).isSupported) {
            try {
                JSONObject dataObj = (JSONObject) arg;
                if (dataObj != null) {
                    this.p2 = dataObj.getBoolean("isLightOn");
                    this.p3 = dataObj.getBoolean("isAlarm");
                    this.a2 = dataObj.getBoolean("isTalking");
                    this.p1 = dataObj.getBoolean("isRecording");
                    this.A4 = dataObj.getString("recordTime");
                    this.p4 = dataObj.getBoolean("isTrackingMode");
                    this.C4 = dataObj.getBoolean("TurnOnOff");
                    this.z4 = dataObj.getBoolean("isFocusing");
                    this.D4 = dataObj.getBoolean("isPathOn");
                    this.y.onNext(true);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5240, new Class[0], Void.TYPE).isSupported) {
            this.E4.postDelayed(new p(this), 100);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public /* synthetic */ void p() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5241, new Class[0], Void.TYPE).isSupported) {
            SafeMeasureRecyclerView safeMeasureRecyclerView = this.f;
            if (safeMeasureRecyclerView == null || safeMeasureRecyclerView.isComputingLayout()) {
                a();
                return;
            }
            if (this.B4) {
                OpItem opItem = OpItem.speakHalf;
                opItem.setWorking(this.a2);
                if (this.a2) {
                    this.q.notifyItemChanged(this.x.indexOf(opItem));
                }
            } else {
                OpItem.speakFull.setWorking(this.a2);
            }
            OpItem.light.setWorking(this.p2);
            OpItem.alarm.setWorking(this.p3);
            OpItem.tracking.setWorking(this.p4);
            OpItem.focus.setWorking(this.z4);
            if (this.p1) {
                OpItem opItem2 = OpItem.record;
                opItem2.setWorking(true);
                opItem2.setDescName(String.format(Locale.US, PubUtils.getString(getContext(), R$string.recording_timestr), new Object[]{this.A4}));
            } else {
                OpItem opItem3 = OpItem.record;
                opItem3.setWorking(false);
                opItem3.setDescName(PubUtils.getString(getContext(), R$string.record));
            }
            OpItem.onoff.setWorking(this.C4);
            OpItem.path.setWorking(this.D4);
            this.q.notifyItemRangeChanged(0, this.x.size());
        }
    }
}
