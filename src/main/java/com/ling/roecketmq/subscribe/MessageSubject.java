package com.ling.roecketmq.subscribe;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 具体消息观察目标
 */
public class MessageSubject implements Subject {
    /**
     * 观察者集合
     */
    private List<Observer> observers = new ArrayList<Observer>();

    private String topic;
    private String tag;
    private String key;
    private String shardingKey;
    private String messageId;
    private String body;

    private static Logger logger = LoggerFactory.getLogger(MessageSubject.class);


    @Override
    public void addObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(Observer obj) {
        int i = observers.indexOf(obj);
        if (i >= 0) {
            observers.remove(obj);
        }
    }

    /**
     * 接收到 mq 消息时，通知给所有观察者
     */
    @Override
    public void notifyObserver() {
        for (int i = 0; i < observers.size(); i++) {
            Observer o = (Observer) observers.get(i);
            try {
                o.subscribe(topic, tag, key, shardingKey, messageId, body);
            } catch (Exception e) {
                logger.error("消息队列subscribe异常,messageId=" + messageId + ",body" + body + ",key=" + key + ",tag=" + tag, e);
            }
        }
    }

    /**
     * 发布新消息
     */
    public void publish(String topic, String tag, String key, String shardingKey, String messageId, String body) {
        /**信息更新完毕，通知所有观察者*/
        this.topic = topic;
        this.tag = tag;
        this.key = key;
        this.shardingKey = shardingKey;
        this.messageId = messageId;
        this.body = body;
        notifyObserver();
    }


}
