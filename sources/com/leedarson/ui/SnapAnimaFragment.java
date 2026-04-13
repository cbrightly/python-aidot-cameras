package com.leedarson.ui;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import com.leedarson.R$color;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$style;
import com.leedarson.utils.n;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import com.sensorsdata.analytics.android.sdk.autotrack.aop.FragmentTrackHelper;
import java.io.File;

public class SnapAnimaFragment extends DialogFragment {
    public static ChangeQuickRedirect changeQuickRedirect;
    Handler a1 = new Handler();
    private ImageView c;
    private Uri d;
    private Handler f = new Handler(Looper.getMainLooper());
    public int p0;
    public int q;
    public int x;
    public int y;
    public int z;

    @SensorsDataInstrumented
    public void onHiddenChanged(boolean z2) {
        super.onHiddenChanged(z2);
        FragmentTrackHelper.trackOnHiddenChanged(this, z2);
    }

    @SensorsDataInstrumented
    public void onPause() {
        super.onPause();
        FragmentTrackHelper.trackFragmentPause(this);
    }

    @SensorsDataInstrumented
    public void onResume() {
        super.onResume();
        FragmentTrackHelper.trackFragmentResume(this);
    }

    @SensorsDataInstrumented
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        FragmentTrackHelper.onFragmentViewCreated(this, view, bundle);
    }

    @SensorsDataInstrumented
    public void setUserVisibleHint(boolean z2) {
        super.setUserVisibleHint(z2);
        FragmentTrackHelper.trackFragmentSetUserVisibleHint(this, z2);
    }

    public static SnapAnimaFragment p1(String imagePath) {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{imagePath}, (Object) null, changeQuickRedirect, true, 11432, new Class[]{String.class}, SnapAnimaFragment.class);
        if (proxy.isSupported) {
            return (SnapAnimaFragment) proxy.result;
        }
        SnapAnimaFragment snapAnimaFragment = new SnapAnimaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image_path", imagePath);
        com.leedarson.base.logger.a.c("SnapAnimFragment", "imagePath=" + imagePath);
        snapAnimaFragment.setArguments(bundle);
        return snapAnimaFragment;
    }

    public static SnapAnimaFragment o1(Uri imageUri, int width, int height, int marginLeft, int marginTop, int marginRight) {
        Object[] objArr = {imageUri, new Integer(width), new Integer(height), new Integer(marginLeft), new Integer(marginTop), new Integer(marginRight)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        Class cls = Integer.TYPE;
        PatchProxyResult proxy = PatchProxy.proxy(objArr, (Object) null, changeQuickRedirect2, true, 11433, new Class[]{Uri.class, cls, cls, cls, cls, cls}, SnapAnimaFragment.class);
        if (proxy.isSupported) {
            return (SnapAnimaFragment) proxy.result;
        }
        SnapAnimaFragment snapAnimaFragment = new SnapAnimaFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("image_uri", imageUri);
        com.leedarson.base.logger.a.c("SnapAnimFragment", "imageUri=" + imageUri);
        bundle.putInt("width", width);
        bundle.putInt("height", height);
        bundle.putInt("marginTop", marginTop);
        bundle.putInt("marginLeft", marginLeft);
        bundle.putInt("marginRight", marginRight);
        snapAnimaFragment.setArguments(bundle);
        return snapAnimaFragment;
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        boolean isLandScap = true;
        PatchProxyResult proxy = PatchProxy.proxy(new Object[]{inflater, container, bundle}, this, changeQuickRedirect, false, 11434, new Class[]{LayoutInflater.class, ViewGroup.class, Bundle.class}, View.class);
        if (proxy.isSupported) {
            return (View) proxy.result;
        }
        if (getActivity().getRequestedOrientation() != 0) {
            isLandScap = false;
        }
        View view = inflater.inflate(isLandScap ? R$layout.fragment_snap_anima_land_origin : R$layout.fragment_snap_anima, container, false);
        ImageView imageView = (ImageView) view.findViewById(R$id.snap_img);
        this.c = imageView;
        Uri uri = this.d;
        if (uri != null) {
            imageView.setImageURI(uri);
            m1(view);
            if (this.f == null) {
                this.f = new Handler();
            }
            Handler handler = this.f;
            if (handler != null) {
                handler.postDelayed(new a(view), 300);
            }
        } else {
            dismiss();
        }
        return view;
    }

    public class a implements Runnable {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View c;

        a(View view) {
            this.c = view;
        }

        public void run() {
            if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11444, new Class[0], Void.TYPE).isSupported) {
                try {
                    if (SnapAnimaFragment.this.getContext() != null) {
                        SnapAnimaFragment.this.s1(this.c);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void m1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11435, new Class[]{View.class}, Void.TYPE).isSupported) {
            if (this.q != 0 || this.x != 0) {
                int i = R$id.lnImageBox;
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) view.findViewById(i).getLayoutParams();
                int i2 = this.z;
                if (i2 != 0) {
                    layoutParams.topMargin = i2;
                }
                int i3 = this.y;
                if (i3 != 0) {
                    layoutParams.leftMargin = i3;
                }
                int i4 = this.p0;
                if (i4 != 0) {
                    layoutParams.rightMargin = i4;
                }
                ConstraintSet constraintSet = new ConstraintSet();
                ConstraintLayout layout = (ConstraintLayout) view.findViewById(R$id.root_constraint);
                constraintSet.clone(layout);
                if (getActivity().getRequestedOrientation() == 0) {
                    constraintSet.setDimensionRatio(i, "w," + (this.q + ":" + this.x));
                } else {
                    constraintSet.setDimensionRatio(i, "w," + (this.x + ":" + this.q));
                }
                view.findViewById(i).setLayoutParams(layoutParams);
                constraintSet.applyTo(layout);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void s1(View contentView) {
        if (!PatchProxy.proxy(new Object[]{contentView}, this, changeQuickRedirect, false, 11436, new Class[]{View.class}, Void.TYPE).isSupported) {
            ConstraintSet constraintSet = new ConstraintSet();
            int i = R$id.lnImageBox;
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) contentView.findViewById(i).getLayoutParams();
            layoutParams.leftMargin = 20;
            layoutParams.bottomMargin = 20;
            ConstraintLayout layout = (ConstraintLayout) contentView.findViewById(R$id.root_constraint);
            constraintSet.clone(layout);
            if (getActivity().getRequestedOrientation() == 0) {
                constraintSet.setDimensionRatio(i, "w,16:9");
                constraintSet.constrainHeight(i, n.b(getContext()) / 3);
            } else if (this.q == 0 || this.x == 0) {
                constraintSet.setDimensionRatio(i, "w,9:16");
                constraintSet.constrainWidth(i, l1());
            } else {
                constraintSet.setDimensionRatio(i, "w," + (this.x + ":" + this.q));
                constraintSet.constrainWidth(i, l1());
            }
            constraintSet.clear(i, 3);
            constraintSet.clear(i, 2);
            contentView.findViewById(i).setLayoutParams(layoutParams);
            constraintSet.connect(i, 1, 0, 1);
            constraintSet.connect(i, 4, 0, 4);
            AutoTransition transition = new AutoTransition();
            transition.setDuration(300);
            transition.addListener(new b(contentView));
            TransitionManager.beginDelayedTransition(layout, transition);
            constraintSet.applyTo(layout);
        }
    }

    public class b implements Transition.TransitionListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View a;

        b(View view) {
            this.a = view;
        }

        public void onTransitionStart(Transition transition) {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11446, new Class[0], Void.TYPE).isSupported) {
                    b bVar = b.this;
                    SnapAnimaFragment.this.q1(bVar.a);
                }
            }
        }

        public void onTransitionEnd(Transition transition) {
            if (!PatchProxy.proxy(new Object[]{transition}, this, changeQuickRedirect, false, 11445, new Class[]{Transition.class}, Void.TYPE).isSupported) {
                SnapAnimaFragment.this.a1.postDelayed(new a(), 600);
            }
        }

        public void onTransitionCancel(Transition transition) {
        }

        public void onTransitionPause(Transition transition) {
        }

        public void onTransitionResume(Transition transition) {
        }
    }

    /* access modifiers changed from: package-private */
    public int l1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11437, new Class[0], Integer.TYPE);
        if (proxy.isSupported) {
            return ((Integer) proxy.result).intValue();
        }
        DisplayMetrics outMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        int heightPixels = outMetrics.heightPixels;
        Log.i("SnapShot", "width=" + widthPixels + "  height=" + heightPixels);
        if (widthPixels <= heightPixels) {
            return (int) (((double) widthPixels) * 0.5d);
        }
        return (int) (((double) widthPixels) * 0.3d);
    }

    /* access modifiers changed from: package-private */
    public void q1(View view) {
        if (!PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 11438, new Class[]{View.class}, Void.TYPE).isSupported) {
            View contentView = view;
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setFillAfter(true);
            TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, -1.3f, 1, 0.0f, 1, 0.0f);
            translateAnimation.setAnimationListener(new c(contentView));
            translateAnimation.setDuration(500);
            AnimationSet animationSet = new AnimationSet(false);
            animationSet.addAnimation(translateAnimation);
            contentView.findViewById(R$id.lnImageBox).startAnimation(animationSet);
        }
    }

    public class c implements Animation.AnimationListener {
        public static ChangeQuickRedirect changeQuickRedirect;
        final /* synthetic */ View a;

        c(View view) {
            this.a = view;
        }

        public void onAnimationStart(Animation animation) {
        }

        public class a implements Runnable {
            public static ChangeQuickRedirect changeQuickRedirect;

            a() {
            }

            public void run() {
                if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11448, new Class[0], Void.TYPE).isSupported) {
                    SnapAnimaFragment.this.dismiss();
                }
            }
        }

        public void onAnimationEnd(Animation animation) {
            if (!PatchProxy.proxy(new Object[]{animation}, this, changeQuickRedirect, false, 11447, new Class[]{Animation.class}, Void.TYPE).isSupported) {
                this.a.post(new a());
            }
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 11439, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            if (n1()) {
                getDialog().getWindow().setFlags(1024, 1024);
            } else {
                getDialog().getWindow().clearFlags(1024);
            }
            super.onActivityCreated(savedInstanceState);
            getDialog().getWindow().setLayout(-1, -1);
            r1(R$color.dialog_statuebar_color);
        }
    }

    public void r1(int i) {
        if (!PatchProxy.proxy(new Object[]{new Integer(i)}, this, changeQuickRedirect, false, 11440, new Class[]{Integer.TYPE}, Void.TYPE).isSupported) {
            if (Build.VERSION.SDK_INT >= 21) {
                Window window = getDialog().getWindow();
                window.clearFlags(2);
                window.setBackgroundDrawableResource(17170445);
                WindowManager.LayoutParams lp = window.getAttributes();
                window.setGravity(53);
                lp.dimAmount = 0.2f;
                lp.x = 40;
                lp.y = 40;
                window.setAttributes(lp);
            }
            getDialog().getWindow().getDecorView().setSystemUiVisibility(0);
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 11441, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            Bundle bundle = getArguments();
            setStyle(2, R$style.LDSDialog_FullScreen);
            if (bundle != null) {
                String imagePath = bundle.getString("image_path");
                if (!TextUtils.isEmpty(imagePath)) {
                    this.d = Uri.fromFile(new File(imagePath));
                } else {
                    this.d = (Uri) bundle.getParcelable("image_uri");
                }
                this.q = bundle.getInt("width");
                this.x = bundle.getInt("height");
                this.z = bundle.getInt("marginTop");
                this.y = bundle.getInt("marginLeft");
                this.p0 = bundle.getInt("marginRight");
            }
        }
    }

    public boolean n1() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 11443, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        try {
            if (getResources().getConfiguration().orientation == 2) {
                return true;
            }
            return getResources().getConfiguration().orientation == 1 ? false : false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
