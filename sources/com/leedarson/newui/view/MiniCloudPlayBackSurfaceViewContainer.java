package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.newui.view.radar.g;
import com.leedarson.view.IpcTextureView;
import com.leedarson.view.LDSBaseIpcTexureRenderView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class MiniCloudPlayBackSurfaceViewContainer extends LinearLayout implements View.OnTouchListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View c;
    private IpcTextureView d;
    private FrameLayout f;
    private b q;

    public interface b {
        void a();
    }

    public MiniCloudPlayBackSurfaceViewContainer(Context context) {
        super(context);
        b(context);
    }

    public MiniCloudPlayBackSurfaceViewContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b(context);
    }

    public MiniCloudPlayBackSurfaceViewContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context);
    }

    public void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5183, new Class[]{Context.class}, Void.TYPE).isSupported) {
            View view = LayoutInflater.from(context).inflate(R$layout.mini_webrtc_view_container, (ViewGroup) null);
            c(view);
            addView(view);
        }
    }

    private void c(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5184, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.c = view.findViewById(R$id.mini_surfaceview_border);
            this.f = (FrameLayout) view.findViewById(R$id.mini_surfaceviewContainer);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5185, new Class[0], Void.TYPE).isSupported) {
            try {
                g.b("显示mini视频播放器");
                setVisibility(0);
                ViewGroup frameLayout = (ViewGroup) this.d.getParent();
                if (frameLayout == null || frameLayout.indexOfChild(this.d) == -1) {
                    g.b("webrtcSurfaceView 无parent....");
                } else {
                    frameLayout.removeView(this.d);
                }
                this.c.setVisibility(0);
                LinearLayout.LayoutParams borderParams = (LinearLayout.LayoutParams) this.c.getLayoutParams();
                ViewGroup.LayoutParams params = this.d.getLayoutParams();
                if (getResources().getConfiguration().orientation == 2) {
                    params.width = com.leedarson.view.a.a(getContext(), 200.0f);
                    params.height = com.leedarson.view.a.a(getContext(), 112.0f);
                    borderParams.topMargin = com.leedarson.view.a.a(getContext(), 23.0f);
                    borderParams.rightMargin = com.leedarson.view.a.a(getContext(), 81.0f);
                } else {
                    params.width = com.leedarson.view.a.a(getContext(), 130.0f);
                    params.height = com.leedarson.view.a.a(getContext(), 73.0f);
                    borderParams.topMargin = com.leedarson.view.a.a(getContext(), 12.0f);
                    borderParams.rightMargin = com.leedarson.view.a.a(getContext(), 18.0f);
                }
                this.d.setLayoutParams(params);
                this.d.setOnTouchListener(new a());
                this.d.b();
                this.f.addView(this.d);
                g.b("显示mini视频播放器完毕");
            } catch (Exception e) {
                g.b("显示mini视频播放器异常:" + e.getMessage());
            }
        }
    }

    public class a implements LDSBaseIpcTexureRenderView.b {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(float f, MotionEvent motionEvent) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(f), motionEvent}, this, changeQuickRedirect, false, 5187, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            g.b("webrtcSurfaceView onTouch..");
            return false;
        }
    }

    public void a() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5186, new Class[0], Void.TYPE).isSupported) {
            try {
                if (getVisibility() == 0) {
                    g.b("显示视频主窗口");
                    ViewGroup frameLayout = (ViewGroup) this.d.getParent();
                    if (frameLayout == null || frameLayout.indexOfChild(this.d) == -1) {
                        g.b("webrtcSurfaceView 无parent....");
                    } else {
                        frameLayout.removeView(this.d);
                    }
                    setVisibility(8);
                    this.c.setVisibility(8);
                    b bVar = this.q;
                    if (bVar != null) {
                        bVar.a();
                    }
                }
            } catch (Exception e) {
                g.b("hide error:" + e.getMessage());
            }
        }
    }

    public void setListener(b listener) {
        this.q = listener;
    }

    public void setWebrtcSurfaceView(IpcTextureView webrtcSurfaceView) {
        this.d = webrtcSurfaceView;
    }

    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
