package com.leedarson.newui.cloud_play_back.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;

public class PlayerBackMenuStatueView extends RelativeLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    ImageView A4;
    LinearLayout B4;
    LinearLayout C4;
    private b D4;
    private a E4;
    private boolean F4;
    public io.reactivex.processors.b<Integer> a1;
    ImageView a2;
    private final Context c;
    View d;
    public io.reactivex.processors.b<b> f;
    public io.reactivex.processors.b<a> p0;
    ImageView p1;
    ImageView p2;
    ImageView p3;
    ImageView p4;
    public io.reactivex.processors.b<Integer> q;
    public io.reactivex.processors.b<Integer> x;
    public io.reactivex.processors.b<Integer> y;
    public io.reactivex.processors.b<Integer> z;
    ImageView z4;

    public enum a {
        START_REC,
        END_REC;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public enum b {
        START_REC,
        END_REC;
        
        public static ChangeQuickRedirect changeQuickRedirect;
    }

    public PlayerBackMenuStatueView(Context context) {
        this(context, (AttributeSet) null);
    }

    public PlayerBackMenuStatueView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerBackMenuStatueView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PlayerBackMenuStatueView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.f = io.reactivex.processors.b.Y();
        this.q = io.reactivex.processors.b.Y();
        this.x = io.reactivex.processors.b.Y();
        this.y = io.reactivex.processors.b.Y();
        this.z = io.reactivex.processors.b.Y();
        this.p0 = io.reactivex.processors.b.Y();
        this.a1 = io.reactivex.processors.b.Y();
        this.D4 = b.END_REC;
        this.E4 = a.END_REC;
        this.F4 = false;
        this.c = context;
        d();
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4053, new Class[0], Void.TYPE).isSupported) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(-1, -2);
            View inflate = LayoutInflater.from(this.c).inflate(R$layout.player_back_menu_layout, (ViewGroup) null);
            this.d = inflate;
            this.p1 = (ImageView) inflate.findViewById(R$id.iv_menu_record);
            this.a2 = (ImageView) this.d.findViewById(R$id.iv_menu_snap);
            this.p2 = (ImageView) this.d.findViewById(R$id.iv_album);
            this.p3 = (ImageView) this.d.findViewById(R$id.iv_share);
            this.p4 = (ImageView) this.d.findViewById(R$id.iv_delete);
            this.z4 = (ImageView) this.d.findViewById(R$id.iv_menu_focus);
            this.A4 = (ImageView) this.d.findViewById(R$id.iv_more);
            this.B4 = (LinearLayout) this.d.findViewById(R$id.lnDeleteBox);
            this.C4 = (LinearLayout) this.d.findViewById(R$id.layout_focus);
            addView(this.d, params);
            c();
        }
    }

    /* access modifiers changed from: package-private */
    public void c() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4054, new Class[0], Void.TYPE).isSupported) {
            this.p1.setOnClickListener(this);
            this.a2.setOnClickListener(this);
            this.p2.setOnClickListener(this);
            this.p3.setOnClickListener(this);
            this.p4.setOnClickListener(this);
            this.z4.setOnClickListener(this);
            this.A4.setOnClickListener(this);
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4055, new Class[0], Void.TYPE).isSupported) {
            this.p1.setEnabled(false);
            this.a2.setEnabled(false);
            this.p3.setEnabled(false);
            this.p4.setEnabled(false);
            this.z4.setEnabled(false);
        }
    }

    public void b() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 4056, new Class[0], Void.TYPE).isSupported) {
            this.p1.setEnabled(true);
            this.a2.setEnabled(true);
            this.p2.setEnabled(true);
            this.p3.setEnabled(true);
            this.p4.setEnabled(true);
            this.z4.setEnabled(true);
        }
    }

    public void setVideoRecordEnable(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4057, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.p1.setEnabled(flag.booleanValue());
        }
    }

    public void setVideoRecordSelected(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4058, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.p1.setSelected(flag.booleanValue());
        }
    }

    public void setSnapShotEnable(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4059, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.a2.setEnabled(flag.booleanValue());
        }
    }

    public void setAlbumEnable(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4060, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.p2.setEnabled(flag.booleanValue());
        }
    }

    public void setShareEnable(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4061, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.p3.setEnabled(flag.booleanValue());
        }
    }

    public void setDeleteEnable(Boolean flag) {
        if (!PatchProxy.proxy(new Object[]{flag}, this, changeQuickRedirect, false, 4062, new Class[]{Boolean.class}, Void.TYPE).isSupported) {
            this.p4.setEnabled(flag.booleanValue());
        }
    }

    public void setFocusEnable(boolean enable) {
        Object[] objArr = {new Byte(enable ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4063, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.z4.setEnabled(enable);
        }
    }

    public void f(b state) {
        if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 4064, new Class[]{b.class}, Void.TYPE).isSupported) {
            this.D4 = state;
            if (state == b.END_REC) {
                this.p1.setSelected(false);
                return;
            }
            setVideoRecordEnable(true);
            this.p1.setSelected(true);
        }
    }

    public void e(a state) {
        if (!PatchProxy.proxy(new Object[]{state}, this, changeQuickRedirect, false, 4065, new Class[]{a.class}, Void.TYPE).isSupported) {
            this.E4 = state;
            if (state == a.END_REC) {
                this.z4.setSelected(false);
            } else {
                this.z4.setSelected(true);
            }
        }
    }

    public void setDisAbleDelete(boolean flag) {
        Object[] objArr = {new Byte(flag ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4066, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.F4 = flag;
            if (flag) {
                this.d.findViewById(R$id.lnDeleteBox).setVisibility(8);
            }
        }
    }

    public void setFocusVisibility(int visibility) {
        Object[] objArr = {new Integer(visibility)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 4067, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.C4.setVisibility(visibility);
        }
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 4068, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        int viewId = v.getId();
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (viewId == R$id.iv_menu_record) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickVideo");
            io.reactivex.processors.b<b> bVar = this.f;
            b bVar2 = this.D4;
            b bVar3 = b.START_REC;
            if (bVar2 == bVar3) {
                bVar3 = b.END_REC;
            }
            bVar.onNext(bVar3);
        } else if (viewId == R$id.iv_menu_snap) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickPhoto");
            this.q.onNext(1);
        } else if (viewId == R$id.iv_album) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickAlbum");
            this.x.onNext(1);
        } else if (viewId == R$id.iv_share) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickShare");
            this.y.onNext(1);
        } else if (viewId == R$id.iv_delete) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsClickDelete");
            this.z.onNext(1);
        } else if (viewId == R$id.iv_menu_focus) {
            io.reactivex.processors.b<a> bVar4 = this.p0;
            a aVar = this.E4;
            a aVar2 = a.START_REC;
            if (aVar == aVar2) {
                aVar2 = a.END_REC;
            }
            bVar4.onNext(aVar2);
        } else if (viewId == R$id.iv_more) {
            com.leedarson.log.sensorsdata.a.b().o("IPCEventsMoremenu");
            this.a1.onNext(1);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
