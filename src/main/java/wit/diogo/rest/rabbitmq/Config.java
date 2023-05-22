package wit.diogo.rest.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${rabbitmq.configuration.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.configuration.write-queue.name}")
    private String writeQueue;
    @Value("${rabbitmq.configuration.write-routing-key.name}")
    private String writeRoutingKey;


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue writeQueue() {
        return new Queue(writeQueue);
    }

    @Bean
    public Binding writeBinding() {
        return BindingBuilder.bind(writeQueue()).to(exchange()).with(writeRoutingKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
