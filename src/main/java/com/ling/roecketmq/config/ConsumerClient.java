package com.ling.roecketmq.config;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import com.ling.roecketmq.delay.DelayMessageListener;
import com.ling.roecketmq.normal.NormalMessageListener;
import com.ling.roecketmq.order.OrderMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 *
 * @author zhangling
 * @date 2022/7/4 9:01 PM
 */
@Configuration
public class ConsumerClient {

    @Autowired
    private OrderMessageListener orderMessageListener;

    @ConditionalOnProperty(prefix = "mq", name = "type", havingValue = "aliyun")
    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        // 配置文件
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, MqConfig.groupId  );
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey,MqConfig.accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, MqConfig.secretKey);
        // 设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "3000");
        // 设置 TCP 接入域名，到控制台的实例基本信息中查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, MqConfig.nameSrvAddr);
        // 将消费者线程数固定为 20 个（20为默认值）
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "5");
        consumerBean.setProperties(properties);

        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>(16);

        /**
         * 延迟消息的订阅和消息监听器绑定
         */
        Subscription subscription = new Subscription();
        subscription.setTopic(MqConfig.delayTopic);
        subscription.setExpression(MqConfig.delayTag);
        subscriptionTable.put(subscription, new DelayMessageListener());

        /**
         * 普通消息的订阅
         */
        Subscription subscriptionNormal = new Subscription();
        subscriptionNormal.setTopic(MqConfig.topic);
        subscriptionNormal.setExpression(MqConfig.tag);
        subscriptionTable.put(subscriptionNormal, new NormalMessageListener());

        /**
         * 全局顺序消息的订阅
         */
        Subscription subscriptionOrder = new Subscription();
        subscriptionOrder.setTopic(MqConfig.orderTopic);
        subscriptionOrder.setExpression(MqConfig.tag);
        subscriptionTable.put(subscriptionOrder, orderMessageListener);

        //订阅多个topic如上面设置
        consumerBean.setSubscriptionTable(subscriptionTable);

        return consumerBean;
    }

}
