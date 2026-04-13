package com.leedarson.newui.smartwidget.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.exifinterface.media.ExifInterface;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.bean.SecurityEventEntity;
import com.leedarson.newui.repos.beans.EventListItemBean;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.data.adapter.DbParams;
import java.util.HashMap;
import java.util.List;

public class SecurityEventAdapter extends BaseMultiItemQuickAdapter<SecurityEventEntity, BaseViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int O4;
    private int P4 = -1;
    private HashMap<String, Integer> Q4 = new HashMap<>();
    c R4;

    public interface c {
        void a(int i);
    }

    public /* bridge */ /* synthetic */ void d(@NonNull BaseViewHolder baseViewHolder, Object obj) {
        Class[] clsArr = {BaseViewHolder.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, obj}, this, changeQuickRedirect, false, 4788, clsArr, Void.TYPE).isSupported) {
            x(baseViewHolder, (SecurityEventEntity) obj);
        }
    }

    public void B(int currentSelectIndex) {
        this.P4 = currentSelectIndex;
    }

    public int z() {
        return this.P4;
    }

    public void setAdapterViewClickListener(c adapterViewClickListener) {
        this.R4 = adapterViewClickListener;
    }

    public SecurityEventAdapter(List<SecurityEventEntity> data, int recycleViewWidth) {
        super(data);
        this.O4 = recycleViewWidth;
        A();
        addItemType(0, R$layout.security_playback_event_item);
        addItemType(1, R$layout.security_playback_event_all_item);
        addItemType(2, R$layout.security_playback_event_no_more_item);
        addItemType(3, R$layout.security_playback_event_empty_holder);
    }

    public void x(@NonNull BaseViewHolder baseViewHolder, SecurityEventEntity securityEventEntity) {
        Class[] clsArr = {BaseViewHolder.class, SecurityEventEntity.class};
        if (!PatchProxy.proxy(new Object[]{baseViewHolder, securityEventEntity}, this, changeQuickRedirect, false, 4785, clsArr, Void.TYPE).isSupported) {
            int position = getItemPosition(securityEventEntity);
            switch (securityEventEntity.getItemType()) {
                case 0:
                    View rlEventContainer = baseViewHolder.findView(R$id.rlEventContainer);
                    rlEventContainer.setOnClickListener(new a(position));
                    y((LinearLayout) baseViewHolder.findView(R$id.lnIconInfo), position, securityEventEntity.getEventListItemBean());
                    if (position == this.P4) {
                        rlEventContainer.setBackgroundResource(R$drawable.security_event_item_bg_select);
                    } else {
                        rlEventContainer.setBackgroundResource(R$drawable.security_event_item_bg_unselect);
                    }
                    baseViewHolder.setText(R$id.tvEventTime, (CharSequence) e.d(BaseApplication.b(), securityEventEntity.getEventListItemBean().eventTime, "hh:mm"));
                    return;
                case 1:
                    TextView tvAllEvent = (TextView) baseViewHolder.findView(R$id.tvAllEvent);
                    ViewGroup.LayoutParams layoutParams = tvAllEvent.getLayoutParams();
                    layoutParams.width = (this.O4 / 2) - 20;
                    tvAllEvent.setLayoutParams(layoutParams);
                    tvAllEvent.requestLayout();
                    baseViewHolder.findView(R$id.rlAllEventContainer).setOnClickListener(new b(position));
                    return;
                case 3:
                    TextView tvLive = (TextView) baseViewHolder.findView(R$id.tvLive);
                    ViewGroup.LayoutParams layoutParams1 = tvLive.getLayoutParams();
                    layoutParams1.width = (this.O4 / 2) + 20;
                    tvLive.setLayoutParams(layoutParams1);
                    tvLive.requestLayout();
                    return;
                default:
                    return;
            }
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ int c;

        a(int i) {
            this.c = i;
        }

        @SensorsDataInstrumented
        public void onClick(View v) {
            if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 4789, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            if (com.leedarson.utils.b.a(v, 500)) {
                SensorsDataAutoTrackHelper.trackViewOnClick(v);
                return;
            }
            c cVar = SecurityEventAdapter.this.R4;
            if (cVar != null) {
                cVar.a(this.c);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
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
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4790, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            c cVar = SecurityEventAdapter.this.R4;
            if (cVar != null) {
                cVar.a(this.c);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private void A() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4786, new Class[0], Void.TYPE).isSupported) {
            HashMap<String, Integer> hashMap = this.Q4;
            int i = R$drawable.vector_event_icon_sound_detected;
            hashMap.put("0", Integer.valueOf(i));
            this.Q4.put("1", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_motion_detect));
            this.Q4.put(ExifInterface.GPS_MEASUREMENT_3D, Integer.valueOf(R$drawable.vector_event_icon_temperature_alarm));
            this.Q4.put("4", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_human_detect));
            this.Q4.put("5", Integer.valueOf(R$drawable.vector_event_icon_lens_cover_alarm));
            this.Q4.put("6", Integer.valueOf(R$drawable.vector_event_icon_glass_break_alarm));
            this.Q4.put("7", Integer.valueOf(R$drawable.vector_event_icon_smoke_detected));
            this.Q4.put("8", Integer.valueOf(R$drawable.vector_event_icon_sd_full));
            this.Q4.put(DbParams.GZIP_DATA_ENCRYPT, Integer.valueOf(R$drawable.vector_event_icon_storage_full));
            this.Q4.put("10", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_linked_alarm));
            this.Q4.put("13", Integer.valueOf(R$drawable.vector_event_icon_alarming));
            this.Q4.put("14", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_cat));
            this.Q4.put("15", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_car));
            this.Q4.put("16", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_fire));
            this.Q4.put("17", Integer.valueOf(R$drawable.vector_event_event_icon_fall));
            this.Q4.put("18", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_package));
            this.Q4.put("19", Integer.valueOf(R$drawable.vector_event_icon_baby_cry));
            this.Q4.put("24", Integer.valueOf(i));
            this.Q4.put("25", Integer.valueOf(R$drawable.vector_drawable_event_icon_security_pet_detect));
            this.Q4.put("26", Integer.valueOf(R$drawable.vector_event_icon_animal_detect));
            this.Q4.put("27", Integer.valueOf(R$drawable.vector_event_icon_posture_detect));
            this.Q4.put("28", Integer.valueOf(R$drawable.vector_event_icon_crossout_detect));
            this.Q4.put("29", Integer.valueOf(R$drawable.vector_event_icon_tamper_detect));
            this.Q4.put("30", Integer.valueOf(R$drawable.vector_event_icon_lingered_detect));
        }
    }

    private void y(LinearLayout lnIconInfo, int _position, EventListItemBean _data) {
        if (!PatchProxy.proxy(new Object[]{lnIconInfo, new Integer(_position), _data}, this, changeQuickRedirect, false, 4787, new Class[]{LinearLayout.class, Integer.TYPE, EventListItemBean.class}, Void.TYPE).isSupported) {
            lnIconInfo.removeAllViews();
            if (_data.eventCodes != null) {
                for (int i = 0; i < _data.eventCodes.size(); i++) {
                    RelativeLayout rlIconItemContainer = (RelativeLayout) LayoutInflater.from(getContext()).inflate(R$layout.security_playback_event_icon_item, (ViewGroup) null);
                    ImageView imgIcon = (ImageView) rlIconItemContainer.findViewById(R$id.imgIcon);
                    String eventCode = _data.eventCodes.get(i);
                    if (!TextUtils.isEmpty(eventCode)) {
                        int resourceId = this.Q4.get(eventCode) == null ? 0 : this.Q4.get(eventCode).intValue();
                        if (_position == this.P4) {
                            rlIconItemContainer.setBackgroundResource(R$drawable.security_event_item_icon_event_selected_bg);
                        } else {
                            rlIconItemContainer.setBackgroundResource(R$drawable.security_event_item_icon_event_default_bg);
                        }
                        if (resourceId != 0) {
                            imgIcon.setBackgroundResource(resourceId);
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                            params.rightMargin = com.leedarson.view.a.a(getContext(), 5.0f);
                            lnIconInfo.addView(rlIconItemContainer, params);
                        } else {
                            return;
                        }
                    }
                }
            }
        }
    }
}
