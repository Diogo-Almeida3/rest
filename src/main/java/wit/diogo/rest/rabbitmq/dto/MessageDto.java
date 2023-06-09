package wit.diogo.rest.rabbitmq.dto;

import lombok.Data;
import wit.diogo.rest.rabbitmq.enums.Operation;

import java.math.BigDecimal;

@Data
public class MessageDto {
    private BigDecimal firstValue;
    private BigDecimal secondValue;
    private Operation operation;
    private String identifier;

    public MessageDto(BigDecimal firstValue, BigDecimal secondValue, Operation operation, String identifier) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.operation = operation;
        this.identifier = identifier;
    }
}
