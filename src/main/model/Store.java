package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Represents a store having a name, balance, and inventory.
public class Store {
    private String name;
    private double balance;
    private ArrayList<Product> inventory;

    // REQUIRES: storeName has a non-zero length
    // EFFECTS: The name of the store is set to name, the balance
    //          of the store is set to initialBalance, and the
    //          inventory of the store is empty.
    public Store(String name, double initialBalance) {
        this.name = name;
        this.balance = initialBalance;
        this.inventory = new ArrayList<>();
    }

    // EFFECTS: adds/subtracts amount to the balance
    // MODIFIES: this
    public void updateBalance(double amount) {
        balance = balance + amount;
    }

    // EFFECTS: adds product to inventory, or updates the amount of product in inventory
    // and updates the balance to reflect the amount spent on buying it
    // MODIFIES: this, Product
    public void addProduct(Product item, double buyPrice) {
        double moneyLost;
        moneyLost = item.getQuantity() * (-1 * buyPrice);
        int itemNum = item.getQuantity();
        updateBalance(moneyLost);
        String itemName = item.getProductName();
        if (containsObject(item)) {
            int originalNum = findProduct(item).getQuantity();
            item = findProduct(item);
            inventory.remove(item);
            Product replacement = new Product(itemName, itemNum + originalNum);
            inventory.add(replacement);
        } else {
            inventory.add(item);
        }
    }

    // REQUIRES: the product must already exist in the inventory
    // EFFECTS: finds and returns the product in the inventory list
    public Product findProduct(Product n) {
        for (int num = 0; num < inventory.size(); num++) {
            Product current = inventory.get(num);
            if (current.getProductName().equalsIgnoreCase(n.getProductName())) {
                return current;
            }
        }
        return n;
    }

    // EFFECTS: checks to see if the product already exists in the inventory and returns true if it does
    public boolean containsObject(Product n) {
        for (int num = 0; num < inventory.size(); num++) {
            Product current = inventory.get(num);
            if ((current.getProductName()).equalsIgnoreCase(n.getProductName())) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: There is enough of the product for the purchase to be made
    // MODIFIES: This
    // EFFECTS: Removes the sold product from the inventory and adds to
    // amount made from the sale to the balance.
    public boolean sellProduct(Product item, double sellPrice) {
        double moneyGained;
        int itemNum = item.getQuantity();
        moneyGained = item.getQuantity() * sellPrice;
        updateBalance(moneyGained);
        String itemName = item.getProductName();
        int originalNum = findProduct(item).getQuantity();
        item = findProduct(item);
        inventory.remove(item);
        if ((originalNum - itemNum) == 0) {
            return true;
        }
        Product replacement = new Product(itemName, originalNum - itemNum);
        inventory.add(replacement);
        return false;
    }

    // REQUIRES: Product must exist in inventory
    // EFFECTS: Checks to see if there is enough of a product in inventory and returns true if there is
    public boolean checkEnoughProduct(Product purchase) {
        Product replacement = findProduct(purchase);
        if (replacement.getQuantity() >= purchase.getQuantity()) {
            return true;
        }
        return false;
    }


    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Product> getInventory() {
        return inventory;
    }


}
