import org.junit.*;
import org.sql2o.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;


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
    Doctor myDoctor = new Doctor("Bowen", 1);
    assertTrue(myDoctor instanceof Doctor);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    assertEquals("Bowen", myDoctor.getName());
  }

  @Test
  public void getSpecialtyId_instantiatesWithSpecialty_int() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    assertEquals(1, myDoctor.getSpecialtyId());
  }

  @Test
  public void getId_instantiatesWithId_int() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    myDoctor.save();
    assertTrue(myDoctor.getId() > 0);
  }

  @Test
  public void getSpecialtyId_instantiatesWithSpecialtyId_int() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    myDoctor.save();
    assertEquals(1, myDoctor.getSpecialtyId());
  }

  @Test
  public void save_savesDoctorIntoDatabase_true() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    myDoctor.save();
    assertTrue(Doctor.all().get(0).equals(myDoctor));
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    Doctor myDoctor1 = new Doctor("Bowen", 1);
    myDoctor1.save();
    Doctor myDoctor2 = new Doctor("Franz", 1);
    myDoctor2.save();
    assertEquals(true, Doctor.all().get(0).equals(myDoctor1));
    assertEquals(true, Doctor.all().get(1).equals(myDoctor2));
  }

  @Test
  public void find_returnsDoctorWithSameId_myDoctor2() {
    Doctor myDoctor1 = new Doctor("Bowen", 1);
    myDoctor1.save();
    Doctor myDoctor2 = new Doctor("Franz", 1);
    myDoctor2.save();
    assertEquals(Doctor.find(myDoctor2.getId()), myDoctor2);
  }

  @Test
  public void getPatients_retrivesAllPatientsFromDatabase_patienceList() {
    Doctor myDoctor = new Doctor("Bowen", 1);
    myDoctor.save();
    Patient firstPatient = new Patient("Emily", "2013-03-16", myDoctor.getId());
    firstPatient.save();
    Patient secondPatient = new Patient("Ralph", "2010-01-16", myDoctor.getId());
    secondPatient.save();
    Patient[] patients = new Patient[] { firstPatient, secondPatient };
    assertTrue(myDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }

  @Test
public void update_updatesDoctorDescription_true() {
  Doctor myDoctor = new Doctor("Sam", 1);
  myDoctor.save();
  myDoctor.update("Roger");
  assertEquals("Roger", Doctor.find(myDoctor.getId()).getName());
}
}
