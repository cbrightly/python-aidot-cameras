package com.sensorsdata.analytics.android.sdk.visual.snap;

import android.os.Handler;
import com.sensorsdata.analytics.android.sdk.SALog;
import com.sensorsdata.analytics.android.sdk.visual.ViewSnapshot;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

public class EditProtocol {
    private static final Class<?>[] NO_PARAMS = new Class[0];
    private static final String TAG = "SA.EProtocol";
    private final ResourceIds mResourceIds;
    private List<PropertyDescription> propertyDescriptionList;

    public EditProtocol(ResourceIds resourceIds) {
        this.mResourceIds = resourceIds;
        try {
            JSONObject payload = new JSONObject("{\"type\":\"snapshot_request\",\"payload\":{\"config\":{\"classes\":[{\"name\":\"android.view.View\",\"properties\":[{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]},{\"name\":\"android.widget.TextView\",\"properties\":[{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]},{\"name\":\"android.widget.ImageView\",\"properties\":[{\"name\":\"clickable\",\"get\":{\"selector\":\"isClickable\",\"parameters\":[],\"result\":{\"type\":\"java.lang.Boolean\"}}}]}]}}}").getJSONObject("payload");
            if (payload.has("config")) {
                this.propertyDescriptionList = getListPropertyDescription(payload);
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
        }
    }

    public ViewSnapshot readSnapshotConfig(Handler mMainThreadHandler) {
        List<PropertyDescription> list = this.propertyDescriptionList;
        if (list == null) {
            return null;
        }
        return new ViewSnapshot(list, this.mResourceIds, mMainThreadHandler);
    }

    private List<PropertyDescription> getListPropertyDescription(JSONObject source) {
        List<PropertyDescription> properties = new LinkedList<>();
        try {
            JSONArray classes = source.getJSONObject("config").getJSONArray("classes");
            for (int classIx = 0; classIx < classes.length(); classIx++) {
                JSONObject classDesc = classes.getJSONObject(classIx);
                Class<?> targetClass = Class.forName(classDesc.getString("name"));
                JSONArray propertyDescs = classDesc.getJSONArray("properties");
                for (int i = 0; i < propertyDescs.length(); i++) {
                    properties.add(readPropertyDescription(targetClass, propertyDescs.getJSONObject(i)));
                }
            }
            return properties;
        } catch (JSONException e) {
            throw new BadInstructionsException("Can't read snapshot configuration", e);
        } catch (ClassNotFoundException e2) {
            throw new BadInstructionsException("Can't resolve types for snapshot configuration", e2);
        }
    }

    private PropertyDescription readPropertyDescription(Class<?> targetClass, JSONObject propertyDesc) {
        String mutatorName;
        try {
            String propName = propertyDesc.getString("name");
            Caller accessor = null;
            if (propertyDesc.has("get")) {
                JSONObject accessorConfig = propertyDesc.getJSONObject("get");
                accessor = new Caller(targetClass, accessorConfig.getString("selector"), NO_PARAMS, Class.forName(accessorConfig.getJSONObject("result").getString(IjkMediaMeta.IJKM_KEY_TYPE)));
            }
            if (propertyDesc.has("set")) {
                mutatorName = propertyDesc.getJSONObject("set").getString("selector");
            } else {
                mutatorName = null;
            }
            return new PropertyDescription(propName, targetClass, accessor, mutatorName);
        } catch (NoSuchMethodException e) {
            throw new BadInstructionsException("Can't create property reader", e);
        } catch (JSONException e2) {
            throw new BadInstructionsException("Can't read property JSON", e2);
        } catch (ClassNotFoundException e3) {
            throw new BadInstructionsException("Can't read property JSON, relevant arg/return class not found", e3);
        }
    }

    public static class BadInstructionsException extends Exception {
        private static final long serialVersionUID = -4062004792184145311L;

        public BadInstructionsException(String message) {
            super(message);
        }

        public BadInstructionsException(String message, Throwable e) {
            super(message, e);
        }
    }
}
