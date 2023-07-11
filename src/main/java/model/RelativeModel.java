package model;

public class RelativeModel {

    public static String name;
    public static String birthday;
    public static String gender;
    public static String phoneNumber;
    public static String relationship;
    public static String errMessage;

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
