package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rf implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rf a = new rf();

    private /* synthetic */ rf() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.AudioOutputCluster) baseChipCluster).readAttributeListAttribute((ChipClusters.AudioOutputCluster.AttributeListAttributeCallback) obj);
    }
}
