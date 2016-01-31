import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
public class CalendarTest {
  public static void main(String[] args) {
    Calendar calendar = new GregorianCalendar(2011,Calendar.MARCH,29);
    int maxFeb = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    System.out.println(maxFeb);
    System.out.println(new SimpleDateFormat("F").format(calendar.getTime()));
    System.out.println(new SimpleDateFormat("dd MMM yyyy hh:mm:ss a z").format(new Date()));
  }
}
