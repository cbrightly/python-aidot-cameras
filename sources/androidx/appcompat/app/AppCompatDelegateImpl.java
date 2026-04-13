package androidx.appcompat.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.ContextThemeWrapper;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.R;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.StandaloneActionMode;
import androidx.appcompat.view.SupportActionModeWrapper;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.WindowCallbackWrapper;
import androidx.appcompat.view.menu.ListMenuPresenter;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.ActionBarContextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.ContentFrameLayout;
import androidx.appcompat.widget.DecorContentParent;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewStubCompat;
import androidx.collection.SimpleArrayMap;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.view.KeyEventDispatcher;
import androidx.core.view.LayoutInflaterCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.core.view.ViewPropertyAnimatorListenerAdapter;
import androidx.core.widget.PopupWindowCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.asm.Opcodes;
import com.tutk.IOTC.AVIOCTRLDEFs;
import java.lang.Thread;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

@RestrictTo({RestrictTo.Scope.LIBRARY})
public class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
    private static final boolean IS_PRE_LOLLIPOP;
    private static final boolean sCanApplyOverrideConfiguration;
    private static final boolean sCanReturnDifferentContext = (!"robolectric".equals(Build.FINGERPRINT));
    private static boolean sInstalledExceptionHandler = true;
    private static final SimpleArrayMap<String, Integer> sLocalNightModes = new SimpleArrayMap<>();
    private static final int[] sWindowBackgroundStyleable = {16842836};
    ActionBar mActionBar;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    private boolean mActivityHandlesUiMode;
    private boolean mActivityHandlesUiModeChecked;
    final AppCompatCallback mAppCompatCallback;
    private AppCompatViewInflater mAppCompatViewInflater;
    private AppCompatWindowCallback mAppCompatWindowCallback;
    private AutoNightModeManager mAutoBatteryNightModeManager;
    private AutoNightModeManager mAutoTimeNightModeManager;
    private boolean mBaseContextAttached;
    private boolean mClosingActionMenu;
    final Context mContext;
    private boolean mCreated;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    ViewPropertyAnimatorCompat mFadeAnim;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    private boolean mHandleNativeActionModes;
    boolean mHasActionBar;
    final Object mHost;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    private final Runnable mInvalidatePanelMenuRunnable;
    boolean mIsDestroyed;
    boolean mIsFloating;
    private LayoutIncludeDetector mLayoutIncludeDetector;
    private int mLocalNightMode;
    private boolean mLongPressBackDown;
    MenuInflater mMenuInflater;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private boolean mStarted;
    private View mStatusGuard;
    ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private int mThemeResId;
    private CharSequence mTitle;
    private TextView mTitleView;
    Window mWindow;
    boolean mWindowNoTitle;

    static {
        int i = Build.VERSION.SDK_INT;
        boolean z = false;
        boolean z2 = i < 21;
        IS_PRE_LOLLIPOP = z2;
        if (i >= 17) {
            z = true;
        }
        sCanApplyOverrideConfiguration = z;
        if (z2 && !sInstalledExceptionHandler) {
            final Thread.UncaughtExceptionHandler defHandler = Thread.getDefaultUncaughtExceptionHandler();
            Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
                    if (shouldWrapException(throwable)) {
                        Throwable wrapped = new Resources.NotFoundException(throwable.getMessage() + AppCompatDelegateImpl.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
                        wrapped.initCause(throwable.getCause());
                        wrapped.setStackTrace(throwable.getStackTrace());
                        defHandler.uncaughtException(thread, wrapped);
                        return;
                    }
                    defHandler.uncaughtException(thread, throwable);
                }

                private boolean shouldWrapException(Throwable throwable) {
                    String message;
                    if (!(throwable instanceof Resources.NotFoundException) || (message = throwable.getMessage()) == null) {
                        return false;
                    }
                    if (message.contains("drawable") || message.contains("Drawable")) {
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    AppCompatDelegateImpl(Activity activity, AppCompatCallback callback) {
        this(activity, (Window) null, callback, activity);
    }

    AppCompatDelegateImpl(Dialog dialog, AppCompatCallback callback) {
        this(dialog.getContext(), dialog.getWindow(), callback, dialog);
    }

    AppCompatDelegateImpl(Context context, Window window, AppCompatCallback callback) {
        this(context, window, callback, context);
    }

    AppCompatDelegateImpl(Context context, Activity activity, AppCompatCallback callback) {
        this(context, (Window) null, callback, activity);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0036, code lost:
        r0 = sLocalNightModes;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private AppCompatDelegateImpl(android.content.Context r4, android.view.Window r5, androidx.appcompat.app.AppCompatCallback r6, java.lang.Object r7) {
        /*
            r3 = this;
            r3.<init>()
            r0 = 0
            r3.mFadeAnim = r0
            r0 = 1
            r3.mHandleNativeActionModes = r0
            r0 = -100
            r3.mLocalNightMode = r0
            androidx.appcompat.app.AppCompatDelegateImpl$2 r1 = new androidx.appcompat.app.AppCompatDelegateImpl$2
            r1.<init>()
            r3.mInvalidatePanelMenuRunnable = r1
            r3.mContext = r4
            r3.mAppCompatCallback = r6
            r3.mHost = r7
            int r1 = r3.mLocalNightMode
            if (r1 != r0) goto L_0x0032
            boolean r1 = r7 instanceof android.app.Dialog
            if (r1 == 0) goto L_0x0032
            androidx.appcompat.app.AppCompatActivity r1 = r3.tryUnwrapContext()
            if (r1 == 0) goto L_0x0032
            androidx.appcompat.app.AppCompatDelegate r2 = r1.getDelegate()
            int r2 = r2.getLocalNightMode()
            r3.mLocalNightMode = r2
        L_0x0032:
            int r1 = r3.mLocalNightMode
            if (r1 != r0) goto L_0x0059
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = sLocalNightModes
            java.lang.Class r1 = r7.getClass()
            java.lang.String r1 = r1.getName()
            java.lang.Object r1 = r0.get(r1)
            java.lang.Integer r1 = (java.lang.Integer) r1
            if (r1 == 0) goto L_0x0059
            int r2 = r1.intValue()
            r3.mLocalNightMode = r2
            java.lang.Class r2 = r7.getClass()
            java.lang.String r2 = r2.getName()
            r0.remove(r2)
        L_0x0059:
            if (r5 == 0) goto L_0x005e
            r3.attachToWindow(r5)
        L_0x005e:
            androidx.appcompat.widget.AppCompatDrawableManager.preload()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.<init>(android.content.Context, android.view.Window, androidx.appcompat.app.AppCompatCallback, java.lang.Object):void");
    }

    @CallSuper
    @NonNull
    public Context attachBaseContext2(@NonNull Context baseContext) {
        boolean needsThemeRebase = true;
        this.mBaseContextAttached = true;
        int modeToApply = mapNightMode(baseContext, calculateNightMode());
        if (sCanApplyOverrideConfiguration && (baseContext instanceof ContextThemeWrapper)) {
            try {
                ContextThemeWrapperCompatApi17Impl.applyOverrideConfiguration((ContextThemeWrapper) baseContext, createOverrideConfigurationForDayNight(baseContext, modeToApply, (Configuration) null));
                return baseContext;
            } catch (IllegalStateException e) {
            }
        }
        if (baseContext instanceof androidx.appcompat.view.ContextThemeWrapper) {
            try {
                ((androidx.appcompat.view.ContextThemeWrapper) baseContext).applyOverrideConfiguration(createOverrideConfigurationForDayNight(baseContext, modeToApply, (Configuration) null));
                return baseContext;
            } catch (IllegalStateException e2) {
            }
        }
        if (!sCanReturnDifferentContext) {
            return super.attachBaseContext2(baseContext);
        }
        Configuration configOverlay = null;
        if (Build.VERSION.SDK_INT >= 17) {
            Configuration overrideConfig = new Configuration();
            overrideConfig.uiMode = -1;
            overrideConfig.fontScale = 0.0f;
            Configuration referenceConfig = Api17Impl.createConfigurationContext(baseContext, overrideConfig).getResources().getConfiguration();
            Configuration baseConfig = baseContext.getResources().getConfiguration();
            referenceConfig.uiMode = baseConfig.uiMode;
            if (!referenceConfig.equals(baseConfig)) {
                configOverlay = generateConfigDelta(referenceConfig, baseConfig);
            }
        }
        Configuration overrideConfig2 = createOverrideConfigurationForDayNight(baseContext, modeToApply, configOverlay);
        androidx.appcompat.view.ContextThemeWrapper wrappedContext = new androidx.appcompat.view.ContextThemeWrapper(baseContext, R.style.Theme_AppCompat_Empty);
        wrappedContext.applyOverrideConfiguration(overrideConfig2);
        try {
            if (baseContext.getTheme() == null) {
                needsThemeRebase = false;
            }
        } catch (NullPointerException e3) {
            needsThemeRebase = false;
        }
        if (needsThemeRebase) {
            ResourcesCompat.ThemeCompat.rebase(wrappedContext.getTheme());
        }
        return super.attachBaseContext2(wrappedContext);
    }

    @RequiresApi(17)
    public static class ContextThemeWrapperCompatApi17Impl {
        private ContextThemeWrapperCompatApi17Impl() {
        }

        static void applyOverrideConfiguration(ContextThemeWrapper context, Configuration overrideConfiguration) {
            context.applyOverrideConfiguration(overrideConfiguration);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        this.mBaseContextAttached = true;
        applyDayNight(false);
        ensureWindow();
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            String parentActivityName = null;
            try {
                parentActivityName = NavUtils.getParentActivityName((Activity) obj);
            } catch (IllegalArgumentException e) {
            }
            if (parentActivityName != null) {
                ActionBar ab = peekSupportActionBar();
                if (ab == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    ab.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
            AppCompatDelegate.addActiveDelegate(this);
        }
        this.mCreated = true;
    }

    public void onPostCreate(Bundle savedInstanceState) {
        ensureSubDecor();
    }

    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    /* access modifiers changed from: package-private */
    public final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    /* access modifiers changed from: package-private */
    public final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    private void initWindowDecorActionBar() {
        ensureSubDecor();
        if (this.mHasActionBar && this.mActionBar == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                this.mActionBar = new WindowDecorActionBar((Activity) this.mHost, this.mOverlayActionBar);
            } else if (obj instanceof Dialog) {
                this.mActionBar = new WindowDecorActionBar((Dialog) this.mHost);
            }
            ActionBar actionBar = this.mActionBar;
            if (actionBar != null) {
                actionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
            }
        }
    }

    public void setSupportActionBar(Toolbar toolbar) {
        if (this.mHost instanceof Activity) {
            ActionBar ab = getSupportActionBar();
            if (!(ab instanceof WindowDecorActionBar)) {
                this.mMenuInflater = null;
                if (ab != null) {
                    ab.onDestroy();
                }
                if (toolbar != null) {
                    ToolbarActionBar tbab = new ToolbarActionBar(toolbar, getTitle(), this.mAppCompatWindowCallback);
                    this.mActionBar = tbab;
                    this.mWindow.setCallback(tbab.getWrappedWindowCallback());
                } else {
                    this.mActionBar = null;
                    this.mWindow.setCallback(this.mAppCompatWindowCallback);
                }
                invalidateOptionsMenu();
                return;
            }
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
    }

    /* access modifiers changed from: package-private */
    public final Context getActionBarThemedContext() {
        Context context = null;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            context = ab.getThemedContext();
        }
        if (context == null) {
            return this.mContext;
        }
        return context;
    }

    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            this.mMenuInflater = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    @Nullable
    public <T extends View> T findViewById(@IdRes int id) {
        ensureSubDecor();
        return this.mWindow.findViewById(id);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        ActionBar ab;
        if (this.mHasActionBar && this.mSubDecorInstalled && (ab = getSupportActionBar()) != null) {
            ab.onConfigurationChanged(newConfig);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        applyDayNight(false);
    }

    public void onStart() {
        this.mStarted = true;
        applyDayNight();
    }

    public void onStop() {
        this.mStarted = false;
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);
        }
    }

    public void onPostResume() {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(true);
        }
    }

    public void setContentView(View v) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        contentParent.addView(v);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void setContentView(int resId) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(resId, contentParent);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void setContentView(View v, ViewGroup.LayoutParams lp) {
        ensureSubDecor();
        ViewGroup contentParent = (ViewGroup) this.mSubDecor.findViewById(16908290);
        contentParent.removeAllViews();
        contentParent.addView(v, lp);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void addContentView(View v, ViewGroup.LayoutParams lp) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(16908290)).addView(v, lp);
        this.mAppCompatWindowCallback.getWrapped().onContentChanged();
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onDestroy() {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mHost
            boolean r0 = r0 instanceof android.app.Activity
            if (r0 == 0) goto L_0x0009
            androidx.appcompat.app.AppCompatDelegate.removeActivityDelegate(r3)
        L_0x0009:
            boolean r0 = r3.mInvalidatePanelMenuPosted
            if (r0 == 0) goto L_0x0018
            android.view.Window r0 = r3.mWindow
            android.view.View r0 = r0.getDecorView()
            java.lang.Runnable r1 = r3.mInvalidatePanelMenuRunnable
            r0.removeCallbacks(r1)
        L_0x0018:
            r0 = 0
            r3.mStarted = r0
            r0 = 1
            r3.mIsDestroyed = r0
            int r0 = r3.mLocalNightMode
            r1 = -100
            if (r0 == r1) goto L_0x0048
            java.lang.Object r0 = r3.mHost
            boolean r1 = r0 instanceof android.app.Activity
            if (r1 == 0) goto L_0x0048
            android.app.Activity r0 = (android.app.Activity) r0
            boolean r0 = r0.isChangingConfigurations()
            if (r0 == 0) goto L_0x0048
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = sLocalNightModes
            java.lang.Object r1 = r3.mHost
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            int r2 = r3.mLocalNightMode
            java.lang.Integer r2 = java.lang.Integer.valueOf(r2)
            r0.put(r1, r2)
            goto L_0x0057
        L_0x0048:
            androidx.collection.SimpleArrayMap<java.lang.String, java.lang.Integer> r0 = sLocalNightModes
            java.lang.Object r1 = r3.mHost
            java.lang.Class r1 = r1.getClass()
            java.lang.String r1 = r1.getName()
            r0.remove(r1)
        L_0x0057:
            androidx.appcompat.app.ActionBar r0 = r3.mActionBar
            if (r0 == 0) goto L_0x005e
            r0.onDestroy()
        L_0x005e:
            r3.cleanupAutoManagers()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.onDestroy():void");
    }

    private void cleanupAutoManagers() {
        AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
        AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
        if (autoNightModeManager2 != null) {
            autoNightModeManager2.cleanup();
        }
    }

    public void setTheme(@StyleRes int themeResId) {
        this.mThemeResId = themeResId;
    }

    private void ensureWindow() {
        if (this.mWindow == null) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                attachToWindow(((Activity) obj).getWindow());
            }
        }
        if (this.mWindow == null) {
            throw new IllegalStateException("We have not been given a Window");
        }
    }

    private void attachToWindow(@NonNull Window window) {
        if (this.mWindow == null) {
            Window.Callback callback = window.getCallback();
            if (!(callback instanceof AppCompatWindowCallback)) {
                AppCompatWindowCallback appCompatWindowCallback = new AppCompatWindowCallback(callback);
                this.mAppCompatWindowCallback = appCompatWindowCallback;
                window.setCallback(appCompatWindowCallback);
                TintTypedArray a = TintTypedArray.obtainStyledAttributes(this.mContext, (AttributeSet) null, sWindowBackgroundStyleable);
                Drawable winBg = a.getDrawableIfKnown(0);
                if (winBg != null) {
                    window.setBackgroundDrawable(winBg);
                }
                a.recycle();
                this.mWindow = window;
                return;
            }
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        throw new IllegalStateException("AppCompat has already installed itself into the Window");
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = createSubDecor();
            CharSequence title = getTitle();
            if (!TextUtils.isEmpty(title)) {
                DecorContentParent decorContentParent = this.mDecorContentParent;
                if (decorContentParent != null) {
                    decorContentParent.setWindowTitle(title);
                } else if (peekSupportActionBar() != null) {
                    peekSupportActionBar().setWindowTitle(title);
                } else {
                    TextView textView = this.mTitleView;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            PanelFeatureState st = getPanelState(0, false);
            if (this.mIsDestroyed) {
                return;
            }
            if (st == null || st.menu == null) {
                invalidatePanelMenu(108);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r2v20, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r2v22, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r8v5, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r2v28, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.view.ViewGroup createSubDecor() {
        /*
            r10 = this;
            android.content.Context r0 = r10.mContext
            int[] r1 = androidx.appcompat.R.styleable.AppCompatTheme
            android.content.res.TypedArray r0 = r0.obtainStyledAttributes(r1)
            int r1 = androidx.appcompat.R.styleable.AppCompatTheme_windowActionBar
            boolean r2 = r0.hasValue(r1)
            if (r2 == 0) goto L_0x01a8
            int r2 = androidx.appcompat.R.styleable.AppCompatTheme_windowNoTitle
            r3 = 0
            boolean r2 = r0.getBoolean(r2, r3)
            r4 = 1
            if (r2 == 0) goto L_0x001e
            r10.requestWindowFeature(r4)
            goto L_0x0029
        L_0x001e:
            boolean r1 = r0.getBoolean(r1, r3)
            if (r1 == 0) goto L_0x0029
            r1 = 108(0x6c, float:1.51E-43)
            r10.requestWindowFeature(r1)
        L_0x0029:
            int r1 = androidx.appcompat.R.styleable.AppCompatTheme_windowActionBarOverlay
            boolean r1 = r0.getBoolean(r1, r3)
            r2 = 109(0x6d, float:1.53E-43)
            if (r1 == 0) goto L_0x0036
            r10.requestWindowFeature(r2)
        L_0x0036:
            int r1 = androidx.appcompat.R.styleable.AppCompatTheme_windowActionModeOverlay
            boolean r1 = r0.getBoolean(r1, r3)
            if (r1 == 0) goto L_0x0043
            r1 = 10
            r10.requestWindowFeature(r1)
        L_0x0043:
            int r1 = androidx.appcompat.R.styleable.AppCompatTheme_android_windowIsFloating
            boolean r1 = r0.getBoolean(r1, r3)
            r10.mIsFloating = r1
            r0.recycle()
            r10.ensureWindow()
            android.view.Window r1 = r10.mWindow
            r1.getDecorView()
            android.content.Context r1 = r10.mContext
            android.view.LayoutInflater r1 = android.view.LayoutInflater.from(r1)
            r5 = 0
            boolean r6 = r10.mWindowNoTitle
            r7 = 0
            if (r6 != 0) goto L_0x00d5
            boolean r6 = r10.mIsFloating
            if (r6 == 0) goto L_0x0075
            int r2 = androidx.appcompat.R.layout.abc_dialog_title_material
            android.view.View r2 = r1.inflate(r2, r7)
            r5 = r2
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            r10.mOverlayActionBar = r3
            r10.mHasActionBar = r3
            goto L_0x00ec
        L_0x0075:
            boolean r6 = r10.mHasActionBar
            if (r6 == 0) goto L_0x00ec
            android.util.TypedValue r6 = new android.util.TypedValue
            r6.<init>()
            android.content.Context r8 = r10.mContext
            android.content.res.Resources$Theme r8 = r8.getTheme()
            int r9 = androidx.appcompat.R.attr.actionBarTheme
            r8.resolveAttribute(r9, r6, r4)
            int r4 = r6.resourceId
            if (r4 == 0) goto L_0x0097
            androidx.appcompat.view.ContextThemeWrapper r4 = new androidx.appcompat.view.ContextThemeWrapper
            android.content.Context r8 = r10.mContext
            int r9 = r6.resourceId
            r4.<init>((android.content.Context) r8, (int) r9)
            goto L_0x0099
        L_0x0097:
            android.content.Context r4 = r10.mContext
        L_0x0099:
            android.view.LayoutInflater r8 = android.view.LayoutInflater.from(r4)
            int r9 = androidx.appcompat.R.layout.abc_screen_toolbar
            android.view.View r8 = r8.inflate(r9, r7)
            r5 = r8
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            int r8 = androidx.appcompat.R.id.decor_content_parent
            android.view.View r8 = r5.findViewById(r8)
            androidx.appcompat.widget.DecorContentParent r8 = (androidx.appcompat.widget.DecorContentParent) r8
            r10.mDecorContentParent = r8
            android.view.Window$Callback r9 = r10.getWindowCallback()
            r8.setWindowCallback(r9)
            boolean r8 = r10.mOverlayActionBar
            if (r8 == 0) goto L_0x00c0
            androidx.appcompat.widget.DecorContentParent r8 = r10.mDecorContentParent
            r8.initFeature(r2)
        L_0x00c0:
            boolean r2 = r10.mFeatureProgress
            if (r2 == 0) goto L_0x00ca
            androidx.appcompat.widget.DecorContentParent r2 = r10.mDecorContentParent
            r8 = 2
            r2.initFeature(r8)
        L_0x00ca:
            boolean r2 = r10.mFeatureIndeterminateProgress
            if (r2 == 0) goto L_0x00d4
            androidx.appcompat.widget.DecorContentParent r2 = r10.mDecorContentParent
            r8 = 5
            r2.initFeature(r8)
        L_0x00d4:
            goto L_0x00ec
        L_0x00d5:
            boolean r2 = r10.mOverlayActionMode
            if (r2 == 0) goto L_0x00e3
            int r2 = androidx.appcompat.R.layout.abc_screen_simple_overlay_action_mode
            android.view.View r2 = r1.inflate(r2, r7)
            r5 = r2
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
            goto L_0x00ec
        L_0x00e3:
            int r2 = androidx.appcompat.R.layout.abc_screen_simple
            android.view.View r2 = r1.inflate(r2, r7)
            r5 = r2
            android.view.ViewGroup r5 = (android.view.ViewGroup) r5
        L_0x00ec:
            if (r5 == 0) goto L_0x0162
            int r2 = android.os.Build.VERSION.SDK_INT
            r4 = 21
            if (r2 < r4) goto L_0x00fd
            androidx.appcompat.app.AppCompatDelegateImpl$3 r2 = new androidx.appcompat.app.AppCompatDelegateImpl$3
            r2.<init>()
            androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(r5, r2)
            goto L_0x010c
        L_0x00fd:
            boolean r2 = r5 instanceof androidx.appcompat.widget.FitWindowsViewGroup
            if (r2 == 0) goto L_0x010c
            r2 = r5
            androidx.appcompat.widget.FitWindowsViewGroup r2 = (androidx.appcompat.widget.FitWindowsViewGroup) r2
            androidx.appcompat.app.AppCompatDelegateImpl$4 r4 = new androidx.appcompat.app.AppCompatDelegateImpl$4
            r4.<init>()
            r2.setOnFitSystemWindowsListener(r4)
        L_0x010c:
            androidx.appcompat.widget.DecorContentParent r2 = r10.mDecorContentParent
            if (r2 != 0) goto L_0x011a
            int r2 = androidx.appcompat.R.id.title
            android.view.View r2 = r5.findViewById(r2)
            android.widget.TextView r2 = (android.widget.TextView) r2
            r10.mTitleView = r2
        L_0x011a:
            androidx.appcompat.widget.ViewUtils.makeOptionalFitsSystemWindows(r5)
            int r2 = androidx.appcompat.R.id.action_bar_activity_content
            android.view.View r2 = r5.findViewById(r2)
            androidx.appcompat.widget.ContentFrameLayout r2 = (androidx.appcompat.widget.ContentFrameLayout) r2
            android.view.Window r4 = r10.mWindow
            r6 = 16908290(0x1020002, float:2.3877235E-38)
            android.view.View r4 = r4.findViewById(r6)
            android.view.ViewGroup r4 = (android.view.ViewGroup) r4
            if (r4 == 0) goto L_0x0154
        L_0x0132:
            int r8 = r4.getChildCount()
            if (r8 <= 0) goto L_0x0143
            android.view.View r8 = r4.getChildAt(r3)
            r4.removeViewAt(r3)
            r2.addView(r8)
            goto L_0x0132
        L_0x0143:
            r3 = -1
            r4.setId(r3)
            r2.setId(r6)
            boolean r3 = r4 instanceof android.widget.FrameLayout
            if (r3 == 0) goto L_0x0154
            r3 = r4
            android.widget.FrameLayout r3 = (android.widget.FrameLayout) r3
            r3.setForeground(r7)
        L_0x0154:
            android.view.Window r3 = r10.mWindow
            r3.setContentView(r5)
            androidx.appcompat.app.AppCompatDelegateImpl$5 r3 = new androidx.appcompat.app.AppCompatDelegateImpl$5
            r3.<init>()
            r2.setAttachListener(r3)
            return r5
        L_0x0162:
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "AppCompat does not support the current theme features: { windowActionBar: "
            r3.append(r4)
            boolean r4 = r10.mHasActionBar
            r3.append(r4)
            java.lang.String r4 = ", windowActionBarOverlay: "
            r3.append(r4)
            boolean r4 = r10.mOverlayActionBar
            r3.append(r4)
            java.lang.String r4 = ", android:windowIsFloating: "
            r3.append(r4)
            boolean r4 = r10.mIsFloating
            r3.append(r4)
            java.lang.String r4 = ", windowActionModeOverlay: "
            r3.append(r4)
            boolean r4 = r10.mOverlayActionMode
            r3.append(r4)
            java.lang.String r4 = ", windowNoTitle: "
            r3.append(r4)
            boolean r4 = r10.mWindowNoTitle
            r3.append(r4)
            java.lang.String r4 = " }"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r2
        L_0x01a8:
            r0.recycle()
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "You need to use a Theme.AppCompat theme (or descendant) with this activity."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.createSubDecor():android.view.ViewGroup");
    }

    /* access modifiers changed from: package-private */
    public void onSubDecorInstalled(ViewGroup subDecor) {
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout cfl = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
        View windowDecor = this.mWindow.getDecorView();
        cfl.setDecorPadding(windowDecor.getPaddingLeft(), windowDecor.getPaddingTop(), windowDecor.getPaddingRight(), windowDecor.getPaddingBottom());
        TypedArray a = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
        a.getValue(R.styleable.AppCompatTheme_windowMinWidthMajor, cfl.getMinWidthMajor());
        a.getValue(R.styleable.AppCompatTheme_windowMinWidthMinor, cfl.getMinWidthMinor());
        int i = R.styleable.AppCompatTheme_windowFixedWidthMajor;
        if (a.hasValue(i)) {
            a.getValue(i, cfl.getFixedWidthMajor());
        }
        int i2 = R.styleable.AppCompatTheme_windowFixedWidthMinor;
        if (a.hasValue(i2)) {
            a.getValue(i2, cfl.getFixedWidthMinor());
        }
        int i3 = R.styleable.AppCompatTheme_windowFixedHeightMajor;
        if (a.hasValue(i3)) {
            a.getValue(i3, cfl.getFixedHeightMajor());
        }
        int i4 = R.styleable.AppCompatTheme_windowFixedHeightMinor;
        if (a.hasValue(i4)) {
            a.getValue(i4, cfl.getFixedHeightMinor());
        }
        a.recycle();
        cfl.requestLayout();
    }

    public boolean requestWindowFeature(int featureId) {
        int featureId2 = sanitizeWindowFeatureId(featureId);
        if (this.mWindowNoTitle && featureId2 == 108) {
            return false;
        }
        if (this.mHasActionBar && featureId2 == 1) {
            this.mHasActionBar = false;
        }
        switch (featureId2) {
            case 1:
                throwFeatureRequestIfSubDecorInstalled();
                this.mWindowNoTitle = true;
                return true;
            case 2:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            case 5:
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            case 10:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            case 108:
                throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            case 109:
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            default:
                return this.mWindow.requestFeature(featureId2);
        }
    }

    public boolean hasWindowFeature(int featureId) {
        boolean result = false;
        switch (sanitizeWindowFeatureId(featureId)) {
            case 1:
                result = this.mWindowNoTitle;
                break;
            case 2:
                result = this.mFeatureProgress;
                break;
            case 5:
                result = this.mFeatureIndeterminateProgress;
                break;
            case 10:
                result = this.mOverlayActionMode;
                break;
            case 108:
                result = this.mHasActionBar;
                break;
            case 109:
                result = this.mOverlayActionBar;
                break;
        }
        return result || this.mWindow.hasFeature(featureId);
    }

    public final void setTitle(CharSequence title) {
        this.mTitle = title;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(title);
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle(title);
        } else {
            TextView textView = this.mTitleView;
            if (textView != null) {
                textView.setText(title);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final CharSequence getTitle() {
        Object obj = this.mHost;
        if (obj instanceof Activity) {
            return ((Activity) obj).getTitle();
        }
        return this.mTitle;
    }

    /* access modifiers changed from: package-private */
    public void onPanelClosed(int featureId) {
        if (featureId == 108) {
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.dispatchMenuVisibilityChanged(false);
            }
        } else if (featureId == 0) {
            PanelFeatureState st = getPanelState(featureId, true);
            if (st.isOpen) {
                closePanel(st, false);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onMenuOpened(int featureId) {
        ActionBar ab;
        if (featureId == 108 && (ab = getSupportActionBar()) != null) {
            ab.dispatchMenuVisibilityChanged(true);
        }
    }

    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
        PanelFeatureState panel;
        Window.Callback cb = getWindowCallback();
        if (cb == null || this.mIsDestroyed || (panel = findMenuPanel(menu.getRootMenu())) == null) {
            return false;
        }
        return cb.onMenuItemSelected(panel.featureId, item);
    }

    public void onMenuModeChange(@NonNull MenuBuilder menu) {
        reopenMenu(true);
    }

    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        if (callback != null) {
            ActionMode actionMode = this.mActionMode;
            if (actionMode != null) {
                actionMode.finish();
            }
            ActionMode.Callback wrappedCallback = new ActionModeCallbackWrapperV9(callback);
            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ActionMode startActionMode = ab.startActionMode(wrappedCallback);
                this.mActionMode = startActionMode;
                if (!(startActionMode == null || (appCompatCallback = this.mAppCompatCallback) == null)) {
                    appCompatCallback.onSupportActionModeStarted(startActionMode);
                }
            }
            if (this.mActionMode == null) {
                this.mActionMode = startSupportActionModeFromWindow(wrappedCallback);
            }
            return this.mActionMode;
        }
        throw new IllegalArgumentException("ActionMode callback can not be null.");
    }

    public void invalidateOptionsMenu() {
        ActionBar ab = getSupportActionBar();
        if (ab == null || !ab.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    /* access modifiers changed from: package-private */
    public ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        Context actionBarContext;
        endOnGoingFadeAnimation();
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        if (!(callback instanceof ActionModeCallbackWrapperV9)) {
            callback = new ActionModeCallbackWrapperV9(callback);
        }
        ActionMode mode = null;
        AppCompatCallback appCompatCallback2 = this.mAppCompatCallback;
        if (appCompatCallback2 != null && !this.mIsDestroyed) {
            try {
                mode = appCompatCallback2.onWindowStartingSupportActionMode(callback);
            } catch (AbstractMethodError e) {
            }
        }
        if (mode != null) {
            this.mActionMode = mode;
        } else {
            boolean z = true;
            if (this.mActionModeView == null) {
                if (this.mIsFloating) {
                    TypedValue outValue = new TypedValue();
                    Resources.Theme baseTheme = this.mContext.getTheme();
                    baseTheme.resolveAttribute(R.attr.actionBarTheme, outValue, true);
                    if (outValue.resourceId != 0) {
                        Resources.Theme actionBarTheme = this.mContext.getResources().newTheme();
                        actionBarTheme.setTo(baseTheme);
                        actionBarTheme.applyStyle(outValue.resourceId, true);
                        actionBarContext = new androidx.appcompat.view.ContextThemeWrapper(this.mContext, 0);
                        actionBarContext.getTheme().setTo(actionBarTheme);
                    } else {
                        actionBarContext = this.mContext;
                    }
                    this.mActionModeView = new ActionBarContextView(actionBarContext);
                    PopupWindow popupWindow = new PopupWindow(actionBarContext, (AttributeSet) null, R.attr.actionModePopupWindowStyle);
                    this.mActionModePopup = popupWindow;
                    PopupWindowCompat.setWindowLayoutType(popupWindow, 2);
                    this.mActionModePopup.setContentView(this.mActionModeView);
                    this.mActionModePopup.setWidth(-1);
                    actionBarContext.getTheme().resolveAttribute(R.attr.actionBarSize, outValue, true);
                    this.mActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(outValue.data, actionBarContext.getResources().getDisplayMetrics()));
                    this.mActionModePopup.setHeight(-2);
                    this.mShowActionModePopup = new Runnable() {
                        public void run() {
                            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                            appCompatDelegateImpl.mActionModePopup.showAtLocation(appCompatDelegateImpl.mActionModeView, 55, 0, 0);
                            AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                            if (AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl2.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl2.mActionModeView).alpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {
                                    public void onAnimationStart(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                    }

                                    public void onAnimationEnd(View view) {
                                        AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                        AppCompatDelegateImpl.this.mFadeAnim.setListener((ViewPropertyAnimatorListener) null);
                                        AppCompatDelegateImpl.this.mFadeAnim = null;
                                    }
                                });
                                return;
                            }
                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                            AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                        }
                    };
                } else {
                    ViewStubCompat stub = (ViewStubCompat) this.mSubDecor.findViewById(R.id.action_mode_bar_stub);
                    if (stub != null) {
                        stub.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
                        this.mActionModeView = (ActionBarContextView) stub.inflate();
                    }
                }
            }
            if (this.mActionModeView != null) {
                endOnGoingFadeAnimation();
                this.mActionModeView.killMode();
                Context context = this.mActionModeView.getContext();
                ActionBarContextView actionBarContextView = this.mActionModeView;
                if (this.mActionModePopup != null) {
                    z = false;
                }
                ActionMode mode2 = new StandaloneActionMode(context, actionBarContextView, callback, z);
                if (callback.onCreateActionMode(mode2, mode2.getMenu())) {
                    mode2.invalidate();
                    this.mActionModeView.initForMode(mode2);
                    this.mActionMode = mode2;
                    if (shouldAnimateActionModeView()) {
                        this.mActionModeView.setAlpha(0.0f);
                        ViewPropertyAnimatorCompat alpha = ViewCompat.animate(this.mActionModeView).alpha(1.0f);
                        this.mFadeAnim = alpha;
                        alpha.setListener(new ViewPropertyAnimatorListenerAdapter() {
                            public void onAnimationStart(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32);
                                if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                                    ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                                }
                            }

                            public void onAnimationEnd(View view) {
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.mFadeAnim.setListener((ViewPropertyAnimatorListener) null);
                                AppCompatDelegateImpl.this.mFadeAnim = null;
                            }
                        });
                    } else {
                        this.mActionModeView.setAlpha(1.0f);
                        this.mActionModeView.setVisibility(0);
                        this.mActionModeView.sendAccessibilityEvent(32);
                        if (this.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent());
                        }
                    }
                    if (this.mActionModePopup != null) {
                        this.mWindow.getDecorView().post(this.mShowActionModePopup);
                    }
                } else {
                    this.mActionMode = null;
                }
            }
        }
        ActionMode actionMode2 = this.mActionMode;
        if (!(actionMode2 == null || (appCompatCallback = this.mAppCompatCallback) == null)) {
            appCompatCallback.onSupportActionModeStarted(actionMode2);
        }
        return this.mActionMode;
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0004, code lost:
        r0 = r1.mSubDecor;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean shouldAnimateActionModeView() {
        /*
            r1 = this;
            boolean r0 = r1.mSubDecorInstalled
            if (r0 == 0) goto L_0x0010
            android.view.ViewGroup r0 = r1.mSubDecor
            if (r0 == 0) goto L_0x0010
            boolean r0 = androidx.core.view.ViewCompat.isLaidOut(r0)
            if (r0 == 0) goto L_0x0010
            r0 = 1
            goto L_0x0011
        L_0x0010:
            r0 = 0
        L_0x0011:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.shouldAnimateActionModeView():boolean");
    }

    public void setHandleNativeActionModesEnabled(boolean enabled) {
        this.mHandleNativeActionModes = enabled;
    }

    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    /* access modifiers changed from: package-private */
    public void endOnGoingFadeAnimation() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean onBackPressed() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        ActionBar ab = getSupportActionBar();
        if (ab == null || !ab.collapseActionView()) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyShortcut(int keyCode, KeyEvent ev) {
        ActionBar ab = getSupportActionBar();
        if (ab != null && ab.onKeyShortcut(keyCode, ev)) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (panelFeatureState == null || !performPanelShortcut(panelFeatureState, ev.getKeyCode(), ev, 1)) {
            if (this.mPreparedPanel == null) {
                PanelFeatureState st = getPanelState(0, true);
                preparePanel(st, ev);
                boolean handled = performPanelShortcut(st, ev.getKeyCode(), ev, 1);
                st.isPrepared = false;
                if (handled) {
                    return true;
                }
            }
            return false;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null) {
            panelFeatureState2.isHandled = true;
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean dispatchKeyEvent(KeyEvent event) {
        View root;
        Object obj = this.mHost;
        boolean isDown = true;
        if (((obj instanceof KeyEventDispatcher.Component) || (obj instanceof AppCompatDialog)) && (root = this.mWindow.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(root, event)) {
            return true;
        }
        if (event.getKeyCode() == 82 && this.mAppCompatWindowCallback.getWrapped().dispatchKeyEvent(event)) {
            return true;
        }
        int keyCode = event.getKeyCode();
        if (event.getAction() != 0) {
            isDown = false;
        }
        return isDown ? onKeyDown(keyCode, event) : onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case 4:
                boolean wasLongPressBackDown = this.mLongPressBackDown;
                this.mLongPressBackDown = false;
                PanelFeatureState st = getPanelState(0, false);
                if (st != null && st.isOpen) {
                    if (!wasLongPressBackDown) {
                        closePanel(st, true);
                    }
                    return true;
                } else if (onBackPressed()) {
                    return true;
                }
                break;
            case 82:
                onKeyUpPanel(0, event);
                return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean z = true;
        switch (keyCode) {
            case 4:
                if ((event.getFlags() & 128) == 0) {
                    z = false;
                }
                this.mLongPressBackDown = z;
                break;
            case 82:
                onKeyDownPanel(0, event);
                return true;
        }
        return false;
    }

    public View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        boolean z = false;
        if (this.mAppCompatViewInflater == null) {
            String viewInflaterClassName = this.mContext.obtainStyledAttributes(R.styleable.AppCompatTheme).getString(R.styleable.AppCompatTheme_viewInflaterClass);
            if (viewInflaterClassName == null) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) Class.forName(viewInflaterClassName).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable t) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + viewInflaterClassName + ". Falling back to default.", t);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        boolean inheritContext = false;
        boolean z2 = IS_PRE_LOLLIPOP;
        if (z2) {
            if (this.mLayoutIncludeDetector == null) {
                this.mLayoutIncludeDetector = new LayoutIncludeDetector();
            }
            if (this.mLayoutIncludeDetector.detect(attrs)) {
                inheritContext = true;
            } else {
                if (!(attrs instanceof XmlPullParser)) {
                    z = shouldInheritContext((ViewParent) parent);
                } else if (((XmlPullParser) attrs).getDepth() > 1) {
                    z = true;
                }
                inheritContext = z;
            }
        }
        return this.mAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext, z2, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent parent) {
        if (parent == null) {
            return false;
        }
        View windowDecor = this.mWindow.getDecorView();
        while (parent != null) {
            if (parent == windowDecor || !(parent instanceof View) || ViewCompat.isAttachedToWindow((View) parent)) {
                return false;
            }
            parent = parent.getParent();
        }
        return true;
    }

    public void installViewFactory() {
        LayoutInflater layoutInflater = LayoutInflater.from(this.mContext);
        if (layoutInflater.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(layoutInflater, this);
        } else if (!(layoutInflater.getFactory2() instanceof AppCompatDelegateImpl)) {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    public final View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return createView(parent, name, context, attrs);
    }

    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView((View) null, name, context, attrs);
    }

    @Nullable
    private AppCompatActivity tryUnwrapContext() {
        for (Context context = this.mContext; context != null; context = ((ContextWrapper) context).getBaseContext()) {
            if (context instanceof AppCompatActivity) {
                return (AppCompatActivity) context;
            }
            if (!(context instanceof ContextWrapper)) {
                return null;
            }
        }
        return null;
    }

    private void openPanel(PanelFeatureState st, KeyEvent event) {
        ViewGroup.LayoutParams lp;
        PanelFeatureState panelFeatureState = st;
        if (!panelFeatureState.isOpen && !this.mIsDestroyed) {
            if (panelFeatureState.featureId == 0) {
                if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                    return;
                }
            }
            Window.Callback cb = getWindowCallback();
            if (cb == null || cb.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
                WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
                if (wm != null && preparePanel(st, event)) {
                    int width = -2;
                    ViewGroup viewGroup = panelFeatureState.decorView;
                    if (viewGroup == null || panelFeatureState.refreshDecorView) {
                        if (viewGroup == null) {
                            if (!initializePanelDecor(st) || panelFeatureState.decorView == null) {
                                return;
                            }
                        } else if (panelFeatureState.refreshDecorView && viewGroup.getChildCount() > 0) {
                            panelFeatureState.decorView.removeAllViews();
                        }
                        if (!initializePanelContent(st) || !st.hasPanelItems()) {
                            panelFeatureState.refreshDecorView = true;
                            return;
                        }
                        ViewGroup.LayoutParams lp2 = panelFeatureState.shownPanelView.getLayoutParams();
                        if (lp2 == null) {
                            lp2 = new ViewGroup.LayoutParams(-2, -2);
                        }
                        panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background);
                        ViewParent shownPanelParent = panelFeatureState.shownPanelView.getParent();
                        if (shownPanelParent instanceof ViewGroup) {
                            ((ViewGroup) shownPanelParent).removeView(panelFeatureState.shownPanelView);
                        }
                        panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, lp2);
                        if (!panelFeatureState.shownPanelView.hasFocus()) {
                            panelFeatureState.shownPanelView.requestFocus();
                        }
                    } else {
                        View view = panelFeatureState.createdPanelView;
                        if (!(view == null || (lp = view.getLayoutParams()) == null || lp.width != -1)) {
                            width = -1;
                        }
                    }
                    panelFeatureState.isHandled = false;
                    WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(width, -2, panelFeatureState.x, panelFeatureState.y, 1002, 8519680, -3);
                    layoutParams.gravity = panelFeatureState.gravity;
                    layoutParams.windowAnimations = panelFeatureState.windowAnimations;
                    wm.addView(panelFeatureState.decorView, layoutParams);
                    panelFeatureState.isOpen = true;
                    return;
                }
                return;
            }
            closePanel(panelFeatureState, true);
        }
    }

    private boolean initializePanelDecor(PanelFeatureState st) {
        st.setStyle(getActionBarThemedContext());
        st.decorView = new ListMenuDecorView(st.listPresenterContext);
        st.gravity = 81;
        return true;
    }

    private void reopenMenu(boolean toggleMenuMode) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent == null || !decorContentParent.canShowOverflowMenu() || (ViewConfiguration.get(this.mContext).hasPermanentMenuKey() && !this.mDecorContentParent.isOverflowMenuShowPending())) {
            PanelFeatureState st = getPanelState(0, true);
            st.refreshDecorView = true;
            closePanel(st, false);
            openPanel(st, (KeyEvent) null);
            return;
        }
        Window.Callback cb = getWindowCallback();
        if (this.mDecorContentParent.isOverflowMenuShowing() && toggleMenuMode) {
            this.mDecorContentParent.hideOverflowMenu();
            if (!this.mIsDestroyed) {
                cb.onPanelClosed(108, getPanelState(0, true).menu);
            }
        } else if (cb != null && !this.mIsDestroyed) {
            if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                this.mInvalidatePanelMenuRunnable.run();
            }
            PanelFeatureState st2 = getPanelState(0, true);
            MenuBuilder menuBuilder = st2.menu;
            if (menuBuilder != null && !st2.refreshMenuContent && cb.onPreparePanel(0, st2.createdPanelView, menuBuilder)) {
                cb.onMenuOpened(108, st2.menu);
                this.mDecorContentParent.showOverflowMenu();
            }
        }
    }

    private boolean initializePanelMenu(PanelFeatureState st) {
        Context context = this.mContext;
        int i = st.featureId;
        if ((i == 0 || i == 108) && this.mDecorContentParent != null) {
            TypedValue outValue = new TypedValue();
            Resources.Theme baseTheme = context.getTheme();
            baseTheme.resolveAttribute(R.attr.actionBarTheme, outValue, true);
            Resources.Theme widgetTheme = null;
            if (outValue.resourceId != 0) {
                widgetTheme = context.getResources().newTheme();
                widgetTheme.setTo(baseTheme);
                widgetTheme.applyStyle(outValue.resourceId, true);
                widgetTheme.resolveAttribute(R.attr.actionBarWidgetTheme, outValue, true);
            } else {
                baseTheme.resolveAttribute(R.attr.actionBarWidgetTheme, outValue, true);
            }
            if (outValue.resourceId != 0) {
                if (widgetTheme == null) {
                    widgetTheme = context.getResources().newTheme();
                    widgetTheme.setTo(baseTheme);
                }
                widgetTheme.applyStyle(outValue.resourceId, true);
            }
            if (widgetTheme != null) {
                context = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
                context.getTheme().setTo(widgetTheme);
            }
        }
        MenuBuilder menu = new MenuBuilder(context);
        menu.setCallback(this);
        st.setMenu(menu);
        return true;
    }

    private boolean initializePanelContent(PanelFeatureState st) {
        View view = st.createdPanelView;
        if (view != null) {
            st.shownPanelView = view;
            return true;
        } else if (st.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            View view2 = (View) st.getListMenuView(this.mPanelMenuPresenterCallback);
            st.shownPanelView = view2;
            if (view2 != null) {
                return true;
            }
            return false;
        }
    }

    private boolean preparePanel(PanelFeatureState st, KeyEvent event) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.mIsDestroyed) {
            return false;
        }
        if (st.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState = this.mPreparedPanel;
        if (!(panelFeatureState == null || panelFeatureState == st)) {
            closePanel(panelFeatureState, false);
        }
        Window.Callback cb = getWindowCallback();
        if (cb != null) {
            st.createdPanelView = cb.onCreatePanelView(st.featureId);
        }
        int i = st.featureId;
        boolean isActionBarMenu = i == 0 || i == 108;
        if (isActionBarMenu && (decorContentParent3 = this.mDecorContentParent) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (st.createdPanelView == null && (!isActionBarMenu || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
            MenuBuilder menuBuilder = st.menu;
            if (menuBuilder == null || st.refreshMenuContent) {
                if (menuBuilder == null && (!initializePanelMenu(st) || st.menu == null)) {
                    return false;
                }
                if (isActionBarMenu && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(st.menu, this.mActionMenuPresenterCallback);
                }
                st.menu.stopDispatchingItemsChanged();
                if (!cb.onCreatePanelMenu(st.featureId, st.menu)) {
                    st.setMenu((MenuBuilder) null);
                    if (isActionBarMenu && (decorContentParent2 = this.mDecorContentParent) != null) {
                        decorContentParent2.setMenu((Menu) null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                st.refreshMenuContent = false;
            }
            st.menu.stopDispatchingItemsChanged();
            Bundle bundle = st.frozenActionViewState;
            if (bundle != null) {
                st.menu.restoreActionViewStates(bundle);
                st.frozenActionViewState = null;
            }
            if (!cb.onPreparePanel(0, st.createdPanelView, st.menu)) {
                if (isActionBarMenu && (decorContentParent = this.mDecorContentParent) != null) {
                    decorContentParent.setMenu((Menu) null, this.mActionMenuPresenterCallback);
                }
                st.menu.startDispatchingItemsChanged();
                return false;
            }
            boolean z = KeyCharacterMap.load(event != null ? event.getDeviceId() : -1).getKeyboardType() != 1;
            st.qwertyMode = z;
            st.menu.setQwertyMode(z);
            st.menu.startDispatchingItemsChanged();
        }
        st.isPrepared = true;
        st.isHandled = false;
        this.mPreparedPanel = st;
        return true;
    }

    /* access modifiers changed from: package-private */
    public void checkCloseActionMenu(@NonNull MenuBuilder menu) {
        if (!this.mClosingActionMenu) {
            this.mClosingActionMenu = true;
            this.mDecorContentParent.dismissPopups();
            Window.Callback cb = getWindowCallback();
            if (cb != null && !this.mIsDestroyed) {
                cb.onPanelClosed(108, menu);
            }
            this.mClosingActionMenu = false;
        }
    }

    /* access modifiers changed from: package-private */
    public void closePanel(int featureId) {
        closePanel(getPanelState(featureId, true), true);
    }

    /* access modifiers changed from: package-private */
    public void closePanel(PanelFeatureState st, boolean doCallback) {
        ViewGroup viewGroup;
        DecorContentParent decorContentParent;
        if (!doCallback || st.featureId != 0 || (decorContentParent = this.mDecorContentParent) == null || !decorContentParent.isOverflowMenuShowing()) {
            WindowManager wm = (WindowManager) this.mContext.getSystemService("window");
            if (!(wm == null || !st.isOpen || (viewGroup = st.decorView) == null)) {
                wm.removeView(viewGroup);
                if (doCallback) {
                    callOnPanelClosed(st.featureId, st, (Menu) null);
                }
            }
            st.isPrepared = false;
            st.isHandled = false;
            st.isOpen = false;
            st.shownPanelView = null;
            st.refreshDecorView = true;
            if (this.mPreparedPanel == st) {
                this.mPreparedPanel = null;
                return;
            }
            return;
        }
        checkCloseActionMenu(st.menu);
    }

    private boolean onKeyDownPanel(int featureId, KeyEvent event) {
        if (event.getRepeatCount() != 0) {
            return false;
        }
        PanelFeatureState st = getPanelState(featureId, true);
        if (!st.isOpen) {
            return preparePanel(st, event);
        }
        return false;
    }

    private boolean onKeyUpPanel(int featureId, KeyEvent event) {
        DecorContentParent decorContentParent;
        if (this.mActionMode != null) {
            return false;
        }
        boolean handled = false;
        PanelFeatureState st = getPanelState(featureId, true);
        if (featureId != 0 || (decorContentParent = this.mDecorContentParent) == null || !decorContentParent.canShowOverflowMenu() || ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
            if (st.isOpen || st.isHandled) {
                handled = st.isOpen;
                closePanel(st, true);
            } else if (st.isPrepared) {
                boolean show = true;
                if (st.refreshMenuContent) {
                    st.isPrepared = false;
                    show = preparePanel(st, event);
                }
                if (show) {
                    openPanel(st, event);
                    handled = true;
                }
            }
        } else if (this.mDecorContentParent.isOverflowMenuShowing()) {
            handled = this.mDecorContentParent.hideOverflowMenu();
        } else if (!this.mIsDestroyed && preparePanel(st, event)) {
            handled = this.mDecorContentParent.showOverflowMenu();
        }
        if (handled) {
            AudioManager audioManager = (AudioManager) this.mContext.getApplicationContext().getSystemService("audio");
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return handled;
    }

    /* access modifiers changed from: package-private */
    public void callOnPanelClosed(int featureId, PanelFeatureState panel, Menu menu) {
        if (menu == null) {
            if (panel == null && featureId >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (featureId < panelFeatureStateArr.length) {
                    panel = panelFeatureStateArr[featureId];
                }
            }
            if (panel != null) {
                menu = panel.menu;
            }
        }
        if ((panel == null || panel.isOpen) && !this.mIsDestroyed) {
            this.mAppCompatWindowCallback.getWrapped().onPanelClosed(featureId, menu);
        }
    }

    /* access modifiers changed from: package-private */
    public PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panels = this.mPanels;
        int N = panels != null ? panels.length : 0;
        for (int i = 0; i < N; i++) {
            PanelFeatureState panel = panels[i];
            if (panel != null && panel.menu == menu) {
                return panel;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public PanelFeatureState getPanelState(int featureId, boolean required) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        PanelFeatureState[] ar = panelFeatureStateArr;
        if (panelFeatureStateArr == null || ar.length <= featureId) {
            PanelFeatureState[] nar = new PanelFeatureState[(featureId + 1)];
            if (ar != null) {
                System.arraycopy(ar, 0, nar, 0, ar.length);
            }
            ar = nar;
            this.mPanels = nar;
        }
        PanelFeatureState st = ar[featureId];
        if (st != null) {
            return st;
        }
        PanelFeatureState panelFeatureState = new PanelFeatureState(featureId);
        PanelFeatureState st2 = panelFeatureState;
        ar[featureId] = panelFeatureState;
        return st2;
    }

    private boolean performPanelShortcut(PanelFeatureState st, int keyCode, KeyEvent event, int flags) {
        MenuBuilder menuBuilder;
        if (event.isSystem()) {
            return false;
        }
        boolean handled = false;
        if ((st.isPrepared || preparePanel(st, event)) && (menuBuilder = st.menu) != null) {
            handled = menuBuilder.performShortcut(keyCode, event, flags);
        }
        if (handled && (flags & 1) == 0 && this.mDecorContentParent == null) {
            closePanel(st, true);
        }
        return handled;
    }

    private void invalidatePanelMenu(int featureId) {
        this.mInvalidatePanelMenuFeatures |= 1 << featureId;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    /* access modifiers changed from: package-private */
    public void doInvalidatePanelMenu(int featureId) {
        PanelFeatureState st;
        PanelFeatureState st2 = getPanelState(featureId, true);
        if (st2.menu != null) {
            Bundle savedActionViewStates = new Bundle();
            st2.menu.saveActionViewStates(savedActionViewStates);
            if (savedActionViewStates.size() > 0) {
                st2.frozenActionViewState = savedActionViewStates;
            }
            st2.menu.stopDispatchingItemsChanged();
            st2.menu.clear();
        }
        st2.refreshMenuContent = true;
        st2.refreshDecorView = true;
        if ((featureId == 108 || featureId == 0) && this.mDecorContentParent != null && (st = getPanelState(0, false)) != null) {
            st.isPrepared = false;
            preparePanel(st, (KeyEvent) null);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x012f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int updateStatusGuard(@androidx.annotation.Nullable androidx.core.view.WindowInsetsCompat r18, @androidx.annotation.Nullable android.graphics.Rect r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r19
            r2 = 0
            if (r18 == 0) goto L_0x000c
            int r2 = r18.getSystemWindowInsetTop()
            goto L_0x0010
        L_0x000c:
            if (r1 == 0) goto L_0x0010
            int r2 = r1.top
        L_0x0010:
            r3 = 0
            androidx.appcompat.widget.ActionBarContextView r4 = r0.mActionModeView
            if (r4 == 0) goto L_0x0126
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            boolean r4 = r4 instanceof android.view.ViewGroup.MarginLayoutParams
            if (r4 == 0) goto L_0x0122
            androidx.appcompat.widget.ActionBarContextView r4 = r0.mActionModeView
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r4 = (android.view.ViewGroup.MarginLayoutParams) r4
            r7 = 0
            androidx.appcompat.widget.ActionBarContextView r8 = r0.mActionModeView
            boolean r8 = r8.isShown()
            if (r8 == 0) goto L_0x010a
            android.graphics.Rect r8 = r0.mTempRect1
            if (r8 != 0) goto L_0x0040
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            r0.mTempRect1 = r8
            android.graphics.Rect r8 = new android.graphics.Rect
            r8.<init>()
            r0.mTempRect2 = r8
        L_0x0040:
            android.graphics.Rect r8 = r0.mTempRect1
            android.graphics.Rect r9 = r0.mTempRect2
            if (r18 != 0) goto L_0x004a
            r8.set(r1)
            goto L_0x005e
        L_0x004a:
            int r10 = r18.getSystemWindowInsetLeft()
            int r11 = r18.getSystemWindowInsetTop()
            int r12 = r18.getSystemWindowInsetRight()
            int r13 = r18.getSystemWindowInsetBottom()
            r8.set(r10, r11, r12, r13)
        L_0x005e:
            android.view.ViewGroup r10 = r0.mSubDecor
            androidx.appcompat.widget.ViewUtils.computeFitSystemWindows(r10, r8, r9)
            int r10 = r8.top
            int r11 = r8.left
            int r12 = r8.right
            android.view.ViewGroup r13 = r0.mSubDecor
            androidx.core.view.WindowInsetsCompat r13 = androidx.core.view.ViewCompat.getRootWindowInsets(r13)
            if (r13 != 0) goto L_0x0073
            r14 = 0
            goto L_0x0077
        L_0x0073:
            int r14 = r13.getSystemWindowInsetLeft()
        L_0x0077:
            if (r13 != 0) goto L_0x007b
            r15 = 0
            goto L_0x007f
        L_0x007b:
            int r15 = r13.getSystemWindowInsetRight()
        L_0x007f:
            int r6 = r4.topMargin
            if (r6 != r10) goto L_0x008b
            int r6 = r4.leftMargin
            if (r6 != r11) goto L_0x008b
            int r6 = r4.rightMargin
            if (r6 == r12) goto L_0x0093
        L_0x008b:
            r6 = 1
            r4.topMargin = r10
            r4.leftMargin = r11
            r4.rightMargin = r12
            r7 = r6
        L_0x0093:
            if (r10 <= 0) goto L_0x00c0
            android.view.View r6 = r0.mStatusGuard
            if (r6 != 0) goto L_0x00c0
            android.view.View r6 = new android.view.View
            android.content.Context r5 = r0.mContext
            r6.<init>(r5)
            r0.mStatusGuard = r6
            r5 = 8
            r6.setVisibility(r5)
            android.widget.FrameLayout$LayoutParams r6 = new android.widget.FrameLayout$LayoutParams
            int r5 = r4.topMargin
            r1 = 51
            r16 = r2
            r2 = -1
            r6.<init>(r2, r5, r1)
            r1 = r6
            r1.leftMargin = r14
            r1.rightMargin = r15
            android.view.ViewGroup r5 = r0.mSubDecor
            android.view.View r6 = r0.mStatusGuard
            r5.addView(r6, r2, r1)
            goto L_0x00e7
        L_0x00c0:
            r16 = r2
            android.view.View r1 = r0.mStatusGuard
            if (r1 == 0) goto L_0x00e7
            android.view.ViewGroup$LayoutParams r1 = r1.getLayoutParams()
            android.view.ViewGroup$MarginLayoutParams r1 = (android.view.ViewGroup.MarginLayoutParams) r1
            int r2 = r1.height
            int r5 = r4.topMargin
            if (r2 != r5) goto L_0x00db
            int r2 = r1.leftMargin
            if (r2 != r14) goto L_0x00db
            int r2 = r1.rightMargin
            if (r2 == r15) goto L_0x00e8
        L_0x00db:
            r1.height = r5
            r1.leftMargin = r14
            r1.rightMargin = r15
            android.view.View r2 = r0.mStatusGuard
            r2.setLayoutParams(r1)
            goto L_0x00e8
        L_0x00e7:
        L_0x00e8:
            android.view.View r1 = r0.mStatusGuard
            if (r1 == 0) goto L_0x00ee
            r2 = 1
            goto L_0x00ef
        L_0x00ee:
            r2 = 0
        L_0x00ef:
            if (r2 == 0) goto L_0x00fc
            int r1 = r1.getVisibility()
            if (r1 == 0) goto L_0x00fc
            android.view.View r1 = r0.mStatusGuard
            r0.updateStatusGuardColor(r1)
        L_0x00fc:
            boolean r1 = r0.mOverlayActionMode
            if (r1 != 0) goto L_0x0104
            if (r2 == 0) goto L_0x0104
            r1 = 0
            goto L_0x0106
        L_0x0104:
            r1 = r16
        L_0x0106:
            r3 = r2
            r2 = r1
            r1 = 0
            goto L_0x011a
        L_0x010a:
            r16 = r2
            int r1 = r4.topMargin
            if (r1 == 0) goto L_0x0117
            r7 = 1
            r1 = 0
            r4.topMargin = r1
            r2 = r16
            goto L_0x011a
        L_0x0117:
            r1 = 0
            r2 = r16
        L_0x011a:
            if (r7 == 0) goto L_0x012b
            androidx.appcompat.widget.ActionBarContextView r5 = r0.mActionModeView
            r5.setLayoutParams(r4)
            goto L_0x012b
        L_0x0122:
            r16 = r2
            r1 = 0
            goto L_0x0129
        L_0x0126:
            r16 = r2
            r1 = 0
        L_0x0129:
            r2 = r16
        L_0x012b:
            android.view.View r4 = r0.mStatusGuard
            if (r4 == 0) goto L_0x0138
            if (r3 == 0) goto L_0x0133
            r5 = r1
            goto L_0x0135
        L_0x0133:
            r5 = 8
        L_0x0135:
            r4.setVisibility(r5)
        L_0x0138:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.app.AppCompatDelegateImpl.updateStatusGuard(androidx.core.view.WindowInsetsCompat, android.graphics.Rect):int");
    }

    private void updateStatusGuardColor(View v) {
        int i;
        if ((ViewCompat.getWindowSystemUiVisibility(v) & 8192) != 0) {
            i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard_light);
        } else {
            i = ContextCompat.getColor(this.mContext, R.color.abc_decor_view_status_guard);
        }
        v.setBackgroundColor(i);
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (this.mSubDecorInstalled) {
            throw new AndroidRuntimeException("Window feature must be requested before adding content");
        }
    }

    private int sanitizeWindowFeatureId(int featureId) {
        if (featureId == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (featureId != 9) {
            return featureId;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    /* access modifiers changed from: package-private */
    public ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    /* access modifiers changed from: package-private */
    public void dismissPopups() {
        MenuBuilder menuBuilder;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException e) {
                }
            }
            this.mActionModePopup = null;
        }
        endOnGoingFadeAnimation();
        PanelFeatureState st = getPanelState(0, false);
        if (st != null && (menuBuilder = st.menu) != null) {
            menuBuilder.close();
        }
    }

    public boolean applyDayNight() {
        return applyDayNight(true);
    }

    private boolean applyDayNight(boolean allowRecreation) {
        if (this.mIsDestroyed) {
            return false;
        }
        int nightMode = calculateNightMode();
        boolean applied = updateForNightMode(mapNightMode(this.mContext, nightMode), allowRecreation);
        if (nightMode == 0) {
            getAutoTimeNightModeManager(this.mContext).setup();
        } else {
            AutoNightModeManager autoNightModeManager = this.mAutoTimeNightModeManager;
            if (autoNightModeManager != null) {
                autoNightModeManager.cleanup();
            }
        }
        if (nightMode == 3) {
            getAutoBatteryNightModeManager(this.mContext).setup();
        } else {
            AutoNightModeManager autoNightModeManager2 = this.mAutoBatteryNightModeManager;
            if (autoNightModeManager2 != null) {
                autoNightModeManager2.cleanup();
            }
        }
        return applied;
    }

    @RequiresApi(17)
    public void setLocalNightMode(int mode) {
        if (this.mLocalNightMode != mode) {
            this.mLocalNightMode = mode;
            if (this.mBaseContextAttached) {
                applyDayNight();
            }
        }
    }

    public int getLocalNightMode() {
        return this.mLocalNightMode;
    }

    /* access modifiers changed from: package-private */
    public int mapNightMode(@NonNull Context context, int mode) {
        switch (mode) {
            case -100:
                return -1;
            case -1:
            case 1:
            case 2:
                return mode;
            case 0:
                if (Build.VERSION.SDK_INT < 23 || ((UiModeManager) context.getApplicationContext().getSystemService("uimode")).getNightMode() != 0) {
                    return getAutoTimeNightModeManager(context).getApplyableNightMode();
                }
                return -1;
            case 3:
                return getAutoBatteryNightModeManager(context).getApplyableNightMode();
            default:
                throw new IllegalStateException("Unknown value set for night mode. Please use one of the MODE_NIGHT values from AppCompatDelegate.");
        }
    }

    private int calculateNightMode() {
        int i = this.mLocalNightMode;
        return i != -100 ? i : AppCompatDelegate.getDefaultNightMode();
    }

    @NonNull
    private Configuration createOverrideConfigurationForDayNight(@NonNull Context context, int mode, @Nullable Configuration configOverlay) {
        int newNightMode;
        switch (mode) {
            case 1:
                newNightMode = 16;
                break;
            case 2:
                newNightMode = 32;
                break;
            default:
                newNightMode = context.getApplicationContext().getResources().getConfiguration().uiMode & 48;
                break;
        }
        Configuration overrideConf = new Configuration();
        overrideConf.fontScale = 0.0f;
        if (configOverlay != null) {
            overrideConf.setTo(configOverlay);
        }
        overrideConf.uiMode = (overrideConf.uiMode & -49) | newNightMode;
        return overrideConf;
    }

    private boolean updateForNightMode(int mode, boolean allowRecreation) {
        boolean handled = false;
        Configuration overrideConfig = createOverrideConfigurationForDayNight(this.mContext, mode, (Configuration) null);
        boolean activityHandlingUiMode = isActivityManifestHandlingUiMode();
        int currentNightMode = this.mContext.getResources().getConfiguration().uiMode & 48;
        int newNightMode = overrideConfig.uiMode & 48;
        if (currentNightMode != newNightMode && allowRecreation && !activityHandlingUiMode && this.mBaseContextAttached && (sCanReturnDifferentContext || this.mCreated)) {
            Object obj = this.mHost;
            if ((obj instanceof Activity) && !((Activity) obj).isChild()) {
                ActivityCompat.recreate((Activity) this.mHost);
                handled = true;
            }
        }
        if (!handled && currentNightMode != newNightMode) {
            updateResourcesConfigurationForNightMode(newNightMode, activityHandlingUiMode, (Configuration) null);
            handled = true;
        }
        if (handled) {
            Object obj2 = this.mHost;
            if (obj2 instanceof AppCompatActivity) {
                ((AppCompatActivity) obj2).onNightModeChanged(mode);
            }
        }
        return handled;
    }

    private void updateResourcesConfigurationForNightMode(int uiModeNightModeValue, boolean callOnConfigChange, @Nullable Configuration configOverlay) {
        Resources res = this.mContext.getResources();
        Configuration conf = new Configuration(res.getConfiguration());
        if (configOverlay != null) {
            conf.updateFrom(configOverlay);
        }
        conf.uiMode = (res.getConfiguration().uiMode & -49) | uiModeNightModeValue;
        res.updateConfiguration(conf, (DisplayMetrics) null);
        int i = Build.VERSION.SDK_INT;
        if (i < 26) {
            ResourcesFlusher.flush(res);
        }
        int i2 = this.mThemeResId;
        if (i2 != 0) {
            this.mContext.setTheme(i2);
            if (i >= 23) {
                this.mContext.getTheme().applyStyle(this.mThemeResId, true);
            }
        }
        if (callOnConfigChange) {
            Object obj = this.mHost;
            if (obj instanceof Activity) {
                Activity activity = (Activity) obj;
                if (activity instanceof LifecycleOwner) {
                    if (((LifecycleOwner) activity).getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        activity.onConfigurationChanged(conf);
                    }
                } else if (this.mStarted) {
                    activity.onConfigurationChanged(conf);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    @VisibleForTesting
    @NonNull
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public final AutoNightModeManager getAutoTimeNightModeManager() {
        return getAutoTimeNightModeManager(this.mContext);
    }

    private AutoNightModeManager getAutoTimeNightModeManager(@NonNull Context context) {
        if (this.mAutoTimeNightModeManager == null) {
            this.mAutoTimeNightModeManager = new AutoTimeNightModeManager(TwilightManager.getInstance(context));
        }
        return this.mAutoTimeNightModeManager;
    }

    private AutoNightModeManager getAutoBatteryNightModeManager(@NonNull Context context) {
        if (this.mAutoBatteryNightModeManager == null) {
            this.mAutoBatteryNightModeManager = new AutoBatteryNightModeManager(context);
        }
        return this.mAutoBatteryNightModeManager;
    }

    private boolean isActivityManifestHandlingUiMode() {
        if (!this.mActivityHandlesUiModeChecked && (this.mHost instanceof Activity)) {
            PackageManager pm = this.mContext.getPackageManager();
            if (pm == null) {
                return false;
            }
            int flags = 0;
            try {
                int i = Build.VERSION.SDK_INT;
                if (i >= 29) {
                    flags = 269221888;
                } else if (i >= 24) {
                    flags = 786432;
                }
                ActivityInfo info = pm.getActivityInfo(new ComponentName(this.mContext, this.mHost.getClass()), flags);
                this.mActivityHandlesUiMode = (info == null || (info.configChanges & 512) == 0) ? false : true;
            } catch (PackageManager.NameNotFoundException e) {
                Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                this.mActivityHandlesUiMode = false;
            }
        }
        this.mActivityHandlesUiModeChecked = true;
        return this.mActivityHandlesUiMode;
    }

    public class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback wrapped) {
            this.mWrapped = wrapped;
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return this.mWrapped.onCreateActionMode(mode, menu);
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            ViewCompat.requestApplyInsets(AppCompatDelegateImpl.this.mSubDecor);
            return this.mWrapped.onPrepareActionMode(mode, menu);
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return this.mWrapped.onActionItemClicked(mode, item);
        }

        public void onDestroyActionMode(ActionMode mode) {
            this.mWrapped.onDestroyActionMode(mode);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.mActionModePopup != null) {
                appCompatDelegateImpl.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.mActionModeView != null) {
                appCompatDelegateImpl2.endOnGoingFadeAnimation();
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                appCompatDelegateImpl3.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl3.mActionModeView).alpha(0.0f);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() {
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImpl.this.mActionModeView.killMode();
                        AppCompatDelegateImpl.this.mFadeAnim.setListener((ViewPropertyAnimatorListener) null);
                        AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                        appCompatDelegateImpl2.mFadeAnim = null;
                        ViewCompat.requestApplyInsets(appCompatDelegateImpl2.mSubDecor);
                    }
                });
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            AppCompatCallback appCompatCallback = appCompatDelegateImpl4.mAppCompatCallback;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished(appCompatDelegateImpl4.mActionMode);
            }
            AppCompatDelegateImpl appCompatDelegateImpl5 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl5.mActionMode = null;
            ViewCompat.requestApplyInsets(appCompatDelegateImpl5.mSubDecor);
        }
    }

    public final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        public void onCloseMenu(@NonNull MenuBuilder menu, boolean allMenusAreClosing) {
            Menu parentMenu = menu.getRootMenu();
            boolean isSubMenu = parentMenu != menu;
            PanelFeatureState panel = AppCompatDelegateImpl.this.findMenuPanel(isSubMenu ? parentMenu : menu);
            if (panel == null) {
                return;
            }
            if (isSubMenu) {
                AppCompatDelegateImpl.this.callOnPanelClosed(panel.featureId, panel, parentMenu);
                AppCompatDelegateImpl.this.closePanel(panel, true);
                return;
            }
            AppCompatDelegateImpl.this.closePanel(panel, allMenusAreClosing);
        }

        public boolean onOpenSubMenu(@NonNull MenuBuilder subMenu) {
            Window.Callback cb;
            if (subMenu != subMenu.getRootMenu()) {
                return true;
            }
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (!appCompatDelegateImpl.mHasActionBar || (cb = appCompatDelegateImpl.getWindowCallback()) == null || AppCompatDelegateImpl.this.mIsDestroyed) {
                return true;
            }
            cb.onMenuOpened(108, subMenu);
            return true;
        }
    }

    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public boolean onOpenSubMenu(@NonNull MenuBuilder subMenu) {
            Window.Callback cb = AppCompatDelegateImpl.this.getWindowCallback();
            if (cb == null) {
                return true;
            }
            cb.onMenuOpened(108, subMenu);
            return true;
        }

        public void onCloseMenu(@NonNull MenuBuilder menu, boolean allMenusAreClosing) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menu);
        }
    }

    public static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;
        int x;
        int y;

        PanelFeatureState(int featureId2) {
            this.featureId = featureId2;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            if (this.createdPanelView != null) {
                return true;
            }
            if (this.listMenuPresenter.getAdapter().getCount() > 0) {
                return true;
            }
            return false;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        /* access modifiers changed from: package-private */
        public void setStyle(Context context) {
            TypedValue outValue = new TypedValue();
            Resources.Theme widgetTheme = context.getResources().newTheme();
            widgetTheme.setTo(context.getTheme());
            widgetTheme.resolveAttribute(R.attr.actionBarPopupTheme, outValue, true);
            int i = outValue.resourceId;
            if (i != 0) {
                widgetTheme.applyStyle(i, true);
            }
            widgetTheme.resolveAttribute(R.attr.panelMenuListTheme, outValue, true);
            int i2 = outValue.resourceId;
            if (i2 != 0) {
                widgetTheme.applyStyle(i2, true);
            } else {
                widgetTheme.applyStyle(R.style.Theme_AppCompat_CompactMenu, true);
            }
            Context context2 = new androidx.appcompat.view.ContextThemeWrapper(context, 0);
            context2.getTheme().setTo(widgetTheme);
            this.listPresenterContext = context2;
            TypedArray a = context2.obtainStyledAttributes(R.styleable.AppCompatTheme);
            this.background = a.getResourceId(R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = a.getResourceId(R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            a.recycle();
        }

        /* access modifiers changed from: package-private */
        public void setMenu(MenuBuilder menu2) {
            ListMenuPresenter listMenuPresenter2;
            MenuBuilder menuBuilder = this.menu;
            if (menu2 != menuBuilder) {
                if (menuBuilder != null) {
                    menuBuilder.removeMenuPresenter(this.listMenuPresenter);
                }
                this.menu = menu2;
                if (menu2 != null && (listMenuPresenter2 = this.listMenuPresenter) != null) {
                    menu2.addMenuPresenter(listMenuPresenter2);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public MenuView getListMenuView(MenuPresenter.Callback cb) {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                ListMenuPresenter listMenuPresenter2 = new ListMenuPresenter(this.listPresenterContext, R.layout.abc_list_menu_item_layout);
                this.listMenuPresenter = listMenuPresenter2;
                listMenuPresenter2.setCallback(cb);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        /* access modifiers changed from: package-private */
        public Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            if (this.menu != null) {
                Bundle bundle = new Bundle();
                savedState.menuState = bundle;
                this.menu.savePresenterStates(bundle);
            }
            return savedState;
        }

        /* access modifiers changed from: package-private */
        public void onRestoreInstanceState(Parcelable state) {
            SavedState savedState = (SavedState) state;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.frozenMenuState = savedState.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        /* access modifiers changed from: package-private */
        public void applyFrozenState() {
            Bundle bundle;
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null && (bundle = this.frozenMenuState) != null) {
                menuBuilder.restorePresenterStates(bundle);
                this.frozenMenuState = null;
            }
        }

        @SuppressLint({"BanParcelableUsage"})
        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
                public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                    return SavedState.readFromParcel(in, loader);
                }

                public SavedState createFromParcel(Parcel in) {
                    return SavedState.readFromParcel(in, (ClassLoader) null);
                }

                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
            int featureId;
            boolean isOpen;
            Bundle menuState;

            SavedState() {
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.featureId);
                dest.writeInt(this.isOpen ? 1 : 0);
                if (this.isOpen) {
                    dest.writeBundle(this.menuState);
                }
            }

            static SavedState readFromParcel(Parcel source, ClassLoader loader) {
                SavedState savedState = new SavedState();
                savedState.featureId = source.readInt();
                boolean z = true;
                if (source.readInt() != 1) {
                    z = false;
                }
                savedState.isOpen = z;
                if (z) {
                    savedState.menuState = source.readBundle(loader);
                }
                return savedState;
            }
        }
    }

    public class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean onInterceptTouchEvent(MotionEvent event) {
            if (event.getAction() != 0 || !isOutOfBounds((int) event.getX(), (int) event.getY())) {
                return super.onInterceptTouchEvent(event);
            }
            AppCompatDelegateImpl.this.closePanel(0);
            return true;
        }

        public void setBackgroundResource(int resid) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), resid));
        }

        private boolean isOutOfBounds(int x, int y) {
            return x < -5 || y < -5 || x > getWidth() + 5 || y > getHeight() + 5;
        }
    }

    public class AppCompatWindowCallback extends WindowCallbackWrapper {
        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        public boolean dispatchKeyEvent(KeyEvent event) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(event) || super.dispatchKeyEvent(event);
        }

        public boolean dispatchKeyShortcutEvent(KeyEvent event) {
            return super.dispatchKeyShortcutEvent(event) || AppCompatDelegateImpl.this.onKeyShortcut(event.getKeyCode(), event);
        }

        public boolean onCreatePanelMenu(int featureId, Menu menu) {
            if (featureId != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(featureId, menu);
            }
            return false;
        }

        public void onContentChanged() {
        }

        public boolean onPreparePanel(int featureId, View view, Menu menu) {
            MenuBuilder mb = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (featureId == 0 && mb == null) {
                return false;
            }
            if (mb != null) {
                mb.setOverrideVisibleItems(true);
            }
            boolean handled = super.onPreparePanel(featureId, view, menu);
            if (mb != null) {
                mb.setOverrideVisibleItems(false);
            }
            return handled;
        }

        public boolean onMenuOpened(int featureId, Menu menu) {
            super.onMenuOpened(featureId, menu);
            AppCompatDelegateImpl.this.onMenuOpened(featureId);
            return true;
        }

        public void onPanelClosed(int featureId, Menu menu) {
            super.onPanelClosed(featureId, menu);
            AppCompatDelegateImpl.this.onPanelClosed(featureId);
        }

        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                return startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        /* access modifiers changed from: package-private */
        public final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, callback);
            androidx.appcompat.view.ActionMode supportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (supportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(supportActionMode);
            }
            return null;
        }

        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                switch (type) {
                    case 0:
                        return startAsSupportActionMode(callback);
                }
            }
            return super.onWindowStartingActionMode(callback, type);
        }

        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
            MenuBuilder menuBuilder;
            PanelFeatureState panel = AppCompatDelegateImpl.this.getPanelState(0, true);
            if (panel == null || (menuBuilder = panel.menu) == null) {
                super.onProvideKeyboardShortcuts(data, menu, deviceId);
            } else {
                super.onProvideKeyboardShortcuts(data, menuBuilder, deviceId);
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    @VisibleForTesting
    public abstract class AutoNightModeManager {
        private BroadcastReceiver mReceiver;

        /* access modifiers changed from: package-private */
        @Nullable
        public abstract IntentFilter createIntentFilterForBroadcastReceiver();

        /* access modifiers changed from: package-private */
        public abstract int getApplyableNightMode();

        /* access modifiers changed from: package-private */
        public abstract void onChange();

        AutoNightModeManager() {
        }

        /* access modifiers changed from: package-private */
        public void setup() {
            cleanup();
            IntentFilter filter = createIntentFilterForBroadcastReceiver();
            if (filter != null && filter.countActions() != 0) {
                if (this.mReceiver == null) {
                    this.mReceiver = new BroadcastReceiver() {
                        public void onReceive(Context context, Intent intent) {
                            AutoNightModeManager.this.onChange();
                        }
                    };
                }
                AppCompatDelegateImpl.this.mContext.registerReceiver(this.mReceiver, filter);
            }
        }

        /* access modifiers changed from: package-private */
        public void cleanup() {
            BroadcastReceiver broadcastReceiver = this.mReceiver;
            if (broadcastReceiver != null) {
                try {
                    AppCompatDelegateImpl.this.mContext.unregisterReceiver(broadcastReceiver);
                } catch (IllegalArgumentException e) {
                }
                this.mReceiver = null;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean isListening() {
            return this.mReceiver != null;
        }
    }

    public class AutoTimeNightModeManager extends AutoNightModeManager {
        private final TwilightManager mTwilightManager;

        AutoTimeNightModeManager(@NonNull TwilightManager twilightManager) {
            super();
            this.mTwilightManager = twilightManager;
        }

        public int getApplyableNightMode() {
            return this.mTwilightManager.isNight() ? 2 : 1;
        }

        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        /* access modifiers changed from: package-private */
        public IntentFilter createIntentFilterForBroadcastReceiver() {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.TIME_SET");
            filter.addAction("android.intent.action.TIMEZONE_CHANGED");
            filter.addAction("android.intent.action.TIME_TICK");
            return filter;
        }
    }

    public class AutoBatteryNightModeManager extends AutoNightModeManager {
        private final PowerManager mPowerManager;

        AutoBatteryNightModeManager(@NonNull Context context) {
            super();
            this.mPowerManager = (PowerManager) context.getApplicationContext().getSystemService("power");
        }

        public int getApplyableNightMode() {
            if (Build.VERSION.SDK_INT < 21 || !Api21Impl.isPowerSaveMode(this.mPowerManager)) {
                return 1;
            }
            return 2;
        }

        public void onChange() {
            AppCompatDelegateImpl.this.applyDayNight();
        }

        /* access modifiers changed from: package-private */
        public IntentFilter createIntentFilterForBroadcastReceiver() {
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            return filter;
        }
    }

    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    public class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        public Drawable getThemeUpIndicator() {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{R.attr.homeAsUpIndicator});
            Drawable result = a.getDrawable(0);
            a.recycle();
            return result;
        }

        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.getActionBarThemedContext();
        }

        public boolean isNavigationVisible() {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            return (ab == null || (ab.getDisplayOptions() & 4) == 0) ? false : true;
        }

        public void setActionBarUpIndicator(Drawable upDrawable, int contentDescRes) {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            if (ab != null) {
                ab.setHomeAsUpIndicator(upDrawable);
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }

        public void setActionBarDescription(int contentDescRes) {
            ActionBar ab = AppCompatDelegateImpl.this.getSupportActionBar();
            if (ab != null) {
                ab.setHomeActionContentDescription(contentDescRes);
            }
        }
    }

    @NonNull
    private static Configuration generateConfigDelta(@NonNull Configuration base, @Nullable Configuration change) {
        Configuration delta = new Configuration();
        delta.fontScale = 0.0f;
        if (change == null || base.diff(change) == 0) {
            return delta;
        }
        float f = base.fontScale;
        float f2 = change.fontScale;
        if (f != f2) {
            delta.fontScale = f2;
        }
        int i = base.mcc;
        int i2 = change.mcc;
        if (i != i2) {
            delta.mcc = i2;
        }
        int i3 = base.mnc;
        int i4 = change.mnc;
        if (i3 != i4) {
            delta.mnc = i4;
        }
        int i5 = Build.VERSION.SDK_INT;
        if (i5 >= 24) {
            Api24Impl.generateConfigDelta_locale(base, change, delta);
        } else if (!ObjectsCompat.equals(base.locale, change.locale)) {
            delta.locale = change.locale;
        }
        int i6 = base.touchscreen;
        int i7 = change.touchscreen;
        if (i6 != i7) {
            delta.touchscreen = i7;
        }
        int i8 = base.keyboard;
        int i9 = change.keyboard;
        if (i8 != i9) {
            delta.keyboard = i9;
        }
        int i10 = base.keyboardHidden;
        int i11 = change.keyboardHidden;
        if (i10 != i11) {
            delta.keyboardHidden = i11;
        }
        int i12 = base.navigation;
        int i13 = change.navigation;
        if (i12 != i13) {
            delta.navigation = i13;
        }
        int i14 = base.navigationHidden;
        int i15 = change.navigationHidden;
        if (i14 != i15) {
            delta.navigationHidden = i15;
        }
        int i16 = base.orientation;
        int i17 = change.orientation;
        if (i16 != i17) {
            delta.orientation = i17;
        }
        int i18 = base.screenLayout & 15;
        int i19 = change.screenLayout;
        if (i18 != (i19 & 15)) {
            delta.screenLayout |= i19 & 15;
        }
        int i20 = base.screenLayout & Opcodes.CHECKCAST;
        int i21 = change.screenLayout;
        if (i20 != (i21 & Opcodes.CHECKCAST)) {
            delta.screenLayout |= i21 & Opcodes.CHECKCAST;
        }
        int i22 = base.screenLayout & 48;
        int i23 = change.screenLayout;
        if (i22 != (i23 & 48)) {
            delta.screenLayout |= i23 & 48;
        }
        int i24 = base.screenLayout & AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART;
        int i25 = change.screenLayout;
        if (i24 != (i25 & AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART)) {
            delta.screenLayout |= i25 & AVIOCTRLDEFs.IOTYPE_USER_IPCAM_AUDIOSTART;
        }
        if (i5 >= 26) {
            Api26Impl.generateConfigDelta_colorMode(base, change, delta);
        }
        int i26 = base.uiMode & 15;
        int i27 = change.uiMode;
        if (i26 != (i27 & 15)) {
            delta.uiMode |= i27 & 15;
        }
        int i28 = base.uiMode & 48;
        int i29 = change.uiMode;
        if (i28 != (i29 & 48)) {
            delta.uiMode |= i29 & 48;
        }
        int i30 = base.screenWidthDp;
        int i31 = change.screenWidthDp;
        if (i30 != i31) {
            delta.screenWidthDp = i31;
        }
        int i32 = base.screenHeightDp;
        int i33 = change.screenHeightDp;
        if (i32 != i33) {
            delta.screenHeightDp = i33;
        }
        int i34 = base.smallestScreenWidthDp;
        int i35 = change.smallestScreenWidthDp;
        if (i34 != i35) {
            delta.smallestScreenWidthDp = i35;
        }
        if (i5 >= 17) {
            Api17Impl.generateConfigDelta_densityDpi(base, change, delta);
        }
        return delta;
    }

    @RequiresApi(17)
    public static class Api17Impl {
        private Api17Impl() {
        }

        static void generateConfigDelta_densityDpi(@NonNull Configuration base, @NonNull Configuration change, @NonNull Configuration delta) {
            int i = base.densityDpi;
            int i2 = change.densityDpi;
            if (i != i2) {
                delta.densityDpi = i2;
            }
        }

        static Context createConfigurationContext(@NonNull Context context, @NonNull Configuration overrideConfiguration) {
            return context.createConfigurationContext(overrideConfiguration);
        }
    }

    @RequiresApi(21)
    public static class Api21Impl {
        private Api21Impl() {
        }

        static boolean isPowerSaveMode(PowerManager powerManager) {
            return powerManager.isPowerSaveMode();
        }
    }

    @RequiresApi(24)
    public static class Api24Impl {
        private Api24Impl() {
        }

        static void generateConfigDelta_locale(@NonNull Configuration base, @NonNull Configuration change, @NonNull Configuration delta) {
            LocaleList baseLocales = base.getLocales();
            LocaleList changeLocales = change.getLocales();
            if (!baseLocales.equals(changeLocales)) {
                delta.setLocales(changeLocales);
                delta.locale = change.locale;
            }
        }
    }

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        static void generateConfigDelta_colorMode(@NonNull Configuration base, @NonNull Configuration change, @NonNull Configuration delta) {
            int i = base.colorMode & 3;
            int i2 = change.colorMode;
            if (i != (i2 & 3)) {
                delta.colorMode |= i2 & 3;
            }
            int i3 = base.colorMode & 12;
            int i4 = change.colorMode;
            if (i3 != (i4 & 12)) {
                delta.colorMode |= i4 & 12;
            }
        }
    }
}
