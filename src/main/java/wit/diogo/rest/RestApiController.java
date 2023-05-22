package wit.diogo.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wit.diogo.rest.rabbitmq.MessageProducer;
import wit.diogo.rest.rabbitmq.enums.Operation;
import wit.diogo.rest.rabbitmq.dto.MessageDto;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private MessageProducer messageProducer;

    public RestApiController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping("/sum")
    public ResponseEntity<String> sum(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {
        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.ADD));
        return ResponseEntity.ok("Sent sum operation...\nResult -> " + result);
    }

    @PostMapping("/subtract")
    public ResponseEntity<String> subtract(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {
        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.SUB));
        return ResponseEntity.ok("Sent sub operation...\nResult -> " + result);
    }

    @PostMapping("/multiply")
    public ResponseEntity<String> multiply(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {
        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.MUL));
        return ResponseEntity.ok("Sent mul operation...\nResult -> " + result);
    }

    @PostMapping("/divide")
    public ResponseEntity<String> divide(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {
        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.DIV));
        return ResponseEntity.ok("Sent div operation...\nResult -> " + result);
    }
}
