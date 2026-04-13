package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class rq0 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ rq0 a = new rq0();

    private /* synthetic */ rq0() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.BallastConfigurationCluster) baseChipCluster).readBallastFactorAdjustmentAttribute((ChipClusters.BallastConfigurationCluster.BallastFactorAdjustmentAttributeCallback) obj);
    }
}
