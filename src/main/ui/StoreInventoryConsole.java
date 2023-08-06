package ui;

import model.Product;
import model.Store;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Store inventory application
public class StoreInventoryConsole {
    private Store store;
    private Product product;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    private Scanner input;

    // EFFECTS: runs the teller application
    public StoreInventoryConsole() throws FileNotFoundException {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runInventory();
    }

    // MODIFIES: this
    // EFFECTS: takes user input
    private void runInventory() {
        boolean keepRunning;
        keepRunning = true;
        String userCommand;
        init();
        openStore();
        while (keepRunning) {
            displayMenu();
            userCommand = input.next();

            if (userCommand.equals("5")) {
                keepRunning = false;
            } else {
                processCommand(userCommand);
            }
        }
        if (store.getBalance() > 0) {
            System.out.println("Your store, " + store.getName() + ", closed with a positive balance of "
                    + store.getBalance());
        } else if (store.getBalance() == 0) {
            System.out.println("Your store, " + store.getName() + ", closed with a balance of 0");
        } else {
            System.out.println("Your store,  " + store.getName() + ", closed with a negative balance of"
                    + (-1 * store.getBalance()));
        }
    }

    // MODIFIES: this
    // EFFECTS: sets up scanner
    private void init() {
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: displays prompts to create the store
    private void openStore() {
        System.out.println("\nWhat is your store's name?");
        String name;
        name = input.next();
        System.out.println("\nWhat balance is your store starting with?");
        String balanceString;
        balanceString = input.next();
        double balanceInt = Double.parseDouble(balanceString);
        store = new model.Store(name, balanceInt);
    }

    // EFFECTS: displays a menu of user options
    private void displayMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("\t1: Add a product order you purchased.");
        System.out.println("\t2: Add a product order you sold.");
        System.out.println("\t3: View the store's current inventory.");
        System.out.println("\t4: View the store's current balance.");
        System.out.println("\t5: Close the business");
        System.out.println("\t6. Save the business");
        System.out.println("\t7. Load the business");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            boughtOrder();
        } else if (command.equals("2")) {
            soldOrder();
        } else if (command.equals("3")) {
            viewInventory();
        } else if (command.equals("4")) {
            viewBalance();
        } else if (command.equals("6")) {
            saveStore();
        } else if (command.equals("7")) {
            loadWorkRoom();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a product order to the stores inventory and update balance
    private void boughtOrder() {
        System.out.println("\nWhat item was purchased?");
        String productName = input.next();
        System.out.println("\nHow many of the item did you purchase?");
        String productAmount;
        productAmount = input.next();
        int amountInt = Integer.parseInt(productAmount);
        System.out.println("\nHow much did you buy each item for?");
        String productPrice = input.next();
        double priceDouble = Double.parseDouble(productPrice);
        product = new model.Product(productName, amountInt);
        store.addProduct(product, priceDouble);
        System.out.println("You have successfully bought " + amountInt + " items of " + productName
                + " for a total of $" + (amountInt * priceDouble) + ".");
        System.out.println("\nThe product order was successfully added to the inventory.");
    }

    // MODIFIES: this
    // EFFECTS: remove a product order to the stores inventory and update balance
    private void soldOrder() {
        System.out.println("\nWhat item was sold?");
        String productName = input.next();
        System.out.println("\nHow many of the item did you sell?");
        String productAmount = input.next();
        int amountInt = Integer.parseInt(productAmount);
        System.out.println("\nHow much did you sell each item for?");
        String productPrice = input.next();
        double priceDouble = Double.parseDouble(productPrice);
        product = new Product(productName, amountInt);
        Store replicant = new Store("replicant", 0);
        soldOrderIfStatement(productName, productAmount, amountInt, priceDouble);
    }

    // MODIFIES: this
    // EFFECTS: attempts to make a sale of a number of items, if the sale can't go through, it tells the user why
    // and if it can go through it makes the sale and updates the balance and inventory.
    private void soldOrderIfStatement(String productName, String productAmount, int amountInt, double priceDouble) {
        if (!store.containsObject(product)) {
            System.out.print("\nSorry but you don't currently carry that item.");
        } else if (!store.checkEnoughProduct(product)) {
            System.out.print("\nSorry there's not enough of that item in stock to make to sale.");
        } else {
            if (store.sellProduct(product, priceDouble)) {
                System.out.println("You have successfully sold " + productAmount + " items of " + productName
                        + " for a total of $" + (amountInt * priceDouble) + ". However, you are now out of "
                        + productName);
            } else {
                System.out.println("You have successfully sold " + productAmount + " items of " + productName
                        + " for a total of $" + (amountInt * priceDouble) + ".");
            }
        }
    }

    // EFFECT: prints the stores balance
    private void viewInventory() {
        ArrayList<Product> inv;
        inv = store.getInventory();
        System.out.println("Your store's current inventory is as follows:");
        printInventory(inv);
    }

    // EFFECTS: prints a legible list of all the products in the stores inventory.
    public ArrayList<Product> printInventory(ArrayList<Product> invt) {
        for (int i = 0; i < invt.size(); i++) {
            Product curr = invt.get(i);
            System.out.println(curr.getProductName() + ", " + curr.getQuantity() + " items");
        }
        return null;
    }

    // EFFECT: prints the stores balance
    private void viewBalance() {
        System.out.println("Your store's current balance is " + store.getBalance() + ".");
    }

    // EFFECTS: saves the store to file
    private void saveStore() {
        try {
            jsonWriter.open();
            jsonWriter.write(store);
            jsonWriter.close();
            System.out.println("Saved " + store.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads store from file
    private void loadWorkRoom() {
        try {
            store = jsonReader.read();
            System.out.println("Loaded " + store.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
