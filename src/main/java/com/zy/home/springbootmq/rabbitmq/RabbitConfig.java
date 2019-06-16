package com.zy.home.springbootmq.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class RabbitConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue hello(){
        return new Queue("hello");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        rabbitTemplate.setMandatory(true);
        // 实现ReturnCallback
        // 当消息发送出去找不到对应路由队列时，将会把消息退回
        // 如果有任何一个路由队列接收投递消息成功，则不会退回消息
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("消息发送失败: " + Arrays.toString(message.getBody()));
        });

        // 实现ConfirmCallback
        // ACK=true仅仅标示消息已被Broker接收到，并不表示已成功投放至消息队列中
        // ACK=false标示消息由于Broker处理错误，消息并未处理成功
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("消息id: " + correlationData + "确认" + (ack ? "成功:" : "失败"));
        });

        return rabbitTemplate;
    }


}
