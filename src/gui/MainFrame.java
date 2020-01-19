package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4775907780893361729L;

    private TextPanel textPanel;
    private Toolbar toolbar;
    private  FormPanel formPanel;
    private JFileChooser fileChooser;
    private Controller controller;
    private TablePanel tablePanel;
    private PrefsDialog prefsDialog;
    private Preferences prefs;
    private JSplitPane splitPane;
    private JTabbedPane tabPane;
    private MessagePanel messagePanel;

    public MainFrame(){
        super("Disease Monitoring System"); // same as: JFrame instance = new JFrame("Hello");

        setLayout(new BorderLayout()); // east, west, north, south and center functionalities inclusion.
        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        fileChooser = new JFileChooser();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);
        prefs = Preferences.userRoot().node("db");
        tabPane = new JTabbedPane();
        messagePanel = new MessagePanel();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,formPanel,tabPane); //means at the left-->> formPanel and at the right -->> tablePanel.

        tabPane.addTab("Person Database", tablePanel);

        // Previously the message panel had the text panel but actually now we change it into something else.
        //tabPane.addTab("Messages", textPanel);

        tabPane.addTab("Message",messagePanel);

        tablePanel.setData(controller.getPeople());

        // These are the Anonymous Classes in Java which are popularily used with the Listener buttons.
        tablePanel.setPersonTableListener(new PersonTableListener(){
          public void rowDeleted(int row){
            controller.removePerson(row);
          }
      });

      prefsDialog.setPrefsListener(new PrefsListener(){

          @Override
          public void preferencesSet(String user, String password, int port) {
              prefs.put("user",user);
              prefs.put("password",password);
              prefs.putInt("port",port); // for the integer value
          }
      });

      String user = prefs.get("user","");
      String password = prefs.get("password","");
      Integer port = prefs.getInt("port",3306);

      prefsDialog.setDefault(user,password,port); // We set the default value in the Preferences Dialog box.


        FileFilter fileFilter = new PersonFileFilter(); // this is not working......!!.....!!
        fileChooser.addChoosableFileFilter(fileFilter);
        fileChooser.setFileFilter(fileFilter);

        setJMenuBar(createMenuBar());

        //Anonymous class
        // first the .setStringListener gets called giving the value for the listener instance and then the textEmitted method is called later on.
        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                System.out.println("Saving is working");
                connect();

                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Cannot save to database", "Problem Saving into the Database", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void refreshEventOccured() {
                System.out.println("Refreshing");
                connect();

                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Cannot load the database", "Problem Loading from the Database", JOptionPane.ERROR_MESSAGE);
                }

                tablePanel.refresh();
            }

        });
        // use: no need to create another class to implement the interface gui.FormListener thus making the code very consise.
        formPanel.setFormListener(new FormListener(){
            public void formEventOccured(FormEvent e){
                controller.addPerson(e);
                tablePanel.refresh(); // the method that made the visibility of the rows possible in the text field of our desktop application.
                //(A nice debugging tool)System.out.println(e.getGenderCommand());
            }
        });

        // Used to disconnect() from the database when the window is closed.
     addWindowListener(new WindowAdapter() {
         @Override
         public void windowClosing(WindowEvent e) {
             controller.disconnect();
             dispose();
             System.gc();
         }
     });

        add(toolbar,BorderLayout.PAGE_START); // adds a toolbar shade in the North.
        //add(textPanel,BorderLayout.CENTER);
        add(splitPane,BorderLayout.CENTER);


        setMinimumSize(new Dimension(400,300)); // the minimum extent till which the window can crunch down to.
        setSize(800,600); // same as: instance.setSize(600,500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // program terminates once the desktop app is closed(IntelliJ red button goes off)
        setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Database Connection Problem", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data");
        JMenuItem importDataItem = new JMenuItem("Import Data");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator(); // visual distinction between the items in the Menu bar.
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JMenuItem prefsItem = new JMenuItem("Preferences...");
       // JMenuItem showFormItem = new JMenuItem("Person Form");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person Form");
        showFormItem.setSelected(false);

        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        // Creates another Dialog Box!!
        prefsItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prefsDialog.setVisible(true);
            }
        });

        showFormItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource(); // we'll get "showFormItem"
                formPanel.setVisible(menuItem.isSelected());
            }
        });

        fileMenu.setMnemonic(KeyEvent.VK_F); // Mnemonics
        exitItem.setMnemonic(KeyEvent.VK_X); // Mnemonics

        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK)); // Accelerators.

       importDataItem.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               if(fileChooser.showOpenDialog(MainFrame.this)==JFileChooser.APPROVE_OPTION){
                    try{
                        controller.loadFromFile(fileChooser.getSelectedFile());
                    } catch (IOException el){
                        JOptionPane.showMessageDialog(MainFrame.this,"Could not load data from file.","Error",JOptionPane.ERROR_MESSAGE);
                    }
               }
           }
       });

        exportDataItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileChooser.showSaveDialog(MainFrame.this)== JFileChooser.APPROVE_OPTION){
                    try{
                        controller.saveToFile(fileChooser.getSelectedFile());
                        tablePanel.refresh();
                    } catch (IOException el){
                        JOptionPane.showMessageDialog(MainFrame.this,"Could not save data to file.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int action = JOptionPane.showConfirmDialog(MainFrame.this,"Do you really want to Exit?","Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
               if(action == JOptionPane.OK_OPTION){
                   WindowListener[] listeners = getWindowListeners();
                   for(WindowListener listener: listeners){
                       listener.windowClosing(new WindowEvent(MainFrame.this,0));
                   }

               }

            }
        });

        return menuBar;
    }
}
