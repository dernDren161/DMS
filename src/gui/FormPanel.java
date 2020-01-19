package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class FormPanel extends JPanel {
    private JLabel nameLabel;
    private JLabel fieldOfStudy;
    private JTextField nameField;
    private JTextField studyField;
    private JButton okBtn;
    private FormListener formListener;
    private JList ageList;
    private JComboBox diseaseComboBox;
    private JCheckBox citizenCheck;
    private JTextField hostel;
    private JLabel hostelNumber;
    // Radio Button is used to select only one option out of the various ButtonGroup. One popular example would be in the MCQs and Quiz formats.
    private JRadioButton maleRadio;
    private JRadioButton femaleRadio;
    // In a ButtonGroup, only one option could be selected out of all the other options.
    private ButtonGroup genderGroup;

    public FormPanel(){
        Dimension dim = getPreferredSize();
        dim.width = 500;
        setPreferredSize(dim);

        nameLabel = new JLabel("Name:");
        fieldOfStudy = new JLabel("Field of Study:");
        nameField = new JTextField(10);
        studyField = new JTextField(10);
        okBtn = new JButton("OK");
        ageList = new JList();
        diseaseComboBox = new JComboBox();
        citizenCheck = new JCheckBox();
        hostel = new JTextField(10);
        hostelNumber = new JLabel("Hostel Number");

        okBtn.setMnemonic(KeyEvent.VK_O);
        hostelNumber.setEnabled(false);
        hostel.setEnabled(false);

        maleRadio = new JRadioButton("male");
        femaleRadio = new JRadioButton("female");

        maleRadio.setActionCommand("male");
        femaleRadio.setActionCommand("female");
        genderGroup = new ButtonGroup();

        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);


        citizenCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isTicked = citizenCheck.isSelected();
                hostelNumber.setEnabled(isTicked);
                hostel.setEnabled(isTicked);
            }
        });

        DefaultListModel ageModel = new DefaultListModel(); // <E> -- use your own class in place of the placeholder "<E>"
        ageModel.addElement(new AgeCategory(0,"17-19")); //just converted the String into the AgeCategory class instantiation.
        ageModel.addElement(new AgeCategory(1,"20-23")); // the reason of converting it into AgeCategory is that now we have both id and String for databases purposes.
        ageModel.addElement(new AgeCategory(2,"24 and Over"));

        ageList.setModel(ageModel);
        //ageList.setPreferredSize(new Dimension(110,20));
        ageList.setBorder(BorderFactory.createEtchedBorder());
        ageList.setSelectedIndex(0);

        DefaultComboBoxModel diseaseType = new DefaultComboBoxModel();
        diseaseType.addElement("Dengue");
        diseaseType.addElement("Malaria");
        diseaseType.addElement("Chikungunya");
        diseaseComboBox.setModel(diseaseType);

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String occupation = studyField.getText();
                AgeCategory ageCat = (AgeCategory) ageList.getSelectedValue();
                // Passing the information to Main as "ev" using .formEventOccured(ev) method.
                String empCat = (String) diseaseComboBox.getSelectedItem();
                String taxId = hostel.getText();
                boolean usCitizen = citizenCheck.isSelected();

                String genderCommand = genderGroup.getSelection().getActionCommand();

                FormEvent ev = new FormEvent(this,name,occupation,ageCat.getId(), empCat, taxId, usCitizen, genderCommand);

                if(formListener != null){
                    formListener.formEventOccured(ev);
                }
            }
        });

        Border innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5,5,5,5); // Just like a padding on all sides.
        setBorder(BorderFactory.createCompoundBorder(outerBorder,innerBorder));
        layoutComponents();

    }

    public void setFormListener(FormListener listener){
        this.formListener = listener;
    }

    class AgeCategory{
        private int id;
        private String text;

        public AgeCategory(int id, String text){
            this.id = id;
            this.text = text;
        }

        public String toString(){
            return text;
        }

        private int getId(){
            return id;
        }
    }

    public void layoutComponents(){
        setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();


        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.LINE_END; //i.e the end of the name label
        gc.insets = new Insets(0,0,0,5);
        add(nameLabel,gc);


        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_START; // and the start of the name field get stuck together
        gc.insets = new Insets(0,0,0,0);
        nameField.setMinimumSize(nameField.getPreferredSize());
        add(nameField,gc);


        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = new Insets(0,0,0,5);
        add(fieldOfStudy,gc);

        gc.gridx = 1;
        gc.gridy = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = new Insets(0,0,0,0);
        studyField.setMinimumSize(studyField.getPreferredSize());
        add(studyField,gc);




        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.2;
        gc.gridx = 0; // x - column
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_START; // name Age is adjacently on top of the given respective field most importantly a row.
        gc.insets = new Insets(0,90,0,0);
        nameField.setMinimumSize(nameField.getPreferredSize());
        add(new JLabel("Age:"),gc);

        gc.gridx = 1;
        gc.gridy = 2;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,0,300);
        add(ageList,gc);

        // weight is only used with the "label" field
        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.2;
        gc.gridx = 0;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.FIRST_LINE_START; // and the start of the name field get stuck together
        gc.insets = new Insets(0,20,0,0);
        //nameField.setMinimumSize(nameField.getPreferredSize());
        add(new JLabel("Disease Type:"),gc);

        gc.gridx = 1;
        gc.gridy = 3;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,0,300);
        add(diseaseComboBox,gc);

        //
        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.2;
        gc.gridx = 0;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.FIRST_LINE_START; // and the start of the name field get stuck together
        gc.insets = new Insets(0,0,0,0);
        //nameField.setMinimumSize(nameField.getPreferredSize());
        add(new JLabel("                Hosteler:"),gc);

        gc.gridx = 1;
        gc.gridy = 4;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,0,300);
        add(citizenCheck,gc);

        ////

        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.2;
        gc.gridx = 1;
        gc.gridy = 5;

        gc.anchor = GridBagConstraints.FIRST_LINE_START; // and the start of the name field get stuck together
        gc.insets = new Insets(0,0,0,200);
        //nameField.setMinimumSize(nameField.getPreferredSize());
        add(hostelNumber,gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,40,0,140);
        add(hostel,gc);

        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 0.2;
        gc.gridx = 1;
        gc.gridy++;

        gc.anchor = GridBagConstraints.FIRST_LINE_START; // and the start of the name field get stuck together
        gc.insets = new Insets(0,0,0,200);
        //nameField.setMinimumSize(nameField.getPreferredSize());
        add(new JLabel("Gender:"),gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,40,0,140);
        add(maleRadio,gc);

        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,40,0,200);
        add(femaleRadio,gc);

        gc.weightx = 1; // Does not matter with the number, just that it is completely relative.
        gc.weighty = 2.0;
        gc.gridx = 1;
        gc.gridy++;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.insets = new Insets(0,0,0,150);
        add(okBtn,gc);

    }
}
