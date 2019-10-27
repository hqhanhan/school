package com.hqhan.school.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;


/**
 * 〈description〉<br>
 * 〈MQ业务处理接口〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-25 22:03
 */
public interface MessageHandler<M extends Message> {

    Logger log = LoggerFactory.getLogger(MessageHandler.class);

    /**
     * description: <br>
     * 〈〉
     * @param message
     * @return com.hqhan.school.mq.Message
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/25 22:03
     */
    default Message verifyStart(Message message) throws Exception {
        if (message == null || StringUtils.isEmpty(message.getTaskId()) || StringUtils.isEmpty(message.getTopic())) {
            return new NullMessage();
        }
        return isConsumed(message);
    }

    /**
     * description: <br>
     * 〈判断当前消息是否被消费过〉
     * @param message  消息体
     * @return com.hqhan.school.mq.Message
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/25 22:52
     */
    Message isConsumed(Message message);

    /**
     * description: <br>
     * 〈创建消息体在redis中存储的KEY〉
     * @param message  消息体
     * @return java.lang.String
     * @version V1.0
     * @author hqhan8080@Gmail.com
     * @date 2019/10/25 22:53
     */
    String buildKey(Message message);

   /**
    * description: <br>
    * 〈消息消费后，需要做的后续处理〉
    * @param isSuccess  消息是否消费成功  true成功，false失败
    * @param message 消息体
    * @return com.hqhan.school.mq.Message
    * @version V1.0
    * @author hqhan8080@Gmail.com
    * @date 2019/10/25 22:54
    */
    Message verifyEnd(boolean isSuccess, Message message);
}
