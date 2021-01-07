//package com.example.demo.mq;
//
//import com.example.demo.domain.GlobalConstant;
//import com.rabbitmq.client.Channel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Component
//public class ConsumerTest implements InitializingBean {
//    private Logger log = LoggerFactory.getLogger(this.getClass());
//
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//    int counter;
//
//    @RabbitListener(queues = GlobalConstant.MQ_TOPIC_QUEUE_TEST)
//    public void processMessage(Message message, Channel channel) {
//        counter++;
//        System.out.println("date: " + simpleDateFormat.format(new Date()));
//        System.out.println("messageProperties: " + message.getMessageProperties());
//        System.out.println("receive--: " + new String(message.getBody()));
//        System.out.println("MessageId--: " + message.getMessageProperties().getMessageId());
//        System.out.println("CorrelationId--: " + message.getMessageProperties().getCorrelationId());
//        if (counter % 2 == 0) {
//
//            try {
//                System.out.println(1 / 0);
////                throw new Exception("手动抛出异常测试");
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            } catch (Exception e) {
//                log.error("处理消息异常：" + e.getMessage());
//                try {
//                    channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        } else {
//            try {
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//
//    }
//}
