create table shop_cart
(
    product_id bigint not null,
    user_id    bigint not null,
    quantity   bigint not null,
    primary key (product_id, user_id)
);

alter table shop_cart
    owner to postgres;

CREATE EXTENSION pg_trgm;
