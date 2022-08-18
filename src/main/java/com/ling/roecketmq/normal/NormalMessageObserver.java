package com.ling.roecketmq.normal;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ling.roecketmq.User;
import com.ling.roecketmq.config.MqConfig;
import com.ling.roecketmq.subscribe.Observer;
import org.springframework.stereotype.Component;

/**
 * 普通消息的订阅 的demo
 *
 * @author zhangling
 * @date 2022/7/4 9:03 PM
 */
@Component
public class NormalMessageObserver implements Observer {



    @Override
    public void subscribe(String topic, String tag, String key, String shardingKey, String messageId, String body) {
        System.out.println("body = " + body);
        if ("tag-invoice-create-finsh".equals(tag)) {
            return;
        }
        if (StrUtil.isEmpty(body)) {
            throw new RuntimeException("参数为空");
        }
        // User user = JSONUtil.toBean(body, User.class);
        // System.out.println("普通消息 demo = " + user);
    }

}
