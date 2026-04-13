package com.amazonaws.auth;

import android.content.Context;
import android.util.Log;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.internal.keyvaluestore.AWSKeyValueStore;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient;
import com.amazonaws.services.cognitoidentity.model.NotAuthorizedException;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.util.VersionInfoUtils;
import java.util.Date;
import java.util.Map;

public class CognitoCachingCredentialsProvider extends CognitoCredentialsProvider {
    private static final String AK_KEY = "accessKey";
    private static final String EXP_KEY = "expirationDate";
    private static final String ID_KEY = "identityId";
    private static final String SK_KEY = "secretKey";
    private static final String ST_KEY = "sessionToken";
    private static final String TAG = "CognitoCachingCredentialsProvider";
    private static final String USER_AGENT = (CognitoCachingCredentialsProvider.class.getName() + "/" + VersionInfoUtils.getVersion());
    private final String DEFAULT_SHAREDPREFERENCES_NAME = "com.amazonaws.android.auth";
    AWSKeyValueStore awsKeyValueStore;
    private String identityId;
    private boolean isPersistenceEnabled = true;
    private final IdentityChangedListener listener = new IdentityChangedListener() {
        public void identityChanged(String oldIdentityId, String newIdentityId) {
            Log.d(CognitoCachingCredentialsProvider.TAG, "Identity id is changed");
            CognitoCachingCredentialsProvider.this.saveIdentityId(newIdentityId);
            CognitoCachingCredentialsProvider.this.clearCredentials();
        }
    };
    volatile boolean needIdentityRefresh = false;

    public CognitoCachingCredentialsProvider(Context context, String accountId, String identityPoolId, String unauthRoleArn, String authRoleArn, Regions region) {
        super(accountId, identityPoolId, unauthRoleArn, authRoleArn, region);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, String accountId, String identityPoolId, String unauthRoleArn, String authRoleArn, Regions region, ClientConfiguration clientConfiguration) {
        super(accountId, identityPoolId, unauthRoleArn, authRoleArn, region, clientConfiguration);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, String identityPoolId, Regions region) {
        super(identityPoolId, region);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, AWSConfiguration awsConfiguration) {
        super(awsConfiguration);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, String identityPoolId, Regions region, ClientConfiguration clientConfiguration) {
        super(identityPoolId, region, clientConfiguration);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, String accountId, String identityPoolId, String unauthArn, String authArn, AmazonCognitoIdentityClient cibClient, AWSSecurityTokenService stsClient) {
        super(accountId, identityPoolId, unauthArn, authArn, cibClient, stsClient);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, AWSCognitoIdentityProvider provider, String unauthArn, String authArn) {
        super(provider, unauthArn, authArn);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, AWSCognitoIdentityProvider provider, String unauthArn, String authArn, AWSSecurityTokenService stsClient) {
        super(provider, unauthArn, authArn, stsClient);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, AWSCognitoIdentityProvider provider, Regions region) {
        super(provider, region);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    public CognitoCachingCredentialsProvider(Context context, AWSCognitoIdentityProvider provider, Regions region, ClientConfiguration clientConfiguration) {
        super(provider, region, clientConfiguration);
        if (context != null) {
            initialize(context);
            return;
        }
        throw new IllegalArgumentException("context can't be null");
    }

    private void initialize(Context context) {
        this.awsKeyValueStore = new AWSKeyValueStore(context, "com.amazonaws.android.auth", this.isPersistenceEnabled);
        checkUpgrade();
        this.identityId = getCachedIdentityId();
        loadCachedCredentials();
        registerIdentityChangedListener(this.listener);
    }

    public String getIdentityId() {
        if (this.needIdentityRefresh) {
            this.needIdentityRefresh = false;
            refresh();
            String identityId2 = super.getIdentityId();
            this.identityId = identityId2;
            saveIdentityId(identityId2);
        }
        String cachedIdentityId = getCachedIdentityId();
        this.identityId = cachedIdentityId;
        if (cachedIdentityId == null) {
            String identityId3 = super.getIdentityId();
            this.identityId = identityId3;
            saveIdentityId(identityId3);
        }
        return this.identityId;
    }

    public AWSSessionCredentials getCredentials() {
        this.credentialsLock.writeLock().lock();
        try {
            if (this.sessionCredentials == null) {
                loadCachedCredentials();
            }
            if (this.sessionCredentialsExpiration != null && !needsNewSession()) {
                return this.sessionCredentials;
            }
            Log.d(TAG, "Making a network call to fetch credentials.");
            super.getCredentials();
            Date date = this.sessionCredentialsExpiration;
            if (date != null) {
                saveCredentials(this.sessionCredentials, date.getTime());
            }
            AWSSessionCredentials aWSSessionCredentials = this.sessionCredentials;
            this.credentialsLock.writeLock().unlock();
            return aWSSessionCredentials;
        } catch (NotAuthorizedException e) {
            Log.e(TAG, "Failure to get credentials", e);
            if (getLogins() != null) {
                super.setIdentityId((String) null);
                super.getCredentials();
                return this.sessionCredentials;
            }
            throw e;
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void refresh() {
        this.credentialsLock.writeLock().lock();
        try {
            super.refresh();
            Date date = this.sessionCredentialsExpiration;
            if (date != null) {
                saveCredentials(this.sessionCredentials, date.getTime());
            }
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void setLogins(Map<String, String> logins) {
        this.credentialsLock.writeLock().lock();
        try {
            super.setLogins(logins);
            this.needIdentityRefresh = true;
            clearCredentials();
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public void clear() {
        super.clear();
        this.awsKeyValueStore.clear();
    }

    public void clearCredentials() {
        this.credentialsLock.writeLock().lock();
        try {
            super.clearCredentials();
            Log.d(TAG, "Clearing credentials from SharedPreferences");
            this.awsKeyValueStore.remove(namespace(AK_KEY));
            this.awsKeyValueStore.remove(namespace(SK_KEY));
            this.awsKeyValueStore.remove(namespace(ST_KEY));
            this.awsKeyValueStore.remove(namespace(EXP_KEY));
        } finally {
            this.credentialsLock.writeLock().unlock();
        }
    }

    public String getCachedIdentityId() {
        String cachedIdentityId = this.awsKeyValueStore.get(namespace(ID_KEY));
        if (cachedIdentityId != null && this.identityId == null) {
            super.setIdentityId(cachedIdentityId);
        }
        return cachedIdentityId;
    }

    private void loadCachedCredentials() {
        Log.d(TAG, "Loading credentials from SharedPreferences");
        if (this.awsKeyValueStore.get(namespace(EXP_KEY)) != null) {
            this.sessionCredentialsExpiration = new Date(Long.parseLong(this.awsKeyValueStore.get(namespace(EXP_KEY))));
        } else {
            this.sessionCredentialsExpiration = new Date(0);
        }
        boolean hasAK = this.awsKeyValueStore.contains(namespace(AK_KEY));
        boolean hasSK = this.awsKeyValueStore.contains(namespace(SK_KEY));
        boolean hasST = this.awsKeyValueStore.contains(namespace(ST_KEY));
        if (!hasAK || !hasSK || !hasST) {
            Log.d(TAG, "No valid credentials found in SharedPreferences");
            this.sessionCredentialsExpiration = null;
            return;
        }
        String accessKey = this.awsKeyValueStore.get(namespace(AK_KEY));
        String secretAccessKey = this.awsKeyValueStore.get(namespace(SK_KEY));
        String sessionToken = this.awsKeyValueStore.get(namespace(ST_KEY));
        if (accessKey == null || secretAccessKey == null || sessionToken == null) {
            Log.d(TAG, "No valid credentials found in SharedPreferences");
            this.sessionCredentialsExpiration = null;
            return;
        }
        this.sessionCredentials = new BasicSessionCredentials(accessKey, secretAccessKey, sessionToken);
    }

    private void saveCredentials(AWSSessionCredentials sessionCredentials, long time) {
        Log.d(TAG, "Saving credentials to SharedPreferences");
        if (sessionCredentials != null) {
            this.awsKeyValueStore.put(namespace(AK_KEY), sessionCredentials.getAWSAccessKeyId());
            this.awsKeyValueStore.put(namespace(SK_KEY), sessionCredentials.getAWSSecretKey());
            this.awsKeyValueStore.put(namespace(ST_KEY), sessionCredentials.getSessionToken());
            this.awsKeyValueStore.put(namespace(EXP_KEY), String.valueOf(time));
        }
    }

    /* access modifiers changed from: private */
    public void saveIdentityId(String identityId2) {
        Log.d(TAG, "Saving identity id to SharedPreferences");
        this.identityId = identityId2;
        this.awsKeyValueStore.put(namespace(ID_KEY), identityId2);
    }

    /* access modifiers changed from: protected */
    public String getUserAgent() {
        return USER_AGENT;
    }

    private void checkUpgrade() {
        if (this.awsKeyValueStore.contains(ID_KEY)) {
            Log.i(TAG, "Identity id without namespace is detected. It will be saved under new namespace.");
            String identityId2 = this.awsKeyValueStore.get(ID_KEY);
            this.awsKeyValueStore.clear();
            this.awsKeyValueStore.put(namespace(ID_KEY), identityId2);
        }
    }

    private String namespace(String key) {
        return getIdentityPoolId() + "." + key;
    }

    public void setPersistenceEnabled(boolean isPersistenceEnabled2) {
        this.isPersistenceEnabled = isPersistenceEnabled2;
        this.awsKeyValueStore.setPersistenceEnabled(isPersistenceEnabled2);
    }
}
