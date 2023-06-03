package ru.store.gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import ru.store.gateway.enums.OpenApiEndPoints;

import java.util.function.Predicate;

@Component
public class RouteValidator {
    public Predicate<ServerHttpRequest> isSecured =
            request -> OpenApiEndPoints.getListEndPoints()
                    .stream()
                    .noneMatch(uri -> request.getURI()
                            .getPath()
                            .contains(uri)
                    );
}
