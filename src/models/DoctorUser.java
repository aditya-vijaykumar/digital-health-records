package models;

public class DoctorUser {
  String name;
  String username;

  DoctorUser(String a, String b) {
    this.name = a;
    this.username = b;
  }

  public String getUsername() {
    return this.username;
  }

  public String getName() {
    return this.name;
  }

  public void setUsername(String a) {
    this.username = a;
  }

  public void setName(String a) {
    this.name = a;
  }
}
