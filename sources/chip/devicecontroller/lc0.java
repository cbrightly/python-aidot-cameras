package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class lc0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ lc0 a = new lc0();

    private /* synthetic */ lc0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BooleanStateCluster) baseChipCluster).readStateValueAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
