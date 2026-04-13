package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.AmazonClientException;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.cognitoidentity.model.RoleMapping;
import com.amazonaws.services.cognitoidentity.model.SetIdentityPoolRolesRequest;
import com.amazonaws.transform.Marshaller;
import com.amazonaws.util.StringInputStream;
import com.amazonaws.util.StringUtils;
import com.amazonaws.util.json.AwsJsonWriter;
import com.amazonaws.util.json.JsonUtils;
import java.io.StringWriter;
import java.util.Map;

public class SetIdentityPoolRolesRequestMarshaller implements Marshaller<Request<SetIdentityPoolRolesRequest>, SetIdentityPoolRolesRequest> {
    public Request<SetIdentityPoolRolesRequest> marshall(SetIdentityPoolRolesRequest setIdentityPoolRolesRequest) {
        if (setIdentityPoolRolesRequest != null) {
            Request<SetIdentityPoolRolesRequest> request = new DefaultRequest<>(setIdentityPoolRolesRequest, "AmazonCognitoIdentity");
            request.addHeader("X-Amz-Target", "AWSCognitoIdentityService.SetIdentityPoolRoles");
            request.setHttpMethod(HttpMethodName.POST);
            request.setResourcePath("/");
            try {
                StringWriter stringWriter = new StringWriter();
                AwsJsonWriter jsonWriter = JsonUtils.getJsonWriter(stringWriter);
                jsonWriter.beginObject();
                if (setIdentityPoolRolesRequest.getIdentityPoolId() != null) {
                    String identityPoolId = setIdentityPoolRolesRequest.getIdentityPoolId();
                    jsonWriter.name("IdentityPoolId");
                    jsonWriter.value(identityPoolId);
                }
                if (setIdentityPoolRolesRequest.getRoles() != null) {
                    Map<String, String> roles = setIdentityPoolRolesRequest.getRoles();
                    jsonWriter.name("Roles");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, String> rolesEntry : roles.entrySet()) {
                        String rolesValue = rolesEntry.getValue();
                        if (rolesValue != null) {
                            jsonWriter.name(rolesEntry.getKey());
                            jsonWriter.value(rolesValue);
                        }
                    }
                    jsonWriter.endObject();
                }
                if (setIdentityPoolRolesRequest.getRoleMappings() != null) {
                    Map<String, RoleMapping> roleMappings = setIdentityPoolRolesRequest.getRoleMappings();
                    jsonWriter.name("RoleMappings");
                    jsonWriter.beginObject();
                    for (Map.Entry<String, RoleMapping> roleMappingsEntry : roleMappings.entrySet()) {
                        RoleMapping roleMappingsValue = roleMappingsEntry.getValue();
                        if (roleMappingsValue != null) {
                            jsonWriter.name(roleMappingsEntry.getKey());
                            RoleMappingJsonMarshaller.getInstance().marshall(roleMappingsValue, jsonWriter);
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
            throw new AmazonClientException("Invalid argument passed to marshall(SetIdentityPoolRolesRequest)");
        }
    }
}
