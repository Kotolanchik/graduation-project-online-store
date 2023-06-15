package ru.kolodkin.store.catalognooptimization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kolodkin.store.catalognooptimization.domain.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
