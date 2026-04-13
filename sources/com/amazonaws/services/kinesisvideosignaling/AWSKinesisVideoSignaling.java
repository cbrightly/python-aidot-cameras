package com.amazonaws.services.kinesisvideosignaling;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigRequest;
import com.amazonaws.services.kinesisvideosignaling.model.GetIceServerConfigResult;
import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterRequest;
import com.amazonaws.services.kinesisvideosignaling.model.SendAlexaOfferToMasterResult;

public interface AWSKinesisVideoSignaling {
    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    GetIceServerConfigResult getIceServerConfig(GetIceServerConfigRequest getIceServerConfigRequest);

    SendAlexaOfferToMasterResult sendAlexaOfferToMaster(SendAlexaOfferToMasterRequest sendAlexaOfferToMasterRequest);

    void setEndpoint(String str);

    void setRegion(Region region);

    void shutdown();
}
