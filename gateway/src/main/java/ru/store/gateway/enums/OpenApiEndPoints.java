package ru.store.gateway.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum OpenApiEndPoints {
    REGISTER("/identity/register"),
    TOKEN("/identity/token"),
    EUREKA("/eureka");

    final private String path;

    OpenApiEndPoints(final String path) {
        this.path = path;
    }

    public static List<String> getListEndPoints() {
        return List.of(
                REGISTER.getPath(),
                TOKEN.getPath(),
                EUREKA.getPath()
        );
    }
}
