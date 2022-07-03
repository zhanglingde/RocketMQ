package com.ling.roecketmq.subscribe;

/**
 * 抽象观察者
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 */
public interface Observer {
    /**
     * 观察者接收到 Mq 消息时响应处理
     * @param topic Mq 的 topic
     * @param tag tag
     * @param key key， 每条消息设置尽可能唯一的 Key，以确保相同的 Key 的消息不会超过 64 条，否则消息会漏查。
     * @param shardingKey 分区key，分区顺序消息用到
     * @param messageId 消息id，由Mq自动生成
     * @param body 消息主体内容
     * */
     void subscribe(String topic,String tag,String key,String shardingKey,String messageId,String body);
}
