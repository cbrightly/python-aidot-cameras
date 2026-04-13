package com.leedarson.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.bean.FilterBean;
import com.leedarson.bean.IpcDeviceBean;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public Context a;
    private final int b = 99;
    private final int c = 100;
    private String d;
    /* access modifiers changed from: private */
    public ArrayList<FilterBean> e;
    private IpcDeviceBean f;
    private a g;

    public interface a {
        void onItemClick(View view, int i);
    }

    static /* synthetic */ void a(FilterAdapter x0, View x1) {
        Class[] clsArr = {FilterAdapter.class, View.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1}, (Object) null, changeQuickRedirect, true, 65, clsArr, Void.TYPE).isSupported) {
            x0.g(x1);
        }
    }

    public FilterAdapter(Context context, ArrayList<FilterBean> list, IpcDeviceBean currentDevice) {
        this.a = context;
        this.e = list;
        this.f = currentDevice;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(viewType)}, this, changeQuickRedirect, false, 59, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        if (viewType == 99) {
            return new TitleHolder(LayoutInflater.from(this.a).inflate(R$layout.adapter_activity_label_title, (ViewGroup) null));
        }
        return new LabelHolder(LayoutInflater.from(this.a).inflate(R$layout.adapter_activity_label_content, (ViewGroup) null), this.g);
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 60, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (getItemViewType(position) == 99) {
                ((TitleHolder) holder).b(position);
            } else {
                ((LabelHolder) holder).b(position);
            }
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 61, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.e.size();
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 62, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.e.get(position).getType().contains("title")) {
            return 99;
        }
        return 100;
    }

    public List<FilterBean> d() {
        return this.e;
    }

    public class TitleHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public final LDSTextView a;
        private final ImageView b;

        public TitleHolder(View inflate) {
            super(inflate);
            this.a = (LDSTextView) inflate.findViewById(R$id.tv_title);
            ImageView imageView = (ImageView) inflate.findViewById(R$id.iv_about);
            this.b = imageView;
            imageView.setOnClickListener(new a(FilterAdapter.this));
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ FilterAdapter c;

            a(FilterAdapter filterAdapter) {
                this.c = filterAdapter;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 70, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View view2 = view;
                Log.d("TitleHolder", "onClick--> position = " + TitleHolder.this.getPosition());
                TitleHolder titleHolder = TitleHolder.this;
                FilterAdapter.a(FilterAdapter.this, titleHolder.a);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }

        /* JADX WARNING: Can't fix incorrect switch cases order */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0059, code lost:
            if (r2.equals("title1") != false) goto L_0x0068;
         */
        @android.annotation.SuppressLint({"WrongConstant"})
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void b(int r10) {
            /*
                r9 = this;
                r0 = 1
                java.lang.Object[] r1 = new java.lang.Object[r0]
                java.lang.Integer r2 = new java.lang.Integer
                r2.<init>(r10)
                r8 = 0
                r1[r8] = r2
                com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
                java.lang.Class[] r6 = new java.lang.Class[r0]
                java.lang.Class r2 = java.lang.Integer.TYPE
                r6[r8] = r2
                java.lang.Class r7 = java.lang.Void.TYPE
                r4 = 0
                r5 = 69
                r2 = r9
                com.meituan.robust.PatchProxyResult r1 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
                boolean r1 = r1.isSupported
                if (r1 == 0) goto L_0x0022
                return
            L_0x0022:
                r1 = r9
                com.leedarson.adapter.FilterAdapter r2 = com.leedarson.adapter.FilterAdapter.this
                java.util.ArrayList r2 = r2.e
                java.lang.Object r2 = r2.get(r10)
                com.leedarson.bean.FilterBean r2 = (com.leedarson.bean.FilterBean) r2
                java.lang.String r2 = r2.getType()
                r3 = -1
                int r4 = r2.hashCode()
                switch(r4) {
                    case -873453352: goto L_0x005c;
                    case -873453351: goto L_0x0052;
                    case -873453350: goto L_0x0047;
                    case -873453349: goto L_0x003c;
                    default: goto L_0x003b;
                }
            L_0x003b:
                goto L_0x0067
            L_0x003c:
                java.lang.String r0 = "title3"
                boolean r0 = r2.equals(r0)
                if (r0 == 0) goto L_0x003b
                r0 = 3
                goto L_0x0068
            L_0x0047:
                java.lang.String r0 = "title2"
                boolean r0 = r2.equals(r0)
                if (r0 == 0) goto L_0x003b
                r0 = 2
                goto L_0x0068
            L_0x0052:
                java.lang.String r4 = "title1"
                boolean r2 = r2.equals(r4)
                if (r2 == 0) goto L_0x003b
                goto L_0x0068
            L_0x005c:
                java.lang.String r0 = "title0"
                boolean r0 = r2.equals(r0)
                if (r0 == 0) goto L_0x003b
                r0 = r8
                goto L_0x0068
            L_0x0067:
                r0 = r3
            L_0x0068:
                r2 = 8
                switch(r0) {
                    case 0: goto L_0x00bd;
                    case 1: goto L_0x00a6;
                    case 2: goto L_0x008f;
                    case 3: goto L_0x0078;
                    default: goto L_0x006d;
                }
            L_0x006d:
                android.widget.ImageView r0 = r1.b
                r0.setVisibility(r2)
                com.leedarson.base.views.common.LDSTextView r0 = r1.a
                r0.setVisibility(r2)
                goto L_0x00d4
            L_0x0078:
                com.leedarson.base.views.common.LDSTextView r0 = r1.a
                com.leedarson.adapter.FilterAdapter r3 = com.leedarson.adapter.FilterAdapter.this
                android.content.Context r3 = r3.a
                int r4 = com.leedarson.R$string.lds_area_event
                java.lang.String r3 = com.leedarson.serviceinterface.utils.PubUtils.getString(r3, r4)
                r0.setText(r3)
                android.widget.ImageView r0 = r1.b
                r0.setVisibility(r2)
                goto L_0x00d4
            L_0x008f:
                com.leedarson.base.views.common.LDSTextView r0 = r1.a
                com.leedarson.adapter.FilterAdapter r3 = com.leedarson.adapter.FilterAdapter.this
                android.content.Context r3 = r3.a
                int r4 = com.leedarson.R$string.sound
                java.lang.String r3 = com.leedarson.serviceinterface.utils.PubUtils.getString(r3, r4)
                r0.setText(r3)
                android.widget.ImageView r0 = r1.b
                r0.setVisibility(r2)
                goto L_0x00d4
            L_0x00a6:
                com.leedarson.base.views.common.LDSTextView r0 = r1.a
                com.leedarson.adapter.FilterAdapter r2 = com.leedarson.adapter.FilterAdapter.this
                android.content.Context r2 = r2.a
                int r3 = com.leedarson.R$string.event
                java.lang.String r2 = com.leedarson.serviceinterface.utils.PubUtils.getString(r2, r3)
                r0.setText(r2)
                android.widget.ImageView r0 = r1.b
                r0.setVisibility(r8)
                goto L_0x00d4
            L_0x00bd:
                com.leedarson.base.views.common.LDSTextView r0 = r1.a
                com.leedarson.adapter.FilterAdapter r3 = com.leedarson.adapter.FilterAdapter.this
                android.content.Context r3 = r3.a
                int r4 = com.leedarson.R$string.camera
                java.lang.String r3 = com.leedarson.serviceinterface.utils.PubUtils.getString(r3, r4)
                r0.setText(r3)
                android.widget.ImageView r0 = r1.b
                r0.setVisibility(r2)
            L_0x00d4:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.leedarson.adapter.FilterAdapter.TitleHolder.b(int):void");
        }
    }

    public class LabelHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public LDSTextView a;
        private final LinearLayout b;
        a c;

        public LabelHolder(View inflate, a mListener) {
            super(inflate);
            this.a = (LDSTextView) inflate.findViewById(R$id.tv_content);
            this.b = (LinearLayout) inflate.findViewById(R$id.llContent);
            this.c = mListener;
            inflate.setOnClickListener(new a(FilterAdapter.this, mListener));
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ FilterAdapter c;
            final /* synthetic */ a d;

            a(FilterAdapter filterAdapter, a aVar) {
                this.c = filterAdapter;
                this.d = aVar;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 67, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View v = view;
                a aVar = this.d;
                if (aVar != null) {
                    aVar.onItemClick(v, LabelHolder.this.getAdapterPosition());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }

        public void b(int position) {
            if (!PatchProxy.proxy(new Object[]{new Integer(position)}, this, changeQuickRedirect, false, 66, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
                FilterBean filterBean = (FilterBean) FilterAdapter.this.e.get(position);
                String deviceName = filterBean.getDesc();
                if (filterBean.isSelect()) {
                    this.a.setSelected(true);
                } else {
                    this.a.setSelected(false);
                }
                this.a.setText(deviceName);
                this.a.setOnClickListener(new b(filterBean));
            }
        }

        public class b implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ FilterBean c;

            b(FilterBean filterBean) {
                this.c = filterBean;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 68, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View v = view;
                if (LabelHolder.this.a.isSelected()) {
                    LabelHolder.this.a.setSelected(false);
                    this.c.setSelect(false);
                } else {
                    LabelHolder.this.a.setSelected(true);
                    this.c.setSelect(true);
                }
                LabelHolder labelHolder = LabelHolder.this;
                a aVar = labelHolder.c;
                if (aVar != null) {
                    aVar.onItemClick(v, labelHolder.getAdapterPosition());
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public void setOnItemClickListener(a listener) {
        this.g = listener;
    }

    private void g(View anchorView) {
        if (!PatchProxy.proxy(new Object[]{anchorView}, this, changeQuickRedirect, false, 63, new Class[]{View.class}, Void.TYPE).isSupported) {
            PopupWindow mPopupWindow = new PopupWindow(LayoutInflater.from(this.a).inflate(R$layout.event_tip_dialog, (ViewGroup) null), -2, -2, true);
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
            mPopupWindow.showAsDropDown(anchorView, 0, 16);
        }
    }

    public void f(ArrayList<FilterBean> list) {
        if (!PatchProxy.proxy(new Object[]{list}, this, changeQuickRedirect, false, 64, new Class[]{ArrayList.class}, Void.TYPE).isSupported) {
            this.e = list;
            notifyDataSetChanged();
        }
    }

    public void e(String eventType) {
        this.d = eventType;
    }
}
