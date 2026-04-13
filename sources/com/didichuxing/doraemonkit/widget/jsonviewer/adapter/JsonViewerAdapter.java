package com.didichuxing.doraemonkit.widget.jsonviewer.adapter;

import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.widget.jsonviewer.utils.Utils;
import com.didichuxing.doraemonkit.widget.jsonviewer.view.JsonItemView;
import com.google.maps.android.BuildConfig;
import com.meituan.robust.Constants;
import com.sensorsdata.analytics.android.sdk.SensorsDataAutoTrackHelper;
import com.sensorsdata.analytics.android.sdk.SensorsDataInstrumented;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonViewerAdapter extends BaseJsonViewerAdapter<JsonItemViewHolder> {
    private String jsonStr;
    private JSONArray mJSONArray;
    private JSONObject mJSONObject;

    public JsonViewerAdapter(String jsonStr2) {
        this.jsonStr = jsonStr2;
        Object object = null;
        try {
            object = new JSONTokener(jsonStr2).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (object != null && (object instanceof JSONObject)) {
            this.mJSONObject = (JSONObject) object;
        } else if (object == null || !(object instanceof JSONArray)) {
            throw new IllegalArgumentException("jsonStr is illegal.");
        } else {
            this.mJSONArray = (JSONArray) object;
        }
    }

    public JsonViewerAdapter(JSONObject jsonObject) {
        this.mJSONObject = jsonObject;
        if (jsonObject == null) {
            throw new IllegalArgumentException("jsonObject can not be null.");
        }
    }

    public JsonViewerAdapter(JSONArray jsonArray) {
        this.mJSONArray = jsonArray;
        if (jsonArray == null) {
            throw new IllegalArgumentException("jsonArray can not be null.");
        }
    }

    public JsonItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new JsonItemViewHolder(new JsonItemView(parent.getContext()));
    }

    public void onBindViewHolder(JsonItemViewHolder holder, int position) {
        JsonItemView itemView = holder.itemView;
        itemView.setTextSize(BaseJsonViewerAdapter.TEXT_SIZE_DP);
        itemView.setRightColor(BaseJsonViewerAdapter.BRACES_COLOR);
        if (this.mJSONObject != null) {
            if (position == 0) {
                itemView.hideLeft();
                itemView.hideIcon();
                itemView.showRight("{");
                return;
            } else if (position == getItemCount() - 1) {
                itemView.hideLeft();
                itemView.hideIcon();
                itemView.showRight("}");
                return;
            } else if (this.mJSONObject.names() != null) {
                String key = this.mJSONObject.names().optString(position - 1);
                Object value = this.mJSONObject.opt(key);
                if (position < getItemCount() - 2) {
                    handleJsonObject(key, value, itemView, true, 1);
                } else {
                    handleJsonObject(key, value, itemView, false, 1);
                }
            } else {
                return;
            }
        }
        if (this.mJSONArray == null) {
            return;
        }
        if (position == 0) {
            itemView.hideLeft();
            itemView.hideIcon();
            itemView.showRight(Constants.ARRAY_TYPE);
        } else if (position == getItemCount() - 1) {
            itemView.hideLeft();
            itemView.hideIcon();
            itemView.showRight("]");
        } else {
            Object value2 = this.mJSONArray.opt(position - 1);
            if (position < getItemCount() - 2) {
                handleJsonArray(value2, itemView, true, 1);
            } else {
                handleJsonArray(value2, itemView, false, 1);
            }
        }
    }

    public int getItemCount() {
        JSONObject jSONObject = this.mJSONObject;
        if (jSONObject == null) {
            JSONArray jSONArray = this.mJSONArray;
            if (jSONArray != null) {
                return jSONArray.length() + 2;
            }
            return 0;
        } else if (jSONObject.names() != null) {
            return this.mJSONObject.names().length() + 2;
        } else {
            return 2;
        }
    }

    /* access modifiers changed from: private */
    public void handleJsonObject(String key, Object value, JsonItemView itemView, boolean appendComma, int hierarchy) {
        SpannableStringBuilder keyBuilder = new SpannableStringBuilder(Utils.getHierarchyStr(hierarchy));
        keyBuilder.append("\"").append(key).append("\"").append(":");
        keyBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.KEY_COLOR), 0, keyBuilder.length() - 1, 33);
        keyBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.BRACES_COLOR), keyBuilder.length() - 1, keyBuilder.length(), 33);
        itemView.showLeft(keyBuilder);
        handleValue(value, itemView, appendComma, hierarchy);
    }

    /* access modifiers changed from: private */
    public void handleJsonArray(Object value, JsonItemView itemView, boolean appendComma, int hierarchy) {
        itemView.showLeft(new SpannableStringBuilder(Utils.getHierarchyStr(hierarchy)));
        handleValue(value, itemView, appendComma, hierarchy);
    }

    private void handleValue(Object value, JsonItemView itemView, boolean appendComma, int hierarchy) {
        SpannableStringBuilder valueBuilder = new SpannableStringBuilder();
        if (value instanceof Number) {
            valueBuilder.append(value.toString());
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.NUMBER_COLOR), 0, valueBuilder.length(), 33);
        } else if (value instanceof Boolean) {
            valueBuilder.append(value.toString());
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.BOOLEAN_COLOR), 0, valueBuilder.length(), 33);
        } else if (value instanceof JSONObject) {
            itemView.showIcon(true);
            valueBuilder.append("Object{...}");
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.BRACES_COLOR), 0, valueBuilder.length(), 33);
            itemView.setIconClickListener(new JsonItemClickListener(value, itemView, appendComma, hierarchy + 1));
        } else if (value instanceof JSONArray) {
            itemView.showIcon(true);
            valueBuilder.append("Array[").append(String.valueOf(((JSONArray) value).length())).append("]");
            int len = valueBuilder.length();
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.BRACES_COLOR), 0, 6, 33);
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.NUMBER_COLOR), 6, len - 1, 33);
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.BRACES_COLOR), len - 1, len, 33);
            itemView.setIconClickListener(new JsonItemClickListener(value, itemView, appendComma, hierarchy + 1));
        } else if (value instanceof String) {
            itemView.hideIcon();
            valueBuilder.append("\"").append(value.toString()).append("\"");
            if (Utils.isUrl(value.toString())) {
                valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.TEXT_COLOR), 0, 1, 33);
                valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.URL_COLOR), 1, valueBuilder.length() - 1, 33);
                valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.TEXT_COLOR), valueBuilder.length() - 1, valueBuilder.length(), 33);
            } else {
                valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.TEXT_COLOR), 0, valueBuilder.length(), 33);
            }
        } else if (valueBuilder.length() == 0 || value == null) {
            itemView.hideIcon();
            valueBuilder.append(BuildConfig.TRAVIS);
            valueBuilder.setSpan(new ForegroundColorSpan(BaseJsonViewerAdapter.NULL_COLOR), 0, valueBuilder.length(), 33);
        }
        if (appendComma) {
            valueBuilder.append(",");
        }
        itemView.showRight(valueBuilder);
    }

    public class JsonItemClickListener implements View.OnClickListener {
        private boolean appendComma;
        private int hierarchy;
        private boolean isCollapsed = true;
        private boolean isJsonArray;
        private JsonItemView itemView;
        private Object value;

        JsonItemClickListener(Object value2, JsonItemView itemView2, boolean appendComma2, int hierarchy2) {
            boolean z = true;
            this.value = value2;
            this.itemView = itemView2;
            this.appendComma = appendComma2;
            this.hierarchy = hierarchy2;
            this.isJsonArray = (value2 == null || !(value2 instanceof JSONArray)) ? false : z;
        }

        @SensorsDataInstrumented
        public void onClick(View view) {
            View view2 = view;
            if (this.itemView.getChildCount() == 1) {
                this.isCollapsed = false;
                this.itemView.showIcon(false);
                JsonItemView jsonItemView = this.itemView;
                jsonItemView.setTag(jsonItemView.getRightText());
                this.itemView.showRight(this.isJsonArray ? Constants.ARRAY_TYPE : "{");
                JSONArray array = this.isJsonArray ? (JSONArray) this.value : ((JSONObject) this.value).names();
                int i = 0;
                while (array != null && i < array.length()) {
                    JsonItemView childItemView = new JsonItemView(this.itemView.getContext());
                    childItemView.setTextSize(BaseJsonViewerAdapter.TEXT_SIZE_DP);
                    childItemView.setRightColor(BaseJsonViewerAdapter.BRACES_COLOR);
                    Object childValue = array.opt(i);
                    if (this.isJsonArray) {
                        JsonViewerAdapter.this.handleJsonArray(childValue, childItemView, i < array.length() - 1, this.hierarchy);
                    } else {
                        JsonViewerAdapter.this.handleJsonObject((String) childValue, ((JSONObject) this.value).opt((String) childValue), childItemView, i < array.length() - 1, this.hierarchy);
                    }
                    this.itemView.addViewNoInvalidate(childItemView);
                    i++;
                }
                JsonItemView childItemView2 = new JsonItemView(this.itemView.getContext());
                childItemView2.setTextSize(BaseJsonViewerAdapter.TEXT_SIZE_DP);
                childItemView2.setRightColor(BaseJsonViewerAdapter.BRACES_COLOR);
                StringBuilder builder = new StringBuilder(Utils.getHierarchyStr(this.hierarchy - 1));
                builder.append(this.isJsonArray ? "]" : "}");
                builder.append(this.appendComma ? "," : "");
                childItemView2.showRight(builder);
                this.itemView.addViewNoInvalidate(childItemView2);
                this.itemView.requestLayout();
                this.itemView.invalidate();
            } else {
                CharSequence temp = this.itemView.getRightText();
                JsonItemView jsonItemView2 = this.itemView;
                jsonItemView2.showRight((CharSequence) jsonItemView2.getTag());
                this.itemView.setTag(temp);
                this.itemView.showIcon(!this.isCollapsed);
                for (int i2 = 1; i2 < this.itemView.getChildCount(); i2++) {
                    this.itemView.getChildAt(i2).setVisibility(this.isCollapsed ? 0 : 8);
                }
                this.isCollapsed = !this.isCollapsed;
            }
            SensorsDataAutoTrackHelper.trackViewOnClick(view);
        }
    }

    public class JsonItemViewHolder extends RecyclerView.ViewHolder {
        JsonItemView itemView;

        JsonItemViewHolder(JsonItemView itemView2) {
            super(itemView2);
            setIsRecyclable(false);
            this.itemView = itemView2;
        }
    }
}
