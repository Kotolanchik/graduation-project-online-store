package ru.kolodkin.shopcartreactivegrpc.config;

import io.netty.channel.ChannelOption;
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

@Configuration
public class NettyConfiguration {
    @Bean
    public NettyServerCustomizer nettyServerCustomizer() {
        return httpServer -> httpServer.idleTimeout(Duration.ofSeconds(10))
                .option(ChannelOption.SO_BACKLOG, 30000)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .tcpConfiguration((tcpClient -> tcpClient.runOn(LoopResources.create("loopRes", 4, true))))
                .childOption(ChannelOption.SO_KEEPALIVE, true);
    }
}
