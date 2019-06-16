package com.zy.home.springbootmq.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/helloRabbit")
    public String helloRabbit(String queue,String value){
        rabbitTemplate.convertAndSend(queue,value);
        return "ok!";
    }

}
