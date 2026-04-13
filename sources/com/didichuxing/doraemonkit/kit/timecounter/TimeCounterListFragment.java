package com.didichuxing.doraemonkit.kit.timecounter;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.kit.timecounter.bean.CounterInfo;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TimeCounterListFragment extends BaseFragment {
    private static final String TAG = "TimeCounterListFragment";
    private TimeCounterListAdapter mAdapter;
    private RecyclerView mBlockList;
    private TitleBar mTitleBar;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_time_counter_list;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        load();
    }

    private void initView() {
        this.mBlockList = (RecyclerView) findViewById(R.id.block_list);
        this.mBlockList.setLayoutManager(new LinearLayoutManager(getContext()));
        TimeCounterListAdapter timeCounterListAdapter = new TimeCounterListAdapter(getContext());
        this.mAdapter = timeCounterListAdapter;
        this.mBlockList.setAdapter(timeCounterListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mBlockList.addItemDecoration(decoration);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = titleBar;
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                TimeCounterListFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
    }

    private void load() {
        List<CounterInfo> infos = new ArrayList<>(TimeCounterManager.get().getHistory());
        infos.add(0, TimeCounterManager.get().getAppSetupInfo());
        Collections.sort(infos, new Comparator<CounterInfo>() {
            public int compare(CounterInfo lhs, CounterInfo rhs) {
                return Long.valueOf(rhs.time).compareTo(Long.valueOf(lhs.time));
            }
        });
        this.mAdapter.setData(infos);
    }
}
