package wit.diogo.rest.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wit.diogo.rest.rabbitmq.dto.MessageDto;

import java.math.BigDecimal;

@Service
public class MessageProducer {

    @Value("${rabbitmq.configuration.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.configuration.write-routing-key.name}")
    private String routingKey;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);
    private RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public BigDecimal sendMessage(MessageDto message) {
        LOGGER.info("Message sent -> " + message.toString());

        BigDecimal result = (BigDecimal) rabbitTemplate.convertSendAndReceive(exchange, routingKey, message);

        if (result != null) {
            LOGGER.info("Message received -> " + result);
            return result;
        }
        return null;
    }
}
