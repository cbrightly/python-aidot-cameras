package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class je implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ je a = new je();

    private /* synthetic */ je() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ChannelCluster) baseChipCluster).changeChannel((ChipClusters.ChannelCluster.ChangeChannelResponseCallback) obj, (String) map.get("match"));
    }
}
