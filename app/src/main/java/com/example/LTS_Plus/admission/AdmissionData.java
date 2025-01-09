package com.example.LTS_Plus.admission;

public class AdmissionData {

    private final String childFirstName;
    private final String childSurname;
    private final String dateOfBirth;
    private final String placeOfBirth;
    private final String nationality;
    private final String religion;
    private final String childAge;
    private final String childsGender;
    private final String fathersName;
    private final String fathersContact;
    private final String mothersName;
    private final String mothersContact;
    private final String residentialAddress;
    private final String emergencyContact;
    private final String doctorsDetails;
    private final String doctorContact;
    private final String dateOfEntry;
    private final String questionOne;
    private final String questionTwo;
    private final String cardUpload;
    private final String otherInfo;
    private final String declaration;

    public AdmissionData(String childFirstName, String childSurname, String dateOfBirth, String placeOfBirth,
                         String nationality, String religion, String childAge, String childsGender,
                         String fathersName, String fathersContact, String mothersName, String mothersContact,
                         String residentialAddress, String emergencyContact, String doctorsDetails,
                         String doctorContact, String dateOfEntry, String questionOne, String questionTwo,
                         String cardUpload, String otherInfo, String declaration) {
        this.childFirstName = childFirstName;
        this.childSurname = childSurname;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.nationality = nationality;
        this.religion = religion;
        this.childAge = childAge;
        this.childsGender = childsGender;
        this.fathersName = fathersName;
        this.fathersContact = fathersContact;
        this.mothersName = mothersName;
        this.mothersContact = mothersContact;
        this.residentialAddress = residentialAddress;
        this.emergencyContact = emergencyContact;
        this.doctorsDetails = doctorsDetails;
        this.doctorContact = doctorContact;
        this.dateOfEntry = dateOfEntry;
        this.questionOne = questionOne;
        this.questionTwo = questionTwo;
        this.cardUpload = cardUpload;
        this.otherInfo = otherInfo;
        this.declaration = declaration;
    }

    public String getChildFirstName() {
        return childFirstName;
    }

    public String getChildSurname() {
        return childSurname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public String getReligion() {
        return religion;
    }

    public String getChildAge() {
        return childAge;
    }

    public String getChildsGender() {
        return childsGender;
    }

    public String getFathersName() {
        return fathersName;
    }

    public String getFathersContact() {
        return fathersContact;
    }

    public String getMothersName() {
        return mothersName;
    }

    public String getMothersContact() {
        return mothersContact;
    }

    public String getResidentialAddress() {
        return residentialAddress;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public String getDoctorsDetails() {
        return doctorsDetails;
    }

    public String getDoctorContact() {
        return doctorContact;
    }

    public String getDateOfEntry() {
        return dateOfEntry;
    }

    public String getQuestionOne() {
        return questionOne;
    }

    public String getQuestionTwo() {
        return questionTwo;
    }

    public String getCardUpload() {
        return cardUpload;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public String getDeclaration() {
        return declaration;
    }
}
