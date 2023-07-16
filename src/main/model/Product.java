package model;

// Represents a single Product
public class Product {
    private int quantity;
    private String productName;

    // REQUIRES: quantity > 0 and productName has a non-zero length.
    // EFFECTS: constructs a product with a given quantity,
    // price it was bought at, and name.
    public Product(String productName, int quantity) {
        this.quantity = quantity;
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }
}
