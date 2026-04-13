package com.amazonaws.services.cognitoidentity.model.transform;

import com.amazonaws.services.cognitoidentity.model.DescribeIdentityPoolResult;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

public class DescribeIdentityPoolResultJsonUnmarshaller implements Unmarshaller<DescribeIdentityPoolResult, JsonUnmarshallerContext> {
    private static DescribeIdentityPoolResultJsonUnmarshaller instance;

    public DescribeIdentityPoolResult unmarshall(JsonUnmarshallerContext context) {
        DescribeIdentityPoolResult describeIdentityPoolResult = new DescribeIdentityPoolResult();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("IdentityPoolId")) {
                describeIdentityPoolResult.setIdentityPoolId(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("IdentityPoolName")) {
                describeIdentityPoolResult.setIdentityPoolName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowUnauthenticatedIdentities")) {
                describeIdentityPoolResult.setAllowUnauthenticatedIdentities(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AllowClassicFlow")) {
                describeIdentityPoolResult.setAllowClassicFlow(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("SupportedLoginProviders")) {
                describeIdentityPoolResult.setSupportedLoginProviders(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("DeveloperProviderName")) {
                describeIdentityPoolResult.setDeveloperProviderName(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("OpenIdConnectProviderARNs")) {
                describeIdentityPoolResult.setOpenIdConnectProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("CognitoIdentityProviders")) {
                describeIdentityPoolResult.setCognitoIdentityProviders(new ListUnmarshaller(CognitoIdentityProviderJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("SamlProviderARNs")) {
                describeIdentityPoolResult.setSamlProviderARNs(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("IdentityPoolTags")) {
                describeIdentityPoolResult.setIdentityPoolTags(new MapUnmarshaller(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return describeIdentityPoolResult;
    }

    public static DescribeIdentityPoolResultJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new DescribeIdentityPoolResultJsonUnmarshaller();
        }
        return instance;
    }
}
