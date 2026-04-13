package com.leedarson.newui.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.utils.b;
import com.leedarson.utils.h;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;
import org.json.JSONObject;

public class VerLiveController extends LinearLayout implements View.OnClickListener, Observer {
    public static ChangeQuickRedirect changeQuickRedirect;
    private boolean a1;
    private boolean a2;
    private ImageView c;
    private LDSTextView d;
    private ImageView f;
    private boolean p0;
    private boolean p1;
    private String p2;
    private boolean p3;
    private ImageView q;
    private ImageView x;
    private h y;
    private boolean z;

    static /* synthetic */ void a(VerLiveController x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 5301, new Class[]{VerLiveController.class}, Void.TYPE).isSupported) {
            x0.d();
        }
    }

    public void setOnEventCallback(h eventCallback) {
        this.y = eventCallback;
    }

    public void c(boolean halfSpeak, boolean fullSpeak) {
        int i = 0;
        Object[] objArr = {new Byte(halfSpeak ? (byte) 1 : 0), new Byte(fullSpeak ? (byte) 1 : 0)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Boolean.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 5295, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            this.q.setVisibility(halfSpeak ? 0 : 8);
            ImageView imageView = this.x;
            if (!fullSpeak) {
                i = 8;
            }
            imageView.setVisibility(i);
            d();
        }
    }

    public VerLiveController(Context context) {
        this(context, (AttributeSet) null);
    }

    public VerLiveController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.z = false;
        this.p0 = false;
        this.a1 = false;
        this.p1 = false;
        this.a2 = false;
        this.p2 = "";
        this.p3 = false;
        b(context);
    }

    private void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5296, new Class[]{Context.class}, Void.TYPE).isSupported) {
            LayoutInflater.from(context).inflate(R$layout.layout_ver_live_ctrl, this, true);
            setOrientation(1);
            setGravity(1);
            this.d = (LDSTextView) findViewById(R$id.tv_record);
            this.c = (ImageView) findViewById(R$id.iv_record);
            this.f = (ImageView) findViewById(R$id.iv_snap);
            this.q = (ImageView) findViewById(R$id.iv_speak_half);
            this.x = (ImageView) findViewById(R$id.iv_speak_full);
            this.c.setOnClickListener(this);
            this.f.setOnClickListener(this);
            this.x.setOnClickListener(this);
        }
    }

    public ImageView getSpeakHalfView() {
        return this.q;
    }

    public boolean onTouchEvent(MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{event}, this, changeQuickRedirect, false, 5297, new Class[]{MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        float x2 = event.getX();
        float y2 = event.getY();
        switch (event.getAction()) {
            case 0:
                View touchView = null;
                int i = 0;
                while (true) {
                    if (i < getChildCount()) {
                        View childView = getChildAt(i);
                        if (new Rect(childView.getLeft(), childView.getTop(), childView.getRight(), childView.getBottom()).contains((int) x2, (int) y2)) {
                            touchView = childView;
                        } else {
                            i++;
                        }
                    }
                }
                if (touchView == null) {
                    return true;
                }
                ImageView imageView = this.q;
                if (touchView != imageView) {
                    return false;
                }
                this.p3 = true;
                imageView.setImageTintList((ColorStateList) null);
                this.q.setImageResource(R$drawable.ic_live_speak_half);
                h hVar = this.y;
                if (hVar != null) {
                    hVar.m(true);
                }
                return true;
            case 1:
                if (this.p3) {
                    this.p3 = false;
                    this.q.setImageTintList(ColorStateList.valueOf(-1));
                    this.q.setImageResource(R$drawable.ic_speak_half);
                    h hVar2 = this.y;
                    if (hVar2 != null) {
                        hVar2.j(true);
                    }
                    return true;
                }
                break;
            case 2:
                if (this.p3) {
                    return true;
                }
                break;
        }
        return false;
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5298, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        View v = view;
        h hVar = this.y;
        if (hVar == null) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (hVar != null) {
            hVar.f();
        }
        if (b.a(v, 500)) {
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
            return;
        }
        if (v == this.c) {
            this.y.c();
        } else if (v == this.f) {
            this.y.v();
        } else if (v == this.x) {
            this.y.w();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void update(Observable observable, Object arg) {
        Class[] clsArr = {Observable.class, Object.class};
        if (!PatchProxy.proxy(new Object[]{observable, arg}, this, changeQuickRedirect, false, 5299, clsArr, Void.TYPE).isSupported) {
            try {
                JSONObject dataObj = (JSONObject) arg;
                if (dataObj != null) {
                    this.a1 = dataObj.getBoolean("isLightOn");
                    this.p1 = dataObj.getBoolean("isAlarm");
                    this.p0 = dataObj.getBoolean("isTalking");
                    this.z = dataObj.getBoolean("isRecording");
                    this.p2 = dataObj.getString("recordTime");
                    this.a2 = dataObj.getBoolean("isTrackingMode");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            post(new a());
        }
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5302, new Class[0], Void.TYPE).isSupported) {
                VerLiveController.a(VerLiveController.this);
            }
        }
    }

    private void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5300, new Class[0], Void.TYPE).isSupported) {
            ColorStateList colorStateList = null;
            this.x.setImageTintList(this.p0 ? null : ColorStateList.valueOf(-1));
            this.x.setImageResource(this.p0 ? R$drawable.ic_live_speak_full : R$drawable.ic_speak_full);
            ImageView imageView = this.c;
            if (!this.z) {
                colorStateList = ColorStateList.valueOf(-1);
            }
            imageView.setImageTintList(colorStateList);
            this.c.setImageResource(this.z ? R$drawable.ic_live_rec_white : R$drawable.ic_record);
            if (!this.z) {
                this.d.setText("");
                this.d.setTextColor(-1);
                return;
            }
            SpannableString spanString = new SpannableString(String.format(Locale.US, PubUtils.getString(getContext(), R$string.recording_timestr), new Object[]{this.p2}));
            spanString.setSpan(new ForegroundColorSpan(Color.parseColor("#F5515B")), 0, 3, 33);
            this.d.setText(spanString);
        }
    }
}
