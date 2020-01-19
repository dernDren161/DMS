package gui;

import model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

// PersonTableModel is just a wrapper which is pointing to add the data into my Table Model.
public class PersonTableModel extends AbstractTableModel {
    private List<Person> db;

    private String[] colNames = {"ID","Name","Study Field","Age Category","Disease", "Hosteler", "Hostel No."};

    public PersonTableModel(){

    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    public void setData(List<Person> db){
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size(); // number of Person Object instantiated
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Person person = db.get(row);

        switch(col){
            case 0:
                return person.getId();
            case 1:
                return person.getName();
            case 2:
                return person.getOccupation();
            case 3:
                return person.getAgeCategory();
            case 4:
                return person.getEmpCat();
            case 5:
                return person.isUsCitizen();
            case 6:
                return person.getTaxId();
        }
        return null;
    }
}
