package com.ling.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送同步消息
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 1. 创建生产者 Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 2. 设置 NameServer
        producer.setNamesrvAddr("192.168.191.128:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            /**
             * 创建并发送消息
             * 参数一：Topic: 消息主题类别
             * 参数二：Tag: Topic下可以包含多个 Tag
             * 参数三：消息内容body
             */
            Message message = new Message("SyncTopic", "TagA", ("Hello RocketMQ").getBytes());
            SendResult result = producer.send(message);

            String msgId = result.getMsgId();
            SendStatus status = result.getSendStatus();
            int queueId = result.getMessageQueue().getQueueId();
            System.out.println("发送状态：" + status + ",消息Id:" + msgId + ",消息队列：" + queueId);

            TimeUnit.SECONDS.sleep(1);

        }
        producer.shutdown();
    }
}
