package com.ling.roecketmq.config;

import jdk.jshell.JShell;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.List;

@Configuration
public class ApacheConsumerClient {

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        // consumer.setConsumerGroup(MqConfig.groupId);
        // consumer.setNamesrvAddr(MqConfig.nameSrvAddr);
        consumer.setConsumerGroup("group1");
        consumer.setNamesrvAddr("192.168.186.128:9876");
        consumer.setConsumeThreadMax(20);
        try {
            consumer.subscribe("SyncTopic","*");
            consumer.subscribe("AsyncTopic","*");
            consumer.subscribe("OnewayTopic","*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeTimeout(1500);
            //
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return consumer;
    }
}
