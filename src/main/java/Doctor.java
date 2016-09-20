import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.sql2o.*;

public class Doctor {
  private String name;
  private String focus;
  private int id;

  public Doctor(String name, String focus){
    this.name = name;
    this.focus = focus;
  }

  public String getName() {
    return name;
  }

  public String getFocus() {
    return focus;
  }

  public int getId() {
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, focus) VALUES (:name, :focus)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("focus", this.focus)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Doctor> all() {
    String sql = "SELECT id, name, focus FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
                .executeAndFetch(Doctor.class);
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where doctorId=:id";
      return con.createQuery(sql)
                .addParameter("id", this.id)
                .executeAndFetch(Patient.class);
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors where id=:id";
      Doctor doctor = con.createQuery(sql)
                         .addParameter("id", id)
                         .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
             this.getFocus().equals(newDoctor.getFocus()) &&
             this.getId() == newDoctor.getId();
    }
  }
}
