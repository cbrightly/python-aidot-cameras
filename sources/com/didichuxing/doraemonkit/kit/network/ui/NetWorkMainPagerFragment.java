package com.didichuxing.doraemonkit.kit.network.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class NetWorkMainPagerFragment extends BaseFragment implements View.OnClickListener {
    private NetworkListView mNetworkListView;
    private NetWorkSummaryView mSummaryView;
    private ViewPager mViewPager;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_net_main_pager;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                NetWorkMainPagerFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
        this.mViewPager = (ViewPager) findViewById(R.id.vp_show);
        this.mSummaryView = new NetWorkSummaryView(getContext());
        NetworkListView networkListView = new NetworkListView(getContext());
        this.mNetworkListView = networkListView;
        networkListView.registerNetworkListener();
        List<View> views = new ArrayList<>();
        views.add(this.mSummaryView);
        views.add(this.mNetworkListView);
        this.mViewPager.setAdapter(new NetWorkMainPagerAdapter(getContext(), views));
        final View tabSummary = findViewById(R.id.tab_summary);
        int i = R.id.tab_text;
        ((TextView) tabSummary.findViewById(i)).setText(R.string.dk_net_monitor_title_summary);
        int i2 = R.id.tab_icon;
        ((ImageView) tabSummary.findViewById(i2)).setImageResource(R.drawable.dk_net_work_monitor_summary_selector);
        tabSummary.setSelected(true);
        tabSummary.setOnClickListener(this);
        final View tabList = findViewById(R.id.tab_list);
        ((TextView) tabList.findViewById(i)).setText(R.string.dk_net_monitor_list);
        ((ImageView) tabList.findViewById(i2)).setImageResource(R.drawable.dk_net_work_monitor_list_selector);
        tabList.setOnClickListener(this);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    tabSummary.setSelected(true);
                    tabList.setSelected(false);
                    return;
                }
                tabList.setSelected(true);
                tabSummary.setSelected(false);
            }

            public void onPageSelected(int position) {
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tab_summary) {
            this.mViewPager.setCurrentItem(0, true);
        } else if (id == R.id.tab_list) {
            this.mViewPager.setCurrentItem(1, true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    public void onDestroyView() {
        super.onDestroyView();
        NetworkListView networkListView = this.mNetworkListView;
        if (networkListView != null) {
            networkListView.unRegisterNetworkListener();
        }
    }
}
