package com.ling.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 监听器
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "TestTopic",consumerGroup = "consumer-1")
public class Consumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String msg) {
        log.info("接收到消息："+msg);
    }
}
