package meshsdk.util;

import com.leedarson.serviceimpl.elkstrays.b;
import com.telink.ble.mesh.foundation.MeshService;
import java.util.List;
import meshsdk.MeshLog;
import meshsdk.MeshLogNew;
import meshsdk.SIGMesh;
import meshsdk.callback.MeshUnbindCallback;
import meshsdk.callback.OnHttpCallback;
import meshsdk.datamgr.LDSDeviceApi;
import meshsdk.model.json.CloudDevice;
import meshsdk.sql.SqlManager;
import org.json.JSONArray;
import org.json.JSONException;

public class RemoveNodeTask implements Runnable {
    private LDSDeviceApi ldsDeviceApi = new LDSDeviceApi();
    /* access modifiers changed from: private */
    public String mac;
    private JSONArray nodeArr;

    public RemoveNodeTask(String mac2) {
        this.mac = mac2;
    }

    public void run() {
        this.nodeArr = SqlManager.getDelNodes();
        MeshLog.i("******* remove cache nodes task start ******* nodeArr =" + this.nodeArr);
        JSONArray jSONArray = this.nodeArr;
        if (jSONArray != null && jSONArray.length() > 0) {
            int i = 0;
            while (i < this.nodeArr.length()) {
                try {
                    if (this.mac.equals(this.nodeArr.get(i).toString())) {
                        filter();
                        return;
                    }
                    i++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void filter() {
        this.ldsDeviceApi.getCloudDevices(SharedPreferenceHelper.getHouseId(SIGMesh.getInstance().getContext()), new OnHttpCallback<List<CloudDevice>>() {
            public void onResult(List<CloudDevice> devices) {
                boolean exist = false;
                String tip = "云端设备列表不存在该MAC:" + RemoveNodeTask.this.mac;
                if (devices != null && devices.size() > 0) {
                    for (CloudDevice device : devices) {
                        if (RemoveNodeTask.this.mac.equals(device.mac)) {
                            tip = "云端存在该MAC，不执行补发删除:" + RemoveNodeTask.this.mac;
                            exist = true;
                        }
                    }
                }
                MeshLog.i(tip);
                if (devices != null && !exist) {
                    MeshLog.i("云端列表不存在该MAC，删除之前离线未删除的节点:" + RemoveNodeTask.this.mac);
                    RemoveNodeTask.this.removeNode();
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void removeNode() {
        MeshLogNew.meshJsonLog(" 补删mesh 节点 =" + this.mac);
        b.a(this.mac + " 上线成功，补删该节点");
        SIGMesh.getInstance().removeDevice(this.mac, true, new MeshUnbindCallback() {
            public void onUnBindSuccess(String mac, int meshAddr, boolean realSuccess) {
                MeshLog.i("******* remove cache nodes task ******* mac =" + mac);
                if (meshAddr == MeshService.k().i()) {
                    MeshLog.i("移除直连设备！！");
                }
                if (SIGMesh.getInstance().getMeshInfo().removeDeviceByMac(mac)) {
                    SIGMesh.getInstance().getMeshInfo().saveOrUpdate(SIGMesh.getInstance().getContext(), "RemoveNodeTask#removeNode");
                    SharedPreferenceHelper.setNeedUpload(SIGMesh.getInstance().getContext(), true);
                }
                SqlManager.removeDelCacheNode(mac);
            }

            public void onUnBindFail(int code, String msg) {
                MeshLog.i("******* remove cache nodes task fail ******* mac =" + RemoveNodeTask.this.mac + ",msg:" + msg);
            }
        });
    }
}
