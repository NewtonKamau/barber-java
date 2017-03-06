import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Client {
  private String description;
  private boolean completed;
  private LocalDateTime createdAt;
  private int id;
  private int stylistId;

  public Client(String description, int stylistId) {
    this.description = description;
    completed = false;
    createdAt = LocalDateTime.now();
    this.stylistId = stylistId;
  }

  public String getDescription() {
    return description;
  }

  public boolean isCompleted() {
    return completed;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public int getStylistId() {
    return stylistId;
  }
  // public String getClient() {
  //   return client;
  // }

  public int getId() {
    return id;
  }

  public static List<Client> all() {
    String sql = "SELECT id, description, stylistId FROM Clients";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Client.class);
    }
  }

  @Override
  public boolean equals(Object otherClient){
    if (!(otherClient instanceof Client)) {
      return false;
    } else {
      Client newClient = (Client) otherClient;
      return this.getDescription().equals(newClient.getDescription()) &&
             this.getId() == newClient.getId() &&
             this.getStylistId() == newClient.getStylistId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Clients(description, stylistId) VALUES (:description, :stylistId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("description", this.description)
        .addParameter("stylistId", this.stylistId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Client find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Clients where id=:id";
      Client Client = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Client.class);
      return Client;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
    String sql = "UPDATE Clients SET description = :description WHERE id = :id";
    con.createQuery(sql)
      .addParameter("description", description)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
  // logic of the delete test
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM Clients WHERE id = :id;";
      con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
  
}
