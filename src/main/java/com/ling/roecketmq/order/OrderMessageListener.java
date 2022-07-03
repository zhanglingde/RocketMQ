package com.ling.roecketmq.order;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.ling.roecketmq.subscribe.MessageSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 * 顺序消息的订阅
 */
@Slf4j
@Component
public class OrderMessageListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            MessageSubject messageSubject = new MessageSubject();

            messageSubject.publish(message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey(), message.getMsgID(), new String(message.getBody()));
            return Action.CommitMessage;
        } catch (Exception e) {
            log.error("顺序消息的订阅消费异常", e);
            // 消费失败
            return Action.ReconsumeLater;
        }
    }

}