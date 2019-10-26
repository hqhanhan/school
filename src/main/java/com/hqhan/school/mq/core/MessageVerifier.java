package com.hqhan.school.mq.core;

import com.hqhan.school.mq.Message;

public interface MessageVerifier {

    /**
     * 消息消费之前进行验证
     * 消费之前：添加持久节点数据，更新临时节点数据，添加消息至redis
     *
     * @param message
     * @return
     */
    Message verifyBeforeConsumption(Message message);

    /**
     * 消息消费之后进行验证
     * 消费之后：删除持久节点，更新临时节点数据，删除redis中数据
     *
     * @param success
     * @param message
     * @return
     */
    boolean verifyAfterConsumption(boolean success, Message message);
}
