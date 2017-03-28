import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;

public class PatientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test",null,null);
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
  public void Patient_instantiatesCorrectly_true() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    assertTrue(testPatient instanceof Patient);
  }

  @Test
  public void getName_getsPatientName_Patience() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    assertEquals("Patience", testPatient.getName());
  }

  @Test
  public void getBirthdate_getsPatientBirthdate_String() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    assertEquals("2013-03-16", testPatient.getBirthdate());
  }

  @Test
  public void getDoctorId_getsPatientDoctorId_1() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    assertEquals(1, testPatient.getDoctorId());
  }

  @Test
  public void all_returnsAllSavedPatients() {
    Patient testPatient1 = new Patient("Patience","2013-03-16", 1);
    testPatient1.save();
    Patient testPatient2 = new Patient("The Dude","1955-05-22", 2);
    testPatient2.save();
    assertTrue(Patient.all().get(0).equals(testPatient1));
    assertTrue(Patient.all().get(1).equals(testPatient2));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    testPatient.save();
    assertTrue(Patient.all().get(0).equals(testPatient));
  }

  @Test
  public void save_assignsIdToObject() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    testPatient.save();
    Patient savedPatient = Patient.all().get(0);
    assertEquals(testPatient.getId(), savedPatient.getId());
  }

  @Test
  public void getId_getPatientId_1() {
    Patient testPatient = new Patient("Patience","2013-03-16", 1);
    testPatient.save();
    assertTrue(testPatient.getId() > 0);
  }

  @Test
  public void find_returnsPatientWithSameId_testPatient2() {
    Patient testPatient1 = new Patient("Patience","2013-03-16", 1);
    testPatient1.save();
    Patient testPatient2 = new Patient("The Dude","1955-05-22", 2);
    testPatient2.save();
    assertEquals(Patient.find(testPatient2.getId()), testPatient2);
  }
}
