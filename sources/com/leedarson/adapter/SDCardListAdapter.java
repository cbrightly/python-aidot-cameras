package com.leedarson.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.j;
import com.leedarson.R$drawable;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.base.utils.r;
import com.leedarson.base.views.common.LDSTextView;
import com.leedarson.smartcamera.utils.d;
import com.leedarson.utils.e;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SDCardListAdapter extends RecyclerView.Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public static boolean a = true;
    public static ChangeQuickRedirect changeQuickRedirect;
    private int b = -1;
    private final LayoutInflater c;
    private final Context d;
    private List<Long> e;
    private c f;
    /* access modifiers changed from: private */
    public String g;
    /* access modifiers changed from: private */
    public String h;
    /* access modifiers changed from: private */
    public ExecutorService i;

    public interface c {
        void onItemClick(View view, int i);
    }

    public /* bridge */ /* synthetic */ void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i2) {
        if (!PatchProxy.proxy(new Object[]{viewHolder, new Integer(i2)}, this, changeQuickRedirect, false, 104, new Class[]{RecyclerView.ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            f((ViewHolder) viewHolder, i2);
        }
    }

    public /* bridge */ /* synthetic */ RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i2) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{viewGroup, new Integer(i2)}, this, changeQuickRedirect, false, 105, new Class[]{ViewGroup.class, Integer.TYPE}, RecyclerView.ViewHolder.class);
        return proxy.isSupported ? (RecyclerView.ViewHolder) proxy.result : g(viewGroup, i2);
    }

    public void h() {
        ExecutorService executorService;
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 100, new Class[0], Void.TYPE).isSupported && (executorService = this.i) != null) {
            executorService.shutdown();
        }
    }

    public void j(String eventStr) {
        this.h = eventStr;
    }

    public SDCardListAdapter(Context context, List<Long> list, String p2pId, String eventStr) {
        this.e = list;
        this.d = context;
        this.g = p2pId;
        this.h = eventStr;
        this.c = LayoutInflater.from(context);
        this.i = Executors.newFixedThreadPool(5, new r("sdlist_adp"));
    }

    public ViewHolder g(ViewGroup parent, int i2) {
        Object[] objArr = {parent, new Integer(i2)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 101, new Class[]{ViewGroup.class, Integer.TYPE}, ViewHolder.class);
        if (proxy.isSupported) {
            return (ViewHolder) proxy.result;
        }
        return new ViewHolder(this.c.inflate(R$layout.item_sd_card, parent, false), this.f);
    }

    public void f(ViewHolder holder, int position) {
        if (!PatchProxy.proxy(new Object[]{holder, new Integer(position)}, this, changeQuickRedirect, false, 102, new Class[]{ViewHolder.class, Integer.TYPE}, Void.TYPE).isSupported) {
            long timeStamp = this.e.get(position).longValue();
            holder.a.setText(e.j(timeStamp, "HH:mm"));
            if (this.b == position) {
                holder.c.setBackgroundResource(R$drawable.selected_border_bg);
            } else {
                holder.c.setBackgroundColor(-1);
            }
            h<Bitmap> i2 = com.bumptech.glide.b.t(this.d).i();
            String str = this.h;
            String str2 = this.g;
            ((h) ((h) ((h) ((h) i2.M0(d.d(str, str2, (timeStamp / 1000) + "")).d0(0)).j(0)).m0(true)).f(i.b)).a(new f().p0(new x(16))).J0(new b(timeStamp)).D0(new a(holder.b, holder));
        }
    }

    public class b implements com.bumptech.glide.request.e<Bitmap> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ long a;

        b(long j) {
            this.a = j;
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 109, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : c((Bitmap) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, j<Bitmap> jVar, boolean z) {
            Object[] objArr = {glideException, obj, jVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 108, new Class[]{GlideException.class, Object.class, j.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            try {
                SDCardListAdapter.this.i.submit(new f(this, this.a));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }

        /* access modifiers changed from: private */
        /* renamed from: a */
        public /* synthetic */ Object b(long timeStamp) {
            Object[] objArr = {new Long(timeStamp)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            ChangeQuickRedirect changeQuickRedirect3 = changeQuickRedirect2;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect3, false, 110, new Class[]{Long.TYPE}, Object.class);
            if (proxy.isSupported) {
                return proxy.result;
            }
            String b2 = SDCardListAdapter.this.h;
            String c = SDCardListAdapter.this.g;
            String filePath = d.d(b2, c, (timeStamp / 1000) + "");
            if (!com.leedarson.smartcamera.utils.b.j(filePath)) {
                return null;
            }
            com.leedarson.smartcamera.utils.b.e(filePath);
            return null;
        }

        public boolean c(Bitmap resource, Object model, j<Bitmap> jVar, com.bumptech.glide.load.a dataSource, boolean isFirstResource) {
            return false;
        }
    }

    public class a extends com.bumptech.glide.request.target.b {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ ViewHolder p1;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        a(ImageView view, ViewHolder viewHolder) {
            super(view);
            this.p1 = viewHolder;
        }

        public /* bridge */ /* synthetic */ void n(Object obj) {
            if (!PatchProxy.proxy(new Object[]{obj}, this, changeQuickRedirect, false, 107, new Class[]{Object.class}, Void.TYPE).isSupported) {
                p((Bitmap) obj);
            }
        }

        public void p(Bitmap resource) {
            if (!PatchProxy.proxy(new Object[]{resource}, this, changeQuickRedirect, false, 106, new Class[]{Bitmap.class}, Void.TYPE).isSupported) {
                if (resource != null) {
                    if (resource.getHeight() > resource.getWidth()) {
                        this.p1.b.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        this.p1.b.setScaleType(ImageView.ScaleType.FIT_XY);
                    }
                    this.p1.b.setImageBitmap(resource);
                }
            }
        }
    }

    public int getItemCount() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 103, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        List<Long> list = this.e;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public void i(int currentPosition) {
        this.b = currentPosition;
    }

    public int e() {
        return this.b;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LDSTextView a;
        ImageView b;
        RelativeLayout c;
        private c d;

        ViewHolder(View view, c mListener) {
            super(view);
            this.a = (LDSTextView) view.findViewById(R$id.tv_time);
            this.b = (ImageView) view.findViewById(R$id.iv_thumbnail);
            this.c = (RelativeLayout) view.findViewById(R$id.rl_image);
            this.d = mListener;
            view.setOnClickListener(new a(mListener));
        }

        public class a implements View.OnClickListener {
            public static ChangeQuickRedirect changeQuickRedirect;
            final /* synthetic */ c c;

            a(c cVar) {
                this.c = cVar;
            }

            @SensorsDataInstrumented
            public void onClick(View view) {
                if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 111, new Class[]{View.class}, Void.TYPE).isSupported) {
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                    return;
                }
                View v = view;
                if (this.c != null && SDCardListAdapter.a) {
                    this.c.onItemClick(v, ViewHolder.this.getLayoutPosition() - 1);
                }
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        }
    }

    public void k(boolean enable) {
        a = enable;
    }

    public void setOnItemClickListener(c listener) {
        this.f = listener;
    }
}
