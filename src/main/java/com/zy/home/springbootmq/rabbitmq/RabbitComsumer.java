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

    @RabbitListener(queues = "test_queue_work")
    public void testQueueWork1(String value,Message message,Channel channel) throws InterruptedException, IOException {
        System.out.println("testQueueWork[1]"+value);
        //basicQos 方法设置了当前信道最大预获取（prefetch）消息数量为1,
        //prefetchCount 的默认值为0，即没有限制，队列会将所有消息尽快发给消费者
        channel.basicQos(1);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = "test_queue_work")
    public void testQueueWork2(String value,Message message,Channel channel) throws InterruptedException, IOException {
        System.out.println("testQueueWork[2]"+value);
        //basicQos 方法设置了当前信道最大预获取（prefetch）消息数量为1,
        //prefetchCount 的默认值为0，即没有限制，队列会将所有消息尽快发给消费者
        channel.basicQos(1);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = "fanout.a")
    public void testFanoutExchangeA(String value,Message message,Channel channel) throws InterruptedException, IOException {
        System.out.println("testFanoutExchangeA"+value);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

    @RabbitListener(queues = "fanout.b")
    public void testFanoutExchangeB(String value,Message message,Channel channel) throws InterruptedException, IOException {
        System.out.println("testFanoutExchangeB"+value);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }

}
