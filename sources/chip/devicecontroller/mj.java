package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class mj implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ mj a = new mj();

    private /* synthetic */ mj() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BridgedDeviceBasicCluster) baseChipCluster).readUniqueIDAttribute((ChipClusters.CharStringAttributeCallback) obj);
    }
}
