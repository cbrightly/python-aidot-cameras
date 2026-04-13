package com.sensorsdata.analytics.android.sdk.visual.snap;

import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sensorsdata.analytics.android.sdk.SALog;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Pathfinder {
    private static final String TAG = "SA.PathFinder";
    private final IntStack mIndexStack = new IntStack();

    public interface Accumulator {
        void accumulate(View view);
    }

    public static boolean hasClassName(Object o, String className) {
        Class cls = o.getClass();
        String canonicalName = SnapCache.getInstance().getCanonicalName(cls);
        while (canonicalName != null) {
            if (canonicalName.equals(className)) {
                return true;
            }
            if (cls == Object.class) {
                return false;
            }
            cls = cls.getSuperclass();
            canonicalName = SnapCache.getInstance().getCanonicalName(cls);
        }
        return false;
    }

    public void findTargetsInRoot(View givenRootView, List<PathElement> path, Accumulator accumulator) {
        if (!path.isEmpty()) {
            if (this.mIndexStack.full()) {
                SALog.i(TAG, "Path is too deep, there is no memory to perfrom the finding");
                return;
            }
            List<PathElement> childPath = path.subList(1, path.size());
            View rootView = findPrefixedMatch(path.get(0), givenRootView, this.mIndexStack.alloc());
            this.mIndexStack.free();
            if (rootView != null) {
                findTargetsInMatchedView(rootView, childPath, accumulator);
            }
        }
    }

    private void findTargetsInMatchedView(View alreadyMatched, List<PathElement> remainingPath, Accumulator accumulator) {
        if (remainingPath.isEmpty()) {
            accumulator.accumulate(alreadyMatched);
        } else if (this.mIndexStack.full()) {
            SALog.i(TAG, "Path is too deep, there is no memory to perfrom the finding");
        } else if (alreadyMatched instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) alreadyMatched;
            PathElement matchElement = remainingPath.get(0);
            List<PathElement> nextPath = remainingPath.subList(1, remainingPath.size());
            int childCount = parent.getChildCount();
            int indexKey = this.mIndexStack.alloc();
            for (int i = 0; i < childCount; i++) {
                View child = findPrefixedMatch(matchElement, parent.getChildAt(i), indexKey);
                if (child != null) {
                    findTargetsInMatchedView(child, nextPath, accumulator);
                }
                if (matchElement.index >= 0 && this.mIndexStack.read(indexKey) > matchElement.index) {
                    break;
                }
            }
            this.mIndexStack.free();
        }
    }

    private View findPrefixedMatch(PathElement findElement, View subject, int indexKey) {
        View result;
        int currentIndex = this.mIndexStack.read(indexKey);
        if (matches(findElement, subject)) {
            this.mIndexStack.increment(indexKey);
            int i = findElement.index;
            if (i == -1 || i == currentIndex) {
                return subject;
            }
        }
        if (findElement.prefix != 1 || !(subject instanceof ViewGroup)) {
            return null;
        }
        ViewGroup group = (ViewGroup) subject;
        int childCount = group.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View child = group.getChildAt(i2);
            if (child != null && (result = findPrefixedMatch(findElement, child, indexKey)) != null) {
                return result;
            }
        }
        return null;
    }

    private boolean matches(PathElement matchElement, View subject) {
        String str = matchElement.viewClassName;
        if (str != null && !hasClassName(subject, str)) {
            return false;
        }
        if (-1 == matchElement.viewId || subject.getId() == matchElement.viewId) {
            return true;
        }
        return false;
    }

    public static class PathElement {
        public static final int SHORTEST_PREFIX = 1;
        public static final int ZERO_LENGTH_PREFIX = 0;
        public final int index;
        public final int prefix;
        public final String viewClassName;
        public final int viewId;

        public PathElement(int usePrefix, String vClass, int ix, int vId) {
            this.prefix = usePrefix;
            this.viewClassName = vClass;
            this.index = ix;
            this.viewId = vId;
        }

        public String toString() {
            try {
                JSONObject ret = new JSONObject();
                if (this.prefix == 1) {
                    ret.put("prefix", (Object) "shortest");
                }
                String str = this.viewClassName;
                if (str != null) {
                    ret.put("view_class", (Object) str);
                }
                int i = this.index;
                if (i > -1) {
                    ret.put(FirebaseAnalytics.Param.INDEX, i);
                }
                int i2 = this.viewId;
                if (i2 > -1) {
                    ret.put("id", i2);
                }
                return ret.toString();
            } catch (JSONException e) {
                throw new RuntimeException("Can't serialize PathElement to String", e);
            }
        }
    }

    public static class IntStack {
        private static final int MAX_INDEX_STACK_SIZE = 256;
        private final int[] mStack = new int[256];
        private int mStackSize = 0;

        public boolean full() {
            return this.mStack.length == this.mStackSize;
        }

        public int alloc() {
            int index = this.mStackSize;
            this.mStackSize++;
            this.mStack[index] = 0;
            return index;
        }

        public int read(int index) {
            return this.mStack[index];
        }

        public void increment(int index) {
            int[] iArr = this.mStack;
            iArr[index] = iArr[index] + 1;
        }

        public void free() {
            int i = this.mStackSize - 1;
            this.mStackSize = i;
            if (i < 0) {
                throw new ArrayIndexOutOfBoundsException(this.mStackSize);
            }
        }
    }
}
