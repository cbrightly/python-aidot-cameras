package meshsdk.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import meshsdk.model.json.HSL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomScene implements Serializable {
    private static final long serialVersionUID = -546546449849494L;
    private HashMap<String, SceneDevice> devices = new HashMap<>();
    private String sceneId;

    public String toString() {
        return "CustomScene{sceneId='" + this.sceneId + '\'' + ", devices=" + this.devices + '}';
    }

    public CustomScene(String sceneId2) {
        this.sceneId = sceneId2;
    }

    public String getSceneId() {
        return this.sceneId;
    }

    public HashMap<String, SceneDevice> getDevices() {
        return this.devices;
    }

    public void putSceneDevice(SceneDevice sd) {
        this.devices.put(sd.mac, sd);
    }

    public List<SceneDevice> toList() {
        List<SceneDevice> list = new ArrayList<>();
        HashMap<String, SceneDevice> hashMap = this.devices;
        if (hashMap != null && hashMap.size() > 0) {
            for (SceneDevice add : this.devices.values()) {
                list.add(add);
            }
        }
        return list;
    }

    public JSONObject toJSON() {
        JSONObject sceneJson = new JSONObject();
        sceneJson.put("sceneId", (Object) this.sceneId);
        HashMap<String, SceneDevice> hashMap = this.devices;
        if (hashMap != null && hashMap.size() > 0) {
            JSONArray devArr = new JSONArray();
            for (SceneDevice json : this.devices.values()) {
                devArr.put((Object) json.toJSON());
            }
            sceneJson.put("devices", (Object) devArr);
        }
        return sceneJson;
    }

    public static class SceneDevice implements Serializable {
        private static final long serialVersionUID = -526546449849494L;
        public String mac;
        private HashMap<Integer, SceneRule> rules = new HashMap<>();

        public SceneDevice(String mac2) {
            this.mac = mac2;
        }

        public void putRule(SceneRule rule) {
            this.rules.put(Integer.valueOf(rule.modelId), rule);
        }

        public HashMap<Integer, SceneRule> getRules() {
            return this.rules;
        }

        public List<SceneRule> toRuleList() {
            List<SceneRule> list = new ArrayList<>();
            HashMap<Integer, SceneRule> hashMap = this.rules;
            if (hashMap != null && hashMap.size() > 0) {
                list.addAll(this.rules.values());
            }
            return list;
        }

        public JSONObject toJSON() {
            JSONObject devJson = new JSONObject();
            devJson.put("mac", (Object) this.mac);
            HashMap<Integer, SceneRule> hashMap = this.rules;
            if (hashMap != null && hashMap.size() > 0) {
                JSONArray ruleArr = new JSONArray();
                for (SceneRule rule : this.rules.values()) {
                    ruleArr.put((Object) rule.toJSON());
                }
                devJson.put("rules", (Object) ruleArr);
            }
            return devJson;
        }
    }

    public static class SceneRule implements Serializable {
        private static final long serialVersionUID = -546546449841494L;
        private transient int dataType = 0;
        /* access modifiers changed from: private */
        public int modelId;
        private Object value;
        private transient String valueStr;

        public SceneRule(int modelId2, Object value2, int type) {
            this.modelId = modelId2;
            this.dataType = type;
            this.value = value2;
            if (type == 1 || (value2 instanceof HSL)) {
                this.valueStr = ((HSL) value2).toJson().toString();
            } else if (type == 0) {
                this.valueStr = String.valueOf(value2);
            }
        }

        public int getModelId() {
            return this.modelId;
        }

        public Object getValue() {
            Object obj = this.value;
            if (obj == null) {
                if (this.dataType == 0) {
                    this.value = Integer.valueOf(Integer.parseInt(this.valueStr));
                } else {
                    try {
                        this.value = new JSONObject(this.valueStr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (obj instanceof HSL) {
                return ((HSL) obj).toJson();
            }
            return this.value;
        }

        public void setValue(int modelId2, Object value2) {
            this.modelId = modelId2;
            this.value = value2;
            if (modelId2 != 4871 || !(value2 instanceof HSL)) {
                this.valueStr = String.valueOf(value2);
            } else {
                this.valueStr = ((HSL) value2).toJson().toString();
            }
        }

        public JSONObject toJSON() {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("modelId", this.modelId).put("value", this.value);
            return jsonObject;
        }
    }
}
