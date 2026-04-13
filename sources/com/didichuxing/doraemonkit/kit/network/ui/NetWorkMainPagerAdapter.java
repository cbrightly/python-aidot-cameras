package com.didichuxing.doraemonkit.kit.network.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import com.didichuxing.doraemonkit.R;
import java.util.ArrayList;
import java.util.List;

public class NetWorkMainPagerAdapter extends PagerAdapter {
    private List<String> tabs;
    private List<View> views;

    public NetWorkMainPagerAdapter(Context context, List<View> views2) {
        ArrayList arrayList = new ArrayList();
        this.tabs = arrayList;
        this.views = views2;
        arrayList.add(context.getString(R.string.dk_net_monitor_title_summary));
        this.tabs.add(context.getString(R.string.dk_net_monitor_list));
    }

    @Nullable
    public CharSequence getPageTitle(int position) {
        return this.tabs.get(position);
    }

    public int getCount() {
        return this.views.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        View view = this.views.get(position);
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.views.get(position));
    }
}
