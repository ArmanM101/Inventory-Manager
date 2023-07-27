package persistence;

import model.Product;
import model.Store;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            model.Store st = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStore() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStore.json");
        try {
            model.Store st = reader.read();
            assertEquals("Costco", st.getName());
            assertEquals(60, st.getBalance());
            assertEquals(0, st.getInventory().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStore() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStore.json");
        try {
            Store st = reader.read();
            assertEquals("Save on Foods", st.getName());
            List<Product> Inventory = st.getInventory();
            assertEquals(2, Inventory.size());
            assertEquals("milk", Inventory.get(0).getProductName());
            assertEquals(10, Inventory.get(0).getQuantity());
            assertEquals("eggs", Inventory.get(1).getProductName());
            assertEquals(20, Inventory.get(1).getQuantity());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}