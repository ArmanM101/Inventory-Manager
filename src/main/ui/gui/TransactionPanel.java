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

    public void createLayer1() {
        layer1 = new JPanel();
        layer1.setLayout(new FlowLayout());
        productName = new JLabel("Product Name: ");
        layer1.add(productName);
        productNameInput = new JTextField();
        productNameInput.setColumns(10);
        layer1.add(productNameInput);
    }

    public void createLayer2() {
        layer2 = new JPanel();
        layer2.setLayout(new FlowLayout());
        productAmount = new JLabel("Number of Items: ");
        layer2.add(productAmount);
        productAmountInput = new JTextField();
        productAmountInput.setColumns(10);
        layer2.add(productAmountInput);
    }

    public void createLayer3() {
        layer3 = new JPanel();
        layer3.setLayout(new FlowLayout());
        pricePerUnit = new JLabel("Price per Unit: ");
        layer3.add(pricePerUnit);
        priceInput = new JTextField();
        priceInput.setColumns(10);
        layer3.add(priceInput);
    }

    public void createLayer4() {
        layer4 = new JPanel();
        layer4.setLayout(new FlowLayout());
        sell = new JButton("Sell");
        sell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameInput.getText();
                int productAmount = Integer.parseInt(productAmountInput.getText());
                double productPrice = Double.parseDouble(priceInput.getText());
                Product product = new Product(productName, productAmount);
                if (!store.containsObject(product)) {
                    JOptionPane.showMessageDialog(mainGUI, "Sorry but you don't currently carry that item.");
                } else if (!store.checkEnoughProduct(product)) {
                    JOptionPane.showMessageDialog(mainGUI, "Sorry there's not enough of "
                            + "that item in stock to make to sale.");
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
        });
        layer4.add(sell);
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
