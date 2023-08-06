package ui.gui;

import model.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreManagementPanel extends JPanel {
    private JPanel top;
    private JPanel middle;
    private JScrollPane inventory;

    private JPanel bottom;
    private JLabel storeName;
    private JLabel currentBal;
    private JLabel inventoryTitle;
    private JButton transaction;
    private JButton save;
    private JButton closeStore;
    private Store store;
    private StoreInventoryGUI mainGUI;

    public StoreManagementPanel(Store store, StoreInventoryGUI mainGUI) {
        this.store = store;
        this.mainGUI = mainGUI;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        createTop();
        add(top);
        createMiddle();
        add(middle);
        createBottom();
        add(bottom);
    }

    public void createTop() {
        top = new JPanel();
        top.setLayout(new FlowLayout());
        storeName = new JLabel("Store Name: " + store.getName());
        top.add(storeName);
        top.add(Box.createHorizontalStrut(150));
        currentBal = new JLabel("Store Balance: " + store.getBalance());
        top.add(currentBal);
    }

    public void createMiddle() {
        middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.PAGE_AXIS));
        inventoryTitle = new JLabel("Inventory");
        middle.add(inventoryTitle);
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        for (model.Product p : store.getInventory()) {
            JLabel label = new JLabel(p.getProductName() + ", " + p.getQuantity() + " items");
            listPane.add(label);
        }
        inventory = new JScrollPane(listPane);
        inventory.setPreferredSize(new Dimension(400, 300));
        middle.add(inventory);
    }

    public void createBottom() {
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout());
        transaction = new JButton("Enter Transaction");
        transaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.moveToTransactionPanel();
            }
        });
        bottom.add(transaction);
        save = new JButton("Save Store");
        bottom.add(save);
        closeStore = new JButton("Close Store");
        bottom.add(closeStore);
    }
}
