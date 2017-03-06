import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/barber_test", "postgres", "newton");
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteclientsQuery = "DELETE FROM clients *;";
      String deleteStylistsQuery = "DELETE FROM Stylists *;";
      con.createQuery(deleteclientsQuery).executeUpdate();
      con.createQuery(deleteStylistsQuery).executeUpdate();
    }
  }

}