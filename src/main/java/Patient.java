import org.sql2o.*;
import java.util.List;

public class Patient {

  private int id;
  private String name;
  private String birthdate;
  private int doctorId;

  public Patient(String name, String birthdate, int doctorId) {
    this.name = name;
    this.birthdate = birthdate;
    this.doctorId = doctorId;
  }

  public String getName() {
    return this.name;
  }

  public String getBirthdate() {
    return this.birthdate;
  }

  public int getDoctorId() {
    return this.doctorId;
  }

  public static List<Patient> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients;";
      return con.createQuery(sql)
        .addColumnMapping("doctor_id", "doctorId")
        .executeAndFetch(Patient.class);
    }
  }

  public int getId() {
    return this.id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthdate, doctor_id) VALUES (:name, CAST(:birthdate AS DATE), :doctorId)";
      this.id = (int) con.createQuery(sql, true)
        .addColumnMapping("doctor_id", "doctorId")
        .addParameter("name", this.name)
        .addParameter("birthdate", this.birthdate)
        .addParameter("doctorId", this.doctorId)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName());
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients WHERE id = :id;";
      Patient patient = con.createQuery(sql)
        .addColumnMapping("doctor_id", "doctorId")
        .addParameter("id", id)
        .executeAndFetchFirst(Patient.class);
      return patient;
    }
  }
}
