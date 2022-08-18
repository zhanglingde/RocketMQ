package com.ling.roecketmq;

import com.aliyun.openservices.ons.api.*;
import com.ling.roecketmq.config.MqConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

import java.util.Date;
import java.util.Properties;


/**
 * 消息队列使用方法：
 * <ol>
 *     <li> 调用 MqUtils 里的静态方法，发送消息。tag 确定业务类型 </li>
 *     <li> 创建业务处理消息的观察者，继承Observer 接口 </li>
 *     <li> 在业务处理类的 subscribe 方法，处理具体业务 </li>
 * </ol>
 *
 * <a href="https://help.aliyun.com/document_detail/96359.html?spm=a2c4g.11186623.3.3.7aa32833XbjDnI">消息类型请查阅阿里云文档</a>
 *
 * @author zhangling
 * @date 2022/7/4 9:07 PM
 */
@Slf4j
public class MqUtils {

    private static Producer producer;
    private static DefaultMQProducer defaultMQProducer;




    /**
     * 延时消息
     *
     * @param key       Message Key 建议消息生产方为每条消息设置尽可能唯一的 Key，以确保相同的 Key 的消息不会超过 64 条，否则消息会漏查
     * @param body      消息内容
     * @param delayTime 延时时间，微秒，1000 是 1 秒
     * @param tag       用来判断业务类型
     * @author zhangling
     * @date 2022/7/4 9:10 PM
     */
    public static boolean delay(String key, String body, Long delayTime, String tag) {
        if (null == key || null == body || null == delayTime || null == tag) {
            throw new RuntimeException("必填参数不能为空");
        }

        if (MqConfig.mqType == "apache") {
            // 获取 producer
            if (null == defaultMQProducer) {
                synchronized (MqUtils.class) {
                    if (defaultMQProducer == null) {
                        defaultMQProducer = getDefaultMQProducer();
                    }
                }
            }
            org.apache.rocketmq.common.message.Message msg = new org.apache.rocketmq.common.message.Message(
                    // Message 所属的 Topic
                    MqConfig.delayTopic,
                    // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                    tag,
                    // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
                    // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                    body.getBytes());
            msg.setKeys(key);
            long time = System.currentTimeMillis() + delayTime;
            msg.setDelayTimeLevel(3);
            try {
                org.apache.rocketmq.client.producer.SendResult sendResult = defaultMQProducer.send(msg);
                if (sendResult != null) {
                    System.out.println("sendResult = " + sendResult);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // 获取 producer
            if (null == producer) {
                synchronized (MqUtils.class) {
                    if (producer == null) {
                        producer = getProducer();
                    }
                }
            }

            Message msg = new Message(
                    // Message 所属的 Topic
                    MqConfig.delayTopic,
                    // Message Tag 可理解为 Gmail 中的标签，对消息进行再归类，方便 Consumer 指定过滤条件在 MQ 服务器过滤
                    tag,
                    // Message Body 可以是任何二进制形式的数据， MQ 不做任何干预，
                    // 需要 Producer 与 Consumer 协商好一致的序列化和反序列化方式
                    body.getBytes());
            // 设置代表消息的业务关键属性，请尽可能全局唯一。
            // 以方便您在无法正常收到消息情况下，可通过阿里云服务器管理控制台查询消息并补发
            // 注意：不设置也不会影响消息正常收发
            msg.setKey(key);
            // 延时消息，单位毫秒（ms），在指定延迟时间（当前时间之后）进行投递，例如消息在 3 秒后投递
            long time = System.currentTimeMillis() + delayTime;
            // 设置消息需要被投递的时间
            msg.setStartDeliverTime(time);
            try {
                SendResult sendResult = producer.send(msg);
                // 同步发送消息，只要不抛异常就是成功
                if (sendResult != null) {
                    //   System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId()+"body="+msg.getBody());
                }
            } catch (Exception e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                log.error("延时消息发送异常,key=" + key + ",body=" + body + ",tag=" + tag + ",delayTime=" + delayTime, e);
                throw new RuntimeException("延时消息发送异常");
            }
        }
        return true;
    }


    /**
     * 普通消息
     *
     * @param key  Message Key 建议消息生产方为每条消息设置尽可能唯一的 Key，以确保相同的 Key 的消息不会超过 64 条，否则消息会漏查
     * @param body 消息内容
     * @param tag  用来判断业务类型
     * @author zhangling
     * @date 2022/7/4 9:10 PM
     */
    public static boolean normal(String key, String body, String tag) {
        if (null == key || null == body || null == tag) {
            throw new RuntimeException("必填参数不能为空");
        }

        if ("apache".equals(MqConfig.mqType)) {
            if (null == defaultMQProducer) {
                synchronized (MqUtils.class) {
                    if (defaultMQProducer == null) {
                        defaultMQProducer = getDefaultMQProducer();
                    }
                }
            }
            org.apache.rocketmq.common.message.Message msg = new org.apache.rocketmq.common.message.Message(MqConfig.topic, tag, body.getBytes());
            msg.setKeys(key);
            try {
                org.apache.rocketmq.client.producer.SendResult sendResult = defaultMQProducer.send(msg);
                if (sendResult != null) {
                    System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMsgId() + "body=" + msg.getBody());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {

            // 获取 producer
            if (null == producer) {
                synchronized (MqUtils.class) {
                    if (producer == null) {
                        producer = getProducer();
                    }
                }
            }

            Message msg = new Message(MqConfig.topic, tag, body.getBytes());
            msg.setKey(key);
            try {
                SendResult sendResult = producer.send(msg);
                // 同步发送消息，只要不抛异常就是成功
                if (sendResult != null) {
                    // System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId()+"body="+msg.getBody());
                }
            } catch (Exception e) {
                // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
                log.error("普通消息发送异常,key=" + key + ",body=" + body + ",tag=" + tag, e);
                throw new RuntimeException("普通消息发送异常");
            }
        }
        return true;
    }

    /**
     * 异步消息
     *
     * @param key
     * @param body
     * @param tag
     * @return
     */
    public static boolean normalAsync(String key, String body, String tag) {
        if (null == key || null == body || null == tag) {
            throw new RuntimeException("必填参数不能为空");
        }

        /**
         * 创建 producer 发布者
         */
        if (null == producer) {
            synchronized (MqUtils.class) {
                if (producer == null) {
                    producer = getProducer();
                }
            }
        }

        Message msg = new Message(
                MqConfig.topic,
                tag,
                body.getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        msg.setKey(key);
        try {
            // 发送异步消息
            producer.sendAsync(msg, new AsyncSendCallback());
        } catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            log.error("普通消息发送异常,key=" + key + ",body=" + body + ",tag=" + tag, e);
            throw new RuntimeException("普通消息发送异常");
        }
        return true;
    }


    /**
     * 全局顺序消息
     *
     * @param key  Message Key 建议消息生产方为每条消息设置尽可能唯一的 Key，以确保相同的 Key 的消息不会超过 64 条，否则消息会漏查
     * @param body 消息内容
     * @param tag  用来判断业务类型
     * @author zhangling
     * @date 2022/7/4 9:10 PM
     */
    public static boolean order(String key, String body, String tag) {
        if (null == key || null == body || null == tag) {
            throw new RuntimeException("必填参数不能为空");
        }
        /**
         * 创建 producer 发布者
         */
        if (null == producer) {
            synchronized (MqUtils.class) {
                if (producer == null) {
                    producer = getProducer();
                }
            }
        }
        Message msg = new Message(
                MqConfig.orderTopic,
                tag,
                body.getBytes());
        // 设置代表消息的业务关键属性，请尽可能全局唯一。
        msg.setKey(key);
        // 分区顺序消息中区分不同分区的关键字段，Sharding Key 与普通消息的 key 是完全不同的概念。
        // 全局顺序消息，该字段可以设置为任意非空字符串。
        // String shardingKey = String.valueOf("order");
        try {
            SendResult sendResult = producer.send(msg);
            // 同步发送消息，只要不抛异常就是成功
            if (sendResult != null) {
                // System.out.println(new Date() + " Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId()+"body="+msg.getBody());
            }
        } catch (Exception e) {
            // 消息发送失败，需要进行重试处理，可重新发送这条消息或持久化这条数据进行补偿处理
            log.error("全局顺序消息发送异常,key=" + key + ",body=" + body + ",tag=" + tag, e);
            throw new RuntimeException("全局顺序消息发送异常", e);
        }
        return true;
    }

    /**
     * 异步消息回调
     */
    static class AsyncSendCallback implements SendCallback {
        @Override
        public void onSuccess(SendResult result) {
            if (result != null) {
                // System.out.println(new Date() + " Send mq message success. Topic is:" + result.getTopic() + " msgId is: " + result.getMessageId() + "body=" + result.toString());
            }
        }

        @Override
        public void onException(OnExceptionContext context) {
            log.error("普通异步消息发送异常," + context.getException().getCause());
            throw new RuntimeException("普通异步消息发送异常");
        }
    }

    private static Producer getProducer() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.GROUP_ID, MqConfig.groupId);
        // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.AccessKey, MqConfig.accessKey);
        // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
        properties.put(PropertyKeyConst.SecretKey, MqConfig.secretKey);
        // 设置发送超时时间，单位毫秒
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, "5000");
        // 设置 TCP 接入域名，到控制台的实例基本信息中查看
        properties.put(PropertyKeyConst.NAMESRV_ADDR, MqConfig.nameSrvAddr);
        properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);
        // 这里直接用 producer 会导致 已赋值  但是未 start 的现象  所以需要等 producer start完再返回
        Producer temp = ONSFactory.createProducer(properties);
        try {
            /**
             *在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
             */
            temp.start();
        } catch (Exception e) {
            log.error("mq初始化异常 [normal]", e);
        }
        return temp;
    }

    private static DefaultMQProducer getDefaultMQProducer() {
        DefaultMQProducer producer = new DefaultMQProducer("group2");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
        return producer;
    }


}
