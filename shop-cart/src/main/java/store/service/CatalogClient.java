package store.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import store.bean.ProductInfo;

import java.util.List;

@Service

public class CatalogClient {
    @Value("${catalog-api}")
    private String catalogUri;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<ProductInfo> getProductsByIds(List<Long> ids) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(String.format("%s%s", catalogUri, "product/listByIds"));
        builder.queryParam("productIds", ids);
        ResponseEntity<List<ProductInfo>> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                ids.toArray()
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve products by IDs");
        }
    }

    public ProductInfo getProductById(Long id) {
        return restTemplate.getForObject(String.format("%sproduct/%d", catalogUri, id), ProductInfo.class);
    }
}
