package com.ling.roecketmq.delay;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ling.roecketmq.User;
import com.ling.roecketmq.config.MqConfig;
import com.ling.roecketmq.subscribe.Observer;
import org.springframework.stereotype.Component;

/**
 * 延迟顺序消息 demo
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 */
@Component
public class DelayMessageDemoObserver implements Observer {



    @Override
    public void subscribe(String topic, String tag, String key, String shardingKey, String messageId, String body) {
        // 处理具体业务
        System.out.println("body = " + body);
        if (!MqConfig.rocketMQ_TEST.equals(tag)) {
            return;
        }
        if (StrUtil.isEmpty(body)) {
            throw new RuntimeException("参数为空");
        }
        User user = JSONUtil.toBean(body, User.class);
        System.out.println("延时消息 = " + user);
    }

}
