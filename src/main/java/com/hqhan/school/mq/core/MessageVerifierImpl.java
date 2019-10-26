package com.hqhan.school.mq.core;

import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.MessageHandler;
import com.hqhan.school.mq.command.NullMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class MessageVerifierImpl implements MessageVerifier {


    private Logger logger = LoggerFactory.getLogger(MessageVerifierImpl.class);

    @Autowired
    private List<? extends MessageHandler<? extends Message>> handlers;


    @Override
    public Message verifyBeforeConsumption(Message message) {
        Stream<Message> map = handlers
                .stream()
                .filter(handler -> GenericTypeResolver.resolveTypeArgument(handler.getClass(), MessageHandler.class).isInstance(message))
                .map(handler -> {
                    try {
                        return handler.verifyStart(message);
                    } catch (Exception e) {
                        logger.error("MessageHandler error", e);
                        return null;
                    }
                });
        List<Message> list = map.collect(Collectors.toList());
        if (list.size() == 1) {
            Message obj = list.get(0);
            return obj instanceof NullMessage ? null : obj;
        } else if (list.size() > 1) {
            throw new RuntimeException(String.format("[MQ Verifier] Found more then one handler for %s", message.getBusinessType()));
        } else {
            throw new RuntimeException(String.format("[MQ Verifier] Handler for %s not be found", message.getBusinessType()));
        }
    }


    @Override
    public boolean verifyAfterConsumption(boolean success, Message message) {
        Stream<Message> map = handlers.stream().filter(handler -> GenericTypeResolver.resolveTypeArgument(handler.getClass(), MessageHandler.class).isInstance(message))
                .map(
                        handler -> {
                            try {
                                return handler.verifyEnd(success, message);
                            } catch (Exception e) {
                                logger.error("MessageHandler error", e);
                                return null;
                            }
                        }
                );

        List<Message> list = map.collect(Collectors.toList());
        if (list.size() == 1) {
            Message obj = list.get(0);
            return obj instanceof NullMessage ? false : true;
        } else if (list.size() > 1) {
            throw new RuntimeException(String.format("[MQ Verifier] Found more then one handler for %s", message.getBusinessType()));
        } else {
            throw new RuntimeException(String.format("[MQ Verifier] Handler for %s not be found", message.getBusinessType()));
        }
    }
}
