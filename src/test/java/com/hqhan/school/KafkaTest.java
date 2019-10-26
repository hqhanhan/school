package com.hqhan.school;

import com.hqhan.school.mq.Message;
import com.hqhan.school.mq.command.SplitRequestMessage;
import com.hqhan.school.mq.constants.BusinessType;
import com.hqhan.school.mq.core.MessageVerifier;
import com.hqhan.school.zk.ZKCustor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class KafkaTest extends BaseCaseTest {

    @Autowired
    private ZKCustor zkCustor;

    @Autowired
    private MessageVerifier messageVerifier;

    @Test
    public void splitRequest() {

        SplitRequestMessage msg = new SplitRequestMessage();
        msg.setBusinessType(BusinessType.SPLIT_REQUEST);
        msg.setTaskId("1");
        msg.setBusinessId("2");
        msg.setTopic(BusinessType.SPLIT_REQUEST+"_"+"topic");
        messageVerifier.verifyBeforeConsumption(msg);
        messageVerifier.verifyAfterConsumption(true,msg);
        System.out.println("");

    }
}
