import org.sql2o.*;
import java.util.List;

public class Specialty {

  private int id;
  private String name;

  public Specialty(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public static List<Specialty> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialties;";
      return con.createQuery(sql)
        .executeAndFetch(Specialty.class);
    }
  }
}
