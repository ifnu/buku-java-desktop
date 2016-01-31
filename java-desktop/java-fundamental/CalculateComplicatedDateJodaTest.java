import org.joda.time.DateTime;
public class CalculateComplicatedDateJodaTest {
  public static void main(String[] args) {
    DateTime d = new DateTime(2000,1,1,0,0,0,0);
    System.out.println("date : " + 
      d.plusMonths(1).plusDays(45).dayOfWeek()
        .withMaximumValue().toString("dd MMM yyyy HH:mm:ss"));
  }
}