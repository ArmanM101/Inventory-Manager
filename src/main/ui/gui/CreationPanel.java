package ui.gui;

import model.Store;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationPanel extends JPanel {
    private JLabel panelTitle;
    private JLabel storeName;
    private JLabel startingBalance;
    private JButton loadButton;
    private JButton createButton;
    private JTextField storeNameInput;
    private JTextField startingBalanceInput;
    private StoreInventoryGUI mainGUI;

    public CreationPanel(StoreInventoryGUI mainGUI) {
        this.mainGUI = mainGUI;
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        panelTitle = new JLabel("Store Creation Page");
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.BOTH;
        add(panelTitle, c);

        storeName = new JLabel("Name of Your Store: ");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        add(storeName, c);

        startingBalance = new JLabel("Store's Starting Balance: ");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 2;
        add(startingBalance, c);

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
                    JOptionPane.showMessageDialog(mainGUI, "Not a valid starting balance");
                }
            }
        });
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        add(createButton, c);

        loadButton = new JButton("Load Previous Store");
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        add(loadButton, c);

        storeNameInput = new JTextField();
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 1;
        c.ipadx = 300;
        c.fill = GridBagConstraints.BOTH;
        add(storeNameInput, c);

        startingBalanceInput = new JTextField();
        c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        add(startingBalanceInput, c);
    }

}
