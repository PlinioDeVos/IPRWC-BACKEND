package nl.plinio.backend.endpoints.account.model;

public enum Role {
    ADMIN("admin"),
    CUSTOMER("customer");

    public final String value;

    Role(String value) {
        this.value = value;
    }
}
