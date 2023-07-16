package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
public class ProductTest {
    private Product testProduct;
    @BeforeEach
    public void runBeforeEach() {
        testProduct = new Product("Milk", 10);
    }

    @Test
    public void testConstructor() {
        assertEquals("Milk", testProduct.getProductName());
        assertEquals(10, testProduct.getQuantity());
    }

    @Test
    public void testConstructor2(){
        testProduct = new Product("Egg", 5);
        assertEquals("Egg", testProduct.getProductName());
        assertEquals(5, testProduct.getQuantity());
    }
}
