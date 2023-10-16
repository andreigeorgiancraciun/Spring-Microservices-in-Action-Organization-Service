package com.optimagrowth.organization.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.optimagrowth.organization.utils.UserContext.AUTH_TOKEN;
import static com.optimagrowth.organization.utils.UserContext.CORRELATION_ID;

@Component
public class UserContextInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders headers = request.getHeaders();

        headers.add(CORRELATION_ID, UserContextHolder.getContext().getCorrelationId());
        headers.add(AUTH_TOKEN, UserContextHolder.getContext().getAuthToken());

        return execution.execute(request, body);
    }
}
