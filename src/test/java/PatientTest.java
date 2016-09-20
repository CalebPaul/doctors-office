import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class PatientTest {
  private Patient firstPatient;
  private Patient secondPatient;

  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
    firstPatient = new Patient("Bill Smith", "09/19/2016", 1);
    secondPatient = new Patient("Pam Jones", "04/02/1985", 1);
  }

  @Test
  public void patient_instantiatesCorrectly_true() {
    assertEquals(true, firstPatient instanceof Patient);
  }

  @Test
  public void getName_instantiatesWithName_String() {
    assertEquals("Bill Smith", firstPatient.getName());
  }

  @Test
  public void getDob_instantiatesWithDob_String() {
    assertEquals("09/19/2016", firstPatient.getDob());
  }

  @Test
  public void getDoctorId_instantiatesWithDoctorId_int() {
    assertEquals(1, firstPatient.getDoctorId());
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    firstPatient.save();
    Patient savedPatient = Patient.all().get(0);
    assertTrue(Patient.all().get(0).equals(firstPatient));
    assertEquals(firstPatient.getId(), savedPatient.getId());
  }

  @Test
  public void all_returnsAllInstancesOfPatient_true() {
    firstPatient.save();
    secondPatient.save();
    assertTrue(Patient.all().contains(firstPatient));
    assertTrue(Patient.all().contains(secondPatient));
  }

  @Test
  public void find_returnsPatientWithSameId_secondPatient() {
    secondPatient.save();
    assertEquals(Patient.find(secondPatient.getId()), secondPatient);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM patients *;";
      con.createQuery(sql).executeUpdate();
    }
  }


}
