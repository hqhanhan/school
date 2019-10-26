package com.hqhan.school.mq.command;


import com.hqhan.school.mq.Message;

import java.util.List;

public class CheckRequestMessage extends Message {

    private List<String> subTaskList;

    public CheckRequestMessage() {
        super();
    }


    public CheckRequestMessage(String taskId, String businessId, String businessType, String topic, List<String> subTaskList) {
        super(taskId, businessId, businessType, topic);
        this.subTaskList = subTaskList;
    }


    public List<String> getSubTaskList() {
        return subTaskList;
    }

    public void setSubTaskList(List<String> subTaskList) {
        this.subTaskList = subTaskList;
    }
}
