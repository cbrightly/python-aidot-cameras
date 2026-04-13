package com.bumptech.glide.integration.webp.decoder;

import com.bumptech.glide.load.engine.p;
import com.bumptech.glide.load.resource.drawable.b;

/* compiled from: WebpDrawableResource */
public class l extends b<WebpDrawable> implements p {
    public l(WebpDrawable drawable) {
        super(drawable);
    }

    public Class<WebpDrawable> a() {
        return WebpDrawable.class;
    }

    public int getSize() {
        return ((WebpDrawable) this.c).i();
    }

    public void recycle() {
        ((WebpDrawable) this.c).stop();
        ((WebpDrawable) this.c).l();
    }

    public void initialize() {
        ((WebpDrawable) this.c).e().prepareToDraw();
    }
}
