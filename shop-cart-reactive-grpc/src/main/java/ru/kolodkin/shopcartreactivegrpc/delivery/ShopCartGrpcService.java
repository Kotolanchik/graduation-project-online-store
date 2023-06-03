package ru.kolodkin.shopcartreactivegrpc.delivery;

import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.devh.boot.grpc.server.service.GrpcService;
import reactor.core.publisher.Mono;
import ru.grpc.shop.cart.service.*;
import ru.kolodkin.shopcartreactivegrpc.service.ShopCartService;

import java.time.Duration;

@GrpcService
@RequiredArgsConstructor
public class ShopCartGrpcService extends ReactorShopCartServiceGrpc.ShopCartServiceImplBase {
    private final ShopCartService shopCartService;

    private static final Long TIMEOUT_MILLIS = 5000L;


    @Override
    public Mono<AddProductInShopCartResponse> addProductInShopCart(Mono<AddProductInShopCartRequest> request) {
        return request.flatMap(req -> shopCartService.addProductInShopCart(req.getUserId(), req.getProductId()))
                .map(shopCart -> AddProductInShopCartResponse.newBuilder()
                        .setShopCart(ShopCart.newBuilder()
                                .setProductId(shopCart.getProductId())
                                .setQuantity(shopCart.getQuantity())
                                .setUserId(shopCart.getUserId())
                                .build())
                        .build())
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS));
    }


    @Override
    public Mono<GetUserShopCartResponse> getAllShopCart(Mono<GetUserShopCartRequest> request) {
    /*
      еще вариант, проверку не проходил на работу
      val builder = GetUserShopCartResponse.newBuilder();

        request.flatMapMany(req -> shopCartService.getAllShopCart(req.getUserId())
                        .map(shopCartDTO -> ShopCartDTO.newBuilder()
                                .setProduct(toProductInfoForProto(shopCartDTO.getProduct()))
                                .setQuantity(shopCartDTO.getQuantity())
                                .setUserId(shopCartDTO.getUserId())
                                .build()))
                .subscribe(builder::addShopCart)
                .dispose();

        return Mono.just(builder.build());*/
        return request.flatMapMany(req -> shopCartService.getAllShopCart(req.getUserId())
                        .map(shopCartDTO -> ShopCartDTO.newBuilder()
                                .setProduct(toProductInfoForProto(shopCartDTO.getProduct()))
                                .setQuantity(shopCartDTO.getQuantity())
                                .setUserId(shopCartDTO.getUserId())
                                .build()))
                .collectList()
                .map(shopCart -> GetUserShopCartResponse.newBuilder()
                        .addAllShopCart(shopCart)
                        .build())
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS));
       /* request.flatMap(req -> shopCartService.addProductInShopCart(req.getUserId(), req.getProductId()))
                .map(shopCart -> AddProductInShopCartResponse.newBuilder()
                        .setShopCart(ShopCart.newBuilder()
                                .setProductId(shopCart.getProductId())
                                .setQuantity(shopCart.getQuantity())
                                .setUserId(shopCart.getUserId())
                                .build())
                        .build())
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS));


        val response = GetUserShopCartResponse.newBuilder();

        val shopCartDTOFlux = userIdFromRequest.map(shopCartService::getAllShopCart);


        shopCartDTOFlux.flatMap(cartFlux -> cartFlux.flatMap())

        request.flatMap(req -> shopCartService.getAllShopCart(req.getUserId())
                .flatMap(shopCartDTO -> ShopCartDTO.newBuilder()
                        .setProduct(toProductInfoForProto(shopCartDTO.getProduct()))
                        .setQuantity(shopCartDTO.getQuantity())
                        .setUserId(shopCartDTO.getUserId())
                        .build())
                .)
        ;
*/
       /* val res = request.flatMap(req -> shopCartService.getAllShopCart(req.getUserId()));

        val result = request.flatMap(req -> shopCartService.getAllShopCart(req.getUserId())
                .flatMap(shopCartDTO ->
                )
                .setAttributes(shopCartDTO.getProduct().getAttributes()
                        .stream().map(attribute ->
                                AttributeInfo.newBuilder()
                                        .setDescription(attribute.getDescription())
                                        .setName(attribute.getName())
                                        .setId(attribute.getId())
                                        .build()))

                .setImages(shopCartDTO.getProduct().getImages()
                        .stream().map(image -> ImageInfo.newBuilder()
                                .setId(image.getId())
                                .setName(image.getName())
                                .setSize(image.getSize())
                                .setPath(ByteString.copyFrom(image.getPath()))
                                .build()))
        return request.flatMap(req -> shopCartService.getAllShopCart(req.getUserId())
                .flatMap(shopCartDTO -> ShopCartDTO.newBuilder()
                        .setProduct(ProductInfo.newBuilder()
                                .setId(shopCartDTO.getProduct().getId())
                                .setAmount(shopCartDTO.getProduct().getAmount())
                                .setAttributes(shopCartDTO.getProduct().getAttributes()
                                        .stream().map(attribute ->
                                                AttributeInfo.newBuilder()
                                                        .setDescription(attribute.getDescription())
                                                        .setName(attribute.getName())
                                                        .setId(attribute.getId())
                                                        .build()))
                                .setCategory(CategoryInfo.newBuilder().build())
                                .setImages(shopCartDTO.getProduct().getImages()
                                        .stream().map(image -> ImageInfo.newBuilder()
                                                .setId(image.getId())
                                                .setName(image.getName())
                                                .setSize(image.getSize())
                                                .setPath(ByteString.copyFrom(image.getPath()))
                                                .build()))
                                .setName(shopCartDTO.getProduct().getName())
                                .setPrice(shopCartDTO.getProduct().getPrice())
                                .setState(shopCartDTO.getProduct().getState())
                                .setDescription(shopCartDTO.getProduct().getDescription())
                                .build())
                        .setQuantity(shopCartDTO.getQuantity())
                        .setUserId(shopCartDTO.getUserId())
                        .build())
        )
                ;*/
    }

    @Override
    public Mono<UpdateProductQuantityInShopCartResponse> updateProductQuantityInShopCart(Mono<UpdateProductQuantityInShopCartRequest> request) {
        return request.flatMap(req -> shopCartService.updateProductQuantityInShopCart(
                        req.getShopCart().getUserId(),
                        req.getShopCartOrBuilder().getProductId(),
                        req.getShopCart().getQuantity()))
                .map(shopCart -> UpdateProductQuantityInShopCartResponse.newBuilder()
                        .setShopCart(ShopCart.newBuilder()
                                .setProductId(shopCart.getProductId())
                                .setQuantity(shopCart.getQuantity())
                                .setUserId(shopCart.getUserId())
                                .build())
                        .build())
                .timeout(Duration.ofMillis(TIMEOUT_MILLIS));
    }

    @Override
    public Mono<GetShopCartItemResponse> getShopCartProduct(Mono<GetShopCartItemRequest> request) {
        return request.flatMap(req -> shopCartService.getShopCartProduct(req.getUserId(), req.getProductId()))
                .map(shopCartDTO -> GetShopCartItemResponse.newBuilder()
                        .setShopCart(ShopCartDTO.newBuilder()
                                .setProduct(toProductInfoForProto(shopCartDTO.getProduct()))
                                .setUserId(shopCartDTO.getUserId())
                                .setQuantity(shopCartDTO.getQuantity())
                                .build())
                        .build());
    }

    @Override
    public Mono<Empty> deleteProductFromShopCart(Mono<DeleteProductFromShopCartRequest> request) {
        request.map(req -> shopCartService.deleteProductFromShopCart(req.getUserId(), req.getProductId()));
        return Mono.just(Empty.newBuilder().build());
    }

    @Override
    public Mono<Empty> deleteProductsFromShopCart(Mono<DeleteProductsFromShopCartRequest> request) {
        request.map(req -> shopCartService.deleteProductsFromShopCart(req.getUserId()));
        return Mono.just(Empty.newBuilder().build());
    }

    private ProductInfo toProductInfoForProto(ru.kolodkin.shopcartreactivegrpc.bean.ProductInfo productInfo) {
        val product = ProductInfo.newBuilder()
                .setAmount(productInfo.getAmount())
                .setCategory(CategoryInfo.newBuilder().setId(productInfo.getId()).setName(productInfo.getName()).build())
                .setId(productInfo.getId())
                .setName(productInfo.getName())
                .setDescription(productInfo.getDescription())
                .setState(ProductState.AVAILABLE.name().equals(productInfo.getState().name())
                        ? ProductState.AVAILABLE : ProductState.NOT_AVAILABLE)
                .setPrice(productInfo.getPrice().toString());

        productInfo.getAttributes()
                .stream().map(attribute -> AttributeInfo.newBuilder()
                        .setId(attribute.getId())
                        .setName(attribute.getName())
                        .setDescription(attribute.getDescription())
                        .build())
                .forEach(product::addAttributes);

        productInfo.getImages()
                .stream().map(image -> ImageInfo.newBuilder()
                        .setPath(ByteString.copyFrom(image.getPath()))
                        .setSize(image.getSize())
                        .setId(image.getId())
                        .setName(image.getName())
                        .build())
                .forEach(product::addImages);

        return product.build();
    }

    private ShopCartDTO toShopCartDTOForProto(ru.kolodkin.shopcartreactivegrpc.bean.ShopCartDTO shopCartDTO) {
        return ShopCartDTO.newBuilder()
                .setProduct(toProductInfoForProto(shopCartDTO.getProduct()))
                .setQuantity(shopCartDTO.getQuantity())
                .setUserId(shopCartDTO.getUserId())
                .build();
    }
}

