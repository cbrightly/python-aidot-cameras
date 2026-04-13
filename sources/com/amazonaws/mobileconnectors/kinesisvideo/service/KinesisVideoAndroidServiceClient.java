package com.amazonaws.mobileconnectors.kinesisvideo.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentials;
import com.amazonaws.kinesisvideo.auth.KinesisVideoCredentialsProvider;
import com.amazonaws.kinesisvideo.client.KinesisVideoClientConfiguration;
import com.amazonaws.kinesisvideo.client.PutMediaClient;
import com.amazonaws.kinesisvideo.common.exception.KinesisVideoException;
import com.amazonaws.kinesisvideo.common.function.Consumer;
import com.amazonaws.kinesisvideo.common.logging.Log;
import com.amazonaws.kinesisvideo.common.preconditions.Preconditions;
import com.amazonaws.kinesisvideo.internal.producer.client.KinesisVideoServiceClient;
import com.amazonaws.kinesisvideo.producer.StreamDescription;
import com.amazonaws.kinesisvideo.producer.StreamStatus;
import com.amazonaws.mobileconnectors.kinesisvideo.signing.KinesisVideoAndroidAWS4Signer;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisvideo.AWSKinesisVideo;
import com.amazonaws.services.kinesisvideo.AWSKinesisVideoClient;
import com.amazonaws.services.kinesisvideo.model.CreateStreamRequest;
import com.amazonaws.services.kinesisvideo.model.CreateStreamResult;
import com.amazonaws.services.kinesisvideo.model.DeleteStreamRequest;
import com.amazonaws.services.kinesisvideo.model.DeleteStreamResult;
import com.amazonaws.services.kinesisvideo.model.DescribeStreamRequest;
import com.amazonaws.services.kinesisvideo.model.DescribeStreamResult;
import com.amazonaws.services.kinesisvideo.model.GetDataEndpointRequest;
import com.amazonaws.services.kinesisvideo.model.GetDataEndpointResult;
import com.amazonaws.services.kinesisvideo.model.TagStreamRequest;
import com.amazonaws.services.kinesisvideo.model.TagStreamResult;
import com.amazonaws.util.StringUtils;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.Map;

public final class KinesisVideoAndroidServiceClient implements KinesisVideoServiceClient {
    private static final String ABSOLUTE_TIMECODE = "ABSOLUTE";
    private static final int RECEIVE_TIMEOUT_1HR = 3600000;
    private static final String RELATIVE_TIMECODE = "RELATIVE";
    private KinesisVideoClientConfiguration configuration;
    private final Log log;

    private static AWSKinesisVideoClient createAwsKinesisVideoClient(KinesisVideoCredentialsProvider credentialsProvider, Region region, String endpoint, int timeoutInMillis) {
        return createAwsKinesisVideoClient(createAwsCredentials(credentialsProvider), region, endpoint, timeoutInMillis);
    }

    private static AWSKinesisVideoClient createAwsKinesisVideoClient(AWSCredentialsProvider awsCredentialsProvider, Region region, String endpoint, int timeoutInMillis) {
        return createAwsKinesisVideoClient(awsCredentialsProvider.getCredentials(), region, endpoint, timeoutInMillis);
    }

    private static AWSKinesisVideoClient createAwsKinesisVideoClient(AWSCredentials credentials, Region region, String endpoint, int timeoutInMillis) {
        AWSKinesisVideoClient awsKinesisVideoClient = new AWSKinesisVideoClient(credentials, createClientConfiguration(timeoutInMillis));
        awsKinesisVideoClient.setRegion(region);
        awsKinesisVideoClient.setSignerRegionOverride(region.getName());
        awsKinesisVideoClient.setServiceNameIntern("kinesisvideo");
        awsKinesisVideoClient.setEndpoint(endpoint);
        return awsKinesisVideoClient;
    }

    private static AWSCredentials createAwsCredentials(@NonNull KinesisVideoCredentialsProvider credentialsProvider) {
        Preconditions.checkNotNull(credentialsProvider);
        final KinesisVideoCredentials kinesisVideoCredentials = credentialsProvider.getCredentials();
        return new AWSSessionCredentials() {
            public String getSessionToken() {
                return kinesisVideoCredentials.getSessionToken();
            }

            public String getAWSAccessKeyId() {
                return kinesisVideoCredentials.getAccessKey();
            }

            public String getAWSSecretKey() {
                return kinesisVideoCredentials.getSecretKey();
            }
        };
    }

    private static ClientConfiguration createClientConfiguration(int timeoutInMillis) {
        return new ClientConfiguration().withProtocol(Protocol.HTTPS).withConnectionTimeout(timeoutInMillis).withMaxConnections(10).withSocketTimeout(timeoutInMillis);
    }

    public KinesisVideoAndroidServiceClient(@NonNull Log log2) {
        this.log = (Log) Preconditions.checkNotNull(log2);
    }

    @NonNull
    public static AWSKinesisVideo getAwsKinesisVideoClient(@NonNull AWSCredentialsProvider credentialsProvider, @NonNull Region region, @NonNull String endpoint, int timeoutInMillis) {
        return createAwsKinesisVideoClient(credentialsProvider, region, endpoint, timeoutInMillis);
    }

    public void initialize(@NonNull KinesisVideoClientConfiguration kinesisVideoClientConfiguration) {
        this.configuration = (KinesisVideoClientConfiguration) Preconditions.checkNotNull(kinesisVideoClientConfiguration);
    }

    public String createStream(@NonNull String streamName, @NonNull String deviceName, @NonNull String contentType, @Nullable String kmsKeyId, long retentionPeriodInHours, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider) {
        AWSKinesisVideoClient serviceClient = createAwsKinesisVideoClient(credentialsProvider, Region.getRegion(Regions.fromName(this.configuration.getRegion())), this.configuration.getEndpoint(), (int) timeoutInMillis);
        CreateStreamRequest createStreamRequest = new CreateStreamRequest().withStreamName(streamName).withDeviceName(deviceName).withMediaType(contentType).withKmsKeyId(StringUtils.isBlank(kmsKeyId) ? null : kmsKeyId).withDataRetentionInHours(Integer.valueOf((int) retentionPeriodInHours)).withTags((Map<String, String>) null);
        Log log2 = this.log;
        log2.debug("calling create stream: " + createStreamRequest.toString());
        try {
            CreateStreamResult createStreamResult = serviceClient.createStream(createStreamRequest);
            Log log3 = this.log;
            log3.debug("create stream result: " + createStreamResult.toString());
            return createStreamResult.getStreamARN();
        } catch (AmazonClientException e) {
            this.log.exception(e, "Service call failed.", new Object[0]);
            throw new KinesisVideoException((Throwable) e);
        }
    }

    public StreamDescription describeStream(@NonNull String streamName, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider) {
        AWSKinesisVideoClient serviceClient = createAwsKinesisVideoClient(credentialsProvider, Region.getRegion(Regions.fromName(this.configuration.getRegion())), this.configuration.getEndpoint(), (int) timeoutInMillis);
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest().withStreamName(streamName);
        Log log2 = this.log;
        log2.debug("calling describe stream: " + describeStreamRequest.toString());
        try {
            DescribeStreamResult describeStreamResult = serviceClient.describeStream(describeStreamRequest);
            if (describeStreamResult == null) {
                this.log.debug("describe stream returned null");
                return null;
            }
            Log log3 = this.log;
            log3.debug("describe stream result: " + describeStreamResult.toString());
            return toStreamDescription(describeStreamResult);
        } catch (AmazonClientException e) {
            this.log.exception(e, "Service call failed.", new Object[0]);
            throw new KinesisVideoException((Throwable) e);
        }
    }

    public void deleteStream(@NonNull String streamName, @NonNull String version, Date creationTime, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider) {
        AWSKinesisVideoClient serviceClient = createAwsKinesisVideoClient(credentialsProvider, Region.getRegion(Regions.fromName(this.configuration.getRegion())), this.configuration.getEndpoint(), (int) timeoutInMillis);
        StreamDescription streamDescription = describeStream(streamName, timeoutInMillis, credentialsProvider);
        DeleteStreamRequest deleteStreamRequest = new DeleteStreamRequest().withStreamARN(streamDescription.getStreamArn()).withCurrentVersion(streamDescription.getUpdateVersion());
        Log log2 = this.log;
        log2.debug("calling delete stream: " + deleteStreamRequest.toString());
        try {
            DeleteStreamResult deleteStreamResult = serviceClient.deleteStream(deleteStreamRequest);
            Log log3 = this.log;
            log3.debug("delete stream result: " + deleteStreamResult.toString());
        } catch (AmazonClientException e) {
            this.log.exception(e, "Service call failed.", new Object[0]);
            throw new KinesisVideoException((Throwable) e);
        }
    }

    public void tagStream(@NonNull String streamArn, @Nullable Map<String, String> tags, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider) {
        AWSKinesisVideoClient serviceClient = createAwsKinesisVideoClient(credentialsProvider, Region.getRegion(Regions.fromName(this.configuration.getRegion())), this.configuration.getEndpoint(), (int) timeoutInMillis);
        TagStreamRequest tagStreamRequest = new TagStreamRequest().withStreamARN(streamArn).withTags(tags);
        Log log2 = this.log;
        log2.debug("calling tag resource: " + tagStreamRequest.toString());
        try {
            TagStreamResult tagStreamResult = serviceClient.tagStream(tagStreamRequest);
            Log log3 = this.log;
            log3.debug("tag resource result: " + tagStreamResult.toString());
        } catch (AmazonClientException e) {
            this.log.exception(e, "Service call failed.", new Object[0]);
            throw new KinesisVideoException((Throwable) e);
        }
    }

    public String getDataEndpoint(@NonNull String streamName, @NonNull String apiName, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider) {
        AWSKinesisVideoClient serviceClient = createAwsKinesisVideoClient(credentialsProvider, Region.getRegion(Regions.fromName(this.configuration.getRegion())), this.configuration.getEndpoint(), (int) timeoutInMillis);
        GetDataEndpointRequest getDataEndpointRequest = new GetDataEndpointRequest().withStreamName(streamName).withAPIName(apiName);
        Log log2 = this.log;
        log2.debug("calling get data endpoint: " + getDataEndpointRequest.toString());
        try {
            GetDataEndpointResult getDataEndpointResult = serviceClient.getDataEndpoint(getDataEndpointRequest);
            Log log3 = this.log;
            log3.debug("get data endpoint result: " + getDataEndpointResult.toString());
            return getDataEndpointResult.getDataEndpoint();
        } catch (AmazonClientException e) {
            this.log.exception(e, "Service call failed.", new Object[0]);
            throw new KinesisVideoException((Throwable) e);
        }
    }

    public void putMedia(@NonNull String streamName, @NonNull String containerType, long streamStartTimeInMillis, boolean absoluteFragmentTimes, boolean ackRequired, @NonNull String dataEndpoint, long timeoutInMillis, @Nullable KinesisVideoCredentialsProvider credentialsProvider, @NonNull InputStream dataInputStream, @NonNull Consumer<InputStream> acksConsumer, @Nullable Consumer<Exception> completionCallback) {
        KinesisVideoAndroidAWS4Signer signer = new KinesisVideoAndroidAWS4Signer(createAwsCredentials(credentialsProvider), this.configuration);
        String str = streamName;
        PutMediaClient.builder().receiveTimeout(Integer.valueOf(RECEIVE_TIMEOUT_1HR)).timestamp(streamStartTimeInMillis).signWith(signer).receiveCompletion(completionCallback).receiveAcks(acksConsumer).streamName(streamName).mkvStream(dataInputStream).fragmentTimecodeType(absoluteFragmentTimes ? ABSOLUTE_TIMECODE : RELATIVE_TIMECODE).putMediaDestinationUri(URI.create(dataEndpoint + "/putMedia")).build().putMediaInBackground();
    }

    private static StreamDescription toStreamDescription(@NonNull DescribeStreamResult result) {
        Preconditions.checkNotNull(result);
        return new StreamDescription(0, result.getStreamInfo().getDeviceName(), result.getStreamInfo().getStreamName(), result.getStreamInfo().getMediaType(), result.getStreamInfo().getVersion(), result.getStreamInfo().getStreamARN(), StreamStatus.valueOf(result.getStreamInfo().getStatus()), result.getStreamInfo().getCreationTime().getTime());
    }
}
