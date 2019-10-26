package com.hqhan.school.mq;

import com.hqhan.school.mq.command.NullMessage;
import com.hqhan.school.mq.core.MessageVerifierImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public interface MessageHandler<M extends Message> {

    Logger log = LoggerFactory.getLogger(MessageHandler.class);

    default Message verifyStart(Message message) throws Exception {
        if (message == null || StringUtils.isEmpty(message.getTaskId()) || StringUtils.isEmpty(message.getTopic())) {
            return new NullMessage();
        }
        return isConsumed(message);
    }

    Message isConsumed(Message message);


    String buildKey(Message message);


    Message verifyEnd(boolean isSuccess, Message message);
}
