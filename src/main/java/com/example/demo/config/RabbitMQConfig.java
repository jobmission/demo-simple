//package com.example.demo.config;
//
//import com.example.demo.domain.GlobalConstant;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.QueueBuilder;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@Configuration
//public class RabbitMQConfig {
//    /**
//     * 声明Topic交换机
//     *
//     * @return
//     */
//    @Bean
//    TopicExchange topicExchange() {
//        return new TopicExchange(GlobalConstant.MQ_EXCHANGE_TOPIC_TEST);
//    }
//
/////    @Bean
/////    public MessageConverter jsonMessageConverter() {
/////        return new Jackson2JsonMessageConverter();
/////   }
//
//    /**
//     * 声明队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue queue() {
//        /// Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
//        Map<String, Object> args = new HashMap<>(16);
/////       x-dead-letter-exchange    声明  死信交换机
//        args.put("x-dead-letter-exchange", GlobalConstant.MQ_EXCHANGE_TOPIC_DLX);
/////       x-dead-letter-routing-key    声明 死信路由键
//        args.put("x-dead-letter-routing-key", GlobalConstant.MQ_ROUTING_KEY_DLX);
//        return QueueBuilder.durable(GlobalConstant.MQ_TOPIC_QUEUE_TEST).withArguments(args).build();
//    }
//
//    /**
//     * 将队列与Topic交换机进行绑定，并指定路由键
//     *
//     * @param queue
//     * @param topicExchange
//     * @return
//     */
//    @Bean
//    Binding topicBinding(Queue queue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(queue).to(topicExchange).with(GlobalConstant.MQ_ROUTING_KEY_TEST);
//    }
//
//
//    @Bean
//    TopicExchange topicExchangeDlx() {
//        return new TopicExchange(GlobalConstant.MQ_EXCHANGE_TOPIC_DLX);
//    }
//
//    @Bean
//    public Queue queue2() {
//        return QueueBuilder.durable(GlobalConstant.MQ_TOPIC_QUEUE_DLX).build();
//    }
//
//    @Bean
//    Binding topicBinding2(Queue queue2, TopicExchange topicExchangeDlx) {
//        return BindingBuilder.bind(queue2).to(topicExchangeDlx).with(GlobalConstant.MQ_ROUTING_KEY_DLX);
//    }
//
//}
