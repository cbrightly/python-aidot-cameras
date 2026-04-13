package com.didichuxing.doraemonkit.widget.easyrefresh.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.didichuxing.doraemonkit.R;
import com.didichuxing.doraemonkit.widget.easyrefresh.ILoadMoreView;
import com.github.ybq.android.spinkit.SpinKitView;

public class SimpleLoadMoreView extends FrameLayout implements ILoadMoreView {
    private SpinKitView spinKitView;
    private TextView tvHitText;
    private View view;

    public SimpleLoadMoreView(Context context) {
        this(context, (AttributeSet) null);
    }

    public SimpleLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View inflate = FrameLayout.inflate(context, R.layout.dk_refresh_default_load_more, this);
        this.view = inflate;
        this.tvHitText = (TextView) inflate.findViewById(R.id.tv_hit_content);
        this.spinKitView = (SpinKitView) this.view.findViewById(R.id.spin_kit);
    }

    public void reset() {
        this.spinKitView.setVisibility(4);
        this.tvHitText.setVisibility(4);
        this.tvHitText.setText("正在加载...");
    }

    public void loading() {
        this.spinKitView.setVisibility(0);
        this.tvHitText.setVisibility(0);
        this.tvHitText.setText("正在加载...");
    }

    public void loadComplete() {
        this.spinKitView.setVisibility(4);
        this.tvHitText.setVisibility(0);
        this.tvHitText.setText("加载完成");
    }

    public void loadFail() {
        this.spinKitView.setVisibility(4);
        this.tvHitText.setVisibility(0);
        this.tvHitText.setText("加载失败,点击重新加载");
    }

    public void loadNothing() {
        this.spinKitView.setVisibility(4);
        this.tvHitText.setVisibility(0);
        this.tvHitText.setText("没有更多可以加载");
    }

    public View getCanClickFailView() {
        return this.view;
    }
}
