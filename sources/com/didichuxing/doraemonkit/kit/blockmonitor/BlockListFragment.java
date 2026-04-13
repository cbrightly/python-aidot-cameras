package com.didichuxing.doraemonkit.kit.blockmonitor;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.blockmonitor.BlockListAdapter;
import com.didichuxing.doraemonkit.kit.blockmonitor.bean.BlockInfo;
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager;
import com.didichuxing.doraemonkit.kit.blockmonitor.core.OnBlockInfoUpdateListener;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.util.LogHelper;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BlockListFragment extends BaseFragment implements OnBlockInfoUpdateListener {
    private static final String TAG = "BlockMonitorIndexFragment";
    /* access modifiers changed from: private */
    public RecyclerView mBlockList;
    private BlockListAdapter mBlockListAdapter;
    /* access modifiers changed from: private */
    public TextView mLogDetail;
    /* access modifiers changed from: private */
    public TitleBar mTitleBar;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_block_list;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        load();
        BlockMonitorManager.getInstance().setOnBlockInfoUpdateListener(this);
    }

    private void initView() {
        this.mBlockList = (RecyclerView) findViewById(R.id.block_list);
        this.mLogDetail = (TextView) findViewById(R.id.tx_block_detail);
        this.mBlockList.setLayoutManager(new LinearLayoutManager(getContext()));
        BlockListAdapter blockListAdapter = new BlockListAdapter(getContext());
        this.mBlockListAdapter = blockListAdapter;
        this.mBlockList.setAdapter(blockListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mBlockList.addItemDecoration(decoration);
        this.mBlockListAdapter.setOnItemClickListener(new BlockListAdapter.OnItemClickListener() {
            public void onClick(BlockInfo info) {
                LogHelper.i(BlockListFragment.TAG, info.toString());
                BlockListFragment.this.mLogDetail.setText(info.toString());
                BlockListFragment.this.mLogDetail.setVisibility(0);
                BlockListFragment.this.mBlockList.setVisibility(8);
                BlockListFragment.this.mTitleBar.setTitle(BlockListFragment.this.getResources().getString(R.string.dk_kit_block_monitor_detail), false);
            }
        });
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = titleBar;
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                BlockListFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
    }

    public boolean onBackPressed() {
        if (this.mLogDetail.getVisibility() != 0) {
            return super.onBackPressed();
        }
        this.mLogDetail.setVisibility(8);
        this.mBlockList.setVisibility(0);
        this.mTitleBar.setTitle(R.string.dk_kit_block_monitor_list);
        return true;
    }

    private void load() {
        List<BlockInfo> infos = new ArrayList<>(BlockMonitorManager.getInstance().getBlockInfoList());
        Collections.sort(infos, new Comparator<BlockInfo>() {
            public int compare(BlockInfo lhs, BlockInfo rhs) {
                return Long.valueOf(rhs.time).compareTo(Long.valueOf(lhs.time));
            }
        });
        this.mBlockListAdapter.setData(infos);
    }

    public void onDestroyView() {
        super.onDestroyView();
        BlockMonitorManager.getInstance().setOnBlockInfoUpdateListener((OnBlockInfoUpdateListener) null);
    }

    public void onBlockInfoUpdate(BlockInfo blockInfo) {
        this.mBlockListAdapter.append(blockInfo, 0);
    }
}
