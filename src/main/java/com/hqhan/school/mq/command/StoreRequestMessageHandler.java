package com.hqhan.school.mq.command;

import com.alibaba.fastjson.JSON;
import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.MessageHandler;
import com.hqhan.school.mq.NullMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
/**
 * 〈description〉<br>
 * 〈mainService向reportService检测结果请求〉
 *
 * @author hqhan8080@Gmail.com
 * @version V1.0
 * @date 2019-10-26 16:45
 */
@Component
public class StoreRequestMessageHandler implements MessageHandler<StoreRequestMessage> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Message isConsumed(Message message) {
        String key = buildKey(message);
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(message), 4, TimeUnit.DAYS);
            //FIXME 保存持久节点
            //FIXME 并且设置临时节点的状态为running
            return message;
        }
        return new NullMessage();
    }


    @Override
    public Message verifyEnd(boolean success, Message message) {
        String key = buildKey(message);
        if (success) {
            //FIXME 更新 临时节点 serverInfo中的status=idle
            //FIXME 删除 持久节点 message数据
            return message;
        }
        redisTemplate.delete(key);
        //FIXME 更新 临时节点 serverInfo中的status=error
        return new NullMessage();
    }

    @Override
    public String buildKey(Message message) {
        CheckResponseMessage msg = CheckResponseMessage.class.cast(message);
        return new StringBuffer().append(msg.getTaskId()).append(":").append(msg.getSubTaskId()).append(":").append(msg.getTopic()).toString();
    }
}
