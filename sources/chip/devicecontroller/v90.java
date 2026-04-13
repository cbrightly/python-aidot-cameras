package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class v90 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ v90 a = new v90();

    private /* synthetic */ v90() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.GeneralDiagnosticsCluster) baseChipCluster).readTestEventTriggersEnabledAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
