package com.amazonaws.services.kinesisvideo;

import com.amazonaws.AmazonWebServiceRequest;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.regions.Region;
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

public interface AWSKinesisVideo {
    CreateSignalingChannelResult createSignalingChannel(CreateSignalingChannelRequest createSignalingChannelRequest);

    CreateStreamResult createStream(CreateStreamRequest createStreamRequest);

    DeleteSignalingChannelResult deleteSignalingChannel(DeleteSignalingChannelRequest deleteSignalingChannelRequest);

    DeleteStreamResult deleteStream(DeleteStreamRequest deleteStreamRequest);

    DescribeSignalingChannelResult describeSignalingChannel(DescribeSignalingChannelRequest describeSignalingChannelRequest);

    DescribeStreamResult describeStream(DescribeStreamRequest describeStreamRequest);

    ResponseMetadata getCachedResponseMetadata(AmazonWebServiceRequest amazonWebServiceRequest);

    GetDataEndpointResult getDataEndpoint(GetDataEndpointRequest getDataEndpointRequest);

    GetSignalingChannelEndpointResult getSignalingChannelEndpoint(GetSignalingChannelEndpointRequest getSignalingChannelEndpointRequest);

    ListSignalingChannelsResult listSignalingChannels(ListSignalingChannelsRequest listSignalingChannelsRequest);

    ListStreamsResult listStreams(ListStreamsRequest listStreamsRequest);

    ListTagsForResourceResult listTagsForResource(ListTagsForResourceRequest listTagsForResourceRequest);

    ListTagsForStreamResult listTagsForStream(ListTagsForStreamRequest listTagsForStreamRequest);

    void setEndpoint(String str);

    void setRegion(Region region);

    void shutdown();

    TagResourceResult tagResource(TagResourceRequest tagResourceRequest);

    TagStreamResult tagStream(TagStreamRequest tagStreamRequest);

    UntagResourceResult untagResource(UntagResourceRequest untagResourceRequest);

    UntagStreamResult untagStream(UntagStreamRequest untagStreamRequest);

    UpdateDataRetentionResult updateDataRetention(UpdateDataRetentionRequest updateDataRetentionRequest);

    UpdateSignalingChannelResult updateSignalingChannel(UpdateSignalingChannelRequest updateSignalingChannelRequest);

    UpdateStreamResult updateStream(UpdateStreamRequest updateStreamRequest);
}
