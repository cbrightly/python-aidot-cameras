package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class n4 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ n4 a = new n4();

    private /* synthetic */ n4() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupsCluster) baseChipCluster).removeGroup((ChipClusters.GroupsCluster.RemoveGroupResponseCallback) obj, (Integer) map.get("groupId"));
    }
}
