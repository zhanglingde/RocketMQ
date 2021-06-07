package com.ling.base.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 负载均衡模式 消费消息
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        // 实例化消费者 Consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 设置 NameServer 的地址
        consumer.setNamesrvAddr("192.168.191.128:9876;192.168.191.129:9876");
        // 订阅一个或多个 Topic，以及 Tag 来过滤需要消费的消息
        consumer.subscribe("AsyncTopic", "*");
        // 负载均衡模式 消费消息
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 设置消费超时时间
        consumer.setConsumeTimeout(15);
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                System.out.println("线程：" + Thread.currentThread().getName() + "，消息：" + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.println("消费者启动...");
    }
}
