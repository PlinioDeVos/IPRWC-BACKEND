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
                "https://www.tinytronics.nl/shop/image/cache/catalog/products_2022/ultrasonic-sensor-hc-sr04-front-600x600.jpg",
                ProductCategory.DISTANCE,
                3
        );
        createProduct(
                "51397119230",
                "RTC DS3231M Module - I2C",
                "Easily keep track of time with this RTC (Real Time Clock).",
                "https://www.tinytronics.nl/shop/image/cache/data/product-3153/rtc-ds3231m-module-i2c-front-600x600w.jpg",
                ProductCategory.TIME,
                8
        );
        createProduct(
                "62210057845",
                "Bitcraze Flow Deck V2",
                "Measure movements in relation to the ground with this optical sensor.",
                "https://www.tinytronics.nl/shop/image/cache/catalog/products_2022/bitcraze-crazyflie-2.x-flow-deck-v2-600x600.jpg",
                ProductCategory.OPTICAL,
                58
        );
        createProduct(
                "96486419853",
                "ASAIR DHT20 Temperature and Humidity Sensor",
                "A handy temperature and humidity sensor in one.",
                "https://www.tinytronics.nl/shop/image/cache/catalog/products/product-003752/dht20-temperature-and-humidity-sensor-600x600.jpg",
                ProductCategory.TEMPERATURE,
                5.95f
        );
        createProduct(
                "86514320977",
                "ASAIR AM2122 Temperature and Humidity Sensor",
                "A handy temperature and humidity sensor in one.",
                "https://www.tinytronics.nl/shop/image/cache/catalog/products/product-003753/am2122-temperature-and-humidity-sensor-600x600.jpg",
                ProductCategory.TEMPERATURE,
                4.4f
        );
        createProduct(
                "6548612009",
                "E18-D80NK Infrared Reflection Sensor",
                "Measure distance between an object with IR reflection.",
                "https://www.tinytronics.nl/shop/image/cache/data/product-1139/E18-D80NK%20Infrared%20Reflection%20Sensor%201-600x600.jpg",
                ProductCategory.OPTICAL,
                7.45f
        );
        createProduct(
                "76998125092",
                "Optical Reflection Sensor IR TCRT5000",
                "A combination of an infrared LED and a phototransistor.",
                "https://www.tinytronics.nl/shop/image/cache/data/product-158/TCRT5000-600x600.JPG",
                ProductCategory.OPTICAL,
                1.3f
        );
        createProduct(
                "54300874910",
                "Ultrasonic Sensor - US-100",
                "Measure distances more accurately with the built-in temperature sensor.",
                "https://www.tinytronics.nl/shop/image/cache/data/product-1873/US-100_1-600x600w.jpg",
                ProductCategory.DISTANCE,
                4.8f
        );
        createProduct(
                "83220985432",
                "CZN-15E Electret Condenser Microphone",
                "An omnidirectional electret condenser microphone.",
                "https://www.tinytronics.nl/shop/image/cache/catalog/products/product-003552/czn-15e-electret-condenser-microphone--600x600.jpg",
                ProductCategory.SOUD,
                0.5f
        );
        createProduct(
                "20975391876",
                "MAX4466 Microphone Amplifier Module",
                "Easily measure ambient sound with an analog pin on your Arduino.",
                "https://www.tinytronics.nl/shop/image/cache/data/product-1781/MAX4466MICMOD_voorkant-600x600.png",
                ProductCategory.SOUD,
                2.5f
        );
        createProduct(
                "79859610422",
                "Sound Detection Sensor Module",
                "Detect sound with this module.",
                "https://www.tinytronics.nl/shop/image/cache/data/product-1781/MAX4466MICMOD_voorkant-600x600.png",
                ProductCategory.SOUD,
                2.5f
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
            String image,
            ProductCategory category,
            float price
    ) {
        Product product = new Product();
        product.setEan(ean);
        product.setName(name);
        product.setDescription(description);
        product.setImage(image);
        product.setCategory(category);
        product.setPrice(price);
        productService.createProduct(product);
    }
}
