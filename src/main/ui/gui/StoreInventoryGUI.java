package ui.gui;

import model.Store;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreInventoryGUI extends JFrame {
    private Store store;
    private CreationPanel creationPanel;
    private StoreManagementPanel managementPanel;
    private TransactionPanel transactionPanel;


    // MODIFIES: this
    // EFFECTS: creates the frame for the GUI and sets the content panel as the store creation panel
    public StoreInventoryGUI() {
        super("Store Inventory Manager");
        setSize(new Dimension(500, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        creationPanel = new CreationPanel(this);
        setContentPane(creationPanel);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the content panel as the store management panel and passes in the current store
    public void moveToStoreManagementPanel(Store store) {
        this.store = store;
        managementPanel = new StoreManagementPanel(store, this);
        setContentPane(managementPanel);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets the content panel as the transaction panel and passes in the current store
    public void moveToTransactionPanel() {
        transactionPanel = new TransactionPanel(store, this);
        setContentPane(transactionPanel);
        setVisible(true);
    }

}
