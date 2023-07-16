package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    private Store testStore;
    private List<Product> testInventory;
    private Product p1;
    private Product p2;
    private Product p3;

    @BeforeEach
    public void runBeforeEach() {
        testStore = new Store("Costco", 0);
        p1 = new Product("Milk", 10);
        p2 = new Product("Chocolate", 5);
        p3 = new Product("Paper", 2);
    }

    @Test
    public void testConstructor() {
        assertEquals("Costco", testStore.getName());
        assertEquals(0, testStore.getBalance());
        List<Product> testInventory = testStore.getInventory();
        int testInventorySize = testInventory.size();
        assertEquals(0, testInventorySize);
    }

    @Test
    public void testConstructor2() {
        testStore = new Store("Save On Foods", 500);
        assertEquals("Save On Foods", testStore.getName());
        assertEquals(500, testStore.getBalance());
        List<Product> testInventory = testStore.getInventory();
        int testInventorySize = testInventory.size();
        assertEquals(0, testInventorySize);
    }

    @Test
    public void testUpdateBalance() {
        testStore.updateBalance(20);
        assertEquals(20, testStore.getBalance());

        testStore.updateBalance(10);
        testStore.updateBalance(40);
        assertEquals(70, testStore.getBalance());
    }

    @Test
    public void testAddProduct() {
        testStore.addProduct(p1, 1);
        assertEquals(10, (testStore.findProduct(p1).getQuantity()));
        assertEquals(1, (testStore.getInventory()).size());
        assertEquals(-10, testStore.getBalance());

        testStore.addProduct(p1, 2);
        assertEquals(20, (testStore.findProduct(p1).getQuantity()));
        assertEquals(1, (testStore.getInventory()).size());
        assertEquals(-30, testStore.getBalance());

        testStore.addProduct(p2, 1);
        assertEquals(20, (testStore.findProduct(p1).getQuantity()));
        assertEquals(5, (testStore.findProduct(p2).getQuantity()));
        assertEquals(2, (testStore.getInventory()).size());
        assertEquals(-35, testStore.getBalance());

        testStore.addProduct(p3, 1);
        assertEquals(20, (testStore.findProduct(p1).getQuantity()));
        assertEquals(5, (testStore.findProduct(p2).getQuantity()));
        assertEquals(2, (testStore.findProduct(p3).getQuantity()));
        assertEquals(3, (testStore.getInventory()).size());
        assertEquals(-37, testStore.getBalance());
    }

    @Test
    public void testFindProduct() {
        testStore.addProduct(p1, 1);
        assertEquals(p1, (testStore.findProduct(p1)));

        testStore.addProduct(p1, 2);
        assertEquals(testStore.findProduct(new Product("Milk", 1)), (testStore.findProduct(p1)));


        testStore.addProduct(p2, 1);
        assertEquals(testStore.findProduct(new Product("Milk", 1)), (testStore.findProduct(p1)));
        assertEquals(p2, (testStore.findProduct(p2)));

        testStore.addProduct(p3, 1);
        assertEquals(testStore.findProduct(new Product("Milk", 1)), (testStore.findProduct(p1)));
        assertEquals(p2, (testStore.findProduct(p2)));
        assertEquals(p3, (testStore.findProduct(p3)));

        testStore.sellProduct(p1, 1);
        assertEquals(testStore.findProduct(new Product("Milk", 1)), (testStore.findProduct(p1)));
    }

    @Test
    public void testContainsObject() {
        testStore.addProduct(p1, 1);
        assertTrue(testStore.containsObject(p1));
        assertFalse(testStore.containsObject(p2));

        testStore.addProduct(p1, 2);
        assertTrue(testStore.containsObject(p1));
        assertFalse(testStore.containsObject(p2));


        testStore.addProduct(p2, 1);
        assertTrue(testStore.containsObject(p1));
        assertTrue(testStore.containsObject(p2));

        testStore.sellProduct(p2, 1);
        assertTrue(testStore.containsObject(p1));
        assertFalse(testStore.containsObject(p2));
    }

    @Test
    public void testSellProduct() {
        testStore.addProduct(p1, 2);
        assertTrue(testStore.sellProduct(p1, 4));
        assertEquals(0, (testStore.getInventory()).size());
        assertEquals(20, testStore.getBalance());

        testStore.addProduct(p1, 2);
        testStore.addProduct(p1, 2);
        assertFalse(testStore.sellProduct(p1, 4));
        assertEquals(1, (testStore.getInventory()).size());
        assertEquals(20, testStore.getBalance());

        testStore.addProduct(p2, 4);
        assertFalse(testStore.sellProduct(new Product("Chocolate", 2), 8));
        assertEquals(2, (testStore.getInventory()).size());
        assertEquals(16, testStore.getBalance());
    }

    @Test
    public void testCheckEnoughProduct() {
        testStore.addProduct(p1, 2);
        assertTrue(testStore.checkEnoughProduct(p1));

        testStore.addProduct(p1, 6);
        assertTrue(testStore.checkEnoughProduct(new Product("Milk", 15)));

        testStore.addProduct(p2, 12);
        assertTrue(testStore.checkEnoughProduct(p2));

        testStore.sellProduct(p1, 20);
        assertFalse(testStore.checkEnoughProduct(new Product("Milk", 15)));
        assertTrue(testStore.checkEnoughProduct(p1));
    }
}
