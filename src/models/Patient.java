package models;

public class Patient {
  private int patientID;
  private String fname;
  private String lname;
  private String email;
  private String bloodGroup;
  private String allergies;
  private int weight;
  private int height;
  private int age;

  public Patient(int patientID, String fname, String lname, String email, String bloodGroup, String allergies, int weight,
      int height, int age) {
    this.patientID = patientID;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.bloodGroup = bloodGroup;
    this.allergies = allergies;
    this.weight = weight;
    this.height = height;
    this.age = age;
  }

  public int getPatientID() {
    return this.patientID;
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

  public String getAllergies() {
    return this.allergies;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWeight() {
    return this.weight;
  }

  public int getAge() {
    return this.age;
  }
}
