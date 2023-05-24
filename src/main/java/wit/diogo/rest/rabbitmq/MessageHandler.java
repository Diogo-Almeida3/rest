package wit.diogo.rest.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wit.diogo.rest.rabbitmq.dto.MessageDto;

import java.math.BigDecimal;

@Service
public class MessageHandler {

    @Value("${rabbitmq.configuration.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.configuration.write-routing-key.name}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;

    public MessageHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public BigDecimal sendMessage(MessageDto message) {
        return (BigDecimal) rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);
    }
}
