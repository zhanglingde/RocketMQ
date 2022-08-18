package com.ling.roecketmq.config;

import com.ling.roecketmq.normal.NormalMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Slf4j
@Configuration
public class ApacheConsumerClient {
    @Autowired
    NormalMessageListener normalMessageListener;

    @Bean(initMethod = "start",destroyMethod = "shutdown")
    @ConditionalOnProperty(prefix = "mq", name = "type", havingValue = "apache")
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setConsumerGroup(MqConfig.groupId);
        consumer.setNamesrvAddr(MqConfig.nameSrvAddr);
        consumer.setConsumeThreadMax(20);
        try {
            consumer.subscribe("SRM_INVOICE-BUSINESS-local","*");
            consumer.subscribe("SyncTopic","*");
            consumer.subscribe("AsyncTopic","*");
            consumer.subscribe("OnewayTopic","*");
            consumer.subscribe("normal","*");
            consumer.subscribe("delay","*");
            consumer.subscribe("order","*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setConsumeTimeout(1500);
            // 注册监听器
            // consumer.registerMessageListener(new MessageListenerConcurrently() {
            //     @Override
            //     public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
            //         for (MessageExt msg : msgs) {
            //             log.info("topic：{},tag：{},key：{},body:{}", msg.getTopic(), msg.getTags(), msg.getKeys(), new String(msg.getBody()));
            //         }
            //         // System.out.println("线程：" + Thread.currentThread().getName() + "，消息：" + msgs);
            //         return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            //     }
            // });
            consumer.registerMessageListener(new NormalMessageListener());
            // consumer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return consumer;
    }
}
