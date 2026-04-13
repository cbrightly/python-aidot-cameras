package com.amazonaws.services.cognitoidentity;

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
import com.amazonaws.services.cognitoidentity.model.CreateIdentityPoolRequest;
import com.amazonaws.services.cognitoidentity.model.CreateIdentityPoolResult;
import com.amazonaws.services.cognitoidentity.model.DeleteIdentitiesRequest;
import com.amazonaws.services.cognitoidentity.model.DeleteIdentitiesResult;
import com.amazonaws.services.cognitoidentity.model.DeleteIdentityPoolRequest;
import com.amazonaws.services.cognitoidentity.model.DescribeIdentityPoolRequest;
import com.amazonaws.services.cognitoidentity.model.DescribeIdentityPoolResult;
import com.amazonaws.services.cognitoidentity.model.DescribeIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.DescribeIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetCredentialsForIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetIdRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdResult;
import com.amazonaws.services.cognitoidentity.model.GetIdentityPoolRolesRequest;
import com.amazonaws.services.cognitoidentity.model.GetIdentityPoolRolesResult;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenRequest;
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenResult;
import com.amazonaws.services.cognitoidentity.model.ListIdentitiesRequest;
import com.amazonaws.services.cognitoidentity.model.ListIdentitiesResult;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsRequest;
import com.amazonaws.services.cognitoidentity.model.ListIdentityPoolsResult;
import com.amazonaws.services.cognitoidentity.model.ListTagsForResourceRequest;
import com.amazonaws.services.cognitoidentity.model.ListTagsForResourceResult;
import com.amazonaws.services.cognitoidentity.model.LookupDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.LookupDeveloperIdentityResult;
import com.amazonaws.services.cognitoidentity.model.MergeDeveloperIdentitiesRequest;
import com.amazonaws.services.cognitoidentity.model.MergeDeveloperIdentitiesResult;
import com.amazonaws.services.cognitoidentity.model.SetIdentityPoolRolesRequest;
import com.amazonaws.services.cognitoidentity.model.TagResourceRequest;
import com.amazonaws.services.cognitoidentity.model.TagResourceResult;
import com.amazonaws.services.cognitoidentity.model.UnlinkDeveloperIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.UnlinkIdentityRequest;
import com.amazonaws.services.cognitoidentity.model.UntagResourceRequest;
import com.amazonaws.services.cognitoidentity.model.UntagResourceResult;
import com.amazonaws.services.cognitoidentity.model.UpdateIdentityPoolRequest;
import com.amazonaws.services.cognitoidentity.model.UpdateIdentityPoolResult;
import com.amazonaws.services.cognitoidentity.model.transform.ConcurrentModificationExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.CreateIdentityPoolRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.CreateIdentityPoolResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DeleteIdentitiesRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DeleteIdentitiesResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DeleteIdentityPoolRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DescribeIdentityPoolRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DescribeIdentityPoolResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DescribeIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DescribeIdentityResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.DeveloperUserAlreadyRegisteredExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ExternalServiceExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetCredentialsForIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetCredentialsForIdentityResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetIdRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetIdResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetIdentityPoolRolesRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetIdentityPoolRolesResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetOpenIdTokenForDeveloperIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetOpenIdTokenRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.GetOpenIdTokenResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.InternalErrorExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.InvalidIdentityPoolConfigurationExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.InvalidParameterExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.LimitExceededExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListIdentitiesRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListIdentitiesResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListIdentityPoolsRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListIdentityPoolsResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListTagsForResourceRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ListTagsForResourceResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.LookupDeveloperIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.LookupDeveloperIdentityResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.MergeDeveloperIdentitiesRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.MergeDeveloperIdentitiesResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.NotAuthorizedExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ResourceConflictExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.ResourceNotFoundExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.SetIdentityPoolRolesRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.TagResourceRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.TagResourceResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.TooManyRequestsExceptionUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UnlinkDeveloperIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UnlinkIdentityRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UntagResourceRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UntagResourceResultJsonUnmarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UpdateIdentityPoolRequestMarshaller;
import com.amazonaws.services.cognitoidentity.model.transform.UpdateIdentityPoolResultJsonUnmarshaller;
import com.amazonaws.transform.JsonErrorUnmarshaller;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.AWSRequestMetrics;
import java.util.ArrayList;
import java.util.List;

public class AmazonCognitoIdentityClient extends AmazonWebServiceClient implements AmazonCognitoIdentity {
    private AWSCredentialsProvider awsCredentialsProvider;
    protected List<JsonErrorUnmarshaller> jsonErrorUnmarshallers;

    @Deprecated
    public AmazonCognitoIdentityClient() {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
    }

    @Deprecated
    public AmazonCognitoIdentityClient(ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new DefaultAWSCredentialsProviderChain(), clientConfiguration);
    }

    public AmazonCognitoIdentityClient(AWSCredentials awsCredentials) {
        this(awsCredentials, new ClientConfiguration());
    }

    public AmazonCognitoIdentityClient(AWSCredentials awsCredentials, ClientConfiguration clientConfiguration) {
        this((AWSCredentialsProvider) new StaticCredentialsProvider(awsCredentials), clientConfiguration);
    }

    public AmazonCognitoIdentityClient(AWSCredentialsProvider awsCredentialsProvider2) {
        this(awsCredentialsProvider2, new ClientConfiguration());
    }

    public AmazonCognitoIdentityClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration) {
        this(awsCredentialsProvider2, clientConfiguration, (HttpClient) new UrlHttpClient(clientConfiguration));
    }

    @Deprecated
    public AmazonCognitoIdentityClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, RequestMetricCollector requestMetricCollector) {
        super(adjustClientConfiguration(clientConfiguration), requestMetricCollector);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    public AmazonCognitoIdentityClient(AWSCredentialsProvider awsCredentialsProvider2, ClientConfiguration clientConfiguration, HttpClient httpClient) {
        super(adjustClientConfiguration(clientConfiguration), httpClient);
        this.awsCredentialsProvider = awsCredentialsProvider2;
        init();
    }

    private void init() {
        ArrayList arrayList = new ArrayList();
        this.jsonErrorUnmarshallers = arrayList;
        arrayList.add(new ConcurrentModificationExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new DeveloperUserAlreadyRegisteredExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ExternalServiceExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InternalErrorExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidIdentityPoolConfigurationExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new InvalidParameterExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new LimitExceededExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new NotAuthorizedExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceConflictExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new ResourceNotFoundExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new TooManyRequestsExceptionUnmarshaller());
        this.jsonErrorUnmarshallers.add(new JsonErrorUnmarshaller());
        setEndpoint("cognito-identity.us-east-1.amazonaws.com");
        this.endpointPrefix = "cognito-identity";
        HandlerChainFactory chainFactory = new HandlerChainFactory();
        this.requestHandler2s.addAll(chainFactory.newRequestHandlerChain("/com/amazonaws/services/cognitoidentity/request.handlers"));
        this.requestHandler2s.addAll(chainFactory.newRequestHandler2Chain("/com/amazonaws/services/cognitoidentity/request.handler2s"));
    }

    private static ClientConfiguration adjustClientConfiguration(ClientConfiguration orig) {
        return orig;
    }

    public CreateIdentityPoolResult createIdentityPool(CreateIdentityPoolRequest createIdentityPoolRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) createIdentityPoolRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<CreateIdentityPoolRequest> request = null;
        Response<CreateIdentityPoolResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new CreateIdentityPoolRequestMarshaller().marshall(createIdentityPoolRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new CreateIdentityPoolResultJsonUnmarshaller()), executionContext);
            CreateIdentityPoolResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DeleteIdentitiesResult deleteIdentities(DeleteIdentitiesRequest deleteIdentitiesRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteIdentitiesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DeleteIdentitiesRequest> request = null;
        Response<DeleteIdentitiesResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DeleteIdentitiesRequestMarshaller().marshall(deleteIdentitiesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DeleteIdentitiesResultJsonUnmarshaller()), executionContext);
            DeleteIdentitiesResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public void deleteIdentityPool(DeleteIdentityPoolRequest deleteIdentityPoolRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) deleteIdentityPoolRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DeleteIdentityPoolRequest> request = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DeleteIdentityPoolRequestMarshaller().marshall(deleteIdentityPoolRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            invoke(request, new JsonResponseHandler<>((Unmarshaller) null), executionContext);
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
            throw th;
        }
    }

    public DescribeIdentityResult describeIdentity(DescribeIdentityRequest describeIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DescribeIdentityRequest> request = null;
        Response<DescribeIdentityResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DescribeIdentityRequestMarshaller().marshall(describeIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DescribeIdentityResultJsonUnmarshaller()), executionContext);
            DescribeIdentityResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public DescribeIdentityPoolResult describeIdentityPool(DescribeIdentityPoolRequest describeIdentityPoolRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) describeIdentityPoolRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<DescribeIdentityPoolRequest> request = null;
        Response<DescribeIdentityPoolResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new DescribeIdentityPoolRequestMarshaller().marshall(describeIdentityPoolRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new DescribeIdentityPoolResultJsonUnmarshaller()), executionContext);
            DescribeIdentityPoolResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetCredentialsForIdentityResult getCredentialsForIdentity(GetCredentialsForIdentityRequest getCredentialsForIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getCredentialsForIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetCredentialsForIdentityRequest> request = null;
        Response<GetCredentialsForIdentityResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetCredentialsForIdentityRequestMarshaller().marshall(getCredentialsForIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetCredentialsForIdentityResultJsonUnmarshaller()), executionContext);
            GetCredentialsForIdentityResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetIdResult getId(GetIdRequest getIdRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getIdRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetIdRequest> request = null;
        Response<GetIdResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetIdRequestMarshaller().marshall(getIdRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetIdResultJsonUnmarshaller()), executionContext);
            GetIdResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetIdentityPoolRolesResult getIdentityPoolRoles(GetIdentityPoolRolesRequest getIdentityPoolRolesRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getIdentityPoolRolesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetIdentityPoolRolesRequest> request = null;
        Response<GetIdentityPoolRolesResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetIdentityPoolRolesRequestMarshaller().marshall(getIdentityPoolRolesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetIdentityPoolRolesResultJsonUnmarshaller()), executionContext);
            GetIdentityPoolRolesResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetOpenIdTokenResult getOpenIdToken(GetOpenIdTokenRequest getOpenIdTokenRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getOpenIdTokenRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetOpenIdTokenRequest> request = null;
        Response<GetOpenIdTokenResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetOpenIdTokenRequestMarshaller().marshall(getOpenIdTokenRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetOpenIdTokenResultJsonUnmarshaller()), executionContext);
            GetOpenIdTokenResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public GetOpenIdTokenForDeveloperIdentityResult getOpenIdTokenForDeveloperIdentity(GetOpenIdTokenForDeveloperIdentityRequest getOpenIdTokenForDeveloperIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) getOpenIdTokenForDeveloperIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<GetOpenIdTokenForDeveloperIdentityRequest> request = null;
        Response<GetOpenIdTokenForDeveloperIdentityResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new GetOpenIdTokenForDeveloperIdentityRequestMarshaller().marshall(getOpenIdTokenForDeveloperIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new GetOpenIdTokenForDeveloperIdentityResultJsonUnmarshaller()), executionContext);
            GetOpenIdTokenForDeveloperIdentityResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListIdentitiesResult listIdentities(ListIdentitiesRequest listIdentitiesRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listIdentitiesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListIdentitiesRequest> request = null;
        Response<ListIdentitiesResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListIdentitiesRequestMarshaller().marshall(listIdentitiesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListIdentitiesResultJsonUnmarshaller()), executionContext);
            ListIdentitiesResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public ListIdentityPoolsResult listIdentityPools(ListIdentityPoolsRequest listIdentityPoolsRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) listIdentityPoolsRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<ListIdentityPoolsRequest> request = null;
        Response<ListIdentityPoolsResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new ListIdentityPoolsRequestMarshaller().marshall(listIdentityPoolsRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new ListIdentityPoolsResultJsonUnmarshaller()), executionContext);
            ListIdentityPoolsResult awsResponse = response.getAwsResponse();
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

    public LookupDeveloperIdentityResult lookupDeveloperIdentity(LookupDeveloperIdentityRequest lookupDeveloperIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) lookupDeveloperIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<LookupDeveloperIdentityRequest> request = null;
        Response<LookupDeveloperIdentityResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new LookupDeveloperIdentityRequestMarshaller().marshall(lookupDeveloperIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new LookupDeveloperIdentityResultJsonUnmarshaller()), executionContext);
            LookupDeveloperIdentityResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public MergeDeveloperIdentitiesResult mergeDeveloperIdentities(MergeDeveloperIdentitiesRequest mergeDeveloperIdentitiesRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) mergeDeveloperIdentitiesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<MergeDeveloperIdentitiesRequest> request = null;
        Response<MergeDeveloperIdentitiesResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new MergeDeveloperIdentitiesRequestMarshaller().marshall(mergeDeveloperIdentitiesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new MergeDeveloperIdentitiesResultJsonUnmarshaller()), executionContext);
            MergeDeveloperIdentitiesResult awsResponse = response.getAwsResponse();
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, response, true);
            return awsResponse;
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, response, true);
            throw th;
        }
    }

    public void setIdentityPoolRoles(SetIdentityPoolRolesRequest setIdentityPoolRolesRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) setIdentityPoolRolesRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<SetIdentityPoolRolesRequest> request = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new SetIdentityPoolRolesRequestMarshaller().marshall(setIdentityPoolRolesRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            invoke(request, new JsonResponseHandler<>((Unmarshaller) null), executionContext);
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
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

    public void unlinkDeveloperIdentity(UnlinkDeveloperIdentityRequest unlinkDeveloperIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) unlinkDeveloperIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UnlinkDeveloperIdentityRequest> request = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UnlinkDeveloperIdentityRequestMarshaller().marshall(unlinkDeveloperIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            invoke(request, new JsonResponseHandler<>((Unmarshaller) null), executionContext);
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
            throw th;
        }
    }

    public void unlinkIdentity(UnlinkIdentityRequest unlinkIdentityRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) unlinkIdentityRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UnlinkIdentityRequest> request = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UnlinkIdentityRequestMarshaller().marshall(unlinkIdentityRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            invoke(request, new JsonResponseHandler<>((Unmarshaller) null), executionContext);
            awsRequestMetrics.endEvent((MetricType) field);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
        } catch (Throwable th) {
            awsRequestMetrics.endEvent((MetricType) AWSRequestMetrics.Field.ClientExecuteTime);
            endClientExecution(awsRequestMetrics, request, (Response<?>) null, true);
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

    public UpdateIdentityPoolResult updateIdentityPool(UpdateIdentityPoolRequest updateIdentityPoolRequest) {
        ExecutionContext executionContext = createExecutionContext((AmazonWebServiceRequest) updateIdentityPoolRequest);
        AWSRequestMetrics awsRequestMetrics = executionContext.getAwsRequestMetrics();
        AWSRequestMetrics.Field field = AWSRequestMetrics.Field.ClientExecuteTime;
        awsRequestMetrics.startEvent((MetricType) field);
        Request<UpdateIdentityPoolRequest> request = null;
        Response<UpdateIdentityPoolResult> response = null;
        try {
            AWSRequestMetrics.Field field2 = AWSRequestMetrics.Field.RequestMarshallTime;
            awsRequestMetrics.startEvent((MetricType) field2);
            request = new UpdateIdentityPoolRequestMarshaller().marshall(updateIdentityPoolRequest);
            request.setAWSRequestMetrics(awsRequestMetrics);
            awsRequestMetrics.endEvent((MetricType) field2);
            response = invoke(request, new JsonResponseHandler<>(new UpdateIdentityPoolResultJsonUnmarshaller()), executionContext);
            UpdateIdentityPoolResult awsResponse = response.getAwsResponse();
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
