package com.didichuxing.doraemonkit.widget.jsonviewer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.didichuxing.doraemonkit.widget.jsonviewer.adapter.BaseJsonViewerAdapter;
import com.didichuxing.doraemonkit.widget.jsonviewer.adapter.JsonViewerAdapter;
import com.didichuxing.doraemonkit.widget.jsonviewer.view.JsonItemView;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonRecyclerView extends RecyclerView {
    private BaseJsonViewerAdapter mAdapter;
    int mode;
    float oldDist;
    private RecyclerView.OnItemTouchListener touchListener;

    public JsonRecyclerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public JsonRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JsonRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.touchListener = new RecyclerView.OnItemTouchListener() {
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent event) {
                switch (event.getAction() & event.getActionMasked()) {
                    case 0:
                        JsonRecyclerView.this.mode = 1;
                        break;
                    case 1:
                        JsonRecyclerView.this.mode = 0;
                        break;
                    case 2:
                        JsonRecyclerView jsonRecyclerView = JsonRecyclerView.this;
                        if (jsonRecyclerView.mode >= 2) {
                            float newDist = jsonRecyclerView.spacing(event);
                            if (Math.abs(newDist - JsonRecyclerView.this.oldDist) > 0.5f) {
                                JsonRecyclerView jsonRecyclerView2 = JsonRecyclerView.this;
                                jsonRecyclerView2.zoom(newDist / jsonRecyclerView2.oldDist);
                                JsonRecyclerView.this.oldDist = newDist;
                                break;
                            }
                        }
                        break;
                    case 5:
                        JsonRecyclerView jsonRecyclerView3 = JsonRecyclerView.this;
                        jsonRecyclerView3.oldDist = jsonRecyclerView3.spacing(event);
                        JsonRecyclerView.this.mode++;
                        break;
                    case 6:
                        JsonRecyclerView.this.mode--;
                        break;
                }
                return false;
            }

            public void onTouchEvent(RecyclerView rv, MotionEvent event) {
            }

            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        };
        initView();
    }

    private void initView() {
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void bindJson(String jsonStr) {
        this.mAdapter = null;
        JsonViewerAdapter jsonViewerAdapter = new JsonViewerAdapter(jsonStr);
        this.mAdapter = jsonViewerAdapter;
        setAdapter(jsonViewerAdapter);
    }

    public void bindJson(JSONArray array) {
        this.mAdapter = null;
        JsonViewerAdapter jsonViewerAdapter = new JsonViewerAdapter(array);
        this.mAdapter = jsonViewerAdapter;
        setAdapter(jsonViewerAdapter);
    }

    public void bindJson(JSONObject object) {
        this.mAdapter = null;
        JsonViewerAdapter jsonViewerAdapter = new JsonViewerAdapter(object);
        this.mAdapter = jsonViewerAdapter;
        setAdapter(jsonViewerAdapter);
    }

    public void setKeyColor(int color) {
        BaseJsonViewerAdapter.KEY_COLOR = color;
    }

    public void setValueTextColor(int color) {
        BaseJsonViewerAdapter.TEXT_COLOR = color;
    }

    public void setValueNumberColor(int color) {
        BaseJsonViewerAdapter.NUMBER_COLOR = color;
    }

    public void setValueBooleanColor(int color) {
        BaseJsonViewerAdapter.BOOLEAN_COLOR = color;
    }

    public void setValueUrlColor(int color) {
        BaseJsonViewerAdapter.URL_COLOR = color;
    }

    public void setValueNullColor(int color) {
        BaseJsonViewerAdapter.NUMBER_COLOR = color;
    }

    public void setBracesColor(int color) {
        BaseJsonViewerAdapter.BRACES_COLOR = color;
    }

    public void setTextSize(float sizeDP) {
        if (sizeDP < 10.0f) {
            sizeDP = 10.0f;
        } else if (sizeDP > 30.0f) {
            sizeDP = 30.0f;
        }
        if (BaseJsonViewerAdapter.TEXT_SIZE_DP != sizeDP) {
            BaseJsonViewerAdapter.TEXT_SIZE_DP = sizeDP;
            if (this.mAdapter != null) {
                updateAll(sizeDP);
            }
        }
    }

    public void setScaleEnable(boolean enable) {
        if (enable) {
            addOnItemTouchListener(this.touchListener);
        } else {
            removeOnItemTouchListener(this.touchListener);
        }
    }

    public void updateAll(float textSize) {
        RecyclerView.LayoutManager manager = getLayoutManager();
        int count = manager.getChildCount();
        for (int i = 0; i < count; i++) {
            loop(manager.getChildAt(i), textSize);
        }
    }

    private void loop(View view, float textSize) {
        if (view instanceof JsonItemView) {
            JsonItemView group = (JsonItemView) view;
            group.setTextSize(textSize);
            int childCount = group.getChildCount();
            for (int i = 0; i < childCount; i++) {
                loop(group.getChildAt(i), textSize);
            }
        }
    }

    /* access modifiers changed from: private */
    public void zoom(float f) {
        setTextSize(BaseJsonViewerAdapter.TEXT_SIZE_DP * f);
    }

    /* access modifiers changed from: private */
    public float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }
}
