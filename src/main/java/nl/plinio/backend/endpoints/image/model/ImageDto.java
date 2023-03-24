package nl.plinio.backend.endpoints.image.model;

import lombok.Value;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Value
public class ImageDto {
    UUID id;
    String filename;
    String mime_type;
    String image_link;

    public ImageDto(Image image) {
        this.id = image.getId();
        this.filename = image.getFilename();
        this.mime_type = getMime_type();
        this.image_link = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/image/products/" + filename)
                .toUriString();
    }
}
