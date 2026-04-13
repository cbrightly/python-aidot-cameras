package meshsdk.ctrl;

import com.telink.ble.mesh.core.message.MeshSigModel;
import com.telink.ble.mesh.core.message.config.ConfigStatus;
import com.telink.ble.mesh.core.message.config.ModelPublicationSetMessage;
import com.telink.ble.mesh.core.message.config.ModelPublicationStatusMessage;
import com.telink.ble.mesh.entity.ModelPublication;
import com.telink.ble.mesh.foundation.Event;
import com.telink.ble.mesh.foundation.EventListener;
import com.telink.ble.mesh.foundation.event.StatusNotificationEvent;
import java.util.ArrayList;
import java.util.List;
import meshsdk.MeshEventHandler;
import meshsdk.MeshLog;
import meshsdk.SIGMesh;
import meshsdk.model.NodeInfo;
import meshsdk.model.PublishModel;

public class PublishCtrl extends CtrlLifecycle implements EventListener<String> {
    private static final int PUB_ADDRESS = 65535;
    private static final int PUB_INTERVAL = 20000;
    private NodeInfo currentNode;
    private PublishModel pubModel;

    public PublishCtrl(SIGMesh sigMesh) {
        super(sigMesh);
        onCreate();
    }

    public void onCreate() {
        MeshEventHandler.getInstance().addEventListener(ModelPublicationStatusMessage.class.getName(), this);
    }

    public List<PublishModel> getAllPubModel(NodeInfo deviceInfo, int pubTargetAddr) {
        PublishModel pubModel2;
        int address = pubTargetAddr;
        ArrayList<PublishModel> publishModels = new ArrayList<>();
        for (int modelId : new int[]{MeshSigModel.SIG_MD_G_ONOFF_S.modelId, MeshSigModel.SIG_MD_LIGHT_CTL_S.modelId, MeshSigModel.SIG_MD_LIGHT_HSL_S.modelId, MeshSigModel.SIG_MD_LIGHTNESS_S.modelId}) {
            int pubEleAdr = deviceInfo.getTargetEleAdr(modelId);
            if (pubEleAdr != -1) {
                if (modelId == MeshSigModel.SIG_MD_G_ONOFF_S.modelId) {
                    pubModel2 = new PublishModel(pubEleAdr, modelId, address, 20000);
                } else {
                    pubModel2 = new PublishModel(pubEleAdr, modelId, address, 20000);
                }
                publishModels.add(pubModel2);
            }
        }
        return publishModels;
    }

    public PublishModel getPriorityPubModel(NodeInfo deviceInfo) {
        int modelId = MeshSigModel.SIG_MD_G_ONOFF_S.modelId;
        int pubEleAdr = deviceInfo.getTargetEleAdr(modelId);
        if (pubEleAdr != -1) {
            return new PublishModel(pubEleAdr, modelId, 65535, 20000);
        }
        int modelId2 = MeshSigModel.SIG_MD_LIGHT_CTL_S.modelId;
        int pubEleAdr2 = deviceInfo.getTargetEleAdr(modelId2);
        if (pubEleAdr2 != -1) {
            return new PublishModel(pubEleAdr2, modelId2, 65535, 20000);
        }
        int modelId3 = MeshSigModel.SIG_MD_LIGHT_HSL_S.modelId;
        int pubEleAdr3 = deviceInfo.getTargetEleAdr(modelId3);
        if (pubEleAdr3 != -1) {
            return new PublishModel(pubEleAdr3, modelId3, 65535, 20000);
        }
        int modelId4 = MeshSigModel.SIG_MD_LIGHTNESS_S.modelId;
        int pubEleAdr4 = deviceInfo.getTargetEleAdr(modelId4);
        if (pubEleAdr4 != -1) {
            return new PublishModel(pubEleAdr4, modelId4, 65535, 20000);
        }
        return null;
    }

    public synchronized void setPublish(NodeInfo deviceInfo, PublishModel pubModel2, boolean ack) {
        int pubAdr;
        this.currentNode = deviceInfo;
        this.pubModel = pubModel2;
        if (pubModel2 != null) {
            pubAdr = pubModel2.address;
        } else {
            pubAdr = 0;
        }
        ModelPublicationSetMessage publicationSetMessage = new ModelPublicationSetMessage(deviceInfo.meshAddress, ModelPublication.createDefault(deviceInfo.getTargetEleAdr(pubModel2.modelId), pubAdr, SIGMesh.getInstance().getMeshInfo().getDefaultAppKeyIndex(), (long) pubModel2.period, pubModel2.modelId, true));
        if (!ack) {
            publicationSetMessage.D(-1);
        }
        MeshMessagePool.getInstance().addAndSend(publicationSetMessage);
    }

    public void performed(Event<String> event) {
        if (event.getType().equals(ModelPublicationStatusMessage.class.getName())) {
            ModelPublicationStatusMessage statusMessage = (ModelPublicationStatusMessage) ((StatusNotificationEvent) event).a().d();
            if (statusMessage.d() == ConfigStatus.SUCCESS.code) {
                MeshLog.debugInfo("publication success: elementAddress:" + statusMessage.c().elementAddress + ",publishAddress:" + statusMessage.c().publishAddress);
                boolean settle = statusMessage.c().publishAddress != 0;
                if (this.currentNode != null && this.pubModel != null) {
                    MeshLog.debugInfo("start offline detect currentNode:" + this.currentNode.macAddress);
                    this.currentNode.setPublishModel(settle ? this.pubModel : null);
                    SIGMesh.getInstance().getMeshInfo().saveOrUpdate(SIGMesh.getInstance().getContext(), "PublishCtrl.java.performed ModelPublicationStatusMessage");
                    MeshEventHandler.getInstance().removeEventListener(this);
                    return;
                }
                return;
            }
            MeshLog.e("publication fail: elementAddress:" + statusMessage.c().elementAddress + ",publishAddress:" + statusMessage.c().publishAddress);
        }
    }
}
