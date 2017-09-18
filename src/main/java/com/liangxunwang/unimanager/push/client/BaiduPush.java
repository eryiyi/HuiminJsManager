package com.liangxunwang.unimanager.push.client;

import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.*;

public interface BaiduPush {

	public PushMsgToSingleDeviceResponse pushMsgToSingleDevice(
			PushMsgToSingleDeviceRequest request) throws PushClientException,
		    PushServerException;
	
	public PushMsgToAllResponse pushMsgToAll(
			PushMsgToAllRequest request) throws PushClientException,
		    PushServerException;
	
	public PushMsgToTagResponse pushMsgToTag(
			PushMsgToTagRequest request) throws PushClientException, 
	PushServerException;
	
	public PushMsgToSmartTagResponse pushMsgToSmartTag(
			PushMsgToSmartTagRequest request) throws PushClientException, 
	PushServerException;
	
	public PushBatchUniMsgResponse pushBatchUniMsg(
			PushBatchUniMsgRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryMsgStatusResponse queryMsgStatus(
			QueryMsgStatusRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryTimerRecordsResponse queryTimerRecords(
			QueryTimerRecordsRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryTopicRecordsResponse queryTopicRecords(
			QueryTopicRecordsRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryTimerListResponse queryTimerList(
			QueryTimerListRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryTopicListResponse queryTopicList(
			QueryTopicListRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryTagsResponse queryTags(
			QueryTagsRequest request) throws PushClientException, 
	PushServerException;
	
	public CreateTagResponse createTag(
			CreateTagRequest request) throws PushClientException, 
	PushServerException;
	
	public DeleteTagResponse deleteTag(
			DeleteTagRequest request) throws PushClientException, 
	PushServerException;
	
	public AddDevicesToTagResponse addDevicesToTag(
			AddDevicesToTagRequest request) throws PushClientException, 
	PushServerException;
	
	public DeleteDevicesFromTagResponse deleteDevicesFromTag(
			DeleteDevicesFromTagRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryDeviceNumInTagResponse queryDeviceNumInTag(
			QueryDeviceNumInTagRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryStatisticMsgResponse queryStatisticMsg(
			QueryStatisticMsgRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryStatisticTopicResponse queryStatisticTopic(
			QueryStatisticTopicRequest request) throws PushClientException, 
	PushServerException;
	
	public QueryStatisticDeviceResponse queryStatisticDevice(
			QueryStatisticDeviceRequest request) throws 
	PushClientException, PushServerException;
	
}
