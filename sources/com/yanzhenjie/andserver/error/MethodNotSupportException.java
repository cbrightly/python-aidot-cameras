package com.yanzhenjie.andserver.error;

import com.yanzhenjie.andserver.http.b;
import java.util.List;
import meshsdk.BaseResp;

public class MethodNotSupportException extends HttpException {
    private List<b> mMethods;

    public MethodNotSupportException(b method) {
        super((int) BaseResp.ERR_CONNECT_FAIL, String.format("The request method [%s] is not supported.", new Object[]{method.value()}));
    }

    public MethodNotSupportException(b method, Throwable cause) {
        super(BaseResp.ERR_CONNECT_FAIL, String.format("The request method [%s] is not supported.", new Object[]{method.value()}), cause);
    }

    public List<b> getMethods() {
        return this.mMethods;
    }

    public void setMethods(List<b> methods) {
        this.mMethods = methods;
    }
}
