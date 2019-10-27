package com.hqhan.school.mq.command;

import com.alibaba.fastjson.JSON;
import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.MessageHandler;
import com.hqhan.school.mq.NullMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
public class CheckRequestMessageHandler implements MessageHandler<CheckRequestMessage> {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CheckResponseMessageHandler checkResponseMessageHandler;


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
    public Message isConsumed(Message message) {
        CheckRequestMessage msg = CheckRequestMessage.class.cast(message);
        String key = buildKey(message);
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, JSON.toJSONString(message), 4, TimeUnit.DAYS);
            //FIXME 保存持久节点
            //FIXME 并且设置临时节点的状态为running
            return message;
        }
        List<String> subTaskList = msg.getSubTaskList();
        List<String> consumedSubTaskList = subTaskList.stream().filter(subTaskId -> checkResponseMessageHandler.isConsumed(message) == null).collect(Collectors.toList());
        subTaskList.removeAll(consumedSubTaskList);
        if (CollectionUtils.isEmpty(subTaskList)) {
            return new NullMessage();
        }
        msg.setSubTaskList(subTaskList);
        return msg;
    }

    @Override
    public String buildKey(Message message) {
        CheckRequestMessage msg = CheckRequestMessage.class.cast(message);
        List<String> subTaskList = msg.getSubTaskList();
        Collections.sort(subTaskList);
        //FIXME 所有子任务拼接生成MD5，作为businessId
        String businessId = "";
        return new StringBuffer().append(msg.getTaskId()).append(":").append(businessId).append(":").append(msg.getTopic()).toString();
    }
}
