package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class xl0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ xl0 a = new xl0();

    private /* synthetic */ xl0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ChannelCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.ChannelCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
