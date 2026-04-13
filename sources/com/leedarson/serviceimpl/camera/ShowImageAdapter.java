package com.leedarson.serviceimpl.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.target.j;
import com.leedarson.base.views.LoadingProgressBar;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.base.views.photoview.AlbumPhotoView;
import com.leedarson.base.views.photoview.g;
import com.leedarson.serviceimpl.camera.view.LdsVideoView;
import com.leedarson.serviceinterface.event.NeedPermissionEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class ShowImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ChangeQuickRedirect changeQuickRedirect;
    private final int a = 0;
    private final int b = 1;
    /* access modifiers changed from: private */
    public final List<String> c;
    /* access modifiers changed from: private */
    public Context d;
    /* access modifiers changed from: private */
    public boolean e;
    /* access modifiers changed from: private */
    public PopupWindow f;
    /* access modifiers changed from: private */
    public boolean g;

    static /* synthetic */ void e(ShowImageAdapter x0, View x1, String x2) {
        Class[] clsArr = {ShowImageAdapter.class, View.class, String.class};
        if (!PatchProxy.proxy(new Object[]{x0, x1, x2}, (Object) null, changeQuickRedirect, true, 7467, clsArr, Void.TYPE).isSupported) {
            x0.j(x1, x2);
        }
    }

    public ShowImageAdapter(Context context, List<String> data, boolean hasExtras, boolean supportSaveToAlbum) {
        this.c = data;
        setHasStableIds(true);
        this.d = context;
        this.g = supportSaveToAlbum;
        this.e = hasExtras;
    }

    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(viewType)}, this, changeQuickRedirect, false, 7458, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        if (proxy.isSupported) {
            return (RecyclerView.ViewHolder) proxy.result;
        }
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType != 1) {
            return new ListViewHolder(inflater.inflate(R$layout.show_image_item, viewGroup, false));
        }
        View view = inflater.inflate(R$layout.show_video_item, (ViewGroup) null);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        return new VideoViewHolder(view);
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 7459, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            if (getItemViewType(position) == 1) {
                ((VideoViewHolder) holder).a.setSource(this.c.get(position));
                return;
            }
            ListViewHolder viewHolder = (ListViewHolder) holder;
            viewHolder.b.setVisibility(0);
            ((h) com.bumptech.glide.b.t(this.d.getApplicationContext()).i().k()).M0(this.c.get(position)).J0(new a(viewHolder)).H0(viewHolder.a);
            viewHolder.a.setOnPhotoTapListener(new b());
            viewHolder.a.setOnLongClickListener(new c(viewHolder, position));
        }
    }

    public class a implements com.bumptech.glide.request.e<Bitmap> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ListViewHolder a;

        a(ListViewHolder listViewHolder) {
            this.a = listViewHolder;
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7470, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Bitmap) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, j<Bitmap> jVar, boolean z) {
            Object[] objArr = {glideException, obj, jVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7468, new Class[]{GlideException.class, Object.class, j.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.a.b.setVisibility(8);
            return false;
        }

        public boolean a(Bitmap bitmap, Object obj, j<Bitmap> jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Object[] objArr = {bitmap, obj, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7469, new Class[]{Bitmap.class, Object.class, j.class, com.bumptech.glide.load.a.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            this.a.b.setVisibility(8);
            return false;
        }
    }

    public class b implements g {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        public void onPhotoTap(ImageView imageView, float f, float f2) {
            Object[] objArr = {imageView, new Float(f), new Float(f2)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Float.TYPE;
            if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7471, new Class[]{ImageView.class, cls, cls}, Void.TYPE).isSupported) {
                if (ShowImageActivity.class.isInstance(ShowImageAdapter.this.d)) {
                    ShowImageActivity activity = (ShowImageActivity) ShowImageAdapter.this.d;
                    if (ShowImageAdapter.this.e) {
                        activity.l0();
                    } else {
                        activity.finish();
                    }
                }
            }
        }
    }

    public class c implements View.OnLongClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ListViewHolder c;
        final /* synthetic */ int d;

        c(ListViewHolder listViewHolder, int i) {
            this.c = listViewHolder;
            this.d = i;
        }

        public boolean onLongClick(View view) {
            PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7472, new Class[]{View.class}, Boolean.TYPE);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            timber.log.a.g("CZB").a("onLongClick", new Object[0]);
            if (ShowImageAdapter.this.g) {
                ShowImageAdapter.e(ShowImageAdapter.this, this.c.a, (String) ShowImageAdapter.this.c.get(this.d));
            }
            return true;
        }
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7460, new Class[0], Integer.TYPE);
        return proxy.isSupported ? ((Integer) proxy.result).intValue() : this.c.size();
    }

    public int getItemViewType(int position) {
        Object[] objArr = {new Integer(position)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7461, new Class[]{cls}, cls);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        return i(this.c.get(position)) ? 1 : 0;
    }

    public static final class ListViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public final AlbumPhotoView a;
        /* access modifiers changed from: private */
        public final LoadingProgressBar b;

        ListViewHolder(View itemView) {
            super(itemView);
            this.a = (AlbumPhotoView) itemView.findViewById(R$id.image);
            this.b = (LoadingProgressBar) itemView.findViewById(R$id.pb_loading);
        }
    }

    public static final class VideoViewHolder extends RecyclerView.ViewHolder {
        public static ChangeQuickRedirect changeQuickRedirect;
        /* access modifiers changed from: private */
        public final LdsVideoView a;

        VideoViewHolder(View itemView) {
            super(itemView);
            this.a = (LdsVideoView) itemView.findViewById(R$id.video_view);
        }
    }

    public boolean h() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7463, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        PopupWindow popupWindow = this.f;
        return popupWindow != null && popupWindow.isShowing();
    }

    public void g() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7464, new Class[0], Void.TYPE).isSupported) {
            if (h()) {
                this.f.dismiss();
            }
        }
    }

    private void j(View view, String url) {
        if (!PatchProxy.proxy(new Object[]{view, url}, this, changeQuickRedirect, false, 7465, new Class[]{View.class, String.class}, Void.TYPE).isSupported) {
            View popupView = LayoutInflater.from(this.d).inflate(R$layout.popup_view, (ViewGroup) null);
            PopupWindow popupWindow = new PopupWindow(popupView, -1, -1);
            this.f = popupWindow;
            popupWindow.setContentView(popupView);
            ((LDSTextView) popupView.findViewById(R$id.tv_save_img)).setOnClickListener(new d(url));
            ((LDSTextView) popupView.findViewById(R$id.tv_cancel)).setOnClickListener(new e());
            popupView.findViewById(R$id.out_layout).setOnClickListener(new f());
            this.f.setBackgroundDrawable(new BitmapDrawable());
            this.f.setOutsideTouchable(true);
            this.f.showAtLocation(view, 17, 0, 0);
        }
    }

    public class d implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ String c;

        d(String str) {
            this.c = str;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7473, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            org.greenrobot.eventbus.c.c().l(new NeedPermissionEvent(201, this.c));
            ShowImageAdapter.this.f.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class e implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        e() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7474, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ShowImageAdapter.this.f.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class f implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        f() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 7475, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            ShowImageAdapter.this.f.dismiss();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    private boolean i(String url) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 7466, new Class[]{String.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        return url.endsWith("mp4") || url.endsWith("flv") || url.endsWith("avi") || url.endsWith("3gp") || url.endsWith("mov");
    }
}
