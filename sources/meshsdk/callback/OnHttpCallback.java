package meshsdk.callback;

public abstract class OnHttpCallback<T> {
    public abstract void onResult(T t);
}
