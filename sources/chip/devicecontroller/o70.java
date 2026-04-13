package chip.devicecontroller;

import chip.clusterinfo.InteractionInfo;
import chip.devicecontroller.ChipClusters;
import java.util.Map;

/* compiled from: lambda */
public final /* synthetic */ class o70 implements InteractionInfo.ClusterCommandFunction {
    public static final /* synthetic */ o70 a = new o70();

    private /* synthetic */ o70() {
    }

    public final void invokeCommand(ChipClusters.BaseChipCluster baseChipCluster, Object obj, Map map) {
        ((ChipClusters.FanControlCluster) baseChipCluster).readPercentSettingAttribute((ChipClusters.FanControlCluster.PercentSettingAttributeCallback) obj);
    }
}
