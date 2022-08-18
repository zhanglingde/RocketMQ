package com.ling.roecketmq.order;


import com.ling.roecketmq.subscribe.Observer;

/**
 * 全局顺序消息 demo
 *
 * @author zhangling
 * @date 2022/7/4 9:03 PM
 */
public class OrderMsgObserver implements Observer {

    /**订阅者名字*/
    private String name;

    public OrderMsgObserver(String name){
        this.name = name;
    }

    @Override
    public void subscribe(String topic, String tag, String key, String shardingKey, String messageId, String body) {
        //log.info("顺序消息，订阅者:[{}], topic:[{}], tag:[{}], key:[{}], messageId:[{}], shardingKey:[{}], body:[{}]",this.name,topic,tag,key,messageId,shardingKey,body);
    }

}
