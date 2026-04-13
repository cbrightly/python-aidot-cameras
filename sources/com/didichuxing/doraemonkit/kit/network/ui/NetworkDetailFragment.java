package com.didichuxing.doraemonkit.kit.network.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;

public class NetworkDetailFragment extends BaseFragment implements View.OnClickListener {
    private static final String TAG = "NetworkDetailFragment";
    /* access modifiers changed from: private */
    public View mDiverRequest;
    /* access modifiers changed from: private */
    public View mDiverResponse;
    /* access modifiers changed from: private */
    public TextView mTvRequest;
    /* access modifiers changed from: private */
    public TextView mTvResponse;
    private ViewPager mViewPager;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_network_monitor_detail;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        this.mViewPager = (ViewPager) findViewById(R.id.network_viewpager);
        this.mDiverRequest = findViewById(R.id.diver_request);
        this.mDiverResponse = findViewById(R.id.diver_response);
        this.mTvRequest = (TextView) findViewById(R.id.tv_pager_request);
        this.mTvResponse = (TextView) findViewById(R.id.tv_pager_response);
        this.mTvRequest.setSelected(true);
        this.mTvResponse.setSelected(false);
        this.mTvRequest.setOnClickListener(this);
        this.mTvResponse.setOnClickListener(this);
        List<NetworkDetailView> views = new ArrayList<>();
        views.add(new NetworkDetailView(getContext()));
        views.add(new NetworkDetailView(getContext()));
        this.mViewPager.setAdapter(new NetworkPagerAdapter(views, (NetworkRecord) getArguments().getSerializable(NetworkListView.KEY_RECORD)));
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                int i = 8;
                boolean z = false;
                NetworkDetailFragment.this.mDiverRequest.setVisibility(position == 0 ? 0 : 8);
                View access$100 = NetworkDetailFragment.this.mDiverResponse;
                if (position == 1) {
                    i = 0;
                }
                access$100.setVisibility(i);
                NetworkDetailFragment.this.mTvRequest.setSelected(position == 0);
                TextView access$300 = NetworkDetailFragment.this.mTvResponse;
                if (position == 1) {
                    z = true;
                }
                access$300.setSelected(z);
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        ((TitleBar) findViewById(R.id.title_bar)).setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                NetworkDetailFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
    }

    public boolean onBackPressed() {
        return super.onBackPressed();
    }

    public void onResume() {
        super.onResume();
    }

    @SensorsDataInstrumented
    public void onClick(View view) {
        View v = view;
        if (v.getId() == R.id.tv_pager_request) {
            this.mViewPager.setCurrentItem(0, true);
        } else if (v.getId() == R.id.tv_pager_response) {
            this.mViewPager.setCurrentItem(1, true);
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(view);
    }
}
