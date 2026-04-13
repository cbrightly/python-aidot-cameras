package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class z8 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ z8 a = new z8();

    private /* synthetic */ z8() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupsCluster) baseChipCluster).addGroupIfIdentifying((ChipClusters.DefaultClusterCallback) obj, (Integer) map.get("groupId"), (String) map.get("groupName"));
    }
}
