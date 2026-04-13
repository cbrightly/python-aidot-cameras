package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class n81 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ n81 a = new n81();

    private /* synthetic */ n81() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AudioOutputCluster) baseChipCluster).readGeneratedCommandListAttribute((ChipClusters.AudioOutputCluster.GeneratedCommandListAttributeCallback) obj);
    }
}
