package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import java.lang.ref.WeakReference;

public class TintResources extends ResourcesWrapper {
    private final WeakReference<Context> mContextRef;

    public TintResources(@NonNull Context context, @NonNull Resources res) {
        super(res);
        this.mContextRef = new WeakReference<>(context);
    }

    public Drawable getDrawable(int id) {
        Drawable d = super.getDrawable(id);
        Context context = (Context) this.mContextRef.get();
        if (!(d == null || context == null)) {
            ResourceManagerInternal.get().tintDrawableUsingColorFilter(context, id, d);
        }
        return d;
    }
}
