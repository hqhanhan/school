package com.hqhan.school.mq.command;

import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.MessageHandler;
import org.springframework.stereotype.Component;

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
