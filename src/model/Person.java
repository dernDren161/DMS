package model;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = -8219218627533074108L;
    // Whole of the class FormEvent.
    private static int count = 1;
    private int id;
    private String name;
    private String occupation;
    private AgeCategory ageCategory;
    private DiseaseCategory empCat;
    private String taxId;
    private boolean usCitizen;
    private Gender genderCommand;

    public Person(String name, String occupation, AgeCategory ageCategory, DiseaseCategory empCat, String taxId, boolean usCitizen, Gender genderCommand){
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.genderCommand = genderCommand;
        this.id = count;
        count++; // helps count the total number of times the "Person" class is instantiated.
    }

    public Person(int id, String name, String occupation, AgeCategory ageCategory, DiseaseCategory empCat, String taxId, boolean usCitizen, Gender genderCommand){

        this(name, occupation, ageCategory, empCat, taxId, usCitizen, genderCommand); // this statement actually calls the upper constructor.
        this.id = id; // back again

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public DiseaseCategory getEmpCat() {
        return empCat;
    }

    public void setEmpCat(DiseaseCategory empCat) {
        this.empCat = empCat;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public boolean isUsCitizen() {
        return usCitizen;
    }

    public void setUsCitizen(boolean usCitizen) {
        this.usCitizen = usCitizen;
    }

    public Gender getGenderCommand() {
        return genderCommand;
    }

    public void setGenderCommand(Gender genderCommand) {
        this.genderCommand = genderCommand;
    }

    public String toString(){
        return id + " : " + name;
    }
}
