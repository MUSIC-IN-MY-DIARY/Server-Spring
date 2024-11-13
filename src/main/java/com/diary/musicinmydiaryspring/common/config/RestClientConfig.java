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

    private static final int READ_TIMEOUT = 7000;
    private static final int CONNECT_TIMEOUT = 10000;

    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(createHttpClient());
        return new RestTemplate(factory);
    }

    private CloseableHttpClient createHttpClient() {
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(CONNECT_TIMEOUT))
                .setResponseTimeout(Timeout.ofMilliseconds(READ_TIMEOUT))
                .build();

        return HttpClients.custom()
                .setDefaultRequestConfig(config)
                .setConnectionManager(new PoolingHttpClientConnectionManager())
                .build();
    }

    @Bean
    public RestClient restClient(RestTemplate restTemplate) {
        return RestClient.create(restTemplate);
    }
}
