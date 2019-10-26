package com.hqhan.school.mq;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.hqhan.school.mq.command.*;
import com.hqhan.school.mq.command.SplitRequestMessage;
import com.hqhan.school.mq.constants.BusinessType;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "businessType",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SplitRequestMessage.class, name = BusinessType.SPLIT_REQUEST),
        @JsonSubTypes.Type(value = SplitResponseMessage.class, name = BusinessType.SPLIT_RESPONSE),
        @JsonSubTypes.Type(value = CheckRequestMessage.class, name = BusinessType.CHECK_REQUEST),
        @JsonSubTypes.Type(value = CheckResponseMessage.class, name = BusinessType.CHECK_RESPONSE),
        @JsonSubTypes.Type(value = StoreRequestMessage.class, name = BusinessType.STORE_REQUEST),
        @JsonSubTypes.Type(value = StoreResponseMessage.class, name = BusinessType.STORE_RESPONSE)
})
public abstract class Message {

    private String taskId;

    private String businessId;

    private String businessType;

    private String topic;


    public Message() {
    }


    public Message(String taskId, String businessId, String businessType, String topic) {
        this.taskId = taskId;
        this.businessId = businessId;
        this.businessType = businessType;
        this.topic = topic;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
