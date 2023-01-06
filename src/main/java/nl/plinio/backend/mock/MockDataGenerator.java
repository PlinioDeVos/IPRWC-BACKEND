package nl.plinio.backend.mock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.Role;
import nl.plinio.backend.endpoints.product.ProductService;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.endpoints.product.model.ProductCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MockDataGenerator {
    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    private final AccountService accountService;
    private final ProductService productService;

    private void generateAccounts() {
        createAccount(adminEmail, adminPassword, Role.ADMIN);
        createAccount("kees@gmail.com", "Keesie1980", Role.CUSTOMER);
    }

    private void generateProducts() {
        createProduct(
                "10128009888",
                "Ultrasonic Sensor - HC-SR04",
                "With this ultrasonic sensor you can easily measure a distance.",
                ProductCategory.DISTANCE,
                3,
                3
        );
        createProduct(
                "51397119230",
                "RTC DS3231M Module - I2C",
                "Easily keep track of time with this RTC (Real Time Clock).",
                ProductCategory.TIME,
                3,
                3
        );
    }

    @PostConstruct
    public void generate() {
        if (!accountService.getAllAccounts(0, 10, "id").isEmpty()) return;
        generateAccounts();
        generateProducts();
    }

    // Creation methods
    private void createAccount(
            String email,
            String password,
            Role role
    ) {
        Account account = new Account();
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(role);
        accountService.createAccount(account);
    }

    private void createProduct(
            String ean,
            String name,
            String description,
            ProductCategory category,
            float price,
            float salePrice
    ) {
        Product product = new Product();
        product.setEan(ean);
        product.setDescription(description);
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        product.setSalePrice(salePrice);
        product.setVisible(true);
        productService.createProduct(product);
    }
}
