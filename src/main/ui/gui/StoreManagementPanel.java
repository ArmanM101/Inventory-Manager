package ui.gui;

import model.EventLog;
import model.Store;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

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
    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/workroom.json";

    // MODIFIES: this
    // EFFECTS: creates the store management panel and then adds all its components
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

    // MODIFIES: this
    // EFFECTS: creates the top panel of the Store Management panel and then adds all its components
    public void createTop() {
        top = new JPanel();
        top.setLayout(new FlowLayout());
        storeName = new JLabel("Store Name: " + store.getName());
        top.add(storeName);
        top.add(Box.createHorizontalStrut(150));
        currentBal = new JLabel("Store Balance: " + store.getBalance());
        top.add(currentBal);
    }

    // MODIFIES: this
    // EFFECTS: creates the middle panel of the Store Management panel and then adds all its components
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

    // MODIFIES: this
    // EFFECTS: creates the bottom panel of the Store Management panel and then adds all its components
    public void createBottom() {
        bottom = new JPanel();
        bottom.setLayout(new FlowLayout());

        buttonCreationTransaction();

        buttonCreationSaveStore();

        buttonCreationCloseStore();
    }

    // MODIFIES: this
    // EFFECTS: creates the Enter Transaction button and adds it to the bottom panel, also adds an action event where
    // if pressed, it tells the mainGUI to move onto the transaction panel
    public void buttonCreationTransaction() {
        transaction = new JButton("Enter Transaction");
        transaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGUI.moveToTransactionPanel();
            }
        });
        bottom.add(transaction);
    }

    // MODIFIES: this
    // EFFECTS: creates the Save Store button and adds it to the bottom panel, also adds an action event where if the
    // button is pressed then it tries to save the store and if it can't, it gives an error message
    public void buttonCreationSaveStore() {
        save = new JButton("Save Store");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter = new JsonWriter(JSON_STORE);
                    jsonWriter.open();
                    jsonWriter.write(store);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(mainGUI, "Saved " + store.getName() + " to " + JSON_STORE);
                } catch (FileNotFoundException c) {
                    JOptionPane.showMessageDialog(mainGUI, "Unable to write to file: " + JSON_STORE);
                }
            }
        });
        bottom.add(save);
    }

    // MODIFIES: this
    // EFFECTS: creates the Close Store button and adds it to the bottom panel, also adds an action event
    // where if pressed, the user is given a message of how much the store made and then the application closes
    public void buttonCreationCloseStore() {
        closeStore = new JButton("Close Store");
        bottom.add(closeStore);
        closeStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainGUI,"Your store, " + store.getName()
                        + ", closes with a balance of " + store.getBalance());
                EventLog el = EventLog.getInstance();
                for (model.Event next : el) {
                    System.out.println(next.toString());
                }
                System.exit(0);
            }
        });
    }
}
