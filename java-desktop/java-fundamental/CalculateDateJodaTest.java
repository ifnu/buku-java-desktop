import org.joda.time.DateTime;
public class CalculateDateJodaTest {
  public static void main(String[] args) {
    DateTime d = new DateTime(2000,1,1,0,0,0,0);
    System.out.println("date : " + d.plusDays(20).toString("dd MMM yyyy HH:mm:ss"));
  }
}