package com.didichuxing.doraemonkit.kit.network.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public interface ResponseHandler {
    void onEOF(ByteArrayOutputStream byteArrayOutputStream);

    void onError(IOException iOException);
}
