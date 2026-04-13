package androidx.transition;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Scene {
    private Context mContext;
    private Runnable mEnterAction;
    private Runnable mExitAction;
    private View mLayout;
    private int mLayoutId = -1;
    private ViewGroup mSceneRoot;

    @NonNull
    public static Scene getSceneForLayout(@NonNull ViewGroup sceneRoot, @LayoutRes int layoutId, @NonNull Context context) {
        int i = R.id.transition_scene_layoutid_cache;
        SparseArray<Scene> scenes = (SparseArray) sceneRoot.getTag(i);
        if (scenes == null) {
            scenes = new SparseArray<>();
            sceneRoot.setTag(i, scenes);
        }
        Scene scene = scenes.get(layoutId);
        if (scene != null) {
            return scene;
        }
        Scene scene2 = new Scene(sceneRoot, layoutId, context);
        scenes.put(layoutId, scene2);
        return scene2;
    }

    public Scene(@NonNull ViewGroup sceneRoot) {
        this.mSceneRoot = sceneRoot;
    }

    private Scene(ViewGroup sceneRoot, int layoutId, Context context) {
        this.mContext = context;
        this.mSceneRoot = sceneRoot;
        this.mLayoutId = layoutId;
    }

    public Scene(@NonNull ViewGroup sceneRoot, @NonNull View layout) {
        this.mSceneRoot = sceneRoot;
        this.mLayout = layout;
    }

    @NonNull
    public ViewGroup getSceneRoot() {
        return this.mSceneRoot;
    }

    public void exit() {
        Runnable runnable;
        if (getCurrentScene(this.mSceneRoot) == this && (runnable = this.mExitAction) != null) {
            runnable.run();
        }
    }

    public void enter() {
        if (this.mLayoutId > 0 || this.mLayout != null) {
            getSceneRoot().removeAllViews();
            if (this.mLayoutId > 0) {
                LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
            } else {
                this.mSceneRoot.addView(this.mLayout);
            }
        }
        Runnable runnable = this.mEnterAction;
        if (runnable != null) {
            runnable.run();
        }
        setCurrentScene(this.mSceneRoot, this);
    }

    static void setCurrentScene(@NonNull ViewGroup sceneRoot, @Nullable Scene scene) {
        sceneRoot.setTag(R.id.transition_current_scene, scene);
    }

    @Nullable
    public static Scene getCurrentScene(@NonNull ViewGroup sceneRoot) {
        return (Scene) sceneRoot.getTag(R.id.transition_current_scene);
    }

    public void setEnterAction(@Nullable Runnable action) {
        this.mEnterAction = action;
    }

    public void setExitAction(@Nullable Runnable action) {
        this.mExitAction = action;
    }

    /* access modifiers changed from: package-private */
    public boolean isCreatedFromLayoutResource() {
        return this.mLayoutId > 0;
    }
}
