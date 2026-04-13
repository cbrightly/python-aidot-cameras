package com.leedarson.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.leedarson.R$id;
import com.leedarson.R$layout;
import com.leedarson.R$string;
import com.leedarson.adapter.PhotoRecyclerAdapter;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceinterface.event.PushCloseIpcActivityEvent;
import com.leedarson.serviceinterface.utils.PubUtils;
import com.leedarson.serviceinterface.utils.StatusBarUtil;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.c;
import org.greenrobot.eventbus.l;

public class AlbumPhotoActivity extends BaseActivity implements View.OnClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ImageView a1;
    private List<String> a2 = new ArrayList();
    private RelativeLayout p0;
    private ViewPager2 p1;
    private TextView p2;

    public void onCreate(Bundle savedInstanceState) {
        if (!PatchProxy.proxy(new Object[]{savedInstanceState}, this, changeQuickRedirect, false, 10813, new Class[]{Bundle.class}, Void.TYPE).isSupported) {
            super.onCreate(savedInstanceState);
            setTitle("IPC Album Photo View");
            StatusBarUtil.setStatusBarFullTransparent(this);
            StatusBarUtil.setLightMode(this);
            getWindow().clearFlags(1024);
            this.p0 = (RelativeLayout) findViewById(R$id.album_layout_title);
            TextView textView = (TextView) findViewById(R$id.tv_title);
            this.p2 = textView;
            textView.setText(PubUtils.getString(this, R$string.photos));
            if (!c.c().j(this)) {
                c.c().p(this);
            }
            init();
        }
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10814, new Class[0], Void.TYPE).isSupported) {
            if (getIntent() == null) {
                finish();
            }
            int position = getIntent().getIntExtra("position", 0);
            ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("datas");
            this.a2 = stringArrayListExtra;
            if (stringArrayListExtra == null || stringArrayListExtra.size() == 0) {
                finish();
            }
            ImageView imageView = (ImageView) findViewById(R$id.btn_back);
            this.a1 = imageView;
            imageView.setOnClickListener(this);
            this.p1 = (ViewPager2) findViewById(R$id.photo_viewpage);
            this.p1.setAdapter(new PhotoRecyclerAdapter(this, this.a2));
            this.p1.setCurrentItem(position, false);
        }
    }

    public void R() {
    }

    public void onDestroy() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 10815, new Class[0], Void.TYPE).isSupported) {
            super.onDestroy();
            c.c().r(this);
        }
    }

    public int O() {
        return R$layout.activity_album_photo;
    }

    @SensorsDataInstrumented
    public void onClick(View v) {
        if (PatchProxy.proxy(new Object[]{v}, this, changeQuickRedirect, false, 10816, new Class[]{View.class}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackViewOnClick(v);
            return;
        }
        if (v.getId() == R$id.btn_back) {
            finish();
        }
        SensorsDataAutoTrackHelper.trackViewOnClick(v);
    }

    @l(threadMode = ThreadMode.MAIN)
    public void onPushCloseEvent(PushCloseIpcActivityEvent pushCloseIpcActivityEvent) {
        if (!PatchProxy.proxy(new Object[]{pushCloseIpcActivityEvent}, this, changeQuickRedirect, false, 10817, new Class[]{PushCloseIpcActivityEvent.class}, Void.TYPE).isSupported) {
            finish();
        }
    }
}
