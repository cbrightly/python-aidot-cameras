package com.leedarson.serviceimpl.business.capture;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.leedarson.serviceimpl.business.R;
import com.leedarson.serviceinterface.event.UrlChangeEvent;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import io.reactivex.android.schedulers.a;
import io.reactivex.disposables.b;
import io.reactivex.l;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.c;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaCodecInfo;

public abstract class DragFloatView<Data> implements View.OnTouchListener {
    private static final int DIRECTION_LEFT = 1;
    private static final int DIRECTION_RIGHT = 2;
    public static final String TAG = DragFloatView.class.getSimpleName();
    public static ChangeQuickRedirect changeQuickRedirect;
    private JSONArray buttonsArray;
    private DisplayMetrics dm;
    public String imagePath;
    public String imageUrl;
    private boolean isAdded = false;
    private boolean isDraggable = true;
    private boolean isKeepSide = false;
    private boolean isShow = false;
    public Activity mActivity;
    private int mBottomLimitHeight = 0;
    private int mClickX = 0;
    private int mClickY = 0;
    /* access modifiers changed from: private */
    public View mContentView;
    private int mDefaultLocationY = 0;
    private int mHeight = 0;
    private boolean mIsMoved = false;
    private ValueAnimator mKeepSideAnimator;
    private long mKeepSideTimeMillis = 0;
    /* access modifiers changed from: private */
    public WindowManager.LayoutParams mLayoutParam;
    private int mScreenHeight = 0;
    private int mScreenWidth = 0;
    private int mTouchSlop = 0;
    private int mWidth = 0;
    /* access modifiers changed from: private */
    public WindowManager mWindowManager;
    private b subscribe;

    public abstract void applyData(Data data);

    public abstract int[] generateWindowSize();

    public abstract View onCreateView();

    public DragFloatView(Activity activity) {
        this.mActivity = activity;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        this.dm = displayMetrics;
        int i = displayMetrics.widthPixels;
        this.mScreenWidth = i;
        int i2 = displayMetrics.heightPixels;
        this.mScreenHeight = i2;
        int[] size = {i / 3, (i2 / 3) + IjkMediaCodecInfo.RANK_SECURE};
        this.mWidth = size[0];
        this.mHeight = size[1];
        this.mDefaultLocationY = getDefaultLocationY();
        this.mKeepSideTimeMillis = getKeepSideTimeMillis();
        this.mBottomLimitHeight = getBottomLimitHeight();
        this.isKeepSide = isKeepSide();
        this.isDraggable = isDraggable();
        this.mTouchSlop = dp2px(4.0f);
        View onCreateView = onCreateView();
        this.mContentView = onCreateView;
        if (onCreateView != null) {
            onCreateView.setOnTouchListener(this);
            return;
        }
        throw new IllegalArgumentException("No content view was found!");
    }

    public View getContentView() {
        return this.mContentView;
    }

    public Activity getActivity() {
        return this.mActivity;
    }

    public int getDefaultLocationY() {
        return (this.mScreenHeight - this.mHeight) - 200;
    }

    public int getBottomLimitHeight() {
        return this.mScreenHeight - (this.mHeight * 2);
    }

    public long getKeepSideTimeMillis() {
        return 500;
    }

    public boolean isKeepSide() {
        return false;
    }

    public void setKeepSide(boolean isKeepSide2) {
        this.isKeepSide = isKeepSide2;
    }

    public void setDraggable(boolean isDraggable2) {
        this.isDraggable = isDraggable2;
    }

    public boolean isDraggable() {
        return true;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getScreenWidth() {
        return this.mScreenWidth;
    }

    public int getScreenHeight() {
        return this.mScreenHeight;
    }

    public WindowManager.LayoutParams generateWindowLayoutParam() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7114, new Class[0], WindowManager.LayoutParams.class);
        return proxy.isSupported ? (WindowManager.LayoutParams) proxy.result : getDefaultWindowLayoutParam();
    }

    private WindowManager.LayoutParams getDefaultWindowLayoutParam() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7115, new Class[0], WindowManager.LayoutParams.class);
        if (proxy.isSupported) {
            return (WindowManager.LayoutParams) proxy.result;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        int i = this.mWidth;
        lp.width = i;
        lp.height = this.mHeight;
        lp.type = 2;
        lp.format = 1;
        lp.flags = 40;
        lp.gravity = 51;
        lp.x = this.mScreenHeight - i;
        lp.y = this.mDefaultLocationY;
        return lp;
    }

    public void create() {
        String title;
        String icon;
        String str = "href";
        String str2 = TypedValues.AttributesType.S_TARGET;
        String str3 = "icon";
        String str4 = "title";
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7116, new Class[0], Void.TYPE).isSupported) {
            if (!this.isAdded) {
                Log.w(TAG, "create: " + getClass().getSimpleName());
                WindowManager wm = this.mActivity.getWindowManager();
                WindowManager.LayoutParams lp = generateWindowLayoutParam();
                if (lp == null) {
                    lp = getDefaultWindowLayoutParam();
                }
                this.mWindowManager = wm;
                this.mLayoutParam = lp;
                wm.addView(this.mContentView, lp);
                this.isAdded = true;
                View llCapture = this.mContentView.findViewById(R.id.ll_capture);
                ViewGroup.LayoutParams layoutParams = llCapture.getLayoutParams();
                layoutParams.height = (this.mScreenHeight / 3) + 40;
                llCapture.setLayoutParams(layoutParams);
                LinearLayout llButtons = (LinearLayout) this.mContentView.findViewById(R.id.ll_feedback_buttons);
                llButtons.removeAllViews();
                try {
                    if (this.buttonsArray != null) {
                        int i = 0;
                        while (i < this.buttonsArray.length()) {
                            JSONObject button = this.buttonsArray.getJSONObject(i);
                            String href = null;
                            if (button.has(str4)) {
                                title = button.getString(str4);
                            } else {
                                title = null;
                            }
                            if (button.has(str3)) {
                                icon = button.getString(str3);
                            } else {
                                icon = null;
                            }
                            if (button.has(str2)) {
                                String target = button.getString(str2);
                            }
                            if (button.has(str)) {
                                href = button.getString(str);
                            }
                            String str5 = str;
                            String str6 = str2;
                            TextView textView = new TextView(this.mActivity);
                            textView.setTextColor(-1);
                            textView.setText(title);
                            llButtons.addView(textView);
                            String str7 = str3;
                            String str8 = str4;
                            int iconId = this.mActivity.getResources().getIdentifier(icon, "drawable", this.mActivity.getPackageName());
                            StringBuilder sb = new StringBuilder();
                            sb.append(iconId);
                            String str9 = icon;
                            sb.append("------------------");
                            sb.append(R.drawable.feedback);
                            Log.i("Ghunt", sb.toString());
                            Drawable iconDraw = this.mActivity.getResources().getDrawable(iconId);
                            iconDraw.setBounds(0, 0, 50, 50);
                            textView.setCompoundDrawablePadding(30);
                            textView.setTextSize(15.0f);
                            textView.setCompoundDrawables(iconDraw, (Drawable) null, (Drawable) null, (Drawable) null);
                            textView.setTag(href);
                            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) textView.getLayoutParams();
                            layoutParams1.height = -2;
                            layoutParams1.gravity = 17;
                            textView.setLayoutParams(layoutParams1);
                            textView.setOnClickListener(new a(this));
                            i++;
                            str = str5;
                            str2 = str6;
                            str3 = str7;
                            str4 = str8;
                        }
                    }
                } catch (Exception e) {
                }
            }
            b bVar = this.subscribe;
            if (bVar != null && bVar.isDisposed()) {
                this.subscribe.dispose();
            }
            this.subscribe = l.F(1).l(5, TimeUnit.SECONDS).J(a.a()).X(new b(this));
        }
    }

    /* access modifiers changed from: private */
    @SensorsDataInstrumented
    /* renamed from: lambda$create$0 */
    public /* synthetic */ void a(View l) {
        if (PatchProxy.proxy(new Object[]{l}, this, changeQuickRedirect, false, 7126, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(l);
            return;
        }
        c.c().l(new UrlChangeEvent(l.getTag().toString() + "?imagePath=" + this.imagePath + "&imageUrl=" + this.imageUrl));
        destroy();
        SensorsDataAutoTrackHelper.trackViewOnClick(l);
    }

    /* access modifiers changed from: private */
    /* renamed from: lambda$create$1 */
    public /* synthetic */ void b(Integer num) {
        if (!PatchProxy.proxy(new Object[]{num}, this, changeQuickRedirect, false, 7125, new Class[]{Integer.class}, Void.TYPE).isSupported) {
            destroy();
        }
    }

    public boolean onTouch(View view, MotionEvent event) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{view, event}, this, changeQuickRedirect, false, 7117, new Class[]{View.class, MotionEvent.class}, Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        if (event.getAction() == 4) {
            Log.i(TAG, "outside");
            if (this.isAdded) {
                destroy();
            }
        }
        return false;
    }

    private void keepSideIfNeed(int currentX, int currentY) {
        boolean isOutOfBounds = false;
        Object[] objArr = {new Integer(currentX), new Integer(currentY)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        if (!PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 7118, new Class[]{cls, cls}, Void.TYPE).isSupported) {
            int i = this.mScreenWidth;
            final int endX = (currentX < i / 2 ? 1 : 2) == 1 ? 0 : i - this.mWidth;
            if (currentY > this.mBottomLimitHeight) {
                isOutOfBounds = true;
            }
            final int endY = isOutOfBounds ? this.mDefaultLocationY : currentY;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
            this.mKeepSideAnimator = ofFloat;
            final int i2 = currentX;
            final int i3 = currentY;
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public static ChangeQuickRedirect changeQuickRedirect;

                public void onAnimationUpdate(ValueAnimator animation) {
                    if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 7127, new Class[]{ValueAnimator.class}, Void.TYPE).isSupported) {
                        float offsetValue = ((Float) animation.getAnimatedValue()).floatValue();
                        WindowManager.LayoutParams access$000 = DragFloatView.this.mLayoutParam;
                        int i = i2;
                        access$000.x = (int) (((float) i) + (((float) (endX - i)) * offsetValue));
                        WindowManager.LayoutParams access$0002 = DragFloatView.this.mLayoutParam;
                        int i2 = i3;
                        access$0002.y = (int) (((float) i2) + (((float) (endY - i2)) * offsetValue));
                        DragFloatView.this.mWindowManager.updateViewLayout(DragFloatView.this.mContentView, DragFloatView.this.mLayoutParam);
                    }
                }
            });
            this.mKeepSideAnimator.setDuration(this.mKeepSideTimeMillis);
            this.mKeepSideAnimator.start();
        }
    }

    public <T extends View> T findViewById(int id) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{new Integer(id)}, this, changeQuickRedirect, false, 7119, new Class[]{Integer.TYPE}, View.class);
        return proxy.isSupported ? (View) proxy.result : this.mContentView.findViewById(id);
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if (!PatchProxy.proxy(new Object[]{listener}, this, changeQuickRedirect, false, 7120, new Class[]{View.OnClickListener.class}, Void.TYPE).isSupported) {
            this.mContentView.setOnClickListener(listener);
        }
    }

    public void toggle(boolean toggle) {
        int i = 0;
        if (!PatchProxy.proxy(new Object[]{new Byte(toggle ? (byte) 1 : 0)}, this, changeQuickRedirect, false, 7121, new Class[]{Boolean.TYPE}, Void.TYPE).isSupported) {
            View view = this.mContentView;
            if (!toggle) {
                i = 4;
            }
            view.setVisibility(i);
        }
    }

    public void destroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7122, new Class[0], Void.TYPE).isSupported) {
            if (this.isAdded) {
                this.mWindowManager.removeView(this.mContentView);
                this.mWindowManager = null;
                this.mLayoutParam = null;
                this.isAdded = false;
            }
        }
    }

    public void dismiss() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 7123, new Class[0], Void.TYPE).isSupported) {
            if (this.isAdded) {
                this.mWindowManager.removeView(this.mContentView);
                this.isAdded = false;
            }
        }
    }

    public boolean isAdded() {
        return this.isAdded;
    }

    public int dp2px(float dp) {
        return (int) ((this.dm.density * dp) + 0.5f);
    }

    public void setButtons(String feedbackButtons) {
        if (!PatchProxy.proxy(new Object[]{feedbackButtons}, this, changeQuickRedirect, false, 7124, new Class[]{String.class}, Void.TYPE).isSupported) {
            try {
                this.buttonsArray = new JSONArray(feedbackButtons);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
