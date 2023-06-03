package ru.kolodkin.shopcartreactivegrpc.service;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.kolodkin.shopcartreactivegrpc.bean.ProductInfo;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Client {
    public Flux<ProductInfo> getProductsByIds(Flux<Long> productIds) {
        return productIds.collectList()
                .flatMapMany(ids -> WebClient.create("http://localhost:8888/product/listByIds")
                        .get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("productIds", ids)
                                .build())
                        .retrieve()
                        .bodyToFlux(ProductInfo.class));
    }

    public Mono<ProductInfo> getProductById(Long  id){
        return  WebClient.create("http://localhost:8888/product")
                .get()
                .uri(uriBuilder -> uriBuilder.path(String.format("/%d",id))
                        .build())
                .retrieve()
                .bodyToMono(ProductInfo.class);
    }

    public Mono<ProductInfo> getProductById(Mono<Long>  id){
        return id.flatMap(productId -> WebClient.create("http://localhost:8888/product")
                .get()
                .uri(uriBuilder -> uriBuilder.path(String.format("/%d",productId))
                        .build())
                .retrieve()
                .bodyToMono(ProductInfo.class));
    }
}
