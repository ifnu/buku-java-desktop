import org.joda.time.DateTime;
import org.joda.time.Interval;
public class IntervalTest {
  public static void main(String[] args) {
    DateTime now = new DateTime();
    DateTime oneMonthLater = now.plusMonths(1);
    Interval interval = new Interval(now, oneMonthLater); 
    System.out.println("interval : " + interval);
    System.out.println("start : " + interval.getStart());
    System.out.println("end :  " + interval.getEnd());
    System.out.println("duration : " + interval.toDuration());
  }
}