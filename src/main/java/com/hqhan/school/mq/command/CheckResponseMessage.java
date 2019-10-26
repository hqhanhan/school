package com.hqhan.school.mq.command;


import com.hqhan.school.mq.Message;

public class CheckResponseMessage extends Message {

    private String subTaskId;

    public CheckResponseMessage() {
        super();
    }


    public CheckResponseMessage(String taskId, String businessId, String businessType, String topic, String subTaskId) {
        super(taskId, businessId, businessType, topic);
        this.subTaskId = subTaskId;
    }

    public String getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }


}
