package nl.plinio.backend.endpoints.product;

import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.endpoints.product.model.ProductCategory;
import nl.plinio.backend.endpoints.product.model.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.UUID;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductDto> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sort
    ) {
        return productService.getAllProducts(page, size, sort);
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public EnumSet<ProductCategory> getAllCategories() {
        return productService.getAllCategories();
    }

    @PreAuthorize("hasAnyAuthority('customer', 'admin')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable UUID id) {
        return new ProductDto(productService.findProduct(id));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PreAuthorize("hasAuthority('admin')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto updateProduct(@PathVariable UUID id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
