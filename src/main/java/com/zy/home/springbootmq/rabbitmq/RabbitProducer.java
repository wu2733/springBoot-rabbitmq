package com.zy.home.springbootmq.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer {

    @RabbitListener(queues = "hello")
    public void listenerHello(String value){
        System.out.println("收到消息 - - >"+value);
    }

}
