package com.hqhan.school.mq.command;


import com.hqhan.school.mq.Message;

import java.util.List;
/**
 * 〈description〉<br>
 * 〈mainService向checkService发送检测请求，包含子任务〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-26 12:31
 */
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
