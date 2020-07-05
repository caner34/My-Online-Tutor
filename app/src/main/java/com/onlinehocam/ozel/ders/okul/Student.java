package com.onlinehocam.ozel.ders.okul;

public class Student
{
    public String name, surname, gender, birthDate, phoneNumber, lastSchool, educationField, classSelections, imageDataAddressID;
    public int userID, photoID, educationalAttainmentID, cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry;

    public Student(String name, String surname, String gender, String birthDate, int educationalAttainmentID, String lastSchool, String educationField, int cityOfResidency, int districtOfResidency, int cityOfRegistry, int districtOfRegistry, String phoneNumber, int userID, int photoID, String classSelections) {
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.educationalAttainmentID = educationalAttainmentID;
        this.lastSchool = lastSchool;
        this.educationField = educationField;
        this.cityOfResidency = cityOfResidency;
        this.districtOfResidency = districtOfResidency;
        this.cityOfRegistry = cityOfRegistry;
        this.districtOfRegistry = districtOfRegistry;
        this.phoneNumber = phoneNumber;
        this.userID = userID;
        this.photoID = photoID;
        this.classSelections = classSelections;
    }

    public Student(int userID, String imageDataAddressID, String gender) {
        this.gender = gender;
        this.userID = userID;
        this.imageDataAddressID = imageDataAddressID;
    }
}
