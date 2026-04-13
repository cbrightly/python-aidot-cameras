package com.bumptech.glide;

import android.content.ComponentCallbacks2;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import com.bumptech.glide.c;
import com.bumptech.glide.load.ImageHeaderParser;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.data.ParcelFileDescriptorRewinder;
import com.bumptech.glide.load.data.k;
import com.bumptech.glide.load.engine.bitmap_recycle.e;
import com.bumptech.glide.load.engine.cache.h;
import com.bumptech.glide.load.engine.j;
import com.bumptech.glide.load.k;
import com.bumptech.glide.load.model.a;
import com.bumptech.glide.load.model.b;
import com.bumptech.glide.load.model.d;
import com.bumptech.glide.load.model.e;
import com.bumptech.glide.load.model.f;
import com.bumptech.glide.load.model.k;
import com.bumptech.glide.load.model.s;
import com.bumptech.glide.load.model.stream.a;
import com.bumptech.glide.load.model.stream.b;
import com.bumptech.glide.load.model.stream.c;
import com.bumptech.glide.load.model.stream.d;
import com.bumptech.glide.load.model.stream.e;
import com.bumptech.glide.load.model.u;
import com.bumptech.glide.load.model.v;
import com.bumptech.glide.load.model.w;
import com.bumptech.glide.load.model.x;
import com.bumptech.glide.load.resource.bitmap.DefaultImageHeaderParser;
import com.bumptech.glide.load.resource.bitmap.VideoDecoder;
import com.bumptech.glide.load.resource.bitmap.a0;
import com.bumptech.glide.load.resource.bitmap.g;
import com.bumptech.glide.load.resource.bitmap.m;
import com.bumptech.glide.load.resource.bitmap.p;
import com.bumptech.glide.load.resource.bitmap.t;
import com.bumptech.glide.load.resource.bitmap.v;
import com.bumptech.glide.load.resource.bitmap.w;
import com.bumptech.glide.load.resource.bitmap.y;
import com.bumptech.glide.load.resource.bytes.a;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.load.resource.gif.i;
import com.bumptech.glide.manager.d;
import com.bumptech.glide.manager.o;
import com.bumptech.glide.module.c;
import com.bumptech.glide.request.f;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* compiled from: Glide */
public class b implements ComponentCallbacks2 {
    @GuardedBy("Glide.class")
    private static volatile b c;
    private static volatile boolean d;
    private final o a1;
    @GuardedBy("managers")
    private final List<i> a2 = new ArrayList();
    private final j f;
    private final com.bumptech.glide.load.engine.bitmap_recycle.b p0;
    private final d p1;
    private final a p2;
    private f p3 = f.NORMAL;
    private final e q;
    private final h x;
    private final d y;
    private final Registry z;

    /* compiled from: Glide */
    public interface a {
        @NonNull
        f build();
    }

    @NonNull
    public static b c(@NonNull Context context) {
        if (c == null) {
            GeneratedAppGlideModule annotationGeneratedModule = d(context.getApplicationContext());
            synchronized (b.class) {
                if (c == null) {
                    a(context, annotationGeneratedModule);
                }
            }
        }
        return c;
    }

    @GuardedBy("Glide.class")
    private static void a(@NonNull Context context, @Nullable GeneratedAppGlideModule generatedAppGlideModule) {
        if (!d) {
            d = true;
            m(context, generatedAppGlideModule);
            d = false;
            return;
        }
        throw new IllegalStateException("You cannot call Glide.get() in registerComponents(), use the provided Glide instance instead");
    }

    @GuardedBy("Glide.class")
    private static void m(@NonNull Context context, @Nullable GeneratedAppGlideModule generatedAppGlideModule) {
        n(context, new c(), generatedAppGlideModule);
    }

    @GuardedBy("Glide.class")
    private static void n(@NonNull Context context, @NonNull c builder, @Nullable GeneratedAppGlideModule annotationGeneratedModule) {
        o.b factory;
        Context applicationContext = context.getApplicationContext();
        List<c> emptyList = Collections.emptyList();
        if (annotationGeneratedModule == null || annotationGeneratedModule.c()) {
            emptyList = new com.bumptech.glide.module.e(applicationContext).a();
        }
        if (annotationGeneratedModule != null && !annotationGeneratedModule.d().isEmpty()) {
            Set<Class<?>> excludedModuleClasses = annotationGeneratedModule.d();
            Iterator<c> it = emptyList.iterator();
            while (it.hasNext()) {
                c current = it.next();
                if (excludedModuleClasses.contains(current.getClass())) {
                    if (Log.isLoggable("Glide", 3)) {
                        Log.d("Glide", "AppGlideModule excludes manifest GlideModule: " + current);
                    }
                    it.remove();
                }
            }
        }
        if (Log.isLoggable("Glide", 3)) {
            for (c glideModule : emptyList) {
                Log.d("Glide", "Discovered GlideModule from manifest: " + glideModule.getClass());
            }
        }
        if (annotationGeneratedModule != null) {
            factory = annotationGeneratedModule.e();
        } else {
            factory = null;
        }
        builder.b(factory);
        for (c module : emptyList) {
            module.a(applicationContext, builder);
        }
        if (annotationGeneratedModule != null) {
            annotationGeneratedModule.a(applicationContext, builder);
        }
        b glide = builder.a(applicationContext);
        for (c module2 : emptyList) {
            try {
                module2.b(applicationContext, glide, glide.z);
            } catch (AbstractMethodError e) {
                throw new IllegalStateException("Attempting to register a Glide v3 module. If you see this, you or one of your dependencies may be including Glide v3 even though you're using Glide v4. You'll need to find and remove (or update) the offending dependency. The v3 module name is: " + module2.getClass().getName(), e);
            }
        }
        if (annotationGeneratedModule != null) {
            annotationGeneratedModule.b(applicationContext, glide, glide.z);
        }
        applicationContext.registerComponentCallbacks(glide);
        c = glide;
    }

    @Nullable
    private static GeneratedAppGlideModule d(Context context) {
        try {
            return (GeneratedAppGlideModule) Class.forName("com.bumptech.glide.GeneratedAppGlideModuleImpl").getDeclaredConstructor(new Class[]{Context.class}).newInstance(new Object[]{context.getApplicationContext()});
        } catch (ClassNotFoundException e) {
            if (!Log.isLoggable("Glide", 5)) {
                return null;
            }
            Log.w("Glide", "Failed to find GeneratedAppGlideModule. You should include an annotationProcessor compile dependency on com.github.bumptech.glide:compiler in your application and a @GlideModule annotated AppGlideModule implementation or LibraryGlideModules will be silently ignored");
            return null;
        } catch (InstantiationException e2) {
            q(e2);
            return null;
        } catch (IllegalAccessException e3) {
            q(e3);
            return null;
        } catch (NoSuchMethodException e4) {
            q(e4);
            return null;
        } catch (InvocationTargetException e5) {
            q(e5);
            return null;
        }
    }

    private static void q(Exception e) {
        throw new IllegalStateException("GeneratedAppGlideModuleImpl is implemented incorrectly. If you've manually implemented this class, remove your implementation. The Annotation processor will generate a correct implementation.", e);
    }

    b(@NonNull Context context, @NonNull j engine, @NonNull h memoryCache, @NonNull e bitmapPool, @NonNull com.bumptech.glide.load.engine.bitmap_recycle.b arrayPool, @NonNull o requestManagerRetriever, @NonNull d connectivityMonitorFactory, int logLevel, @NonNull a defaultRequestOptionsFactory, @NonNull Map<Class<?>, j<?, ?>> defaultTransitionOptions, @NonNull List<com.bumptech.glide.request.e<Object>> defaultRequestListeners, e experiments) {
        com.bumptech.glide.load.resource.bitmap.h hVar;
        k kVar;
        Context context2 = context;
        e eVar = bitmapPool;
        com.bumptech.glide.load.engine.bitmap_recycle.b bVar = arrayPool;
        Class<com.bumptech.glide.gifdecoder.a> cls = com.bumptech.glide.gifdecoder.a.class;
        Class<String> cls2 = String.class;
        Class<Integer> cls3 = Integer.class;
        Class<byte[]> cls4 = byte[].class;
        this.f = engine;
        this.q = eVar;
        this.p0 = bVar;
        this.x = memoryCache;
        this.a1 = requestManagerRetriever;
        this.p1 = connectivityMonitorFactory;
        this.p2 = defaultRequestOptionsFactory;
        Resources resources = context.getResources();
        Registry registry = new Registry();
        this.z = registry;
        registry.r(new DefaultImageHeaderParser());
        int i = Build.VERSION.SDK_INT;
        if (i >= 27) {
            registry.r(new p());
        }
        List<ImageHeaderParser> imageHeaderParsers = registry.g();
        com.bumptech.glide.load.resource.gif.a byteBufferGifDecoder = new com.bumptech.glide.load.resource.gif.a(context2, imageHeaderParsers, eVar, bVar);
        ResourceDecoder<ParcelFileDescriptor, Bitmap> parcelFileDescriptorVideoDecoder = VideoDecoder.h(bitmapPool);
        m downsampler = new m(registry.g(), resources.getDisplayMetrics(), eVar, bVar);
        if (!experiments.a(c.b.class) || i < 28) {
            g gVar = new g(downsampler);
            kVar = new y(downsampler, bVar);
            hVar = gVar;
        } else {
            kVar = new t();
            hVar = new com.bumptech.glide.load.resource.bitmap.h();
        }
        Class<byte[]> cls5 = cls4;
        com.bumptech.glide.load.resource.drawable.d resourceDrawableDecoder = new com.bumptech.glide.load.resource.drawable.d(context2);
        int i2 = i;
        s.c resourceLoaderStreamFactory = new s.c(resources);
        s.d resourceLoaderUriFactory = new s.d(resources);
        Class<String> cls6 = cls2;
        s.b resourceLoaderFileDescriptorFactory = new s.b(resources);
        s.d resourceLoaderUriFactory2 = resourceLoaderUriFactory;
        s.a resourceLoaderAssetFileDescriptorFactory = new s.a(resources);
        com.bumptech.glide.load.resource.bitmap.c bitmapEncoder = new com.bumptech.glide.load.resource.bitmap.c(bVar);
        com.bumptech.glide.load.resource.transcode.a bitmapBytesTranscoder = new com.bumptech.glide.load.resource.transcode.a();
        com.bumptech.glide.load.resource.transcode.d gifDrawableBytesTranscoder = new com.bumptech.glide.load.resource.transcode.d();
        Class<Integer> cls7 = cls3;
        ContentResolver contentResolver = context.getContentResolver();
        s.b resourceLoaderFileDescriptorFactory2 = resourceLoaderFileDescriptorFactory;
        s.c resourceLoaderStreamFactory2 = resourceLoaderStreamFactory;
        com.bumptech.glide.load.resource.drawable.d resourceDrawableDecoder2 = resourceDrawableDecoder;
        registry.a(ByteBuffer.class, new com.bumptech.glide.load.model.c()).a(InputStream.class, new com.bumptech.glide.load.model.t(bVar)).e("Bitmap", ByteBuffer.class, Bitmap.class, hVar).e("Bitmap", InputStream.class, Bitmap.class, kVar);
        if (ParcelFileDescriptorRewinder.c()) {
            registry.e("Bitmap", ParcelFileDescriptor.class, Bitmap.class, new v(downsampler));
        }
        m mVar = downsampler;
        k kVar2 = hVar;
        com.bumptech.glide.load.resource.drawable.d resourceDrawableDecoder3 = resourceDrawableDecoder2;
        registry.e("Bitmap", ParcelFileDescriptor.class, Bitmap.class, parcelFileDescriptorVideoDecoder).e("Bitmap", AssetFileDescriptor.class, Bitmap.class, VideoDecoder.c(bitmapPool)).d(Bitmap.class, Bitmap.class, v.a.a()).e("Bitmap", Bitmap.class, Bitmap.class, new a0()).b(Bitmap.class, bitmapEncoder).e("BitmapDrawable", ByteBuffer.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, hVar)).e("BitmapDrawable", InputStream.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, kVar)).e("BitmapDrawable", ParcelFileDescriptor.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, parcelFileDescriptorVideoDecoder)).b(BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.b(eVar, bitmapEncoder)).e("Gif", InputStream.class, GifDrawable.class, new i(imageHeaderParsers, byteBufferGifDecoder, bVar)).e("Gif", ByteBuffer.class, GifDrawable.class, byteBufferGifDecoder).b(GifDrawable.class, new com.bumptech.glide.load.resource.gif.c()).d(cls, cls, v.a.a()).e("Bitmap", cls, Bitmap.class, new com.bumptech.glide.load.resource.gif.g(eVar)).c(Uri.class, Drawable.class, resourceDrawableDecoder3).c(Uri.class, Bitmap.class, new w(resourceDrawableDecoder3, eVar)).s(new a.C0042a()).d(File.class, ByteBuffer.class, new d.b()).d(File.class, InputStream.class, new f.e()).c(File.class, File.class, new com.bumptech.glide.load.resource.file.a()).d(File.class, ParcelFileDescriptor.class, new f.b()).d(File.class, File.class, v.a.a()).s(new k.a(bVar));
        if (ParcelFileDescriptorRewinder.c()) {
            registry.s(new ParcelFileDescriptorRewinder.a());
        }
        Class cls8 = Integer.TYPE;
        s.c resourceLoaderStreamFactory3 = resourceLoaderStreamFactory2;
        s.b resourceLoaderFileDescriptorFactory3 = resourceLoaderFileDescriptorFactory2;
        Class<Integer> cls9 = cls7;
        com.bumptech.glide.load.resource.bitmap.c cVar = bitmapEncoder;
        s.d resourceLoaderUriFactory3 = resourceLoaderUriFactory2;
        s.a resourceLoaderAssetFileDescriptorFactory2 = resourceLoaderAssetFileDescriptorFactory;
        Class<String> cls10 = cls6;
        Context context3 = context;
        registry.d(cls8, InputStream.class, resourceLoaderStreamFactory3).d(cls8, ParcelFileDescriptor.class, resourceLoaderFileDescriptorFactory3).d(cls9, InputStream.class, resourceLoaderStreamFactory3).d(cls9, ParcelFileDescriptor.class, resourceLoaderFileDescriptorFactory3).d(cls9, Uri.class, resourceLoaderUriFactory3).d(cls8, AssetFileDescriptor.class, resourceLoaderAssetFileDescriptorFactory2).d(cls9, AssetFileDescriptor.class, resourceLoaderAssetFileDescriptorFactory2).d(cls8, Uri.class, resourceLoaderUriFactory3).d(cls10, InputStream.class, new e.c()).d(Uri.class, InputStream.class, new e.c()).d(cls10, InputStream.class, new u.c()).d(cls10, ParcelFileDescriptor.class, new u.b()).d(cls10, AssetFileDescriptor.class, new u.a()).d(Uri.class, InputStream.class, new a.c(context.getAssets())).d(Uri.class, ParcelFileDescriptor.class, new a.b(context.getAssets())).d(Uri.class, InputStream.class, new b.a(context3)).d(Uri.class, InputStream.class, new c.a(context3));
        int i3 = i2;
        if (i3 >= 29) {
            registry.d(Uri.class, InputStream.class, new d.c(context3));
            registry.d(Uri.class, ParcelFileDescriptor.class, new d.b(context3));
        }
        com.bumptech.glide.load.resource.drawable.d resourceDrawableDecoder4 = resourceDrawableDecoder3;
        ContentResolver contentResolver2 = contentResolver;
        List<ImageHeaderParser> list = imageHeaderParsers;
        Class<byte[]> cls11 = cls5;
        com.bumptech.glide.load.resource.transcode.a bitmapBytesTranscoder2 = bitmapBytesTranscoder;
        s.d dVar = resourceLoaderUriFactory3;
        com.bumptech.glide.load.resource.transcode.d gifDrawableBytesTranscoder2 = gifDrawableBytesTranscoder;
        registry.d(Uri.class, InputStream.class, new w.d(contentResolver2)).d(Uri.class, ParcelFileDescriptor.class, new w.b(contentResolver2)).d(Uri.class, AssetFileDescriptor.class, new w.a(contentResolver2)).d(Uri.class, InputStream.class, new x.a()).d(URL.class, InputStream.class, new e.a()).d(Uri.class, File.class, new k.a(context3)).d(com.bumptech.glide.load.model.g.class, InputStream.class, new a.C0038a()).d(cls11, ByteBuffer.class, new b.a()).d(cls11, InputStream.class, new b.d()).d(Uri.class, Uri.class, v.a.a()).d(Drawable.class, Drawable.class, v.a.a()).c(Drawable.class, Drawable.class, new com.bumptech.glide.load.resource.drawable.e()).t(Bitmap.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.transcode.b(resources)).t(Bitmap.class, cls11, bitmapBytesTranscoder2).t(Drawable.class, cls11, new com.bumptech.glide.load.resource.transcode.c(eVar, bitmapBytesTranscoder2, gifDrawableBytesTranscoder2)).t(GifDrawable.class, cls11, gifDrawableBytesTranscoder2);
        if (i3 >= 23) {
            ResourceDecoder<ByteBuffer, Bitmap> byteBufferVideoDecoder = VideoDecoder.d(bitmapPool);
            registry.c(ByteBuffer.class, Bitmap.class, byteBufferVideoDecoder);
            registry.c(ByteBuffer.class, BitmapDrawable.class, new com.bumptech.glide.load.resource.bitmap.a(resources, byteBufferVideoDecoder));
        }
        s.c cVar2 = resourceLoaderStreamFactory2;
        s.b bVar2 = resourceLoaderFileDescriptorFactory3;
        ContentResolver contentResolver3 = contentResolver;
        com.bumptech.glide.load.resource.transcode.a aVar = bitmapBytesTranscoder2;
        com.bumptech.glide.load.resource.drawable.d dVar2 = resourceDrawableDecoder4;
        Resources resources2 = resources;
        com.bumptech.glide.load.resource.gif.a aVar2 = byteBufferGifDecoder;
        com.bumptech.glide.load.k kVar3 = kVar;
        com.bumptech.glide.load.k kVar4 = kVar2;
        this.y = new d(context, arrayPool, registry, new com.bumptech.glide.request.target.f(), defaultRequestOptionsFactory, defaultTransitionOptions, defaultRequestListeners, engine, experiments, logLevel);
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.e f() {
        return this.q;
    }

    @NonNull
    public com.bumptech.glide.load.engine.bitmap_recycle.b e() {
        return this.p0;
    }

    @NonNull
    public Context h() {
        return this.y.getBaseContext();
    }

    /* access modifiers changed from: package-private */
    public com.bumptech.glide.manager.d g() {
        return this.p1;
    }

    /* access modifiers changed from: package-private */
    @NonNull
    public d i() {
        return this.y;
    }

    public void b() {
        com.bumptech.glide.util.j.a();
        this.x.d();
        this.q.d();
        this.p0.d();
    }

    public void r(int level) {
        com.bumptech.glide.util.j.a();
        synchronized (this.a2) {
            for (i manager : this.a2) {
                manager.onTrimMemory(level);
            }
        }
        this.x.a(level);
        this.q.a(level);
        this.p0.a(level);
    }

    @NonNull
    public o k() {
        return this.a1;
    }

    @NonNull
    private static o l(@Nullable Context context) {
        com.bumptech.glide.util.i.e(context, "You cannot start a load on a not yet attached View or a Fragment where getActivity() returns null (which usually occurs when getActivity() is called before the Fragment is attached or after the Fragment is destroyed).");
        return c(context).k();
    }

    @NonNull
    public static i t(@NonNull Context context) {
        return l(context).f(context);
    }

    @NonNull
    public static i u(@NonNull FragmentActivity activity) {
        return l(activity).g(activity);
    }

    @NonNull
    public Registry j() {
        return this.z;
    }

    /* access modifiers changed from: package-private */
    public boolean p(@NonNull com.bumptech.glide.request.target.j<?> target) {
        synchronized (this.a2) {
            for (i requestManager : this.a2) {
                if (requestManager.x(target)) {
                    return true;
                }
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public void o(i requestManager) {
        synchronized (this.a2) {
            if (!this.a2.contains(requestManager)) {
                this.a2.add(requestManager);
            } else {
                throw new IllegalStateException("Cannot register already registered manager");
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void s(i requestManager) {
        synchronized (this.a2) {
            if (this.a2.contains(requestManager)) {
                this.a2.remove(requestManager);
            } else {
                throw new IllegalStateException("Cannot unregister not yet registered manager");
            }
        }
    }

    public void onTrimMemory(int level) {
        r(level);
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public void onLowMemory() {
        b();
    }
}
