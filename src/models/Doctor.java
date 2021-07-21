package models;

public class Doctor {
  private String mlicense;
  private String fname;
  private String lname;
  private String email;
  private String quali;
  private int age;
  private String gender;
  private String specialization;
  private String about;

  public Doctor(String mlicense, String fname, String lname, String email, String quali, int age, String gender,
      String specialization, String about) {
    this.mlicense = mlicense;
    this.fname = fname;
    this.lname = lname;
    this.email = email;
    this.quali = quali;
    this.age = age;
    this.gender = gender;
    this.specialization = specialization;
    this.about = about;
  }

  public String getEmail() {
    return this.email;
  }

  public String getFName() {
    return this.fname;
  }

  public String getLName() {
    return this.lname;
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

  public String getGender() {
    return this.gender;
  }

  public String getSpclzn() {
    return this.specialization;
  }

  public String getAbout() {
    return this.about;
  }

  public String emailProperty() {
    return this.email;
  }

  public String nameProperty() {
    return this.fname + " " + this.lname;
  }

  public String qualiProperty() {
    return this.quali;
  }

  public String mLicenseProperty() {
    return this.mlicense;
  }

  public int ageProperty() {
    return this.age;
  }
}
