package com.interview.ammaryali.pheramor_android_developer_position_assessment.model;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

public class UserVO implements Serializable {

    private String fullName;
    private String email;
    private String password;
    private int zipCode;
    private String height;
    private String gender;
    private Date dateOfBirth;
    private String interestGender;
    private int ageRangeMin;
    private int ageRangeMax;
    private String race;
    private String religion;
    private Bitmap profilePicture;

    public UserVO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInterestGender() {
        return interestGender;
    }

    public void setInterestGender(String interestGender) {
        this.interestGender = interestGender;
    }

    public int getAgeRangeMin() {
        return ageRangeMin;
    }

    public void setAgeRangeMin(int ageRangeMin) {
        this.ageRangeMin = ageRangeMin;
    }

    public int getAgeRangeMax() {
        return ageRangeMax;
    }

    public void setAgeRangeMax(int ageRangeMax) {
        this.ageRangeMax = ageRangeMax;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

}
