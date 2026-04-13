package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class fr0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ fr0 a = new fr0();

    private /* synthetic */ fr0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.TargetNavigatorCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.TargetNavigatorCluster.AttributeListAttributeCallback) obj);
    }
}
