package com.amazonaws.mobileconnectors.kinesisvideo.auth;

import androidx.annotation.NonNull;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.kinesisvideo.auth.AbstractKinesisVideoCredentialsProvider;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentials;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import java.util.Date;

public class KinesisVideoCredentialsProviderImpl extends AbstractKinesisVideoCredentialsProvider {
    private final AWSCredentialsProvider credentialsProvider;
    private final Log log;

    public KinesisVideoCredentialsProviderImpl(@NonNull AWSCredentialsProvider awsCredentialsProvider, @NonNull Log log2) {
        this.credentialsProvider = (AWSCredentialsProvider) Preconditions.checkNotNull(awsCredentialsProvider);
        this.log = (Log) Preconditions.checkNotNull(log2);
    }

    /* access modifiers changed from: protected */
    public KinesisVideoCredentials updateCredentials() {
        this.log.debug("Refreshing credentials");
        this.credentialsProvider.refresh();
        AWSCredentials awsCredentials = this.credentialsProvider.getCredentials();
        String sessionToken = null;
        if (awsCredentials instanceof AWSSessionCredentials) {
            sessionToken = ((AWSSessionCredentials) awsCredentials).getSessionToken();
        }
        Date expiration = KinesisVideoCredentials.CREDENTIALS_NEVER_EXPIRE;
        AWSCredentialsProvider aWSCredentialsProvider = this.credentialsProvider;
        if (aWSCredentialsProvider instanceof CognitoCredentialsProvider) {
            expiration = ((CognitoCredentialsProvider) aWSCredentialsProvider).getSessionCredentitalsExpiration();
            this.log.debug("Refreshed token expiration is %s", expiration);
        }
        Log log2 = this.log;
        Object[] objArr = new Object[2];
        objArr[0] = sessionToken == null ? "" : "session ";
        objArr[1] = expiration;
        log2.debug("Returning %scredentials with expiration %s", objArr);
        return new KinesisVideoCredentials(awsCredentials.getAWSAccessKeyId(), awsCredentials.getAWSSecretKey(), sessionToken, expiration);
    }
}
