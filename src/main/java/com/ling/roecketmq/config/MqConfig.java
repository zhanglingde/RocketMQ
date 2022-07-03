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

    @Value("${spring.rocketmq.tcpUrl}")
    public void setNameSrvAddr(String tcpUrl) {
        nameSrvAddr = tcpUrl;
    }

    /**
     * Group ID
     */
    public static String groupId;

    @Value("${spring.rocketmq.GID}")
    public void setGroupId(String GID) {
        groupId = GID;
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


    public static final String XPG_ORDER_TAG = "xpgOrder";
    public static final String XPG_ORDER_KEY = "xpgOrder:";

    //订单扣减 新tag
    public static final String XPG_ORDER_NEW_TAG = "xpgNewOrder";
    public static final String XPG_ORDER_NEW_KEY = "xpgNewOrder:";

    public static final String XPG_ORDER_RETURN_TAG = "xpgOrderReturn";
    public static final String XPG_ORDER_RETURN_KEY = "xpgOrderReturn:";

    public static final Long RECORD_CANCEL_TIME = 2 * 24 * 60 * 60 * 1000L;
    public static final String XPG_INVENTORY_RECORD_CANCEL_TAG = "xpgInventoryRecordCancelTag";
    public static final String XPG_INVENTORY_RECORD_CANCLE_KEY = "xpgInventoryRecordCancelKey";

    // 24小时超时未发货
    //public static final Long OVERTIME_NO_DELIVERY = 24 * 60 * 60 * 1000L;
    public static final Long OVERTIME_NO_DELIVERY = 2 * 60 * 1000L;
    public static final String OVERTIME_NO_DELIVERY_TAG = "OVERTIME_NO_DELIVERY_TAG";
    public static final String OVERTIME_NO_DELIVERY_KEY = "OVERTIME_NO_DELIVERY_KEY";

    // 超时无物流
    //public static final Long OVERTIME_NO_LOGISTICS = 24 * 60 * 60 * 1000L;
    public static final Long OVERTIME_NO_LOGISTICS = 2 * 60 * 1000L;
    public static final String OVERTIME_NO_LOGISTICS_TAG = "OVERTIME_NO_LOGISTICS_TAG";
    public static final String OVERTIME_NO_LOGISTICS_KEY = "OVERTIME_NO_LOGISTICS_KEY";

    // 超时未走件
    //public static final Long OVERTIME_NO_MOVING = 24 * 60 * 60 * 1000L;
    public static final Long OVERTIME_NO_MOVING = 2 * 60 * 1000L;
    public static final String OVERTIME_NO_MOVING_KEY = "OVERTIME_NO_MOVING_KEY";
    public static final String OVERTIME_NO_MOVING_TAG = "OVERTIME_NO_MOVING_TAG";

    // 严重超时无物流
    //public static final Long SERIOUS_OVERTIME = 24 * 60 * 60 * 1000L;
    public static final Long SERIOUS_OVERTIME = 2 * 60 * 1000L;
    public static final String SERIOUS_OVERTIME_KEY = "SERIOUS_OVERTIME_KEY";
    public static final String SERIOUS_OVERTIME_TAG = "SERIOUS_OVERTIME_TAG";

    // 采购价调价周期开始
    public static final Long ADJUST_COST_START = 24 * 60 * 60 * 1000L;
    public static final String ADJUST_COST_START_KEY = "ADJUST_COST_START_KEY";
    public static final String ADJUST_COST_START_TAG = "ADJUST_COST_START_TAG";

    // 采购价调价周期结束
    public static final Long ADJUST_COST_END = 24 * 60 * 60 * 1000L;
    public static final String ADJUST_COST_END_KEY = "ADJUST_COST_END_KEY";
    public static final String ADJUST_COST_END_TAG = "ADJUST_COST_END_TAG";

    // 违规单发货时间回填
    public static final String VIOLATE_DELIVERY_TIME_BACK_KEY = "VIOLATE_DELIVERY_TIME_BACK_KEY";
    public static final String VIOLATE_DELIVERY_TIME_BACK_TAG = "VIOLATE_DELIVERY_TIME_BACK_TAG";

}