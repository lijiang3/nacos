package com.admin.nacosconsumer.controller;

import com.rabbitmq.client.Channel;
import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;
/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Component
//@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {
 
//    @RabbitHandler
//    public void process(Map testMessage) {
//        System.out.println("FanoutReceiverA消费者收到消息  : " +testMessage.toString());
//    }


    @RabbitHandler
    public void process(Message message, Channel channel) throws IOException {
        try {
            // 采用手动应答模式, 手动确认应答更为安全稳定
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            //log.info("receive: " + new String(message.getBody()));
            //log.info("MyAckReceiver2 receive: " + message.toString());
        }catch (Exception e){
            //失败后消息被确认
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            //log.info("mq接收消息失败",e);
        }
    }

}
