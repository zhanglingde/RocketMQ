package com.ling.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送延时消息
 */
public class DelayProducer {
    public static void main(String[] args) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("192.168.191.128:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("DelayTopic",("Hello Scheduled Message"+i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            msg.setDelayTimeLevel(3);

            SendResult result = producer.send(msg);
            System.out.println(result);

            //TimeUnit.SECONDS.sleep(1);
        }
        producer.shutdown();
    }
}
