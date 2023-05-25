package wit.diogo.rest.logging;


import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class Interceptor implements HandlerInterceptor {

    private static final String IDENTIFIER_MDC_KEY = "identifier";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String identifier = UUID.randomUUID().toString();
        MDC.put(IDENTIFIER_MDC_KEY, identifier);

        response.addHeader("Unique-Identifier", identifier);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        //MDC.remove(IDENTIFIER_MDC_KEY);
    }
}
