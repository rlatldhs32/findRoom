package sion.bestRoom.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class FeignLogConfiguration {

//    private static final Logger logger = LoggerFactory.getLogger(FeignLogConfiguration.class);

    @Bean
    public RequestInterceptor userAgentInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                template.header("User-Agent", "insomnia/2023.5.8");
            }
        };
    }
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return requestTemplate -> {
//            logger.info("Request URL: {}", requestTemplate.url());
//            logger.info("Request Method: {}", requestTemplate.method());
//            logger.info("Request Headers: {}", requestTemplate.headers());
//
//            if (requestTemplate.body() != null) {
//                logger.info("Request Body: {}", new String(requestTemplate.body()));
//            }
//        };
//    }
//
//    @Bean
//    public ErrorDecoder errorDecoder() {
//        return new CustomErrorDecoder();
//    }
//
//    public class CustomErrorDecoder implements ErrorDecoder {
//        @Override
//        public Exception decode(String methodKey, Response response) {
//            Request request = response.request();
//
//            logger.error("Error on API call:");
//            logger.error("URL: {}", request.url());
//            logger.error("Method: {}", request.httpMethod());
//            logger.error("Headers: {}", request.headers());
//            logger.error("Status code: {}", response.status());
//
//            // 응답 본문 로깅
//            if (response.body() != null) {
//                try {
//                    String responseBody = new String(response.body().asInputStream().readAllBytes());
//                    logger.error("Response Body: {}", responseBody);
//                } catch (Exception e) {
//                    logger.error("Error reading response body", e);
//                }
//            }
//
//            // 여기서 적절한 예외를 throw합니다.
//            return new RuntimeException("API call failed");
//        }
//    }
}