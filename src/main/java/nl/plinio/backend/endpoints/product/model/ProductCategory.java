package nl.plinio.backend.endpoints.product.model;

public enum ProductCategory {
    DISTANCE("distance"),
    OPTICAL("optical"),
    SOUND("sound"),
    TEMPERATURE("temperature"),
    TIME("time");

    public final String value;

    ProductCategory(String value) {
        this.value = value;
    }
}
