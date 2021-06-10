package com.ling.rocketmq.producer;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProducerApplicationTests {

	@Autowired
	RocketMQTemplate rocketMQTemplate;

	/**
	 * 测试生产消息
	 */
	@Test
	void contextLoads() {
		rocketMQTemplate.convertAndSend("TestTopic", "Hello World SpringBoot RocketMQ");
	}

}
