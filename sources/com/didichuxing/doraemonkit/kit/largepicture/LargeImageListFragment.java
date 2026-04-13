package com.didichuxing.doraemonkit.kit.largepicture;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.config.PerformanceSpInfoConfig;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.widget.recyclerview.DividerItemDecoration;
import com.didichuxing.doraemonkit.widget.titlebar.TitleBar;
import java.util.ArrayList;
import java.util.List;

public class LargeImageListFragment extends BaseFragment {
    private static final String TAG = "LargeImageListFragment";
    private double fileThreshold = PerformanceSpInfoConfig.getLargeImgFileThreshold(LargePictureManager.FILE_DEFAULT_THRESHOLD);
    private RecyclerView mLargeImageList;
    private LargeImageListAdapter mLargeImageListAdapter;
    private TitleBar mTitleBar;
    private double memoryThreshold = PerformanceSpInfoConfig.getLargeImgMemoryThreshold(LargePictureManager.MEMORY_DEFAULT_THRESHOLD);

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_large_img_list;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        load();
    }

    private void initView() {
        this.mLargeImageList = (RecyclerView) findViewById(R.id.block_list);
        this.mLargeImageList.setLayoutManager(new LinearLayoutManager(getContext()));
        LargeImageListAdapter largeImageListAdapter = new LargeImageListAdapter(getContext());
        this.mLargeImageListAdapter = largeImageListAdapter;
        this.mLargeImageList.setAdapter(largeImageListAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(1);
        decoration.setDrawable(getResources().getDrawable(R.drawable.dk_divider));
        this.mLargeImageList.addItemDecoration(decoration);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        this.mTitleBar = titleBar;
        titleBar.setOnTitleBarClickListener(new TitleBar.OnTitleBarClickListener() {
            public void onLeftClick() {
                LargeImageListFragment.this.getActivity().onBackPressed();
            }

            public void onRightClick() {
            }
        });
    }

    private void load() {
        List<LargeImageInfo> imageInfos = new ArrayList<>();
        for (LargeImageInfo largeImageInfo : LargePictureManager.LARGE_IMAGE_INFO_MAP.values()) {
            if (largeImageInfo.getFileSize() >= this.fileThreshold || largeImageInfo.getMemorySize() >= this.memoryThreshold) {
                imageInfos.add(largeImageInfo);
            }
        }
        this.mLargeImageListAdapter.setData(imageInfos);
    }

    public void onDestroyView() {
        super.onDestroyView();
    }
}
