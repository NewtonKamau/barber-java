import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Stylist {
  private String name;
  private int id;

  public Stylist(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static List<Stylist> all() {
    String sql = "SELECT id, name FROM Stylists";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  public int getId() {
    return id;
  }

 public static Stylist find(int id) {
     try(Connection con = DB.sql2o.open()) {
       String sql = "SELECT * FROM Stylists where id=:id";
       Stylist stylist = con.createQuery(sql)
         .addParameter("id", id)
         .executeAndFetchFirst(Stylist.class);
       return stylist;
     }
   }

 public List<Client> getClients() {
   try(Connection con = DB.sql2o.open()) {
     String sql = "SELECT * FROM Clients where stylistId=:id";
     return con.createQuery(sql)
       .addParameter("id", this.id)
       .executeAndFetch(Client.class);
   }
 }

 @Override
 public boolean equals(Object otherstylist) {
   if (!(otherstylist instanceof Stylist)) {
     return false;
   } else {
     Stylist newstylist = (Stylist) otherstylist;
     return this.getName().equals(newstylist.getName()) &&
            this.getId() == newstylist.getId();
   }
 }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Stylists(name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

}
