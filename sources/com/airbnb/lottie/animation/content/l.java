package com.airbnb.lottie.animation.content;

import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import com.airbnb.lottie.model.content.i;
import java.util.ArrayList;
import java.util.List;

@TargetApi(19)
/* compiled from: MergePathsContent */
public class l implements m, j {
    private final Path a = new Path();
    private final Path b = new Path();
    private final Path c = new Path();
    private final String d;
    private final List<m> e = new ArrayList();
    private final i f;

    public l(i mergePaths) {
        if (Build.VERSION.SDK_INT >= 19) {
            this.d = mergePaths.c();
            this.f = mergePaths;
            return;
        }
        throw new IllegalStateException("Merge paths are not supported pre-KitKat.");
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP:0: B:0:0x0000->B:3:0x000a, LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void g(java.util.ListIterator<com.airbnb.lottie.animation.content.c> r4) {
        /*
            r3 = this;
        L_0x0000:
            boolean r0 = r4.hasPrevious()
            if (r0 == 0) goto L_0x000d
            java.lang.Object r0 = r4.previous()
            if (r0 == r3) goto L_0x000d
            goto L_0x0000
        L_0x000d:
            boolean r0 = r4.hasPrevious()
            if (r0 == 0) goto L_0x0029
            java.lang.Object r0 = r4.previous()
            com.airbnb.lottie.animation.content.c r0 = (com.airbnb.lottie.animation.content.c) r0
            boolean r1 = r0 instanceof com.airbnb.lottie.animation.content.m
            if (r1 == 0) goto L_0x0028
            java.util.List<com.airbnb.lottie.animation.content.m> r1 = r3.e
            r2 = r0
            com.airbnb.lottie.animation.content.m r2 = (com.airbnb.lottie.animation.content.m) r2
            r1.add(r2)
            r4.remove()
        L_0x0028:
            goto L_0x000d
        L_0x0029:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.airbnb.lottie.animation.content.l.g(java.util.ListIterator):void");
    }

    public void b(List<c> contentsBefore, List<c> contentsAfter) {
        for (int i = 0; i < this.e.size(); i++) {
            this.e.get(i).b(contentsBefore, contentsAfter);
        }
    }

    public Path getPath() {
        this.c.reset();
        if (this.f.d()) {
            return this.c;
        }
        switch (a.a[this.f.b().ordinal()]) {
            case 1:
                a();
                break;
            case 2:
                d(Path.Op.UNION);
                break;
            case 3:
                d(Path.Op.REVERSE_DIFFERENCE);
                break;
            case 4:
                d(Path.Op.INTERSECT);
                break;
            case 5:
                d(Path.Op.XOR);
                break;
        }
        return this.c;
    }

    /* compiled from: MergePathsContent */
    public static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[i.a.values().length];
            a = iArr;
            try {
                iArr[i.a.MERGE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[i.a.ADD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[i.a.SUBTRACT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[i.a.INTERSECT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[i.a.EXCLUDE_INTERSECTIONS.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    private void a() {
        for (int i = 0; i < this.e.size(); i++) {
            this.c.addPath(this.e.get(i).getPath());
        }
    }

    @TargetApi(19)
    private void d(Path.Op op) {
        this.b.reset();
        this.a.reset();
        for (int i = this.e.size() - 1; i >= 1; i--) {
            m content = this.e.get(i);
            if (content instanceof d) {
                List<m> k = ((d) content).k();
                for (int j = k.size() - 1; j >= 0; j--) {
                    Path path = k.get(j).getPath();
                    path.transform(((d) content).l());
                    this.b.addPath(path);
                }
            } else {
                this.b.addPath(content.getPath());
            }
        }
        m lastContent = this.e.get(0);
        if (lastContent instanceof d) {
            List<m> k2 = ((d) lastContent).k();
            for (int j2 = 0; j2 < k2.size(); j2++) {
                Path path2 = k2.get(j2).getPath();
                path2.transform(((d) lastContent).l());
                this.a.addPath(path2);
            }
        } else {
            this.a.set(lastContent.getPath());
        }
        this.c.op(this.a, this.b, op);
    }
}
