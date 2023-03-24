package nl.plinio.backend.mock;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.plinio.backend.endpoints.account.AccountService;
import nl.plinio.backend.endpoints.account.model.Account;
import nl.plinio.backend.endpoints.account.model.Role;
import nl.plinio.backend.endpoints.cart.model.Cart;
import nl.plinio.backend.endpoints.image.model.Image;
import nl.plinio.backend.endpoints.product.ProductService;
import nl.plinio.backend.endpoints.product.model.Product;
import nl.plinio.backend.endpoints.product.model.ProductCategory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
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
                "hc-sr04.jpg",
                ProductCategory.DISTANCE,
                3.4f
        );
        createProduct(
                "51397119230",
                "RTC DS3231M Module - I2C",
                "Easily keep track of time with this RTC (Real Time Clock).",
                "rtc-ds3231m.jpg",
                ProductCategory.TIME,
                8.89f
        );
        createProduct(
                "62210057845",
                "Bitcraze Flow Deck V2",
                "Measure movements in relation to the ground with this optical sensor.",
                "bitcraze-flow-deck-v2.jpg",
                ProductCategory.OPTICAL,
                58f
        );
        createProduct(
                "96486419853",
                "ASAIR DHT20 Temperature and Humidity Sensor",
                "A handy temperature and humidity sensor in one.",
                "dht20.jpg",
                ProductCategory.TEMPERATURE,
                5.95f
        );
        createProduct(
                "86514320977",
                "ASAIR AM2122 Temperature and Humidity Sensor",
                "A handy temperature and humidity sensor in one.",
                "am2122.jpg",
                ProductCategory.TEMPERATURE,
                4.4f
        );
        createProduct(
                "6548612009",
                "E18-D80NK Infrared Reflection Sensor",
                "Measure distance between an object with IR reflection.",
                "e18-d80nk.jpg",
                ProductCategory.OPTICAL,
                7.45f
        );
        createProduct(
                "76998125092",
                "Optical Reflection Sensor IR TCRT5000",
                "A combination of an infrared LED and a phototransistor.",
                "tcrt5000.jpg",
                ProductCategory.OPTICAL,
                1.3f
        );
        createProduct(
                "54300874910",
                "Ultrasonic Sensor - US-100",
                "Measure distances more accurately with the built-in temperature sensor.",
                "us-100.jpg",
                ProductCategory.DISTANCE,
                4.8f
        );
        createProduct(
                "83220985432",
                "CZN-15E Electret Condenser Microphone",
                "An omnidirectional electret condenser microphone.",
                "czn-15e.jpg",
                ProductCategory.SOUND,
                0.5f
        );
        createProduct(
                "20975391876",
                "MAX4466 Microphone Amplifier Module",
                "Easily measure ambient sound with an analog pin on your Arduino.",
                "max4466.png",
                ProductCategory.SOUND,
                2.5f
        );
        createProduct(
                "79859610422",
                "Sound Detection Sensor Module",
                "Detect sound with this module.",
                "sound-detection-sensor-module.jpg",
                ProductCategory.SOUND,
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
        account.setCart(new Cart());
        accountService.createAccount(account);
    }

    private Image createImage(String filename) {
        Image image = new Image();
        image.setFilename(filename);

        filename = filename.toLowerCase();

        if (filename.endsWith(".jpg")) {
            image.setMimeType("image/jpeg");
        } else if (filename.endsWith(".png")) {
            image.setMimeType("image/png");
        }

        try {
            InputStream inputStream = MockDataGenerator.class.getResourceAsStream("/images/" + filename);
            assert inputStream != null;
            image.setData(inputStream.readAllBytes());
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return image;
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
        product.setCategory(category);
        product.setPrice(price);
        product.setImage(createImage(image));

        productService.createMockProduct(product);
    }
}
