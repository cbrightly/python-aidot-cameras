package com.sensorsdata.analytics.android.sdk.visual.snap;

import android.view.View;
import com.sensorsdata.analytics.android.sdk.visual.snap.Pathfinder;
import java.util.List;

public abstract class ViewVisitor implements Pathfinder.Accumulator {
    private static final String TAG = "SA.ViewVisitor";
    private final List<Pathfinder.PathElement> mPath;
    private final Pathfinder mPathfinder = new Pathfinder();

    public abstract void cleanup();

    /* access modifiers changed from: protected */
    public abstract String name();

    protected ViewVisitor(List<Pathfinder.PathElement> path) {
        this.mPath = path;
    }

    public void visit(View rootView) {
        this.mPathfinder.findTargetsInRoot(rootView, this.mPath, this);
    }
}
