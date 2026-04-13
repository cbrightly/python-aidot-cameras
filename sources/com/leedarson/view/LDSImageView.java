package com.leedarson.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.bumptech.glide.h;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.i;
import com.bumptech.glide.load.resource.bitmap.x;
import com.bumptech.glide.request.e;
import com.bumptech.glide.request.f;
import com.bumptech.glide.request.target.j;
import com.leedarson.R$drawable;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;

public class LDSImageView extends AppCompatImageView {
    public static ChangeQuickRedirect changeQuickRedirect;

    public interface d {
        void a(View view, String str);

        void b(View view, String str, int i, int i2);
    }

    public LDSImageView(Context context) {
        super(context);
    }

    public LDSImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LDSImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setImgUrl(String url) {
        if (!PatchProxy.proxy(new Object[]{url}, this, changeQuickRedirect, false, 11729, new Class[]{String.class}, Void.TYPE).isSupported) {
            ((h) ((h) ((h) com.bumptech.glide.b.t(getContext()).q(url).f(i.a)).h()).c()).v0(new a()).H0(this);
        }
    }

    public class a implements e<Drawable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11733, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Drawable) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, j<Drawable> jVar, boolean isFirstResource) {
            return false;
        }

        public boolean a(Drawable resource, Object model, j<Drawable> jVar, com.bumptech.glide.load.a dataSource, boolean isFirstResource) {
            return false;
        }
    }

    public void c(String url, int holderId) {
        if (!PatchProxy.proxy(new Object[]{url, new Integer(holderId)}, this, changeQuickRedirect, false, 11730, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ((h) ((h) ((h) ((h) ((h) com.bumptech.glide.b.t(getContext()).q(url).f(i.d)).h()).c()).d0(holderId)).j(holderId)).H0(this);
        }
    }

    public void b(String url, int radius, d handler, int errorResourceId) {
        Object[] objArr = {url, new Integer(radius), handler, new Integer(errorResourceId)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        Class[] clsArr = {String.class, cls, d.class, cls};
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11731, clsArr, Void.TYPE).isSupported) {
            int _errorResourceId = R$drawable.shape_c4_all;
            if (errorResourceId > 0) {
                _errorResourceId = errorResourceId;
            }
            int _radius = 1;
            if (radius > 0) {
                _radius = radius;
            }
            ((h) ((h) com.bumptech.glide.b.t(getContext()).q(url).f(i.a)).j(_errorResourceId)).a(f.v0(new x(_radius))).v0(new b(handler, url)).H0(this);
        }
    }

    public class b implements e<Drawable> {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ d a;
        final /* synthetic */ String b;

        b(d dVar, String str) {
            this.a = dVar;
            this.b = str;
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11736, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Drawable) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, j<Drawable> jVar, boolean z) {
            Object[] objArr = {glideException, obj, jVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11734, new Class[]{GlideException.class, Object.class, j.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            d dVar = this.a;
            if (dVar != null) {
                dVar.a(LDSImageView.this, this.b);
            }
            return false;
        }

        public boolean a(Drawable drawable, Object obj, j<Drawable> jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Object[] objArr = {drawable, obj, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11735, new Class[]{Drawable.class, Object.class, j.class, com.bumptech.glide.load.a.class, cls}, cls);
            if (proxy.isSupported) {
                return ((Boolean) proxy.result).booleanValue();
            }
            d dVar = this.a;
            if (dVar != null) {
                dVar.b(LDSImageView.this, this.b, 0, 0);
            }
            return false;
        }
    }

    public void a(String url, int radius) {
        if (!PatchProxy.proxy(new Object[]{url, new Integer(radius)}, this, changeQuickRedirect, false, 11732, new Class[]{String.class, Integer.TYPE}, Void.TYPE).isSupported) {
            ((h) ((h) com.bumptech.glide.b.t(getContext()).q(url).f(i.a)).j(R$drawable.shape_c4_all)).a(f.v0(new x(radius))).v0(new c()).H0(this);
        }
    }

    public class c implements e<Drawable> {
        public static ChangeQuickRedirect changeQuickRedirect;

        c() {
        }

        public /* bridge */ /* synthetic */ boolean onResourceReady(Object obj, Object obj2, j jVar, com.bumptech.glide.load.a aVar, boolean z) {
            Class<Object> cls = Object.class;
            Object[] objArr = {obj, obj2, jVar, aVar, new Byte(z ? (byte) 1 : 0)};
            ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
            Class cls2 = Boolean.TYPE;
            PatchProxyResult proxy = PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 11737, new Class[]{cls, cls, j.class, com.bumptech.glide.load.a.class, cls2}, cls2);
            return proxy.isSupported ? ((Boolean) proxy.result).booleanValue() : a((Drawable) obj, obj2, jVar, aVar, z);
        }

        public boolean onLoadFailed(@Nullable GlideException e, Object model, j<Drawable> jVar, boolean isFirstResource) {
            return false;
        }

        public boolean a(Drawable resource, Object model, j<Drawable> jVar, com.bumptech.glide.load.a dataSource, boolean isFirstResource) {
            return false;
        }
    }
}
