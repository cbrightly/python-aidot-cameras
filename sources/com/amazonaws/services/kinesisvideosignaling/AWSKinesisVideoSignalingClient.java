package com.amazonaws.services.kinesisvideosignaling;

import com.amazonaws.AmazonWebServiceClient;
import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.handlers.HandlerChainFactory;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpClient;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.http.JsonErrorResponseHandler;
import com.amazonaws.http.JsonResponseHandler;
import com.amazonaws.http.UrlHttpClient;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.metrics.MetricType;
import com.amazonaws.metrics.RequestMetricCollector;
import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest;
import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigResult;
import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterRequest;
import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterResult;
import com.amazonaws.services.kinesisvideosignaling.model.transform.ClientLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.GetIceServerConfigRequestMarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.GetIceServerConfigResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.InvalidArgumentExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.InvalidClientExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.NotAuthorizedExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.SendAlexaOfferToMasterRequestMarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.SendAlexaOfferToMasterResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideosignaling.model.transform.SessionExpiredExceptionUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import java.util.ArrayList;
import java.util.List;

public class AWSKinesisVideoSignalingClient extends AmazonWebServiceClient implements AWSKinesisVideoSignaling {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    @Deprecated
    public AWSKinesisVideoSignalingClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AWSKinesisVideoSignalingClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AWSKinesisVideoSignalingClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AWSKinesisVideoSignalingClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AWSKinesisVideoSignalingClient(AWSCredentialsProvider awsCredentialsProvider2) {
        this(awsCredentialsProvider2, new ClientConfiguration());
    }

    public AWSKinesisVideoSignalingClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider2, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AWSKinesisVideoSignalingClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    public AWSKinesisVideoSignalingClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    private void init() {
        ArrayList arrayList = new ArrayList();
        this.jsonErrorUnmarshallers = arrayList;
        arrayList.add(new ClientLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidArgumentExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidClientExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new NotAuthorizedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new SessionExpiredExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("kinesisvideo.us-east-1.amazonaws.com");
        this.endpointPrefix = "kinesisvideo";
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/kinesisvideosignaling/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/kinesisvideosignaling/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        return orig;
    }

    public GetIceServerConfigResult getIceServerConfig(GetIceServerConfigRequest getIceServerConfigRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getIceServerConfigRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetIceServerConfigRequest> request = null;
        Response<GetIceServerConfigResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetIceServerConfigRequestMarshaller().marshall(getIceServerConfigRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetIceServerConfigResultJsonUnmarshaller()), executionContext);
            GetIceServerConfigResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public SendAlexaOfferToMasterResult sendAlexaOfferToMaster(SendAlexaOfferToMasterRequest sendAlexaOfferToMasterRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) sendAlexaOfferToMasterRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<SendAlexaOfferToMasterRequest> request = null;
        Response<SendAlexaOfferToMasterResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new SendAlexaOfferToMasterRequestMarshaller().marshall(sendAlexaOfferToMasterRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new SendAlexaOfferToMasterResultJsonUnmarshaller()), executionContext);
            SendAlexaOfferToMasterResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    @Deprecated
    public ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest request) {
        return this.client.getResponseMetadataForRequest(request);
    }

    private <X, Y extends AmazonWebServiceRequest> Response<X> invoke(Request<Y> request, HttpResponseHandler<AmazonWebServiceResponse<X>> responseHandler, ExecutionContext executionContext) {
        request.setEndpoint(this.endpoint);
        request.setTimeOffset(this.timeOffset);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.CredentialsRequestTime;
        awsRequestMetrics.startEvent((MetricType) field);
        try {
            AWSCredentials credentials = this.awsCredentialsProvider.getCredentials();
            awsRequestMetrics.endEvent((MetricType) field);
            AmazonWebServiceRequest originalRequest = request.getOriginalRequest();
            if (!(originalRequest == null || originalRequest.getRequestCredentials() == null)) {
                credentials = originalRequest.getRequestCredentials();
            }
            executionContext.setCredentials(credentials);
            return this.client.execute(request, responseHandler, new JsonErrorResponseHandler(this.jsonErrorUnmarshallers), executionContext);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.CredentialsRequestTime);
            throw th;
        }
    }
}
