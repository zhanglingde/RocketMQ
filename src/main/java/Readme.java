public class Readme {

    /**
     * 消息生产者：
     * <ul>
     *     <li> 同步消息：可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。      {@link com.ling.base.producer.SyncProducer SyncProducer} </li>
     *     <li> 异步消息：异步消息通常用在对响应时间敏感的业务场景，即发送端不能容忍长时间地等待Broker的响应。      {@link com.ling.base.producer.AsyncProducer AsyncProducer} </li>
     *     <li> 单向消息：这种方式主要用在不特别关心发送结果的场景，例如日志发送      {@link com.ling.base.producer.OnewayProducer OnewayProducer} </li>
     * </ul>
     *
     * 消费者消息消费方式 {@link com.ling.base.consumer.Consumer}
     * <ul>
     *     <li>
     *         负载均衡模式：只消费一次，集群中任一消费者消费处理就可以了
     *          <ul>
     *              <li> 每条消息只需要被处理一次，broker 只会把消息发送给消费者集群中的一个消费者 </li>
     *              <li> 在消息重投时，不能保证路由到同一台机器上 </li>
     *              <li> 消费状态由 broker 维护 </li>
     *          </ul>
     *     </li>
     *     <li>
     *         广播模式消费：当使用广播消费模式时，MQ 会将每条消息推送给集群内所有注册过的消费者，保证消息至少被每台机器消费一次
     *          <ul>
     *              <li> 消费进度由 consumer 维护 </li>
     *              <li> 保证每个消费者消费一次消息 </li>
     *              <li> 消费失败的消息不会重投 </li>
     *          </ul>
     *     </li>
     * </ul>
     */
    void baseMessage(){}

    /**
     * 顺序消息：顺序消息的生产者需要保证同一类型的消息发送到同一个队列中
     * <ul>
     *     <li> {@link com.ling.order.OrderProducer} </li>
     *     <li> {@link com.ling.order.OrderConsumer} </li>
     * </ul>
     *
     */
    void orderMessage(){}

    /**
     * 延时消息
     */
    void delay(){}
}
