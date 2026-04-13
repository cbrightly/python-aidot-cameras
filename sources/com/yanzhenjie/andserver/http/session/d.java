package com.yanzhenjie.andserver.http.session;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/* compiled from: StandardIdGenerator */
public class d implements a {
    private SecureRandom a = a();

    private SecureRandom a() {
        SecureRandom result;
        try {
            result = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            result = new SecureRandom();
        }
        result.nextInt();
        return result;
    }
}
