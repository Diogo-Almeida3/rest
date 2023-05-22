package wit.diogo.rest;

import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wit.diogo.rest.rabbitmq.MessageProducer;
import wit.diogo.rest.rabbitmq.dto.MessageDto;
import wit.diogo.rest.rabbitmq.enums.Operation;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private MessageProducer messageProducer;

    public RestApiController(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    @PostMapping(value = "/sum", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> sum(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {

        HttpHeaders headers = new HttpHeaders();
        String identifier = generateIdAndHeader(headers);

        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.ADD));

        return ResponseEntity.ok().headers(headers).body(generateBody(result));
    }


    @PostMapping(value = "/subtract", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> subtract(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {

        HttpHeaders headers = new HttpHeaders();
        String identifier = generateIdAndHeader(headers);

        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.SUB));
        return ResponseEntity.ok().headers(headers).body(generateBody(result));
    }

    @PostMapping(value = "/multiply", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> multiply(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {

        HttpHeaders headers = new HttpHeaders();
        String identifier = generateIdAndHeader(headers);

        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.MUL));
        return ResponseEntity.ok().headers(headers).body(generateBody(result));
    }

    @PostMapping(value = "/divide", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> divide(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue) {

        HttpHeaders headers = new HttpHeaders();
        String identifier = generateIdAndHeader(headers);

        BigDecimal result = messageProducer.sendMessage(new MessageDto(firstValue, secondValue, Operation.DIV));
        return ResponseEntity.ok().headers(headers).body(generateBody(result));
    }


    private String generateIdAndHeader(HttpHeaders headers) {
        String identifier = UUID.randomUUID().toString();
        headers.set("Unique-Identifier", identifier);
        return identifier;
    }

    private String generateBody(BigDecimal result) {
        JSONObject json = new JSONObject();
        json.put("result", result);
        return json.toString();
    }
}
