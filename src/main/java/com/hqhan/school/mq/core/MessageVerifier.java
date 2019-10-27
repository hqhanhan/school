package com.hqhan.school.mq.core;

import com.hqhan.school.mq.Message;

/**
 * 〈description〉<br>
 * 〈消息是否消费验证者〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-27 20:25
 */
public interface MessageVerifier {

    /**
     * description: <br>
     * 〈消息消费之前进行验证，消费之前：添加持久节点数据，更新临时节点数据，添加消息至redis〉
     * @param message
     * @return com.hqhan.school.mq.Message
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/25 21:23
     */
    Message verifyBeforeConsumption(Message message);

    /**
     * description: <br>
     * 〈消息消费之后进行验证：删除持久节点，更新临时节点数据，删除redis中数据〉
     * @param success
     * @param message
     * @return boolean
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/27 21:39
     */
    boolean verifyAfterConsumption(boolean success, Message message);
}
