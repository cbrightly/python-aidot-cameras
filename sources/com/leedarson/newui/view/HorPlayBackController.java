package com.leedarson.newui.view;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import com.leedarson.R$color;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.e;
import com.leedarson.utils.i;
import com.leedarson.view.easypopup.b;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Locale;

public class HorPlayBackController extends LinearLayout implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private int A4 = 1;
    private float B4 = 1.0f;
    /* access modifiers changed from: private */
    public LDSTextView C4;
    /* access modifiers changed from: private */
    public LDSTextView D4;
    /* access modifiers changed from: private */
    public LDSTextView E4;
    private boolean F4 = false;
    int G4 = 0;
    private boolean a1 = true;
    private View a2;
    String c = "HorPlayBackController";
    private ImageView d;
    private ImageView f;
    /* access modifiers changed from: private */
    public LDSTextView p0;
    private View p1;
    /* access modifiers changed from: private */
    public i p2;
    private boolean p3 = false;
    private com.leedarson.view.easypopup.b p4;
    private SeekBar q;
    /* access modifiers changed from: private */
    public LDSTextView x;
    private LDSTextView y;
    private LDSTextView z;
    private float[] z4 = {0.5f, 1.0f, 2.0f};

    public HorPlayBackController(Context context) {
        super(context);
    }

    public HorPlayBackController(Context context, AttributeSet attrs) {
        super(context, attrs);
        k(context);
    }

    public void setHasSpeed(boolean hasSpeed) {
        if (!PatchProxy.proxy(new Object[]{new Byte(hasSpeed ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5003, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.F4 = hasSpeed;
            if (hasSpeed) {
                this.p0.setVisibility(0);
            } else {
                this.p0.setVisibility(8);
            }
        }
    }

    private void k(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5004, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.include_playback_ctrl_hor, this, true);
            View findViewById = findViewById(R$id.fade_layout);
            this.p1 = findViewById;
            findViewById.setOnClickListener(this);
            this.a2 = findViewById(R$id.layout_hor_record);
            this.d = (ImageView) findViewById(R$id.iv_fullscreen);
            this.f = (ImageView) findViewById(R$id.iv_silence);
            this.q = (SeekBar) findViewById(R$id.sb_time);
            this.x = (LDSTextView) findViewById(R$id.tv_total_time);
            this.y = (LDSTextView) findViewById(R$id.tv_current_time);
            this.z = (LDSTextView) findViewById(R$id.tv_recording_time);
            l();
            LDSTextView lDSTextView = (LDSTextView) findViewById(R$id.tv_speed);
            this.p0 = lDSTextView;
            lDSTextView.setText(R$string._1px);
            this.p0.setOnClickListener(this);
            this.f.setOnClickListener(this);
            this.d.setOnClickListener(this);
            this.q.setOnSeekBarChangeListener(new a());
        }
    }

    public class a implements SeekBar.OnSeekBarChangeListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (!PatchProxy.proxy(new Object[]{seekBar, new Integer(progress), new Byte(fromUser ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5018, new Class[]{SeekBar.class, Integer.TYPE, Boolean.TYPE}, Void.TYPE).isSupported) {
                HorPlayBackController.this.x.setText(e.c((seekBar.getMax() - progress) / 1000));
                if (HorPlayBackController.this.p2 != null) {
                    HorPlayBackController.this.p2.onProgressChanged(seekBar, progress, fromUser);
                }
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (!PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 5019, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                if (HorPlayBackController.this.p2 != null) {
                    HorPlayBackController.this.p2.onStartTrackingTouch(seekBar);
                }
            }
        }

        @SensorsDataInstrumented
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (PatchProxy.proxy(new Object[]{seekBar}, this, changeQuickRedirect, false, 5020, new Class[]{SeekBar.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
                return;
            }
            SeekBar seekBar2 = seekBar;
            if (HorPlayBackController.this.p2 != null) {
                HorPlayBackController.this.p2.onStopTrackingTouch(seekBar2);
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(seekBar);
        }
    }

    private void l() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5005, new Class[0], Void.TYPE).isSupported) {
            com.leedarson.view.easypopup.b bVar = (com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) ((com.leedarson.view.easypopup.b) com.leedarson.view.easypopup.b.U().O(getContext(), R$layout.layout_speed_pop_above)).P(true)).W(new b()).p();
            this.p4 = bVar;
            bVar.A().setOnDismissListener(new c());
        }
    }

    public class b implements b.a {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void a(View view, com.leedarson.view.easypopup.b bVar) {
            Class[] clsArr = {View.class, com.leedarson.view.easypopup.b.class};
            if (!PatchProxy.proxy(new Object[]{view, bVar}, this, changeQuickRedirect, false, 5021, clsArr, Void.TYPE).isSupported) {
                LDSTextView unused = HorPlayBackController.this.C4 = (LDSTextView) view.findViewById(R$id.tv_2px);
                LDSTextView unused2 = HorPlayBackController.this.D4 = (LDSTextView) view.findViewById(R$id.tv_1px);
                LDSTextView unused3 = HorPlayBackController.this.E4 = (LDSTextView) view.findViewById(R$id.tv_5px);
                HorPlayBackController.this.C4.setOnClickListener(HorPlayBackController.this);
                HorPlayBackController.this.D4.setOnClickListener(HorPlayBackController.this);
                HorPlayBackController.this.E4.setOnClickListener(HorPlayBackController.this);
                HorPlayBackController.this.D4.setTextColor(HorPlayBackController.this.getResources().getColor(R$color.second_color));
            }
        }
    }

    public class c implements PopupWindow.OnDismissListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public void onDismiss() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5022, new Class[0], Void.TYPE).isSupported) {
                HorPlayBackController.this.p0.setTextColor(-1);
                if (HorPlayBackController.this.p2 != null) {
                    HorPlayBackController.this.p2.d(2, -1.0f);
                }
            }
        }
    }

    public void setEventCallback(i onEventCallback) {
        this.p2 = onEventCallback;
    }

    public boolean m() {
        return this.a1;
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5006, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        i iVar = this.p2;
        if (iVar != null) {
            iVar.f();
        }
        if (com.leedarson.utils.b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        int viewId = v.getId();
        if (viewId == R$id.iv_silence) {
            boolean z2 = true ^ this.a1;
            this.a1 = z2;
            this.f.setImageResource(z2 ? R$drawable.ic_silence : R$drawable.ic_voice);
            i iVar2 = this.p2;
            if (iVar2 != null) {
                iVar2.b(this.a1);
            }
        } else if (viewId == R$id.iv_fullscreen) {
            i iVar3 = this.p2;
            if (iVar3 != null) {
                iVar3.e();
            }
        } else if (viewId == R$id.tv_speed) {
            int i = this.A4 + 1;
            this.A4 = i;
            if (i > 2) {
                this.A4 = 0;
            }
            float f2 = this.z4[this.A4];
            this.B4 = f2;
            if (f2 == 2.0f) {
                this.p0.setText(R$string._2px);
            } else if (f2 == 1.0f) {
                this.p0.setText(R$string._1px);
            } else if (f2 == 0.5f) {
                this.p0.setText(R$string._0_5px);
            }
            i iVar4 = this.p2;
            if (iVar4 != null) {
                iVar4.d(3, this.B4);
            }
        } else if (viewId == R$id.tv_2px) {
            this.p4.y();
            float f3 = this.B4;
            float[] fArr = this.z4;
            if (f3 != fArr[0]) {
                this.B4 = fArr[0];
                this.p0.setText(R$string._2px);
                this.C4.setTextColor(getResources().getColor(R$color.second_color));
                this.D4.setTextColor(-1);
                this.E4.setTextColor(-1);
                i iVar5 = this.p2;
                if (iVar5 != null) {
                    iVar5.d(3, this.B4);
                }
            }
        } else if (viewId == R$id.tv_1px) {
            this.p4.y();
            float f4 = this.B4;
            float[] fArr2 = this.z4;
            if (f4 != fArr2[1]) {
                this.B4 = fArr2[1];
                this.p0.setText(R$string._1px);
                this.C4.setTextColor(-1);
                this.D4.setTextColor(getResources().getColor(R$color.second_color));
                this.E4.setTextColor(-1);
                i iVar6 = this.p2;
                if (iVar6 != null) {
                    iVar6.d(3, this.B4);
                }
            }
        } else if (viewId == R$id.tv_5px) {
            this.p4.y();
            float f5 = this.B4;
            float[] fArr3 = this.z4;
            if (f5 != fArr3[2]) {
                this.B4 = fArr3[2];
                this.p0.setText(R$string._0_5px);
                this.C4.setTextColor(-1);
                this.D4.setTextColor(-1);
                this.E4.setTextColor(getResources().getColor(R$color.second_color));
                i iVar7 = this.p2;
                if (iVar7 != null) {
                    iVar7.d(3, this.B4);
                }
            }
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void setBarMaxProgress(int max) {
        Object[] objArr = {new Integer(max)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5007, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q.setMax(max);
            this.x.setText(e.c(max / 1000));
        }
    }

    public void setBarProgress(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5008, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q.setProgress(time);
            this.x.setText(e.c((int) Math.ceil((double) (((float) (this.q.getMax() - time)) / 1000.0f))));
        }
    }

    public void setBarProgressNoAnimation(int time) {
        if (!PatchProxy.proxy(new Object[]{new Integer(time)}, this, changeQuickRedirect, false, 5009, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (time >= 0 && time / 1000 <= this.q.getMax() / 1000) {
                this.G4 = 0;
                this.q.setProgress(time);
                this.x.setText(e.c((int) Math.ceil((double) (((float) (this.q.getMax() - time)) / 1000.0f))));
            }
        }
    }

    public void setSecondProgress(int time) {
        Object[] objArr = {new Integer(time)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5010, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            this.q.setSecondaryProgress(time);
        }
    }

    public int getSecondProgress() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5011, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.q.getSecondaryProgress();
    }

    public void setFullScreen(boolean isFullScreen) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isFullScreen ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5012, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.d.setImageResource(isFullScreen ? R$drawable.ic_live_icon_min : R$drawable.ic_fullscreen);
        }
    }

    public void setRecording(boolean isRecord) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isRecord ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5013, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.p3 = isRecord;
            if (isRecord) {
                this.p1.setVisibility(8);
                this.a2.setVisibility(0);
                this.z.setText(e.c(0));
                return;
            }
            this.p1.setVisibility(0);
            this.a2.setVisibility(8);
            this.z.setText("");
        }
    }

    public void p(int time) {
        if (!PatchProxy.proxy(new Object[]{new Integer(time)}, this, changeQuickRedirect, false, 5014, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (this.p3) {
                SpannableString spanString = new SpannableString(String.format(Locale.US, PubUtils.getString(getContext(), R$string.recording_timestr), new Object[]{e.c(time - 1)}));
                spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5515B")), 0, 3, 33);
                this.z.setText(spanString);
            }
        }
    }

    public void n() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5015, new Class[0], Void.TYPE).isSupported) {
            SeekBar seekBar = this.q;
            seekBar.setProgress(seekBar.getMax());
            this.y.setText(e.c(this.q.getMax() / 1000));
        }
    }

    public void j(boolean isSilence) {
        if (!PatchProxy.proxy(new Object[]{new Byte(isSilence ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 5016, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            this.a1 = isSilence;
            this.f.setImageResource(isSilence ? R$drawable.ic_silence : R$drawable.ic_voice);
        }
    }

    public void o() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5017, new Class[0], Void.TYPE).isSupported) {
            this.B4 = 1.0f;
            this.p0.setText(R$string._1px);
            this.C4.setTextColor(-1);
            this.D4.setTextColor(getResources().getColor(R$color.second_color));
            this.E4.setTextColor(-1);
        }
    }
}
