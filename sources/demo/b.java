package demo;

import android.view.View;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;

/* compiled from: lambda */
public final /* synthetic */ class b implements View.OnClickListener {
    public final /* synthetic */ DevListAdapter c;
    public final /* synthetic */ MatterDeviceBean d;

    public /* synthetic */ b(DevListAdapter devListAdapter, MatterDeviceBean matterDeviceBean) {
        this.c = devListAdapter;
        this.d = matterDeviceBean;
    }

    public final void onClick(View view) {
        DevListAdapter.z(this.c, this.d, view);
    }
}
