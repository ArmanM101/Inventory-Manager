package ui.gui;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransactionPanel extends JPanel {
    private model.Store store;
    private StoreInventoryGUI mainGUI;
    private JPanel layer1;
    private JPanel layer2;
    private JPanel layer3;
    private JPanel layer4;
    private JLabel productName;
    private JLabel productAmount;
    private JLabel pricePerUnit;
    private JTextField productNameInput;
    private JTextField productAmountInput;
    private JTextField priceInput;
    private JButton sell;
    private JButton buy;
    private Icon icon = new ImageIcon("data/X-mark.png");

    // MODIFIES: this
    // EFFECTS: creates the Transaction panel and then adds all its components
    public TransactionPanel(model.Store store, StoreInventoryGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.store = store;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createLayer1();
        add(layer1);
        createLayer2();
        add(layer2);
        createLayer3();
        add(layer3);
        createLayer4();
        add(layer4);
    }

    // MODIFIES: this
    // EFFECTS: creates the top panel of the transaction panel and then adds all its components
    public void createLayer1() {
        layer1 = new JPanel();
        layer1.setLayout(new FlowLayout());
        productName = new JLabel("Product Name: ");
        layer1.add(productName);
        productNameInput = new JTextField();
        productNameInput.setColumns(10);
        layer1.add(productNameInput);
    }

    // MODIFIES: this
    // EFFECTS: creates the 2nd highest panel of the transaction panel and then adds all its components
    public void createLayer2() {
        layer2 = new JPanel();
        layer2.setLayout(new FlowLayout());
        productAmount = new JLabel("Number of Items: ");
        layer2.add(productAmount);
        productAmountInput = new JTextField();
        productAmountInput.setColumns(10);
        layer2.add(productAmountInput);
    }

    // MODIFIES: this
    // EFFECTS: creates the 3rd highest panel of the transaction panel and then adds all its components
    public void createLayer3() {
        layer3 = new JPanel();
        layer3.setLayout(new FlowLayout());
        pricePerUnit = new JLabel("Price per Unit: ");
        layer3.add(pricePerUnit);
        priceInput = new JTextField();
        priceInput.setColumns(10);
        layer3.add(priceInput);
    }

    // MODIFIES: this
    // EFFECTS: creates the bottom panel of the transaction panel and then adds all its components
    public void createLayer4() {
        layer4 = new JPanel();
        layer4.setLayout(new FlowLayout());

        buttonCreationSell();

        buttonCreationBuy();
    }

    // MODIFIES: this
    // EFFECTS: creates the Sell button with an action event for the transaction panel and then adds it
    public void buttonCreationSell() {
        sell = new JButton("Sell");
        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellButtonPressed();
            }
        });
        layer4.add(sell);
    }

    // EFFECTS: acts as the action event for the sell button. When pressed it checks to see if the
    // sell transaction can go through, if it can, it updates the store and has the
    // mainGUI move back to the store management panel. If the sell transaction can't go through, it produces
    // an error message
    public void sellButtonPressed() {
        String productName = productNameInput.getText();
        int productAmount = Integer.parseInt(productAmountInput.getText());
        double productPrice = Double.parseDouble(priceInput.getText());
        Product product = new Product(productName, productAmount);
        if (!store.containsObject(product)) {
            JOptionPane.showMessageDialog(mainGUI, "Sorry but you don't currently carry that item.",
                    "Error", JOptionPane.PLAIN_MESSAGE, icon);
        } else if (!store.checkEnoughProduct(product)) {
            JOptionPane.showMessageDialog(mainGUI, "Sorry there's not enough of "
                    + "that item in stock to make to sale.", "Error", JOptionPane.PLAIN_MESSAGE, icon);
        } else {
            if (store.sellProduct(product, productPrice)) {
                JOptionPane.showMessageDialog(mainGUI, "You have successfully sold " + productAmount
                        + " items of " + productName + " for a total of $" + (productAmount * productPrice)
                        + ". However, you are now out of " + productName);
                mainGUI.moveToStoreManagementPanel(store);
            } else {
                JOptionPane.showMessageDialog(mainGUI, "You have successfully sold " + productAmount
                        + " items of " + productName
                        + " for a total of $" + (productAmount * productPrice) + ".");
                mainGUI.moveToStoreManagementPanel(store);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the buy button for the transaction panel and adds it. It also creates an action event
    // for the button so that when pressed, it buys the product, updates the store, and has the mainGUI
    // move back to the Store management panel
    public void buttonCreationBuy() {
        buy = new JButton("Buy");
        buy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameInput.getText();
                int productAmount = Integer.parseInt(productAmountInput.getText());
                double productPrice = Double.parseDouble(priceInput.getText());
                Product product = new Product(productName, productAmount);
                store.addProduct(product, productPrice);
                mainGUI.moveToStoreManagementPanel(store);
            }
        });
        layer4.add(buy);
    }
}
