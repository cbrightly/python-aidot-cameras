package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class o00 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ o00 a = new o00();

    private /* synthetic */ o00() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.ActionsCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.ActionsCluster.AttributeListAttributeCallback) obj);
    }
}
