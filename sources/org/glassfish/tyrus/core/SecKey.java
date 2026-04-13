package org.glassfish.tyrus.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;
import org.glassfish.tyrus.spi.UpgradeRequest;

public class SecKey {
    private static final int KEY_SIZE = 16;
    private static final Random random = new SecureRandom();
    private final String secKey;

    public SecKey() {
        this.secKey = create();
    }

    private String create() {
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public SecKey(String base64) {
        if (base64 != null) {
            this.secKey = base64;
            return;
        }
        throw new HandshakeException(LocalizationMessages.SEC_KEY_NULL_NOT_ALLOWED());
    }

    public static SecKey generateServerKey(SecKey clientKey) {
        String key = clientKey.getSecKey() + UpgradeRequest.SERVER_KEY_HASH;
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(key.getBytes("UTF-8"));
            byte[] digest = instance.digest();
            if (digest.length == 20) {
                return new SecKey(Base64.getEncoder().encodeToString(digest));
            }
            throw new HandshakeException(LocalizationMessages.SEC_KEY_INVALID_LENGTH(Integer.valueOf(digest.length)));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            throw new HandshakeException(e.getMessage());
        }
    }

    public String getSecKey() {
        return this.secKey;
    }

    public String toString() {
        return this.secKey;
    }

    public void validateServerKey(String serverKey) {
        if (!generateServerKey(this).getSecKey().equals(serverKey)) {
            throw new HandshakeException(LocalizationMessages.SEC_KEY_INVALID_SERVER());
        }
    }
}
