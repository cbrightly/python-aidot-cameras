package com.leedarson.newui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.http.observer.l;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.e;
import com.leedarson.utils.i;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.reactivex.processors.b;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class VerPlayBackController extends LinearLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView c;
    private TextView d;
    private ImageView f;
    private i q;
    private b<Boolean> x;
    private io.reactivex.disposables.b y;

    public VerPlayBackController(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerPlayBackController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.x = b.Y();
        a(context);
    }

    private void a(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5303, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_ver_playback_ctrl, this, true);
            setOrientation(1);
            setGravity(1);
            setOnClickListener(this);
            this.c = (ImageView) findViewById(R$id.iv_record);
            this.d = (TextView) findViewById(R$id.tv_record);
            this.f = (ImageView) findViewById(R$id.iv_snap);
            this.d.setText("");
            this.d.setTextColor(-1);
            g();
            this.c.setOnClickListener(new s(this));
            this.f.setOnClickListener(this);
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: b */
    public /* synthetic */ void c(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5312, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View view2 = view;
        this.x.onNext(true);
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    private void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5304, new Class[0], Void.TYPE).isSupported) {
            h();
            this.y = this.x.e(500, TimeUnit.MILLISECONDS).c(l.c()).I(new r(this), q.c);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public /* synthetic */ void e(Boolean bool) {
        i iVar;
        if (!PatchProxy.proxy(new Object[]{bool}, this, changeQuickRedirect, false, 5311, new Class[]{Boolean.class}, Void.TYPE).isSupported && (iVar = this.q) != null) {
            iVar.c();
        }
    }

    static /* synthetic */ void f(Throwable throwable) {
    }

    public void setEventCallback(i eventCallback) {
        this.q = eventCallback;
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        i iVar;
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5305, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        i iVar2 = this.q;
        if (iVar2 == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (iVar2 != null) {
            iVar2.f();
        }
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (v == this.c) {
            i iVar3 = this.q;
            if (iVar3 != null) {
                iVar3.c();
            }
        } else if (v == this.f && (iVar = this.q) != null) {
            iVar.a();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setRecording(boolean isRecording) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isRecording ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5306, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c.setImageTintList(isRecording ? null : ColorStateList.valueOf(-1));
            this.c.setImageResource(isRecording ? R$drawable.ic_live_rec_white : R$drawable.ic_record_selector);
            this.c.setSelected(isRecording);
            if (!isRecording) {
                this.d.setText("");
                this.d.setTextColor(-1);
            }
        }
    }

    public void i(int time) {
        if (!PatchProxy.proxy(new Object[]{new Integer(time)}, this, changeQuickRedirect, false, 5307, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.c.isSelected()) {
                SpannableString spanString = new SpannableString(String.format(Locale.US, PubUtils.getString(getContext(), R$string.recording_timestr), new Object[]{e.c(time - 1)}));
                spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5515B")), 0, 3, 33);
                this.d.setText(spanString);
            }
        }
    }

    public void setPlayStatus(boolean isPlay) {
        Object[] objArr = {new Byte(isPlay ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5308, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.c.setEnabled(isPlay);
            this.f.setEnabled(isPlay);
        }
    }

    public void onDetachedFromWindow() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5309, new Class[0], Void.TYPE).isSupported) {
            super.onDetachedFromWindow();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:3:0x0016, code lost:
        r0 = r8;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void h() {
        /*
            r8 = this;
            r0 = 0
            java.lang.Object[] r1 = new java.lang.Object[r0]
            com.meituan.robust.ChangeQuickRedirect r3 = changeQuickRedirect
            java.lang.Class[] r6 = new java.lang.Class[r0]
            java.lang.Class r7 = java.lang.Void.TYPE
            r4 = 0
            r5 = 5310(0x14be, float:7.441E-42)
            r2 = r8
            com.meituan.robust.PatchProxyResult r0 = com.meituan.robust.PatchProxy.proxy(r1, r2, r3, r4, r5, r6, r7)
            boolean r0 = r0.isSupported
            if (r0 == 0) goto L_0x0016
            return
        L_0x0016:
            r0 = r8
            io.reactivex.disposables.b r1 = r0.y
            if (r1 == 0) goto L_0x0026
            boolean r1 = r1.isDisposed()
            if (r1 != 0) goto L_0x0026
            io.reactivex.disposables.b r1 = r0.y
            r1.dispose()
        L_0x0026:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.leedarson.newui.view.VerPlayBackController.h():void");
    }
}
