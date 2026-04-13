package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class pv0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ pv0 a = new pv0();

    private /* synthetic */ pv0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readUpTimeAttribute((ChipClusters.LongAttributeCallback) obj);
    }
}
