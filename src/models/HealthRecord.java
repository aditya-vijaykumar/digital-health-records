package models;

//CREATE TABLE HEALTH_RECORDS(RECORD_NO INT PRIMARY KEY AUTO_INCREMENT, CREATE_DATE DATE, 
//SYMPTOMS VARCHAR(255), MED_CONDITION VARCHAR(255), DIAGNOSIS VARCHAR(1000), MEDICATION VARCHAR(255));
public class HealthRecord {
  private int record_no;
  private String create_date;
  private String symptoms;
  private String med_condition;
  private String diagnosis;
  private String medication;

  public HealthRecord(int record_no, String create_date, String symptoms, String med_condition, String diagnosis,
      String medication) {
    this.record_no = record_no;
    this.create_date = create_date;
    this.symptoms = symptoms;
    this.med_condition = med_condition;
    this.diagnosis = diagnosis;
    this.medication = medication;
  }

  public String getCreateDate() {
    return this.create_date;
  }

  public String getSymptoms() {
    return this.symptoms;
  }

  public String getMedCondtn() {
    return this.med_condition;
  }

  public String getDiagnosis() {
    return this.diagnosis;
  }

  public String getMedication() {
    return this.medication;
  }

  public int getRecordNo() {
    return this.record_no;
  }
}
