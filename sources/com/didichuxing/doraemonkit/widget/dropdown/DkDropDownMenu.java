package com.didichuxing.doraemonkit.widget.dropdown;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.didichuxing.doraemonkit.R;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class DkDropDownMenu extends LinearLayout {
    private static final String TAG = "DkDropDownMenu";
    private static int iconOrientation = 2;
    private FrameLayout containerView;
    private int current_tab_position;
    private int dividerColor;
    private float dividerHeight;
    List<View> dropTabViews;
    OnItemMenuClickListener itemMenuClickListener;
    private Orientation mOrientation;
    private int maskColor;
    private View maskView;
    private int menuSelectedIcon;
    private int menuTextSize;
    private int menuUnselectedIcon;
    private FrameLayout popupMenuViews;
    private LinearLayout tabMenuView;
    private int textSelectedColor;
    private int textUnselectedColor;

    public interface OnItemMenuClickListener {
        void OnItemMenuClick(TextView textView, int i);
    }

    public DkDropDownMenu(Context context) {
        super(context, (AttributeSet) null);
        this.dropTabViews = new ArrayList();
        this.current_tab_position = -1;
        this.dividerColor = -3355444;
        this.textSelectedColor = -7795579;
        this.textUnselectedColor = -15658735;
        this.maskColor = -2004318072;
        this.menuTextSize = 14;
    }

    public DkDropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DkDropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dropTabViews = new ArrayList();
        this.current_tab_position = -1;
        this.dividerColor = -3355444;
        this.textSelectedColor = -7795579;
        this.textUnselectedColor = -15658735;
        this.maskColor = -2004318072;
        this.menuTextSize = 14;
        setOrientation(1);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DkDropDownMenu);
        int underlineColor = a.getColor(R.styleable.DkDropDownMenu_dk_ddunderlineColor, -3355444);
        this.dividerColor = a.getColor(R.styleable.DkDropDownMenu_dk_dddividerColor, this.dividerColor);
        this.textSelectedColor = a.getColor(R.styleable.DkDropDownMenu_dk_ddtextSelectedColor, this.textSelectedColor);
        this.textUnselectedColor = a.getColor(R.styleable.DkDropDownMenu_dk_ddtextUnselectedColor, this.textUnselectedColor);
        int menuBackgroundColor = a.getColor(R.styleable.DkDropDownMenu_dk_ddmenuBackgroundColor, -1);
        this.maskColor = a.getColor(R.styleable.DkDropDownMenu_dk_ddmaskColor, this.maskColor);
        this.menuTextSize = a.getDimensionPixelSize(R.styleable.DkDropDownMenu_dk_ddmenuTextSize, this.menuTextSize);
        this.dividerHeight = (float) a.getDimensionPixelSize(R.styleable.DkDropDownMenu_dk_dddividerHeight, -1);
        this.menuSelectedIcon = a.getResourceId(R.styleable.DkDropDownMenu_dk_ddmenuSelectedIcon, this.menuSelectedIcon);
        this.menuUnselectedIcon = a.getResourceId(R.styleable.DkDropDownMenu_dk_ddmenuUnselectedIcon, this.menuUnselectedIcon);
        iconOrientation = a.getInt(R.styleable.DkDropDownMenu_dk_ddmenuIconOrientation, iconOrientation);
        a.recycle();
        Orientation orientation = new Orientation(getContext());
        this.mOrientation = orientation;
        orientation.init(iconOrientation, this.menuSelectedIcon, this.menuUnselectedIcon);
        this.tabMenuView = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        this.tabMenuView.setOrientation(0);
        this.tabMenuView.setBackgroundColor(menuBackgroundColor);
        this.tabMenuView.setLayoutParams(params);
        addView(this.tabMenuView, 0);
        View underLine = new View(getContext());
        underLine.setLayoutParams(new LinearLayout.LayoutParams(-1, dpTpPx(1.0f)));
        underLine.setBackgroundColor(underlineColor);
        addView(underLine, 1);
        FrameLayout frameLayout = new FrameLayout(context);
        this.containerView = frameLayout;
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.containerView, 2);
    }

    public void setDropDownMenu(@NonNull List<String> tabTexts, @NonNull List<View> popupViews, @NonNull View contentView) {
        if (tabTexts.size() == popupViews.size()) {
            for (int i = 0; i < tabTexts.size(); i++) {
                addTab(tabTexts, i);
            }
            this.containerView.addView(contentView, 0);
            View view = new View(getContext());
            this.maskView = view;
            view.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
            this.maskView.setBackgroundColor(this.maskColor);
            this.maskView.setOnClickListener(new View.OnClickListener() {
                @SensorsDataInstrumented
                public void onClick(View view) {
                    View view2 = view;
                    DkDropDownMenu.this.closeMenu();
                    SensorsDataAutoTrackHelper.trackViewOnClick(view);
                }
            });
            this.containerView.addView(this.maskView, 1);
            this.maskView.setVisibility(8);
            FrameLayout frameLayout = new FrameLayout(getContext());
            this.popupMenuViews = frameLayout;
            frameLayout.setVisibility(8);
            this.containerView.addView(this.popupMenuViews, 2);
            for (int i2 = 0; i2 < popupViews.size(); i2++) {
                if (popupViews.get(i2).getLayoutParams() == null) {
                    popupViews.get(i2).setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
                }
                this.popupMenuViews.addView(popupViews.get(i2), i2);
            }
            return;
        }
        throw new IllegalArgumentException("params not match, tabTexts.size() should be equal popupViews.size()");
    }

    public void addTab(View tab, int index) {
        if (index == (this.tabMenuView.getChildCount() + 1) / 2) {
            addTabEnd(tab);
            return;
        }
        this.tabMenuView.addView(tab, index * 2);
        this.tabMenuView.addView(getDividerView(), (index * 2) + 1);
    }

    public void addTabEnd(View tab) {
        this.tabMenuView.addView(getDividerView(), this.tabMenuView.getChildCount());
        LinearLayout linearLayout = this.tabMenuView;
        linearLayout.addView(tab, linearLayout.getChildCount());
    }

    private void addTab(@NonNull List<String> tabTexts, final int i) {
        View tab = LinearLayout.inflate(getContext(), R.layout.dk_dropdownmenu_tab_item, (ViewGroup) null);
        tab.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        final TextView textView = getTabTextView(tab);
        textView.setText(tabTexts.get(i));
        textView.setTextColor(this.textUnselectedColor);
        textView.setTextSize(0, (float) this.menuTextSize);
        setTextDrawables(textView, true);
        this.tabMenuView.addView(tab);
        tab.setOnClickListener(new View.OnClickListener() {
            @SensorsDataInstrumented
            public void onClick(View view) {
                View v = view;
                OnItemMenuClickListener onItemMenuClickListener = DkDropDownMenu.this.itemMenuClickListener;
                if (onItemMenuClickListener != null) {
                    onItemMenuClickListener.OnItemMenuClick(textView, i);
                }
                DkDropDownMenu.this.switchMenu(v);
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
            }
        });
        if (i < tabTexts.size() - 1) {
            this.tabMenuView.addView(getDividerView());
        }
        this.dropTabViews.add(tab);
    }

    private View getDividerView() {
        View view = new View(getContext());
        float f = this.dividerHeight;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpTpPx(0.5f), (int) (f > 0.0f ? (double) dpTpPx(f) : (double) f));
        params.gravity = 16;
        view.setLayoutParams(params);
        view.setBackgroundColor(this.dividerColor);
        return view;
    }

    private TextView getTabTextView(View tabView) {
        return (TextView) tabView.findViewById(R.id.tv_tab);
    }

    public void setTabText(String text) {
        int i = this.current_tab_position;
        if (i != -1) {
            getTabTextView(this.tabMenuView.getChildAt(i)).setText(text);
        }
    }

    public void resetTabText(String[] tabs) {
        int tvIndex;
        for (int index = 0; index < tabs.length; index++) {
            if (index == 0) {
                tvIndex = 0;
            } else {
                tvIndex = index + 1;
            }
            TextView tv2 = getTabTextView(this.tabMenuView.getChildAt(tvIndex));
            if (tv2 != null) {
                tv2.setText(tabs[index]);
            }
        }
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < this.tabMenuView.getChildCount(); i += 2) {
            this.tabMenuView.getChildAt(i).setClickable(clickable);
        }
    }

    public void closeMenu() {
        int i = this.current_tab_position;
        if (i != -1) {
            TextView textView = getTabTextView(this.tabMenuView.getChildAt(i));
            textView.setTextColor(this.textUnselectedColor);
            setTextDrawables(textView, true);
            this.popupMenuViews.setVisibility(8);
            this.popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dk_dd_menu_out));
            this.maskView.setVisibility(8);
            this.maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dk_dd_mask_out));
            this.current_tab_position = -1;
        }
    }

    public boolean isActive() {
        return this.current_tab_position != -1;
    }

    public void setTextDrawables(TextView textview, boolean close) {
        textview.setCompoundDrawablesWithIntrinsicBounds(this.mOrientation.getLeft(close), this.mOrientation.getTop(close), this.mOrientation.getRight(close), this.mOrientation.getBottom(close));
    }

    public boolean isShowing() {
        return this.current_tab_position != -1;
    }

    /* access modifiers changed from: private */
    public void switchMenu(View target) {
        for (int i = 0; i < this.tabMenuView.getChildCount(); i += 2) {
            if (target == this.tabMenuView.getChildAt(i)) {
                int i2 = this.current_tab_position;
                if (i2 == i) {
                    closeMenu();
                } else {
                    if (i2 == -1) {
                        this.popupMenuViews.setVisibility(0);
                        this.popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dk_dd_menu_in));
                        this.maskView.setVisibility(0);
                        this.maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dk_dd_mask_in));
                    }
                    View listView = getListView(this.tabMenuView.getChildAt(i));
                    if (listView != null) {
                        listView.setVisibility(0);
                    }
                    this.current_tab_position = i;
                    TextView textView = getTabTextView(this.tabMenuView.getChildAt(i));
                    textView.setTextColor(this.textSelectedColor);
                    setTextDrawables(textView, false);
                }
            } else {
                TextView textView2 = getTabTextView(this.tabMenuView.getChildAt(i));
                if (textView2 != null) {
                    textView2.setTextColor(this.textUnselectedColor);
                }
                View listView2 = getListView(this.tabMenuView.getChildAt(i));
                if (listView2 != null) {
                    if (textView2 != null) {
                        setTextDrawables(textView2, true);
                    }
                    listView2.setVisibility(8);
                }
            }
        }
    }

    public void setOnItemMenuClickListener(OnItemMenuClickListener listener) {
        this.itemMenuClickListener = listener;
    }

    private View getListView(View view) {
        if (!this.dropTabViews.contains(view)) {
            return null;
        }
        return this.popupMenuViews.getChildAt(this.dropTabViews.indexOf(view));
    }

    public int dpTpPx(float value) {
        return (int) (((double) TypedValue.applyDimension(1, value, getResources().getDisplayMetrics())) + 0.5d);
    }
}
