package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class z50 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ z50 a = new z50();

    private /* synthetic */ z50() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ChannelCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.ChannelCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
