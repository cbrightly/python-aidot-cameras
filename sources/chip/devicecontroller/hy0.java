package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class hy0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ hy0 a = new hy0();

    private /* synthetic */ hy0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.OnOffCluster.AttributeListAttributeCallback) obj);
    }
}
