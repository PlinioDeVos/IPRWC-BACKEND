package nl.plinio.backend.endpoints.image.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.model.BaseEntity;

@Entity
@Table(name = "images")
@Getter @Setter
public class Image extends BaseEntity {
    private String filename;

    private String mimeType;

    private byte[] data;

    @OneToOne(mappedBy = "image")
    private Product product;
}
