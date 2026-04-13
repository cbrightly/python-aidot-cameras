package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import chip.devicecontroller.ChipStructs;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class r1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ r1 a = new r1();

    private /* synthetic */ r1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupKeyManagementCluster) baseChipCluster).keySetWrite((ChipClusters.DefaultClusterCallback) obj, (ChipStructs.GroupKeyManagementClusterGroupKeySetStruct) map.get("groupKeySet"));
    }
}
