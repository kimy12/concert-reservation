package kr.hhplus.be.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.api.common.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(new ObjectMapper()))
                .excludePathPatterns("/concert/**");
    }
}
