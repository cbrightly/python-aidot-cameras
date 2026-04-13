package com.didichuxing.doraemonkit.kit.health;

import com.didichuxing.doraemonkit.okgo.model.Response;

public interface UploadAppHealthCallback {
    void onError(Response<String> response);

    void onSuccess(Response<String> response);
}
