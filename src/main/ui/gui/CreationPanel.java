package ui.gui;

import model.Store;
import persistence.JsonReader;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreationPanel extends JPanel {
    private JLabel panelTitle;
    private JLabel storeName;
    private JLabel startingBalance;
    private JButton loadButton;
    private JButton createButton;
    private JTextField storeNameInput;
    private JTextField startingBalanceInput;
    private StoreInventoryGUI mainGUI;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/workroom.json";

    // MODIFIES: this
    // EFFECTS: creates the creation panel and then adds all its components
    public CreationPanel(StoreInventoryGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new GridBagLayout());

        labelCreationTitle();

        labelCreationStoreName();

        labelCreationStartingBalance();

        buttonCreationCreateStore();

        buttonCreationLoadStore();

        textFieldCreationStoreNameInput();

        textFieldCreationStoreBalanceInput();
    }


    // MODIFIES: this
    // EFFECTS: adds "store creation page" label to the creation panel
    public void labelCreationTitle() {
        GridBagConstraints c = new GridBagConstraints();
        panelTitle = new JLabel("Store Creation Page");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        this.add(panelTitle, c);
    }

    // MODIFIES: this
    // EFFECTS: adds "name of your store: " label to the creation panel
    private void labelCreationStoreName() {
        GridBagConstraints c = new GridBagConstraints();
        storeName = new JLabel("Name of Your Store: ");
        c.gridx = 0;
        c.gridy = 1;
        this.add(storeName, c);
    }

    // MODIFIES: this
    // EFFECTS: adds "store's starting balance: " label to the creation panel
    public void labelCreationStartingBalance() {
        GridBagConstraints c = new GridBagConstraints();
        startingBalance = new JLabel("Store's Starting Balance: ");
        c.gridx = 0;
        c.gridy = 2;
        this.add(startingBalance, c);
    }

    // MODIFIES: this
    // EFFECTS: adds "Create Store" button to the creation panel and adds the action event for it
    // where if pressed, it checks if the inputs are valid and if so creates a store with them and
    // tells the mainGUI to move on to the store management page with that store
    public void buttonCreationCreateStore() {
        GridBagConstraints c = new GridBagConstraints();
        createButton = new JButton("Create Store");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String storeName = storeNameInput.getText();
                    double storeBal = Double.parseDouble(startingBalanceInput.getText());
                    Store store = new Store(storeName, storeBal);
                    mainGUI.moveToStoreManagementPanel(store);
                } catch (NumberFormatException a) {
                    Icon x = new ImageIcon("data/X-mark.png");
                    JOptionPane.showMessageDialog(mainGUI, "Not a valid starting balance",
                            "Error", JOptionPane.PLAIN_MESSAGE, x);
                }
            }
        });
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        this.add(createButton, c);
    }

    // MODIFIES: this
    // EFFECTS: adds "Load Store" button to the creation panel and adds the action event for it
    // where if pressed, it checks if there is a saved store and if there is, it then loads it. If
    // there isn't a saved store then it gives an error window saying there is no saved stores to load.
    public void buttonCreationLoadStore() {
        GridBagConstraints c = new GridBagConstraints();
        loadButton = new JButton("Load Previous Store");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonReader = new JsonReader(JSON_STORE);
                    Store store = jsonReader.read();
                    JOptionPane.showMessageDialog(mainGUI, "Loaded " + store.getName() + " from " + JSON_STORE);
                    mainGUI.moveToStoreManagementPanel(store);
                } catch (IOException a) {
                    Icon x = new ImageIcon("data/X-mark.png");
                    JOptionPane.showMessageDialog(mainGUI, "Unable to read from file: " + JSON_STORE,
                            "Error", JOptionPane.PLAIN_MESSAGE, x);
                }
            }
        });
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        add(loadButton, c);
    }

    // MODIFIES: this
    // EFFECTS: adds a text field to the creation panel that the user can use to input their store's name
    public void textFieldCreationStoreNameInput() {
        GridBagConstraints c = new GridBagConstraints();
        storeNameInput = new JTextField();
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 300;
        c.fill = GridBagConstraints.BOTH;
        add(storeNameInput, c);
    }

    // MODIFIES: this
    // EFFECTS: adds a text field to the creation panel that the user can use to input their store's starting balance
    public void textFieldCreationStoreBalanceInput() {
        GridBagConstraints c = new GridBagConstraints();
        startingBalanceInput = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        add(startingBalanceInput, c);
    }

}
