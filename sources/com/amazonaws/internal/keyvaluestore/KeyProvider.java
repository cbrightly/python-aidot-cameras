package com.amazonaws.internal.keyvaluestore;

import java.security.Key;

public interface KeyProvider {
    void deleteKey(String str);

    Key generateKey(String str);

    Key retrieveKey(String str);
}
