package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class sz implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ sz a = new sz();

    private /* synthetic */ sz() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.OnOffCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
