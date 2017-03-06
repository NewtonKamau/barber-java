import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;

public class ClientTest {

 @Rule
  public DatabaseRule database = new DatabaseRule();
 

  @Test
  public void Client_instantiatesCorrectly_true() {
    Client myClient = new Client("Mow the lawn", 1);
    assertEquals(true, myClient instanceof Client);
  }

  @Test
  public void Client_instantiatesWithDescription_String() {
    Client myClient = new Client("Mow the lawn", 1);
    assertEquals("Mow the lawn", myClient.getDescription());
  }

  @Test
  public void isCompleted_isFalseAfterInstantiation_false() {
    Client myClient = new Client("Mow the lawn", 1);
    assertEquals(false, myClient.isCompleted());
  }

  @Test
  public void getCreatedAt_instantiatesWithCurrentTime_today() {
    Client myClient = new Client("Mow the lawn", 1);
    assertEquals(LocalDateTime.now().getDayOfWeek(), myClient.getCreatedAt().getDayOfWeek());
  }

  @Test
  public void all_returnsAllInstancesOfClient_true() {
    Client firstClient = new Client("Mow the lawn", 1);
    firstClient.save();
    Client secondClient = new Client("Buy groceries", 1);
    secondClient.save();
    assertEquals(true, Client.all().get(0).equals(firstClient));
    assertEquals(true, Client.all().get(1).equals(secondClient));
  }

  @Test
  public void clear_emptiesAllClientsFromArrayList_0() {
    Client myClient = new Client("Mow the lawn", 1);
    assertEquals(Client.all().size(), 0);
  }

  @Test
  public void getId_ClientsInstantiateWithAnID_1() {
    Client myClient = new Client("Mow the lawn", 1);
    myClient.save();
    assertTrue(myClient.getId() > 0);
  }

  @Test
  public void find_returnsClientWithSameId_secondClient() {
    Client firstClient = new Client("Mow the lawn", 1);
    firstClient.save();
    Client secondClient = new Client("Facial", 1);
    secondClient.save();
    assertEquals(Client.find(secondClient.getId()), secondClient);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Client firstClient = new Client("Mow the lawn", 1);
    Client secondClient = new Client("Mow the lawn", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Client myClient = new Client("Mow the lawn", 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Mow the lawn", 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist myStylist = new Stylist("manicure");
    myStylist.save();
    Client myClient = new Client("Mow the lawn", myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }

  @Test
  public void update_updatesClientDescription_true() {
    Client myClient = new Client("Mow the lawn", 1);
    myClient.save();
    myClient.update("massage");
    assertEquals("massage", Client.find(myClient.getId()).getDescription());
  }
  // a test to include delete functionality

  @Test
  public void delete_deletesClients_true() {
    Client myClient = new Client("mow the lawn",1);
    myClient.save();
    int myClientId = myClient.getId();
    myClient.delete();
    assertEquals(null, Client.find(myClientId));

  }

}
