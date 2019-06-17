package com.zy.home.springbootmq.rabbitmq;

import com.rabbitmq.client.Channel;
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

    @RequestMapping(value = "/testQueueWork")
    public String testQueueWork(){
        for (int i=0;i<100; i++){
            rabbitTemplate.convertAndSend("test_queue_work",i);
        }
        return "ok!";
    }

    //fanoutExchange模式下，即便指定了路由关键字，也会忽略，发送到exchange指定的队列
    //实现一个生产者被多个消费者获取的目的
    @RequestMapping(value = "/testFanoutExchange")
    public String testFanoutExchange(){
        //rabbitTemplate.convertAndSend("fanoutExchange",""abcd.ee,"嘿嘿嘿");
        rabbitTemplate.convertAndSend("fanoutExchange","","嘿嘿嘿");
        return "ok";
    }

}
