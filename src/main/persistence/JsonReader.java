package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Product;
import model.Store;
import org.json.*;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads store from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Store read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStore(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses store from JSON object and returns it
    private Store parseStore(JSONObject jsonObject) {
        String name = jsonObject.getString("storeName");
        double balance = jsonObject.getDouble("balance");
        Store st = new Store(name, balance);
        addProducts(st, jsonObject);
        return st;
    }

    // MODIFIES: st
    // EFFECTS: parses products from JSON object and adds them to store
    private void addProducts(Store st, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("inventory");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(st, nextProduct);
        }
    }

    // MODIFIES: st
    // EFFECTS: parses product from JSON object and adds it to store
    private void addProduct(Store st, JSONObject jsonObject) {
        String name = jsonObject.getString("productName");
        int quantity = jsonObject.getInt("quantity");
        Product product = new Product(name, quantity);
        st.justAddProduct(product);
    }
}
