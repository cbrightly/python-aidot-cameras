package meshsdk.strategy.scene;

import android.content.Context;
import java.util.List;
import meshsdk.callback.MeshSceneCallback;
import meshsdk.model.CustomScene;
import org.json.JSONObject;

public abstract class SceneStrategy {
    protected Context mContext;

    public abstract int addScene(int i);

    public abstract void addSceneAction(int i, String str, List<CustomScene.SceneRule> list, MeshSceneCallback meshSceneCallback);

    public abstract boolean hasRules(int i, String str);

    public abstract boolean isExist(int i);

    public abstract JSONObject removeScene(int i);

    public abstract void removeSceneAction(int i, String str, MeshSceneCallback meshSceneCallback);

    public abstract JSONObject runScene(int i, int i2);

    public SceneStrategy(Context context) {
        this.mContext = context;
    }
}
