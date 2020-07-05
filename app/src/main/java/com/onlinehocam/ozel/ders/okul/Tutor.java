package com.onlinehocam.ozel.ders.okul;

import android.graphics.Bitmap;

public class Tutor
{
    public int userID;
    public String userName;
    public String gender;
    public int photoID;
    public Bitmap photoImage;
    public double tutorRating;
    public int answersDisplayedTimes;
    public String lastSchoolName;
    public String educationField;
    public String ibanNo;
    public int[] mostRecentCommentIDsArray;
    public int[] allCommentIDsArray;
    public boolean isFavorite;

    public String realName, realSurname, birthDate, phoneNumber, classSelections;
    public int educationalAttainment, cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry;

    // name, surname, gender, birthDate, educationalAttainment, lastSchool, educationField,
    // cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry, phoneNumber, -1, photoID


    // Constructor For Tutor Register
    public Tutor(String realName, String realSurname, String gender, String birthDate, int educationalAttainment, String lastSchoolName, String educationField, int cityOfResidency, int districtOfResidency, int cityOfRegistry, int districtOfRegistry, String phoneNumber, int userID, int photoID, String ibanNo, String classSelections) {
        this.userID = userID;
        this.gender = gender;
        this.photoID = photoID;
        this.photoImage = photoImage;
        this.lastSchoolName = lastSchoolName;
        this.educationField = educationField;
        this.realName = realName;
        this.realSurname = realSurname;
        this.birthDate = birthDate;
        this.educationalAttainment = educationalAttainment;
        this.cityOfResidency = cityOfResidency;
        this.districtOfResidency = districtOfResidency;
        this.cityOfRegistry = cityOfRegistry;
        this.districtOfRegistry = districtOfRegistry;
        this.phoneNumber = phoneNumber;
        this.ibanNo = ibanNo;
        this.classSelections = classSelections;
    }

    // Constructor For Tutor Discovery
    public Tutor(int userID, String userName, String gender, int photoID, Bitmap photoImage, double tutorRating, int answersDisplayedTimes, String lastSchoolName, String educationField, int[] mostRecentCommentIDsArray, int[] allCommentIDsArray, boolean isFavorite, String classSelections) {
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.photoID = photoID;
        this.photoImage = photoImage;
        this.tutorRating = tutorRating;
        this.answersDisplayedTimes = answersDisplayedTimes;
        this.lastSchoolName = lastSchoolName;
        this.educationField = educationField;
        this.mostRecentCommentIDsArray = mostRecentCommentIDsArray;
        this.allCommentIDsArray = allCommentIDsArray;
        this.isFavorite = isFavorite;
        this.classSelections = classSelections;
    }



}



