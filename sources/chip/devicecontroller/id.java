package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class id implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ id a = new id();

    private /* synthetic */ id() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupsCluster) baseChipCluster).addGroup((ChipClusters.GroupsCluster.AddGroupResponseCallback) obj, (Integer) map.get("groupId"), (String) map.get("groupName"));
    }
}
