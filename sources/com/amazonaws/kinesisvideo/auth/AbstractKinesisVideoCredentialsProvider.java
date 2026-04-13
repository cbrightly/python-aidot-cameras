package com.amazonaws.kinesisvideo.auth;

public abstract class AbstractKinesisVideoCredentialsProvider implements KinesisVideoCredentialsProvider {
    private KinesisVideoCredentials credentials = null;
    private Object syncObj = new Object();

    /* access modifiers changed from: protected */
    public abstract KinesisVideoCredentials updateCredentials();

    protected AbstractKinesisVideoCredentialsProvider() {
    }

    public KinesisVideoCredentials getCredentials() {
        KinesisVideoCredentials kinesisVideoCredentials;
        synchronized (this.syncObj) {
            refreshCredentials(false);
            kinesisVideoCredentials = this.credentials;
        }
        return kinesisVideoCredentials;
    }

    public KinesisVideoCredentials getUpdatedCredentials() {
        KinesisVideoCredentials kinesisVideoCredentials;
        synchronized (this.syncObj) {
            refreshCredentials(true);
            kinesisVideoCredentials = this.credentials;
        }
        return kinesisVideoCredentials;
    }

    private void refreshCredentials(boolean forceUpdate) {
        long currentMillis = System.currentTimeMillis();
        KinesisVideoCredentials kinesisVideoCredentials = this.credentials;
        if (kinesisVideoCredentials == null || forceUpdate || currentMillis <= kinesisVideoCredentials.getExpiration().getTime()) {
            this.credentials = updateCredentials();
        }
    }
}
