package com.richard;

import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import java.util.Optional;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Filter("/**")
public class ProductLoggingFilter implements HttpServerFilter {

    Logger LOGGER = LoggerFactory.getLogger(ProductLoggingFilter.class);

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
//        request.getMethod();
        HttpMethod method = request.getMethod();
        LOGGER.info("Method: {}, URI: {}", method, request.getUri());
        if (method.equals(HttpMethod.POST) || method.equals(HttpMethod.PUT)) {
            Optional<?> body = request.getBody();
            body.ifPresentOrElse(b -> LOGGER.info("Body: {}", b), () -> LOGGER.info("NO Body Found"));

        }

        return chain.proceed(request);
    }
}
