package gui;

import java.util.EventObject;

public class FormEvent extends EventObject {
    private String name;
    private String occupation;
    private int ageCategory;
    private String empCat;
    private String taxId;
    private boolean usCitizen;
    private String genderCommand;

    public FormEvent(Object source) {
        super(source);
    }

    public FormEvent(Object source, String name, String occupation, int ageCat, String empCat
    ,String taxId, boolean usCitizen, String genderCommand) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCat;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.genderCommand = genderCommand;
    }

    public String getGenderCommand() {
        return genderCommand;
    }

    public String getTaxId() {
        return taxId;
    }


    public boolean isUsCitizen() {
        return usCitizen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getAgeCategory() {
        return ageCategory;
    }

    public String getEmploymentCategory(){
        return empCat;
    }
}
