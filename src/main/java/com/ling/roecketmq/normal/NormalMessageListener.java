package com.ling.roecketmq.normal;

import cn.hutool.extra.spring.SpringUtil;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.ling.roecketmq.subscribe.MessageSubject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 * 普通消息
 */
@Slf4j
public class NormalMessageListener implements MessageListener {

    private static Logger logger = LoggerFactory.getLogger(NormalMessageListener.class);

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            MessageSubject messageSubject = new MessageSubject();
            /**测试订阅，用于日志记录*/
            messageSubject.addObserver(SpringUtil.getBean(NormalMessageObserver.class));
            messageSubject.publish(message.getTopic(), message.getTag(), message.getKey(), message.getShardingKey(), message.getMsgID(), new String(message.getBody()));
            return Action.CommitMessage;
        } catch (Exception e) {
            // 消费失败
            log.error("普通消息队列异常", e);
            return Action.ReconsumeLater;
        }
    }
}
