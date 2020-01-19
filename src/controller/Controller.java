package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    Database db = new Database();

    public List<Person> getPeople(){
        return db.getPeople();
    }

    public void removePerson(int index){
        db.removePerson(index);
    }

    public void save() throws SQLException {
        db.save();
    }

    public void load() throws SQLException {
        db.load();
    }

    public void connect() throws Exception {
        db.connect();
    }
    public void disconnect(){
        db.disconnect();
    }
    public void addPerson(FormEvent e){
        String name = e.getName();
        String occupation = e.getOccupation();
        int ageCatId = e.getAgeCategory();
        String empCat = e.getEmploymentCategory();
        boolean isUs = e.isUsCitizen();
        String taxId = e.getTaxId();
        String gender = e.getGenderCommand();

        AgeCategory ageCategory = null;

        switch (ageCatId){
            case 0:
                ageCategory = AgeCategory.teen;
                break;
            case 1:
                ageCategory = AgeCategory.adult;
            case 2:
                ageCategory = AgeCategory.mature;
                break;
        }

        DiseaseCategory empCategory;

        if(empCat.equals("employed")){
            empCategory = DiseaseCategory.dengue;
        }
        else if(empCat.equals("self-employed")){
            empCategory = DiseaseCategory.malaria;
        }
        else if(empCat.equals("unemployed")){
            empCategory = DiseaseCategory.chikungunya;
        }
        else{
            empCategory = DiseaseCategory.other;
        }
        Gender genderCat;
        if(gender.equals("male")){
            genderCat = Gender.male;
        }
        else{
            genderCat = Gender.female;
        }

        Person person = new Person(name,occupation,ageCategory,empCategory,taxId,isUs,genderCat);
        db.addPerson(person);

    }

    public void saveToFile(File file) throws IOException{
        db.saveToFile(file);
    }
    public void loadFromFile(File file) throws IOException{
        db.loadFromFile(file);
    }
}
