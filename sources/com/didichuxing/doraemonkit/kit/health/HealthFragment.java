package com.didichuxing.doraemonkit.kit.health;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.kit.core.BaseFragment;
import com.didichuxing.doraemonkit.widget.titlebar.HomeTitleBar;
import com.didichuxing.doraemonkit.widget.verticalviewpager.VerticalViewPager;
import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends BaseFragment {
    FragmentPagerAdapter mFragmentPagerAdapter;
    List<Fragment> mFragments = new ArrayList();
    HomeTitleBar mHomeTitleBar;
    VerticalViewPager mVerticalViewPager;

    /* access modifiers changed from: protected */
    public int onRequestLayout() {
        return R.layout.dk_fragment_health;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            initView();
        }
    }

    private void initView() {
        this.mFragments.clear();
        this.mFragments.add(new HealthFragmentChild0());
        this.mFragments.add(new HealthFragmentChild1());
        HomeTitleBar homeTitleBar = (HomeTitleBar) findViewById(R.id.title_bar);
        this.mHomeTitleBar = homeTitleBar;
        homeTitleBar.setListener(new HomeTitleBar.OnTitleBarClickListener() {
            public void onRightClick() {
                HealthFragment.this.finish();
            }
        });
        this.mVerticalViewPager = (VerticalViewPager) findViewById(R.id.view_pager);
        AnonymousClass2 r0 = new FragmentPagerAdapter(getChildFragmentManager()) {
            public Fragment getItem(int position) {
                return HealthFragment.this.mFragments.get(position);
            }

            public int getCount() {
                return HealthFragment.this.mFragments.size();
            }
        };
        this.mFragmentPagerAdapter = r0;
        this.mVerticalViewPager.setAdapter(r0);
    }

    /* access modifiers changed from: protected */
    public void scroll2theTop() {
        VerticalViewPager verticalViewPager = this.mVerticalViewPager;
        if (verticalViewPager != null && this.mFragmentPagerAdapter != null) {
            verticalViewPager.setCurrentItem(0, true);
        }
    }
}
