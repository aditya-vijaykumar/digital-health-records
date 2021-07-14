package models;

public class Doctor {
  private String mlicense;
  private String fname;
  private String lname;
  private String email;
  private String quali;
  private int age;

  public Doctor(String mlicense, String fname, String lname, String email, int age) {
    this.mlicense = mlicense;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.age = age;
  }

  public String getEmail() {
    return this.email;
  }

  public String getName() {
    return this.fname + " " + this.lname;
  }

  public String getQuali() {
    return this.quali;
  }

  public String getMLicense() {
    return this.mlicense;
  }

  public int getAge() {
    return this.age;
  }
}
