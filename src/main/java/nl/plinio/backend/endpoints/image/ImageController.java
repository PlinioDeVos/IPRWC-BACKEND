package nl.plinio.backend.endpoints.image;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.image.model.Image;
import nl.plinio.backend.endpoints.image.model.ImageDto;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ImageDto> getAllImages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return imageService.getAllImages(page, size, sort);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("products/{filename}")
    public ResponseEntity<Resource> getImageResource(@PathVariable String filename) {
        Image image = imageService.findImageByFilename(filename);
        ByteArrayResource resource = new ByteArrayResource(image.getData());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, image.getMimeType())
                .body(resource);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImageDto getImage(@PathVariable UUID id) {
        return new ImageDto(imageService.findImage(id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto createImage(@RequestPart MultipartFile file) {
        return imageService.createImage(file);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImageDto updateImage(@PathVariable UUID id, @RequestBody Image image) {
        return imageService.updateImage(id, image);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable UUID id) {
        imageService.deleteImage(id);
    }

    // https://www.youtube.com/watch?v=-clbpg9N9A8
}
