package com.alibaba.android.arouter.routes;

import com.alibaba.android.arouter.facade.model.a;
import com.alibaba.android.arouter.facade.template.e;
import com.leedarson.newui.EasyLiveFragment;
import com.leedarson.newui.EventsActivity;
import com.leedarson.newui.IpcLiveActivity;
import com.leedarson.newui.IpcMainActivity;
import com.leedarson.newui.MainWebViewShowActivity;
import com.leedarson.newui.cloud_play_back.CloudPlayBackEventSignalActivity;
import com.leedarson.ui.AlbumPhotoActivity;
import com.leedarson.ui.CloudFragment;
import com.leedarson.ui.LiveFragment;
import com.leedarson.ui.SDPlayFragment;
import java.util.Map;

public class ARouter$$Group$$ipc implements e {
    public void loadInto(Map<String, a> atlas) {
        Map<String, a> map = atlas;
        com.alibaba.android.arouter.facade.enums.a aVar = com.alibaba.android.arouter.facade.enums.a.FRAGMENT;
        map.put("/ipc/cloud/", a.a(aVar, CloudFragment.class, "/ipc/cloud/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/easy_live/", a.a(aVar, EasyLiveFragment.class, "/ipc/easy_live/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        com.alibaba.android.arouter.facade.enums.a aVar2 = com.alibaba.android.arouter.facade.enums.a.ACTIVITY;
        map.put("/ipc/events/", a.a(aVar2, EventsActivity.class, "/ipc/events/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/live/", a.a(aVar, LiveFragment.class, "/ipc/live/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/live_ac/", a.a(aVar2, IpcLiveActivity.class, "/ipc/live_ac/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/main/", a.a(aVar2, IpcMainActivity.class, "/ipc/main/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/photoPreview/", a.a(aVar2, AlbumPhotoActivity.class, "/ipc/photopreview/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/sdcard/", a.a(aVar, SDPlayFragment.class, "/ipc/sdcard/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/showWebview/", a.a(aVar2, MainWebViewShowActivity.class, "/ipc/showwebview/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
        map.put("/ipc/signalevent/", a.a(aVar2, CloudPlayBackEventSignalActivity.class, "/ipc/signalevent/", "ipc", (Map<String, Integer>) null, -1, Integer.MIN_VALUE));
    }
}
