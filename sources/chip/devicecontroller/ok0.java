package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ok0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ok0 a = new ok0();

    private /* synthetic */ ok0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.GeneralDiagnosticsCluster.AttributeListAttributeCallback) obj);
    }
}
