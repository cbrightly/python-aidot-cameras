package meshsdk.cache;

import com.google.gson.Gson;
import com.leedarson.base.application.BaseApplication;
import com.leedarson.base.utils.m;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import meshsdk.MeshLogNew;
import meshsdk.util.SharedPreferenceHelper;

public abstract class FileCache<T> extends ICache<T> {
    private static final String TAG = "MeshFileCache";

    public void put(String key, T t) {
        String value = new Gson().toJson((Object) t);
        log("putk:" + key + ",v:" + t + ",value:" + value);
        SharedPreferenceHelper.setValue(BaseApplication.b(), key, value);
    }

    public T get(String key) {
        try {
            String value = SharedPreferenceHelper.getValue(BaseApplication.b(), key);
            T t = m.a(value, getActuralType());
            log("getk:" + key + ",value:" + value + ",return:" + t);
            return t;
        } catch (Exception e) {
            log("getk:" + key + " failed:" + e.getMessage());
            return null;
        }
    }

    public Class getActuralType() {
        Type genericSupperClass = getClass().getGenericSuperclass();
        if (genericSupperClass instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) genericSupperClass).getActualTypeArguments()[0];
        }
        return null;
    }

    public void log(String msg) {
        MeshLogNew.i("MeshFileCache-->" + msg);
    }
}
