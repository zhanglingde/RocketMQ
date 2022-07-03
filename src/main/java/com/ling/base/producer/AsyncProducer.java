package com.ling.base.producer;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.CountDownLatch2;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.concurrent.TimeUnit;

/**
 * 发送异步消息
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 实例化消息生产者 Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 设置 NameServer 地址
        producer.setNamesrvAddr("192.168.186.128:9876");
        producer.start();
        producer.setRetryTimesWhenSendFailed(0);

        int messageCount = 5;
        final CountDownLatch2 countDownLatch = new CountDownLatch2(messageCount);

        for (int i = 0; i < messageCount; i++) {
            Message msg = new Message("AsyncTopic", "AsyncTag", ("Async Message" + i).getBytes());
            msg.setKeys("TEST_KEY" + i);

            // SendCallback 接收异步返回结果的回调，该方式send方法为异步
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println(e.getCause().getMessage());
                }
            });
        }
        // 等待5s后释放阻塞
        countDownLatch.await(5, TimeUnit.SECONDS);
        // 主线程关闭 Producer 实例
        producer.shutdown();
    }
}
