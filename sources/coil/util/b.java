package coil.util;

import android.graphics.Canvas;
import android.graphics.PostProcessor;
import coil.transform.a;

/* compiled from: lambda */
public final /* synthetic */ class b implements PostProcessor {
    public final /* synthetic */ a a;

    public /* synthetic */ b(a aVar) {
        this.a = aVar;
    }

    public final int onPostProcess(Canvas canvas) {
        return GifExtensions.d(this.a, canvas);
    }
}
