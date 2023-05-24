package wit.diogo.rest;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class Interceptor implements Filter {

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        MDC.put("identifier", UUID.randomUUID().toString());

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void init(final FilterConfig filterConfig) {
    }
}