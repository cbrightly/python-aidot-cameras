package com.amazonaws.auth.policy.actions;

import com.amazonaws.auth.policy.Action;

public enum SecurityTokenServiceActions implements Action {
    AllSecurityTokenServiceActions("sts:*"),
    AssumeRole("sts:AssumeRole"),
    AssumeRoleWithSAML("sts:AssumeRoleWithSAML"),
    AssumeRoleWithWebIdentity("sts:AssumeRoleWithWebIdentity"),
    DecodeAuthorizationMessage("sts:DecodeAuthorizationMessage"),
    GetAccessKeyInfo("sts:GetAccessKeyInfo"),
    GetCallerIdentity("sts:GetCallerIdentity"),
    GetFederationToken("sts:GetFederationToken"),
    GetSessionToken("sts:GetSessionToken");
    
    private final String action;

    private SecurityTokenServiceActions(String action2) {
        this.action = action2;
    }

    public String getActionName() {
        return this.action;
    }
}
