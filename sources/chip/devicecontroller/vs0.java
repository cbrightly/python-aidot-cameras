package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class vs0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ vs0 a = new vs0();

    private /* synthetic */ vs0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.OnOffCluster) baseChipCluster).readOnOffAttribute((ChipClusters.BooleanAttributeCallback) obj);
    }
}
