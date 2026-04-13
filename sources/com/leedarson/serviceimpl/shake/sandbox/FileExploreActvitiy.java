package com.leedarson.serviceimpl.shake.sandbox;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.leedarson.base.ui.BaseActivity;
import com.leedarson.serviceimpl.shake.R$id;
import com.leedarson.serviceimpl.shake.R$layout;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import java.io.File;
import java.util.ArrayList;

public class FileExploreActvitiy extends BaseActivity implements AdapterView.OnItemClickListener {
    public static ChangeQuickRedirect changeQuickRedirect;
    private ArrayList<File> a1 = new ArrayList<>();
    private TextView a2;
    private ListView p0;
    private a p1;
    private File p2;
    private File p3;

    static /* synthetic */ void X(FileExploreActvitiy x0) {
        if (!PatchProxy.proxy(new Object[]{x0}, (Object) null, changeQuickRedirect, true, 8760, new Class[]{FileExploreActvitiy.class}, Void.TYPE).isSupported) {
            x0.Z();
        }
    }

    public int O() {
        return R$layout.activity_file_explore;
    }

    public void init() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8755, new Class[0], Void.TYPE).isSupported) {
            this.p0 = (ListView) findViewById(R$id.listView);
            a aVar = new a(this, this.a1);
            this.p1 = aVar;
            this.p0.setAdapter(aVar);
            findViewById(R$id.btn_back).setOnClickListener(new a());
            this.p0.setOnItemClickListener(this);
            TextView textView = (TextView) findViewById(R$id.tv_top_dir);
            this.a2 = textView;
            textView.setOnClickListener(new b());
            File filesDir = getFilesDir();
            this.p2 = filesDir;
            Y(filesDir.getAbsolutePath());
        }
    }

    public class a implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        a() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8761, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            FileExploreActvitiy.this.finish();
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class b implements View.OnClickListener {
        public static ChangeQuickRedirect changeQuickRedirect;

        b() {
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            if (PatchProxy.proxy(new Object[]{view}, this, changeQuickRedirect, false, 8762, new Class[]{View.class}, Void.TYPE).isSupported) {
                SensorsDataAutoTrackHelper.trackViewOnClick(view);
                return;
            }
            View view2 = view;
            FileExploreActvitiy.X(FileExploreActvitiy.this);
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public void R() {
    }

    public void onBackPressed() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8756, new Class[0], Void.TYPE).isSupported) {
            if (this.p3.getAbsolutePath().equals(this.p2.getAbsolutePath())) {
                super.onBackPressed();
            } else {
                Z();
            }
        }
    }

    private void Z() {
        if (!PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 8757, new Class[0], Void.TYPE).isSupported) {
            String path = this.p3.getAbsolutePath();
            Y(path.substring(0, path.lastIndexOf("/")));
        }
    }

    private void Y(String path) {
        if (!PatchProxy.proxy(new Object[]{path}, this, changeQuickRedirect, false, 8758, new Class[]{String.class}, Void.TYPE).isSupported) {
            Log.d("CZB", " getData:" + path);
            File dir = new File(path);
            this.p3 = dir;
            this.a2.setVisibility(path.equals(this.p2.getAbsolutePath()) ? 8 : 0);
            File[] files = dir.listFiles();
            this.a1.clear();
            for (File f : files) {
                this.a1.add(f);
            }
            this.p1.notifyDataSetChanged();
        }
    }

    @SensorsDataInstrumented
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long j) {
        Object[] objArr = {adapterView, view, new Integer(position), new Long(j)};
        ChangeQuickRedirect changeQuickRedirect2 = changeQuickRedirect;
        if (PatchProxy.proxy(objArr, this, changeQuickRedirect2, false, 8759, new Class[]{AdapterView.class, View.class, Integer.TYPE, Long.TYPE}, Void.TYPE).isSupported) {
            SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
            return;
        }
        View view2 = view;
        AdapterView<?> adapterView2 = adapterView;
        File item = this.a1.get(position);
        if (item.isDirectory()) {
            Y(item.getAbsolutePath());
        } else {
            Intent intent = new Intent(this, FileContentActivity.class);
            intent.putExtra("path", item.getAbsolutePath());
            startActivity(intent);
        }
        SensorsDataAutoTrackHelper.trackListView(adapterView, view, position);
    }
}
