import org.sql2o.*;
import java.util.List;
import java.util.Arrays;

public class Doctor {
  private int id;
  private String name;
  private int specialtyId;

  public Doctor(String name, int specialtyId) {
    this.name = name;
    this.specialtyId = specialtyId;

  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }

  public int getSpecialtyId() {
    return this.specialtyId;
  }

  public static List<Doctor> all() {
    String sql = "SELECT * FROM doctors;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
        .addColumnMapping("specialty_id", "specialtyId")
        .executeAndFetch(Doctor.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors(name, specialty_id) VALUES (:name, :specialtyId);";
      this.id = (int) con.createQuery(sql, true)
        .addColumnMapping("specialty_id", "specialtyId")
        .addParameter("name", this.name)
        .addParameter("specialtyId", this.specialtyId)
        .executeUpdate()
        .getKey();
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if(!(otherDoctor instanceof Doctor)){
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName());
      // && this.getId() == newDoctor.getId();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors where id=:id;";
      Doctor doctor = con.createQuery(sql)
        .addColumnMapping("specialty_id", "specialtyId")
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients WHERE doctor_id=:id;";
      return con.createQuery(sql)
        .addColumnMapping("doctor_id", "doctorId")
        .addParameter("id", this.id)
        .executeAndFetch(Patient.class);
    }
  }

  public void update(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE doctors SET name = :name WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
