package nl.plinio.backend.endpoints.image;

import nl.plinio.backend.endpoints.image.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ImageRepository extends JpaRepository<Image, UUID> {
    @Override
    List<Image> findAll();

    Optional<Image> findByFilename(String filename);

    boolean existsByFilename(String filename);
}
