package gui;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

    private JTextArea textArea;
        // JPanel is just a container in Java Swing.
    public TextPanel(){
        textArea = new JTextArea();
        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER); // the scrolling function for the desktop application.
    }

    public void appendText(String text){
        textArea.append(text);
    }
}
