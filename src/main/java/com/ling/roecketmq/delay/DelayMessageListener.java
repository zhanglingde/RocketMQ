package com.ling.roecketmq.delay;

import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.ling.roecketmq.subscribe.MessageSubject;
import lombok.extern.slf4j.Slf4j;


/**
 * 延迟消息的订阅
 *
 * @author zhangling
 * @date 2022/7/4 9:02 PM
 */
@Slf4j
public class DelayMessageListener implements MessageListener, org.apache.rocketmq.client.consumer.listener.MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            MessageSubject messageSubject = new MessageSubject();
            /**
             * TODO 业务处理
             * 订单模块 订阅*/
            messageSubject.addObserver(SpringUtil.getBean(DelayMessageDemoObserver.class));

            messageSubject.publish(message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey(), message.getMsgID(), new String(message.getBody()));
            return Action.CommitMessage;
        } catch (Exception e) {
            log.error("延迟消息的订阅异常", e);
            // 消费失败
            return Action.ReconsumeLater;
        }
    }
}
