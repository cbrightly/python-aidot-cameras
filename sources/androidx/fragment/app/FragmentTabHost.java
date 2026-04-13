package androidx.fragment.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

@Deprecated
public class FragmentTabHost extends TabHost implements TabHost.OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private TabHost.OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList<>();

    public static final class TabInfo {
        @Nullable
        final Bundle args;
        @NonNull
        final Class<?> clss;
        Fragment fragment;
        @NonNull
        final String tag;

        TabInfo(@NonNull String _tag, @NonNull Class<?> _class, @Nullable Bundle _args) {
            this.tag = _tag;
            this.clss = _class;
            this.args = _args;
        }
    }

    public static class DummyTabFactory implements TabHost.TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context context) {
            this.mContext = context;
        }

        public View createTabContent(String tag) {
            View v = new View(this.mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        String curTab;

        SavedState(Parcelable superState) {
            super(superState);
        }

        SavedState(Parcel in) {
            super(in);
            this.curTab = in.readString();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(this.curTab);
        }

        @NonNull
        public String toString() {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
        }
    }

    @Deprecated
    public FragmentTabHost(@NonNull Context context) {
        super(context, (AttributeSet) null);
        initFragmentTabHost(context, (AttributeSet) null);
    }

    @Deprecated
    public FragmentTabHost(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initFragmentTabHost(context, attrs);
    }

    private void initFragmentTabHost(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, new int[]{16842995}, 0, 0);
        this.mContainerId = a.getResourceId(0, 0);
        a.recycle();
        super.setOnTabChangedListener(this);
    }

    private void ensureHierarchy(Context context) {
        if (findViewById(16908307) == null) {
            LinearLayout ll = new LinearLayout(context);
            ll.setOrientation(1);
            addView(ll, new FrameLayout.LayoutParams(-1, -1));
            TabWidget tw = new TabWidget(context);
            tw.setId(16908307);
            tw.setOrientation(0);
            ll.addView(tw, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout fl = new FrameLayout(context);
            fl.setId(16908305);
            ll.addView(fl, new LinearLayout.LayoutParams(0, 0, 0.0f));
            FrameLayout fl2 = new FrameLayout(context);
            this.mRealTabContent = fl2;
            fl2.setId(this.mContainerId);
            ll.addView(fl2, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Deprecated
    public void setup() {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    @Deprecated
    public void setup(@NonNull Context context, @NonNull FragmentManager manager) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        ensureContent();
    }

    @Deprecated
    public void setup(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        ensureHierarchy(context);
        super.setup();
        this.mContext = context;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
        ensureContent();
        this.mRealTabContent.setId(containerId);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void ensureContent() {
        if (this.mRealTabContent == null) {
            FrameLayout frameLayout = (FrameLayout) findViewById(this.mContainerId);
            this.mRealTabContent = frameLayout;
            if (frameLayout == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    @Deprecated
    public void setOnTabChangedListener(@Nullable TabHost.OnTabChangeListener l) {
        this.mOnTabChangeListener = l;
    }

    @Deprecated
    public void addTab(@NonNull TabHost.TabSpec tabSpec, @NonNull Class<?> clss, @Nullable Bundle args) {
        tabSpec.setContent(new DummyTabFactory(this.mContext));
        String tag = tabSpec.getTag();
        TabInfo info = new TabInfo(tag, clss, args);
        if (this.mAttached) {
            Fragment findFragmentByTag = this.mFragmentManager.findFragmentByTag(tag);
            info.fragment = findFragmentByTag;
            if (findFragmentByTag != null && !findFragmentByTag.isDetached()) {
                FragmentTransaction ft = this.mFragmentManager.beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
        }
        this.mTabs.add(info);
        addTab(tabSpec);
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        String currentTag = getCurrentTabTag();
        FragmentTransaction ft = null;
        int count = this.mTabs.size();
        for (int i = 0; i < count; i++) {
            TabInfo tab = this.mTabs.get(i);
            Fragment findFragmentByTag = this.mFragmentManager.findFragmentByTag(tab.tag);
            tab.fragment = findFragmentByTag;
            if (findFragmentByTag != null && !findFragmentByTag.isDetached()) {
                if (tab.tag.equals(currentTag)) {
                    this.mLastTab = tab;
                } else {
                    if (ft == null) {
                        ft = this.mFragmentManager.beginTransaction();
                    }
                    ft.detach(tab.fragment);
                }
            }
        }
        this.mAttached = true;
        FragmentTransaction ft2 = doTabChanged(currentTag, ft);
        if (ft2 != null) {
            ft2.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    /* access modifiers changed from: protected */
    @NonNull
    @Deprecated
    public Parcelable onSaveInstanceState() {
        SavedState ss = new SavedState(super.onSaveInstanceState());
        ss.curTab = getCurrentTabTag();
        return ss;
    }

    /* access modifiers changed from: protected */
    @Deprecated
    public void onRestoreInstanceState(@SuppressLint({"UnknownNullness"}) Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentTabByTag(ss.curTab);
    }

    @Deprecated
    public void onTabChanged(@Nullable String tabId) {
        FragmentTransaction ft;
        if (this.mAttached && (ft = doTabChanged(tabId, (FragmentTransaction) null)) != null) {
            ft.commit();
        }
        TabHost.OnTabChangeListener onTabChangeListener = this.mOnTabChangeListener;
        if (onTabChangeListener != null) {
            onTabChangeListener.onTabChanged(tabId);
        }
    }

    @Nullable
    private FragmentTransaction doTabChanged(@Nullable String tag, @Nullable FragmentTransaction ft) {
        Fragment fragment;
        TabInfo newTab = getTabInfoForTag(tag);
        if (this.mLastTab != newTab) {
            if (ft == null) {
                ft = this.mFragmentManager.beginTransaction();
            }
            TabInfo tabInfo = this.mLastTab;
            if (!(tabInfo == null || (fragment = tabInfo.fragment) == null)) {
                ft.detach(fragment);
            }
            if (newTab != null) {
                Fragment fragment2 = newTab.fragment;
                if (fragment2 == null) {
                    Fragment instantiate = this.mFragmentManager.getFragmentFactory().instantiate(this.mContext.getClassLoader(), newTab.clss.getName());
                    newTab.fragment = instantiate;
                    instantiate.setArguments(newTab.args);
                    ft.add(this.mContainerId, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(fragment2);
                }
            }
            this.mLastTab = newTab;
        }
        return ft;
    }

    @Nullable
    private TabInfo getTabInfoForTag(String tabId) {
        int count = this.mTabs.size();
        for (int i = 0; i < count; i++) {
            TabInfo tab = this.mTabs.get(i);
            if (tab.tag.equals(tabId)) {
                return tab;
            }
        }
        return null;
    }
}
