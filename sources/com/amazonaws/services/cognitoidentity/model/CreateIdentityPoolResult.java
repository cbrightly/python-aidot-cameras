package com.amazonaws.services.cognitoidentity.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateIdentityPoolResult implements Serializable {
    private Boolean allowClassicFlow;
    private Boolean allowUnauthenticatedIdentities;
    private List<CognitoIdentityProvider> cognitoIdentityProviders;
    private String developerProviderName;
    private String identityPoolId;
    private String identityPoolName;
    private Map<String, String> identityPoolTags;
    private List<String> openIdConnectProviderARNs;
    private List<String> samlProviderARNs;
    private Map<String, String> supportedLoginProviders;

    public String getIdentityPoolId() {
        return this.identityPoolId;
    }

    public void setIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
    }

    public CreateIdentityPoolResult withIdentityPoolId(String identityPoolId2) {
        this.identityPoolId = identityPoolId2;
        return this;
    }

    public String getIdentityPoolName() {
        return this.identityPoolName;
    }

    public void setIdentityPoolName(String identityPoolName2) {
        this.identityPoolName = identityPoolName2;
    }

    public CreateIdentityPoolResult withIdentityPoolName(String identityPoolName2) {
        this.identityPoolName = identityPoolName2;
        return this;
    }

    public Boolean isAllowUnauthenticatedIdentities() {
        return this.allowUnauthenticatedIdentities;
    }

    public Boolean getAllowUnauthenticatedIdentities() {
        return this.allowUnauthenticatedIdentities;
    }

    public void setAllowUnauthenticatedIdentities(Boolean allowUnauthenticatedIdentities2) {
        this.allowUnauthenticatedIdentities = allowUnauthenticatedIdentities2;
    }

    public CreateIdentityPoolResult withAllowUnauthenticatedIdentities(Boolean allowUnauthenticatedIdentities2) {
        this.allowUnauthenticatedIdentities = allowUnauthenticatedIdentities2;
        return this;
    }

    public Boolean isAllowClassicFlow() {
        return this.allowClassicFlow;
    }

    public Boolean getAllowClassicFlow() {
        return this.allowClassicFlow;
    }

    public void setAllowClassicFlow(Boolean allowClassicFlow2) {
        this.allowClassicFlow = allowClassicFlow2;
    }

    public CreateIdentityPoolResult withAllowClassicFlow(Boolean allowClassicFlow2) {
        this.allowClassicFlow = allowClassicFlow2;
        return this;
    }

    public Map<String, String> getSupportedLoginProviders() {
        return this.supportedLoginProviders;
    }

    public void setSupportedLoginProviders(Map<String, String> supportedLoginProviders2) {
        this.supportedLoginProviders = supportedLoginProviders2;
    }

    public CreateIdentityPoolResult withSupportedLoginProviders(Map<String, String> supportedLoginProviders2) {
        this.supportedLoginProviders = supportedLoginProviders2;
        return this;
    }

    public CreateIdentityPoolResult addSupportedLoginProvidersEntry(String key, String value) {
        if (this.supportedLoginProviders == null) {
            this.supportedLoginProviders = new HashMap();
        }
        if (!this.supportedLoginProviders.containsKey(key)) {
            this.supportedLoginProviders.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public CreateIdentityPoolResult clearSupportedLoginProvidersEntries() {
        this.supportedLoginProviders = null;
        return this;
    }

    public String getDeveloperProviderName() {
        return this.developerProviderName;
    }

    public void setDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
    }

    public CreateIdentityPoolResult withDeveloperProviderName(String developerProviderName2) {
        this.developerProviderName = developerProviderName2;
        return this;
    }

    public List<String> getOpenIdConnectProviderARNs() {
        return this.openIdConnectProviderARNs;
    }

    public void setOpenIdConnectProviderARNs(Collection<String> openIdConnectProviderARNs2) {
        if (openIdConnectProviderARNs2 == null) {
            this.openIdConnectProviderARNs = null;
        } else {
            this.openIdConnectProviderARNs = new ArrayList(openIdConnectProviderARNs2);
        }
    }

    public CreateIdentityPoolResult withOpenIdConnectProviderARNs(String... openIdConnectProviderARNs2) {
        if (getOpenIdConnectProviderARNs() == null) {
            this.openIdConnectProviderARNs = new ArrayList(openIdConnectProviderARNs2.length);
        }
        for (String value : openIdConnectProviderARNs2) {
            this.openIdConnectProviderARNs.add(value);
        }
        return this;
    }

    public CreateIdentityPoolResult withOpenIdConnectProviderARNs(Collection<String> openIdConnectProviderARNs2) {
        setOpenIdConnectProviderARNs(openIdConnectProviderARNs2);
        return this;
    }

    public List<CognitoIdentityProvider> getCognitoIdentityProviders() {
        return this.cognitoIdentityProviders;
    }

    public void setCognitoIdentityProviders(Collection<CognitoIdentityProvider> cognitoIdentityProviders2) {
        if (cognitoIdentityProviders2 == null) {
            this.cognitoIdentityProviders = null;
        } else {
            this.cognitoIdentityProviders = new ArrayList(cognitoIdentityProviders2);
        }
    }

    public CreateIdentityPoolResult withCognitoIdentityProviders(CognitoIdentityProvider... cognitoIdentityProviders2) {
        if (getCognitoIdentityProviders() == null) {
            this.cognitoIdentityProviders = new ArrayList(cognitoIdentityProviders2.length);
        }
        for (CognitoIdentityProvider value : cognitoIdentityProviders2) {
            this.cognitoIdentityProviders.add(value);
        }
        return this;
    }

    public CreateIdentityPoolResult withCognitoIdentityProviders(Collection<CognitoIdentityProvider> cognitoIdentityProviders2) {
        setCognitoIdentityProviders(cognitoIdentityProviders2);
        return this;
    }

    public List<String> getSamlProviderARNs() {
        return this.samlProviderARNs;
    }

    public void setSamlProviderARNs(Collection<String> samlProviderARNs2) {
        if (samlProviderARNs2 == null) {
            this.samlProviderARNs = null;
        } else {
            this.samlProviderARNs = new ArrayList(samlProviderARNs2);
        }
    }

    public CreateIdentityPoolResult withSamlProviderARNs(String... samlProviderARNs2) {
        if (getSamlProviderARNs() == null) {
            this.samlProviderARNs = new ArrayList(samlProviderARNs2.length);
        }
        for (String value : samlProviderARNs2) {
            this.samlProviderARNs.add(value);
        }
        return this;
    }

    public CreateIdentityPoolResult withSamlProviderARNs(Collection<String> samlProviderARNs2) {
        setSamlProviderARNs(samlProviderARNs2);
        return this;
    }

    public Map<String, String> getIdentityPoolTags() {
        return this.identityPoolTags;
    }

    public void setIdentityPoolTags(Map<String, String> identityPoolTags2) {
        this.identityPoolTags = identityPoolTags2;
    }

    public CreateIdentityPoolResult withIdentityPoolTags(Map<String, String> identityPoolTags2) {
        this.identityPoolTags = identityPoolTags2;
        return this;
    }

    public CreateIdentityPoolResult addIdentityPoolTagsEntry(String key, String value) {
        if (this.identityPoolTags == null) {
            this.identityPoolTags = new HashMap();
        }
        if (!this.identityPoolTags.containsKey(key)) {
            this.identityPoolTags.put(key, value);
            return this;
        }
        throw new IllegalArgumentException("Duplicated keys (" + key.toString() + ") are provided.");
    }

    public CreateIdentityPoolResult clearIdentityPoolTagsEntries() {
        this.identityPoolTags = null;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getIdentityPoolId() != null) {
            sb.append("IdentityPoolId: " + getIdentityPoolId() + ",");
        }
        if (getIdentityPoolName() != null) {
            sb.append("IdentityPoolName: " + getIdentityPoolName() + ",");
        }
        if (getAllowUnauthenticatedIdentities() != null) {
            sb.append("AllowUnauthenticatedIdentities: " + getAllowUnauthenticatedIdentities() + ",");
        }
        if (getAllowClassicFlow() != null) {
            sb.append("AllowClassicFlow: " + getAllowClassicFlow() + ",");
        }
        if (getSupportedLoginProviders() != null) {
            sb.append("SupportedLoginProviders: " + getSupportedLoginProviders() + ",");
        }
        if (getDeveloperProviderName() != null) {
            sb.append("DeveloperProviderName: " + getDeveloperProviderName() + ",");
        }
        if (getOpenIdConnectProviderARNs() != null) {
            sb.append("OpenIdConnectProviderARNs: " + getOpenIdConnectProviderARNs() + ",");
        }
        if (getCognitoIdentityProviders() != null) {
            sb.append("CognitoIdentityProviders: " + getCognitoIdentityProviders() + ",");
        }
        if (getSamlProviderARNs() != null) {
            sb.append("SamlProviderARNs: " + getSamlProviderARNs() + ",");
        }
        if (getIdentityPoolTags() != null) {
            sb.append("IdentityPoolTags: " + getIdentityPoolTags());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5 = 0;
        int hashCode = ((((1 * 31) + (getIdentityPoolId() == null ? 0 : getIdentityPoolId().hashCode())) * 31) + (getIdentityPoolName() == null ? 0 : getIdentityPoolName().hashCode())) * 31;
        if (getAllowUnauthenticatedIdentities() == null) {
            i = 0;
        } else {
            i = getAllowUnauthenticatedIdentities().hashCode();
        }
        int hashCode2 = (((hashCode + i) * 31) + (getAllowClassicFlow() == null ? 0 : getAllowClassicFlow().hashCode())) * 31;
        if (getSupportedLoginProviders() == null) {
            i2 = 0;
        } else {
            i2 = getSupportedLoginProviders().hashCode();
        }
        int hashCode3 = (((hashCode2 + i2) * 31) + (getDeveloperProviderName() == null ? 0 : getDeveloperProviderName().hashCode())) * 31;
        if (getOpenIdConnectProviderARNs() == null) {
            i3 = 0;
        } else {
            i3 = getOpenIdConnectProviderARNs().hashCode();
        }
        int hashCode4 = (hashCode3 + i3) * 31;
        if (getCognitoIdentityProviders() == null) {
            i4 = 0;
        } else {
            i4 = getCognitoIdentityProviders().hashCode();
        }
        int hashCode5 = (((hashCode4 + i4) * 31) + (getSamlProviderARNs() == null ? 0 : getSamlProviderARNs().hashCode())) * 31;
        if (getIdentityPoolTags() != null) {
            i5 = getIdentityPoolTags().hashCode();
        }
        return hashCode5 + i5;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof CreateIdentityPoolResult)) {
            return false;
        }
        CreateIdentityPoolResult other = (CreateIdentityPoolResult) obj;
        if ((other.getIdentityPoolId() == null) ^ (getIdentityPoolId() == null)) {
            return false;
        }
        if (other.getIdentityPoolId() != null && !other.getIdentityPoolId().equals(getIdentityPoolId())) {
            return false;
        }
        if ((other.getIdentityPoolName() == null) ^ (getIdentityPoolName() == null)) {
            return false;
        }
        if (other.getIdentityPoolName() != null && !other.getIdentityPoolName().equals(getIdentityPoolName())) {
            return false;
        }
        if ((other.getAllowUnauthenticatedIdentities() == null) ^ (getAllowUnauthenticatedIdentities() == null)) {
            return false;
        }
        if (other.getAllowUnauthenticatedIdentities() != null && !other.getAllowUnauthenticatedIdentities().equals(getAllowUnauthenticatedIdentities())) {
            return false;
        }
        if ((other.getAllowClassicFlow() == null) ^ (getAllowClassicFlow() == null)) {
            return false;
        }
        if (other.getAllowClassicFlow() != null && !other.getAllowClassicFlow().equals(getAllowClassicFlow())) {
            return false;
        }
        if ((other.getSupportedLoginProviders() == null) ^ (getSupportedLoginProviders() == null)) {
            return false;
        }
        if (other.getSupportedLoginProviders() != null && !other.getSupportedLoginProviders().equals(getSupportedLoginProviders())) {
            return false;
        }
        if ((other.getDeveloperProviderName() == null) ^ (getDeveloperProviderName() == null)) {
            return false;
        }
        if (other.getDeveloperProviderName() != null && !other.getDeveloperProviderName().equals(getDeveloperProviderName())) {
            return false;
        }
        if ((other.getOpenIdConnectProviderARNs() == null) ^ (getOpenIdConnectProviderARNs() == null)) {
            return false;
        }
        if (other.getOpenIdConnectProviderARNs() != null && !other.getOpenIdConnectProviderARNs().equals(getOpenIdConnectProviderARNs())) {
            return false;
        }
        if ((other.getCognitoIdentityProviders() == null) ^ (getCognitoIdentityProviders() == null)) {
            return false;
        }
        if (other.getCognitoIdentityProviders() != null && !other.getCognitoIdentityProviders().equals(getCognitoIdentityProviders())) {
            return false;
        }
        if ((other.getSamlProviderARNs() == null) ^ (getSamlProviderARNs() == null)) {
            return false;
        }
        if (other.getSamlProviderARNs() != null && !other.getSamlProviderARNs().equals(getSamlProviderARNs())) {
            return false;
        }
        if ((other.getIdentityPoolTags() == null) ^ (getIdentityPoolTags() == null)) {
            return false;
        }
        if (other.getIdentityPoolTags() == null || other.getIdentityPoolTags().equals(getIdentityPoolTags())) {
            return true;
        }
        return false;
    }
}
