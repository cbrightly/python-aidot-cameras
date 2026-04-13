package com.leedarson.newui.smartwidget.widgets.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.HashMap;

public class SecurityEventItemAdapter extends LDSBaseRecAdapter<EventListItemBean> {
    public static ChangeQuickRedirect changeQuickRedirect;
    /* access modifiers changed from: private */
    public int c;
    /* access modifiers changed from: private */
    public ViewGroup d;
    a e;

    public interface a {
        void a(int i);
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{parent, new Integer(viewType)}, this, changeQuickRedirect, false, 4828, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        this.d = parent;
        if (viewType == 0) {
            return new EventViewHolder(LayoutInflater.from(this.b).inflate(R$layout.security_playback_event_item, parent, false));
        }
        if (viewType == 1) {
            return new AllEventViewHolder(LayoutInflater.from(this.b).inflate(R$layout.security_playback_event_all_item, parent, false));
        }
        if (viewType == 2) {
            return new NoMoreEventViewHolder(LayoutInflater.from(this.b).inflate(R$layout.security_playback_event_no_more_item, parent, false));
        }
        return new EmptyFooterViewHolder(LayoutInflater.from(this.b).inflate(R$layout.security_playback_event_empty_holder, parent, false));
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 4829, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (holder instanceof EventViewHolder) {
                ((EventViewHolder) holder).a(position, (EventListItemBean) this.a.get(position));
            } else if (holder instanceof AllEventViewHolder) {
                ((AllEventViewHolder) holder).a(position, (EventListItemBean) this.a.get(position));
            } else if (holder instanceof NoMoreEventViewHolder) {
                ((NoMoreEventViewHolder) holder).a(position, (EventListItemBean) this.a.get(position));
            } else if (holder instanceof EmptyFooterViewHolder) {
                ((EmptyFooterViewHolder) holder).a(position, (EventListItemBean) this.a.get(position));
            }
            super.onBindViewHolder(holder, position);
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4830, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.a.size();
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4831, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        if (this.a.size() > 0) {
            return ((EventListItemBean) this.a.get(position)).view_type;
        }
        return super.getItemViewType(position);
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        RelativeLayout a;
        LDSTextView b;
        LinearLayout c;
        private int d;
        private EventListItemBean e;
        private HashMap<String, Integer> f = new HashMap<>();

        public EventViewHolder(View itemView) {
            super(itemView);
            d();
            c();
        }

        private void c() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4837, new Class[0], Void.TYPE).isSupported) {
                this.f.put("1", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_motion_detect));
                this.f.put("4", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_human_detect));
                this.f.put("14", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_cat));
                this.f.put("15", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_car));
                this.f.put("16", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_fire));
                this.f.put("18", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_package));
                this.f.put("19", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_face));
                this.f.put("25", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_pet_detect));
            }
        }

        private void d() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4838, new Class[0], Void.TYPE).isSupported) {
                this.a = (RelativeLayout) this.itemView.findViewById(R$id.rlEventContainer);
                this.b = (LDSTextView) this.itemView.findViewById(R$id.tvEventTime);
                this.c = (LinearLayout) this.itemView.findViewById(R$id.lnIconInfo);
                this.a.setOnClickListener(new b(this));
            }
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: e */
        public /* synthetic */ void f(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4841, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            a aVar = SecurityEventItemAdapter.this.e;
            if (aVar != null) {
                aVar.a(this.d);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public void a(int position, EventListItemBean data) {
            Object[] objArr = {new Integer(position), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4839, new Class[]{Integer.TYPE, EventListItemBean.class}, Void.TYPE).isSupported) {
                this.e = data;
                this.d = position;
                b();
                if (position == SecurityEventItemAdapter.this.c) {
                    this.a.setBackgroundResource(R$drawable.security_event_item_bg_select);
                } else {
                    this.a.setBackgroundResource(R$drawable.security_event_item_bg_unselect);
                }
                this.b.setText(e.d(BaseApplication.b(), this.e.eventTime, "hh:mm"));
            }
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4840, new Class[0], Void.TYPE).isSupported) {
                this.c.removeAllViews();
                if (this.e.eventCodes != null) {
                    for (int i = 0; i < this.e.eventCodes.size(); i++) {
                        RelativeLayout rlIconItemContainer = (RelativeLayout) LayoutInflater.from(SecurityEventItemAdapter.this.b).inflate(R$layout.security_playback_event_icon_item, (ViewGroup) null);
                        ImageView imgIcon = (ImageView) rlIconItemContainer.findViewById(R$id.imgIcon);
                        String eventCode = this.e.eventCodes.get(i);
                        if (!TextUtils.isEmpty(eventCode)) {
                            Integer resourceId = this.f.get(eventCode);
                            if (this.d == SecurityEventItemAdapter.this.c) {
                                rlIconItemContainer.setBackgroundResource(R$drawable.security_event_item_icon_event_selected_bg);
                            } else {
                                rlIconItemContainer.setBackgroundResource(R$drawable.security_event_item_icon_event_default_bg);
                            }
                            if (resourceId != null || resourceId.intValue() != 0) {
                                imgIcon.setBackgroundResource(resourceId.intValue());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                                params.leftMargin = com.leedarson.view.a.b(SecurityEventItemAdapter.this.b, 5.0f);
                                this.c.addView(rlIconItemContainer, params);
                            } else {
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public class AllEventViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        RelativeLayout a;
        LDSTextView b;
        private int c;
        private EventListItemBean d;

        public AllEventViewHolder(View itemView) {
            super(itemView);
            b();
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4832, new Class[0], Void.TYPE).isSupported) {
                this.a = (RelativeLayout) this.itemView.findViewById(R$id.rlAllEventContainer);
                this.b = (LDSTextView) this.itemView.findViewById(R$id.tvAllEvent);
                this.a.setOnClickListener(new a(this));
            }
        }

        /* access modifiers changed from: private */
        @SensorsDataInstrumented
        /* renamed from: c */
        public /* synthetic */ void d(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4834, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            a aVar = SecurityEventItemAdapter.this.e;
            if (aVar != null) {
                aVar.a(this.c);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }

        public void a(int position, EventListItemBean data) {
            Object[] objArr = {new Integer(position), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4833, new Class[]{Integer.TYPE, EventListItemBean.class}, Void.TYPE).isSupported) {
                this.c = position;
                this.d = data;
                if (SecurityEventItemAdapter.this.d != null) {
                    ViewGroup.LayoutParams layoutParams = this.a.getLayoutParams();
                    layoutParams.width = SecurityEventItemAdapter.this.d.getWidth() / 2;
                    this.a.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public class NoMoreEventViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        LDSTextView a;

        public NoMoreEventViewHolder(View itemView) {
            super(itemView);
            b();
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4842, new Class[0], Void.TYPE).isSupported) {
                this.a = (LDSTextView) this.itemView.findViewById(R$id.tvNoMoreEvent);
            }
        }

        public void a(int position, EventListItemBean data) {
        }
    }

    public class EmptyFooterViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        LDSTextView a;
        private int b;
        private EventListItemBean c;

        public EmptyFooterViewHolder(View itemView) {
            super(itemView);
            b();
        }

        private void b() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4835, new Class[0], Void.TYPE).isSupported) {
                this.a = (LDSTextView) this.itemView.findViewById(R$id.tvLive);
            }
        }

        public void a(int position, EventListItemBean data) {
            Object[] objArr = {new Integer(position), data};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4836, new Class[]{Integer.TYPE, EventListItemBean.class}, Void.TYPE).isSupported) {
                this.b = position;
                this.c = data;
                if (SecurityEventItemAdapter.this.d != null) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.a.getLayoutParams();
                    layoutParams.width = SecurityEventItemAdapter.this.d.getWidth() / 2;
                    this.a.setLayoutParams(layoutParams);
                }
            }
        }
    }
}
