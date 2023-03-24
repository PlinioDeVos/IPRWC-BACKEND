package nl.plinio.backend.endpoints.image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.image.model.Image;
import nl.plinio.backend.endpoints.image.model.ImageDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import nl.plinio.backend.exception.EntityExistsException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;

    public Image findImage(UUID id) {
        return imageRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Image.class));
    }

    public Image findImageByFilename(String filename) {
        return imageRepository.findByFilename(filename).orElseThrow(() -> new EnitityNotFoundException(Image.class));
    }

    public Page<ImageDto> getAllImages(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<ImageDto> imageResponseList = new ArrayList<>();
        Page<Image> existingImages = imageRepository.findAll(pageable);
        existingImages.forEach(image -> imageResponseList.add(new ImageDto(image)));
        return new PageImpl<>(imageResponseList, pageable, existingImages.getTotalElements());
    }

    public ImageDto createImage(MultipartFile file) {
        String filename = file.getOriginalFilename();

        if (imageRepository.existsByFilename(filename)) {
            throw new EntityExistsException("An image with the name '" + filename + "' already exists.");
        }

        Image image = new Image();
        image.setFilename(filename);
        image.setMimeType(file.getContentType());

        try {
            image.setData(file.getBytes());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return new ImageDto(imageRepository.save(image));
    }

    public ImageDto updateImage(UUID id, Image image) {
        Image existingImage = findImage(id);
        existingImage.setFilename(image.getFilename());
        existingImage.setData(image.getData());
        existingImage.setMimeType(image.getMimeType());
        return new ImageDto(imageRepository.save(existingImage));
    }

    public void deleteImage(UUID id) {
        try {
            imageRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Image.class);
        }
    }

    public Image createNewProductImage(String productName) {
        Image image = new Image();
        image.setFilename(productName);
        image.setMimeType("images/png");

        try {
            InputStream inputStream = getClass().getResourceAsStream("/BOOT-INF/classes/images/new-product.png");
            assert inputStream != null;
            image.setData(inputStream.readAllBytes());
        } catch (IOException | NullPointerException ex) {
            log.error(ex.getMessage());
        }

        imageRepository.save(image);
        return image;
    }
}
