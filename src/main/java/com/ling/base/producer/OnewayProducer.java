package com.ling.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * 发送单向消息
 */
public class OnewayProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.191.128:9876");
        producer.start();

        for (int i = 0; i < 100; i++) {
            Message msg = new Message("OnewayTopic","OnewayTag","Oneway Message".getBytes());
            // 发送单向消息
            producer.sendOneway(msg);
        }
        producer.shutdown();
    }
}
