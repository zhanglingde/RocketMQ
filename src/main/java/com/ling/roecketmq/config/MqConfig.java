package com.ling.roecketmq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
  RocketMQ的配置文件
 */
@Component
public class MqConfig {

    public static final String accessKey = "LTAI5tAxHE6VU8owAryAQ6tZ";
    public static final String secretKey = "Tvs23Wx3ig44BI5EpIXRQfr1wOPq6u";


    /**
     * 链接地址
     */
    public static String nameSrvAddr;

    @Value("${mq.nameSrvServer}")
    public void setNameSrvAddr(String tcpUrl) {
        nameSrvAddr = tcpUrl;
    }

    /**
     * Group ID
     */
    public static String groupId;


    @Value("${mq.groupId}")
    public void setGroupId(String GID) {
        groupId = GID;
    }

    public static String mqType;

    @Value("${mq.type}")
    public void setMqType(String type) {
        mqType = type;
    }

    //普通消息
    public static final String topic = "normal";
    public static final String tag = "*";

    // 全局顺序消息
    public static final String orderTopic = "supplier_order";
    public static final String orderTag = "*";

    // 延迟消息
    public static final String delayTopic = "delay";
    public static final String delayTag = "*";


    // 测试消息
    public static final String rocketMQ_TEST = "rocketMQ_TEST";
    public static final String rocketMQ_TEST_KEY = "rocketMQ_TEST_KEY";

    /*
     *  TODO 这里增加消息的tag
     */
    //订单支付后自动取消订单的时间 默认30分钟
    public static final Long tagOrderCancelTime = 1800000L;
    //订单支付后 tagOrderCancelTime时间内未支付 取消订单
    public static final String tagOrderCancel = "orderCancel";

    // 新建采购单供应商5天内未确认，自动作废
    public static final Long poCancelTime = 5 * 24 * 60 * 60 * 1000L;
    public static final String poCancelTag = "poCancel";
    public static final String poCancelKey = "poCancel:";

    // 新建违规单
    public static final Long VIOLATE_NEW_TIME = 10 * 24 * 3600 * 1000L;
    public static final String VIOLATE_NEW_TAG = "VIOLATE_NEW_TAG";
    public static final String VIOLATE_NEW_KEY = "VIOLATE_NEW_KEY";

    //订单扣减 新tag
    public static final String XPG_ORDER_NEW_TAG = "xpgNewOrder";
    public static final String XPG_ORDER_NEW_KEY = "xpgNewOrder:";

    public static final String XPG_ORDER_RETURN_TAG = "xpgOrderReturn";
    public static final String XPG_ORDER_RETURN_KEY = "xpgOrderReturn:";

    public static final Long RECORD_CANCEL_TIME = 2 * 24 * 60 * 60 * 1000L;
    public static final String XPG_INVENTORY_RECORD_CANCEL_TAG = "xpgInventoryRecordCancelTag";
    public static final String XPG_INVENTORY_RECORD_CANCLE_KEY = "xpgInventoryRecordCancelKey";



}