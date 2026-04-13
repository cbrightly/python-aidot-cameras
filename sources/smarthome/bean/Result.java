package smarthome.bean;

public class Result<T> {
    private int code = 200;
    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data2) {
        this.data = data2;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code2) {
        this.code = code2;
    }
}
