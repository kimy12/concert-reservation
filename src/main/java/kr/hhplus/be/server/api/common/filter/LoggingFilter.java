package kr.hhplus.be.server.api.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("Filter init");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(req);
        String requestBody = getContentAsString(requestWrapper.getContentAsByteArray(), request.getCharacterEncoding());
        log.info("Request Method: {}, URI: {}, Body: {}",
                requestWrapper.getMethod(),
                requestWrapper.getRequestURI(),
                requestBody);
        chain.doFilter(request, response);
    }

    private String getContentAsString(byte[] content, String encoding) {
        try {
            return new String(content, encoding != null ? encoding : StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            log.warn("fail to encoding", e);
            return "[unknown]";
        }
    }

    @Override
    public void destroy() {
        log.info("Filter destroy");
        Filter.super.destroy();
    }
}
