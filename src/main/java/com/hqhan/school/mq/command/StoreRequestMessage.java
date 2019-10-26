package com.hqhan.school.mq.command;


import com.hqhan.school.mq.Message;

public class StoreRequestMessage extends Message {

    private String subTaskId;

    public String getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }

}
