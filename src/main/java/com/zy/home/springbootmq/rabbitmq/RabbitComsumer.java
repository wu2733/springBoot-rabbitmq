package com.zy.home.springbootmq.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RabbitComsumer {

    @RabbitListener(queues = "hello")
    public void listenerHello(String value, Message message, Channel channel) throws IOException {
        if(value.equals("你好！")){
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }else{
            if(message.getMessageProperties().getDeliveryTag() < 5){
                //重回队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
            }else {
                //消息的序号大于4--丢弃
                channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,false);
            }
        }
        System.out.println("收到消息 - - >"+value);
    }

}
