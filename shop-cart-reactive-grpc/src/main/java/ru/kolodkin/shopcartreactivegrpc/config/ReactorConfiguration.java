package ru.kolodkin.shopcartreactivegrpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.util.function.Function;

@Configuration
public class ReactorConfiguration {
    @Bean
    public ReactorResourceFactory resourceFactory() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("connectionPool")
                .maxConnections(2000)
                .pendingAcquireMaxCount(2000)
                .build();
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        factory.setConnectionProvider(connectionProvider);
        factory.setLoopResources(LoopResources.create("loopRes", 4, true));
        return factory;
    }

    @Bean
    public WebClient webClientCustomizer() {
        Function<HttpClient, HttpClient> mapper = client -> client;
        ClientHttpConnector connector = new ReactorClientHttpConnector(resourceFactory(), mapper);
        return WebClient.builder()
                .clientConnector(connector)
                .build();
    }
}
