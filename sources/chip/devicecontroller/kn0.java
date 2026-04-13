package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class kn0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ kn0 a = new kn0();

    private /* synthetic */ kn0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).readMinLevelAttribute((ChipClusters.IntegerAttributeCallback) obj);
    }
}
