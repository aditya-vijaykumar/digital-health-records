package models;

public class Patient {
  private int patientID;
  private String fname;
  private String lname;
  private String email;
  private String bloodGroup;
  private String gender;
  private int age;
  private int height;
  private int weight;
  private String allergies;

  public Patient(int patientID, String fname, String lname, String email, String bloodGroup, String gender, int age,
      int height, int weight, String allergies) {
    this.patientID = patientID;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.bloodGroup = bloodGroup;
    this.gender = gender;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.allergies = allergies;
  }

  public int getPatientID() {
    return this.patientID;
  }

  public String getFName() {
    return this.fname;
  }

  public String getLName() {
    return this.lname;
  }

  public String getName() {
    return this.fname + "  " + this.lname;
  }

  public String getEmail() {
    return this.email;
  }

  public String getBloodGrp() {
    return this.bloodGroup;
  }

  public String getGender() {
    return this.gender;
  }

  public int getAge() {
    return this.age;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWeight() {
    return this.weight;
  }

  public String getAllergies() {
    return this.allergies;
  }
}
