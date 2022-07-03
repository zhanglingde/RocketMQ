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
        // 设置 NameServer 的地址（可以订阅多个）
        consumer.setNamesrvAddr("101.43.42.91:9876;192.168.186.128:9876");
        // 订阅一个或多个 Topic，以及 Tag 来过滤需要消费的消息
        consumer.subscribe("SyncTopic", "*");
        consumer.subscribe("AsyncTopic", "*");
        consumer.subscribe("OnewayTopic", "*");
        // 负载均衡模式 消费消息
        consumer.setMessageModel(MessageModel.CLUSTERING);
        // 广播模式
        // consumer.setMessageModel(MessageModel.BROADCASTING);
        // 设置消费超时时间
        consumer.setConsumeTimeout(1500);
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
