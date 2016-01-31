import org.joda.time.Duration;
import org.joda.time.DateTime;
public class DurationTest {
  public static void main(String[] args) {
    Duration duration = new Duration(10000); // 10 detik
    System.out.println("duration : " + duration);
    DateTime now = new DateTime();
    DateTime oneMonthLater = now.plusMonths(1);
    duration = new Duration(now, oneMonthLater); 
    System.out.println("duration of one month : " + duration);
    Duration oneHour = new Duration(1000 * 60 * 60);
    DateTime oneHourLater = now.plus(oneHour);
    System.out.println("one hour later : " + oneHourLater);
  }
}