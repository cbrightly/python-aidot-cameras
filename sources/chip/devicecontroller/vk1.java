package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vk1 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vk1 a = new vk1();

    private /* synthetic */ vk1() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BasicCluster) baseChipCluster).writeLocalConfigDisabledAttribute((ChipClusters.DefaultClusterCallback) obj, (Boolean) map.get("value"));
    }
}
