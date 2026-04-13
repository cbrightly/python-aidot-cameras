package com.amazonaws.auth;

import com.amazonaws.internal.config.InternalConfig;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SignerFactory {
    private static final String NO_OP_SIGNER = "NoOpSignerType";
    private static final String QUERY_STRING_SIGNER = "QueryStringSignerType";
    private static final Map<String, Class<? extends Signer>> SIGNERS;
    private static final String VERSION_FOUR_SIGNER = "AWS4SignerType";
    private static final String VERSION_THREE_SIGNER = "AWS3SignerType";

    static {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        SIGNERS = concurrentHashMap;
        concurrentHashMap.put(QUERY_STRING_SIGNER, QueryStringSigner.class);
        concurrentHashMap.put(VERSION_THREE_SIGNER, AWS3Signer.class);
        concurrentHashMap.put(VERSION_FOUR_SIGNER, AWS4Signer.class);
        concurrentHashMap.put(NO_OP_SIGNER, NoOpSigner.class);
    }

    private SignerFactory() {
    }

    public static void registerSigner(String signerType, Class<? extends Signer> signerClass) {
        if (signerType == null) {
            throw new IllegalArgumentException("signerType cannot be null");
        } else if (signerClass != null) {
            SIGNERS.put(signerType, signerClass);
        } else {
            throw new IllegalArgumentException("signerClass cannot be null");
        }
    }

    public static Signer getSigner(String serviceName, String regionName) {
        return lookupAndCreateSigner(serviceName, regionName);
    }

    public static Signer getSignerByTypeAndService(String signerType, String serviceName) {
        return createSigner(signerType, serviceName);
    }

    private static Signer lookupAndCreateSigner(String serviceName, String regionName) {
        return createSigner(InternalConfig.Factory.getInternalConfig().getSignerConfig(serviceName, regionName).getSignerType(), serviceName);
    }

    private static Signer createSigner(String signerType, String serviceName) {
        Class<? extends Signer> signerClass = SIGNERS.get(signerType);
        if (signerClass != null) {
            try {
                Signer signer = (Signer) signerClass.newInstance();
                if (signer instanceof ServiceAwareSigner) {
                    ((ServiceAwareSigner) signer).setServiceName(serviceName);
                }
                return signer;
            } catch (InstantiationException ex) {
                throw new IllegalStateException("Cannot create an instance of " + signerClass.getName(), ex);
            } catch (IllegalAccessException ex2) {
                throw new IllegalStateException("Cannot create an instance of " + signerClass.getName(), ex2);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}
