package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

// Not sure but I think the "JToolBar" class helps in the docking of the toolbar buttons to the rightmost side of the application.
public class Toolbar extends JToolBar implements ActionListener {
    private JButton saveButton;
    private JButton refreshButton;
    private ToolbarListener textListener; // Misconception: This is not an instantiation of the interface class in Java. A a = new A() // is the real instantiation of interface A which is not allowed by Java

    public Toolbar(){
        //Pro Tip: While using external jars with these applications like the "mysql-connector-driver" or the "java-graphics-repository"
        // always add these external jars as the "class path" in the project by -->>> File -> Project Structure -> Libraries -> ("then go ahead and add the packages")

        setFloatable(false); // if I don't want the docking thing to happen.
        saveButton = new JButton();
        saveButton.setIcon(Utils.createIcon("/images/Save24.gif"));
        saveButton.setToolTipText("Save");

        refreshButton = new JButton();
        refreshButton.setIcon(Utils.createIcon("/images/Refresh24.gif"));
        saveButton.setToolTipText("Refresh");

        saveButton.addActionListener(this); // "this" means the instance "helloButton"
        refreshButton.addActionListener(this);

        add(saveButton);
        add(refreshButton);

        //setLayout(new FlowLayout(FlowLayout.LEFT)); // "FlowLayout" arranges things in a row with a default gap between components to be 5 pixels.
        add(saveButton);
        //addSeparator();
        add(refreshButton);
    }



    //called from the gui.MainFrame class i.e this method is called from the gui.MainFrame class.
  public void setToolbarListener(ToolbarListener listener){
        this.textListener = listener;
  }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if(clicked == saveButton){
            if(textListener != null){
                textListener.saveEventOccured();
            }
        }else if(clicked == refreshButton){
            if(textListener != null){
                textListener.refreshEventOccured();
            }
        }

    }
}
