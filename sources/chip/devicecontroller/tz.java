package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class tz implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ tz a = new tz();

    private /* synthetic */ tz() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupKeyManagementCluster) baseChipCluster).readMaxGroupKeysPerFabricAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
