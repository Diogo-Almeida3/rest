package wit.diogo.rest;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wit.diogo.rest.rabbitmq.MessageHandler;
import wit.diogo.rest.rabbitmq.dto.MessageDto;
import wit.diogo.rest.rabbitmq.enums.Operation;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestApiController.class);
    private MessageHandler messageHandler;

    public RestApiController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @PostMapping(value = "/sum", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> sum(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue,
                                      HttpServletRequest request) {

        String identifier = MDC.get("identifier");

        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " - First Value -> " + firstValue + " - Second Value -> " + secondValue);

        BigDecimal result = messageHandler.sendMessage(new MessageDto(firstValue, secondValue, Operation.ADD, identifier));

        if (result == null) {
            return ResponseEntity.badRequest().body(generateError());
        }

        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " -  Result -> " + result);
        return ResponseEntity.ok().body(generateBody(result));
    }


    @PostMapping(value = "/subtract", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> subtract(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue,
                                           HttpServletRequest request) {

        String identifier = MDC.get("identifier");
        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " - First Value -> " + firstValue + " - Second Value -> " + secondValue);

        BigDecimal result = messageHandler.sendMessage(new MessageDto(firstValue, secondValue, Operation.SUB, identifier));

        if (result == null) {
            return ResponseEntity.badRequest().body(generateError());
        }

        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " -  Result -> " + result);
        return ResponseEntity.ok().body(generateBody(result));
    }

    @PostMapping(value = "/multiply", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> multiply(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue,
                                           HttpServletRequest request) {


        String identifier = MDC.get("identifier");
        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " - First Value -> " + firstValue + " - Second Value -> " + secondValue);

        BigDecimal result = messageHandler.sendMessage(new MessageDto(firstValue, secondValue, Operation.MUL, identifier));

        if (result == null) {
            return ResponseEntity.badRequest().body(generateError());
        }

        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " -  Result -> " + result);
        return ResponseEntity.ok().body(generateBody(result));
    }

    @PostMapping(value = "/divide", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> divide(@RequestParam BigDecimal firstValue, @RequestParam BigDecimal secondValue,
                                         HttpServletRequest request) {


        String identifier = MDC.get("identifier");
        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " - First Value -> " + firstValue + " - Second Value -> " + secondValue);

        BigDecimal result = messageHandler.sendMessage(new MessageDto(firstValue, secondValue, Operation.DIV, identifier));

        if (result == null) {
            return ResponseEntity.badRequest().body(generateError());
        }

        LOGGER.info("Module -> Rest - Request -> " + request.getRequestURI() + " - Identifier -> " + identifier + " -  Result -> " + result);
        return ResponseEntity.ok().body(generateBody(result));
    }

    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addContextValves(new LogbackValve());
        return tomcat;
    }

    private String generateBody(BigDecimal result) {
        JSONObject json = new JSONObject();
        json.put("result", result);
        return json.toString();
    }


    private String generateError() {
        JSONObject json = new JSONObject();
        json.put("error", "Problem performing the request");
        return json.toString();
    }
}
