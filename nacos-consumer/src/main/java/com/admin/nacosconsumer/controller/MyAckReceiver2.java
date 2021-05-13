package com.admin.nacosconsumer.controller;
/**
 * @title: MyAckReceiver2
 * @Author gjt
 * @Date: 2020-12-12
 * @Description：消息手动确认方式二：
 */
 
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;
 
import java.io.IOException;
 
/**
 * 消息手动确认方式二：
 */
 
@Component
public class MyAckReceiver2 {
    private Logger log = LoggerFactory.getLogger(MyAckReceiver2.class);
 
    /*@RabbitListener表示监听的具体队列.
        bindings属性代表绑定。里边有几个值填写,填写好绑定的队列名字和交换机名字
        指定好routingKey。若指定的这些参数不存在的话。则会自行给你创建好
        durable代表是否持久化
    */
    @RabbitListener(queues = "TestDirectQueue")
//    @RabbitListener(bindings = @QueueBinding(//绑定队列和交换机
//        value = @Queue(value = "TestDirectQueue", durable = "true"),//创建queuqe
//        exchange = @Exchange(value = "TestDirectExchange",type = "direct", durable = "true"),//创建交换机
//        key = "TestDirectRouting"//路由规则，routingkey如果是Direct-A就发到这个监听
//    ))
    @RabbitHandler
    public void process(Message message, Channel channel) throws IOException {
        try {
            // 采用手动应答模式, 手动确认应答更为安全稳定
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            //log.info("receive: " + new String(message.getBody()));
            log.info("MyAckReceiver2 receive: " + message.toString());
        }catch (Exception e){
            //失败后消息被确认
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            log.info("mq接收消息失败",e);
        }
    }
}
