import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class DoctorTest{
  private Doctor firstDoctor;
  private Doctor secondDoctor;
  private Patient testPatient;


  @Before
  public void initialize() {
      DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctor_office_test", null, null);
      firstDoctor = new Doctor("Doc Oc", "Spiders");
      secondDoctor = new Doctor("Dr Strange", "Magic");
      testPatient = new Patient("Will Smith", "07/02/1985", 1);
  }

  @Test
  public void Doctor_instantiatesCorrectly_true() {
    assertTrue(firstDoctor instanceof Doctor);
  }

  @Test
  public void getName_doctorInstantiatesWithName_DocOc() {
    assertEquals("Doc Oc", firstDoctor.getName());
  }

  @Test
  public void getFocus_doctorInstantiatesWithFocus_Spiders() {
    assertEquals("Spiders", firstDoctor.getFocus());
  }

  @Test
  public void getId_doctorInstantiatesWithId_1() {
    firstDoctor.save();
    assertTrue(firstDoctor.getId() > 0);
  }

  @Test
  public void save_assignsIdToObject() {
    firstDoctor.save();
    Doctor savedDoctor = Doctor.all().get(0);
    assertEquals(firstDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void all_returnsAllInstancesOfDoctor_true() {
    firstDoctor.save();
    secondDoctor.save();
    assertTrue(Doctor.all().contains(firstDoctor));
    assertTrue(Doctor.all().contains(secondDoctor));
  }

  @Test
  public void getPatients_returnsListOfDoctorPatients_true() {
    firstDoctor.save();
    Patient firstPatient = new Patient("Will Smith", "07/02/1985", firstDoctor.getId());
    firstPatient.save();
    Patient secondPatient = new Patient("Will Smith", "07/02/1985", firstDoctor.getId());
    secondPatient.save();
    Patient[] patients = new Patient[] {firstPatient, secondPatient};
    assertTrue(firstDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDoctorQuery = "DELETE FROM doctors *;";
      String deletePatientsQuery = "DELETE FROM patients *;";
      con.createQuery(deleteDoctorQuery).executeUpdate();
      con.createQuery(deletePatientsQuery).executeUpdate();
    }
  }
}
