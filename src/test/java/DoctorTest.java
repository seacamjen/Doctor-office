import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;


public class DoctorTest {

  @Before
  public void setUp () {
  DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    Doctor myDoctor = new Doctor("Bowen", "Jokester");
    assertTrue(myDoctor instanceof Doctor);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    Doctor myDoctor = new Doctor("Bowen", "Jokester");
    assertEquals("Bowen", myDoctor.getName());
  }

  @Test
  public void getSpecialty_instantiatesWithSpecialty_String() {
    Doctor myDoctor = new Doctor("Bowen", "Jokester");
    assertEquals("Jokester", myDoctor.getSpecialty());
  }

  @Test
  public void saved_savesDoctor
}
