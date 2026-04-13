package com.didichuxing.doraemonkit.kit.network.ui;

import android.view.View;
import android.view.ViewGroup;
import androidx.viewpager.widget.PagerAdapter;
import com.didichuxing.doraemonkit.kit.network.bean.NetworkRecord;
import java.util.List;

public class NetworkPagerAdapter extends PagerAdapter {
    private NetworkRecord mRecord;
    private List<NetworkDetailView> views;

    public NetworkPagerAdapter(List<NetworkDetailView> views2, NetworkRecord record) {
        this.views = views2;
        this.mRecord = record;
    }

    public int getCount() {
        return this.views.size();
    }

    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        NetworkDetailView view = this.views.get(position);
        if (position == 0) {
            view.bindRequest(this.mRecord);
        } else {
            view.bindResponse(this.mRecord);
        }
        container.addView(view);
        return view;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(this.views.get(position));
    }
}
