import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class StylistTest {

 @Rule
public DatabaseRule database = new DatabaseRule();


  @Test
  public void Stylist_instantiatesCorrectly_true() {
    Stylist testStylist = new Stylist("Home");
    assertEquals(true, testStylist instanceof Stylist);
  }

  @Test
  public void getName_StylistInstantiatesWithName_Home() {
    Stylist testStylist = new Stylist("Home");
    assertEquals("Home", testStylist.getName());
  }

 @Test
 public void all_returnsAllInstancesOfStylist_true() {
   Stylist firstStylist = new Stylist("Home");
   firstStylist.save();
   Stylist secondStylist = new Stylist("Work");
   secondStylist.save();
   assertEquals(true, Stylist.all().get(0).equals(firstStylist));
   assertEquals(true, Stylist.all().get(1).equals(secondStylist));
 }

 @Test
 public void getId_StylistsInstantiateWithAnId_1() {
   Stylist testStylist = new Stylist("Home");
   testStylist.save();
   assertTrue(testStylist.getId() > 0);
 }

 @Test
 public void find_returnsStylistWithSameId_secondStylist() {
   Stylist firstStylist = new Stylist("Home");
   firstStylist.save();
   Stylist secondStylist = new Stylist("Work");
   secondStylist.save();
   assertEquals(Stylist.find(secondStylist.getId()), secondStylist);
 }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Stylist firstStylist = new Stylist("Household chores");
    Stylist secondStylist = new Stylist("Household chores");
    assertTrue(firstStylist.equals(secondStylist));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Stylist myStylist = new Stylist("Household chores");
    myStylist.save();
    assertTrue(Stylist.all().get(0).equals(myStylist));
  }

  @Test
  public void save_assignsIdToObject() {
    Stylist myStylist = new Stylist("Household chores");
    myStylist.save();
    Stylist savedStylist = Stylist.all().get(0);
    assertEquals(myStylist.getId(), savedStylist.getId());
  }

  @Test
  public void getClients_retrievesALlClientsFromDatabase_ClientsList() {
    Stylist myStylist = new Stylist("Household chores");
    myStylist.save();
    Client firstClient = new Client("Mow the lawn", myStylist.getId());
    firstClient.save();
    Client secondClient = new Client("Do the dishes", myStylist.getId());
    secondClient.save();
    Client[] Clients = new Client[] { firstClient, secondClient };
    assertTrue(myStylist.getClients().containsAll(Arrays.asList(Clients)));
  }

}
