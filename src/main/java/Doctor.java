import org.sql2o.*;
import java.util.List;

public class Doctor {
  private String name;
  private String specialty;

  public Doctor(String name, String specialty) {
    this.name = name;
    this.specialty = specialty;
  }

  public String getName() {
    return this.name;
  }

  public String getSpecialty() {
    return this.specialty;
  }
}
