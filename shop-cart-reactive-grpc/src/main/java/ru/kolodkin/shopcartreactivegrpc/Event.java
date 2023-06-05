package ru.kolodkin.shopcartreactivegrpc;

import net.devh.boot.grpc.server.event.GrpcServerStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Event {
    @EventListener
    public void onServerStarted(GrpcServerStartedEvent event) {
        System.out.println("gRPC Server started, listening on address: " + event.getAddress() + ", port: " + event.getPort());
    }
}
