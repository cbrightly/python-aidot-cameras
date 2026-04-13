package com.leedarson.smartcamera.kvswebrtc.signaling.model;

import com.amazonaws.services.kinesisvideo.model.ResourceEndpointListItem;
import com.amazonaws.services.kinesisvideosignaling.model.IceServer;
import com.meituan.robust.ChangeQuickRedirect;
import com.meituan.robust.PatchProxy;
import com.meituan.robust.PatchProxyResult;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.webrtc.PeerConnection;

public class KVSICEConfigCacheBean {
    public static ChangeQuickRedirect changeQuickRedirect;
    public String dataEndpoint = null;
    public boolean enableUse = true;
    public long expireTime = -1;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public List<ResourceEndpointListItem> mEndpointList = new ArrayList();
    public List<IceServer> mIceServerList = new ArrayList();
    public String mWssEndpoint;
    public List<PeerConnection.IceServer> peerIceServers = new ArrayList();
    public long updateTime = -1;
    public String wsHost = "";

    public boolean isContainExpiredIceServerTtl() {
        PatchProxyResult proxy = PatchProxy.proxy(new Object[0], this, changeQuickRedirect, false, 9999, new Class[0], Boolean.TYPE);
        if (proxy.isSupported) {
            return ((Boolean) proxy.result).booleanValue();
        }
        List<IceServer> list = this.mIceServerList;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < this.mIceServerList.size(); i++) {
                if (this.updateTime + ((long) (this.mIceServerList.get(i).getTtl().intValue() * 1000)) < System.currentTimeMillis() - 60000) {
                    return true;
                }
            }
        }
        return false;
    }
}
