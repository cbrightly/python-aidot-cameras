package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class SVGImageView extends ImageView {
    private static Method c;
    /* access modifiers changed from: private */
    public g d = null;
    private f f = new f();

    static {
        c = null;
        Class<View> cls = View.class;
        try {
            c = cls.getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class});
        } catch (NoSuchMethodException e) {
        }
    }

    public SVGImageView(Context context) {
        super(context);
    }

    public SVGImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        d(attrs, 0);
    }

    public SVGImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        d(attrs, defStyle);
    }

    private void d(AttributeSet attrs, int defStyle) {
        if (!isInEditMode()) {
            TypedArray a2 = getContext().getTheme().obtainStyledAttributes(attrs, R$styleable.SVGImageView, defStyle, 0);
            try {
                String css = a2.getString(R$styleable.SVGImageView_css);
                if (css != null) {
                    this.f.a(css);
                }
                int i = R$styleable.SVGImageView_svg;
                int resourceId = a2.getResourceId(i, -1);
                if (resourceId != -1) {
                    setImageResource(resourceId);
                    return;
                }
                String url = a2.getString(i);
                if (url != null) {
                    if (f(Uri.parse(url))) {
                        a2.recycle();
                        return;
                    } else if (e(url)) {
                        a2.recycle();
                        return;
                    } else {
                        setFromString(url);
                    }
                }
                a2.recycle();
            } finally {
                a2.recycle();
            }
        }
    }

    public void setSVG(g svg) {
        if (svg != null) {
            this.d = svg;
            c();
            return;
        }
        throw new IllegalArgumentException("Null value passed to setSVG()");
    }

    public void setCSS(String css) {
        this.f.a(css);
        c();
    }

    public void setImageResource(int resourceId) {
        new b(getContext(), resourceId).execute(new Integer[0]);
    }

    public void setImageURI(Uri uri) {
        if (!f(uri)) {
            Log.e("SVGImageView", "File not found: " + uri);
        }
    }

    public void setImageAsset(String filename) {
        if (!e(filename)) {
            Log.e("SVGImageView", "File not found: " + filename);
        }
    }

    private boolean f(Uri uri) {
        try {
            InputStream is = getContext().getContentResolver().openInputStream(uri);
            new c().execute(new InputStream[]{is});
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    private boolean e(String filename) {
        try {
            InputStream is = getContext().getAssets().open(filename);
            new c().execute(new InputStream[]{is});
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void setFromString(String url) {
        try {
            this.d = g.o(url);
            c();
        } catch (SVGParseException e) {
            Log.e("SVGImageView", "Could not find SVG at: " + url);
        }
    }

    public class b extends AsyncTask<Integer, Integer, g> {
        private Context a;
        private int b;

        b(Context context, int resourceId) {
            this.a = context;
            this.b = resourceId;
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public g doInBackground(Integer... params) {
            try {
                return g.m(this.a, this.b);
            } catch (SVGParseException e) {
                Log.e("SVGImageView", String.format("Error loading resource 0x%x: %s", new Object[]{Integer.valueOf(this.b), e.getMessage()}));
                return null;
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public void onPostExecute(g svg) {
            g unused = SVGImageView.this.d = svg;
            SVGImageView.this.c();
        }
    }

    public class c extends AsyncTask<InputStream, Integer, g> {
        private c() {
        }

        /* access modifiers changed from: protected */
        /* renamed from: a */
        public g doInBackground(InputStream... is) {
            try {
                g l = g.l(is[0]);
                try {
                    is[0].close();
                } catch (IOException e) {
                }
                return l;
            } catch (SVGParseException e2) {
                Log.e("SVGImageView", "Parse error loading URI: " + e2.getMessage());
                try {
                    is[0].close();
                    return null;
                } catch (IOException e3) {
                    return null;
                }
            } catch (Throwable th) {
                try {
                    is[0].close();
                } catch (IOException e4) {
                }
                throw th;
            }
        }

        /* access modifiers changed from: protected */
        /* renamed from: b */
        public void onPostExecute(g svg) {
            g unused = SVGImageView.this.d = svg;
            SVGImageView.this.c();
        }
    }

    private void g() {
        if (c != null) {
            try {
                int LAYER_TYPE_SOFTWARE = View.class.getField("LAYER_TYPE_SOFTWARE").getInt(new View(getContext()));
                c.invoke(this, new Object[]{Integer.valueOf(LAYER_TYPE_SOFTWARE), null});
            } catch (Exception e) {
                Log.w("SVGImageView", "Unexpected failure calling setLayerType", e);
            }
        }
    }

    /* access modifiers changed from: private */
    public void c() {
        g gVar = this.d;
        if (gVar != null) {
            Picture picture = gVar.u(this.f);
            g();
            setImageDrawable(new PictureDrawable(picture));
        }
    }
}
