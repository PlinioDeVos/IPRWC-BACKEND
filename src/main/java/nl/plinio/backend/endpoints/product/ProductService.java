package nl.plinio.backend.endpoints.product;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.endpoints.product.model.ProductCategory;
import nl.plinio.backend.endpoints.product.model.ProductDto;
import nl.plinio.backend.exception.EnitityNotFoundException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findProduct(UUID id) {
        return productRepository.findById(id).orElseThrow(() -> new EnitityNotFoundException(Product.class));
    }

    public Page<ProductDto> getAllProducts(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<ProductDto> productResponseList = new ArrayList<>();
        Page<Product> existingProducts = productRepository.findAll(pageable);
        existingProducts.forEach(product -> productResponseList.add(new ProductDto(product)));
        return new PageImpl<>(productResponseList, pageable, existingProducts.getTotalElements());
    }

    public EnumSet<ProductCategory> getAllCategories() {
        return EnumSet.allOf(ProductCategory.class);
    }

    public ProductDto createProduct(Product product) {
        return new ProductDto(productRepository.save(product));
    }

    public ProductDto updateProduct(UUID id, Product product) {
        Product existingProduct = findProduct(id);
        existingProduct.setEan(product.getEan());
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setSalePrice(product.getSalePrice());
        existingProduct.setVisible(product.isVisible());
        return new ProductDto(productRepository.save(existingProduct));
    }

    public void deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
        } catch (IllegalArgumentException ex) {
            throw new EnitityNotFoundException(Product.class);
        }
    }
}
