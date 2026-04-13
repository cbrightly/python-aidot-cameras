package com.leedarson.newui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import androidx.annotation.Nullable;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.OpItem;
import com.leedarson.module_base.R$string;
import com.leedarson.serviceinterface.utils.PubUtils;
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
import org.json.JSONObject;
import timber.log.a;

public class ShrinkGridView extends FrameLayout implements Observer {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public boolean A4;
    /* access modifiers changed from: private */
    public boolean B4;
    /* access modifiers changed from: private */
    public boolean C4;
    /* access modifiers changed from: private */
    public boolean D4;
    /* access modifiers changed from: private */
    public String E4;
    private View.OnTouchListener F4;
    /* access modifiers changed from: private */
    public boolean G4;
    private View.OnTouchListener H4;
    private AdapterView.OnItemClickListener I4;
    /* access modifiers changed from: private */
    public com.leedarson.utils.h J4;
    private boolean a1;
    /* access modifiers changed from: private */
    public int a2;
    private final String c;
    private final int d;
    /* access modifiers changed from: private */
    public GridView f;
    /* access modifiers changed from: private */
    public boolean p0;
    /* access modifiers changed from: private */
    public boolean p1;
    /* access modifiers changed from: private */
    public int p2;
    /* access modifiers changed from: private */
    public float p3;
    /* access modifiers changed from: private */
    public int p4;
    private View q;
    /* access modifiers changed from: private */
    public h x;
    /* access modifiers changed from: private */
    public ImageView y;
    /* access modifiers changed from: private */
    public ArrayList<OpItem> z;
    /* access modifiers changed from: private */
    public boolean z4;

    static /* synthetic */ void c(ShrinkGridView x0, String x1) {
        Class[] clsArr = {ShrinkGridView.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 5266, clsArr, Void.TYPE).isSupported) {
            x0.B(x1);
        }
    }

    static /* synthetic */ void v(ShrinkGridView x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5267, new Class[]{ShrinkGridView.class}, Void.TYPE).isSupported) {
            x0.D();
        }
    }

    public ShrinkGridView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ShrinkGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.c = "ShrinkGridView";
        this.d = 5;
        this.z = new ArrayList<>();
        this.p0 = true;
        this.a1 = true;
        this.z4 = false;
        this.A4 = false;
        this.B4 = false;
        this.C4 = false;
        this.D4 = false;
        this.E4 = "";
        this.F4 = new a();
        this.G4 = false;
        this.H4 = new b();
        this.I4 = new c();
        A(context);
    }

    private void A(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5255, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_shrink_grid_view, this, true);
            this.f = (GridView) findViewById(R$id.gridview);
            this.q = findViewById(R$id.drag_layout);
            this.y = (ImageView) findViewById(R$id.iv_indicator);
            h hVar = new h(this.z, context);
            this.x = hVar;
            this.f.setAdapter(hVar);
            this.f.setOnItemClickListener(this.I4);
            this.f.setOnTouchListener(this.H4);
            setShrinkable(true);
        }
    }

    public void setShrinkable(boolean b2) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(b2 ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5256, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.a1 = b2;
            View view = this.q;
            if (!b2) {
                i = 8;
            }
            view.setVisibility(i);
            this.q.setOnTouchListener(b2 ? this.F4 : null);
        }
    }

    private void D() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5257, new Class[0], Void.TYPE).isSupported) {
            if (this.p0) {
                x();
            } else {
                C(true);
            }
        }
    }

    public class a implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 5268, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            switch (event.getAction()) {
                case 0:
                    float unused = ShrinkGridView.this.p3 = event.getRawY();
                    ShrinkGridView shrinkGridView = ShrinkGridView.this;
                    ShrinkGridView.c(shrinkGridView, "downY=" + ShrinkGridView.this.p3);
                    return true;
                case 1:
                case 3:
                    int offset = com.leedarson.base.utils.d.b(ShrinkGridView.this.getContext(), 20.0f);
                    Rect rect = new Rect(ShrinkGridView.this.y.getLeft() - (offset * 2), ShrinkGridView.this.y.getTop() - (offset * 2), ShrinkGridView.this.y.getRight() + offset, ShrinkGridView.this.y.getBottom());
                    int upX = (int) event.getX();
                    int upY = (int) event.getY();
                    ShrinkGridView shrinkGridView2 = ShrinkGridView.this;
                    ShrinkGridView.c(shrinkGridView2, rect.toShortString() + ",upX=" + upX + ",upY=" + upY);
                    if (event.getRawY() != ShrinkGridView.this.p3 || !rect.contains(upX, upY)) {
                        int bottomMargin = ((LinearLayout.LayoutParams) ShrinkGridView.this.f.getLayoutParams()).bottomMargin;
                        ShrinkGridView shrinkGridView3 = ShrinkGridView.this;
                        ShrinkGridView.c(shrinkGridView3, "up bottomMargin=" + bottomMargin);
                        if (Math.abs(bottomMargin) > ShrinkGridView.this.p4 / 2) {
                            ShrinkGridView.this.C(true);
                        } else {
                            ShrinkGridView.this.x();
                        }
                        return true;
                    }
                    ShrinkGridView.v(ShrinkGridView.this);
                    return true;
                case 2:
                    int dy = (int) (event.getRawY() - ShrinkGridView.this.p3);
                    ShrinkGridView shrinkGridView4 = ShrinkGridView.this;
                    ShrinkGridView.c(shrinkGridView4, "move dy=" + dy + ",max=" + ShrinkGridView.this.p4);
                    ShrinkGridView shrinkGridView5 = ShrinkGridView.this;
                    int unused2 = shrinkGridView5.p4 = shrinkGridView5.a2 - ShrinkGridView.this.p2;
                    if (ShrinkGridView.this.p0 && dy < 0) {
                        dy = Math.min(Math.abs(dy), ShrinkGridView.this.p4);
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ShrinkGridView.this.f.getLayoutParams();
                        params.bottomMargin = dy - ShrinkGridView.this.p4;
                        ShrinkGridView.this.f.setLayoutParams(params);
                    }
                    if (!ShrinkGridView.this.p0 && dy > 0) {
                        int dy2 = Math.min(Math.abs(dy), ShrinkGridView.this.p4);
                        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) ShrinkGridView.this.f.getLayoutParams();
                        params2.bottomMargin = -dy2;
                        ShrinkGridView.this.f.setLayoutParams(params2);
                    }
                    return true;
                default:
                    return false;
            }
        }
    }

    public class b implements View.OnTouchListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            OpItem item;
            OpItem opItem;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, motionEvent}, this, changeQuickRedirect, false, 5269, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            MotionEvent event = motionEvent;
            View view2 = view;
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case 0:
                    int touchPos = -1;
                    int childCount = ShrinkGridView.this.f.getChildCount();
                    int i = 0;
                    while (true) {
                        if (i < childCount) {
                            View childView = ShrinkGridView.this.f.getChildAt(i);
                            if (new Rect(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom()).contains((int) x, (int) y)) {
                                touchPos = i;
                            } else {
                                i++;
                            }
                        }
                    }
                    if (touchPos != -1 && (item = (OpItem) ShrinkGridView.this.z.get(touchPos)) == (opItem = OpItem.speakHalf)) {
                        boolean unused = ShrinkGridView.this.G4 = true;
                        boolean unused2 = ShrinkGridView.this.p1 = item == opItem;
                        if (ShrinkGridView.this.J4 != null) {
                            opItem.setWorking(true);
                            ShrinkGridView.this.x.notifyDataSetChanged();
                            ShrinkGridView.this.J4.m(true);
                            break;
                        }
                    } else {
                        return false;
                    }
                    break;
                case 1:
                case 3:
                    if (ShrinkGridView.this.G4 && ShrinkGridView.this.J4 != null) {
                        OpItem.speakHalf.setWorking(false);
                        ShrinkGridView.this.x.notifyDataSetChanged();
                        ShrinkGridView.this.J4.j(true);
                        boolean unused3 = ShrinkGridView.this.G4 = false;
                        break;
                    }
                case 2:
                    if (ShrinkGridView.this.G4) {
                        return true;
                    }
                    break;
            }
            return false;
        }
    }

    public class c implements AdapterView.OnItemClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        @SensorsDataInstrumented
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
            Object[] objArr = {adapterView, view, new Integer(position), new Long(j)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5270, new Class[]{AdapterView.class, View.class, Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
                return;
            }
            View view2 = view;
            AdapterView<?> adapterView2 = adapterView;
            OpItem item = ShrinkGridView.this.x.a(position);
            if (ShrinkGridView.this.J4 == null || !item.isStateEnabled()) {
                SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
                return;
            }
            switch (g.a[item.ordinal()]) {
                case 1:
                    ShrinkGridView.this.J4.c();
                    break;
                case 2:
                    ShrinkGridView.this.J4.v();
                    break;
                case 4:
                    ShrinkGridView.this.J4.w();
                    break;
                case 5:
                    ShrinkGridView.this.J4.o();
                    break;
                case 6:
                    ShrinkGridView.this.J4.s();
                    break;
                case 7:
                    ShrinkGridView.this.J4.t();
                    break;
                case 8:
                    ShrinkGridView.this.J4.h();
                    break;
                case 9:
                    ShrinkGridView.this.J4.u();
                    break;
                case 10:
                    ShrinkGridView.this.J4.k();
                    break;
            }
            SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
        }
    }

    public static /* synthetic */ class g {
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
        }
    }

    public void C(boolean anim) {
        if (!PatchProxy.proxy(new Object[]{new Byte(anim ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5258, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p0 = true;
            this.y.setImageResource(R$drawable.ic_navigator_up);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) this.f.getLayoutParams();
            int bottomMargin = params.bottomMargin;
            int dy = this.a2 - this.p2;
            if (anim) {
                ValueAnimator animator = ValueAnimator.ofInt(new int[]{bottomMargin, -dy});
                animator.setDuration(150);
                animator.addUpdateListener(new d());
                animator.start();
                return;
            }
            params.bottomMargin = -dy;
            this.f.setLayoutParams(params);
        }
    }

    public class d implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        d() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5271, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int margin = ((Integer) animation.getAnimatedValue()).intValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ShrinkGridView.this.f.getLayoutParams();
                params.bottomMargin = margin;
                ShrinkGridView.this.f.setLayoutParams(params);
            }
        }
    }

    public void x() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5259, new Class[0], Void.TYPE).isSupported) {
            this.p0 = false;
            this.y.setImageResource(R$drawable.ic_navigator_down);
            ValueAnimator animator = ValueAnimator.ofInt(new int[]{((LinearLayout.LayoutParams) this.f.getLayoutParams()).bottomMargin, 0});
            animator.setDuration(200);
            animator.addUpdateListener(new e());
            animator.start();
        }
    }

    public class e implements ValueAnimator.AnimatorUpdateListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 5272, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                int margin = ((Integer) animation.getAnimatedValue()).intValue();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ShrinkGridView.this.f.getLayoutParams();
                params.bottomMargin = margin;
                ShrinkGridView.this.f.setLayoutParams(params);
            }
        }
    }

    public void setDataList(List<OpItem> datas) {
        if (!PatchProxy.proxy(new Object[]{datas}, this, changeQuickRedirect, false, 5260, new Class[]{List.class}, Void.TYPE).isSupported) {
            this.z.clear();
            this.z.addAll(datas);
            this.x.notifyDataSetChanged();
            if (datas.size() > 5) {
                this.q.setVisibility(0);
                int[] viewHeight = y(this.f, 5);
                this.a2 = viewHeight[0];
                this.p2 = viewHeight[1];
                if (this.a1) {
                    C(false);
                } else {
                    z();
                }
            } else {
                z();
            }
        }
    }

    public void z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5261, new Class[0], Void.TYPE).isSupported) {
            this.q.setVisibility(8);
        }
    }

    public static class h extends BaseAdapter {
        public static ChangeQuickRedirect changeQuickRedirect;
        private ArrayList<OpItem> c;
        private Context d;

        public /* bridge */ /* synthetic */ Object getItem(int i) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 5277, new Class[]{Integer.TYPE}, Object.class);
            return proxy.isSupported ? proxy.result : a(i);
        }

        public h(ArrayList<OpItem> list, Context context) {
            this.c = list;
            this.d = context;
        }

        public int getCount() {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5274, new Class[0], Integer.TYPE);
            return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.size();
        }

        public OpItem a(int position) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 5275, new Class[]{Integer.TYPE}, OpItem.class);
            return proxy.isSupported ? (OpItem) proxy.result : this.c.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup viewGroup) {
            a viewHolder;
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(position), convertView, viewGroup}, this, changeQuickRedirect, false, 5276, new Class[]{Integer.TYPE, View.class, ViewGroup.class}, View.class);
            if (proxy.isSupported) {
                return (View) proxy.result;
            }
            if (convertView == null) {
                viewHolder = new a();
                convertView = LayoutInflater.from(this.d).inflate(R$layout.item_grid, (ViewGroup) null);
                viewHolder.a = (LDSTextView) convertView.findViewById(R$id.tv_name);
                viewHolder.b = (ImageView) convertView.findViewById(R$id.iv_op_icon);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (a) convertView.getTag();
            }
            OpItem opItem = this.c.get(position);
            viewHolder.a.setText(opItem.getDescName());
            if (opItem.isStateEnabled()) {
                viewHolder.b.setImageResource(opItem.isWorking() ? opItem.getWorkingResId() : opItem.getResId());
                if (opItem.isWorking()) {
                    viewHolder.a.setTextColor(this.d.getResources().getColor(opItem.getWorkingColor()));
                    if (opItem == OpItem.record) {
                        SpannableString spanString = new SpannableString(opItem.getDescName());
                        spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5515B")), 0, 3, 33);
                        viewHolder.a.setText(spanString);
                    }
                } else {
                    viewHolder.a.setTextColor(this.d.getResources().getColor(opItem.getNormalColor()));
                }
            } else {
                viewHolder.a.setTextColor(this.d.getResources().getColor(opItem.getDisabledColorId()));
                viewHolder.b.setImageResource(opItem.getDisabledResId());
            }
            return convertView;
        }

        public class a {
            public LDSTextView a;
            public ImageView b;

            a() {
            }
        }
    }

    public void setListViewHeightBasedOnChildren(GridView listView) {
        if (!PatchProxy.proxy(new Object[]{listView}, this, changeQuickRedirect, false, 5262, new Class[]{GridView.class}, Void.TYPE).isSupported) {
            int[] viewHeight = y(this.f, 5);
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = viewHeight[0];
            ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
            listView.setLayoutParams(params);
        }
    }

    private int[] y(GridView listView, int col) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{listView, new Integer(col)}, this, changeQuickRedirect, false, 5263, new Class[]{GridView.class, Integer.TYPE}, int[].class);
        if (proxy.isSupported) {
            return (int[]) proxy.result;
        }
        int[] viewHeight = new int[2];
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return viewHeight;
        }
        int totalHeight = 0;
        int i = 0;
        while (i < listAdapter.getCount()) {
            int lineMaxHeight = 0;
            for (int j = 0; j < col; j++) {
                if (i + j < listAdapter.getCount()) {
                    View listItem = listAdapter.getView(i + j, (View) null, listView);
                    listItem.measure(0, 0);
                    int measuredHeight = listItem.getMeasuredHeight();
                    Log.e("ShrinkGridView", "getViewHeight line:" + (i + j) + "=" + measuredHeight);
                    if (measuredHeight > lineMaxHeight) {
                        lineMaxHeight = measuredHeight;
                    }
                }
            }
            if (i == 0) {
                viewHeight[1] = lineMaxHeight;
            }
            totalHeight += lineMaxHeight;
            i += col;
        }
        viewHeight[0] = totalHeight;
        return viewHeight;
    }

    private void B(String s) {
        if (!PatchProxy.proxy(new Object[]{s}, this, changeQuickRedirect, false, 5264, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.d("ShrinkGridView", s);
        }
    }

    public void setOnEventCallback(com.leedarson.utils.h eventCallback) {
        this.J4 = eventCallback;
    }

    public void update(Observable observable, Object arg) {
        if (!PatchProxy.proxy(new Object[]{observable, arg}, this, changeQuickRedirect, false, 5265, new Class[]{Observable.class, Object.class}, Void.TYPE).isSupported) {
            a.b g2 = timber.log.a.g("NewLive");
            g2.a("arg:" + arg.toString(), new Object[0]);
            try {
                JSONObject dataObj = (JSONObject) arg;
                this.B4 = dataObj.getBoolean("isLightOn");
                this.C4 = dataObj.getBoolean("isAlarm");
                this.A4 = dataObj.getBoolean("isTalking");
                this.z4 = dataObj.getBoolean("isRecording");
                this.E4 = dataObj.getString("recordTime");
                this.D4 = dataObj.getBoolean("isTrackingMode");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            post(new f());
        }
    }

    public class f implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5273, new Class[0], Void.TYPE).isSupported) {
                OpItem.speakFull.setWorking(ShrinkGridView.this.A4);
                OpItem.light.setWorking(ShrinkGridView.this.B4);
                OpItem.alarm.setWorking(ShrinkGridView.this.C4);
                OpItem.tracking.setWorking(ShrinkGridView.this.D4);
                if (ShrinkGridView.this.z4) {
                    OpItem opItem = OpItem.record;
                    opItem.setWorking(true);
                    opItem.setDescName(String.format(Locale.US, PubUtils.getString(ShrinkGridView.this.getContext(), R$string.recording_timestr), new Object[]{ShrinkGridView.this.E4}));
                } else {
                    OpItem opItem2 = OpItem.record;
                    opItem2.setWorking(false);
                    opItem2.setDescName(PubUtils.getString(ShrinkGridView.this.getContext(), R$string.record));
                }
                ShrinkGridView.this.x.notifyDataSetChanged();
            }
        }
    }
}
