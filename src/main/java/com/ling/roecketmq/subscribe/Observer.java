package com.ling.roecketmq.subscribe;

/**
 * 抽象观察者
 *
 * @author zhangling
 * @date 2022/7/4 9:04 PM
 */
public interface Observer {
    /**
     * 观察者接收到 Mq 消息时响应处理
     * @param topic Mq 的 topic
     * @param tag tag
     * @param key key， 每条消息设置尽可能唯一的 Key，以确保相同的 Key 的消息不会超过 64 条，否则消息会漏查。
     * @param shardingKey 分区 key，分区顺序消息用到
     * @param messageId 消息 id，由 Mq 自动生成
     * @param body 消息主体内容
     * */
     void subscribe(String topic,String tag,String key,String shardingKey,String messageId,String body);
}
