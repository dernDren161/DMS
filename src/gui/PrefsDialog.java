package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrefsDialog extends JDialog {

    private JButton okButton;
    private JButton cancelButton;
    private JSpinner portSpinner;
    private SpinnerNumberModel spinnerModel;
    private JTextField userField;
    private JPasswordField passField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent){
        super(parent,"Preferences",false);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        spinnerModel = new SpinnerNumberModel(3306,0,9999,1);
        portSpinner = new JSpinner(spinnerModel);
        userField = new JTextField(10);
        passField = new JPasswordField(10);

        passField.setEchoChar('*'); // instead of ".", we use "*"

        setLayout(new GridBagLayout());



        GridBagConstraints gc = new GridBagConstraints();

        layoutControls(gc);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer port = (Integer) portSpinner.getValue();
                String user = userField.getText();
                char[] password = passField.getPassword();

                //System.out.println(user + ":" + new String(password)); // Nice one characters to String password decryption
                if(prefsListener != null){
                    prefsListener.preferencesSet(user,new String(password),port);
                }
                setVisible(false);

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer value = (Integer) portSpinner.getValue();
                setVisible(false); // closes the current window.
            }
        });


        setSize(340,250); // sets the 2D dimension of the Dialog Box
        setLocationRelativeTo(parent);
    }

    private void layoutControls(GridBagConstraints gc){

        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        int space = 15;
        // the next three lines are responsible for creating the light-blue border surrounding the components within the dialog box.
        Border spaceBorder = BorderFactory.createEmptyBorder(space,space,space,space);
        Border titleBorder = BorderFactory.createTitledBorder("Database Preferences");

        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder,titleBorder));

        controlsPanel.setLayout(new GridBagLayout());

        gc.gridy =0;

        Insets rightPadding = new Insets(0,0,0,15);
        Insets noPadding = new Insets(0,0,0,0);
        //////First Row//////

        gc.weighty =1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("User"),gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(userField,gc);

        ///// Next row /////

        gc.gridy++;
        gc.weighty =1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password"),gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(passField,gc);

        ///// Next row /////

        gc.gridy++;
        gc.weighty =1;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port:"),gc);

        gc.gridx++;
        gc.anchor = GridBagConstraints.WEST;
        gc.insets = noPadding;
        controlsPanel.add(portSpinner,gc);

        /////// Buttons Panel /////

       buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
       buttonsPanel.add(okButton);
       buttonsPanel.add(cancelButton);

        Dimension btnSize = cancelButton.getPreferredSize();
        okButton.setPreferredSize(btnSize);

        //Add sub panels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel,BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

    }


    public void setDefault(String user, String password, int port){
        userField.setText(user);
        passField.setText(password);
        portSpinner.setValue(port);
    }
    public void setPrefsListener(PrefsListener prefsListener){
            this.prefsListener = prefsListener;
    }
}
