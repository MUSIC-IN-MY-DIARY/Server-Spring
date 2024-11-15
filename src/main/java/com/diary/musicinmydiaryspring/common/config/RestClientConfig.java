package com.diary.musicinmydiaryspring.common.config;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.util.Timeout;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestClientConfig {

    private static final int CONNECT_TIMEOUT = 10000; // 연결 타임아웃
    private static final int BASE_READ_TIMEOUT = 9000; // 기본 읽기 타임아웃
    private static final int MAX_READ_TIMEOUT = 30000; // 최대 읽기 타임아웃
    private static final int LENGTH_FACTOR = 100; // 요청 길이에 따른 증가량 (ms)

    @Bean
    public RestTemplate restTemplate() {
        return createRestTemplate(BASE_READ_TIMEOUT);
    }

    public RestTemplate restTemplateForRequest(String request) {
        int dynamicReadTimeout = calculateDynamicTimeout(request);
        return createRestTemplate(dynamicReadTimeout);
    }

    private RestTemplate createRestTemplate(int readTimeout) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(createHttpClient(readTimeout));
        return new RestTemplate(factory);
    }

    private CloseableHttpClient createHttpClient(int readTimeout) {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECT_TIMEOUT))
                .setResponseTimeout(Timeout.ofMilliseconds(readTimeout))
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .build();
    }

    private int calculateDynamicTimeout(String request) {
        int additionalTimeout = request.length() * LENGTH_FACTOR;
        return Math.min(BASE_READ_TIMEOUT + additionalTimeout, MAX_READ_TIMEOUT);
    }

    @Bean
    public RestClient restClient(RestTemplate restTemplate) {
        return RestClient.create(restTemplate);
    }
}
