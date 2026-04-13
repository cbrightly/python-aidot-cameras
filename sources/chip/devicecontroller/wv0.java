package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class wv0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ wv0 a = new wv0();

    private /* synthetic */ wv0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.SoftwareDiagnosticsCluster) baseChipCluster).readClusterRevisionAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
