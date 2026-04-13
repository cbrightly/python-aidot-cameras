package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.CognitoIdentityProvider;
import com.amazonaws.services.cognitoidentity.model.CreateIdentityPoolRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

public class CreateIdentityPoolRequestMarshaller implements Marshaller<Request<CreateIdentityPoolRequest>, CreateIdentityPoolRequest> {
    public Request<CreateIdentityPoolRequest> marshall(CreateIdentityPoolRequest createIdentityPoolRequest) {
        if (createIdentityPoolRequest != null) {
            Request<CreateIdentityPoolRequest> request = new DefaultRequest<>(createIdentityPoolRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.CreateIdentityPool");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (createIdentityPoolRequest.getIdentityPoolName() != null) {
                    String identityPoolName = createIdentityPoolRequest.getIdentityPoolName();
                    jsonWriter.name("IdentityPoolName");
                    jsonWriter.value(identityPoolName);
                }
                if (createIdentityPoolRequest.getAllowUnauthenticatedIdentities() != null) {
                    Boolean allowUnauthenticatedIdentities = createIdentityPoolRequest.getAllowUnauthenticatedIdentities();
                    jsonWriter.name("AllowUnauthenticatedIdentities");
                    jsonWriter.value(allowUnauthenticatedIdentities.booleanValue());
                }
                if (createIdentityPoolRequest.getAllowClassicFlow() != null) {
                    Boolean allowClassicFlow = createIdentityPoolRequest.getAllowClassicFlow();
                    jsonWriter.name("AllowClassicFlow");
                    jsonWriter.value(allowClassicFlow.booleanValue());
                }
                if (createIdentityPoolRequest.getSupportedLoginProviders() != null) {
                    Map<String, String> supportedLoginProviders = createIdentityPoolRequest.getSupportedLoginProviders();
                    jsonWriter.name("SupportedLoginProviders");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, String> supportedLoginProvidersEntry : supportedLoginProviders.entrySet()) {
                        String supportedLoginProvidersValue = supportedLoginProvidersEntry.getValue();
                        if (supportedLoginProvidersValue != null) {
                            jsonWriter.name(supportedLoginProvidersEntry.getKey());
                            jsonWriter.value(supportedLoginProvidersValue);
                        }
                    }
                    jsonWriter.endObject();
                }
                if (createIdentityPoolRequest.getDeveloperProviderName() != null) {
                    String developerProviderName = createIdentityPoolRequest.getDeveloperProviderName();
                    jsonWriter.name("DeveloperProviderName");
                    jsonWriter.value(developerProviderName);
                }
                if (createIdentityPoolRequest.getOpenIdConnectProviderARNs() != null) {
                    List<String> openIdConnectProviderARNs = createIdentityPoolRequest.getOpenIdConnectProviderARNs();
                    jsonWriter.name("OpenIdConnectProviderARNs");
                    jsonWriter.beginArray();
                    for (String openIdConnectProviderARNsItem : openIdConnectProviderARNs) {
                        if (openIdConnectProviderARNsItem != null) {
                            jsonWriter.value(openIdConnectProviderARNsItem);
                        }
                    }
                    jsonWriter.endArray();
                }
                if (createIdentityPoolRequest.getCognitoIdentityProviders() != null) {
                    List<CognitoIdentityProvider> cognitoIdentityProviders = createIdentityPoolRequest.getCognitoIdentityProviders();
                    jsonWriter.name("CognitoIdentityProviders");
                    jsonWriter.beginArray();
                    for (CognitoIdentityProvider cognitoIdentityProvidersItem : cognitoIdentityProviders) {
                        if (cognitoIdentityProvidersItem != null) {
                            CognitoIdentityProviderJsonMarshaller.getInstance().marshall(cognitoIdentityProvidersItem, jsonWriter);
                        }
                    }
                    jsonWriter.endArray();
                }
                if (createIdentityPoolRequest.getSamlProviderARNs() != null) {
                    List<String> samlProviderARNs = createIdentityPoolRequest.getSamlProviderARNs();
                    jsonWriter.name("SamlProviderARNs");
                    jsonWriter.beginArray();
                    for (String samlProviderARNsItem : samlProviderARNs) {
                        if (samlProviderARNsItem != null) {
                            jsonWriter.value(samlProviderARNsItem);
                        }
                    }
                    jsonWriter.endArray();
                }
                if (createIdentityPoolRequest.getIdentityPoolTags() != null) {
                    Map<String, String> identityPoolTags = createIdentityPoolRequest.getIdentityPoolTags();
                    jsonWriter.name("IdentityPoolTags");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, String> identityPoolTagsEntry : identityPoolTags.entrySet()) {
                        String identityPoolTagsValue = identityPoolTagsEntry.getValue();
                        if (identityPoolTagsValue != null) {
                            jsonWriter.name(identityPoolTagsEntry.getKey());
                            jsonWriter.value(identityPoolTagsValue);
                        }
                    }
                    jsonWriter.endObject();
                }
                jsonWriter.endObject();
                jsonWriter.close();
                String snippet = stringWriter.toString();
                byte[] content = snippet.getBytes(StringUtils.UTF8);
                request.setContent(new StringInputStream(snippet));
                request.addHeader("Content-Length", Integer.toString(content.length));
                if (!request.getHeaders().containsKey("Content-Type")) {
                    request.addHeader("Content-Type", "application/x-amz-json-1.1");
                }
                return request;
            } catch (Throwable t) {
                throw new AmazonClientException("Unable to marshall request to JSON: " + t.getMessage(), t);
            }
        } else {
            throw new AmazonClientException("Invalid argument passed to marshall(CreateIdentityPoolRequest)");
        }
    }
}
