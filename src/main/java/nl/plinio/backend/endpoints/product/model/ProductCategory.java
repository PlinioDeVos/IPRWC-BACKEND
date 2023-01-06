package nl.plinio.backend.endpoints.product.model;

public enum ProductCategory {
    AIR("air"),
    DISTANCE("distance"),
    CAMERA("camera"),
    INFRARED("infrared"),
    TEMPERATURE("temperature"),
    TIME("time");

    public final String value;

    ProductCategory(String value) {
        this.value = value;
    }
}
