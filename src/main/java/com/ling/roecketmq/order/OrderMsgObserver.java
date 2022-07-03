package com.ling.roecketmq.order;


import com.ling.roecketmq.subscribe.Observer;

/**
 * @author shisan
 * @Description:
 * @date 2019-08-06 10:27:43
 * 全局顺序消息 demo
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
