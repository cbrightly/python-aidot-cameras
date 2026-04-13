package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.ArrayList;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class j6 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ j6 a = new j6();

    private /* synthetic */ j6() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GroupsCluster) baseChipCluster).getGroupMembership((ChipClusters.GroupsCluster.GetGroupMembershipResponseCallback) obj, (ArrayList) map.get("groupList"));
    }
}
