package meshsdk.strategy.group;

import android.content.Context;
import meshsdk.callback.MeshCustomcmdCallback;
import meshsdk.callback.MeshGroupCallback;
import meshsdk.ctrl.EffectLinkageCtrlAdapter;
import meshsdk.ctrl.EffectModeCtrlAdapter;
import meshsdk.model.json.CustomEffectMode;
import org.json.JSONObject;

public abstract class GroupStrategy {
    protected Context mContext;

    public abstract int addGroup(int i, String str);

    public abstract void addGroupMember(int i, String str, MeshGroupCallback meshGroupCallback);

    public abstract JSONObject allControl(int i, Object obj);

    public abstract JSONObject controlGroup(int i, int i2, Object obj);

    public abstract boolean isExist(int i);

    public abstract JSONObject removeGroup(int i);

    public abstract void removeGroupMember(int i, String str, MeshGroupCallback meshGroupCallback);

    public abstract void removeGroupMember2(String str, int i, MeshGroupCallback meshGroupCallback);

    public abstract void setCustomEffectMode(EffectModeCtrlAdapter effectModeCtrlAdapter, CustomEffectMode customEffectMode, MeshCustomcmdCallback meshCustomcmdCallback);

    public abstract void setEffectMode(EffectModeCtrlAdapter effectModeCtrlAdapter, int i, long j, int i2, String str, MeshCustomcmdCallback meshCustomcmdCallback);

    public abstract void setLinkageMode(EffectLinkageCtrlAdapter effectLinkageCtrlAdapter, int i, int i2, String str, MeshCustomcmdCallback meshCustomcmdCallback);

    public GroupStrategy(Context mContext2) {
        this.mContext = mContext2;
    }
}
