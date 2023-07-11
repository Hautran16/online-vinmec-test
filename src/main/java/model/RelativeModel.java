package model;

public class RelativeModel {

    public String name;
    public String birthday;
    public String gender;
    public String phoneNumber;
    public String relationship;
    public String errMessage;

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRelationship() {
        return relationship;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public RelativeModel(){

    }

    public RelativeModel (String name, String birthday, String gender, String phoneNumber, String relationship){
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.relationship = relationship;
    }



}
