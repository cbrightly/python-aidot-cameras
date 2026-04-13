package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class ql0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ ql0 a = new ql0();

    private /* synthetic */ ql0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AudioOutputCluster) baseChipCluster).readAcceptedCommandListAttribute((ChipClusters.AudioOutputCluster.AcceptedCommandListAttributeCallback) obj);
    }
}
