package com.hqhan.school.mq.command;

import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.MessageHandler;
import org.springframework.stereotype.Component;

/**
 * 〈description〉<br>
 * 〈暂无此项业务〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-26 16:45
 */
@Component
public class StoreResponseMessageHandler implements MessageHandler<StoreResponseMessage> {

    @Override
    public Message isConsumed(Message message) {
        //TODO 暂无此业务
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public String buildKey(Message message) {
        //TODO 暂无此业务
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public Message verifyEnd(boolean isSuccess, Message message) {
        //TODO 暂无此业务
        throw new UnsupportedOperationException("Unsupported Operation");
    }
}
