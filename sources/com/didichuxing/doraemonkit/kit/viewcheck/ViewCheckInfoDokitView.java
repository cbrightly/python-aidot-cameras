package com.didichuxing.doraemonkit.kit.viewcheck;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.blankj.utilcode.util.a;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.AbsDokitView;
import com.didichuxing.doraemonkit.kit.core.DokitViewLayoutParams;
import com.didichuxing.doraemonkit.kit.core.DokitViewManager;
import com.didichuxing.doraemonkit.kit.viewcheck.ViewCheckDokitView;
import com.didichuxing.doraemonkit.util.ColorUtil;
import com.didichuxing.doraemonkit.util.UIUtils;
import com.meituan.robust.Constants;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.List;

public class ViewCheckInfoDokitView extends AbsDokitView implements ViewCheckDokitView.OnViewSelectListener, View.OnClickListener {
    private TextView mActivityInfo;
    private ImageView mClose;
    private TextView mDesc;
    private TextView mFragmentInfo;
    private TextView mId;
    private TextView mName;
    private ImageView mNext;
    private TextView mPosition;
    private ImageView mPre;

    public void onCreate(Context context) {
    }

    public void onDestroy() {
        super.onDestroy();
        ViewCheckDokitView dokitView = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(getActivity(), ViewCheckDokitView.class.getSimpleName());
        if (dokitView != null) {
            dokitView.removeViewSelectListener(this);
        }
    }

    public View onCreateView(Context context, FrameLayout view) {
        return LayoutInflater.from(context).inflate(R.layout.dk_float_view_check_info, (ViewGroup) null);
    }

    public void onViewCreated(FrameLayout view) {
        this.mId = (TextView) findViewById(R.id.id);
        this.mName = (TextView) findViewById(R.id.name);
        this.mPosition = (TextView) findViewById(R.id.position);
        this.mDesc = (TextView) findViewById(R.id.desc);
        this.mActivityInfo = (TextView) findViewById(R.id.activity);
        this.mFragmentInfo = (TextView) findViewById(R.id.fragment);
        ImageView imageView = (ImageView) findViewById(R.id.close);
        this.mClose = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.pre);
        this.mPre = imageView2;
        imageView2.setOnClickListener(this);
        ImageView imageView3 = (ImageView) findViewById(R.id.next);
        this.mNext = imageView3;
        imageView3.setOnClickListener(this);
        postDelayed(new Runnable() {
            public void run() {
                ViewCheckDokitView dokitView = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(ViewCheckInfoDokitView.this.getActivity(), ViewCheckDokitView.class.getSimpleName());
                if (dokitView != null) {
                    dokitView.setViewSelectListener(ViewCheckInfoDokitView.this);
                }
            }
        }, 200);
    }

    public void initDokitViewLayoutParams(DokitViewLayoutParams params) {
        params.flags = DokitViewLayoutParams.FLAG_NOT_FOCUSABLE;
        params.x = 0;
        params.y = UIUtils.getHeightPixels() - UIUtils.dp2px(185.0f);
        params.width = getScreenShortSideLength();
        params.height = DokitViewLayoutParams.WRAP_CONTENT;
    }

    public void updateViewLayout(String tag, boolean isActivityResume) {
        super.updateViewLayout(tag, isActivityResume);
        FrameLayout.LayoutParams params = getNormalLayoutParams();
        params.height = -2;
        getRootView().setLayoutParams(params);
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        ViewCheckDokitView dokitView;
        ViewCheckDokitView dokitView2;
        Class<ViewCheckDokitView> cls = ViewCheckDokitView.class;
        View v = view;
        if (v == this.mClose) {
            DokitViewManager.getInstance().detach(ViewCheckDrawDokitView.class.getSimpleName());
            DokitViewManager.getInstance().detach(ViewCheckInfoDokitView.class.getSimpleName());
            DokitViewManager.getInstance().detach(cls.getSimpleName());
        }
        if (v == this.mNext && (dokitView2 = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(getActivity(), cls.getSimpleName())) != null) {
            dokitView2.preformNextCheckView();
        }
        if (v == this.mPre && (dokitView = (ViewCheckDokitView) DokitViewManager.getInstance().getDokitView(getActivity(), cls.getSimpleName())) != null) {
            dokitView.preformPreCheckView();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }

    public void onViewSelected(@Nullable View current, @NonNull List<View> checkViewList) {
        this.mNext.setVisibility(checkViewList.size() > 1 ? 0 : 8);
        this.mPre.setVisibility(checkViewList.size() > 1 ? 0 : 8);
        if (current == null) {
            this.mName.setText("");
            this.mId.setText("");
            this.mPosition.setText("");
            this.mDesc.setText("");
            return;
        }
        this.mName.setText(getResources().getString(R.string.dk_view_check_info_class, new Object[]{current.getClass().getSimpleName()}));
        this.mId.setText(getResources().getString(R.string.dk_view_check_info_id, new Object[]{UIUtils.getIdText(current)}));
        this.mPosition.setText(getResources().getString(R.string.dk_view_check_info_size, new Object[]{Integer.valueOf(current.getWidth()), Integer.valueOf(current.getHeight())}));
        String descText = getViewExtraInfo(current);
        if (TextUtils.isEmpty(descText)) {
            this.mDesc.setVisibility(8);
        } else {
            this.mDesc.setText(descText);
            this.mDesc.setVisibility(0);
        }
        Activity activity = a.b();
        if (activity != null) {
            setTextAndVisible(this.mActivityInfo, getResources().getString(R.string.dk_view_check_info_activity, new Object[]{activity.getClass().getSimpleName()}));
            String fragmentText = getVisibleFragment(activity);
            if (!TextUtils.isEmpty(fragmentText)) {
                setTextAndVisible(this.mFragmentInfo, getResources().getString(R.string.dk_view_check_info_fragment, new Object[]{fragmentText}));
            } else {
                setTextAndVisible(this.mFragmentInfo, "");
            }
        } else {
            setTextAndVisible(this.mActivityInfo, "");
            setTextAndVisible(this.mFragmentInfo, "");
        }
    }

    private String getViewExtraInfo(View v) {
        StringBuilder info = new StringBuilder();
        Drawable drawable = v.getBackground();
        if (drawable != null && (drawable instanceof ColorDrawable)) {
            String backgroundColor = ColorUtil.parseColorInt(((ColorDrawable) drawable).getColor());
            info.append(getResources().getString(R.string.dk_view_check_info_desc, new Object[]{backgroundColor}));
            info.append("\n");
        }
        if (!(v.getPaddingLeft() == 0 || v.getPaddingTop() == 0 || v.getPaddingRight() == 0 || v.getPaddingBottom() == 0)) {
            info.append(getResources().getString(R.string.dk_view_check_info_padding, new Object[]{Integer.valueOf(v.getPaddingLeft()), Integer.valueOf(v.getPaddingTop()), Integer.valueOf(v.getPaddingRight()), Integer.valueOf(v.getPaddingBottom())}));
            info.append("\n");
        }
        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) layoutParams;
            if (!(mp.leftMargin == 0 || mp.topMargin == 0 || mp.rightMargin == 0 || mp.bottomMargin == 0)) {
                info.append(getResources().getString(R.string.dk_view_check_info_margin, new Object[]{Integer.valueOf(mp.leftMargin), Integer.valueOf(mp.topMargin), Integer.valueOf(mp.rightMargin), Integer.valueOf(mp.bottomMargin)}));
                info.append("\n");
            }
        }
        if (v instanceof TextView) {
            TextView tv2 = (TextView) v;
            String textColor = ColorUtil.parseColorInt(tv2.getCurrentTextColor());
            info.append(getResources().getString(R.string.dk_view_check_info_text_color, new Object[]{textColor}));
            info.append("\n");
            info.append(getResources().getString(R.string.dk_view_check_info_text_size, new Object[]{Integer.valueOf((int) tv2.getTextSize())}));
            info.append("\n");
        }
        if (!TextUtils.isEmpty(info)) {
            info.deleteCharAt(info.length() - 1);
        }
        return info.toString();
    }

    private void setTextAndVisible(TextView textView, String text) {
        if (TextUtils.isEmpty(text)) {
            textView.setVisibility(8);
            textView.setText("");
            return;
        }
        textView.setVisibility(0);
        textView.setText(text);
    }

    private String getVisibleFragment(Activity activity) {
        if (activity == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        if (!(activity instanceof AppCompatActivity)) {
            return getFragmentForActivity(activity);
        }
        List<Fragment> fragments = ((AppCompatActivity) activity).getSupportFragmentManager().getFragments();
        if (fragments == null || fragments.size() == 0) {
            return getFragmentForActivity(activity);
        }
        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isVisible()) {
                builder.append(fragment.getClass().getSimpleName() + "#" + fragment.getId());
                if (i < fragments.size() - 1) {
                    builder.append(Constants.PACKNAME_END);
                }
            }
        }
        return builder.toString();
    }

    private String getFragmentForActivity(Activity activity) {
        List<android.app.Fragment> list;
        StringBuilder builder = new StringBuilder();
        if (Build.VERSION.SDK_INT >= 26 && (list = activity.getFragmentManager().getFragments()) != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                android.app.Fragment fragment = list.get(i);
                if (fragment != null && fragment.isVisible()) {
                    builder.append(fragment.getClass().getSimpleName() + "#" + fragment.getId());
                    if (i < list.size() - 1) {
                        builder.append(Constants.PACKNAME_END);
                    }
                }
            }
        }
        return builder.toString();
    }
}
