package com.leedarson.newui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.newui.view.radar.g;
import com.leedarson.view.IpcWebrtcSurfaceView;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class MiniWebRtcSurfaceViewContainer extends LinearLayout implements View.OnTouchListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private View c;
    private IpcWebrtcSurfaceView d;
    private View f;
    private FrameLayout q;
    private ImageView x;
    private b y;

    public interface b {
        void a();
    }

    public MiniWebRtcSurfaceViewContainer(Context context) {
        super(context);
        b(context);
    }

    public MiniWebRtcSurfaceViewContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        b(context);
    }

    public MiniWebRtcSurfaceViewContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        b(context);
    }

    public void b(Context context) {
        if (!PatchProxy.proxy(new Object[]{context}, this, changeQuickRedirect, false, 5188, new Class[]{Context.class}, Void.TYPE).isSupported) {
            View view = LayoutInflater.from(context).inflate(R$layout.mini_webrtc_view_container, (ViewGroup) null);
            c(view);
            addView(view);
        }
    }

    private void c(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 5189, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.c = view.findViewById(R$id.mini_surfaceview_border);
            this.q = (FrameLayout) view.findViewById(R$id.mini_surfaceviewContainer);
        }
    }

    public void d() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5190, new Class[0], Void.TYPE).isSupported) {
            try {
                g.a("显示mini视频播放器");
                setVisibility(0);
                FrameLayout frameLayout = (FrameLayout) this.d.getParent();
                if (frameLayout == null || frameLayout.indexOfChild(this.d) == -1) {
                    g.a("webrtcSurfaceView 无parent....");
                } else {
                    frameLayout.removeView(this.d);
                }
                this.c.setVisibility(0);
                LinearLayout.LayoutParams borderParams = (LinearLayout.LayoutParams) this.c.getLayoutParams();
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this.d.getLayoutParams();
                ViewGroup.MarginLayoutParams imageCacheParams = null;
                ImageView imageView = this.x;
                if (imageView != null) {
                    imageCacheParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
                }
                if (getResources().getConfiguration().orientation == 2) {
                    params.width = com.leedarson.view.a.a(getContext(), 200.0f);
                    int a2 = com.leedarson.view.a.a(getContext(), 112.0f);
                    params.height = a2;
                    imageCacheParams.width = params.width;
                    imageCacheParams.height = a2;
                    borderParams.topMargin = com.leedarson.view.a.a(getContext(), 23.0f);
                    borderParams.rightMargin = com.leedarson.view.a.a(getContext(), 81.0f);
                } else {
                    params.width = com.leedarson.view.a.a(getContext(), 130.0f);
                    int a3 = com.leedarson.view.a.a(getContext(), 73.0f);
                    params.height = a3;
                    imageCacheParams.width = params.width;
                    imageCacheParams.height = a3;
                    borderParams.topMargin = com.leedarson.view.a.a(getContext(), 12.0f);
                    borderParams.rightMargin = com.leedarson.view.a.a(getContext(), 18.0f);
                }
                this.d.setLayoutParams(params);
                this.d.setTouchListener(new a());
                this.d.l();
                this.d.c();
                this.q.addView(this.d);
                ImageView imageView2 = this.x;
                if (imageView2 != null) {
                    ViewGroup viewGroup = (ViewGroup) imageView2.getParent();
                    if (viewGroup != null) {
                        viewGroup.removeView(this.x);
                    }
                    g.a("设置imageCache Center_crop");
                    this.x.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    this.q.addView(this.x, imageCacheParams);
                }
                g.a("显示mini视频播放器完毕");
            } catch (Exception e) {
                g.a("显示mini视频播放器异常:" + e.getMessage());
            }
        }
    }

    public class a implements IpcWebrtcSurfaceView.e {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public boolean a(float f, MotionEvent motionEvent) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Float(f), motionEvent}, this, changeQuickRedirect, false, 5193, new Class[]{Float.TYPE, MotionEvent.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            g.a("webrtcSurfaceView onTouch..");
            return false;
        }
    }

    public void a() {
        ViewGroup viewGroup;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 5191, new Class[0], Void.TYPE).isSupported) {
            try {
                if (getVisibility() == 0) {
                    g.a("显示视频主窗口");
                    FrameLayout frameLayout = (FrameLayout) this.d.getParent();
                    if (frameLayout == null || frameLayout.indexOfChild(this.d) == -1) {
                        g.a("webrtcSurfaceView 无parent....");
                    } else {
                        frameLayout.removeView(this.d);
                    }
                    setVisibility(8);
                    this.c.setVisibility(8);
                    ImageView imageView = this.x;
                    if (!(imageView == null || (viewGroup = (ViewGroup) imageView.getParent()) == null)) {
                        viewGroup.removeView(this.x);
                    }
                    b bVar = this.y;
                    if (bVar != null) {
                        bVar.a();
                    }
                }
            } catch (Exception e) {
                g.a("hide error:" + e.getMessage());
            }
        }
    }

    public void setListener(b listener) {
        this.y = listener;
    }

    public void setWebrtcSurfaceView(IpcWebrtcSurfaceView webrtcSurfaceView) {
        this.d = webrtcSurfaceView;
    }

    public void setPlayer(View player) {
        if (!PatchProxy.proxy(new Object[]{player}, this, changeQuickRedirect, false, 5192, new Class[]{View.class}, Void.TYPE).isSupported) {
            this.f = player;
            this.x = (ImageView) player.findViewById(R$id.img_cache);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
