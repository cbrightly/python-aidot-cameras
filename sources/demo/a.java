package demo;

import android.widget.CompoundButton;
import com.leedarson.serviceimpl.bean.MatterDeviceBean;

/* compiled from: lambda */
public final /* synthetic */ class a implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ DevListAdapter a;
    public final /* synthetic */ MatterDeviceBean b;

    public /* synthetic */ a(DevListAdapter devListAdapter, MatterDeviceBean matterDeviceBean) {
        this.a = devListAdapter;
        this.b = matterDeviceBean;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        DevListAdapter.y(this.a, this.b, compoundButton, z);
    }
}
