package com.amazonaws.services.securitytoken;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleResult;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithSAMLRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithSAMLResult;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityRequest;
import com.amazonaws.services.securitytoken.model.AssumeRoleWithWebIdentityResult;
import com.amazonaws.services.securitytoken.model.DecodeAuthorizationMessageRequest;
import com.amazonaws.services.securitytoken.model.DecodeAuthorizationMessageResult;
import com.amazonaws.services.securitytoken.model.GetAccessKeyInfoRequest;
import com.amazonaws.services.securitytoken.model.GetAccessKeyInfoResult;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityRequest;
import com.amazonaws.services.securitytoken.model.GetCallerIdentityResult;
import com.amazonaws.services.securitytoken.model.GetFederationTokenRequest;
import com.amazonaws.services.securitytoken.model.GetFederationTokenResult;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import com.amazonaws.services.securitytoken.model.GetSessionTokenResult;

public interface AWSSecurityTokenService {
    AssumeRoleResult assumeRole(AssumeRoleRequest assumeRoleRequest);

    AssumeRoleWithSAMLResult assumeRoleWithSAML(AssumeRoleWithSAMLRequest assumeRoleWithSAMLRequest);

    AssumeRoleWithWebIdentityResult assumeRoleWithWebIdentity(AssumeRoleWithWebIdentityRequest assumeRoleWithWebIdentityRequest);

    DecodeAuthorizationMessageResult decodeAuthorizationMessage(DecodeAuthorizationMessageRequest decodeAuthorizationMessageRequest);

    GetAccessKeyInfoResult getAccessKeyInfo(GetAccessKeyInfoRequest getAccessKeyInfoRequest);

    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    GetCallerIdentityResult getCallerIdentity();

    GetCallerIdentityResult getCallerIdentity(GetCallerIdentityRequest getCallerIdentityRequest);

    GetFederationTokenResult getFederationToken(GetFederationTokenRequest getFederationTokenRequest);

    GetSessionTokenResult getSessionToken();

    GetSessionTokenResult getSessionToken(GetSessionTokenRequest getSessionTokenRequest);

    void setEndpoint(String str);

    void setRegion(Region region);

    void shutdown();
}
