package meshsdk.model.json;

import java.util.ArrayList;
import java.util.List;
import meshsdk.model.CustomScene;

public class CustomSceneStorage {
    private List<SceneDeviceStorage> devices = new ArrayList();
    private String sceneId;

    public CustomSceneStorage(String sceneId2) {
        this.sceneId = sceneId2;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public void addSceneDevice(SceneDeviceStorage sd) {
        this.devices.add(sd);
    }

    public List<SceneDeviceStorage> getDevices() {
        return this.devices;
    }

    public static class SceneDeviceStorage {
        public String mac;
        private List<CustomScene.SceneRule> rules = new ArrayList();

        public SceneDeviceStorage(String mac2) {
            this.mac = mac2;
        }

        public void addRule(CustomScene.SceneRule rule) {
            this.rules.add(rule);
        }

        public void addRules(List<CustomScene.SceneRule> rule) {
            this.rules.addAll(rule);
        }

        public List<CustomScene.SceneRule> getRules() {
            return this.rules;
        }
    }
}
