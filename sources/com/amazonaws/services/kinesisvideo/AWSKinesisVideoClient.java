package com.amazonaws.services.kinesisvideo;

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
import com.amazonaws.services.kinesisvideo.model.CreateSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.CreateSignalingChannelResult;
import com.amazonaws.services.kinesisvideo.model.CreateStreamRequest;
import com.amazonaws.services.kinesisvideo.model.CreateStreamResult;
import com.amazonaws.services.kinesisvideo.model.DeleteSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.DeleteSignalingChannelResult;
import com.amazonaws.services.kinesisvideo.model.DeleteStreamRequest;
import com.amazonaws.services.kinesisvideo.model.DeleteStreamResult;
import com.amazonaws.services.kinesisvideo.model.DescribeSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.DescribeSignalingChannelResult;
import com.amazonaws.services.kinesisvideo.model.DescribeStreamRequest;
import com.amazonaws.services.kinesisvideo.model.DescribeStreamResult;
import com.amazonaws.services.kinesisvideo.model.GetDataEndpointRequest;
import com.amazonaws.services.kinesisvideo.model.GetDataEndpointResult;
import com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointRequest;
import com.amazonaws.services.kinesisvideo.model.GetSignalingChannelEndpointResult;
import com.amazonaws.services.kinesisvideo.model.ListSignalingChannelsRequest;
import com.amazonaws.services.kinesisvideo.model.ListSignalingChannelsResult;
import com.amazonaws.services.kinesisvideo.model.ListStreamsRequest;
import com.amazonaws.services.kinesisvideo.model.ListStreamsResult;
import com.amazonaws.services.kinesisvideo.model.ListTagsForResourceRequest;
import com.amazonaws.services.kinesisvideo.model.ListTagsForResourceResult;
import com.amazonaws.services.kinesisvideo.model.ListTagsForStreamRequest;
import com.amazonaws.services.kinesisvideo.model.ListTagsForStreamResult;
import com.amazonaws.services.kinesisvideo.model.TagResourceRequest;
import com.amazonaws.services.kinesisvideo.model.TagResourceResult;
import com.amazonaws.services.kinesisvideo.model.TagStreamRequest;
import com.amazonaws.services.kinesisvideo.model.TagStreamResult;
import com.amazonaws.services.kinesisvideo.model.UntagResourceRequest;
import com.amazonaws.services.kinesisvideo.model.UntagResourceResult;
import com.amazonaws.services.kinesisvideo.model.UntagStreamRequest;
import com.amazonaws.services.kinesisvideo.model.UntagStreamResult;
import com.amazonaws.services.kinesisvideo.model.UpdateDataRetentionRequest;
import com.amazonaws.services.kinesisvideo.model.UpdateDataRetentionResult;
import com.amazonaws.services.kinesisvideo.model.UpdateSignalingChannelRequest;
import com.amazonaws.services.kinesisvideo.model.UpdateSignalingChannelResult;
import com.amazonaws.services.kinesisvideo.model.UpdateStreamRequest;
import com.amazonaws.services.kinesisvideo.model.UpdateStreamResult;
import com.amazonaws.services.kinesisvideo.model.transform.AccessDeniedExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.AccountChannelLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.AccountStreamLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ClientLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.CreateSignalingChannelRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.CreateSignalingChannelResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.CreateStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.CreateStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DeleteSignalingChannelRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DeleteSignalingChannelResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DeleteStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DeleteStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DescribeSignalingChannelRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DescribeSignalingChannelResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DescribeStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DescribeStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.DeviceStreamLimitExceededExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.GetDataEndpointRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.GetDataEndpointResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.GetSignalingChannelEndpointRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.GetSignalingChannelEndpointResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.InvalidArgumentExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.InvalidDeviceExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.InvalidResourceFormatExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListSignalingChannelsRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListSignalingChannelsResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListStreamsRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListStreamsResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListTagsForResourceRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListTagsForResourceResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListTagsForStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ListTagsForStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.NotAuthorizedExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ResourceInUseExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.TagResourceRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.TagResourceResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.TagStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.TagStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.TagsPerResourceExceededLimitExceptionUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UntagResourceRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UntagResourceResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UntagStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UntagStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateDataRetentionRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateDataRetentionResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateSignalingChannelRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateSignalingChannelResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateStreamRequestMarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.UpdateStreamResultJsonUnmarshaller;
import com.amazonaws.services.kinesisvideo.model.transform.VersionMismatchExceptionUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import java.util.ArrayList;
import java.util.List;

public class AWSKinesisVideoClient extends AmazonWebServiceClient implements AWSKinesisVideo {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    @Deprecated
    public AWSKinesisVideoClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AWSKinesisVideoClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AWSKinesisVideoClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AWSKinesisVideoClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AWSKinesisVideoClient(AWSCredentialsProvider awsCredentialsProvider2) {
        this(awsCredentialsProvider2, new ClientConfiguration());
    }

    public AWSKinesisVideoClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider2, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AWSKinesisVideoClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    public AWSKinesisVideoClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    private void init() {
        ArrayList arrayList = new ArrayList();
        this.jsonErrorUnmarshallers = arrayList;
        arrayList.add(new AccessDeniedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new AccountChannelLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new AccountStreamLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ClientLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new DeviceStreamLimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidArgumentExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidDeviceExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidResourceFormatExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new NotAuthorizedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceInUseExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new TagsPerResourceExceededLimitExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new VersionMismatchExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("https://kinesisvideo.us-west-2.amazonaws.com");
        this.endpointPrefix = "kinesisvideo";
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/kinesisvideo/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/kinesisvideo/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        return orig;
    }

    public CreateSignalingChannelResult createSignalingChannel(CreateSignalingChannelRequest createSignalingChannelRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) createSignalingChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<CreateSignalingChannelRequest> request = null;
        Response<CreateSignalingChannelResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new CreateSignalingChannelRequestMarshaller().marshall(createSignalingChannelRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new CreateSignalingChannelResultJsonUnmarshaller()), executionContext);
            CreateSignalingChannelResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public CreateStreamResult createStream(CreateStreamRequest createStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) createStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<CreateStreamRequest> request = null;
        Response<CreateStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new CreateStreamRequestMarshaller().marshall(createStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new CreateStreamResultJsonUnmarshaller()), executionContext);
            CreateStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DeleteSignalingChannelResult deleteSignalingChannel(DeleteSignalingChannelRequest deleteSignalingChannelRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteSignalingChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DeleteSignalingChannelRequest> request = null;
        Response<DeleteSignalingChannelResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DeleteSignalingChannelRequestMarshaller().marshall(deleteSignalingChannelRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DeleteSignalingChannelResultJsonUnmarshaller()), executionContext);
            DeleteSignalingChannelResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DeleteStreamResult deleteStream(DeleteStreamRequest deleteStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DeleteStreamRequest> request = null;
        Response<DeleteStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DeleteStreamRequestMarshaller().marshall(deleteStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DeleteStreamResultJsonUnmarshaller()), executionContext);
            DeleteStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DescribeSignalingChannelResult describeSignalingChannel(DescribeSignalingChannelRequest describeSignalingChannelRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeSignalingChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DescribeSignalingChannelRequest> request = null;
        Response<DescribeSignalingChannelResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DescribeSignalingChannelRequestMarshaller().marshall(describeSignalingChannelRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DescribeSignalingChannelResultJsonUnmarshaller()), executionContext);
            DescribeSignalingChannelResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DescribeStreamResult describeStream(DescribeStreamRequest describeStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DescribeStreamRequest> request = null;
        Response<DescribeStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DescribeStreamRequestMarshaller().marshall(describeStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DescribeStreamResultJsonUnmarshaller()), executionContext);
            DescribeStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetDataEndpointResult getDataEndpoint(GetDataEndpointRequest getDataEndpointRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getDataEndpointRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetDataEndpointRequest> request = null;
        Response<GetDataEndpointResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetDataEndpointRequestMarshaller().marshall(getDataEndpointRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetDataEndpointResultJsonUnmarshaller()), executionContext);
            GetDataEndpointResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetSignalingChannelEndpointResult getSignalingChannelEndpoint(GetSignalingChannelEndpointRequest getSignalingChannelEndpointRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getSignalingChannelEndpointRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetSignalingChannelEndpointRequest> request = null;
        Response<GetSignalingChannelEndpointResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetSignalingChannelEndpointRequestMarshaller().marshall(getSignalingChannelEndpointRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetSignalingChannelEndpointResultJsonUnmarshaller()), executionContext);
            GetSignalingChannelEndpointResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListSignalingChannelsResult listSignalingChannels(ListSignalingChannelsRequest listSignalingChannelsRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listSignalingChannelsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListSignalingChannelsRequest> request = null;
        Response<ListSignalingChannelsResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListSignalingChannelsRequestMarshaller().marshall(listSignalingChannelsRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListSignalingChannelsResultJsonUnmarshaller()), executionContext);
            ListSignalingChannelsResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListStreamsResult listStreams(ListStreamsRequest listStreamsRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listStreamsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListStreamsRequest> request = null;
        Response<ListStreamsResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListStreamsRequestMarshaller().marshall(listStreamsRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListStreamsResultJsonUnmarshaller()), executionContext);
            ListStreamsResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListTagsForResourceResult listTagsForResource(ListTagsForResourceRequest listTagsForResourceRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listTagsForResourceRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListTagsForResourceRequest> request = null;
        Response<ListTagsForResourceResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListTagsForResourceRequestMarshaller().marshall(listTagsForResourceRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListTagsForResourceResultJsonUnmarshaller()), executionContext);
            ListTagsForResourceResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListTagsForStreamResult listTagsForStream(ListTagsForStreamRequest listTagsForStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listTagsForStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListTagsForStreamRequest> request = null;
        Response<ListTagsForStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListTagsForStreamRequestMarshaller().marshall(listTagsForStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListTagsForStreamResultJsonUnmarshaller()), executionContext);
            ListTagsForStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public TagResourceResult tagResource(TagResourceRequest tagResourceRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) tagResourceRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<TagResourceRequest> request = null;
        Response<TagResourceResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new TagResourceRequestMarshaller().marshall(tagResourceRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new TagResourceResultJsonUnmarshaller()), executionContext);
            TagResourceResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public TagStreamResult tagStream(TagStreamRequest tagStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) tagStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<TagStreamRequest> request = null;
        Response<TagStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new TagStreamRequestMarshaller().marshall(tagStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new TagStreamResultJsonUnmarshaller()), executionContext);
            TagStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) untagResourceRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UntagResourceRequest> request = null;
        Response<UntagResourceResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UntagResourceRequestMarshaller().marshall(untagResourceRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UntagResourceResultJsonUnmarshaller()), executionContext);
            UntagResourceResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UntagStreamResult untagStream(UntagStreamRequest untagStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) untagStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UntagStreamRequest> request = null;
        Response<UntagStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UntagStreamRequestMarshaller().marshall(untagStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UntagStreamResultJsonUnmarshaller()), executionContext);
            UntagStreamResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UpdateDataRetentionResult updateDataRetention(UpdateDataRetentionRequest updateDataRetentionRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateDataRetentionRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UpdateDataRetentionRequest> request = null;
        Response<UpdateDataRetentionResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UpdateDataRetentionRequestMarshaller().marshall(updateDataRetentionRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UpdateDataRetentionResultJsonUnmarshaller()), executionContext);
            UpdateDataRetentionResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UpdateSignalingChannelResult updateSignalingChannel(UpdateSignalingChannelRequest updateSignalingChannelRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateSignalingChannelRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UpdateSignalingChannelRequest> request = null;
        Response<UpdateSignalingChannelResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UpdateSignalingChannelRequestMarshaller().marshall(updateSignalingChannelRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UpdateSignalingChannelResultJsonUnmarshaller()), executionContext);
            UpdateSignalingChannelResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public UpdateStreamResult updateStream(UpdateStreamRequest updateStreamRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateStreamRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UpdateStreamRequest> request = null;
        Response<UpdateStreamResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UpdateStreamRequestMarshaller().marshall(updateStreamRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UpdateStreamResultJsonUnmarshaller()), executionContext);
            UpdateStreamResult awsResponse = response.getAwsResponse();
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
