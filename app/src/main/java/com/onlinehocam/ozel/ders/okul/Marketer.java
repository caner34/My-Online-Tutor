package com.onlinehocam.ozel.ders.okul;

import android.graphics.Bitmap;

public class Marketer
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

    public String realName, realSurname, birthDate, phoneNumber, marketingFieldSelections;
    public int educationalAttainment, cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry;

    // name, surname, gender, birthDate, educationalAttainment, lastSchool, educationField,
    // cityOfResidency, districtOfResidency, cityOfRegistry, districtOfRegistry, phoneNumber, -1, photoID


    // Constructor For Marketer Register
    public Marketer(String realName, String realSurname, String gender, String birthDate, int educationalAttainment, String lastSchoolName, String educationField, int cityOfResidency, int districtOfResidency, int cityOfRegistry, int districtOfRegistry, String phoneNumber, int userID, int photoID, String ibanNo, String marketingFieldSelections) {
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
        this.marketingFieldSelections = marketingFieldSelections;
    }
}

