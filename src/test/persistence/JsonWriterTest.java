package persistence;

import model.Product;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Store st = new Store("Costco", 100);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Store st = new Store("Costco", 200);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStore.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStore.json");
            st = reader.read();
            assertEquals("Costco", st.getName());
            assertEquals(0, st.getInventory().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Store st = new Store("Costco", 200);
            st.justAddProduct(new Product("milk", 10));
            st.justAddProduct(new Product("eggs", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStore.json");
            writer.open();
            writer.write(st);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStore.json");
            st = reader.read();
            assertEquals("Costco", st.getName());
            List<Product> Inventory = st.getInventory();
            assertEquals(2, Inventory.size());
            assertEquals("milk", Inventory.get(0).getProductName());
            assertEquals(10, Inventory.get(0).getQuantity());
            assertEquals("eggs", Inventory.get(1).getProductName());
            assertEquals(20, Inventory.get(1).getQuantity());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}