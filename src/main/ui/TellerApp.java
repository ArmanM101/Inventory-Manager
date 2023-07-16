package ui;

import model.Product;
import model.Store;

import java.util.ArrayList;
import java.util.Scanner;

// Store inventory application
public class TellerApp {
    private Store store;
    private Product product;

    private Scanner input;

    // EFFECTS: runs the teller application
    public TellerApp() {
        runTeller();
    }

    // MODIFIES: this
    // EFFECTS: takes user input
    private void runTeller() {
        boolean keepRunning;
        keepRunning = true;
        String userCommand;
        userCommand = null;

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
            System.out.println("Congrats your store, " + store.getName() + ", made $" + store.getBalance());
        } else if (store.getBalance() == 0) {
            System.out.println("Your store: " + store.getName() + " didn't make or lose money");
        } else {
            System.out.println("Sorry your store, " + store.getName() + ", lost $" + store.getBalance());
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
        if (!store.containsObject(product)) {
            System.out.print("\nSorry but you don't currently carry that item.");
        } else if (!store.checkEnoughProduct(product)) {
            System.out.print("\nSorry there's not enough of that item in stock to make to sale.");
        } else {
            if (store.sellProduct(product, priceDouble)) {
                System.out.println("The sale successfully went through. However, you are now out of "
                        + productName + ".");
            } else {
                System.out.println("The sale successfully went through.");
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
}
