package ru.practicum.ewm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import ru.practicum.ewm.stats.client.StatsClient;

@Configuration
public class StatsClientConfig {

    @Value("${stats-server.url:http://stats-server:9090}")
    private String statsServerUrl;

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(statsServerUrl)
                .build();
    }

    @Bean
    public StatsClient statsClient(RestClient restClient) {
        return new StatsClient(restClient);
    }
}
